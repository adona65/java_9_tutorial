package tutorial_000.languageNewFeatures;

public class Freelancer extends Employee {

	private int fid;

	/* Constructor */
	public Freelancer(int id, int fid, String name) {
		super(id, name);
		this.fid = fid;
	}
	
	@Override
	public String toString() {
		return "[id="+super.getId()+",name="+super.getName()+",fid="+fid+"]";
	}
	
	/* Getter and Setters*/
	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}
	
	

}
