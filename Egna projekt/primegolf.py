l=1<<21;r=range
a=[1 for i in r(l)]
for i in r(2,l):
	for j in r(2,l//i):a[i*j]=0
p=[i for i in r(2,l)if a[i]]