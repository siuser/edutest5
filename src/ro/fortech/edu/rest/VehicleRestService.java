package ro.fortech.edu.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ro.fortech.edu.model.Vehicle;
import ro.fortech.edu.repository.VehicleRepository;

/**
 * The @Path annotation tells JAX-RS that this class provides
 * a REST endpoint mapped to 'rest/vehicles' 
 * (concatenating the path from the activator ('/rest') with the path for this endpoint '/vehicles')
 * The bean is request scoped, as JAX-RS interactions typically don't hold state between requests
 * @author Silviu
 *
 */
@Path("/vehicles")
@RequestScoped
public class VehicleRestService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private VehicleRepository vehicleRepository;
	
	/**
	 * The listAllVehicles() method is called when the raw endpoint is accessed
	 *  and offers up a list of endpoints. 
	 *  The object is automatically marshalled to JSON 
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vehicle> listAllVehicles() {
		return vehicleRepository.findAllVehicles();
	}
	
	@GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vehicle lookupVehicleById(@PathParam("id") long id) {
		Vehicle vehicle = vehicleRepository.findById(id);
        if (vehicle == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return vehicle;
    }
	
	

	
}

	