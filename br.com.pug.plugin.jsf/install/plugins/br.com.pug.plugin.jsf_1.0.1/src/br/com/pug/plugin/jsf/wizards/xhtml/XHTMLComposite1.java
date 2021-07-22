/*******************************************************************************
 * Copyright 2016 Pug Plugin. All Rights Reserved.
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
package br.com.pug.plugin.jsf.wizards.xhtml;

import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.internal.core.DesignerPlugin;
import org.eclipse.wb.internal.core.utils.dialogfields.ComboDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogFieldUtils;
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;
import org.eclipse.wb.internal.core.utils.dialogfields.StringDialogField;

import br.com.pug.plugin.jsf.wizards.AbstractComposite;
import br.com.pug.plugin.jsf.wizards.ClientPackageSelectionDialogField;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class XHTMLComposite1 extends AbstractComposite {
	ClientPackageSelectionDialogField m_clientPackageField;
	private final StringDialogField m_serviceField;
	private final StringDialogField m_title;
	ComboDialogField listClass;
	IPackageFragment selectedPackage;
	private StringDialogField webContentText;
	private boolean updateList;
	private int lastClass;
	public XHTMLComposite2 pageComposite2;
	public XHTMLComposite3 pageComposite3;
	public static ArrayList<String> listForms;

	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public XHTMLComposite1(Composite parent,
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
			m_clientPackageField = new ClientPackageSelectionDialogField("Bean package:", "&Browse...");
			m_clientPackageField.setDialogFieldListener(m_validateListener);
			DialogFieldUtils.fillControls(this, m_clientPackageField, columns, 60);
		}
		// service name
		{
			m_serviceField = new StringDialogField();
			m_serviceField.setDialogFieldListener(m_validateListener);
			m_serviceField.setLabelText("XHTML &name:");
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
		// initialize fields
		m_clientPackageField.setPackageFragment(selectedPackage);
		m_serviceField.setText("");

		{
			listClass = new ComboDialogField(SWT.READ_ONLY);
			listClass.setLabelText("Class &DAO:");
			listClass.setDialogFieldListener(m_validateListener);
			listClass.doFillIntoGrid(this, columns);
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
								if (pkg.length() > 4 && (pkg.toLowerCase().indexOf(".dao.") > -1 ||
										pkg.toLowerCase().substring(pkg.length()-4).equals(".dao"))) {
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

		{
			webContentText = new StringDialogField();
			webContentText.setDialogFieldListener(m_validateListener);
			webContentText.setLabelText("&WebContent:");
			webContentText.setText("");
			DialogFieldUtils.fillControls(this, webContentText, columns, 60);
			webContentText.setText("/WebContent/pages");
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
			if (packageFragment == null || packageFragment.getElementName().indexOf(".bean") == -1) {
				return "Select bean package.";
			}
		}
		// validate service name
		{
			String serviceName = m_serviceField.getText();
			if (serviceName.length() == 0) {
				return "XHTML name can not be empty.";
			}
			// check that there are no class with same name
			try {
				IJavaProject javaProject = packageFragment.getJavaProject();
				IProject project = javaProject.getProject();			    
				IFolder pagesFolder = project.getFolder(getWebContext());
				if (pagesFolder.getFile(serviceName + ".xhtml").exists()) {				
					return "Type with such name already exists.";
				}
			} catch (Throwable e) {
				DesignerPlugin.log(e);
			}
		}
		{
			if (listClass.getSelectionIndex() == -1) 
				return "Choose the class DAO.";
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

	public String getWebContext() {
		return webContentText.getText();
	}

	public String getServiceName() {
		String name = m_serviceField.getText();
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

	public String getExternalForm() {
		return "";//pageComposite3.dialogBoxForm.getText();
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
										String fieldLength = "";
										String fieldWidth = "";
										String fieldName = iVariableBinding2.getName();
										String fieldType = CreateXHTMLperation.fieldTypeDTO(iVariableBinding2.getType().getName());
										String typeComp = typeComp(fieldType);
										String typeCell = typeCell(fieldType);
										String validation = "";
										String msgValidation = "";
										String fieldNameCamel = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
										String mask = "";

										IMethodBinding[] iMethodBindig = td.resolveBinding().getDeclaredMethods();
										for (IMethodBinding iMethodBinding : iMethodBindig) {
											if (iMethodBinding.getName().toLowerCase().equals("get" + iVariableBinding2.getName().toLowerCase())) { 
												IAnnotationBinding[] iAnnotationBinding = iMethodBinding.getAnnotations();
												for (IAnnotationBinding iAnnotationBinding2 : iAnnotationBinding) {
													String[] keys = iAnnotationBinding2.toString().split(",");
													for (String key : keys) {
														if (key.indexOf("length") > -1) {
															fieldLength = key.split("=")[1].trim();
															fieldLength = fieldLength.replace(")", "");
															fieldLength = fieldLength.replace("(", "").trim();
															if (!fieldLength.isEmpty()) {
																try {
																	Integer x = Integer.parseInt(fieldLength);
																	if (x > 255) {
																		fieldLength = "";
																		fieldWidth = "";
																		if (fieldType.equals("String"))
																			typeComp = "inputTextarea";
																	} else {
																		fieldWidth = (x * 7) + "px";
																	}	
																} catch (Exception e) {
																}
															}
														}	
													}
												}
											}	
										}										
										if (!typeComp.toLowerCase().equals("inputmask")) {
											fieldLength = "";
											fieldWidth = "";											
										}
										if (fieldType.toLowerCase().equals("date"))
											mask = ""; //"99/99/9999";
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
										if (CreateXHTMLperation.validFieldName(fieldName, fieldType) && !fieldName.toLowerCase().equals("sizedata")) {

											// page 2

											TableItem item3 = new TableItem (pageComposite2.tableDialogFieldGrid.getTable(), SWT.NONE);
											item3.setText(new String [] {fieldNameCamel, fieldName, typeCell});

											// page 3
											TableItem item4 = new TableItem (pageComposite3.tableDialogFieldForm.getTable(), SWT.NONE);											
											item4.setText(new String [] {fieldNameCamel + ":", fieldName, fieldLength, fieldWidth, typeComp, mask, "false", ""});
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
		String ret = "inputMask";
		if (type.toLowerCase().equals("date"))
			ret = "calendar";
		if (type.toLowerCase().equals("boolean"))
			ret = "selectBooleanCheckbox";
		/*if (type.equals("int") || type.equals("Integer"))
			ret = "IntegerBox";
		else if (type.toLowerCase().equals("long"))
			ret = "LongBox";
		else if (type.toLowerCase().equals("double"))
			ret = "DoubleBox";
		else if (type.toLowerCase().equals("date"))
			ret = "DateBox";
		else if (type.toLowerCase().equals("boolean"))
			ret = "CheckBox";*/
		return ret;
	}

	public String typeCell(String type) {
		String ret = "outputText";
		if (type.toLowerCase().equals("boolean"))
			ret = "selectBooleanCheckbox";
		/*if (type.equals("IntegerBox") || type.equals("LongBox") || type.equals("DoubleBox"))
			ret = "NumberCell";
		else if (type.equals("DateBox"))
			ret = "DateCell";*/
		return ret;
	}	

}
