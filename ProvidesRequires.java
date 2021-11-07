public class ProvidesRequires extends SupportedBy {
    private String providesProvisionSet;
    private String requiresProvisionSet;

    public ProvidesRequires(){}
    public ProvidesRequires(String source, String destination, String providesProvisionSet, String requiresProvisionSet){
        super(source, destination);
        this.providesProvisionSet = providesProvisionSet;
        this.requiresProvisionSet = requiresProvisionSet;
    }

    @Override
    public void setSource(String source) {
        super.setSource(source);
    }

    @Override
    public void setDestination(String destination) {
        super.setDestination(destination);
    }

    public void setProvidesProvisionSet(String providesProvisionSet) {
        this.providesProvisionSet = providesProvisionSet;
    }

    public void setRequiresProvisionSet(String requiresProvisionSet) {
        this.requiresProvisionSet = requiresProvisionSet;
    }

    @Override
    public String getSource() {
        return super.getSource();
    }

    @Override
    public String getDestination() {
        return super.getDestination();
    }

    public String getProvidesProvisionSet() {
        return providesProvisionSet;
    }

    public String getRequiresProvisionSet() {
        return requiresProvisionSet;
    }
}
