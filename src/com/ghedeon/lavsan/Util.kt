package com.ghedeon.lavsan

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import org.jetbrains.kotlin.psi.KtFile

fun AnActionEvent.editor(): Editor = getData(PlatformDataKeys.EDITOR)!!

fun AnActionEvent.file(): KtFile {
    return getData(LangDataKeys.PSI_FILE) as KtFile
}

fun Editor.caretOffset(): Int = caretModel.offset

fun isLayoutUnderCaret(editor: Editor, file: KtFile): Boolean {
    val element = file.findElementAt(editor.caretOffset())
    val layoutRes = element?.parent?.parent?.text

    return layoutRes?.startsWith("R.layout") ?: false
}

fun String.toCamel() = snakeToCamel(this)

fun snakeToCamel(snake: String) =
        snake.split("_").filter { it.isNotEmpty() }.foldRightIndexed("", { idx, str, acc ->
            if (idx == 0) {
                str
            } else {
                str.capitalize()
            } + acc
        })

fun getLayoutIds(editor: Editor, file: KtFile): LayoutIds? {
    return null
}