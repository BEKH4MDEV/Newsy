package com.bekhamdev.newsy.main.presentation.detail.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bekhamdev.newsy.R
import com.bekhamdev.newsy.ui.theme.NewsyTheme

@Composable
fun InformationDetail(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    content: String,
    author: String,
    url: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(NewsyTheme.dimens.itemSpacing))
        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.height(NewsyTheme.dimens.defaultSpacing))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape
                        )
                        .background(MaterialTheme.colorScheme.background)
                        .clip(CircleShape)
                        .padding(NewsyTheme.dimens.mediumPadding)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(35.dp),
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Author Icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Spacer(modifier = Modifier.width(NewsyTheme.dimens.defaultSpacing))
                Text(
                    text = "By",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = author,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = TextDecoration.Underline
                )
            }

            val context = LocalContext.current
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            Box(
                modifier = Modifier
                    .weight(.6f),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = {
                        context.startActivity(intent)
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.go_to_site),
                        contentDescription = "Go to Site",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(28.dp)
                            .rotate(90f)
                    )
                }
            }
        }
        Spacer(Modifier.height(NewsyTheme.dimens.defaultSpacing))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal
        )
    }
}