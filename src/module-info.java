module java_9_tutorial {
	// Will be replaced by java.httpclient in JDK 10.
	requires jdk.incubator.httpclient;
	// Mandatory for MultiResolutionImage API demonstration.
	requires transitive java.desktop;
}