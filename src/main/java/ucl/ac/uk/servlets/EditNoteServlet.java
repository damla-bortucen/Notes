package ucl.ac.uk.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.classes.Note;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;
import java.util.Set;

@WebServlet("/editNote")
public class EditNoteServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String noteId = request.getParameter("noteId");

        Model model = ModelFactory.getModel();

        Note noteToEdit = model.getNote(noteId);
        request.setAttribute("note", noteToEdit);
        request.setAttribute("parentFolderId", noteToEdit.getParentId());

        Set<String> categories = model.getCategories();
        request.setAttribute("categories", model.getCategories());

        request.setAttribute("isNew", false);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/noteForm.jsp");
        dispatch.forward(request, response);
    }
}
