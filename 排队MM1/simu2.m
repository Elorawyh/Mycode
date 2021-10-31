function simu2
M(1)=0;T(1)=0;
for i=1:500 %天数
    [M(i),T(i)]=aaa;
end
mean_M=mean(M);
mean_T=mean(T);
fprintf('五百天每天平均服务顾客数：%f位 \n',  mean_M);
fprintf('五百天每位顾客平均等待时间：%f分钟 \n', mean_T);