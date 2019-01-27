function double_pendulum(ivp, duration, fps, movie)
% DOUBLE_PENDULUM Animates the double pendulum's (mostly) chaotic behavior.
%
%   author:  Alexander Erlich (alexander.erlich@gmail.com)
%
%   parameters:
%   
%   ivp=[phi1; dtphi1; phi2; dtphi2; g; m1; m2; l1; l2]
%
%                               Initial value problem. phi1 and dtphi1 are
%                               the initial angle and anglular velocity. g
%                               is gravity, m1 and l1 mass and rod length.
%                               For an explaining picture, see
%                               documentation file in same folder.
%  
%   duration                    The time interval on which the ode is
%                               solved spans from 0 to duration (in sec).
%
%   fps                         Frames Per Second. The framerate is
%                               relevant both for normal (realtime)
%                               animation and movie recording.
%
%   movie                       If false, a normal realtime animation of
%                               the motion of the double pendulum (the 
%                               framerate being fps) is shown.
%                               If true, a movie (.avi) is recorded. The
%                               filename is 'doublePendulumAnimation.avi'
%                               and the folder into which it is saved is
%                               the current working directory.
%
%   This function calls double_pendulum_ODE and is, in turn, called by
%   double_pendulum_init.
%
%   Example call:    >> double_pendulum([pi;0;pi;5;9.81;1;1;2;1],100,10,false)
%   Or, simply call  >> double_pendulum_init
%
%   ---------------------------------------------------------------------

clear All; clf;
figure(2)
clf
whitebg('k')
figure(1)
whitebg('w')
nframes=duration*fps;
t = linspace(0,duration,nframes);

sol=ode45(@double_pendulum_ODE,[0 duration], ivp);
y=deval(sol,t);



phi1=y(1,:)'; dtphi1=y(2,:)';
phi2=y(3,:)'; dtphi2=y(4,:)';
l1=ivp(8); l2=ivp(9);
P = [];
% phi1=x(:,1); dtphi1=x(:,2);
% phi2=x(:,3); dtphi2=x(:,4);
% l1=ivp(8); l2=ivp(9);

h=plot(0,0,'MarkerSize',10,'Marker','.','LineWidth',2, 'Color', 'k');
range=1.1*(l1+l2); axis([-range range -range range]); axis square;
set(gca,'nextplot','replacechildren');

Xcoord=[0,l1*sin(phi1(1)),l1*sin(phi1(1))+l2*sin(phi2(1))];
Ycoord=[0,-l1*cos(phi1(1)),-l1*cos(phi1(1))-l2*cos(phi2(1))];

    for i=1:length(phi1)-1
        if (ishandle(h)==1)
            ivp(5) = 10*sin(i/50);
            sol=ode45(@double_pendulum_ODE,[0 duration], ivp);
            y=deval(sol,t);
            phi1=y(1,:)'; dtphi1=y(2,:)';
            phi2=y(3,:)'; dtphi2=y(4,:)';
            xtemp = Xcoord(3);
            ytemp = Ycoord(3);
            Xcoord=[0,l1*sin(phi1(i)),l1*sin(phi1(i))+l2*sin(phi2(i))];
            Ycoord=[0,-l1*cos(phi1(i)),-l1*cos(phi1(i))-l2*cos(phi2(i))];
            P = [P;Xcoord Ycoord];
            figure(2)
            axis([-range range -range range]); axis square;
            col = min(1,abs(1-3*abs((xtemp-Xcoord(3))^2+(ytemp-Ycoord(3))^2)))
            colvec = [col col col].^4;
            plot([xtemp, Xcoord(3)], [ytemp, Ycoord(3)], 'Color', colvec, 'LineWidth', 5*col^5)
            hold on
            %figure(1)
            %set(h,'XData',Xcoord,'YData',Ycoord);
            drawnow;
            F(i) = getframe;
            if movie==false
                pause(t(i+1)-t(i));
            end
        end
    end
    if and(movie==true, false)
        movie2avi(F,'doublePendulumAnimation.avi','compression','Cinepak','fps',fps)
    end