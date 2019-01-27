import random


def coprime(a, b):
	if (not (a|b)&1):
		return False
	while (b):
		t = a%b
		a = b
		b = t
	return a == 1

c = 0
r = 0
m = 1<<100

while 1:
	a = random.randint(1, m)
	b = random.randint(1, m)
	if coprime(a, b):
		c += 1
	r += 1
	if (c>0 and r%10000 == 0):
		print("\r", r, str((6/(c/r))**.5), end='')