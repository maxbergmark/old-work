


public class Hello {
    public static void main(String[] args) {
        int c= 042 ^ 0x42;
        while(c > 1) {
            int j= (c&~-2)==0?c>>=1:(c += c << 1) + ++c;
            j= c+~-33+(c>=17?j++<32?45:-8:++j<5|12%c>0?~-1:12-~(-j++ +0100));
            j += j==42?-c:j==043||c==(c&020)?111-j:j==2+j-c?100-j:(j^c)&~-1;
            if(j % 8 == 5 & c % 5 < 3) j += c+77;
            if((~c^010) == -1) j ^= (j<<1)^10;
            System.out.print((char)j);
        }
    }
}