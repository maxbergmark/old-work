import java.awt.*;

public class Main  {
    public static void main(String[] args) throws AWTException, InterruptedException {
        Robot r = new Robot();
        Thread.sleep(2500);
        for(int i = 0; i < 2000; i++) {
            r.keyPress(81);
            r.keyRelease(81);
            Thread.sleep(10);
        }
    }
}
