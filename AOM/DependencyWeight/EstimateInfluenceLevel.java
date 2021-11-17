import Calculation.Count;
import SACInstance.*;
import GSN.*;
import Translation.DAG_Translation;
import Translation.Dep_Rel;
import Calculation.*;

import java.util.ArrayList;

public class EstimateInfluenceLevel {
    public static void main(String[] args) {
        System.out.println("hi");
        InstantiateSAC assuranceCaseInstance = new InstantiateSAC("AU-5(1).xml");
        assuranceCaseInstance.loadXML();
        DAG_Translation dag_translation = new DAG_Translation();

        for (MainGoal mainGoal: assuranceCaseInstance.getMainGoals()) {

            dag_translation.getDepRelNodes(mainGoal.getIdentifier().trim(), assuranceCaseInstance);
        }


        DependencyWeight dependencyWeight = new DependencyWeight();
        dependencyWeight.calculateDependencyWeight(dag_translation.getDep_rels(), assuranceCaseInstance);
        for (Count count: dependencyWeight.getCounts())
        {
            System.out.println( count.getNodes() + " "+ count.getCount());
        }

    }
}
