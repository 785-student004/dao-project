package la.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import la.bean.EmpBean;
import la.dao.DAOException;
import la.dao.EmpDAO;

@WebServlet("/EmpServlet")
public class EmpServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String minAge = null, maxAge = null;
			try {
				minAge = request.getParameter("minAge");
				maxAge = request.getParameter("maxAge");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			EmpDAO dao = new EmpDAO();

			List<EmpBean> list;
			list = dao.findByAge(minAge, maxAge);

			request.setAttribute("employees", list);
			gotoPage(request, response, "/emp.jsp");

		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "正しい年齢を入力してください");
			gotoPage(request, response, "/error.jsp");
		}
	}

	private void gotoPage(HttpServletRequest request,
			HttpServletResponse response, String page) throws ServletException,
			IOException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
