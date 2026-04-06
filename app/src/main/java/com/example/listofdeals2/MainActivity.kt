package com.example.listofdeals2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.listofdeals2.database.Deal
import com.example.listofdeals2.database.DealsDao
import com.example.listofdeals2.database.DealsDatabase
import kotlinx.coroutines.launch

class MainActivity () : ComponentActivity() {
    private lateinit var dao: DealsDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(
            applicationContext,
            DealsDatabase::class.java,
            "dealsDatabase"
        ).build()
        dao = db.dealDao()
        setContent{
            DealListApp(dao = dao)
        }
    }
}

@Composable
fun Header() {
    Text(
        text = "Deals list",
        fontFamily = FontFamily.Cursive,
        modifier = Modifier.padding(22.dp)
            .padding(top = 22.dp),
        style = TextStyle(
            color = Color.Black,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun AddDeal(dao: DealsDao) {
    var deals by remember {
        mutableStateOf(emptyList<Deal>())
    }

    var newDealText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    fun deleteDealButton(deal: Deal) { // понадобилась, тк не могу передать deals
                                        // в следующую функцию
        scope.launch {
            dao.deleteDeal(deal)
            deals = dao.loadAllDeals()
        }
    }

    scope.launch {
        deals = dao.loadAllDeals()
    }


    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    )
    {
        OutlinedTextField(
            value = newDealText,
            onValueChange = {
                newDealText = it
            },
            modifier = Modifier.width(250.dp)
                               .height(48.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        Button(
            onClick = {
                if (newDealText.isNotBlank()) {
                    scope.launch {
                        dao.insertDeal(Deal(dealName = newDealText))
                        deals = dao.loadAllDeals()
                        newDealText = ""
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.lightpink),
            )
        ) {
            Text(text = "Add deal")
        }
    }
    LazyColumn {
        items(deals) { deal ->
            CheckBoxDeals(dealText = deal.dealName, deals = deals, deal, dao,
                deleteButton = { deal -> deleteDealButton(deal) }
            )
        }
    }
}

@Composable
fun CheckBoxDeals(dealText: String, deals: List<Deal>, deal: Deal, dao: DealsDao,
                  deleteButton: (Deal) -> Unit) {
    var isCheckedState by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isCheckedState,
            onCheckedChange = { isCheckedState = it },
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(id = R.color.pink),
                uncheckedColor = Color.DarkGray,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = dealText,
            color = if (!isCheckedState) Color.Black else Color.White,
            modifier = Modifier.weight(1f) // выстраивает строки задач красиво
                .background(colorResource(id = R.color.light2pink).copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        )
        IconButton(onClick = {
            deleteButton(deal)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_outline_24),
                contentDescription = "Delete deal"
            )
        }
    }
}

@Composable
fun DealListApp(dao: DealsDao) {
    Column(
        modifier = Modifier.fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background_image),
                contentScale = ContentScale.Crop
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        AddDeal(dao)
    }
}


//@Preview(showBackground = true)
//@Composable
//fun ListOfDealsPreview() {
//    ListOfDeals2Theme {
//        DealListApp()
//    }
//}