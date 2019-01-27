import random

n = 100
s = str(n) + '\n'
for i in range(n):
	l = random.randint(1,1000)
	for _ in range(l):
		s += random.choice(['A', 'R'])
	s += '\n'

f = open('output.txt', 'w')
f.write(s)
