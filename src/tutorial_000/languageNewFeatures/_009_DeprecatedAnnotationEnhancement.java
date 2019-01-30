package tutorial_000.languageNewFeatures;

public class _009_DeprecatedAnnotationEnhancement {
	/*
	 * In Java SE 8 and earlier versions, @Deprecated annotation is just a Marker interface without any methods. It is used to mark a Java API that 
	 * is a class, field, method, interface, constructor, enum etc.
	 * 
	 * In Java SE 9, @Deprecated annotation is enhanced to provide more informations. It get two optionnal elements: forRemoval() and since().
	 */
		
	/*
	 * since : Returns the version in which the annotated element became deprecated. The default value is the empty string. 
	 */
	@Deprecated(since="9")
	public void sinceMethod(){};
	
	/*
	 * forRemoval : Indicates whether the annotated element is subject to removal in a future version. The default value is false.
	 */
	@Deprecated(forRemoval=true)
	public void removedMethod(){};

}
