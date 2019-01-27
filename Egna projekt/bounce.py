
global v1
global v2
global M
global m
v1 = 10
v2 = 0
m = 1
M = 16*m#*1*100**0

def bouncer():
    global v1
    global v2
    global M
    global m

    print(v1, v2)

    v1t = ((M-m)/(M+m))*v1-((2*m)/(M+m))*v2
    v2t = ((2*M)/(M+m))*v1+((M-m)/(M+m))*v2

    v1 = v1t
    v2 = v2t

    print(v1, v2)
    #(v1, v2) = (((m-M)/(M+m))*v1+((2*m)/(M+m))*v2, ((2*M)/(M+m))*v1+((M-m)/(M+m))*v2)

while True:
    print(v1, v2)
    v2 = -v2
    print(v1, v2)
    bouncer()
    input()
