% Laplacian边缘检测算法
function Img_out=LaplaceEdgeDetector(Img)
laplace=[1,1,1;1,-8,1;1,1,1];   %构造拉普拉斯算子
Img_out=uint8(conv2(Img,laplace,'same'));
end
% 在当图像比较"干净"时有较好的边缘检测作用，且精度明显优于Sobel算法
% 但是Laplacian对噪声过于敏感，很容易受到噪音干扰，