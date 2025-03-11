package ucl.ac.uk.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Note extends Item
{
    private String content;
    private String dateTime;
    private List<String> categories;

    public Note() {
        super("", ""); // default values for id and name
        this.content = "";
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
