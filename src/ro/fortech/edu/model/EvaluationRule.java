package ro.fortech.edu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the EVALUATION_RULE database table.
 * 
 */
@Entity
@Table(name="EVALUATION_RULE")
@NamedQuery(name="EvaluationRule.findAll", query="SELECT e FROM EvaluationRule e")
public class EvaluationRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID_EVALUATION_RULE", unique=true, nullable=false)
	private Long idEvaluationRule;

	@Column(name="VEHICLE_ATTRIBUTE", length=50)
	private String vehicleAttribute;

	@Column(name="VEHICLE_ATTRIBUTE_SOURCE", length=50)
	private String vehicleAttributeSource;

	@Column(name="VEHICLE_ATTRIBUTE_TARGET", length=50)
	private String vehicleAttributeTarget;
	
	@Column(name="IS_MAPPING_RULE", nullable=false)
	private Boolean isMappingRule;

	//bi-directional many-to-one association to EvaluationResultRuleDetail
	@OneToMany(mappedBy="evaluationRule")
	private List<EvaluationResultRuleDetail> evaluationResultRuleDetails;

	//bi-directional many-to-one association to MarketRule
	@ManyToOne
	@JoinColumn(name="ID_MARKET_RULE_IND", nullable=false)
	private MarketRule marketRule;

	//bi-directional many-to-one association to RuleActivity
	@OneToMany(mappedBy="evaluationRule")
	private List<RuleActivity> ruleActivities;

	//bi-directional many-to-one association to RuleCondition
	@OneToMany(mappedBy="evaluationRule", cascade = CascadeType.ALL)
	private List<RuleCondition> ruleConditions;

	public EvaluationRule() {
	}

	public Long getIdEvaluationRule() {
		return this.idEvaluationRule;
	}

	public void setIdEvaluationRule(Long idEvaluationRule) {
		this.idEvaluationRule = idEvaluationRule;
	}

	public String getVehicleAttribute() {
		return this.vehicleAttribute;
	}

	public void setVehicleAttribute(String vehicleAttribute) {
		this.vehicleAttribute = vehicleAttribute;
	}

	public String getVehicleAttributeSource() {
		return this.vehicleAttributeSource;
	}

	public void setVehicleAttributeSource(String vehicleAttributeSource) {
		this.vehicleAttributeSource = vehicleAttributeSource;
	}

	public String getVehicleAttributeTarget() {
		return this.vehicleAttributeTarget;
	}

	public void setVehicleAttributeTarget(String vehicleAttributeTarget) {
		this.vehicleAttributeTarget = vehicleAttributeTarget;
	}

	public List<EvaluationResultRuleDetail> getEvaluationResultRuleDetails() {
		return this.evaluationResultRuleDetails;
	}

	public void setEvaluationResultRuleDetails(List<EvaluationResultRuleDetail> evaluationResultRuleDetails) {
		this.evaluationResultRuleDetails = evaluationResultRuleDetails;
	}

	public EvaluationResultRuleDetail addEvaluationResultRuleDetail(EvaluationResultRuleDetail evaluationResultRuleDetail) {
		getEvaluationResultRuleDetails().add(evaluationResultRuleDetail);
		evaluationResultRuleDetail.setEvaluationRule(this);

		return evaluationResultRuleDetail;
	}

	public EvaluationResultRuleDetail removeEvaluationResultRuleDetail(EvaluationResultRuleDetail evaluationResultRuleDetail) {
		getEvaluationResultRuleDetails().remove(evaluationResultRuleDetail);
		evaluationResultRuleDetail.setEvaluationRule(null);

		return evaluationResultRuleDetail;
	}

	public MarketRule getMarketRule() {
		return this.marketRule;
	}

	public void setMarketRule(MarketRule marketRule) {
		this.marketRule = marketRule;
	}

	public List<RuleActivity> getRuleActivities() {
		return this.ruleActivities;
	}

	public void setRuleActivities(List<RuleActivity> ruleActivities) {
		this.ruleActivities = ruleActivities;
	}

	public RuleActivity addRuleActivity(RuleActivity ruleActivity) {
		getRuleActivities().add(ruleActivity);
		ruleActivity.setEvaluationRule(this);

		return ruleActivity;
	}

	public RuleActivity removeRuleActivity(RuleActivity ruleActivity) {
		getRuleActivities().remove(ruleActivity);
		ruleActivity.setEvaluationRule(null);

		return ruleActivity;
	}

	public List<RuleCondition> getRuleConditions() {
		return this.ruleConditions;
	}

	public void setRuleConditions(List<RuleCondition> ruleConditions) {
		this.ruleConditions = ruleConditions;
	}

	public RuleCondition addRuleCondition(RuleCondition ruleCondition) {
		getRuleConditions().add(ruleCondition);
		ruleCondition.setEvaluationRule(this);

		return ruleCondition;
	}

	public RuleCondition removeRuleCondition(RuleCondition ruleCondition) {
		getRuleConditions().remove(ruleCondition);
		ruleCondition.setEvaluationRule(null);

		return ruleCondition;
	}
	
	public Boolean getIsMappingRule() {
		return this.isMappingRule;
	}

	public void setIsMappingRule(Boolean isMappingRule) {
		this.isMappingRule = isMappingRule;
	}

}