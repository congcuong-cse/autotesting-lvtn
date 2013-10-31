package hcmut.autotestgenerator.popup.actions;

import hcmut.autotestgenerator.controller.Controller;
import hcmut.autotestgenerator.controller.IController;
import hcmut.autotestgenerator.utils.BundleChecker;
import hcmut.autotestgenerator.utils.Statics;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;


/**
 * This Action is for the covered graph. Check if the bundle is installed. if
 * not an error message is shown.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class CoverAction implements IObjectActionDelegate {
	private IWorkbenchPart targetPart;
	private Shell shell;
	private ISelection fSelection;
	private ICompilationUnit cu;
	private boolean enabled = true;

	/**
	 * Constructor for Action1.
	 */
	public CoverAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
		this.targetPart = targetPart;
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (enabled) {
			cu = getCompilationUnit().getCompilationUnit();
			IController know = Controller.newInstance(cu, getCompilationUnit(), targetPart);
			know.operateFromTest();
		} else {
			MessageDialog.openError(shell, Statics.EDITOR_NAME,
					"Looks like CodeCover is not installed. Please check it!\nIn case you dont have it yet, you can get it at www.codecover.org");
		}

	}

	private IMethod getCompilationUnit() {
		return (IMethod) ((IStructuredSelection) fSelection).getFirstElement();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.fSelection = selection;
		if (!BundleChecker.hasCodeCover()) {
			enabled = false;
		}

	}

}
