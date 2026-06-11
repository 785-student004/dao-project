package la.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import la.bean.CartBean;
import la.bean.CustomerBean;
import la.dao.DAOException;
import la.dao.OrderDAO;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 注文処理の業務はすべてセッションとCartが存在することが前提
		HttpSession session = request.getSession(false);
		if (session == null) { // セッションオブジェクトなし
			request.setAttribute("message",
					"セッションが切れています。もう一度トップページより操作してください。");
			gotoPage(request, response, "/errInternal.jsp");
			return;
		}
		CartBean cart = (CartBean) session.getAttribute("cart");
		if (cart == null) { // カートがない
			request.setAttribute("message", "正しく操作してください。");
			gotoPage(request, response, "/errInternal.jsp");
			return;
		}

		try {
			// パラメータの解析
			String action = request.getParameter("action");
			if (action == null || action.length() == 0
					|| action.equals("input_customer")) {
				// input_customerまたはパラメータなしの場合は顧客情報入力ページを表示 
				gotoPage(request, response, "/customerInfo.jsp");
			} else if (action.equals("confirm")) {
				if (session.getAttribute("customer") == null) {
					request.setAttribute("message", "ログインしてください");
					request.setAttribute("action", action);
					request.setAttribute("login", request.getParameter("login"));

					gotoPage(request, response, "/login.jsp");

				} else {
					
					gotoPage(request, response, "/confirm.jsp");
				}
			} else if (action.equals("order")) {
				// orderは注文確定
				CustomerBean customer = (CustomerBean) session.getAttribute("customer");
				if (customer == null) { // 顧客情報がない
					request.setAttribute("message", "正しく操作してください。");
					gotoPage(request, response, "/errInternal.jsp");
					return;
				}

				OrderDAO order = new OrderDAO();
				int orderNumber = order.saveOrder(customer, cart);
				// 注文後はセッション情報をクリアする
				session.removeAttribute("cart");
				session.removeAttribute("customer");
				// 注文番号をクライアントへ送る
				request.setAttribute("orderNumber", Integer.valueOf(orderNumber));
				gotoPage(request, response, "/order.jsp");
			} else {
				// actionの値が不正
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}