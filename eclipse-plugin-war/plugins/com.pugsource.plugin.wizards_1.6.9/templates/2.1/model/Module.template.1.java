/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %packageName%;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.pugsource.gwt.library.client.popup.MessageProgressPug;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * Template: Basic
 */
public class %className% implements EntryPoint {

	private RootPanel rootPanel;

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
			}
		});				
	    
		History.newItem("", false);
		
	}

}
