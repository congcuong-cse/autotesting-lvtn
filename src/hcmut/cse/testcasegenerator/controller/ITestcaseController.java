package hcmut.cse.testcasegenerator.controller;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Controller interface
 * @author Aldi Alimucaj
 *
 */
public interface ITestcaseController {
	public void operate();
	public void setPosition(int pos);
	public int getPosition();
	public ICompilationUnit getCompUnit();
	public Shell getShell();
}
