import random
import string

class Evolve:

	def __init__(self, password, mutaterate, num):
		self.password = password
		self.num = num
		self.gen = 0
		self.charmap = string.ascii_lowercase + ' ' + 'åäö'
		self.copies = [[random.choice(self.charmap) for _ in range(len(password))] for _ in range(self.num)]
		self.mutaterate = mutaterate
		

	
	def fitness(self, test):
#		return sum((ord(i)-ord(j))**2 for i, j in zip(self.password, test))
		return sum(i==j for i, j in zip(self.password, test))

	def mutate(self, test, mutaterate):
		return [ch if random.random() >= mutaterate else random.choice(self.charmap) for ch in test]

	def breed(self, parent1, parent2):
		return [parent1[i] if random.random() < .5 else parent2[i] for i in range(len(parent1))]

	def generation(self):
		self.copies = sorted(self.copies, key = self.fitness, reverse = True)
		self.copies = [self.breed(self.copies[int(self.num*random.random()**2)], self.copies[int(self.num*random.random()**2)]) for _ in range(self.num)]
		self.copies = [self.mutate(child, self.mutaterate) for child in self.copies]
		self.gen += 1
#		self.copies = [self.mutate(self.parent, self.mutaterate) for _ in range(1000)] + [self.parent]
#		self.parent = max(self.copies, key = self.fitness)

	def evolution(self):
		while ''.join(self.copies[0]) != self.password:
			self.generation()
			print(''.join(self.copies[0]))
			if self.gen*self.num > 100000:
				break

class Evolveparam:

	def __init__(self, password):
		self.c = 20
		self.mutaterate = .05
		self.password = password
		self.copies = [Evolve(self.password, .001+.1*random.random(), 20 + int(1000*random.random())) for _ in range(self.c)]
		temp = 0
		for copy in self.copies:
			temp += 1
			print('setting up copy', temp)
			copy.evolution()

	def breedmutate(self, parent1, parent2):
		return parent1.mutaterate if random.random() < .5 else parent2.mutaterate

	def breednum(self, parent1, parent2):
		return parent1.num if random.random() < .5 else parent2.num

	def generation(self):
		print('   new evolve generation')
		#self.copies = sorted(self.copies, key = self.getGen)
		temp = []
		for _ in range(self.c):
			parent1 = self.mutate(self.copies[int(self.c*random.random()**2)], self.mutaterate)
			parent2 = self.mutate(self.copies[int(self.c*random.random()**2)], self.mutaterate)
			temp.append(Evolve(self.password, self.breedmutate(parent1, parent2), self.breednum(parent1, parent2)))

		self.copies = temp[:]
#		self.copies = sorted(self.copies, key = self.getGen)
		for copy in self.copies:
			copy.evolution()
			#print(round(copy.mutaterate, 5), copy.num, copy.gen)

	def mutate(self, child, mutaterate):
		if random.random() < self.mutaterate:
			child.mutaterate = .0001+.1*random.random()
		if random.random() < self.mutaterate:
			child.num = 20 + int(1000*random.random())
		return child

	def evolution(self):
		while True:
			self.generation()
			self.copies = sorted(self.copies, key = self.getGen)
			print('  ', self.copies[0].mutaterate, self.copies[0].num, self.copies[0].gen)

	def getGen(self, value):
		return value.gen*value.num



#evolve = Evolve('här är det hemliga lösenordet kan du gissa det abcdefghijklmnopqrstuvwxyzåäö', .01, 20)

#evolver = Evolveparam('hej jag heter max bergmark')
#evolver.evolution()

evolve = Evolve('hej jag heter max bergmark', .00, 1000)
evolve.evolution()
print(evolve.gen)