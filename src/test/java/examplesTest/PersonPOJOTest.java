package examplesTest;

import examples.Person;
import examples.PersonPOJO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PersonPOJOTest {
    Person person;

    @BeforeMethod
    public void setup(){
        PersonPOJO.Address myAddress = new PersonPOJO.Address("123 Main St","Anytown","CA","12345");
        PersonPOJO.PhoneNumber phoneNumber1 = new PersonPOJO.PhoneNumber("home","555-1234");
        PersonPOJO.PhoneNumber phoneNumber2 = new PersonPOJO.PhoneNumber("work","555-5678");
        List<PersonPOJO.PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNumber1);
        phoneNumbers.add(phoneNumber2);
        person = new Person("John Smith",35,"male",myAddress,phoneNumbers);
    }

    @Test
    public void convertPOJOToJsonTest(){
        person.convertPOJOToJson();
    }
}
