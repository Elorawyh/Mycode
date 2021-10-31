function [x,i] = f1(A,B,x_0,max_i,rol)
x=x_0;
fprintf('\n x0= ');
fprintf('   %10.6f',x_0);
r=B-A*x;
d=r;
D={};
I={};
for k=0:max_i
    alpha=(r'*r)/(d'*A*d);
    xx=x+alpha*d;
    rr=B-A*xx;
    if (norm(rr,2)/norm(B,2))<= rol
        fprintf('\n 找到');
        i = k+1;
        x=xx;
        r=rr;
        fprintf('\n x%d = ',k+1);
        fprintf('   %10.6f',x);
        I{1}=x;
        fprintf('\n r%d = ',k+1);
        fprintf('   %10.6f',r);
        I{2}=r;
        xlswrite('data.xlsx',cell2mat(I),2); % 存放到data.xlsx的第2张sheet
        return
    end
    beta=(rr'*rr)/(r'*r);
    d=rr+beta*d;
    x=xx;
    r=rr;
    fprintf('\n x%d = ',k+1);
    fprintf('   %10.6f',x);
    D{k+1}=x;
    xlswrite('data.xlsx',cell2mat(D),1); % 存放到data.xlsx的第1张sheet
end
i = max_i;
return 
end