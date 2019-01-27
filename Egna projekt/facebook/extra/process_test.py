import pickle
import sys


num_files = 162

data = pickle.load(open("data.p", "rb"))
lines_data = pickle.load(open("lines.p", "rb"))

lines = lines_data['lines']
lengths = lines_data['lengths']
processed = data['processed']

m = {}
m2 = [None for _ in range(max(lines))]

# lines = []
# lengths = []

# for i in range(num_files):
# 	print("\r%d   " % (i), end='')
# 	with open("data/backup" + str(i) + "_100000.p", "rb") as f:
# 		temp = pickle.load(f)
# 		for k in temp.keys():
# 			m2[k] = temp[k]
# 		# print(sys.getsizeof(m2))
# 		print(sum(len(i) for i in m2 if i), len(m2))
# 		# lines.extend(temp.keys())
# 		# lengths.extend([len(temp[k]) for k in temp.keys()])

# with open("data/extra0.p", "rb") as f:
# 	temp = pickle.load(f)
# 	# m.update(temp)	
# 	# lines.extend(temp.keys())
# 	# lengths.extend([len(temp[k]) for k in temp.keys()])


print(sys.getsizeof(m))

# d = {}
# d['lines'] = lines
# d['lengths'] = lengths
# pickle.dump(d, open("lines.p", "wb"))

conns = sum(lengths)
lin = len(lines)

print()
print("Unprocessed elements:", len(processed) - lin)
print("Total number of articles:", lin)
print("Total connections:", conns)
print("Memory usage:", 4*conns//1000000, "MB")
print("Average connections per article: %.3f" % (conns/lin))
print("Max article id:", len(m2))


s = 0
s2 = 0

temp_set = set()

for l in lines:
	if l not in temp_set:
		temp_set.add(l)
	else:
		print("Duplicate found:", l)


lines_set = set(lines)


not_processed = []

for l in processed:
	if l not in lines_set:
		not_processed.append(l)
		#print(l)

print("Not processed:", len(not_processed), '\n')
