package hcmut.autotestgenerator.popup.actions;

import hcmut.autotestgenerator.controller.Controller;
import hcmut.autotestgenerator.controller.IController;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;


/**
 * The main action. Called from the build button.
 * @author Aldi Alimucaj
 *
 */
public class MainAction implements IObjectActionDelegate {
	private IWorkbenchPart targetPart;
	private ISelection fSelection;
	private ICompilationUnit cu;

	/**
	 * Constructor for Action1.
	 */
	public MainAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		cu = getCompilationUnit().getCompilationUnit();
		IController know = Controller.newInstance(cu, getCompilationUnit(),targetPart);
		know.operate();
	}

	private IMethod getCompilationUnit() {
		return (IMethod) ((IStructuredSelection) fSelection).getFirstElement();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.fSelection = selection;
	}

}
