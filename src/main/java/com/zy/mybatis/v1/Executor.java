package com.zy.mybatis.v1;

import com.zy.mybatis.v1.domain.Blog;

import java.sql.*;

public class Executor {

    private DataSourceProperties sourceProperties;

    public Executor(DataSourceProperties sourceProperties){
        this.sourceProperties = sourceProperties;
    }

    public <T> T query(String sql,Object param) {

        //JDBC封装
        Blog blog = new Blog();
        Statement statement = null;
        Connection conn = null;
        try {
            Class.forName(sourceProperties.getDriverClassName());
            conn = DriverManager.getConnection(sourceProperties.getUrl(),sourceProperties.getUsername(),sourceProperties.getPassword());
            statement = conn.createStatement();
            String exeSql = sql.replace("?",param.toString());
            ResultSet resultSet = statement.executeQuery(exeSql);

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String book = resultSet.getString("book");
                blog.setId(id);
                blog.setName(name);
                blog.setBook(book);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null!=statement){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null!=conn){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T)blog;
    }
}
