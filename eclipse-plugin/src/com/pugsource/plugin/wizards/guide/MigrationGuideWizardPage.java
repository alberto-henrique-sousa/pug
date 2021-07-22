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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;
import org.eclipse.wb.internal.core.utils.ui.GridDataFactory;

import com.pugsource.plugin.wizards.AbstractGwtWizardPage;

/**
 * @author alberto
 */
public class MigrationGuideWizardPage extends AbstractGwtWizardPage {

	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	public MigrationGuideWizardPage() {
		setTitle("Migration Guide");
		setMessage("Notes...");
	}

	////////////////////////////////////////////////////////////////////////////
	//
	// GUI
	//
	////////////////////////////////////////////////////////////////////////////
	@Override
	protected void createPageControls(Composite parent) {
		{
			IMessageContainer messagesContainer = IMessageContainer.Util.forWizardPage(this);
			GridDataFactory.create(new MigrationGuideComposite(parent, SWT.NONE, messagesContainer)).grab().fill();
		}

	}

	////////////////////////////////////////////////////////////////////////////
	//
	// Creation
	//
	////////////////////////////////////////////////////////////////////////////
	public void createService() throws Exception {

	}
}
