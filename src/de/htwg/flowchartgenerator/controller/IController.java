package de.htwg.flowchartgenerator.controller;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Controller interface
 * @author Aldi Alimucaj
 *
 */
public interface IController {
	public void operate();
	public void operateFromTest();
	public void operateFromChosenTest();
	public void setPosition(int pos);
	public int getPosition();
	public ICompilationUnit getCompUnit();
	public Shell getShell();
}
