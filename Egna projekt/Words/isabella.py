
with open('ordlista.txt', 'r') as f:
	word_list = f.read().split('\n')
	for word in word_list:
		if (word[-4:] == 'issa'):
			print(word)
		if (word[-3:] == 'iss'):
			print(' ',word)
