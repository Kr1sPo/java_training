package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    if(app.contact().all().size()==0){
      app.contact().create(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withMobile("89274223344").withEmail("ivanivanov@gmail.com"));
    }
    app.goTo().homepage();
  }
  @Test
  public void testContactDeletion() throws Exception {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().homepage();
    Contacts after= app.contact().all();
    assertEquals(after.size(), before.size()-1);
    assertThat(after, equalTo(before.without(deletedContact)));
  }

}
