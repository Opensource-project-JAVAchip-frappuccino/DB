//package SERVER;

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

        String[] src = {"과목", "재구", "자바", "선과목"};

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
            //cs.RewriteInfo(id, 1, arr);
        }
        else if(cmd.equals("get")){
            int var = cs.GetUserinfo(id, 10);
            System.out.println(var);
            String str = cs.GetUserinfo(id, "major");
            System.out.println(str);
        }
        else if(cmd.equals("course")) {
            cs.SetCourse(src,0,0);
        }
        else if(cmd.equals("title")){
            cs.SetTitle(id, "제목을 입력하세요", "게시글은 이렇게 쓸 수 있고 최대 200자 까지 쓸 수 있습니다. 근데 얼마나 적히는지 제대로 알 순 없어요.");
            String str = cs.GetTitle(id, "title");
            System.out.println(str);
            str = cs.GetTitle(id, "content");
            System.out.println(str);
        }
        //String str = cs.GetCourse(1, "name");
        //System.out.println(str);

        //int r = cs.subject_row_size();
        //System.out.println(r);

        cs.DisconnectServer();
    }
}
