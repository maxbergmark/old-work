import math

global lmax;

def c2(X,n):
	P = [0 for _ in range(n)]
	M = [0 for _ in range(n+1)]


	L = 0
	for i in range(0, n):
		lo = 1
		hi = L
		while lo <= hi:
			mid = math.ceil((lo+hi)/2)
			if X[M[mid]] < X[i]:
				lo = mid+1
			else:
				hi = mid-1

		newL = lo

		P[i] = M[newL-1]
		M[newL] = i

		if newL > L:
			L = newL

	S = [0 for _ in range(L)]
	k = M[L]
	print(L)
	for i in range(L-1, 0):
		S[i] = X[k]
		k = P[k]

	return S


def check(S,c,m=0,l=0):
	global lmax
	if(l>lmax): 
		print(l,S,c)
		lmax=l

	for i in range(m,len(S)):
		if m==0 or S[m-1]<S[i]:
			c[i]=1
			check(S,c,i+1,l+1)
			c[i]=0

#S = [3,0,5,2,3,3,7,1,8,0]
lmax = 5;
S = [i for i in range(30)]

chosen = [0 for _ in S]

#check(S,chosen)

n = 10
X = [i for i in range(n)]
X = [3,0,5,2,3,3,7,1,8,0]
c2(X,len(X))