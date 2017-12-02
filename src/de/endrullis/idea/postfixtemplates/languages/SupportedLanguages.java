package de.endrullis.idea.postfixtemplates.languages;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiCompiledElement;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import de.endrullis.idea.postfixtemplates.language.CptLang;
import de.endrullis.idea.postfixtemplates.language.CptUtil;
import de.endrullis.idea.postfixtemplates.languages.java.JavaLang;
import de.endrullis.idea.postfixtemplates.languages.javascript.JavaScriptLang;
import de.endrullis.idea.postfixtemplates.languages.kotlin.KotlinLang;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static de.endrullis.idea.postfixtemplates.utils.CollectionUtils._List;

/**
 * All supported languages of this plugin.
 *
 * @author Stefan Endrullis &lt;stefan@endrullis.de&gt;
 */
public class SupportedLanguages {

	private static final List<CptLang> supportedLanguages = _List(
		new JavaLang(),
		new JavaScriptLang(),
		new KotlinLang()
	);

	private static final HashMap<String, CptLang> languageToCptLang = new HashMap<String, CptLang>() {{
		supportedLanguages.forEach(lang -> put(lang.getLanguage(), lang));
	}};

	public static CptLang getCptLang(@NotNull final String language) {
		return languageToCptLang.get(language);
	}

	public static Optional<CptLang> getCptLang(@NotNull final PsiElement element) {
		final VirtualFile vFile = element.getContainingFile().getViewProvider().getVirtualFile().getCanonicalFile();
		if (vFile == null) {
			return Optional.empty();
		}

		final String language = CptUtil.getLanguageOfTemplateFile(vFile);

		return Optional.of(getCptLang(language));
	}

}
