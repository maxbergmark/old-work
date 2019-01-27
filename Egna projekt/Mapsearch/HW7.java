public class HW7  {
 public static  void
  main(String[] args)
   {int l0=1<<20,c =0;
    for (int i =0,a =0;
     i<l0; i++,a=0) {for
      (int j=0;j<(3<<6)-2
       ;j +=1) {a += (int)
        (Math.random()+.5);
         }c+=a>(7<<4)-8?1:0;
          }System.out.println
           (c/(double) l0);} }