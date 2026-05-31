package algorithms.search;
import java.io.Serializable;

/**
 * An abstract representation of a state in a searchable problem.
 * Holds information about the state identifier, cost, and the parent state.
 */
public abstract class AState implements Serializable {
    private String state;// Unique identifier for the state
    private double cost;// Total cost accumulated to reach this specific state
    private AState cameFrom;// Reference to the previous state (parent) for path reconstruction

    /**
     * Constructor for AState.
     * @param state a string representation/identifier of the state.
     */
    public AState(String state) {
        this.state = state;
    }

    /**
     * Sets the parent state of this state.
     * @param cameFrom the parent state.
     */
    public void setCameFrom(AState cameFrom) {this.cameFrom = cameFrom;}

    /**
     * Sets the cost to reach this state.
     * @param cost the accumulated cost.
     */
    public void setCost(double cost) {this.cost = cost;}

    /**
     * Returns the parent state.
     * @return the state from which this state was reached.
     */
    public AState getCameFrom() {return cameFrom;}

    /**
     * Returns the string identifier of the state.
     * @return the state ID.
     */
    public String getState() {return state;}

    /**
     * Returns the accumulated cost to reach this state.
     * @return the cost.
     */
    public double getCost() {return cost;}

    /**
     * Abstract method to compare two states and determine if they represent the same position.
     * @param obj the object to compare with.
     * @return true if the states are equal, false otherwise.
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Abstract method to generate a unique hash code for the state.
     * @return the hash code.
     */
    @Override
    public abstract int hashCode();
}
