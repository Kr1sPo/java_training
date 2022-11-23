package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
  @Test
  public void testContactModification(){
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Ivan1", "Ivanov1", "89274223344", "ivanivanov1@gmail.com"));
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomepage();
  }
}
