package ro.fortech.edu.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ro.fortech.edu.model.Vehicle;

/**
 * Service class for Vehicle
 * This bean requires transactions as it needs to write to the database
 * Making this an EJB gives us access to declarative transactions
 * (vs manual transaction control)
 * @author Silviu
 *
 */
@Stateless
public class VehicleService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager entityManager;

	@Inject
	private Event<Vehicle> vehicleEventSrc;

	public void register(Vehicle vehicle) throws Exception {		
		//System.out.println("Registering Vehicle id= " + vehicle.getIdVehicle());
		entityManager.persist(vehicle);
		vehicleEventSrc.fire(vehicle);
	}
	
	public void update(Vehicle vehicle) throws Exception {    	
    	//System.out.println("Updating Vehicle id= " + vehicle.getIdVehicle());
        entityManager.merge(vehicle);
        vehicleEventSrc.fire(vehicle);
    }
	
	public Vehicle findVehicleById(long id) {

		return entityManager.find(Vehicle.class, id);
	}

}
