package hcmut.autotestgenerator.editor;

import hcmut.autotestgenerator.utils.Statics;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;


/**
 * The outline page of the graph. Displays various information in the outline
 * view of the editor.
 * 
 * @author Aldi Alimucaj
 * 
 */
public class FlowChartOutlinePage extends Page implements IContentOutlinePage,
		ISelectionChangedListener {
	private ListenerList selectionChangedListeners = new ListenerList();
	private GraphicalViewer gViewer;
	private Composite parent;
	private OutlineModel outlineModel;
	private StyledText info;
	protected FlowChartOutlinePage() {
		super();
	}

	public FlowChartOutlinePage(OutlineModel outlineModel2) {
		this.outlineModel = outlineModel2;
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	public void createControl(Composite parent) {
		this.parent = parent;
	}

	/**
	 * Fires a selection changed event.
	 * 
	 * @param selection
	 *            the new selection
	 */
	protected void fireSelectionChanged(ISelection selection) {
		// create an event
		final SelectionChangedEvent event = new SelectionChangedEvent(this,
				selection);

		// fire the event
		Object[] listeners = selectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			SafeRunner.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(event);
				}
			});
		}
	}

	/*
	 * (non-Javadoc) Method declared on IPage (and Page).
	 */
	public Control getControl() {
		Composite c = new Composite(parent, 0);
		c.setLayout(new GridLayout(1, true));

		StyledText head = new StyledText(c, SWT.LEFT_TO_RIGHT);
		head.setBackground(head.getParent().getBackground());
		head.setFont(new Font(null, "Arial", 12, 0));
		head.setAlignment(SWT.CENTER);
		head.setText("Flow Chart Generator");

		info = new StyledText(c, SWT.LEFT_TO_RIGHT);
		info.setBackground(head.getParent().getBackground());
		info.setText(genInfo(Statics.MACCABE_MAX_RES));

		Table table = new Table(c, SWT.NO_FOCUS);
		TableColumn tc1 = new TableColumn(table,SWT.LEFT);
		tc1.setWidth(90);
		TableColumn tc2 = new TableColumn(table,SWT.LEFT);
		tc2.setWidth(30);
		table.setBackground(c.getBackground());
		setInfo(table);
		
		StyledText extra = new StyledText(c, SWT.MULTI);
		extra.setBackground(head.getParent().getBackground());
		extra.setFont(new Font(null, "Arial", 8, 0));
		extra
				.setText("*Whether the MacCabe result is \nunder the limit or not, it depends\non the limit itself.");

		new Label(c, SWT.LEFT);
		
		/*
		 * Button print = new Button(c, SWT.PUSH); print.setText("Print");
		 * print.setEnabled(false); print.addSelectionListener(new
		 * SelectionAdapter() { public void widgetSelected(SelectionEvent e) {
		 * //TODO still to implement } });
		 */
		return (Control) c;
	}

	private void setInfo(Table table) {
		int cons = outlineModel.getConnections();
		int nodes = outlineModel.getNodes();
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { "Nodes:", String.valueOf(nodes)});
		TableItem item1 = new TableItem(table, SWT.NONE);
		item1.setText(new String[] { "Connections:", String.valueOf(cons)});
		TableItem item2 = new TableItem(table, SWT.NONE);
		TableEditor editor = new TableEditor(table);
		StyledText limit = new StyledText(table, SWT.MULTI);
		limit.setText(" Limit:");
		limit.setBackground(table.getBackground());
		editor.grabHorizontal = true;
	    editor.setEditor(limit, item2, 0);
		editor = new TableEditor(table);
		final Spinner sp = new Spinner(table,SWT.None);
		sp.setValues(Statics.MACCABE_MAX_RES, 0, 20, 0, 1, 1);
		sp.setBackground(table.getBackground());
		sp.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				info.setText(genInfo(sp.getSelection()));			
			}
			
		});
		
		editor.grabHorizontal = true;
	    editor.setEditor(sp, item2, 1);
	    
		item2.setData(sp);
	}
	private String genInfo(int lim) {
		int cons = outlineModel.getConnections();
		int nodes = outlineModel.getNodes();
		int result = (cons - nodes + 2);
		String generatorInfo = "MacCabe results\n"+ cons + " - " + nodes
				+ " + 2 = " + result + "\n*Satisfied :\t\t"
				+ (result <= lim)+"  ";
		return generatorInfo;
	}

	/*
	 * (non-Javadoc) Method declared on ISelectionProvider.
	 */
	public ISelection getSelection() {
		if (gViewer == null) {
			return StructuredSelection.EMPTY;
		}
		return StructuredSelection.EMPTY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
	 */
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		pageSite.setSelectionProvider(this);
	}

	/*
	 * (non-Javadoc) Method declared on ISelectionProvider.
	 */
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	/*
	 * (non-Javadoc) Method declared on ISelectionChangeListener. Gives
	 * notification that the tree selection has changed.
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		fireSelectionChanged(event.getSelection());
	}

	/**
	 * Sets focus to a part in the page.
	 */
	public void setFocus() {
	}

	/*
	 * (non-Javadoc) Method declared on ISelectionProvider.
	 */
	public void setSelection(ISelection selection) {
		if (gViewer != null) {
			gViewer.setSelection(selection);
		}
	}

	public static class OutlineModel {
		int nodes;
		int connections;

		public int getNodes() {
			return nodes;
		}

		public void setNodes(int nodes) {
			this.nodes = nodes;
		}

		public int getConnections() {
			return connections;
		}

		public void setConnections(int connections) {
			this.connections = connections;
		}

		public OutlineModel(int nodes, int connections) {
			super();
			this.nodes = nodes;
			this.connections = connections;
		}

	}

}
