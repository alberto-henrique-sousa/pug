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
package com.pugsource.plugin.wizards.project;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.wb.internal.core.DesignerPlugin;
import org.eclipse.wb.internal.core.utils.jdt.core.ProjectUtils;

import com.google.gdt.eclipse.designer.model.web.WebAppElement;
import com.google.gdt.eclipse.designer.model.web.WebDocumentEditContext;
import com.google.gdt.eclipse.designer.model.web.WebUtils;
import com.pugsource.plugin.util.Tools;
import com.pugsource.plugin.wizards.Activator;
import com.pugsource.plugin.wizards.WizardUtils;

/**
 * @author alberto
 */
public class AddPugGWTWizard extends Wizard implements INewWizard {
	private AddPugGWTWizardPage page;
	private String webInfSrcText;
	private String webInfLibText;
	private Boolean generateLibrary;
	private Boolean generateConfig;
	private Boolean generateVRaptor;
	private ISelection selection;
	
	/** JARs of PugFramework */
	public static final String[] JARS = {
		"lib/antlr-2.7.7.jar",
		"lib/cdi-api-1.2.jar",
		"lib/classmate-1.3.0.jar",
		"lib/commons-beanutils-1.8.0.jar",
		"lib/commons-codec-1.4.jar",
		"lib/commons-collections-3.2.1.jar",
		"lib/commons-digester-1.8.jar",
		"lib/commons-fileupload-1.2.1.jar",
		"lib/commons-httpclient-3.0.1.jar",
		"lib/commons-io-1.4.jar",
		"lib/commons-lang-2.4.jar",
		"lib/commons-logging.jar",
		"lib/dom4j-1.6.1.jar",
		"lib/fontbox-0.1.0.jar",
		"lib/fonttimes.jar",
		"lib/groovy-all-2.0.1.jar",
		"lib/hibernate-commons-annotations-5.0.1.Final.jar",
		"lib/hibernate-core-5.1.0.Final.jar",
		"lib/hibernate-jpa-2.1-api-1.0.0.Final.jar",
		"lib/hibernate-validator-5.2.4.Final.jar",
		"lib/itext-2.1.0.jar",
		"lib/iTextAsian.jar",
		"lib/jandex-2.0.0.Final.jar",
		"lib/jasperreports-5.1.0.jar",
		"lib/jasperreports-fonts-5.1.0.jar",
		"lib/javassist-3.20.0-GA.jar",
		"lib/javax.servlet.jsp.jstl-1.2.1.jar",
		"lib/javax.servlet.jsp.jstl-api-1.2.1.jar",
		"lib/jboss-logging-3.3.0.Final.jar",
		"lib/jboss-logging-annotations-1.2.0.Beta1.jar",
		"lib/jconn2.jar",
		"lib/jconn3.jar",
		"lib/jdom.jar",
		"lib/jfreechart-1.0.12.jar",
		"lib/jta-1.1.jar",
		"lib/lib-gwt-file-0.3.3.jar",
		"lib/log4j-1.2.16.jar",
		"lib/mailapi.jar",
		"lib/mybatis-3.2.4.jar",
		"lib/mysql-connector-java-5.1.7-bin.jar",
		"lib/ojdbc6.jar",
		"lib/poi-3.7-20101029.jar",
		"lib/postgresql-9.2-1000.jdbc4.jar",
		"lib/pug-gwt-library-2.jar",
		"lib/pug-library-jar-2.3.jar",
		"lib/slf4j-api-1.6.1.jar",
		"lib/slf4j-log4j12-1.6.1.jar",
		"lib/smtp.jar",
		"lib/spring-beans-3.2.3.RELEASE.jar",
		"lib/spring-core-3.2.3.RELEASE.jar",
		"lib/thumbnailator-0.4.8.jar",
		"lib/validation-api-1.1.0.Final.jar"
		};	
	/** JARs of VRaptor */
	public static final String [] JARS_VRAPTOR = {
		"lib/aopalliance-1.0.jar",
		"lib/gson-2.3.1.jar",
		"lib/guava-11.0.2.jar",
		"lib/guice-3.0.jar",
		"lib/guice-multibindings-3.0.jar",
		"lib/iogi-0.9.2.jar",
		"lib/javax.inject-1.jar",
		"lib/jsr305-1.3.9.jar",
		"lib/mirror-1.6.1.jar",
		"lib/objenesis-1.3.jar",
		"lib/paranamer-2.5.2.jar",
		"lib/scannotation-1.0.2.jar",
		"lib/vraptor-3.5.3.jar",
		"lib/xpp3_min-1.1.4c.jar",
		"lib/xstream-1.3.1.jar"		
	};	
	/** JARs PATH of PugFramework */
	public static final String[] JARS_PATH = {
		"antlr-2.7.7.jar",
		"cdi-api-1.2.jar",
		"classmate-1.3.0.jar",
		"commons-collections-3.2.1.jar",
		"commons-fileupload-1.2.1.jar",
		"dom4j-1.6.1.jar",
		"hibernate-commons-annotations-5.0.1.Final.jar",
		"hibernate-core-5.1.0.Final.jar",
		"hibernate-jpa-2.1-api-1.0.0.Final.jar",
		"hibernate-validator-5.2.4.Final.jar",
		"javassist-3.20.0-GA.jar",
		"jconn2.jar",
		"jconn3.jar",
		"jdom.jar",
		"jasperreports-5.1.0.jar",
		"jasperreports-fonts-5.1.0.jar",
		"lib-gwt-file-0.3.3.jar",
		"ojdbc6.jar",
		"pug-gwt-library-2.jar",
		"pug-library-jar-2.3.jar",
		"slf4j-api-1.6.1.jar",
		"slf4j-log4j12-1.6.1.jar",
		"mysql-connector-java-5.1.7-bin.jar",
		"postgresql-9.2-1000.jdbc4.jar",
		"smtp.jar"};
	/** JARs PATH of PugFramework */
	public static final String[] JARS_PATH_VRAPTOR = {
		"vraptor-3.5.3.jar"
	};	
	public static final String[] CONFIG = {
		"config/commons-logging.properties",
		"config/hibernate.cfg.xml",
		"config/hibernate.production.cfg.xml",
		"config/hibernate.sybase.cfg.xml",
		"config/hibernate.mysql.cfg.xml",
		"config/hibernate.oracle.cfg.xml",
		"config/hibernate.properties",
		"config/log4j.properties"		
	};
	public static final String PUG_PROPERTIES = "config/pug.properties";
	/**
	 * Constructor for SetProjectGWT.
	 */
	public AddPugGWTWizard() {
		super();
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("wizards/addpug/banner.png"));
		setWindowTitle("Add Pug");
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new AddPugGWTWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		webInfSrcText = page.getWebInfSrc();
		webInfLibText = page.getWebInfLib();
		generateLibrary = page.getGenerateLibrary();
		generateConfig = page.getGenerateConfig();
		generateVRaptor = page.getVRaptor();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */

	private void doFinish(
			String containerName,
			IProgressMonitor monitor)
					throws CoreException {
		// create a sample file
		monitor.beginTask("Creating ...", 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(containerName);
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;
		if (generateLibrary) {
			for (int i=0;i<JARS.length;i++){
				Tools.copyFile(monitor, container, getTemplatePath(project) + JARS[i], webInfLibText, false);
				monitor.worked(1);
			}
			if (generateVRaptor) {
				System.out.println("VRaptor");
				for (int i=0;i<JARS_VRAPTOR.length;i++){
					Tools.copyFile(monitor, container, getTemplatePath(project) + JARS_VRAPTOR[i], webInfLibText, false);
					System.out.println(getTemplatePath(project) + JARS_VRAPTOR[i]);
					monitor.worked(1);
				}				
			}
			IJavaProject javaProject = getSelectedProject(selection);
			String webInfLibTextPath = project.getFullPath().toString() + webInfLibText;			
			for (int i=0;i<JARS_PATH.length;i++){
		        IClasspathEntry entry = 
		              JavaCore.newLibraryEntry(new Path(webInfLibTextPath + "/" + JARS_PATH[i]), null, null);		        
				ProjectUtils.addClasspathEntry(javaProject, entry);
			}
			if (generateVRaptor) {
				for (int i=0;i<JARS_PATH_VRAPTOR.length;i++){
			        IClasspathEntry entry = 
			              JavaCore.newLibraryEntry(new Path(webInfLibTextPath + "/" + JARS_PATH_VRAPTOR[i]), null, null);		        
					ProjectUtils.addClasspathEntry(javaProject, entry);
				}
				
				String webFolderName = WebUtils.getWebFolderName(project);
				IFile webFile = project.getFile(new Path(webFolderName + "/WEB-INF/web.xml"));
				WebDocumentEditContext context;
				try {
					context = new WebDocumentEditContext(webFile);
					WebAppElement moduleElement = context.getWebAppElement();
					{
						// filter
						FilterElement filterElement = new FilterElement();
						moduleElement.addChild(filterElement);
						filterElement.setName("vraptor");
						filterElement.setClassName("br.com.caelum.vraptor.VRaptor");
						// filter-mapping
						FilterMappingElement filterMappingElement = new FilterMappingElement();
						moduleElement.addChild(filterMappingElement);
						filterMappingElement.setName("vraptor");
						filterMappingElement.setPattern("/*");
						filterMappingElement.setDispatcher("FORWARD");
						filterMappingElement.setDispatcher("REQUEST");
					}
					// commit modifications
					context.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (generateConfig) {
			for (int i=0;i<CONFIG.length;i++){
				Tools.copyFile(monitor, container, getTemplatePath(project) + CONFIG[i], webInfSrcText, false);
				monitor.worked(1);
			}	

			final Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("%version%", Tools.VERSION);		
			try {
				Tools.copyFileCode(container, getTemplatePath(project) + PUG_PROPERTIES, webInfSrcText, "", false, parameters, monitor);
			} catch (IOException e) {
				System.out.println(PUG_PROPERTIES);
				e.printStackTrace();
			}					
			monitor.worked(1);

		}
		container.refreshLocal(IResource.DEPTH_INFINITE, monitor);			
		monitor.worked(1);
	}

	/**
	 * We will initialize file contents with a sample text.
	 */

	private void throwCoreException(String message) throws CoreException {
		IStatus status =
				new Status(IStatus.ERROR, "com.pugsource.plugin.wizards.project", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	public static String getTemplatePath(IProject project) {
		return "/" + WizardUtils.getTemplatePath(project);
	}

	private static IJavaProject getSelectedProject(ISelection selection) {
		try {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				Object selectedObject = structuredSelection.getFirstElement();
				return getProjectFromResource(selectedObject);
			} else if (selection instanceof ITextSelection) {
				IEditorPart activeEditor = DesignerPlugin.getActiveEditor();
				IEditorInput editorInput = activeEditor.getEditorInput();
				if (editorInput instanceof IFileEditorInput) {
					IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
					return getProjectFromResource(fileEditorInput.getFile());
				}
			}
		} catch (Throwable e) {
		}
		return null;
	}

	private static IJavaProject getProjectFromResource(Object o) throws CoreException {
		if (o instanceof IAdaptable) {
			o = ((IAdaptable) o).getAdapter(IResource.class);
		}
		if (o instanceof IResource) {
			IResource resource = (IResource) o;
			IProject project = resource.getProject();
			if (project.hasNature(JavaCore.NATURE_ID)) {
				return JavaCore.create(project);
			}
		}
		return null;
	}

}