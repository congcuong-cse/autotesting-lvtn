/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.Trees;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JavaTest {
	public static void main(String[] args) throws Exception {
		ANTLRInputStream input = new ANTLRFileStream(args[0]);
		//ANTLRInputStream input = new ANTLRFileStream("input1.java");
		JavaLexer lexer = new JavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);
		parser.setBuildParseTree(true);
		//RuleContext tree = parser.compilationUnit();		
		RuleContext tree = parser.methodDeclaration();

        TestInfo testInfo = new TestInfo();
        testInfo.getTestInfo(tree, parser);
        System.out.println(testInfo.toString());
        System.out.println(tree.getChild(3).getText());
		tree.inspect(parser); // show in gui

		//StringBuilder buf = new StringBuilder();
        //buf.append("(");
        String s = Utils.escapeWhitespace(Trees.getNodeText(tree.getChild(2).getChild(1).getChild(0), parser), false);
        //buf.append(s);
        System.out.println(s);
		tree.inspect(parser); // show in gui
		//tree.save(parser, "out.txt"); // Generate postscript
		System.out.println(tree.toStringTree(parser));
	}
}
