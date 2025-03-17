package ucl.ac.uk.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
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

@WebServlet("/updateNote")
@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024, // 2MB before writing to disk
        maxFileSize = 10 * 1024 * 1024, // 10MB max size
        maxRequestSize = 50 * 1024 * 1024 // 50MB max request size
)
public class UpdateNoteServlet extends HttpServlet
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String noteId = request.getParameter("noteId");
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String[] selectedCategories = request.getParameterValues("categories");

        Part imagePart = request.getPart("image");

        Model model = ModelFactory.getModel();

        Set<String> newCategories = new HashSet<>();
        if (selectedCategories != null) {
            Collections.addAll(newCategories, selectedCategories); // make categories a set
        }

        String removeImage = request.getParameter("removeImage");
        if (removeImage != null) {
            model.removeImage(model.getNote(noteId));
        }

        model.updateNote(noteId, name, content, newCategories, imagePart);


        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/index.jsp");
        dispatch.forward(request, response);
    }
}
