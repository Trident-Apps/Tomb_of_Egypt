package au.net.ab.ui.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import au.net.ab.model.Url
import au.net.ab.model.UrlDatabase
import au.net.ab.util.Const
import au.net.ab.util.TagSender
import au.net.ab.util.UriBuilder
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application, private val db: UrlDatabase) : AndroidViewModel(application) {

    private val sender = TagSender()
    private val builder = UriBuilder()

    val urlLiveData: MutableLiveData<String> = MutableLiveData()

    fun fetchDeeplink(activity: Activity) {
        AppLinkData.fetchDeferredAppLinkData(activity) {
            val deepLink = it?.targetUri.toString()

            if (deepLink == "null") {
                fetchAppsData(activity)
            } else {
                urlLiveData.postValue(builder.createUrl(deepLink, null, activity))
                sender.sendTag(deepLink, null)
            }
        }
    }

    private fun fetchAppsData(activity: Activity) {
        AppsFlyerLib.getInstance().init(Const.APPS_DEV_KEY, object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                urlLiveData.postValue(builder.createUrl("null", data, activity))
                sender.sendTag("null", data)
            }

            override fun onConversionDataFail(data: String?) {}

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {}

            override fun onAttributionFailure(data: String?) {}
        }, activity)
        AppsFlyerLib.getInstance().start(activity)
    }

    fun getUrlFromDb() = db.getDao().getUrl()

    fun saveUrlToDb(url: Url) = viewModelScope.launch(Dispatchers.IO) {
        db.getDao().insertUrl(url)
    }
}