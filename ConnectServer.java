package ConnectServer;

import java.sql.*;
import java.util.Scanner;

class OnlineServer{
    private int id;
    private int pw;

    public OnlineServer(int id, int pw){
        this.id = id;
        this.pw = pw;
    }

    public int InputData(int id, int pw){
        Connection conn = null;

        String server = "marketeam";   //서버명
        String user = "root";          //유저명
        String password = "7749";      //비번

        try{ //실행구간
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+server,user,password);

            System.out.println("연결 성공!");
            //매개변수화된 SQL문 작성
            String sql = ""
                    + "INSERT INTO login (id, pw) "
                    + "VALUES (?,?)";

            //매개변수화된 SQL문 실행 메소드 pstmt
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //변수 삽입
            pstmt.setInt(1, id);
            pstmt.setInt(2, pw);

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
        return id;
    }
}
public class ConnectServer {
    //login_enter(), loginbtnclicked()
    public ConnectServer(int id, int pw) { //Login_Title에서 입력받은 변수 사용
        OnlineServer os = new OnlineServer(id, pw);
        os.InputData(id, pw);
    }
}
