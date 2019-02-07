package tutorial_001.modules;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Exports;
import java.lang.module.ModuleDescriptor.Opens;
import java.lang.module.ModuleDescriptor.Provides;
import java.lang.module.ModuleDescriptor.Requires;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class _005_ModuleApi {

	@SuppressWarnings({ "unlikely-arg-type", "rawtypes", "unused" })
	public static void main(String[] args) {
		/*
		 * java.lang.Module API was introduced alongside the Java Platform Module System. This API provides a way to access a module programmatically 
		 * in order to retrieve specific information from a module, and generally to work with it and its ModuleDescriptor.
		 * 
		 * READING MODULE INFORMATION
		 * 
		 * The Module class represents both named and unnamed modules. Named modules have a name and are constructed by the Java Virtual Machine when 
		 * it creates a module layer, using a graph of modules as a definition. An unnamed module doesn't have a name, and there is one for each 
		 * ClassLoader. All types that aren't in a named module are members of the unnamed module related to their class loader.
		 * 
		 * The interesting part of the Module class is that it exposes methods that allow us to retrieve information from the module, like the module 
		 * name, the module classloader and the packages within the module.
		 * 
		 * NAMED OR UNNAMED
		 * 
		 * isNamed() : allow to identify whether a module is named or not :
		 */
		Class<HashMap> hashMapClass = HashMap.class;
		Module javaBaseModule = hashMapClass.getModule();
		 
		System.out.println(javaBaseModule.isNamed());
		System.out.println(javaBaseModule.getName());
		
		System.out.println("-------------------------------------");
		
		// Inner Person class will be part of the current module. 
		Class<Person> personClass = Person.class;
		Module module = personClass.getModule();
		 
		System.out.println(module.isNamed());
		System.out.println(module.getName());
		
		System.out.println("=====================================");
		
		/*
		 * PACKAGES
		 * 
		 * When working with a module, it might be important to know which packages are available within the module. We can check that fact :
		 */
		System.out.println(javaBaseModule.getPackages().contains("java.lang.annotation"));
		System.out.println(javaBaseModule.getPackages().contains("java.sql"));
		
		System.out.println("=====================================");
		
		/*
		 * ANNOTATIONS
		 * 
		 * getAnnotations() : retrieve the annotations that are present in the module. If there are no annotations present in a named module, the method 
		 * will return an empty array. When invoked on an unnamed module, the getAnnotations() method will return an empty array.
		 */
		System.out.println(javaBaseModule.getAnnotations().length);
		
		System.out.println("=====================================");
		
		/*
		 * CLASSLOADER
		 * 
		 * getClassLoader() : retrieve the ClassLoader for a given module.
		 */
		System.out.println(module.getClassLoader().getClass().getName());
		
		System.out.println("=====================================");
		
		/*
		 * LAYER
		 * 
		 * Another valuable information that could be extracted from a module is the ModuleLayer, which represents a layer of modules in the Java virtual 
		 * machine. A module layer informs the JVM about the classes that may be loaded from the modules. In this way, the JVM knows exactly which module 
		 * each class is a member of. A ModuleLayer contains information related to its configuration, the parent layer and the set of modules available 
		 * within the layer.
		 * 
		 * A special case is the boot layer, created when Java Virtual Machine is started. The boot layer is the only layer that contains the java.base 
		 * module.
		 */
		// Retrieve the layer.
		ModuleLayer javaBaseModuleLayer = javaBaseModule.getLayer();
		// Retrieve layer's informations.
		System.out.println(javaBaseModuleLayer.configuration().findModule("java.base"));
		System.out.println(javaBaseModuleLayer.configuration().modules().size());
		
		System.out.println("=====================================");
		
		/*
		 * DEALING WITH MODULEDESCRIPTOR
		 * 
		 * A ModuleDescriptor describes a named module and defines methods to obtain each of its components. ModuleDescriptor objects are immutable and 
		 * thread safe. We can retrieve a ModuleDescriptor directly from a Module.
		 */
		ModuleDescriptor moduleDescriptor = javaBaseModule.getDescriptor();
		System.out.println(moduleDescriptor.name());
		
		System.out.println("-------------------------------------");
		
		/*
		 * CREATING A MODULEDESCRIPTOR
		 *
		 * It's also possible to create a module descriptor using the ModuleDescriptor.Builder class or by reading the binary form of a module declaration, 
		 * the module-info.class.
		 */
		ModuleDescriptor.Builder moduleBuilder = ModuleDescriptor.newModule("toto");
		ModuleDescriptor moduleDescriptor2 = moduleBuilder.build();
		System.out.println(moduleDescriptor2.name());
		
		/*
		 * With newModule() we created a normal module, but in case we want to create an open module or an automatic one, we can respectively use the 
		 * newOpenModule() or the newAutomaticModule() method.
		 */
		
		System.out.println("=====================================");
		
		/*
		 * CLASSIFYING A MODULE
		 * 
		 * Thanks to methods available within the ModuleDescriptor, it's possible to identify the type of the module (normal, open or automatic) :
		 */
		System.out.println(moduleDescriptor.isAutomatic());
		System.out.println(moduleDescriptor.isOpen());
		
		System.out.println("-------------------------------------");
		
		System.out.println(moduleDescriptor2.isAutomatic());
		System.out.println(moduleDescriptor2.isOpen());
		
		System.out.println("=====================================");
		
		/*
		 * RETRIEVING REQUIRES
		 * 
		 * requires() : retrieve the set of Requires, representing the module dependencies.
		 */
		Set<Requires> javaBaseRequires = javaBaseModule.getDescriptor().requires();
		Set<Requires> currentModuleRequires = module.getDescriptor().requires();
		
		Set<String> currentModuleRequiresNames = currentModuleRequires.stream()
				  .map(Requires::name)
				  .collect(Collectors.toSet());
		
		System.out.println(javaBaseRequires);
		System.out.println(currentModuleRequires.size());
		System.out.println(currentModuleRequiresNames);

		/*
		 * All modules, except java.base, have the java.base module as a dependency. If the module is an automatic module, the set of dependencies will be 
		 * empty except for the java.base one.
		 */
		
		System.out.println("=====================================");
		
		/*
		 * RETRIEVING PROVIDES
		 * 
		 * provides() : retrieve the list of services that the module provides.
		 */
		
		Set<Provides> javaBaseProvides = javaBaseModule.getDescriptor().provides();
		Set<Provides> currentModuleProvides = module.getDescriptor().provides();
		
		Set<String> currentModuleProvidesNames = currentModuleProvides.stream()
				  .map(Provides::service)
				  .collect(Collectors.toSet());
		
		System.out.println(javaBaseProvides);
		System.out.println(currentModuleProvides.size());
		System.out.println(currentModuleProvidesNames);
		
		System.out.println("=====================================");
		
		/*
		 * RETRIEVING EXPORTS
		 * 
		 * export() : find out if the modules exports packages and which in particular
		 */
		Set<Exports> javaBaseExports = javaBaseModule.getDescriptor().exports();
		Set<Exports> currentModuleExports = module.getDescriptor().exports();
		
		Set<String> currentModuleExportsNames = currentModuleExports.stream()
				  .map(Exports::source)
				  .collect(Collectors.toSet());
		
		System.out.println(javaBaseExports);
		System.out.println(currentModuleExports.size());
		System.out.println(currentModuleExportsNames);
		
		System.out.println("=====================================");
		
		/*
		 * RETRIEVING USES
		 * 
		 * uses() : retrieve the set of service dependencies of the module.
		 */
		Set<String> javaBaseUses = javaBaseModule.getDescriptor().uses();
		Set<String> currentModuleUses = module.getDescriptor().uses();
		
		System.out.println(javaBaseUses);
		System.out.println(javaBaseUses.contains("java.util.spi.ResourceBundleControlProvider"));
		System.out.println(currentModuleUses.size());
		
		System.out.println("=====================================");
		
		/*
		 * RETRIEVING OPENS
		 * 
		 * opens() : retrieve the list of the open packages of a module. The set will be empty if the module is an open or an automatic one.
		 */
		Set<Opens> javaBaseOpens = javaBaseModule.getDescriptor().opens();
		Set<Opens> currentModuleOpens = module.getDescriptor().opens();
		
		System.out.println(javaBaseOpens);
		System.out.println(javaBaseOpens.contains("java.util.spi.ResourceBundleControlProvider"));
		System.out.println(currentModuleOpens.size());
	
		System.out.println("=====================================");
		
		/*
		 * DEALING WITH MODULES
		 * 
		 * With the Module API, we also can update a module definition.
		 * 
		 * ADDING EXPORTS
		 */
		Module updatedModule = module.addExports("tutorial_001.modules", module);
		
		/*
		 * ADDING READS
		 * 
		 * When we want to update a module to read a given module, we can use the addReads() method. This method does nothing if we add the module itself 
		 * since all modules read themselves. In the same way, this method does nothing if the module is an unnamed module or this module already reads 
		 * the other.
		 */
		Module updatedModule2 = module.addReads(module);
		
		/*
		 * ADDING OPENS
		 * 
		 * When we want to update a module that has opened a package to at least the caller module, we can use addOpens() to open the package to another 
		 * module. This method has no effect if the package is already open to the given module.
		 */
		Module updatedModule3 = module.addOpens("tutorial_001.modules", module);
		
		/*
		 * ADDING USES
		 * 
		 * Whenever we want to update a module adding a service dependency, the method addUses() is made for it. This method does nothing when invoked on an 
		 * unnamed module or an automatic module.
		 */
		Module updatedModule4 = module.addUses(Driver.class);
	}

	private class Person {}
}
