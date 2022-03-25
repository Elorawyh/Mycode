/*三角形三边判断
# include<stdio.h>
# include<math.h>
int main()
{
	float a,b,c;
	printf("请输入三角形的三边（用逗号隔开）:\n");
	scanf("%f,%f,%f",&a,&b,&c);
	switch(a>0&&b>0&&c>0)
	{
		case 1:
			switch(a+b<c||a+c<b||b+c<a)
			{
				case 0:
					switch(fabs(a-b)<1e-19||fabs(b-c)<1e-19||fabs(c-a)<1e-19)
					{
						case 1:
							switch(a==b==c)
								{
									case 1:printf("该三角形是等边三角形\n");break;
									case 0:printf("该三角形是等腰三角形\n");break;
								};break;
						case 0:printf("该三角形不是等腰三角形\n");break;
					}
					switch(fabs(a*a+b*b-c*c)<1e-19||(b*b+c*c-a*a)<1e-19||(c*c+a*a-b*b)<1e-19)
					{
						case 1:printf("该三角形是直角三角形\n");break;
						case 0:printf("该三角形不是直角三角形\n");break;
					};break;
				case 1:printf("不构成三角形\n");break;
			};break;
		case 0:printf("数据错误，线段长度应为非负数！\n");break;
	}
	return 0;
}*/


/*成绩等级
# include<stdio.h>
int main()
{
	printf("请输入百分制成绩\n");
	int score;
	scanf("%d",&score);
	if(score>100||score<0)
		printf("Data errors!\n"
				"百分制成绩应当是0到100之间的数！\n");
	else if(90<=score&&score<=100)
		printf("score=%d,'A'\n",score);
	else if(80<=score&&score<90)
		printf("score=%d,'B'\n",score);
	else if(70<=score&&score<80)
		printf("score=%d,'C'\n",score);
	else if(60<=score&&score<70)
		printf("score=%d,'D'\n",score);
	else if(score<60)
		printf("score=%d,'E'\n",score);
	
	return 0;
}*/

/*寻找最大数与最小数*/
# include<stdio.h>
int main()
{
	int a,max,min,k=1;
	printf("请输入5个数(用空格隔开):\n");
	scanf("%d",&a);
	max=a;min=a;
	while(k<5)
	{
		scanf("%d",&a);
		if(a<min)
			min=a;
		if(a>max)
			max=a;
		k++;
	}
	printf("最大数=%d,最小数=%d\n",max,min);
	return 0;
}
