

inp = []
try:
	while True:
		s = str(input())
		inp.append(s)
except:
	pass

for i in inp:
	print(i, i.encode())