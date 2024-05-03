package src.utils;

import java.util.*;

public class Result {
    private List<String> solution;
    private int numOfVisitedNodes;

    public Result(List<String> solution, int numOfVisitedNodes) {
        this.solution = solution;
        this.numOfVisitedNodes = numOfVisitedNodes;
    }

    public List<String> getSolution() {
        return this.solution;
    }

    public int getNumOfVisitedNodes() {
        return this.numOfVisitedNodes;
    }
}