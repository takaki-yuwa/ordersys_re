package servlet;

import java.io.IOException;
import java.util.List;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderSystem")
public class product_name extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductDAO dao = new ProductDAO();
		List<product_list> productNameList = dao.getAllProductNames();

		request.setAttribute("product_list", productNameList);
		request.getRequestDispatcher("/OrderMenu.jsp").forward(request, response);
	}
}
