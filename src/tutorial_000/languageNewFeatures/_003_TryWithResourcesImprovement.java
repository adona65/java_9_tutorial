package tutorial_000.languageNewFeatures;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;

public class _003_TryWithResourcesImprovement {
	
	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		/*
		 * Prior to java 9, we cannot use any Resource which is declared outside the Try-With-Resources within try() block.
		 * In Java 9, if we have a resource which is already declared outside the Try-With-Resource Statement as final or effectively final, 
		 * then we can use it within try() block without any issues: 
		 */
		BufferedReader reader1 = new BufferedReader(new FileReader(_003_TryWithResourcesImprovement.class.getResource("test.txt").toURI().getPath()));
		try(reader1) {
			System.out.println(reader1.readLine());
		} catch (Exception e) {
			
		}
		
		System.out.println("-------------------------------------");
		
		/*
		 * Before java 9, we should have wrote :
		 */
		try(BufferedReader reader2 = new BufferedReader(new FileReader(_003_TryWithResourcesImprovement.class.getResource("test.txt").toURI().getPath()));) {
			System.out.println(reader2.readLine());
		} catch (Exception e) {
			
		}
	}
}
