package com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.khalekuzzaman.just.cse.dsavisualizer.core.realm.realm.DB

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RealmUI() {
    FlowRow {
        Button(onClick = {
            DB.create()
        }) {
            Text(text = "Create")
        }
        Button(onClick = {
            DB.getAll()
        }) {
            Text(text = "GetAll")
        }

    }

}