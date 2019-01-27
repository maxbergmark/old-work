
def getHash(x, y):
	hash = 27211
	hash = 68597*hash + x
	hash = 92801*hash + y
	return hash

s = set()
c = 0
m = 0

for i in range(1000):
	for j in range(1000):
		h = getHash(i,j)
		if h > m:
			m = h
		if h in s:
			print(h, "collision", c)
			c += 1
		s.add(h)

print(m)