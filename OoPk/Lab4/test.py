import sys
import random


def fac2(a, b):
	val = 1
	for i in range(a, b+1):
		val *= i
	return val

def fac(n):
	val = 1
	for i in range(1, n+1):
		val *= i
	return val

def choose(n, k):
	return fac(n)//(fac(k)*fac(n-k))

def s2(n, k):
	val = 0
	for i in range(k+1):
		val += (-1)**i*choose(k, i)*(k-i)**n
	return val//fac(k)

def incexc(n, k):
	tot = k**n
	val = 0
	for i in range(1, k):
		A = 0
		for j in range(i+1):
			A += choose(i, j)*(k-j)**n*(-1)**j
		val += choose(k, i)*A*(-1)**(i+1)

	prob = (tot*(-1)**(k+1)+val*(-1)**(k))/(tot)
	return prob

def simu(n, k):
	c = 1000
	f = 0
	for _ in range(c):
		test = set()
		for _ in range(n):
			test.add(random.randint(1, k))
		#print(test)
		for i in range(1, k+1):
			if i not in test:
				#print(i, 'not in set')
				f += 1
				break
	return 1-f/c

n = 3300
k = 366

#print(simu(n, k))
#print(incexc(n, k))

num = fac(k)*s2(n, k)
den = k**n
numval = int(str(num)[:20])
numexp = len(str(num))
denval = int(str(den)[:20])
denexp = len(str(den))
print(numval/denval*10**(numexp-denexp))