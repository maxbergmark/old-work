
def lettercheck(word):
    vokal = False
    konsonant = False
    vokaler = ['a', 'e', 'i', 'o', 'u']
    konsonanter = ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z']
    if len(word) == 1:
        if word in vokaler:
            return True
    for letter in range(len(word)):
        if word[letter] in vokaler:
            vokal = True
        if word[letter] in konsonanter:
            konsonant = True
    return vokal and konsonant
    
fil = open('all.num.o5')
totallist = open('wordfreq.txt', 'w')

string = fil.read()
ordlista = string.split('\n')[1:]


templista = []

for element in ordlista:
    if element != '':
        templista.append(element.split()[1])



slutlista = []

print(len(templista))
print()
written = []
count = 0
for element in templista:
    if element.isalpha() == True and lettercheck(element) and (element not in written):
        totallist.write((element.lower() + ' ' + ''.join(sorted(element.lower())) + '\n'))
        written.append(element.lower())
        count += 1
        if count % 1000 == 0:
            print(count)

print(count)

for letters in range(1, 26):
    written = []
    count = 0
    temp = open(str(letters)+'letters.txt', 'w')
    for element in templista:
         if element.isalpha() == True and len(element) == letters and lettercheck(element) and (element not in written):
            temp.write((element.lower() + ' ' + ''.join(sorted(element.lower())) + '\n'))
            written.append(element.lower())
            count += 1

    print(letters, count)
