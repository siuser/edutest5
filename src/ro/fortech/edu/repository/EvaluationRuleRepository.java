package ro.fortech.edu.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ro.fortech.edu.model.EvaluationRule;

/**
 * Bean responsible for interaction with persistence layer
 * The bean is application scoped, like a Singleton
 * Entity Manager is injected
 * Uses criteria API
 * @author Silviu
 */
@ApplicationScoped
public class EvaluationRuleRepository {

	@Inject
    private EntityManager em;
  
    public List<EvaluationRule> findAllEvaluationRules() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EvaluationRule> criteria = cb.createQuery(EvaluationRule.class);
        Root<EvaluationRule> evaluationRule = criteria.from(EvaluationRule.class);           
        criteria.select(evaluationRule);        
        return em.createQuery(criteria).getResultList();
    }
    
    
	public EvaluationRule findById(long id) {
        return em.find(EvaluationRule.class, id);
    }
    
}
