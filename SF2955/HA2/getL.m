function L = getL(t1,t2,t3,y)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here

n=length(y);
y1=sum(y(1:t1));
y2=sum(y(t1+1:n));
L=exp(log(t2)*y1+log(t3)*y2-t1*t2-(n-t1)*t3);

end

