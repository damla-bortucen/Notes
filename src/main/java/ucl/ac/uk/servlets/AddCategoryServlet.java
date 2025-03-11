package ucl.ac.uk.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import java.io.IOException;

@WebServlet("/addCategory")
public class AddCategoryServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String categoryName = request.getParameter("categoryName");

        Model model = ModelFactory.getModel();
        if (model.getAllCategories().contains(categoryName)) {
            request.setAttribute("error", "Category already exists. Please choose a different name.");

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/addCategory.jsp");
            dispatch.forward(request, response);

        } else {
            model.addCategory(categoryName);

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/index.jsp");
            dispatch.forward(request, response);
        }
    }
}
