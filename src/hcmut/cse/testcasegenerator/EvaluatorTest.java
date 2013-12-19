package hcmut.cse.testcasegenerator;
import bsh.EvalError;
import bsh.Interpreter;

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
					in.eval("con = a==0");
					if( (Boolean) in.get("con") == false){
						continue;
					}
					in.eval("return 1/a");
					System.out.println("Result:"+i);
					break;
				} catch (EvalError e) {
					// TODO Auto-generated catch block
					System.out.println("Result:"+i);
				}
        	}
        	for(int i =-99; i<=99; i++){
        		try {
					Interpreter in = new Interpreter();
					in.set("a",i);
					in.eval("con = !(a==0)");
					if( (Boolean) in.get("con") == false){
						continue;
					}
					in.eval("return a");
					System.out.println("Result:"+i);
					break;
				} catch (EvalError e) {
					// TODO Auto-generated catch block
				}
        	}
//			bsh.set("b",0);
//			bsh.eval("result = b==0");
//			if( (Boolean) bsh.get("result") == true){
//    			System.out.println("d");
//    		}
		

	}

}
