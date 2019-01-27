import os, os.path as p
def cD(d):
	try: return [sum(c) for c in zip(*[[1 if int(str(p.getsize(d+'/'+i))[0]) == j else 0 for j in range(10)] if p.isfile(d+'/'+i) else cD(d+'/'+i,) for i in os.listdir(d)])] if len(os.listdir(d)) > 0 else [0 for _ in range(10)]
	except: return [0 for _ in range(10)]
f = cD('c:\\')
print('\nTotal files:', sum(f), '\n\n', [round(100*i/sum(f), 4) for i in f][1:])