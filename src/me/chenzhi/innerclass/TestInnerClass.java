package me.chenzhi.innerclass;

public class TestInnerClass {
	public static void main(String[] args) {
		OuterClass out = new OuterClass();
		out.method();
	}
}

class OuterClass {
	private int privateInt;
	public int pulbicInt;
	protected int protectedInt;
	
	public Object method() {
		final int finalInt = 0;
		// valid in JDK 8 and later, because notFinalInt is effective final, although it is declared as not final.
		int notFinalInt = 0;
		class LocalInnerClass {
			public void print() {
				// If you uncomment this assignment, complier error will happen,
				// because this will make notFinalInt not effective final.
				// notFinalInt = 1;
				System.out.println(privateInt); // can access private member of outer class
				System.out.println(pulbicInt); // can access public member of outer class
				System.out.println(protectedInt); // can access protected member of outer class
				System.out.println(finalInt); // can access final local variable of outer class's method
				System.out.println(notFinalInt); // why can access???
			}
		}
		Object in = new LocalInnerClass();
		return in;
	}
	
	// inner class
	private class InnerClass {
		// static private int staticInnerInt; // can not be static
		public void print() {
			System.out.println(privateInt); // can access private member of outer class
			System.out.println(pulbicInt); // can access public member of outer class
			System.out.println(protectedInt); // can access protected member of outer class
		}
	}
	
	public static class PublicInnerClass {
		static private int InnerInt; // can be static
		public void print() {
			// System.out.println(privateInt); // can not access private member of outer class
			// System.out.println(pulbicInt); // can not access public member of outer class
			// System.out.println(protectedInt); // can not access protected member of outer class
		}
	}
}