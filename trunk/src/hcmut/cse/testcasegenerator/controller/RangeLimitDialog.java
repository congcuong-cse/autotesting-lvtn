package hcmut.cse.testcasegenerator.controller;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jdt.core.ILocalVariable;

public class RangeLimitDialog extends Dialog {
  private Text txtMin;
  private Text txtMax;
  private String min = "";
  private String max = "";
  private String varname="";

  public RangeLimitDialog(Shell parentShell, String varname) {
    super(parentShell);
    this.varname = varname;
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);
    GridLayout layout = new GridLayout(2, false);
    layout.marginRight = 15;
    layout.marginLeft = 20;
    container.setLayout(layout);

    Label lblMin = new Label(container, SWT.NONE);
    lblMin.setText("Min:");

    txtMin = new Text(container, SWT.BORDER);
    txtMin.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
        1, 1));
    txtMin.setText(min);

    Label lblMax = new Label(container, SWT.NONE);
    GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false,
        false, 1, 1);
    gd_lblNewLabel.horizontalIndent = 1;
    lblMax.setLayoutData(gd_lblNewLabel);
    lblMax.setText("Max:");

    txtMax = new Text(container, SWT.BORDER);
    txtMax.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
        false, 1, 1));
    txtMax.setText(max);
    return container;
  }

  // override method to use "Login" as label for the OK button
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, "OK", true);
    createButton(parent, IDialogConstants.CANCEL_ID,
        IDialogConstants.CANCEL_LABEL, false);
  }

  @Override
  protected Point getInitialSize() {
    return new Point(450, 300);
  }
  
  //overriding this methods allows you to set the
  // title of the custom dialog
  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText("Input range limit for parameter " + varname);
  }
  @Override
  protected void okPressed() {
    // Copy data from SWT widgets into fields on button press.
    // Reading data from the widgets later will cause an SWT
    // widget diposed exception.
    min = txtMin.getText();
    max = txtMax.getText();
    super.okPressed();
  }

  public String getMin() {
    return min;
  }

  public void setMin(String min) {
    this.min = min;
  }

  public String getMax() {
    return max;
  }

  public void setMax(String max) {
    this.max = max;
  }

} 
