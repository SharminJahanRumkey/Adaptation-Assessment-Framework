package Operators;


import SACInstance.*;
import GSN.OppGoal;
import GSN.Solution;
import GSN.SupportedBy;
import Planner.Changes;
import Planner.Impact;

import java.util.List;

public class SubstituteOperator {

    private InstantiateSAC assuranceCaseInstance;
    private List<Impact> impactTableList;

    public SubstituteOperator(InstantiateSAC assuranceCaseInstance,List<Impact> impactTableList) {
        this.assuranceCaseInstance = assuranceCaseInstance;
        this.impactTableList = impactTableList;
    }

    public void EvoluteSACInstance(Changes changes) {
        if (changes.getOperatorType().equals("Substitute")) {
            String stVar = changes.getStateVar().trim();
            String newSt = changes.getNewState().trim();
            String newFunc = changes.getChangeCond().getNewFunc().trim();
            String evidence = changes.getEvidence().trim();

            double impact = -1.0;

            for (Impact impactTable : this.impactTableList) {

                String prov = impactTable.getProvision().trim();
                String cond = impactTable.getCondition().trim();

                if (prov.equals(stVar) && cond.equals(newSt)) {
                    impact = impactTable.getImpact();
                    break;
                }
            }

            for (OppGoal oppGoal : this.assuranceCaseInstance.getOppGoals()) {
                if (oppGoal.getVariable().equals(stVar)) {
                    if (impact >= 0.0) {
                        oppGoal.setCondition(newSt);
                        oppGoal.setAchievementWeight(String.valueOf(impact));
                    }
                }
                for (SupportedBy supportedbyLinks : this.assuranceCaseInstance.getSupportedByLinks()) {
                    if(supportedbyLinks.getSource().equals(oppGoal.getIdentifier()) )
                    {
                        String solID = supportedbyLinks.getDestination();
                        for (Solution solution: this.assuranceCaseInstance.getSolutions()) {
                            if(solution.getIdentifier().equals(solID)){
                                solution.setAssessment(evidence);
                            }
                        }
                    }
                }
            }
        }
    }

}

