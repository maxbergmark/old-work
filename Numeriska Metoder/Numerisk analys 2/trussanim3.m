function M=trussanim3(xnod,ynod,bars,y,xbel)
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

n=10;
dt = 2*pi/n;

M = moviein(n);
xny = zeros(size(xbel));
yny = zeros(size(xbel));

for j=1:n
    clf
    set(gcf,'PaperUnits','inches','PaperPosition',[0 0 1 1])
    set(gca,'position',[0 0 1 1],'units','normalized')
    row = 0;
    col = 0;
  for k = 1:length(xbel(1,:))
      
    xny(:,k) = xnod + sin(j*dt)*y(1:2:end, k);
    yny(:,k) = ynod + sin(j*dt)*y(2:2:end, k);
    hold on
    trussplot2(xny(:,k)+3*col,yny(:,k)-5*row, bars, sin(j*dt)*xbel(:,k), max(xbel(:,k)));
    axis([min(xnod)-.5,max(xnod)+.5+5*3,-5*row,max(ynod)+.5]);
    drawnow
    if and(col == 5, k ~= length(xbel(1,:)))
        row = row+1;
        col = 0;
    else
        col = col+1;
    end
  end
  
  axis([min(xnod)-.5,max(xnod)+.5+5*3,-5*row,max(ynod)+.5]);
  M(:,j) = getframe;
end
movie(M,10)  % Repeat 10 times