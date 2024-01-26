/*
 * Copyright (c) Robin Fey 2024.
 * Alle Rechte vorbehalten.
 * Die Verwendung, Vervielfältigung oder Veränderung dieses Codes oder von Teilen
 * davon, in jeglicher Form, ohne schriftliche Genehmigung des Autors ist untersagt.
 * Eine Genehmigung kann beim Autor angefragt werden unter info@niborblog.de.
 */

package de.niborblog.simpleswipecard

/**
 * Swipe card Data Class
 *
 * @author Robin Fey
 * @property front
 * @property back
 * @property author
 * @constructor Create empty Swipe card
 */
data class SwipeCard(
    val front: String,
    val back: String,
    val author: String? = "",
)
