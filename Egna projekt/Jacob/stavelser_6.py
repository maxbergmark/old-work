#tillägg av calculation()-funktionen, för att ytterligare separera programmets funktioner

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

def startaupp():
    print()
    print(42*'-')
    print('- VÄLKOMMEN TILL ORDSVÅRIGHETSBERÄKNAREN -')
    print(42*'-')
    filnamn = str(input('Vad heter din fil? '))

    while True:
        try:
            ordstring = open(filnamn+'.txt','r').read()
            break
        except:
            filnamn = str(input('Filen hittades inte, försök igen: '))
    return ordstring




def calculation(ordstring):

    #teststrängen nedan kan användas om man inte har någon fil.    
    #ordstring = 'Här kommer Pippi Långstrump, tjolahopp tjolahej tjolahoppsan-sa, här kommer Pippi Långstrump, ja, här kommer faktiskt jag.'
    meningslista = ordstring.split('.')
    data = (0,0) #(antal ord totalt, antal svåra ord)

    for mening in meningslista:
        data = (data[0]+kollamening(mening)[0],data[1]+kollamening(mening)[1])

    #print(data)
    fog = .4*(data[0]/len(meningslista)+data[1]/data[0])
    return round(fog,1)



ordstring = startaupp()
print(calculation(ordstring))


#
#------------------------------------------
#- VÄLKOMMEN TILL ORDSVÅRIGHETSBERÄKNAREN -
#------------------------------------------
#Vad heter din fil? test
#Filen hittades inte, försök igen: testtext
#7.3                                       
