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
package com.pugsource.plugin.wizards.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.wb.internal.core.model.util.WorkspaceUtils;
import org.eclipse.wb.internal.core.utils.ast.AstEditor;
import org.eclipse.wb.internal.core.utils.ast.BodyDeclarationTarget;

import com.google.common.collect.Lists;
import com.pugsource.plugin.util.Tools;
import com.pugsource.plugin.wizards.service.AbstractCreateGWTOperation;

/**
 * @author alberto
 */
public class CreateDTOGWTOperation extends AbstractCreateGWTOperation {
	////////////////////////////////////////////////////////////////////////////
	//
	// Creation
	//
	////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public void create(IPackageFragment packageFragment, String dtoName, String classDAO) throws Exception {

		String fields = "";
		fields += "	private int sizeData;\n";
		IPackageFragment packageDAO = null;
		if (!classDAO.isEmpty()) {		
			IPackageFragment[] pkgs = packageFragment.getJavaProject().getPackageFragments();
			for (IPackageFragment iPackageFragment : pkgs) {
				ICompilationUnit[] compUnits = iPackageFragment.getCompilationUnits();
				for (ICompilationUnit iCompilationUnit : compUnits) {
					CompilationUnit ast = getAst(iCompilationUnit, false);
					for(AbstractTypeDeclaration type : (List<AbstractTypeDeclaration>)ast.types()) {
						if(type instanceof TypeDeclaration) { 
							TypeDeclaration td = (TypeDeclaration)type;
							if (td.resolveBinding().getPackage().getName().indexOf("server.dao") > -1) {
								packageDAO = iPackageFragment;
							}
							if (classDAO.equals(td.resolveBinding().getName() + " (" +td.resolveBinding().getPackage().getName() + ")")) {
								IVariableBinding[] iVariableBinding = td.resolveBinding().getDeclaredFields();
								// variables
								for (IVariableBinding iVariableBinding2 : iVariableBinding) {
									String fieldName = iVariableBinding2.getName();
									String fieldType = fieldTypeDTO(iVariableBinding2.getType().getName());
									if (validFieldName(fieldName, fieldType)) {
										fields += "	private " + fieldType + " " + fieldName + ";\n";
									}	
								}								
								// getters e setters
								fields += "\n";
								fields += "	public int getSizeData() {\n";
								fields += "		return sizeData;\n";
								fields += "	}\n";
								fields += "\n";
								fields += "	public void setSizeData(int sizeData) {\n";
								fields += "		this.sizeData = sizeData;\n";
								fields += "	}\n";								
								for (IVariableBinding iVariableBinding2 : iVariableBinding) {
									String fieldName = iVariableBinding2.getName();
									String fieldType = fieldTypeDTO(iVariableBinding2.getType().getName());
									if (validFieldName(fieldName, fieldType)) {
										String fieldNameCamel = fieldName.substring(0, 1).toUpperCase();
										if (fieldName.length() > 1)
											fieldNameCamel = fieldNameCamel.substring(0, 1) + fieldName.substring(1);
										fields += "\n";
										fields += "	public "+fieldType+" get"+fieldNameCamel+"() {\n";
										fields += "		return "+fieldName+";\n";
										fields += "	}\n";
										fields += "\n";
										fields += "	public void set"+fieldNameCamel+"("+fieldType+" "+fieldName+") {\n";
										fields += "		this."+fieldName+" = "+fieldName+";\n";
										fields += "	}\n";
									}	
								}
							}
						}
					}
				}
			}
		}

		// prepare packages names
		String servicePackageName = packageFragment.getElementName();
		// prepare variables
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("version", Tools.VERSION);
		variables.put("servicePackage", servicePackageName);
		variables.put("dtoName", dtoName);
		variables.put("fields", fields);
		// client
		{
			// create RemoteService interface, "async" interface will be done by builder
			createFileFromTemplate(
					packageFragment,
					dtoName + ".java",
					"DTO.java",
					variables);
		}
		// DaoFactory
		{
			if (packageDAO != null) {
				ICompilationUnit factoryUnit =
						packageDAO.getCompilationUnit("DaoFactory.java");			
				AstEditor editor = new AstEditor(factoryUnit);
				MethodDeclaration methodDeclaration;
				TypeDeclaration factoryPrimaryType = editor.getPrimaryType();
				BodyDeclarationTarget bodyDeclarationTarget =
						new BodyDeclarationTarget(factoryPrimaryType, false);
				classDAO = classDAO.substring(0, classDAO.indexOf(" ")).trim();
				String classDAONameCamel = classDAO.substring(0, 1).toUpperCase();
				if (classDAO.length() > 1)
					classDAONameCamel = classDAONameCamel.substring(0, 1) + classDAO.substring(1);				
		        String methodHeader = "public Dao<"+classDAONameCamel+"> get"+classDAONameCamel+"Dao()";
		        final List<String> lines = Lists.newArrayList("return new Dao<"+classDAONameCamel+">(this.session, "+classDAONameCamel+".class);");
		        methodDeclaration = editor.addMethodDeclaration(methodHeader, lines, bodyDeclarationTarget);				
		        editor.resolveImports(methodDeclaration);
		        editor.saveChanges(true);
			}
		}		
		// open DTO in editor
		{
			String qualifiedServiceName = packageFragment.getElementName() + "." + dtoName;
			IType type =
					WorkspaceUtils.waitForType(packageFragment.getJavaProject(), qualifiedServiceName);
			JavaUI.openInEditor(type);
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

	public static String fieldTypeDTO(String field) {
		String newField = field;
		//if (newField.equals(""))
		//	newField = "";
		return newField;
	}

	public static boolean validFieldName(String fieldName, String fieldType) {
		return (!fieldName.equals("serialVersionUID") &&
				fieldName.indexOf("sizeData") == -1 &&
				fieldName.indexOf("<") == -1 &&
				fieldType.indexOf("<") == -1 &&
				!fieldType.equals("Set"));
	}

}
