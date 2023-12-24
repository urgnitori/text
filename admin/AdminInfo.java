package admin;

import java.sql.Blob;

public class AdminInfo{
	private String aid;
	private String apassword;
	private String aname;
	private String abirth;
	private Blob aphoto;
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getApassword() {
		return apassword;
	}
	public void setApassword(String apassword) {
		this.apassword = apassword;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getAbirth() {
		return abirth;
	}
	public void setAbirth(String abirth) {
		this.abirth = abirth;
	}
	public Blob getAphoto() {
		return aphoto;
	}
	public void setAphoto(Blob aphoto) {
		this.aphoto = aphoto;
	}
}