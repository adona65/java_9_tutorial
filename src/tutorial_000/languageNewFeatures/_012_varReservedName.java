package tutorial_000.languageNewFeatures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _012_varReservedName {
	public static void main(String[] args) {
		/*
		 * Beware : The following examples show functionnalitys incubated in Java 9, but really implemented since Java 10. So var word will compile 
		 * only for java 10+ compliance.
		 * 
		 * Java SE 9 is coming with â€œvarâ€? to define and initialise local variables. The main reasons of this change in the Java Language are :
		 * - To improve â€œType Inferenceâ€?.
		 * - To reduce verbosity in the code.
		 * 
		 * For example, let's say we want define a Map :
		 */
		Map<String, List<Integer>> phoneBook = new HashMap<>();
		
		// To fix this verbosity issues, we may use "var". Here Java 9 Compiler with infer the type of phoneBook reference as HashMap<String, List<Int>>. 
		// The type is inferred based on the type of the initializer. If there is no initializer, the initializer is the null literal.
		var nonVerbosePhoneBook = new HashMap<String, List<Integer>>();
		
		var list = List.of("one","two","three");
		
		// It also works whith methods returning a type. Here Java 9 Compiler infers the type of list as Stream<String>.
		var stream = list.stream(); 
		stream.forEach(System.out::println);
		
		System.out.println("-------------------------------------");
		
		// It also works fwith for loops :
		for(var item : list) {
			System.out.println(item);	
		}
		
		/*
		 * In Java SE 9, â€œvarâ€? is NOT a keyword. It is a Reserved Type name. That means if our existing code uses var as a variable name, method name, or 
		 * package name, then they are NOT effect with this change. However any class name or interface will affect this change. It is very rare case and 
		 * not recommended approach to use â€œvarâ€? as a class or interface name so this change does not effect existing code base.
		 */
	}
}
