import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Before
import org.openqa.selenium.remote.DesiredCapabilities
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

    @org.junit.Test
    fun firstTest() {
        println("First test run")
    }
}