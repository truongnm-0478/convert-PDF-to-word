package Model.BO;


import Model.Bean.UserBean;
import Model.DAO.UserDAO;


public class UserBO {
	public static UserBean select(String username) {
		return UserDAO.select(username);
	}
	
	public static int create(UserBean u) {
		return UserDAO.create(u);
	} 
	
	

}
