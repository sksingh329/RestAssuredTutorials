package examples;

import java.util.List;

public class PersonPOJO {
    private String name;
    private int age;
    private String gender;
    private Address address;
    private List<PhoneNumber> phoneNumbers;

    public PersonPOJO(String name, int age, String gender, Address address, List<PhoneNumber> phoneNumbers) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
    }

    public static class PhoneNumber{
        private String type;
        private String number;

        public PhoneNumber(String type, String number){
            this.type = type;
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

    public static class Address{
        private String street;
        private String city;
        private String state;
        private String zip;


        public Address(String street,String city,String state, String zip){
            this.street = street;
            this.city = city;
            this.state = state;
            this.zip = zip;
        }
        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }
    }

    public Address getAddress(){
        return this.address;
    }

    public void setAddress(Address address){
        this.address = address;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
