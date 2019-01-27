import time
import winsound
import random
import string
import itertools

letters = sorted(string.ascii_lowercase)
letters.extend(['å', 'ä', 'ö'])
lista = open('ordlista.txt').read().split()

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
    for letter in sorted(string):
        if not letter.isalpha():
            return False
        if sorted(string).count(letter) > 1:
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

def wordcheck(string, points, lista):
    if points == '?':
        print(lista)
        return lista
    elif points == '#':
        print('  Jag har', len(lista), 'ord att välja mellan.')
        return lista
    elif points.lower() == 'rätt':
        print()
        print('Jag vann!')
        quit()
    elif points.isdigit():
        returnlista = []
        inword = int(points) // 10
        correct = int(points) % 10
        if inword < correct:
            print('Nu gjorde du fel!')
            return

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
            word = lista[random.randint(0, len(lista)-1)]
        
        points = str(input('  ' + word.capitalize() + '? Poäng: '))
        if points.isdigit() and len(points) == 2:
            guesses.append((word, points))
        
        lista = wordcheck(word, points, lista)
    print()
    if len(lista) == 1:
        check = str(input('Är ditt ord ' + lista[0] + '? (j/n): '))
        if check == 'j':
            print('Jag vann!')
        elif check == 'n':
            postcheck(original, guesses)
        else:
            pass
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
    

print()



player = int(input('Välkommen till den magiska ordgissaren!\nVem är det som ska gissa ordet?\n\n  1 Datorn\n  2 Spelaren\n  '))

if player == 1:
    iguess(lista, lista)
elif player == 2:
    youguess(lista)
