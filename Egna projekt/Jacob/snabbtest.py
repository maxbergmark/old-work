#Jacob Werner
#930511-3632
#2014-05-05

import re

class Word():

    def __init__(self, word, nameList = []):
        self.word = word.strip(' ,-:;') #tar bort alla skiljetecken
        self.syllables = self.calcSyllables() #beräknar antalet stavelser
        self.nameList = nameList
        self.isName = self.checkIfName() #kontrollera om ordet är ett egennamn
          
    def calcSyllables(self): #räknar ut antalet stavelser
        #index = 0 #vilken bokstav som undersöks
        #syllableCount = 0 #här sparas antalet stavelser
        vowels = ['a', 'á', 'à', 'e', 'é', 'è', 'i', 'o', 'u', 'y', 'å', 'ä', 'ö'] #en lista med alla vokaler
        #while index < len(self.word):
        #    if (self.word[index] in vowels): #kontrollera om bokstaven finns i vokallistan
        #        if self.word[index:index+2] == 'au': #kontrollera specialfallet 'au'
        #            index += 1 #för att inte räkna med 'u':et i 'au'
        #        syllableCount += 1 #addera en stavelse
        #    index += 1 #kolla nästa bokstav
        #return syllableCount
        return sum([1 for letter in self.word if letter in vowels]) - self.word.count('au')

    def checkIfName(self): #kontrollera om ordet är ett egennamn
        if self.word in self.nameList: #kolla om ordet finns i namnlistan
            return True
        return False

class Sentence():

    def __init__(self, string, nameList):
        self.string = string.strip('.?! ') #ta bort skiljetecken och mellanslag från början och slutet på meningen
        self.wordList = self.string.lower().split() #gör om till lowercase och splitta upp i enskilda ord
        self.nameList = nameList
        self.calcSentence()
    
    def calcSentence(self):
        self.wordClassList = [Word(word, self.nameList) for word in self.wordList] #lista med Word-objekt för orden i meningen
        self.length = sum([1 for word in self.wordClassList if word.isName == False]) #antalet ord i meningen, namn ej räknade
        self.hardWords = sum([1 for word in self.wordClassList if word.syllables > 2 and word.isName == False]) #antalet svåra ord, som inte är namn
        
def startUp(): #skriv ut en introduktion till programmet
    print()
    print()
    print(42*'-')
    print('- VÄLKOMMEN TILL LÄSBARHETSINDEX -')
    print(42*'-')
    print()
    print('  Det här programmet analyserar en textfil, och anger \n  vilket FOG-värde texten har.\n')
    fileName = str(input('  Vad heter din fil? ')) #fråga om filnamnet

    while True: #upprepar tills en körbar fil har hittats
        try: #kör tills vi hittar en fil med rätt namn, säger ifrån annars
            wordString = open(fileName+'.txt','r').read().strip('\n .') #en sträng med hela texten
            break
        except:
            fileName = str(input('  Filen hittades inte, försök igen (skriv "avbryt" för att avsluta): '))
            if fileName == 'avbryt':
                print('\n  Ha en bra dag!')
                quit() #avslutar programmet
    return wordString

def calculation(wordString):
    nameList = open('namnlista.txt').read().lower().split('\n') #lista med egennamn
    wordString = wordString.replace('\n',' ').lower() #tar bort radsluten
    sentenceList = re.split(r"[?!.]", wordString) #splittar på alla meningsskiljare
    sentenceClassList = [Sentence(sentence, nameList) for sentence in sentenceList] #skapar Sentence-objekt
    totalWords = sum([sentence.length for sentence in sentenceClassList]) #summerar meningslängderna
    hardWords = sum([sentence.hardWords for sentence in sentenceClassList]) #summerar antalet svåra ord
    FOG = .4*(totalWords/len(sentenceList)+hardWords/totalWords) #FOG-indexet
    return round(FOG,1)

wordString = startUp()
print('\n  Texten har svårighetsgrad:', calculation(wordString))
