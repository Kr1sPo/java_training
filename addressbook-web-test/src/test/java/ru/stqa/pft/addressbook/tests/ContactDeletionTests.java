package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    if(app.contact().all().size()==0){
      app.contact().create(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withMobilePhone("89274223344").withEmail1("ivanivanov@gmail.com"));
    }
    app.goTo().homepage();
  }
  @Test
  public void testContactDeletion() throws Exception {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().homepage();
    assertThat(app.contact().count(),equalTo(before.size()-1));
    Contacts after= app.contact().all();
    assertThat(after, equalTo(before.without(deletedContact)));
  }

}
