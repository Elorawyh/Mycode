/*1
#include <stdio.h>
int main()
{
	int num[5]={1,2,3,4,5};
	int i;
	for(i=0;i<=5;i++)
		printf("%d",num[i]);
	return 0;
}*/


/*1改
#include <stdio.h>
int main()
{
	int num[5]={1,2,3,4,5};
	int i;
	for(i=0;i<5;i++)
		printf("%d",num[i]);
	return 0;
}*/

/*冒泡排序(从小到大)*/
#include <stdio.h>
int main()
{
	printf("请输入7个整型数：\n");
	int a[7],i,t,k=6;
	for(i=0;i<7;i++)
		scanf("%d",&a[i]);
	printf("\n");
	for(i=0;i<k;i++)
	{
		if(a[i]>a[i+1])
		{
			t=a[i];
			a[i]=a[i+1];
			a[i+1]=t;
			k--;
		}
	}
	for(i=0;i<7;i++)
		printf("%d",a[i]);
	return 0;
}

/*返回一个3*5矩阵的各元素地址
#include<stdio.h>
int main ()
{
	int;
	int a[3][5]={{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}};

}*/