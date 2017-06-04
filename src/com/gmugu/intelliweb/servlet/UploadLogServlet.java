package com.gmugu.intelliweb.servlet;

import com.gmugu.intelliweb.bean.LogBean;
import com.gmugu.intelliweb.dao.LogDao;
import com.gmugu.intelliweb.model.Result;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mugu on 17/5/8.
 */
@WebServlet(name = "UploadLogServlet", urlPatterns = "/uploadlog")
public class UploadLogServlet extends HttpServlet {

    private LogDao logDao = new LogDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Result result=new Result();
        PrintWriter out = resp.getWriter();
        String[] lockMac = req.getParameterValues("lockMac");
        String[] events = req.getParameterValues("event");
        if (lockMac == null || lockMac.length != 1) {
            result.code = -1;
            result.msg = "�������lockMac������";
            out.println(new Gson().toJson(result));
            out.close();
            return;
        }
        String mac = lockMac[0];

        System.out.println("UploadLogServlet  lockMac:"+mac+"  "+events[0]);

        LogBean entity = new LogBean();
        entity.setEvent("unlock");


        if (events[0] != null) {
            if (events[0].equals("0")) {
                entity.setEvent("���ּ��̽���");
            } else if (events[0].equals("1")) {
                entity.setEvent("�ƶ��豸����");
            }
        }
        entity.setTime(System.currentTimeMillis());
        entity.setLockMac(mac);
        logDao.save(entity);
        out.close();
    }

}
