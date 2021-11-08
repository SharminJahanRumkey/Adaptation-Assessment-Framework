public class ProvidesRequires extends SupportedBy {

    private String provisionSet;

    public ProvidesRequires(){}
    public ProvidesRequires(String source, String destination,  String provisionSet){
        super(source, destination);

        this.provisionSet = provisionSet;
    }

    @Override
    public void setSource(String source) {
        super.setSource(source);
    }

    @Override
    public void setDestination(String destination) {
        super.setDestination(destination);
    }



    public void setProvisionSet(String provisionSet) {
        this.provisionSet = provisionSet;
    }

    @Override
    public String getSource() {
        return super.getSource();
    }

    @Override
    public String getDestination() {
        return super.getDestination();
    }


    public String getProvisionSet() {
        return provisionSet;
    }
}
