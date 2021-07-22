/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %packageName%;

%importForms%
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pugsource.gwt.library.client.popup.MessageProgressPug;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * Template: MenuBar
 */
public class %className% implements EntryPoint {

	private RootPanel rootPanel;
	private VerticalPanel verticalPanelArea;
	private VerticalPanel verticalPanelModule;

	public void onModuleLoad() {
		DOM.setInnerHTML(RootPanel.get("gwt-div-app").getElement(), "");
		RootPanel.get("gwt-div-app").getElement().removeFromParent();

		rootPanel = RootPanel.get();
		rootPanel.setStyleName("gwt-rootPanel");
		
		Window.addWindowClosingHandler(new Window.ClosingHandler() {
			public void onWindowClosing(Window.ClosingEvent closingEvent) {
				if (MessageProgressPug.Util.isProgress())
					closingEvent.setMessage("Há uma solicitação pendente no servidor no momento. Você pode perder alterações recentes se navegar para outra página.");
			}
		});
		Window.addCloseHandler(new CloseHandler<Window>() {
			@Override
			public void onClose(CloseEvent<Window> event) {
			}			
		});
		
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {				
				String historyToken = event.getValue();
				callModule(historyToken);
			}
		});
		
		History.newItem("", false);
	    
		VerticalPanel verticalPanelMain = new VerticalPanel();
		rootPanel.add(verticalPanelMain, 0, 0);
		verticalPanelMain.setWidth("100%");

		FlexTable flexTable = new FlexTable();
		verticalPanelMain.add(flexTable);
		flexTable.setSize("100%", "");
		
		MenuBar menuBar = new MenuBar(false);
		menuBar.setAnimationEnabled(true);
		verticalPanelMain.add(menuBar);
		MenuBar menuBar_1 = new MenuBar(true);

		MenuItem mntmFormulrios = new MenuItem("Formulários", false, menuBar_1);
		
%menuItem%		
		menuBar.addItem(mntmFormulrios);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(10);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable.setWidget(0, 0, horizontalPanel);

		Label lblTitleProject = new Label("Título do Projeto");
		lblTitleProject.setStyleName("gwt-title-project");
		horizontalPanel.add(lblTitleProject);
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setSpacing(10);
		flexTable.setWidget(0, 1, horizontalPanel_1);

		verticalPanelModule = new VerticalPanel();
		verticalPanelModule.setSpacing(10);
		verticalPanelModule.setVisible(false);
		verticalPanelMain.add(verticalPanelModule);
		verticalPanelModule.setWidth("100%");

		verticalPanelArea = new VerticalPanel();
		verticalPanelArea.setStyleName("gwt-area-menu");
		verticalPanelModule.add(verticalPanelArea);
		verticalPanelArea.setWidth("100%");
	}

	private void callModule(String form) {
		if (form != null && !form.trim().isEmpty()) {
			verticalPanelArea.clear();
			verticalPanelModule.setVisible(true);
%callModules%			
		} else {
			verticalPanelModule.setVisible(false);			
		}
	}
	
}
