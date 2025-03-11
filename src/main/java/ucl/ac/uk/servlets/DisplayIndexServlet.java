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

@WebServlet("/displayIndex")
public class DisplayIndexServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String folderId = request.getParameter("folderId");

        Model model = ModelFactory.getModel();
        if (folderId.equals("root")) {
            folderId = model.getRootFolder().getId();
            System.out.println(folderId);
            request.setAttribute("title", "All Notes and Folders");
        }

        Folder folder = model.getFolder(folderId);

        request.setAttribute("currentFolder", folder);
        if (!folderId.equals(model.getRootFolder().getId()))
        {
            request.setAttribute("title", folder.getName());
        }

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/indexList.jsp");
        dispatch.forward(request, response);

    }
}
