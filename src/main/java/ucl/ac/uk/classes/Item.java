package ucl.ac.uk.classes;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Item {
    protected final String id;
    protected String name;
    protected String parentId;
    protected String dateTime;

    public Item(String name, String parentId)
    {
        setDateTime();
        this.id = this.dateTime.replaceAll("[-: .]", "");
        this.name = name;
        this.parentId = parentId;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
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

