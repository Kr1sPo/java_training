package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Ivan").withLastname("Ivanov").withMobile("89274223344").withEmail("ivanivanov@gmail.com");
    app.contact().create(contact);
    app.goTo().homepage();
    Contacts after= app.contact().all();
    Assert.assertEquals(after.size(), before.size()+1);
    Assert.assertEquals(after,before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt())));
  }
}
