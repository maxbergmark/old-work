import time
import string
from operator import mul
from functools import reduce


def isPrime(p):
	for i in range(2, int(p**.5)+1):
		if p % i == 0:
			return False
	return True

l = list(string.ascii_lowercase+'åäö')
d = {}
for i in range(len(l)):
	d[l[i]] = i

p = [i for i in range(2, 200) if isPrime(i)][:len(l)]
print(l)
print(p)
w = open('ordlista.txt').read().split('\n')
inp = str(input('Letters: '))
prod = reduce(mul, [p[d[l]] or 0 for l in inp], 1)
print(prod)

t0 = time.clock()

for word in w:
	w_prod = prod
	c = True
	for letter in word:
		p_l = (p[d[letter]] if letter in d else .1231)
		if w_prod % p_l == 0:
			w_prod //= p_l
		else:
			c = False
			break
	# if c:
		# print(word)


t1 = time.clock()
print(t1-t0)