package com.ghedeon.lavsan

import com.intellij.ui.layout.panel

fun main(args: Array<String>) {
    val panel = panel {
        noteRow("Login to get notified when the submitted\nexceptions are fixed.")
        row("Username:") { label("sfsdfg") }
        row("Password:") { label("sfsdfg") }
        row {
            label("sfsdfg")
            right {
                link("Forgot password?") { /* custom action */ }
            }
        }
        noteRow("""Do not have an account? <a href="https://account.jetbrains.com/login">Sign Up</a>""")
    }
    panel.isVisible = true
}