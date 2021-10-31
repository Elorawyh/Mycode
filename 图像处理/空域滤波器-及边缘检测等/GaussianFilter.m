% 高斯滤波
% masksize = [N N]
% 表示采用N*N窗的中值滤波器
% sigma值越大，滤波效果越好，图像越模糊
% 窗口取得越大，滤波效果越好，同样图像也会变得越模糊
function Img_out = GaussianFilter( Img, masksize, sigma)
w = fspecial( 'gaussian', masksize, sigma);
%replicate:图像大小通过赋值外边界的值来扩展
%symmetric 图像大小通过沿自身的边界进行镜像映射扩展
Img_out = imfilter( Img, w, 'replicate');