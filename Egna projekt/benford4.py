print([sum([1 for i in range(10000) if int(str(2**i)[0]) == j])/10000 for j in range(1,10)])
