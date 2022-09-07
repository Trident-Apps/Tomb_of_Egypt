package au.net.ab.ui.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import au.net.ab.R
import au.net.ab.util.Const
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_activity)

        val splashScreen = installSplashScreen()

        lifecycleScope.launch(Dispatchers.IO) {

            val gadId =
                AdvertisingIdClient.getAdvertisingIdInfo(application.applicationContext).id.toString()
            OneSignal.initWithContext(application.applicationContext)
            OneSignal.setAppId(Const.ONESIGNAL_ID)
            OneSignal.setExternalUserId(gadId)
        }
    }
}