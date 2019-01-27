import random
import time


def QuickSelect(A, k):
	if (len(A) < k):
		return A[0]
	pivot = random.choice(A)
	A0 = []
	A1 = []
	A.remove(pivot)
	for i in A:
		if (i < pivot):
			A0.append(i)
		else:
			A1.append(i)
	if (len(A0) == k):
		return pivot
	elif (len(A0) > k):
		return QuickSelect(A0, k)
	else:
		return QuickSelect(A1, k-len(A0)-1)



n = 100000
A = [i for i in range(n)]
random.shuffle(A)
t0 = time.clock()
for _ in range(100):
	k = random.choice(A)
	QuickSelect(A[:], k)
t1 = time.clock()
print(t1-t0)