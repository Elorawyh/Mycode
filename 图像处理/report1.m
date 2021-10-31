% report
close all
%% 清除
clc
clear
%% 输入图像
Img = imread( 'newspaper.jpg');
% 输出原图像
figure, imshow(Img);
title( 'origin');
%% 为图像增加高斯噪声
% 设置均值av、方差std
av = 0; std = 0.5;
% 将均值为avg、方差为std的高斯噪声加到图像Img上。
% 默认值为均值是0、方差是0.01的噪声
I1 = imnoise( Img, 'gaussian' , av , std );
% 输出高斯噪声图像
figure, imshow(I1);
title( 'GaussianNoise');
%% 为图像增加椒盐噪声
% 设置噪声密度d
d = 0.5;
% 用椒盐噪声污染图像Img，其中d是噪声密度（即包括噪声值的图像区域的百分比）。
% 因此，大约有d*numel（Img）个像素受到影响。默认的噪声密度为0.05。
I2 = imnoise( Img, 'salt & pepper', d);
%  输出椒盐噪声图像
figure, imshow(I2);
title( 'Salt & PepperNoise');
%% 使用中值滤波和高斯滤波进行恢复
% %% 中值滤波器
% %% 采用3*3窗的中值滤波器
% masksize = [3 3];
% % 处理高斯噪声图像
% I1_M3=MedianFilter(I1,masksize);
% % 处理椒盐噪声图像
% I2_M3=MedianFilter(I2,masksize);
% % 输出高斯噪声恢复后图像
% figure, imshow(I1_M3);
% title( 'GaussianNoise-M3');
% %  输出椒盐噪声恢复后图像
% figure, imshow(I2_M3);
% title( 'Salt & PepperNoise-M3');
% %% 采用7*7窗的中值滤波器
% masksize = [7 7];
% % 处理高斯噪声图像
% I1_M7=MedianFilter(I1,masksize);
% % 处理椒盐噪声图像
% I2_M7=MedianFilter(I2,masksize);
% % 输出高斯噪声恢复后图像
% figure, imshow(I1_M7);
% title( 'GaussianNoise-M7');
% %  输出椒盐噪声恢复后图像
% figure, imshow(I2_M7);
% title( 'Salt & PepperNoise-M7');
% %% 高斯滤波器
%% 图像黑白化(二值化)(彩色处理不了)(新发现彩的也能处理)
I1_G=rgb2gray(I1);
I2_G=rgb2gray(I2);
% 输出噪声图的灰度图
figure, imshow(I1_G);
title( 'GaussianNoise-Gray');
figure, imshow(I2_G);
title( 'Salt & PepperNoise-Gray');
% %% 采用3*3窗的高斯滤波器
% masksize = [3 3];
% % masksize = [5 5];
% % sigma (参考值0.8)
% sigma = 1;
% % 处理高斯噪声图像
% I1_G3 = GaussianFilter( I1, masksize, 'replicate');
% % 处理椒盐噪声图像
% I2_G3 = GaussianFilter( I2, masksize, 'replicate');
% % 输出高斯噪声恢复后图像
% figure, imshow(I1_G3);
% title( 'GaussianNoise-G3');
% %  输出椒盐噪声恢复后图像
% figure, imshow(I2_G3);
% title( 'Salt & PepperNoise-G3');
% %% 采用7*7窗的高斯滤波器
% masksize = [7 7];
% % sigma (参考值0.8)
% sigma = 1;
% % 处理高斯噪声图像
% I1_G7 = GaussianFilter( I1, masksize, 'replicate');
% % 处理椒盐噪声图像
% I2_G7 = GaussianFilter( I2, masksize, 'replicate')
% % 输出高斯噪声恢复后图像
% figure, imshow(I1_G7);
% title( 'GaussianNoise-G7');
% %  输出椒盐噪声恢复后图像
% figure, imshow(I2_G7);
% title( 'Salt & PepperNoise-G7');
%% 使用频域滤波器
%% 巴特沃斯低通滤波
% 参考参数D0 = 200, n = 5
D0 = 700; n = 20;
% 处理高斯噪声图像
I1_BwLP = ButterworthL_PFilter(I1_G, D0, n);
% 处理椒盐噪声图像
I2_BwLP = ButterworthL_PFilter(I2_G, D0, n);
% 输出高斯噪声恢复后图像
figure, imshow(I1_BwLP);
title( 'GaussianNoise-BwLP');
%  输出椒盐噪声恢复后图像
figure, imshow(I2_BwLP);
title( 'Salt & PepperNoise-BwLP');
%% 高斯高通滤波器
% 参考参数D0 = 100
D0 = 100;
% 处理高斯噪声图像
I1_GHP = GaussianH_PFilter(I1_G, D0);
% 处理椒盐噪声图像
I2_GHP = GaussianH_PFilter(I2_G, D0);
% 输出高斯噪声恢复后图像
figure, imshow(I1_GHP);
title( 'GaussianNoise-GHP');
%  输出椒盐噪声恢复后图像
figure, imshow(I2_GHP);
title( 'Salt & PepperNoise-GHP');







