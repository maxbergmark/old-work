
import java.util.Arrays;


public class Model {
    Child[] children;
    boolean[][] answer;
    final int elitismNumber;

    public Model (int numOfChildren, boolean[][] answer, int elitism) {
        children = new Child[numOfChildren];
        this.answer = answer;
        elitismNumber = elitism;
        for (int i = 0; i < children.length ; i++) {
            children[i] = new Child();
        }
        Arrays.sort(children);
    }

    public static boolean[][] generateAnswer(int xsize, int ysize) {
        boolean[][] array = new boolean[xsize][ysize];
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                if (Math.random() < .5) {
                    array[i][j] = true;
                }
            }
        }
        return array;
    }
    public void nextGen() {
        crossOver();
        mutate();
        for (int i = 0; i < children.length; i++) {
            children[i].evaluate();
        }
        Arrays.sort(children);
    }

    public void mutate() {
        for (int i = elitismNumber; i < children.length; i++) {
            children[i].mutate();
        }
    }

    public void crossOver() {
        int lastIndex = elitismNumber;
        for (int i = 0; lastIndex < children.length; i++) {
            for (int j = i+1; lastIndex < children.length && j < elitismNumber; j++) {
                children[lastIndex] = children[i].crossOver(children[j]);
                lastIndex++;
            }
        }
    }

    public class Child implements Comparable<Child>{
        boolean[][] array;
        int fitness;

        public Child () {
            array = new boolean[answer.length][answer[0].length];
            for (int i = 0; i < answer.length; i++) {
                for (int j = 0; j < answer[0].length; j++) {
                    if (Math.random() < .5) {
                        array[i][j] = true;
                    }
                }
            }
            evaluate();
        }

        public void evaluate() {
            int fit = 0;
            for (int i = 0; i < answer.length; i++) {
                for (int j = 0; j < answer[0].length; j++) {
                    if(array[i][j] == answer[i][j]){
                        fit++;
                    }
                }
            }
            fitness = fit;
        }

        public void mutate () {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {
                    if(Math.random() < 0.0001) {
                        array[i][j] = !array[i][j];
                    }
                }
            }
        }

        public Child crossOver (Child parent) {
            Child child = new Child();
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {
                    if (Math.random() < 0.5) {
                        child.array[i][j] = array[i][j];
                    } else {
                        child.array[i][j] = parent.array[i][j];
                    }
                }
            }
            return child;
        }

        public String toString() {
            String string = "FITNESS: " + fitness + "\r\n";
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {
                    if(array[i][j]) {
                        string += "TRUE ";
                    } else {
                        string += "FALS ";
                    }
                }
                string += "\n\r";
            }
            return string;
        }


        @Override
        public int compareTo(Child other) {
            return -(Integer.compare(this.fitness, other.fitness));
        }
    }
}
