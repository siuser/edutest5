package ro.fortech.edu.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ro.fortech.edu.model.EvaluationResult;
import ro.fortech.edu.model.EvaluationRule;
import ro.fortech.edu.model.Vehicle;
import ro.fortech.edu.service.EvaluationRuleService;

@Model
public class EvaluationRuleController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private EvaluationRuleService evaluationRuleService;

	@Produces
	@Named
	private EvaluationRule newEvaluationRule;

	@PostConstruct
	public void initNewEvaluationRule() {
		newEvaluationRule = new EvaluationRule();
	}

	private long idMarketRule;

	public long getIdMarketRule() {
		return idMarketRule;
	}

	public void setIdMarketRule(long idMarketRule) {
		this.idMarketRule = idMarketRule;
	}

	public void register() throws Exception {
		try {
			evaluationRuleService.register(newEvaluationRule);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewEvaluationRule();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	public void evaluate(Vehicle vehicle) throws IllegalArgumentException, IllegalAccessException {
		//

		vehicle.setChassisSerial("AAAA");
		Class<?> vehicleClass = vehicle.getClass();
		System.out.println("Class = " + vehicleClass);
		Field[] vehicleFields = vehicleClass.getDeclaredFields();

		// Class<?> fieldType=null;
		String fieldType = null;
		String fieldStringValue = null;
		for (Field field : vehicleFields) {
			field.setAccessible(true);
			Object objValue = field.get(vehicle);
			fieldType = field.getType().toString();
			switch (fieldType) {
			case "class java.lang.String":
				fieldStringValue = (String) objValue;
				System.out.println("String case Field name= " + field.getName() + " type= " + fieldType
						+ " Object Value= " + field.get(vehicle) + "value= " + fieldStringValue);
				break;
			case "class java.math.BigDecimal":
				System.out.println("Big Decimal case Field name= " + field.getName() + " type= " + fieldType
						+ " Object Value= " + field.get(vehicle) + "value= " + fieldStringValue);
				break;
			default:
				System.out.println("Default Field name= " + field.getName() + " type= " + field.getType()
						+ " Object Value= " + field.get(vehicle));

			}
		}

	}

	public EvaluationResult evaluate(Vehicle vehicle, ArrayList<String> evaluationRuleIds) {

		return null;
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
