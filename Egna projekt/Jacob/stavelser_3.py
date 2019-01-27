#tillägg av funktion som kontrollerar en mening, samt användning av teststräng

def stavelsekoll(word):
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

def kollamening(mening):
    meningsord = mening.strip('. ').lower().split()
    antalsvara = 0
    for word in meningsord:
        if stavelsekoll(word) > 2:
            antalsvara += 1
    return (len(meningsord), antalsvara)

print()
print(42*'-')
print('- VÄLKOMMEN TILL ORDSVÅRIGHETSBERÄKNAREN -')
print(42*'-')
#filnamn = str(input('Vad heter din fil? '))

#teststrängen nedan kan användas om man inte har någon fil.    
ordstring = 'Här kommer Pippi Långstrump, tjolahopp tjolahej tjolahoppsan-sa, här kommer Pippi Långstrump, ja, här kommer faktiskt jag.'
meningslista = ordstring.split('. ')
data = (0,0) 

for mening in meningslista:
    data = (data[0]+kollamening(mening)[0],data[1]+kollamening(mening)[1])

print(data)

#utskrift: (16, 3)
