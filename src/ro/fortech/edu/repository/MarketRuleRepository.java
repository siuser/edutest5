package ro.fortech.edu.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ro.fortech.edu.model.MarketRule;

/**
 * Bean responsible for interaction with persistence layer
 * The bean is application scoped, like a Singleton
 * Entity Manager is injected
 * Uses criteria API
 * @author Silviu
 */
@ApplicationScoped
public class MarketRuleRepository {
	@Inject
    private EntityManager em;
  
    public List<MarketRule> findAllMarketRules() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MarketRule> criteria = cb.createQuery(MarketRule.class);
        Root<MarketRule> marketRule = criteria.from(MarketRule.class);           
        criteria.select(marketRule);        
        return em.createQuery(criteria).getResultList();
    }
    
    
	public MarketRule findById(long id) {
        return em.find(MarketRule.class, id);
    }
    
}
