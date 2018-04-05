package jpa;


/**
 *
 * @author Espérance
 */
public enum NatureAgent {

    APE("Agent Permanent"),
    ACE("Agent Saisonnier"),
    AA("Autres Agents");
    private final String label;

    private NatureAgent(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return this.label;
    }

}
