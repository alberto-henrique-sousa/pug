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
package br.com.pug.plugin.jsf.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;

import br.com.pug.plugin.jsf.Activator;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class Tools {
	
	public static final String NAME = "Pug Plugin"; 
	public static final String VERSION = "1.0.0";
	
	public static String pluginJSFTitle() {
		return NAME + " - JSF" + " (" + VERSION + ")";
	}	
	
	public static IFolder createFolder(String folder, IProject project, IProgressMonitor monitor) throws CoreException {
		String[] components = folder.split("\\/");
		Boolean start = true;
		IFolder pkgFolder = null;					
		for (String component : components) {
			if (component.trim().length() > 0) {
				if (start) {
					pkgFolder = project.getFolder(new Path(component));
					start = false;
				}
				else {
					pkgFolder = pkgFolder.getFolder(component);														
				}
				if (!pkgFolder.exists()) {
					pkgFolder.create(true /* force */, true /* local */,
							new SubProgressMonitor(monitor, 10));
				}
			}
		}
		return pkgFolder;
	}
			
	public static IFolder createPackage(IWorkspaceRoot root, String containerName, String pathPackage, String folderWebSrc, String separator, IProgressMonitor monitor) throws CoreException {
		IFolder pkgFolder = null;					
		pkgFolder = root.getFolder(new Path(containerName + "/" + folderWebSrc));
		String[] components = pathPackage.split("\\" + separator); // .
		for (String component : components) {
			pkgFolder = pkgFolder.getFolder(component);				
			if (!pkgFolder.exists()) {
				pkgFolder.create(true /* force */, true /* local */,
						new SubProgressMonitor(monitor, 10));
			}
		}
		return pkgFolder;
	}

	/** Copy a file.  */
	public static synchronized void copyFile(IProgressMonitor monitor,IContainer container,
			String entry,String target, boolean force){
		try {
			URL url = Activator.getDefault().getBundle().getEntry(entry);
			String fileName = new File(entry).getName();
			if (monitor != null)
				monitor.setTaskName("Copying " + fileName + "...");
			File file = container.getFile(new Path(target+"/"+fileName)).getLocation().makeAbsolute().toFile();
			if (!file.exists() || force) {
				InputStream in = url.openStream();
				OutputStream out = new FileOutputStream(file);
				byte[] buf = new byte[1024 * 8];
				int length = 0;
				while((length=in.read(buf))!=-1){
					out.write(buf,0,length);
				}
				out.close();
				in.close();				
			}

		} catch(Exception ex){
			System.out.println("Exception: " +entry);
			ex.printStackTrace();
		}
	}

	public static void copyFileCode(IContainer container, String resourceFilename, String target, String fileName,
			boolean force, Map<String, String> parameters, IProgressMonitor monitor)
	throws CoreException, IOException {

		// Read existing file.
		String template = Activator.readEmbeddedTextFile(
				resourceFilename);

		// Replace all keyword parameters
		template = replaceParameters(template, parameters);

		// Save in the project as UTF-8
		//InputStream stream = new ByteArrayInputStream(template.getBytes("UTF-8")); //$NON-NLS-1$
		InputStream stream = new ByteArrayInputStream(template.getBytes()); //$NON-NLS-1$
		if (fileName.trim().length() == 0) 
			fileName = new File(resourceFilename).getName();
		IFile destFile = container.getFile(new Path(target+"/"+fileName));
		if (!destFile.exists() || force) {
			if (destFile.exists())
				destFile.delete(true, monitor);
			destFile.create(stream, false /* force */, new SubProgressMonitor(monitor, 10));
		}	
	}

	public static String replaceParameters(String str, Map<String, String> parameters) {
		for (String key : parameters.keySet()) {
			str = str.replaceAll(key, parameters.get(key));
		}

		return str;
	}    
	
	public static String stringToUTF8(String str) {
		try {
		    byte[] utf8Bytes = str.getBytes("UTF8");

		    return new String(utf8Bytes);
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
			return null;
		}
		
	}

}
