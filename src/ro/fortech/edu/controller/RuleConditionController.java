package ro.fortech.edu.controller;

import javax.enterprise.inject.Model;

@Model
public class RuleConditionController {
	
	/*

	@Inject
	private FacesContext facesContext;

	@Inject
	private RuleConditionService ruleConditionService;
	
	
	
	@Produces
	@Named
	private RuleCondition newRuleCondition;

	@PostConstruct
	public void initNewRuleCondition() {		
		newRuleCondition = new RuleCondition();
	}
	

	public void register() throws Exception {
		try {
			//evaluationRuleController.i
			System.out.println("Faces version=" + FacesContext.class.getPackage().getImplementationVersion());
			ruleConditionService.register(newRuleCondition);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewRuleCondition();
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
	
	
	*/
	
	
}