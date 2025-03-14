package ucl.ac.uk.main;

public abstract class Content {
    protected String type;
    protected Content(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
