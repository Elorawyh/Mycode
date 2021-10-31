%A是系数矩阵
%B是方程组的右端向量
clc
clear
s = 13;
x1 = [];
for i=1:s
    fprintf('请分别依次单位输入%d位学号:\n',s);
    x = input('请输入：');
    x1(end+1) = x;
end
x1  %完整学号
m = 10;  %学号重复次数(m>=10)
n = s*m;
A1 = ones(n,n);
A2 = ones(n-1,n-1);
A = diag(diag(4*A1))+ diag(diag(-1*A2),1)...
    + diag(diag(-1*A2),-1);  %系数矩阵
x0 = repmat(x1,1,m);  %学号重复m次得到的x0(x*)
B = A*x0.';  %利用b=Ax0生成的B
x = run(A,B,n);