def mains(s):
	n = len(s)
	c = 0
	if s[0] == s[-1]:
		while c < n-1 and s[c] == s[-1]:
			c += 1
	s = s[c:] + s[:c]
	curr = s[0]
	c = 0
	m1 = 0
	m2 = 0
	for i in range(n):
		if curr == s[i]:
			c += 1
		else:
			if c > m2:
				m2 = c
				if c >= m1:
					m2 = m1
					m1 = c
			c = 1
			curr = s[i]
 
	if c > m2:
		m2 = c
		if c >= m1:
			m2 = m1
			m1 = c
	if n == 1:
		ans = 1
	elif n == 2:
		if m1 == 2:
			ans = 1
		else:
			ans = 2
	elif n == 3:
		ans = 2
	elif m1 == n:
	    ans=n-1
	elif m1 == 1:
		ans = 3
	elif m1 == 2:
		if "ARAA" in s+s[:4] or "AARA" in s+s[:4] or "RRAR" in s+s[:4] or "RARR" in s+s[:4]:
			ans = 2
		else:
			ans=3
	else:
		ans = max(m1/2, m2)
 
	return ans
 
for t in range(int(input())):
    s = input()
    print("Case %d: %d" % (t+1, mains(s)))
 