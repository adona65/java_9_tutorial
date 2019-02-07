package tutorial_001.modules;

public class _001_ModulesBasics {
		/*
		 * In this section, we will talk about two module's important concepts : module itself, and module descriptor.
		 * 
		 * MODULE'S BASICS
		 * 
		 * We should remember the following important points about Java 9 Module :
		 * 
		 * - Each module has a unique Name.
		 * - Each module has some description in a source file called "module-info.java" (also known as "Module Descriptor").
		 * - This module descriptor is a Java file. It is not an XML, text or properties file. 
		 * - By convention, we should use same name "module-info.java" for module descriptor.
		 * - By convention, module descriptor file is placed in the top level directory of a module.
		 * - Each Module should have one and only one module descriptor ("module-info.java").
		 * - Each Module can have any number of Packages and Types (class and so on).
		 * - One Module can depends on any number of other modules.
		 * 
		 * JAVA 9 MODULE DESCRIPTOR
		 * 
		 * Module descriptor is a resource, which contains module's meta-datas. It is NOT an XML or a properties file, but a plain Java file.  We 
		 * must name this file as "module-info.java". By convention, we must place it at the root folder of the module. Like other Java source files, 
		 * a module descriptor file is compiled into "module-info.class".
		 * 
		 * A module descriptor is created using "module" keyword (look at "module-info.java" file of this project).
		 * 
		 * WHAT IS MODULE META DATA?
		 * 
		 * A module has the following meta-datas :
		 * 
		 * - A unique name => "Java_9_tutorial" for this project.
		 * - exports clauses => A module can exports it's packages to outside world so that other modules can use them. We use the "exports" clause to
		 * 		do so, for example : "exports tutorial_001.modules;". It is not mandatory to export all packages. It's up-to the Module Owner to decide 
		 * 		which one to export and which one not.
		 * - requires clauses => A module can import or use other modules packages. We use "requires" clause to do so, for example : 
		 * 		"requires java.desktop;".
		 * 
		 * This previous examples are not all existing module's meta-date, but it's enough for starting module's discovery. Note that :
		 * 
		 * - Module descriptor must contain at least module's name.
		 * - It can contain zero, one or more exports clause.
		 * - It can contain zero, one or more requires clause.
		 * - Of course, it can contain both at same time.
		 * 
		 * ARE "MODULE", "REQUIRES", AND "EXPORTS" KEYWORDS ?
		 * 
		 * All these three words are not keywords. They are just "Contextual Keywords". That means they are keywords only inside a module descriptor
		 * ("module-info.java") file. If we used them as Identifiers in the existing code base, Java Compiler or Run-time does NOT throw any error or 
		 * Exceptions at compile-time and run-time.
		 * 
		 * JAVA 8 JARS VS JAVA 9 MODULES
		 * 
		 * When we create Java 8 or earlier versions project in an IDE, we can see this IDE adding lots of JDK JARs into our Project CLASSPATH. If we do 
		 * the same thing in Java 9, IDE adds lots of JDK Modules into our Project MODULEPATH. So from Java 9 on-wards, We refer MODULEPATH instead of 
		 * CLASSPATH. Of course, Java 9 supports both MODULEPATH and CLASSPATH.
		 * 
		 * CLASSPATH VS MODULEPATH
		 * 
		 * A ClassPath is a sequence of classes and packages (or JARs) which are user-defined classes and built-in classes. JVM or Java Compiler requires 
		 * this to compile the application or classes.
		 * 
		 * A ModulePath is a sequence of Modules (which are provided in a Folder format or JAR format). If a Module is in folder format, that means its in 
		 * Exploded Module format. If its in a JAR format, that jar is know as "Modular JAR".
		 */
}
