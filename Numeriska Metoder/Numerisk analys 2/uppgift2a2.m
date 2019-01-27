function M = uppgift2a2()
load('eiffel1.mat')
[V,D] = eig(A);
D = diag(D);
[D, ind] = sort(D);
V = V';
Vs = zeros(size(A));
for r = 1:size(D,1)
   Vs(r,:) = V(ind(r),:);
end
n = 12;
D = D(1:n);
Vs = Vs(1:n,:);
format long

for k = 1:n
    clf
    xbel = xnod + Vs(k,1:2:end)';
    ybel = ynod + Vs(k,2:2:end)';
    xbels(:, k) = xbel;
    ybels(:, k) = ybel;

    beldat = (Vs(k,1:2:end)'.^2 + Vs(k,2:2:end)'.^2).^.5;
    beldats(:, k) = beldat;

end

M = trussanim3(xnod, ynod, bars, 10*Vs(1:n, :)', beldats);