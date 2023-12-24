package comment;

import journey.common.DBConnection;
import journey.common.MyTools;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CommentDB {
    private Connection con = null;

    //找该景点名的全部评论
    public ArrayList<CommentInfo> getAllComments(String Sid){
        ResultSet rs=null;
        Statement sql=null;
        ArrayList<CommentInfo> comments=new ArrayList<CommentInfo>();
        try {
            con=DBConnection.getConnection();
            sql=con.createStatement();
            rs=sql.executeQuery("select * from comment where SID="+Sid);
            //System.out.println(spot.getSid());
            while(rs.next()) {
                CommentInfo comment=new CommentInfo();
                comment.setCid(rs.getString("CID"));
                comment.setSid(rs.getString("SID"));
                comment.setDate(rs.getString("DATE"));
                comment.setContent(rs.getString("CONTENT"));
                comment.setUid(rs.getString("UID"));
                comment.setTag(rs.getString("TAG"));
                comments.add(comment);
                rs.close();
            }
            sql.close();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("获取所属景点公告信息失败！");
        }finally{
            DBConnection.closeConnection();
        }
        return comments;
    }

    //管理删除评论
    public void deleteComment(String Cid){
        PreparedStatement pStmt=null;
        try {
            con=DBConnection.getConnection();
            String sql="delete from comment  where CID="+Cid;
            pStmt = con.prepareStatement(sql);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除评论失败！");
        } finally{
            DBConnection.closeConnection();
        }
    }
    //用户删除评论
    public int userDeleteComment(String Cid,String Uid){
        PreparedStatement pStmt=null;
        int data = 0;
        try {
            con=DBConnection.getConnection();
            String sql="delete from comment where CID="+Cid+"&& UID="+Uid;
            pStmt = con.prepareStatement(sql);
            data = pStmt.executeUpdate();
            System.out.println(sql);
            System.out.println(data);
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除评论失败！");
        } finally{
            DBConnection.closeConnection();
            return data;
        }
    }

    //筛选评论
    public ArrayList<CommentInfo> filterComment(String Tag,String Sid){
        ResultSet rs=null;
        Statement sql=null;
        ArrayList<CommentInfo> comments=new ArrayList<CommentInfo>();
        try {
            con=DBConnection.getConnection();
            sql=con.createStatement();
            rs=sql.executeQuery("select * from comment where SID="+Sid);
            //System.out.println(spot.getSid());
            while(rs.next()) {
                String tagSql = rs.getString("TAG");
                int flag=1;
                char[] ch = Tag.toCharArray();
                for(char c:ch){
                    if(!tagSql.contains(c+"")){
                        flag=0;
                        break;
                    }
                }
                if(flag==1){
                CommentInfo comment=new CommentInfo();
                comment.setTag(tagSql);
                comment.setCid(rs.getString("CID"));
                comment.setSid(rs.getString("SID"));
                comment.setDate(rs.getString("DATE"));
                comment.setContent(rs.getString("CONTENT"));
                comment.setUid(rs.getString("UID"));
                comment.setLevel(rs.getInt("LEVEL"));
                comment.setName(rs.getString("NAME"));
                comments.add(comment);
                }
            }
            rs.close();
            sql.close();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("获取所属景点公告信息失败！");
        }finally{
            DBConnection.closeConnection();
        }
        return comments;
    }

    public void postComment(CommentInfo comment){
        ResultSet rs=null;
        Statement stmt=null;
        PreparedStatement pStmt=null;

        try {
            con = DBConnection.getConnection();
            pStmt = con.prepareStatement("insert into comment (CID,SID,UID,CONTENT,DATE,TAG,LEVEL,NAME) VALUES (?,?,?,?,?,?,?,?)");
            pStmt.setString(1,comment.getCid());
            pStmt.setString(2,comment.getSid());
            pStmt.setString(3,comment.getUid());
            pStmt.setString(4,comment.getContent());
            String dateS = comment.getDate();
            System.out.println(dateS);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(dateS);
            java.sql.Date holdDate=new java.sql.Date(date.getTime());
            java.sql.Date commentDate = MyTools.getSqlDate(holdDate);
            System.out.println("date");
            pStmt.setDate(5,commentDate);
            pStmt.setString(6,comment.getTag());
            pStmt.setInt(7,comment.getLevel());
            pStmt.setString(8,comment.getName());

            pStmt.executeUpdate();
            pStmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
