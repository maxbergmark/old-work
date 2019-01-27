
public class Main {

    public static void main(String[] args) {
        //new Paint(30,30);
        Read read = new Read("monroe.jpg", 32);
        new Controller(300, read.array, 30, 0);
    }
}
