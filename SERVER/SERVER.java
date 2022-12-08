//package SERVER;
/*
 * 함수 건들지 말 것
 * 함수 건들지 말 것
 * 함수 건들지 말 것
 * 함수 건들지 말 것
 * 함수 건들지 말 것
 * 함수 건들지 말 것
 * 함수 건들지 말 것
 */

import java.util.Scanner;
//해당 함수는 매뉴얼용 함수
public class SERVER {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConnectServer cs = new ConnectServer();
        String cmd = sc.nextLine();

        int id = 2019038054;
        String major = "소프트웨어학과";
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
            cs.CheckList(user_info, major, "김경민");
        }

        else if(cmd.equals("rewrite")){
            cs.RewriteInfo(id, 1);
        }
        else if(cmd.equals("get")){
            int var = cs.GetUserinfo(id, 10);
            System.out.println(var);
            String str = cs.GetUserinfo(id, "major");
            System.out.println(str);
        }
        else if(cmd.equals("course")) {
            cs.SetCourse(2019038053);
        }


        cs.DisconnectServer();
    }
}
