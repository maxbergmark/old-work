
P = (ones(5) + diag(ones(1,5))*15)/20;
Z = [0 0;3.5 0;0 3.5;-3.5 0;0 -3.5];
c = randi(5);
dt = 0.5;
alpha = 0.6;

phi_dash = [1 dt dt^2/2;0 1 dt; 0 0 alpha];
phi_dash_z = [dt^2/2;dt;0];
phi_dash_w = [dt^2/2;dt;1];
phi = [phi_dash zeros(3);zeros(3) phi_dash];
phi_w = [phi_dash_w zeros(3,1);zeros(3,1) phi_dash_w];
phi_z = [phi_dash_z zeros(3,1);zeros(3,1) phi_dash_z];

X = randn(6,1) .* [500 5 5 200 5 5]';



%%

figure(1)
clf
hold on

for k = 1:100
    W = randn(2, 1)*0.25;
%     vx = X(2)*dt + X(3)*dt^2/2+Z(c,1)*dt;
%     vy = X(5)*dt + X(6)*dt^2/2+Z(c,2)*dt;
    X2 = phi*X + phi_z * Z(c,:)';
%     disp(X2(1)-X(1)-vx)
    vx = X2(1)-X(1);
    vy = X2(4)-X(4);
    plot([X(1) X(1)+vx], [X(4) X(4)+vy])
    X = X2 + phi_w*W;
    
    if (rand() > 0.8)
        temp = randi(4);
        if (temp >= c)
            temp = temp+1;
        end
        c = temp;
    end
    plot(X(1),X(4), 'r.');
    title({['X:' num2str(X(1)) 'Y: ' num2str(X(4))];['v_x: ' num2str(X(2)) 'v_y: ' num2str(X(5))];['a_x: ' num2str(X(3)) 'a_y: ' num2str(X(6))]});
    axis equal
%     lim = 1000;
%     axis([-lim lim -lim lim]);
    drawnow;
end
    
    
    
    
    
    
    
    
    
    
    