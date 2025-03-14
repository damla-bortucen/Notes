package ucl.ac.uk.main;

public class Text extends Content {
    private String text;
    public Text() {
        super("text");
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
