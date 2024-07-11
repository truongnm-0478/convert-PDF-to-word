package Model.Bean;

public class FileBean {
	private int fid;
	private int uid;
	private String fname;
	private int fstatus;
	public FileBean(int fid, int uid, String fname, int fstatus) {
		this.fid = fid;
		this.uid = uid;
		this.fname = fname;
		this.fstatus = fstatus;
	}
	public FileBean() {
		super();

	}	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public int getFstatus() {
		return fstatus;
	}
	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}
	
}
