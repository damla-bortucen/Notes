package ucl.ac.uk.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import ucl.ac.uk.main.Folder;
import ucl.ac.uk.main.Note;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Model
{
    private Folder rootFolder;
    private static final String FILE_PATH = "data/notes.json";
    private Map<String, Map<String, Note>> categories;

    public Model() {
        rootFolder = new Folder();
        rootFolder.setName("Root Index");
        rootFolder.setParentId(null);
        categories = new HashMap<>();
    }

    public void readData()
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(FILE_PATH);

            if (file.exists()) {
                rootFolder = objectMapper.readValue(file, Folder.class);
                System.out.println("Root folder loaded successfully!");
            } else {
                System.out.println("File not found, starting with an empty root folder.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveData()
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(FILE_PATH), rootFolder);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public Folder getRootFolder()
    {
        return rootFolder;
    }

    public Folder getFolder(String folderId)
    {
        return findFolderByDFS(rootFolder, folderId);
    }

    private Folder findFolderByDFS(Folder currFolder, String folderId)
    {
        if (currFolder.getId().equals(folderId)) {
            return currFolder;
        }
        for (Folder subfolder : currFolder.getSubfolders().values()) {

            Folder found = findFolderByDFS(subfolder, folderId);
            if (found != null) {  // Only return if the folder was actually found
                return found;
            }
        }
        return null;
    }

    public Note getNote(String noteId)
    {
        return findNoteByDFS(rootFolder, noteId);
    }

    private Note findNoteByDFS(Folder currFolder, String noteId)
    {
        if (currFolder.getNotes().containsKey(noteId)) {
            return currFolder.getNotes().get(noteId);
        }
        for (Folder subfolder : currFolder.getSubfolders().values())
        {
            return findNoteByDFS(subfolder, noteId);
        }
        return null;
    }

    public void addNote(Note newNote, Folder folder)
    {
        folder.addNote(newNote);
        saveData();
    }

    public void addFolder(Folder newFolder, Folder parentFolder)
    {
        parentFolder.addFolder(newFolder);
        saveData();
    }


    public Map<String, Note> getNotesByCategory(String category) {
        return categories.get(category);
    }
    public Set<String> getAllCategories() {
        return categories.keySet();
    }
}