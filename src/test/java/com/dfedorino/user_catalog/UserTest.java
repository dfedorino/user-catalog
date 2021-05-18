package com.dfedorino.user_catalog;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest extends BaseEntityTest {
    @Test
    public void testGetFirstName() {
        assertThat(new User().getFirstName()).isNull();
    }

    @Test
    public void testGetFamilyName() {
        assertThat(new User().getFirstName()).isNull();
    }

    @Test
    public void testGetDateOfBirth() {
        assertThat(new User().getDateOfBirth()).isNull();
    }

    @Test
    public void testGetAddress() {
        assertThat(new User().getAddress()).isNull();
    }

    @Test
    public void testGetPhoneNumber() {
        assertThat(new User().getPhoneNumber()).isNull();
    }

    @Test
    public void testSetFirstName() {
        User user = new User();
        user.setFirstName("FirstName");
        assertThat(user.getFirstName()).isEqualTo("FirstName");
    }

    @Test
    public void testSetFamilyName() {
        User user = new User();
        user.setFirstName("FamilyName");
        assertThat(user.getFirstName()).isEqualTo("FamilyName");
    }

    @Test
    public void testSetDateOfBirth() {
        User user = new User();
        LocalDate dob = LocalDate.of(1970, Month.JANUARY, 1);
        user.setDateOfBirth(dob);
        assertThat(user.getDateOfBirth()).isEqualTo(dob);
    }

    @Test
    public void testSetAddress() {
        User user = new User();
        user.setAddress("Address");
        assertThat(user.getAddress()).isEqualTo("Address");
    }

    @Test
    public void testSetPhoneNumber() {
        User user = new User();
        user.setPhoneNumber("Phone Number");
        assertThat(user.getPhoneNumber()).isEqualTo("Phone Number");
    }

    @Test
    public void testTestEquals_AllFieldsAreSame_UsersAreEqual() {
        String firstName = "FirstName";
        String familyName = "FamilyName";
        LocalDate dob = LocalDate.of(1970, Month.JANUARY, 1);
        String address = "Address";
        String phone = "Phone Number";
        User first = new User(firstName, familyName, dob, address, phone);
        User second = new User(firstName, familyName, dob, address, phone);
        assertThat(first).isEqualTo(second);
    }

    @Test
    public void testTestEquals_EmptyUsers_UsersAreEqual() {
        User first = new User();
        User second = new User();
        assertThat(first).isEqualTo(second);
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    public void testTestEquals_AllFieldsAreSameExceptName_UsersAreNotEqual() {
        String firstName = "FirstName";
        String familyName = "FamilyName";
        LocalDate dob = LocalDate.of(1970, Month.JANUARY, 1);
        String address = "Address";
        String phone = "Phone Number";
        User first = new User(firstName, familyName, dob, address, phone);
        User second = new User("Second Name", familyName, dob, address, phone);
        assertThat(first).isNotEqualTo(second);
        assertThat(first.hashCode()).isNotEqualTo(second.hashCode());
    }

    @Test
    public void testToString_EmptyUser_ClassNameAndAllFieldsAreNull() {
        User user = new User();
        String expectedToString = "User(firstName=null, familyName=null, dateOfBirth=null, address=null, phoneNumber=null)";
        assertThat(user.toString()).isEqualTo(expectedToString);
    }

    @Test
    public void testToString_FilledUser_ClassNameAndAllFieldsWithValues() {
        String firstName = "First Name";
        String familyName = "Family Name";
        LocalDate dateOfBirth = LocalDate.of(1970, Month.JANUARY, 1);
        String address = "Address";
        String phoneNumber = "Phone Number";
        User user = new User(firstName, familyName, dateOfBirth, address, phoneNumber);

        String expectedToString = "User(" +
                "firstName=" + firstName +
                ", familyName=" + familyName +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                ")";
        assertThat(user.toString()).isEqualTo(expectedToString);
    }
}