package ro.fortech.edu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EVALUATION_RESULT_RULE_DETAIL database table.
 * 
 */
@Entity
@Table(name="EVALUATION_RESULT_RULE_DETAIL")
@NamedQuery(name="EvaluationResultRuleDetail.findAll", query="SELECT e FROM EvaluationResultRuleDetail e")
public class EvaluationResultRuleDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID_EVALUATION_RESULT_RULE_DETAIL", unique=true, nullable=false)
	private long idEvaluationResultRuleDetail;

	@Lob
	private String message;

	@Column(name="RULE_STATUS", length=10)
	private String ruleStatus;

	//bi-directional many-to-one association to EvaluationResult
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_EVALUATION_RESULT_IND", nullable=false)
	private EvaluationResult evaluationResult;

	//bi-directional many-to-one association to EvaluationRule
	@ManyToOne
	@JoinColumn(name="ID_EVALUATION_RULE_IND", nullable=false)
	private EvaluationRule evaluationRule;

	public EvaluationResultRuleDetail() {
	}

	public long getIdEvaluationResultRuleDetail() {
		return this.idEvaluationResultRuleDetail;
	}

	public void setIdEvaluationResultRuleDetail(long idEvaluationResultRuleDetail) {
		this.idEvaluationResultRuleDetail = idEvaluationResultRuleDetail;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRuleStatus() {
		return this.ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public EvaluationResult getEvaluationResult() {
		return this.evaluationResult;
	}

	public void setEvaluationResult(EvaluationResult evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	public EvaluationRule getEvaluationRule() {
		return this.evaluationRule;
	}

	public void setEvaluationRule(EvaluationRule evaluationRule) {
		this.evaluationRule = evaluationRule;
	}

}