def rektangel(indrag, hojd, bredd):
    for d in range(0, indrag):
        print(bredd*'*')
    for e in range(0, hojd-2*indrag):
        print(indrag*'*', end="")
        print((bredd-2*indrag)*' ', end="")
        print(indrag*'*')
    for f in range(0, indrag):
        print(bredd*'*')

a = int(input("width: "))
b = int(input("height: "))

indr = int(input("thick: "))
print()

if 2*indr < a and 2*indr <= b:
    rektangel(indr, b, a)
else:
    print("Ej ram")
