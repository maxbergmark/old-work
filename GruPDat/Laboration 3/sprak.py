def stjarnsprak(inrad):
    utrad = ""
    for tkn in inrad:
        utrad += tkn
        utrad += '*'
    return utrad

def vokaltest(bokstav):
    vokal = ['A', 'E', 'I', 'O', 'U', 'Y', 'Å', 'Ä', 'Ö', 'a', 'e', 'i', 'o', 'u', 'y', 'å', 'ä', 'ö']
    return bokstav in vokal

def konsonanttest(bokstav):
    konsonant = ['B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Z', 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z']
    return bokstav in konsonant
