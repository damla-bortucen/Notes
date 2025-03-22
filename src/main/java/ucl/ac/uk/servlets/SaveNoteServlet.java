package ucl.ac.uk.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/saveNote")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class SaveNoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String folderId = request.getParameter("folderId");
        String noteId = request.getParameter("noteId");
        String noteName = request.getParameter("noteName");
        String noteContent = request.getParameter("content");
        String[] selectedCategories = request.getParameterValues("noteCategories");

        Part imagePart = request.getPart("image");
        boolean removeImage = request.getParameter("removeImage") != null;

        Set<String> categories = new HashSet<>();
        if (selectedCategories != null) {
            Collections.addAll(categories, selectedCategories); // make categories a set
        }

        Model model = ModelFactory.getModel();

        if (noteId == null) {
            // NEW NOTE
            model.createNote(noteName, noteContent, folderId, categories, imagePart);
        } else {
            // EDITING AN EXISTING NOTE
            if (removeImage) {
                model.removeImage(model.getNote(noteId));
            }
            model.updateNote(noteId, noteName, noteContent, categories, imagePart);
        }

        response.sendRedirect("displayFolder?folderId=" + folderId);
    }
}