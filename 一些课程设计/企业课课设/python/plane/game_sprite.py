from game_constant import *
import random


class BaseSprite(pygame.sprite.Sprite):
    """
    基类, 用来做所有游戏内精灵的模板, 提供统一的特征与行为
    """
    def __init__(self, img_src, speed=1):
        # 1. 先调用父类的初始化方法
        super().__init__()

        # 2. 根据路径将图片加载到内存中
        self.image = pygame.image.load(img_src)
        # 3. 获取图像的尺寸(矩形区域)
        self.rect = self.image.get_rect()
        self.speed = speed

    def update(self):
        """更新精灵的方法(更新精灵的位置)"""
        super().update()
        # super(BaseSprite, self).update()
        # 默认所有继承自该类的精灵 由上至下进行运动
        self.rect.y += self.speed


class BackGround(BaseSprite):
    """
    背景精灵类
    功能: 用来控制背景精灵的行为
    """
    def __init__(self, is_overlap=True):
        super().__init__(os.path.join(IMG_SRC, "starfield.png"))

        if not is_overlap:
            self.rect.y = -self.rect.height

    def update(self):
        super().update()

        # 判断,如果背景精灵移出到屏幕外, 即重置它的位置
        if self.rect.y >= SCREEN_RECT.height:
            self.rect.y = -self.rect.height


class Player(BaseSprite):
    """
    玩家精灵类
    功能: 用来控制玩家的行为
    """
    def __init__(self):
        super().__init__(os.path.join(IMG_SRC, "playerShip1_orange.png"))

        # 重新设置图像(精灵)的尺寸
        self.image = pygame.transform.scale(self.image, (50, 38))
        self.rect = self.image.get_rect()

        # 设置飞机精灵的初始位置
        self.rect.centerx = SCREEN_RECT.width * 0.5
        self.rect.bottom = SCREEN_RECT.height - 30

        # 初始化飞机水平速度
        self.speedx = 0

        # 初始化子弹精灵组
        self.bullets_group = pygame.sprite.Group()

    def update(self):

        self.speedx = 0

        keystate = pygame.key.get_pressed()
        if keystate[pygame.K_LEFT]:
            self.speedx = -8
        if keystate[pygame.K_RIGHT]:
            self.speedx = 8

        self.rect.x += self.speedx

        # 限制飞机的飞行区域
        if self.rect.x < 0:
            self.rect.x = 0
        elif self.rect.x > SCREEN_RECT.width - self.rect.width:
            self.rect.right = SCREEN_RECT.right

    def shoot(self):
        # 1. 创建子弹
        bullet = Bullet()

        # 1.1 设置子弹的位置
        bullet.rect.centerx = self.rect.centerx
        bullet.rect.bottom = self.rect.y - 20

        # 2. 将子弹精灵加入子弹精灵组中
        self.bullets_group.add(bullet)


class PlayerLife(BaseSprite):
    """玩家生命值精灵类"""
    pass


class Bullet(BaseSprite):
    """
    子弹精灵类
    功能: 用来控制子弹的行为
    """
    def __init__(self):
        super().__init__(os.path.join(IMG_SRC, "laserRed16.png"), speed=-10)


class EnemyStone(BaseSprite):
    """敌人石头精灵类"""
    def __init__(self, img_name):
        super().__init__(os.path.join(IMG_SRC, img_name))

        # 设置陨石的随机速度
        self.speed = random.randint(5, 9)
        self.speedx = random.randint(-3, 3)

        # 设置陨石的初始位置
        self.rect.bottom = -10
        self.rect.x = random.randint(0, SCREEN_RECT.width - self.rect.width)

    def update(self):
        super().update()

        # 叠加水平速度
        self.rect.x += self.speedx


class EnemyPlane(BaseSprite):
    """敌人飞机精灵类"""
    pass


class Explosion(pygame.sprite.Sprite):

    def __init__(self, center, exp_animation_name):
        super().__init__()

        # 获取爆炸字典的key
        self.exp_name = exp_animation_name
        # 先展示第一张图片
        self.image = EXP_ANIMATION[self.exp_name][0]
        # 获取尺寸
        self.rect = self.image.get_rect()
        # 设置第一张图片的显示位置
        self.rect.center = center

        # 索引
        self.index = 0
        # 切换图片的帧率
        self.rate = 1

        # 初始化第一张图片后获取的那一帧的时间
        self.last_time = pygame.time.get_ticks()

    def update(self):
        # 获取当前帧的游戏时间
        now_time = pygame.time.get_ticks()

        if now_time - self.last_time > self.rate:
            self.last_time = now_time
            # 索引+1
            self.index += 1

            if self.index == len(EXP_ANIMATION[self.exp_name]):
                self.kill()
            else:
                # 设置爆炸显示的位置
                last_center = self.rect.center
                self.image = EXP_ANIMATION[self.exp_name][self.index]
                self.rect = self.image.get_rect()
                # 设置中心点位置
                self.rect.center = last_center

