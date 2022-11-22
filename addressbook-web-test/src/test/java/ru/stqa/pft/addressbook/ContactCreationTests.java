package ru.stqa.pft.addressbook;

import org.testng.annotations.*;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("Ivan", "Ivanov", "89274223344", "ivanivanov@gmail.com"));
    submitContactCreation();
    returnToHomepage();
  }

}
