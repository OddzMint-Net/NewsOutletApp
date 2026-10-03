package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.oddzmint.newsoutletapp.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp
) {
    Image(
        painter = painterResource(id = R.drawable.splash),
        contentDescription = "NewsOutletApp",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(RectangleShape)
            .scale(1.6f)
    )
}