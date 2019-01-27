load('eiffel1.mat')
[V,D] = eig(A);
D = diag(D);
[D, ind] = sort(D);
V = V';
Vs = zeros(size(A));
for r = 1:size(D,1)
   Vs(r,:) = V(ind(r),:);
end
n = 522;
D = D(1:n);
Vs = Vs(1:n,:);
format long

for k = 50:n
    clf
    xbel = xnod + Vs(k,1:2:end)';
    ybel = ynod + Vs(k,2:2:end)';
    %if Vs(k,1:2:end)*Vs(k,1:2:end)' > Vs(k,2:2:end)*Vs(k,2:2:end)'
    %    beldat = Vs(k,1:2:end)';
    %else
    %    beldat = Vs(k,2:2:end)';
    %end
    beldat = (Vs(k,1:2:end)'.^2 + Vs(k,2:2:end)'.^2).^.5;
    trussanim2(xnod, ynod, bars, Vs(k,:)', beldat)
    %trussplot2(xnod+Vs(k,1:2:end)', ynod+Vs(k,2:2:end)', bars, beldat)
    %drawnow
    %trussanim2(xnod, ynod, bars, Vs(k,:)')
end