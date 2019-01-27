function tprop = randomwalkproposal( t_in, ro )
%randomwalkproposal Returns a random walk proposal by updating one breakpoint at a
%time.
%   For each breakpoint t(i) we generate a candidate tprop(i) according to
%   HA2.pdf

tprop = t_in;
for i=2:(length(t_in)-1)
    R = ro*(t_in(i+1)-t_in(i-1));
    epsilon = unifrnd(-R,R);
    tprop(i) = tprop(i) + epsilon;
end

end

