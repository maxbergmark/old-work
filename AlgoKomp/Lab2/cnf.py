#[print("fizzbuzz" if n%15 == 0 else ("fizz" if n%3 == 0 else ("buzz" if n%5 == 0 else n))) for n in range(1,11)]
for n in range(100):print(n%3//2*'fizz'+n%5//4*'buzz'or n+1)
#[print("fizzbuzz"[4*((n%5==0)-(n%15==0)):(8*(n%5==0)+4*(n%3==0))] or n) for n in range(1,20)]
n=1
while ((n+=1)<101): print(n%3//2*'fizz'+n%5//4*'buzz'or (n+1))
