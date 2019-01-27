

N = 10000;


sigmas = [500 5 5 200 5 5];

X_p = [randn(6,N).*sigmas'; randi(5,1,N)];
% figure(1)
% clf
% plot(coords(:,1),coords(:,2), '.')
% hold on
% plot(pos_vec(1,:), pos_vec(2,:), 'r*')
% axis equal

figure(2)
clf
plot(y')
legend('norr', 'söder', 'nordost', 'sydost', 'sydväst', 'nordväst')
% log(P(path | \sigma))
figure(3)
clf
plot(X_p(1,:), X_p(4,:), '.')
hold on
plot(pos_vec(1,:), pos_vec(2,:), 'r*')
axis equal

y1t = zeros(6,N);
for i = 1:6
    y1t(i,:) = v - 10*eta*log10(sqrt( sum((X_p([1 4],:)-pos_vec(:,i)).^2, 1) ));
end
W_p = prod(normpdf(y1t, y(:,1), sigma));
% W_p = ones(1,N);

figure(4)
clf
hist3(X_p([1 4],:)');
% plot3(X_p(1,:),X_p(4,:), W_p, '*')
hold on
% plot3(coords(1,1), coords(1,2), max(W_p), 'ro')
axis vis3d
xlabel('X')
ylabel('Y')
zlabel('Z')

guesses = [];
guesses = [guesses sum(X_p(1:6,:).*W_p, 2)/sum(W_p)];
% plot3(guess(1),guess(4),max(W_p), 'go')

sigma_w = .25;


for i = 2:501
    disp(i)
    
%     particle(:,t+1) = phi*particle(:,t) + phi_z*Z(c,:)' + phi_w*W;
    sample = randsample(N,N,true, W_p);
    W_p = W_p(sample');
    X_p(:,:) = X_p(:,sample');
    
    W = randn(2, N)*sigma_w^2;
    X_p(1:6,:) = phi*X_p(1:6,:) + phi_z * Z(X_p(7,:),:)' + phi_w*W;
%     X_diff = phi_w*W;

    
%     W_p = prod(normpdf(W, 0, sigma_w),1);
    
%     for j2 = 1:N
        
%     end
    
    change = rand(N,1);
    num = sum(change<.25);
    new = zeros(1,N);
    new(change<.25) = randi(5, 1,num);
    changed = X_p(7,:) ~= new & (change<.25)';
    X_p(7, change<.25) = new(new>0);
    
    for j = 1:6
        y1t(j,:) = v - 10*eta*log10(sqrt( sum((X_p([1 4],:)-pos_vec(:,j)).^2, 1) ));
    end
    
    W_p = prod(normpdf(y1t, y(:,i), sigma));
    guesses = [guesses sum(X_p(1:6,:).*W_p, 2)/sum(W_p)];


%     figure(3)
%     clf
%     plot(X_p(1,:), X_p(4,:), '.')
%     hold on
%     plot(pos_vec(1,:), pos_vec(2,:),  'r*');
%     plot(coords(i,1), coords(i,2), 'g*')
%     plot(guesses(1,i), guesses(4,i), 'm*')
% 
%     text(-4900, 3500, ['Time step: ' num2str(i)]);
%     text(-4900, 3000, ['Sum: ' num2str(sum(W_p))]);
%     axis equal
%     axis([-5000 8000 -8000 4000])
%     legend('Particles', 'Base stations', 'Actual path', 'SISR estimate')
%     drawnow

end


%%

figure(5)
clf
% plot(coords(:,1), coords(:,2), 'b')
% hold on
plot(guesses(1,:), guesses(4,:), 'g')
hold on
plot(pos_vec(1,:), pos_vec(2,:), 'r*')
axis equal
title('SISR estimation of path')
ylabel('Y')
xlabel('X')
legend('SISR estimate', 'Base stations')

% figure(6)
% clf
% diff = guesses([1 4],:)-coords';
% diff_len = arrayfun(@(idx) norm(diff(:,idx)'), 1:size(diff,2));
% plot(1:500, diff_len)




