package tutorial_000.languageNewFeatures;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class _014_ObjectsAdditions {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		/*
		 * The java.util.Objects class has been part of Java since version 1.7. This class provides static utility methods for objects which can 
		 * be used to perform some of the everyday tasks like checking for equality, null checks, etc. Java 9 introduced new usefull methods to
		 * it.
		 * 
		 * requireNonNullElse() : This method accepts two parameters and returns the first parameter if it is not null, the second parameter otherwise. 
		 * If both parameters are null, it throws NullPointerException.
		 */
		List<String> aList = Objects.<List>requireNonNullElse(null, Collections.EMPTY_LIST);
		System.out.println(aList);
		
		
		List<String> aList2 = Objects.<List>requireNonNullElse(List.of("item1", "item2"), Collections.EMPTY_LIST);
		System.out.println(aList2);
		
		try {
			Objects.<List>requireNonNullElse(null, null);
		} catch(NullPointerException e) {
			System.out.println("NullPointerException throwed");
		}
		
		System.out.println("=====================================");
		
		/*
		 * requireNonNullElseGet() : This method is similar to requireNonNullElse, except that the second parameter, is a java.util.function.Supplier 
		 * interface which allows a lazy instantiation of the provided collection. The Supplier implementation is responsible for returning a non-null 
		 * object.
		 */
		List<String> aList4 = Objects.<List>requireNonNullElseGet(null, () -> List.of("item1", "item2"));
		System.out.println(aList4);
		
		/*
		 * checkIndex() : This method is used for checking if the index is within the given length. It returns the index if 0 <= index < length. Otherwise, 
		 * it throws an IndexOutOfBoundsException.
		 */
		 int length = 5;
		 
		 System.out.println(Objects.checkIndex(4, length));
		 
		 try {
			 Objects.checkIndex(5, length);
		 } catch(IndexOutOfBoundsException e) {
			 System.out.println("IndexOutOfBoundsException throwed");
		 }
		 
		 System.out.println("=====================================");
		 
		 /*
		  * checkFromToIndex() : This method is used to check if the given sub range formed by [fromIndex, toIndex) is within the range formed by [0, length). 
		  * If the sub-range is valid, then it returns the lower bound.
		  * 
		  * Note : In mathematics, a range is represented in the form of [a, b) indicates the range is inclusive of a and exclusive of b. [ and ] state that 
		  * the number is included and ( and ) state that the number is excluded.
		  */
		 System.out.println(Objects.checkFromToIndex(2, length, length));
		 
		 try {
			 Objects.checkFromToIndex(2, 7, length);
		 } catch(IndexOutOfBoundsException e) {
			 System.out.println("IndexOutOfBoundsException throwed");
		 }
		 
		 System.out.println("=====================================");
		 
		 /*
		  * checkFromIndexSize() : This method is similar to checkFromToIndex except that instead of providing the upper bound of the sub-range we provide the 
		  * size and the lower bound of the sub-range. The sub-range, in this case, is [fromIndex, fromIndex + size) and this method checks that the sub range 
		  * is within the range formed by [0, length):
		  */
		 System.out.println(Objects.checkFromIndexSize(2, 3, length));
		 
		 try {
			 Objects.checkFromIndexSize(2, 6, length);
		 } catch(IndexOutOfBoundsException e) {
			 System.out.println("IndexOutOfBoundsException throwed");
		 }
	}
}
