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
package br.com.pug.plugin.jsf.wizards.bean;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.internal.core.DesignerPlugin;
import org.eclipse.wb.internal.core.utils.dialogfields.ComboDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogFieldUtils;
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;
import org.eclipse.wb.internal.core.utils.dialogfields.SelectionButtonDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.StringDialogField;

import br.com.pug.plugin.jsf.wizards.AbstractComposite;
import br.com.pug.plugin.jsf.wizards.ClientPackageSelectionDialogField;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class BeanJSFComposite extends AbstractComposite {
  ClientPackageSelectionDialogField m_clientPackageField;
  private final StringDialogField m_serviceField;
  ComboDialogField listClass;
  private final SelectionButtonDialogField m_createBean;
  private final SelectionButtonDialogField m_createConverter;
  IPackageFragment packageFragmentDAO;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  @SuppressWarnings("unchecked")
public BeanJSFComposite(Composite parent,
      int style,
      IMessageContainer messageContainer,
      IPackageFragment selectedPackage) {
    super(parent, style, messageContainer);
    //
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
      m_serviceField.setLabelText("Bean &name:");
      DialogFieldUtils.fillControls(this, m_serviceField, columns, 60);
      m_serviceField.getTextControl(null).setFocus();
    }
	listClass = new ComboDialogField(SWT.READ_ONLY);
	listClass.setLabelText("Class &DAO:");
	listClass.setDialogFieldListener(m_validateListener);
	listClass.doFillIntoGrid(this, columns);    
    // initialize fields
    m_clientPackageField.setPackageFragment(selectedPackage);
    m_serviceField.setText("");
    Label label = new Label(this, SWT.NONE);
    label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
    label.setText("Note: make sure that Eclipse auto-build is turned on.");
    
    m_createBean = new SelectionButtonDialogField(SWT.CHECK);
    m_createBean.setLabelText("Create Bean");
    m_createBean.setSelection(true);
    m_createBean.doFillIntoGrid(this, columns);
    m_createBean.setDialogFieldListener(m_validateListener);
    
    m_createConverter = new SelectionButtonDialogField(SWT.CHECK);
    m_createConverter.setLabelText("Create Converter");
    m_createConverter.setSelection(true);
    m_createConverter.doFillIntoGrid(this, columns);        
    m_createConverter.setDialogFieldListener(m_validateListener);
        
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
								packageFragmentDAO = iPackageFragment;
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

  ////////////////////////////////////////////////////////////////////////////
  //
  // Validation
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected String validate() {
    IPackageFragment packageFragment = m_clientPackageField.getPackageFragment();
    // validate package
    {
      if (packageFragment == null) {
        return "Select bean package.";
      }
      try {
        if (packageFragment.getElementName().indexOf(".bean") == -1) {
          return "Package " + packageFragment.getElementName() + " is not a bean package.";
        }
      } catch (Throwable e) {
        return "Exception: " + e.getMessage();
      }
    }
    // validate service name
    {
      String serviceName = m_serviceField.getText();
      if (serviceName.length() == 0) {
        return "Bean name can not be empty.";
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
		if (serviceName.toLowerCase().indexOf("service") == -1)
			qualifiedServiceName += "Bean";
        if (packageFragment.getJavaProject().findType(qualifiedServiceName) != null && getCreateBean()) {
          return "Type with such name already exists.";
        }
      } catch (Throwable e) {
        DesignerPlugin.log(e);
      }
    }
	{
		if (listClass.getSelectionIndex() == -1) {
			return "Choose the class DAO.";
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
	if (name.length() < 7 || !name.toLowerCase().substring(name.length()-7).equals("service")) {
		name += "Bean";
	}
	return name;
  }
  
	public String getClassDAO() {
		String name = "";
		if (listClass.getSelectionIndex() > -1)
			name = listClass.getText();
		return name;
	}
	
	public boolean getCreateBean() {
		return m_createBean.isSelected();
	}
	
	public boolean getCreateConverter() {
		return m_createConverter.isSelected();
	}	
	
	public IPackageFragment getPackageFragmentDAO() {
		return packageFragmentDAO;
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
}
