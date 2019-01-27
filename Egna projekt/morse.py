import winsound
import time

def ttom(list2):
    stringret = ""
    
    for word1 in range(0, len(list1)):

        for letter1 in range(0, len(list2[word1])):

            stringret += morse[letter.index(list2[word1][letter1])] + '0'
            

        stringret += '00'
    return stringret

def sound(string, vel):

    for digit in range(0, len(string)):
        if int(string[digit]) != 0:
            winsound.Beep(900, int(3000/vel)*int(int(string[digit])))
        else:
            time.sleep(0.2/vel**0.7)
        time.sleep(0.8/vel)


letter = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0']
morse = ['12', '2111', '2121', '211', '1', '1121', '221', '1111', '11', '1222', '212', '1211', '22', '21', '222', '1221', '2212', '121', '111', '2', '112', '1112', '122', '2112', '2122', '2211', '12222', '11222', '11122', '11112', '11111', '21111', '22111', '22211', '22221', '22222']

text = str(input("Text: "))
list1 = str.split(text)
morsestr = ttom(list1)
vel = int(input("Speed: "))
#while 1:
sound(morsestr, vel)

