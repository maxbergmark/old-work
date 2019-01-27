import time

class Truck:
    
    def __init__(self):
        self.tank = 100
        self.cost = 0
        self.distance = 0
        self.index = 0

    def refill(self, amount, price):
        self.tank += amount
        self.cost += amount*price


def calcRefill(truck, distance, lists):
    tempdist = truck.index
    while lists[tempdist][0] - truck.distance <= truck.tank:
        tempdist += 1

    minprice = lists[truck.index][1]
    mindist = None
    for i in range(truck.index, tempdist):
        if lists[i][1] <= minprice:
            minprice = lists[i][1]
            mindist = lists[i][0]
    truck.distance = mindist
    truck.refill(200-truck.tank, minprice)





dist = 500
stations = [(100,999),
            (150,888),
            (200,777),
            (300,999),
            (400,1009),
            (450,1019),
            (500,1399),]
        
truck = Truck()


calcRefill(truck, dist, stations)
print(truck.distance, truck.tank, truck.cost)

#while truck.distance < dist:
#    print(calcRefill(truck, dist, stations))
