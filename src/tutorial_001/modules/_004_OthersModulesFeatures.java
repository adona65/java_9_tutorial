package tutorial_001.modules;

public class _004_OthersModulesFeatures {
	/*
	 * We will list here some module's features that were'nt looked in previous tutorial's classes.
	 * 
	 * MODULE DESCRIPTOR
	 * 
	 * The module descriptor ("module-info.java" file) is also used to describe "Reflection Permissions", ie  explicitly allows other classes 
	 * to use reflection to access the private members of a package. This aspect will be describe later in this class.
	 * 
	 * Beware to the content of the "module-info.java" file. Indeed, by default all packages are module private. So all packages that aren't
	 * quoted as exported in the module descriptor won't be public. The same is true for reflection.
	 * 
	 * MODULE TYPES
	 * 
	 * Besides the already described modules (ie JRE/JDK's modules and our own modules), we can defined two other "sorts" of modules :
	 * 
	 * - Automatic Modules : We can include unofficial modules by adding existing JAR files to the module path. The name of the module will be 
	 * 		derived from the name of the JAR. Automatic modules will have full read access to every other module loaded by the path.
	 * - Unnamed Module : When a class or JAR is loaded onto the classpath, but not the module path, it's automatically added to the unnamed 
	 * 		module. It's a catch-all module to maintain backward compatibility with previously-written Java code.
	 * 
	 * DISTRIBUTION
	 * 
	 * Modules can be distributed one of two ways: 
	 * 
	 * - As a JAR file
	 * - As an â€œexplodedâ€� compiled project.
	 * 
	 * We can create multi-module projects comprised of a "main application" and several library modules. We have to be careful though because we 
	 * can only have one module per JAR file. When we set up our build file, we need to make sure to bundle each module in our project as a separate 
	 * jar.
	 * 
	 * DEFAULT MODULES
	 * 
	 * As said previoulsy (in other classes), the JDK has now a new structure. We can see what a modules list of it by typing into the command line :
	 * 
	 * java --list-modules
	 * 
	 * These modules are split into four major groups: java, javafx, jdk, and Oracle :
	 * - java modules are the implementation classes for the core SE Language Specification.
	 * - javafx modules are the FX UI libraries.
	 * - jdk modules are anything needed by the JDK itself.
	 * - Oracle hold anything that is Oracle-specific.
	 * 
	 * REQUIRES CLAUSE
	 * 
	 * The "requires" clause used in module descriptor to import other modules supply both a runtime and a compile-time dependency's access to this 
	 * modules.
	 * 
	 * REQUIRES STATIC CLAUSE
	 * 
	 * Sometimes, a part of our application references another module, but users of our library will never want to use this another module. In these 
	 * cases, we want to use an optional dependency. By using the requires static directive, we create a compile-time-only dependency :
	 * 
	 * "requires static module.name;"
	 * 
	 * EXPORTS TO CLAUSE
	 * 
	 * Exports to clause is usefull to restrict which modules have access to our APIs. Similarly to the exports directive, we declare a package as 
	 * exported, but we also list which modules are allowed to import this package :
	 * 
	 * "exports com.my.package to com.specific.package1, com.another.package2;"
	 * 
	 * USES CLAUSE
	 * 
	 * A service is an implementation of a specific interface or abstract class that can be consumed by other classes. We designate the services our 
	 * module consumes with the uses directive. Bewrae that the class name we use is either the interface or abstract class of the service, not the 
	 * implementation class :
	 * 
	 * "uses class.name;"
	 * 
	 * We should note here that there's a difference between the requires directive and the uses directive. We might require a module that provides a 
	 * service we want to consume, but that service may implements an interface from one of its transitive dependencies. Instead of forcing our module 
	 * to require all transitive dependencies just in case, we use the uses directive to add the required interface to the module path.
	 * 
	 * PROVIDES WITH CLAUSE
	 * 
	 * A module can also be a service provider that other modules can consume. The first part of the directive is the provides keyword. Here is where we 
	 * put the interface or abstract class name. Next, we have the with directive where we provide the implementation class name that either implements 
	 * the interface or extends the abstract class :
	 * 
	 * "provides MyInterface with MyInterfaceImpl;"
	 * 
	 * OPEN CLAUSE
	 * 
	 * We now have to explicitly grant permission for other modules to use reflection upon our classes. If we want to continue to allow full reflection 
	 * as older versions of Java did, we can simply open the entire module up by using open word before module declaration :
	 * 
	 * "open module my.module {}"
	 * 
	 * OPENS CLAUSE
	 * 
	 * If we need to allow reflection of private types, but we don't want all of our code being exposed, we can use the opens directive to expose specific 
	 * packages. Remember that this will open the package up to the entire world, so make sure that is what you want :
	 * 
	 * "opens com.my.package;"
	 * 
	 * OPENS TO CLAUSE
	 * 
	 * We can selectively open our packages to a pre-approved list of modules. In this case, we can use the opens to directive:
	 * 
	 * "opens com.my.package to moduleOne, moduleTwo;"
	 * 
	 * COMMAND LINE OPTIONS
	 * 
	 * In case of need,  we can use a list of command lines to perform actions on our modules :
	 * 
	 * - module-path : We use the -module-path option to specify the module path. This is a list of one or more directories that contain your modules.
	 * - add-reads : Instead of relying on the module declaration file, we can use the command line equivalent of the requires directive : -add-reads.
	 * - add-exports : Command line replacement for the exports directive.
	 * - add-opens : Replace the open clause in the module declaration file.
	 * - add-modules : Adds the list of modules into the default set of modules
	 * - list-modules : Prints a list of all modules and their version strings
	 * - patch-module : Add or override classes in a modules
	 * - illegal-access=permit|warn|deny : Either relax strong encapsulation by showing a single global warning, shows every warning, or fails with 
	 * 		errors. The default is permit.
	 * 
	 * VISIBILITY
	 * 
	 * Beware : A lot of libraries depend on reflection to work their magic (JUnit and Spring come to mind). By default in Java 9, we will only have 
	 * access to public classes, methods, and fields in our exported packages. Even if we use reflection to get access to non-public members and call 
	 * setAccessible(true), we won't be able to access these members. We can use the open, opens, and open to options to grant runtime-only access for 
	 * reflection. Note, this is runtime-only !
	 * 
	 *  If we must have access to a module for reflection, and we're not the owner of that module (i.e., we can't use the open to directive), then it's 
	 *  possible to use the command line -add-opens option to allow own modules reflection access to the locked down module at runtime. The only caveat 
	 *  here is that you need to have access to the command line arguments that are used to run a module for this to work.
	 *
	 * ADDING MODULES TO THE UNNAMED MODULE
	 * 
	 * The unnamed module concept is similar to the default package. Therefore, it's not considered a real module, but can be viewed as the default 
	 * module. If a class is not a member of a named module, then it will be automatically considered as part of this unnamed module. Sometimes, to 
	 * ensure specific platform, library, or service-provider modules in the module graph, we need to add modules to the default root set. For example, 
	 * when we try to run Java 8 programs as-is with Java 9 compiler we may need to add modules.
	 * 
	 * In general, the option to add the named modules to the default set of root modules is -add-modules <module>(,<module>)* where <module> is a module 
	 * name. For example, to provide access to all java.xml.bind modules the syntax would be :
	 * 
	 * "--add-modules java.xml.bind"
	 * 
	 * To use this in Maven, we can embed the same to the maven-compiler-plugin :
	 "
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-compiler-plugin</artifactId>
			<version>3.8.0</version>
			<configuration>
				<source>9</source>
				<target>9</target>
				<compilerArgs>
					<arg>--add-modules</arg>
					<arg>java.xml.bind</arg>
				</compilerArgs>
			</configuration>
		</plugin>
	 "
	 */

}
