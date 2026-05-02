package algorithms.search;

public abstract class AState {
    private String state;
    private double cost;
    private AState cameFrom;

    public AState(String state) {
        this.state = state;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }


    @Override
    public abstract boolean equals(Object obj);

//    @Override
//    public abstract int hashCode();
}
