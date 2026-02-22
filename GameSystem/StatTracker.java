package GameSystem;

public class StatTracker {
    private double previous;
    private double current;
    private double best;

    public StatTracker(double prev, double curr, double best){
        this.previous = prev;
        this.current = curr;
        this.best = best;
    }

    public double getPrevious(){
        return this.previous;
    }

    public double getCurrent(){
        return this.current;
    }

    public double getBest(){
        return this.best;
    }

    private void setCurrent(double curr){
        this.current = curr;
    }

    public void resetCurr(){
        setCurrent(0);
    }

    public void updateCurr(double value){
        this.current += value;
    }

    public void updateByDay(){
        this.previous = this.current;
        this.best = Math.max(best, this.current);
        this.current = 0;
    }
}
