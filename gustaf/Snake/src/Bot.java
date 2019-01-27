
public class Bot {
    Window window;
    boolean lastTurn, saveProtocol;
    int currentDirection;

    public Bot(Window window) {
        this.window = window;
    }

    public void updateBot() {
        chase();
        collision();
        currentDirection = window.snake.direction;
    }

    public void chase() {
        if(window.goal.y == window.snake.get(0).y) {
            if(window.goal.x < window.snake.get(0).x && currentDirection != 3) {
                System.out.println("CHASE: " + 3);
                safeDirect(3);
            } else if (window.goal.x > window.snake.get(0).x && currentDirection != 1) {
                System.out.println("CHASE: " + 1);
                safeDirect(1);
            }
        } else if(window.goal.x == window.snake.get(0).x && currentDirection != 0) {
            if(window.goal.y < window.snake.get(0).y) {
                System.out.println("CHASE: " + 0);
                safeDirect(0);
            } else if (window.goal.y > window.snake.get(0).y && currentDirection != 2) {
                System.out.println("CHASE: " + 2);
                safeDirect(2);
            }
        }
    }
    public void lastTurn(int turn) {
        if(currentDirection == 0) {
            if (turn == 1) {
                lastTurn = true;
            } else if (turn == 3) {
                lastTurn = false;
            }
        } else if(currentDirection == 1) {
            if (turn == 2) {
                lastTurn = true;
            } else if (turn == 0) {
                lastTurn = false;
            }
        } else if(currentDirection == 2) {
            if (turn == 3) {
                lastTurn = true;
            } else if (turn == 1) {
                lastTurn = false;
            }
        } else if(currentDirection == 3) {
            if (turn == 0) {
                lastTurn = true;
            } else if (turn == 2) {
                lastTurn = false;
            }
        }
    }

    public void collision() {
        if(!window.snake.checkSnake(window.snake.get(0))) {
            Window.Snake.Point point = window.snake.get(0).move(window.snake.direction);
            if(point.x >= 0 && point.x <= window.grid.length - 1 && point.y >= 0 && point.y <= window.grid[0].length - 1) {
                if(lastTurn) {
                    System.out.println("LEFT");
                    safeDirect((window.snake.direction + 3)%4);
                } else {
                    System.out.println("RIGHT");
                    safeDirect((window.snake.direction + 1)%4);
                }
            } else if(currentDirection == 1 || currentDirection == 3) {
                int up = freeway(window.snake.get(0), 0);
                int down = freeway(window.snake.get(0), 2);

                if(up < down) {
                    safeDirect(2);
                    System.out.println("DIRECT 2: " + "UP: " + up + " DOWN: " + down);
                } else if (up > down){
                    safeDirect(0);
                    System.out.println("DIRECT 0: " + "UP: " + up + " DOWN: " + down);
                } else {
                    if (lastTurn) {
                        safeDirect((window.snake.direction + 3)%4);
                    } else {
                        safeDirect((window.snake.direction + 1)%4);
                    }
                }
            } else if(currentDirection == 0 || currentDirection == 2) {
                int right = freeway(window.snake.get(0), 1);
                int left = freeway(window.snake.get(0), 3);
                if(right < left) {
                    safeDirect(3);
                    System.out.println("DIRECT 3: " + "RIGHT: " + right + " LEFT: " + left);
                } else if (right > left){
                    safeDirect(1);
                    System.out.println("DIRECT 1: " + "RIGHT: " + right + " LEFT: " + left);
                } else {
                    if (lastTurn) {
                        safeDirect((window.snake.direction + 3)%4);
                    } else {
                        safeDirect((window.snake.direction + 1)%4);
                    }
                }
            }
        }
        if (!window.snake.checkSnake(window.snake.get(0)) || saveProtocol) {
            if(!window.snake.checkSnake(window.snake.get(0))) {
                System.out.println("SAVE 1");
                safeDirect((window.snake.direction + 2) % 4);
                saveProtocol = true;
            } else if (saveProtocol) {
                System.out.println("SAVE 2");
                saveProtocol = false;
                if(lastTurn) {
                    safeDirect((currentDirection + 1)%4);
                } else {
                    safeDirect((currentDirection + 3)%4);
                }
            }
        }
        if (!window.snake.checkSnake(window.snake.get(0))) {
            safeDirect(currentDirection);
        }
    }

    public int freeway(Window.Snake.Point head, int direction) {
        int freeway = 0;
        if(direction == 0) {
            for (int i = head.y-1; i >= 0; i--) {
                if(!window.grid[head.x][i]) {
                    freeway++;
                } else {
                    break;
                }
            }
            return freeway;
        }
        if(direction == 1) {
            for (int i = head.x+1; i < window.grid.length; i++) {
                if(!window.grid[i][head.y]) {
                    freeway++;
                } else {
                    break;
                }
            }
            return freeway;
        }
        if(direction == 2) {
            for (int i = head.y+1; i < window.grid.length; i++) {
                if(!window.grid[head.x][i]) {
                    freeway++;
                } else {
                    break;
                }
            }
            return freeway;
        }
        if(direction == 3) {
            for (int i = head.x-1; i >= 0; i--) {
                if(!window.grid[i][head.y]) {
                    freeway++;
                } else {
                    break;
                }
            }
            return freeway;
        }
        System.out.println("CAWK");
        return 0;
    }

    public void safeDirect(int direct) {
        lastTurn(direct);
        if((currentDirection + 2)%4 != direct) {
            window.snake.direction = direct;
        } else if (direct == 0 || direct == 2) {
            if(window.snake.get(0).x < window.grid.length/2) {
                window.snake.direction = 1;
            }
            if(window.snake.get(0).x > window.grid.length/2) {
                window.snake.direction = 3;
            }
        } else if (direct == 1 || direct == 3) {
            if(window.snake.get(0).y <= window.grid[0].length/2) {
                window.snake.direction = 2;
            }
            if(window.snake.get(0).y > window.grid[0].length/2) {
                window.snake.direction = 0;
            }
        }
    }
}
