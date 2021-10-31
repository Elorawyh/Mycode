clc
clear
sheet = 4; % 读取表中第sheet张sheet
[num,txt]= xlsread('data.xls', sheet);
% 读取数据，文字全部记为NaN
l_d = zeros(28,11); 
% 存储葡萄酒评分，行数为葡萄酒样品数，列数为品酒员人数加一
n = 12; % 所需要读取数据的行与行的行号的差值
j = 1; % 起始行数
m1 = 10; % 平均值所在行号与酒样品标号所在行号的差值
for i = 11:n:335; 
% 起始行数：步长：结束行数（excel最终行数减去无数字行数）
% 比如表格前3行全是文字/空格，表格总行数为 n，
% 那么结束行数取 n-3
    m = i-m1; % 样品酒的标号所在行 
    l_d(j,1) = num(m,1); % 矩阵第一列 = 样品酒标号
    l_d(j,2:11) = num(i,5:14);
    % 品酒员1至10号对样品酒num(m,1)评分记入矩阵第2至11列
    j=j+1;
end
savesheet = 2; % 数据存储于文件第savesheet张sheet
xlswrite('data1', l_d, savesheet); %输出乱序的样品酒得分