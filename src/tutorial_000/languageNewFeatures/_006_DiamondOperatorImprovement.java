package tutorial_000.languageNewFeatures;

public class _006_DiamondOperatorImprovement {

	public static void main(String[] args) {
		/*
		 * Diamond operator was introduced in java 7 to make code more readable but it could not be used with Anonymous inner classes. In java 9, it
		 * can be used with anonymous class as well to simplify code and improves readability.
		 */

		// <> instead of <Integer> prior to Java 9
		Handler<Integer> intHandler = new Handler<>(1) {
			@Override
			public void handle() {
				System.out.println(content);
			}
		};
		
		intHandler.handle();
		
		// <> instead of prior <Number> to Java 9
		Handler<? extends Number> intHandler1 = new Handler<>(2) {
			@Override
			public void handle() {
				System.out.println(content);
			}
		};
		
		intHandler1.handle();
		
		// <> instead of prior <Object> to Java 9
		Handler<?> handler = new Handler<>("test") {
			@Override
			public void handle() {
				System.out.println(content);
			}
		};

		handler.handle();    
	}  
}

abstract class Handler<T> {
	public T content;

	public Handler(T content) {
		this.content = content; 
	}

	abstract void handle();
}
