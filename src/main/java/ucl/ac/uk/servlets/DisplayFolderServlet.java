package ucl.ac.uk.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.classes.Folder;
import ucl.ac.uk.classes.Note;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/displayFolder")
public class DisplayFolderServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String folderId = request.getParameter("folderId");

        Model model = ModelFactory.getModel();

        if (folderId.equals("root") || folderId.equals(model.getRootFolder().getId())) {
            folderId = model.getRootFolder().getId();
            request.setAttribute("title", "All Notes and Folders");
        }

        Folder folder = model.getFolder(folderId);

        request.setAttribute("currentFolder", folder);
        if (!folderId.equals(model.getRootFolder().getId()))
        {
            request.setAttribute("title", folder.getName());
        }

        String sort = request.getParameter("sort");
        if (sort == null) {
            sort = "alpha"; // Default sort if none is specified
        }
        request.setAttribute("sort", sort);

        List<Note> sortedNotes = model.sortNotes(sort, folder.getNotes());
        request.setAttribute("sortedNotes", sortedNotes);

        List<Folder> sortedFolders = model.sortFolders(sort, folder.getSubfolders());
        request.setAttribute("sortedFolders", sortedFolders);


        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/folder.jsp");
        dispatch.forward(request, response);

    }
}
