package ucl.ac.uk.main;

import java.util.ArrayList;
import java.util.List;

public class Note extends Item
{
    private String content;
    private List<String> categories;

    public Note() {
        super("", ""); // default values for id and name
        this.content = "";
    }

    public Note(String id, String name, String content)
    {
        super(id, name);
        this.content = content;
        this.categories = new ArrayList<>();
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public List<String> getCategories()
    {
        return categories;
    }

    public void addCategory(String category)
    {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

}
