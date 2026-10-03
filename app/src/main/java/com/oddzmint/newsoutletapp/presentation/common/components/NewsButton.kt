package com.oddzmint.newsoutletapp.presentation.common.components

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oddzmint.newsoutletapp.ui.theme.NewsOutletAppTheme

@Composable
fun NewsButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
fun NewsTextButton(
    text: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color.DarkGray
        )
    }

}

@Preview(showBackground = true, name = "NewsButton Preview Light Mode")
@Composable
private fun NewsButtonPreview() {
    NewsOutletAppTheme {
        NewsButton(
            text = "Get Started",
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "NewsButton Preview Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NewsButtonDarkPreview() {
    NewsOutletAppTheme {
        NewsButton(
            text = "Get Started",
            onClick = {}
        )
    }
}