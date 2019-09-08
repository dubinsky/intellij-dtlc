package org.ice1000.tt.psi.mlang

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import org.ice1000.tt.MlangLanguage

class MlangTokenType(debugName: String) : IElementType(debugName, MlangLanguage.INSTANCE) {
	companion object Builtin {
		@JvmField val LINE_COMMENT = MlangTokenType("line comment")
		@JvmField val BLOCK_COMMENT = MlangTokenType("block comment")
		@JvmField val COMMENTS = TokenSet.create(LINE_COMMENT, BLOCK_COMMENT)
		@JvmField val IDENTIFIERS = TokenSet.create(MlangTypes.IDENTIFIER)

		fun fromText(text: String, project: Project) = PsiFileFactory.getInstance(project).createFileFromText(MlangLanguage.INSTANCE, text).firstChild
	}
}
