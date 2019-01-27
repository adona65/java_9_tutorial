package tutorial_000.languageNewFeatures;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class _000_FactoryMethods {

	public static void main(String args[]) {
		/*
		 * In Java SE 8 and earlier Versions, if we want to create an empty Immutable or Unmodifiable List/Set, we should use Collections class utility 
		 * method : unmodifiableList()/unmodifiableSet(). Java 9 has introduced some convenient less-verbose factory methods to create Immutable List, 
		 * Set, Map and Map.Entry objects. This methods takes from 0 to undefined amount of parameters (as varargs).
		 * 
		 * LIST : List interfaces have of() methods to create an empty or no-empty Immutable List :
		 */
		List<String> immutableEmptyList = List.of();
		List<String> immutableList = List.of("one","two","three");
		
		System.out.println(immutableEmptyList);
		System.out.println(immutableList);
		
		/*
		 * Characteristics of Immutable List : Immutable Lists are not modifiable (unmodifiable collections). Their characteristics are the same in all
		 * java's versions :
		 * - We cannot add, modify and delete their elements. If we try to perform Add/Delete/Update operations on them, we will get an
		 *   UnsupportedOperationException.
		 * - They don’t allow null elements. If we try to create them with null elements, we will get NullPointerException.
		 * - They are serializable if all elements are serializable. 
		 */
		
		System.out.println("=====================================");
		
		/*
		 * SET : Set interfaces also have of() methods to create an empty or no-empty Immutable Set. Characteristics of a Immutable Set are similar to 
		 * Immutable List. 
		 */
		Set<String> immutableEmptySet = Set.of();
		Set<String> immutableSet = Set.of("one","two","three");
		
		System.out.println(immutableEmptySet);
		System.out.println(immutableSet);
		
		System.out.println("=====================================");
		
		/*
		 * MAP : Map has two different "of()" methods :
		 * - of() method to create an Immutable Map object
		 * - ofEntries() method to create an Immutable Map.Entry object
		 * 
		 * Characteristics of a Immutable Map are similar to Immutable List.
		 */
		Map<Integer, String> immutableEmptyMap = Map.of();
		Map<Integer, String> immutableMap = Map.of(1, "one", 2, "two", 3, "three");
		
		System.out.println(immutableEmptyMap);
		System.out.println(immutableMap);
		
		System.out.println("-------------------------------------");
		
		/*
		 * We can use Map.entry() utility method to create an Immutable Map.Entry using given key and value pairs. It is used as part of Map.ofEntries() 
		 * method to create an Immutable Map.
		 */
		
		Map<Integer,String> immutableEmptyMapFromEntries = Map.ofEntries();
		Map.Entry<Integer,String> immutableEntry = Map.entry(1,"one");
		Map<Integer,String> immutableMapFromEntries = Map.ofEntries(immutableEntry,Map.entry(2,"two"), Map.entry(3,"three"));
		
		System.out.println(immutableEmptyMapFromEntries);
		System.out.println(immutableEntry);
		System.out.println(immutableMapFromEntries);
		
	}
}
