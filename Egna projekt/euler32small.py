s,t,r=set,str,range
print(sum(s([a*b for a in r(4,49)for b in r(138,7860//a)if s(t(a)+t(b)+t(a*b))==s('123456789')])))