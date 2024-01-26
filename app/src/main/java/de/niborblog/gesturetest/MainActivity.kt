/*
 * Copyright (c) Robin Fey 2024.
 * Alle Rechte vorbehalten.
 * Die Verwendung, Vervielfältigung oder Veränderung dieses Codes oder von Teilen
 * davon, in jeglicher Form, ohne schriftliche Genehmigung des Autors ist untersagt.
 * Eine Genehmigung kann beim Autor angefragt werden unter info@niborblog.de.
 */

package de.niborblog.gesturetest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.niborblog.gesturetest.ui.theme.GestureTestTheme
import de.niborblog.simpleswipecard.SwipeCard
import de.niborblog.simpleswipecard.SwipeCardDeck
import de.niborblog.simpleswipecard.SwipeCardListSaver

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestureTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "TEst")
                        /*                        SwipeCard(
                                                    moveLeftFunction = {
                                                        Log.d("DRAG:","ACCEPT")
                                                    },
                                                    moveRightFunction = {
                                                        Log.d("DRAG:","DENIED")
                                                    }
                                                ){
                                                    Text(
                                                        text = "Hello!"
                                                    )
                                                }*/


                        val cards = rememberSaveable(saver = SwipeCardListSaver) {
                            mutableStateListOf(
                                SwipeCard("Front1", "Back1"),
                                SwipeCard("Front2", "back2")
                            )
                        }

                        SwipeCardDeck(swipeCards = cards,
                            moveLeftFunction = {
                                Log.d("DRAG:", "ACCEPT")
                            },
                            moveRightFunction = {
                                Log.d("DRAG:", "DENIED")
                            }) { _, card ->
                            Text(text = card.front)
                        }
                    }

                }
            }
        }
    }
}


