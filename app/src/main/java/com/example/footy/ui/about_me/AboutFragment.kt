package com.example.footy.ui.about_me


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.footy.R
import kotlinx.android.synthetic.main.fragment_about.*


/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        messageCard.setOnClickListener { whatsApp() }

    }

    fun whatsApp() {
        val isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp")
        if (isWhatsappInstalled) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=+2001094095356&text=Hey")
                )
            )
        } else {
            Toast.makeText(context, "You don't have whatsapp on your device", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun whatsappInstalledOrNot(uri: String): Boolean {
        val pm: PackageManager? = activity?.packageManager
        var app_installed = false
        app_installed = try {
            pm?.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }
}
