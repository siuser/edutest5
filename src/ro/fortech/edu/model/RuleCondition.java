package ro.fortech.edu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RULE_CONDITION database table.
 * 
 */
@Entity
@Table(name="RULE_CONDITION")
@NamedQuery(name="RuleCondition.findAll", query="SELECT r FROM RuleCondition r")
public class RuleCondition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID_RULE_CONDITION", unique=true, nullable=false)
	private long idRuleCondition;

	@Column(name="VEHICLE_ATTRIBUTE", nullable=false, length=50)
	private String vehicleAttribute;

	@Column(name="VEHICLE_ATTRIBUTE_VALUE", nullable=false, length=50)
	private String vehicleAttributeValue;

	//bi-directional many-to-one association to EvaluationRule
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ID_EVALUATION_RULE_IND", nullable=false)
	private EvaluationRule evaluationRule;

	public RuleCondition() {
	}

	public long getIdRuleCondition() {
		return this.idRuleCondition;
	}

	public void setIdRuleCondition(long idRuleCondition) {
		this.idRuleCondition = idRuleCondition;
	}

	public String getVehicleAttribute() {
		return this.vehicleAttribute;
	}

	public void setVehicleAttribute(String vehicleAttribute) {
		this.vehicleAttribute = vehicleAttribute;
	}

	public String getVehicleAttributeValue() {
		return this.vehicleAttributeValue;
	}

	public void setVehicleAttributeValue(String vehicleAttributeValue) {
		this.vehicleAttributeValue = vehicleAttributeValue;
	}

	public EvaluationRule getEvaluationRule() {
		return this.evaluationRule;
	}

	public void setEvaluationRule(EvaluationRule evaluationRule) {
		this.evaluationRule = evaluationRule;
	}

}