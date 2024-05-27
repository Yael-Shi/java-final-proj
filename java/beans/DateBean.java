/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean (name = "dateBean")
@ApplicationScoped
public class DateBean implements Serializable {

    private List<Date> items;
    private String selectedDate;
    
    public String getSelectedDate() {
        return selectedDate;
    }
    
    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
     }   
    public DateBean() {
        items = new ArrayList<>();
        items.add(new Date());
        items.add(new Date());
        items.add(new Date());
    }
    
    public List<Date> getItems() {
        return items;
    }
    
}

