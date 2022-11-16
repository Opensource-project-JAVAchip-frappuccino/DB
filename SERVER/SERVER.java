package SERVER;

import java.util.Scanner;
//DB를 이용하려면 반드시 사용 해당 클래스를 통해서 프론트와 연결.
public class SERVER {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConnectServer cs = new ConnectServer();
        String cmd = sc.nextLine();

        //sign up 버튼을 누를 시에만 실행되게 하기.
        if(cmd.equals("sign up")) { //중복되면 에러 남기고 안집어넣음.
            cs.InputData(2019038054, 2019038054);               //id pw 변수화!!!!!!!!!!!!!!!!!!!11
        }
        //login 버튼 시 DB에서 id 찾고 해당 id의 pw비교하여 일치 하면 true
        //id, pw 일치 하지 않을 시 false.
        else if (cmd.equals("login")) {
            boolean flag = cs.FindID(2019038054, 2019038054);   //id pw 변수화!!!!!!!!!!!!!!!!!!!
            if(flag) System.out.println("아이디 존재.");
        }
        else if( cmd.equals("check")){   //학과는 String 나머지는 int
            cs.CheckList(2019038054);
        }


        cs.DisconnectServer();
    }
}
