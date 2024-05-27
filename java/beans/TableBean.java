package beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="tableBean")
@RequestScoped
public class TableBean {

    private List<Person> data;
    private String selectedStatus;

    public TableBean() {
        data = new ArrayList<Person>();
        data.add(new Person("John Smith", 35, "New York"));
        data.add(new Person("Jane Doe", 25, "Los Angeles"));
        data.add(new Person("Bob Johnson", 45, "Chicago"));
    }

    public List<Person> getData() {
        return data;
    }

    public void setData(List<Person> data) {
        this.data = data;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public List<String> getStatusOptions() {
        List<String> options = new ArrayList<>();
        options.add("All");
        options.add("Upcoming");
        options.add("Past");
        return options;
    }

    public static class Person {

        private String name;
        private int age;
        private String city;

        public Person(String name, int age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}