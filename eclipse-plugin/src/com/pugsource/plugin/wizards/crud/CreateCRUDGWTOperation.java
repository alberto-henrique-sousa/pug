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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.internal.core.model.util.WorkspaceUtils;

import com.pugsource.plugin.util.Tools;
import com.pugsource.plugin.wizards.service.AbstractCreateGWTOperation;

/**
 * @author alberto
 */
public class CreateCRUDGWTOperation extends AbstractCreateGWTOperation {
	////////////////////////////////////////////////////////////////////////////
	//
	// Creation
	//
	////////////////////////////////////////////////////////////////////////////
	public void create(IPackageFragment packageFragment, String crudName, String title, int titlePosition, String size, String classDTO, TableItem[] tableOrder, TableItem[] tableSearch, TableItem[] tableGrid, TableItem[] tableForm, String template, String formDialogBox) throws Exception {

		// prepare packages names
		String servicePackageName = packageFragment.getElementName();
		classDTO = classDTO.substring(0, classDTO.indexOf(" ")).trim();
		String instanceDTO = classDTO.substring(0, 1).toLowerCase() + classDTO.substring(1);			
		String classDAO = classDTO.substring(0, classDTO.length()-3);
		String instanceDAO = instanceDTO.substring(0, classDTO.length()-3);
		String imports = "";
		String methods = "";
		String fieldsDeclare = "";
		String fieldsInstance = "";
		String fieldsValid = "";
		String fieldsValidClean = "";
		String fieldsEdit = "";
		String fieldFocus = "";
		String fieldsAdd = "";
		String fieldsClear = "";
		String columnsGrid = "";
		String fieldsOrder = "";
		String fieldsSearch = "";
		String fieldsSearchArg = "";
		String fieldUsername = "usuario";
		String fieldPassword = "senha";
		String commentLogin = "Replace \"username\" and \"password\" in the fields of the table";

		boolean upload = false;

		for (TableItem tableItem : tableOrder) {
			fieldsOrder += "		comboBoxOrder.addItem(\""+Tools.stringToUTF8(tableItem.getText(0))+"\", \""+tableItem.getText(1)+"\");\n";
		}
		if (fieldsOrder.trim().isEmpty()) {
			fieldsOrder += "		lblOrder.setVisible(false);\n";
			fieldsOrder += "		horizontalPanelOrder.setVisible(false);\n";
		} else {
			fieldsOrder += "		comboBoxOrder.setSelectedIndex(0);\n";
		}

		for (TableItem tableItem : tableSearch) {
			String styleName = "";
			String mask = "";
			boolean comboBox = tableItem.getText(2).equals("ComboBox");
			boolean listBox = tableItem.getText(2).equals("ListBox");
			if (comboBox) {
				tableItem.setText(2, "ListBox");
			}
			if (tableItem.getText(2).equals("IntegerBox") ||
				tableItem.getText(2).equals("DoubleBox") ||	
				tableItem.getText(2).equals("LongBox"))
				styleName = "styleName=\"gwt-TextBox\"";
			String tag = "g";
			if (tableItem.getText(2).equals("DateBox"))
				tag = "p2";
			else if (tableItem.getText(2).equals("MaskDecimalTextBox") ||
					tableItem.getText(2).equals("MaskIntegerTextBox") ||
					tableItem.getText(2).equals("MaskTextBox")) {
				tag = "pug";
				if (!tableItem.getText(3).trim().isEmpty() || tableItem.getText(2).equals("MaskTextBox")) {
					mask = " mask=\""+tableItem.getText(3)+"\"";
				}
			}	
			fieldsSearch += "						<g:row>\n";
			fieldsSearch += "							<g:customCell>\n";
			fieldsSearch += "								<g:Label text=\""+Tools.stringToUTF8(tableItem.getText(0))+"\" horizontalAlignment=\"ALIGN_RIGHT\" />\n";
			fieldsSearch += "							</g:customCell>\n";
			fieldsSearch += "							<g:customCell>\n";
			fieldsSearch += "								<"+tag+":"+tableItem.getText(2)+" ui:field=\""+tableItem.getText(1)+"\" "+styleName+(listBox ? " visibleItemCount=\"5\"" : "")+mask+"/>\n";
			fieldsSearch += "							</g:customCell>\n";
			fieldsSearch += "						</g:row>\n";
			if (tableItem.getText(2).equals("ListBox") ||
				tableItem.getText(2).equals("ComboBox")	)
				fieldsSearchArg += tableItem.getText(1) + ".getValue("+tableItem.getText(1)+".getSelectedIndex()), "; 
			else	
				fieldsSearchArg += tableItem.getText(1) + ".getValue(), "; 
			
			fieldsDeclare += "	@UiField "+tableItem.getText(2)+" "+tableItem.getText(1)+";\n";
		}

		for (TableItem tableItem : tableForm) {
			String mask = "";
			String styleName = "";
			if (tableItem.getText(4).equals("IntegerBox") ||
				tableItem.getText(4).equals("DoubleBox") ||	
				tableItem.getText(4).equals("LongBox"))
				styleName = "styleName=\"gwt-TextBox\"";
			boolean comboBox = tableItem.getText(4).equals("ComboBox");
			boolean listBox = tableItem.getText(4).equals("ListBox");
			if (comboBox) {
				tableItem.setText(4, "ListBox");
			}			
			String tag = "g";
			if (tableItem.getText(4).equals("DateBox"))
				tag = "p2";
			else if (tableItem.getText(4).equals("MaskDecimalTextBox") ||
					tableItem.getText(4).equals("MaskIntegerTextBox") ||
					tableItem.getText(4).equals("MaskTextBox") ||
					tableItem.getText(4).equals("RichTextPug")) {
				tag = "pug";
				if (!tableItem.getText(8).trim().isEmpty() || tableItem.getText(4).equals("MaskTextBox")) {
					mask = " mask=\""+tableItem.getText(8)+"\"";
				}
			}	
			if (!tableItem.getText(6).trim().isEmpty() && !tableItem.getText(6).equals("0")) {
				mask += " maxLength=\""+tableItem.getText(6)+"\"";
			}
			if (!tableItem.getText(7).trim().isEmpty() && !tableItem.getText(7).equals("0") && !tableItem.getText(7).equals("0px")) {
				mask += " width=\""+tableItem.getText(7)+"\"";
			}			

			String fieldCamel = tableItem.getText(1).substring(0, 1).toUpperCase() + tableItem.getText(1).substring(1);
			
			fieldsInstance += "					<g:row>\n";
			fieldsInstance += "						<g:customCell>\n";
			fieldsInstance += "							<g:Label text=\""+Tools.stringToUTF8(tableItem.getText(0))+"\" horizontalAlignment=\""+(titlePosition == 0 ? "ALIGN_RIGHT" : "ALIGN_LEFT")+"\"\n";
			fieldsInstance += "								ui:field=\"lbl"+tableItem.getText(3)+"\" />\n";
			fieldsInstance += "						</g:customCell>\n";
			if (titlePosition == 1) {
				fieldsInstance += "					</g:row>\n";
				fieldsInstance += "					<g:row>\n";				
			}
			fieldsInstance += "						<g:customCell>\n";
			fieldsInstance += "							<g:HorizontalPanel verticalAlignment=\"ALIGN_MIDDLE\">\n";
			if (tableItem.getText(4).equals("FileUpload")) {
				fieldsDeclare += "	@UiField FormPanel formPanel"+tableItem.getText(3)+";\n";
				fieldsDeclare += "	@UiField Label lblFormPanel"+tableItem.getText(3)+";\n";
				fieldsInstance += "								<g:FormPanel ui:field=\"formPanel"+tableItem.getText(3)+"\" method=\"METHOD_POST\" encoding=\"ENCODING_MULTIPART\">\n";				
				fieldsInstance += "									<g:FileUpload name=\"file\" width=\"100%\" height=\"100%\" ui:field=\""+tableItem.getText(3)+"\" "+styleName+"/>\n";				
				fieldsInstance += "								</g:FormPanel>\n";				
				fieldsInstance += "								<g:Label width=\"5px\"/>\n";				
				fieldsInstance += "								<g:Label text=\"Enviar arquivo\" styleName=\"aLink\" ui:field=\"lblFormPanel"+tableItem.getText(3)+"\"/>\n";
				fieldsOrder += "		formPanel"+tableItem.getText(3)+".setAction(PathServices.UPLOADSERVICE);\n";										
				fieldsOrder += "		formPanel"+tableItem.getText(3)+".setMethod(FormPanel.METHOD_POST);\n";										
				fieldsOrder += "		formPanel"+tableItem.getText(3)+".setEncoding(FormPanel.ENCODING_MULTIPART);\n";										
				methods += "	@UiHandler(\"formPanel"+tableItem.getText(3)+"\")\n";
				methods += "	void onFormPanel"+tableItem.getText(3)+"SubmitComplete(SubmitCompleteEvent event) {\n";
				methods += "		MessageProgressPug.Util.close();\n";										
				methods += "		HTML html = new HTML(event.getResults());\n";										
				methods += "		if (html.getText().equals(\"0\"))\n";										
				methods += "			MessageBoxErrorPug.Util.showMessage(\"Erro!\", \"Falha no envio da imagem...\", \"Erro interno.\", null);\n";										
				methods += "		else {\n";										
				methods += "			MessageBoxPug.Util.showMessage(\"Sucesso!\", html.getText(), null);\n";										
				methods += "		}\n";										
				methods += "	}\n\n";				
				methods += "	@UiHandler(\"formPanel"+tableItem.getText(3)+"\")\n";
				methods += "	void onFormPanel"+tableItem.getText(3)+"Submit(SubmitEvent event) {\n";
				methods += "		if ("+tableItem.getText(3)+".getFilename().trim().isEmpty()) {\n";										
				methods += "			event.cancel();\n";										
				methods += "			MessageProgressPug.Util.close();\n";										
				methods += "			MessageBoxPug.Util.showMessage(\"Erro!\", \"Selecione o arquivo para envio.\", null);\n";										
				methods += "		}\n";										
				methods += "	}\n\n";
				methods += "	@UiHandler(\"lblFormPanel"+tableItem.getText(3)+"\")\n";
				methods += "	void onLblFormPanel"+tableItem.getText(3)+"Click(ClickEvent event) {\n";
				methods += "		MessageProgressPug.Util.show(\"\", true);\n";										
				methods += "		formPanel"+tableItem.getText(3)+".submit();\n";										
				methods += "	}\n";
				upload = true;
			} else {
				fieldsInstance += "								<"+tag+":"+tableItem.getText(4)+" ui:field=\""+tableItem.getText(3)+"\" "+styleName+(listBox ? " visibleItemCount=\"5\"" : "")+mask+"/>\n";
			}
			if (!tableItem.getText(5).trim().isEmpty()) {
				fieldsDeclare += "	@UiField Image formSearch"+tableItem.getText(3)+";\n";
				fieldsInstance += "								<g:Label width=\"5px\"/>\n";
				fieldsInstance += "								<g:Image ui:field=\"formSearch"+tableItem.getText(3)+"\"/>\n";
				fieldsOrder += "		formSearch"+tableItem.getText(3)+".setResource(Resources.lupa());\n";
				fieldsOrder += "		formSearch"+tableItem.getText(3)+".setStyleName(\"aLink\");\n";
				String formInstance = tableItem.getText(5);
				formInstance = formInstance.toLowerCase() + formInstance.substring(1);
				methods += "	@UiHandler(\"formSearch"+tableItem.getText(3)+"\")\n";
				methods += "	void onImageClick(ClickEvent event) {\n";
				methods += "		ConfigFormPug configFormPug = new ConfigFormPug();\n";
				methods += "		configFormPug.setPopupSearch(true);\n";
				methods += "		"+tableItem.getText(5)+" "+formInstance+" = new "+tableItem.getText(5)+"(configFormPug);\n";
				methods += "		DialogBoxPug.Util.showDialogBox(\"\", "+formInstance+", \"500px\", \"\", (new CloseHandler<PopupPanel>() {\n";
				methods += "			public void onClose(CloseEvent<PopupPanel> event) {\n";
				methods += "				// return form...\n";										
				methods += "			}\n";					
				methods += "		}));\n";				
				methods += "	}\n";
			}
			fieldsInstance += "								<g:Label ui:field=\"lblError"+tableItem.getText(3)+"\" styleName=\"gwt-msg-error-validation\" visible=\"false\"/>\n";
			fieldsInstance += "							</g:HorizontalPanel>\n";
			fieldsInstance += "						</g:customCell>\n";
			fieldsInstance += "					</g:row>\n";
			
			fieldsDeclare += "	@UiField "+tableItem.getText(4)+" "+tableItem.getText(3)+";\n";
			fieldsDeclare += "	@UiField Label lbl"+tableItem.getText(3)+";\n";										
			fieldsDeclare += "	@UiField Label lblError"+tableItem.getText(3)+";\n";
			
			if (tableItem.getText(4).equals("DateBox"))
				fieldsOrder += "		"+tableItem.getText(3)+".setFormat(new DefaultFormat(DateTimeFormat.getFormat(\"dd/MM/yyyy\")));\n";

			if (!tableItem.getText(9).trim().isEmpty() && !tableItem.getText(4).equals("FileUpload")) {
				fieldsValid = "			valid = Tools.validate(valid, "+tableItem.getText(9)+", lbl"+tableItem.getText(3)+", "+tableItem.getText(3)+", lblError"+tableItem.getText(3)+", \""+Tools.stringToUTF8(tableItem.getText(10))+".\");\n" + fieldsValid;
				fieldsValidClean = "		Tools.validate(true, true, lbl"+tableItem.getText(3)+", "+tableItem.getText(3)+", lblError"+tableItem.getText(3)+", \"\");\n" + fieldsValidClean;
			}				

			if (tableItem.getText(4).equals("IntegerBox") || tableItem.getText(4).equals("MaskIntegerTextBox")) {
				fieldsClear += "		"+tableItem.getText(3)+".setValue(0);\n";
			} else if (tableItem.getText(4).equals("LongBox")) {
				fieldsClear += "		"+tableItem.getText(3)+".setValue(0L);\n";
			} else if (tableItem.getText(4).equals("DoubleBox") || tableItem.getText(4).equals("MaskDecimalTextBox")) {
				fieldsClear += "		"+tableItem.getText(3)+".setValue(0D);\n";
			} else if (tableItem.getText(4).equals("DateBox")) {
				fieldsClear += "		"+tableItem.getText(3)+".setValue(null);\n";
			} else if (tableItem.getText(4).equals("CheckBox")) {
				fieldsClear += "		"+tableItem.getText(3)+".setValue(false);\n";
			} else if (tableItem.getText(4).equals("ListBox")) {
				fieldsClear += "		"+tableItem.getText(3)+".setSelectedIndex(-1);\n";
			} else if (tableItem.getText(4).equals("RichTextPug")) {
				fieldsClear += "		"+tableItem.getText(3)+".setHTML(\"\");\n";
			} else {
				if (!tableItem.getText(4).equals("FileUpload"))
					fieldsClear += "		"+tableItem.getText(3)+".setValue(\"\");\n";				
			}

			if (!tableItem.getText(4).equals("FileUpload")) {
				if (tableItem.getText(4).equals("ListBox")) {
					fieldsEdit += "					"+tableItem.getText(3)+".setSelectedIndex(Tools.findItemListBox("+tableItem.getText(3)+","+instanceDTO+".get"+fieldCamel+"()));\n";				
					fieldsAdd += "			"+instanceDTO+".set"+fieldCamel+"("+tableItem.getText(3)+".getValue("+tableItem.getText(3)+".getSelectedIndex()));\n";
				} else if (tableItem.getText(4).equals("RichTextPug")) {
					fieldsEdit += "					"+tableItem.getText(3)+".setHTML("+instanceDTO+".get"+fieldCamel+"());\n";				
					fieldsAdd += "			"+instanceDTO+".set"+fieldCamel+"("+tableItem.getText(3)+".getHTML());\n";
				} else if (tableItem.getText(4).equals("MaskDecimalTextBox")) {
					fieldsEdit += "					"+tableItem.getText(3)+".setValue("+instanceDTO+".get"+fieldCamel+"());\n";
					fieldsAdd += "			"+instanceDTO+".set"+fieldCamel+"("+tableItem.getText(3)+".getValueDouble());\n";	
				} else if (tableItem.getText(4).equals("MaskIntegerTextBox")) {
					fieldsEdit += "					"+tableItem.getText(3)+".setValue("+instanceDTO+".get"+fieldCamel+"());\n";
					fieldsAdd += "			"+instanceDTO+".set"+fieldCamel+"("+tableItem.getText(3)+".getValueInteger());\n";	
				} else {
					fieldsEdit += "					"+tableItem.getText(3)+".setValue("+instanceDTO+".get"+fieldCamel+"());\n";
					fieldsAdd += "			"+instanceDTO+".set"+fieldCamel+"("+tableItem.getText(3)+".getValue());\n";
				}
			}
			if (fieldFocus.trim().isEmpty())
				fieldFocus = tableItem.getText(3) + ".setFocus(true);";
		}

		for (TableItem tableItem : tableGrid) {
			String fieldCamel = tableItem.getText(1).substring(0, 1).toUpperCase() + tableItem.getText(1).substring(1);
			String type = tableItem.getText(4);
			String typeC = "String";
			String argC = ""; 
			if (tableItem.getText(3).equals("NumberCell"))
				typeC = "Number";
			else if (tableItem.getText(3).equals("DateCell") || tableItem.getText(3).equals("DatePickerCell")) {
				typeC = "Date";
				argC = "dateFormat";				
			} else if (tableItem.getText(3).equals("CheckboxCell")) {
				typeC = "Boolean";
			}
			if (tableItem.getText(3).equals("SelectionCell")) {				
				columnsGrid += "		ArrayList<String> "+tableItem.getText(1)+"ColumnOptions = new ArrayList<String>();\n";
				argC = tableItem.getText(1) + "ColumnOptions";
			}
			columnsGrid += "		Column<"+classDTO+", "+typeC+"> "+tableItem.getText(1)+"Column = new Column<"+classDTO+", "+typeC+">(new "+tableItem.getText(3)+"("+argC+")) {\n";
			columnsGrid += "			@Override\n";
			columnsGrid += "			public "+typeC+" getValue("+classDTO+" "+instanceDTO+") {\n";
			if (type.equals("Boolean") && !tableItem.getText(3).equals("CheckboxCell")) {
				columnsGrid += "				return "+instanceDTO+".get"+fieldCamel+"() ? \"Sim\" : \"Não\";\n";
			} else {
				if (!tableItem.getText(6).trim().isEmpty()) {
					columnsGrid += "				return Formatter.format(\""+tableItem.getText(6)+"\","+instanceDTO+".get"+fieldCamel+"());\n";					
				} else {
					if (tableItem.getText(3).equals("EditTextCell") && !typeC.equals("String")) 
						columnsGrid += "				return String.valueOf("+instanceDTO+".get"+fieldCamel+"());\n";
					else
						columnsGrid += "				return "+instanceDTO+".get"+fieldCamel+"();\n";
				}	
			}	
			columnsGrid += "			}\n";
			columnsGrid += "		};\n";
			if (tableItem.getText(3).equals("CheckboxCell") ||
					tableItem.getText(3).equals("DatePickerCell") ||
					tableItem.getText(3).equals("EditTextCell") ||
					tableItem.getText(3).equals("SelectionCell")) {
				String argUpdate = "value";
				if (tableItem.getText(3).equals("EditTextCell")) {
					argUpdate = type + ".valueOf(value)";
					type = "String";					
				}
				columnsGrid += "		"+tableItem.getText(1)+"Column.setFieldUpdater(new FieldUpdater<"+classDTO+", "+type+">() {\n";
				columnsGrid += "			public void update(int index, "+classDTO+" "+instanceDTO+", "+type+" value) {\n";
				columnsGrid += "				"+instanceDTO+"Edit = "+instanceDTO+";\n";
				columnsGrid += "				rowUpdate = index;\n";
				columnsGrid += "				" + instanceDTO + ".set" + fieldCamel + "("+argUpdate+");\n"  ;
				columnsGrid += "				callSave();\n"  ;
				columnsGrid += "			}\n";
				columnsGrid += "		});\n";
			}
			columnsGrid += "		"+tableItem.getText(1)+"Column.setCellStyleNames(\"gwt-cell-description\");\n";
			columnsGrid += "		dataGrid.addColumn("+tableItem.getText(1)+"Column, \""+Tools.stringToUTF8(tableItem.getText(0))+"\");\n";
			if (!tableItem.getText(5).trim().isEmpty() && !tableItem.getText(5).equals("0")) {
				columnsGrid += "		dataGrid.setColumnWidth("+tableItem.getText(1)+"Column, "+tableItem.getText(5)+", Unit.PX);\n";
			}
			columnsGrid += "\n";
		}

		// prepare variables
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("version", Tools.VERSION);
		variables.put("formPackage", servicePackageName);
		String notForm = servicePackageName.substring(0, servicePackageName.length()-5);
		variables.put("clientPackage", notForm);
		variables.put("crudName", crudName);
		variables.put("title", Tools.stringToUTF8(title));
		variables.put("classDTO", classDTO);
		variables.put("instanceDTO", instanceDTO);		
		variables.put("classDAO", classDAO);
		variables.put("instanceDAO", instanceDAO);
		variables.put("pathServices", classDAO.toUpperCase());
		variables.put("methods", methods);
		variables.put("fieldsDeclare", fieldsDeclare);
		variables.put("fieldsInstance", fieldsInstance);
		variables.put("fieldsValid", fieldsValid);
		variables.put("fieldsValidClean", fieldsValidClean);
		variables.put("fieldsEdit", fieldsEdit);
		variables.put("fieldFocus", fieldFocus);
		variables.put("fieldsClear", fieldsClear);
		variables.put("fieldsAdd", fieldsAdd);
		variables.put("fieldsOrder", fieldsOrder);
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
		if (upload) {
			imports += "import com.google.gwt.user.client.ui.FileUpload;\n";
			imports += "import com.google.gwt.user.client.ui.FormPanel;\n";
			imports += "import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;\n";
			imports += "import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;\n";
			imports += "import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;\n";
			imports += "import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;\n";
			imports += "import com.google.gwt.user.client.ui.HTML;\n";
			imports += "import com.pugsource.gwt.library.client.popup.MessageBoxPug;\n";
		}
		variables.put("imports", imports);				

		// client
		{
			createFileFromTemplate(
					packageFragment,
					crudName + ".java",
					"Form."+template+".java",
					variables);
			if (!template.equals("Login")) {
				createFileFromTemplate(
						packageFragment,
						crudName + ".ui.xml",
						"Form."+template+".ui.xml",
						variables);
			}
		}
		// open DTO in editor
		{
			String qualifiedServiceName = packageFragment.getElementName() + "." + crudName;
			IType type =
					WorkspaceUtils.waitForType(packageFragment.getJavaProject(), qualifiedServiceName);
			JavaUI.openInEditor(type);
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
