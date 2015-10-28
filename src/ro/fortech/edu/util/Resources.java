package ro.fortech.edu.util;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** 
 * Use the 'resource producer' pattern, from CDI
 * The @Produces annotation to create an object, then use @Inject 
 * to inject the created object (or resource) where it’s required
 * @author Silviu
 *
 */
@RequestScoped
public class Resources {
    
	
	@Produces
    @PersistenceContext
    private EntityManager em;
	
    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces   
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}

