package learningresourcefinder.batch;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldBatch implements Runnable {

	public static void main(String[] args) {
		BatchUtil.startSpringBatch(HelloWorldBatch.class);
	}
	
	@Override
	public void run() {
		System.out.println("Hello World");
	}
}
