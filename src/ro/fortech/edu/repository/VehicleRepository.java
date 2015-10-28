package ro.fortech.edu.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ro.fortech.edu.model.Vehicle;

/**
 * Bean responsible for interaction with persistence layer
 * The bean is application scoped, like a Singleton
 * Entity Manager is injected
 * Uses criteria API
 * @author Silviu
 */
@ApplicationScoped
public class VehicleRepository {

	@Inject
    private EntityManager em;
  
    /**
     * 
     * @return a list of all vehicles from database
     */
	public List<Vehicle> findAllVehicles() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vehicle> criteria = cb.createQuery(Vehicle.class);
        Root<Vehicle> vehicle = criteria.from(Vehicle.class);           
        criteria.select(vehicle);        
        return em.createQuery(criteria).getResultList();
    }
    
    /**
     * 
     * @param id
     * @return a vehicle found by unique id
     */
	public Vehicle findById(long id) {
        return em.find(Vehicle.class, id);
    }
    
}
