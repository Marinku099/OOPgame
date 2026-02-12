package GameSystem;

public class StatTracker {
    private int previous;
    private int current;
    private int best;

    public StatTracker(int prev, int curr, int best){
        this.previous = prev;
        this.current = curr;
        this.best = best;
    }

    public int getPrevious(){
        return this.previous;
    }

    public int getCurrent(){
        return this.current;
    }

    public int getBest(){
        return this.best;
    }

    public void setPrevious(int prev){
        this.previous = prev;
    }

    public void setCurrent(int curr){
        this.current = curr;
    }

    public void updateBest(int newValue){
        this.best = Math.max(best, newValue);
    }
}
