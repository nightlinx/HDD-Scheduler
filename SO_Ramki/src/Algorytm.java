import java.util.ArrayList;
import java.util.Random;


public class Algorytm {
	int time;
	static int errors;
	static Page[] frames;
	
	static ArrayList <Page> queue = new ArrayList<>();

	public static int FIFO(ArrayList <Page> queue, int length){
		Algorytm.queue = queue;
		frames = new Page[length];		
		errors = 0;
		
		//wypelniam cala ramke i dodaje czasy procesom
		for(int i = 0; i < frames.length; i++){
			int index = isInFrame(queue.get(0));
			
			if(index==-1){
				frames[i] = queue.get(0);
				errors++;
			}
			else{
				frames[index] = queue.get(0);
				i--;
			}
			queue.remove(0);
		}
		int i = 0;
		while(!queue.isEmpty()){
			Page p = queue.get(0);
			int index = isInFrame(p);
			
			if(index==-1){			
				frames[i] = p;
				i++;
				errors++;
			}
			
			if (i == frames.length){
				i = 0;
			}
			queue.remove(0);
		}	
		return errors;
	}	

	public static int RAND(ArrayList <Page> queue, int length){
		Algorytm.queue = queue;
		frames = new Page[length];		
		errors = 0;
		
		//wypelniam cala ramke i dodaje czasy procesom
		for(int i = 0; i < frames.length; i++){
			int index = isInFrame(queue.get(0));
			
			if(index==-1){
				frames[i] = queue.get(0);
				errors++;
//				addTime();
			}
			else{
				frames[index] = queue.get(0);
				i--;
			}
			queue.remove(0);
		}

		while(!queue.isEmpty()){
			Page p = queue.get(0);
			int index = isInFrame(p);
			
			if(index==-1){
				Random rand = new Random();
				int randIndex = rand.nextInt(frames.length);

				frames[randIndex] = p;
				errors++;		
			}			
			else{
				frames[index] = p;
			}
//			addTime();
			queue.remove(0);
		}	
		return errors;
	}	
	
	public static int LRU(ArrayList <Page> queue, int length){
		Algorytm.queue = queue;
		frames = new Page[length];		
		errors = 0;
		
		for(int i = 0; i < frames.length; i++){
			int index = isInFrame(queue.get(0));
			
			if(index==-1){
				frames[i] = queue.get(0);
				errors++;
			}
			else{
				frames[index] = queue.get(0);
				i--;
			}
			queue.remove(0);
		}

		while(!queue.isEmpty()){
			Page p = queue.get(0);
			int index = isInFrame(p);
			
			if(index==-1){				
				frames[getOldest()] = p;
				errors++;			
			}			
			else{
				frames[index] = p;
			}
			queue.remove(0);
		}
		return errors;
	}
	
	public static int ARLU(ArrayList <Page> queue, int length){
		Algorytm.queue = queue;
		Algorytm.frames = new Page[length];		
		errors = 0;
				
		//wypelniam cala ramke i dodaje czasy procesom
		for(int i = 0; i < frames.length; i++){
			int index = isInFrame(queue.get(0));
			
			if(index==-1){
				frames[i] = queue.get(0);
				errors++;
				addTime();
			}
			else{
				frames[index] = queue.get(0);
				i--;
			}
			queue.remove(0);
		}

		while(!queue.isEmpty()){
			Page p = queue.get(0);
			int index = isInFrame(p);
			
			if(index==-1){
				//lece przez tablice i szukam tego co ma bit = 0
				boolean found = false;
				for(int j = getOldest(); j < frames.length; j++ ){
					if(frames[j].bit == 1){
						frames[j].bit = 0;
					}
					else{
						frames[j] = p;
						errors++;
						found = true;
						break;
					}
				}
				
				if(!found){
					for(int j = 0; j <= getOldest(); j++){
						if(frames[j].bit == 1){
							frames[j].bit = 0;
						}
						else{
							frames[j] = p;
							errors++;
							found = true;
							break;
						}	
					}
				}	
			}			
			else{
				frames[index] = p;
			}
			addTime();
			queue.remove(0);
		}	
		return errors;		

	}
	
	public static int OPT(ArrayList <Page> queue, int length){
		Algorytm.queue = queue;
		Algorytm.frames = new Page[length];		
		errors = 0;
				
		//wypelniam cala ramke i dodaje czasy procesom
		for(int i = 0; i < frames.length; i++){
			int index = isInFrame(queue.get(0));
			
			if(index==-1){
				frames[i] = queue.get(0);
				errors++;
//				addTime();
			}
			else{
				frames[index] = queue.get(0);
				i--;
			}
			queue.remove(0);
		}
		
		while(!queue.isEmpty()){
			Page p = queue.get(0);
			int index = isInFrame(p);
			
			if(index==-1){
				int latestReferenceIndex = uptadeNextReference();
				frames[latestReferenceIndex] = p;
				errors++;
			}			
			else{
				frames[index] = p;
			}
			addTime();
			queue.remove(0);
		}	
		return errors;
	}
	
	//aktualizuje kolejne refenrecje do odwo³an stron, oraz zwraca index najpozniejszego odwolania
	public static int uptadeNextReference(){
		boolean found;
		for(Page f: frames){
			found = false;
			for(Page q: queue){
				if(f.value==q.value && !found){
					//jesli znajde pierwsze odwolanie do strony w kolejce, to zapamietuje
					f.nextReference = queue.indexOf(q);
					found = true;
				}
			}
			//jesli nie znalazlam to nie ma odwolania to daje mu najdalsza refenrencje
			if(!found){
				f.nextReference = queue.size();
			}
		}
		
		int latest = 0;
		for(int i = 0; i <frames.length; i++){
			if(frames[i].nextReference > frames[latest].nextReference){
				latest = i;
			}
		}
		return latest;
	}
		
	public static void addTime(){
		for(Page p : frames){
			if(p!=null)
				p.time++;
		}
	}
	
	//-1 - nie ma
	public static int isInFrame(Page p){
		for(int i = 0; i < frames.length; i++){
			if(frames[i]!=null && frames[i].value == p.value){
				return i;
			}
		}
		return -1;
	}
	
	//index of a process, witch is the longest time in a frame;
	public static int getOldest(){
		int oldest = 0;
		for(int i = 0; i <frames.length; i++){
			if(frames[i].time > frames[oldest].time){
				oldest = i;
			}
		}
		return oldest;
	}
	
	
}
