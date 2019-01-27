class TriangleTester{
  public static boolean isTriangle(int a, int b, int c){
  boolean bo = true;
  if (a == 0 || b == 0 || c == 0) {
    return false;
  }
  if (a+b <= c) {
    bo = false;
  }
  if (b+c <= a) {
    bo = false;
  }
  if (a+c <= b) {
    bo = false;
  }
  return bo;
  }

  public static void main(String[] args) {
    for (int a = 0; a < 10; a++) {
      for (int b = 0; b < 10; b++) {
       for (int c = 0; c < 10; c++) {
          System.out.println(a + "   " + b + "   " + c + "   " + isTriangle(a, b, c));
        }
      }
    }
  }
}