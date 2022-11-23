package SERVER;

import java.util.Scanner;
//DB를 이용하려면 반드시 사용 해당 클래스를 통해서 프론트와 연결.
public class SERVER {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConnectServer cs = new ConnectServer();
        String cmd = sc.nextLine();

        int id = 2019038054;
        String major = "software";
        int[] user_info = {id, 1, 2, 3, 4, 5, 6, 7, 0};

        //sign up 버튼을 누를 시에만 실행되게 하기.
        if(cmd.equals("sign up")) { //중복되면 에러 남기고 안집어넣음.(자동처리)
            cs.InputData(id, id);               //id pw 변수화
        }
        //login 버튼 시 DB에서 id 찾고 해당 id의 pw비교하여 일치 하면 true
        //id, pw 일치 하지 않을 시 false.
        else if (cmd.equals("login")) {
            boolean flag = cs.FindID(id, id);   //id pw 변수화
            if(flag) System.out.println("아이디 존재.");
        }
        else if( cmd.equals("check")){   //학과는 String 나머지는 int
            cs.CheckList(user_info, major);
        }
        else if(cmd.equals("rewrite")){
            cs.RewriteInfo(id);
        }
        cs.DisconnectServer();
    }
}
