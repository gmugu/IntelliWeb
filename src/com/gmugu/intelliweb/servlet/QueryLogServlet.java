package com.gmugu.intelliweb.servlet;

import com.gmugu.intelliweb.bean.LogBean;
import com.gmugu.intelliweb.dao.LogDao;
import com.gmugu.intelliweb.model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by mugu on 17/5/8.
 */
@WebServlet(name = "QueryLogServlet", urlPatterns = "/querylog")
public class QueryLogServlet extends HttpServlet {

    private LogDao logDao = new LogDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Result<List<LogBean>> result = new Result<>();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json");
        PrintWriter out = resp.getWriter();
        String[] lockMac = req.getParameterValues("lockMac");
        if (lockMac == null || lockMac.length != 1) {
            result.code = -1;
            result.msg = "请求参数lockMac不存在";
            out.println(new Gson().toJson(result));
            out.close();
            return;
        }
        String mac = lockMac[0];
        List<LogBean> byLockMac = logDao.findByLockMac(mac);
        result.data = byLockMac;
        Gson gson = new GsonBuilder()
                .create();
        String json = gson.toJson(result);
        out.println(json);
        out.close();
    }

}
