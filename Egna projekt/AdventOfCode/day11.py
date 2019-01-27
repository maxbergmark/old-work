
print([chr(i) for i in range(97,97+26)])

def pair(s):
	c = 0
	i = 0
	while i < len(s)-1:
		if s[i] == s[i+1]:
			c += 1
			i += 1
		i += 1
	return c > 1

def next(n):
	i = -1
	n = list(n)
	n[-1] = chr(ord(n[-1])+1)
	while (n[i] == '{'):
		c = False
		n[i] = 'a'
		n[i-1] = chr(ord(n[i-1])+1)
		i -= 1
	return ''.join(n)


s = 'vzbxxyzz'

i1 = [''.join([chr(j+i) for i in range(3)]) for j in range(97,97+26-2)]
i2 = ['i', 'o', 'l']
print(i1)

while True:
	s = next(s)
	c1 = False
	c2 = True
	c3 = pair(s)
	for l in i1:
		if l in s:
			c1 = True
	for l in i2:
		if l in s:
			c2 = False

	if c1 and c2 and c3:
		print(s)
		break
