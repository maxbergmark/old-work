figure(1)
load M
clf
set(gcf,'PaperUnits','inches','PaperPosition',[0 0 1 1])
set(gca,'position',[0 0 1 1],'units','normalized')
movie(gca, M, 1000, 500)