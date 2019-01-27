import random
import time
import math

class Func:

	def __init__(self, function, mutaterate, num):
		self.function = function
		self.mutaterate = mutaterate
		self.num = num
		self.gen = 0
		self.copies = [[random.random() for _ in range(len(self.function))] for _ in range(self.num)]

	def fitness(self, test):
		return sum((i-j)**2 for i, j in zip(self.function, test))

	def breed(self, parent1, parent2):
		#return [(p1+p2)/2 for p1, p2 in zip(parent1, parent2)]
		return [parent1[i] if random.random() < .5 else parent2[i] for i in range(len(parent1))]

	def mutate(self, test, mutaterate):
		return [value if random.random() < mutaterate else random.random() for value in test]

	def generation(self):
		self.copies = sorted(self.copies, key = self.fitness)
		temp = self.copies[0]
		self.copies = [self.breed(self.copies[int(self.num*random.random()**4)], self.copies[int(self.num*random.random()**4)]) for _ in range(self.num-1)]
		self.copies = [self.mutate(child, self.mutaterate) for child in self.copies] + [temp]
		self.copies = sorted(self.copies, key = self.fitness)
		self.gen += 1

	def evolve(self):
		for _ in range(1000):
			print()
			self.generation()
			tempf = [str(round(i, 5)) for i in self.function]
			tempc = [str(round(i, 5)) for i in self.copies[0]]
#			for i in range(len(self.function)):
#				print(tempf[i], tempc[i])
			print(self.fitness(self.copies[0]))
#			print([self.fitness(test) for test in self.copies])



function = [math.sin(x*math.pi/10)**2 for x in range(-20, 20)]
func = Func(function, .03, 200)
func.evolve()
print([i-j for i, j in zip(func.function, func.copies[0])])