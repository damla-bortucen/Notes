package ucl.ac.uk.main;

public class Image extends Content{
    private String imagePath;
    private String description;

    public Image() {
        super("image");
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
