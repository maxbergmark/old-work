function A=trussplot2(x,y,br,xbel,xmax)
% TRUSSPLOT  Plots a truss
%
% TRUSSPLOT(X,Y,BARS) plots a truss with nodes in
% coordinates (X,Y) and bars between node indices
% given in BARS. The optional arguments XBEL and XMAX
% are used to decide the color for a point. The color
% varies from blue for low values, to red for high values.
%
% The same plotstyles S as the PLOT command can be obtained
% with TRUSSPLOT(X,Y,BARS,S).

if (nargin < 4)
    xbel = [];
end
if (nargin < 5)
    xmax = max(xbel);
end

for k=1:length(br)
    if isempty(xbel)
        plot(x(br(k,1:2)),y(br(k,1:2)))
    else
        maxval = max(xmax, max(abs(xbel)));
        colval = abs(xbel(br(k, 1))/maxval);
        plot(x(br(k,1:2)),y(br(k,1:2)),'Color', [colval 0 1-colval]);
    end

    hold on
end
axis equal
hold off


