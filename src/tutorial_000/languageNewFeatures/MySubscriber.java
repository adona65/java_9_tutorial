package tutorial_000.languageNewFeatures;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MySubscriber implements Subscriber<Employee> {

	// Variable to keep reference so that request can be made in onNext() method.
	private Subscription subscription;
	
	// Counter variable to keep count of number of items processed, notice that it’s value is increased in onNext() method. 
	// This will be used in our main method to wait for execution to finish before ending the main thread.
	private int counter = 0;
	
	/** 
	 * Subscription request is invoked in this method to start the processing. Also notice that it’s again called in onNext 
	 * method after processing the item, demanding next item to process from the publisher.
	 */
	@Override
	public void onSubscribe(Subscription subscription) {
		System.out.println("Subscribed");
		this.subscription = subscription;
		this.subscription.request(1); //requesting data from publisher
		System.out.println("onSubscribe requested 1 item");
	}

	@Override
	public void onNext(Employee item) {
		System.out.println("Processing Employee "+item);
		counter++;
		// Demand next item to process from the publisher.
		this.subscription.request(1);
	}

	/**
	 * Not really used in our example. In real world scenario, it should be used to perform corrective measures when error occurs.
	 */ 
	@Override
	public void onError(Throwable e) {
		System.out.println("Some error happened");
		e.printStackTrace();
	}

	
	/**
	 * Not really used in our example. In real world scenario, it should be used to perform cleanup of resources when processing completes successfully.
	 */
	@Override
	public void onComplete() {
		System.out.println("All Processing Done");
	}

	public int getCounter() {
		return counter;
	}

}
