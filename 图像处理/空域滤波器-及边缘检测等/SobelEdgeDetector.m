% Sobel边缘检测
% 构造x，y方向的Sobel算子Sx和Sy
% 分别用原图与Sx，Sy进行二维卷积，得到两个方向的梯度矩阵
% 将两个方向梯度分别求绝对值并相加得到图像的梯度矩阵
% 将梯度矩阵转成uint8格式，即为处理后图像
function [Img_out,gx,gy]=SobelEdgeDetector(Img)
sobely=[1,2,1;0,0,0;-1,-2,-1];  %构造y方向Sobel算子
sobelx=[1,0,-1;2,0,-2;1,0,-1];  %构造x方向Sobel算子
gx=conv2(Img,sobelx,'same');
gy=conv2(Img,sobely,'same');
Img_out=uint8(abs(gx)+abs(gy));
end
% 采用Sobel算法提取出的边缘范围较大，所以显示的边缘较为粗大，
% 看起来更加清晰明了，且对噪声有不错的抑制效果，但是精确度不高。