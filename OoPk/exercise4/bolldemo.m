x = 0;
y = 0;
vx = 2;
vy = 1;
g = -9.82;
dt = 0.05;
clf;

while(1)
    
    if (abs(x) > 2)
        vx = -vx;
    end
    if (abs(y) > 2)
        vy = -vy;
    end
    
    newvx = vx;
    newvy = vy + g*dt;
    
    x = x + .5*(vx+newvx)*dt;
    y = y + .5*(vy+newvy)*dt;
    
    vx = newvx;
    vy = newvy;
    
    plot(x,y,'o')
    hold on
    axis([-2 2 -2 2]);
    drawnow;
    
    pause(dt);
    
    
    
end
