package hcmut.cse.testcasegenerator;
import java.util.*;

import hcmut.cse.testcasegenerator.model.*;

import org.eclipse.zest.layouts.LayoutStyles;

import bsh.EvalError;
import bsh.Interpreter;

import com.rits.cloning.Cloner;

import static de.htwg.flowchartgenerator.utils.Statics.RED_COLOR;

public class TestcaseGenerator {
	private ArrayList<ArrayList<TestcaseNode>> tests = new ArrayList<ArrayList<TestcaseNode>>();
	
	public ArrayList<ArrayList<TestcaseNode>> getTests(){
		return this.tests;
	}

	public ArrayList<TestcaseNode> getTruePath(TestcaseGraph g, TestcaseNode startNode, TestcaseNode endNode){
		ArrayList<TestcaseNode> result =  new ArrayList<TestcaseNode>();
		
		Stack<TestcaseNode> stack = new Stack<TestcaseNode>();
		stack.push(startNode);
		
		while(!stack.empty()){
			TestcaseNode n = stack.pop();
			if(endNode.equals(n)){
				result.add(n);
				return result;
			}
			else{
				List<TestcaseGraphConnection> gn = n.getNode().getSourceConnections();
				ArrayList<TestcaseNode> newBefore =  new ArrayList<TestcaseNode>();
				if( n.getBefore() != null){
					for (TestcaseNode i : n.getBefore()) {
					newBefore.add(new TestcaseNode(i));
					}
				}
				TestcaseNode new_n = new TestcaseNode(n);
				newBefore.add(new_n);
				TestcaseNode newTestcaseNode = null; 
				if(gn.size() == 1){
					newTestcaseNode = new TestcaseNode(gn.get(0).getDestination(), newBefore);
					if(gn.get(0).getLineColor() == RED_COLOR){
						new_n.getNode().setText("!(" +new_n.getNode().getText()+")");
					}
					stack.push(newTestcaseNode);
					result.add(new_n);
				}
				else if(gn.size() == 2){
					
					int isNew;
					
					if(gn.get(0).getDestination().getDeep() < n.getNode().getDeep()){
						isNew = 0;
					}
					else if(gn.get(1).getDestination().getDeep() < n.getNode().getDeep()){
						isNew = 1;
					}
					else if(gn.get(0).getLineColor() == RED_COLOR){
						isNew = 1;
					}
					else{
						isNew = 0;
					}
					//newTestcaseNode
					if( isNew == 0 && !result.contains(newTestcaseNode = new TestcaseNode(gn.get(0).getDestination(), newBefore))){
						if(gn.get(0).getLineColor() == RED_COLOR){
							new_n.getNode().setText("!(" +new_n.getNode().getText()+")");
						}
						stack.push(newTestcaseNode);
						result.add(new_n);
					}
					else{
						newTestcaseNode = new TestcaseNode(gn.get(1).getDestination(), newBefore);
						if(gn.get(1).getLineColor() == RED_COLOR){
							new_n.getNode().setText("!(" +new_n.getNode().getText()+")");
						}
						stack.push(newTestcaseNode);
						result.add(new_n);
					}
				}
			}
		}
		
		return null;
	}
	
	
	public void breadthFirstTraversal(TestcaseGraph g){
		
		if(g.getNodes().size()>2){
			TestcaseNode startNode = new TestcaseNode((TestcaseGraphNode) g.getNodes().get(0),null);
			TestcaseNode endNode = new TestcaseNode((TestcaseGraphNode) g.getNodes().get(1),null);
			
			//addTruePath
			this.tests.add(getTruePath(g, startNode, endNode));
			
			ArrayList<TestcaseNode> notVisited = new ArrayList<TestcaseNode>();
			for(int i=0; i< g.getNodes().size(); i++){
				TestcaseNode tmpNode = new TestcaseNode( (TestcaseGraphNode) g.getNodes().get(i), null);
				notVisited.add(tmpNode);
			}
			Queue<TestcaseNode> q = new LinkedList<TestcaseNode>();
			q.add(startNode);
			while(!q.isEmpty()){
				//System.out.println(q.toString());
				TestcaseNode n = q.poll();
		
				System.out.println("<<<" + n.toString());
				List<TestcaseGraphConnection> gn = n.getNode().getSourceConnections();
				TestcaseNode newTestcaseNode1 = null;
				TestcaseNode newTestcaseNode2 = null;
				ArrayList<TestcaseNode> newBefore1 =  new ArrayList<TestcaseNode>();
				if( n.getBefore() != null){
					for (TestcaseNode i : n.getBefore()) {
						newBefore1.add(new TestcaseNode(i));
					}
				}
				
				ArrayList<TestcaseNode> newBefore2 =  new ArrayList<TestcaseNode>();
				if( n.getBefore() != null){
					for (TestcaseNode i : n.getBefore()) {
						newBefore2.add(new TestcaseNode(i));
					}
				}
				TestcaseNode new_n1 = new TestcaseNode(n);
				newBefore1.add(new_n1);
				TestcaseNode new_n2 = new TestcaseNode(n);
				newBefore2.add(new_n2);
				
				if(gn.size()>0){
					newTestcaseNode1 = new TestcaseNode(gn.get(0).getDestination(),newBefore1);
				}
				if(gn.size()>1){
					newTestcaseNode2 = new TestcaseNode(gn.get(1).getDestination(),newBefore2);		
				}
				
				if(gn.size() == 1 && notVisited.contains(newTestcaseNode1) ){
					q.add(newTestcaseNode1);
					notVisited.remove(newTestcaseNode1);
				}
				else if (gn.size ()== 2){
					
					int isNew;// 0 or 1
					
					if(gn.get(0).getDestination().getDeep() < n.getNode().getDeep()){
						isNew = 1;
						if(!notVisited.contains(newTestcaseNode2)){
							isNew = 0;
						}
					}
					else if(gn.get(1).getDestination().getDeep() < n.getNode().getDeep()){
						isNew = 0;
						if(!notVisited.contains(newTestcaseNode1)){
							isNew = 1;
						}
					}
					else if(gn.get(0).getLineColor() == RED_COLOR){
						isNew = 0;
						if(!notVisited.contains(newTestcaseNode1)){
							isNew = 1;
						}
					}
					else{
						isNew = 1;
						if(!notVisited.contains(newTestcaseNode2)){
							isNew = 0;
						}
					}
					
					//isNew == 0
					if(isNew == 0){
						// queue add 1
						if(notVisited.contains(newTestcaseNode2)){
							q.add(newTestcaseNode2);
							notVisited.remove(newTestcaseNode2);
						}
						//newtest
						if(gn.get(0).getLineColor() == RED_COLOR){
							new_n1.getNode().setText("!(" +new_n1.getNode().getText()+")");
						}
						ArrayList<TestcaseNode> newtest = new ArrayList<TestcaseNode>();
						for( TestcaseNode i : newTestcaseNode1.getBefore()){
							newtest.add(new TestcaseNode(i));
						}
						for( TestcaseNode j : getTruePath(g, newTestcaseNode1, endNode)){
							newtest.add(new TestcaseNode(j));
						}
						this.tests.add(newtest);
						//queue add 0
						if(notVisited.contains(newTestcaseNode1)){
							q.add(newTestcaseNode1);
							notVisited.remove(newTestcaseNode1);
						}
					}
					else{//isNew == 1
						//queue add 0
						if(notVisited.contains(newTestcaseNode1)){
							q.add(newTestcaseNode1);
							notVisited.remove(newTestcaseNode1);
						}
						//newtest
						if(gn.get(1).getLineColor() == RED_COLOR){
							new_n2.getNode().setText("!(" +new_n2.getNode().getText()+")");
						}
						ArrayList<TestcaseNode> newtest = new ArrayList<TestcaseNode>();
						for( TestcaseNode i : newTestcaseNode2.getBefore()){
							newtest.add(new TestcaseNode(i));
						}
						for( TestcaseNode j : getTruePath(g, newTestcaseNode2, endNode)){
							newtest.add(new TestcaseNode(j));
						}
						this.tests.add(newtest);
						//queue add 1
						if(notVisited.contains(newTestcaseNode2)){
							q.add(newTestcaseNode2);
							notVisited.remove(newTestcaseNode2);

						}
					}
				}
				
			}
			
		}
		
	}
	public static boolean runLoop(Interpreter in, ArrayList<TestcaseNode> paths, int deep, String outputExpected) throws EvalError{
		Interpreter new_in = new Interpreter();
		new_in.setNameSpace(new Cloner().deepClone(in.getNameSpace()));
		int gonext = 0;
		String loopCondition = paths.get(0).getNode().getInfo();
		String loopBody = paths.get(0).getNode().getInfo_();
		for(int i=0; i<paths.size(); i++){
			TestcaseNode test = paths.get(i);
			
			if(test.getNode().getDeep() > deep  && isBeginLoop(test)){
				ArrayList<TestcaseNode> newpaths = new ArrayList<TestcaseNode>();
				newpaths.add(test);
				for(int j = i+1; j < paths.size(); j++){
					TestcaseNode newtest = paths.get(j);
					if(newtest.getNode().getDeep() > deep && newtest.getNode().getType() != 0){
						newpaths.add(newtest);
					}
					else{
						i = j;
						break;
					}
				}
				return runLoop(new_in, newpaths, deep +1, outputExpected);
			}
			else{
				
				//TODO
				if(test.getNode().getType()==1){
					//System.out.println("eval(" +e.getNode().getText() + ")");
					new_in.eval(test.getNode().getText());
				}
				else if(test.getNode().getType()==2){
					//System.out.println("eval(_con = " +e.getNode().getText() + ")");
					new_in.eval("_con = " + test.getNode().getText());
					if( (Boolean) new_in.get("_con") == false){
						gonext =1;
						break;
					}
				}
				
				else if(test.getNode().getType()==3){
					//System.out.println("eval("+  e.getNode().getText().replaceFirst("return","return _result =") + ")");
					new_in.eval(test.getNode().getText().replaceFirst("return","return _result ="));
					outputExpected = new_in.get("_result").toString();
				}
			}
		}
		if(gonext == 0){
			in.setNameSpace(new Cloner().deepClone(new_in.getNameSpace()));;
			return true;
		}
		else{
			in.eval("_con = " + loopCondition );
			if( (Boolean) in.get("_con") == false){
				return false;
			}
			else{
				in.eval(loopBody);
				return runLoop(in, paths, deep, outputExpected);
			}
		}
	}
	
	public static boolean isBeginLoop(TestcaseNode test){
		return (test.getNode().getText().equals("FOR")
				|| test.getNode().getText().equals("WHILE")
				|| test.getNode().getText().equals("DO"));
	}
}
