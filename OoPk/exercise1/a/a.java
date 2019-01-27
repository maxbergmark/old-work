package a;
class a {
	static a a = new a(5);
	a(int a){}
	a a(int a) {
		return new a(a);
	}
	void a(long a) {
		this.a = new a((int)a); 
	}
	a a() {
		return a;
	}
	a a(a a) {
		return a.a.a.a.a.a.a.a.a.a.a.a.a();
	}
	public static void main(String[] args) {
		a a = new a(3);
		a.a.a(3L);
		System.out.println(a);
		System.out.println(a.a());
		System.out.println(a.a(a));
		System.out.println(a.a.a.a);
	}
}