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
import ro.fortech.edu.repository.InterpretationRuleRepository;

@RequestScoped
public class InterpretationRuleListProducer {

	@Inject
    private InterpretationRuleRepository interpretationRuleRepository;

    private List<EvaluationRule> interpretationRules;

    /**
     * The list  is exposed as a producer method
     * The list  is available via EL variable name
     * @return 
     */
    @Produces
    @Named
    public List<EvaluationRule> getInterpretationRules() {
        return interpretationRules;
    }

    public void onInterpretationRuleListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final EvaluationRule interpretationRules) {
        retrieveAllInterpretationRules();
    }

    @PostConstruct
    public void retrieveAllInterpretationRules() {
    	interpretationRules = interpretationRuleRepository.findAllInterpretationRules();
    }
}

