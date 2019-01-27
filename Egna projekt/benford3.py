import os, os.path as p
def cD(c, f, l):
	try:
		print(c) 
		for i in os.listdir(c):
			if p.isfile(c+'/'+i): f[int(str(p.getsize(c+'/'+i))[0])] += 1
			else: cD(c+'/'+i, f, l+1)
	except: pass
	#print(f)
	#if l == 0: return f
f = [0 for _ in range(10)]
cD('..', f, 0)
print('\nTotal files:', sum(f), '\n\n', [round(100*i/sum(f), 4) for i in f][1:])