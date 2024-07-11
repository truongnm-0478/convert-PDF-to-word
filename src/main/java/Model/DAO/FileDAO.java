package Model.DAO;

import Database.JDBCUtil;
import Model.Bean.FileBean;
import Model.Bean.UserBean;
import _CONSTAINT.CONSTAINT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FileDAO {

	public static FileBean GetFile(int fid) {
		FileBean file = new FileBean();

		try {
			Connection cnn = JDBCUtil.getConnection();
			String sql = "select * from uploadfile where fid = ?";
			PreparedStatement st = cnn.prepareStatement(sql);
			st.setInt(1, fid);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				int _fid = rs.getInt("fid");
				int _uid = rs.getInt("uid");
				String fname = rs.getString("fname").split("\\.")[0] + ".docx";
				int status = 2;

				file = new FileBean(_fid, _uid, fname, status);
			}

		} catch (Exception e) {
			return null;
		}

		return file;
	}

	public static int Upload(String filename, UserBean u, int fstatus) {
		try {
			Connection cnn = JDBCUtil.getConnection();
			String sql = "";
			PreparedStatement st;

			if (!isFileExist(u.getUid(), filename)) {
				sql = "insert into uploadfile (uid, fname, fstatus) values (?, ?, ?)";
				st = cnn.prepareStatement(sql);
				st.setInt(1, u.getUid());
				st.setString(2, filename);
				st.setInt(3, fstatus);
			} else {
				sql = "update uploadfile set fstatus = ? where fname = ?";
				st = cnn.prepareStatement(sql);
				st.setInt(1, fstatus);
				st.setString(2, filename);
			}

			st.executeUpdate();
			JDBCUtil.closeConnection(cnn);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static boolean isFileExist( int uid ,String filename) {
		try {
			Connection cnn = JDBCUtil.getConnection();
			String sql = "select * from uploadfile where uid = ? and fname = ?";
			PreparedStatement st = cnn.prepareStatement(sql);
			st.setInt(1, uid);
			st.setString(2, filename);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				JDBCUtil.closeConnection(cnn);
				return true;
			} else {
				JDBCUtil.closeConnection(cnn);
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public static ArrayList<FileBean> GetListConvertFile(UserBean user) {
		ArrayList<FileBean> files = new ArrayList<FileBean>();

		try {
			Connection cnn = JDBCUtil.getConnection();
			String sql = "select * from  uploadfile where uid = ? and fstatus = ? ";
			PreparedStatement st = cnn.prepareStatement(sql);

			st.setInt(1, user.getUid());
			st.setInt(2, CONSTAINT.PROCESSING);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int fid = rs.getInt("fid");
				int uid = rs.getInt("uid");
				String fname = rs.getString("fname");
				int fstatus = rs.getInt("fstatus");

				files.add(new FileBean(fid, uid, fname, fstatus));
			}

			JDBCUtil.closeConnection(cnn);
		} catch (Exception e) {
			return null;
		}
		return files;
	}

	public static void ChangeStatus(int fid, int fstatus) {
	    try {
	        Connection cnn = JDBCUtil.getConnection();
	        String sql = "update uploadfile set fstatus = ? where fid = ?";
	        PreparedStatement st = cnn.prepareStatement(sql);

	        st.setInt(1, fstatus);
	        st.setInt(2, fid);

	        st.executeUpdate(); 
	        JDBCUtil.closeConnection(cnn);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	public static ArrayList<FileBean> GetProcessedFile(int uid) {
		ArrayList<FileBean> files = new ArrayList<FileBean>();

		try {
			Connection cnn = JDBCUtil.getConnection();
			String sql = "select * from uploadfile where uid = ?";
			PreparedStatement st = cnn.prepareStatement(sql);

			st.setInt(1, uid);


			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int fid = rs.getInt("fid");
				String fname = rs.getString("fname").split("\\.")[0] + ".docx";
				if(fname.length() > 20)
					fname = fname.substring(0, 20) + "...docx";
				int fstatus = rs.getInt("fstatus");
				
				files.add(new FileBean(fid, uid, fname, fstatus));
			}

			JDBCUtil.closeConnection(cnn);
		} catch (Exception e) {
			return null;
		}
		return files;
	}
	
	public static void delete(int fid) {
	    try {
	        Connection cnn = JDBCUtil.getConnection();
	        String sql = "DELETE FROM uploadfile WHERE fid = ?";
	        PreparedStatement st = cnn.prepareStatement(sql);

	        st.setInt(1, fid);

	        st.executeUpdate();

	        JDBCUtil.closeConnection(cnn);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	} 
}
