package ro.fortech.edu.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ro.fortech.edu.model.EvaluationResult;

@Stateless
public class EvaluationResultService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager entityManager;

	@Inject
	private Event<EvaluationResult> evaluationResultEventSrc;

	public void register(EvaluationResult evaluationResult) throws Exception {		
		entityManager.persist(evaluationResult);
		evaluationResultEventSrc.fire(evaluationResult);
	}
	
	public void update(EvaluationResult evaluationResult) throws Exception {    	
    	entityManager.merge(evaluationResult);
        evaluationResultEventSrc.fire(evaluationResult);
    }
	
	public EvaluationResult findEvaluationResultById(long id) {

		return entityManager.find(EvaluationResult.class, id);
	}

}
