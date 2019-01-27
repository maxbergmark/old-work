import os, os.path as p

def cD(c, f):
	try:
		for i in os.listdir(c):
			if p.isfile(c+'/'+i): f[int(str(p.getsize(c+'/'+i))[0])] += 1
			else: cD(c+'/'+i, f)
	except: pass

f = [0 for _ in range(10)]
cD('/home', f)
print('\n Total files:', sum(f), '\n\n', [round(100*i/sum(f), 4) for i in f][1:])