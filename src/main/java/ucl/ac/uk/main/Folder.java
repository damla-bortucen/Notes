package ucl.ac.uk.main;

import java.util.HashMap;
import java.util.Map;

public class Folder extends Item
{
    private Map<String, Folder> subfolders; // <id, folder>
    private Map<String, Note> notes; // <id, note>

    public Folder() // default constructor
    {
        super("", "");
        this.subfolders = new HashMap<>();
        this.notes = new HashMap<>();
    }
    public Folder(String id, String name)
    {
        super(id, name);
        this.subfolders = new HashMap<>();
        this.notes = new HashMap<>();
    }

    public void addFolder(Folder folder)
    {
        subfolders.put(folder.getId(), folder);
    }

    public void removeFolder(String folderId)
    {
        subfolders.remove(folderId);
    }

    public void addNote(Note note)
    {
        notes.put(note.getId(), note);
    }

    public void removeNote(String noteId)
    {
        notes.remove(noteId);
    }

    public Map<String, Folder> getSubfolders()
    {
        return subfolders;
    }

    public Map<String, Note> getNotes()
    {
        return notes;
    }

}
