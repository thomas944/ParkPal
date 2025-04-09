package com.example.parkpalv1.ui.components.plan

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.data.model.openrouter.BudgetLevel

@Composable
fun Budget(
    currBudgetLevel: BudgetLevel,
    setBudgetLevel: (BudgetLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Budget",
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.selectableGroup()
    ) {
        BudgetLevel.entries.forEach { budget ->
            FilterChip(
                selected = currBudgetLevel == budget,
                onClick = { setBudgetLevel(budget) },
                label = { Text(budget.name) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }

}