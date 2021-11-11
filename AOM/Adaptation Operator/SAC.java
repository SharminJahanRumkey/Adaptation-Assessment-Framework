import Planner.*;
import SACInstance.*;
import Operators.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import AssessmentMetric.*;

public class SAC {
    public static void main(String[] args) {
        List<Impact> impactTableList = new ImpactTable("Impact.txt").getImpactList();

        ChangeSet changeSet = new ChangeSet("ChangeSet.txt");

        File file;
        int planID = 0;

        try {

            String content = "";

            File newfile = new File("./PlanRank.txt");
            file = newfile;

            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            for (Plan plan : changeSet.getPlans()) {
                planID++;
                InstantiateSAC assuranceCaseInstance = new InstantiateSAC("AU-5(1).xml");
                assuranceCaseInstance.loadXML();
                for (Changes change : plan.getChangesList()) {
                    if (change.getOperatorType().equals("ChangeVal")) {
                        changeValOperator operation = new changeValOperator(assuranceCaseInstance, impactTableList);
                        operation.EvoluteSACInstance(change);
                    } else if (change.getOperatorType().equals("Support")) {
                        supportOperator operation = new supportOperator(assuranceCaseInstance, impactTableList);
                        operation.EvoluteSACInstance(change);
                    } else if (change.getOperatorType().equals("Substitute")) {
                        SubstituteOperator operation = new SubstituteOperator(assuranceCaseInstance, impactTableList);
                        operation.EvoluteSACInstance(change);
                    }
                }
                CalculateAchievementWeight calAW = new CalculateAchievementWeight();
                calAW.assignAchievementWeight(assuranceCaseInstance);

                InstantiateSAC updateAC = new InstantiateSAC();
                updateAC.generateAssuranceCase("AU-5(1)"+" plan" + plan.getId(), assuranceCaseInstance);
                CalculateSatisficingWeight satisficingWeight = new CalculateSatisficingWeight(assuranceCaseInstance);

                double totalSL = satisficingWeight.calculateSatisficingWeight();
                content = "Plan"+ planID + ":" + totalSL;
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.newLine();
                bw.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



