package Converter;

import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.Agent;

/**
 *
 * @author Aarn
 */
@FacesConverter("agentConverter")
public class AgentConverter implements Converter {

    private static final Map<String, Agent> cache = new HashMap<>();

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
        if (value instanceof Agent) {
            Agent model = (Agent) value;
            code = String.valueOf(model.getId());
            if (code != null) {
                cache.put(code, model);
            }
        }
        return code;
    }
}
