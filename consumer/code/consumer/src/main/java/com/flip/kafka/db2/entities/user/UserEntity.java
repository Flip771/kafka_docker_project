package com.flip.kafka.db2.entities.user;




import javax.persistence.*;


@Entity
@Table(name="user")
public class UserEntity {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;


    public UserEntity(int userId, String userName, String password, String role) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }


    public UserEntity() {
        super();
    }


    public UserEntity( int userId,String userName, String password) {
        super();

        this.userName = userName;
        this.password = password;
        this.userId=userId;

    }



    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public int hashCode() {

        return this.userId;
    }


    @Override
    public boolean equals(Object obj) {


        if(obj==null || !(obj instanceof UserEntity) )
            return false;
        return this.userId==((UserEntity)obj).getUserId();
    }








}

