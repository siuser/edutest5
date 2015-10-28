package ro.fortech.edu.producer;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import ro.fortech.edu.model.Vehicle;
import ro.fortech.edu.repository.VehicleRepository;

/**
 * Bean responsible for building the list of vehicle
 * @author Silviu
 *
 */
@RequestScoped
public class VehicleListProducer {

	@Inject
    private VehicleRepository vehicleRepository;

    private List<Vehicle> vehicles;

       
    /**
     * The list  is exposed as a producer method
     * The list  is available via EL variable name
     * @return 
     */
    @Produces
    @Named
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    
    /**
     * This method allows us to refresh the list of vehicles whenever they are needed
     * Observer method is notified whenever a vehicle is created, removed, or updated
     * notifyObserver = Reception.IF_EXISTS specifies that the observer method 
     * is only called if the current instance of the bean declaring the observer method 
     * already exists (vs Reception.ALWAYS)
     * @param vehicles
     */    
    public void onVehicleListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Vehicle vehicle) {
        retrieveAllVehicles();
    }
    
    /**
     * The @PostConstruct annotation causes refreshing the list of all vehicle
     * when the bean is instantiate
     */
    @PostConstruct
    public void retrieveAllVehicles() {
    	vehicles = vehicleRepository.findAllVehicles();
    }
}

