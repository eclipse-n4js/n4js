package org.eclipse.n4js.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.wizard.dependencies.ExternalLibrariesWizard;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Handler for user requesting to fix all problems with projects, delegates to {@link ExternalLibrariesWizard}.
 */
public class LibrariesFixHandler extends AbstractHandler {

	@Inject
	private Provider<ExternalLibrariesWizard> wizardPovider;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		WizardDialog wizardDialog = new WizardDialog(UIUtils.getShell(),
				wizardPovider.get());
		wizardDialog.open();

		return null;
	}

}
