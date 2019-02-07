package tutorial_000.languageNewFeatures;

public class _013_MultireleaseJar {
	/*
	 * In java 9, jar format has been enhanced to be able to handle different versions of java class or resources. This versions can be
	 * can be maintained and used dependently of the platform executing the application. 
	 * 
	 * In JAR, the file MANIFEST.MF now get an entry "Multi-Release: true" in its main section to allow it. META-INF directory also get a versions 
	 * subdirectory whose subdirectories (starting with 9 for Java 9 ) store version-specific classes and resource files.
	 */
}
