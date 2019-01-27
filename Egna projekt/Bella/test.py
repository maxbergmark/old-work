a="bajs"
b=a*5+"korv"*45+"bella"*23
print(len(b))

acounter=0
# den här raden ska inte köras
for i in range(len(b)):
#	print(b[i], b[i]=="a")
	if (b[i]=="a"):
		acounter+=1

print(acounter)