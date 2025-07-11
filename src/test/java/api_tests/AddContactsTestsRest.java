package api_tests;

import dto.Contact;
import dto.ErrorMessageDto;
import dto.ResponseMessageDto;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

public class AddContactsTestsRest extends ContactController {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void addNewContact_PositiveTest() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("0123456789")
                .email(generateEmail(10))
                .address("Tel Aviv" + generateString(10))
                .description(generateString(15))
                .build();
        Response response = addNewContactRequest(contact, tokenDto);
        System.out.println(response.getStatusLine());
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        if (response.getStatusCode() == 200) {
            responseMessageDto = response.body().as(ResponseMessageDto.class);
        }
        Assert.assertTrue(responseMessageDto.getMessage().contains("Contact was added!"));
    }

    @Test
    public void addNewContact_NegativeTest_wrongToken() {
        Contact contact = Contact.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("0123456789")
                .email(generateEmail(10))
                .address("Tel Aviv" + generateString(10))
                .description(generateString(15))
                .build();
        Response response = addNewContactRequest(contact, tokenDto.builder()
                .token("wrong")
                .build());
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        softAssert.assertEquals(errorMessageDto.getStatus(), 401);
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("JNT strings must contain exactly 2 period characters."));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertAll();
    }
}
