import numpy

inp = ''
try:
	while True:
		s = str(input())
		inp += s + '\n'
except:
	pass

l0 = inp.strip().split('\n');
l1 = [i.split('x') for i in l0]
l2 = [[int(j) for j in d] for d in l1]

A = 0
L = 0

for d in l2:
	tl = [i*j for i,j in zip(d, [d[2]]+d[:2])]
	a0 = 2*(sum(tl))
	a1 = min(tl)
	p = 2*(sum(d)-max(d))
	b = numpy.prod(d)
	A += a0 + a1
	L += p + b

print(A)
print(L)