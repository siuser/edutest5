package ro.fortech.edu.producer;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import ro.fortech.edu.model.EvaluationRule;
import ro.fortech.edu.repository.EvaluationRuleRepository;

@RequestScoped
public class EvaluationRuleListProducer {

	@Inject
    private EvaluationRuleRepository evaluationRuleRepository;

    private List<EvaluationRule> evaluationRules;

    /**
     * The list  is exposed as a producer method
     * The list  is available via EL variable name
     * @return 
     */
    @Produces
    @Named
    public List<EvaluationRule> getEvaluationRules() {
        return evaluationRules;
    }

    public void onEvaluationRuleListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final EvaluationRule evaluationRules) {
        retrieveAllEvaluationRules();
    }

    @PostConstruct
    public void retrieveAllEvaluationRules() {
    	evaluationRules = evaluationRuleRepository.findAllEvaluationRules();
    }
}

