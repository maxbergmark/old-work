def f(n):return int(n<2)or f(n-1)+f(n-2)
[print(i+1,f(i)) for i in range(20)]