package tutorial_001.modules;

import fullName.provider.FullNameConstructor;

public class _003_ImpliedReadabilityBetweenModules {

	public static void main(String[] args) {
		/*
		 * WHAT IS READABILITY ? 
		 * 
		 * Let say a Module-1 directly depends on Module-2, then it is know as "Module-1 Reads Module-2". In other words, we can say that 
		 * "Module-2 is Readable by Module-1". This is know as Readability relationship between Module-1 and Module-2.
		 * 
		 * Look at "readability_graph.png" that describe this principle. We can see that "java.sql" module reads "java.xml", "java.base" and 
		 * "java.logging" modules.
		 * 
		 * WHAT IS ACCESSIBILITY ?
		 * 
		 * If Module-1 has Readability Relationship with Module-2, then Module-1 can "Access" all Public API of Module-2. It is know as Accessibility 
		 * Relationship between those two modules (remember that a Module can have only Public API or both Public and Private API). 
		 * 
		 * Both Readability and Accessibility concepts are the basis for achieving the two main Goals of Java 9 Module System :
		 * 
		 * - Reliable Configuration
		 * - Strong Encapsulation
		 * 
		 * We can define Readability and Accessibility Relationships between modules using the following concepts :
		 * 
		 *  - exports clause
		 *  - requires clause
		 *  - transitive modifier
		 *  
		 *  WHAT IS IMPLIED READABILITY ?
		 *  
		 *  If Module-1 reads on Module-2 and Module-2 reads on Module-3, then Module-1 reads Module-3. This kinds of Transitive Dependency is known 
		 *  as "Implied Readability" from Module-3 to Module-1. It is also known as Implied Dependency. To schematize it, let's assume we are in the
		 *  case drawn in the "implied_readability_graph.png" file :
		 *  
		 *   - "LastName" Module depends on "FirstName".
		 *   - "FullName" Module depends on Both Modules : "FirstName" and "LastName". 
		 *   
		 * That means there is a Transitive Dependency between these three modules : "FullName ==> LastName ==> FirstName". We have two ways to solce
		 * this dependency's problem : a "good" and a "bad" solution.
		 * 
		 * BAD APPROACH
		 * 
		 * This approach solves Transitive Dependency between modules without using "Implied Readability" :
		 "
		 
			module FirstName{ 
			  exports FirstName;
			}
			
			module LastName{
			   requires FirstName;
			   exports LastName;
			}
			
			module FullName{
			   requires FirstName;
			   requires LastName;
			   exports FullName;
			}
		 "
		 * Here "FullName" imports both "FirstName" and "LastName" modules. It is bad approach because "LastName" is already importing "FirstName" 
		 * Module, so we could have import "FirstName" into "FullName" through "Transitive Dependency" using "LastName". Instead of using it, 
		 * FullName is importing both modules. It works perfectly, however it’s not a recommended approach.
		 * 
		 * GOOD APPROACH
		 * 
		 * As just say, this approach solves Transitive Dependency between modules using "Implied Readability" Technique.
		 "
			 module FirstName{ 
			  exports FirstName;
			 }
			 
			module LastName{
			   requires transitive FirstName;
			   exports LastName;
			}
			
			module FullName{
			   requires LastName;
			   exports FullName;
			}
		 "
		 * Here "FullName" imports only LastName module, and gets "FirstName" automatically without importing it explicitly.
		 * 
		 * The following little example will show this functionnality :
		 */
		// Here, "FullName" requires "LastName" that requires transitive "FirstName". So "FullName" car use "FirstName" inside itself.
		// Look inside this three modules (especially "module-info.java" files) to understand it.
		FullNameConstructor fullName = new FullNameConstructor();
		System.out.println("Full name get through Transitive Dependency => " + fullName.getFullName());
		
		/*
		 * FEW IMPORTANT POINTS TO REMEMBER
		 * 
		 * - By definition, every module read itself.
		 * - Java 9 Module System does NOT support Cyclic Graphs.
		 * - Two or more Modules may have same package names. That means we can use same package names in two different Modules. But we
		 * 		CANNOT have two different modules with same name.
		 * - By default, all Java 9 Modules (JDK Modules or User Define Modules) depends on "java.base" Module (the "Mother of Java 9 Modules"
		 * 		as called in _000_ModulesIntroduction.java). 
		 */
	}

}
