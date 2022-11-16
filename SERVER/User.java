package SERVER;
//사용자에 대한 정보
public class User {
    private int id;
    private int pw;
    private String major;
    //0:id 1:grade 2:leader 3:announce 4:ppt 5:frontend 6:backend 7:selfability 8:teammate
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
