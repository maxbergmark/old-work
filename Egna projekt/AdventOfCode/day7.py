import re


inp = []
try:
	while True:
		s = str(input())
		inp.append(s)
except:
	pass

h = {}
c = set()

for l in inp:
	s = l.split(' -> ')
	h[s[1]] = s[0]
	if s[0].isdigit():
		c.add(s[1])

m = ['NOT ', ' AND ', ' OR ', ' RSHIFT ', ' LSHIFT ']
while len(c) < len(h):
	for k in h.keys():
		if (k not in c):
			tC = -1
			for d in range(len(m)):
				if m[d] in h[k]:
					tC = d
			s = h[k].split(m[tC])
			if (0 < tC < 3):
				if ((s[0] in c or s[0].isdigit()) and (s[1] in c or s[1].isdigit())):
					c.add(k)
					v1 = int(s[0]) if s[0].isdigit() else int(h[s[0]])
					v2 = int(s[1]) if s[1].isdigit() else int(h[s[1]])
					if tC == 1:
						h[k] = v1&v2%65536
					if tC == 2:
						h[k] = v1|v2%65536

					

			if (tC > 2):
				if (s[0] in c):
					c.add(k)
					if tC == 3:
						h[k] = int(h[s[0]])>>int(s[1])%65536
					if tC == 4:
						h[k] = int(h[s[0]])<<int(s[1])%65536


			if (tC == 0):
				if (s[1] in c):
					c.add(k)
					h[k] = ~int(h[s[1]])%65536

			if ('lx' in c):
				h['a'] = h['lx']
				c.add('a')

	print(len(h), len(c))

print(h['a'])
