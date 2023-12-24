package admin;

import java.io.IOException;
import java.io.PrintWriter;

import journey.common.MyTools;

import notice.NoticeAction;
import spot.SpotAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminAction extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static NoticeAction noticeAction = new NoticeAction();
    private static SpotAction spotAction = new SpotAction();
    public AdminAction(){ super();}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String actionType=request.getParameter("action");
        if(actionType.equals("postNotice")){//����Ա��������
            noticeAction.PublishNotice(request,response);
        }
        else if(actionType.equals("updateNotice")){//����Ա���¹���
            noticeAction.UpdateNotice(request,response);
        }
        else if(actionType.equals("deleteNotice")){//����Աɾ������
            noticeAction.DelNotice(request,response);
        }
        else if(actionType.equals("list")){//����Ա��ȡ���о�����Ϣ
            spotAction.GetAllSpots(request,response);
        }
        else if(actionType.equals("insertSpot")){//����Ա��Ӿ�����Ϣ
            spotAction.InsertSpot(request,response);
        }
        else if(actionType.equals("updateSpot")){//����Ա���¾�����Ϣ
            spotAction.UpdateSpot(request,response);
        }
        else if(actionType.equals("deleteSpot")){//����Աɾ��������Ϣ
            spotAction.DelSpot(request,response);
        }
        else if(actionType.equals("codeSpotCheck")){//����Ա��֤������Ϣ
            spotAction.CheckSname(request,response);
        }
        else if(actionType.equals("findAdmin")){//����Ա���ҹ���Ա
            findAdmin(request,response);
        }
        else if(actionType.equals("deleteAdmin")){//����Աɾ������Ա
            deleteAdmin(request,response);
        }
        else if (actionType.equals("findUser")) {//����Ա�����û�
            findUser(request,response);
        }
        else if(actionType.equals("deleteUser")){//����Աɾ���û�
            deleteUser(request,response);
        }
    }

    protected void findAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException {
        AdminDB adminDB = new AdminDB();
        AdminInfo admin = new AdminInfo();
        PrintWriter out = response.getWriter();

        int count = adminDB.findAdminByName(request.getParameter("aName"));
        if(count!=0){
            out.print("1");
        }
        else out.print("0");
        out.close();
    }

    protected void deleteAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException {
        AdminDB adminDB = new AdminDB();
        AdminInfo admin = new AdminInfo();
        PrintWriter out = response.getWriter();

        int identity= MyTools.strToint(request.getParameter("identity"));
        if(identity==1){
            adminDB.deleteAdmin(request.getParameter("aid"));
            out.print("ɾ���ɹ�");
        }
        else out.print("����ԱȨ�޲���");
    }

    protected void findUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
        AdminDB adminDB = new AdminDB();
        AdminInfo admin = new AdminInfo();
        PrintWriter out = response.getWriter();

        int count = adminDB.findUserByName(request.getParameter("UName"));
        if(count!=0){
            out.print("1");
        }
        else out.print("0");
        out.close();
    }
    protected void deleteUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
        AdminDB adminDB = new AdminDB();
        AdminInfo admin = new AdminInfo();
        PrintWriter out = response.getWriter();

        adminDB.deleteUserByID(request.getParameter("uid"));
        out.print("ɾ���û��ɹ���idΪ"+request.getParameter("uid"));
    }
}
