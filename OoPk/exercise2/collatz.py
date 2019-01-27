import time


s = set()
u = 1;
n = 200000;
t0 = time.clock()
for _ in range(n):
	u *= 2
	if u&1:
		u //= 2
	else:
		u = 9*u+1
	#if (u in s):
	#	print('double found', u)
	#s.add(u)

t1 = time.clock()
if (len(s) == n):
	print("No matches found!")
else:
	print(n-len(s), 'matches found!')

print('Total time:', t1-t0)