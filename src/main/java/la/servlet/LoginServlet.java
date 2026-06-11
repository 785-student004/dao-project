package la.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import la.bean.CategoryBean;
import la.bean.CustomerBean;
import la.dao.CustomerDAO;
import la.dao.DAOException;
import la.dao.ItemDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter("action");

			CustomerDAO dao = new CustomerDAO();

			if (action == null || action.length() == 0 || action.equals("top")) {

				gotoPage(request, response, "/top.jsp");

			} else if (action.equals("login")) {
				String email = request.getParameter("email");
				String password = request.getParameter("password");

				List<CustomerBean> list = dao.findByEmailAndPassword(email, password);

				if ((list != null) && (list.size() != 0)) {
					request.setAttribute("customers", list);
					gotoPage(request, response, "/top.jsp");
				} else {
					request.setAttribute("message", "メールアドレスとパスワードが一致しませんでした");
					gotoPage(request, response, "/login.jsp");
				}

			} else {
				request.setAttribute("message", "正しく操作してください。");
				gotoPage(request, response, "/errInternal.jsp");
			}
		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			gotoPage(request, response, "/errInternal.jsp");
		}
	}

	private void gotoPage(HttpServletRequest request,
			HttpServletResponse response, String page) throws ServletException,
			IOException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	public void init() throws ServletException {
		try {
			// カテゴリ一覧は最初にアプリケーションスコープへ入れる
			ItemDAO dao = new ItemDAO();
			List<CategoryBean> list = dao.findAllCategory();
			getServletContext().setAttribute("categories", list);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
