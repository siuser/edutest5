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

import ro.fortech.edu.model.MarketRule;
import ro.fortech.edu.repository.MarketRuleRepository;

/**
 * 
 * This class produces a RESTful service to read/write markeRule 
 */
@Path("/marketRules")
@RequestScoped
public class MarketRuleRestService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private MarketRuleRepository marketRuleRepository;

	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public List<MarketRule> listAllMarketRules() {
		return marketRuleRepository.findAllMarketRules();
	}
	
	@GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public MarketRule lookupMarketRuleById(@PathParam("id") long id) {
		MarketRule marketRule = marketRuleRepository.findById(id);
        if (marketRule == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return marketRule;
    }
	
	
	
}
