
public class TeamELORate{
	 
	private int rate;
	private int formerRate;
	private int k;
	 
	
	public TeamELORate(){
		
	}
	
	public TeamELORate(int initRate){
		this.rate = initRate;
	}
	public int getRate (){
		return this.rate;
	}
	
	public int setRate(int rate){
		this.rate = rate;
	}

	public int getFormerRate(){
		return this.formerRate;
	}
	
	public void syncRate(){
		formerRate = rate;
	}
	public void setK(int kValue){
		if(k>=0&& k<=100){
			this.k = kValue;
		}
	}
	
	public int getK(){
		return this.k;
	}
	
	public void updateRate(int oppentRate,Result result){
		adjustK();
		
		rate = formerRate + k * (result.weight - getWinRate(rate,oppentRate)); 
	}
	
	
	private double getWinRate(int aRate,int bRate,int param =400){
		if(param<100||param>1000)param = 400;
		return 1/(1+Math.pow(10,(bRate-aRate)/param));
	}
	

	
	private void adjustK(){
		if(rate < 1000){
			k=40;
		}else if(rate < 2000){
			k=20;
		}else{
			k=10;
		}
	}
	
	public static void main(String[] args){
		TeamELORate a(1000),b(1000);
		//a:b = 0:0
		a.syncRate();
		b.syncRate();
		a.updateRate(b.getFormerRate(),DRAW);
		b.updateRate(a.getFormerRate(),DRAW);
		System.out.println(a.getRate());
		System.out.println(b.getRate());
		
		//a:b = 3:0
		a.syncRate();
		b.syncRate();
		a.updateRate(b.getFormerRate(),WIN);
		b.updateRate(a.getFormerRate(),LOSS);
		System.out.println(a.getRate());
		System.out.println(b.getRate());
		
		//a:b = 0:3
		a.syncRate();
		b.syncRate();
		a.updateRate(b.getFormerRate(),LOSS);
		b.updateRate(a.getFormerRate(),WIN);
		System.out.println(a.getRate());
		System.out.println(b.getRate());		
	}

}

enum Result {
	WIN(1),DRAW(0.5),LOSS(0);
	private double weight;
	Result(double weight){
		this.weight = weight;
	}
	public double getWeight(){
		return this.weight;
	}
} 
