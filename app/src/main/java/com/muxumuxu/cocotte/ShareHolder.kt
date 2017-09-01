package com.muxumuxu.cocotte

import android.content.Context
import android.content.Intent
import com.muxumuxu.cocotte.data.Food

// Lol

private fun getEmoji(food: Food): String {
    return (when (food.danger) {
        "avoid" -> "⛔️ "
        "care" -> "⚠️️ "
        else -> "✅ "
    })
}

private fun share(context: Context, message: String) {
    val intent = Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_TEXT, message)
            .setType("text/plain")
    context.startActivity(intent)
}

fun shareFoods(context: Context, foods: List<Food>) {
    share(context, context.getString(
            R.string.share_list_foods,
            foods.fold("") { acc, food -> acc + "${getEmoji(food)}${food.name}\n" }))
}

fun shareFood(context: Context, food: Food) {
    val message = "${context.getString(
            context.resources.getIdentifier("share_food_${food.danger}", "string", context.packageName))}\n" +
            "--\n" +
            "${food.name}\n" +
            "${getEmoji(food)}${getFoodDanger(context, food)}\n" +
            context.getString(R.string.share_see_more, context.getString(R.string.store_url, context.packageName))

    share(context, message)
}

fun shareApp(context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_app_subject))
            .putExtra(Intent.EXTRA_TEXT, getStoreUrl(context))
            .setType("text/plain")

    context.startActivity(intent)
}

fun getStoreUrl(context: Context): String {
    return context.getString(R.string.store_url, context.packageName)
}

fun getFoodDanger(context: Context, food: Food): String {
    return context.getString(context.resources.getIdentifier(food.danger, "string", context.packageName))
}