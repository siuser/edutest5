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
import ro.fortech.edu.repository.MappingRuleRepository;

@RequestScoped
public class MappingRuleListProducer {

	@Inject
    private MappingRuleRepository mappingRuleRepository;

    private List<EvaluationRule> mappingRules;

    /**
     * The list  is exposed as a producer method
     * The list  is available via EL variable name
     * @return 
     */
    @Produces
    @Named
    public List<EvaluationRule> getMappingRules() {
        return mappingRules;
    }

    public void onMappingRuleListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final EvaluationRule mappingRules) {
        retrieveAllMappingRules();
    }

    @PostConstruct
    public void retrieveAllMappingRules() {
    	mappingRules = mappingRuleRepository.findAllMappingRules();
    }
}

