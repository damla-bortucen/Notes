package ucl.ac.uk.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.classes.Folder;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;

@WebServlet("/deleteFolder")
public class DeleteFolderServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String folderId = request.getParameter("folderId");

        Model model = ModelFactory.getModel();

        Folder folder = model.getFolder(folderId);
        String parentId = folder.getParentId();
        model.deleteFolder(folderId);

        response.sendRedirect("displayFolder?folderId=" + parentId);
    }
}
