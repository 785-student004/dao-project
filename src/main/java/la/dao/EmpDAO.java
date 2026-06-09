package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import la.bean.EmpBean;

public class EmpDAO {

	private String url = "jdbc:postgresql:sample";
	private String user = "student";
	private String pass = "himitu";

	public EmpDAO() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("ドライバの登録に失敗しました。");
		}
	}

	public List<EmpBean> findAll() throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM emp";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);
				// SQLの実行
				ResultSet rs = st.executeQuery();) {
			// 結果の取得
			List<EmpBean> list = new ArrayList<EmpBean>();
			while (rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String tel = rs.getString("tel");
				EmpBean bean = new EmpBean(code, name, age, tel);
				list.add(bean);
			}
			// 商品一覧をListとして返す
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public List<EmpBean> findByAge(String minAge, String maxAge) throws DAOException {
		String sql = "SELECT * FROM emp WHERE age BETWEEN ? AND ?";

		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql);) {

			st.setString(1, minAge);
			st.setString(2, maxAge);
			try (ResultSet rs = st.executeQuery();) {
				List<EmpBean> list = new ArrayList<EmpBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int age = rs.getInt("age");
					String tel = rs.getString("tel");
					EmpBean bean = new EmpBean(code, name, age, tel);
					list.add(bean);
				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの操作に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
}
