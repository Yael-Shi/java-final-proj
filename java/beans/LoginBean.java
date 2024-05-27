/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import jakarta.enterprise.context.RequestScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


@ManagedBean (name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    private String artist;
    private String venue;
    private String date;

 public LoginBean() {
    this.username = "default";
    this.password = "password";
 }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
     public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = venue;
    }
    
    public String login() {
        if(username.equals("admin") && password.equals("password")) {
            return "success.xhtml";
        }
        else {
            FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage("Invalid credentials"));
            return null;
        }
    }    
    public void addNewShow() {
    //enter code here for inserting new shows to database
    //this.artist//
    //this.venue
    //this.date
    
    }
}
    

