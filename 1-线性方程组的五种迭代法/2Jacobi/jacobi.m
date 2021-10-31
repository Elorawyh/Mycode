function r = jacobi(A,B,varargin)
sizeA=size(A);
sizev=size(varargin);
if sizev(2) == 0
    rol = 0.001; %精度epsilon
    n = 1000;
    x = zeros(sizeA(1),1);
elseif sizev(2) == 1
    rol = varargin{1};
    n = 1000;
    x = zeros(sizeA(1),1);
elseif sizev(2) == 2
    rol = varargin{1};
    n = varargin{2};
    x = zeros(sizeA(1),1000);
elseif sizev(2) == 3
    rol = varargin{1};
    n = varargin{2};
    x = varargin{3};
else
    error('输入参数过多');
end
for i = 2:n
    fprintf('迭代%d次\n',i-1)
    for j = 1:sizeA(2)
        sum1=0;
        for k = 1:sizeA(1)
            if j == k
                continue;
            end
            sum1 = sum1 - x(k,i-1)*A(j,k)/A(j,j);
        end
        x(j,i)=B(j)/A(j,j)+sum1;
    end
    if any(abs(x(:,i)-x(:,i-1))>rol) == 0
       break;
    end
end
r = x;
end
