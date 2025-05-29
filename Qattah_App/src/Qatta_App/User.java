package Qatta_App;


import java.io.Serializable;



public class User implements Serializable {
    String name;
    double  qattah;

    public User(String name, double qattah) {
        this.name = name;
        this.qattah = qattah;
    }
    
    public User(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
   

   

   
    
    
}
