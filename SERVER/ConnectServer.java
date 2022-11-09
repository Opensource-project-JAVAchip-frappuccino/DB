package SERVER;

import java.sql.*;

public class ConnectServer {
    private Connection conn;
    public ConnectServer() { // 서버 연결 함수
        String url = "jdbc:mysql://localhost:3306/marketeam"; //서버
        String user = "root";           //유저명
        String password = "7749";       //비번

        try { //실행구간
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, user, password);
            System.out.println("연결 성공!");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }
    }//public ConnectServer

    public void DisconnectServer() { //서버 연결 해제
        if (conn != null) {
            try {
                conn.close();
                System.out.println("연결 끊기");
            } catch (SQLException e) {
            }
        }
    }//disconnectserver

    public void InputData(int id, int pw) { //signup 데이터 삽입 함수
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO login (id, pw) VALUES (?,?)";
            //매개변수화된 SQL문 실행 메소드 pstmt
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            //변수 삽입
            pstmt.setInt(1, id);
            pstmt.setInt(2, pw);
            //데이터 저장 확인
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
            //이미 있는 아이디입니다. 출력.
        }
    }//inputdata

    public boolean FindID(int id, int pw){ //login 데이터를 가져오는 함수
        try {
            String sql = "SELECT id, pw FROM login WHERE id=?";
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            pstmt.setInt(1, id); //입력한 id를 통해서 서버와 확인
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){ //일치하는 id를 찾아도 pw가 일치하지 않으면 false 되야함.
                User user = new User();
                user.setID(rs.getInt("id"));
                user.setPW(rs.getInt("pw"));
                System.out.println(user.toString());
                pstmt.close();
                return true;
            } else {
                System.out.println("존재하지 하지 않음");
                pstmt.close();
                return false;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }//FindID



}