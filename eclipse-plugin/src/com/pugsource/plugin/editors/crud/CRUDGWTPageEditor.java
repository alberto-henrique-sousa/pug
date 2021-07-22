package com.pugsource.plugin.editors.crud;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

public class CRUDGWTPageEditor extends FormEditor { //extends MultiPageEditorPart implements IResourceChangeListener{

	private TextEditor editor;
	
	public CRUDGWTPageEditor() {
		super();
		//ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	void createPage0() {
		//Section section = createStaticSection(
		//		getToolkit(),
		//		getContainer(),
		//		"DataGrid");
		
		CRUDGWTCompositeDataGrid composite = new CRUDGWTCompositeDataGrid(getContainer(), SWT.NONE); //getContainer()
		
		//section.setClient(composite);

		int index = addPage(composite);
		setPageText(index, "DataGrid");
	}

	void createPage1() {
		//Composite composite = new Composite(getContainer(), SWT.NONE);
		TreeToTableComposite composite = new TreeToTableComposite(getContainer(), SWT.NONE);
		
		int index = addPage(composite);
		setPageText(index, "Form");
	}
	
	void createPage2() {
		//Composite composite = new Composite(getContainer(), SWT.NONE);
		Section section = createStaticSection(
				getToolkit(),
				getContainer(),
				"Teste");

		int index = addPage(section);
		setPageText(index, "Order/Search");
	}	
	
	void createPage3() {
		try {
			editor = new TextEditor();
			int index = addPage(editor, getEditorInput());
			setPageText(index, editor.getTitle());
		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),
				"Error creating nested text editor",
				null,
				e.getStatus());
		}
	}	

	protected void createPages() {
		createPage0();
		createPage1();
		createPage2();
		createPage3();
	}

	public void dispose() {
		//ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
	}

	public void resourceChanged(final IResourceChangeEvent event){
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i<pages.length; i++){
						if(((FileEditorInput)editor.getEditorInput()).getFile().getProject().equals(event.getResource())){
							IEditorPart editorPart = pages[i].findEditor(editor.getEditorInput());
							pages[i].closeEditor(editorPart,true);
						}
					}
				}            
			});
		}
	}
	
	private Section createStaticSection(FormToolkit toolkit, Composite parent, String text) {
		Section section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);
		section.clientVerticalSpacing = 4;
		section.setText(text);
		return section;
	}

	@Override
	protected void addPages() {
		// TODO Auto-generated method stub
		
	}	

}
