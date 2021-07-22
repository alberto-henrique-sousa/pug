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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.swt.widgets.TableItem;

import br.com.pug.plugin.jsf.util.Tools;
import br.com.pug.plugin.jsf.wizards.AbstractCreateOperation;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class CreateXHTMLperation extends AbstractCreateOperation {
	////////////////////////////////////////////////////////////////////////////
	//
	// Creation
	//
	////////////////////////////////////////////////////////////////////////////
	public void create(IPackageFragment packageFragment, String webContext, String crudName, String title, int titlePosition, String classDAO, TableItem[] tableGrid, TableItem[] tableForm, String formDialogBox) throws Exception {

		// prepare packages names
		String servicePackageName = packageFragment.getElementName();
		String instanceDAO = classDAO.substring(0, 1).toLowerCase() + classDAO.substring(1);			
		String imports = "";
		String methods = "";
		String fieldsDeclare = "";
		String fieldsGrid = "";
		String fieldsForm = "";
		String fieldsInstance = "";
		String fieldsValid = "";
		String fieldsValidClean = "";
		String fieldsEdit = "";
		String fieldFocus = "";
		String fieldsAdd = "";
		String fieldsClear = "";
		String columnsGrid = "";
		String fieldsSearch = "";
		String fieldsSearchArg = "";
		String fieldUsername = "usuario";
		String fieldPassword = "senha";
		String commentLogin = "Replace \"username\" and \"password\" in the fields of the table";

		boolean upload = false;

		String focusField = "";
		for (TableItem tableItem : tableForm) {
			String readOnly = "#{"+crudName+"Bean.view}";
			if (tableItem.getText(1).equals("id")) {
				readOnly = "true";				
			}
			if (focusField.isEmpty() && !tableItem.getText(1).equals("id")) {
				focusField = tableItem.getText(1);
			}	
			String fieldCamel = tableItem.getText(1).substring(0, 1).toUpperCase() + tableItem.getText(1).substring(1);
			fieldCamel = fieldCamel.replace(".", "");
			String field = tableItem.getText(1).replace(".", "");
			String mask = "";
			String maxLength = "";
			String with = "";
			String required = "";
			if (!tableItem.getText(5).trim().isEmpty() && tableItem.getText(4).equals("inputMask")) {
				mask = " mask=\""+tableItem.getText(5).trim()+"\"";
			}
			if (tableItem.getText(4).equals("inputMask")) {
				if (!tableItem.getText(2).trim().isEmpty())
					maxLength = " maxlength=\"" + tableItem.getText(2).trim()+"\"";
				if (!tableItem.getText(3).trim().isEmpty())
					with = " style=\"width: " + tableItem.getText(3).trim()+"\"";
			}	
			if (tableItem.getText(6).equals("true")) {
				required = " required=\"true\" requiredMessage=\""+Tools.stringToUTF8(tableItem.getText(7).trim())+"\"";
			}
			
			fieldsForm += "		        		<h:outputLabel id=\""+crudName+"Label"+fieldCamel+"\" for=\""+field+"\" value=\""+Tools.stringToUTF8(tableItem.getText(0))+"\" />\n";
			if (tableItem.getText(4).equals("buttonSearch")) {
				fieldsForm += "			        	<p:commandButton icon=\"ui-icon-search\" actionListener=\"#{"+crudName+"Bean.dialog"+fieldCamel+"}\" disabled=\"#{"+crudName+"Bean.view}\">\n";
				fieldsForm += "		        			<p:ajax event=\"dialogReturn\" listener=\"#{"+crudName+"Bean.onSelect"+fieldCamel+"}\" update=\""+field+"\" />\n";
				fieldsForm += "			        		<p:inputMask id=\""+field+"\" label=\""+Tools.stringToUTF8(tableItem.getText(0))+"\" value=\"#{"+crudName+"Bean."+crudName+"."+tableItem.getText(1)+"}\""+mask+" readonly=\"true\"/>\n";								
				fieldsForm += "	        				<p:spacer width=\"5px\"/>\n";
				fieldsForm += "		        		</p:commandButton>\n";
			} else if (tableItem.getText(4).equals("comboBox")) {
				fieldsForm += "			        	<p:selectOneMenu id=\""+field+"\""+required+" value=\"#{"+crudName+"Bean."+crudName+"."+tableItem.getText(1)+"}\" converter=\""+field+"Converter\" disabled=\"#{"+crudName+"Bean.view}\">\n";
				fieldsForm += "							<f:selectItem itemLabel=\"Selecione...\" itemValue=\"\" />\n";
				fieldsForm += "							<f:selectItems value=\"#{"+crudName+"Bean.list"+fieldCamel+"}\" var=\""+field+"\" itemLabel=\"#{"+field+".nome}\" itemValue=\"#{"+field+"}\"/>\n";
				fieldsForm += "			        	</p:selectOneMenu>\n";
			} else {
				if (tableItem.getText(4).equals("calendar")) {
					fieldsForm += "			        	<p:"+tableItem.getText(4)+" id=\""+field+"\" label=\""+Tools.stringToUTF8(tableItem.getText(0))+"\""+required+" value=\"#{"+crudName+"Bean."+crudName+"."+tableItem.getText(1)+"}\" pattern=\"dd/MM/yyyy\" mask=\"true\" disabled=\"#{"+crudName+"Bean.view}\"/>\n";					
				} else {
					String textArea = "";
					if (tableItem.getText(4).equals("inputTextarea"))
						textArea = " rows=\"5\" cols=\"60\" autoResize=\"false\"";
					fieldsForm += "			        	<p:"+tableItem.getText(4)+" id=\""+field+"\""+textArea+" label=\""+Tools.stringToUTF8(tableItem.getText(0))+"\""+required+maxLength+with+" value=\"#{"+crudName+"Bean."+crudName+"."+tableItem.getText(1)+"}\""+mask+" "+(tableItem.getText(4).equals("selectBooleanCheckbox") ? "disabled" : "readonly")+"=\""+readOnly+"\"/>\n";
				}	
			}
			fieldsForm += "			        	<p:message id=\""+crudName+"MsgError"+fieldCamel+"\" for=\""+field+"\" display=\"icon, text\"/>\n";							
		}
		fieldsForm += "			        	<p:focus for=\""+focusField+"\"/>";

		for (TableItem tableItem : tableGrid) {
			fieldsGrid += "						<p:column headerText=\""+Tools.stringToUTF8(tableItem.getText(0))+"\" sortBy=\"#{data"+classDAO+"."+tableItem.getText(1)+"}\" filterBy=\"#{data"+classDAO+"."+tableItem.getText(1)+"}\">\n";
			if (tableItem.getText(2).equals("outputText")) { 
				fieldsGrid += "							<h:outputText value=\"#{data"+classDAO+"."+tableItem.getText(1)+"}\" />\n";
			} else if (tableItem.getText(2).equals("selectBooleanCheckbox")) {
				fieldsGrid += "							<h:selectBooleanCheckbox value=\"#{data"+classDAO+"."+tableItem.getText(1)+"}\"  disabled=\"true\"/>\n";				
			} else {
				fieldsGrid += "							<h:outputText value=\"#{data"+classDAO+"."+tableItem.getText(1)+"}\" >\n";				
				fieldsGrid += "								<f:convertDateTime pattern=\"dd/MM/yyyy\" />\n";				
				fieldsGrid += "							</h:outputText>\n";				
			}
			fieldsGrid += "						</p:column>\n";			
		}

		// prepare variables
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("version", Tools.VERSION);
		variables.put("formPackage", servicePackageName);
		variables.put("clientPackage", servicePackageName);
		variables.put("crudName", crudName);
		variables.put("layoutName", crudName);
		variables.put("classLayoutName", classDAO);
		variables.put("title", Tools.stringToUTF8(title));
		variables.put("classDAO", classDAO);
		variables.put("instanceDAO", instanceDAO);
		variables.put("pathServices", classDAO.toUpperCase());
		variables.put("methods", methods);
		variables.put("fieldsForm", fieldsForm);
		variables.put("fieldsGrid", fieldsGrid);
		variables.put("fieldsDeclare", fieldsDeclare);
		variables.put("fieldsInstance", fieldsInstance);
		variables.put("fieldsValid", fieldsValid);
		variables.put("fieldsValidClean", fieldsValidClean);
		variables.put("fieldsEdit", fieldsEdit);
		variables.put("fieldFocus", fieldFocus);
		variables.put("fieldsClear", fieldsClear);
		variables.put("fieldsAdd", fieldsAdd);
		variables.put("fieldsSearch", fieldsSearch);
		variables.put("fieldsSearchArg", fieldsSearchArg);
		variables.put("columnsGrid", columnsGrid);		
		String instanceFormDialogBox = "";
		if (!formDialogBox.trim().isEmpty())
			instanceFormDialogBox = formDialogBox.substring(0, 1).toLowerCase() + formDialogBox.substring(1);			
		variables.put("classFormDialogBox", formDialogBox);		
		variables.put("instanceFormDialogBox", instanceFormDialogBox);		
		variables.put("fieldUsername", fieldUsername);		
		variables.put("fieldPassword", fieldPassword);		
		variables.put("commentLogin", fieldUsername.equals("username") && fieldPassword.equals("password") ? commentLogin: "");
		variables.put("imports", imports);				

		// client
		IJavaProject javaProject = packageFragment.getJavaProject();
	    IProject project = javaProject.getProject();
	    IFolder pagesFolder = project.getFolder(webContext);

		{		    
		    createFileFromTemplate(
		    		pagesFolder,
					"full" + crudName + ".xhtml",
					"fullbean.xhtml",
					variables);
		    createFileFromTemplate(
		    		pagesFolder,
					"dialog" + crudName + ".xhtml",
					"dialogbean.xhtml",
					variables);
			createFileFromTemplate(
					pagesFolder,
					crudName + ".xhtml",
					"bean.xhtml",
					variables);
		}
	}

	public static String fieldTypeDTO(String field) {
		String newField = field;
		//if (newField.equals(""))
		//	newField = "";
		return newField;
	}

	public static boolean validFieldName(String fieldName, String fieldType) {
		return (!fieldName.equals("serialVersionUID") &&
				fieldName.indexOf("<") == -1 &&
				!fieldType.equals("Set"));
	}

	public String typeComp(String type) {
		String ret = "textBox";
		if (type.equals("int") || type.equals("Integer"))
			ret = "integerBox";
		else if (type.toLowerCase().equals("long"))
			ret = "longBox";
		else if (type.toLowerCase().equals("double"))
			ret = "doubleBox";
		else if (type.toLowerCase().equals("date"))
			ret = "dateBox";
		else if (type.toLowerCase().equals("boolean"))
			ret = "checkBox";
		return ret;
	}

}
