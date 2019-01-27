import time

class Node:

	def __init__(self, letter = ''):
		self.children = {}

	def add(self, word):
		if word:
			if word[0] not in self.children:
				self.children[word[0]] = Node(word[0])
			self.children[word[0]].add(word[1:])
		else:
			self.children[''] = Node('')

class Tree:

	def __init__(self):
		self.node = Node()

	def add(self, word):
		self.node.add(word)

	def find(self, word, node):
		if word == '':
			return word in node.children
		if word[0] in node.children:
			return self.find(word[1:], node.children[word[0]])
		return False


tree = Tree()
wordlist = open('DSSO.txt').read().split('\n')

t0 = time.clock()

for word in wordlist:
	tree.add(word)
count = 0
t1 = time.clock()
for word in wordlist:
	try:
		if tree.find(word[1:], tree.node) and word[0] != 'o':
			#if len(word) > 12:
				#print(word, count)
			count += 1
	except:
		pass

print(time.clock()-t0)
print(time.clock()-t1)
print(count)