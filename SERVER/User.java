package SERVER;
//사용자에 대한 정보
public class User {
    private int id;
    private int pw;
    private String major;
    /* 1:id, 2:grade, 3:leader 4:announce 5:ppt 6:frontend 7:backend 8:selfability 9:teammate*/
    private int user_info[] = new int[9];

    public void setID(int id){
        this.id = id;
    }
    public void setPW(int pw){
        this.pw = pw;
    }
    public String toString(){
        return "User("+ id + ","+ pw + ")";
    }
}
