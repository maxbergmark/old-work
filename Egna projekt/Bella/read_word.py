f=open("ordlista.txt","r")
s=f.read()
l=s.split("\n")
avg=(len(s))/(len(l))
print(len(l),avg)
total_letters=0
for word in l:
	total_letters+=len(word)

avg2=total_letters/(len(l))
print(avg2)

print(sum([len(word) for word in l])/len(l))