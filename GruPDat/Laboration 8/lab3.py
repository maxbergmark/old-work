import bintree
import random

tree = bintree.Bintree()


lista = open('word3.txt').read().split()
print()

for element in lista:

    if tree.exist1(element) == True:
        print(element)
    tree.put(element)
