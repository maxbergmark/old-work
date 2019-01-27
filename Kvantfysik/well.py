#import matplotlib.pyplot as plt
import numpy as np
import math
import scipy


def phi(x):
	y1 = (2/(5*a))**.5 * np.sin(np.pi*x/a)
	y2 = (2/(5*a))**.5 * 2 * np.sin(2*np.pi*x/a)
	return (y1+y2)**2
	

a = 1

x = np.linspace(0,a, 1000)



#plt.plot(x, phi(x))
#plt.show()