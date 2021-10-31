clc
clear
fun=inline('x^3-3*x-1');    %原方程
dfun=inline('3*x^2-3');    %原方程的微分
x=newton(fun,dfun,1.5)     %初值1.5