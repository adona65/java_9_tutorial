package tutorial_000.languageNewFeatures;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class _008_StreamApiImprovements {
	
	public static void main(String[] args) {
		/*
		 * Java SE 9 has added the followng four useful new methods to java.util.Stream interface :
		 * - dropWhile
		 * - takeWhile
		 * - iterate
		 * - ofNullable
		 * 
		 * takeWhile() : Returns, if this stream is ordered, a stream consisting of the longest prefix of elements taken from this stream that 
		 * match the given predicate. Otherwise returns, if this stream is unordered, a stream consisting of a subset of elements taken from 
		 * this stream that match the given predicate.
		 */
		
		// Ordered stream : returns first three elements which matches our Predicate. 
		Stream.of(1,2,3,4,5,6,7,8,9,10).takeWhile(x -> x < 4).forEach(a -> System.out.println(a));
		
		System.out.println("-------------------------------------");
		
		// Unordered stream : returns first two elements which matches our Predicate.
		Stream.of(1,2,4,5,3,6,7,8,9,10).takeWhile(x -> x < 4).forEach(a -> System.out.println(a));
		
		/*
		 * This examples shows that takeWhile() returns all prefixed elements until they match Predicate condition. When that Predicate returns false 
		 * for first element, then it stops evaluation and returns that subset elements. That Predicate is evaluated until that returns false for first 
		 * time.
		 */
		
		System.out.println("=====================================");
		
		/*
		 * dropwhile() : Returns, if this stream is ordered, a stream consisting of the remaining elements of this stream after dropping the longest 
		 * prefix of elements that match the given predicate. Otherwise returns, if this stream is unordered, a stream consisting of the remaining 
		 * elements of this stream after dropping a subset of elements that match the given predicate.
		 */
		
		// Ordered stream : drop first three elements which matches our Predicate and returns rest of the elements into resulted Stream.
		Stream.of(1,2,3,4,5,6,7,8,9,10).dropWhile(x -> x < 4).forEach(a -> System.out.println(a));
		
		System.out.println("-------------------------------------");
		
		// Unordered stream : drop first two elements only which matches our Predicate and returns rest of the elements into resulted Stream.
		Stream.of(1,2,4,5,3,6,7,8,9,10).dropWhile(x -> x < 4).forEach(a -> System.out.println(a));
		
		/*
		 * This examples shows that dropWhile() first drops all prefixed elements until they match Predicate condition. When that Predicate returns 
		 * false for first element, then it stops evaluation and returns the rest of subset elements into resulted Stream. 
		 */
		
		System.out.println("=====================================");
		
		/*
		 * iterate() : returns a Stream of elements which start with initialValue (first parameter), matches the Predicate (2nd parameter) and generate 
		 * next element using 3rd parameter. It is similar to for-loop: First parameter is init value, next parameter is condition and final parameter 
		 * is to generate next element (for instance, increment or decrement operation).
		 */
		IntStream.iterate(2, x -> x < 20, x -> x * x).forEach(System.out::println);
		
		System.out.println("=====================================");
		
		/*
		 * ofNullable() : returns a sequential Stream containing a single element, if non-null, otherwise returns an empty Stream.
		 */
		Stream.ofNullable(1).forEach(System.out::println);
		
		Stream.ofNullable(null).forEach(System.out::println); // Print nothing since the strem is empty.
	}
}
