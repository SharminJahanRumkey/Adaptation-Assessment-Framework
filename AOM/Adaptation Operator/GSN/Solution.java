package GSN;

public class Solution {
    private String identifier;
    private String assessment;

    public Solution() {}

    public Solution(String identifier, String assessment)
    {
        this.identifier = identifier;
        this.assessment = assessment;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getAssessment() {
        return assessment;
    }

}
