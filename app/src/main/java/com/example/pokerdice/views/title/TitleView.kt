package com.example.pokerdice.views.title

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokerdice.R

@Composable
fun TitleView(
    onStartMatch: () -> Unit,
    onStatistics: () -> Unit,
    onAbout: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.cmpd),
            contentDescription = "Title background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = onStartMatch,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Start Match")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onStatistics,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("AI Statistics")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onAbout,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("About")
            }
        }
    }
}