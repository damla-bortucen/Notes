package ucl.ac.uk.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.main.Folder;
import ucl.ac.uk.main.Note;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;

@WebServlet("/saveNote")
public class SaveNoteServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String folderId = request.getParameter("folderId");
        String noteName = request.getParameter("noteName");
        String noteContent = request.getParameter("content");

        Model model = ModelFactory.getModel();
        Folder folder = model.getFolder(folderId);

        Note newNote = new Note();
        newNote.setName(noteName);
        newNote.setContent(noteContent);
        newNote.setDateTime();

        model.addNote(newNote, folder);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/index.jsp");
        dispatch.forward(request, response);

    }
}