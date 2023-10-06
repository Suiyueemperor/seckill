package com.xxxx.seckill.utils;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.vo.RespBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 生产用户工具类
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-10-06 10:08
 **/

public class UserUtil {
    /**
     * 生成用户
     * @param count
     */
    public static void createUser(int count) throws Exception {
        List<User> users = new ArrayList<>(count);
        // 用户名不同, 密码相同
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(13000000000L+i);
            user.setNickname("user"+i);
            user.setSalt("1a2b3c4d");
            user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSalt()));
            user.setLoginCount(1);
            users.add(user);
        }
        System.out.println("Create users");
        // 插入数据库
//        Connection conn = getConn();
//        System.out.println("创建数据库连接");
//        // JDBC
//        String sql = "insert into t_user(login_count,nickname,salt,password,id) values(?,?,?,?,?)";
//        PreparedStatement preparedStatement = conn.prepareStatement(sql);
//        for(int i = 0; i < count; i++) {
//            User user = users.get(i);
//            preparedStatement.setInt(1, user.getLoginCount());
//            preparedStatement.setString(2, user.getNickname());
//            preparedStatement.setString(3, user.getSalt());
//            preparedStatement.setString(4, user.getPassword());
//            preparedStatement.setLong(5,user.getId());
//            preparedStatement.addBatch();
//        }
//        preparedStatement.executeBatch();
//        preparedStatement.clearParameters();
//        preparedStatement.close();
//        System.out.println("插入成功");
//        conn.close();

        // 模拟登录,生成userTicket
        String urlString = "http://localhost:8080/login/doLogin";
        File file = new File("C:\\Users\\shi xingyan\\Desktop\\config.txt");
        if (file.exists()) {// 如果存在 删除, 可能会重复使用
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();// 创建一个新的空文件,存在则返回false
        raf.seek(0);// 文本插入起始位置

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);

            OutputStream out = co.getOutputStream();//输出流
            // 准备参数
            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFromPass("123456");//记得加密
            out.write(params.getBytes());// 字符串->字节数组
            out.flush();// 刷一下
            InputStream inputStream = co.getInputStream();//输入流
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();

            String response = new String(bout.toByteArray());//拿到响应结果

            ObjectMapper mapper = new ObjectMapper();
            RespBean respBean = mapper.readValue(response, RespBean.class);
            String userTicket = ((String) respBean.getObj());
            System.out.println("create userTicket : " + user.getId());

            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();

        System.out.println("over");
    }

    /**
     * 获取数据库
     * @return
     */
    private static Connection getConn() throws Exception {
        String url = "jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }

}
