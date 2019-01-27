public class Labb4 {

    public static void main(String[] args) {
        int x = 300;
        int y = 300;
        Model model = new Model(10000, 1, x, y);
        View view = new View(x,y, model);
        Controller controller = new Controller(model, view, 0);
        MyFrame frame = new MyFrame(model, view);
        controller.timer.start();
    }
}
