
import org.junit.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;

public class TestOption2 {

    String  fistItem;
    String  secondItem;
    int firstItemPrice;
    int secondItemPrice;
    int first = 0;
    int second = 1;

    @Test
    public void buyTwoGoodsUserStory(){
        open("https://www.saucedemo.com/");
        loginAs("standard_user", "secret_sauce");
        addToBasketItems();
        checkItemsInBasket();
        inputInformation("firstName", "lastName", "666");
        checkTotalPrice();
        checkSucsess();
    }

    private void loginAs(String user, String password) {
        $("#user-name").sendKeys(user);
        $("#password").sendKeys(password);
        $("#login-button").click();
    }

    private void addToBasketItems (){
        fistItem = getNameOfItem(first);
        firstItemPrice = getPriceOfItem(first);
        addToBasketItem(first);
        secondItem = getNameOfItem(second);
        secondItemPrice = getPriceOfItem(second);
        addToBasketItem(second);
        $("#shopping_cart_container").click();
    }

    private void checkItemsInBasket(){
        checkItem(first, fistItem);
        checkItem(second, secondItem);
        $(".checkout_button").click();
    }

    private void inputInformation(String firstname, String  lastname, String code){
        $("#first-name").sendKeys(firstname);
        $("#last-name").sendKeys(lastname);
        $("#postal-code").sendKeys(code);
        $("[value='CONTINUE']").click();
    }

    private void checkTotalPrice() {
        int sum = Integer.parseInt($(".summary_subtotal_label").getText()
                .replaceAll("[^0-9]", ""));
        assertEquals(sum , firstItemPrice + secondItemPrice);

        //$(".summary_subtotal_label").shouldHave(text(String.valueOf((firstItemPrice + secondItemPrice))));
        $(byText("FINISH")).click();
    }

    private void checkSucsess(){
        $("#checkout_complete_container").shouldBe(visible);
    }

    private String getNameOfItem(int index){
        return $$(".inventory_item_name").get(index).getText();
    }

    private int getPriceOfItem(int index){
        return Integer.parseInt($$(".inventory_item_price").get(index).getText()
                .replaceAll("[^0-9]", ""));
        //return price[0];
    }

    private void addToBasketItem(int index){
        $$(".btn_inventory").get(index).click();
    }

    private void checkItem(int index, String name){
        $$(".inventory_item_name").get(index).shouldHave(text(name));
    }
}

