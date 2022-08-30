package com.example.firstcomposeactivity.accompanist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.flowlayout.FlowRow
import java.io.UnsupportedEncodingException

class AccompanistActivty: ComponentActivity() {

    val EMOJIS = listOf(
        "U+1F44F",
        "U+1F525",
        "U+1F602",
        "U+2764",
        "U+1F622",
        "U+1F929",
        "U+1F644"
    )

    val list = mutableListOf<String>()

    var emoji = "U+1F44F"
    var text = emoji.replace("U+", "0x")

    val b = StringBuilder()
//    var em = b.appendCodePoint(text.toInt())

//    val rr = text.toString(16)


//    var aaa = BigInteger(text, 16)

    fun convertToNewEmoji(): String {
        val b = StringBuilder()

//        b.appendCodePoint(text.toString(16))

        return b.toString()
    }


    fun convertToEmo(): String {
        val b = StringBuilder()

        for (c in "U+1F44F".toCharArray()) {
            if (c.code >= 128) b.append("\\u")
                .append(String.format("%04X", c.code)) else b.append(c)
        }

        return b.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val convertToEmo = convertToEmo()


        Log.i("ConvertToEmo", convertToEmo)

        val listOfStat = listOf<String>("Ganeshddd", "hegde", "Nikhillllllldwsdfsl"/*, "Helllloooooo"*/)

        val newList = convertToEmoji()

        var answerString = CheckCLass().getEmoji()

        setContent {
            Column {
//                StatsSection(duration = "Duration", stats = listOfStat)

                for (list in answerString) {
                    Text(text = list)
                }

//                Text(text = em.toString())
            }
        }
    }

    fun convertToEmoji(): MutableList<String> {

        for (emoji in EMOJIS) {
            try {
                //I am assuming you are getting unicode from an inputbox

                //I am also assuming you are getting emoji in hexadecimal form `U+<hexdigits>`
                var unicodeHexEmoji = "U+"
                val sb = java.lang.StringBuilder()

                //Firstly you want to encode emojis to unicode types by converting to byte array
                val utf8Bytes = emoji.toByteArray(charset("UTF-8")) // "\\uf0\\u9f\\u98\\u80"
                val utf16Bytes = emoji.toByteArray(charset("UTF-16")) // "\\ud83d\\ude00"

                //convert emoji to hex
                for (b in utf16Bytes) {
                    sb.append(String.format("%02x", b))
                }
                //we are converting our current emoji to hex just for the purpose of this example
                unicodeHexEmoji += sb //yields "U+feffd83dde21";
                val utfHexBytes: ByteArray =
                    getByteFromHex(unicodeHexEmoji.replace("U+", "")) // "\\ud83d\\ude00"
                //NB: we removed "U+" because its only a prefix denoting that the string is a <hex>

                //Decoding our unicodes back to emoji string
                val emojiUTF_8 = utf8Bytes.decodeToString()
                val emojiUTF_16 = /*String(utf16Bytes *//*"UTF-16"*//*)*/ utf16Bytes.decodeToString()
                val emojiUTF_hex = /*String(utfHexBytes *//*"UTF-16"*//*)*/ utfHexBytes.decodeToString()
                Log.d("Emoji", "emojiUTF_8 : $emojiUTF_8")
                Log.d("Emoji", "emojiUTF_16 : $emojiUTF_16")
                Log.d("Emoji", "emojiUTF_hex : $emojiUTF_hex")
                //output
                //emojiUTF-8 : 😀
                //emojiUTF-16 : 😀
                //emojiUTF-hex : 😡

                list.add(emojiUTF_hex)


            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }

        return list
    }


    fun getByteFromHex(hexString: String): ByteArray {
        //To convert hex string to byte array, you need to first get the length
        //of the given string and include it while creating a new byte array.
        val `val` = ByteArray(hexString.length / 2)
        for (i in `val`.indices) {
            val index = i * 2
            val j = hexString.substring(index, index + 2).toInt(16)
            `val`[i] = j.toByte()
        }
        return `val`
    }
}



@OptIn(ExperimentalFoundationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun StatsSection(modifier: Modifier = Modifier, duration: String, stats: List<String>) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val flowRowWidth = screenWidth.minus(80.dp)
    val itemWidth = flowRowWidth/2

    Log.d("Width","$screenWidth")
    Log.d("flowRowWidth","$flowRowWidth")
    Log.d("itemWidth","$itemWidth")

    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (statsRef, durationRef, statsRefCheck) = createRefs()

        Text(
            text = "String",
            modifier = Modifier.constrainAs(durationRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        )
        FlowRow(modifier = Modifier
            .width(flowRowWidth)
            .constrainAs(statsRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(durationRef.bottom, margin = 36.dp)
            }
            .background(Color.Blue)) {

            for (index in stats.indices) {
                Text(text = stats[index],
                    modifier = Modifier
                        .width(itemWidth)
                        .background(
                            Color.Yellow
                        )
                        .padding(bottom = 32.dp /*start = getStartMargin(index, stats)*/))
            }
        }
    }
}

/*
fun getStartMargin(index: Int, stats: List<String>): Dp = if (index % 2 == 0) {
    75.dp
} else 0.dp
*/



/*@Composable

fun LiveStreamEndStat(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    stat: Int,
    @StringRes subHeader: Int,
    showShimmer: Boolean = false
) {
    Row(modifier = modifier, verticalAlignment = Alignment.Top) {
        Image(
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .placeholder(
                    visible = showShimmer,
                    highlight = PlaceholderHighlight.shimmer(),
                ),
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = subHeader)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(

                text = "Text"
            )
            Text(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .placeholder(
                        visible = showShimmer,
                        highlight = PlaceholderHighlight.shimmer(),
                    ),
                text = stringResource(id = subHeader),
                color = AppTheme.colors.disable,
                style = AppTheme.typography.subhead.let {
                    if (showShimmer) {
                        it.copy(fontSize = 9.sp)
                    } else {
                        it
                    }
                }
            )
        }
    }
}*/
