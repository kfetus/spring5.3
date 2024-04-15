package base.comm.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ojh
 *
 */
public class ServiceCheckAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCheckAdvice.class);
	
	public void beforeService(JoinPoint thisJoinPoint) throws Exception {
		Class<?> clazz = thisJoinPoint.getTarget().getClass();
		Signature signature = thisJoinPoint.getSignature();

		LOGGER.debug("######### ServiceCheckAdvice Start #########" + clazz.getCanonicalName()+"."+signature.getName());
		
		
		
    }
	
	public void afterService() throws Exception {
		LOGGER.debug("######### ServiceCheckAdvice END #########");
    }



}
