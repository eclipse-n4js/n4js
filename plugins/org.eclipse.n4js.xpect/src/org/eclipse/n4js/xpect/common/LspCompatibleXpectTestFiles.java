package org.eclipse.n4js.xpect.common;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.runner.XpectTestFiles.FileRoot;
import org.eclipse.xpect.runner.XpectTestFiles.XpectTestFileCollector;
import org.eclipse.xpect.runner.XpectURIProvider;
import org.eclipse.xtext.util.UriExtensions;

import com.google.common.collect.Lists;

/**
 * Customization that ensures that all produced file URIs do have an empty authority.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@XpectURIProvider(LspCompatibleXpectTestFiles.LspCompatibleXpectTestFileCollector.class)
public @interface LspCompatibleXpectTestFiles {

	/**
	 * @see XpectTestFiles#baseDir()
	 */
	String baseDir() default "";

	/**
	 * @see XpectTestFiles#fileExtensions()
	 */
	String[] fileExtensions() default "xt";

	/**
	 * @see XpectTestFiles#files()
	 */
	String[] files() default {};

	/**
	 * @see XpectTestFiles#relativeTo()
	 */
	FileRoot relativeTo() default FileRoot.PROJECT;

	/**
	 * Wraps all file uris created by the super impl with empty authorities.
	 */
	public static class LspCompatibleXpectTestFileCollector extends XpectTestFileCollector {

		/**
		 * Adapt to default annotation type to allow using the base impl.
		 */
		private static XpectTestFiles asXpectTestFiles(LspCompatibleXpectTestFiles ctx) {
			return new XpectTestFiles() {

				@Override
				public Class<? extends Annotation> annotationType() {
					return XpectTestFiles.class;
				}

				@Override
				public String baseDir() {
					return ctx.baseDir();
				}

				@Override
				public String[] fileExtensions() {
					return ctx.fileExtensions();
				}

				@Override
				public String[] files() {
					return ctx.files();
				}

				@Override
				public FileRoot relativeTo() {
					return ctx.relativeTo();
				}

			};
		}

		private final UriExtensions uriExtensions = new UriExtensions();

		/**
		 * Constructor
		 */
		public LspCompatibleXpectTestFileCollector(Class<?> owner, LspCompatibleXpectTestFiles ctx) {
			super(owner, asXpectTestFiles(ctx));
		}

		@Override
		protected URI createURI(File file) {
			throw new UnsupportedOperationException("Never called by super impl");
		}

		@Override
		public URI deresolveToProject(URI uri) {
			return uri.deresolve(uriExtensions.withEmptyAuthority(getBundle().getRootURI()));
		}

		@Override
		public Collection<URI> getAllURIs() {
			List<URI> result = Lists.newArrayList(super.getAllURIs());
			for (int i = 0; i < result.size(); i++) {
				result.set(i, uriExtensions.withEmptyAuthority(result.get(i)));
			}
			return result;
		}

		@Override
		protected URI resolvePlatformResourceURI(URI uri) {
			URI result = super.resolvePlatformResourceURI(uri);
			return uriExtensions.withEmptyAuthority(result);
		}

		@Override
		protected URI resolveProjectRelativeURI(URI uri) {
			URI result = super.resolveProjectRelativeURI(uri);
			return uriExtensions.withEmptyAuthority(result);
		}

		@Override
		public URI resolveURI(URI base, String newURI) {
			URI uri = super.resolveURI(base, newURI);
			return uriExtensions.withEmptyAuthority(uri);
		}

	}

}
