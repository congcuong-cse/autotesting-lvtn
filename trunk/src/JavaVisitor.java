// Generated from C:\JavaIndigo\grammar\Java.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface JavaVisitor<T> extends ParseTreeVisitor<T> {
	T visitInnerCreator(JavaParser.InnerCreatorContext ctx);

	T visitGenericMethodDeclaration(JavaParser.GenericMethodDeclarationContext ctx);

	T visitExpressionList(JavaParser.ExpressionListContext ctx);

	T visitTypeDeclaration(JavaParser.TypeDeclarationContext ctx);

	T visitForUpdate(JavaParser.ForUpdateContext ctx);

	T visitAnnotation(JavaParser.AnnotationContext ctx);

	T visitEnumConstant(JavaParser.EnumConstantContext ctx);

	T visitImportDeclaration(JavaParser.ImportDeclarationContext ctx);

	T visitAnnotationMethodOrConstantRest(JavaParser.AnnotationMethodOrConstantRestContext ctx);

	T visitEnumConstantName(JavaParser.EnumConstantNameContext ctx);

	T visitFinallyBlock(JavaParser.FinallyBlockContext ctx);

	T visitVariableDeclarators(JavaParser.VariableDeclaratorsContext ctx);

	T visitElementValuePairs(JavaParser.ElementValuePairsContext ctx);

	T visitInterfaceMethodDeclaration(JavaParser.InterfaceMethodDeclarationContext ctx);

	T visitInterfaceBodyDeclaration(JavaParser.InterfaceBodyDeclarationContext ctx);

	T visitEnumConstants(JavaParser.EnumConstantsContext ctx);

	T visitCatchClause(JavaParser.CatchClauseContext ctx);

	T visitConstantExpression(JavaParser.ConstantExpressionContext ctx);

	T visitEnumDeclaration(JavaParser.EnumDeclarationContext ctx);

	T visitExplicitGenericInvocationSuffix(JavaParser.ExplicitGenericInvocationSuffixContext ctx);

	T visitTypeParameter(JavaParser.TypeParameterContext ctx);

	T visitEnumBodyDeclarations(JavaParser.EnumBodyDeclarationsContext ctx);

	T visitTypeBound(JavaParser.TypeBoundContext ctx);

	T visitStatementExpression(JavaParser.StatementExpressionContext ctx);

	T visitVariableInitializer(JavaParser.VariableInitializerContext ctx);

	T visitBlock(JavaParser.BlockContext ctx);

	T visitGenericInterfaceMethodDeclaration(JavaParser.GenericInterfaceMethodDeclarationContext ctx);

	T visitLocalVariableDeclarationStatement(JavaParser.LocalVariableDeclarationStatementContext ctx);

	T visitSuperSuffix(JavaParser.SuperSuffixContext ctx);

	T visitFieldDeclaration(JavaParser.FieldDeclarationContext ctx);

	T visitFormalParameterList(JavaParser.FormalParameterListContext ctx);

	T visitExplicitGenericInvocation(JavaParser.ExplicitGenericInvocationContext ctx);

	T visitParExpression(JavaParser.ParExpressionContext ctx);

	T visitSwitchLabel(JavaParser.SwitchLabelContext ctx);

	T visitTypeParameters(JavaParser.TypeParametersContext ctx);

	T visitQualifiedName(JavaParser.QualifiedNameContext ctx);

	T visitClassDeclaration(JavaParser.ClassDeclarationContext ctx);

	T visitAnnotationConstantRest(JavaParser.AnnotationConstantRestContext ctx);

	T visitTypeName(JavaParser.TypeNameContext ctx);

	T visitArguments(JavaParser.ArgumentsContext ctx);

	T visitConstructorBody(JavaParser.ConstructorBodyContext ctx);

	T visitFormalParameters(JavaParser.FormalParametersContext ctx);

	T visitTypeArgument(JavaParser.TypeArgumentContext ctx);

	T visitForInit(JavaParser.ForInitContext ctx);

	T visitVariableDeclarator(JavaParser.VariableDeclaratorContext ctx);

	T visitAnnotationTypeDeclaration(JavaParser.AnnotationTypeDeclarationContext ctx);

	T visitExpression(JavaParser.ExpressionContext ctx);

	T visitResources(JavaParser.ResourcesContext ctx);

	T visitFormalParameter(JavaParser.FormalParameterContext ctx);

	T visitType(JavaParser.TypeContext ctx);

	T visitElementValueArrayInitializer(JavaParser.ElementValueArrayInitializerContext ctx);

	T visitAnnotationName(JavaParser.AnnotationNameContext ctx);

	T visitEnhancedForControl(JavaParser.EnhancedForControlContext ctx);

	T visitAnnotationMethodRest(JavaParser.AnnotationMethodRestContext ctx);

	T visitPrimary(JavaParser.PrimaryContext ctx);

	T visitClassBody(JavaParser.ClassBodyContext ctx);

	T visitClassOrInterfaceModifier(JavaParser.ClassOrInterfaceModifierContext ctx);

	T visitDefaultValue(JavaParser.DefaultValueContext ctx);

	T visitVariableModifier(JavaParser.VariableModifierContext ctx);

	T visitConstDeclaration(JavaParser.ConstDeclarationContext ctx);

	T visitCreatedName(JavaParser.CreatedNameContext ctx);

	T visitInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx);

	T visitPackageDeclaration(JavaParser.PackageDeclarationContext ctx);

	T visitConstantDeclarator(JavaParser.ConstantDeclaratorContext ctx);

	T visitCatchType(JavaParser.CatchTypeContext ctx);

	T visitTypeArguments(JavaParser.TypeArgumentsContext ctx);

	T visitClassCreatorRest(JavaParser.ClassCreatorRestContext ctx);

	T visitModifier(JavaParser.ModifierContext ctx);

	T visitStatement(JavaParser.StatementContext ctx);

	T visitInterfaceBody(JavaParser.InterfaceBodyContext ctx);

	T visitPackageOrTypeName(JavaParser.PackageOrTypeNameContext ctx);

	T visitClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx);

	T visitLastFormalParameter(JavaParser.LastFormalParameterContext ctx);

	T visitForControl(JavaParser.ForControlContext ctx);

	T visitTypeList(JavaParser.TypeListContext ctx);

	T visitLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx);

	T visitVariableDeclaratorId(JavaParser.VariableDeclaratorIdContext ctx);

	T visitCompilationUnit(JavaParser.CompilationUnitContext ctx);

	T visitElementValue(JavaParser.ElementValueContext ctx);

	T visitClassOrInterfaceType(JavaParser.ClassOrInterfaceTypeContext ctx);

	T visitTypeArgumentsOrDiamond(JavaParser.TypeArgumentsOrDiamondContext ctx);

	T visitAnnotationTypeElementDeclaration(JavaParser.AnnotationTypeElementDeclarationContext ctx);

	T visitBlockStatement(JavaParser.BlockStatementContext ctx);

	T visitAnnotationTypeBody(JavaParser.AnnotationTypeBodyContext ctx);

	T visitQualifiedNameList(JavaParser.QualifiedNameListContext ctx);

	T visitCreator(JavaParser.CreatorContext ctx);

	T visitMemberDeclaration(JavaParser.MemberDeclarationContext ctx);

	T visitMethodDeclaration(JavaParser.MethodDeclarationContext ctx);

	T visitAnnotationTypeElementRest(JavaParser.AnnotationTypeElementRestContext ctx);

	T visitResourceSpecification(JavaParser.ResourceSpecificationContext ctx);

	T visitConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx);

	T visitResource(JavaParser.ResourceContext ctx);

	T visitElementValuePair(JavaParser.ElementValuePairContext ctx);

	T visitMethodBody(JavaParser.MethodBodyContext ctx);

	T visitArrayInitializer(JavaParser.ArrayInitializerContext ctx);

	T visitNonWildcardTypeArgumentsOrDiamond(JavaParser.NonWildcardTypeArgumentsOrDiamondContext ctx);

	T visitPrimitiveType(JavaParser.PrimitiveTypeContext ctx);

	T visitNonWildcardTypeArguments(JavaParser.NonWildcardTypeArgumentsContext ctx);

	T visitArrayCreatorRest(JavaParser.ArrayCreatorRestContext ctx);

	T visitInterfaceMemberDeclaration(JavaParser.InterfaceMemberDeclarationContext ctx);

	T visitGenericConstructorDeclaration(JavaParser.GenericConstructorDeclarationContext ctx);

	T visitLiteral(JavaParser.LiteralContext ctx);

	T visitSwitchBlockStatementGroup(JavaParser.SwitchBlockStatementGroupContext ctx);
}