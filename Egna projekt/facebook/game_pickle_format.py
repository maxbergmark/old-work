import pickle

d = {}
count = 0
name = "jesus"
with open("game_data_" + name + ".txt", "r") as f:
	for line in f:
		count += 1
		if (count % 1000000 == 0):
			print(count)
		temp = line.strip().split(" ")
		if (len(temp) < 3):
			print("test" + line + "test")
		d[int(temp[0])] = [int(temp[1]), int(temp[2])]

print("Data processed, dumping.")

pickle.dump(d, open("game_data_" + name + ".p", "wb"))