package ro.fortech.edu.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ro.fortech.edu.model.EvaluationRule;
import ro.fortech.edu.model.RuleActivity;
import ro.fortech.edu.model.RuleCondition;
import ro.fortech.edu.service.EvaluationRuleService;
import ro.fortech.edu.service.RuleConditionService;

@Named
@Model
//@RequestScoped
//@SessionScoped
public class InterpretationRuleController {
	
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
	
	@Inject
	private RuleConditionService ruleConditionService;
	
	@Produces
	@Named
	private RuleCondition newRuleCondition;
	
	@Produces
	@Named
	private RuleActivity newRuleActivity;

	public EvaluationRule getNewInterpretationRule() {
		return newInterpretationRule;
	}

	public void setNewInterpretationRule(EvaluationRule newInterpretationRule) {
		this.newInterpretationRule = newInterpretationRule;
	}

	@Produces
	@Named
	private EvaluationRule newInterpretationRule;

	@PostConstruct
	public void initNewInterpretationRule() {
		newRuleCondition = new RuleCondition();
		newRuleActivity = new RuleActivity();
		newInterpretationRule = new EvaluationRule();
	}
	
	public void updateInterpretationRule() throws Exception {
		try {
			evaluationRuleService.update(interpretationRule);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewInterpretationRule();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}
	

	public void register() throws Exception {
		try {			
			evaluationRuleService.register(newInterpretationRule);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewInterpretationRule();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}
	
	public void registerRuleCondition() throws Exception {
		try {
			//newInterpretationRule.getEvaluationResultRuleDetails()
			System.out.println("interpretationRulid");
			newRuleCondition.setEvaluationRule(interpretationRule);
			//System.out.println("newRuleCondid="+newRuleCondition.getIdRuleCondition());
			//System.out.println("newRuleCond attribute= "+newRuleCondition.getVehicleAttribute());
			ruleConditionService.register(newRuleCondition);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewInterpretationRule();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
		
		//return "viewInterpretationRule.xhtml";
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
	
	public String goToViewInterpretationRule(){
		System.out.println("Enter to goToViewInterpretationRule");
		//System.out.println("iddd=="+this.interpretationRule.getIdEvaluationRule());
		//System.out.println("CCondition list size="+this.interpretationRule.getRuleConditions().size());
		newRuleCondition = new RuleCondition();
		return "viewInterpretationRule.xhtml";
	}
	
	public RuleCondition getNewRuleCondition() {
		return newRuleCondition;
	}

	public void setNewRuleCondition(RuleCondition newRuleCondition) {
		this.newRuleCondition = newRuleCondition;
	}

	public String viewInterpretationRule(){
		System.out.println("Entered viewInterpretationRule()");
		System.out.println("iddd=="+this.interpretationRule.getIdEvaluationRule());
		System.out.println("CCondition list size="+this.interpretationRule.getRuleConditions().size());
		newInterpretationRule=evaluationRuleService.findEvaluationRuleById(interpretationRule.getIdEvaluationRule());
		
		newRuleCondition = new RuleCondition();
		
		return "viewInterpretationRule?faces-redirect=false";
	}
	
	public void registerRuleCondition2(EvaluationRule evaluationRule) throws Exception {
		try {
			//newInterpretationRule.getRuleConditions().add(arg0)
			//System.out.println("newRuleConditionvehattr="+newRuleCondition.getVehicleAttribute());
			//System.out.println("this id=="+this.interpretationRule.getIdEvaluationRule());
			
			if(evaluationRule==null){
				System.out.println("evrullnull");
			}else{
				System.out.println("idddddd="+this.getIdEvaluationRule());
				System.out.println("id="+evaluationRule.getIdEvaluationRule());
				System.out.println("branch="+evaluationRule.getMarketRule().getBranch());
				System.out.println("interpretationRulid ok");
				evaluationRule.getRuleConditions().add(newRuleCondition);
				//newRuleCondition.setEvaluationRule(evaluationRule);
				//System.out.println("newRuleCondid="+newRuleCondition.getIdRuleCondition());
				//System.out.println("newRuleCond attribute= "+newRuleCondition.getVehicleAttribute());
				//ruleConditionService.register(newRuleCondition);
				
				evaluationRuleService.update(evaluationRule);
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
				facesContext.addMessage(null, m);
				initNewInterpretationRule();
			}
			
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
		
		//return "viewInterpretationRule.xhtml";
	}
	
	public String  registerRuleCondition2() throws Exception {
		try {
			Map<String,String> params = 
	                facesContext.getExternalContext().getRequestParameterMap();
		  String idEvaluationRule = params.get("idEvaluationRule");
		  System.out.println("iddEvaluationRule="+idEvaluationRule);
			long ruleId=new Long(idEvaluationRule);
			newInterpretationRule =evaluationRuleService.findEvaluationRuleById(ruleId);
			newInterpretationRule.addRuleCondition(newRuleCondition);
				//newRuleCondition.setEvaluationRule(newInterpretationRule);
				System.out.println("newRuleCondid="+newRuleCondition.getIdRuleCondition());
				System.out.println("newRuleCond attribute= "+newRuleCondition.getVehicleAttribute());
				evaluationRuleService.update(newInterpretationRule);
				//ruleConditionService.register(newRuleCondition);
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
				facesContext.addMessage(null, m);
				newRuleCondition=new RuleCondition();
			
			
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
		
		return "viewInterpretationRule.xhtml?redirect=false";
	}
	
	public String registerRuleActivity2() throws Exception {
		try {
			Map<String,String> params = 
	                facesContext.getExternalContext().getRequestParameterMap();
		  String idEvaluationRule = params.get("idEvaluationRule");
		  System.out.println("iddEvaluationRule="+idEvaluationRule);
			long ruleId=new Long(idEvaluationRule);
			newInterpretationRule =evaluationRuleService.findEvaluationRuleById(ruleId);
			newInterpretationRule.addRuleActivity(newRuleActivity);
				//newRuleCondition.setEvaluationRule(newInterpretationRule);
				System.out.println("newRuleCondid="+newRuleCondition.getIdRuleCondition());
				System.out.println("newRuleCond attribute= "+newRuleCondition.getVehicleAttribute());
				evaluationRuleService.update(newInterpretationRule);
				//ruleConditionService.register(newRuleCondition);
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
				facesContext.addMessage(null, m);
				newRuleActivity=new RuleActivity();
			
			
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
		
		return "viewInterpretationRule.xhtml";
	}
	
	public void removeRuleActivity() throws Exception {
		System.out.println("enter =removeRuleActivity");
		//return "viewInterpretationRule.xhtml";
	}
				 
	public String removeRuleCondition() throws Exception {
		System.out.println("enter =removeRuleCondition");
		try {
			Map<String,String> params = 
	                facesContext.getExternalContext().getRequestParameterMap();
		  String idRuleCondition = params.get("idRuleCondition");
		  System.out.println("idRuleCondition="+idRuleCondition);
			//long ruleId=new Long(idEvaluationRule);
			/*
			newInterpretationRule =evaluationRuleService.findEvaluationRuleById(2L);
			newInterpretationRule.removeRuleCondition(newRuleCondition);
				//newRuleCondition.setEvaluationRule(newInterpretationRule);
				System.out.println("newRuleCondid="+newRuleCondition.getIdRuleCondition());
				System.out.println("newRuleCond attribute= "+newRuleCondition.getVehicleAttribute());
				evaluationRuleService.update(newInterpretationRule);
				//ruleConditionService.register(newRuleCondition);
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
				facesContext.addMessage(null, m);
				*/
				//newRuleCondition=new RuleCondition();
			
			
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
		
		return "viewInterpretationRule.xhtml";
	}








}
