

def operate(M,r1,r2,c):
	for y in range(r1[0],r2[0]+1):
		for x in range(r1[1],r2[1]+1):
				M[y][x] = max(M[y][x]+c,0)


inp = []
try:
	while True:
		s = str(input())
		inp.append(s)
except:
	pass

com = ['turn on ', 'toggle ']

M = [[0 for _ in range(1000)] for _ in range(1000)]

for l in inp:
	tC = -1
	for c in range(2):
		if com[c] in l:
			tC = c+1
	s = l.split(' ')
	vals = [tuple([int(j) for j in s[i].split(',')]) for i in [-3,-1]]
	operate(M,vals[0],vals[1],tC)

print(sum([sum(i) for i in M]))