import time
import winsound
import random
import string
import itertools

letters = sorted(string.ascii_lowercase)
letters.extend(['å', 'ä', 'ö'])


def postcheck(original, guesses):
    test = str(input('Vilket var ditt ord? '))
    if len(test) > 5:
        print('Ditt ord är för långt.')
    elif len(test) < 5:
        print('Ditt ord är för kort.')
    elif not answercheck(test):
        print('Ditt ord uppfyller inte alla kriterier.')
    elif test not in original:
        print('Ditt ord finns inte.')
    else:
        for element in guesses:
            if pointcheck(element[0], test) != element[1]:
                print(element[0], 'borde ha varit', pointcheck(element[0], test), 'poäng. Du angav:', element[1])

def answercheck(string):
    count = 0
    for letter in list(string):
        if not letter.isalpha():
            return False
        if list(string).count(letter) > 1:
            return False
    return True

def pointcheck(string, word):
    correct = 0
    inword = 0
    check = []
    for letter in range(5):
        if string[letter] == word[letter]:
            correct += 1
        if string[letter] not in check:
            check.append(string[letter])
            
            if string[letter] in word:
                inword += 1

    return str(inword) + str(correct)

def wordcheck(string, points, lista, guesses = None):
    
    if points == '?':
        print(lista)
        return lista
    elif points == '#':
        print('  Jag har', len(lista), 'ord att välja mellan.')
        return lista
    elif points.lower() == 'rätt':
        print()
        if guesses:
            attempts = len(guesses)+1
        else:
            attempts = 1
        print('  Jag vann! Det tog mig bara', len(guesses)+1, 'försök.')
        quit()
    elif points.isdigit():
        returnlista = []
        inword = int(points) // 10
        correct = int(points) % 10
        if inword < correct:
            print('  Nu gjorde du fel!')
            return lista

        tester = sorted(string)
        
        
        for element in lista:
            
            incount = 0
            corrcount = 0
            temp = sorted(element)
            for letter in range(5):
                if temp[letter] in tester:
                    incount += 1
                if element[letter] == string[letter]:
                    corrcount += 1

            if incount == inword and corrcount == correct:
                returnlista.append(element)

        return returnlista
    else:
        print('  Jag kan inte tolka ditt svar.')
        return lista

def iguess(lista, original):
    
    print()
    print('Det gäller för mig att gissa det ord som du tänker på. Ordet')
    print('skall innehålla fem bokstäver som alla är olika. Jag känner inte')
    print('till ord med plural- eller tempusändelser och inte heller namn,')
    print('alltså inte BRÄNT eller LÅDOR. Du svarar med en tvåsiffrig poäng')
    print('XY där X är antalet bokstäver som över huvud taget är rätt, och')
    print('Y är det antal bokstäver som dessutom står rätt placerade. Om du')
    print('tänkte på SPARV och jag föreslår SKOLA blir alltså min poäng 21,')
    print('nämligen 20 för S och A och 1 för rätt placerat S. Om du skriver')
    print('? får du en lista över ord som jag för tillfället tror det kan')
    print('vara som du tänker på, skriver du # anger jag endast hur många')
    print('de är.')
    print()
    guesses = []
    points = '00'
    while len(lista) > 1:
        
        if points.isdigit():
            if len(points) == 2:
                if points[0] >= points[1]:
                    word = lista[random.randint(0, len(lista)-1)]
        
        points = str(input('  ' + word.capitalize() + '? Poäng: '))
        if points.isdigit() and len(points) == 2:
            guesses.append((word, points))
        
        lista = wordcheck(word, points, lista, guesses)
    print()
    if len(lista) == 1:
        answer = False
        check = str(input('Är ditt ord ' + lista[0] + '? (j/n): '))
        while not answer:
            
            if check == 'j':
                print('Jag vann! Det tog mig bara', len(guesses), 'försök.')
                playanswer = False
                while not playanswer:
                    playagain = str(input('Vill du spela igen? (j/n): '))
                    if playagain == 'j':
                        startgame()
                        playanswer = True
                    elif playagain == 'n':
                        print('Hejdå!')
                        playanswer = True
                    else:
                        playagain = str(input('Skriv något vettigt. Vill du spela igen? (j/n): '))
                answer = True
            elif check == 'n':
                postcheck(original, guesses)
                answer = True
            else:
                print()
                check = str(input('Svara på riktigt nu. Är ditt ord ' + lista[0] + '? (j/n): '))
    elif lista == []:
        postcheck(original, guesses)

def youguess(lista):
    word = lista[random.randint(0, len(lista)-1)]
    
    #print(word)
    guess = ''
    count = 0
    while guess != word:
        guess = str(input('Gissa nu: '))
        if guess.isalpha() and len(guess) == 5:
            if pointcheck(guess, word) != '55':
                print(pointcheck(guess, word))
            count += 1
        elif not guess.isalpha() and len(guess) == 5:
            print('Använd bokstäver.')
        elif guess.isalpha() and len(guess) != 5:
            print('Ordet ska vara fem bokstäver.')
        else:
            print('Nu blev allt fel. Gör om, gör rätt.')
    print()
    print('Du gissade rätt! Det tog "bara"', count, 'försök.')
    playanswer = False
    while not playanswer:
        playagain = str(input('Vill du spela igen? (j/n): '))
        if playagain == 'j':
            startgame()
            playanswer = True
        elif playagain == 'n':
            print('Hejdå!')
            playanswer = True
        else:
            playagain = str(input('Skriv något vettigt. Vill du spela igen? (j/n): '))

    

print()

def startgame():
    print()
    lista = open('ordlista.txt').read().split()
    player = int(input('Välkommen till den magiska ordgissaren!\nVem är det som ska gissa ordet?\n\n  1 Datorn\n  2 Spelaren\n  '))
    answer = False
    while not answer:
        if player == 1:
            iguess(lista, lista[:])
            answer = True
        elif player == 2:
            youguess(lista)
            answer = True
        else:
            player = int(input('Nu gjorde du helt fel. Försök igen.\nVem är det som ska gissa ordet?\n\n  1 Datorn\n  2 Spelaren\n  '))
        
#lista = lista1[:]
startgame()
