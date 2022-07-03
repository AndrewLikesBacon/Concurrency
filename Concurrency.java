
public class Concurrency {
	public static int totals[] = new int[4];
	
	public static void main(String[] args) {
		
		int[] nums = new int[200000000];
		int total = 0;
		
		for (int i=0; i<200000000; i++) {
			nums[i] = (int)((Math.random()*10)+1);
		}
		
		//With one thread
		System.out.println("One thread:");
		long startTime = System.nanoTime();
		
		for (int i=0; i<200000000; i++) {
			total += nums[i];
		}
		
		System.out.println("Total: " + total);
		
		long endTime = System.nanoTime();
		
		System.out.println("Time taken in nanoseconds: " + (endTime - startTime) + " (" + ((endTime - startTime) / 1000000000.0) + " seconds)");
		System.out.println();
		
		//With multiple threads
		System.out.println("Multiple threads:");
		total = 0;
		
		startTime = System.nanoTime();
		
		Thread t1 = new Thread (new Runnable() {

			@Override
			public void run() {
				
				for (int i=0; i<50000000; i++) {
					totals[0] += nums[i];
				}
			}
		});
		
		Thread t2 = new Thread (new Runnable() {

			@Override
			public void run() {
				
				for (int i=50000000; i<100000000; i++) {
					totals[1] += nums[i];
				}
			}
		});
		
		Thread t3 = new Thread (new Runnable() {

			@Override
			public void run() {
				
				for (int i=100000000; i<150000000; i++) {
					totals[2] += nums[i];
				}
			}
		});
		
		Thread t4 = new Thread (new Runnable() {

			@Override
			public void run() {
				
				for (int i=150000000; i<200000000; i++) {
					totals[3] += nums[i];
				}
			}
		});
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<4; i++) {
			total += totals[i];
		}
		
		System.out.println("Total: " + total);
		
		endTime = System.nanoTime();
		
		System.out.println("Time taken in nanoseconds: " + (endTime - startTime) + "(" + ((endTime - startTime) / 1000000000.0) + " seconds)");
	}
}
