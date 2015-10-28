package ro.fortech.edu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RULE_ACTIVITY database table.
 * 
 */
@Entity
@Table(name="RULE_ACTIVITY")
@NamedQuery(name="RuleActivity.findAll", query="SELECT r FROM RuleActivity r")
public class RuleActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID_RULE_ACTIVITY", unique=true, nullable=false)
	private long idRuleActivity;

	@Column(name="VEHICLE_ATTRIBUTE", nullable=false, length=50)
	private String vehicleAttribute;

	@Column(name="VEHICLE_ATTRIBUTE_VALUE", nullable=false, length=50)
	private String vehicleAttributeValue;

	//bi-directional many-to-one association to EvaluationRule
	@ManyToOne
	@JoinColumn(name="ID_EVALUATION_RULE_IND", nullable=false)
	private EvaluationRule evaluationRule;

	public RuleActivity() {
	}

	public long getIdRuleActivity() {
		return this.idRuleActivity;
	}

	public void setIdRuleActivity(long idRuleActivity) {
		this.idRuleActivity = idRuleActivity;
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