import bintree
import random

svtree = bintree.Bintree()
entree = bintree.Bintree()

svlista = open('word3.txt').read().split()
enlista = open('englishu.txt').read().split()

for element in svlista:
    svtree.put(element)

for element in enlista:
    element.strip('"\'.,')
    if svtree.exist1(element) and not entree.exist1(element):
        print(element)
    entree.put(element)


