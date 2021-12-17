package ru.aasmc.jetreddit.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.aasmc.jetreddit.R

@Composable
fun JoinButton(
    onClick: (Boolean) -> Unit = {}
) {
    var buttonState: JoinButtonState by remember {
        mutableStateOf(JoinButtonState.IDLE)
    }

    val shape = RoundedCornerShape(corner = CornerSize(12.dp))

    val transition = updateTransition(
        targetState = buttonState,
        label = "JoinButtonTransition"
    )

    val duration = 600
    val buttonBackgroundColor: Color
            by transition.animateColor(
                transitionSpec = { tween(duration) },
                label = "Button background color"
            ) { state ->
                when (state) {
                    JoinButtonState.IDLE -> Color.Blue
                    JoinButtonState.PRESSED -> Color.White
                }
            }

    val buttonWidth: Dp
            by transition.animateDp(
                transitionSpec = { tween(duration) },
                label = "Button width"
            ) { state ->
                when (state) {
                    JoinButtonState.IDLE -> 70.dp
                    JoinButtonState.PRESSED -> 32.dp
                }
            }

    val textMaxWidth: Dp
            by transition.animateDp(
                transitionSpec = { tween(duration) },
                label = "Text max width"
            ) { state ->
                when (state) {
                    JoinButtonState.IDLE -> 40.dp
                    JoinButtonState.PRESSED -> 0.dp
                }
            }

    val iconAsset: ImageVector =
        if (buttonState == JoinButtonState.PRESSED) {
            Icons.Default.Check
        } else {
            Icons.Default.Add
        }

    val iconTintColor: Color
            by transition.animateColor(
                transitionSpec = { tween(duration) },
                label = "Icon tint color"
            ) { state ->
                when (state) {
                    JoinButtonState.IDLE -> Color.White
                    JoinButtonState.PRESSED -> Color.Blue
                }
            }

    Box(
        modifier = Modifier
            .clip(shape = shape)
            .border(width = 1.dp, color = Color.Blue, shape = shape)
            .background(color = buttonBackgroundColor)
            .size(width = buttonWidth, height = 24.dp)
            .clickable(onClick = {
                buttonState =
                    if (buttonState == JoinButtonState.IDLE) {
                        onClick.invoke(true)
                        JoinButtonState.PRESSED
                    } else {
                        onClick.invoke(false)
                        JoinButtonState.IDLE
                    }
            }),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = iconAsset,
                contentDescription = stringResource(id = R.string.plus_icon),
                tint = iconTintColor,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = stringResource(id = R.string.join),
                color = Color.White,
                fontSize = 12.sp,
                maxLines = 1,
                modifier = Modifier
                    .widthIn(
                        min = 0.dp,
                        max = textMaxWidth
                    )
            )
        }

    }
}

enum class JoinButtonState {
    IDLE,
    PRESSED
}

@Preview
@Composable
fun JoinButtonPreview() {
    JoinButton()
}















