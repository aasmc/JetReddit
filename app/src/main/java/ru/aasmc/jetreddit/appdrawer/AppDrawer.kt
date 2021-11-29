package ru.aasmc.jetreddit.appdrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.aasmc.jetreddit.R
import ru.aasmc.jetreddit.theme.JetRedditThemeSettings

@Composable
fun AppDrawer(
    modifier: Modifier = Modifier,
    closeDrawerAction: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        AppDrawerHeader()

        AppDrawerBody(
            closeDrawerAction = closeDrawerAction
        )

        AppDrawerFooter(
            modifier = modifier
        )
    }

}


@Composable
private fun AppDrawerHeader(

) {

}


@Composable
private fun ProfileInfo() {

}

@Composable
private fun ProfileInfoItem(
    iconAsset: ImageVector,
    amountResourceId: Int,
    textResourceId: Int,
    modifier: Modifier
) {

}

/**
 * Represents App Drawer actions:
 *  - screen navigation
 *  - app light / dark theme switch
 */
@Composable
private fun AppDrawerBody(
    closeDrawerAction: () -> Unit
) {

}

/**
 * Represents component in the AppDrawer that the user can use to change the screen.
 */
@Composable
private fun ScreenNavigationButton(
    icon: ImageVector,
    label: String,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val surfaceModifier = Modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()

    Surface(
        modifier = surfaceModifier,
        color = colors.surface,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = onClickAction,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = label,
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = label,
                    fontSize = 10.sp,
                    style = MaterialTheme.typography.body2,
                    color = colors.primaryVariant
                )
            }
        }
    }
}


@Composable
private fun AppDrawerFooter(
    modifier: Modifier = Modifier
) {

}

private fun changeTheme() {
    JetRedditThemeSettings.isInDarkTheme.value = JetRedditThemeSettings.isInDarkTheme.value.not()
}

@Preview
@Composable
private fun ProfileInfoItemPreview() {
    ProfileInfoItem(
        iconAsset = Icons.Filled.ShoppingCart,
        amountResourceId = R.string.default_reddit_age_amount,
        textResourceId = R.string.reddit_age,
        modifier = Modifier
    )
}

















