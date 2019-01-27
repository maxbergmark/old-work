s = str(input())

m = {'v': (0,1), '^': (0,-1), '<': (-1,0), '>': (1,0)}

h = set()

p = [(0,0),(0,0)]

h.add(p[0])

count = 1
x = 0

for c in s:
	p[x%2] = tuple([i+j for i,j in zip(p[x%2],m[c])])
	if p[x%2] not in h:
		count += 1
	h.add(p[x%2])
	x += 1

print(count)