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
package com.pugsource.plugin.wizards.module;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.internal.core.utils.dialogfields.ComboDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogFieldUtils;
import org.eclipse.wb.internal.core.utils.dialogfields.IDialogFieldListener;
import org.eclipse.wb.internal.core.utils.dialogfields.IListAdapter;
import org.eclipse.wb.internal.core.utils.dialogfields.IStringButtonAdapter;
import org.eclipse.wb.internal.core.utils.dialogfields.ListDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.SelectionButtonDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.StringDialogField;
import org.eclipse.wb.internal.core.utils.external.ExternalFactoriesHelper;
import org.eclipse.wb.internal.core.utils.jdt.ui.PackageRootSelectionDialogField;
import org.eclipse.wb.internal.core.utils.ui.GridDataFactory;
import org.eclipse.wb.internal.core.utils.ui.GridLayoutFactory;

import com.google.common.collect.Lists;
import com.google.gdt.eclipse.designer.wizards.model.common.AbstractGwtComposite;
import com.google.gdt.eclipse.designer.wizards.model.common.IMessageContainer;
import com.pugsource.plugin.actions.model.IModuleConfigurator;
import com.pugsource.plugin.wizards.GwtProjectPackageRootFilter;
import com.pugsource.plugin.wizards.crud.CRUDGWTComposite1;

/**
 * Composite that ask user for parameters of new GWT module.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard.ui
 * modified by alberto
 */
public class ModuleGWTComposite extends AbstractGwtComposite {
  private static final int COLUMNS = 3;
  private IPackageFragmentRoot m_root;
  ////////////////////////////////////////////////////////////////////////////
  //
  // Dialog fields
  //
  ////////////////////////////////////////////////////////////////////////////
  private PackageRootSelectionDialogField m_sourceFolderField;
  private final StringDialogField m_moduleField;
  private final StringDialogField m_packageField;
  private ComboDialogField listLibrary;
  private final SelectionButtonDialogField m_createEntryPointField;
  private ComboDialogField template;
  private ComboDialogField dialogBoxForm;
  private ComboDialogField serviceForm;
  private ListDialogField m_listDialogForms;
  private List<String> listForms;	
  //private final SelectionButtonDialogField m_createEntryPointMVPField;
  private final Text m_descriptionText;
  private final List<ConfiguratorRadio> m_configurators = Lists.newArrayList();
  private Group m_configuratorsGroup;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public ModuleGWTComposite(Composite parent, int style, IMessageContainer messageContainer) {
    this(parent, style, messageContainer, false, null);
  }

  public ModuleGWTComposite(Composite parent,
      int style,
      IMessageContainer messageContainer,
      IPackageFragmentRoot selectedRoot) {
    this(parent, style, messageContainer, true, selectedRoot);
  }

@SuppressWarnings("unchecked")
private ModuleGWTComposite(Composite parent,
      int style,
      IMessageContainer messageContainer,
      boolean selectRoot,
      IPackageFragmentRoot selectedRoot) {
    super(parent, style, messageContainer);
    m_root = selectedRoot;
    //
    setLayout(new GridLayout(COLUMNS, false));
    // source folder
    if (selectRoot) {
      m_sourceFolderField = PackageRootSelectionDialogField.create("Source folder:", "&Browse...");
      m_sourceFolderField.setPackageRootFilter(new GwtProjectPackageRootFilter());
      m_sourceFolderField.setUpdateListener(m_validateListener);
      DialogFieldUtils.fillControls(this, m_sourceFolderField, COLUMNS, 60);
    }
    // module name
    {
      m_moduleField = new StringDialogField();
      m_moduleField.setDialogFieldListener(m_validateListener);
      m_moduleField.setLabelText("&Module name:");
      DialogFieldUtils.fillControls(this, m_moduleField, COLUMNS, 60);
    }
    // package name
    {
      m_packageField = new StringDialogField();
      m_packageField.setDialogFieldListener(m_validateListener);
      m_packageField.setLabelText("&Package name:");
      DialogFieldUtils.fillControls(this, m_packageField, COLUMNS, 60);
    }
	template = new ComboDialogField(SWT.READ_ONLY);
	template.setItems(new String[]{
            "Basic",
            "MenuBar",
            "MenuBar with login"
            });		
	template.setLabelText("Template:");
	template.setDialogFieldListener(m_validateListener);
	template.doFillIntoGrid(this, COLUMNS);
	
	dialogBoxForm = new ComboDialogField(SWT.READ_ONLY);
	dialogBoxForm.setLabelText("Login (Form):");
	dialogBoxForm.setEnabled(false);
	dialogBoxForm.setDialogFieldListener(m_validateListener);
	dialogBoxForm.doFillIntoGrid(this, COLUMNS);
	
	serviceForm = new ComboDialogField(SWT.READ_ONLY);
	serviceForm.setLabelText("Service (Form):");
	serviceForm.setEnabled(false);
	serviceForm.setDialogFieldListener(m_validateListener);
	serviceForm.doFillIntoGrid(this, COLUMNS);

	String[] addButtons= new String[] {
			/* 0 */ "Up",
			/* 1 */ "Down",
			/* 2 */ "Remove"
		};	
	m_listDialogForms = new ListDialogField(new Adapter(), addButtons, new LabelProvider());
	m_listDialogForms.setLabelText("Menu Options:");
	m_listDialogForms.setEnabled(false);
	m_listDialogForms.setDialogFieldListener(m_validateListener);
	m_listDialogForms.doFillIntoGrid(this, COLUMNS);
	m_listDialogForms.setUpButtonIndex(0);
	m_listDialogForms.setDownButtonIndex(1);
	m_listDialogForms.setRemoveButtonIndex(2);
	listForms = new ArrayList<String>();	
    
    // library package name
    {
    	listLibrary = new ComboDialogField(SWT.READ_ONLY);
    	listLibrary.setLabelText("&Library:");
    	listLibrary.setDialogFieldListener(m_validateListener);
    	listLibrary.doFillIntoGrid(this, COLUMNS);
    	    	
    }    
    // entry point
    {
      new Label(this, SWT.NONE); // filler
      m_createEntryPointField = new SelectionButtonDialogField(SWT.CHECK);
      m_createEntryPointField.setLabelText("Create EntryPoint and public resources");
      m_createEntryPointField.setSelection(true);
      m_createEntryPointField.doFillIntoGrid(this, COLUMNS - 1);
      // use MVP
      {
        /*
    	new Label(this, SWT.NONE); // filler
        m_createEntryPointMVPField = new SelectionButtonDialogField(SWT.CHECK);
        m_createEntryPointMVPField.setLabelText("Use MVP framework");
        m_createEntryPointMVPField.setSelection(false);
        //m_createEntryPointMVPField.doFillIntoGrid(this, COLUMNS - 1);
        GridDataFactory.create(m_createEntryPointMVPField.getSelectionButton(this)).spanH(
            COLUMNS - 1).grab().fill().indentH(10);
        */    
      }
      m_createEntryPointField.setDialogFieldListener(m_validateListener);
    }
    // description
    {
      m_descriptionText = new Text(this, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY);
      GridDataFactory.create(m_descriptionText).spanH(COLUMNS).grab().fill().hintHC(60).hintVC(5);
    }
    // configurators
    createConfiguratorsGroup();
    // initialize fields
    {
      if (m_sourceFolderField != null) {
        m_sourceFolderField.setRoot(selectedRoot);
      }
      m_moduleField.setTextWithoutUpdate("Index");
      m_packageField.setTextWithoutUpdate("br.com.mycompany.project.index");
      validateAll();
    }
    
	template.selectItem(0);
	
	if (selectedRoot != null && selectedRoot.getJavaProject() != null) {
		try {
			IPackageFragment[] pkgs = selectedRoot.getJavaProject().getPackageFragments();
			for (IPackageFragment iPackageFragment : pkgs) {
				if (iPackageFragment.getElementName().toLowerCase().indexOf("library") > -1) {
					for (Object object : iPackageFragment.getNonJavaResources()) {					
						if (object instanceof IFile) {
							IFile file = (IFile) object;
							if (file != null && iPackageFragment.getElementName() != null && file.getName().indexOf(".gwt.xml") > -1) {
								listLibrary.addItem(iPackageFragment.getElementName() + "." + file.getName().replace(".gwt.xml", ""));
								listLibrary.selectItem(0);
							}	
						}
					}
				}
				ICompilationUnit[] compUnits = iPackageFragment.getCompilationUnits();
				for (ICompilationUnit iCompilationUnit : compUnits) {
					CompilationUnit ast = CRUDGWTComposite1.getAst(iCompilationUnit, false);
					for(AbstractTypeDeclaration type : (java.util.List<AbstractTypeDeclaration>)ast.types()) {
						if(type instanceof TypeDeclaration) { 
							TypeDeclaration td = (TypeDeclaration)type;		
							String pkg = td.resolveBinding().getPackage().getName();
							if (pkg.indexOf(".client.") > -1 && pkg.indexOf(".form") > -1) {
								dialogBoxForm.addItem(td.resolveBinding().getName());
								listForms.add(td.resolveBinding().getName());
							}		
							if (pkg.indexOf(".client.") > -1 && pkg.indexOf(".service") > -1 && td.resolveBinding().getName().indexOf("Async") == -1) {
								serviceForm.addItem(td.resolveBinding().getName());
							}
						}
					}
				}				
			}    			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	m_listDialogForms.addElements(listForms);
    
  }

  private void createConfiguratorsGroup() {
    List<IConfigurationElement> elements =
        ExternalFactoriesHelper.getElements(
            "com.google.gdt.eclipse.designer.moduleConfigurators",
            "configurator");
    if (!elements.isEmpty()) {
      m_configuratorsGroup = new Group(this, SWT.NONE);
      m_configuratorsGroup.setText("Configure for third-party toolkit");
      GridDataFactory.create(m_configuratorsGroup).spanH(COLUMNS).grab().fill();
      GridLayoutFactory.create(m_configuratorsGroup);
      {
        Button button = new Button(m_configuratorsGroup, SWT.RADIO);
        button.setText("Use standard GWT only");
        button.setSelection(true);
      }
      /*for (IConfigurationElement element : elements) {
        Button button = new Button(m_configuratorsGroup, SWT.RADIO);
        button.setText(element.getAttribute("name"));
        IModuleConfigurator configurator =
            ExternalFactoriesHelper.createExecutableExtension(element, "class");
        m_configurators.add(new ConfiguratorRadio(button, configurator));
      }*/
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Validation
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected String validate() {
    m_descriptionText.setText("");
	if (dialogBoxForm != null && template != null) {
		dialogBoxForm.setEnabled(template.getSelectionIndex() == 2 || template.getSelectionIndex() == 4 || template.getSelectionIndex() == 6);
		serviceForm.setEnabled(template.getSelectionIndex() == 2 || template.getSelectionIndex() == 4 || template.getSelectionIndex() == 6);
	}
	if (m_listDialogForms != null && template != null)
		m_listDialogForms.setEnabled(template.getSelectionIndex() > 0);		
    // validate source folder
    if (m_sourceFolderField != null) {
      if (m_sourceFolderField.getText().length() == 0) {
        return "Source folder name can not be empty.";
      }
      m_root = m_sourceFolderField.getRoot();
      if (m_root == null) {
        return "Source folder is invalid.";
      }
    }
    // validtae MVP
    if (m_root != null) {
      //m_createEntryPointMVPField.setEnabled(m_createEntryPointField.isSelected()
      //    && Utils.supportMvp(m_root.getJavaProject()));
    }
    // validate package name
    {
      String packageName = m_packageField.getText();
      if (packageName.length() == 0) {
        return "Package can not be empty.";
      }
      // check that package name is valid
      IStatus status = JavaConventions.validatePackageName(packageName, null, null);
      if (status.getSeverity() == IStatus.ERROR) {
        return status.getMessage();
      }
      // check that package does not exist
      if (m_root != null && m_root.getPackageFragment(packageName).exists()) {
        return "Package " + packageName + " already exists.";
      }
    }
    // validate module name
    {
      String moduleName = m_moduleField.getText();
      if (moduleName.length() == 0) {
        return "Module name can not be empty.";
      }
      // check that module name is valid identifier
      IStatus status = JavaConventions.validateIdentifier(moduleName, null, null);
      if (status.getSeverity() == IStatus.ERROR) {
        return status.getMessage();
      }
    }
    {
		if (dialogBoxForm.isEnabled() && dialogBoxForm.getSelectionIndex() == -1) {
			return "Choose a login (form)";
		}
		if (serviceForm.isEnabled() && serviceForm.getSelectionIndex() == -1) {
			return "Choose a service (form)";
		}
		
		if (m_listDialogForms.isEnabled() && m_listDialogForms.getSize() == 0) {
			return "Choose from options to the menu";
		}			    	
		
    }
    // update description
    {
      String mainLocation = m_packageField.getText().replace('.', '/');
      String descriptorLocation = mainLocation + "/" + m_moduleField.getText() + ".gwt.xml";
      String clientLocation = mainLocation + "/client";
      String publicLocation = mainLocation + "/public";
      String serverLocation = mainLocation + "/server";
      m_descriptionText.setText("Location of module descriptor file: "
          + descriptorLocation
          + "\n"
          + "Location of client (GWT) part: "
          + clientLocation
          + "\n"
          + "Location of public (static) part: "
          + publicLocation
          + "\n"
          + "Location of server part: "
          + serverLocation);
    }
    return null;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Access
  //
  ////////////////////////////////////////////////////////////////////////////
  
  public String getLibrary() {
	  String name = "";
	  if (listLibrary.getSelectionIndex() > -1) {
		  name = listLibrary.getText();
	  }	  
	  return name;
  }  
  
  public IPackageFragmentRoot getRoot() {
    return m_root;
  }

  public String getModuleName() {
    return m_moduleField.getText();
  }

  public String getPackageName() {
    return m_packageField.getText();
  }

  public boolean createEntryPoint() {
    return m_createEntryPointField.isSelected();
  }

  public boolean createEntryPointMVP() {
    //return m_createEntryPointMVPField.isEnabled() && m_createEntryPointMVPField.isSelected();
	  return false;
  }
  
  public int getTemplate() {
	return template.getSelectionIndex() + 1;
  }
	
  public String getLoginForm() {
	return dialogBoxForm.getText();
  }
  
  public String getServiceForm() {
	return serviceForm.getText();
  }

  @SuppressWarnings("unchecked")
  public List<String> getMenuOptions() {
	return m_listDialogForms.getElements();
  }  
  
  public IModuleConfigurator getConfigurator() {
    for (ConfiguratorRadio configurationRadio : m_configurators) {
      if (configurationRadio.m_radio.getSelection()) {
        return configurationRadio.m_configurator;
      }
    }
    return null;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Utils
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * Container of {@link IModuleConfigurator} and corresponding radio {@link Button}.
   */
  static final class ConfiguratorRadio {
    final Button m_radio;
    final IModuleConfigurator m_configurator;

    ConfiguratorRadio(Button radio, IModuleConfigurator configurator) {
      m_radio = radio;
      m_configurator = configurator;
    }
  }

	private class Adapter implements IStringButtonAdapter, IDialogFieldListener, IListAdapter {

		// -------- IStringButtonAdapter
		public void changeControlPressed(DialogField field) {
		}

		// -------- IListAdapter
		public void customButtonPressed(ListDialogField field, int index) {
		}

		public void selectionChanged(ListDialogField field) {
		}

		// -------- IDialogFieldListener
		public void dialogFieldChanged(DialogField field) {
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jdt.internal.ui.wizards.dialogfields.IListAdapter#doubleClicked(org.eclipse.jdt.internal.ui.wizards.dialogfields.ListDialogField)
		 */
		public void doubleClicked(ListDialogField field) {
		}
	}	
  
}
