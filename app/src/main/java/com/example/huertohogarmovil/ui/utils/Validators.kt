package com.example.huertohogarmovil.ui.utils

object Validators {

    fun EmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isEmailValid(email: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(regex)
    }
    fun isPasswordValid(password: String): Boolean {
        val regex = "^(?=.*[A-Z])(?=.*[0-9]).{6,}$".toRegex()
        return password.matches(regex)
    }

    fun isCardNumberValid(card: String): Boolean {
        return card.length in 13..19 && card.all { it.isDigit() }
    }

    fun isCVVValid(cvv: String): Boolean {
        return cvv.length in 3..4 && cvv.all { it.isDigit() }
    }

    fun isExpiryValid(expiry: String): Boolean {
        // Formato esperado MM/YY
        if (!expiry.matches(Regex("\\d{2}/\\d{2}"))) return false
        val (month, year) = expiry.split("/")
        return month.toInt() in 1..12
    }

    fun isTextNotEmpty(text: String): Boolean {
        return text.trim().isNotEmpty()
    }
    fun isPhoneValid(phone: String): Boolean {
        // Opcional: si está vacío, es válido
        if (phone.isBlank()) return true

        val regex = "^[0-9]{8,12}$".toRegex()
        return phone.matches(regex)
    }
}
