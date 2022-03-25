#include<stdio.h>
int main()
{
	int a=5,b=7,c=100,d,e,f;
	d=a/b*c;
	e=a*c/b;
	f=c/b*a;
	printf("d=%d,e=%d,f=%d\n",d,e,f);
	return 0;
}