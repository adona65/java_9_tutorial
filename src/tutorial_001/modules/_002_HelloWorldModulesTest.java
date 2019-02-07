package tutorial_001.modules;

// This package can be imported because it's exported from by the "java_9_helloProvider" module, which is set 
// as requires in the current module.
import helloProvider.hello.HelloWorld;

/*
 * "The type nonExported.privateHello.PrivateWorld is not accessible" in the IDE. Still importing it would lead
 * to an "Exception in thread "main" java.lang.Error: Unresolved compilation problem" exception.
 */
// import nonExported.privateHello.PrivateWorld;

public class _002_HelloWorldModulesTest {

	public static void main(String[] args) {
		/*
		 * In this example, we will see how our current module can access import functionalities from other modules and use them. Here, we wan't
		 * call the HelloWorld.sayHelloWorld() method, contained in the "java_9_helloProvider.hello" package from the "java_9_helloProvider" module.
		 * To do so, we must :
		 * 
		 * - Be sure that the "java_9_helloProvider" module well export the package "helloProvider.hello" (it's "module-info.java" file must 
		 * 		contains "exports helloProvider.hello;").
		 * - Import this module in our current module (it's "module-info.java" file contains "requires java_9_helloProvider;").
		 * - Be sure the "java_9_helloProvider" module is contained in the Modulepath of the current module (easy to set with modern IDE).
		 */
		
		HelloWorld hello = new HelloWorld();
	    System.out.println(hello.sayHelloWorld());
	    System.out.println(hello.forbiddenPrivateworld());
	    
	    /*
	     * Trying to use the PrivateWorld class would lead to an "Unresolved compilation problems: PrivateWorld cannot be resolved to a type" 
	     * exception because importing it is impossible.
	     */
	    // PrivateWorld privateworld = new PrivateWorld();
	}

}
