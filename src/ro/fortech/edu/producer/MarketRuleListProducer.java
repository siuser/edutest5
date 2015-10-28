package ro.fortech.edu.producer;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import ro.fortech.edu.model.MarketRule;
import ro.fortech.edu.repository.MarketRuleRepository;

@RequestScoped
public class MarketRuleListProducer {

	@Inject
    private MarketRuleRepository marketRuleRepository;

    private List<MarketRule> marketRules;

    /**
     * The list  is exposed as a producer method
     * The list  is available via EL variable name
     * @return 
     */
    @Produces
    @Named
    public List<MarketRule> getMarketRules() {
        return marketRules;
    }

    public void onMarketRuleListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final MarketRule marketRules) {
        retrieveAllMarketRules();
    }

    @PostConstruct
    public void retrieveAllMarketRules() {
    	marketRules = marketRuleRepository.findAllMarketRules();
    }
}

