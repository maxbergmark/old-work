
public class Snek {
    Window window;
    Window.Snake snake;
    int currentDirection;
    String why;

    public Snek(Window window) {
        this.window = window;
        this.snake = window.snake;
        why = "whut";
    }

    public void updateBot() {
        pattern();
        if((double)window.score/(window.grid.length * window.grid[0].length) < .4) {
            chase();
        }
        currentDirection = snake.direction;
    }

    public void chase() {

        if(snake.get(0).x == window.goal.x && snake.get(0).y == window.goal.y) {
            returnToPattern();
        } else if(snake.get(0).y > window.goal.y && !window.grid[0][snake.get(0).y] && snake.get(0).x == 1 && currentDirection != 1) {
            snake.direction = 3;
            why = "wdjmsde";
        } else if(snake.get(0).y == window.goal.y && freeway(window.goal) && snake.get(0).x < window.goal.x && currentDirection != 3) {
            snake.direction = 1;
            why = "asdfjgf";
        } else if(snake.get(0).x == window.goal.x && freeway(window.goal) && (currentDirection  == 1 || currentDirection == 3)
                && snake.get(0).y < window.goal.y && currentDirection != 0 && goalX()) {
            snake.direction = 2;
            why = "awesdfgj";
        }
    }

    public void returnToPattern() {
        if(currentDirection == 2) {
            if (snake.get(0).y % 2 == 0 && snake.get(0).x != window.grid.length - 1) {
                snake.direction = 1;
                why = "hjkhghc";
            } else if (snake.get(0).y % 2 == 1 && snake.get(0).x != 1) {
                snake.direction = 3;
                why = "plokyjghn";
            }
        }
    }

    public void pattern() {
        if(snake.get(0).x == 0 && snake.get(0).y == 0) {
            snake.direction = 1;
            why = "kyutjyrhtdg";
        }
        if((snake.get(0).x == 1 && snake.get(0).y != 0) || snake.get(0).x == window.grid.length-1) {
            if(snake.get(0).x == 1 && snake.get(0).y >= window.grid[0].length - 2) {

            } else {
                snake.direction = 2;
                why = "kuyjtygv";
            }
        }
        if(snake.get(0).x == 0 && snake.get(0).y != 0) {
            snake.direction = 0;
            why = "kjtyrhtdg";
        }
        if(currentDirection == 2 && (snake.get(0).x == 1 || snake.get(0).x == window.grid.length-1)) {
            if(snake.get(0).y % 2 == 0 && snake.get(0).x == 1) {
                snake.direction = 1;
                why = "ktjyhdfvds";
            } else if (snake.get(0).y % 2 == 1 && snake.get(0).x == window.grid.length - 1) {
                snake.direction = 3;
                why = "ydfgxrtydfg";
            }
        }
    }
    public boolean freeway(Window.Snake.Point goal) {
        int freeway = 0;
        if(currentDirection == 0) {
            for (int i = 0; i <= 1; i++) {
                for (int j = 1; j < window.grid.length; j++) {
                    if(window.grid[j][snake.get(0).y + i]) {
                        return false;
                    }
                }
            }
            return true;

            /*for (int i = 1; i < window.grid.length; i++) {
                if(!window.grid[i][snake.get(0).y]) {
                    freeway++;
                } else {
                    break;
                }
            }
            if(freeway == window.grid.length-1) {
                return true;
            }*/
        }
        if(currentDirection == 1 || currentDirection == 3) {
            for (int i = snake.get(0).y+1; i <= window.goal.y; i++) {
                if(!window.grid[snake.get(0).x][i]) {
                    freeway++;
                } else {
                    break;
                }
            }
            if(freeway >= goal.y - snake.get(0).y) {
                return true;
            }
        }
        return false;
    }

    public boolean goalX () {
        for (int i = 1; i < window.grid.length; i++) {
            if(window.grid[i][window.goal.y]) {
               return false;
            }
        }
        return true;
    }
}
