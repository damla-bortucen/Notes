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

@WebServlet("/editFolder")
public class EditFolderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String folderId = request.getParameter("folderId");

        Model model = ModelFactory.getModel();
        Folder folder = model.getFolder(folderId);

        request.setAttribute("folder", folder);
        request.setAttribute("isNew", false);

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/folderForm.jsp");
        dispatch.forward(request, response);


    }
}
