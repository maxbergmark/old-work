% No prediction 10x16

A00 = [29 93 24 77 113 84 59 26 172 126 67 43 94 132 110 51 74 33 126 35];
A11 = [51 24 39 95 126 69 77 69 119 76 41 32 58 238 77 98 34 52 107 51];
A01 = [87 60 156 68 149 159 117 24 57 453 28 370 641 231 312 171 210 53 252 1062];
A12 = [434 692 361 66 596 134 200 248 374 34 293 1005 520 132 36 292 302 52 248 200];
A23 = [307 201 349 144 222 425 398 94 391 468 170 896 315 1270 465 92 77 46 93 746];
A34 = [34 47 450 362 91 195 46 257 51 194 171 578 122 213 316 25 31 106 314 579];
A45 = [50 274 74 50 184 45 75 386 58 72 37 225 204 194 53 149 146 47 340 243];

a00 = sum(A00)/length(A00);
a11 = sum(A11)/length(A11);
a01 = sum(A01)/length(A01);
a12 = sum(A12)/length(A12);
a23 = sum(A23)/length(A23);
a34 = sum(A34)/length(A34);
a45 = sum(A45)/length(A45);

va00 = a00/sqrt(length(A00));
va11 = a11/sqrt(length(A11));
va01 = a01/sqrt(length(A01));
va12 = a12/sqrt(length(A12));
va23 = a23/sqrt(length(A23));
va34 = a34/sqrt(length(A34));
va45 = a45/sqrt(length(A45));

as = [a00 a11 a01 a12 a23 a34 a45];
vas = [va00 va11 va01 va12 va23 va34 va45];

figure(1);
clf;
bar(as);
hold on;
errorbar(as, vas, 'r.')
title('No prediction, 10x16 grid');
ylabel('Average number of cleared lines');
xlabel('Weighing functions (f(y) / g(y))');
set(gca,'XTickLabel',{'1 / 1', 'y / y', 'y / 1', 'y^2 / y', 'y^3 / y^2', 'y^4 / y^3', 'y^5 / y^4'});

% Single step prediction 10x16

B00 = [422 596 1539 1451 88 4873 1708 1750 1299 292 1776 57 129 1883 1436 224 235 231 323 1847 1077 1864 4721 202 458 2685 1139 983 185 723];
B11 = [462 847 231 182 533 516 1864 477 395 98 163 352 1497 1221 1296 331 358 962 703 84 146 209 70 365 247 95 2374 265 1039 73];
B01 = [833 844 1934 60 334 962 1113 2568 1826 705 501 175 3118 1283 3675 1840 399 1549 513 2355 119 237 1048 4156 23 1941 1346 1335 862 17];
B12 = [1910 7313 3509 4152 3522 1424 511 5599 2567 5037 5552 2390 608 9181 11132 94 285 2497 4796 726 136 10267 1712 6901 3891 2270 2363 3020 4877 349];
B23 = [3069 8740 317 6956 3250 197 1474 5108 1343 5898 1160 3826 1508 3128 726 1102 1916 8654 4564 1771 1870 1962 1635 458 423 74 1606 912 1811 2139];
B34 = [2196 602 1433 2746 6875 207 1896 20 3911 3060 3562 5566 1501 2443 1402 205 6464 460 5998 713 826 528 3879 3747 257 1701 19 9534 1043 1268];
B45 = [428 206 3019 409 1660 596 2235 6114 793 1574 1128 1815 377 1884 4173 973 755 1925 246 1245 825 1491 1391 470 256 2346 3243 1562 3453 2195];


b00 = sum(B00)/length(B00);
b11 = sum(B11)/length(B11);
b01 = sum(B01)/length(B01);
b12 = sum(B12)/length(B12);
b23 = sum(B23)/length(B23);
b34 = sum(B34)/length(B34);
b45 = sum(B45)/length(B45);

vb00 = b00/sqrt(length(B00));
vb11 = b11/sqrt(length(B11));
vb01 = b01/sqrt(length(B01));
vb12 = b12/sqrt(length(B12));
vb23 = b23/sqrt(length(B23));
vb34 = b34/sqrt(length(B34));
vb45 = b45/sqrt(length(B45));

bs = [b00 b11 b01 b12 b23 b34 b45];
vbs = [vb00 vb11 vb01 vb12 vb23 vb34 vb45];

figure(2);
clf;
bar(bs);
hold on;
errorbar(bs, vbs, 'r.')
title('Single step prediction, 10x16 grid');
ylabel('Average number of cleared lines');
xlabel('Weighing functions (f(y) / g(y))');
set(gca,'XTickLabel',{'1 / 1', 'y / y', 'y / 1', 'y^2 / y', 'y^3 / y^2', 'y^4 / y^3', 'y^5 / y^4'});

% Two step prediction 10x16

C00 = [23525 27709 3787 13477 36426 47453 486 14582 12361 5711 1767 6707 16566 9397 2901];
C11 = [3356 29005 3716 3962 6705 5101 2569 46783 117759 9520 1876 15299];
C01 = [1199 6155 157 7619 5730 1157 7544 22 3874 4592 1774 1410 6025 1276 5977];
C12 = [9197 4203 6941 8364 2451 3019 18113 5631 9261 38454 60867 4011 9623 55297 8640];
C23 = [614 44402 2575 7183 17721 38527 58665 21307 20872 38979 13197 83009 5503 5473 23637];
C34 = [92456 6798 23673 7734 76184 43246 47675 7091 4206 27245 40676 1413 6023 14561 61796];
C45 = [87411 25462 2128 3445 9710 52635];

c00 = sum(C00)/length(C00);
c11 = sum(C11)/length(C11);
c01 = sum(C01)/length(C01);
c12 = sum(C12)/length(C12);
c23 = sum(C23)/length(C23);
c34 = sum(C34)/length(C34);
c45 = sum(C45)/length(C45);


vc00 = c00/sqrt(length(C00));
vc11 = c11/sqrt(length(C11));
vc01 = c01/sqrt(length(C01));
vc12 = c12/sqrt(length(C12));
vc23 = c23/sqrt(length(C23));
vc34 = c34/sqrt(length(C34));
vc45 = c45/sqrt(length(C45));

cs = [c00 c11 c01 c12 c23 c34 c45];
vcs = [vc00 vc11 vc01 vc12 vc23 vc34 vc45];

figure(3);
clf;
bar(cs);
hold on;
errorbar(cs, vcs, 'r.')
title('Two step prediction, 10x16 grid');
ylabel('Average number of cleared lines');
xlabel('Weighing functions (f(y) / g(y))');
set(gca,'XTickLabel',{'1 / 1', 'y / y', 'y / 1', 'y^2 / y', 'y^3 / y^2', 'y^4 / y^3', 'y^5 / y^4'});

% Two step 10x20

D00 = [72628 318496 189279 129124 76368 47453 2605 50952 292540 24587 82130 91684 203183 254213 178612 64710];
D11 = [0];
D01 = [0];
D12 = [0];
D23 = [245008 204255 471121 32091];
D34 = [92456 28416 382979 145190 91799 252994 479349 133480 465712 179451 142139 50997 291087 202804 371366 120182];
D45 = [327886 38952 507232 298181 154081 848026 602971 256764 156110 138712 656865 509083 410856 466260 151372 532205];

d00 = sum(D00)/length(D00);
d11 = sum(D11)/length(D11);
d01 = sum(D01)/length(D01);
d12 = sum(D12)/length(D12);
d23 = sum(D23)/length(D23);
d34 = sum(D34)/length(D34);
d45 = sum(D45)/length(D45);

vd00 = d00/sqrt(length(D00));
vd11 = d11/sqrt(length(D11));
vd01 = d01/sqrt(length(D01));
vd12 = d12/sqrt(length(D12));
vd23 = d23/sqrt(length(D23));
vd34 = d34/sqrt(length(D34));
vd45 = d45/sqrt(length(D45));

ds = [d00 d11 d01 d12 d23 d34 d45];
vds = [vd00 vd11 vd01 vd12 vd23 vd34 vd45];

figure(4);
clf;
bar(ds);
hold on;
errorbar(ds, vds, 'r.')
title('Two step prediction, 10x20 grid');
ylabel('Average number of cleared lines');
xlabel('Weighing functions (f(y) / g(y))');
set(gca,'XTickLabel',{'1 / 1', 'y / y', 'y / 1', 'y^2 / y', 'y^3 / y^2', 'y^4 / y^3', 'y^5 / y^4'});

bestScores = [a23 b12 c34 d45];
standardScores = [a00 b00 c00 d00];
improvements = bestScores./standardScores;
figure(5);
clf;

bar(bestScores);
set(gca,'YScale','log');
title('Best obtained scores for different levels of prediction');
ylabel('Average number of cleared lines');
xlabel('Game board size and level of prediction');
set(gca,'XTickLabel',{'10x16, 0 steps', '10x16, 1 steps', '10x16, 2 steps', '10x20, 2 steps'});

figure(6);
clf;
plot(improvements);