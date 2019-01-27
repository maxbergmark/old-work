% Function approximation with gaussian RBF

%Do iterative improvement according to the delta rule

clf
hold on

Phi=calcPhi(x,m,var);
f=feval(fun,x);
y=x;
alg='Stochastic';
iterstart=iter;
iterstop=iter+itermax;
psum=zeros(1,itermax/itersub)+0.1;

while iter<iterstop
  substop=iter+itersub;
  esum=0;
%   if (mod(iter, 500) == 0) 
%       eta = eta/10;
%   end
  while iter<substop
    if (iter == 100000)
        eta = 1;
    end
    iter=iter+1;
    rx=fmin + (fmax-fmin)*rand;
    rphi=gauss(rx,m,var);
    ry=rphi'*w;
    err=feval(fun,rx)-ry;
    w=w+eta*err*rphi;
    esum=esum+sqrt(sum(err.*err));
  end
%   eta = min(1,5*esum);
  psum((iter-iterstart)/itersub)=esum;
y=Phi*w;

subplot(3,1,1); loglog(iterstart+1:itersub:iterstop,psum);
title(['RBF-units=' int2str(units) ', ' alg ': log(error vs iter)' num2str(eta)]);
subplot(3,1,2); plot(x,y,x,f);title('Function y and desired y');
subplot(3,1,3); plot(x,f-y);
title(['Residual, max= ' num2str(max(abs(f-y)))]);
drawnow;

end
iter=iterstop

y=Phi*w;

subplot(3,1,1); loglog(iterstart+1:itersub:iterstop,psum);
title(['RBF-units=' int2str(units) ', ' alg ': log(error vs iter)']);
subplot(3,1,2); plot(x,y,x,f);title('Function y and desired y');
subplot(3,1,3); plot(x,f-y);
title(['Residual, max= ' num2str(max(abs(f-y)))]);

%Find the actual output by using the calculated weight vector
