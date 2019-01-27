

def perm(n):
	i = 0
	sa = []
	r = ''
	while i < len(n):
		c = 1
		if n[i] not in sa:
			sa.append(n[i])
		while i+1 < len(n) and n[i] == n[i+1]:
			c += 1
			i += 1
		r += str(c)+n[i]
		i += 1
	print(sorted(sa))
	return r


inp = '0987654'
for i in range(5):
	print(inp)
	inp = perm(inp)
print(len(inp))