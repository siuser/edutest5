package ro.fortech.edu.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ro.fortech.edu.model.StockCategory;

/**
 * Bean responsible for interaction with persistence layer
 * The bean is application scoped, like a Singleton
 * Entity Manager is injected
 * Uses criteria API
 * @author Silviu
 */
@ApplicationScoped
public class StockCategoryRepository {
	@Inject
    private EntityManager em;
  
    public List<StockCategory> findAllStockCategories() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StockCategory> criteria = cb.createQuery(StockCategory.class);
        Root<StockCategory> stockCategory = criteria.from(StockCategory.class);           
        criteria.select(stockCategory);        
        return em.createQuery(criteria).getResultList();
    }
    
    
	public StockCategory findById(int id) {
        return em.find(StockCategory.class, id);
    }
    
}