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
import la.bean.ItemBean;
import la.dao.DAOException;
import la.dao.ItemDAO2;

@WebServlet("/ItemServlet2")
public class ItemServlet2 extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// パラメータの解析
			String action = request.getParameter("action");
			// モデルのDAOを生成
			ItemDAO2 dao = new ItemDAO2();
			// パラメータなしの場合は全レコード表示
			if (action == null || action.length() == 0) {
				List<ItemBean> list = dao.findAll();
				// Listをリクエストスコープに入れてJSPへフォーワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			}
			// addは追加
			else if (action.equals("add")) {
				String name = request.getParameter("name");
				int price = Integer.parseInt(request.getParameter("price"));
				dao.addItem(name, price);
				// 追加後、全レコード表示
				List<ItemBean> list = dao.findAll();
				// Listをリクエストスコープに入れてJSPへフォーワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			}
			// sortはソート
			else if (action.equals("sort")) {
				String key = request.getParameter("key");
				List<ItemBean> list;

				HttpSession session = request.getSession();
				int minPrice = 0, maxPrice = 0;
				String pname = null;
				try {
					if (session.getAttribute("minPrice") != null) {
						minPrice = (int) session.getAttribute("minPrice");
					} else {
						minPrice = 0;
					}
					if (session.getAttribute("maxPrice") != null) {
						maxPrice = (int) session.getAttribute("maxPrice");
					} else {
						maxPrice = 0;
					}
					if (session.getAttribute("pname") != null) {
						pname = (String) session.getAttribute("productName");
					} else {
						pname = null;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				if ((minPrice == 0) && (maxPrice == 0) && (pname == null)) {
					if (key.equals("price_asc")) {
						list = dao.sortPrice(true);
					} else {
						list = dao.sortPrice(false);
					}
				} else {
					if (key.equals("price_asc")) {
						list = dao.sortAndFind(true, minPrice, maxPrice, pname);
					} else {
						list = dao.sortAndFind(false, minPrice, maxPrice, pname);
					}
				}
				// Listをリクエストスコープに入れてJSPへフォーワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			}
			// searchは検索
			else if (action.equals("search")) {
				int lePrice = 0, hePrice = 0;
				String pname = null;
				try {
					if (!request.getParameter("minPrice").equals("")) {
						lePrice = Integer.parseInt(request.getParameter("minPrice"));
					} else {
						lePrice = 0;
					}
					if (!request.getParameter("maxPrice").equals("")) {
						hePrice = Integer.parseInt(request.getParameter("maxPrice"));
					} else {
						hePrice = 0;
					}
					if (!request.getParameter("productName").equals(pname)) {
						pname = request.getParameter("productName");
					} else {
						pname = null;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				HttpSession session = request.getSession();
				session.setAttribute("pname", pname);
				if (lePrice != 0) {
					session.setAttribute("minPrice", lePrice);
				}
				if (hePrice != 0) {
					session.setAttribute("maxPrice", hePrice);
				}
				List<ItemBean> list = null;
				if (pname == null) {
					list = dao.findByPrice(lePrice, hePrice);
				} else if ((lePrice == 0) && (hePrice == 0)) {
					list = dao.findByName(pname);
				} else {
					list = dao.findByPriceAndName(lePrice, hePrice, pname);
				}
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			}
			// deleteは削除
			else if (action.equals("delete")) {
				int code = Integer.parseInt(request.getParameter("code"));
				dao.deleteByPrimaryKey(code);
				// 削除後、全レコード表示
				List<ItemBean> list = dao.findAll();
				// Listをリクエストスコープに入れてJSPへフォーワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}