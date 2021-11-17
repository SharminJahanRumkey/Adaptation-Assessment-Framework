package Calculation;
import GSN.*;
import SACInstance.*;
import Translation.*;

import java.util.ArrayList;

public class DependencyWeight {
    private String nodes;
    private int dependencyWeight;
    private ArrayList<Count> counts = new ArrayList<>();

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getNodes() {
        return nodes;
    }

    public ArrayList<Count> getCounts() {
        return counts;
    }

    public void setCounts(ArrayList<Count> counts) {
        this.counts = counts;
    }

    public int getDependencyWeight() {
        return dependencyWeight;
    }

    public void setDependencyWeight(int dependencyWeight) {
        this.dependencyWeight = dependencyWeight;
    }


    public int actualVal (int n)
    {
        int mult =2;
        int val = 1;
        while (mult <= n)
        {
            val++;
            mult = mult*val;
        }
        return val;
    }
    public void calculateDependencyWeight(ArrayList<Dep_Rel> rels, InstantiateSAC SAC) {
        ArrayList<Count> pairs = new ArrayList<>();
        ArrayList<Count> temp = new ArrayList<>();


        for (Dep_Rel rel : rels) {
            for (Dep_Rel rel1 : rels) {
                if (!rel.getMainGoal().equals(rel1.getMainGoal()) && rel.getNodes().equals(rel1.getNodes())) {
                    Count p = new Count(rel.getNodes());
                    pairs.add(p);

                }
            }
        }


        for (Count r : pairs) {
            int n = 0;
            for (Count t : pairs) {
                if (r.getNodes().equals(t.getNodes())) {
                    n++;
                }
            }

            int v = actualVal(n);

            Count fin = new Count(r.getNodes());
            fin.setCount(v);
            this.counts.add(fin);
            temp.add(fin);
        }

        for (SupportedBy supportedBy:SAC.getSupportedByLinks())
        {
            for(Count c: temp)
            {
                for(OppGoal oppGoal: SAC.getOppGoals())
                {

                    if(supportedBy.getDestination().equals(c.getNodes()) && supportedBy.getSource().equals(oppGoal.getIdentifier()))
                    {
                        for (Count cc: temp)
                        {
                            if(cc.getNodes().equals(oppGoal.getIdentifier()) && cc.getCount() < c.getCount())
                            {
                                cc.setCount(oppGoal.getIdentifier(),c.getCount());
                            }
                            else
                            {
                                Count opp = new Count(oppGoal.getIdentifier());
                                opp.setCount(c.getCount());
                                this.counts.add(opp);

                            }
                        }
                    }
                }
            }
        }
    }
}

