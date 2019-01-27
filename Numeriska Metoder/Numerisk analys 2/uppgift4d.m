dist0 = 0;
dist1 = 0;
l0 = 15;
l1 = 0;
dist = 0;
while abs(dist-40) > 10^-10
    l = .5*(l0+l1);
    dist = goatRope(l);
    if dist < 40
        l1 = l;
    else
        l0 = l;
    
    end
end
disp(l)