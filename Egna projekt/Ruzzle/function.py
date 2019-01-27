import copy
import time
import winsound

def wordfind(matrix, row, column, string, lista):

   
    if (row in range(4)) and (column in range(4)):
        
        if matrix[row][column].isalpha():
            if len(string) == 5 and (string not in prefix5):
                pass
            else:
                string += matrix[row][column]
                tempm = copy.deepcopy(matrix)
                tempm[row][column] = '0'
                if len(string) >= 6:
                    lista.append(string)

                iters = [[0, -1], [1, -1], [1, 0], [1, 1], [0, 1], [-1, 1], [-1, 0], [-1, -1]]
                for iter1 in iters:
                    rowtemp = row + iter1[0]
                    columntemp = column +iter1[1]
                    wordfind(tempm, rowtemp, columntemp, string, lista)
                
    
    

fil = open('ss100list.txt')
pref5 = open('prefixlist5.txt')
pref6 = open('prefixlist6.txt')
pref7 = open('prefixlist7.txt')
prefix5 = pref5.read()
prefix6 = pref6.read()
prefix7 = pref7.read()
string = fil.read()
ordlista = string.split()
#preflist5 = prefix5.split()
#preflist7 = prefix7.split()
slutlista = []
for element in ordlista:
    if element.isalpha() == True:
        slutlista.append(element.lower())

inputstr = str(input('Bräde: '))
inputlist = inputstr.split('  ')
matrix = []
for x in range(4):
    matrix.append(inputlist[x].split())


#matrix = [['b', 'a', 'c', 'd'],
#          ['s', 'j', 'g', 'h'],
#          ['a', 't', 'k', 'l'],
#          ['m', 'n', 'o', 'p']]

llist = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z', 'å', 'ä', 'ö']
plist = [ 1,   3,   8,   1,   1,   4,   2,   3,   1,   8,   3,   2,   3,   1,   2,   3,   10,  1,   1,   1,   4,   3,   10,  8,   10,  4,   4,   4]


svar = []
t = time.clock()
for row in range(3):
    for column in range(3):

        wordfind(matrix, row, column, '', svar)

svarset = list(set(svar))

print(len(svarset))

svarset5 = []
svarset6 = []
svarset7 = []

#for element in svarset:
#    if len(element) <= 5:
#        svarset5.append(element)
  
for element in svarset:
    if len(element) in range(6, 11):

        if (element[0:6] in prefix6):
        
            svarset5.append(element)

print(len(svarset5))
#for element in svarset5:
#    if len(element) <= 6 or len(element) >= 7 and (element[0:7] in prefix7):
#        svarset7.append(element)

slut = time.clock() - t
print(len(svarset))
print(slut)
wordlist = []

words = 0
totscore = 0
for word in svarset5:
    score = 0
    if word in ordlista:
        print(word)
        
        for letter in range(len(word)):
            score += plist[llist.index(word[letter])]
        if len(word) > 4:
            score += (len(word)-4)*5
            
        wordlist.append((score, word))
        totscore += score
wordlist
wordsort = list(set(wordlist))
wordsort.sort()
for element in wordsort:
    print(element)



print(len(wordsort))
print(int(0.5*totscore))
print(time.clock() - t)
winsound.Beep(500, 500)
