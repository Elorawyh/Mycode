function [c,err,yc] = cut(f,a,b,eps)
if nargin < 4
    eps = 1e-5;
end
ya = feval(f,a);
yb = feval(f,b);
if yb == 0
    c = b;
    return
end
if ya*yb>0
    disp('(a,b)不是有根区间');
    return
end
max1 = 1 + round((log(b-a)-log(eps))/log(2));
for k = 1:max1
    c = (a+b)/2;
    yc = feval(f,c);
    fprintf('迭代第%d次\n',k)
    if yc == 0
        a = c;
        b = c;
        break;
    elseif yb*yc > 0
        b = c;
        yb = yc;
    else
        a = c;
        ya = c;
    end
    if(b-a) < eps
        break
    end
end
k = (a+b)/2;
c = (a+b)/2;
err = abs(b-a);
yc = feval(f,c);