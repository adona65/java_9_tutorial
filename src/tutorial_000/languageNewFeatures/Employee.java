package tutorial_000.languageNewFeatures;

public class Employee {

	private int id;
	private String name;
	
	/* Contructors */
	public Employee() {}
	
	public Employee(int i, String s) {
		this.id = i;
		this.name = s;
	}
	
	@Override
	public String toString() {
		return "[id="+id+",name="+name+"]";
	}
	
	/* Getters and Setters*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
