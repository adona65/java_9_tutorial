package tutorial_000.languageNewFeatures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class _004_CompletableFutureApiImprovements {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		/*
		 * Java 9 main improvements to CompletableFutur API are support for delays and timeouts, better support for subclassing and a few
		 * utility methods.
		 * 
		 * METHOD defaultExecutor() : Returns the default Executor used for async methods that do not specify an Executor. This can be overridden
		 * by subclasses returning an executor providing, at least, one independent thread.
		 */
		new CompletableFuture().defaultExecutor();
		
		/*
		 * METHOD newIncompleteFuture() : The newIncompleteFuture, also known as the "virtual constructor", is used to get a new completable future 
		 * instance of the same type. This method is especially useful when subclassing CompletableFuture, mainly because it is used internally in 
		 * almost all methods returning a new CompletionStage, allowing subclasses to control what subtype gets returned by such methods.
		 */
		new CompletableFuture().newIncompleteFuture();
		
		/*
		 * METHOD copy() : This method returns a new CompletableFuture which :
		 * - When this gets completed normally, the new one gets completed normally too
		 * - When this gets completed exceptionally with exception X, the new one is also completed exceptionally with a CompletionException with X 
		 *   as cause
		 *   
		 *   This method may be useful as a form of "defensive copying", to prevent clients from completing, while still being able to arrange dependent 
		 *   actions on a specific instance of CompletableFuture.
		 */
		new CompletableFuture().copy();
		
		/*
		 * METHOD minimalCompletionStage() : This method returns a new CompletionStage which behaves in the exact same way as described by the copy method, 
		 * however, such new instance throws UnsupportedOperationException in every attempt to retrieve or set the resolved value. A new CompletableFuture 
		 * with all methods available can be retrieved by using the toCompletableFuture method available on the CompletionStage API.
		 */
		new CompletableFuture().minimalCompletionStage();
		
		/*
		 * METHODS completeAsync() : The completeAsync method should be used to complete the CompletableFuture asynchronously using the value given by the 
		 * Supplier provided. The difference between the two overloaded methods is the existence of the second argument, where the Executor running the task 
		 * can be specified. If none is provided, the default executor (returned by the defaultExecutor method) will be used.
		 */
		// CompletableFuture<T> completeAsync(Supplier<? extends T> supplier, Executor executor)
		// CompletableFuture<T> completeAsync(Supplier<? extends T> supplier)
		
		/*
		 * METHODS orTimeout() : Resolves the CompletableFuture exceptionally with TimeoutException, unless it is completed before the specified timeout.
		 */
		new CompletableFuture().orTimeout(1, TimeUnit.SECONDS);
		
		/*
		 * METHOD completeOnTimeout() : Completes the CompletableFuture normally with the specified value unless it is completed before the specified timeout.
		 */
		new CompletableFuture().completeOnTimeout("specified value as object", 1, TimeUnit.SECONDS);
		
		/*
		 * METHODS delayedExecutor() : Returns a new Executor that submits a task to the given base executor after the given delay (or no delay if non-positive).
		 * Each delay commences upon invocation of the returned executor's execute method. If no executor is specified the default executor 
		 * (ForkJoinPool.commonPool()) will be used.
		 */
		// Executor delayedExecutor(long delay, TimeUnit unit, Executor executor)
		// Executor delayedExecutor(long delay, TimeUnit unit)
		
		/*
		 * METHODS completedStage() and failedStage() : This utility methods return already resolved CompletionStage instances, either completed normally with a 
		 * value (completedStage) or completed exceptionally (failedStage) with the given exception.
		 */
		// <U> CompletionStage<U> completedStage(U value)
		// <U> CompletionStage<U> failedStage(Throwable ex)
		
		/*
		 * METHOD failedFuture : The failedFuture method adds the ability to specify an already completed exceptionally CompleatebleFuture instance. 
		 */
		// <U> CompletableFuture<U> failedFuture(Throwable ex)
		
		/*
		 * In the following, we will see some little examples about few news methods listed above. 
		 */
		// This example will show how to delay the completion of a CompletableFuture with a specific value by one second. That can be achieved by using the 
		// completeAsync method together with the delayedExecutor.
		CompletableFuture<String> completeAsyncFuture = new CompletableFuture<>();
		completeAsyncFuture.completeAsync(() -> {
												System.out.println("Inside completeAsync() method's supplier."); 
												return "Complete async supplier end working.";
											}, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
		try {
			System.out.println(completeAsyncFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("=====================================");
		
		// Another way to achieve a delayed result is to use the completeOnTimeout method. This example defines a CompletableFuture that will be resolved with
		// a given input if it stays unresolved after 1 second.
		CompletableFuture<String> onTimeoutFuture = new CompletableFuture<>();
		onTimeoutFuture.completeOnTimeout("On timeout end working.", 1, TimeUnit.SECONDS);
	
		try {
			System.out.println(onTimeoutFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("=====================================");
		
		// Another possibility is timing out which resolves the future exceptionally with TimeoutException. For example, having the CompletableFuture timing 
		// out after 1 second given it is not completed before that.
		CompletableFuture<Object> orTimeoutFuture = new CompletableFuture<>();
		orTimeoutFuture.orTimeout(1, TimeUnit.SECONDS);
		
		try {
			System.out.println("Folling example will throw a java.util.concurrent.TimeoutException.");
			// Will throw a java.util.concurrent.TimeoutException as the future is uncompleted after 1 second.
			orTimeoutFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
