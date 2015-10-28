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

@Named
@Model
public class InterpretationRuleListController {

	private long idEvaluationRule;

	public long getIdEvaluationRule() {
		return idEvaluationRule;
	}

	public void setIdEvaluationRule(long idEvaluationRule) {
		this.idEvaluationRule = idEvaluationRule;
	}

	private EvaluationRule interpretationRule;

	public EvaluationRule getInterpretationRule() {
		return interpretationRule;
	}

	public void setInterpretationRule(EvaluationRule interpretationRule) {
		this.interpretationRule = interpretationRule;
	}

	@Inject
	private FacesContext facesContext;

	@Inject
	private EvaluationRuleService evaluationRuleService;

	
	/*
	 * 
	 * public EvaluationRule getNewInterpretationRule() { return
	 * newInterpretationRule; }
	 * 
	 * public void setNewInterpretationRule(EvaluationRule
	 * newInterpretationRule) { this.newInterpretationRule =
	 * newInterpretationRule; }
	 * 
	 */

	@Produces
	@Named
	private EvaluationRule newwInterpretationRule;

	@PostConstruct
	public void initNewInterpretationRule() {
		this.newwInterpretationRule = new EvaluationRule();
	}

	/*
	 * 
	 * public void updateInterpretationRule() throws Exception { try {
	 * System.out.println("interpretationRul.id ="
	 * +interpretationRule.getIdEvaluationRule()); //newInterpretationRule.
	 * evaluationRuleService.update(interpretationRule); FacesMessage m = new
	 * FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!",
	 * "Registration successful"); facesContext.addMessage(null, m);
	 * initNewInterpretationRule(); } catch (Exception e) { String errorMessage
	 * = getRootErrorMessage(e); FacesMessage m = new
	 * FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
	 * "Registration unsuccessful"); facesContext.addMessage(null, m); } }
	 */

	public void register() throws Exception {
		try {
			newwInterpretationRule.setIsMappingRule(false);
			evaluationRuleService.register(newwInterpretationRule);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewInterpretationRule();
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
