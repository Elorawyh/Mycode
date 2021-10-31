%Gray-level Co-occurrence Matrix（灰度共生矩阵）
clc
clear
I=[1,1,5,6,8; 2,3,5,7,1; 4,5,7,1,2; 8,5,1,2,5];%像素矩阵

row=size(I,1);%获取矩阵行数
column=size(I,2);%获取矩阵列数
GV_MIN=min(min(I));%获取最小灰度值
GV_MAX=max(max(I));%获取最大灰度值
d=1;%设置距离
COUNT_FLTR=zeros(GV_MAX,GV_MAX);%计数矩阵（从左到右）
COUNT_FTTL=zeros(GV_MAX,GV_MAX);%计数矩阵（从上到下）
COUNT_LD=zeros(GV_MAX,GV_MAX);%计数矩阵（左对角线）
COUNT_RD=zeros(GV_MAX,GV_MAX);%计数矩阵（右对角线）

%从左到右
for  m=GV_MIN:GV_MAX
    for n=GV_MIN:GV_MAX
        for i=1:row
            for j=1:d:column-d
                 if(I(i,j)==m&&I(i,j+d)==n)%判断像素对
                     COUNT_FLTR(m,n)=COUNT_FLTR(m,n)+1;
                 end
            end
        end
    end
end

%从上到下
for  m=GV_MIN:GV_MAX
    for n=GV_MIN:GV_MAX
        for i=1:d:row-d
            for j=1:column
                 if(I(i,j)==m&&I(i+d,j)==n)%判断像素对
                     COUNT_FTTL(m,n)=COUNT_FTTL(m,n)+1;
                 end
            end
        end
    end
end

%左对角线
for  m=GV_MIN:GV_MAX
    for n=GV_MIN:GV_MAX
        for k=-1:1
            diag_M=diag(I,k);%左对角线向量
            for i=1:d:length(diag_M)-d
                 if(diag_M(i)==m&&diag_M(i+d)==n)%判断像素对
                     COUNT_LD(m,n)=COUNT_LD(m,n)+1;
                 end
            end
        end
    end
end

%右对角线
I1=rot90(I);%处理原像素矩阵
for  m=GV_MIN:GV_MAX
    for n=GV_MIN:GV_MAX
        for k=-1:1
            arcdiag_M=diag(I1,k);%右对角线向量
            for i=1:d:length(arcdiag_M)-d
                 if(arcdiag_M(i)==m&&arcdiag_M(i+d)==n)%判断像素对
                     COUNT_RD(m,n)=COUNT_RD(m,n)+1;
                 end
            end
        end
    end
end

%归一化得到灰度共生矩阵
disp('从左到右得到的灰度共生矩阵：')
GRAY_FLTR=COUNT_FLTR./sum(sum(COUNT_FLTR))
disp('从上到下得到的灰度共生矩阵：')
GRAY_FTTL=COUNT_FTTL./sum(sum(COUNT_FTTL))
disp('从左对角线得到的灰度共生矩阵：')
GRAY_LD=COUNT_LD./sum(sum(COUNT_LD))
disp('从右对角线得到的灰度共生矩阵：')
GRAY_RD=COUNT_FLTR./sum(sum(COUNT_RD))
