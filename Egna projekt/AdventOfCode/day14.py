import sys

inp = []
m = {}
c = []

def fly(r, t):
	t0 = 0
	d = []
	s = 0
	while t0 < t:
		#if t-t0 < r[1]:
		#	s += (t-t0)*r[0]
		#	break
		d.extend([s+r[0]*(i+1) for i in range(r[1])])
		s += r[0]*r[1]
		t0 += r[1]+r[2]
		d.extend([s for i in range(r[2])])
	return d[:t+1]

for l in sys.stdin:
	inp.append(l.strip('.\n').split(' '))
	print(inp[-1])
	m[inp[-1][0]] = [int(inp[-1][i]) for i in [3, 6, -2]]
	if (inp[-1][0]) not in c:
		c.append(inp[-1][0])




k = []
p = [0 for _ in range(len(c))]

for r in c:
	k.append(fly(m[r], 2503))
for t in range(1, 2503):
	ml = 0
	mp = []
	for r in range(len(c)):
		s = k[r][t]
		if s > ml:
			ml = s
	for r in range(len(c)):
		if k[r][t] == ml:
			p[r] += 1
print(mp)
print(ml)
print(p)
