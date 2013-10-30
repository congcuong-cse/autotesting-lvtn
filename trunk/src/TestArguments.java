
import java.util.ArrayList;





public class TestArguments {
	private ArrayList<TestArgument> args;
	
	public ArrayList<TestArgument> getArgs(){
		return this.args;
	}
	
	public TestArguments(){
		this.args = new ArrayList<TestArgument>();
	}
	
	public TestArguments(ArrayList<TestArgument> args){
		this.args = new ArrayList<TestArgument>(args);
	}
	
	public void addArg(TestArgument arg){
		this.args.add(arg);
	}

}
