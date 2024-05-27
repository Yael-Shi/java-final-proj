/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean (name = "imageBean")
@ApplicationScoped
public class ImageBean implements Serializable{
    private String selectedOption;
    private String imagePath;

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        System.out.println("Check2");
        this.selectedOption = selectedOption;
        updateImagePath();
        
    }

    public String getImagePath() {
        return imagePath;
    }

    private void updateImagePath() {
        if (selectedOption == null) {
            imagePath = "";
            return;
        }

        if (selectedOption.equals("Coldplay")) {
            
            imagePath = "/coldplay.jpg";
        }
            
       /* switch (selectedOption) {
            case "Coldplay":
                imagePath = "coldplay.jpg";
                break;
            case "option2":
                imagePath = "/path/to/image2.jpg";
                break;
            case "option3":
                imagePath = "/path/to/image3.jpg";
                break;
            default:
                imagePath = "";
        }
*/
    
}


}