import re

inp = []
try:
	while True:
		s = str(input())
		inp.append(s)
except:
	pass

f = ['ab','cd','pq','xy']

n = 0

for s in inp:
	v = re.sub(r'[aeiou]', 'a', s).count('a')
	d = re.search(r"(.)\1", s)
	m = True
	for w in f:
		if w in s:
			m = False
	if (v >= 3 and d != None and m):
		n += 1

print(n)