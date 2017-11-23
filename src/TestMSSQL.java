import com.device.util.PrimaryKeyUtil;

public class TestMSSQL {
    public static void main(String[] args) {
        int count = 2;
        for (int i = 0; i < count; i++) {
            System.out.println(PrimaryKeyUtil.getSeq());
        }
        /*String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库引擎
        String connectDB = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=elab";//数据源

        try {
            Class.forName(JDriver);//加载数据库引擎，返回给定字符串名的类
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("加载数据库引擎失败");
            System.exit(0);
        }
        System.out.println("数据库驱动成功");

        try {
            String user = "sa";
            String password = "123456";
            Connection con = DriverManager.getConnection(connectDB, user, password);//连接数据库对象
            System.out.println("连接数据库成功");
            Statement stmt = con.createStatement();//创建SQL命令对象

            //创建表
            String query = "select id from t_system_user where id=1";//创建表SQL语句
            ResultSet rs = stmt.executeQuery(query);//返回SQL语句查询结果集(集合)
            //循环输出每一条记录
            while (rs.next()) {
                //输出每个字段
                System.out.println(rs.getString("usid"));
            }
            System.out.println("读取完毕");

            //关闭连接
            stmt.close();//关闭命令对象连接
            con.close();//关闭数据库连接
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("数据库连接错误");
            System.exit(0);
        }*/
    }
}
