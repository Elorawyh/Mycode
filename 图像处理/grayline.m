% 计算每根射线所打到图像上的像素累计和，
% 得到极坐标灰度曲线
function re = grayline(img)

[m,n]=size(img);

cy=m/2;
cx=n/2;

%求中心点到图像四个角的距离
up_l=sqrt((cy-0)^2+(cx-0)^2);
up_r=sqrt((cy-0)^2+(cx-n)^2);
down_l=sqrt((cy-m)^2+(cx-0)^2);
down_r=sqrt((cy-m)^2+(cx-n)^2);

num=3600;
%求中心点距离四角距离的最大值，作为变换后图像的高。
%这个最大值也是极坐标变换的极径
r=round(max([up_l up_r down_l down_r]));
re = zeros(num,1);

for i=0:1:r          %纵坐标代表极径，不同情况不一样
    for j=1:num       %横坐标代表极角，为3600
        %cy,cx作为极坐标变换中心坐标，需要作为偏移量相加
        ind = j/10;
        h=round(cy+i*sin(ind*pi/180));
        w=round(cx+i*cos(ind*pi/180));
        
        if h>0 && w> 0&& h<=m && w<=n       %超出原图像的像素忽略
            re(j)= re(j) +double(img(h,w));
        end
    end
end
re = re/sum(re);
end