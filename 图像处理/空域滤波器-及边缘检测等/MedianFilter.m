% 中值滤波
% masksize = [N N]
% 表示采用N*N窗的中值滤波器
% 窗口取得越大，滤波效果越好，同样图像也会变得越模糊
function Img_out=MedianFilter(Img,masksize)
exsize=floor(masksize/2);   %各方向扩展大小
Imgex=padarray(Img,[exsize,exsize],'replicate','both'); %扩展图片
[m,n]=size(Img);
Img_out=Img;    %将Img_out准备为和Img相同的size
for i=1:m
    for j=1:n
        neighbor=Imgex(i:i+masksize-1,j:j+masksize-1);  %截取邻域
        Img_out(i,j)=median(neighbor(:));   %中值滤波
    end
end
end