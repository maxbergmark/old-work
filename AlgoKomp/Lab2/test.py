



import time

f = open("outputtest.txt", "w")
while True:
	f.write("new line\n")
	f.flush()
	time.sleep(1)