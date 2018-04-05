package Converter;

import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.Annee;

/**
 *
 * @author Aarn
 */
@FacesConverter("anneeConverter")
public class AnneeConverter implements Converter {

    private static final Map<String, Annee> cache = new HashMap<String, Annee>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return cache.get(value.trim());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        String code = null;
        if (value instanceof Annee) {
            Annee model = (Annee) value;
            code = String.valueOf(model.getId());
            if (code != null) {
                cache.put(code, model);
            }
        }
        return code;
    }
}
