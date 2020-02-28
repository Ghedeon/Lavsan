package com.ghedeon.lavsan

import com.ghedeon.lavsan.ui.LavsanDialog
import com.ghedeon.lavsan.ui.MainDialog
import com.intellij.codeInsight.generation.actions.BaseGenerateAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.layout.panel
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.types.typeUtil.supertypes

class SyntheticAction : BaseGenerateAction(null) {
    override fun actionPerformed(event: AnActionEvent?) {
        val project = event?.project
        val file = event?.file()
        if (project == null || file == null) return

        val editor = event.editor()
        if (isLayoutUnderCaret(file, editor)) {
            val layoutIds = getLayoutIds(editor, file)
            val dialog = LavsanDialog(project)
            dialog.bind(layoutIds.ids)
            dialog.show()
            if (dialog.isOK) {
                ImportWriter(project, event.editor(), file, layoutIds).execute()
            }
        }
    }

    private fun getLayoutIds(
        editor: Editor,
        file: KtFile
    ): LayoutIds {
        val layout = Utils.getLayoutFileFromCaret(editor, file)
        val ids = Utils.getIDsFromLayout(layout).map { Id(it.name, it.id, snakeToCamel(it.id)) }.toList()
        val layoutName = layout.name.removeSuffix(".xml")
        val element = file.findElementAt(editor.caretOffset())
        val clazz = PsiTreeUtil.getParentOfType(element, KtClass::class.java)
        val context = clazz?.analyze()
        var isView = true
        clazz?.superTypeListEntries?.forEach {
            val superType = context!![BindingContext.TYPE, it.typeReference]!!
            if (superType.getJetTypeFqName(false) == "android.app.Activity"){
                isView = false
                return@forEach
            }
            run loop@{
                superType.supertypes().forEach {
                    if (it.getJetTypeFqName(false) == "android.app.Activity") {
                        isView = false
                        return@loop
                    }
                }
            }
        }
        val flavor = layout.containingDirectory.parentDirectory!!.parentDirectory!!.name
        return LayoutIds(layoutName, flavor, isView, ids)
    }

    override fun isValidForFile(project: Project, editor: Editor, file: PsiFile): Boolean {
        return file is KtFile && isLayoutUnderCaret(editor, file)
    }

    override fun isValidForClass(targetClass: PsiClass?): Boolean {
        return true
    }
}


fun isLayoutUnderCaret(file: KtFile, editor: Editor): Boolean {
    val element = file.findElementAt(editor.caretOffset())
    val layout = element?.parent?.parent?.firstChild
    return ("R.layout" == layout?.text)
}