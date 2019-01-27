function X = newton(x, error)
if nargin < 2
    error = 10^-10;
end
y = vpa(100);
error = vpa(error);
x = vpa(x);
digits(200)

format long
X = x;
while abs(y) > error

    x = x-(x-4*sin(2*x)-3)/(1-8*cos(2*x));
    y = x-4*sin(2*x)-3;
    X = [X; x];
    if abs(y) > 10^10
        X = inf;
        break
    end
end
