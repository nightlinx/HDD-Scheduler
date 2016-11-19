import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList <Page> queue = new ArrayList<>();

		Random r = new Random();
		
		int pages = 10;
		int frames = 4;
		int pagesInQueue = 200;
		
		for(int i = 0; i<pagesInQueue; i++){
			queue.add(new Page(r.nextInt(pages)));
		}

		int errors = Algorytm.FIFO(clonedList(queue), frames);
		System.out.println("FIFO: " + errors);

		errors = Algorytm.RAND(clonedList(queue), frames);
		System.out.println("RAND: " + errors);
		
		errors = Algorytm.LRU(clonedList(queue), frames);
		System.out.println("LRU: " + errors);			
		
		errors = Algorytm.ARLU(clonedList(queue), frames);
		System.out.println("ARLU: " + errors);	
		
		errors = Algorytm.OPT(clonedList(queue), frames);
		System.out.println("OPT: " + errors);			
		
		
		
	}

	public static ArrayList <Page> clonedList(ArrayList <Page> queue) {
		ArrayList <Page> clonedList = new ArrayList <Page>(queue.size());
		   for (Page p : queue) {
		       clonedList.add(new Page(p.value));
		   }
		 
		   return clonedList;
	}
}
