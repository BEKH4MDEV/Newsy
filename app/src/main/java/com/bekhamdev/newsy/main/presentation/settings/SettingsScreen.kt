package com.bekhamdev.newsy.main.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.core.presentation.utils.countryCodeList
import com.bekhamdev.newsy.main.presentation.components.TopBar
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = {
                    Text(text = "Settings")
                },
                goBack = goBack,
                isSearchVisible = false
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = NewsyTheme.dimens.defaultPadding)
                .fillMaxSize(),
        ) {
            Text(
                text = "Countries",
                color = MaterialTheme.colorScheme.outlineVariant
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = NewsyTheme.dimens.defaultPadding)
            ) {
                Image(
                    painter = painterResource(
                        countryCodeList[0].icRedId
                    ),
                    contentDescription = "Country Flag",
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .size(56.dp)
                )
                Text(
                    text = countryCodeList[0].name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                RadioButton(
                    selected = true,
                    enabled = false,
                    onClick = { /*TODO: Change country*/ }
                )
            }
            HorizontalDivider(thickness = .5.dp)
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NewsyTheme.dimens.defaultPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "USA only – more countries coming soon!",
                    color = MaterialTheme.colorScheme.outlineVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}