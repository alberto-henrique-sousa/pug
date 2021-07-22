/*******************************************************************************
 * Generator by Pug Plugin %version%
 *******************************************************************************/
package %packageBeanConverter%;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import %packageDao%.%classDAO%;

@FacesConverter("%instanceConverter%")
public class %serviceConverter% implements Converter {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
            return (%classDAO%) uiComponent.getAttributes().get(value);
        } else
        	return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof %classDAO%) {
			%classDAO% %instanceDAO% = (%classDAO%) value;
            if (%instanceDAO% != null) {
                uiComponent.getAttributes().put(%instanceDAO%.toString(), %instanceDAO%);
                return %instanceDAO%.toString();
            }
        }
        return "";	
	}
		
}