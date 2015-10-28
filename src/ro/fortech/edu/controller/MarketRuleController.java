package ro.fortech.edu.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ro.fortech.edu.model.MarketRule;
import ro.fortech.edu.service.MarketRuleService;

@Model
public class MarketRuleController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private MarketRuleService marketRuleService;	
	
	@Produces
	@Named
	private MarketRule newMarketRule;

	@PostConstruct
	public void initNewMarketRule() {		
		newMarketRule = new MarketRule();
	}

	public void register() throws Exception {
		try {
			//evaluationRuleController.i
			System.out.println("Faces version=" + FacesContext.class.getPackage().getImplementationVersion());
			marketRuleService.register(newMarketRule);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewMarketRule();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}
	
	
	
	
	

}
