/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.*;

public class JavaTest {
	public static void main(String[] args) throws Exception {
		ANTLRInputStream input = new ANTLRFileStream(args[0]);
		//ANTLRInputStream input = new ANTLRFileStream("input1.java");
		JavaLexer lexer = new JavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);
		parser.setBuildParseTree(true);
		RuleContext tree = parser.methodDeclaration();
		tree.inspect(parser); // show in gui
		//tree.save(parser, "out.txt"); // Generate postscript
		System.out.println(tree.toStringTree(parser));
	}
}
