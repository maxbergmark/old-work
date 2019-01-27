import os, os.path as p
def cD(c, f):
	try: 
		for i in [j for j in os.listdir(c) if p.isfile(c+'/'+j)]: f[int(str(p.getsize(c+'/'+i))[0])] += 1
		for i in [j for j in os.listdir(c) if p.isdir(c+'/'+j)]: cD(c+'/'+i, f)
	except: pass
f = [0 for _ in range(10)]
cD('..', f)
print('\nTotal files:', sum(f), '\n\n', [round(100*i/sum(f), 4) for i in f][1:])