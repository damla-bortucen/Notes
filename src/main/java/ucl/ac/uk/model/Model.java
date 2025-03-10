package ucl.ac.uk.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import ucl.ac.uk.main.Folder;
import ucl.ac.uk.main.Note;

import java.io.File;
import java.io.IOException;

public class Model
{
    private Folder rootFolder;
    private static final String FILE_PATH = "data/notes.json";

    public Model() {
        rootFolder = new Folder("root", "Root Index");
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
        for (Folder subfolder : currFolder.getSubfolders().values())
        {
            return findFolderByDFS(subfolder, folderId);
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
}