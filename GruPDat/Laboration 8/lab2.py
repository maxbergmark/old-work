import bintree
import random

tree = bintree.Bintree()

lista = open('15ordu.txt').read().split()

for element in lista:
    tree.put(element)

print(tree.height())
