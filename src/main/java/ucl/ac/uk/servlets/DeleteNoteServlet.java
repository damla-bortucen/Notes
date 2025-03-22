package ucl.ac.uk.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.classes.Note;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;

@WebServlet("/deleteNote")
public class DeleteNoteServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String noteId = request.getParameter("noteId");

        Model model = ModelFactory.getModel();

        Note note = model.getNote(noteId);
        String parentId = note.getParentId();

        model.deleteNote(noteId);

        response.sendRedirect("displayFolder?folderId=" + parentId);
    }
}
