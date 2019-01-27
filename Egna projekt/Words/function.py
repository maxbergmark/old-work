import copy
import time
# import winsound
import string

def hintcheck(lista, string):
    for element in lista:
        if string[int(element[0])-1] != element[1]:
            return False
    return True

def hints(string):
    if string == '':
        return ()
    else:
        for letter in range(len(string)):
            if string[letter].isdigit() and string.count(string[letter]) != 1 or string[letter].isdigit() and int(string[letter]) > letters:
                return False
        templist = string.split()
        tuples = [(element[:-1], element[-1:]) for element in templist]
        for element in tuples:
            if len(element) != 2:
                return False
        return tuples

inputstr = str(input('Letters: '))
if inputstr == '':
    inputstr = string.ascii_lowercase

inputlist = inputstr.split(' ')
letters = str(input('Length: '))
inpreuse = str(input('Use only once? (y/n): '))

if inpreuse != 'n':
    reuse = True
else:
    reuse = False


known = False
while known == False:
    known = str(input('Known letters: '))
    known = hints(known)
    if known == False:
        print('Error')
if letters != '':
    fil = open(letters + 'letters.txt')
    letters = int(letters)
elif letters == '':
    fil = open('wordfreq.txt')



string = fil.read()
lista = string.split('\n')
ordlista = []
for element in lista:
    ordlista.append(element.split())
print('ord: ', len(ordlista))



svar = []
t = time.clock()


inputstr = sorted(inputstr.lower().strip())
inputstr

print()

count = 0
temp = 0
tstop = 0
maxlen = 0
maxword = []

for element in ordlista:
    if element != []:
        temp = 0
        templist = copy.deepcopy(inputstr)
        for letter in range(len(element[0])):
            if element[1][letter] in templist:
                temp += 1
                if reuse:
                    templist[templist.index(element[1][letter])] = '0'
                if temp == len(element[0]) and hintcheck(known, element[0]):
                    print('  ' + element[0])
                    if len(element[0]) > maxlen and letters == '':
                        maxlen = len(element[0])
                        maxword = [element[0]]
                    if len(element[0]) == maxlen and letters == '' and (element[0] not in maxword):
                        maxword.append(element[0])
                    count += 1
                    if count == 50:
                        tstop = time.clock() - t
                        tempstring = str(input('\nKeep going? y/n: '))
                        if tempstring != 'y':
                            print()
                            print('hittade ord: ', count)
                            print('tid: ', round(tstop, 3), 'seconds')
                            # winsound.Beep(500, 500)
                            quit()
                        t = time.clock()
                            
                            
            else:
                break



print()
if letters == '':
    print(maxword)
print('hittade ord: ', count)
print('tid: ', round(tstop + time.clock() - t, 3), 'seconds')
# winsound.Beep(500, 500)
