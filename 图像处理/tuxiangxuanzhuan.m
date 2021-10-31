clc
clear
A=imread('newspaper.jpg');
figure,imshow(A);
title('origin'); %输出原图像
I1=imrotate(A,1,'bicubic','crop'); %双三次插值法旋转1°，并剪切图像，使得到的图像和原图像大小一致
I2=imrotate(A,50,'bicubic','crop'); %双三次插值法旋转50°，并剪切图像，使得到的图像和原图像大小一致
I3=imrotate(A,30,'bicubic','crop'); %双三次插值法旋转30°，并剪切图像，使得到的图像和原图像大小一致
I4=imrotate(A,60,'bicubic','crop'); %双三次插值法旋转60°，并剪切图像，使得到的图像和原图像大小一致
figure,imshow(I1);
title('image-1°');
figure,imshow(I2);
title('image-50°');
figure,imshow(I3);
title('image-30°');
figure,imshow(I4);
title('image-60°');