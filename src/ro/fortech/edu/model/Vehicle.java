package ro.fortech.edu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * The persistent class for the VEHICLE database table.
 * 
 */
@Entity
//@JsonIgnoreProperties(value={"evaluationResults"})
//@XmlRootElement
@Table(name="VEHICLE")
@NamedQuery(name="Vehicle.findAll", query="SELECT v FROM Vehicle v")
public class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_VEHICLE", unique=true, nullable=false)
	@GeneratedValue(strategy = IDENTITY)
	private long idVehicle;

	@Column(nullable=false)
	private int branch;

	@Column(name="CHASSIS_SERIAL", unique=true, nullable=false, length=30)
	private String chassisSerial;

	@Column(length=30)
	private String classe;

	@Column(name="COUNTRY_NUMBER", nullable=false, length=10)
	private String countryNumber;

	@Column(name="ENGINE_CODE", length=30)
	private String engineCode;

	@Column(name="ENGINE_FUEL_TYPE", length=30)
	private String engineFuelType;

	@Column(length=30)
	private String model;

	@Column(name="STOCK_CATEGORY", nullable=false, length=10)
	private String stockCategory;

	@Column(name="\"VERSION\"", length=30)
	private String version;

	//bi-directional many-to-one association to EvaluationResult
	@OneToMany(mappedBy="vehicle", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<EvaluationResult> evaluationResults;

	public Vehicle() {
	}

	public long getIdVehicle() {
		return this.idVehicle;
	}

	public void setIdVehicle(long idVehicle) {
		this.idVehicle = idVehicle;
	}
	
	public int getBranch() {
		return this.branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public String getChassisSerial() {
		return this.chassisSerial;
	}

	public void setChassisSerial(String chassisSerial) {
		this.chassisSerial = chassisSerial;
	}

	public String getClasse() {
		return this.classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getCountryNumber() {
		return this.countryNumber;
	}

	public void setCountryNumber(String countryNumber) {
		this.countryNumber = countryNumber;
	}

	public String getEngineCode() {
		return this.engineCode;
	}

	public void setEngineCode(String engineCode) {
		this.engineCode = engineCode;
	}

	public String getEngineFuelType() {
		return this.engineFuelType;
	}

	public void setEngineFuelType(String engineFuelType) {
		this.engineFuelType = engineFuelType;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStockCategory() {
		return this.stockCategory;
	}

	public void setStockCategory(String stockCategory) {
		this.stockCategory = stockCategory;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@JsonIgnore
	public List<EvaluationResult> getEvaluationResults() {
		return this.evaluationResults;
	}
	
	@JsonIgnore
	public void setEvaluationResults(List<EvaluationResult> evaluationResults) {
		this.evaluationResults = evaluationResults;
	}

	public EvaluationResult addEvaluationResult(EvaluationResult evaluationResult) {
		getEvaluationResults().add(evaluationResult);
		evaluationResult.setVehicle(this);

		return evaluationResult;
	}

	public EvaluationResult removeEvaluationResult(EvaluationResult evaluationResult) {
		getEvaluationResults().remove(evaluationResult);
		evaluationResult.setVehicle(null);

		return evaluationResult;
	}

}