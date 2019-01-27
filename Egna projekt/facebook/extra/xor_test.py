import copy
import random



l1 = [i for i in range(10000000) if random.random() > 0.5]
l2 = copy.copy(l1)

l2.append(123123)

s = 0
s2 = 0

for l in l1:
	s ^= l


for l in l2:
	s2 ^= l

print(s)
print(s2)
for i in range(max(l2)):
	if (s ^ i == s2):
		print(i)