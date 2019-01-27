import java.util.ArrayList;

public class Composite extends MyComponent {

	public String name;
	public ArrayList<Composite> children;

	public Composite(String name) {
		super(name);
		this.children = new ArrayList<>();
	}

	public void add(Composite child) {
		children.add(child);
	}

	public ArrayList<Composite> getChildren() {
		return children;
	}



}