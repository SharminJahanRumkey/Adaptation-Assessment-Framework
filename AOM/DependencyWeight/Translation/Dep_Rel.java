package Translation;

public class Dep_Rel {
    private String nodes;
    private String mainGoal;
    private String type;

    public Dep_Rel(String nodes, String mainGoal, String type)
    {
        this.mainGoal = mainGoal;
        this.nodes = nodes;
        this.type = type;
    }
    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public void setMainGoal(String mainGoal) {
        this.mainGoal = mainGoal;
    }

    public String getNodes() {
        return nodes;
    }

    public String getMainGoal() {
        return mainGoal;
    }

    public String getType() {
        return type;
    }
}
