function Conjugate(A,B,x_0,rol)
max_i=1000; %迭代次数上限
fprintf('\n');
[y,i]= f1(A,B,x_0,max_i,rol);
fprintf('\n');
fprintf('迭代次数:\n   %d \n',i);
fprintf('方程的解: \n');
fprintf('%10.6f',y);
xlswrite('data.xlsx',y,3); % 存放到data.xlsx的第3张sheet
end