package ro.fortech.edu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the EVALUATION_RESULT database table.
 * 
 */
@Entity
@XmlRootElement
@Table(name="EVALUATION_RESULT")
@NamedQuery(name="EvaluationResult.findAll", query="SELECT e FROM EvaluationResult e")
public class EvaluationResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID_EVALUATION_RESULT", unique=true, nullable=false)
	private long idEvaluationResult;

	@Column(name="DATE_OF_EVALUATION")
	private Timestamp dateOfEvaluation;

	//bi-directional many-to-one association to Vehicle
	@ManyToOne
	@JoinColumn(name="ID_VEHICLE_IND", nullable=false)	
	private Vehicle vehicle;

	//bi-directional many-to-one association to EvaluationResultRuleDetail
	@OneToMany(mappedBy="evaluationResult", cascade=CascadeType.ALL)
	private List<EvaluationResultRuleDetail> evaluationResultRuleDetails = new ArrayList<>();

	public EvaluationResult() {
	}

	public long getIdEvaluationResult() {
		return this.idEvaluationResult;
	}

	public void setIdEvaluationResult(long idEvaluationResult) {
		this.idEvaluationResult = idEvaluationResult;
	}

	public Timestamp getDateOfEvaluation() {
		return this.dateOfEvaluation;
	}

	public void setDateOfEvaluation(Timestamp dateOfEvaluation) {
		this.dateOfEvaluation = dateOfEvaluation;
	}
	
	
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public List<EvaluationResultRuleDetail> getEvaluationResultRuleDetails() {
		return this.evaluationResultRuleDetails;
	}

	public void setEvaluationResultRuleDetails(List<EvaluationResultRuleDetail> evaluationResultRuleDetails) {
		this.evaluationResultRuleDetails = evaluationResultRuleDetails;
	}

	public EvaluationResultRuleDetail addEvaluationResultRuleDetail(EvaluationResultRuleDetail evaluationResultRuleDetail) {
		getEvaluationResultRuleDetails().add(evaluationResultRuleDetail);
		evaluationResultRuleDetail.setEvaluationResult(this);

		return evaluationResultRuleDetail;
	}

	public EvaluationResultRuleDetail removeEvaluationResultRuleDetail(EvaluationResultRuleDetail evaluationResultRuleDetail) {
		getEvaluationResultRuleDetails().remove(evaluationResultRuleDetail);
		evaluationResultRuleDetail.setEvaluationResult(null);

		return evaluationResultRuleDetail;
	}

}