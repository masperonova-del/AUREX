package com.novapay.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val GuestBannerGold = Color(0xFFD9BD79)
private val GuestBannerPanel = Color(0xFF171D2A)
private val GuestBannerMuted = Color(0xFF9AA6B8)

@Composable
fun GuestWalletExpiryBanner(expiresAtMillis: Long) {
    val expiryLabel = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.FRANCE)
        .format(Date(expiresAtMillis))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(GuestBannerPanel)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Rounded.Schedule, contentDescription = null, tint = GuestBannerGold)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text("PORTEFEUILLE INVITÉ", color = GuestBannerGold, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Text("Session temporaire • Démo uniquement", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text("Expire le $expiryLabel", color = GuestBannerMuted, fontSize = 10.sp)
        }
    }
}
