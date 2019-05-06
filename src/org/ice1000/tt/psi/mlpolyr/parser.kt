package org.ice1000.tt.psi.mlpolyr

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lexer.FlexAdapter
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.stubs.PsiFileStubImpl
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IStubFileElementType
import com.intellij.psi.tree.TokenSet
import org.ice1000.tt.MLPolyRFile
import org.ice1000.tt.MLPolyRLanguage
import org.ice1000.tt.psi.WHITE_SPACE

class MLPolyRElementType(debugName: String) : IElementType(debugName, MLPolyRLanguage.INSTANCE)

class MLPolyRTokenType(debugName: String) : IElementType(debugName, MLPolyRLanguage.INSTANCE) {
	companion object Builtin {
		@JvmField val COMMENT = MLPolyRTokenType("comment")
		@JvmField val COMMENTS = TokenSet.create(COMMENT)
		@JvmField val IDENTIFIERS = TokenSet.create(MLPolyRTypes.ID, MLPolyRTypes.IDENTIFIER)

		fun fromText(text: String, project: Project) = PsiFileFactory.getInstance(project).createFileFromText(MLPolyRLanguage.INSTANCE, text).firstChild
	}
}

fun mlpolyrLexer() = FlexAdapter(MLPolyRLexer())

class MLPolyRParserDefinition : ParserDefinition {
	private companion object {
		private val FILE = IStubFileElementType<PsiFileStubImpl<MLPolyRFile>>(MLPolyRLanguage.INSTANCE)
	}

	override fun createParser(project: Project?) = MLPolyRParser()
	override fun createLexer(project: Project?) = mlpolyrLexer()
	override fun createElement(node: ASTNode?): PsiElement = MLPolyRTypes.Factory.createElement(node)
	override fun createFile(viewProvider: FileViewProvider) = MLPolyRFile(viewProvider)
	override fun getStringLiteralElements() = TokenSet.EMPTY
	override fun getWhitespaceTokens() = WHITE_SPACE
	override fun getCommentTokens() = MLPolyRTokenType.COMMENTS
	override fun getFileNodeType() = FILE
	// TODO: replace after dropping support for 183
	override fun spaceExistanceTypeBetweenTokens(left: ASTNode?, right: ASTNode?) = ParserDefinition.SpaceRequirements.MAY
}