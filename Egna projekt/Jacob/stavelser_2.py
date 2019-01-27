#tillägg av funktion som räknar stavelser för ett ord

def stavelsekoll(word):
    word = word.lower()
    index = 0
    antalstavelser = 0
    vokaler = ['a', 'á', 'à', 'e', 'é', 'è', 'i', 'o', 'u', 'y', 'å', 'ä', 'ö']
    while index < len(word):
        if (word[index] in vokaler):
            if word[index:index+2] == 'au':
                index += 1
            antalstavelser += 1
        index += 1

    return antalstavelser


print()
print(42*'-')
print('- VÄLKOMMEN TILL ORDSVÅRIGHETSBERÄKNAREN -')
print(42*'-')

print('Aula', stavelsekoll('Aula'))
print('kobbe', stavelsekoll('kobbe'))
print('skälmskt', stavelsekoll('skälmskt'))
print('å', stavelsekoll('å'))
print('velociraptor', stavelsekoll('velociraptor'))


#Aula 2
#kobbe 2
#skälmskt 1
#å 1
#velociraptor 5
