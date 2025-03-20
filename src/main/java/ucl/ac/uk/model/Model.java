package ucl.ac.uk.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Part;
import ucl.ac.uk.classes.Folder;
import ucl.ac.uk.classes.Note;
import ucl.ac.uk.classes.Image;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Model
{
    private Folder rootFolder;
    private static final String FILE_PATH = "data/notes.json";
    private static final String CATEGORIES_FILE_PATH = "data/categories.json";
    private static final String IMAGES_PATH = "src/main/webapp/";
    private Set<String> categories; // category -> noteID

    public Model() {
        rootFolder = new Folder();
        rootFolder.setName("Root Index");
        rootFolder.setParentId(null);
        categories = new HashSet<>();
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

    public void createNote(String noteName, String noteContent, String folderId, Set<String> categories, Part imagePart)
    {
        Folder folder = getFolder(folderId);
        System.out.println(folder);
        if (folder != null) {
            Note newNote = new Note();
            newNote.setName(noteName);
            newNote.setContent(noteContent);
            newNote.setParentId(folderId);
            newNote.setCategories(categories);
            System.out.println(newNote);

            if (imagePart!=null && imagePart.getSize() > 0) {
                Image image = saveImage(imagePart);
                newNote.setImage(image);
            }

            folder.addNote(newNote);
            saveData();
        }
    }

    private Image saveImage(Part imagePart)
    {
        try {
            String imageName = imagePart.getSubmittedFileName();

            String absoluteDir = new File(IMAGES_PATH).getAbsolutePath();

            String filePath = absoluteDir + File.separator + imageName;
            imagePart.write(filePath);

            Image image = new Image(imageName, filePath);
            return (image);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving image" + e.getMessage());
        }
        return null;
    }

    public void addFolder(Folder newFolder, Folder parentFolder)
    {
        parentFolder.addFolder(newFolder);
        saveData();
    }

    public void updateNote(String noteId, String name, String content, Set<String> newCategories, Part imagePart)
    {
        Note note = getNote(noteId);

        note.setName(name);
        note.setContent(content);

        if (newCategories != null) {
            note.getCategories().clear();
            note.setCategories(newCategories);
        }

        if (imagePart != null && imagePart.getSize() > 0) {
            Image image = saveImage(imagePart);
            // delete old image
            removeImage(note);
            note.setImage(image);
        }

        saveData();
    }

    public void removeImage(Note note) {
        Image oldImage = note.getImage();
        if (oldImage != null) {
            File oldImageFile = new File(oldImage.getImagePath());
            if (oldImageFile.exists()) {
                oldImageFile.delete();
                note.setImage(null);
            }
        }
    }

    public void deleteNote(String noteId) {
        Note noteToDelete = getNote(noteId);

        if (noteToDelete != null) {
            Folder parentFolder = getFolder(noteToDelete.getParentId());
            if (parentFolder != null) {
                parentFolder.removeNote(noteId);
            }
            if (noteToDelete.getImage() != null) {
                removeImage(noteToDelete);
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

    public void updateFolder(Folder folder, String newName) {
        folder.setName(newName);
        saveData();
    }

    public Set<Note> getNotesByCategory(String category)
    {
        Set<Note> notesInCategory = new HashSet<>();
        findNotesInCategory(rootFolder, category, notesInCategory);
        return notesInCategory;
    }

    private void findNotesInCategory(Folder folder, String category, Set<Note> result)
    {
        for (Note note : folder.getNotes().values()) {
            if (note.getCategories().contains(category)) {
                result.add(note);
            }
        }
        for (Folder subfolder : folder.getSubfolders().values()) {
            findNotesInCategory(subfolder, category, result);
        }
    }

    public Set<String> getCategories()
    {
        return categories;
    }

    public void addCategory(String category)
    {
        if (categories.contains(category)) {
            System.out.println("Category already exists: " + category);
        } else {
            categories.add(category);
            saveData();
        }
    }

    public void deleteCategory(String categoryName)
    {
        if (categories.contains(categoryName)) {
            categories.remove(categoryName);
        }

        // remove category from the root folder's notes
        for (Note note : rootFolder.getNotes().values()) {
            if (note.getCategories().contains(categoryName)) {
                note.removeCategory(categoryName);
            }
        }

        // remove category from all notes in subfolders
        for (Folder folder : rootFolder.getSubfolders().values()) {
            for (Note note : folder.getNotes().values()) {
                if (note.getCategories().contains(categoryName)) {
                    note.removeCategory(categoryName);
                }
            }
        }
        saveData();
    }

    public List<Note> searchNotes(String searchTerm)
    {
        List<Note> resultNotes = new ArrayList<>();
        searchTerm = searchTerm.toLowerCase();

        searchNotesRecursive(rootFolder, searchTerm, resultNotes);
        return resultNotes;
    }

    private void searchNotesRecursive(Folder folder, String searchTerm, List<Note> resultNotes)
    {
        for (Note note : folder.getNotes().values())
        {
            if (note.getName().toLowerCase().contains(searchTerm)
                    || note.getContent().toLowerCase().contains(searchTerm)
                    || categorySearch(note.getCategories(), searchTerm)) {
                resultNotes.add(note);
            }
        }

        // Recursively search in subfolders
        for (Folder subfolder : folder.getSubfolders().values())
        {
            searchNotesRecursive(subfolder, searchTerm, resultNotes);
        }
    }

    public List<Folder> searchFolders(String searchTerm)
    {
        List<Folder> resultFolders = new ArrayList<>();
        searchTerm = searchTerm.toLowerCase();

        searchFoldersRecursive(rootFolder, searchTerm, resultFolders);
        return resultFolders;
    }

    private void searchFoldersRecursive(Folder folder, String searchTerm, List<Folder> resultFolders)
    {
        for (Folder subfolder : folder.getSubfolders().values()) {
            if (subfolder.getName().toLowerCase().contains(searchTerm)) {
                resultFolders.add(subfolder);
            }
            // search deeper subfolders
            searchFoldersRecursive(subfolder, searchTerm, resultFolders);
        }
    }

    public boolean categorySearch(Set<String> categories, String searchTerm)
    {
        if (categories != null) {
            for (String category : categories) {
                if (category.toLowerCase().contains(searchTerm)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Note> sortNotes(String sort, Map<String, Note> notes)
    {
        Comparator<Note> compareByName = (Note n1, Note n2) -> (n1.getName().toLowerCase()).compareTo(n2.getName().toLowerCase());
        Comparator<Note> compareByDate = (Note n1, Note n2) -> n1.getDateTime().compareTo(n2.getDateTime());

        List<Note> notesList = new ArrayList<>(notes.values());

        if (sort != null)
        {
            if (sort.equals("alpha")) {
                Collections.sort(notesList, compareByName);
            } else if (sort.equals("newest")) {
                // ascending order - smallest to largest (oldest to newest) so REVERSE
                Collections.sort(notesList, compareByDate);
                Collections.reverse(notesList);
            } else {
                // ascending -> oldest to newest
                Collections.sort(notesList, compareByDate);
            }
        }
        return notesList;
    }

    public List<Folder> sortFolders(String sort, Map<String, Folder> folders)
    {
        Comparator<Folder> compareByName = (Folder f1, Folder f2) -> (f1.getName().toLowerCase()).compareTo(f2.getName().toLowerCase());
        Comparator<Folder> compareByDate = (Folder f1, Folder f2) -> f1.getDateTime().compareTo(f2.getDateTime());

        List<Folder> folderList = new ArrayList<>(folders.values());

        if (sort.equals("alpha")) {
            Collections.sort(folderList, compareByName);
        } else if (sort.equals("newest")) {
            // ascending order - smallest to largest (oldest to newest) so REVERSE
            Collections.sort(folderList, compareByDate);
            Collections.reverse(folderList);
        } else {
            // ascending -> oldest to newest
            Collections.sort(folderList, compareByDate);
        }

        return folderList;
    }


}