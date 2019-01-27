import math
import time
import copy

def decrypt(lista, key):
   result = []
   for element in range(len(lista)):
      result.append(chr(lista[element] ^ key[element % 3]))
      #print(key[element % 3])
   string = ''.join(result)
   return string



fil = open('cipher1.txt')
string = fil.read()
lista = string.split(',')

for element in range(len(lista)):
   lista[element] = int(lista[element])

summa = 0
for letter1 in range(ord('a'), ord('z')+1):

   for letter2 in range(ord('a'), ord('z')+1):

      for letter3 in range(ord('a'), ord('z')+1):
        
         string = decrypt(lista, [letter1, letter2, letter3])
         if string.find(' the ') > 0:
            print(string)
            for letter in range(len(string)):
               summa += ord(string[letter])

print(summa)
