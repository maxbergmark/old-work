import java.util.LinkedList;

public class Window {
    Snake snake;
    int score;
    boolean[][] grid;
    Snake.Point goal;
    Controller controller;
    Snake.Point last;


    public Window(int snakeLength, int gridSize, Controller controller) {
        score = snakeLength;
        grid = new boolean[gridSize][gridSize];
        snake = new Snake(snakeLength);
        this.controller = controller;
        snake.goal();
    }

    public void resetGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = false;
            }
        }
    }

    public class Snake extends LinkedList<Snake.Point> {
        int direction;
        boolean upgrade;

        public Snake(int length) {
            for (int i = 0; i < length; i++) {
                add(new Point(grid.length/2-i, 0));
                grid[grid.length/2-i][0] = true;
            }
            direction = 1;
            upgrade = false;
        }

        public void updateSnake() {
            addFirst(get(0).move(direction));
            grid[get(0).x][get(0).y] = true;
            if(!upgrade) {
                grid[get(size()-1).x][get(size()-1).y] = false;
                last = get(size()-1);
                remove(size() - 1);
            } else {
                upgrade = false;
                goal();
                score++;
                //controller.timer.setDelay(200-5*score);
            }
            /*resetGrid();
            for (int i = 0; i < size(); i++) {
                grid[get(i).x][get(i).y] = true;
            }*/
            //System.out.println(get(0).x + "    " + get(0).y + "    " + direction);
        }
        public boolean checkSnake (Point head) {
            Point point = head.move(direction);
            grid[get(size()-1).x][get(size()-1).y] = false;
            if (head.x == goal.x && head.y == goal.y) {
                upgrade = true;
            }
            try {
                if(grid[point.x][point.y]) {
                    return false;
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                return false;
            }
            return true;
        }
        public void goal() {
            int x,y;
            do {
                x = (int)(Math.random() * grid.length);
                y = (int)(Math.random() * grid[0].length);
            } while(grid[x][y]);
            goal = new Snake.Point(x,y);
        }

        public class Point {
            int x,y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
            public Point move(int direction) {
                Point point = new Point(x,y);
                if(direction == 0) {
                    point.y--;
                } else if(direction == 1) {
                    point.x++;
                } else if (direction == 2) {
                    point.y++;
                } else if (direction == 3) {
                    point.x--;
                }
                return point;
            }
        }
    }
}
