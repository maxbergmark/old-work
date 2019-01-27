for i in range(1200):
  values = [0 for _ in range(12)];
  for x in range(12):
    xval = 100*x;
    values[x] = 0;
    yval = -(xval-i)*(xval-i)/4200.0 + 15.0;
    
    if (yval > 0):
      dist = int(yval);
      values[x] = dist;


  print(values);
