function x=newton(fun,dfun,x0,ep,N)
if nargin<5
    N = 1000;
end
if nargin<4
    ep = 1e-5;
end
k=0;
while k<N
    k
    x = x0-feval(fun,x0)/feval(dfun,x0)
    if abs(x-x0)<ep
        break;
    end
    k=k+1;
    x0=x;
end
if k==N
    warning('已到达迭代次数上限！');
end