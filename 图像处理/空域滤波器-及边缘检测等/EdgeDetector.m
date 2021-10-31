% Canny边缘检测算法
% 完整的Matlab代码实现
% （当然也可以调用edge函数，但是学习的话
% 最好自己从底层实现一遍嘛是吧）
I=imread('cameraman.tif');
%I=rgb2gray(I);
figure(1);subplot(121);imshow(I);xlabel('原图像');
[m n]=size(I);
a=double(I);
sigma=1;               %高斯标准差
%highThresh=0.0625;     %上阈值
%lowThresh=0.0250 ;    %下阈值
  
  
%=======================高斯滤波&梯度计算=======================

%%%%%%%%%%%%%%%%%%%%%%%%%Old%%%%%%%%%%%%%%%%%%%%%%%%%%%
%pw = 1:30; 
%ssq = sigma^2;
%width = find(exp(-(pw.*pw)/(2*ssq))>0.0001,1,'last');
%if isempty(width)
%    width = 1;  
%end
    
%t = (-width:width);
%gauss = exp(-(t.*t)/(2*ssq))/(2*pi*ssq);     % 一维高斯滤波器
    

%[x,y]=meshgrid(-width:width,-width:width);
%gauss2=-x.*exp(-(x.*x+y.*y)/(2*ssq))/(pi*ssq);   %二维高斯滤波器
    
%对图像进行高斯滤波平滑
%aSmooth=imfilter(a,gauss,'conv','replicate');   % 沿着X轴卷积
%aSmooth=imfilter(aSmooth,gauss','conv','replicate'); % 沿着Y轴卷积
    
%使用二维高斯对图像进行卷积
%dx = imfilter(aSmooth, gauss2, 'conv','replicate');
%dy = imfilter(aSmooth, gauss2', 'conv','replicate');
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


% 根据高斯标准差计算滤波器长度
filterExtent = ceil(4*sigma);
x = -filterExtent:filterExtent;

% 生成一维高斯核
c = 1/(sqrt(2*pi)*sigma);
gaussKernel = c * exp(-(x.^2)/(2*sigma^2));

% 标准化
gaussKernel = gaussKernel/sum(gaussKernel);

% 数值梯度函数(Gaussian核的生成1-D导数)
derivGaussKernel = gradient(gaussKernel);

% 标准化
negVals = derivGaussKernel < 0;
posVals = derivGaussKernel > 0;
derivGaussKernel(posVals) = derivGaussKernel(posVals)/sum(derivGaussKernel(posVals));
derivGaussKernel(negVals) = derivGaussKernel(negVals)/abs(sum(derivGaussKernel(negVals)));

    
% 对图像进行高斯滤波平滑
aSmooth=imfilter(a,gaussKernel,'conv','replicate');   % 沿着X轴卷积
aSmooth=imfilter(aSmooth,gaussKernel','conv','replicate');  % 沿着Y轴卷积
%hv=fspecial('sobel');

% 计算梯度
dx = imfilter(aSmooth, derivGaussKernel, 'conv','replicate');
dy = imfilter(aSmooth, derivGaussKernel', 'conv','replicate');


mag = hypot(dx,dy); 
magmax = max(mag(:));
if magmax>0
    magGrad = mag / magmax;   % 梯度标准化
end

% 阈值选择
%PercentOfPixelsNotEdges = 0.7; 
counts=imhist(magGrad, 64);
highThresh = find(cumsum(counts) > 0.7*m*n, 1 ,'first' ) / 64;
lowThresh = 0.4*highThresh;

%figure(8);imshow(magGrad);
%%========================高斯滤波========================================
%w=fspecial('gaussian',[5 5]);
%img=imfilter(img,w,'replicate');
%figure;
%imshow(uint8(img))

%%===================sobel边缘检测=======================================
%hv=fspecial('sobel');
%dx=imfilter(img,hv,'replicate');      %求横边缘
%hh=hv';
%dy=imfilter(img,hh,'replicate');      %求竖边缘
%img=sqrt(dx.^2+dy.^2); 


%    magmax = max(img(:));%      （阈值选择归一化）
%    if magmax > 0
%        magGrad = img / magmax;
%    end
%figure;
%imshow(uint8(img));

I = thinAndThreshold(dx, dy, magGrad, lowThresh, highThresh);


%disp(lowThresh);
subplot(122);imshow(I);xlabel('canny边缘检测');
disp('高阈值TL: '+highThresh);
disp('低阈值TH: '+lowThresh);


%========================非极大值抑制和边缘连接=======================================
function H = thinAndThreshold(dx, dy, magGrad, lowThresh, highThresh)

E = cannyFindLocalMaxima(dx,dy,magGrad,lowThresh);  %非极大值抑制

if ~isempty(E)
    [rstrong,cstrong] = find(magGrad>highThresh & E);

    if ~isempty(rstrong) 
        H = bwselect(E, cstrong, rstrong, 8);   % 选定强边缘8连通目标
       % figure(2);imshow(H);
        
       % set(0,'RecursionLimit',1000);      %弱边缘连通（无太大作用，且运算时间过长）
       % [xstrong ystrong]=find(magGrad>highThresh & E);
       % for i=1:numel(xstrong);
       %     H = connect1(H,xstrong(i),ystrong(i),lowThresh,highThresh,magGrad);
       % end
       %  figure(3);imshow(H);
        
        H = bwmorph(H, 'thin', 1);      % 边缘细化
    else
        H = false(size(E));
    end
else
    H = false(size(E));
end
end

%========================弱边缘连接=======================================

function nedge=connect1(nedge,y,x,low,high,magGrad)       %种子定位后的连通分析
    neighbour=[-1 -1;-1 0;-1 1;0 -1;0 1;1 -1;1 0;1 1];  %八连通搜寻
    [m n]=size(nedge);

    for k=1:8
        yy=fix(y+neighbour(k,1));
        xx=fix(x+neighbour(k,2));


        if yy>=1 &&yy<=m &&xx>=1 && xx<=n  
            if magGrad(yy,xx)>=low & nedge(yy,xx)~=255 & magGrad(yy,xx)<high  
                nedge(yy,xx)=255;
                %disp('check check');
                %nedge=connect1(nedge,yy,xx,low,high,magGrad);
            end
        end
    end
end