package Model.DAO;

import Database.JDBCUtil;
import Model.Bean.UserBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {
	public static int create(UserBean u) {
	    int result = 0; 
	    try {
	        Connection cnn = JDBCUtil.getConnection();
	        String sql = "insert into user (username, password) values(?, ?)";
	        PreparedStatement st = cnn.prepareStatement(sql);
	        st.setString(1, u.getUsername());
	        st.setString(2, u.getPassword());

	        result = st.executeUpdate();

	        JDBCUtil.closeConnection(cnn);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result; 
	}

	
	public static ArrayList<UserBean> selectAll () {
		ArrayList<UserBean> kq = new ArrayList<UserBean>();
		try {
			Connection cnn = JDBCUtil.getConnection();
			String sql = "select * from user";
			
			PreparedStatement st = cnn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				int uid = rs.getInt("uid");
				String username = rs.getString("username");
				String password = rs.getString("password");
				
				UserBean user = new UserBean(uid, username, password);
				kq.add(user);
			}
			
			JDBCUtil.closeConnection(cnn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return kq;
	}
	
	public static UserBean select(String value) {
		return find("username", value);
	}
	
	public static UserBean find(String field, String value) {
		UserBean user = null;
		try {
			Connection cnn = JDBCUtil.getConnection();
			String sql = "select * from user where " + field + " = ?";	
			PreparedStatement st = cnn.prepareStatement(sql);
			st.setString(1, value);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				int uid = rs.getInt("uid");
				String username = rs.getString("username");
				String password = rs.getString("password");
				
				user = new UserBean(uid, username, password);
				
			}
			
			JDBCUtil.closeConnection(cnn);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		return user;
	}
}
