package tutorial_001.modules;

public class _000_ModulesIntroduction {
	/*
	 * Jigsaw project is going to introduce completely new concept of Java SE 9 : Java Module System. Main goals of it are :
	 * 
	 * - The Modular JDK : divide JDK itself into small modules.
	 * - Modular Source Code : Current source code jar files are too big, especially rt.jar. So they are going to divide Java Source code into smaller
	 *                         modules.
	 * - Modular Run-Time Images : The main goal of this Feature is "Restructure the JDK and JRE run-time images to accommodate modules".
	 * - Encapsulate Most Internal APIs : The main goal of this feature is "Make most of the JDK's internal APIs inaccessible by default but leave a 
	 * 						   few critical, widely-used internal APIs accessible".
	 * - Java Platform Module System : The main goal of this Feature is "Allowing the user to create their modules to develop their applications".
	 * - jlink - The Java Linker : The main goal of this jlink Tool is "Allowing the user to create executable to their applications".
	 * 
	 * But, why java need Java SE 9 Module System ? Because java SE 8 or earlier get following problems in developing or delivering Java Based 
	 * applications :
	 * 
	 * - As JDK is too big, it is a bit tough to scale down to small devices (chipset for example). Java SE 8 has introduced 3 types of compact profiles 
	 * 		to solve this problem : compact1, compact2, and compact3. But it does not solve it properly.
	 * - Furthermore, because of this JDK's size, our applications or devices Performances are not as good as it might be.
	 * - JAR files like rt.jar etc are too big to use in small devices and applications.
	 * - There is no Strong Encapsulation in the current Java System because "public" access modifier is too open. Everyone can access it. So Oracle 
	 *  	is not able to avoid the access to some Internal APIs like sun.*, *.internal.* etc.
	 * - So, as User can access Internal APIs, it may be some Security issues.
	 * - As JDK, JRE is too big, it may be hard to Test and Maintain applications.
	 * - Its a bit hard to support Less Coupling between components.
	 * 
	 * That's where Java 9 Module system come in action : it allow to solve all these problems by providing the following benefits :
	 * 
	 * - As Java SE 9 is going to divide JDK, JRE, JARs etc, into smaller modules, we can use whatever modules we want. So it is very easy to scale 
	 * 		down the Java Application to Small devices.
	 * - Ease of Testing and Maintainability.
	 * - Supports better Performance.
	 * - As public is not just public, it supports very Strong Encapsulation (will be shown in futur examples).
	 * - We cannot access Internal Non-Critical APIs anymore. 
	 * - Modules can hide unwanted and internal details very safely, we can get better Security. 
	 * - Its easy to support Less Coupling between components.
	 * - Its easy to support Single Responsibility Principle (SRP).
	 * 
	 * COMPARE JDK8 AND JDK9
	 * 
	 * After installing JDK 8 software, Java Home folder contains inner directories like bin, jre, lib etc. Java 9 introduce tiny changes in this
	 * structure (to see this changes, compare jdk8_folder_tree.png and jdk9_folder_tree.png images stored in this package). In JDK 9, JRE is separated 
	 * into a separate distribution folder. JDK 9 software contains a new folder "jmods". It contains a set of Java 9 Modules (but no rt.jar or 
	 * tools.jar). The files contained in this jmods folder are known as JDK Modules.
	 * 
	 * NOW, A QUESTION MAY HAS RISED : WHAT EXACTLY IS A JAVA 9 MODULE ?
	 * 
	 * A Module is a self-describing collection of Code, Data, and some Resources. It is a set of Packages, Types (classes, abstract classes,
	 * interfaces etc) with Code & Data and Resources. Each Module contains only a set of related code and data to support Single Responsibility 
	 * (Functionality) Principle (SRP). The main goal of Java 9 Module System is to support Modular Programming in Java.
	 * 
	 * "MOTHER OF JAVA 9 MODULE SYSTEM"
	 * 
	 * Oracle has separated JDK jars and Java SE Specifications into two sets of Modules :
	 * - All JDK Modules starts with "jdk.*"
	 * - All Java SE Specifications Modules starts with "java.*"
	 * 
	 * Java 9 Module System has a "java.base" Module. It is known as Base Module. It's an Independent module and does NOT dependent on any other modules.
	 * By default, all other Modules depends on this module. That's why "java.base" Module is also known as the "Mother of Java 9 Modules".
	 * 
	 * COMPARE JAVA 8 AND JAVA 9 APPLICATIONS
	 * 
	 * Look at jdk8_application.png image to see a classical java 8 application folders hierarchy. Top level components are Packages, that contains
	 * classes and so on. If we compare this organisation with it of java 9 (look at jdk9_application.png), we may see that java 9 applications does not 
	 * have much differences with this. It just introduced a new component called "Module", which is used to group a set of related Packages, plus a
	 * "module-info.java" file which job is to describe modules in our application (we wiil see this later).
	 * 
	 * Beware : Each Java 9 Module have one and only one Module and one Module Descriptor. Unlike packages and sub-packages, we cannot create multiple 
	 * modules into a single module.
	 */
}
