/*
 * Copyright (c) Robin Fey 2024.
 * Alle Rechte vorbehalten.
 * Die Verwendung, Vervielfältigung oder Veränderung dieses Codes oder von Teilen
 * davon, in jeglicher Form, ohne schriftliche Genehmigung des Autors ist untersagt.
 * Eine Genehmigung kann beim Autor angefragt werden unter info@niborblog.de.
 */

package de.niborblog.simpleswipecard

import android.os.Bundle
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.core.os.bundleOf

/**
 * Swipe card list saver
 *
 * @constructor Create empty Swipe card list saver
 */
object SwipeCardListSaver : Saver<MutableList<SwipeCard>, List<Bundle>> {
    override fun restore(value: List<Bundle>): MutableList<SwipeCard> {
        return value.map { bundle ->
            SwipeCard(
                bundle.getString("front") ?: "",
                bundle.getString("back") ?: "",
                bundle.getString("author") ?: ""
            )
        }.toMutableList()
    }

    override fun SaverScope.save(value: MutableList<SwipeCard>): List<Bundle> {
        return value.map { swipeCard ->
            bundleOf(
                "front" to swipeCard.front,
                "back" to swipeCard.back,
                "author" to swipeCard.author
            )
        }
    }
}