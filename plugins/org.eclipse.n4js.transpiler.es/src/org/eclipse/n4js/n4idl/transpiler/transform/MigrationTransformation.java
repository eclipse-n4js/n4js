package org.eclipse.n4js.n4idl.transpiler.transform;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.MigrationDeclaration;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerBuilderBlocks;
import org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.operations.TranspilerStateOperations;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Replaces {@link MigrationDeclaration} by a property access on the involved principle types. (first input and first
 * output parameters).
 *
 * The migrations can be accessed via the property $migrateUp and $migrateDown respectively.
 *
 */
public class MigrationTransformation extends Transformation {

	@Override
	public void analyze() {
		// nothing to do
	}

	@Override
	public void transform() {
		collectNodes(getState().im, MigrationDeclaration.class, false).forEach(this::transformMigration);
	}

	/**
	 * Replaces a given {@link MigrationDeclaration} with an equivalent function declaration.
	 */
	private void transformMigration(MigrationDeclaration migrationDeclaration) {
		final String name = generateMigrationName(migrationDeclaration);
		FunctionExpression migrationFunctionExpr = createMigrationFunctionDeclaration(migrationDeclaration);

		if (migrationFunctionExpr.getFpars().isEmpty() || migrationDeclaration.getFrets().isEmpty()) {
			throw new IllegalStateException(
					"A migration must have at least one input and at least one output parameter");
		}

		TClassifier inputClassifier = getClassfierOfFormalParameter(migrationFunctionExpr.getFpars().get(0));
		TClassifier outputClassifier = getClassfierOfFormalParameter(migrationDeclaration.getFrets().get(0));

		if (!inputClassifier.getName().equals(outputClassifier.getName())) {
			throw new IllegalStateException(
					"Principle in- and output parameter types of a migration must be the same.");
		}

		boolean isUpgrade = inputClassifier.getVersion() < outputClassifier.getVersion();

		TranspilerStateOperations.replace(getState(), migrationDeclaration,
				migrationRegistrationStatement(migrationFunctionExpr, name, isUpgrade));
	}

	/**
	 * Returns the {@link TClassifier} of a given {@link FormalParameter} .
	 */
	private TClassifier getClassfierOfFormalParameter(FormalParameter formalParameter) {
		TypeRef declaredTypeRef = formalParameter.getDeclaredTypeRef();

		if (!(declaredTypeRef instanceof ParameterizedTypeRef_IM)) {
			throw new IllegalStateException("Encountered invalid TypeRef instances in intermediate model.");
		}

		SymbolTableEntry entry = ((ParameterizedTypeRef_IM) declaredTypeRef).getDeclaredType_IM();

		if (!(entry instanceof SymbolTableEntryOriginal)) {
			throw new IllegalStateException(
					"Types of migration parameters may not refer to IM-only or internal symbol table entries.");
		}

		IdentifiableElement originalTarget = ((SymbolTableEntryOriginal) entry).getOriginalTarget();

		if (!(originalTarget instanceof TClassifier)) {
			throw new IllegalStateException("Non-classifier type used as migration parameter.");
		}

		return ((TClassifier) originalTarget);
	}

	private ExpressionStatement migrationRegistrationStatement(FunctionExpression migrationFunction, String name,
			boolean isUpgrade) {
		if (migrationFunction.getFpars().isEmpty()) {
			throw new IllegalStateException("Migrations without input are not valid.");
		}

		TypeRef migratedType = migrationFunction.getFpars().get(0).getDeclaredTypeRef();

		if (!(migratedType instanceof ParameterizedTypeRef_IM)) {
			throw new IllegalStateException("Encountered non-IM TypeRef instance during transpilation.");
		}

		return TranspilerBuilderBlocks._ExprStmnt(
				TranspilerBuilderBlocks._CallExpr(TranspilerBuilderBlocks._PropertyAccessExpr(
						TranspilerBuilderBlocks._IdentRef(getSymbolTableEntryInternal("Object", true)),
						getSymbolTableEntryInternal("defineProperty", true)),
						// constructor STE
						TranspilerBuilderBlocks._IdentRef(
								((ParameterizedTypeRef_IM) migratedType).getDeclaredType_IM()),
						// property name
						TranspilerBuilderBlocks._StringLiteral("$migrate" + (isUpgrade ? "Up" : "Down")),
						TranspilerBuilderBlocks
								._ObjLit(TranspilerBuilderBlocks._PropertyNameValuePair("value", migrationFunction),
										TranspilerBuilderBlocks._PropertyNameValuePair("writable",
												TranspilerBuilderBlocks._BooleanLiteral(false)))));
	}

	private FunctionExpression createMigrationFunctionDeclaration(MigrationDeclaration migrationDeclaration) {
		// create a variable declaration for each of the return parameters
		VariableDeclaration[] returnParameterDeclarations = migrationDeclaration.getFrets().stream()
				.map(parameter -> TranspilerBuilderBlocks._VariableDeclaration(parameter.getName()))
				.toArray(VariableDeclaration[]::new);

		// return expression
		@SuppressWarnings("unchecked")
		ObjectLiteral returnParameterExpressions = TranspilerBuilderBlocks
				._ObjLit(Stream.of(returnParameterDeclarations)
						.map(r -> getSymbolTableEntryInternal(r.getName(), true))
						.map(e -> Pair.of(e.getName(), TranspilerBuilderBlocks._IdentRef(e)))
						.toArray(size -> new Pair[size]));

		ReturnStatement returnStatement = TranspilerBuilderBlocks
				._ReturnStmnt(returnParameterExpressions);

		Block migrationBody = TranspilerBuilderBlocks._Block(
				TranspilerBuilderBlocks._VariableStatement(returnParameterDeclarations), // declare
																							// output
																							// parameters
																							// as
																							// local
																							// variables
				migrationDeclaration.getBody(), // include original migration
												// body
				returnStatement // return output parameter as array literal
		);

		return TranspilerBuilderBlocks._FunExpr(false, generateMigrationName(migrationDeclaration),
				migrationDeclaration.getFpars().toArray(new FormalParameter[0]), migrationBody);
	}

	private String generateMigrationName(MigrationDeclaration migrationDeclaration) {
		String fromTypes = migrationDeclaration.getFpars().stream().map(p -> p.getDeclaredTypeRef())
				.map(p -> ((ParameterizedTypeRef_IM) p)).map(typeRefIM -> typeRefIM.getDeclaredType_IM())
				.map(e -> e.getName()).collect(Collectors.joining("_"));

		String toTypes = migrationDeclaration.getFrets().stream().map(p -> p.getDeclaredTypeRef())
				.map(p -> ((ParameterizedTypeRef_IM) p)).map(typeRefIM -> typeRefIM.getDeclaredType_IM())
				.map(e -> e.getName()).collect(Collectors.joining("_"));

		return "$migration_" + fromTypes + "$to$" + toTypes;
	}

	@Override
	public void assertPreConditions() {
		// nothing to do

	}

	@Override
	public void assertPostConditions() {
		// nothing to do
	}

}
