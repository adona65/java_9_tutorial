package tutorial_000.languageNewFeatures;

import java.util.List;
import java.util.Optional;

public class _007_OptionalClassImprovements {

	public static void main(String[] args) {
		/*
		 * Java SE 9 has introduced the following three methods to improve Optional functionality :
		 * - stream()
		 * - ifPresentOrElse()
		 * - or()
		 * 
		 * Stream() : If a value is present in the given Optional object, stream() method returns a sequential Stream with that value. 
		 * Otherwise, it returns an Empty Stream. 
		 */
		
		Optional<List<String>> opt = Optional.of(List.of("one","two","three"));
		
		opt.stream().forEach(l -> l.stream().filter(s -> !s.contains("w")).forEach(System.out::println));
		
		System.out.println("=====================================");
		
		/*
		 * IfPresentOrElse() : If a value is present, performs the given action with the value, otherwise performs the given empty-based action. 
		 */
		 Optional.of(4).ifPresentOrElse( x -> System.out.println("Result found: " + x), () -> System.out.println("Not Found.")); // Result found: 4
		 
		 Optional.empty().ifPresentOrElse( x -> System.out.println("Result found: " + x), () -> System.out.println("Not Found.")); // Not found.
		 
		 System.out.println("=====================================");
		 
		 /*
		  * or() : if a value is present in the optional, returns an Optional describing the value, otherwise returns an Optional produced by the 
		  * supplier passed as parameter.
		  */
		 System.out.println(Optional.of("Rams").or(() -> Optional.of("No Name"))); // Optional[Rams]

		 System.out.println(Optional.empty().or(() -> Optional.of("No Name"))); // Optional[No Name]
	}
}
