
package com.example.parkpalv1.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.plan.Plan
import com.example.parkpalv1.ui.viewmodel.PlanViewModel

@Composable
fun PlanScreen(
    onBackClick: () -> Unit,
    planViewModel: PlanViewModel,
    modifier: Modifier = Modifier
) {
    Plan(
        onBackClick = onBackClick,
        planViewModel = planViewModel,
    )
}