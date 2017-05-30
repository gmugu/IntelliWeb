package com.gmugu.intelliweb.dao;

import com.gmugu.intelliweb.bean.LogBean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mugu on 17/5/8.
 */
public class LogDao {


    private final Connection conn;

    public LogDao() {
        DBHelper dbHelper = new DBHelper();
        conn = dbHelper.getConn();
    }

    public void save(LogBean entity) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.execute(String.format("INSERT INTO log (lock_mac, event, time,img) VALUES ('%s','%s','%s','%s')",
                    entity.getLockMac(),
                    entity.getEvent(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entity.getTime()),
                    entity.getImg()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(LogBean entity) throws SQLException {
        throw new SQLException("没实现呢");
//        Statement statement;
//        try {
//            statement = conn.createStatement();
//            int execute = statement.executeUpdate(String.format(""));
//            if (execute < 1) {
//                throw new SQLException("更新失败!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void delete(LogBean entity) throws SQLException {
        throw new SQLException("没实现呢");
    }

    public LogBean findById(Serializable id) {
        Statement statement;
        LogBean logBean = new LogBean();
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM log WHERE id=" + id);
            while (resultSet.next()) {
                logBean.setId(resultSet.getInt("id"));
                logBean.setLockMac(resultSet.getString("lock_mac"));
                logBean.setEvent(resultSet.getString("event"));
                logBean.setTime(resultSet.getTimestamp("time").getTime());
                logBean.setImg(resultSet.getString("img"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logBean;
    }

    public List<LogBean> findByLockMac(String mac) {
        Statement statement;
        List<LogBean> list = new ArrayList<>();
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM log WHERE lock_mac='%s'", mac));
            while (resultSet.next()) {
                LogBean logBean = new LogBean();
                logBean.setId(resultSet.getInt("id"));
                logBean.setLockMac(resultSet.getString("lock_mac"));
                logBean.setEvent(resultSet.getString("event"));
                logBean.setTime(resultSet.getTimestamp("time").getTime());
                logBean.setImg(resultSet.getString("img"));
                list.add(logBean);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LogBean> findAll() {
        Statement statement;
        List<LogBean> list = new ArrayList<>();
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM log");
            while (resultSet.next()) {
                LogBean logBean = new LogBean();
                logBean.setId(resultSet.getInt("id"));
                logBean.setLockMac(resultSet.getString("lock_mac"));
                logBean.setEvent(resultSet.getString("event"));
                logBean.setTime(resultSet.getTimestamp("time").getTime());
                logBean.setImg(resultSet.getString("img"));
                list.add(logBean);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
