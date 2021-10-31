function [x,i] = steepest(A,B,x_0,rol,n)
% n是线性方程组的维度
max1 = 1000;
r = B-A*x_0;
% 求内积
c = dot(r,r)/dot(A*r,r);
x = x_0+c*r;
i = 1;
D = {};

while norm(x-x_0)>=rol
    x_0 = x;
    
    r = B-A*x_0;
    c = dot(r,r)/dot(A*r,r);
    x = x_0+c*r;
    fprintf('迭代第%d次\n',i);
    D{i} = x;
    xlswrite('x.xlsx',cell2mat(D),1); % 存放到x.xlsx的第一张sheet
    i = i+1;
    if(i>=max1)
        disp('迭代次数超过',max1,'次，方程组可能不收敛');
        return;
    end
end