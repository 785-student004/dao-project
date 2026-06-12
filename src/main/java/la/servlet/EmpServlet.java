package la.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import la.bean.EmpBean;
import la.dao.DAOException;
import la.dao.EmpDAO;

@WebServlet("/EmpServlet")
public class EmpServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action = request.getParameter("action");
			EmpDAO dao = new EmpDAO();
			HttpSession session = request.getSession();
			session.removeAttribute("minAge");
			session.removeAttribute("maxAge");
			session.removeAttribute("numPeople");
			
			if (action.equals("between")) {
				try {
					String minAge = null, maxAge = null;
					try {
						minAge = request.getParameter("minAge");
						maxAge = request.getParameter("maxAge");
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}

					List<EmpBean> list;
					list = dao.findByAge(minAge, maxAge);

					request.setAttribute("employees", list);
					session.setAttribute("minAge", minAge);
					session.setAttribute("maxAge", maxAge);

					gotoPage(request, response, "/emp.jsp");

				} catch (DAOException e) {
					e.printStackTrace();
					request.setAttribute("message", "正しい年齢を入力してください");
					gotoPage(request, response, "/error.jsp");
				}
			} else if (action.equals("limit")) {
				try {
					String num = null;

					try {
						num = request.getParameter("numPeople");
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}

					List<EmpBean> list = dao.findByAgeLimit(num);

					request.setAttribute("employees", list);
					session.setAttribute("numPeople", num);

					gotoPage(request, response, "/emp.jsp");

				} catch (DAOException e) {
					e.printStackTrace();
					request.setAttribute("message", "正しい人数を入力してください");
					gotoPage(request, response, "/error.jsp");
				}
			}

		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "正しい操作をしてください");
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
