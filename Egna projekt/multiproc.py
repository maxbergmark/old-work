import multiprocessing as mp
import random
import string
import time


def rand_string(length, queue):
	rand_str = ''.join(random.choice(
		string.ascii_lowercase
		+ string.ascii_uppercase
		+ string.digits) for _ in range(length))
	#queue.put(rand_str)
	return


t0 = time.clock()

if __name__ == '__main__':
	jobs = []
	queue = mp.Queue()
	for i in range(40):
		p = mp.Process(target = rand_string, args = (50000, queue))
		jobs.append(p)
		p.start()

	for i in range(40):
		print(i)
		jobs[i].join()
	print(time.clock()-t0)

	#del jobs
	
	#while not queue.empty():
		#print(queue.get())
