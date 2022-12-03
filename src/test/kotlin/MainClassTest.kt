import org.junit.Assert
import org.junit.Test

class MainClassTest {
    @Test
    fun testGetLocalNumber() {
        Assert.assertTrue("Метод getLocalNumber не возвращает 14", MainClass().getLocalNumber() === 14)
    }
}