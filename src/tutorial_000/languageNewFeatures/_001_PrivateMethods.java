package tutorial_000.languageNewFeatures;

public class _001_PrivateMethods {

	public static void main(String[] args) {
		/*
		 * In Java 8, we can provide method implementation in Interfaces using Default and Static methods,however we cannot create private methods 
		 * in Interfaces. Java 9 introduce private and private static methods in interfaces. Logically, these interface's private methods are useful 
		 * or accessible only within that interface only. We cannot access or inherit private methods from an interface to another interface or class.
		 */
		Card.java8StaticMethod();
		Card.callPrivateStaticMethod();
		
		CardImplementation cardImplementation = new CardImplementation();
		cardImplementation.callPrivateMethod();
	}
	
	private interface Card{
		static void java8StaticMethod() {
			System.out.println("I'm a java 8 new interface's static methode feature");
		}
		
		static void callPrivateStaticMethod() {
			java9StaticPrivateMethod();
		}
		
		private static void java9StaticPrivateMethod() {
			System.out.println("I'm a java 9 new interface's private static methode feature");
		}
		
		default void callPrivateMethod() {
			java9PrivateMethod();
		}
	
		private void java9PrivateMethod() {
			System.out.println("I'm a java 9 new interface's private methode feature");
		}
	}
	
	private static class CardImplementation implements Card {}
}
