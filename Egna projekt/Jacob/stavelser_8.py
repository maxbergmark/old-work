#tillägg för hantering av egennamn, antingen från en inläst namnlista eller en i programkoden skriven namnlista

def stavelsekoll(word):
    index = 0
    antalstavelser = 0
    vokaler = ['a', 'á', 'à', 'e', 'é', 'è', 'i', 'o', 'u', 'y', 'å', 'ä', 'ö']
    while index < len(word):
        if (word[index] in vokaler):
            if word[index:index+2] == 'au':
                index += 1 #för att inte räkna med 'u':et i 'au'
            antalstavelser += 1
        index += 1

    return antalstavelser

def kollamening(mening, namnlista):
    meningsord = mening.strip('. ').lower().split() #splitta upp i en lista med ord
    antalord = 0
    antalsvara = 0
    for word in meningsord:
        if word not in namnlista:
            if stavelsekoll(word) > 2:
                antalsvara += 1
            antalord += 1
    return (antalord, antalsvara)

def startaupp():
    print()
    print(42*'-')
    print('- VÄLKOMMEN TILL ORDSVÅRIGHETSBERÄKNAREN -')
    print(42*'-')
    print()
    filnamn = str(input('  Vad heter din fil? '))

    while True:
        try: #kör tills vi hittar en fil med rätt namn, säger ifrån annars
            ordstring = open(filnamn+'.txt','r').read()
            break
        except:
            filnamn = str(input('  Filen hittades inte, försök igen: '))
    return ordstring

def calculation(ordstring):
    #namnlista = open('namnlista.txt', 'r').read().split('\n')
    namnlista = ['pippi', 'långstrump'] #används som test
    ordstring = ordstring.replace('\n',' ') #tar bort radsluten
    meningslista = ordstring.split('.') #splittar upp i meningar
    data = (0,0) #(antal ord totalt, antal svåra ord)

    for mening in meningslista:
        data = (data[0]+kollamening(mening, namnlista)[0],data[1]+kollamening(mening, namnlista)[1])

    print(data, len(meningslista))
    fog = .4*(data[0]/len(meningslista)+data[1]/data[0])
    return round(fog,1)



ordstring = startaupp()
print('\n  Texten har svårighetsgrad:',calculation(ordstring))


#
#------------------------------------------
#- VÄLKOMMEN TILL ORDSVÅRIGHETSBERÄKNAREN -
#------------------------------------------
#
#  Vad heter din fil? test
#  Filen hittades inte, försök igen: testtext
#
#  Texten har svårighetsgrad: 7.0
