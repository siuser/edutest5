package ro.fortech.edu.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ro.fortech.edu.model.EvaluationRule;

@Stateless
public class EvaluationRuleService {

	@Inject
    private Logger log;

    @Inject
    private EntityManager entityManager;

    @Inject
    private Event<EvaluationRule> evaluationRuleEventSrc;

    public void register(EvaluationRule evaluationRule) throws Exception {    	
    	entityManager.persist(evaluationRule);
        evaluationRuleEventSrc.fire(evaluationRule);
    }
    
    public void update(EvaluationRule evaluationRule) throws Exception {    	
    	entityManager.merge(entityManager.merge(evaluationRule));
        evaluationRuleEventSrc.fire(evaluationRule);
    }
    
    public EvaluationRule findEvaluationRuleById(long id){
    	
    	return entityManager.find(EvaluationRule.class, id);
    }
}