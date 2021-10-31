%反谐波均值滤波：
% Q为正数时，对胡椒噪声有较好的恢复效果，
% 而对白盐噪声则恢复效果很差，几乎将整幅图像处理成白色。
% 当Q为辐射时，对白盐噪声有很好的恢复效果，
% 而对胡椒噪声的恢复效果很差，几乎将整幅图像处理成黑色。
function Img_out=ContraharmonicFilter(Img,Q)
[M,N]=size(Img);
ImgSize=3;   ImgSize=(ImgSize-1)/2;
Img_out=Img;
for x=1+ImgSize:1:M-ImgSize
    for y=1+ImgSize:1:M-ImgSize
        is=Img(x-ImgSize:1:x+ImgSize,y-ImgSize:1:y+ImgSize);
        Img_out(x,y)=sum(double(is(:)).^(Q+1))/sum(double(is(:)).^(Q));
    end
end
end