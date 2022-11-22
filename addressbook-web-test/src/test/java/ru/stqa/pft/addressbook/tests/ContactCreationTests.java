package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.initContactCreation();
    app.fillContactForm(new ContactData("Ivan", "Ivanov", "89274223344", "ivanivanov@gmail.com"));
    app.submitContactCreation();
    app.returnToHomepage();
  }

}
