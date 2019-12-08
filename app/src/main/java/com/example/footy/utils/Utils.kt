package com.example.footy.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.footy.network.Ingredient

fun filterIngredientsList(ingredient: Ingredient): MutableList<String> {
    val ingredientsList = mutableListOf<String>()


    if (ingredient.strIngredient1 != "") ingredientsList.add("•" + ingredient.strIngredient1 + " : " + ingredient.strMeasure1)
    if (ingredient.strIngredient2 != "") ingredientsList.add("•" + ingredient.strIngredient2 + " : " + ingredient.strMeasure2)
    if (ingredient.strIngredient3 != "") ingredientsList.add("•" + ingredient.strIngredient3 + " : " + ingredient.strMeasure3)
    if (ingredient.strIngredient4 != "") ingredientsList.add("•" + ingredient.strIngredient4 + " : " + ingredient.strMeasure4)
    if (ingredient.strIngredient5 != "") ingredientsList.add("•" + ingredient.strIngredient5 + " : " + ingredient.strMeasure5)
    if (ingredient.strIngredient6 != "") ingredientsList.add("•" + ingredient.strIngredient6 + " : " + ingredient.strMeasure6)
    if (ingredient.strIngredient7 != "") ingredientsList.add("•" + ingredient.strIngredient7 + " : " + ingredient.strMeasure7)
    if (ingredient.strIngredient8 != "") ingredientsList.add("•" + ingredient.strIngredient8 + " : " + ingredient.strMeasure8)
    if (ingredient.strIngredient9 != "") ingredientsList.add("•" + ingredient.strIngredient9 + " : " + ingredient.strMeasure9)
    if (ingredient.strIngredient10 != "") ingredientsList.add("•" + ingredient.strIngredient10 + " : " + ingredient.strMeasure10)
    if (ingredient.strIngredient11 != "") ingredientsList.add("•" + ingredient.strIngredient11 + " : " + ingredient.strMeasure11)
    if (ingredient.strIngredient12 != "") ingredientsList.add("•" + ingredient.strIngredient12 + " : " + ingredient.strMeasure12)
    if (ingredient.strIngredient13 != "") ingredientsList.add("•" + ingredient.strIngredient13 + " : " + ingredient.strMeasure13)
    if (ingredient.strIngredient14 != "") ingredientsList.add("•" + ingredient.strIngredient14 + " : " + ingredient.strMeasure14)
    if (ingredient.strIngredient15 != "") ingredientsList.add("•" + ingredient.strIngredient15 + " : " + ingredient.strMeasure15)
    if (ingredient.strIngredient16 != "") ingredientsList.add("•" + ingredient.strIngredient16 + " : " + ingredient.strMeasure16)
    if (ingredient.strIngredient17 != "") ingredientsList.add("•" + ingredient.strIngredient17 + " : " + ingredient.strMeasure17)
    if (ingredient.strIngredient18 != "") ingredientsList.add("•" + ingredient.strIngredient18 + " : " + ingredient.strMeasure18)
    if (ingredient.strIngredient19 != "") ingredientsList.add("•" + ingredient.strIngredient19 + " : " + ingredient.strMeasure19)
    if (ingredient.strIngredient20 != "") ingredientsList.add("•" + ingredient.strIngredient20 + " : " + ingredient.strMeasure20)

    return ingredientsList
}


abstract class ConnectionBroadcastReceiver : BroadcastReceiver() {
    companion object {
        @JvmStatic
        fun registerWithoutAutoUnregister(
            context: Context,
            connectionBroadcastReceiver: ConnectionBroadcastReceiver
        ) {
            context.registerReceiver(
                connectionBroadcastReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }

        @JvmStatic
        fun registerToFragmentAndAutoUnregister(
            context: Context,
            fragment: Fragment,
            connectionBroadcastReceiver: ConnectionBroadcastReceiver
        ) {
            val applicationContext = context.applicationContext
            registerWithoutAutoUnregister(applicationContext, connectionBroadcastReceiver)
            fragment.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    applicationContext.unregisterReceiver(connectionBroadcastReceiver)
                }
            })
        }

        @JvmStatic
        fun registerToActivityAndAutoUnregister(
            activity: AppCompatActivity,
            connectionBroadcastReceiver: ConnectionBroadcastReceiver
        ) {
            registerWithoutAutoUnregister(activity, connectionBroadcastReceiver)
            activity.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    activity.unregisterReceiver(connectionBroadcastReceiver)
                }
            })
        }

        @JvmStatic
        fun hasInternetConnection(context: Context): Boolean {
            val info =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            return !(info == null || !info.isConnectedOrConnecting)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val hasConnection =
            !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
//        Log.d("AppLog", "conenctivity changed. hasConnection? $hasConnection")
        onConnectionChanged(hasConnection)
    }

    abstract fun onConnectionChanged(hasConnection: Boolean)
}


/*ConnectionBroadcastReceiver.registerToFragmentAndAutoUnregister(activity!!, this, object : ConnectionBroadcastReceiver() {
            override fun onConnectionChanged(hasConnection: Boolean) {
                println("FavouritesFragment.onConnectionChanged:$hasConnection")
            }
        })*/