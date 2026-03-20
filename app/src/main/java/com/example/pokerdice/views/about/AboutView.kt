package com.example.pokerdice.views.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokerdice.about.AboutScreenNavigationIntent
import com.example.pokerdice.ui.theme.almostBlack
import com.example.pokerdice.ui.theme.darkestGrey
import com.example.pokerdice.ui.theme.greyShade
import com.example.pokerdice.ui.theme.neonGreen


const val ABOUT_BACK_BUTTON_TAG = "AboutBackButton"
const val ABOUT_LINK_BUTTON_TAG = "AboutLinkButton"
const val ABOUT_EMAIL_BUTTON_TAG = "AboutEmailButton"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutView(
    modifier: Modifier = Modifier,
    onNavigate: (AboutScreenNavigationIntent) -> Unit = { }
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val gradient = Brush.verticalGradient(
        colors = listOf(almostBlack, greyShade)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About",
                        color = neonGreen,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigate(AboutScreenNavigationIntent.NavigateBack) },
                        modifier = Modifier.testTag(ABOUT_BACK_BUTTON_TAG)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = neonGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkestGrey
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = CardDefaults.cardColors(containerColor = darkestGrey),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Developed by:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = neonGreen,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "- Filipe Carvalho",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Button(
                        onClick = {
                            onNavigate(
                                AboutScreenNavigationIntent.Link(
                                    destination = "https://en.wikipedia.org/wiki/Poker_dice"
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(ABOUT_LINK_BUTTON_TAG),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = neonGreen,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Game explanation:",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = { onNavigate(AboutScreenNavigationIntent.Email) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(ABOUT_EMAIL_BUTTON_TAG),
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(width = 1.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = neonGreen
                        )
                    ) {
                        Text(
                            text = "Contact group",
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
