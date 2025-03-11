package ucl.ac.uk.main;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Item {
    protected final String id;
    protected String name;
    protected String parentId;

    public Item(String name, String parentId)
    {
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        this.id = dateTime + "-" + name.replaceAll("\\s+", "_");
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
}

