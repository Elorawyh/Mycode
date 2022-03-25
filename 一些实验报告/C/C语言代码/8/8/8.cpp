/*(1)
#include<stdio.h>
int main()
{
	int a=5,b=5,*p;
	p=&a;
	printf("%d,%ud\n",a,p);
	*p=8;
	printf("%d,%ud\n",a,p);
	p=&b;
	printf("%d,%ud\n",a,p);
	b=10;
	printf("%d,%ud\n",a,p);
	return 0;
}*/
/*(2)
#include<stdio.h>
int main()
{
	int i,*p,s=0,a[5]={5,6,7,8,9};
	p=a;
	for(i=0;i<5;i++,p++)
		s+=*p;
	printf("\ns=%d",s);
	return 0;
}*/
/*(3)
#include<stdio.h>
int main()
{
	int i,s1=0,s2=0,s3=0,s4=0,*p,a[5]={1,2,3,4,5};
	p=a;
	for(i=0;i<5;i++)
		s1+=p[i];
	for(i=0;i<5;i++)
		s2+=*(p+i);
	for(p=a;p<a+5;p++)
		s3+=*p;
	p=a;
	for(i=0;i<5;i++)
		s4+=*p++;
	printf("\n s1=%d,s2=%d,s3=%d,s4=%d,",s1,s2,s3,s4);
	return 0;
}*/
/*(4)ÄæÐòÅÅÁÐ£¨Ö¸Õë£©*/
#include<stdio.h>
#define N 10
int exchange(int,int);
int a[N],*p;
int main()
{
	int i,m;
	p=a;
	printf("printf 10 data:\n");
	for(i=0;i<N;i++)
		scanf("%d",&a[i]);
	for(i=0;i<N;i++)
	{
		a[i]=exchange(i,m);
		printf("%d",m);
	}
	return 0;
}
int exchange(int i,int m)
{
	p+=(N-1-i);
	m=*p;
	return m;
}