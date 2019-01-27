load halfdata
restoredefaultpath %pga bugg hos Matlab i E-datasalen
rehash toolboxcache

sortedData = sort(timelist,'ascend');
lowBound = find(sortedData > 10, 1)
highBound = find(sortedData > 1500, 1)
bins = 100;

prepData = sortedData(lowBound:highBound);
deltaTimeBin = ( max(prepData) - min(prepData) ) / bins;

y = hist(prepData, bins);
x = (1:size(y,2)) * deltaTimeBin;

%% Fit: 'untitled fit 1'.
[xData, yData] = prepareCurveData( x, y );

% Set up fittype and options.
ft = fittype( 'a*exp(-x/b)+c', 'independent', 'x', 'dependent', 'y' );
opts = fitoptions( 'Method', 'NonlinearLeastSquares' );
opts.Algorithm = 'Levenberg-Marquardt';
opts.Display = 'Off';
opts.Robust = 'Bisquare';
opts.StartPoint = [800 1/0.05 50];

% Fit model to data.
[fitresult, gof] = fit( xData, yData, ft, opts );

% Plot fit with data.
figure( 'Name', 'Muon mean lifetime fit' );
h = plot( fitresult, xData, yData );
legend( h, 'Observations vs. time', 'fitted exponential', 'Location', 'NorthEast' );
% Label axes
xlabel( 'Time' );
ylabel( 'Detected muons' );
grid on
%%

tau = fitresult.b
confInt = confint(fitresult); % 95%
confIntTau = confInt(:,2)
text(410, 180, ['Tau: ' num2str(tau)])
text(410, 140, '95% confidence interval: ' )
text(950, 140,  num2str(confIntTau))