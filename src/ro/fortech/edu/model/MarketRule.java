package ro.fortech.edu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MARKET_RULE database table.
 * 
 */
@Entity
@Table(name="MARKET_RULE")
@NamedQuery(name="MarketRule.findAll", query="SELECT m FROM MarketRule m")
public class MarketRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID_MARKET_RULE", unique=true, nullable=false)
	private long idMarketRule;

	@Column(unique=true, nullable=false)
	private int branch;

	@Column(name="COUNTRY_NUMBER", unique=true, nullable=false, length=10)
	private String countryNumber;

	@Column(name="IS_ACTIVE", nullable=false)
	private boolean isActive;

	@Column(name="STOCK_CATEGORY", unique=true, nullable=false, length=10)
	private String stockCategory;

	//bi-directional many-to-one association to EvaluationRule
	@OneToMany(mappedBy="marketRule")
	private List<EvaluationRule> evaluationRules;

	public MarketRule() {
	}

	public long getIdMarketRule() {
		return this.idMarketRule;
	}

	public void setIdMarketRule(long idMarketRule) {
		this.idMarketRule = idMarketRule;
	}

	public int getBranch() {
		return this.branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public String getCountryNumber() {
		return this.countryNumber;
	}

	public void setCountryNumber(String countryNumber) {
		this.countryNumber = countryNumber;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getStockCategory() {
		return this.stockCategory;
	}

	public void setStockCategory(String stockCategory) {
		this.stockCategory = stockCategory;
	}

	public List<EvaluationRule> getEvaluationRules() {
		return this.evaluationRules;
	}

	public void setEvaluationRules(List<EvaluationRule> evaluationRules) {
		this.evaluationRules = evaluationRules;
	}

	public EvaluationRule addEvaluationRule(EvaluationRule evaluationRule) {
		getEvaluationRules().add(evaluationRule);
		evaluationRule.setMarketRule(this);

		return evaluationRule;
	}

	public EvaluationRule removeEvaluationRule(EvaluationRule evaluationRule) {
		getEvaluationRules().remove(evaluationRule);
		evaluationRule.setMarketRule(null);

		return evaluationRule;
	}
	
	public String toString(){
		return "Country: "+this.countryNumber+ " - "+"Branch: "+this.branch+" - "+"Category: "+this.stockCategory;
	}

}