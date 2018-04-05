/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import jpa.Document;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.Agent;
import jpa.AgentDocument;
import jpa.Personne;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class DocumentFacade extends AbstractFacade<Document> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @Inject
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentFacade() {
        super(Document.class);
    }

    public AgentDocument createAgentDocument(Agent agent, Document document) {
        AgentDocument newAgentDocument = new AgentDocument();
        newAgentDocument.setAgent(agent);
        newAgentDocument.setDocument(document);
        newAgentDocument.setId(commonFacade.getId(newAgentDocument));
        getEntityManager().persist(newAgentDocument);
        return newAgentDocument;
    }

}
