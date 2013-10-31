package de.htwg.flowchartgenerator.controller;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.swt.widgets.Shell;

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
