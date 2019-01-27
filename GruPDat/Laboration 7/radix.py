class Radix():
    def __init__(self, numbers = []):
        

        for n in range(1, 5):
            hinkar = [[] for i in range(10)]
            print(numbers)
            for number in numbers:
                hinkar[int(number%(10**n)//(10**(n-1)))].append(number)
            numbers = []
            for hink in hinkar:
                numbers.extend(hink)
        

    def view(self):
        pass
        #return numbers 
                
