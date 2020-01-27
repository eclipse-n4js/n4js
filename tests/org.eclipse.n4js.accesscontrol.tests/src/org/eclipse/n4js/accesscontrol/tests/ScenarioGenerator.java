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
package org.eclipse.n4js.accesscontrol.tests;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.tests.codegen.Class;
import org.eclipse.n4js.tests.codegen.Classifier;
import org.eclipse.n4js.tests.codegen.Field;
import org.eclipse.n4js.tests.codegen.Getter;
import org.eclipse.n4js.tests.codegen.Interface;
import org.eclipse.n4js.tests.codegen.Member;
import org.eclipse.n4js.tests.codegen.Method;
import org.eclipse.n4js.tests.codegen.Module;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.tests.codegen.Setter;
import org.eclipse.n4js.tests.issues.IssueExpectations;

/**
 * Generates scenarios to be used by {@link AccessControlTest}. The created scenarios comprise of one or more N4JS
 * projects that are created at a given path according to a test specification. Each scenario tests access control for a
 * given specification and member type.
 */
class ScenarioGenerator {

	/** The project type for N4JS projects used in access control tests. */
	private static final ProjectType PROJECT_TYPE = ProjectType.VALIDATION;

	private final TestSpecification specification;
	private final MemberType memberType;

	/**
	 * Creates a new generator for the given test specification and member type.
	 *
	 * @param specification
	 *            the specification for which to generate a scenario
	 * @param memberType
	 *            the member type for which to generate a scenario
	 */
	ScenarioGenerator(TestSpecification specification, MemberType memberType) {
		this.specification = Objects.requireNonNull(specification);
		this.memberType = Objects.requireNonNull(memberType);
	}

	/**
	 * Generates a test scenario according to the parameters specified in the test specification.
	 *
	 * @param destination
	 *            the path to generate the scenario in
	 * @return a list files representing the root directories of the created projects
	 */
	public List<File> generateScenario(Path destination) {
		List<File> result = new LinkedList<>();

		// Create the required classifiers for the scenario.
		ScenarioResult scenario = createScenario();
		Classifier<?> supplier = scenario.supplier;
		Classifier<?> client = scenario.client;
		Class factory = scenario.factory;
		Class implementer = scenario.implementer;

		// Create a member for the supplier according the specified visibility and "abstract-ness".
		switch (specification.getSupplierType()) {
		case CLASS:
		case DEFAULT_INTERFACE:
			supplier.addMember(createMember("member", specification.getMemberVisibility()));
			break;
		case ABSTRACT_CLASS:
		case INTERFACE:
			supplier.addMember(
					createMember("member", specification.getMemberVisibility())
							.makeAbstract());
			break;
		}

		// Create members that actually use the supplier's member that was just
		// created. Depending on the scenario and the usage type, such a member may override the supplier's member
		// or it may attempt to access it by reading or writing it or calling it if it is a method.
		switch (specification.getScenario()) {
		case EXTENDS:
		case IMPLEMENTS: {
			switch (specification.getUsageType()) {
			case ACCESS:
				switch (specification.getMemberStatic()) {
				case YES:
					client.addMember(createAccess("member", "S"));
					break;
				case NO:
					client.addMember(createAccess("member", "this"));
					break;
				}
				if (specification.getSupplierType() != ClassifierType.INTERFACE &&
						specification.getSupplierType() != ClassifierType.ABSTRACT_CLASS)
					break;
				if (memberType == MemberType.FIELD) // Fields cannot be abstract
					break;
				// We want to fall through here because in the case of implementing an abstract interface or extending
				// an abstract class, we need to override the abstract member.
				// $FALL-THROUGH$
			case OVERRIDE:
				client.addMember(
						createMember("member", specification.getMemberVisibility())
								.makeOverride());
				break;
			default:
				throw new IllegalArgumentException("Unexpected usage type: " + specification.getUsageType());
			}
			break;
		}
		case REFERENCES: {
			if (specification.getUsageType() == UsageType.OVERRIDE)
				throw new IllegalArgumentException("Cannot override in reference scenario");
			switch (specification.getSupplierType()) {
			case CLASS:
			case DEFAULT_INTERFACE:
				break;
			case ABSTRACT_CLASS:
			case INTERFACE:
				implementer.addMember(
						createMember("member", specification.getMemberVisibility())
								.makeOverride());
				break;
			}

			// Create a method that accesses the supplier's member via an instance created by the factory.
			client.addMember(createAccess("member", "new GetS().getS()"));
			break;
		}
		}

		// Now create the actual modules, source folders and projects that contain the classifiers that were just
		// created, according to the client location in the test specification.
		switch (specification.getClientLocation()) {
		case SAME_TYPE:
		case SAME_MODULE: {
			Module module = new Module("SameModule");
			module.addClassifier(supplier);
			if (implementer != null)
				module.addClassifier(implementer);
			if (factory != null)
				module.addClassifier(factory);
			module.addClassifier(client);

			Project project = new Project("SameModule", "sameVendor", "SameVendor", PROJECT_TYPE);
			project.createSourceFolder("src").addModule(module);
			result.add(project.create(destination));
			break;
		}
		case SAME_PROJECT: {
			Module supplierModule = createSupplierModule(supplier, factory, implementer);
			Module clientModule = createClientModule(client, supplier, factory, supplierModule);

			Project project = new Project("SameProject", "sameVendor", "SameVendor", PROJECT_TYPE);
			project.createSourceFolder("src").addModule(supplierModule).addModule(clientModule);
			result.add(project.create(destination));
			break;
		}
		case SAME_VENDOR: {
			Module supplierModule = createSupplierModule(supplier, factory, implementer);
			Module clientModule = createClientModule(client, supplier, factory, supplierModule);

			Project supplierProject = createSupplierProject(supplierModule, "sameVendor");
			Project clientProject = createClientProject(clientModule, "sameVendor", supplierProject);

			result.add(supplierProject.create(destination));
			result.add(clientProject.create(destination));
			break;
		}
		case OTHER: {
			Module supplierModule = createSupplierModule(supplier, factory, implementer);
			Module clientModule = createClientModule(client, supplier, factory, supplierModule);

			Project supplierProject = createSupplierProject(supplierModule, "vendorA");
			Project clientProject = createClientProject(clientModule, "vendorB", supplierProject);

			result.add(supplierProject.create(destination));
			result.add(clientProject.create(destination));
			break;
		}
		default:
			break;
		}

		return result;
	}

	/**
	 * Represents the result of creating the required classifiers for a scenario. In the least, the result consists of a
	 * supplier and a client classifier. For the {@link Scenario#REFERENCES} scenario, the result will also contain a
	 * factory class that creates an instance of the supplier and returns it via the method <code>getS()</code> and
	 * optionally an implementer that extends or implements the supplier classifier if it is abstract or an interface.
	 */
	private class ScenarioResult {
		public Classifier<?> supplier;
		public Classifier<?> client;
		public Class factory;
		public Class implementer;

		/**
		 * Creates a new result that consists only of a supplier and a client.
		 *
		 * @param supplier
		 *            the supplier
		 * @param client
		 *            the client
		 */
		public ScenarioResult(Classifier<?> supplier, Classifier<?> client) {
			this.supplier = Objects.requireNonNull(supplier);
			this.client = Objects.requireNonNull(client);
			this.factory = null;
			this.implementer = null;
		}

		/**
		 * Creates a new result that consists of a supplier, a client, and a factory.
		 *
		 * @param supplier
		 *            the supplier
		 * @param client
		 *            the client
		 * @param factory
		 *            the factory
		 */
		public ScenarioResult(Classifier<?> supplier, Class client, Class factory) {
			this(supplier, client);
			this.factory = Objects.requireNonNull(factory);
			this.implementer = null;
		}

		/**
		 * Creates a new result that consists of a supplier, a client, a factory, and an implementer.
		 *
		 * @param supplier
		 *            the supplier
		 * @param client
		 *            the client
		 * @param factory
		 *            the factory
		 * @param implementer
		 *            the implementer
		 */
		public ScenarioResult(Classifier<?> supplier, Class client, Class factory, Class implementer) {
			this(supplier, client, factory);
			this.implementer = Objects.requireNonNull(implementer);
		}
	}

	/**
	 * Creates the scenario for the given. To be more precise, this method creates the classifiers that participate in
	 * the scenario, but not the required members.
	 *
	 * @return the created classifiers wrapped in an instance of {@link ScenarioResult}.
	 */
	private ScenarioResult createScenario() {
		switch (specification.getScenario()) {
		case EXTENDS:
			return createExtensionScenario();
		case IMPLEMENTS:
			return createImplementationScenario();
		case REFERENCES:
			return createReferenceScenario();
		}

		throw new IllegalArgumentException("Unexpected scenario: " + specification.getScenario());
	}

	/**
	 * Creates an instance of the extension scenario where either one class extends another or where one interface
	 * extends another.
	 *
	 * @return the created classifiers wrapped in an instance of {@link ScenarioResult}.
	 *
	 * @throws IllegalArgumentException
	 *             if the supplier and client types do not agree with this scenario, e.g., if the supplier is a class
	 *             and the client is an interface
	 */
	private ScenarioResult createExtensionScenario() {
		switch (specification.getSupplierType()) {
		case CLASS: {
			Class supplier = new Class("S").setVisibility(specification.getSupplierVisibility());
			switch (specification.getClientType()) {
			case CLASS:
				return new ScenarioResult(supplier, new Class("C").setSuperClass(supplier));
			case ABSTRACT_CLASS:
				return new ScenarioResult(supplier, new Class("C").setSuperClass(supplier).makeAbstract());
			case INTERFACE:
			case DEFAULT_INTERFACE:
				throw new IllegalArgumentException("Invalid Scenario: An interface cannot extend a class");
			}
			break;
		}
		case ABSTRACT_CLASS: {
			Class supplier = new Class("S").setVisibility(specification.getSupplierVisibility()).makeAbstract();
			switch (specification.getClientType()) {
			case CLASS:
				return new ScenarioResult(supplier, new Class("C").setSuperClass(supplier));
			case ABSTRACT_CLASS:
				return new ScenarioResult(supplier, new Class("C").setSuperClass(supplier).makeAbstract());
			case INTERFACE:
			case DEFAULT_INTERFACE:
				throw new IllegalArgumentException("Invalid Scenario: An interface cannot extend a class");
			}
			break;
		}
		case INTERFACE:
		case DEFAULT_INTERFACE: {
			Interface supplier = new Interface("S").setVisibility(specification.getSupplierVisibility());
			switch (specification.getClientType()) {
			case CLASS:
			case ABSTRACT_CLASS:
				throw new IllegalArgumentException("Invalid Scenario: A class cannot extend an interface");
			case INTERFACE:
			case DEFAULT_INTERFACE:
				return new ScenarioResult(supplier, new Interface("C").addSuperInterface(supplier));
			}
			break;
		}
		}

		throw new IllegalArgumentException("Unexpected supplier type: " + specification.getSupplierType());
	}

	/**
	 * Creates an instance of the implementation scenario where the supplier is an interface and the client is a class
	 * implementing that interface.
	 *
	 * @return the created classifiers wrapped in an instance of {@link ScenarioResult}.
	 *
	 * @throws IllegalArgumentException
	 *             if the supplier and client types do not agree with this scenario, e.g., if the supplier is a class
	 */
	private ScenarioResult createImplementationScenario() {
		switch (specification.getSupplierType()) {
		case CLASS:
		case ABSTRACT_CLASS:
			throw new IllegalArgumentException("Invalid Scenario: Classes cannot be implemented");
		case INTERFACE:
		case DEFAULT_INTERFACE: {
			Interface supplier = new Interface("S").setVisibility(specification.getSupplierVisibility());
			switch (specification.getClientType()) {
			case CLASS:
				return new ScenarioResult(supplier, new Class("C").addInterface(supplier));
			case ABSTRACT_CLASS:
				return new ScenarioResult(supplier, new Class("C").makeAbstract().addInterface(supplier));
			case INTERFACE:
			case DEFAULT_INTERFACE:
				throw new IllegalArgumentException("Invalid Scenario: A class cannot extend an interface");
			}
			break;
		}
		}

		throw new IllegalArgumentException("Unexpected supplier type: " + specification.getSupplierType());
	}

	/**
	 * Creates an instance of the reference scenario where the supplier and client can be any type. Additionally creates
	 * a factory and, optionally, an implementer if the supplier is an abstract class or an interface. In this scenario,
	 * the client must be a class.
	 *
	 * @return the created classifiers wrapped in an instance of {@link ScenarioResult}
	 *
	 * @throws IllegalArgumentException
	 *             if the supplier and client types do not agree with this scenario, e.g., if the supplier is a class
	 *             and the client is an interface
	 */
	private ScenarioResult createReferenceScenario() {
		switch (specification.getSupplierType()) {
		case CLASS: {
			Class supplier = new Class("S").setVisibility(specification.getSupplierVisibility());
			Class factory = new Class("GetS").setVisibility(Classifier.Visibility.PUBLIC);
			factory.addMember(new Method("getS").setVisibility(Member.Visibility.PUBLIC).setReturnType("S")
					.setBody("return new S();"));
			switch (specification.getClientType()) {
			case CLASS:
				return new ScenarioResult(supplier, new Class("C"), factory);
			case ABSTRACT_CLASS:
			case INTERFACE:
			case DEFAULT_INTERFACE:
				throw new IllegalArgumentException(
						"Invalid Scenario: Cannot instantiate abstract classes or interfaces for reference scenario");
			}
			break;
		}
		case ABSTRACT_CLASS: {
			Class supplier = new Class("S").setVisibility(specification.getSupplierVisibility()).makeAbstract();
			Class implementer = new Class("SImpl").setSuperClass(supplier).setVisibility(Classifier.Visibility.PUBLIC);
			Class factory = new Class("GetS").setVisibility(Classifier.Visibility.PUBLIC);
			factory.addMember(new Method("getS").setVisibility(Member.Visibility.PUBLIC).setReturnType("S")
					.setBody("return new SImpl();"));
			switch (specification.getClientType()) {
			case CLASS:
				return new ScenarioResult(supplier, new Class("C"), factory, implementer);
			case ABSTRACT_CLASS:
			case INTERFACE:
			case DEFAULT_INTERFACE:
				throw new IllegalArgumentException(
						"Invalid Scenario: Cannot instantiate abstract classes or interfaces for reference scenario");
			}
			break;
		}
		case INTERFACE: {
			Interface supplier = new Interface("S").setVisibility(specification.getSupplierVisibility());
			Class implementer = new Class("SImpl").addInterface(supplier).setVisibility(Classifier.Visibility.PUBLIC);
			Class factory = new Class("GetS").setVisibility(Classifier.Visibility.PUBLIC);
			factory.addMember(new Method("getS").setVisibility(Member.Visibility.PUBLIC).setReturnType("S")
					.setBody("return new SImpl();"));
			switch (specification.getClientType()) {
			case CLASS:
				return new ScenarioResult(supplier, new Class("C"), factory, implementer);
			case ABSTRACT_CLASS:
			case INTERFACE:
			case DEFAULT_INTERFACE:
				throw new IllegalArgumentException(
						"Invalid Scenario: Cannot instantiate abstract classes or interfaces for reference scenario");
			}
			break;
		}
		case DEFAULT_INTERFACE: {
			Interface supplier = new Interface("S").setVisibility(specification.getSupplierVisibility());
			Class implementer = new Class("SImpl").addInterface(supplier).setVisibility(Classifier.Visibility.PUBLIC);
			Class factory = new Class("GetS").setVisibility(Classifier.Visibility.PUBLIC);
			factory.addMember(new Method("getS").setVisibility(Member.Visibility.PUBLIC).setReturnType("S")
					.setBody("return new SImpl();"));
			switch (specification.getClientType()) {
			case CLASS:
				return new ScenarioResult(supplier, new Class("C"), factory, implementer);
			case ABSTRACT_CLASS:
			case INTERFACE:
			case DEFAULT_INTERFACE:
				throw new IllegalArgumentException(
						"Invalid Scenario: Cannot instantiate abstract classes or interfaces for reference scenario");
			}
			break;
		}
		}

		throw new IllegalArgumentException("Unexpected supplier type: " + specification.getSupplierType());
	}

	/**
	 * Creates a new member with the given name and visibility. The type of member depends on the value of the parameter
	 * <code>memberType</code>. The member is also set to according to the value returned by
	 * {@link TestSpecification#getMemberStatic()}.
	 *
	 * @param name
	 *            the name of the created member
	 * @param visibility
	 *            the visibility of the created member
	 *
	 * @return the newly created member
	 */
	private Member<?> createMember(String name, Member.Visibility visibility) {
		Member<?> result = null;
		switch (memberType) {
		case FIELD:
			result = new Field(name).setVisibility(visibility);
			break;
		case GETTER:
			result = new Getter(name).setVisibility(visibility);
			break;
		case SETTER:
			result = new Setter(name).setVisibility(visibility);
			break;
		case METHOD:
			result = new Method(name).setVisibility(visibility);
			break;
		default:
			throw new IllegalArgumentException("Unknown member type: " + memberType);
		}

		if (specification.getMemberStatic() == Member.Static.YES)
			result.makeStatic();

		return result;
	}

	/**
	 * Creates a method in which the supplier's member with the given name is accessed. The given expression will be
	 * used to retrieve the subject of access, i.e., the supplier. In most cases, we will pass <code>this</code> as the
	 * subject expression.
	 *
	 * @param memberName
	 *            the name of the member being accessed
	 * @param subjectExpression
	 *            the expression used to retrieve the subject of access
	 *
	 * @return the newly created method
	 */
	private Method createAccess(String memberName, String subjectExpression) {
		Method result = null;
		switch (memberType) {
		case FIELD:
			result = new Method("accessor").setBody("var t = " + subjectExpression + "." + memberName + "; t = t;");
			break;
		case GETTER:
			result = new Method("accessor").setBody("var t = " + subjectExpression + "." + memberName + "; t = t;");
			break;
		case SETTER:
			result = new Method("accessor").setBody("" + subjectExpression + "." + memberName + " = null;");
			break;
		case METHOD:
			result = new Method("accessor").setBody("" + subjectExpression + "." + memberName + "();");
			break;
		default:
			throw new IllegalArgumentException("Unknown member type: " + memberType);
		}

		if (specification.getMemberStatic() == Member.Static.YES)
			result.makeStatic();

		return result;
	}

	/**
	 * Creates the supplier module with the given supplier classifier, factory, and implementer classes. While the
	 * supplier classifier must not be <code>null</code>, the factory and / or the implementer may be <code>null</code>.
	 * The newly created module will contain the supplier classifier and the given factory and / or implementer if they
	 * are not <code>null</code>.
	 *
	 * @param supplier
	 *            the supplier classifier
	 * @param factory
	 *            the factory class
	 * @param implementer
	 *            the implementer class
	 * @return the newly created module
	 */
	private Module createSupplierModule(Classifier<?> supplier, Class factory, Class implementer) {
		Module supplierModule = new Module("SupplierModule");
		supplierModule.addClassifier(supplier);
		if (implementer != null)
			supplierModule.addClassifier(implementer);
		if (factory != null)
			supplierModule.addClassifier(factory);
		return supplierModule;
	}

	/**
	 * Creates the client module with the given client classifier. If the given factory is not <code>null</code>, the
	 * newly created module will import the given supplier classifier from the given supplier module. Otherwise, it will
	 * import the given factory from the given supplier module, but not the given supplier itself.
	 *
	 * @param client
	 *            the client classifier
	 * @param supplier
	 *            the supplier classifier
	 * @param supplierFactory
	 *            the supplier factory
	 * @param supplierModule
	 *            the supplier module containing the supplier classifier and factory
	 *
	 * @return the newly created module
	 */
	private Module createClientModule(Classifier<?> client, Classifier<?> supplier, Class supplierFactory,
			Module supplierModule) {
		Module clientModule = new Module("ClientModule");
		if (supplierFactory != null)
			clientModule.addImport(supplierFactory, supplierModule);
		else
			clientModule.addImport(supplier, supplierModule);
		clientModule.addClassifier(client);
		return clientModule;
	}

	/**
	 * Creates the supplier project containing the given module and vendor ID.
	 *
	 * @param supplierModule
	 *            the supplier module to add the project
	 * @param vendorId
	 *            the vendor ID
	 *
	 * @return the newly created project
	 */
	private Project createSupplierProject(Module supplierModule, String vendorId) {
		Project supplierProject = new Project("SupplierProject", vendorId, vendorId + "_name", PROJECT_TYPE);
		supplierProject.createSourceFolder("src").addModule(supplierModule);
		return supplierProject;
	}

	/**
	 * Creates the client project containing the given module and vendor ID with a dependency on the given supplier
	 * project.
	 *
	 * @param clientModule
	 *            the client module to add to the project
	 * @param vendorId
	 *            the vendor ID
	 * @param supplierProject
	 *            the supplier project which the newly created project should depend on
	 *
	 * @return the newly created project
	 */
	private Project createClientProject(Module clientModule, String vendorId, Project supplierProject) {
		Project clientProject = new Project("ClientProject", vendorId, vendorId + "_name", PROJECT_TYPE);
		clientProject.addProjectDependency(supplierProject);
		clientProject.createSourceFolder("src").addModule(clientModule);
		return clientProject;
	}

	/**
	 * Creates the issues according to the expectation returned by {@link TestSpecification#getExpectation()}.
	 *
	 * @return an instance of {@link IssueExpectations} that represents the expectations
	 */
	IssueExpectations createIssues() {
		IssueExpectations result = new IssueExpectations();

		switch (specification.getExpectation()) {
		case OK:
		case FIXME_OK:
			break;
		case FAIL:
		case FIXME_FAIL:
			result.add().error();
			break;
		case UNUSABLE:
		case FIXME_UNUSABLE:
			result.add().error();
			if (memberType != MemberType.FIELD) // fields cannot be abstract
				result.add().error().message().startsWith("Cannot use");
			break;
		case SKIP:
			break;
		}

		switch (specification.getExpectation()) {
		case FIXME_OK:
		case FIXME_FAIL:
		case FIXME_UNUSABLE:
			result.invert();
			break;
		case OK:
		case FAIL:
		case UNUSABLE:
		case SKIP:
			break;
		}

		return result;
	}
}
