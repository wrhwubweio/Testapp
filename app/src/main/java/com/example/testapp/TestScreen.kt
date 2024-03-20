package com.example.testapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapp.views.TestViewModel

@Composable
fun TestScreen(navController: NavController,
               questionDao: QuestionDao,
               lifecycleOwner: LifecycleOwner) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.lite_gray))
    ) {
        val nameTest = createRef()
        val question = createRef()
        val timer = createRef()
        val endTest = createRef()
        val left = createRef()
        val right = createRef()
        val a1Layout = createRef()
        val a2Layout = createRef()
        val a3Layout = createRef()
        val a4Layout = createRef()
        val testViewModel: TestViewModel = viewModel()
        var textQuestion by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            testViewModel.loadDataQuestions(questionDao)
        }

        testViewModel.question.observe(lifecycleOwner, Observer { question ->
            textQuestion = question
        })

        Text(
            text = "Тест",
            modifier = Modifier.constrainAs(nameTest) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(72.dp)
            },
            style = TextStyle(
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = textQuestion,
            modifier = Modifier
                .constrainAs(question) {
                top.linkTo(nameTest.bottom, 40.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.value(380.dp)
                height = Dimension.value(129.dp)
            },
            style = TextStyle(
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        )

        Text(
            text = "15:00",
            modifier = Modifier.constrainAs(timer) {
                bottom.linkTo(endTest.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(64.dp)
            },
            style = TextStyle(
                color = Color.Gray,
                fontSize = 32.sp
            )
        )


        Button(
            onClick = { navController.navigate("fragment_main") },
            modifier = Modifier.constrainAs(endTest) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(64.dp)
                width = Dimension.value(156.dp)
            }
        ) {
            Text(
                text = "Завершить",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        Button(
            onClick = { testViewModel.MinusIndex(questionDao) },
            modifier = Modifier.constrainAs(left) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(endTest.start)
                height = Dimension.value(64.dp)
                width = Dimension.value(64.dp)
            }
        ) {
            Text(
                text = "<",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        Button(
            onClick = { testViewModel.AddIndex(questionDao) },
            modifier = Modifier.constrainAs(right) {
                bottom.linkTo(parent.bottom)
                start.linkTo(endTest.end)
                end.linkTo(parent.end)
                height = Dimension.value(64.dp)
                width = Dimension.value(64.dp)
            }
        ) {
            Text(
                text = ">",
                color = Color.White,
                fontSize = 20.sp
            )
        }


        TestAnswer(Modifier.constrainAs(a1Layout) {
            top.linkTo(question.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(90.dp)
        }, 0, testViewModel, lifecycleOwner)
        TestAnswer(Modifier.constrainAs(a2Layout) {
            top.linkTo(a1Layout.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(90.dp)
        }, 1, testViewModel, lifecycleOwner)
        TestAnswer(Modifier.constrainAs(a3Layout) {
            top.linkTo(a2Layout.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(90.dp)
        }, 2, testViewModel, lifecycleOwner)
        TestAnswer(Modifier.constrainAs(a4Layout) {
            top.linkTo(a3Layout.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(90.dp)
        }, 3, testViewModel, lifecycleOwner)
    }
}

@Composable
fun TestAnswer(modifier: Modifier, ind: Int, viewModel: TestViewModel, viewLifecycleOwner: LifecycleOwner) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val aText = createRef()
        val a = createRef()
        var textAnswer by remember { mutableStateOf("") }

        viewModel.listAnswers.observe(viewLifecycleOwner, Observer { answers ->
            textAnswer = answers[ind]
        })

        Text(
            text = textAnswer,
            modifier = Modifier.constrainAs(aText) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(a.end)
                width = Dimension.value(320.dp)
            },
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            )
        )

        IconButton(
            onClick = { /* Обработка нажатия */ },
            modifier = Modifier.constrainAs(a) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                width = Dimension.value(64.dp)
                height = Dimension.value(64.dp)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.circle),
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

