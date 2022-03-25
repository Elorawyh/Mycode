from game_sprite import *


class GameManage:
    """
    游戏管理类
    功能: 管理游戏的三大阶段
    """

    def __init__(self):
        """
        游戏准备阶段
        作用: 做一些初始化工作
        """
        # 1. 设置窗口
        self.window = pygame.display.set_mode(SCREEN_RECT.size)
        # 2. 设置帧率
        # fps -> (游戏循环的执行次数)
        # 60  -> 执行游戏循环约0.02s 一帧
        # 2.1 创建时钟对象
        self.clock = pygame.time.Clock()
        # 3. 创建游戏精灵
        self.__create_sprite()
        # 4. 循环条件
        self.isRunning = True
        # 4. 1 设置定时器事件
        pygame.time.set_timer(CREATE_ENEMY_STONE_EVENT, 1000)
        # 5. 初始化pygame内部的模块
        pygame.init()

    def __create_sprite(self):
        """创建游戏精灵的"""
        # 1. 创建背景精灵
        bg1 = BackGround()
        bg2 = BackGround(is_overlap=False)
        # 1.1 创建背景精灵组
        self.back_group = pygame.sprite.Group(bg1, bg2)

        # 2. 创建玩家飞机精灵
        self.player = Player()
        self.player_group = pygame.sprite.Group(self.player)

        # 3. 创建石头精灵组
        self.stone_group = pygame.sprite.Group()

        # 4. 创建爆炸精灵组
        self.exp_group = pygame.sprite.Group()

    def __event(self):
        """事件监听"""
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                # 说明用户点击了x 触发了关闭窗口的事件
                self.isRunning = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    # 按下了空格键
                    self.player.shoot()
            elif event.type == CREATE_ENEMY_STONE_EVENT:
                for i in range(random.randint(1, 4)):
                    stone = EnemyStone(STONE_NAME_LIST[random.randint(0, len(STONE_NAME_LIST)-1)])
                    self.stone_group.add(stone)

    def __collided(self):
        """碰撞检测"""
        # 1. 用于检测碰撞的方法
        hits = pygame.sprite.groupcollide(self.player.bullets_group, self.stone_group, True, True)
        # 1.1 产生爆炸效果
        for hit in hits:
            exp = Explosion(hit.rect.center, SONIC)
            # 把爆炸精灵添加到精灵组
            self.exp_group.add(exp)

    def __update_sprites(self):
        """更新精灵"""

        # 先更新精灵组
        self.back_group.update()
        # 重新绘制
        self.back_group.draw(self.window)

        self.player_group.update()
        self.player_group.draw(self.window)

        # 更新绘制子弹精灵组
        self.player.bullets_group.update()
        self.player.bullets_group.draw(self.window)

        # 更新
        self.stone_group.update()
        self.stone_group.draw(self.window)

        # 更新
        self.exp_group.update()
        self.exp_group.draw(self.window)

    def gaming(self):
        """
        游戏阶段
        作用: 实现游戏的主要逻辑
        """
        while self.isRunning:
            # 1. 设置帧率
            self.clock.tick(GAME_FRAME)
            # 2. 事件监听
            self.__event()
            # 3. 碰撞检测
            self.__collided()
            # 4. 更新精灵
            self.__update_sprites()
            # 5. 更新窗口
            pygame.display.update()

        # 如果能执行到这里,说明游戏已经结束了
        GameManage.game_over()

    @classmethod
    def game_over(cls):
        """
        游戏结束
        作用: 实现游戏结束时的一些设置
        """
        # quit()这个方法,是对之前加载的模块从内存中释放掉
        pygame.quit()


if __name__ == '__main__':
    GameManage().gaming()
