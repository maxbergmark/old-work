

def remake(ip):
	num = [int(i) for i in ip.split(".")]
	s = 0
	for i in range(4):
		s *= 256
		s += num[i]
	return s


print(remake(str(input())))	