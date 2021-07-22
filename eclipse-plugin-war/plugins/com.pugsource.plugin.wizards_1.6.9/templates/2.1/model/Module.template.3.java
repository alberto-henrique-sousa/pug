/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %packageName%;

%importForms%
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pugsource.gwt.library.client.Tools;
import com.pugsource.gwt.library.client.popup.DialogBoxPug;
import com.pugsource.gwt.library.client.popup.MessageBoxErrorPug;
import com.pugsource.gwt.library.client.popup.MessageProgressPug;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * Template: MenuBar with login
 */
public class %className% implements EntryPoint {

	private RootPanel rootPanel;
	private VerticalPanel verticalPanelMain;
	private VerticalPanel verticalPanelArea;
	private VerticalPanel verticalPanelModule;
	private Label lblUser;

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
	    
		verticalPanelMain = new VerticalPanel();
		verticalPanelMain.setVisible(false);
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

		lblUser = new Label("Usuário");
		horizontalPanel_1.add(lblUser);

		Label lblNewLabel = new Label("|");
		horizontalPanel_1.add(lblNewLabel);

		Label lblLogout = new Label("Sair");
		lblLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				callLogout();
			}
		});
		lblLogout.setStyleName("aLink");
		horizontalPanel_1.add(lblLogout);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);	
		flexTable.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);

		verticalPanelModule = new VerticalPanel();
		verticalPanelModule.setVisible(false);
		verticalPanelModule.setSpacing(10);
		verticalPanelMain.add(verticalPanelModule);
		verticalPanelModule.setWidth("100%");

		verticalPanelArea = new VerticalPanel();
		verticalPanelArea.setStyleName("gwt-area-menu");
		verticalPanelModule.add(verticalPanelArea);
		verticalPanelArea.setWidth("100%");
	
		callLogged();
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
	
	public void callLogged() {
		MessageProgressPug.Util.show("", true);
		%classLoginService%ServiceAsync %instanceLoginService%Service = %classLoginService%Service.Util.getInstance();
		((ServiceDefTarget) %instanceLoginService%Service).setServiceEntryPoint( PathServices.%pathLoginService%SERVICE );
		%instanceLoginService%Service.logged(loginServiceCallback);			
	}
	
	public AsyncCallback<%classLoginService%DTO> loginServiceCallback = new AsyncCallback<%classLoginService%DTO>() {    
		public void onFailure(Throwable caught) {
			MessageProgressPug.Util.close();
			MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na resposta do servidor, tente novamente.", caught.getMessage(), null);			
		}    
		public void onSuccess(%classLoginService%DTO %instanceLoginService%DTO) {
			try {
				MessageProgressPug.Util.close();
				if (%instanceLoginService%DTO == null) {
					%classLoginForm% %instanceLoginForm% = new %classLoginForm%();
					%instanceLoginForm%.remember();
					DialogBoxPug.Util.showDialogBox("Login", %instanceLoginForm%, "", "",  (new CloseHandler<PopupPanel>() {
						public void onClose(CloseEvent<PopupPanel> event) {
							Tools.reload();
						};
					}));	
					%instanceLoginForm%.focus();
				} else {
					lblUser.setText(%instanceLoginService%DTO.getUsuario());
					verticalPanelMain.setVisible(true);
				}
			} catch (Exception e) {
				MessageProgressPug.Util.close();
				MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na leitura dos dados, tente novamente.", e.getMessage(), null);			
			}
		}
	};
	
	public void callLogout() {
		MessageProgressPug.Util.show("", true);
		%classLoginService%ServiceAsync %instanceLoginService%Service = %classLoginService%Service.Util.getInstance();
		((ServiceDefTarget) %instanceLoginService%Service).setServiceEntryPoint( PathServices.%pathLoginService%SERVICE );
		%instanceLoginService%Service.logout(logoutServiceCallback);			
	}
	
	public AsyncCallback<Void> logoutServiceCallback = new AsyncCallback<Void>() {    
		public void onFailure(Throwable caught) {
			MessageProgressPug.Util.close();
			MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na resposta do servidor, tente novamente.", caught.getMessage(), null);			
		}    
		public void onSuccess(Void result) {
			try {
				MessageProgressPug.Util.close();
				Tools.removeUserCookie();
				Tools.reload();
			} catch (Exception e) {
				MessageProgressPug.Util.close();
				MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na leitura dos dados, tente novamente.", e.getMessage(), null);			
			}
		}
	};	
	
}