package algorithms.search;

public abstract class AState {
    private String state;// Unique identifier for the state
    private double cost;// Total cost accumulated to reach this specific state
    private AState cameFrom;// Reference to the previous state (parent) for path reconstruction

    public AState(String state) {
        this.state = state;
    }
    // Setters - used by the search algorithms to update the state during discovery
    public void setCameFrom(AState cameFrom) {this.cameFrom = cameFrom;}
    public void setCost(double cost) {this.cost = cost;}

    // Getters - used to retrieve information about the state or the path
    public AState getCameFrom() {return cameFrom;}
    public String getState() {return state;}
    public double getCost() {return cost;}

    // Abstract method to compare two states and determine if they represent the same position
    @Override
    public abstract boolean equals(Object obj);

    // Abstract method to generate a unique hash code for the state
    @Override
    public abstract int hashCode();
}
