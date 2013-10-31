package hcmut.autotestgenerator.ast;

import hcmut.autotestgenerator.ast.model.CoverNode;
import hcmut.autotestgenerator.ast.model.FNode;
import hcmut.autotestgenerator.ast.model.INode;
import hcmut.autotestgenerator.xml.XML2Cover;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;


/**
 * 
 * Fills the root node with child nodes and additional information
 * if the coverage function is activated.
 * Methods are called automatically from the visitor.
 * 
 * @author Aldi Alimucaj
 *
 */
public class ASTNodeVisitor4Cover extends ASTVisitor {
	private INode tmp;
	private XML2Cover x2c;
	int startPosition;

	public ASTNodeVisitor4Cover(INode nodes) {
		tmp = nodes;
	}

	public ASTNodeVisitor4Cover(INode nodes, XML2Cover x2c) {
		this.tmp = nodes;
		this.x2c = x2c;
	}

	public ASTNodeVisitor4Cover(INode nodes, XML2Cover x2c, int startPosition) {
		this(nodes, x2c);
		this.startPosition = startPosition;
	}

	public boolean visit(ExpressionStatement node) {
		int nodePos = node.getStartPosition() + startPosition;
		INode expNode = new CoverNode(node.getExpression().toString(), ASTNode.EXPRESSION_STATEMENT, nodePos, nodePos + node.getLength());
		expNode.setInfo(node.getExpression().toString());
		boolean covered = x2c.checkNode(expNode, nodePos, node.getLength());
		INode conditionalExpression = new CoverNode(node.getExpression().toString(), ASTNode.IF_STATEMENT, nodePos, nodePos + node.getLength());
		ASTVisitor aec = new ASTExpressionChecker(conditionalExpression);
		node.accept(aec);
		if (conditionalExpression.getNodes().size() > 0) {
			expNode = conditionalExpression;
		}
		addToCursor(tmp, expNode);
		if (covered) {
			expNode.setCovered();
		}
		tmp = expNode;
		return true;
	}

	public boolean visit(VariableDeclarationFragment node) {
		int nodePos = node.getStartPosition() + startPosition;
		System.out.println("VariableDeclarationFragment#visitor " + node);
		if (null != node.getInitializer()) {
			INode expNode = new CoverNode(node.getName().toString(), ASTNode.EXPRESSION_STATEMENT, nodePos, nodePos + node.getLength());
			expNode.setInfo(node.getInitializer().toString());
			boolean covered = x2c.checkNode(expNode, nodePos, node.getLength());
			if (covered) {
				expNode.setCovered();
			}
			addToCursor(tmp, expNode);
			tmp = expNode;
		}
		return true;
	}

	public boolean visit(IfStatement node) {
		int nodePos = node.getStartPosition() + startPosition;
		System.out.println("IfStatement#visitor " + node.getExpression() + " pos: " + nodePos);

		// this is the if-Statement. the first element added to the main stream.
		INode ifNode = new CoverNode(node.getExpression().toString(), ASTNode.IF_STATEMENT);
		ifNode.setInfo(node.toString());
		boolean covered = x2c.checkNode(ifNode, nodePos, node.getLength());
		if (covered) {
			ifNode.setCovered();
		}
		ASTVisitor visitor = new ASTNodeVisitor4Cover(ifNode,x2c,startPosition);
		node.getThenStatement().accept(visitor);
		addToCursor(tmp, ifNode);
		tmp = ifNode;
		Statement elseSt = node.getElseStatement();
		if (null != elseSt) {
			// if there is a else-statement than it should be the third element
			// the second one is left empty for the main stream
			if(tmp.getSize()>0){
				tmp.getNodes().add(new FNode("", -1));
			}
			INode tn = new CoverNode("Else", ASTNode.EMPTY_STATEMENT);
			visitor = new ASTNodeVisitor4Cover(tn,x2c,startPosition);
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

		CoverNode tryNode = new CoverNode("Try", ASTNode.TRY_STATEMENT);
		tryNode.setInfo(node.toString());
		int nodePos = node.getStartPosition() + startPosition;
		boolean covered = x2c.checkNode(tryNode, nodePos, node.getLength());
		if (covered) {
			tryNode.setCovered();
		}
		ASTVisitor visitor1 = new ASTNodeVisitor4Cover(tryNode,x2c,startPosition);
		node.getBody().accept(visitor1);
		tryNode.getNodes().add(new CoverNode("", -1));

		List l = node.catchClauses();
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			CatchClause tNode = (CatchClause) iterator.next();
			System.out.println(tNode);
			ASTVisitor visitor = new ASTNodeVisitor4Cover(tryNode,x2c,startPosition);
			tNode.getBody().accept(visitor);
		}
		INode n1 = tryNode.getNodes().get(1);
		tryNode.addNode(n1);
		tryNode.getNodes().set(1, new CoverNode("", -1));
		Statement finallySt = node.getFinally();
		if (null != finallySt) {
			CoverNode finallyNode = new CoverNode("", ASTNode.CATCH_CLAUSE);
			visitor1 = new ASTNodeVisitor4Cover(finallyNode,x2c,startPosition);
			finallySt.accept(visitor1);
			tryNode.addNode(finallyNode);
		}
		addToCursor(tmp, tryNode);
		tmp = tryNode;


		return false;
	}

	public boolean visit(CatchClause node) {
		System.out.println("catch " + node);
		return true;
	}

	public boolean visit(ForStatement node) {
		System.out.println("ForStatement#visitor " + node.getExpression());
		CoverNode forNode = new CoverNode(node.getExpression().toString(), ASTNode.FOR_STATEMENT);
		forNode.setInfo(node.toString());
		int nodePos = node.getStartPosition() + startPosition;
		boolean covered = x2c.checkNode(forNode, nodePos, node.getLength());
		if (covered) {
			forNode.setCovered();
		}
		ASTVisitor visitor = new ASTNodeVisitor4Cover(forNode,x2c,startPosition);
		node.getBody().accept(visitor);
		addToCursor(tmp, forNode);
		tmp = forNode;
		return false;
	}

	public boolean visit(WhileStatement node) {
		System.out.println("WhileStatement#visitor " + node.getExpression());
		CoverNode forNode = new CoverNode(node.getExpression().toString(), ASTNode.FOR_STATEMENT);
		forNode.setInfo(node.toString());
		int nodePos = node.getStartPosition() + startPosition;
		boolean covered = x2c.checkNode(forNode, nodePos, node.getLength());
		if (covered) {
			forNode.setCovered();
		}
		ASTVisitor visitor = new ASTNodeVisitor4Cover(forNode,x2c,startPosition);
		node.getBody().accept(visitor);
		addToCursor(tmp, forNode);
		tmp = forNode;
		return false;
	}

	public boolean visit(DoStatement node) {
		System.out.println("DoStatement#visitor " + node.getExpression());
		CoverNode forNode = new CoverNode("do", ASTNode.DO_STATEMENT);
		forNode.setInfo(node.toString());
		int nodePos = node.getStartPosition() + startPosition;
		boolean covered = x2c.checkNode(forNode, nodePos, node.getLength());
		
		ASTVisitor visitor = new ASTNodeVisitor4Cover(forNode,x2c,startPosition);
		node.getBody().accept(visitor);
		CoverNode whileNode = new CoverNode(node.getExpression().toString(), ASTNode.WHILE_STATEMENT);
		forNode.addNode(whileNode);
		if (covered) {
			forNode.setCovered();
		}
		if (x2c.checkLoopNode(forNode, nodePos, node.getLength())){
			whileNode.setCovered();
		}
		
		addToCursor(tmp, forNode);
		tmp = whileNode;
		return false;
	}

	public boolean visit(SwitchStatement node) {
		System.out.println("SwitchStatement#visitor " + node);
		CoverNode switchNode = new CoverNode(node.getExpression().toString(), ASTNode.SWITCH_STATEMENT);
		switchNode.setInfo(node.toString());
		switchNode.addNode(new CoverNode("", -1));// fill the first two nodes
		switchNode.addNode(new CoverNode("", -1));
		int nodePos = node.getStartPosition() + startPosition;
		boolean covered = x2c.checkNode(switchNode, nodePos, node.getLength());
		if (covered) {
			switchNode.setCovered();
		}
		INode tCase = null;
		List l = node.statements();
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			Statement tNode = (Statement) iterator.next();
			System.out.println(tNode);
			if (tNode instanceof SwitchCase) {
				tCase = new CoverNode(tNode.toString(), ASTNode.EXPRESSION_STATEMENT);
				switchNode.addNode(tCase);
				continue;
			}
			ASTVisitor visitor = new ASTNodeVisitor4Cover(tCase,x2c,startPosition);
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
		System.out.println("BreakStatement#visitor " + node);
		INode breakNode = new CoverNode(node.getClass().getSimpleName(), ASTNode.BREAK_STATEMENT);
		addToCursor(tmp, breakNode);
		return false;
	}
	public boolean visit(ContinueStatement node) {
		INode breakNode = new CoverNode(node.getClass().getSimpleName(), ASTNode.CONTINUE_STATEMENT);
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
		System.out.println(node.getClass().getSimpleName());
	}

	private void addToCursor(INode tmp, INode ast) {
		if (tmp.getNodes().size() >= 2) {// its no Statement
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
