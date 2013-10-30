import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.Trees;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.RuleContext;

public class TestInfo{
	private TestFunction fun;
	private TestArguments args;
	
	public TestInfo(){
		this.fun = new TestFunction();
		this.args = new TestArguments();
	}
	
	public static TestArgument getArgument(Tree tree, JavaParser ruleNames){
		String type = Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(0).getChild(0).getChild(0), ruleNames), false);
		String var = Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(1).getChild(0), ruleNames), false);
		return new TestArgument(type, var);
	}
	
	
	public static TestArguments getArguments(Tree tree, JavaParser ruleNames){
		TestArguments result = new TestArguments();
		for (int i = 0; i<tree.getChildCount(); i++) {
			//System.out.println(Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(i),ruleNames ), false));
			if( Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(i),ruleNames ), false).contains("formalParameter")){
				//System.out.println("pass");
				TestArgument arg = getArgument(tree.getChild(i), ruleNames);
				result.addArg(arg);
			}
		}
		return result;
    }
	
	public void getTestInfo(Tree tree, JavaParser ruleNames){
		//System.out.println(Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(i),ruleNames ), false));
		if( Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(0),ruleNames ), false).contains("type")){
			//System.out.println("pass");
			this.fun.setType(Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(0).getChild(0).getChild(0),ruleNames ), false));
			this.fun.setName(Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(1),ruleNames ), false));
			if( Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(2).getChild(1),ruleNames ), false).contains("formalParameterList") ){
				this.args = getArguments(tree.getChild(2).getChild(1), ruleNames);
			}
		}

	}
	
	public String toString(){
		String s = "";
		s += "Ten ham: " + this.fun.getName() +"\n";
		s += "Kieu tra ve: " + this.fun.getType() +"\n";
		for(int i = 0; i < this.args.getArgs().size(); i++){
			s += "Tham so: " +  this.args.getArgs().get(i).getVar() + "\t kieu: " + this.args.getArgs().get(i).getType()+"\n";
		}
		
		return s;
		
	}
	

}

