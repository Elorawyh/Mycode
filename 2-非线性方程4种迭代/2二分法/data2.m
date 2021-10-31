clc
clear
fun=inline('x^3-3*x-1');    %原方程
x=cut(fun,-5,5)     %求根区间