import org.junit.Assert
import org.junit.Test

class MainClassTest {
    @Test
    fun testGetLocalNumber() {
        Assert.assertTrue("Метод getLocalNumber не возвращает 14", MainClass().getLocalNumber() === 14)
    }

    @Test
    fun testGetClassNumber(){
        Assert.assertTrue("Метод getClassNumber возвращает число < 45 или == 45", MainClass().getClassNumber() > 45)
    }
}