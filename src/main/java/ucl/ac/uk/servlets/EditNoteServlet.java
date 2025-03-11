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

@WebServlet("/editNote")
public class EditNoteServlet extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String noteId = request.getParameter("noteId");

        Model model = ModelFactory.getModel();
        Note noteToEdit = model.getNote(noteId);

        request.setAttribute("noteToEdit", noteToEdit);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/editNote.jsp");
        dispatch.forward(request, response);
    }
}
