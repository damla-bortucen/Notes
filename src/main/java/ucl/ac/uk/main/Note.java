package ucl.ac.uk.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Note extends Item
{
    private String content;
    private String dateTime;
    private Set<String> categories;

    public Note() {
        super("", ""); // default values for id and name
        this.content = "";
        this.categories = new HashSet<>();
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Set<String> getCategories()
    {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String category)
    {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    public void removeCategory(String category)
    {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    public String getDateTime()
    {
        return dateTime;
    }

    public void setDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateTime = dateFormat.format(new Date());
    }

}
