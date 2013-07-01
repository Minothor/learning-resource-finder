package learningresourcefinder.batch;

import learningresourcefinder.exception.ExceptionUtil;
import learningresourcefinder.web.ContextUtil;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BatchUtil {
	
	/** Called by a main method to start a batch easily. That batch class must be a spring bean (@Service).
	 *  BatchUtil.startSpringBatch(CreateTestDataBatch.class);
	 */ 

	public static void startSpringBatch(Class<? extends Runnable> batchClass) 
	{
		ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml" });
		
		ContextUtil.contextInitialized(applicationContext);

		Runnable batchObject = ContextUtil.getSpringBean(batchClass);
		
		try{
			
			batchObject.run();
		

		} catch (Exception e) {
	        ExceptionUtil.printBatchUpdateException(e, System.err);
			throw new RuntimeException("Error in batch", e);

		} finally{
		    applicationContext.close();
		}
		
	}
		
}