package com.vnea.intellij.plugin.movecursortoclass.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.fileEditor.ex.IdeDocumentHistory
import com.intellij.psi.impl.source.PsiJavaFileImpl

class MoveCursorToClassAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val project = e.getData(CommonDataKeys.PROJECT)
        val editor = e.getData(CommonDataKeys.EDITOR)
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)

        e.presentation.isEnabled = project != null && editor != null && psiFile != null && psiFile is PsiJavaFileImpl;
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getRequiredData(CommonDataKeys.PROJECT)
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val psiJavaFile = e.getRequiredData(CommonDataKeys.PSI_FILE) as PsiJavaFileImpl

        val classes = psiJavaFile.classes
        if (classes.isNotEmpty()) {
            val caretModel = editor.caretModel
            caretModel.removeSecondaryCarets()
            caretModel.moveToOffset(classes[0].textOffset)
            editor.selectionModel.removeSelection()
            editor.scrollingModel.scrollToCaret(ScrollType.CENTER_DOWN)
            IdeDocumentHistory.getInstance(project).includeCurrentCommandAsNavigation()
        }

    }
}
