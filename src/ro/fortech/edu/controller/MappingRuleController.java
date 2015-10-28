package ro.fortech.edu.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ro.fortech.edu.model.EvaluationRule;
import ro.fortech.edu.service.EvaluationRuleService;

@Model
public class MappingRuleController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private EvaluationRuleService evaluationRuleService;

	@Produces
	@Named
	private EvaluationRule newMappingRule;

	@PostConstruct
	public void initNewMappingRule() {
		newMappingRule = new EvaluationRule();
	}

	private long idMappingRule;

	private EvaluationRule mappingRule;

	public EvaluationRule getMappingRule() {
		return mappingRule;
	}

	public void setMappingRule(EvaluationRule mappingRule) {
		this.mappingRule = mappingRule;
	}

	public long getIdMappingRule() {
		return idMappingRule;
	}

	public void setIdMappingRule(long idMappingRule) {
		this.idMappingRule = idMappingRule;
	}

	public void init() {
		System.out.println("Enter init()");
		this.mappingRule = evaluationRuleService.findEvaluationRuleById(this.idMappingRule);
	}

	public void register() throws Exception {
		try {
			newMappingRule.setIsMappingRule(true);
			evaluationRuleService.register(newMappingRule);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewMappingRule();
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
