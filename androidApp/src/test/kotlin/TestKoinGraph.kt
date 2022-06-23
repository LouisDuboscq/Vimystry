import android.content.Context
import com.lduboscq.vimystry.android.di.appModule
import com.lduboscq.vimystry.di.initKoin
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.check.checkModules
import org.mockito.Mockito
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.robolectric.RobolectricTestRunner
import org.koin.test.mock.MockProviderRule

@RunWith(RobolectricTestRunner::class)
class TestKoinGraph {
    private val context = getApplicationContext<Context>()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `checking koin modules`() {
        initKoin {
            androidContext(context)
            modules(appModule)
        }.checkModules()
    }
}