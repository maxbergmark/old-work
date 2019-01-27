import re

def pair(s):
	for i in range(len(s)-3):
		for j in range(i+2,len(s)-1):
			if s[i:i+2] == s[j:j+2]:
				return True
	return False

def pal(s):
	for i in range(len(s)-2):
		if s[i:i+3] == s[i+2:i-1:-1]:
			return True
	return False

inp = []
try:
	while True:
		s = str(input())
		inp.append(s)
except:
	pass
n = 0
for s in inp:
	if (pair(s) and pal(s)):
		n += 1
print(n)