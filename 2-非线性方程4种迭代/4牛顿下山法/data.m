clc
clear
fun='x^3-3*x-1';    %原方程
dfun='3*x^2-3';    %原方程的微分
[x,y]=newton_down(-3,fun,dfun,1e-5)     %求根区间