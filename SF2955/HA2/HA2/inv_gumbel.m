function [ y ] = inv_gumbel( x, beta, mu )
%INV_GUMBEL The quantile (inverse qumulative distribution function) of the Gumbel distribution
y = mu-beta*log(-log(x));
end