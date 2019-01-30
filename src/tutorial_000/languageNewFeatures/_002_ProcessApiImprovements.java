package tutorial_000.languageNewFeatures;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class _002_ProcessApiImprovements {

	public static void main(String[] args) {
		/*
		 * The process API in Java had been quite primitive prior to Java 5, the only way to spawn a new process was to use the Runtime.getRuntime().exec() 
		 * API. Then in Java 5, ProcessBuilder API was introduced which supported a cleaner way of spawning new processes.
		 * Java 9 is adding a new way of getting information about current and any spawned processes.
		 * 
		 * CURRENT JAVA PROCESS INFORMATION : We can now obtain a lot of information about the process via the API java.lang.ProcessHandle.Info API :
		 * - the command used to start the process
		 * - the arguments of the command
		 * - time instant when the process was started
		 * - total time spent by it and the user who created it
		 */
		ProcessHandle processHandle = ProcessHandle.current();
	    ProcessHandle.Info processInfo = processHandle.info();
	    
	    // Returns the native process ID of the process. 
	    System.out.println(processHandle.pid());
	    System.out.println("-------------------------------------");
	    // Print the list of the arguments of the process.
	    try {
	    	Arrays.stream(processInfo.arguments().get()).forEach((arg) -> System.out.println(arg));
	    } catch(NoSuchElementException e) {
	    	System.out.println("No arguments passed to the process.");
	    }
	    System.out.println("-------------------------------------");
	    // Returns the executable pathname of the process. 
	    System.out.println(processInfo.command());
	    // Return true if the executable pathname contain "java". 
	    System.out.println("Is command contain java ? : " + processInfo.command().get().contains("java"));
	    // Returns the start time of the process.
	    System.out.println(processInfo.startInstant());
	    // Returns the total cputime accumulated of the process.
	    System.out.println(processInfo.totalCpuDuration());
	    // Return the user of the process.
	    System.out.println(processInfo.user());
	    
	    /*
	     * It is important to note that java.lang.ProcessHandle.Info is a public interface defined within another interface java.lang.ProcessHandle. The 
	     * JDK provider (Oracle JDK, Open JDK, Zulu or others) should provide implementations to these interfaces in such a way that these implementations 
	     * return the relevant informations for the processes.
	     */
	    
	    System.out.println("=====================================");
	    
	    /*
	     * SPAWNED PROCESS INFORMATION : It is also possible to get the process information of a newly spawned process. In this case, after we spawn the 
	     * process and get an instance of the java.lang.Process, we invoke the toHandle() method on it to get an instance of java.lang.ProcessHandle. The 
	     * rest of the details remain the same as above :
	     */
		try {
			String javaCmd = getJavaCmd().getAbsolutePath();
		    ProcessBuilder processBuilder = new ProcessBuilder(javaCmd, "-version");
			Process spawnProcess = processBuilder.inheritIO().start();
			
			ProcessHandle spawnProcessHandle = spawnProcess.toHandle();
			ProcessHandle.Info spawnProcessInfo = spawnProcessHandle.info();
			
		    System.out.println(spawnProcessHandle.pid());
		    System.out.println("-------------------------------------");
		    try {
		    	Arrays.stream(spawnProcessInfo.arguments().get()).forEach((arg) -> System.out.println(arg));
		    } catch(NoSuchElementException e) {
		    	System.out.println("No arguments passed to the process.");
		    }
		    System.out.println("-------------------------------------");
		    System.out.println(spawnProcessInfo.command());
		    System.out.println("Is command contain java ? : " + spawnProcessInfo.command().get().contains("java"));
		    System.out.println(spawnProcessInfo.startInstant());
		    System.out.println(spawnProcessInfo.totalCpuDuration());
		    System.out.println(spawnProcessInfo.user());
			
		    spawnProcess.destroyForcibly();
		} catch (IOException | UnsupportedOperationException e) {
			e.printStackTrace();
		}
	    
		System.out.println("=====================================");
		
		/*
		 * ENUMERATION LIVE PROCESSES IN THE SYSTEM : We can list all the processes currently in the system, which are visible to the current process. The 
		 * returned list is a snapshot at the time when the API was invoked, so it’s possible that some processes terminated after taking the snapshot or 
		 * some new processes were added. In order to do that, we can use the static method allProcesses() available in the java.lang.ProcessHandle interface 
		 * which returns Stream of ProcessHandle :
		 */
		Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
	    liveProcesses.filter(ProcessHandle::isAlive)
	    	.limit(5)
	        .forEach(ph -> {
	        	System.out.println(ph.pid());
	        	Optional<String> opt = ph.info().command();
	        	System.out.println(opt.isPresent() ? opt.get() : "No pathname.");
	        	System.out.println("-------------------------------------");
	        });
	    
	    System.out.println("=====================================");
	    
	    /*
	     * ENUMERATING CHILD PROCESSES : There are two variants to do this :
	     * - get direct children of the current process by using the childern() method
	     * - get all the descendants of the current process by using the descendants() method
	     */
	    try {
		    int childProcessCount = 5;
		    List<Process> processesList = new ArrayList<>();
		    for (int i = 0; i < childProcessCount; i++){
		        String javaCmd = getJavaCmd().getAbsolutePath();
		        ProcessBuilder processBuilder = new ProcessBuilder(javaCmd, "-version");
		        processesList.add(processBuilder.inheritIO().start());
		    }
		    
		    // Get direct children of the process.
		    Stream<ProcessHandle> children = ProcessHandle.current().children();
		 
		    children.filter(ProcessHandle::isAlive)
		      		.forEach(ph -> System.out.println(String.format("PID: %s, Cmd: %s", ph.pid(), ph.info().command())));
		 
		    System.out.println("-------------------------------------");
		    
		    // Get all descendants or the process.
		    Stream<ProcessHandle> descendants = ProcessHandle.current().descendants();
		    
		    descendants.filter(ProcessHandle::isAlive)
		      			.forEach(ph -> System.out.println(String.format("PID: %s, Cmd: %s", ph.pid(), ph.info().command())));
		    
		    processesList.forEach(process -> process.destroyForcibly());
	    } catch(IOException | UnsupportedOperationException e) {
	    	e.printStackTrace();
	    }
	    
	    System.out.println("=====================================");
	    
	    /*
	     * TRIGGERING DEPENDENT ACTIONS ON PROCESS TERMINATION : We might want to run something on termination of a process. This can be achieved by 
	     * using the onExit() method in the java.lang.ProcessHandle interface. The method returns us a CompletableFuture which provides the ability to 
	     * trigger dependent operations when the CompletableFuture is completed.
	     * 
	     * In the next example, the CompletableFuture indicates the process has completed, but it doesn’t matter if the process has completed successfully 
	     * or not. We invoke the get() method on the CompletableFuture, to wait for its completion :
	     */
	    try {
	    	String javaCmd = getJavaCmd().getAbsolutePath();
	    	
		    ProcessBuilder processBuilder  = new ProcessBuilder(javaCmd, "-version");
		    Process process = processBuilder.inheritIO().start();
		    ProcessHandle processHandle2 = process.toHandle();
		 
		    System.out.println(String.format("PID: %s has started", processHandle2.pid()));
		    		
		    CompletableFuture<ProcessHandle> onProcessExit = processHandle2.onExit();
		    
		    System.out.println("Is process alive ? : " + processHandle2.isAlive());
		    
		    onProcessExit.get();
		    
		    System.out.println("Is process alive ? : " + processHandle2.isAlive());
		    
		    onProcessExit.thenAccept(ph -> System.out.println(String.format("PID: %s has stopped", processHandle2.pid())));
		    
	    } catch(InterruptedException | ExecutionException | IOException | UnsupportedOperationException e) {
	    	e.printStackTrace();
		    }
	}
	
	private static File getJavaCmd() throws IOException, UnsupportedOperationException {
		String javaHome = System.getProperty("java.home");
		File javaCmd;
		if(System.getProperty("os.name").startsWith("Win")) {
			javaCmd = new File(javaHome, "bin/java.exe");
		} else {
			javaCmd = new File(javaHome, "bin/java");
		}
		
		if(javaCmd.canExecute()) {
			return javaCmd;
		} else {
			throw new UnsupportedOperationException(javaCmd.getCanonicalPath() + "is not executable");
		}
	}

}
