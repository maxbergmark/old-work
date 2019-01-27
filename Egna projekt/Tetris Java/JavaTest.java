public class JavaTest extends Thread{
static int a = 0;
public static void main(String[] Args) throws Exception{
JavaTest MyB = new JavaTest();
MyB.start();
//Thread.sleep(100);
runLoop(1000000);
System.out.println(a);
}
public static synchronized void runLoop(int b){
for(int i = 0; i<b; i++){
a=i;
}
}
public void run(){
runLoop(12345678);
}
}
