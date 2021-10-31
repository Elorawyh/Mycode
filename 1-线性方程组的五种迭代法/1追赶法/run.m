function [x] = run(A,B,n)
a=[0;diag(tril(A,-1),-1)];%下对角线
b=diag(A);%中对角线
c=[diag(triu(A,1),1);0];%上对角线
l=zeros(size(a,1),1);%求L
u=zeros(size(b,1),1);%求U
x=zeros(n,1);
y=zeros(n,1);
u(1)=b(1);
for i=2:n
    l(i)=a(i)/u(i-1);
    u(i)=b(i)-l(i)*c(i-1);
end
y(1)=B(1);
for i=2:n
    y(i)=B(i)-l(i)*y(i-1);
end
x(n)=y(n)/u(n);
for i=n-1:-1:1
    x(i)=(y(i)-c(i)*x(i+1))/u(i);
end
end