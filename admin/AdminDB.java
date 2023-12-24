package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import journey.common.DBConnection;

public class AdminDB {
    private Connection con = null;


    //查找用户
    public int findUserByName(String username){
        ResultSet rs=null;
        Statement sql=null;
        int count=0;
        try {
            con=DBConnection.getConnection();
            sql=con.createStatement();
            rs=sql.executeQuery("SELECT * from USER");
            while(rs.next()){
                if(rs.getString("UNAME").equals(username))count++;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("查找用户失败");
        } finally{
            DBConnection.closeConnection();
        }
        return count;
    }
    //删除用户
    public void deleteUserByID(String uid){
        ResultSet rs=null;
        Statement sql=null;
        try {
            con=DBConnection.getConnection();
            sql=con.createStatement();
            rs=sql.executeQuery("delete * from USER where UID ="+ uid);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除用户失败");
        } finally{
            DBConnection.closeConnection();
        }
    }

    //删除管理员
    public void deleteAdmin(String aid){
        PreparedStatement pStmt=null;
        try {
            con=DBConnection.getConnection();
            String sql="delete * from admin where AID="+aid;
            pStmt = con.prepareStatement(sql);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除管理员失败！");
        } finally{
            DBConnection.closeConnection();
        }
    }

    //查找管理员
    public int findAdminByName(String adminName){
        ResultSet rs=null;
        Statement sql=null;
        int count=0;
        try {
            con=DBConnection.getConnection();
            sql=con.createStatement();
            rs=sql.executeQuery("SELECT * from admin");
            while(rs.next()){
                if(rs.getString("ANAME").equals(adminName))count++;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("查找管理员失败！");
        } finally{
            DBConnection.closeConnection();
        }
        return count;
    }

}
