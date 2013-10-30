package de.htwg.flowchartgenerator.editor;

import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

import de.htwg.flowchartgenerator.controller.NodeFolder;
import de.htwg.flowchartgenerator.utils.ResourcesUtils;

/**
 * The Mouse listener
 * 
 * @author a
 * 
 */
public class GraphMouseListener implements MouseListener {
	Graph selectedGraph;
	private static String ICON_MINS = "/icons/minus.png";
	private static String ICON_PLUS = "/icons/plus.png";
	private static String ICON_CANCEL = "/icons/cancel.png";
	Display display;
	List<GraphNode> colapseList;

	public void mouseDoubleClick(MouseEvent e) {
	}

	public void mouseDown(MouseEvent e) {
		if (e.button == SWT.MouseDown) {
			display = ((Control) e.getSource()).getDisplay();
			colapseList = ((Graph) e.getSource()).getSelection();
			selectedGraph = ((Graph) e.getSource());
			creanteMenu();
		}
	}

	/**
	 * Creates the context menu from the right click.
	 */
	private void creanteMenu() {
		Menu menu = new Menu(display.getActiveShell(), SWT.POP_UP);
		// the collapse button
		MenuItem collapse = new MenuItem(menu, SWT.PUSH);
		collapse.setText("Collapse");
		collapse.addListener(SWT.Selection, new CollapseListener(colapseList, display.getActiveShell(), selectedGraph));
		collapse.setImage(new Image(selectedGraph.getDisplay(), ResourcesUtils.getLocation().toString() + ICON_MINS));
		// the expand button
		MenuItem expand = new MenuItem(menu, SWT.PUSH);
		expand.setText("Expand");
		expand.addListener(SWT.Selection, new ExpandListener(colapseList, display.getActiveShell(), selectedGraph));
		expand.setImage(new Image(selectedGraph.getDisplay(), ResourcesUtils.getLocation().toString() + ICON_PLUS));
		// the hide button
		MenuItem hide = new MenuItem(menu, SWT.PUSH);
		hide.setText("Hide");
		hide.addListener(SWT.Selection, new CloseListener(colapseList, display.getActiveShell(), selectedGraph));
		hide.setImage(new Image(selectedGraph.getDisplay(), ResourcesUtils.getLocation().toString() + ICON_CANCEL));
		PointerInfo info = MouseInfo.getPointerInfo();
		java.awt.Point location = info.getLocation();
		menu.setLocation(location.x, location.y);
		menu.setVisible(true);

		while (!menu.isDisposed() && menu.isVisible()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		menu.dispose();
	}

	public Image getImageIcon(String name) {
		return new Image(selectedGraph.getDisplay(), ClassLoader.getSystemResource(name).toString());
	}

	public void mouseUp(MouseEvent e) {

	}

	/**
	 * The Listener for the collapse action.
	 * 
	 * @author Aldi Alimucaj
	 * 
	 */
	static class CollapseListener implements Listener {
		List<GraphNode> colapseList;
		Shell shell;
		Graph graph;

		public CollapseListener(List<GraphNode> colapseList, Shell shell, Graph graph) {
			this.colapseList = colapseList;
			this.shell = shell;
			this.graph = graph;
		}

		public void handleEvent(Event e) {
			if (colapseList.size() > 1) {
				MessageDialog.openError(shell, "Flow Plug-in", "Curent node selection: " + colapseList.size() + "\nPlease select just one node ");
			}
			if (colapseList.size() == 1) {
				new NodeFolder(colapseList.get(0), graph, true);
			}
		}
	}

	/**
	 * The Listener for the expand action.
	 * 
	 * @author Aldi Alimucaj
	 * 
	 */
	static class ExpandListener implements Listener {
		List<GraphNode> colapseList;
		Shell shell;
		Graph graph;

		public ExpandListener(List<GraphNode> colapseList, Shell shell, Graph graph) {
			this.colapseList = colapseList;
			this.shell = shell;
			this.graph = graph;
		}

		public void handleEvent(Event e) {
			if (colapseList.size() > 1) {
				MessageDialog.openError(shell, "Flow Plug-in", "Curent node selection: " + colapseList.size() + "\nPlease select just one node ");
			}
			if (colapseList.size() == 1) {
				new NodeFolder(colapseList.get(0), graph, false);
			}
		}
	}

	/**
	 * The Listener for the hide action.
	 * 
	 * @author Aldi Alimucaj
	 * 
	 */
	static class CloseListener implements Listener {
		List<GraphNode> colapseList;
		Shell shell;
		Graph graph;

		public CloseListener(List<GraphNode> colapseList, Shell shell, Graph graph) {
			this.colapseList = colapseList;
			this.shell = shell;
			this.graph = graph;
		}

		public void handleEvent(Event e) {
			if (colapseList.size() > 1) {
				MessageDialog.openError(shell, "Flow Plug-in", "Curent node selection: " + colapseList.size() + "\nPlease select just one node ");
			}
			if (colapseList.size() == 1) {
				colapseList.get(0).setVisible(false);

			}
		}
	}

}
