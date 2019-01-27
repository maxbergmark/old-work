import time

def findcubesum(num, lista):
    tal = 1
    count = 0

    lista = []
    time1 = time.clock()
    while count < num:
        tempcount = 0
        listatemp = []
        
        for a in range(1, int((tal/2)**(1/3)+1)):
            
            talcheck = tal - a**3

            if round(talcheck**(1/3),10) % 1 == 0:

                tempcount += 1
  
        if tempcount == 2:
 
            lista.append(tal)
           
            count += 1
            print(" ", count)
        tal += 1
        time2 = time.clock()

        
    return lista


def findcubes(num, lista):
    
    count = 0
    resultlista = []

    while count < num:
        tal = lista[count]

        tempcount = 0
        listatemp = []
        for a in range(1, int(tal**(1/3)+1)):
            
            for b in range(1, int((tal/2)**(1/3)+1)):

                if a**3 + b**3 == tal:

                    listatemp.append((b,a))

        count += 1
        

        resultlista.append(listatemp)

    return resultlista


n = int(input("Antal tal: "))
print()
print("Tal hittade: ")
print()

listacubes = []
listasums = []

start1 = time.clock()
listasums = findcubesum(n, listasums)
end1 = time.clock()

start2 = time.clock()
listacubes = findcubes(n, listasums)
end2 = time.clock()
print()
#print()
#print(listasums, end =" ")
#print(listacubes)
#print()
for count in range(n):
    print(listasums[count], ": ", listacubes[count])

print()
print("time1: ", end1-start1)
print("time2: ", end2-start2)
