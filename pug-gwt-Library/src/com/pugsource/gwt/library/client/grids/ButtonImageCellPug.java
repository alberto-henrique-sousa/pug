/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.grids;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Image;

public class ButtonImageCellPug extends ButtonCell {
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, 
			String value, SafeHtmlBuilder sb) {
		SafeHtml html = SafeHtmlUtils.fromTrustedString(new Image(value).toString());
		sb.append(html);
	}
	
}
