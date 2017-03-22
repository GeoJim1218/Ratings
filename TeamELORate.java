/**
 * Created by gaoji on 2017/3/22.
 */
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

    public void setRate(int rate){
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

    public void updateRate(int opponentRate,Result result){
        adjustK();

        rate = (int)(formerRate + k * (result.getWeight() - getWinRate(rate,opponentRate,400)));
    }


    private double getWinRate(int aRate,int bRate,int param){
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
        TeamELORate a = new TeamELORate(1000);
        TeamELORate b = new TeamELORate(1000);
        //a:b = 0:0
        a.syncRate();
        b.syncRate();
        a.updateRate(b.getFormerRate(),Result.DRAW);
        b.updateRate(a.getFormerRate(),Result.DRAW);
        System.out.println(a.getRate());
        System.out.println(b.getRate());

        //a:b = 3:0
        a.syncRate();
        b.syncRate();
        a.updateRate(b.getFormerRate(),Result.WIN);
        b.updateRate(a.getFormerRate(),Result.LOSS);
        System.out.println(a.getRate());
        System.out.println(b.getRate());

        //a:b = 0:3
        a.syncRate();
        b.syncRate();
        a.updateRate(b.getFormerRate(),Result.LOSS);
        b.updateRate(a.getFormerRate(),Result.WIN);
        System.out.println(a.getRate());
        System.out.println(b.getRate());
    }

}

enum Result {
    WIN(1),DRAW(0.5),LOSS(0);
    private double weight;
    private Result(double weight){
        this.weight = weight;
    }
    public double getWeight(){
        return this.weight;
    }
}
