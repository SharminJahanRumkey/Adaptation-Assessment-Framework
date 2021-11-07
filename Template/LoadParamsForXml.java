import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LoadParamsForXml {

    public LoadParamsForXml(Document document, Element root, InstantiateXml instantiateXml) {
        instantiateXml.CollectContextNodes();
        instantiateXml.CollectSolutionNodes();
        instantiateXml.CollectGoalNodes();
        instantiateXml.CollectStrategyNodes();
        instantiateXml.CollectInContextOfLinks();
        instantiateXml.CollectSupportedByLinks();

        for (Context c : instantiateXml.getContextList()) {
            Element Context = document.createElement("Context");
            root.appendChild(Context);

            Attr id = document.createAttribute("identifier");
            id.setValue(c.getIdentifier());
            Context.setAttributeNode(id);

            Attr assignmentTailoring = document.createAttribute("tailoring");
            assignmentTailoring.setValue(c.getTailoring());
            Context.setAttributeNode(assignmentTailoring);

            Attr Statement = document.createAttribute("statement");
            String stmt = "Context: " + c.getIdentifier() + ": Assignment: " + c.getTailoring();
            Statement.setValue(stmt);
            Context.setAttributeNode(Statement);
        }

        for (Solution c : instantiateXml.getSolutionList()) {
            Element solution = document.createElement("Solution");
            root.appendChild(solution);

            Attr id = document.createAttribute("identifier");
            id.setValue(c.getIdentifier());
            solution.setAttributeNode(id);

            Attr assignmentTailoring = document.createAttribute("assessment");
            assignmentTailoring.setValue(c.getAssessment());
            solution.setAttributeNode(assignmentTailoring);

            Attr Statement = document.createAttribute("statement");
            String stmt = "Solution: " + c.getIdentifier() + ": " + c.getAssessment();
            Statement.setValue(stmt);
            solution.setAttributeNode(Statement);
        }

        for (Goal g : instantiateXml.getGoalList()) {
            if (g instanceof MainGoal) {
                Element goal = document.createElement("MainGoal");
                root.appendChild(goal);

                Attr family = document.createAttribute("family");
                family.setValue(((MainGoal) g).getFamily());
                goal.setAttributeNode(family);

                Attr number = document.createAttribute("number");
                number.setValue(((MainGoal) g).getNumber());
                goal.setAttributeNode(number);

                Attr identifier = document.createAttribute("identifier");
                identifier.setValue(((MainGoal) g).getIdentifier());
                goal.setAttributeNode(identifier);

                Attr actor = document.createAttribute("actor");
                actor.setValue(((MainGoal) g).getActor());
                goal.setAttributeNode(actor);

                Attr action = document.createAttribute("control_action");
                action.setValue(((MainGoal) g).getControlAction());
                goal.setAttributeNode(action);

                Attr baselineAllocation = document.createAttribute("baseline_allocation");
                baselineAllocation.setValue(((MainGoal) g).getBaselineAllocation());
                goal.setAttributeNode(baselineAllocation);

                Attr provisionSet = document.createAttribute("provision_set");
                provisionSet.setValue(((MainGoal) g).getProvisionSet());
                goal.setAttributeNode(provisionSet);


                Attr achievemnetweight = document.createAttribute("achievement_weight");
                achievemnetweight.setValue(((MainGoal) g).getAchievementWeight());
                goal.setAttributeNode(achievemnetweight);

                Attr Statement = document.createAttribute("statement");
                String stmt = "Goal: " + ((MainGoal) g).getFamily() + "-" + ((MainGoal) g).getNumber() + " " + g.getIdentifier() + " Main: " +
                        ((MainGoal) g).getActor() + " " + ((MainGoal) g).getControlAction() + "; Impact = " + ((MainGoal) g).getBaselineAllocation() +
                        ", Provides= [ " + ((MainGoal) g).getProvisionSet() + "]; aw = " + g.getAchievementWeight();
                Statement.setValue(stmt);
                goal.setAttributeNode(Statement);
            } else if (g instanceof ReqGoal) {
                Element goal = document.createElement("ReqGoal");
                root.appendChild(goal);

                Attr identifier = document.createAttribute("identifier");
                identifier.setValue(g.getIdentifier());
                goal.setAttributeNode(identifier);

                Attr assessmentProcess = document.createAttribute("assessment_process");
                assessmentProcess.setValue(((ReqGoal) g).getAssessmentProcess());
                goal.setAttributeNode(assessmentProcess);

                Attr funcReq = document.createAttribute("functional_requirement");
                funcReq.setValue(((ReqGoal) g).getFunctionalReq());
                goal.setAttributeNode(funcReq);

                Attr achievemnetweight = document.createAttribute("achievement_weight");
                achievemnetweight.setValue(g.getAchievementWeight());
                goal.setAttributeNode(achievemnetweight);

                Attr Statement = document.createAttribute("statement");
                String stmt = "Goal: " + g.getIdentifier() + ": " + ((ReqGoal) g).getAssessmentProcess() + " maintains " +
                        ((ReqGoal) g).getFunctionalReq() + "; aw = " + g.getAchievementWeight();
                Statement.setValue(stmt);
                goal.setAttributeNode(Statement);

            } else if (g instanceof ModuleGoal) {
                Element goal = document.createElement("ModuleGoal");
                root.appendChild(goal);

                Attr identifier = document.createAttribute("identifier");
                identifier.setValue(g.getIdentifier());
                goal.setAttributeNode(identifier);

                Attr assessmentProcess = document.createAttribute("assessment_process");
                assessmentProcess.setValue(((ModuleGoal) g).getAssessmentProcess());
                goal.setAttributeNode(assessmentProcess);

                Attr achievemnetweight = document.createAttribute("achievement_weight");
                achievemnetweight.setValue(g.getAchievementWeight());
                goal.setAttributeNode(achievemnetweight);

                Attr Statement = document.createAttribute("statement");
                String stmt = "Module: " + g.getIdentifier() + " " + ": Argument over operational subgoal(s) of " +
                        ((ModuleGoal) g).getAssessmentProcess() + "; aw = " + g.getAchievementWeight();
                Statement.setValue(stmt);
                goal.setAttributeNode(Statement);

            } else if (g instanceof OppGoal) {

                Element goal = document.createElement("OppGoal");
                root.appendChild(goal);

                Attr identifier = document.createAttribute("identifier");
                identifier.setValue(g.getIdentifier());
                goal.setAttributeNode(identifier);

                Attr variables = document.createAttribute("variable");
                variables.setValue(((OppGoal) g).getVariable());
                goal.setAttributeNode(variables);

                Attr con = document.createAttribute("condition");
                con.setValue(((OppGoal) g).getCondition());
                goal.setAttributeNode(con);

                Attr achievemnetweight = document.createAttribute("achievement_weight");
                achievemnetweight.setValue(g.getAchievementWeight());
                goal.setAttributeNode(achievemnetweight);

                Attr Statement = document.createAttribute("statement");
                String stmt = "Goal: " + g.getIdentifier() + ": Variables = " +
                        ((OppGoal) g).getVariable() + "; Condition = " + ((OppGoal) g).getCondition() + "; aw = " + g.getAchievementWeight();
                Statement.setValue(stmt);
                goal.setAttributeNode(Statement);
            }
        }

        for (Strategy c : instantiateXml.getStrategyList()) {
            Element strategy = document.createElement("Strategy");
            root.appendChild(strategy);

            Attr identifier = document.createAttribute("identifier");
            identifier.setValue(c.getIdentifier());
            strategy.setAttributeNode(identifier);

            Attr assessmentProcess = document.createAttribute("assessment_Process");
            assessmentProcess.setValue(c.getAssessmentProcess());
            strategy.setAttributeNode(assessmentProcess);

            Attr Statement = document.createAttribute("statement");
            String stmt = "Strategy: " + c.getIdentifier()+": Argument over "+ c.getAssessmentProcess();
            Statement.setValue(stmt);
            strategy.setAttributeNode(Statement);
        }

        for (InContextOf c : instantiateXml.getInContextOfLinks()) {
            Element inContextOf = document.createElement("InContextOf");
            root.appendChild(inContextOf);

            Attr source = document.createAttribute("source");
            source.setValue(c.getSource());
            inContextOf.setAttributeNode(source);

            Attr dest = document.createAttribute("destination");
            dest.setValue(c.getDestination());
            inContextOf.setAttributeNode(dest);
        }

        for (SupportedBy c : instantiateXml.getSupportedByLinks()) {
            Element supportedBy = document.createElement("SupportedBy");
            root.appendChild(supportedBy);

            Attr source = document.createAttribute("source");
            source.setValue(c.getSource());
            supportedBy.setAttributeNode(source);

            Attr dest = document.createAttribute("destination");
            dest.setValue(c.getDestination());
            supportedBy.setAttributeNode(dest);

            if (c instanceof ProvidesRequires){
                Attr provids = document.createAttribute("Provides");
                provids.setValue(((ProvidesRequires) c).getProvidesProvisionSet());
                supportedBy.setAttributeNode(provids);

                Attr req = document.createAttribute("Requires");
                req.setValue(((ProvidesRequires) c).getRequiresProvisionSet());
                supportedBy.setAttributeNode(req);
            }
        }
    }
}

