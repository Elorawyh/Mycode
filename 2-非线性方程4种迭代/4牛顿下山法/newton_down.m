function [x,k]=newton_down(x_0,fun,dfun,eps) 
%fun是原函数，fun是导函数，error是收敛误差，x0是迭代初始点
x=x_0;
y=f(fun,x);
k=1;	        %标记迭代了多少次
while abs(y)>eps
    d=1;
    x2=x-d*y/f(dfun,x);
    while abs(f(fun,x2))>abs(f(fun,x))
        d=d/2;
        x2=x-d*y/f(dfun,x);
    end
    x=x2;
    y=f(fun,x);
    k=k+1;
end
