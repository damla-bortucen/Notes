package ucl.ac.uk.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/updateNote")
public class UpdateNoteServlet extends HttpServlet
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String noteId = request.getParameter("noteId");
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String[] selectedCategories = request.getParameterValues("categories");

        Model model = ModelFactory.getModel();
        model.updateNote(noteId, name, content);

        Set<String> newCategories = new HashSet<>();
        if (selectedCategories != null) {
            Collections.addAll(newCategories, selectedCategories); // make categories a set
        }
        model.updateNoteCategories(noteId, newCategories);


        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/index.jsp");
        dispatch.forward(request, response);
    }
}
