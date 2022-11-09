package SERVER;
//사용자에 대한 정보
public class User {
    private int id;
    private int pw;

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
