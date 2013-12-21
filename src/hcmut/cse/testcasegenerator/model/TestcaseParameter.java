package hcmut.cse.testcasegenerator.model;
import java.util.ArrayList;

import org.eclipse.jdt.core.ILocalVariable;

public class TestcaseParameter {
	private ILocalVariable var;
	private int value;
	private int min;
	private int max;
	
	public TestcaseParameter(ILocalVariable ilv, int v, int min, int max){
		this.var = ilv;
		this.value = v;
		this.min = min;
		this.max = max;
	}
	
	public TestcaseParameter(ILocalVariable ilv){
		this.var = ilv;
	}
	
	public ILocalVariable getVar(){
		return this.var;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int v){
		this.value = v;
	}
	
	public int getMin(){
		return this.min;
	}
	
	public int getMax(){
		return this.max;
	}
	
	public static void beginLoop(ArrayList<TestcaseParameter> list){
		for(int j = 0; j< list.size(); j++){
			list.get(j).setValue(list.get(j).getMin());
		}
	}
	
	public static boolean endLoop(ArrayList<TestcaseParameter> list){
		for(int i = 0; i< list.size(); i++){
			if(list.get(i).value  != list.get(i).getMax()){
				return false;
			}
		}
		return true;
	}
	
	public static void nextValue(ArrayList<TestcaseParameter> list){
		int index = 0;
		for(int i = 0; i< list.size(); i++){
			if(list.get(i).value  == list.get(i).getMax()){
				index = i+1;
			}
			else
				break;
		}
		list.get(index).setValue(list.get(index).getValue()+1);
		for(int j = 0; j< index; j++){
			list.get(j).setValue(list.get(j).getMin());
		}
	}
}
