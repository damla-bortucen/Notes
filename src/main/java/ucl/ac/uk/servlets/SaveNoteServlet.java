package ucl.ac.uk.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.main.Folder;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/saveNote")
public class SaveNoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String folderId = request.getParameter("folderId");
        String noteName = request.getParameter("noteName");
        String noteContent = request.getParameter("content");
        String[] selectedCategories = request.getParameterValues("noteCategories");

        Set<String> categories = new HashSet<>();
        if (selectedCategories != null) {
            Collections.addAll(categories, selectedCategories); // make categories a set
        }

        Model model = ModelFactory.getModel();
        Folder folder = model.getFolder(folderId);

        model.createNote(noteName, noteContent, folderId, categories);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/index.jsp");
        dispatch.forward(request, response);

    }
}