function A=trussanim2(xnod,ynod,bars,y,xbel)
% TRUSSANIM  Animates oscillations in a truss
%
% TRUSSANIM(XNOD,YNOD,BARS,Y, XBEL) animates a truss with
% amplitdes given in Y. The syntax for XNOD,YNOD 
% and BARS is the same as for TRUSSPLOT. The optional
% variable YBEL decides the color of the plot, with 
% low values being blue, and high values being red.
% Try creating a vector describing the displacement 
% of the tower.

if nargin < 5
    xbel = [];
end

n=20;
dt = 2*pi/n;

M = moviein(n);
for j=1:n
  xny = xnod + sin(j*dt)*y(1:2:end);
  yny = ynod + sin(j*dt)*y(2:2:end);
  if isempty(xbel)
     trussplot2(xny,yny, bars)
  else
     %The first one varies in color during the animation.
     %The second one has a constant color depending on the maximum
     %displacement. Choose the one you want.
     
     trussplot2(xny,yny, bars, sin(j*dt)*xbel, max(xbel));
     %trussplot2(xny,yny, bars, xbel);
  end
    
  axis([min(xnod)-.5,max(xnod)+.5,0,max(ynod)+.5]);
  M(:,j) = getframe;
end
movie(M,5)  % Repeat 5 times