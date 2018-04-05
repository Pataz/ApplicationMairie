/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.TypeEvenement;

/**
 *
 * @author MJLDH
 */
@FacesConverter("typeEvenementConverter")
public class TypeEvenementConverter implements Converter {

    private static Map<String, TypeEvenement> cache = new HashMap<>();

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
        if (value instanceof TypeEvenement) {
            TypeEvenement model = (TypeEvenement) value;
            code = String.valueOf(model.getCode());
            if (code != null) {
                cache.put(code, model);
            }
        }
        return code;
    }
}
