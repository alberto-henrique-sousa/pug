/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %servicePackageClient%;

import com.google.gwt.core.client.GWT;
import com.pugsource.gwt.library.client.grids.BlueDataGridResources;
import com.pugsource.gwt.library.client.grids.DarkDataGridResources;
import com.pugsource.gwt.library.client.grids.GreenDataGridResources;

public class Theme {
	
	public enum TypeTheme { BLUE, DARK, GREEN }

	public static <T> T dataGrid(TypeTheme type) {
		switch (type) {
		case DARK:
			return GWT.create(DarkDataGridResources.class);
		case GREEN:
			return GWT.create(GreenDataGridResources.class);
		default:
			return GWT.create(BlueDataGridResources.class);
		}
	}
	
	public static <T> T dataGridDefault() {
		return dataGrid(TypeTheme.BLUE);
	}
	
}
