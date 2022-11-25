//import ConnectServer.ConnectServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Login_Title extends JFrame
{
    private JButton signup;
    private JButton Login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel Title_panel;
    private JLabel Notice;
    private JLabel Title_img;
    private JPanel Main_panel;
    private JPanel Logine_panel;
    private JScrollPane Notice_Scroll;

    public Login_Title()
    {
        setContentPane(Main_panel);
        setTitle("");
        setSize(1000,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Notice_Scroll.setViewportView(Notice);

        this.login_enter();
        this.loginbtnclicked();
        this.ReadFile();
        this.signupbtn();


    }

    private void loginbtnclicked()
    {
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String ID = textField1.getText();
                char[] PW = passwordField1.getPassword();

                int id = Integer.parseInt(ID);
                int pw = Integer.parseInt(new String(PW));

                ConnectServer cs = new ConnectServer();
                boolean flag = cs.FindID(id, pw);
                if(flag) {
                    System.out.println("아이디 존재.");
                    User_Info info = new User_Info();
                }
                else {
                    //아이디가 없을 시 회원가입을 하라고 메시지 띄울 곳.
                }

                cs.DisconnectServer();
            }
        });
    }
    private void login_enter()
    {
        Action ok = new AbstractAction()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                String ID = textField1.getText();
                char[] PW = passwordField1.getPassword();

                int id = Integer.parseInt(ID);
                int pw = Integer.parseInt(new String(PW));

                ConnectServer cs = new ConnectServer();
                boolean flag = cs.FindID(id, pw);
                if(flag) {
                    System.out.println("아이디 존재.");
                    User_Info info = new User_Info();
                }
                else {
                    //아이디가 없을 시 회원가입을 하라고 메시지 띄울 곳.
                }

                cs.DisconnectServer();
            }
        };
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        passwordField1.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "ENTER");
        passwordField1.getActionMap().put("ENTER", ok);
    }

    private void ReadFile()
    {
        StringBuffer strbuf_notice = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/PatchNote.txt"));
            String str;
            while ((str = reader.readLine()) != null)
            {
                strbuf_notice.append("<html>");
                strbuf_notice.append(str);
                strbuf_notice.append(" <br>");
            }
            reader.close();
            strbuf_notice.append("</html>");
            String str_notice = strbuf_notice.toString();
            this.Notice.setText(str_notice);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signupbtn()
    {
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
                {
                    String ID = textField1.getText();
                    char[] PW = passwordField1.getPassword();

                    int id = Integer.parseInt(ID);
                    int pw = Integer.parseInt(new String(PW));

                    ConnectServer cs = new ConnectServer();

                    if(cs.InputData(id, pw)) {
                        //이미 있는 아이디입니다. 로그인으로 접속해주세요 메세지 띄우기.
                    }
                    if(id == pw) {
                        User_Info info = new User_Info(); //수정
                    }

                    cs.DisconnectServer();

            }
        });
    }

   /* public void findID()
    {
        String ID = textField1.getText();           //id 입력받은 곳
        char[] PW = passwordField1.getPassword();   //pw 입력받은 곳

        int a = Integer.parseInt(ID);
        int b = Integer.parseInt(new String(PW));

        if(DB.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "없는 회원정보입니다.", "회원 가입 필요", JOptionPane.DEFAULT_OPTION);
        }

        for(int i = 0; i < DB.Users.size(); i++)
        {
            if (a == DB.Users.get(i).hakbun && b == DB.Users.get(i).password)
            {
                dispose();
                if(DB.Users.get(i).signup)
                {
                    if(DB.Users.get(i).grade == 0)
                    {
                        Main_Title_professor main = new Main_Title_professor(DB.Users.get(i));
                    }
                    else
                    {
                        Main_Title main = new Main_Title(DB.Users.get(i));
                    }
                }
                else
                {
                    User_Info info = new User_Info(DB.Users.get(i));
                }

            }
            else if (a != DB.Users.get(i).hakbun)
            {
                JOptionPane.showMessageDialog(null, "학번을 정확히 입력하세요.", "학번 확인", JOptionPane.DEFAULT_OPTION);
            }
            if(a == DB.Users.get(i).hakbun && b != DB.Users.get(i).password)
            {
                JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.", "비밀번호 확인", JOptionPane.DEFAULT_OPTION);
            }
        }

    }*/

}
