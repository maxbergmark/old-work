import bintree
import random
import string

tree = bintree.Bintree()


while True:
    hej = str(input('Input: '))
    if hej.isdigit():
        hej = int(hej)
        tree.put(hej)
    elif hej == 'max':
        print(tree.maximum())
        temp = tree.top
        while temp.right != None:
            temp = temp.right
        print(temp.value)
    elif hej == 'min':
        print(tree.minimum())
        temp = tree.top
        while temp.left != None:
            temp = temp.left
        print(temp.value)
    elif hej == 'print':
        tree.printtree()
    elif hej == 'del':
        n = int(input('Tal: '))
        tree.remove(n)
    elif hej == 'h':
        print(tree.height())

