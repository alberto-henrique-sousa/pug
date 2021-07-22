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
package com.pugsource.plugin.wizards.guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogFieldUtils;
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;
import org.eclipse.wb.internal.core.utils.dialogfields.StringAreaDialogField;

import com.pugsource.plugin.wizards.AbstractGwtComposite;
import com.pugsource.plugin.wizards.Activator;
import com.pugsource.plugin.wizards.project.AddPugJSFWizard;

/**
 * @author alberto
 */
public class MigrationGuideComposite extends AbstractGwtComposite {
	private final StringAreaDialogField m_text;

	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	public MigrationGuideComposite(Composite parent,
			int style,
			IMessageContainer messageContainer) {
		super(parent, style, messageContainer);
		int columns = 3;
		setLayout(new GridLayout(columns, false));
		m_text = new StringAreaDialogField(10);
		m_text.setDialogFieldListener(m_validateListener);
		String txt = "";
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			final IProject project = workspace.getRoot().getProject();
			URL url = Activator.getDefault().getBundle().getEntry(AddPugJSFWizard.getTemplatePath(project) + "notes.txt");
			InputStream in = url.openStream();
			txt = getStringFromInputStream(in);
			in.close();				
		} catch (Exception e) {
			txt = "Erro :" + e.getMessage() + " " + e.getCause() != null ?  e.getCause().getMessage() : "";
		}
		m_text.setText(txt);
		DialogFieldUtils.fillControls(this, m_text, columns, 60);
	}

	private String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n") ;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}	
}
