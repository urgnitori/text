package comment;

import com.google.gson.Gson;
import journey.common.MyTools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CommentAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CommentAction() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");//设置编码
        response.setContentType("application/json;charset=UTF-8");
        String actionType=request.getParameter("action");
        if(actionType.equals("list")) {			//获取该景点所有评论
            getAllComment(request,response);
        }else if(actionType.equals("delete")) {	//删除评论
            DelComment(request,response);
        }
        else if(actionType.equals("post")){//发布评论
            postComment(request,response);
        }
        else if(actionType.equals("userDel")){
            UserDelComment(request,response);
        }
        else if(actionType.equals("filter")){
            filterComment(request,response);
        }
    }
    public void getAllComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("enter list");
        CommentDB comDB=new CommentDB();


        ArrayList<CommentInfo> comments=new ArrayList<CommentInfo>();

        String sid = request.getParameter("Sid");
        //MyTools.strToint(request.getParameter("id"));
        //System.out.println(aid); 0
        comments = comDB.getAllComments(sid);


        System.out.println(comments.size());
        PrintWriter out = response.getWriter();
        Gson gson=new Gson();
        String jsonData = gson.toJson(comments);
        out.print(jsonData);
        out.flush();
        out.close();
    }
    public void DelComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("enter del");
        CommentDB beanDB=new CommentDB();
        //String cid = "2";
        String cid=request.getParameter("Cid");
        beanDB.deleteComment(cid);
    }
    public void UserDelComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("enter UserDel");
        CommentDB beanDB=new CommentDB();
        //String cid=request.getParameter("Cid");
        //String uid=request.getParameter("Uid");
        String cid = "6";
        String uid = "1";
        PrintWriter out = response.getWriter();

        int data = beanDB.userDeleteComment(cid,uid);
        if(data==0){
            out.println("删除评论有误，不可删除他人评论");
        }
        else {
            out.println("删除成功");
        }
    }
    public void postComment(HttpServletRequest request, HttpServletResponse response){

        response.setContentType("application/json;charset=UTF-8");	//设置响应的内容类型及编码方式
        System.out.println("enter post");

        CommentDB comDB=new CommentDB();
        CommentInfo comment = new CommentInfo();
        String sid = request.getParameter("Sid");
        //MyTools.strToint(request.getParameter("id"));
        //System.out.println(aid); 0
        String cid = request.getParameter("Cid");
        String uid = request.getParameter("Uid");
        String content = request.getParameter("Content");
        String date = request.getParameter("Date");
        String tag = (request.getParameter("Tag"));
        int level = MyTools.strToint(request.getParameter("Level"));
        String name = request.getParameter("Name");

        comment.setCid(cid);
        comment.setDate(date);
        comment.setTag(tag);
        comment.setName(name);
        comment.setUid(uid);
        comment.setLevel(level);
        comment.setContent(content);
        comment.setSid(sid);

        comDB.postComment(comment);
    }
    public void filterComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("application/json;charset=UTF-8");	//设置响应的内容类型及编码方式
        CommentDB commentDB = new CommentDB();
        //String tag=request.getParameter("tag");
        //String sid = request.getParameter("sid");
        String tag = "45";
        String sid = "1";
        ArrayList<CommentInfo> comments = new ArrayList<CommentInfo>();
        comments = commentDB.filterComment(tag,sid);
        System.out.println(comments.size());
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson=new Gson();
        String jsonData = gson.toJson(comments);
        out.print(jsonData);
        out.flush();
        out.close();
    }
}
