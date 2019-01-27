

def bubble(n):
	h = {}
	check = 0
	while check < len(n)-1:

		check = 0
		for i in range(len(n)-1):
			if n[i]+n[i+1] in h:
				sort = h[n[i]+n[i+1]]
			elif n[i+1]+n[i] in h:
				sort = 3-h[n[i+1]+n[i]]
			else:
				sort = int(input(n[i] + '(1) or ' + n[i+1] + '(2): '))
				h[n[i]+n[i+1]] = sort
			if sort == 1:
				n[i:i+2] = [n[i+1], n[i]]
			else:
				check += 1

	return n


n = ['Snakso', 'Khrubby', 'Venerated', 'Cooleja', 'Escá', 'Flóóz', 'Havocwreaker', 'Kimahri', 
	'Klistermärke', 'Ligger', 'Paradarswe', 'Saweetroll', 'Terith', 'Vudak', 'Wepolo', 
	'Xaap', 'Zinqier', 'Zoranda']

print(bubble(n))