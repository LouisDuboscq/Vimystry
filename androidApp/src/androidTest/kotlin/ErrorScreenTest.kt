import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.lduboscq.vimystry.android.ErrorScreen
import org.junit.Rule
import org.junit.Test

class ErrorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun errorTextIsDisplayedTest() {
        val error = "This is the problem"
        composeTestRule.setContent {
           ErrorScreen(error)
        }

        composeTestRule.onNodeWithText("Error\n$error").assertIsDisplayed()
    }

}