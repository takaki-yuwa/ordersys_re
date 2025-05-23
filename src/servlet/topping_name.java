package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ToppingDAO;

@WebServlet("/topping")
public class topping_name extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        ToppingDAO dao = new ToppingDAO();
        List<String> toppingNameList = dao.getAllProductNames();

        request.setAttribute("topping_name", toppingNameList);
        request.getRequestDispatcher("/topping_name.jsp").forward(request, response);
    }
}
