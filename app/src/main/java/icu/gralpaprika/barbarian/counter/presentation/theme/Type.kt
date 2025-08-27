package icu.gralpaprika.barbarian.counter.presentation.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import icu.gralpaprika.barbarian.counter.R

val PlayfairDisplay = FontFamily(
    Font(R.font.playfair_display_regular),
    Font(R.font.playfair_display_bold, FontWeight.Bold),
    Font(R.font.playfair_display_black, FontWeight.Black),
    Font(R.font.playfair_display_extrabold, FontWeight.ExtraBold),
    Font(R.font.playfair_display_medium, FontWeight.Medium),
    Font(R.font.playfair_display_semibold, FontWeight.SemiBold),
)

val PlusJakartaSans = FontFamily(
    Font(R.font.plus_jakarta_sans_regular),
    Font(R.font.plus_jakarta_sans_bold, FontWeight.Bold),
    Font(R.font.plus_jakarta_sans_extrabold, FontWeight.ExtraBold),
    Font(R.font.plus_jakarta_sans_extralight, FontWeight.ExtraLight),
    Font(R.font.plus_jakarta_sans_light, FontWeight.Light),
    Font(R.font.plus_jakarta_sans_medium, FontWeight.Medium),
    Font(R.font.plus_jakarta_sans_semibold, FontWeight.SemiBold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = PlayfairDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 100.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

@Preview(name = "Typography Preview", showBackground = true)
@Composable
fun TypographyPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Body Large - Default Typography",
                style = MaterialTheme.typography.bodyLarge
            )
            
            Text(
                text = "Custom Text - Medium Weight",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = "Barbarian Acts: 5",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
