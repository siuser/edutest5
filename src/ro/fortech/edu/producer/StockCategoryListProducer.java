package ro.fortech.edu.producer;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import ro.fortech.edu.model.StockCategory;
import ro.fortech.edu.repository.StockCategoryRepository;

@RequestScoped
public class StockCategoryListProducer {

	@Inject
    private StockCategoryRepository stockCategoryRepository;

    private List<StockCategory> stockCategories;

    /**
     * The list  is exposed as a producer method
     * The list  is available via EL variable name
     * @return 
     */
    @Produces
    @Named
    public List<StockCategory> getStockCategories() {
        return stockCategories;
    }

    public void onStockCategoryListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final StockCategory stockCategories) {
        retrieveAllStockCategories();
    }

    @PostConstruct
    public void retrieveAllStockCategories() {
    	stockCategories = stockCategoryRepository.findAllStockCategories();
    }
}
