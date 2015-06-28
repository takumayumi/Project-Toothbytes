/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package models;

public class Accounts {
    private int accountID, authority;
    private String username, password;
    
    public Accounts(){}
    
    public Accounts(int accountID, String username, String password, int authority){
        this.accountID = accountID;
        this.authority = authority;
        this.username = username;
        this.password = password;
    }
    
    public int getAuthority(){
        return authority;
    }
    
    public void setAccountID(int accountID){
        this.accountID = accountID;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public int getAccountID(){
        return accountID;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
}
