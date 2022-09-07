package au.net.ab.util

import android.app.Activity
import androidx.core.net.toUri
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import java.util.*

class UriBuilder {
    fun createUrl(
        deepLink: String,
        data: MutableMap<String, Any>?,
        activity: Activity,
    ): String {
        val gadId =
            AdvertisingIdClient.getAdvertisingIdInfo(activity!!).id.toString()

        val url = Const.BASE_URL.toUri().buildUpon().apply {
            appendQueryParameter(Const.SECURE_GET_PARAMETR, Const.SECURE_KEY)
            appendQueryParameter(Const.DEV_TMZ_KEY, TimeZone.getDefault().id)
            appendQueryParameter(Const.GADID_KEY, gadId)
            appendQueryParameter(Const.DEEPLINK_KEY, deepLink)
            appendQueryParameter(Const.SOURCE_KEY, data?.get("media_source").toString())
            if (deepLink == "null") {
                appendQueryParameter(
                    Const.AF_ID_KEY,
                    AppsFlyerLib.getInstance().getAppsFlyerUID(activity!!.applicationContext)
                )
            } else {
                appendQueryParameter(Const.AF_ID_KEY, "null")
            }
            appendQueryParameter(Const.ADSET_ID_KEY, data?.get(DATA_ADSET_ID).toString())
            appendQueryParameter(Const.CAMPAIGN_ID_KEY, data?.get(DATA_CAMPAIGN_ID).toString())
            appendQueryParameter(Const.APP_COMPAIGN_KEY, data?.get(DATA_CAMPAIGN).toString())
            appendQueryParameter(Const.ADSET_KEY, data?.get(DATA_ADSET).toString())
            appendQueryParameter(Const.ADGROUP_KEY, data?.get(DATA_ADGROUP).toString())
            appendQueryParameter(Const.ORIG_COST_KEY, data?.get(DATA_ORIG_COST).toString())
            appendQueryParameter(Const.AF_SITEID_KEY, data?.get(DATA_AF_SITEID).toString())

        }.toString()
        return url
    }

    companion object {
        val DATA_ADSET_ID = "adset_id"
        val DATA_CAMPAIGN_ID = "campaign_id"
        val DATA_CAMPAIGN = "campaign"
        val DATA_ADSET = "adset"
        val DATA_ADGROUP = "adgroup"
        val DATA_ORIG_COST = "orig_cost"
        val DATA_AF_SITEID = "af_siteid"
    }
}