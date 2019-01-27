import bintree
import random

tree = bintree.Bintree()

#for x in range(10):
#    tree.put(random.randint(1, 100))

tree = bintree.Bintree()
print(tree.height())     # bör ge 0
print(tree.isempty())    # bör ge True
tree.put('solen')
print(tree.isempty())    # bör ge False
print(tree.height())     # bör ge 1
tree.put('går')
tree.put('sin')
tree.put('höga')
tree.put('ban')
tree.put('uppå')
tree.put('himlarunden')
tree.put('månen')
tree.put('seglar')
tree.put('som')
tree.put('en')
tree.put('svan')
tree.put('uti')
tree.put('midnattsstuden')

print(tree.exist1('visa'))     # bör ge False
print(tree.exist1('ban'))      # bör ge True
tree.printtree()               # skriv ut sorterat
print(tree.height())           # ska ge 6 om insättningarna gjorts exakt enligt ovan
       
    
print()


print()

#print(tree.height())

print()


