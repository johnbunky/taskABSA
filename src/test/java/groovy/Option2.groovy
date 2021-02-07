package groovy

import org.junit.Test

import static com.codeborne.selenide.Selectors.*
import static com.codeborne.selenide.Condition.*
import static com.codeborne.selenide.Selenide.*

class Option2 {

    def fistItem
    def secondItem
    def firstItemPrice
    def secondItemPrice
    def first = 0
    def second = 1

    @Test
    void buyTwoGoodsUserStory(){
        open"https://www.saucedemo.com/"
        loginAs'standard_user', 'secret_sauce'
        addToBasketItems()
        checkItemsInBasket()
        inputInformation 'firstName', 'lastName', '666'
        checkTotalPrice()
        checkSucsess()
    }




    private loginAs(def user, def password) {
        $('#user-name').sendKeys(user)
        $("#password").sendKeys(password)
        $('#login-button').click()
    }

    private addToBasketItems (){
        fistItem = getNameOfItem first
        firstItemPrice = getPriceOfItem first
        addToBasketItem first
        secondItem = getNameOfItem second
        secondItemPrice = getPriceOfItem second
        addToBasketItem second
        $('#shopping_cart_container').click()
    }

    private checkItemsInBasket(){
        checkItem(first, fistItem)
        checkItem(second, secondItem)
        $('.checkout_button').click()
    }

    private inputInformation(def firstname, def lastname, def code){
        $('#first-name').sendKeys(firstname)
        $('#last-name').sendKeys(lastname)
        $('#postal-code').sendKeys(code)
        $('[value="CONTINUE"]').click()
    }

    private checkTotalPrice() {
        $('.summary_subtotal_label').shouldHave(text((firstItemPrice + secondItemPrice).toString()))
        $(byText('FINISH')).click()
    }

    private checkSucsess(){
        $('#checkout_complete_container').shouldBe(visible)
    }

    private  getNameOfItem(def index){
         $$('.inventory_item_name').get(index).getText()
    }

    private getPriceOfItem(def index){
        def price = $$('.inventory_item_price').get(index).getText()
                .findAll( /-?\d+\.\d*|-?\d*\.\d+|-?\d+/ )*.toDouble()
        return price[0]
    }

    private addToBasketItem(int index){
        $$('.btn_inventory').get(index).click()
    }

    private checkItem(int index, def name){
        $$('.inventory_item_name').get(index).shouldHave(text(name))
    }
}
