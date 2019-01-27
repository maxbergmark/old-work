n=40001;f=open("out.txt","w");[f.write(("wo"*(n//2+2))[i%2:n+(i%2)]+'\n') for i in range(n)]
