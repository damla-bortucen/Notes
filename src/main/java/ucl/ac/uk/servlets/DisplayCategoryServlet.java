package ucl.ac.uk.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.main.Note;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;
import java.util.Set;

@WebServlet("/displayCategory")
public class DisplayCategoryServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String category = request.getParameter("category");

        if (category != null) {
            Model model = ModelFactory.getModel();

            Set<Note> notesInCat = model.getNotesByCategory(category);

            request.setAttribute("notes", notesInCat);
            request.setAttribute("category", category);

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/displayCategory.jsp");
            dispatch.forward(request, response);
        }
    }
}
