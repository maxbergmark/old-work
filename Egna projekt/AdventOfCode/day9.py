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
	for i in range(len(n)-1):
		d += m[n[i]][n[i+1]]
	return d

inp = []
m = {}
c = []

for l in sys.stdin:
	inp.append(re.split('( = | to )', l))
	m[inp[-1][0]] = {}
	m[inp[-1][2]] = {}
	if (inp[-1][0]) not in c:
		c.append(inp[-1][0])
	if (inp[-1][2]) not in c:
		c.append(inp[-1][2])

for l in inp:
	m[l[0]][l[2]] = int(l[4])
	m[l[2]][l[0]] = int(l[4])

print(len(c))
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
