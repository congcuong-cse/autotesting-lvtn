package hcmut.autotestgenerator.utils;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class SessionSelector {
	Shell shell;
	Display display;
	java.util.List<File> fileList;
	File f = null;
	public SessionSelector(Shell shell, java.util.List<File> fileList) {
		display = shell.getDisplay();
		this.shell = new Shell(display);
		this.fileList=fileList;
	}

	public File getFile() {
		showWindow();
		return f;
	}

	private void showWindow() {
		shell.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData();
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		gridData.heightHint = 200;
		list.setLayoutData(gridData);
		for (Iterator iterator = fileList.iterator(); iterator.hasNext();) {
			File type = (File) iterator.next();
			list.add(type.getName());
		}
		list.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				int[] selectedItems = list.getSelectionIndices();
							
				int sel = list.getSelectionIndex();
				f = fileList.get(sel);
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				int[] selectedItems = list.getSelectionIndices();
				int sel = list.getSelectionIndex();
				f = fileList.get(sel);
			}
		});
		
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = SWT.FILL;

		Button button1 = new Button(shell, SWT.PUSH);
		button1.setText("OK");
		
		button1.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				shell.dispose();				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		Button button2 = new Button(shell, SWT.PUSH);
		button2.setText("Cancel");
		button2.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				shell.dispose();				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		button1.setLayoutData(gridData);
		button2.setLayoutData(gridData);
		
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed() && shell.isVisible()) {
	          if (!display.readAndDispatch())
	            display.sleep();
	        }
	   shell.dispose();
	}
}
