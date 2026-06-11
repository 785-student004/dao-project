package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import la.bean.CustomerBean;

public class CustomerDAO {

	private String url = "jdbc:postgresql:sample";
	private String user = "student";
	private String pass = "himitu";

	public CustomerDAO() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		}
	}

	public CustomerBean findByEmailAndPassword(String e_mail, String password) throws DAOException {
		String sql = "SELECT * FROM customer WHERE (email = ?) AND (password = ?)";

		try (
				Connection con = DriverManager.getConnection(url, user, pass);

				PreparedStatement st = con.prepareStatement(sql);) {

			st.setString(1, e_mail);
			st.setString(2, password);

			try (
					ResultSet rs = st.executeQuery();) {

				if (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					String address = rs.getString("address");
					String tel = rs.getString("tel");
					String email = rs.getString("email");
					String passwords = rs.getString("password");

					CustomerBean bean = new CustomerBean(code, name, address, tel, email, passwords);
					return bean;
				} else {
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
}
