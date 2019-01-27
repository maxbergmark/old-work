import time
import math

def isqrt(n):
   return int(math.floor(math.sqrt(float(n))))

def factorize(n):
   if n < -1: return [[-1, 1]] + factorize(-n)
   elif n == -1: return [[-1, 1]]
   elif n == 0: return [[0, 1]]
   elif n == 1: return [[1, 1]]
   else:
      factors = []
      divisor = 2
      while divisor <= isqrt(n):
         power = 0
         while (n % divisor) == 0:
            n //= divisor
            power += 1
         if power > 0:
            factors.extend(power*[divisor])
         divisor += 1
      if n > 1:
         factors.extend([n])
      return factors
    
proda = 1
prodb = 1
for a in range(1, 100):

    for b in range(1, a):
        stra = str(a) + '0'
        strb = str(b) + '0'

        for mova in range(2):
            for movb in range(2):
                if stra[mova] != '0' and strb[movb] != '0':
                    if stra[1-mova] == strb[1-movb] and stra[1-mova] != '0':
                            if a/b == int(stra[mova]) / int(strb[movb]):
                                print(b, a)
                                proda *= a
                                prodb *= b


faclistb = factorize(prodb)
faclista = factorize(proda)

slutprod = 1
for element in faclista:
    if element in faclistb:
        
        faclistb.remove(element)
        
    else:
        slutprod *= element


print(slutprod)
