/*
 * Copyright (c) Robin Fey 2024.
 * Alle Rechte vorbehalten.
 * Die Verwendung, Vervielfältigung oder Veränderung dieses Codes oder von Teilen
 * davon, in jeglicher Form, ohne schriftliche Genehmigung des Autors ist untersagt.
 * Eine Genehmigung kann beim Autor angefragt werden unter info@niborblog.de.
 */

package de.niborblog.simpleswipecard

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

/**
 * Swipe card deck
 *
 * Create a stack from a <SwipeCard> list.
 *
 * @author Robin Fey
 * @param modifier
 * @param swipeCards
 * @param moveLeftFunction
 * @param moveRightFunction
 * @receiver
 * @receiver
 */
@Composable
fun SwipeCardDeck(
    modifier: Modifier = Modifier,
    swipeCards: MutableList<SwipeCard>,
    moveLeftFunction: () -> Unit = { /** TODO: NOT IMPLEMENTED **/ },
    moveRightFunction: () -> Unit = { /** TODO: NOT IMPLEMENTED **/ },
) {
    Box {
        swipeCards.reversed().forEachIndexed() { _, card ->
            SwipeCard(
                modifier = modifier,
                moveRightFunction = {
                    swipeCards.remove(card)
                    moveRightFunction()
                }, // transfer the functions that are executed when swiping to the left or right
                moveLeftFunction = {
                    swipeCards.remove(card)
                    moveLeftFunction()
                },
                isEnabled = swipeCards.first() == card
            ) {
                Text(text = card.front) // Overlay the structure of the map here
            }
        }
    }
}

/**
 * Swipe card deck
 *
 * Create a stack from a <SwipeCard> list. With custom Layout
 *
 * @author Robin Fey
 * @param modifier
 * @param swipeCards
 * @param moveLeftFunction
 * @param moveRightFunction
 * @param content
 * @receiver
 * @receiver
 * @receiver
 */
@Composable
fun SwipeCardDeck(
    modifier: Modifier = Modifier,
    swipeCards: MutableList<SwipeCard>,
    moveLeftFunction: () -> Unit = { /** TODO: NOT IMPLEMENTED **/ },
    moveRightFunction: () -> Unit = { /** TODO: NOT IMPLEMENTED **/ },
    content: @Composable (index: Int, card: SwipeCard) -> Unit
) {
    Box {
        swipeCards.reversed().forEachIndexed() { index, card ->
            SwipeCard(
                modifier = modifier,
                moveRightFunction = {
                    swipeCards.remove(card)
                    moveRightFunction()
                }, // transfer the functions that are executed when swiping to the left or right
                moveLeftFunction = {
                    swipeCards.remove(card)
                    moveLeftFunction()
                },
                isEnabled = swipeCards.first() == card
            ) {
                content(index, card) // Overlay the structure of the map here
            }
        }
    }
}

/**
 * Swipe card
 *
 * Create a SwipeCard here, which can be used individually or in a SwipeCardDeck.
 *
 * @author Robin Fey
 * @param modifier
 * @param moveLeftFunction
 * @param moveRightFunction
 * @param content
 * @receiver
 * @receiver
 * @receiver
 */
@Composable
fun SwipeCard(
    modifier: Modifier = Modifier,
    width: Float = 400f,
    height: Float = 700f,
    isEnabled: Boolean = false,
    moveLeftFunction: () -> Unit = { /** TODO: NOT IMPLEMENTED **/ },
    moveRightFunction: () -> Unit = { /** TODO: NOT IMPLEMENTED **/ },
    content: @Composable () -> Unit,
) {
    var offsetX by remember { mutableFloatStateOf(0f) } // Value that changes with the movement
    val rotateLimit = 120f * (width + height) / 2 // Calculation of the rotation limit of the card
    val dragState = rememberDraggableState { delta ->
        offsetX += delta / 2f // Calculate change in movement
    }

    var isHidden by rememberSaveable { // Indicates whether Card has already been swiped or still needs to be swiped
        mutableStateOf(false)
    }

    if (!isHidden) {
        Card(
            modifier = modifier
                .width(width.dp)
                .height(height.dp)
                .offset {
                    IntOffset(
                        offsetX.roundToInt() * (width.toInt() / 140),
                        0
                    )
                } // Represents the horizontal movement, y stay on zero
                .rotate(
                    offsetX.coerceIn(
                        -rotateLimit,
                        rotateLimit
                    ) / 10f
                ) // Tilt the card based on our limits and our horizontal movement
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = dragState,
                    onDragStopped = {
                        if (offsetX < -200f) { // Function that is executed when the screen is weighted to the left, -200f from when the movement is evaluated as a trigger
                            moveLeftFunction()
                            isHidden = true
                        } else if (offsetX > 200f) { // Function that is executed when the screen is weighted to the right
                            moveRightFunction()
                            isHidden = true
                        } else { // It is not executed because the movement was not strong enough to have made a clear decision
                            Log.d("DRAG:", "NONE")
                            //STAY BY CARD
                        }
                        offsetX = 0f; // Reset the State
                    },
                    enabled = isEnabled
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary.copy(
                    alpha = 1 - (offsetX.absoluteValue / 400f).coerceIn(
                        0f,
                        0.6f
                    ) // Change the visibility of the background with the increasing swipe movement
                ), contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            content()
        }
    }
}