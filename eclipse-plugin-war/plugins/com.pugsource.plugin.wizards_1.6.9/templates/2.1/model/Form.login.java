/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %formPackage%;

import %clientPackage%.dto.%classDTO%;
import %clientPackage%.service.%classDAO%Service;
import %clientPackage%.service.%classDAO%ServiceAsync;
import %clientPackage%.service.PathServices;
import com.pugsource.gwt.library.client.Tools;
import com.pugsource.gwt.library.client.ui.ButtonPug;
import com.pugsource.gwt.library.client.popup.DialogBoxPug;
import com.pugsource.gwt.library.client.popup.MessageBoxPug;
import com.pugsource.gwt.library.client.popup.MessageBoxErrorPug;
import com.pugsource.gwt.library.client.popup.MessageProgressPug;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.CheckBox;

/*
 * Form Login
 */
public class %crudName% extends Composite {

	private TextBox textBoxUsername;
	private PasswordTextBox passwordTextBox;
	private ButtonPug btnLogin;
	private Label lblNewLabel;
	private CheckBox checkConnected;

	public %crudName%() {

		History.newItem("%crudName%", false);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");

		FlexTable flexTable = new FlexTable();
		flexTable.setCellSpacing(10);
		flexTable.setStyleName("gwt-box-login");
		verticalPanel.add(flexTable);
		flexTable.setHeight("51px");

		lblNewLabel = new Label("Autenticação");
		flexTable.setWidget(0, 1, lblNewLabel);
		
		Label lblUsurioemail = new Label("Usuário:");
		flexTable.setWidget(1, 0, lblUsurioemail);

		textBoxUsername = new TextBox();
		flexTable.setWidget(1, 1, textBoxUsername);
		textBoxUsername.setWidth("260px");

		Label lblSenha = new Label("Senha:");
		flexTable.setWidget(2, 0, lblSenha);

		passwordTextBox = new PasswordTextBox();
		flexTable.setWidget(2, 1, passwordTextBox);
		passwordTextBox.setWidth("260px");
		passwordTextBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					callLogin();
				}
			}
		});
		
		checkConnected = new CheckBox("Lembrar conexão");
		flexTable.setWidget(3, 1, checkConnected);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);		

		btnLogin = new ButtonPug();
		btnLogin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				callLogin();
			}
		});
		btnLogin.setLabel("Login");
		flexTable.setWidget(4, 1, btnLogin);
		btnLogin.setWidth("75px");
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);		
		
	}

	public void focus() {
		this.textBoxUsername.setFocus(true);
	}

	public void callLogin() {
		if (btnLogin.isEnabled() && !textBoxUsername.getValue().isEmpty() && !passwordTextBox.getValue().isEmpty()) {
			btnLogin.setEnabled(false);
			MessageProgressPug.Util.show("", true);
			%classDAO%ServiceAsync %instanceDAO%Service = %classDAO%Service.Util.getInstance();
			((ServiceDefTarget) %instanceDAO%Service).setServiceEntryPoint( PathServices.%pathServices%SERVICE );
			%instanceDAO%Service.login(textBoxUsername.getValue(), passwordTextBox.getValue(), %instanceDAO%ServiceCallback);			
		}
	}

	public AsyncCallback<%classDTO%> %instanceDAO%ServiceCallback = new AsyncCallback<%classDTO%>() {    
		public void onFailure(Throwable caught) {
			MessageProgressPug.Util.close();
			MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na resposta do servidor, tente novamente.", caught.getMessage(), null);			
			btnLogin.setEnabled(true);
		}    
		public void onSuccess(%classDTO% %instanceDTO%) {
			try {
				MessageProgressPug.Util.close();
				if (%instanceDTO% == null) {
					btnLogin.setEnabled(true);
					MessageBoxPug.Util.showMessage("Erro!", "Usuário e/ou senha incorretos.", (new CloseHandler<PopupPanel>() {
						public void onClose(CloseEvent<PopupPanel> event) {
							textBoxUsername.setFocus(true);
						};
					}));								
				} else {
					if (checkConnected.getValue())
						Tools.setUserCookie(textBoxUsername.getValue(), passwordTextBox.getValue());
					else
						Tools.removeUserCookie();
					DialogBoxPug.Util.getInstance().hide();
				}
			} catch (Exception e) {
				MessageProgressPug.Util.close();
				MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na leitura dos dados, tente novamente.", e.getMessage(), null);			
				btnLogin.setEnabled(true);
			}
		}
	};	

	public void remember() {
		String username = Tools.getCookieUsername();
		String password = Tools.getCookiePassword();
		if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
			textBoxUsername.setValue(username);
			passwordTextBox.setValue(password);
			checkConnected.setValue(true);
		}
	}
	
}