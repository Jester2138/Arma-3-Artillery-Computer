package com.armaApp.armaFDC

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.armaApp.armaFDC.ui.theme.ArtilleryComputer3Theme
import kotlinx.coroutines.delay
import java.lang.Math.round
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.seconds

// test comment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtilleryComputer3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DrawPointTargetUI()
                }
            }
        }
    }
}

@Composable
fun DrawPointTargetUI () {
    // width settings
    val box1width = 0.33F
    val box2width = 0.5F
    val box3width = 0.5F
    val box4width = 1F
    // height settings
    val buttonHeight = 50.dp
    val boxHeight = 60.dp
    val textLineHeight = 30.dp
    // variables
    var mX by rememberSaveable {mutableStateOf ("0")}
    var mY by rememberSaveable {mutableStateOf ("0")}
    var gX by rememberSaveable {mutableStateOf ("")}
    var gY by rememberSaveable {mutableStateOf ("")}
    var gK by rememberSaveable {mutableStateOf ("")}
    var gE by rememberSaveable {mutableStateOf ("")}
    var tX by rememberSaveable {mutableStateOf ("")}
    var tY by rememberSaveable {mutableStateOf ("")}
    var tK by rememberSaveable {mutableStateOf ("")}
    var tE by rememberSaveable {mutableStateOf ("")}
    var activeIndex by rememberSaveable {mutableStateOf (-1)}
    var activeTTI by rememberSaveable {mutableStateOf (0)}
    // draw UI
    BoxWithConstraints (
        Modifier
            .fillMaxSize()
            .padding(all = 8.dp)) {
        Column {
            // create variables
            var mapCorr: List<String>
            var gunType = rhs_2s1
            var azimuthArr: List<Float>
            var range = 0

            // top row of buttons
            Row (
                modifier = Modifier.height(buttonHeight)
            ) {
                // gun type button
                gunType = gunTypeMenu()
                // spacer
                Spacer(modifier = Modifier.width(8.dp))
                // map correction button
                mapCorr = mapCorrPopup(mX, mY)
                mX = mapCorr[0]
                mY = mapCorr[1]
                // spacer
                Spacer(modifier = Modifier.width(8.dp))
                // timer button
                ShotButton(activeTTI)
                // rest is justified right
                Box (
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    // help button
                    HelpButton()
                }
            }

            Log.i("ArmaFDC", "Map Correction Received: ($mX, $mY)")

            // row of gun and map info
            Row {
                Text("Gun: " + gunType.name)
                Box (
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ){
                    Text("Map Corr: $mX, $mY")
                }
            }

            // gun input text box
            val focusManager = LocalFocusManager.current
            Row {
                Box(
                    Modifier
                        .fillMaxWidth(fraction = box1width)
                        .height(boxHeight)) {
                    //var gX by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = gX,
                        label = { Text(text = "gX") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            gX = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth(fraction = box2width)
                        .height(boxHeight)) {
                    //var gY by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = gY,
                        label = { Text(text = "gY") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            gY = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth(fraction = box3width)
                        .height(boxHeight)) {
                    //var gK by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = gK,
                        label = { Text(text = "gK") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            gK = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth(fraction = box4width)
                        .height(boxHeight)) {
                    //var gE by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = gE,
                        label = { Text(text = "gE") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            gE = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.clearFocus() }
                        )
                    )
                }
            }

            // target input text box
            Row {
                Box(
                    Modifier
                        .fillMaxWidth(fraction = box1width)
                        .height(boxHeight)) {
                    OutlinedTextField(
                        value = tX,
                        label = { Text(text = "tX") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            tX = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth(fraction = box2width)
                        .height(boxHeight)) {
                    OutlinedTextField(
                        value = tY,
                        label = { Text(text = "tY") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            tY = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth(fraction = box3width)
                        .height(boxHeight)) {
                    OutlinedTextField(
                        value = tK,
                        label = { Text(text = "tK") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            tK = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Right) }
                        )
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth(fraction = box4width)
                        .height(boxHeight)) {
                    OutlinedTextField(
                        value = tE,
                        label = { Text(text = "tE") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            tE = it
                        },
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.clearFocus() }
                        )
                    )
                }
            }

            // calc/print azimuth and range on one line
            Row {
                Box(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(fraction = 0.5F)
                        .height(textLineHeight)
                ) {
                    Log.i("ArmaFDC", "For azimuth, sending grids to readGrid: ($gX, $gY, $gK, $mX, $mY)")
                    val gunGrid = readGrid (gX, gY, gK, mX, mY)
                    val tgtGrid = readGrid (tX, tY, tK, mX, mY)
                    // if valid grid inputs
                    if ( ( gunGrid.count() == 2 ) && ( tgtGrid.count() == 2 ) ) {
                        Log.i("ArmaFDC", "Valid grid: $gunGrid and $tgtGrid. Getting azimuth...")
                        azimuthArr = getAzimuth(gunGrid, tgtGrid)
                        Log.i("ArmaFDC", "Azimuth: " + azimuthArr[1] + " mils / " + azimuthArr[0] + " degrees")
                        Text ( "Az: " + azimuthArr[1].roundToInt() + " mils / " + azimuthArr[0].roundToInt() + "°" )
                    } else { Text ("") }
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(fraction = 1F)
                        .height(textLineHeight),
                    contentAlignment = Alignment.TopEnd
                )
                {
                    Log.i("ArmaFDC", "For range, sending grids to readGrid: ($gX, $gY, $gK, $mX, $mY)")
                    val gunGrid = readGrid (gX, gY, gK, mX, mY)
                    val tgtGrid = readGrid (tX, tY, tK, mX, mY)
                    // if valid grid inputs
                    if ( ( gunGrid.count() == 2 ) && ( tgtGrid.count() == 2 ) ) {
                        Log.i("ArmaFDC", "Valid grid: $gunGrid and $tgtGrid. Getting range...")
                        range = (getRange (gunGrid, tgtGrid)).toInt()
                        Log.i("ArmaFDC", "Range: $range")
                        Text ("Rng: $range m")
                    } else {
                        Log.i("ArmaFDC", "Invalid grid.")
                        Text ("")
                    }
                }
            }

            // if valid grid inputs, calculate solutions
            val gunGrid = readGrid (gX, gY, gK, mX, mY)
            val tgtGrid = readGrid (tX, tY, tK, mX, mY)
            if ((gunGrid.count()==2) && (tgtGrid.count()==2) && (range > 0)) {
                // calc elevation difference
                val gunElev = if (gE.isEmpty()) { 0 } else { gE.toInt() }
                val tgtElev = if (tE.isEmpty()) { 0 } else { tE.toInt() }
                val elevDiff = tgtElev - gunElev
                // init solution list
                val solutions = mutableListOf<Solution>()
                // iterate through velocities and create solutions
                for (i in gunType.velocities.indices) {
                    val velocity = gunType.velocities[i]
                    val quadElevHigh = getQuadElevHigh(range = range, velocity = velocity, elevDiff = elevDiff)
                    val quadElevLow = getQuadElevLow(range = range, velocity = velocity, elevDiff = elevDiff)
                    val tofHigh = getTOF(range = range, velocity = velocity, quadElev = quadElevHigh)
                    val tofLow = getTOF(range = range, velocity = velocity, quadElev = quadElevLow)
                    val solutionHigh = Solution(
                        //azimuth = azimuth,
                        charge = i,
                        quadElev = quadElevHigh,
                        tof = tofHigh,
                        spread = (tofHigh * gunType.spread).toInt()
                    )
                    val solutionLow = Solution(
                        //azimuth = azimuth,
                        charge = i,
                        quadElev = quadElevLow,
                        tof = tofLow,
                        spread = (tofLow * gunType.spread).toInt()
                    )
                    val gunName = gunType.name
                    val elevHigh = solutionHigh.quadElev
                    val elevLow = solutionLow.quadElev
                    if (solutionHigh.quadElev in gunType.minQE..gunType.maxQE) {
                        solutions.add(solutionHigh)
                        Log.i("ArmaFDC", "$gunName Solution High: $elevHigh")
                    }
                    if (solutionLow.quadElev in gunType.minQE..gunType.maxQE) {
                        solutions.add(solutionLow)
                        Log.i("ArmaFDC", "$gunName Solution Low: $elevLow")
                    }
                }

                // if solutions exist, draw solutions data table
                if (solutions.isNotEmpty()) {
                    // sort results
                    val solutionsSorted: List<Solution> = solutions.sortedBy { it.quadElev }
                    // draw data table of results
                    activeIndex = tableScreen(solutions = solutionsSorted, activeIndex = activeIndex)
                    // set active TTI
                    activeTTI = if (activeIndex > -1) {solutionsSorted[activeIndex].tof} else {0}
                }
            } else {
                // print that there are no valid solutions

            }
        }
    }
}

@Composable
fun ShotButton(tti: Int) {
    var ttiHere by rememberSaveable { mutableStateOf(tti) }
    val counting = rememberSaveable {mutableStateOf(false)}
    var color = MaterialTheme.colors.onPrimary
    //val view = LocalView.current
    Button(
        onClick = {
            counting.value = !counting.value
            Log.i("ArmaFDC", "Timer started...")
        }
    ) {
        Text(
            "TTI: $ttiHere s",
            modifier = Modifier,
            color = color
        )
    }
    if (counting.value) {
        LaunchedEffect(Unit) {
            Log.i("ArmaFDC", "Changing button color to red.")
            color = Color.Red
            // text only seems to change color when the text changes. need a better way...
            ttiHere += 1
            ttiHere -= 1
            // start ticking
            while ((ttiHere > 0) && counting.value) {
                delay(1.seconds)
                ttiHere -= 1
                Log.i("ArmaFDC", "Timer seconds left: $ttiHere")
            }
            counting.value = false
            ttiHere = tti
        }
    } else {
        color = MaterialTheme.colors.onPrimary
        ttiHere = tti
        Log.i("ArmaFDC", "Timer resetting")
    }
}
/*
@Composable
fun TimerButton() {
    MaterialTheme {
        Column {
            val openDialog = rememberSaveable {mutableStateOf(false)}
            Button(
                onClick = { openDialog.value = true }
            ) {
                Text("Timer")
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false },
                    title = {Text (text = "Timer:")},
                    text = {
                        Text (
                            text = "Test"
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
    Log.i("ArmaFDC", "Timer button opened.")
}
*/
@Composable
fun HelpButton() {
    MaterialTheme {
        Column {
            val openDialog = rememberSaveable {mutableStateOf(false)}
            Button(
                onClick = { openDialog.value = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant
                )
            ) {
                Text("?")
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false },
                    title = {Text (text = "Instructions:")},
                    text = {
                        Text (
                            text = "1) Select gun type." +
                                    "\r\n" +
                                    "\r\n" +
                                    "2) Input map correction. Some maps don't use grid [0,0] as the bottom-left. In this case, find which edge grid [0,0] is located on, and input the X or Y value found on the far end of that axis. For example, on Beketov, the map correction would be [0,062]. Leave empty or [0,0] if the map uses grid [0,0] as the bottom-left." +
                                    "\r\n" +
                                    "\r\n" +
                                    "3) Input gun location. gX/gY are X and Y grids. gK is keypad. gE is terrain elevation." +
                                    "\r\n" +
                                    "\r\n" +
                                    "4) Input target location." +
                                    "\r\n" +
                                    "\r\n" +
                                    "5) All possible solutions will be displayed below the inputs." +
                                    "\r\n" +
                                    "\r\n" +
                                    "6) To use TTI (Time To Impact), tap on a solution and the TTI button will show the ToF. Tap the TTI button and it will count down to 0 and then reset. Tap the button mid-count and it will reset." +
                                    "\r\n" +
                                    "\r\n" +
                                    "NOTE: If using a 8 or 10 digit grid format, leave keypad blank or 0."
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
    Log.i("ArmaFDC", "Help button opened.")
}

@Composable
fun mapCorrPopup(mX: String, mY: String): List<String> {
    var mXNew by rememberSaveable {mutableStateOf ("")}
    var mYNew by rememberSaveable {mutableStateOf ("")}
    MaterialTheme {
        Column {
            val openDialog = rememberSaveable {mutableStateOf(false)}
            Button(onClick = {
                openDialog.value = true
            }) {
                Text("Map")
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = { openDialog.value = false },
                    title = {Text (text = "Map Correction:")},
                    text = {
                        Column {
                            Row {
                                Box (
                                    Modifier
                                        .fillMaxWidth(fraction = .5F)
                                ) {
                                    OutlinedTextField(
                                        value = mXNew,
                                        label = { Text(text = "X") },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        onValueChange = { mXNew = it }
                                    )
                                }
                                Box (
                                    Modifier
                                        .fillMaxWidth(fraction = 1F)
                                ) {
                                    OutlinedTextField(
                                        value = mYNew,
                                        label = { Text(text = "Y") },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        onValueChange = { mYNew = it }
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                //mX = mXNew
                            }) {
                            Text("Confirm")
                        }
                    }
                )
            }
        }
    }
    Log.i("ArmaFDC", "Map Correction Input: ($mX, $mY)")
    return listOf(
        (mXNew.ifEmpty { "000" }),
        (mYNew.ifEmpty { "000" })
    )
}

@Composable
fun DropdownMenu(
    expanded: Boolean,
    selectedIndex: Int,
    items: List<String>,
    onSelect: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
    ) {
        Box {
            content()
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
                //offset = DpOffset(x = (-74).dp, y = (0).dp),
                modifier = Modifier
                    .height((guns.count() * 50).dp)
                    .width(250.dp)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                items.forEachIndexed { index, s ->
                    if (selectedIndex == index) {
                        DropdownMenuItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            onClick = { onSelect(index) }
                        ) {
                            Text(
                                text = s,
                                color = MaterialTheme.colors.onPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    } else {
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onSelect(index) }
                        ) {
                            Text(
                                text = s,
                                color = MaterialTheme.colors.onPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }

@Composable
fun gunTypeMenu(): Gun {
    // build array of just gun names from stored guns
    val gunNames = mutableListOf<String>()
    for (gun in guns) {
        gunNames.add(gun.name)
    }
    // init remembered variables
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    // create menu and button
    //val gunName = gunNames[selectedIndex]
    DropdownMenu(
        expanded = expanded,
        selectedIndex = selectedIndex,
        items = gunNames,
        onSelect = { index ->
            selectedIndex = index
            expanded = false
        },
        onDismissRequest = {
            expanded = false
        }
    ) {
        Button(
            onClick = {
                expanded = true
            }
        ) {
            Text(
                text = "Gun",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    return guns[selectedIndex]
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    color: Color
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, MaterialTheme.colors.background)
            .weight(weight)
            .padding(8.dp),
        color = color
    )
}

@Composable
fun tableScreen(solutions: List<Solution>, activeIndex: Int): Int {

    Log.i("ArmaFDC", "Data Table solutions: " + solutions.count().toString())
    Log.i("ArmaFDC", "Data Table fed active index: $activeIndex")

    // receive active solution
    var oldActiveIndex = activeIndex
    if (activeIndex > (solutions.count() - 1)) {oldActiveIndex = 0}
    var newActiveIndex by rememberSaveable {mutableStateOf (-1)}

    // set colors
    val activeColor = Color.Red
    val inactiveColor = MaterialTheme.colors.onBackground

    // Each cell of a column must have the same weight.
    val columnWeight = .2F

    // Data table
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(8.dp)) {

        // Header
        item {
            Row(Modifier.background(MaterialTheme.colors.background)) {
                TableCell(text = "Elev", weight = columnWeight, color = inactiveColor)      // column 1
                TableCell(text = "Charge", weight = columnWeight, color = inactiveColor)    // column 2
                TableCell(text = "ToF", weight = columnWeight, color = inactiveColor)       // column 3
                TableCell(text = "Spread", weight = columnWeight, color = inactiveColor)    // column 4
            }
        }

        // Data
        item {

            // Iterate through given data creating rows
            for ((index, value) in solutions.withIndex()) {
                Log.i("ArmaFDC", "Data Table active loop index: $index")
                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colors.onBackground
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    Log.i("ArmaFDC", "Data Table tapped row: $index")
                                    newActiveIndex = index
                                }
                            )
                        }
                ) {
                    TableCell(   // column 1
                        text = value.quadElev.toString(),
                        weight = columnWeight,
                        color = if (oldActiveIndex == index) {activeColor} else {inactiveColor}
                    )
                    TableCell(     // column 2
                        text = value.charge.toString(),
                        weight = columnWeight,
                        color = if (oldActiveIndex == index) {activeColor} else {inactiveColor}
                    )
                    TableCell(     // column 3
                        text = value.tof.toString(),
                        weight = columnWeight,
                        color = if (oldActiveIndex == index) {activeColor} else {inactiveColor}
                    )
                    TableCell(     // column 4
                        text = value.spread.toString(),
                        weight = columnWeight,
                        color = if (oldActiveIndex == index) {activeColor} else {inactiveColor}
                    )
                }
            }
        }
    }
    return if (newActiveIndex < solutions.count()) {newActiveIndex} else {-1}
}