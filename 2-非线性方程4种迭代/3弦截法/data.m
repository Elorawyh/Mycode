clc
clear
fun=inline('x^3-3*x-1');    %原方程
x=secant(fun,-5,5,1e-5,1000)     %求根区间