package com.muxumuxu.cocotte

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_more.*

class MoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        version.text = getString(R.string.version, BuildConfig.VERSION_NAME, "#${BuildConfig.VERSION_CODE}")

        // FIXME: Some externalisation with ShareHolder maybe maybelline
        rate.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getStoreUrl())))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, R.string.invalid_store_url, Toast.LENGTH_SHORT).show()
            }
        }

        contact.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_EMAIL, contactEmail)
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.contact_subject))
                    .setType("text/html")

            startActivity(intent)
        }

        share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_app_subject))
                    .putExtra(Intent.EXTRA_TEXT, getStoreUrl())
                    .setType("text/plain")

            startActivity(intent)
        }
    }

    private fun getStoreUrl(): String {
        return getString(R.string.store_url, packageName)
    }
}