package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactPhoneTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    if(app.contact().all().size()==0){
      app.contact().create(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withMobilePhone("89274223344")
              .withHomePhone("123456").withWorkPhone("777543").withEmail("ivanivanov@gmail.com"));
    }
    app.goTo().homepage();
  }

  @Test
  public void testContactPhones(){
    app.goTo().homepage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
  }
}
