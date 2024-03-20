package com.example.testapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapp.model.Item
import com.example.testapp.views.MainViewModel

@Composable
fun MainScreen(navController: NavController) {
    val mainViewModel: MainViewModel = viewModel()
    val itemList: List<Item> = mainViewModel.genList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.blue))
            ) {
                val imageView = createRef()
                val outText = createRef()
                val quit = createRef()

                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(imageView) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(72.dp)
                        .padding(4.dp)
                )

                Text(
                    text = "Иван Шишкин",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(outText) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(imageView.end)
                            end.linkTo(quit.start)
                        }
                        .padding(4.dp)
                )

                IconButton(
                    onClick = { navController.navigate("fragment_login") },
                    modifier = Modifier
                        .constrainAs(quit) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(66.dp)
                        .padding(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.out),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

                itemsIndexed(itemList) { index, item ->
                    itemList[index].getTitle()?.let {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        ){
                            Image(
                                painter = painterResource(itemList[index].getPicture()),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(start = 30.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Text(
                                text = it,
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 0.dp)
                                    .clickable {
                                        navController.navigate("fragment_test")
                                    }
                            )
                        }

                    }
                }
            }
        }
    }
}
