p=s[2..2000000]
s(p:xs)=p:s[x|x<-xs,x`mod`p>0]