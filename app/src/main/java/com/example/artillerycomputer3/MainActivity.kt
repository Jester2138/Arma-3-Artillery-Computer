package com.example.artillerycomputer3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.artillerycomputer3.ui.theme.ArtilleryComputer3Theme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

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
    // draw UI
    BoxWithConstraints (
        Modifier
            .fillMaxSize()
            .padding(all = 8.dp)) {
        Column {
            // create variables
            var mapCorr: List<String>
            var gunType = rhs_2s1
            var azimuth = 0
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
                TimerButton()
                Box (
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    // help button
                    HelpButton()
                }
            }

            Log.i("Jesse", "Map Correction Received: ($mX, $mY)")

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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            gX = it
                        }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            gY = it
                        }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            gK = it
                        }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            gE = it
                        }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            tX = it
                        }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            tY = it
                        }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            tK = it
                        }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            tE = it
                        }
                    )
                }
            }

            // calc/print azimuth and range on one line
            Row {
                Box(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(fraction = .2F)
                        .height(textLineHeight))
                {
                    Text("Azimuth:")
                }
                Box(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(fraction = .35F)
                        .height(textLineHeight))
                {
                    val gunGrid = readGrid (gX, gY, gK, mX, mY)
                    val tgtGrid = readGrid (tX, tY, tK, mX, mY)
                    // if valid grid inputs
                    if ( ( gunGrid.count() == 2 ) && ( tgtGrid.count() == 2 ) ) {
                        azimuth = (getAzimuth (gunGrid, tgtGrid)).toInt()
                        Text ( "$azimuth mils" )
                    } else { Text ("") }
                }
                Box(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(fraction = .35F)
                        .height(textLineHeight))
                {
                    Text("Range:")
                }
                Box(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(fraction = 1F)
                        .height(textLineHeight))
                {
                    Log.i("Jesse", "Sending gun grid to readGrid: ($gX, $gY, $gK, $mX, $mY)")
                    val gunGrid = readGrid (gX, gY, gK, mX, mY)
                    Log.i("Jesse", "Sending tgt grid to readGrid: ($tX, $tY, $tK, $mX, $mY)")
                    val tgtGrid = readGrid (tX, tY, tK, mX, mY)
                    // if valid grid inputs
                    if ( ( gunGrid.count() == 2 ) && ( tgtGrid.count() == 2 ) ) {
                        Log.i("Jesse", "Valid grid: $gunGrid and $tgtGrid. Getting range...")
                        range = (getRange (gunGrid, tgtGrid)).toInt()
                        Log.i("Jesse", "Range: $range")
                        Text ("$range m")
                    } else {
                        Log.i("Jesse", "Invalid grid.")
                        Text ("")
                    }
                }
            }

            // if valid grind inputs, calculate solutions
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
                        azimuth = azimuth,
                        charge = i,
                        quadElev = quadElevHigh,
                        tof = tofHigh,
                        spread = (tofHigh * gunType.spread).toInt()
                    )
                    val solutionLow = Solution(
                        azimuth = azimuth,
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
                        Log.i("Jesse", "$gunName Solution High: $elevHigh")
                    }
                    if (solutionLow.quadElev in gunType.minQE..gunType.maxQE) {
                        solutions.add(solutionLow)
                        Log.i("Jesse", "$gunName Solution Low: $elevLow")
                    }
                }

                // if solutions exist, draw solutions data table
                if (solutions.isNotEmpty()) {
                    // sort results
                    val solutionsSorted: List<Solution> = solutions.sortedBy { it.quadElev }
                    // draw data table of results
                    activeIndex = tableScreen(solutions = solutionsSorted, activeIndex = activeIndex)
                }
            } else {
                // print that there are no valid solutions

            }
        }
    }
}

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
    Log.i("Jesse", "Timer button opened.")
}

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
                                    "3) Input gun location." +
                                    "\r\n" +
                                    "\r\n" +
                                    "4) Input target location." +
                                    "\r\n" +
                                    "\r\n" +
                                    "5) All possible solutions will be displayed below the inputs." +
                                    "\r\n" +
                                    "\r\n" +
                                    "6) The timer can be used to track time to impact."
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
    Log.i("Jesse", "Help button opened.")
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
    Log.i("Jesse", "Map Correction Input: ($mX, $mY)")
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

    Log.i("Jesse", "Data Table solutions: " + solutions.count().toString())
    Log.i("Jesse", "Data Table fed active index: $activeIndex")

    // receive active solution
    var newActiveIndex by rememberSaveable {mutableStateOf (-1)}

    // set colors
    val activeColor = Color.Red
    val inactiveColor = MaterialTheme.colors.onPrimary

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
                Log.i("Jesse", "Data Table active loop index: $index")
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
                                    Log.i("Jesse", "Data Table tapped row: $index")
                                    newActiveIndex = index
                                }
                            )
                        }
                ) {
                    TableCell(   // column 1
                        text = value.quadElev.toString(),
                        weight = columnWeight,
                        color = if (activeIndex == index) {activeColor} else {inactiveColor}
                    )
                    TableCell(     // column 2
                        text = value.charge.toString(),
                        weight = columnWeight,
                        color = if (activeIndex == index) {activeColor} else {inactiveColor}
                    )
                    TableCell(     // column 3
                        text = value.tof.toString(),
                        weight = columnWeight,
                        color = if (activeIndex == index) {activeColor} else {inactiveColor}
                    )
                    TableCell(     // column 4
                        text = value.spread.toString(),
                        weight = columnWeight,
                        color = if (activeIndex == index) {activeColor} else {inactiveColor}
                    )
                }
            }
        }
    }
    return newActiveIndex
}