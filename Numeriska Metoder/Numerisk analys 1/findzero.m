function X = findzero(x, error)
if nargin < 2
    error = 10^-10;
end
y = vpa(100);
error = vpa(error);
x = vpa(x);
digits(200)
X = x;


while abs(y) > error

    x = -sin(2*x) + 5/4*x - 3/4;
    y = x-4*sin(2*x)-3;
    X = [X; x];
    if abs(y) > 10^10
        X = inf;
        break
    end
        
end
%disp(x)
%disp(y)

%Alla nollst�llen ligger i intervallet -2:5
%g(x) = -sin(2*x) + 5/4*x - 3/4 = f(x)/4 + x. D� f(x) == 0 kommer 
%g(x) == x. Annars kommer g(x) g�ra s� att x blir st�rre vid iteration
%om f(x) > 0 och x blir mindre vid iteration om f(x) < 0. Detta inneb�r 
%att det endast �r tv� nollst�llen som �r stabila, -.5444 och 3.1618. 
%Genom att s�tta upp ett teckenschema kan vi se att de tre andra 
%nollst�llena �r instabila, och x divergerar ifr�n dem.