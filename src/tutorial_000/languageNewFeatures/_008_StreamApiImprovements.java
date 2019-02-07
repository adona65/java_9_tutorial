package tutorial_000.languageNewFeatures;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class _008_StreamApiImprovements {
	
	public static void main(String[] args) {
		/*
		 * Java SE 9 has added the following four useful new methods to java.util.Stream interface :
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
		
		/*
		 * Furthermore, Java 9 add two new collectors :
		 * - Collectors.filtering
		 * - Collectors.flatMapping
		 * 
		 * FILTERING
		 * 
		 * The Collectors.filtering is similar to the Stream filter(). It is used for filtering input elements, but for different scenarios. The Stream’s 
		 * filter() is used in the stream chain whereas the filtering is a Collector which was designed to be used along with groupingBy.
		 * 
		 * With Stream’s filter(), the values are filtered first and then grouped. In this way, the values which are filtered out are gone and there is no 
		 * trace of it. If we need a trace then we would need to group first and then apply filtering, which actually the Collectors.filtering does.
		 * 
		 * The Collectors.filtering takes a function for filtering the input elements and a collector to collect the filtered elements :
		 */
		List<Integer> numbers = List.of(1, 2, 3, 5, 5);
		
		Map<Integer, Long> result = numbers.stream()
			      .filter(val -> val > 3)
			      .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
		
		System.out.println(result);
		
		result = numbers.stream()
			      .collect(Collectors.groupingBy(i -> i, Collectors.filtering(val -> val > 3, Collectors.counting())));
		
		System.out.println(result);
		
		System.out.println("=====================================");
		
		/*
		 * FLATMAPPING
		 * 
		 * The Collectors.flatMapping is similar to Collectors.mapping but has a more fine-grained objective. Both takes a function and a collector where the 
		 * elements are collected, but flatMapping() function accepts a Stream of elements which is then accumulated by the collector.
		 * 
		 * Collectors.flatMapping lets us skip intermediate collection and write directly to a single container which is mapped to that group defined by the 
		 * Collectors.groupingBy.
		 */
		Blog blog1 = new Blog("1", List.of("Nice", "Very Nice"));
	    Blog blog2 = new Blog("2", List.of("Disappointing", "Ok", "Could be better"));
	    
	    List<Blog> blogs = List.of(blog1, blog2);
	    
	    Map<String,  List<List<String>>> authorComments1 = blogs.stream()
	    	     .collect(Collectors.groupingBy(Blog::getAuthorName, Collectors.mapping(Blog::getComments, Collectors.toList())));
	    
	    System.out.println(authorComments1.size());
	    System.out.println(authorComments1.get("1"));
	    System.out.println(authorComments1.get("2"));
	    
	    System.out.println("-------------------------------------");
	    
	    Map<String, List<String>> authorComments2 = blogs.stream()
	    	      .collect(Collectors.groupingBy(Blog::getAuthorName, Collectors.flatMapping(blog -> blog.getComments().stream(), Collectors.toList())));
	    
	    System.out.println(authorComments2.size());
	    System.out.println(authorComments2.get("1"));
	    System.out.println(authorComments2.get("2"));
	    
	    /*
	     * The Collectors.mapping maps all grouped author’s comments to the collector’s container i.e. List, whereas this intermediate collection is removed 
	     * with flatMapping as it gives a direct stream of the comment list to be mapped to the collector’s container.
	     */
	}
}
