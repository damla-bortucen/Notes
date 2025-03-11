package ucl.ac.uk.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ucl.ac.uk.main.Folder;
import ucl.ac.uk.main.Note;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Model
{
    private Folder rootFolder;
    private static final String FILE_PATH = "data/notes.json";
    private static final String CATEGORIES_FILE_PATH = "data/categories.json";
    private Map<String, Set<String>> categories; // category -> noteID

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
            File categoriesFile = new File(CATEGORIES_FILE_PATH);

            if (file.exists()) {
                rootFolder = objectMapper.readValue(file, Folder.class);
                System.out.println("Root folder loaded successfully!");
            } else {
                System.out.println("File not found, starting with an empty root folder.");
            }

            if (categoriesFile.exists()) {
                categories = objectMapper.readValue(categoriesFile, new TypeReference<>() {});
                System.out.println("Categories loaded successfully!");
            } else {
                System.out.println("Categories file not found, starting fresh.");
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
            objectMapper.writeValue(new File(CATEGORIES_FILE_PATH), categories);
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

        for (Folder subfolder : currFolder.getSubfolders().values()) {
            Note foundNote = findNoteByDFS(subfolder, noteId);
            if (foundNote != null) {
                return foundNote;
            }
        }
        return null;
    }

    public void createNote(String noteName, String noteContent, String folderId)
    {
        Folder folder = getFolder(folderId);
        if (folder != null) {
            Note newNote = new Note();
            newNote.setName(noteName);
            newNote.setContent(noteContent);
            newNote.setParentId(folderId);
            // TODO currently doesnt do anything with categories
            folder.addNote(newNote);
            saveData();
        }
    }

    public void addFolder(Folder newFolder, Folder parentFolder)
    {
        parentFolder.addFolder(newFolder);
        saveData();
    }

    public void updateNote(String noteId, String name, String content)
    {
        // TODO currently doesnt do anything with categories
        Note note = getNote(noteId);

        note.setName(name);
        note.setContent(content);

        saveData();
    }

    public void deleteNote(String noteId) {
        Note noteToDelete = getNote(noteId);

        if (noteToDelete != null) {
            Folder parentFolder = getFolder(noteToDelete.getParentId());
            if (parentFolder != null) {
                parentFolder.removeNote(noteId);
            }
            // remove from categories
            for (String category : categories.keySet()) {
                categories.get(category).remove(noteId);
            }
        }
        saveData();
    }

    public void deleteFolder(String folderId) {
        Folder folderToDelete = getFolder(folderId);

        if (folderToDelete != null) {
            Folder parentFolder = getFolder(folderToDelete.getParentId());

            for (String noteId : folderToDelete.getNotes().keySet()) {
                deleteNote(noteId);
            }

            if (parentFolder != null) {
                parentFolder.removeFolder(folderId);
                saveData();
            }
        }
    }

    public Set<String> getNoteIdsByCategory(String category)
    {
        return categories.get(category);
    }
    public Set<String> getAllCategories()
    {
        return categories.keySet();
    }

    public void addCategory(String name) {
        if (getAllCategories().contains(name)) {
            System.out.println("Category already exists: " + name);
        } else {
            categories.put(name, new HashSet<>());
            saveData();
        }
    }


}