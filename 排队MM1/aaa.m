function [i,t]=aaa %顾客人数
i=1;e=0;w=0;
x(i)=random('exp',2); %顾客到达符合参数为2的泊松分布
c(i)=x(i);b(i)=x(i);
while(b(i)<=900) %工作日总时间
   y(i)=0.5*rand(1)+0.333; %服从区间[0.33，0.83]的均值分布
   %y(i)=normrnd(1,1); %服务时间服从均值为1.5，标准差为1的正态分布
   e(i)=b(i)+y(i) %服务完第i个人的时间
   w=w+b(i)-c(i); %第i个人累计等待时间
   i=i+1;
   x(i)=random('exp',2);
   c(i)=c(i-1)+x(i); %第i+1个顾客出现时间
   b(i)=max(c(i),e(i-1)); %开始服务时间
end
i=i-1;
t=w/i;
fprintf('一天服务顾客人数：%f位 \n', i);
fprintf('每位顾客平均等待时间：%f分钟 \n', t);
