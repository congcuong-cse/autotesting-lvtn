
public class TestFunction {
	private String type;
	private String name;
	
	public String getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	public TestFunction(){
		this.type = "___";
		this.name = "___";
	}
	
	public TestFunction(String type, String name){
		this.type = type;
		this.name = name;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
