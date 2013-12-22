package hcmut.cse.testcasegenerator;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.This;
import bsh.ConsoleInterface;
import bsh.NameSpace;
import com.rits.cloning.Cloner;

public class EvaluatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		InfixPostfixEvaluator ipe = new InfixPostfixEvaluator();
//		int result = ipe.evalInfix("32+1");
//		System.out.println(result);
		
		Interpreter bsh = new Interpreter();

        // Evaluate statements and expressions
        	for(int i =-99; i<=99; i++){
        		try {
					Interpreter in = new Interpreter();
					in.set("a",i);
					in.eval("con = a==3");
					if( (Boolean) in.get("con") == false){
						continue;
					}
					Interpreter new_in = new Interpreter();
					new_in.setNameSpace(new Cloner().deepClone(in.getNameSpace()));
					//Interpreter new_in = in;
					//This.bind(null, in.getNameSpace(), new_in);
					System.out.println(in.getNameSpace());
					System.out.println(new_in.getNameSpace());
					new_in.eval("return result = a");
					in.eval("result = a*a");
					System.out.println("Result:"+new_in.get("result"));
					break;
				} catch (EvalError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Fail:"+i);
				}
        	}
//        	for(int i =-99; i<=99; i++){
//        		try {
//					Interpreter in = new Interpreter();
//					in.set("a",i);
//					in.eval("con = !(a==0)");
//					if( (Boolean) in.get("con") == false){
//						continue;
//					}
//					in.eval("return a");
//					System.out.println("Result:"+i);
//					break;
//				} catch (EvalError e) {
//					// TODO Auto-generated catch block
//				}
//        	}
//			bsh.set("b",0);
//			bsh.eval("result = b==0");
//			if( (Boolean) bsh.get("result") == true){
//    			System.out.println("d");
//    		}
		

	}

}
