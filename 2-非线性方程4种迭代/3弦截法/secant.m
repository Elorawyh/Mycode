function [p1,err,k,y] = secant(f,p0,p1,eps,max1)
p0,p1,feval(f,p0),feval(f,p1),k=0,
for k = 1:max1
p2 = p1 - feval(f,p1)*(p1-p0)/(feval(f,p1)-feval(f,p0));
err = abs(p2 - p1);
p0 = p1;
p1 = p2;
p1,err,k,y=feval(f,p1)
if(err < eps) | (y == 0)
break
end
end