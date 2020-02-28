package com.ghedeon.lavsan

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtImportsFactory
import org.jetbrains.kotlin.resolve.ImportPath

class GenerateWriter : PsiElementBaseIntentionAction() {
    override fun getFamilyName(): String {
        return "aslfhslfjghsldf"
    }

    override fun isAvailable(p0: Project, p1: Editor?, p2: PsiElement): Boolean {
        return true
    }

    override fun invoke(project: Project, p1: Editor, element: PsiElement) {
        object : WriteCommandAction.Simple<String>(project, element.containingFile) {
            override fun run() {
                val file = PsiTreeUtil.getParentOfType(element, KtFile::class.java)!!
                val importsFactory = KtImportsFactory(project)
                val directives = importsFactory.createImportDirectives(listOf(ImportPath(FqName("kotlinx.android.synthetic.main.activity_main.text"), false, Name.identifier("asfg"))))
                directives.forEach {
                    file.importList?.add(it)
                }
            }
        }.execute()
    }
}