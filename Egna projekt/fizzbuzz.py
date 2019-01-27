for i in range(1,101):print((i%3==0)*'fizz'+(i%5==0)*'buzz'or i)









i=1;exec"print'FizzBuzz'[i%-3&4:12&8-i%5]or i;i+=1;"*100