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
        if (this.conn != null) {
            try {
                this.conn.close();
                System.out.println("연결 끊기");
            } catch (SQLException e) {
            }
        }
    }//disconnectserver

    public boolean InputData(int ID, int pw) { //signup 데이터 삽입 함수
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO login (id, pw) VALUES (?,?)";
            //매개변수화된 SQL문 실행 메소드 pstmt
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            //변수 삽입
            pstmt.setInt(1, ID);
            pstmt.setInt(2, pw);
            //데이터 저장 확인
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
            return false;
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
            return true; //이미 있는 아이디입니다. 출력.
        }
    }//inputdata

    public boolean FindID(int ID, int pw){ //login 데이터를 가져오는 함수
        try {
            String sql = "SELECT id, pw FROM login WHERE id=?";
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            pstmt.setInt(1, ID); //입력한 id를 통해서 서버와 확인
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

    public void CheckList(int userlist[], String major){ //체크리스트 저장할 DB 접근 User_info 부분
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO user_info () VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //15th
            PreparedStatement pstmt = this.conn.prepareStatement(sql);

            //user_info에서 받은 정보를 배열에 삽입하고, 일부는 직접 가져와야함.
            /* 1:id, 2:grade, 3:leader 4:announce 5:ppt 6:frontend 7:backend 8:selfability 9:teammate*/
            int i; for(i=0;i<9;i++) {
                pstmt.setInt(i + 1, userlist[i]);
            }
            pstmt.setString(10, major); //major
            pstmt.setInt(11, 1); //group 그룹 유무
            pstmt.setInt(12, 0); //list 게시글 유무
            pstmt.setInt(13, 1); //career 경험횟수
            pstmt.setDouble(14, 4.4); //rating 평점
            pstmt.setInt(15, 4); //score 평점


            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    } //CheckList

    public void RewriteInfo(int ID){ //userinfo 수정
        //user_info 전체 수정. 여러개 접근?
        try{
            String tail = "WHERE id = ID"; //고정되는 ID로 DB 접근.
            Statement stmt = null;
            stmt = conn.createStatement();

            stmt.executeUpdate("UPDATE user_info SET grade = " + tail);
            stmt.executeUpdate("UPDATE user_info SET leader = " + tail);
            stmt.executeUpdate("UPDATE user_info SET announce = " + tail);
            stmt.executeUpdate("UPDATE user_info SET ppt = " + tail);
            stmt.executeUpdate("UPDATE user_info SET frontend = " + tail);
            stmt.executeUpdate("UPDATE user_info SET backend = " + tail);
            stmt.executeUpdate("UPDATE user_info SET selfability = " + tail);
            stmt.executeUpdate("UPDATE user_info SET teammate = " + tail);
            stmt.executeUpdate("UPDATE user_info SET major= " + tail);
            
        }catch(SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }//Rewrite userinfo 수정 함수
}