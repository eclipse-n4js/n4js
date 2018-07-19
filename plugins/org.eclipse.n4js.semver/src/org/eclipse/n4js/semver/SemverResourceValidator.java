package org.eclipse.n4js.semver;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.ResourceValidatorImpl;

import com.google.inject.Inject;

public class SemverResourceValidator extends ResourceValidatorImpl {

	@Inject
	private OperationCanceledManager operationCanceledManager;

	public List<Issue> validate(Resource resource, EObject root, CheckMode mode, CancelIndicator mon) {
		List<Issue> result = new ArrayList<>();
		IAcceptor<Issue> acceptor = createAcceptor(result);

		TreeIterator<EObject> allContentsIter = root.eAllContents();
		while (allContentsIter.hasNext()) {
			EObject eObject = allContentsIter.next();
			operationCanceledManager.checkCanceled(mon);
			validate(resource, eObject, mode, mon, acceptor);
		}

		return result;
	}

}
