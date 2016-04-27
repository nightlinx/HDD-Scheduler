
public class Page {
	int time;
	int value;
	int bit = 1;
	int nextReference = 0;
	
	public Page(int v){
		time = 0;
		value = v;
	}
	
	public String toString(){
		return "Value " + value + ". Time in frame: "+ time + ". bit: "+bit;
		
	}
}
