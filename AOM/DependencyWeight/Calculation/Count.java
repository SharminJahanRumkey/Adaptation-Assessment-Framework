package Calculation;
import GSN.*;
import SACInstance.*;
import Translation.*;

import java.util.ArrayList;


public class Count {
    private String nodes;
    private int count;

    public Count(String nodes)
    {
        this.nodes = nodes;
        this.count = 1;
    }
    public String getNodes() {
        return nodes;
    }

    public int getCount() {
        return count;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCount(String nodes, int count) {
        if(this.nodes.equals(nodes)) {
            this.count = count;
        }
    }



}
