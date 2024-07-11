package Model.BO;

import Model.Bean.FileBean;
import Model.DAO.FileDAO;

import java.util.ArrayList;


public class FileBO {
	public static FileBean GetFile(int fid) {
		return FileDAO.GetFile(fid);
	}
	
	public static ArrayList<FileBean> GetProcessedFile(int uid) {
		return FileDAO.GetProcessedFile(uid);
	}
	
	public static void delete(int fid) {
		FileDAO.delete(fid);
	}
}
