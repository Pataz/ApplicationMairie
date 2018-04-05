/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.pojo;

/**
 *
 * @author SI-MJLDH
 */
public enum EnumMois {

    JANVIER("JANVIER"),
    FEVRIER("FEVRIER"),
    MARS("MARS"),
    AVRIL("AVRIL"),
    MAI("MAI"),
    JUIN("JUIN"),
    JUILLET("JUILLET"),
    AOUT("AOUT"),
    SEPTEMBRE("SEPTEMBRE"),
    OCTOBRE("OCTOBRE"),
    NOVEMBRE("NOVEMBRE"),
    DECEMBRE("DECEMBRE");

    private final String label;

    private EnumMois(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public int action() {
        switch (this) {
            case JANVIER:
                return 1;
            case FEVRIER:
                return 2;
            case MARS:
                return 3;
            case AVRIL:
                return 4;
            case MAI:
                return 5;
            case JUIN:
                return 6;
            case JUILLET:
                return 7;
            case AOUT:
                return 8;
            case SEPTEMBRE:
                return 9;
            case OCTOBRE:
                return 10;
            case NOVEMBRE:
                return 11;
            case DECEMBRE:
                return 12;
            default:
                return 100;
        }
    }

    public int CompareTo(EnumMois selectedMois) {
        int resultat = 0;
        if (this.action() > selectedMois.action()) {
            resultat = 1;
        }
        if (this.action() < selectedMois.action()) {
            resultat = -1;
        }
        if (this.action() == selectedMois.action()) {
            resultat = 0;
        }
        return resultat;
    }
}
