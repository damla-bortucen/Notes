package ucl.ac.uk.classes;

public class Image {
    private String imagePath;
    private String imageName;

    public Image() {
        this.imagePath = "";
        this.imageName = "";
    }

    public Image(String imageName, String imagePath) {
        this.imagePath = imagePath;
        this.imageName = imageName;
    }

    public String getImagePath() {return imagePath;}

    public void setImagePath(String imagePath) {this.imagePath = imagePath;}

    public String getImageName() {return imageName;}

    public void setImageName(String imageName) {this.imageName = imageName;}
}
