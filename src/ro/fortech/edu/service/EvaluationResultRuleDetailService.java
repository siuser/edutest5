package ro.fortech.edu.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ro.fortech.edu.model.EvaluationResultRuleDetail;

@Stateless
public class EvaluationResultRuleDetailService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager entityManager;

	@Inject
	private Event<EvaluationResultRuleDetail> evaluationResultRuleDetailEventSrc;

	public void register(EvaluationResultRuleDetail evaluationResultRuleDetail) throws Exception {		
		entityManager.persist(evaluationResultRuleDetail);
		evaluationResultRuleDetailEventSrc.fire(evaluationResultRuleDetail);
	}
	
	public void update(EvaluationResultRuleDetail evaluationResultRuleDetail) throws Exception {    	
    	 entityManager.merge(evaluationResultRuleDetail);
        evaluationResultRuleDetailEventSrc.fire(evaluationResultRuleDetail);
    }
	
	public EvaluationResultRuleDetail findEvaluationResultRuleDetailById(long id) {

		return entityManager.find(EvaluationResultRuleDetail.class, id);
	}

}
