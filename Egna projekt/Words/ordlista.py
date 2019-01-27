
def lettercheck(word):
    vokal = False
    konsonant = False
    vokaler = ['a', 'e', 'i', 'o', 'u']
    konsonanter = ['b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z']
    for letter in range(len(word)):
        if word[letter] in vokaler:
            vokal = True
        if word[letter] in konsonanter:
            konsonant = True
    return vokal and konsonant
    
fil = open('wordlist.txt')


string = fil.read()
ordlista = string.split()

slutlista = []
for letters in range(2, 13):
    print(letters)
    temp = open(str(letters)+'letters.txt', 'w')
    for element in ordlista:
        if element.isalpha() == True and len(element) == letters and lettercheck(element):
            temp.write((element.lower() + ' ' + ''.join(sorted(element.lower())) + '\n'))
