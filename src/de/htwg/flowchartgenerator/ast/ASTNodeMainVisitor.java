package de.htwg.flowchartgenerator.ast;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.ui.IWorkbenchPart;

import de.htwg.flowchartgenerator.ast.model.FNode;
import de.htwg.flowchartgenerator.ast.model.INode;
import de.htwg.flowchartgenerator.ast.model.LNode;
import de.htwg.flowchartgenerator.controller.Controller;

/**
 * 
 * Fills the root node with child nodes. Methods are called automatically from the visitor.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class ASTNodeMainVisitor extends ASTVisitor {
	INode tmp;
	INode switchCase;
	INode switchSt;
	IWorkbenchPart wb;
	int startPosition = Controller.getInstance().getPosition();

	public ASTNodeMainVisitor(INode nodes) {
		tmp = nodes;
	}

	public ASTNodeMainVisitor(IWorkbenchPart targetPart, INode nodes) {
		this.wb = targetPart;
		this.tmp = nodes;
	}

	
	public boolean visit(ReturnStatement node){
		INode returnNode = new FNode(node.toString(), ASTNode.RETURN_STATEMENT);
		returnNode.setInfo(node.toString());
		addToCursor(tmp, returnNode);
		return false;
	}


	public boolean visit(ExpressionStatement node) {
		INode expNode = new FNode(node.getExpression().toString(), ASTNode.EXPRESSION_STATEMENT);
		expNode.setInfo(node.toString());
		INode conditionalExpression = new FNode(node.getExpression().toString(), ASTNode.IF_STATEMENT);
		conditionalExpression.setInfo(node.toString());
		ASTVisitor aec = new ASTExpressionChecker(conditionalExpression);
		node.accept(aec);
		if (conditionalExpression.getNodes().size() > 0) {
			expNode = conditionalExpression;
		}
		addToCursor(tmp, expNode);
		tmp = expNode;
		return true;
	}

	public boolean visit(VariableDeclarationFragment node) {
		if (null != node.getInitializer()) {
			INode expNode = new FNode(node.toString(), ASTNode.EXPRESSION_STATEMENT);
			expNode.setInfo(node.toString());
			addToCursor(tmp, expNode);
			tmp = expNode;
		}
		return true;
	}

	public boolean visit(IfStatement node) {
		// this is the if-Statement. the first element added to the main stream.
		FNode ifNode = new FNode(node.getExpression().toString(), ASTNode.IF_STATEMENT);
//		System.out.println(e.getLeftOperand().toString());
//		System.out.println(e.getOperator());
//		System.out.println(e.getRightOperand().toString());
		Expression expr = node.getExpression();
		FNode tmpNode = new FNode("FUCK",0);
		ASTVisitor visitor_ = new ASTInfixExpressionChecker(tmpNode);
		expr.accept(visitor_);
		ifNode.setInfo(node.toString());
		ASTVisitor visitor = new ASTNodeMainVisitor(ifNode);
		node.getThenStatement().accept(visitor);
		addToCursor(tmp, ifNode);
		tmp = ifNode;
		Statement elseSt = node.getElseStatement();
		if (null != elseSt) {
			// if there is a else-statement than it should be the third element
			// the second one is left empty for the main stream
			if (tmp.getSize() > 0) {
				tmp.getNodes().add(new FNode("", -1));
			}

			INode tn = new FNode("Else", ASTNode.EMPTY_STATEMENT);
			visitor = new ASTNodeMainVisitor(tn);
			elseSt.accept(visitor);
			if (tn.getSize() > 0) {
				tmp.addNode(tn);
			}
		}
		if(tmp.getSize()==0){
			tmp.setType(ASTNode.EXPRESSION_STATEMENT);
		}
		return false;
	}

	public boolean visit(TryStatement node) {
		FNode tryNode = new FNode("Try", ASTNode.TRY_STATEMENT);
		tryNode.setInfo(node.toString());
		ASTVisitor visitor1 = new ASTNodeMainVisitor(tryNode);
		node.getBody().accept(visitor1);
		tryNode.getNodes().add(new FNode("", -1));

		List l = node.catchClauses();
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			CatchClause tNode = (CatchClause) iterator.next();
			System.out.println(tNode);
			ASTVisitor visitor = new ASTNodeMainVisitor(tryNode);
			tNode.getBody().accept(visitor);
		}
		INode n1 = tryNode.getNodes().get(1);
		tryNode.addNode(n1);
		tryNode.getNodes().set(1, new FNode("", -1));
		Statement finallySt = node.getFinally();
		if (null != finallySt) {
			FNode finallyNode = new FNode("", ASTNode.CATCH_CLAUSE);
			visitor1 = new ASTNodeMainVisitor(finallyNode);
			finallySt.accept(visitor1);
			tryNode.addNode(finallyNode);
		}
		addToCursor(tmp, tryNode);
		tmp = tryNode;

		return false;
	}

	public boolean visit(CatchClause node) {
		return true;
	}

	public boolean visit(ForStatement node) {
		FNode forNode = new FNode(node.getExpression().toString(), ASTNode.FOR_STATEMENT);
		forNode.setInfo(node.toString());
		ASTVisitor visitor = new ASTNodeMainVisitor(forNode);
		node.getBody().accept(visitor);
		if (forNode.getSize() == 0) {
			forNode.setType(ASTNode.EXPRESSION_STATEMENT);
		}
		addToCursor(tmp, forNode);
		tmp = forNode;
		return false;
	}

	public boolean visit(WhileStatement node) {
		FNode forNode = new FNode(node.getExpression().toString(), ASTNode.FOR_STATEMENT);
		forNode.setInfo(node.toString());
		ASTVisitor visitor = new ASTNodeMainVisitor(forNode);
		node.getBody().accept(visitor);
		if (forNode.getSize() == 0) {
			forNode.setType(ASTNode.EXPRESSION_STATEMENT);
		}
		addToCursor(tmp, forNode);
		tmp = forNode;
		return false;
	}

	public boolean visit(DoStatement node) {
		FNode forNode = new FNode("do", ASTNode.DO_STATEMENT);
		forNode.setInfo(node.toString());
		ASTVisitor visitor = new ASTNodeMainVisitor(forNode);
		node.getBody().accept(visitor);
		FNode whileNode = new FNode(node.getExpression().toString(), ASTNode.WHILE_STATEMENT);
		forNode.addNode(whileNode);
		addToCursor(tmp, forNode);
		tmp = whileNode;
		return false;
	}

	public boolean visit(SwitchStatement node) {
		FNode switchNode = new FNode(node.getExpression().toString(), ASTNode.SWITCH_STATEMENT);
		switchNode.setInfo(node.toString());
		switchNode.addNode(new FNode("", -1));// fill the first two nodes
		switchNode.addNode(new FNode("", -1));
		INode tCase = null;
		List l = node.statements();
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			Statement tNode = (Statement) iterator.next();
			if (tNode instanceof SwitchCase) {
				if(((SwitchCase) tNode).getExpression() != null){
					System.out.println(((SwitchCase) tNode).getExpression().toString());
					tCase = new FNode(node.getExpression().toString() +"==" + ((SwitchCase) tNode).getExpression().toString(), ASTNode.SWITCH_CASE);
				}
				else{
					tCase = new FNode(tNode.toString(), ASTNode.SWITCH_CASE);
				}
					//caseNode.addNode(tCase);
				switchNode.addNode(tCase);
				continue;
			}
			ASTVisitor visitor = new ASTNodeMainVisitor(tCase);
			tNode.accept(visitor);

			if (tCase.getSize() == 1) {
				tCase = tCase.getNodes().get(0);
			} else if (tCase.getSize() == 2) {
				tCase = tCase.getNodes().get(1);
			}
		}
		addToCursor(tmp, switchNode);
		tmp = switchNode;
		return false;
	}

	public boolean visit(BreakStatement node) {
		INode breakNode = new FNode(node.getClass().getSimpleName(), ASTNode.BREAK_STATEMENT);
		addToCursor(tmp, breakNode);
		return false;
	}
	public boolean visit(ContinueStatement node) {
		INode breakNode = new FNode(node.getClass().getSimpleName(), ASTNode.CONTINUE_STATEMENT);
		addToCursor(tmp, breakNode);
		return false;
	}

	/*
	 * Method is called from the visitor. Change the name from preVisit_ to preVisit to see the call sequence.
	 */
	public void preVisit_(ASTNode node) {
		ASTNode tempNode = node.getParent();
		while (tempNode != null) {
			System.out.print("\t>");
			tempNode = tempNode.getParent();
		}
	}

	private void addToCursor(INode tmp, INode ast) {
		if (tmp.getNodes().size() >= 2) {// its no simple Statement
			if (tmp.getNodes().get(1).getType() == -1) {
				tmp.getNodes().set(1, ast);
			} else {
				tmp.addNode(ast);
			}
		} else {
			tmp.addNode(ast);
		}
	}
}
