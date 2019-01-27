import random


def mergesort(lista):
	n = len(lista)
	if (n <= 1):
		return lista
	left = lista[:n//2]
	right = lista[n//2:]

	left_sorted = mergesort(left)
	right_sorted = mergesort(right)

	left_i = 0
	right_i = 0
	total_list = []

	while (left_i < len(left_sorted) and right_i < len(right_sorted)):
		if (left_sorted[left_i] < right_sorted[right_i]):
			total_list.append(left_sorted[left_i])
			left_i += 1
		else:
			total_list.append(right_sorted[right_i])
			right_i += 1

	total_list.extend(left_sorted[left_i:])
	total_list.extend(right_sorted[right_i:])

	return total_list



lista = [random.random() for i in range(100000)]
lista = mergesort(lista)
for num in lista:
	print(num)

