package tutorial_000.languageNewFeatures;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpHeaders;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpRequest.Builder;
import jdk.incubator.http.HttpResponse;

public class _010_Http2Client {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		/*
		 * The HTTP Client module is bundled as an incubator module in JDK 9 and supports HTTP/2 with backward compatibility still facilitating 
		 * HTTP/1.1. To use it, we need specify the correct module in the module-info.java file to be able to use it. To do so we need to add requires 
		 * "jdk.incubator.httpclient;"
		 * 
		 * Unlike HttpURLConnection, HTTP Client provides synchronous and asynchronous request mechanisms. The API consists of 3 core classes :
		 * - HttpRequest : represents the request to be sent via the HttpClien
		 * - HttpClient : behaves as a container for configuration information common to multiple requests
		 * - HttpResponse : represents the result of an HttpRequest call
		 * 
		 * HttpRequest : it is an object which represents a request we want to send. New instances can be created using HttpRequest.Builder. We can get 
		 * it by calling HttpRequest.newBuilder(). Builder class provides a bunch of methods which we can use to configure our request.
		 */
		
		// The first thing we have to do when creating a request is to provide the URL. We can do that in two ways : by using the constructor for Builder 
		// with URI parameter or by calling method uri(URI) on the Builder instance :
		Builder httpRequestBuilder = HttpRequest.newBuilder(new URI("https://postman-echo.com/get"));
		Builder httpRequestBuilder2 = HttpRequest.newBuilder().uri(new URI("https://postman-echo.com/get"));
		
		// The next thing we have to configure to create a basic request is an HTTP method.
		
		/*
		 * HTTP Method : We can define HTTP method which our request will use by calling one of the methods from Builder :
		 * - GET()
		 * - POST(BodyProcessor body)
		 * - PUT(BodyProcessor body)
		 * - DELETE(BodyProcessor body)
		 */
		
		// Here we will create a simple GET request.
		HttpRequest getRequest = httpRequestBuilder.GET().build();
		
		/*
		 * This request has all parameters required by HttpClient. However, sometimes we need to add additional parameters to our request, for example :
		 * - the version of the HTTP protocol
		 * - headers
		 * - a timeout
		 * 
		 * HTTP Protocol Version : The API fully leverages the HTTP/2 protocol and uses it by default but we can define which version of protocol we want 
		 * to use.
		 */
		
		// The client will fallback to, e.g., HTTP/1.1 if HTTP/2 isn't supported. 
		HttpRequest versionedRequest = httpRequestBuilder.version(Version.HTTP_2).GET().build();
		
		/*
		 * Setting Headers : In case we want to add additional headers to our request, we can use the provided builder methods. We can do that in one of two 
		 * ways :
		 * - passing all headers as key-value pairs to the headers() method
		 * - using header() method for the single key-value header
		 */
		HttpRequest headeredRequest = httpRequestBuilder.headers("key1", "value1", "key2", "value2").GET().build();
		HttpRequest headeredRequest2 = httpRequestBuilder.header("key1", "value1").header("key2", "value2").GET().build();
		
		/*
		 * Setting a Timeout : timeout() define the amount of time we want to wait for a response. If the set time expires, a HttpTimeoutException will be 
		 * thrown. The default timeout is set to infinity. The timeout can be set with the Duration object by calling timeout() method on the builder instance :
		 */
		HttpRequest timedRequest = httpRequestBuilder.timeout(Duration.of(10, ChronoUnit.SECONDS)).GET().build();
		
		/*
		 * Setting a Request Body : we can add a body to a request by using the following builder's methods : POST(BodyProcessor body), PUT(BodyProcessor body) 
		 * and DELETE(BodyProcessor body). The new API provides a number of BodyProcessor implementations which simplify passing the request body :
		 * - StringProcessor : reads body from a String, created with HttpRequest.BodyProcessor.fromString
		 * - InputStreamProcessor : reads body from an InputStream, created with HttpRequest.BodyProcessor.fromInputStream
		 * - ByteArrayProcessor : reads body from a byte array, created with HttpRequest.BodyProcessor.fromByteArray
		 * - FileProcessor : reads body from a file at given path, created with HttpRequest.BodyProcessor.fromFile
		 * 
		 * If the request don't need a body, we can simply pass in an HttpRequest.noBody() :
		 */
		HttpRequest noBodyRequest = httpRequestBuilder.uri(new URI("https://postman-echo.com/post")).POST(HttpRequest.noBody()).build();
		
		/*
		 * StringBodyProcessor : allow to pass simple String as a body. As mentioned above, this object can be created with the factory method fromString() :
		 */
		HttpRequest stringBodyRequest = httpRequestBuilder
				  .uri(new URI("https://postman-echo.com/post"))
				  .headers("Content-Type", "text/plain;charset=UTF-8")
				  .POST(HttpRequest.BodyProcessor.fromString("Sample request body"))
				  .build();
		
		/*
		 * InputStreamBodyProcessor : the InputStream has to be passed as a Supplier for being intégrated to the body of the resquest.
		 */
		byte[] sampleStreamData = "Sample request body".getBytes();
		
		HttpRequest streamBodyRequest = httpRequestBuilder
				  .uri(new URI("https://postman-echo.com/post"))
				  .headers("Content-Type", "text/plain;charset=UTF-8")
				  .POST(HttpRequest.BodyProcessor.fromInputStream(() -> new ByteArrayInputStream(sampleStreamData)))
				  .build();
		
		/*
		 * ByteArrayProcessor : we can also use ByteArrayProcessor and pass an array of bytes as the parameter :
		 */
		byte[] sampleByteArrayData = "Sample request body".getBytes();
		
		HttpRequest byteArrayRequest = httpRequestBuilder
				  .uri(new URI("https://postman-echo.com/post"))
				  .headers("Content-Type", "text/plain;charset=UTF-8")
				  .POST(HttpRequest.BodyProcessor.fromByteArray(sampleByteArrayData))
				  .build();
		
		/*
		 * FileProcessor : to work with a File, we can make use of the provided FileProcessor. Its factory method takes a path to the file as a parameter 
		 * and creates a body from the content.
		 */
		HttpRequest fileRequest = httpRequestBuilder
				  .uri(new URI("https://postman-echo.com/post"))
				  .headers("Content-Type", "text/plain;charset=UTF-8")
				  .POST(HttpRequest.BodyProcessor.fromFile(Paths.get("src/tutorial_000/languageNewFeatures/test.txt")))
				  .build();
		
		/*
		 * HttpClient : All requests are sent using HttpClient, which can be instantiated using the HttpClient.newBuilder() method or by calling 
		 * HttpClient.newHttpClient(). It provides a lot methods we can use to handle our request/response. We will see some of them in the next
		 * examples.
		 * 
		 * Setting a Proxy : We can define a proxy for the connection by calling proxy() method on a Builder instance.
		 */
		jdk.incubator.http.HttpClient.Builder httpClientBuilder = HttpClient.newBuilder();
		
		HttpResponse<String> proxifiedResponse = httpClientBuilder
				  .proxy(ProxySelector.getDefault())
				  .build()
				  .send(getRequest, HttpResponse.BodyHandler.asString());
		
		/*
		 * Setting the Redirect Policy : Sometimes the page we want to access has moved to a different address. In that case, we'll receive HTTP status code 
		 * 3xx, usually with the information about new URI. HttpClient can redirect the request to the new URI automatically if we set appropriate redirect 
		 * policy. We can do it with the followRedirects() method of Builder :
		 */
		HttpResponse<String> rerirectedResponse = httpClientBuilder
				  .followRedirects(HttpClient.Redirect.ALWAYS)
				  .build()
				  .send(getRequest, HttpResponse.BodyHandler.asString());
		
		/*
		 * Setting Authenticator for a Connection : An Authenticator is an object which negotiates credentials (HTTP authentication) for a connection. It 
		 * provides different authentication schemes (like e.g., basic or digest authentication). In most cases, authentication requires username and password 
		 * to connect to a server. We can use PasswordAuthentication class which is just a holder of these values.
		 */
		HttpResponse<String> authenticatedResponse = httpClientBuilder
				  .authenticator(new Authenticator() {
				    @Override
				    protected PasswordAuthentication getPasswordAuthentication() {
				      // In "real world", do not use plain text loggin/password.
				      return new PasswordAuthentication(
				        "username", 
				        "password".toCharArray());
				    }
				  })
				  .build()
				  .send(getRequest, HttpResponse.BodyHandler.asString());
		/*
		 * Note that not every request should use the same username and password. The Authenticator class provides a number of getXXX (e.g., getRequestingSite())
		 * methods that can be used to find out what values should be provided.
		 */
		
		/*
		 * Send Requests : Sync vs. Async : New HttpClient provides two possibilities for sending a request to a server :
		 * - send() : synchronously request the server (blocks until the response comes). It returns an HttpResponse object, and we're sure that the next 
		 *             instruction from our application flow will be executed only when the response is already here.
		 * - sendAsync() : asynchronously request the server (doesn't wait for response, non-blocking). It returns CompletableFeature<HttpResponse>.
		 */
		CompletableFuture<HttpResponse<String>> asynchronousResponse = httpClientBuilder
				  .build()
				  .sendAsync(getRequest, HttpResponse.BodyHandler.asString());
		
		/*
		 * The new API can also deal with multiple responses, and stream the request and response bodies :
		 */
		List<URI> targets = Arrays.asList(
				  new URI("https://postman-echo.com/get?foo1=bar1"),
				  new URI("https://postman-echo.com/get?foo2=bar2"));
		
		HttpClient client = HttpClient.newHttpClient();
		
		List<CompletableFuture<String>> futures = targets.stream()
				  .map(target -> client.sendAsync(
									      HttpRequest.newBuilder(target).GET().build(),
									      HttpResponse.BodyHandler.asString()
									   )
								       .thenApply(response -> response.body())
				   )
				  .collect(Collectors.toList());
		
		/*
		 * Setting Executor for Asynchronous Calls : We can also define an Executor which provides threads to be used by asynchronous calls. This way we 
		 * can, for example, limit the number of threads used for processing requests. By default, the HttpClient uses executor 
		 * java.util.concurrent.Executors.newCachedThreadPool().
		 */
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		 
		CompletableFuture<HttpResponse<String>> response1 = HttpClient.newBuilder()
				  .executor(executorService)
				  .build()
				  .sendAsync(getRequest, HttpResponse.BodyHandler.asString());
		 
		CompletableFuture<HttpResponse<String>> response2 = HttpClient.newBuilder()
				  .executor(executorService)
				  .build()
				  .sendAsync(getRequest, HttpResponse.BodyHandler.asString());
		
		/*
		 * Defining a CookieManager : With new API and builder, it's straightforward to set a CookieManager for our connection. We can use builder method 
		 * cookieManager(CookieManager cookieManager) to define client-specific CookieManager. Let's define a CookieManager which doesn't allow to accept 
		 * cookies at all :
		 */
		HttpClient.newBuilder()
				  .cookieManager(new CookieManager(null, CookiePolicy.ACCEPT_NONE))
				  .build();
		
		// In case our CookieManager allows cookies to be stored, we can access them by checking CookieManager from our HttpClient :
		HttpClient.newHttpClient().cookieManager().get().getCookieStore();
		
		/*
		 * HttpResponse Object : the HttpResponse class represents the response from the server. It provides a number of useful methods, but among the most 
		 * important are :
		 * - statusCode() : returns status code (type int) for a response (HttpURLConnection class contains possible values).
		 * - body() : returns a body for a response (return type depends on the response BodyHandler parameter passed to the send() method).
		 * 
		 * The response object has other useful method we will see in the following examples.
		 * 
		 * URI of Response Object : The method uri() on the response object returns the URI from which we received the response. Sometimes it may be different 
		 * than the URI we used in the request object, because a redirection may have occur.
		 */
		String requestUri = getRequest.uri().toString();
		String responseUri = proxifiedResponse.uri().toString();
		
		/*
		 * Headers from Response : we can obtain headers from the response by calling method headers() on a response object. It returns HttpHeaders object 
		 * as a return type. This is a new type defined in jdk.incubator.http package which represents a read-only view of HTTP Headers. It has some useful 
		 * methods which simplify searching for headers value.
		 */
		HttpHeaders responseHeaders = proxifiedResponse.headers();
		
		/*
		 * Get Trailers from Response : The HTTP response may contain additional headers which are included after the response content. These headers are 
		 * called trailer headers. We can obtain them by calling method trailers() on HttpResponse (this method return a CompletableFuture<HttpHeaders>.
		 */
		CompletableFuture<HttpHeaders> trailers = proxifiedResponse.trailers();
		
		/*
		 * Version of the Response : The method version() defines which version of HTTP protocol was used to talk with a server. It return an Optional<Version>.
		 * Remember that even if we define that we want to use HTTP/2, the server can answer via HTTP/1.1. The version in which server answered is specified in 
		 * the response.
		 */
		Optional<Version> version = versionedRequest.version();
	}

}
