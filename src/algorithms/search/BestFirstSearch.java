package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{
    public BestFirstSearch() {
        // אנחנו דורסים את ה-openList של BFS ב-PriorityQueue
        // הוא יסדר את המצבים מהעלות (Cost) הנמוכה ביותר לגבוהה ביותר
        this.openList = new PriorityQueue<>((s1, s2) -> Double.compare(s1.getCost(), s2.getCost()));
    }

    @Override
    public String getName() { return "Best First Search"; }
}
