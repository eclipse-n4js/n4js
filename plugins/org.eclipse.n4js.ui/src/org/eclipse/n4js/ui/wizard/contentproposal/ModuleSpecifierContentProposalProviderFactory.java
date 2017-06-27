/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.wizard.contentproposal;

import java.util.Arrays;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Content proposal provider for module specifiers.
 *
 * The proposal lets the user choose from the list of file system children for the path of the content.
 */
public class ModuleSpecifierContentProposalProviderFactory {

	private static final IContentProposal[] EMPTY_PROPOSAL = new IContentProposal[] {};

	/**
	 * Returns a new module specifier proposal provider, which proposes files and folders in the given working
	 * directory.
	 *
	 * @param root
	 *            The working directory path
	 * @return The proposal provider
	 */
	public IContentProposalProvider createProviderForPath(IPath root) {
		return new ModuleSpecifierContentProposalProvider(root);
	}

	/**
	 * A label provider for {@link ModuleSpecifierContentProposalProviderFactory}s proposals
	 */
	public static final class ModuleSpecifierProposalLabelProvider extends LabelProvider {

		private final static Image FOLDER_SYMBOL = PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJ_FOLDER);
		private final static Image FILE_SYMBOL = PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJ_FILE);

		@Override
		public Image getImage(Object element) {
			if (element instanceof ModuleSpecifierProposal) {
				ModuleSpecifierProposal.ModuleProposalType type = ((ModuleSpecifierProposal) element).getModuleType();
				return type == ModuleSpecifierProposal.ModuleProposalType.FOLDER ? FOLDER_SYMBOL : FILE_SYMBOL;
			}
			return super.getImage(element);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof ModuleSpecifierProposal) {
				return ((ModuleSpecifierProposal) element).getLabel();
			}
			return super.getText(element);
		}
	}

	/**
	 * Custom proposal type which also holds information about the {@link ModuleProposalType}.
	 */
	private static final class ModuleSpecifierProposal extends ContentProposal {

		public enum ModuleProposalType {
			FOLDER, MODULE
		}

		private final ModuleProposalType moduleType;

		/**
		 * Creates a module specifier proposal from a given path and type
		 *
		 * @param path
		 *            The path of the proposal
		 * @param moduleType
		 *            The type of the proposal
		 * @return The proposal
		 */
		static ModuleSpecifierProposal createFromPath(IPath path, ModuleProposalType moduleType) {
			String content, label;

			if (moduleType == ModuleProposalType.FOLDER) {
				content = path.removeFileExtension().addTrailingSeparator().toString();
			} else {
				content = path.removeFileExtension().toString();
			}

			if (moduleType == ModuleProposalType.FOLDER) {
				label = path.addTrailingSeparator().toString();
			} else {
				label = path.toString();
			}

			return new ModuleSpecifierProposal(content, label, moduleType);
		}

		public ModuleSpecifierProposal(String content, String label, ModuleProposalType moduleType) {
			super(content, label, "");
			this.moduleType = moduleType;
		}

		public ModuleProposalType getModuleType() {
			return moduleType;
		}
	}

	/**
	 * A content proposal provider for module specifier inside a given root folder.
	 */
	private static class ModuleSpecifierContentProposalProvider implements IContentProposalProvider {

		private final IPath rootFolder;

		/**
		 * Creates a new module specifier content proposal for the given root folder
		 */
		private ModuleSpecifierContentProposalProvider(IPath rootFolder) {
			this.rootFolder = rootFolder;
		}

		@Override
		public IContentProposal[] getProposals(String contents, int position) {
			IContainer proposalRootFolder;

			if (null == rootFolder) {
				return EMPTY_PROPOSAL;
			}

			if (rootFolder.isEmpty()) {
				proposalRootFolder = ResourcesPlugin.getWorkspace().getRoot();
			} else if (rootFolder.segmentCount() == 1) {
				proposalRootFolder = ResourcesPlugin.getWorkspace().getRoot().getProject(rootFolder.segment(0));
			} else {
				proposalRootFolder = findContainerForPath(rootFolder);
			}

			if (null == proposalRootFolder || !proposalRootFolder.exists()) {
				return EMPTY_PROPOSAL;
			}

			// The field content as path
			IPath contentsPath = new Path(contents);

			// The directory to look for prefix matches
			IPath workingDirectoryPath;

			// If the contents path has a trailing separator...
			if (contentsPath.hasTrailingSeparator()) {
				// Use the full content as working directory path
				workingDirectoryPath = contentsPath;
			} else {
				// Otherwise only use complete segments as working directory
				workingDirectoryPath = contentsPath.removeLastSegments(1);
			}

			IContainer workingDirectory;

			if (workingDirectoryPath.segmentCount() > 0) {
				workingDirectory = proposalRootFolder.getFolder(workingDirectoryPath);
			} else {
				workingDirectory = proposalRootFolder;
			}

			// Return an empty proposal list for non-existing working directories
			if (null == workingDirectory || !workingDirectory.exists()) {
				return EMPTY_PROPOSAL;
			}
			try {
				return Arrays.asList(workingDirectory.members()).stream()
						// Only work with files and folders
						.filter(r -> (r instanceof IFile || r instanceof IFolder))
						// Filter by prefix matching
						.filter(resource -> {
							IPath rootRelativePath = resource.getFullPath().makeRelativeTo(rootFolder);
							return rootRelativePath.toString().startsWith(contentsPath.toString());
						})
						// Transform to a ModuleSpecifierProposal
						.map(resource -> {
							// Create proposal path
							IPath proposalPath = resource.getFullPath()
									.makeRelativeTo(proposalRootFolder.getFullPath());
							// Set the proposal type
							ModuleSpecifierProposal.ModuleProposalType type = resource instanceof IFile
									? ModuleSpecifierProposal.ModuleProposalType.MODULE
									: ModuleSpecifierProposal.ModuleProposalType.FOLDER;
							// Create a new module specifier proposal
							return ModuleSpecifierProposal.createFromPath(proposalPath, type);
						})
						.toArray(IContentProposal[]::new);
			} catch (CoreException e) {
				return EMPTY_PROPOSAL;
			}

		}

	}

	/**
	 * Helper method to return the workspace container at the given path.
	 *
	 * Returns null when not found or the element is not of type {@link IContainer}.
	 *
	 * @param path
	 *            workspace absolute path
	 */
	private static IContainer findContainerForPath(IPath path) {
		IResource containerResource = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
		if (containerResource instanceof IContainer) {
			return (IContainer) containerResource;
		}
		return null;
	}
}
