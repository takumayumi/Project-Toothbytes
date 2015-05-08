/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.model;

/**
 *
 * @author Ecchi Powa
 */
public class Accounts {
    private int accountID;
    private String username, password;
    
    public Accounts(){}
    
    public Accounts(int accountID, String username, String password){
        this.accountID = accountID;
        this.username = username;
        this.password = password;
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
