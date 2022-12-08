package ReplayMemory;

public class Experience {
    public final double[] state;
    public final double reward;
    public final double[] next_state;

    public Experience(double[] state, double reward, double[] next_state){
        this.state = state;
        this.reward = reward;
        this.next_state = next_state;
    }

    public double[] getState() {
        return state;
    }

    public double getReward() {
        return reward;
    }

    public double[] getNext_state() {
        return next_state;
    }
}
