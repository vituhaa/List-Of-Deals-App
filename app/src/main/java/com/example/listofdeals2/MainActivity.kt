package com.example.listofdeals2

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.listofdeals2.ui.theme.ListOfDeals2Theme
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListOfDealsPreview()
        }
    }
}

@Composable
fun ListOfDeals() {
    val dealList = listOf(
        "Погулять с собакой", "Помыть посуду", "Сделать уроки", "Убраться в комнате"
    )

    var isCheckedState by remember {
        mutableStateOf(dealList.associateWith { false }.toMutableMap())
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Список дел",
            modifier = Modifier.padding(32.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = TextUnit(value = 24f, type = TextUnitType.Sp)
            ),
            fontWeight = FontWeight.ExtraBold
        )
        LazyColumn {
            items(dealList) { deal ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Checkbox(
                        checked = isCheckedState.getValue(deal),
                        onCheckedChange = { isChecked ->
                            isCheckedState = isCheckedState.toMutableMap().apply {
                                put(deal, isChecked)
                            }
                        }
                    )
                    Text(
                        text = deal,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListOfDealsPreview() {
    ListOfDeals2Theme {
        ListOfDeals()
    }
}