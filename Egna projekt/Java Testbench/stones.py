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

def getTwoChains(s):
	n = len(s)
	if (n == 0):
		return 0
	if (n == 1):
		return 1
	if (n == 3):
		return 2
	c0 = 0
	l0 = 0
	l1 = 0
	lc = s[-1]
	c = 0
	if s[0] == s[-1]:
		while c < n-1 and s[c] == s[-1]:
			c += 1
	if c == n-1:
		return n-1
	s = s[c:] + s[:c]
	count = 0
	for i in range(n+1):
		if s[count%n] == lc:
			c0 += 1
		else:
			# print(c0, l0, l1)
			if c0 > l0:
				if (l1 < l0):
					l1 = l0
				l0 = c0
			elif c0 > l1:
				l1 = c0
			if count > n:
				break

			c0 = 1
			lc = s[count%n]
		count += 1
	# print(l0, l1)
	if l0 == 1:
		if n > 3:
			return 3
		if n == 2:
			return 2
	if l0 == 2 and n >= 4:
		if "ARAA" in s+s[:3] or "AARA" in s+s[:3] or "RRAR" in s+s[:3] or "RARR" in s+s[:3]:
			return 2
		else:
			return 3
	if ((l0)//2 < l1):
		return l1
	return l0//2




for i in range(int(input())):
	s = input()
	r = getTwoChains(s)
	print('Case %s: %d' % (i+1, r))
