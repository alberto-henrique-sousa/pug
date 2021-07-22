/*******************************************************************************
 * Copyright 2013 Pug Framework. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.pugsource.plugin.wizards.crud;

import java.util.ArrayList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.internal.core.DesignerPlugin;
import org.eclipse.wb.internal.core.utils.dialogfields.ComboDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogFieldUtils;
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;
import org.eclipse.wb.internal.core.utils.dialogfields.StringDialogField;

import com.google.gdt.eclipse.designer.util.Utils;
import com.pugsource.plugin.wizards.AbstractGwtComposite;
import com.pugsource.plugin.wizards.ClientPackageSelectionDialogField;

/**
 * @author alberto
 */
public class CRUDGWTComposite1 extends AbstractGwtComposite {
	ClientPackageSelectionDialogField m_clientPackageField;
	private final StringDialogField m_serviceField;
	private final StringDialogField m_winSize;
	private final StringDialogField m_title;
	ComboDialogField listClass;
	ComboDialogField template;
	IPackageFragment selectedPackage;
	private TableDialogField tableDialogFieldOrder;
	private boolean updateList;
	private int lastClass;
    public CRUDGWTComposite2 pageComposite2;
    public CRUDGWTComposite3 pageComposite3;
    public static ArrayList<String> listForms;

	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public CRUDGWTComposite1(Composite parent,
			int style,
			IMessageContainer messageContainer,
			IPackageFragment selectedPackage) {
		super(parent, style, messageContainer);
		//
		this.lastClass = -1;
		this.selectedPackage = selectedPackage;
		int columns = 3;
		setLayout(new GridLayout(columns, false));
		// client package
		{
			m_clientPackageField = new ClientPackageSelectionDialogField("Client package:", "&Browse...");
			m_clientPackageField.setDialogFieldListener(m_validateListener);
			DialogFieldUtils.fillControls(this, m_clientPackageField, columns, 60);
		}
		// service name
		{
			m_serviceField = new StringDialogField();
			m_serviceField.setDialogFieldListener(m_validateListener);
			m_serviceField.setLabelText("Class &name:");
			DialogFieldUtils.fillControls(this, m_serviceField, columns, 60);
			m_serviceField.getTextControl(null).setFocus();
		}
		// title
		{
			m_title = new StringDialogField();
			m_title.setDialogFieldListener(m_validateListener);
			m_title.setLabelText("&Title:");
			m_title.setText("");
			DialogFieldUtils.fillControls(this, m_title, columns, 60);
		}				
		// width, height
		{
			m_winSize = new StringDialogField();
			m_winSize.setDialogFieldListener(m_validateListener);
			m_winSize.setLabelText("&Size (Width,Height):");
			m_winSize.setText("100%,400px");
			DialogFieldUtils.fillControls(this, m_winSize, columns, 60);
		}				
		// initialize fields
		m_clientPackageField.setPackageFragment(selectedPackage);
		m_serviceField.setText("");
		
		{
			listClass = new ComboDialogField(SWT.READ_ONLY);
			listClass.setLabelText("Class &DTO:");
			listClass.setDialogFieldListener(m_validateListener);
			listClass.doFillIntoGrid(this, columns);
		}
							
		{
			template = new ComboDialogField(SWT.READ_ONLY);
			template.setItems(new String[]{
	                "Basic", "Login"});		
			template.setLabelText("Template:");
			template.setDialogFieldListener(m_validateListener);
			template.doFillIntoGrid(this, columns);
			template.selectItem(0);
		}
		
		{
		    Label labelOrder = new Label(this, SWT.NONE);
		    labelOrder.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		    labelOrder.setText("&Order:");
			
		    tableDialogFieldOrder = new TableDialogField(this, 0, "Order", 1);

		    final TableColumn titleTableColumn = new TableColumn(tableDialogFieldOrder.getTable(), SWT.NONE);
		    titleTableColumn.setWidth(160);
		    titleTableColumn.setText("Title");		    
			
		    final TableColumn fieldTableColumn = new TableColumn(tableDialogFieldOrder.getTable(), SWT.NONE);
		    fieldTableColumn.setWidth(160);
		    fieldTableColumn.setText("Field");		    
		    
		    new Label(this, SWT.NONE);
		}		
					
		listForms = new ArrayList<String>();
		if (selectedPackage != null && selectedPackage.getJavaProject() != null) {
			try {
				IPackageFragment[] pkgs = selectedPackage.getJavaProject().getPackageFragments();
				for (IPackageFragment iPackageFragment : pkgs) {
					ICompilationUnit[] compUnits = iPackageFragment.getCompilationUnits();
					for (ICompilationUnit iCompilationUnit : compUnits) {
						CompilationUnit ast = getAst(iCompilationUnit, false);
						for(AbstractTypeDeclaration type : (java.util.List<AbstractTypeDeclaration>)ast.types()) {
							if(type instanceof TypeDeclaration) { 
								TypeDeclaration td = (TypeDeclaration)type;		
								String pkg = td.resolveBinding().getPackage().getName();
								if (pkg.length() > 4 && (pkg.toLowerCase().indexOf(".dto.") > -1 ||
										pkg.toLowerCase().substring(pkg.length()-4).equals(".dto"))) {
									listClass.addItem(td.resolveBinding().getName() + " (" + pkg + ")");
								}
								if (pkg.equals(selectedPackage.getElementName())) {
									listForms.add(td.resolveBinding().getName());
								}									
							}
						}
					}
				}    			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static CompilationUnit getAst(ICompilationUnit compUnit, boolean resolveBindings) {
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setResolveBindings(true);
		astParser.setBindingsRecovery(true);
		astParser.setStatementsRecovery(true);
		astParser.setSource(compUnit);
		return (CompilationUnit) astParser.createAST(null);
		//return SharedASTProvider.getAST(compUnit, SharedASTProvider.WAIT_YES, new NullProgressMonitor());
	}

	////////////////////////////////////////////////////////////////////////////
	//
	// Validation
	//
	////////////////////////////////////////////////////////////////////////////
	@Override
	protected String validate() {
		IPackageFragment packageFragment = m_clientPackageField.getPackageFragment();
		if (listClass != null && listClass.getSelectionIndex() > -1) {
			if (lastClass != listClass.getSelectionIndex()) {
				updateList = true;
				lastClass = listClass.getSelectionIndex();
			}	
			addListFields();
		}	
		// validate package
		{
			if (packageFragment == null) {
				return "Select client package.";
			}
			try {
				if (!Utils.isModuleSourcePackage(packageFragment)) {
					return "Package " + packageFragment.getElementName() + " is not a client package.";
				}
			} catch (Throwable e) {
				return "Exception: " + e.getMessage();
			}
		}
		// validate service name
		{
			String serviceName = m_serviceField.getText();
			if (serviceName.length() == 0) {
				return "Class name can not be empty.";
			}
			// check that service name is valid identifier
			IStatus status = JavaConventions.validateIdentifier(serviceName, null, null);
			if (status.getSeverity() == IStatus.ERROR) {
				return status.getMessage();
			}
			String firstChar = serviceName.substring(0, 1);
			if (!firstChar.equals(firstChar.toUpperCase())) {
				return "Type name is discouraged. By convention, Java type names usually start with an uppercase letter.";				
			}      
			// check that there are no class with same name
			try {
				String qualifiedServiceName = packageFragment.getElementName() + "." + serviceName;
				if (serviceName.toLowerCase().indexOf("form") == -1)
					qualifiedServiceName += "Form";
				if (packageFragment.getJavaProject().findType(qualifiedServiceName) != null) {
					return "Type with such name already exists.";
				}
			} catch (Throwable e) {
				DesignerPlugin.log(e);
			}
		}
		{
			if (listClass.getSelectionIndex() == -1) 
				return "Choose the class DTO.";
			if (template.getSelectionIndex() == -1) {
				return "Choose the template.";
			}
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////
	//
	// Access
	//
	////////////////////////////////////////////////////////////////////////////
	public IPackageFragment getPackageFragment() {
		return m_clientPackageField.getPackageFragment();
	}

	public String getServiceName() {
		String name = m_serviceField.getText();
		if (name.length() < 4 || !name.toLowerCase().substring(name.length()-4).equals("form")) {
			name += "Form";
		}
		return name;
	}
	
	public String getClassDAO() {
		String name = "";
		if (listClass.getSelectionIndex() > -1)
			name = listClass.getText();
		return name;
	}
	
	public String getTitle() {
		return m_title.getText();
	}
	
	public String getWinSize() {
		return m_winSize.getText();
	}	
	
	public String getExternalForm() {
		return "";//pageComposite3.dialogBoxForm.getText();
	}	
			
	public String getTemplate() {
		return template.getText();
	}
	
	public TableItem[] getOrder() {
		return tableDialogFieldOrder.getTable().getItems();
	}
	
	public TableItem[] getSearch() {
		return pageComposite2.tableDialogFieldSearch.getTable().getItems();
	}
	
	public TableItem[] getGrid() {
		return pageComposite2.tableDialogFieldGrid.getTable().getItems();
	}
	
	public TableItem[] getForm() {
		return pageComposite3.tableDialogFieldForm.getTable().getItems();
	}
	
	public int getTitlePosition() {
		return pageComposite3.labelPosition.getSelectionIndex();
	}
	
	@SuppressWarnings("unchecked")
	private void addListFields() {
		if (updateList) {
			updateList = false;
			tableDialogFieldOrder.getTable().removeAll();
			pageComposite2.tableDialogFieldSearch.getTable().removeAll();
			pageComposite2.tableDialogFieldGrid.getTable().removeAll();
			pageComposite3.tableDialogFieldForm.getTable().removeAll();
			/*if (pageComposite3.dialogBoxForm.getItemCount() == 1) {
				for (String form : listForms) {
					pageComposite3.dialogBoxForm.addItem(form);				
				}
			}*/
			try {
				IPackageFragment[] pkgs = selectedPackage.getJavaProject().getPackageFragments();
				for (IPackageFragment iPackageFragment : pkgs) {
					ICompilationUnit[] compUnits = iPackageFragment.getCompilationUnits();
					for (ICompilationUnit iCompilationUnit : compUnits) {
						CompilationUnit ast = getAst(iCompilationUnit, false);
						for(AbstractTypeDeclaration type : (java.util.List<AbstractTypeDeclaration>)ast.types()) {
							if(type instanceof TypeDeclaration) { 
								TypeDeclaration td = (TypeDeclaration)type;
								if (getClassDAO().equals(td.resolveBinding().getName() + " (" +td.resolveBinding().getPackage().getName() + ")")) {							
									IVariableBinding[] iVariableBinding = td.resolveBinding().getDeclaredFields();
									for (IVariableBinding iVariableBinding2 : iVariableBinding) {
										String fieldName = iVariableBinding2.getName();
										String fieldType = CreateCRUDGWTOperation.fieldTypeDTO(iVariableBinding2.getType().getName());
										String typeComp = typeComp(fieldType);
										String typeCell = typeCell(typeComp);
										String validation = "";
										String msgValidation = "";
										String fieldNameCamel = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
										if (typeComp.equals("TextBox")) {
											validation = "!" + fieldName + "Input" + ".getValue().trim().isEmpty()";
											msgValidation = "Informe o " + fieldNameCamel;
										} else if (typeComp.equals("DateBox") || 
												typeComp.equals("IntegerBox") || 
												typeComp.equals("DoubleBox") || 
												typeComp.equals("LongBox")) {
											validation = fieldName + "Input" + ".getValue() != null";
											msgValidation = "Informe o " + fieldNameCamel;
										}  
										if (CreateCRUDGWTOperation.validFieldName(fieldName, fieldType) && !fieldName.toLowerCase().equals("sizedata")) {
											
											// page 1
											TableItem item1 = new TableItem (tableDialogFieldOrder.getTable(), SWT.NONE);
											item1.setText(new String [] {fieldNameCamel, fieldName});
											
											// page 2
											
											TableItem item2 = new TableItem (pageComposite2.tableDialogFieldSearch.getTable(), SWT.NONE);
											item2.setText(new String [] {fieldNameCamel + ":", fieldName + "Search", typeComp, ""});
											
											TableItem item3 = new TableItem (pageComposite2.tableDialogFieldGrid.getTable(), SWT.NONE);
											item3.setText(new String [] {fieldNameCamel, fieldName, fieldName + "Column", typeCell, fieldType, "0", ""});
											
											// page 3
											TableItem item4 = new TableItem (pageComposite3.tableDialogFieldForm.getTable(), SWT.NONE);
											item4.setText(new String [] {fieldNameCamel + ":", fieldName, "false", fieldName + "Input", typeComp, "", "0", typeComp.equals("TextBox") ? "200px" : "0px", "", validation, msgValidation});
										}	
									}
								}	
							}
						}
					}
				}    			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String typeComp(String type) {
		String ret = "TextBox";
		if (type.equals("int") || type.equals("Integer"))
			ret = "IntegerBox";
		else if (type.toLowerCase().equals("long"))
			ret = "LongBox";
		else if (type.toLowerCase().equals("double"))
			ret = "DoubleBox";
		else if (type.toLowerCase().equals("date"))
			ret = "DateBox";
		else if (type.toLowerCase().equals("boolean"))
			ret = "CheckBox";
		return ret;
	}
	
	public String typeCell(String type) {
		String ret = "TextCell";
		if (type.equals("IntegerBox") || type.equals("LongBox") || type.equals("DoubleBox"))
			ret = "NumberCell";
		else if (type.equals("DateBox"))
			ret = "DateCell";
		return ret;
	}	
	
}
