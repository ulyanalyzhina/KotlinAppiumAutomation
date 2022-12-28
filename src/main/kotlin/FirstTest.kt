import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL

open class FirstTest {
    private var driver: AndroidDriver<MobileElement>? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platform", "android")
        capabilities.setCapability("deviceName", "AndroidTestDevice")
        capabilities.setCapability("platformVersion", "8.0")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability(
            "app",
            "/Users/ulyana/IdeaProjects/JavaAppiumAutomation/JavaAppiumAutomation/apks/org.wikipedia.apk"
        )
        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
    }

    @After
    fun tearDown() {
        driver?.quit()
    }

    @Test
    open fun firstTest() {
        waitForElementByAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            15
        )
        waitForElementByAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find element",
            15
        )
        waitForElementPresentBy(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find element",
            15
        )
    }

    @Test
    open fun cancelSearch() {
        waitForElementByAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find Search Wikipedia input",
            10
        )
        waitForElementByAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            10
        )
        waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page",
            10
        )
    }

    @Test
    open fun compareArticleTitle() {
        waitForElementByAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find Search Wikipedia input",
            5
        )
        waitForElementByAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find element",
            5
        )
        waitForElementByAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find element",
            5
        )
        val title_element = waitForElementPresentBy(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            20
        )
        val article_title = title_element.getAttribute("text")
        Assert.assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            article_title
        )
    }

    @Test
    open fun fieldForSearchContainsText() {
        assertElementHasText(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Search Wikipedia",
            "There is now field 'Search Wikipedia' "
        )
    }

    @Test
    open fun cancelSearchOfArticles() {
        //1. Ищем слово "winter"
        waitForElementByAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find Search Wikipedia input",
            5
        )
        waitForElementByAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "winter",
            "Cannot find element",
            15
        )
        //2. Убеждаемся, что найдено несколько статей
        //2.1 Убеждаемся, что отобразился контейнер
        waitForElementPresentBy(
            By.id("org.wikipedia:id/search_results_list"),
            "Cannot find search results list",
            15
        )
        //2.2 Убеждаемся, что статей в контейнере больше чем 1
        waitForElementsListPresentAndToBeMoreThan(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
            "The number of elements is not the number we were waiting for",
            10,
            1
        )
        //3. Отменяем поиск
        waitForElementByAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5
        )

        //4. Убеждаемся, что результат поиска пропал
        waitForElementNotPresent(
            By.id("org.wikipedia:id/search_results_list"),
            "Cannot find search results list",
            5
        )
    }

    private fun waitForElementsListPresentAndToBeMoreThan(
        by: By,
        error_message: String,
        timeout_in_seconds: Long,
        numberOfElements: Int
    ): List<WebElement?>? {
        val wait = WebDriverWait(driver, timeout_in_seconds)
        wait.withMessage(
            error_message + "\n"
        )
        return wait.until(
            ExpectedConditions.numberOfElementsToBeMoreThan(by, numberOfElements)
        )
    }

    private fun waitForElementPresentBy(by: By, error_message: String, timeout_in_seconds: Long): WebElement {
        val wait = WebDriverWait(driver, timeout_in_seconds)
        wait.withMessage(
            error_message + "\n"
        )
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(by)
        )
    }

    private fun waitForElementByAndClick(by: By, error_message: String, timeout_in_seconds: Long): WebElement? {
        val element = waitForElementPresentBy(by, error_message, timeout_in_seconds)
        element.click()
        return element
    }

    private fun waitForElementByAndSendKeys(
        by: By,
        value: String,
        error_message: String,
        timeout_in_seconds: Long
    ): WebElement? {
        val element = waitForElementPresentBy(by, error_message, timeout_in_seconds)
        element.sendKeys(value)
        return element
    }

    private fun waitForElementNotPresent(by: By, error_message: String, timeoutInSeconds: Long): Boolean {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage(
            error_message + "\n"
        )
        return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by)
        )
    }

    private fun assertElementHasText(by: By, expectedValue: String, error_message: String) {
        val elementWithText = waitForElementPresentBy(by, error_message, 5)
        val text = elementWithText.getAttribute("text")
        Assert.assertEquals(
            error_message,
            expectedValue,
            text
        )
    }
}