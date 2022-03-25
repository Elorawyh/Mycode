import pygame
import os


# 爆炸动画字典需要用的key
REGULAR = "regular"
SONIC = "sonic"
# 游戏屏幕的尺寸
SCREEN_RECT = pygame.Rect(0, 0, 480, 600)
# 游戏帧率
GAME_FRAME = 60
# 游戏得根目录
GAME_SRC = os.path.dirname(__file__)
# img文件夹得路径
IMG_SRC = os.path.join(GAME_SRC, "img")
# 自定义用户事件
CREATE_ENEMY_STONE_EVENT = pygame.USEREVENT
CREATE_ENEMY_PLANE_EVENT = pygame.USEREVENT + 1
# 石头图片列表
STONE_NAME_LIST = [
    "meteorBrown_big1.png", "meteorBrown_big2.png",
    "meteorBrown_med1.png", "meteorBrown_med3.png",
    "meteorBrown_small1.png", "meteorBrown_small2.png"
]
# 爆炸效果字典
EXP_ANIMATION = {
    REGULAR: [],
    SONIC: []
}


def add_dict(img_name):
    """通过图片名字加载图像得方法"""
    exp_img = pygame.image.load(os.path.join(IMG_SRC, img_name))
    # 3. 修改尺寸
    new_exp_img = pygame.transform.scale(exp_img, (50, 50))
    return new_exp_img


for i in range(9):
    # 1. 拼接图片名称
    file_name_r = 'regularExplosion0{}.png'.format(i)
    EXP_ANIMATION[REGULAR].append(add_dict(file_name_r))

    file_name_s = 'sonicExplosion0{}.png'.format(i)
    EXP_ANIMATION[SONIC].append(add_dict(file_name_s))

# print(EXP_ANIMATION)



