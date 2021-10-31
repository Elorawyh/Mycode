%加高斯噪声：
%% 法一
% g=imnoise( f , 'gaussian' , m , var )
% 将均值为m、方差为var的高斯噪声加到图像f上。
% 默认值为均值是0、方差是0.01的噪声。
%% 法二
% !!!!!!!!!需要灰度化图像才能使用!!!!!!!!!!!!

% 高斯噪声的效果与均值、方差两个参数密切相关，
% 方差越大时越模糊，颗粒感更大、更重，
% 标准差越大时，图像越亮、越偏白。
% 均值av、方差std
function Img_out=GaussianNoise(Img,av,std)
[M,N]=size(Img);
u1=rand(M,N);   u2=rand(M,N);
x=std*sqrt(-2*log(u1)).*cos(2*pi*u2)+av;
Img_out=uint8(255*(double(Img)/255+x));
end