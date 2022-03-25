/*反位数
# include<stdio.h>
int main()
{
	printf("请输入一个正整数:\n");
	int temp,sum=0;
	scanf("%d",&temp);
	while(temp!=0)
	{
		sum=sum*10+temp%10;
		temp/=10;
	}
	printf("该正整数的反位数为：%d\n",sum);
	return 0;
}*/

/*取出某正整数中的偶数位
# include<stdio.h>
int main()
{
	printf("请输入一个正整数:\n");
	int m,temp,sum=0;
	scanf("%d",&temp);
	while(temp!=0)
	{
		m=temp%10;
		if(m%2==0)
			sum=sum*10+m;
		temp/=10;
	}
	int u=0;
	while(sum!=0)
	{
		u=u*10+sum%10;
		sum/=10;
	}
	printf("该正整数的偶数位为：%d\n",u);
	return 0;
}*/

/*判断101-200之间有多少个素数
# include<stdio.h>
# include<math.h>
int main()
{
	int t,sum=0,i;
	for(t=101;t<=200;t++)
	{
		int k=0;
		double u=sqrt(t*1.0);
		for(i=2;i<=u;i++)
		{
			if(t%i==0)
				k++;
		}
		if(k==0)
			sum++;
	}
	printf("101-200之间共有%d个素数\n",sum);
	return 0;
}*/

/*杨辉三角形 */
# include<stdio.h>
int main()
{
	int a[20][20]={0},n,i,j;
	printf("请输入杨辉三角形的层数(0<n<=20)：\n");
	scanf("%d",&n);
	printf("%d层杨辉三角形为：\n",n);
	for(i=0;i<n;i++)
		for(j=0;j<=i;j++)
		{
			a[i][0]=1;
			a[i][j]=a[i-1][j-1]+a[i-1][j];
			a[i][i]=1;
		}
	for(i=0;i<n;i++)
	{
		int t;
		for(t=i;t<n;t++)
			printf("   ");
		for(j=0;j<n;j++)
			if(a[i][j]!=0)
				printf("%3d   ",a[i][j]);
		printf("\n");
	}
	return 0;
} 