package ro.fortech.edu.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ro.fortech.edu.model.EvaluationResult;
import ro.fortech.edu.model.EvaluationResultRuleDetail;
import ro.fortech.edu.model.EvaluationRule;
import ro.fortech.edu.model.MarketRule;
import ro.fortech.edu.model.RuleActivity;
import ro.fortech.edu.model.RuleCondition;
import ro.fortech.edu.model.Vehicle;
import ro.fortech.edu.repository.EvaluationRuleRepository;
import ro.fortech.edu.service.EvaluationResultRuleDetailService;
import ro.fortech.edu.service.EvaluationResultService;
import ro.fortech.edu.service.VehicleService;

/**
 * This class is responsible for allowing JSF to interact with the services
 * The @Model stereotype adds @Named and @RequestScoped to the class.
 * 
 * @author Silviu
 *
 */
@Model
public class VehicleController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private VehicleService vehicleService;

	@Inject
	private EvaluationResultService evaluationResultService;

	@Inject
	private EvaluationResultRuleDetailService evaluationResultRuleDetailService;

	@Inject
	private EvaluationRuleRepository evaluationRuleRepository;

	// The newVehicle is exposed using a named producer field, which allows
	// access from JSF
	// Note that the named producer field has dependent scope,
	// so every time it is injected, the field will be read
	@Produces
	@Named
	private Vehicle newVehicle;

	/**
	 * The @PostConstruct annotation causes a new vehicle object to be placed in
	 * the newVehicle field when the bean is instantiated
	 */
	@PostConstruct
	public void initNewVehicle() {
		newVehicle = new Vehicle();
	}

	private Vehicle vehicle;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	private EvaluationResult evaluationResult;

	public EvaluationResult getEvaluationResult() {
		return evaluationResult;
	}

	public void setEvaluationResult(EvaluationResult evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	/**
	 * At this stage of application, we do not know the list of EvaluationRule
	 * to be appllied on vehicle This method is called from JSF with only a
	 * Vehicle as parameter Then it calls evaluate(Vehicle vehicle, List
	 * <String> ruleIds)
	 * 
	 * @param vehicle
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public String evaluateVehicle(Vehicle vehicle)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

		System.out.println("Enter evaluateVehicle()");
		System.out.println("Vehicle id=" + vehicle.getIdVehicle());
		this.vehicle = vehicle;
		// Project requirement: method evaluate must have as parameters
		// a vehicle and a list of rule id's
		// At the moment let's use all rules from the database
		List<EvaluationRule> allEvaluationRules = evaluationRuleRepository.findAllEvaluationRules();
		List<String> evaluationRuleIdsAsStringList = new ArrayList<>();
		for (EvaluationRule evaluationRule : allEvaluationRules) {
			evaluationRuleIdsAsStringList.add(evaluationRule.getIdEvaluationRule().toString());
		}
		if (evaluationRuleIdsAsStringList.size() > 0) {
			evaluate(vehicle, evaluationRuleIdsAsStringList);
		} else {
			System.out.println("NO evaluationRule to be applied");
		}

		return "viewEvaluationResult";
	}

	/**
	 * 
	 * @param vehicle
	 * @param ruleIds
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public EvaluationResult evaluate(Vehicle vehicle, List<String> ruleIds)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// A new Evaluation Result item every time an evaluate method call
		// (every evaluation)
		EvaluationResult evaluationResult = new EvaluationResult();
		evaluationResult.setVehicle(vehicle);

		// Apply every rule to vehicle
		for (String ruleId : ruleIds) {
			// Find EvaluationRule
			Long ruleIdAsLong = new Long(ruleId);
			EvaluationRule evaluationRule = evaluationRuleRepository.findById(ruleIdAsLong);

			if (evaluationRule.getIsMappingRule()) {
				// Apply a mapping rule
				applyMappingRule(vehicle, evaluationRule, evaluationResult);
			} else {
				// Apply an interpretation rule
				applyInterpretationRule(vehicle, evaluationRule, evaluationResult);
			}

		}

		try {
			this.evaluationResult = evaluationResult;
			this.register(evaluationResult);
			this.update(vehicle);
			// this.register(evaluationResultRuleDetail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void applyInterpretationRule(Vehicle vehicle, EvaluationRule evaluationRule,
			EvaluationResult evaluationResult)
					throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

		// A new EvaluationResultRuleDetail for every rule applied
		EvaluationResultRuleDetail evaluationResultRuleDetail = new EvaluationResultRuleDetail();

		evaluationResultRuleDetail.setEvaluationResult(evaluationResult);
		evaluationResultRuleDetail.setEvaluationRule(evaluationRule);

		// The message for EvaluationResultRuleDetail
		StringBuilder evaluationResultRuleDetailMessage = new StringBuilder();
		evaluationResultRuleDetailMessage.append("Interpretation rule id= " + evaluationRule.getIdEvaluationRule());
		evaluationResultRuleDetailMessage.append("\n");

		// The map of vehicle fields (field name as map key,field value as map
		// value)
		Map<String, String> vehicleFieldsMap = this.getVehicleFiels(vehicle);

		// Get the marketRule corresponding to this rule
		MarketRule marketRule = evaluationRule.getMarketRule();
		// evaluationResultRuleDetailMessage
		// .append("*****Market rule for this rule is identified, id= " +
		// marketRule.getIdMarketRule());
		// evaluationResultRuleDetailMessage.append("\n");

		// Let's check if marketRule matches with vehicle
		// Assume that vehicle has indeed fields: countryNumber, branch and
		// stockCategory
		if (vehicle.getCountryNumber().equals(marketRule.getCountryNumber())
				&& (vehicle.getBranch() == marketRule.getBranch())
				&& vehicle.getStockCategory().toLowerCase().equals(vehicle.getStockCategory().toLowerCase())) {

			// This marketRule matches vehicle
			// So we can apply evaluationRule
			evaluationResultRuleDetailMessage
					.append("*****Market rule id= " + marketRule.getIdMarketRule() + " matches vehicle");
			evaluationResultRuleDetailMessage.append("\n");
			// Find conditions list
			List<RuleCondition> conditionsList = evaluationRule.getRuleConditions();
			// Need to check if all conditions met
			boolean isConditionListApplicable = true;
			for (RuleCondition ruleCondition : conditionsList) {
				Long conditionId = ruleCondition.getIdRuleCondition();
				String ruleConditionKey = ruleCondition.getVehicleAttribute();
				System.out.println("ruleConditionKey= " + ruleConditionKey);
				if (vehicleFieldsMap.containsKey(ruleConditionKey)) {
					// Condition vehicle attribute has a correspondent in
					// vehicle fields
					evaluationResultRuleDetailMessage
							.append("*****Condition id=" + conditionId + " condition's vehicle attribute " + "("
									+ ruleConditionKey + ")" + " is in vehicle fields");
					evaluationResultRuleDetailMessage.append(System.getProperty("line.separator"));
					// evaluationResultRuleDetailMessage.append("\n");
					if (vehicleFieldsMap.get(ruleConditionKey).equals(ruleCondition.getVehicleAttributeValue())) {
						evaluationResultRuleDetailMessage.append(
								"*****Condition id=" + ruleCondition.getIdRuleCondition() + " verified result = OK");
						evaluationResultRuleDetailMessage.append("\n");
						// this.setVehicleFieldValue(vehicle,
						// evaluationRule.getVehicleAttribute(),
						// evaluationRule.getVehicleAttributeTarget());
					} else {
						System.out.println("rule condition attr value !=vehicle attr value");
						isConditionListApplicable = false;
						evaluationResultRuleDetailMessage.append(
								"*****Condition id=" + ruleCondition.getIdRuleCondition() + "verified result = NOK");
						evaluationResultRuleDetailMessage.append("\n");
						// break;
					}
				} else {
					// Condition vehicle attribute has NO correspondent in
					// vehicle fields
					evaluationResultRuleDetailMessage
							.append("*****Condition id=" + conditionId + " condition's vehicle attribute " + "("
									+ ruleConditionKey + ")" + " NOT in vehicle fields");
					evaluationResultRuleDetailMessage.append(System.getProperty("line.separator"));
				}
			} // end for conditionsList
			if (isConditionListApplicable) {
				// All conditions passed
				evaluationResultRuleDetailMessage
				.append("*****All conditions applied result = OK");
		evaluationResultRuleDetailMessage.append("\n");
				// So we can apply activities
				List<RuleActivity> activitiesList = evaluationRule.getRuleActivities();
				String ruleActivityKey = null;
				for (RuleActivity ruleActivity : activitiesList) {
					ruleActivityKey = ruleActivity.getVehicleAttribute();
					if (vehicleFieldsMap.containsKey(ruleActivityKey)) {
						// Activity attribute has a correspondent in vehicle
						// fields
						this.setVehicleFieldValue(vehicle, ruleActivityKey, ruleActivity.getVehicleAttributeValue());
						evaluationResultRuleDetailMessage
								.append("*****Activity id= " + ruleActivity.getIdRuleActivity() + " applied OK");
						evaluationResultRuleDetailMessage.append("\n");
						evaluationResultRuleDetail.setRuleStatus("OK");

					} else {
						// Activity attribute has NO correspondent in vehicle
						// fields
						evaluationResultRuleDetailMessage
								.append("*****Activity id=" + ruleActivity.getIdRuleActivity() + " NOT applied");
						evaluationResultRuleDetailMessage.append("\n");

					}
				}
			} else {
				// Not all conditions passed
				evaluationResultRuleDetailMessage
						.append("*****NOT ALL conditions passed, interpretation rule will NOT be applied");
				evaluationResultRuleDetailMessage.append("\n");
				evaluationResultRuleDetail.setRuleStatus("NOK");
			}

		} else {
			// Market rule does not matches vehicle
			evaluationResultRuleDetailMessage
					.append("*****Market rule id= " + marketRule.getIdMarketRule() + " does NOT matches vehicle");
			evaluationResultRuleDetailMessage.append("\n");
			evaluationResultRuleDetailMessage.append(
					"*****Interpretation rule id= " + evaluationRule.getIdEvaluationRule() + " will NOT be applied");
			evaluationResultRuleDetailMessage.append("\n");
			evaluationResultRuleDetail.setRuleStatus("NOK");
		}

		evaluationResultRuleDetail.setMessage(evaluationResultRuleDetailMessage.toString());
		evaluationResult.getEvaluationResultRuleDetails().add(evaluationResultRuleDetail);

	}

	/**
	 * 
	 * @param vehicle
	 * @param evaluationRule
	 * @param evaluationResult
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public void applyMappingRule(Vehicle vehicle, EvaluationRule evaluationRule, EvaluationResult evaluationResult)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

		// A new EvaluationResultRuleDetail for every mapping rule
		EvaluationResultRuleDetail evaluationResultRuleDetail = new EvaluationResultRuleDetail();
		evaluationResultRuleDetail.setEvaluationResult(evaluationResult);
		evaluationResultRuleDetail.setEvaluationRule(evaluationRule);

		// The message for EvaluationResultRuleDetail
		StringBuilder evaluationResultRuleDetailMessage = new StringBuilder();
		evaluationResultRuleDetailMessage.append("Mapping rule id= " + evaluationRule.getIdEvaluationRule());
		evaluationResultRuleDetailMessage.append("\n");

		Map<String, String> vehicleFieldsMap = this.getVehicleFiels(vehicle);
		MarketRule marketRule = evaluationRule.getMarketRule();
		// Let's check if marketRule matches with vehicle
		// Assume that vehicle has indeed fields: countryNumber, branch and
		// stockCategory
		if (vehicle.getCountryNumber().equals(marketRule.getCountryNumber())
				&& (vehicle.getBranch() == marketRule.getBranch())
				&& vehicle.getStockCategory().equals(marketRule.getStockCategory())) {
			// This marketRule matches vehicle
			// So we can apply evaluationRule
			evaluationResultRuleDetailMessage
					.append("*****Market rule id= " + marketRule.getIdMarketRule() + " matches vehicle");
			evaluationResultRuleDetailMessage.append("\n");

			if (vehicleFieldsMap.containsKey(evaluationRule.getVehicleAttribute())) {
				// vehicle has a field with name evaluationRule.vehicleAttribute
				// so we can apply this mapping rule
				evaluationResultRuleDetailMessage.append("*****Mapping rule vehicle attribute " + "("
						+ evaluationRule.getVehicleAttribute() + ")" + " matches a vehicle field");
				evaluationResultRuleDetailMessage.append("\n");
				String key = evaluationRule.getVehicleAttribute();
				if (vehicleFieldsMap.get(key).equals(evaluationRule.getVehicleAttributeSource())) {
					// Mapping rule attribute same as vehicle attribute
					this.setVehicleFieldValue(vehicle, evaluationRule.getVehicleAttribute(),
							evaluationRule.getVehicleAttributeTarget());
					evaluationResultRuleDetailMessage.append("*****Mapping rule attribute value " + "("
							+ evaluationRule.getVehicleAttributeSource() + ")" + " same as vehicle attribute value");
					evaluationResultRuleDetailMessage.append("\n");
					evaluationResultRuleDetailMessage.append(
							"*****Aplly Mapping rule id= " + evaluationRule.getIdEvaluationRule() + " result OK");
					evaluationResultRuleDetailMessage.append("\n");
					evaluationResultRuleDetail.setRuleStatus("OK");
				} else {
					// Mapping rule attribute NOT the same as vehicle attribute
					evaluationResultRuleDetailMessage.append("*****Mapping rule attribute value " + "("
							+ evaluationRule.getVehicleAttributeSource() + ")"
							+ " NOT the same as vehicle attribute value" + "(" + vehicleFieldsMap.get(key) + ")");
					evaluationResultRuleDetailMessage.append("\n");

					evaluationResultRuleDetailMessage
							.append("*****Aplly mapping rule id= " + evaluationRule.getIdEvaluationRule() + " NOK");
					evaluationResultRuleDetailMessage.append("\n");
					evaluationResultRuleDetail.setRuleStatus("NOK");
				}

			} else {
				System.out.println("vehicle attribute name = " + evaluationRule.getVehicleAttribute());
				System.out.println("vehicle does NOT have a field with name evaluationRule.vehicleAttribute");
				evaluationResultRuleDetailMessage.append("*****Mapping rule vehicle attribute " + "("
						+ evaluationRule.getVehicleAttribute() + ")" + " does NOT matche any vehicle field");
				evaluationResultRuleDetailMessage.append("\n");
				evaluationResultRuleDetailMessage
						.append("*****Aplly a mapping rule id= " + evaluationRule.getIdEvaluationRule() + " NOK");
				evaluationResultRuleDetailMessage.append("\n");
				evaluationResultRuleDetail.setRuleStatus("NOK");
				// evaluationResultRuleDetailMessage
				// .append("**********Vehicle attribute value not equal to rule
				// attribute value ");
				// evaluationResultRuleDetailMessage.append("\n");
			}

		} else {
			// Market rule does not matche vehicle
			System.out.println("Market rule id= " + marketRule.getIdMarketRule() + " does NOT matche vehicle");
			evaluationResultRuleDetailMessage
					.append("*****Market rule id= " + marketRule.getIdMarketRule() + " does NOT matche vehicle");
			evaluationResultRuleDetailMessage.append("\n");
			evaluationResultRuleDetailMessage
					.append("*****Mapping rule id= " + evaluationRule.getIdEvaluationRule() + " will NOT be applied");
			evaluationResultRuleDetailMessage.append("\n");
			evaluationResultRuleDetail.setRuleStatus("NOK");

		}
		System.out.println("evaluationResultRuleDetailMessage=" + evaluationResultRuleDetailMessage);
		evaluationResultRuleDetail.setMessage(evaluationResultRuleDetailMessage.toString());
		// evaluationResult.addEvaluationResultRuleDetail(evaluationResultRuleDetail);
		evaluationResult.getEvaluationResultRuleDetails().add(evaluationResultRuleDetail);
		System.out.println(
				"evaluationResultRuleDetail id=" + evaluationResultRuleDetail.getIdEvaluationResultRuleDetail());

	}

	/**
	 * 
	 * @param vehicle
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Map<String, String> getVehicleFiels(Vehicle vehicle)
			throws IllegalArgumentException, IllegalAccessException {
		System.out.println("Enter method = getVehicleFiels(Vehicle vehicle)");
		// Use java.reflect API to find vehicle's fields
		Class<?> vehicleClass = vehicle.getClass();
		Field[] vehicleFields = vehicleClass.getDeclaredFields();

		// Put vehicle field in a map
		// fieldName, which is unique, will be the key
		// fieldStringValue will be the value
		String fieldName = null;
		String fieldStringValue = null;
		Map<String, String> vehicleFieldsMap = new HashMap<>();
		for (Field field : vehicleFields) {
			// There are some system fields (like _persistence_primaryKey)
			// which start with underscore; no interested
			if (!field.getName().startsWith("_")) {
				field.setAccessible(true);
				Object objValue = field.get(vehicle);

				// fieldType = field.getType().toString();
				fieldName = field.getName();
				if (!(objValue == null)) {
					// objValue not null, so can apply toString
					fieldStringValue = objValue.toString();
				} else {
					// objValue is null, set fieldValue to null
					fieldStringValue = null;
				}
				// System.out.println("Field name= " + fieldName + " string
				// value= " + fieldStringValue);
				vehicleFieldsMap.put(fieldName, fieldStringValue);
			}

		}
		System.out.println("Field map=" + vehicleFieldsMap);
		return vehicleFieldsMap;

	}

	/**
	 * 
	 * @param vehicle
	 * @param fieldName
	 * @param fieldValue
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public void setVehicleFieldValue(Vehicle vehicle, String fieldName, String fieldValue)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		System.out.println("Enter method = setVehicleFieldValue(Vehicle vehicle)");
		// Use java.reflect API to set vehicle's fields
		Class<?> vehicleClass = vehicle.getClass();
		Field field = vehicleClass.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(vehicle, fieldValue);
	}

	public void getVehicleFiels_2(Vehicle vehicle) throws IllegalArgumentException, IllegalAccessException {
		//
		Class<?> vehicleClass = vehicle.getClass();
		System.out.println("Class = " + vehicleClass);
		Field[] vehicleFields = vehicleClass.getDeclaredFields();

		// Class<?> fieldType=null;
		String fieldType = null;
		String fieldStringValue = null;
		BigDecimal fieldBigDecimalValue = null;
		for (Field field : vehicleFields) {
			// There are some system fields (like _persistence_primaryKey)
			// which start with underscore; no interested
			if (!field.getName().startsWith("_")) {
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
					fieldBigDecimalValue = (BigDecimal) objValue;
					System.out.println("Big Decimal case Field name= " + field.getName() + " type= " + fieldType
							+ " Object Value= " + field.get(vehicle) + "value= " + fieldBigDecimalValue);
					break;
				case "int":
					System.out.println("int case Field name= " + field.getName() + " type= " + fieldType
							+ " Object Value= " + field.get(vehicle) + "value= " + fieldStringValue);
					break;
				case "long":
					System.out.println("int case Field name= " + field.getName() + " type= " + fieldType
							+ " Object Value= " + field.get(vehicle) + "value= " + fieldStringValue);
					break;
				default:
					System.out.println("Default Field name= " + field.getName() + " type= " + field.getType()
							+ " Object Value= " + field.get(vehicle));

				}
			}

		}

	}

	/**
	 * Facade pattern method for persisting a new vehicle entity Work is done in
	 * service class
	 * 
	 * @param vehicle
	 * @throws Exception
	 */
	public void register(Vehicle vehicle) throws Exception {
		try {
			// evaluationRuleController.i
			System.out.println("Faces version=" + FacesContext.class.getPackage().getImplementationVersion());
			vehicleService.register(vehicle);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewVehicle();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	/**
	 * Facade pattern method for updating changes of a Vehicle entity Work is
	 * done in service class
	 * 
	 * @param vehicle
	 * @throws Exception
	 */
	public void update(Vehicle vehicle) throws Exception {
		try {

			vehicleService.update(vehicle);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Updating successful");
			facesContext.addMessage(null, m);
			initNewVehicle();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Updated unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	/**
	 * Facade pattern method for persisting an EvaluationResult entity
	 * 
	 * @param evaluationResult
	 * @throws Exception
	 */
	public void register(EvaluationResult evaluationResult) throws Exception {
		try {
			evaluationResultService.register(evaluationResult);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewVehicle();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	/**
	 * Facade pattern method for updating changes of an EvaluationResult entity
	 * 
	 * @param evaluationResult
	 * @throws Exception
	 */
	public void update(EvaluationResult evaluationResult) throws Exception {
		try {
			evaluationResultService.update(evaluationResult);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Updating successful");
			facesContext.addMessage(null, m);
			initNewVehicle();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Updated unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	/**
	 * Facade pattern method for persisting an EvaluationResultRuleDetail entity
	 * 
	 * @param evaluationResultRuleDetail
	 * @throws Exception
	 */
	public void register(EvaluationResultRuleDetail evaluationResultRuleDetail) throws Exception {
		try {
			evaluationResultRuleDetailService.register(evaluationResultRuleDetail);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
			facesContext.addMessage(null, m);
			initNewVehicle();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	/**
	 * Facade pattern method for updating changes of an
	 * EvaluationResultRuleDetail entity
	 * 
	 * @param evaluationResultRuleDetail
	 * @throws Exception
	 */
	public void update(EvaluationResultRuleDetail evaluationResultRuleDetail) throws Exception {
		try {

			evaluationResultRuleDetailService.update(evaluationResultRuleDetail);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Updating successful");
			facesContext.addMessage(null, m);
			initNewVehicle();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Updated unsuccessful");
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
