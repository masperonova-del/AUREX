package com.novapay.app

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AuthInk = Color(0xFF070A11)
private val AuthPanel = Color(0xFF111827)
private val AuthGold = Color(0xFFD9BD79)
private val AuthText = Color(0xFFF3F5F8)
private val AuthMuted = Color(0xFF9AA6B8)
private val AuthBlue = Color(0xFF243653)

@Composable
fun AuthScreen(
    authStorage: AuthStorage,
    onAuthenticated: (AuthUser) -> Unit
) {
    var mode by remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var guestDurationHours by remember { mutableStateOf(24L) }
    var message by remember { mutableStateOf("") }

    BackHandler(enabled = mode != 0) {
        message = ""
        mode = 0
    }

    Surface(modifier = Modifier.fillMaxSize(), color = AuthInk) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.linearGradient(listOf(AuthBlue, AuthPanel))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("N", color = AuthGold, fontSize = 31.sp, fontWeight = FontWeight.Black)
                }
                Spacer(Modifier.width(13.dp))
                Column {
                    Text(
                        "NOVA PAY",
                        color = AuthText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.2.sp
                    )
                    Text(
                        "YOUR WORLD. ONE PAYMENT.",
                        color = AuthMuted,
                        fontSize = 9.sp,
                        letterSpacing = 1.5.sp
                    )
                }
                Spacer(Modifier.weight(1f))
                Box(
                    Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(AuthGold)
                )
            }

            Spacer(Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = AuthPanel),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    when (mode) {
                        0 -> {
                            Text(
                                "Le contrôle, en toute simplicité.",
                                color = AuthText,
                                fontSize = 27.sp,
                                lineHeight = 33.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Découvrez l'expérience NOVA PAY. Choisissez un compte local ou explorez l'application avec un portefeuille temporaire.",
                                color = AuthMuted,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            AuthButton("Créer un compte") {
                                message = ""
                                mode = 1
                            }
                            OutlinedButton(
                                onClick = {
                                    message = ""
                                    mode = 3
                                },
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = AuthText),
                                border = androidx.compose.foundation.BorderStroke(1.dp, AuthGold.copy(alpha = 0.7f))
                            ) {
                                Icon(Icons.Rounded.Schedule, null, tint = AuthGold)
                                Spacer(Modifier.width(8.dp))
                                Text("Créer un portefeuille temporaire")
                            }
                            TextButton(
                                onClick = {
                                    message = ""
                                    mode = 2
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("J'ai déjà un compte • Se connecter", color = AuthMuted)
                            }
                        }

                        1 -> {
                            Text("Créer mon compte", color = AuthText, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                            Text("Le numéro de téléphone est facultatif.", color = AuthMuted, fontSize = 12.sp)
                            AuthField(name, { name = it; message = "" }, "Nom d'affichage")
                            AuthField(email, { email = it; message = "" }, "Adresse e-mail", KeyboardType.Email)
                            AuthField(phone, { phone = it; message = "" }, "Téléphone (facultatif)", KeyboardType.Phone)
                            AuthField(
                                password,
                                { password = it; message = "" },
                                "Mot de passe (10 caractères minimum)",
                                KeyboardType.Password,
                                isPassword = true,
                                passwordVisible = passwordVisible,
                                onTogglePasswordVisibility = { passwordVisible = !passwordVisible }
                            )
                            Text(
                                "L'e-mail sert à retrouver ce compte sur cet appareil. La synchronisation distante n'est pas encore activée.",
                                color = AuthMuted,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                            AuthMessage(message)
                            AuthButton("Créer mon compte") {
                                val result = authStorage.register(
                                    name.trim(),
                                    email.trim(),
                                    phone.trim(),
                                    password
                                )
                                when (result) {
                                    AuthStorageResult.SUCCESS -> {
                                        val user = authStorage.getCurrentUser()
                                        if (user != null) onAuthenticated(user)
                                        else message = "Impossible de charger le compte."
                                    }
                                    AuthStorageResult.ACCOUNT_EXISTS ->
                                        message = "Un compte existe déjà sur cet appareil."
                                    AuthStorageResult.INVALID_DATA ->
                                        message = "Vérifie le nom, l'adresse e-mail et le mot de passe."
                                    AuthStorageResult.ERROR ->
                                        message = "Une erreur est survenue. Réessaie."
                                }
                            }
                            TextButton(onClick = { message = ""; mode = 0 }) {
                                Text("Retour", color = AuthMuted)
                            }
                        }

                        2 -> {
                            Text("Connexion", color = AuthText, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                            AuthField(email, { email = it; message = "" }, "Adresse e-mail", KeyboardType.Email)
                            AuthField(
                                password,
                                { password = it; message = "" },
                                "Mot de passe",
                                KeyboardType.Password,
                                isPassword = true,
                                passwordVisible = passwordVisible,
                                onTogglePasswordVisibility = { passwordVisible = !passwordVisible }
                            )
                            AuthMessage(message)
                            AuthButton("Se connecter") {
                                val success = authStorage.login(email.trim(), password)
                                if (success) {
                                    val user = authStorage.getCurrentUser()
                                    if (user != null) onAuthenticated(user)
                                    else message = "Impossible de charger le compte."
                                } else {
                                    message = "E-mail ou mot de passe incorrect."
                                }
                            }
                            TextButton(onClick = { message = ""; mode = 0 }) {
                                Text("Retour", color = AuthMuted)
                            }
                        }

                        3 -> {
                            Text("Portefeuille invité", color = AuthText, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                            Text(
                                "Explore l'interface sans fournir de numéro de téléphone ni d'adresse e-mail.",
                                color = AuthMuted,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(AuthBlue.copy(alpha = 0.45f))
                                    .padding(14.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Rounded.Shield, null, tint = AuthGold)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Mode découverte uniquement", color = AuthGold, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                }
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    "Aucun argent réel, aucun paiement et aucune carte bancaire ne sont créés. Les données de démonstration de cette session expirent avec le portefeuille.",
                                    color = AuthMuted,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp
                                )
                            }
                            Text("Choisir la durée", color = AuthText, fontWeight = FontWeight.SemiBold)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                GuestDurationButton("1 h", 1L, guestDurationHours, Modifier.weight(1f)) { guestDurationHours = it }
                                GuestDurationButton("24 h", 24L, guestDurationHours, Modifier.weight(1f)) { guestDurationHours = it }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                GuestDurationButton("7 jours", 168L, guestDurationHours, Modifier.weight(1f)) { guestDurationHours = it }
                                GuestDurationButton("30 jours", 720L, guestDurationHours, Modifier.weight(1f)) { guestDurationHours = it }
                            }
                            AuthMessage(message)
                            AuthButton("Créer mon portefeuille") {
                                val durationMillis = guestDurationHours * 60L * 60L * 1000L
                                val result = authStorage.createGuestWallet(durationMillis)
                                if (result == AuthStorageResult.SUCCESS) {
                                    val user = authStorage.getCurrentUser()
                                    if (user != null) onAuthenticated(user)
                                    else message = "Impossible de créer la session temporaire."
                                } else {
                                    message = "La création du portefeuille temporaire a échoué."
                                }
                            }
                            TextButton(onClick = { message = ""; mode = 0 }) {
                                Text("Retour", color = AuthMuted)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
            Text(
                "NOVA PAY • VERSION DE DÉMONSTRATION • AUCUN PAIEMENT RÉEL",
                color = AuthMuted,
                fontSize = 9.sp,
                letterSpacing = 0.6.sp
            )
        }
    }
}

@Composable
private fun GuestDurationButton(
    label: String,
    hours: Long,
    selectedHours: Long,
    modifier: Modifier = Modifier,
    onSelect: (Long) -> Unit
) {
    val selected = hours == selectedHours
    Button(
        onClick = { onSelect(hours) },
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) AuthGold else AuthBlue.copy(alpha = 0.6f),
            contentColor = if (selected) AuthInk else AuthText
        )
    ) {
        Text(label, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium, fontSize = 12.sp)
    }
}

@Composable
private fun AuthMessage(message: String) {
    if (message.isNotBlank()) {
        Text(message, color = Color(0xFFFF8585), fontSize = 12.sp)
    }
}

@Composable
private fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            androidx.compose.ui.text.input.VisualTransformation.None
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { onTogglePasswordVisibility?.invoke() }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                        contentDescription = if (passwordVisible) "Masquer le mot de passe" else "Afficher le mot de passe",
                        tint = AuthMuted
                    )
                }
            }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = AuthText,
            unfocusedTextColor = AuthText,
            focusedBorderColor = AuthGold,
            unfocusedBorderColor = AuthMuted,
            focusedLabelColor = AuthGold,
            unfocusedLabelColor = AuthMuted,
            cursorColor = AuthGold
        )
    )
}

@Composable
private fun AuthButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AuthGold,
            contentColor = AuthInk
        )
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}
