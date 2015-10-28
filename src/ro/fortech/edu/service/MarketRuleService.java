package ro.fortech.edu.service;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ro.fortech.edu.model.MarketRule;

@Stateless
public class MarketRuleService {

	@Inject
    private Logger log;

    @Inject
    private EntityManager entityManager;

    @Inject
    private Event<MarketRule> marketRuleEventSrc;

    public void register(MarketRule marketRule) throws Exception {
    	entityManager.persist(marketRule);
        marketRuleEventSrc.fire(marketRule);
    }
    
    public MarketRule findMarketRuleById(long id){
    	
    	return entityManager.find(MarketRule.class, id);
    }
    
    @Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final MarketRuleService ejbProxy = this.sessionContext
				.getBusinessObject(MarketRuleService.class);

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return ejbProxy.findMarketRuleById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((MarketRule) value).getIdMarketRule());
			}
		};
	}

	



}