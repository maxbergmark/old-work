import sys
import re

def f(cs, m, c):
	s = [0,0,0,0]
	print(cs, m, c)
	for i in range(4):
		for j in range(len(c)):
			print(cs[j], m[c[j]][i])
			s[i]+=cs[j]*m[c[j]][i]

	print(s)

inp = []
m = {}
c = []

for l in sys.stdin:
	inp.append(re.split('(: |, )', l.strip())[::2])
	m[inp[-1][0]] = [int(inp[-1][i].split(' ')[-1]) for i in [1, 2, 3, 4, 5]]
	if (inp[-1][0]) not in c:
		c.append(inp[-1][0])


for c1 in range(101):
	c2 = 100-c1
	f([c1, c2], m, c)

'''
for c1 in range(3):
	for c2 in range(3-c1):
		for c3 in range(3-c1-c2):
			c4 = 3-c1-c2-c3-1
			f([c1, c2, c3, c4], m, c)
'''