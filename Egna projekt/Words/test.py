import time
import string

inputstr = str(input('Letters: '))

inputlist = inputstr.split(' ')

reuse = False


fil = open('DSSO.txt')



string = fil.read()
lista = string.split('\n')
ordlista = []
for element in lista:
    ordlista.append(element.strip(' '))
print('ord: ', len(ordlista))
 


svar = []
t = time.clock()


inputstr = sorted(inputstr.lower())
print(inputstr)

print()

for element in ordlista:
	temp = inputstr[:]
	check = True
	for letter in element:
		if letter not in temp:
			if '*' not in temp:
				check = False
			else:
				temp.remove('*')
		else:
			#pass
			temp.remove(letter)
	if len(element) > 2:
		if check:
			print(element)