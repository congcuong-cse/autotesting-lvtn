import org.antlr.v4.runtime.tree.Trees;
import org.antlr.v4.runtime.*;
public class TestArgument {
	private String type;
	private String var;
	
	public String getType(){
		return type;
	}
	
	public String getVar(){
		return var;
	}
	
	public TestArgument(){
		this.type = "___";
		this.var = "___";
	}
	
	public TestArgument(String type, String var){
		this.type = type;
		this.var = var;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setVar(String var){
		this.var = var;
	}
	
	
}
