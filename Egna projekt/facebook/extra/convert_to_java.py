import pickle



for i in range(162):
	print("\r%d     " % (i), end="")
	with open("data/backup" + str(i) + "_100000.p", "rb") as f:
		temp = pickle.load(f)
		with open("formatted/backup" + str(i) + ".txt", "w") as w:
			w.write("%X\n" % (len(temp)))
			for k in temp.keys():
				w.write("%X\n" % (k))
				w.write("%X\n" % (len(temp[k])))
				w.write(' '.join(["%X" % (i) for i in temp[k]]))
				w.write("\n")


with open("data/extra0.p", "rb") as f:
	temp = pickle.load(f)
	with open("formatted/backup162.txt", "w") as w:
		w.write("%X\n" % (len(temp)))
		for k in temp.keys():
			w.write("%X\n" % (k))
			w.write("%X\n" % (len(temp[k])))
			w.write(' '.join(["%X" % (i) for i in temp[k]]))
			w.write("\n")

print()
