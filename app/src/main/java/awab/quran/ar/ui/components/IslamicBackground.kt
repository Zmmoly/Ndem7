package awab.quran.ar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import awab.quran.ar.R

@Composable
fun IslamicPatternBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // الخلفية - الصورة الأصلية بالضبط
        Image(
            painter = painterResource(id = R.drawable.islamic_pattern),
            contentDescription = "Islamic Pattern Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        // المحتوى فوق الخلفية
        content()
    }
}
