import winsound
import pyaudio
import time

morseDictionary = {
    'a':'12'    ,
    'b':'2111'  ,
    'c':'2121'  ,
    'd':'211'   ,
    'e':'1'     ,
    'f':'1121'  ,
    'g':'221'   ,
    'h':'1111'  ,
    'i':'11'    ,
    'j':'1222'  ,
    'k':'212'   ,
    'l':'1211'  ,
    'm':'22'    ,
    'n':'21'    ,
    'o':'222'   ,
    'p':'1221'  ,
    'q':'2212'  ,
    'r':'121'   ,
    's':'111'   ,
    't':'2'     ,
    'u':'112'   ,
    'v':'1112'  ,
    'w':'122'   ,
    'x':'2112'  ,
    'y':'2122'  ,
    'z':'2211'  ,
    '1':'12222' ,
    '2':'11222' ,
    '3':'11122' ,
    '4':'11112' ,
    '5':'11111' ,
    '6':'21111' ,
    '7':'22111' ,
    '8':'22211' ,
    '9':'22221' ,
    '0':'22222'}

def toMorse(text):
    words = text.split()
    morse = ""

    for word in words:

        word = word.lower()
        for index in range(0, len(word)):

            letter = word[index]
            morse += morseDictionary[letter] + '0'

        morse += '00'
    return morse

def translateMorse(morse):
    result = ""
    words = morse.strip('0').split('000')

    reverseDictionary = {m:l for l, m in morseDictionary.items()}

    for word in words:
        letters = word.split('0')
        for morseLetter in letters:
            result += reverseDictionary[morseLetter]
        result += ' '

    return result

def sound(string, vel):

    for digit in range(0, len(string)):
        if int(string[digit]) != 0:
            winsound.Beep(900, int(3000/vel)*int(int(string[digit])))
        else:
            time.sleep(1.6/vel**0.7)
        time.sleep(0.8/vel)

text = str(input("Text: "))
morse = toMorse(text)
vel = int(input("Speed: "))

print(translateMorse(toMorse(text)))
