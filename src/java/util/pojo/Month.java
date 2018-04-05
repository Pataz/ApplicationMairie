/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.pojo;

import java.util.List;
import jpa.Agent;

/**
 *
 * @author utilisateur
 */
public class Month {
    String mois;
    List<Agent> agents;

    public Month(String mois, List<Agent> agents) {
        this.mois = mois;
        this.agents = agents;
    }
    

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }
 
}
