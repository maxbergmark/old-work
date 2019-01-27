
def fib(n):
	a = 0
	b = 1
	for _ in range(abs(n)//2):
		a = a+b
		b = a+b
	if (n % 2 == 0):
		if (n < 0):
			return -a
		return a
	return b

def fibneg(n):
	a = 1
	b = 0
	for _ in range((-n)//2):
		a = b-a
		b = b-a
	if (n % 2 == 0):
		return a
	return b


for i in range(1000000, 1500000):
	print(i, fib(i))

