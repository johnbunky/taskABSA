
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

    @Test
    public void sum(){
        int[] arr = new int[]{1, 3, 5, 7, 9};

        int sum = 0;
        //Loop through the array to calculate sum of elements
        for (int i = 0; i < arr.length; i++) {
            sum = sum + arr[i];
        }
        int mixSum = sum - getMax(arr);// Calling getMax() method for getting max value
        System.out.print(mixSum);
        int maxSum = sum - getMin(arr); // Calling getMin() method for getting min value
        System.out.print(" " + maxSum);
    }

    // Method for getting the maximum value
    private static int getMax(int[] inputArray){
        int maxValue = inputArray[0];
        for(int i=1;i < inputArray.length;i++){
            if(inputArray[i] > maxValue){
                maxValue = inputArray[i];
            }
        }
        return maxValue;

    }

    // Method for getting the minimum value
    private static int getMin(int[] inputArray){
        int minValue = inputArray[0];
        for(int i=1;i<inputArray.length;i++){
            if(inputArray[i] < minValue){
                minValue = inputArray[i];
            }
        }
        return minValue;
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

