% 加椒盐噪声：
% 椒盐噪声的两个参数分别表示胡椒噪声和白盐噪声的密度。
function Img_out=SaltPepperNoise(Img,a,b)
[M,N]=size(Img);
x=rand(M,N);
Img_out=Img;
Img_out(find(x<=a))=0;
Img_out(find(x>a&x<(a+b)))=255;
end