package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Ivan").withLastname("Ivanov").withMobilePhone("89274223344")
            .withHomePhone("123456").withWorkPhone("777543").withEmail("ivanivanov@gmail.com");
    app.contact().create(contact);
    app.goTo().homepage();
    assertThat(app.contact().count(),equalTo(before.size()+1));
    Contacts after= app.contact().all();
    assertEquals(after,before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt())));
  }
}
