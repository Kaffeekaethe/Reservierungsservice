package reservierung.domainmodel;

public class Platz {
	private int zug_id;
	private int platz_id;
	private int status;
	
	public Platz(int zug_id, int platz_id, int status){
		this.zug_id = zug_id;
		this.platz_id = platz_id;
		this.status = status;
	}
	
	public int getZugID(){
		return this.zug_id;
	}
	
	public int getPlatzID(){
		return this.platz_id;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public String toString(){
		return String.format("zug_id: %d, platz_id: %d, status: %d", zug_id, platz_id, status);
	}

}
