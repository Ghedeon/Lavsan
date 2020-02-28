package com.ghedeon.lavsan

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtImportsFactory
import org.jetbrains.kotlin.resolve.ImportPath

class ImportWriter(
    project: Project,
    private val editor: Editor,
    private val file: KtFile,
    private val layoutIds: LayoutIds
) : WriteCommandAction.Simple<Any>(project, file) {
    override fun run() {
        val importsFactory = KtImportsFactory(project)

        val importPaths = layoutIds.ids.map { id ->
            ImportPath(
                fqName = FqName(
                    (if (layoutIds.isView) IMPORT_VIEW else IMPORT).format(
                        layoutIds.flavor,
                        layoutIds.layoutName,
                        id.resId
                    )
                ),
                isAllUnder = false,
                alias = Name.identifier(id.alias)
            )
        }
        val directives = importsFactory.createImportDirectives(importPaths)

        directives.forEach { import ->
            file.importDirectives.filter { it.importPath?.pathStr == import.importPath?.pathStr }
                .forEach { it.delete() }
            file.importList?.add(import)
        }
    }
}

private const val IMPORT = "kotlinx.android.synthetic.%s.%s.%s"
private const val IMPORT_VIEW = "kotlinx.android.synthetic.%s.%s.view.%s"