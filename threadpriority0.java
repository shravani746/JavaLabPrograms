class threadpriority0 extends Thread 
{ 
	public void run() 
	{ 
		System.out.println("Thread Running..."); 
	} 

	public static void main(String[]args) 
	{ 
		threadpriority0 p1 = new threadpriority0(); 
		threadpriority0 p2 = new threadpriority0(); 
		threadpriority0 p3 = new threadpriority0(); 
		p1.start();
		System.out.println("P1 thread priority : " + p1.getPriority()); 
		System.out.println("P2 thread priority : " + p2.getPriority());  
		System.out.println("P3 thread priority : " + p3.getPriority()); 
		
	} 
}