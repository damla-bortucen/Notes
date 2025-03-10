package ucl.ac.uk.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addItem")
public class AddItemServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String folderId = request.getParameter("folderId");
        String itemType = request.getParameter("itemType");

        request.setAttribute("folderId", folderId);
        request.setAttribute("itemType", itemType);

        if (itemType.equals("note")) {
            request.getRequestDispatcher("/takeNote.jsp").forward(request, response);
        } else if (itemType.equals("folder")) {
            request.getRequestDispatcher("/addFolder.jsp").forward(request, response);
        }

    }
}

