


void writeTofile(Node **nodes, int n) {

	FILE *f;
	unsigned char *img = NULL;
	unsigned long w = 2*n+1;
	unsigned long h = 2*n+1;
	unsigned long filesize = 54L + 3L*w*h;	//w is your image width, h is image height, both int
	if( img )
		free( img );
	img = (unsigned char *)malloc((unsigned long) (3L*w*h));
	//memset(img,0,sizeof(img));


	for(unsigned long i=0; i<w; i++) {
		for(unsigned long j=0; j<h; j++) {
			//printf("%u\n", (i+j*w)*3+2);
			img[(i+j*w)*3+2] = (unsigned char) (0);
			img[(i+j*w)*3+1] = (unsigned char) (0);
			img[(i+j*w)*3+0] = (unsigned char) (0);
		}
	}

	for (unsigned long i = 0; i < (unsigned long) n; i++) {
		for (unsigned long j = 0; j < (unsigned long) n; j++) {
			img[(2*i+2*j*w+w+1)*3+2] = (unsigned char) 255;
			img[(2*i+2*j*w+w+1)*3+1] = (unsigned char) 255;
			img[(2*i+2*j*w+w+1)*3+0] = (unsigned char) 255;
			
			if (nodes[i][j].c & 1) {
				img[(2*i+2*j*w+w+w+1)*3+2] = (unsigned char) 255;
				img[(2*i+2*j*w+w+w+1)*3+1] = (unsigned char) 255;
				img[(2*i+2*j*w+w+w+1)*3+0] = (unsigned char) 255;
				//values[2*i][2*j+1] = 1;
			}

			if (nodes[i][j].c & 2) {
				img[(2*i+1+2*j*w+w+1)*3+2] = (unsigned char) 255;
				img[(2*i+1+2*j*w+w+1)*3+1] = (unsigned char) 255;
				img[(2*i+1+2*j*w+w+1)*3+0] = (unsigned char) 255;
				//values[2*i+1][2*j] = 1;
			}
		}
	}


	unsigned char bmpfileheader[14] = {'B','M', 0,0,0,0, 0,0, 0,0, 54,0,0,0};
	unsigned char bmpinfoheader[40] = {40,0,0,0, 0,0,0,0, 0,0,0,0, 1,0, 24,0};
	unsigned char bmppad[3] = {0,0,0};

	printf("%lu\n", filesize);

	bmpfileheader[ 2] = (unsigned char)(filesize	);
	bmpfileheader[ 3] = (unsigned char)(filesize>> 8);
	bmpfileheader[ 4] = (unsigned char)(filesize>>16);
	bmpfileheader[ 5] = (unsigned char)(filesize>>24);

	bmpinfoheader[ 4] = (unsigned char)(	 w	);
	bmpinfoheader[ 5] = (unsigned char)(	 w>> 8);
	bmpinfoheader[ 6] = (unsigned char)(	 w>>16);
	bmpinfoheader[ 7] = (unsigned char)(	 w>>24);
	bmpinfoheader[ 8] = (unsigned char)(	 h	);
	bmpinfoheader[ 9] = (unsigned char)(	 h>> 8);
	bmpinfoheader[10] = (unsigned char)(	 h>>16);
	bmpinfoheader[11] = (unsigned char)(	 h>>24);

	f = fopen("img2.bmp","wb");
	fwrite(bmpfileheader,1,14,f);
	fwrite(bmpinfoheader,1,40,f);
	for(unsigned long  i=0; i<h; i++)
	{
		fwrite(img+(w*(h-i-1L)*3L),3,w,f);
		fwrite(bmppad,1,(4-(w*3)%4)%4,f);
	}
	fclose(f);
	delete[] img;

}


struct Comparator
{
	bool
	operator()(const Tuple & obj1, const Tuple & obj2) const
	{
		if ((obj1.x == obj2.x) & (obj1.y == obj2.y)) {
			return true;
		}
		return false;
	}
};

// passed string objects length
struct Hasher
{
	size_t
	operator()(const Tuple & obj) const
	{
		return obj.x;
	}
};