package ro.fortech.edu.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ro.fortech.edu.model.RuleCondition;

@Stateless
public class RuleConditionService {

	@Inject
    private Logger log;

    @Inject
    private EntityManager entityManager;

    @Inject
    private Event<RuleCondition> ruleConditionEventSrc;

    public void register(RuleCondition ruleCondition) throws Exception {
    	//System.out.println("Registering " + ruleCondition.getIdRuleCondition());
        entityManager.persist(ruleCondition);
        ruleConditionEventSrc.fire(ruleCondition);
    }
    
    public RuleCondition findRuleConditionById(long id){
    	
    	return entityManager.find(RuleCondition.class, id);
    }
    
    
}

	



