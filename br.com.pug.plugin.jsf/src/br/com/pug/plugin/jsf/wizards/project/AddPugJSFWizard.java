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
package br.com.pug.plugin.jsf.wizards.project;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import br.com.pug.plugin.jsf.Activator;
import br.com.pug.plugin.jsf.util.Tools;
import br.com.pug.plugin.jsf.wizards.WizardUtils;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class AddPugJSFWizard extends Wizard implements INewWizard {
	private AddPugJSFWizardPage page;
	private String webContent;
	private String webInfSrcText;
	private String webInfLibText;
	private String packageBasic;
	private String packageBean;
	private String packageBeanUtil;
	private String packageDao;
	private String packageLazy;
	private String packageConverter;
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
		"lib/commons-lang3-3.5.jar",
		"lib/commons-logging.jar",
		"lib/dom4j-1.6.1.jar",
		"lib/fontbox-0.1.0.jar",
		"lib/fonttimes.jar",
		"lib/groovy-all-2.0.1.jar",
		"lib/gson-2.7.jar",
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
		"lib/log4j-1.2.16.jar",
		"lib/mailapi.jar",
		"lib/mybatis-3.2.4.jar",
		"lib/mysql-connector-java-5.1.7-bin.jar",
		"lib/ojdbc6.jar",
		"lib/poi-3.7-20101029.jar",
		"lib/postgresql-9.2-1000.jdbc4.jar",
		"lib/pug-library-jar-2.5.jar",
		"lib/slf4j-api-1.6.1.jar",
		"lib/slf4j-log4j12-1.6.1.jar",
		"lib/smtp.jar",
		"lib/spring-beans-3.2.3.RELEASE.jar",
		"lib/spring-core-3.2.3.RELEASE.jar",
		"lib/thumbnailator-0.4.8.jar",
		"lib/validation-api-1.1.0.Final.jar"
	};	
	/** JARs of JSF */
	public static final String [] JARS_JSF = {
		"lib-jsf/all-themes-1.0.10.jar",
		"lib-jsf/javax.faces-2.2.9.jar",
		"lib-jsf/primefaces-extensions-6.0.0.jar",
		"lib-jsf/primefaces-6.0.jar"
	};	
	public static final String[] CONFIG = {
		"config/commons-logging.properties",
		"config/hibernate.cfg.xml",
		"config/hibernate.production.cfg.xml",
		"config/hibernate.sybase.cfg.xml",
		"config/hibernate.mysql.cfg.xml",
		"config/hibernate.oracle.cfg.xml",
		"config/hibernate.properties",
		"config/messages_pt_BR.properties",
		"config/log4j.properties"		
	};
	public static final String[] PAGES = {
		"pages/login.xhtml",
		"pages/main.xhtml"
	};
	public static final String[] RESOURCES_CSS = {
		"resources/css/standard.css"
	};
	public static final String[] RESOURCES_IMAGES = {
		"resources/images/favicon.ico",
		"resources/images/logo.png"
	};
	public static final String[] RESOURCES_JS = {
		"resources/js/calendar.js"
	};
	public static final String[] TEMPLATES = {
		"templates/footer.xhtml",
		"templates/header.xhtml",
		"templates/layout.xhtml",
		"templates/menu.xhtml",
		"templates/viewExpired.xhtml"
	};
	public static final String PUG_PROPERTIES = "config/pug.properties";
	public static final String WEB_XML = "config/web.xml";
	public static final String FACES_CONFIG = "config/faces-config.xml";
	public static final String INDEX_HTML = "config/index.html";
	public static final String APPLICATION_BEAN = "model/ApplicationBean.java";
	public static final String LOGIN_BEAN = "model/LoginBean.java";
	public static final String ACCESSPHASE_LISTENER = "model/AccessPhaseListener.java";
	public static final String BASE_BEAN = "model/BaseBean.java";
	public static final String DAOFACTORY_BEAN = "model/DaoFactoryBean.java";
	public static final String LOCALE_BEAN = "model/LocaleBean.java";
	public static final String THEMES_BEAN = "model/ThemesBean.java";
	public static final String DAOFACTORY = "model/DaoFactory.java";

	public AddPugJSFWizard() {
		super();
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("wizards/addpug/banner.png"));
		setWindowTitle("Add Pug");
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new AddPugJSFWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		webContent = page.getWebContent();
		webInfSrcText = page.getWebInfSrc();
		webInfLibText = page.getWebContent() + "/WEB-INF/lib";
		packageBasic = page.getPackage();
		packageBean = page.getPackageBean();
		packageBeanUtil = page.getPackageBeanUtil();
		packageDao = page.getPackageDao();
		packageLazy = page.getPackageLazy();
		packageConverter = page.getPackageConverter();
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
		for (int i=0;i<JARS.length;i++){
			Tools.copyFile(monitor, container, getTemplatePath(project) + JARS[i], webInfLibText, false);
			monitor.worked(1);
		}
		System.out.println("JSF with Primefaces");
		for (int i=0;i<JARS_JSF.length;i++){
			Tools.copyFile(monitor, container, getTemplatePath(project) + JARS_JSF[i], webInfLibText, false);
			System.out.println(getTemplatePath(project) + JARS_JSF[i]);
			monitor.worked(1);
		}				

		for (int i=0;i<CONFIG.length;i++){
			Tools.copyFile(monitor, container, getTemplatePath(project) + CONFIG[i], webInfSrcText, false);
			System.out.println(getTemplatePath(project) + CONFIG[i]);
			monitor.worked(1);
		}	

		final Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("%version%", Tools.VERSION);
		parameters.put("%packageBean%", packageBasic + "." + packageBean);
		parameters.put("%packageBeanUtil%", packageBasic + "." + packageBeanUtil);
		parameters.put("%servicePackageDAO%", packageBasic + "." + packageDao);
		parameters.put("%packageLazy%", packageBasic + "." + packageLazy);
		parameters.put("%packageConverter%", packageBasic + "." + packageConverter);
		try {
			Tools.copyFile(monitor, container, getTemplatePath(project) + WEB_XML, webContent + "/WEB-INF", true);
			Tools.copyFileCode(container, getTemplatePath(project) + PUG_PROPERTIES, webInfSrcText, "", false, parameters, monitor);
			Tools.copyFileCode(container, getTemplatePath(project) + FACES_CONFIG, webContent + "/WEB-INF", "", true, parameters, monitor);
		} catch (IOException e) {
			System.out.println("Erro");
			e.printStackTrace();
		}					
		monitor.worked(1);
		
		Tools.copyFile(monitor, container, getTemplatePath(project) + INDEX_HTML, webContent, false);
		monitor.worked(1);		
		
		Tools.createFolder(webContent + "/pages", project, monitor);
		for (int i=0;i<PAGES.length;i++){
			Tools.copyFile(monitor, container, getTemplatePath(project) + PAGES[i], webContent + "/pages", false);
			monitor.worked(1);
		}		
		Tools.createFolder(webContent + "/resources", project, monitor);
		Tools.createFolder(webContent + "/resources/css", project, monitor);
		for (int i=0;i<RESOURCES_CSS.length;i++){
			Tools.copyFile(monitor, container, getTemplatePath(project) + RESOURCES_CSS[i], webContent + "/resources/css", false);
			monitor.worked(1);
		}		
		Tools.createFolder(webContent + "/resources/images", project, monitor);
		for (int i=0;i<RESOURCES_IMAGES.length;i++){
			Tools.copyFile(monitor, container, getTemplatePath(project) + RESOURCES_IMAGES[i], webContent + "/resources/images", false);
			monitor.worked(1);
		}		
		Tools.createFolder(webContent + "/resources/js", project, monitor);
		for (int i=0;i<RESOURCES_JS.length;i++){
			Tools.copyFile(monitor, container, getTemplatePath(project) + RESOURCES_JS[i], webContent + "/resources/js", false);
			monitor.worked(1);
		}		
		Tools.createFolder(webContent + "/templates", project, monitor);
		for (int i=0;i<TEMPLATES.length;i++){
			Tools.copyFile(monitor, container, getTemplatePath(project) + TEMPLATES[i], webContent + "/templates", false);
			monitor.worked(1);
		}		

		try {
			Tools.createPackage(root, containerName, packageBasic + "." + packageBean, webInfSrcText, ".", monitor);
			Tools.copyFileCode(container, getTemplatePath(project) + APPLICATION_BEAN, webInfSrcText + "/" + packageBasic.replace(".", "/") + "/" + packageBean.replace(".", "/"), "", true, parameters, monitor);
			Tools.copyFileCode(container, getTemplatePath(project) + LOGIN_BEAN, webInfSrcText + "/" + packageBasic.replace(".", "/") + "/" + packageBean.replace(".", "/"), "", true, parameters, monitor);
			monitor.worked(1);
			
			Tools.createPackage(root, containerName, packageBasic + "." + packageBeanUtil, webInfSrcText, ".", monitor);
			Tools.copyFileCode(container, getTemplatePath(project) + ACCESSPHASE_LISTENER, webInfSrcText + "/" + packageBasic.replace(".", "/") + "/" + packageBeanUtil.replace(".", "/"), "", true, parameters, monitor);
			Tools.copyFileCode(container, getTemplatePath(project) + BASE_BEAN, webInfSrcText + "/" + packageBasic.replace(".", "/") + "/" + packageBeanUtil.replace(".", "/"), "", true, parameters, monitor);
			Tools.copyFileCode(container, getTemplatePath(project) + DAOFACTORY_BEAN, webInfSrcText + "/" + packageBasic.replace(".", "/") + "/" + packageBeanUtil.replace(".", "/"), "", true, parameters, monitor);
			Tools.copyFileCode(container, getTemplatePath(project) + LOCALE_BEAN, webInfSrcText + "/" + packageBasic.replace(".", "/") + "/" + packageBeanUtil.replace(".", "/"), "", true, parameters, monitor);
			Tools.copyFileCode(container, getTemplatePath(project) + THEMES_BEAN, webInfSrcText + "/" + packageBasic.replace(".", "/") + "/" + packageBeanUtil.replace(".", "/"), "", true, parameters, monitor);
			monitor.worked(1);

			Tools.createPackage(root, containerName, packageBasic + "." + packageDao, webInfSrcText, ".", monitor);
			Tools.copyFileCode(container, getTemplatePath(project) + DAOFACTORY, webInfSrcText + "/" + packageBasic.replace(".", "/") + "/" + packageDao.replace(".", "/"), "", true, parameters, monitor);
			monitor.worked(1);
			
			Tools.createPackage(root, containerName, packageBasic + "." + packageLazy, webInfSrcText, ".", monitor);
			monitor.worked(1);
			
			Tools.createPackage(root, containerName, packageBasic + "." + packageConverter, webInfSrcText, ".", monitor);
			monitor.worked(1);			
			
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
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

}