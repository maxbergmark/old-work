import random

def FL(seq, k):
    curr = 1
    for i in range(1, k):
        curr *= (0 < seq[i]-seq[i-1] < 1)
        curr += 1
    return curr

def FL2(s, k,c=[1]):
	[c.append(c[i-1]*(0<s[i]-s[i-1]<1)+1)for i in range(len(c),k)]
	return c[k-1]

def FL3(s,k,c=1):
	for i in range(k-1):c*=(0<s[i+1]-s[i]<1)+1/c
	return int(c)

def test(c=[1]):
	[c.append(i)for i in range(100)]
	return c[-1]

def test2():
	c=[1]
	[c.append(i)for i in range(100)]
	return c[-1]

seq = [random.random() for i in range(1000)]

for k in range(1000):
	f1 = FL(seq, k)
	f2 = FL2(seq, k)
	f3 = FL3(seq, k)
	print(f1, f2, f3)
	if f1 != f2 != f3:
		print("error")
