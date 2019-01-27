import sys
import re


def perm(string, first = []):
    lista = []
    if len(string) == 1:
        if first == []:
            return [string]
        return [first+string, string+first]

    temp = perm(string[1:], [string[0]])

    if first != []:
        for element in temp:
            for i in range(len(element)+1):
                lista.append(element[:i]+first+element[i:])
    else:
        lista = temp
    return lista

def dist(n, m):
	d = 0
	for i in range(-1, len(n)-1):
		d += m[n[i]][n[i+1]]
		d += m[n[i+1]][n[i]]
	return d

inp = []
m = {}
c = []

for l in sys.stdin:
	inp.append(l.strip('.\n').split(' '))
	m[inp[-1][0]] = {}
	m[inp[-1][-1]] = {}
	if (inp[-1][0]) not in c:
		c.append(inp[-1][0])
	if (inp[-1][-1]) not in c:
		c.append(inp[-1][-1])

for l in inp:
	f = 1 if l[2] == 'gain' else -1
	m[l[0]][l[-1]] = f*int(l[3])

print(c)
ps = perm(c[:])
ml = 0
mp = []
for p in ps:
	d = dist(p, m)
	if d > ml:
		ml = d
		mp = p
print(mp)
print(ml)
