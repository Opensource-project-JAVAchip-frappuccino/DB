//package SERVER;
import java.sql.*;

public class ConnectServer {
    private Connection conn;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    ConnectServer() { // 서버 연결 함수
        String url = "jdbc:mysql://localhost:3306/marketeam"; //서버
        String user = "root";           //유저명
        String password = "7749";       //비번

        try { //실행구간
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, user, password);
            System.out.println("연결 성공!");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            System.out.println("라이브러리 연결 확인필요");
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

    public boolean InputData(int ID, int pw) { //signup 데이터 삽입 함수 //완성
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO login (id, pw) VALUES (?,?)";
            //매개변수화된 SQL문 실행 메소드 pstmt
            pstmt = this.conn.prepareStatement(sql);
            //변수 삽입
            pstmt.setInt(1, ID);
            pstmt.setInt(2, pw);
            //데이터 저장 확인
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
            return false;
        } catch (SQLException e) {
            System.out.println("ERROR in inputdata");
            System.out.println("SQLException");
            e.printStackTrace();
            return true; //이미 있는 아이디입니다. 출력.
        }
    }//inputdata

    public boolean FindID(int id, int pw){ //login 데이터를 가져오는 함수 //완성
        try {
            String sql = "SELECT id, pw FROM login WHERE id=?";
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setInt(1, id); //입력한 id를 통해서 서버와 확인
            rs = pstmt.executeQuery();
            int ret;
            if(rs.next()){ //일치하는 id를 찾아도 pw가 일치하지 않으면 false 되야함.
                ret = rs.getInt("id");
                //user.setPW(rs.getInt("pw"));
                System.out.println("아이디 = " +ret);
                pstmt.close();
                return CheckPW(pw);
            } else {
                System.out.println("존재하지 하지 않음");
                pstmt.close();
                return false;
            }
        }catch(SQLException e) {
            System.out.println("ERROR in FindID");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in FindID");
            e.printStackTrace();
        }
        return false;
    }//FindID

    public boolean CheckPW(int PW){ //비밀번호 확인 함수 //완성
        try {
            String sql = "SELECT id, pw FROM login WHERE pw=?";
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setInt(1, PW); //입력한 id를 통해서 서버와 확인
            rs = pstmt.executeQuery();
            int ret;
            if(rs.next()){ //일치하는 id를 찾아도 pw가 일치하지 않으면 false 되야함.
                ret = rs.getInt("pw");
                System.out.println("비밀번호 = " + ret);
                pstmt.close();
                return true;
            } else {
                System.out.println("존재하지 하지 않음");
                pstmt.close();
                return false;
            }
        }catch(SQLException e) {
            System.out.println("ERROR in CheckPW");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in CheckPW");
            e.printStackTrace();
        }
        return false;
    }//CheckPW

    public void CheckList(int userlist[], String major, String name){ //체크리스트 저장할 DB 접근 User_info 부분
        //user_info에서 받은 정보를 배열에 삽입하고, 일부는 직접 가져와야함.
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO user_info " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //15
            pstmt = this.conn.prepareStatement(sql);

            /* 1:id, 2:grade, 3:leader 4:announce 5:ppt 6:frontend 7:backend 8:selfability 9:teammate*/
            int i; for(i=0;i<9;i++) {
                pstmt.setInt(i + 1, userlist[i]);
            }

            pstmt.setString(10, name);    //name 이름
            pstmt.setString(11, major);   //major 학과
            pstmt.setInt(12, 0);        //score 그룹 유무

            //해당 부분은 차후에 수정함수로 입력 받기로 함.
            pstmt.setInt(13, 0);        //subject 그룹 유무
            pstmt.setInt(14, 0);        //list 게시글 유무
            pstmt.setInt(15, 0);        //group 경험횟수

            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
        }catch(SQLException e) {
            System.out.println("ERROR in CheckList");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in CheckList");
            e.printStackTrace();
        }
    } //CheckList

    public void RewriteInfo(int ID, int var, int arr[]){ //subject, list, group에 대한 업데이트 함수 
        //arr내부에 변수 들의 수정값 들어있음 또는 따로 들어있음
        try{
            String sql = "UPDATE user_info";
            String tail = "WHERE id =" + ID; //고정되는 ID로 DB 접근.
            stmt = null;
            stmt = conn.createStatement();

            switch (var){
                case 1: //subject 값 변경
                    stmt.executeUpdate(sql + " SET subject = " + arr[0] + " " + tail);
                    break;
                case 2: //list 변경
                    stmt.executeUpdate(sql + " SET list = " + arr[1] + " " + tail);
                    break;
                case 3: //group 변경
                    stmt.executeUpdate(sql + " SET group = " + arr[2] + " " + tail);
                    break;
                default:
                    break;
            }
        }catch(SQLException e) {
            System.out.println("ERROR in Rewriteinfo");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in Rewriteinfo");
            e.printStackTrace();
        }
    }//Rewriteuserinfo 정보수정함수

    public int GetUserinfo(int ID, int num){
        try{
            String sql = "SELECT * FROM user_info WHERE id =" + ID;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int ret = 0;
            switch(num){
                case 1: break;
                case 2: //grade 출력
                    if(rs.next()){
                        ret = rs.getInt("grade");
                        //System.out.println(ret);
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;

                case 3: //leader
                    if(rs.next()){
                        ret = rs.getInt("leader");
                        //System.out.println(ret);
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;

                case 4: //announce
                    if(rs.next()){
                        ret = rs.getInt("announce");
                        //System.out.println(ret);
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;

                case 5: //ppt
                    if(rs.next()){
                        ret = rs.getInt("ppt");
                        //System.out.println(ret);
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;

                case 6: //document
                    if(rs.next()){
                        ret = rs.getInt("document");
                        //System.out.println(ret);
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;

                case 7: //front-back
                    if(rs.next()){
                        ret = rs.getInt("front-back");
                        //System.out.println(ret);
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;

                case 8: //selfability
                    if(rs.next()){
                        ret = rs.getInt("selfability");
                        //System.out.println(ret);
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;

                case 9: //teammate
                    if(rs.next()){
                        ret = rs.getInt("teammate");
                        //System.out.println(ret);
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;

                default:
                    System.out.println("존재하지 하지 않음"); break;
            }
            rs.close();
            stmt.close();
            return ret;
        }catch(SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;
    }//int GetUserinfo

    public String GetUserinfo(int ID, String str) { //완성
        try{
            String sql = "SELECT * FROM user_info WHERE id =" + ID;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            switch(str){
                case "major":
                    if(rs.next()){
                        String major = rs.getString("major");
                        //System.out.println(major);
                        rs.close();stmt.close();
                        return major;
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;
                default:
                    System.out.println("존재하지 하지 않음"); break;
            }
            rs.close();
            stmt.close();
            return "Failed";
        }catch(SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return "Failed";
    }//String GetUserinfo

    public void SetCourse(String [] str, int subnum, int teamnum){ //과목 게시글 생성 페이지(교수만 접근?)
        //교수는 과목을 여러개 맡을 수 있음, 과목이 primary 값을 가짐.
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO subject () VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; //8개
            pstmt = this.conn.prepareStatement(sql);

            pstmt.setInt(1, 0); //인덱스 수 건들지 말기
            for(int i = 0; i < 4; i++)
            {
                pstmt.setString(i+2,str[i]);
            }
            pstmt.setInt(6, 1/*Integer.parseInt(str[4])*/); //학년
            pstmt.setInt(7, subnum); //subnum
            pstmt.setInt(8,teamnum); //teamnum

            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
        }catch(SQLException e) {
            System.out.println("ERROR in SetCourse");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in SetCourse");
            e.printStackTrace();
        }
    }//SetCourse

    public String GetCourse(int i, String str){ //고유 넘버도
        try{
            String sql = "SELECT * FROM subject WHERE num =" + i;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            String ret;
            switch(str){
                case "name":
                    if(rs.next()){
                        ret = rs.getString("prof_name");
                        //System.out.println(ret);
                        rs.close();stmt.close();
                        return ret;
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;
                case "subject":
                    if(rs.next()){
                        ret = rs.getString("subject");
                        //System.out.println(ret);
                        rs.close();stmt.close();
                        return ret;
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;
                case "language":
                    if(rs.next()){
                        ret = rs.getString("language");
                        //System.out.println(ret);
                        rs.close();stmt.close();
                        return ret;
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;
                case "precourse":
                    if(rs.next()){
                        ret = rs.getString("pre-course");
                        //System.out.println(ret);
                        rs.close();stmt.close();
                        return ret;
                    } else { System.out.println("존재하지 하지 않음"); }
                    break;
                default:
                    System.out.println("존재하지 하지 않음"); break;
            }
            rs.close();
            stmt.close();
            return "Failed";
        }catch(SQLException e) {
            System.out.println("ERROR in GetCourse");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetCourse");
            e.printStackTrace();
        }
        return "Failed";
    }//String GetCourse

    public int GetCourse(int i, int num){
        try{
            String sql = "SELECT * FROM subject WHERE num =" + i;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int ret = 0;
            switch(num) {
                case 1: //grade
                    if (rs.next()) {
                        ret = rs.getInt("grade");
                        //System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case 2: //maximum
                    if (rs.next()) {
                        ret = rs.getInt("maximum");
                        //System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case 3: //teamnum
                    if (rs.next()) {
                        ret = rs.getInt("teamnum");
                        //System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                default:
                    System.out.println("존재하지 하지 않음"); break;
            }
            rs.close();
            stmt.close();
            return ret;
        }catch(SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return 0;
    }//int GetCourse

    public int subject_row_size() {
        try {
            String sql = "SELECT * FROM subject";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int size = 0;
            while (rs.next()) {
                size++;
            }
            rs.close();
            stmt.close();
            return size;
        } catch (SQLException e) {
            System.out.println("ERROR in Size");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR in Size");
            e.printStackTrace();
        }
        return 0;
    }

    public String GetTitle(int ID, String name){
        try{
            String sql = "SELECT * FROM title WHERE id =" + ID;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            String ret = "Failed";
            switch(name) {
                case "title" :
                    if (rs.next()) {
                        ret = rs.getString("title");
                        //System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                case "content" :
                    if (rs.next()) {
                        ret = rs.getString("content");
                        //System.out.println(ret);
                    } else {
                        System.out.println("존재하지 하지 않음");
                    }
                    break;
                default:
                    System.out.println("존재하지 하지 않음"); break;
            }
            rs.close();
            stmt.close();
            return ret;
        }catch(SQLException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in GetUserInfo");
            e.printStackTrace();
        }
        return "Failed";
    }

    public int SetTitle(int ID, String title, String content){
        try {
            //매개변수화된 SQL문 작성
            String sql = "INSERT INTO title () VALUES (?, ?, ?)"; //3개
            pstmt = this.conn.prepareStatement(sql);

            pstmt.setInt(1, ID);
            pstmt.setString(2, title); //
            pstmt.setString(3, content); //최대 200자

            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 : " + rows);
            pstmt.close();
        }catch(SQLException e) {
            System.out.println("ERROR in SetTitle");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("ERROR in SetTitle");
            e.printStackTrace();
        }
        return 0;
    }

}
