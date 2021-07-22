/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface GlobalResources extends ClientBundle {

	@Source("img/closeOut.png")   
	ImageResource closeOut();
	
	@Source("img/closeOutWhite.png")   
	ImageResource closeOutWhite();	

	@Source("img/closeMove.png")   
	ImageResource closeMove();
	
	@Source("img/lupa.png")   
	ImageResource lupa();	
}
