package ucl.ac.uk.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.classes.Folder;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;

@WebServlet("/saveFolder")
public class SaveFolderServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String parentFolderId = request.getParameter("parentFolderId");
        String folderId = request.getParameter("folderId");
        String folderName = request.getParameter("folderName");

        Model model = ModelFactory.getModel();
        Folder parent = model.getFolder(parentFolderId);

        if (folderId == null || folderId.isEmpty()) {
            Folder folder = new Folder();
            folder.setName(folderName);
            folder.setParentId(parentFolderId);
            model.addFolder(folder, parent);
        } else {
            Folder folderToUpdate = model.getFolder(folderId);
            model.updateFolder(folderToUpdate, folderName);
        }



        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/index.jsp");
        dispatch.forward(request, response);
    }
}
