import os
import time
def checkDir(currDir, filesizes):
	try: 
		for i in os.listdir(currDir):
			if os.path.isfile(os.path.join(currDir, i)): filesizes.append(os.path.getsize(os.path.join(currDir, i)))
			else: checkDir(os.path.join(currDir, i), filesizes)
	except: pass
t0 = time.clock()
filesizes = []
checkDir('c:\\', filesizes)
avgs = [round(100*i/len(filesizes), 4) for i in [sum([1 for j in filesizes if int(str(j)[0]) == i]) for i in range(10)]]
print('\nTotal files:', len(filesizes), '\n\n', avgs[1:])
print(time.clock()-t0)