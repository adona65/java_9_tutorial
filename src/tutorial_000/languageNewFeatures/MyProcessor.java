package tutorial_000.languageNewFeatures;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

public class MyProcessor extends SubmissionPublisher<Freelancer> implements Processor<Employee, Freelancer> {

	private Subscription subscription;
	//  Will be used to convert Employee object to Freelancer object.
	private Function<Employee,Freelancer> function;
	
	public MyProcessor(Function<Employee,Freelancer> function) {  
	    super();  
	    System.out.println("MyProcessor subscribed for Freelancer");
	    this.function = function;  
	  }  
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	/**
	 * We will convert incoming Employee message to Freelancer message in this method, and then use SubmissionPublisher submit() method to send it to 
	 * the subscriber.
	 */
	@Override
	public void onNext(Employee emp) {
		System.out.println("Will transform an amployee to freelancer inside MyProcessor " + emp);
		// function.apply(emp) convert incoming Employee message to Freelancer message.
		// submit() send newly formated Freelancer message top the subscriber.
		submit((Freelancer) function.apply(emp));  
	    subscription.request(1);  
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("Done for MyProcessor");
	}

}
