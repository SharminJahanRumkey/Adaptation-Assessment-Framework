package Translation;

import GSN.*;
import SACInstance.*;

import java.util.ArrayList;

public class DAG_Translation {

    private ArrayList<Dep_Rel> dep_rels = new ArrayList<>();


    public DAG_Translation()
    {
        this.dep_rels = new ArrayList<Dep_Rel>();

    }

    public void setDep_rels(ArrayList<Dep_Rel> dep_rels) {
        this.dep_rels = dep_rels;
    }

    public ArrayList<Dep_Rel> getDep_rels() {
        return dep_rels;
    }



    public void getDepRelNodes (String mainGoal, InstantiateSAC SAC) {
        ArrayList<Dep_Rel> reqGoal = new ArrayList<>();
        ArrayList<Dep_Rel> modGoal = new ArrayList<>();
        ArrayList<Dep_Rel> oppGoal = new ArrayList<>();
        ArrayList<Dep_Rel> solution = new ArrayList<>();

        for (SupportedBy supportedBy : SAC.getSupportedByLinks()) {
            // remove startegy node
            for (Strategy strategy : SAC.getStrategies()) {
                if (supportedBy.getSource().trim().equals(mainGoal.trim()) && strategy.getIdentifier().trim().equals(supportedBy.getDestination().trim())) {
                    for (SupportedBy supportedBy1 : SAC.getSupportedByLinks()) {
                        if (supportedBy1.getSource().trim().equals(strategy.getIdentifier().trim())) {
                            Dep_Rel depReqRels = new Dep_Rel(supportedBy1.getDestination().trim(), mainGoal, "req");

                            reqGoal.add(depReqRels);
                        }
                    }

                }
            }

        }


        for (Dep_Rel rel: reqGoal)
        {
            for (SupportedBy supportedBy : SAC.getSupportedByLinks()) {
                // add module goal
                if (supportedBy.getSource().trim().equals(rel.getNodes().trim())) {
                    for(ModuleGoal moduleGoal: SAC.getModuleGoals()) {
                        if(moduleGoal.getIdentifier().trim().equals(supportedBy.getDestination().trim())) {
                            Dep_Rel depReqRels = new Dep_Rel(supportedBy.getDestination().trim(), mainGoal, "mod");

                            modGoal.add(depReqRels);
                        }
                    }

                }
            }
        }


        for (Dep_Rel rel: modGoal)
        {
            for (SupportedBy supportedBy : SAC.getSupportedByLinks()) {
                // remove startegy node
                for (Strategy strategy : SAC.getStrategies()) {
                    if (supportedBy.getSource().trim().equals(rel.getNodes().trim()) && strategy.getIdentifier().trim().equals(supportedBy.getDestination().trim())) {
                        for (SupportedBy supportedBy1 : SAC.getSupportedByLinks()) {
                            if (supportedBy1.getSource().trim().equals(strategy.getIdentifier().trim())) {
                                Dep_Rel depReqRels = new Dep_Rel(supportedBy1.getDestination().trim(), mainGoal, "opp");

                                oppGoal.add(depReqRels);
                            }
                        }

                    }
                }

            }
        }

        for (Dep_Rel rel: oppGoal)
        {
            for (SupportedBy supportedBy : SAC.getSupportedByLinks()) {
                // add module goal
                if (supportedBy.getSource().trim().equals(rel.getNodes().trim())) {

                    Dep_Rel depsolRels = new Dep_Rel(supportedBy.getDestination().trim(), mainGoal, "solution");

                    solution.add(depsolRels);

                }
            }
        }

        for (Dep_Rel rel: reqGoal)
        {
            for (SupportedBy supportedBy : SAC.getSupportedByLinks()) {

                if (supportedBy.getSource().trim().equals(rel.getNodes().trim())) {
                    for(Solution sn: SAC.getSolutions()) {
                        if(sn.getIdentifier().trim().equals(supportedBy.getDestination().trim())) {

                            Dep_Rel depsolRels = new Dep_Rel(supportedBy.getDestination().trim(), mainGoal, "solution1");

                            solution.add(depsolRels);
                        }
                    }

                }
            }
        }

        for (Dep_Rel rel: reqGoal)
        {
            this.dep_rels.add(rel);
        }

        for (Dep_Rel rel1: modGoal)
        {
            this.dep_rels.add(rel1);
        }

        for (Dep_Rel rel2: oppGoal)
        {
            this.dep_rels.add(rel2);
        }

        for (Dep_Rel rel3: solution)
        {
            this.dep_rels.add(rel3);
        }

    }



}
