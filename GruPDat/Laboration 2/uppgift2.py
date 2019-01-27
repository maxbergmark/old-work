def rektangel(indrag, hojd, bredd, tecken):
    for d in range(0, hojd):
        print((indrag)*' ', end="")
        print(bredd*tecken)
a = int(input("width: "))
b = int(input("height: "))
c = str(input("symbol: "))
indr = int(input("push: "))

print()
rektangel(indr, b, a, c)
