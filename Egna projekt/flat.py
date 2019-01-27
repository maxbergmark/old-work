def FL(seq, k):
	curr = 1
	for i in range(1, k):
		curr *= (0 < seq[i]-seq[i-1] < 1)
		curr += 1
	return curr

def flat(seq, k, c = [1]):
	[c.append(c[i-1]*(0 < seq[i]-seq[i-1] < 1)+1)for i in range(len(c), k)]
#	for i in range(len(c), k):
#		c.append(c[i-1]*(0 < seq[i]-seq[i-1] < 1)+1)
	return c[k-1]

import random
seq = [random.random() for _ in range(1000)]
for l in range(1000):
	print(FL(seq,l) == flat(seq, l))