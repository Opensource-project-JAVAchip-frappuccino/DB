package ConnectServer;

import java.sql.*;

public class ConnectServer{
    public static void main(String[] args) {
        Connection conn = null;

        String server = "thisisjava";   //서버명
        String user = "root";           //유저명
        String password = "7749";       //비번

        try{ //실행구간
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+server,user,password);

            System.out.println("연결 성공!");
            //매개변수화된 SQL문 작성
            String sql = "" + "INSERT INTO users (userid, username, userpassword, userage, useremail) " + "VALUES (?,?,?,?,?)";

            //매개변수화된 SQL문 실행 메소드 pstmt
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //변수 삽입

            pstmt.setString(1, "winter");
            pstmt.setString(2, "한겨울");
            pstmt.setString(3, "12345");
            pstmt.setInt(4, 25);
            pstmt.setString(5, "winterinmauve");
            
            //데이터 저장 확인
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : "+rows);
            pstmt.close();

        }catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                try {
                    conn.close();
                    System.out.println("연결 끊기");
                } catch (SQLException e) {}
            }
        }
    }
}
