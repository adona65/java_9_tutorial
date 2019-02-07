package tutorial_000.languageNewFeatures;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class _005_ReactiveStreams {

	public static void main(String[] args) throws InterruptedException {
		/*
		 * Reactive programming is programming with asynchronous data streams. A quick definition from wikipedia is :
		 * Reactive programming is a declarative programming paradigm concerned with data streams and the propagation of change. With this paradigm it is 
		 * possible to express static (e.g., arrays) or dynamic (e.g., event emitters) data streams with ease, and also communicate that an inferred 
		 * dependency within the associated execution model exists, which facilitates the automatic propagation of the changed data flow.
		 * 
		 * For example, in an imperative programming setting, a := b + c {\displaystyle a:=b+c} a:=b+c would mean that a a is being assigned the result of 
		 * b + c in the instant the expression is evaluated, and later, the values of b and c can be changed with no effect on the value of a. On the other 
		 * hand, in reactive programming, the value of a is automatically updated whenever the values of b or c change, without the program having to re-execute 
		 * the statement a := b + c to determine the presently assigned value of a.
		 * 
		 * Java SE 9 introduce a new Reactive Streams API as a Publish/Subscribe Framework to implement Asynchronous, Scalable and Parallel applications very 
		 * easily using Java language. This new APIs are :
		 * - java.util.concurrent.Flow
		 * - java.util.concurrent.Flow.Publisher
		 * - java.util.concurrent.Flow.Subscriber
		 * - java.util.concurrent.Flow.Processor
		 * 
		 * Java 9 Reactive Streams allows us to implement non-blocking asynchronous stream processing. Reactive Streams is about asynchronous processing of 
		 * stream, so there should be a Publisher and a Subscriber. The Publisher publishes the stream of data and the Subscriber consumes the data. 
		 * Sometimes we have to transform the data between Publisher and Subscriber. Processor is the entity sitting between the end publisher and subscriber 
		 * to transform the data received from publisher so that subscriber can understand it. We can have a chain of processors. Processor works both as 
		 * a Subscriber and a Publisher (cf processor_principle.png).
		 * 
		 * Flow API is a combination of Iterator and Observer pattern. Iterator works on pull model where application pulls items from the source, whereas 
		 * Observer works on push model and reacts when item is pushed from source to application. Java 9 Flow API subscriber can request for N items while 
		 * subscribing to the publisher. Then the items are pushed from publisher to subscriber until there are no more items left to push or some error occurs.
		 * 
		 * Let's have a quick look at Flow API classes and interfaces :
		 * 
		 * - java.util.concurrent.Flow : This is the main class of Flow API. This class encapsulates all the important interfaces of the Flow API. This is a 
		 *    final class and we can't extend it.
		 * - java.util.concurrent.Flow.Publisher : This is a functional interface and every publisher has to implement it's subscribe method to add the given 
		 *    subscriber to receive messages.
		 * - java.util.concurrent.Flow.Subscriber : Every subscriber has to implement this interface. The methods in the subscriber are invoked in strict 
		 *    sequential order. There are four methods in this interface : 
		 *   	* onSubscribe : This is the first method to get invoked when subscriber is subscribed to receive messages by publisher. Usually we invoke 
		 *   	   subscription.request to start receiving items from processor.
		 *   	* onNext : This method gets invoked when an item is received from publisher, this is where we implement our business logic to process the stream 
		 *         and then request for more data from publisher.
		 *   	* onError : This method is invoked when an irrecoverable error occurs. We can do cleanup taks in this method, such as closing database connection.
		 *   	* onComplete : This is like finally method and gets invoked when no other items are being produced by publisher and publisher is closed. We can 
		 *         use it to send notification of successful processing of stream.
		 * - java.util.concurrent.Flow.Subscription : This is used to create asynchronous non-blocking link between publisher and subscriber. Subscriber invokes 
		 *    its request method to demand items from publisher. It also has cancel method to cancel the subscription i.e. closing the link between publisher and 
		 *    subscriber.
		 * - java.util.concurrent.Flow.Processor : This interface extends both Publisher and Subscriber, this is used to transform the message between publisher 
		 *    and subscriber.
		 * - java.util.concurrent.SubmissionPublisher : A Publisher implementation that asynchronously issues submitted items to current subscribers until it is 
		 *    closed. It uses Executor framework. We will use this class in reactive stream examples to add subscriber and then submit items to them.
		 * 
		 *    
		 *    In the next example, we will implement Flow API Subscriber interface and use SubmissionPublisher to create publisher and send messages. Our 
		 *    Example will use the following classes :
		 *    - Employee : bean used to create the stream message to be sent from publisher to subscriber.
		 *    - EmpHelper : utility class to create a list of employees for our example.
		 *    - MySubscriber : this is the subscriber of the example, that consume Employee. Look inside the class for some explanations.
		 */

		List<Employee> employeesList = EmpHelper.getEmps();
		
		// Create Publisher.
		SubmissionPublisher<Employee> publisher = new SubmissionPublisher<>();
		
		// Register Subscriber.
		MySubscriber subscriber = new MySubscriber();
		publisher.subscribe(subscriber);
		
		// Wait for well display of outputs (allow the subscription to finished before the call of the next println()).
		Thread.sleep(300);
		// Publish items.
		System.out.println("Publishing Items to Subscriber");
		// Here, the publisher submit the employees, that next goes inside the subscriber.
		employeesList.stream().forEach(publisher::submit);
		// The most important pieces of above code are subscribe() and submit() methods invocation of publisher. Pay particular attention to them.
		
		// Logic to wait till processing of all messages are over. Whithout it, we would get unwanted results.
		while (employeesList.size() != subscriber.getCounter()) {
			Thread.sleep(10);
		}
		
		// We should always close publisher to avoid any memory leaks.
		publisher.close();
		
		// Wait for well display of outputs (allow the subscriber's onComplete() method to be called before the next println()).
		Thread.sleep(300);
		
		System.out.println("End of first example.");
		
		System.out.println("=====================================");
		
		/*
		 * Message Transformation Example
		 * 
		 * Processor is used to transform the message between a publisher and subscriber. Let's say we have another subscriber which is expecting a different 
		 * type of message to process. Our example will use the following classes :
		 * - Freelancer : the new type of message expected by the subscriber (it extends Employee class).
		 * - MyFreelancerSubscriber: The new subscriber consuming Freelancer.
		 * - MyProcessor : It implement the Processor interface. Also, since we want to utilize the SubmissionPublisher, it will be extend by this processor 
		 *    and used wherever applicable. Since Processor works as both subscriber and publisher, we can create a chain of processors between end 
		 *    publishers and subscribers. Look inside the class for some explanations.
		 */
		List<Employee> employeesToFreelancers = EmpHelper.getEmps();
		
		// Create End Publisher.
		SubmissionPublisher<Employee> endPublisher = new SubmissionPublisher<>();
		
		// Create Processor (works End Publisher between and End Subscriber).
		MyProcessor processor = new MyProcessor(employee -> new Freelancer(employee.getId(), employee.getId() + 100, employee.getName()));
		
		//Create End Subscriber.
		MyFreelancerSubscriber endSubscriber = new MyFreelancerSubscriber();
		
		//Create chain of publisher, processor and subscriber
		endPublisher.subscribe(processor); // from publisher to processor
		processor.subscribe(endSubscriber); // from processor to subscriber
		
		// Wait for well display of outputs (allow the subscription to finished before the call of the next println()).
		Thread.sleep(300);
		// Publish items
		System.out.println("Publishing Items to Subscriber");
		// Here, the publisher submit the employees, that next goes inside the processor. Then the processor, inside himself, will
		// transform the employees to freelancers. This freelancers will then be submitted by the processor, and goes inside subscriber.
		employeesToFreelancers.stream().forEach(endPublisher::submit);

		// Logic to wait for messages processing to finish. Whithout it, we would get unwanted results.
		while (employeesToFreelancers.size() != endSubscriber.getCounter()) {
			Thread.sleep(10);
		}

		// Closing publishers (don't forget the processor, that is a publisher too).
		processor.close();
		endPublisher.close();
		
		// Wait for well display of outputs (allow the subscriber's and processor's onComplete() methods to be called before the next println()).
		Thread.sleep(1000);

		System.out.println("Exiting the processor use example.");
		
		/*
		 * CANCEL SUBSCRIPTION : We can use Subscription cancel method to stop receiving message in subscriber. Note that if we cancel the subscription, 
		 * then subscriber will not receive onComplete() or onError() signal. Note that if we cancel the subscription in the subscriber, Processor will
		 * continue to submit freelancers and receive onComplete() or onError() signal.
		 * 
		 * Here is a sample code where subscriber is consuming only 3 messages and then canceling the subscription :
		 "
			@Override
			public void onNext(Employee item) {
				System.out.println("Processing Employee "+item);
				counter++;
				if(counter==3) {
					this.subscription.cancel();
					return;
				}
				this.subscription.request(1);
			}
		 "
		 * BEWARE : in this case, our logic to sleep main thread before all the messages are processed will go into infinite loop.
		 */
		
		/*
		 * BACK PRESSURE : When publisher is producing messages in much faster rate than it's being consumed by subscriber, back pressure gets built. Flow API 
		 * doesn't provide any mechanism to signal about back pressure or to deal with it. But we can devise our own strategy to deal with it, such as fine tuning 
		 * the subscriber or reducing the message producing rate. 
		 * 
		 * For example, you can read how RxJava deals with Back Pressure :  https://github.com/ReactiveX/RxJava/wiki/Backpressure
		 */
	}

}
