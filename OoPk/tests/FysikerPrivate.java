
public class FysikerPrivate extends HumanPrivate {
	private int a;
	private FysikerPrivate() {
		this.a = 5;
	}
	public static FysikerPrivate newFysiker() {
		return new FysikerPrivate();
	}
}