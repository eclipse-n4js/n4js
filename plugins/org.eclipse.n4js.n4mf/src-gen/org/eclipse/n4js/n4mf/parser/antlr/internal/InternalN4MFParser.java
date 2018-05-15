package org.eclipse.n4js.n4mf.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
@SuppressWarnings("all")
public class InternalN4MFParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ExtendedRuntimeEnvironment", "ProvidedRuntimeLibraries", "RequiredRuntimeLibraries", "ImplementedProjects", "ProjectDependencies", "RuntimeEnvironment", "ImplementationId", "ProjectVersion", "TestedProjects", "RuntimeLibrary", "ModuleFilters", "ModuleLoader", "NoModuleWrap", "Node_builtin", "InitModules", "ProjectType", "Application", "ExecModule", "MainModule", "VendorName", "NoValidate", "Validation", "Libraries", "ProjectId", "Resources", "Processor", "VendorId", "Commonjs", "External", "Sources", "Compile", "Content", "Library", "Output", "Source", "KW_System", "N4js", "Test", "User", "API", "In", "LeftParenthesis", "RightParenthesis", "Comma", "HyphenMinus", "FullStop", "Colon", "LeftSquareBracket", "RightSquareBracket", "LeftCurlyBracket", "RightCurlyBracket", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int TestedProjects=12;
    public static final int KW_System=39;
    public static final int ProjectDependencies=8;
    public static final int ExecModule=21;
    public static final int LeftParenthesis=45;
    public static final int Test=41;
    public static final int ProjectVersion=11;
    public static final int Libraries=26;
    public static final int ModuleFilters=14;
    public static final int RightSquareBracket=52;
    public static final int Validation=25;
    public static final int VendorName=23;
    public static final int RuntimeEnvironment=9;
    public static final int RULE_ID=55;
    public static final int NoValidate=24;
    public static final int NoModuleWrap=16;
    public static final int RightParenthesis=46;
    public static final int Sources=33;
    public static final int Content=35;
    public static final int RULE_INT=56;
    public static final int ProjectType=19;
    public static final int External=32;
    public static final int RULE_ML_COMMENT=58;
    public static final int LeftSquareBracket=51;
    public static final int Resources=28;
    public static final int Library=36;
    public static final int Application=20;
    public static final int ImplementedProjects=7;
    public static final int Processor=29;
    public static final int User=42;
    public static final int In=44;
    public static final int VendorId=30;
    public static final int RULE_STRING=57;
    public static final int Node_builtin=17;
    public static final int N4js=40;
    public static final int Compile=34;
    public static final int Source=38;
    public static final int RULE_SL_COMMENT=59;
    public static final int ImplementationId=10;
    public static final int Comma=47;
    public static final int HyphenMinus=48;
    public static final int Output=37;
    public static final int MainModule=22;
    public static final int Colon=50;
    public static final int RightCurlyBracket=54;
    public static final int EOF=-1;
    public static final int ExtendedRuntimeEnvironment=4;
    public static final int FullStop=49;
    public static final int ModuleLoader=15;
    public static final int Commonjs=31;
    public static final int RULE_WS=60;
    public static final int ProjectId=27;
    public static final int LeftCurlyBracket=53;
    public static final int ProvidedRuntimeLibraries=5;
    public static final int RULE_ANY_OTHER=61;
    public static final int RequiredRuntimeLibraries=6;
    public static final int InitModules=18;
    public static final int API=43;
    public static final int RuntimeLibrary=13;

    // delegates
    // delegators


        public InternalN4MFParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalN4MFParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalN4MFParser.tokenNames; }
    public String getGrammarFileName() { return "InternalN4MFParser.g"; }



     	private N4MFGrammarAccess grammarAccess;

        public InternalN4MFParser(TokenStream input, N4MFGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "ProjectDescription";
       	}

       	@Override
       	protected N4MFGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleProjectDescription"
    // InternalN4MFParser.g:65:1: entryRuleProjectDescription returns [EObject current=null] : iv_ruleProjectDescription= ruleProjectDescription EOF ;
    public final EObject entryRuleProjectDescription() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProjectDescription = null;


        try {
            // InternalN4MFParser.g:65:59: (iv_ruleProjectDescription= ruleProjectDescription EOF )
            // InternalN4MFParser.g:66:2: iv_ruleProjectDescription= ruleProjectDescription EOF
            {
             newCompositeNode(grammarAccess.getProjectDescriptionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleProjectDescription=ruleProjectDescription();

            state._fsp--;

             current =iv_ruleProjectDescription; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProjectDescription"


    // $ANTLR start "ruleProjectDescription"
    // InternalN4MFParser.g:72:1: ruleProjectDescription returns [EObject current=null] : ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+ {...}?) ) ) ;
    public final EObject ruleProjectDescription() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token lv_vendorName_15_0=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token lv_mainModule_18_0=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        Token otherlv_29=null;
        Token otherlv_31=null;
        Token otherlv_33=null;
        Token otherlv_34=null;
        Token otherlv_35=null;
        Token otherlv_37=null;
        Token otherlv_39=null;
        Token otherlv_40=null;
        Token otherlv_41=null;
        Token otherlv_43=null;
        Token otherlv_44=null;
        Token otherlv_46=null;
        Token otherlv_48=null;
        Token otherlv_49=null;
        Token otherlv_50=null;
        Token otherlv_52=null;
        Token otherlv_54=null;
        Token otherlv_55=null;
        Token otherlv_56=null;
        Token otherlv_58=null;
        Token otherlv_59=null;
        Token lv_outputPathRaw_60_0=null;
        Token otherlv_61=null;
        Token otherlv_62=null;
        Token lv_libraryPathsRaw_63_0=null;
        Token otherlv_64=null;
        Token lv_libraryPathsRaw_65_0=null;
        Token otherlv_66=null;
        Token otherlv_67=null;
        Token otherlv_68=null;
        Token lv_resourcePathsRaw_69_0=null;
        Token otherlv_70=null;
        Token lv_resourcePathsRaw_71_0=null;
        Token otherlv_72=null;
        Token otherlv_73=null;
        Token otherlv_74=null;
        Token otherlv_76=null;
        Token otherlv_77=null;
        Token otherlv_78=null;
        Token otherlv_80=null;
        Token otherlv_81=null;
        Token otherlv_82=null;
        Token otherlv_84=null;
        Token otherlv_86=null;
        Token otherlv_87=null;
        Token otherlv_88=null;
        AntlrDatatypeRuleToken lv_projectId_3_0 = null;

        Enumerator lv_projectType_6_0 = null;

        EObject lv_projectVersion_9_0 = null;

        AntlrDatatypeRuleToken lv_declaredVendorId_12_0 = null;

        EObject lv_extendedRuntimeEnvironment_21_0 = null;

        EObject lv_providedRuntimeLibraries_24_0 = null;

        EObject lv_providedRuntimeLibraries_26_0 = null;

        EObject lv_requiredRuntimeLibraries_30_0 = null;

        EObject lv_requiredRuntimeLibraries_32_0 = null;

        EObject lv_projectDependencies_36_0 = null;

        EObject lv_projectDependencies_38_0 = null;

        AntlrDatatypeRuleToken lv_implementationId_42_0 = null;

        EObject lv_implementedProjects_45_0 = null;

        EObject lv_implementedProjects_47_0 = null;

        EObject lv_initModules_51_0 = null;

        EObject lv_initModules_53_0 = null;

        EObject lv_execModule_57_0 = null;

        EObject lv_sourceFragment_75_0 = null;

        EObject lv_moduleFilters_79_0 = null;

        EObject lv_testedProjects_83_0 = null;

        EObject lv_testedProjects_85_0 = null;

        Enumerator lv_moduleLoader_89_0 = null;



        	enterRule();

        try {
            // InternalN4MFParser.g:78:2: ( ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+ {...}?) ) ) )
            // InternalN4MFParser.g:79:2: ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+ {...}?) ) )
            {
            // InternalN4MFParser.g:79:2: ( ( ( ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+ {...}?) ) )
            // InternalN4MFParser.g:80:3: ( ( ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+ {...}?) )
            {
            // InternalN4MFParser.g:80:3: ( ( ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+ {...}?) )
            // InternalN4MFParser.g:81:4: ( ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+ {...}?)
            {
             
            			  getUnorderedGroupHelper().enter(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            			
            // InternalN4MFParser.g:84:4: ( ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+ {...}?)
            // InternalN4MFParser.g:85:5: ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+ {...}?
            {
            // InternalN4MFParser.g:85:5: ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+
            int cnt17=0;
            loop17:
            do {
                int alt17=22;
                alt17 = dfa17.predict(input);
                switch (alt17) {
            	case 1 :
            	    // InternalN4MFParser.g:86:3: ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:86:3: ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) )
            	    // InternalN4MFParser.g:87:4: {...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0)");
            	    }
            	    // InternalN4MFParser.g:87:112: ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) )
            	    // InternalN4MFParser.g:88:5: ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0);
            	    				
            	    // InternalN4MFParser.g:91:8: ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) )
            	    // InternalN4MFParser.g:91:9: {...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:91:18: (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) )
            	    // InternalN4MFParser.g:91:19: otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) )
            	    {
            	    otherlv_1=(Token)match(input,ProjectId,FOLLOW_3); 

            	    								newLeafNode(otherlv_1, grammarAccess.getProjectDescriptionAccess().getProjectIdKeyword_0_0());
            	    							
            	    otherlv_2=(Token)match(input,Colon,FOLLOW_4); 

            	    								newLeafNode(otherlv_2, grammarAccess.getProjectDescriptionAccess().getColonKeyword_0_1());
            	    							
            	    // InternalN4MFParser.g:99:8: ( (lv_projectId_3_0= ruleN4mfIdentifier ) )
            	    // InternalN4MFParser.g:100:9: (lv_projectId_3_0= ruleN4mfIdentifier )
            	    {
            	    // InternalN4MFParser.g:100:9: (lv_projectId_3_0= ruleN4mfIdentifier )
            	    // InternalN4MFParser.g:101:10: lv_projectId_3_0= ruleN4mfIdentifier
            	    {

            	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_0_2_0());
            	    									
            	    pushFollow(FOLLOW_5);
            	    lv_projectId_3_0=ruleN4mfIdentifier();

            	    state._fsp--;


            	    										if (current==null) {
            	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										set(
            	    											current,
            	    											"projectId",
            	    											lv_projectId_3_0,
            	    											"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
            	    										afterParserOrEnumRuleCall();
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalN4MFParser.g:124:3: ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:124:3: ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) )
            	    // InternalN4MFParser.g:125:4: {...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1)");
            	    }
            	    // InternalN4MFParser.g:125:112: ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) )
            	    // InternalN4MFParser.g:126:5: ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1);
            	    				
            	    // InternalN4MFParser.g:129:8: ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) )
            	    // InternalN4MFParser.g:129:9: {...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:129:18: (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) )
            	    // InternalN4MFParser.g:129:19: otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) )
            	    {
            	    otherlv_4=(Token)match(input,ProjectType,FOLLOW_3); 

            	    								newLeafNode(otherlv_4, grammarAccess.getProjectDescriptionAccess().getProjectTypeKeyword_1_0());
            	    							
            	    otherlv_5=(Token)match(input,Colon,FOLLOW_6); 

            	    								newLeafNode(otherlv_5, grammarAccess.getProjectDescriptionAccess().getColonKeyword_1_1());
            	    							
            	    // InternalN4MFParser.g:137:8: ( (lv_projectType_6_0= ruleProjectType ) )
            	    // InternalN4MFParser.g:138:9: (lv_projectType_6_0= ruleProjectType )
            	    {
            	    // InternalN4MFParser.g:138:9: (lv_projectType_6_0= ruleProjectType )
            	    // InternalN4MFParser.g:139:10: lv_projectType_6_0= ruleProjectType
            	    {

            	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectTypeProjectTypeEnumRuleCall_1_2_0());
            	    									
            	    pushFollow(FOLLOW_5);
            	    lv_projectType_6_0=ruleProjectType();

            	    state._fsp--;


            	    										if (current==null) {
            	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										set(
            	    											current,
            	    											"projectType",
            	    											lv_projectType_6_0,
            	    											"org.eclipse.n4js.n4mf.N4MF.ProjectType");
            	    										afterParserOrEnumRuleCall();
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalN4MFParser.g:162:3: ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:162:3: ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) )
            	    // InternalN4MFParser.g:163:4: {...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2)");
            	    }
            	    // InternalN4MFParser.g:163:112: ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) )
            	    // InternalN4MFParser.g:164:5: ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2);
            	    				
            	    // InternalN4MFParser.g:167:8: ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) )
            	    // InternalN4MFParser.g:167:9: {...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:167:18: (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) )
            	    // InternalN4MFParser.g:167:19: otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) )
            	    {
            	    otherlv_7=(Token)match(input,ProjectVersion,FOLLOW_3); 

            	    								newLeafNode(otherlv_7, grammarAccess.getProjectDescriptionAccess().getProjectVersionKeyword_2_0());
            	    							
            	    otherlv_8=(Token)match(input,Colon,FOLLOW_7); 

            	    								newLeafNode(otherlv_8, grammarAccess.getProjectDescriptionAccess().getColonKeyword_2_1());
            	    							
            	    // InternalN4MFParser.g:175:8: ( (lv_projectVersion_9_0= ruleDeclaredVersion ) )
            	    // InternalN4MFParser.g:176:9: (lv_projectVersion_9_0= ruleDeclaredVersion )
            	    {
            	    // InternalN4MFParser.g:176:9: (lv_projectVersion_9_0= ruleDeclaredVersion )
            	    // InternalN4MFParser.g:177:10: lv_projectVersion_9_0= ruleDeclaredVersion
            	    {

            	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectVersionDeclaredVersionParserRuleCall_2_2_0());
            	    									
            	    pushFollow(FOLLOW_5);
            	    lv_projectVersion_9_0=ruleDeclaredVersion();

            	    state._fsp--;


            	    										if (current==null) {
            	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										set(
            	    											current,
            	    											"projectVersion",
            	    											lv_projectVersion_9_0,
            	    											"org.eclipse.n4js.n4mf.N4MF.DeclaredVersion");
            	    										afterParserOrEnumRuleCall();
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalN4MFParser.g:200:3: ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:200:3: ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) )
            	    // InternalN4MFParser.g:201:4: {...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3)");
            	    }
            	    // InternalN4MFParser.g:201:112: ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) )
            	    // InternalN4MFParser.g:202:5: ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3);
            	    				
            	    // InternalN4MFParser.g:205:8: ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) )
            	    // InternalN4MFParser.g:205:9: {...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:205:18: (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) )
            	    // InternalN4MFParser.g:205:19: otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) )
            	    {
            	    otherlv_10=(Token)match(input,VendorId,FOLLOW_3); 

            	    								newLeafNode(otherlv_10, grammarAccess.getProjectDescriptionAccess().getVendorIdKeyword_3_0());
            	    							
            	    otherlv_11=(Token)match(input,Colon,FOLLOW_4); 

            	    								newLeafNode(otherlv_11, grammarAccess.getProjectDescriptionAccess().getColonKeyword_3_1());
            	    							
            	    // InternalN4MFParser.g:213:8: ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) )
            	    // InternalN4MFParser.g:214:9: (lv_declaredVendorId_12_0= ruleN4mfIdentifier )
            	    {
            	    // InternalN4MFParser.g:214:9: (lv_declaredVendorId_12_0= ruleN4mfIdentifier )
            	    // InternalN4MFParser.g:215:10: lv_declaredVendorId_12_0= ruleN4mfIdentifier
            	    {

            	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_3_2_0());
            	    									
            	    pushFollow(FOLLOW_5);
            	    lv_declaredVendorId_12_0=ruleN4mfIdentifier();

            	    state._fsp--;


            	    										if (current==null) {
            	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										set(
            	    											current,
            	    											"declaredVendorId",
            	    											lv_declaredVendorId_12_0,
            	    											"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
            	    										afterParserOrEnumRuleCall();
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalN4MFParser.g:238:3: ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:238:3: ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) )
            	    // InternalN4MFParser.g:239:4: {...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4)");
            	    }
            	    // InternalN4MFParser.g:239:112: ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) )
            	    // InternalN4MFParser.g:240:5: ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4);
            	    				
            	    // InternalN4MFParser.g:243:8: ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) )
            	    // InternalN4MFParser.g:243:9: {...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:243:18: (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) )
            	    // InternalN4MFParser.g:243:19: otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) )
            	    {
            	    otherlv_13=(Token)match(input,VendorName,FOLLOW_3); 

            	    								newLeafNode(otherlv_13, grammarAccess.getProjectDescriptionAccess().getVendorNameKeyword_4_0());
            	    							
            	    otherlv_14=(Token)match(input,Colon,FOLLOW_8); 

            	    								newLeafNode(otherlv_14, grammarAccess.getProjectDescriptionAccess().getColonKeyword_4_1());
            	    							
            	    // InternalN4MFParser.g:251:8: ( (lv_vendorName_15_0= RULE_STRING ) )
            	    // InternalN4MFParser.g:252:9: (lv_vendorName_15_0= RULE_STRING )
            	    {
            	    // InternalN4MFParser.g:252:9: (lv_vendorName_15_0= RULE_STRING )
            	    // InternalN4MFParser.g:253:10: lv_vendorName_15_0= RULE_STRING
            	    {
            	    lv_vendorName_15_0=(Token)match(input,RULE_STRING,FOLLOW_5); 

            	    										newLeafNode(lv_vendorName_15_0, grammarAccess.getProjectDescriptionAccess().getVendorNameSTRINGTerminalRuleCall_4_2_0());
            	    									

            	    										if (current==null) {
            	    											current = createModelElement(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										setWithLastConsumed(
            	    											current,
            	    											"vendorName",
            	    											lv_vendorName_15_0,
            	    											"org.eclipse.xtext.common.Terminals.STRING");
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalN4MFParser.g:275:3: ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:275:3: ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) )
            	    // InternalN4MFParser.g:276:4: {...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5)");
            	    }
            	    // InternalN4MFParser.g:276:112: ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) )
            	    // InternalN4MFParser.g:277:5: ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5);
            	    				
            	    // InternalN4MFParser.g:280:8: ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) )
            	    // InternalN4MFParser.g:280:9: {...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:280:18: (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) )
            	    // InternalN4MFParser.g:280:19: otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) )
            	    {
            	    otherlv_16=(Token)match(input,MainModule,FOLLOW_3); 

            	    								newLeafNode(otherlv_16, grammarAccess.getProjectDescriptionAccess().getMainModuleKeyword_5_0());
            	    							
            	    otherlv_17=(Token)match(input,Colon,FOLLOW_8); 

            	    								newLeafNode(otherlv_17, grammarAccess.getProjectDescriptionAccess().getColonKeyword_5_1());
            	    							
            	    // InternalN4MFParser.g:288:8: ( (lv_mainModule_18_0= RULE_STRING ) )
            	    // InternalN4MFParser.g:289:9: (lv_mainModule_18_0= RULE_STRING )
            	    {
            	    // InternalN4MFParser.g:289:9: (lv_mainModule_18_0= RULE_STRING )
            	    // InternalN4MFParser.g:290:10: lv_mainModule_18_0= RULE_STRING
            	    {
            	    lv_mainModule_18_0=(Token)match(input,RULE_STRING,FOLLOW_5); 

            	    										newLeafNode(lv_mainModule_18_0, grammarAccess.getProjectDescriptionAccess().getMainModuleSTRINGTerminalRuleCall_5_2_0());
            	    									

            	    										if (current==null) {
            	    											current = createModelElement(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										setWithLastConsumed(
            	    											current,
            	    											"mainModule",
            	    											lv_mainModule_18_0,
            	    											"org.eclipse.xtext.common.Terminals.STRING");
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 7 :
            	    // InternalN4MFParser.g:312:3: ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:312:3: ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) )
            	    // InternalN4MFParser.g:313:4: {...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6)");
            	    }
            	    // InternalN4MFParser.g:313:112: ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) )
            	    // InternalN4MFParser.g:314:5: ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6);
            	    				
            	    // InternalN4MFParser.g:317:8: ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) )
            	    // InternalN4MFParser.g:317:9: {...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:317:18: (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) )
            	    // InternalN4MFParser.g:317:19: otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) )
            	    {
            	    otherlv_19=(Token)match(input,ExtendedRuntimeEnvironment,FOLLOW_3); 

            	    								newLeafNode(otherlv_19, grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentKeyword_6_0());
            	    							
            	    otherlv_20=(Token)match(input,Colon,FOLLOW_4); 

            	    								newLeafNode(otherlv_20, grammarAccess.getProjectDescriptionAccess().getColonKeyword_6_1());
            	    							
            	    // InternalN4MFParser.g:325:8: ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) )
            	    // InternalN4MFParser.g:326:9: (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference )
            	    {
            	    // InternalN4MFParser.g:326:9: (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference )
            	    // InternalN4MFParser.g:327:10: lv_extendedRuntimeEnvironment_21_0= ruleProjectReference
            	    {

            	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_6_2_0());
            	    									
            	    pushFollow(FOLLOW_5);
            	    lv_extendedRuntimeEnvironment_21_0=ruleProjectReference();

            	    state._fsp--;


            	    										if (current==null) {
            	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										set(
            	    											current,
            	    											"extendedRuntimeEnvironment",
            	    											lv_extendedRuntimeEnvironment_21_0,
            	    											"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
            	    										afterParserOrEnumRuleCall();
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 8 :
            	    // InternalN4MFParser.g:350:3: ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:350:3: ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:351:4: {...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7)");
            	    }
            	    // InternalN4MFParser.g:351:112: ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:352:5: ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7);
            	    				
            	    // InternalN4MFParser.g:355:8: ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:355:9: {...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:355:18: (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket )
            	    // InternalN4MFParser.g:355:19: otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket
            	    {
            	    otherlv_22=(Token)match(input,ProvidedRuntimeLibraries,FOLLOW_9); 

            	    								newLeafNode(otherlv_22, grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesKeyword_7_0());
            	    							
            	    otherlv_23=(Token)match(input,LeftCurlyBracket,FOLLOW_10); 

            	    								newLeafNode(otherlv_23, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_7_1());
            	    							
            	    // InternalN4MFParser.g:363:8: ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )?
            	    int alt2=2;
            	    int LA2_0 = input.LA(1);

            	    if ( (LA2_0==ProjectDependencies||LA2_0==ProjectVersion||LA2_0==ModuleFilters||(LA2_0>=ProjectType && LA2_0<=Application)||LA2_0==VendorName||(LA2_0>=Libraries && LA2_0<=VendorId)||LA2_0==Sources||LA2_0==Content||LA2_0==Output||(LA2_0>=Test && LA2_0<=API)||LA2_0==RULE_ID) ) {
            	        alt2=1;
            	    }
            	    switch (alt2) {
            	        case 1 :
            	            // InternalN4MFParser.g:364:9: ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )*
            	            {
            	            // InternalN4MFParser.g:364:9: ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) )
            	            // InternalN4MFParser.g:365:10: (lv_providedRuntimeLibraries_24_0= ruleProjectReference )
            	            {
            	            // InternalN4MFParser.g:365:10: (lv_providedRuntimeLibraries_24_0= ruleProjectReference )
            	            // InternalN4MFParser.g:366:11: lv_providedRuntimeLibraries_24_0= ruleProjectReference
            	            {

            	            											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_0_0());
            	            										
            	            pushFollow(FOLLOW_11);
            	            lv_providedRuntimeLibraries_24_0=ruleProjectReference();

            	            state._fsp--;


            	            											if (current==null) {
            	            												current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            											}
            	            											add(
            	            												current,
            	            												"providedRuntimeLibraries",
            	            												lv_providedRuntimeLibraries_24_0,
            	            												"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
            	            											afterParserOrEnumRuleCall();
            	            										

            	            }


            	            }

            	            // InternalN4MFParser.g:383:9: (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )*
            	            loop1:
            	            do {
            	                int alt1=2;
            	                int LA1_0 = input.LA(1);

            	                if ( (LA1_0==Comma) ) {
            	                    alt1=1;
            	                }


            	                switch (alt1) {
            	            	case 1 :
            	            	    // InternalN4MFParser.g:384:10: otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) )
            	            	    {
            	            	    otherlv_25=(Token)match(input,Comma,FOLLOW_4); 

            	            	    										newLeafNode(otherlv_25, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_7_2_1_0());
            	            	    									
            	            	    // InternalN4MFParser.g:388:10: ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) )
            	            	    // InternalN4MFParser.g:389:11: (lv_providedRuntimeLibraries_26_0= ruleProjectReference )
            	            	    {
            	            	    // InternalN4MFParser.g:389:11: (lv_providedRuntimeLibraries_26_0= ruleProjectReference )
            	            	    // InternalN4MFParser.g:390:12: lv_providedRuntimeLibraries_26_0= ruleProjectReference
            	            	    {

            	            	    												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_1_1_0());
            	            	    											
            	            	    pushFollow(FOLLOW_11);
            	            	    lv_providedRuntimeLibraries_26_0=ruleProjectReference();

            	            	    state._fsp--;


            	            	    												if (current==null) {
            	            	    													current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            	    												}
            	            	    												add(
            	            	    													current,
            	            	    													"providedRuntimeLibraries",
            	            	    													lv_providedRuntimeLibraries_26_0,
            	            	    													"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
            	            	    												afterParserOrEnumRuleCall();
            	            	    											

            	            	    }


            	            	    }


            	            	    }
            	            	    break;

            	            	default :
            	            	    break loop1;
            	                }
            	            } while (true);


            	            }
            	            break;

            	    }

            	    otherlv_27=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_27, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_7_3());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 9 :
            	    // InternalN4MFParser.g:419:3: ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:419:3: ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:420:4: {...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8)");
            	    }
            	    // InternalN4MFParser.g:420:112: ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:421:5: ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8);
            	    				
            	    // InternalN4MFParser.g:424:8: ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:424:9: {...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:424:18: (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket )
            	    // InternalN4MFParser.g:424:19: otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket
            	    {
            	    otherlv_28=(Token)match(input,RequiredRuntimeLibraries,FOLLOW_9); 

            	    								newLeafNode(otherlv_28, grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesKeyword_8_0());
            	    							
            	    otherlv_29=(Token)match(input,LeftCurlyBracket,FOLLOW_10); 

            	    								newLeafNode(otherlv_29, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_8_1());
            	    							
            	    // InternalN4MFParser.g:432:8: ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )?
            	    int alt4=2;
            	    int LA4_0 = input.LA(1);

            	    if ( (LA4_0==ProjectDependencies||LA4_0==ProjectVersion||LA4_0==ModuleFilters||(LA4_0>=ProjectType && LA4_0<=Application)||LA4_0==VendorName||(LA4_0>=Libraries && LA4_0<=VendorId)||LA4_0==Sources||LA4_0==Content||LA4_0==Output||(LA4_0>=Test && LA4_0<=API)||LA4_0==RULE_ID) ) {
            	        alt4=1;
            	    }
            	    switch (alt4) {
            	        case 1 :
            	            // InternalN4MFParser.g:433:9: ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )*
            	            {
            	            // InternalN4MFParser.g:433:9: ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) )
            	            // InternalN4MFParser.g:434:10: (lv_requiredRuntimeLibraries_30_0= ruleProjectReference )
            	            {
            	            // InternalN4MFParser.g:434:10: (lv_requiredRuntimeLibraries_30_0= ruleProjectReference )
            	            // InternalN4MFParser.g:435:11: lv_requiredRuntimeLibraries_30_0= ruleProjectReference
            	            {

            	            											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_0_0());
            	            										
            	            pushFollow(FOLLOW_11);
            	            lv_requiredRuntimeLibraries_30_0=ruleProjectReference();

            	            state._fsp--;


            	            											if (current==null) {
            	            												current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            											}
            	            											add(
            	            												current,
            	            												"requiredRuntimeLibraries",
            	            												lv_requiredRuntimeLibraries_30_0,
            	            												"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
            	            											afterParserOrEnumRuleCall();
            	            										

            	            }


            	            }

            	            // InternalN4MFParser.g:452:9: (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )*
            	            loop3:
            	            do {
            	                int alt3=2;
            	                int LA3_0 = input.LA(1);

            	                if ( (LA3_0==Comma) ) {
            	                    alt3=1;
            	                }


            	                switch (alt3) {
            	            	case 1 :
            	            	    // InternalN4MFParser.g:453:10: otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) )
            	            	    {
            	            	    otherlv_31=(Token)match(input,Comma,FOLLOW_4); 

            	            	    										newLeafNode(otherlv_31, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_8_2_1_0());
            	            	    									
            	            	    // InternalN4MFParser.g:457:10: ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) )
            	            	    // InternalN4MFParser.g:458:11: (lv_requiredRuntimeLibraries_32_0= ruleProjectReference )
            	            	    {
            	            	    // InternalN4MFParser.g:458:11: (lv_requiredRuntimeLibraries_32_0= ruleProjectReference )
            	            	    // InternalN4MFParser.g:459:12: lv_requiredRuntimeLibraries_32_0= ruleProjectReference
            	            	    {

            	            	    												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_1_1_0());
            	            	    											
            	            	    pushFollow(FOLLOW_11);
            	            	    lv_requiredRuntimeLibraries_32_0=ruleProjectReference();

            	            	    state._fsp--;


            	            	    												if (current==null) {
            	            	    													current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            	    												}
            	            	    												add(
            	            	    													current,
            	            	    													"requiredRuntimeLibraries",
            	            	    													lv_requiredRuntimeLibraries_32_0,
            	            	    													"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
            	            	    												afterParserOrEnumRuleCall();
            	            	    											

            	            	    }


            	            	    }


            	            	    }
            	            	    break;

            	            	default :
            	            	    break loop3;
            	                }
            	            } while (true);


            	            }
            	            break;

            	    }

            	    otherlv_33=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_33, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_8_3());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 10 :
            	    // InternalN4MFParser.g:488:3: ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:488:3: ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:489:4: {...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9)");
            	    }
            	    // InternalN4MFParser.g:489:112: ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:490:5: ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9);
            	    				
            	    // InternalN4MFParser.g:493:8: ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:493:9: {...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:493:18: (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket )
            	    // InternalN4MFParser.g:493:19: otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket
            	    {
            	    otherlv_34=(Token)match(input,ProjectDependencies,FOLLOW_9); 

            	    								newLeafNode(otherlv_34, grammarAccess.getProjectDescriptionAccess().getProjectDependenciesKeyword_9_0());
            	    							
            	    otherlv_35=(Token)match(input,LeftCurlyBracket,FOLLOW_10); 

            	    								newLeafNode(otherlv_35, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_9_1());
            	    							
            	    // InternalN4MFParser.g:501:8: ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )?
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( (LA6_0==ProjectDependencies||LA6_0==ProjectVersion||LA6_0==ModuleFilters||(LA6_0>=ProjectType && LA6_0<=Application)||LA6_0==VendorName||(LA6_0>=Libraries && LA6_0<=VendorId)||LA6_0==Sources||LA6_0==Content||LA6_0==Output||(LA6_0>=Test && LA6_0<=API)||LA6_0==RULE_ID) ) {
            	        alt6=1;
            	    }
            	    switch (alt6) {
            	        case 1 :
            	            // InternalN4MFParser.g:502:9: ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )*
            	            {
            	            // InternalN4MFParser.g:502:9: ( (lv_projectDependencies_36_0= ruleProjectDependency ) )
            	            // InternalN4MFParser.g:503:10: (lv_projectDependencies_36_0= ruleProjectDependency )
            	            {
            	            // InternalN4MFParser.g:503:10: (lv_projectDependencies_36_0= ruleProjectDependency )
            	            // InternalN4MFParser.g:504:11: lv_projectDependencies_36_0= ruleProjectDependency
            	            {

            	            											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependencyParserRuleCall_9_2_0_0());
            	            										
            	            pushFollow(FOLLOW_11);
            	            lv_projectDependencies_36_0=ruleProjectDependency();

            	            state._fsp--;


            	            											if (current==null) {
            	            												current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            											}
            	            											add(
            	            												current,
            	            												"projectDependencies",
            	            												lv_projectDependencies_36_0,
            	            												"org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
            	            											afterParserOrEnumRuleCall();
            	            										

            	            }


            	            }

            	            // InternalN4MFParser.g:521:9: (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )*
            	            loop5:
            	            do {
            	                int alt5=2;
            	                int LA5_0 = input.LA(1);

            	                if ( (LA5_0==Comma) ) {
            	                    alt5=1;
            	                }


            	                switch (alt5) {
            	            	case 1 :
            	            	    // InternalN4MFParser.g:522:10: otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) )
            	            	    {
            	            	    otherlv_37=(Token)match(input,Comma,FOLLOW_4); 

            	            	    										newLeafNode(otherlv_37, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_9_2_1_0());
            	            	    									
            	            	    // InternalN4MFParser.g:526:10: ( (lv_projectDependencies_38_0= ruleProjectDependency ) )
            	            	    // InternalN4MFParser.g:527:11: (lv_projectDependencies_38_0= ruleProjectDependency )
            	            	    {
            	            	    // InternalN4MFParser.g:527:11: (lv_projectDependencies_38_0= ruleProjectDependency )
            	            	    // InternalN4MFParser.g:528:12: lv_projectDependencies_38_0= ruleProjectDependency
            	            	    {

            	            	    												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependencyParserRuleCall_9_2_1_1_0());
            	            	    											
            	            	    pushFollow(FOLLOW_11);
            	            	    lv_projectDependencies_38_0=ruleProjectDependency();

            	            	    state._fsp--;


            	            	    												if (current==null) {
            	            	    													current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            	    												}
            	            	    												add(
            	            	    													current,
            	            	    													"projectDependencies",
            	            	    													lv_projectDependencies_38_0,
            	            	    													"org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
            	            	    												afterParserOrEnumRuleCall();
            	            	    											

            	            	    }


            	            	    }


            	            	    }
            	            	    break;

            	            	default :
            	            	    break loop5;
            	                }
            	            } while (true);


            	            }
            	            break;

            	    }

            	    otherlv_39=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_39, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_9_3());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 11 :
            	    // InternalN4MFParser.g:557:3: ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:557:3: ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) )
            	    // InternalN4MFParser.g:558:4: {...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10)");
            	    }
            	    // InternalN4MFParser.g:558:113: ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) )
            	    // InternalN4MFParser.g:559:5: ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10);
            	    				
            	    // InternalN4MFParser.g:562:8: ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) )
            	    // InternalN4MFParser.g:562:9: {...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:562:18: (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) )
            	    // InternalN4MFParser.g:562:19: otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) )
            	    {
            	    otherlv_40=(Token)match(input,ImplementationId,FOLLOW_3); 

            	    								newLeafNode(otherlv_40, grammarAccess.getProjectDescriptionAccess().getImplementationIdKeyword_10_0());
            	    							
            	    otherlv_41=(Token)match(input,Colon,FOLLOW_4); 

            	    								newLeafNode(otherlv_41, grammarAccess.getProjectDescriptionAccess().getColonKeyword_10_1());
            	    							
            	    // InternalN4MFParser.g:570:8: ( (lv_implementationId_42_0= ruleN4mfIdentifier ) )
            	    // InternalN4MFParser.g:571:9: (lv_implementationId_42_0= ruleN4mfIdentifier )
            	    {
            	    // InternalN4MFParser.g:571:9: (lv_implementationId_42_0= ruleN4mfIdentifier )
            	    // InternalN4MFParser.g:572:10: lv_implementationId_42_0= ruleN4mfIdentifier
            	    {

            	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getImplementationIdN4mfIdentifierParserRuleCall_10_2_0());
            	    									
            	    pushFollow(FOLLOW_5);
            	    lv_implementationId_42_0=ruleN4mfIdentifier();

            	    state._fsp--;


            	    										if (current==null) {
            	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										set(
            	    											current,
            	    											"implementationId",
            	    											lv_implementationId_42_0,
            	    											"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
            	    										afterParserOrEnumRuleCall();
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 12 :
            	    // InternalN4MFParser.g:595:3: ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:595:3: ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:596:4: {...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11)");
            	    }
            	    // InternalN4MFParser.g:596:113: ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:597:5: ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11);
            	    				
            	    // InternalN4MFParser.g:600:8: ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:600:9: {...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:600:18: (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket )
            	    // InternalN4MFParser.g:600:19: otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket
            	    {
            	    otherlv_43=(Token)match(input,ImplementedProjects,FOLLOW_9); 

            	    								newLeafNode(otherlv_43, grammarAccess.getProjectDescriptionAccess().getImplementedProjectsKeyword_11_0());
            	    							
            	    otherlv_44=(Token)match(input,LeftCurlyBracket,FOLLOW_10); 

            	    								newLeafNode(otherlv_44, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_11_1());
            	    							
            	    // InternalN4MFParser.g:608:8: ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )?
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==ProjectDependencies||LA8_0==ProjectVersion||LA8_0==ModuleFilters||(LA8_0>=ProjectType && LA8_0<=Application)||LA8_0==VendorName||(LA8_0>=Libraries && LA8_0<=VendorId)||LA8_0==Sources||LA8_0==Content||LA8_0==Output||(LA8_0>=Test && LA8_0<=API)||LA8_0==RULE_ID) ) {
            	        alt8=1;
            	    }
            	    switch (alt8) {
            	        case 1 :
            	            // InternalN4MFParser.g:609:9: ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )*
            	            {
            	            // InternalN4MFParser.g:609:9: ( (lv_implementedProjects_45_0= ruleProjectReference ) )
            	            // InternalN4MFParser.g:610:10: (lv_implementedProjects_45_0= ruleProjectReference )
            	            {
            	            // InternalN4MFParser.g:610:10: (lv_implementedProjects_45_0= ruleProjectReference )
            	            // InternalN4MFParser.g:611:11: lv_implementedProjects_45_0= ruleProjectReference
            	            {

            	            											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsProjectReferenceParserRuleCall_11_2_0_0());
            	            										
            	            pushFollow(FOLLOW_11);
            	            lv_implementedProjects_45_0=ruleProjectReference();

            	            state._fsp--;


            	            											if (current==null) {
            	            												current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            											}
            	            											add(
            	            												current,
            	            												"implementedProjects",
            	            												lv_implementedProjects_45_0,
            	            												"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
            	            											afterParserOrEnumRuleCall();
            	            										

            	            }


            	            }

            	            // InternalN4MFParser.g:628:9: (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )*
            	            loop7:
            	            do {
            	                int alt7=2;
            	                int LA7_0 = input.LA(1);

            	                if ( (LA7_0==Comma) ) {
            	                    alt7=1;
            	                }


            	                switch (alt7) {
            	            	case 1 :
            	            	    // InternalN4MFParser.g:629:10: otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) )
            	            	    {
            	            	    otherlv_46=(Token)match(input,Comma,FOLLOW_4); 

            	            	    										newLeafNode(otherlv_46, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_11_2_1_0());
            	            	    									
            	            	    // InternalN4MFParser.g:633:10: ( (lv_implementedProjects_47_0= ruleProjectReference ) )
            	            	    // InternalN4MFParser.g:634:11: (lv_implementedProjects_47_0= ruleProjectReference )
            	            	    {
            	            	    // InternalN4MFParser.g:634:11: (lv_implementedProjects_47_0= ruleProjectReference )
            	            	    // InternalN4MFParser.g:635:12: lv_implementedProjects_47_0= ruleProjectReference
            	            	    {

            	            	    												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsProjectReferenceParserRuleCall_11_2_1_1_0());
            	            	    											
            	            	    pushFollow(FOLLOW_11);
            	            	    lv_implementedProjects_47_0=ruleProjectReference();

            	            	    state._fsp--;


            	            	    												if (current==null) {
            	            	    													current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            	    												}
            	            	    												add(
            	            	    													current,
            	            	    													"implementedProjects",
            	            	    													lv_implementedProjects_47_0,
            	            	    													"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
            	            	    												afterParserOrEnumRuleCall();
            	            	    											

            	            	    }


            	            	    }


            	            	    }
            	            	    break;

            	            	default :
            	            	    break loop7;
            	                }
            	            } while (true);


            	            }
            	            break;

            	    }

            	    otherlv_48=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_48, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_11_3());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 13 :
            	    // InternalN4MFParser.g:664:3: ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:664:3: ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:665:4: {...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12)");
            	    }
            	    // InternalN4MFParser.g:665:113: ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:666:5: ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12);
            	    				
            	    // InternalN4MFParser.g:669:8: ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:669:9: {...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:669:18: (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket )
            	    // InternalN4MFParser.g:669:19: otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket
            	    {
            	    otherlv_49=(Token)match(input,InitModules,FOLLOW_9); 

            	    								newLeafNode(otherlv_49, grammarAccess.getProjectDescriptionAccess().getInitModulesKeyword_12_0());
            	    							
            	    otherlv_50=(Token)match(input,LeftCurlyBracket,FOLLOW_12); 

            	    								newLeafNode(otherlv_50, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_12_1());
            	    							
            	    // InternalN4MFParser.g:677:8: ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )?
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==RULE_STRING) ) {
            	        alt10=1;
            	    }
            	    switch (alt10) {
            	        case 1 :
            	            // InternalN4MFParser.g:678:9: ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )*
            	            {
            	            // InternalN4MFParser.g:678:9: ( (lv_initModules_51_0= ruleBootstrapModule ) )
            	            // InternalN4MFParser.g:679:10: (lv_initModules_51_0= ruleBootstrapModule )
            	            {
            	            // InternalN4MFParser.g:679:10: (lv_initModules_51_0= ruleBootstrapModule )
            	            // InternalN4MFParser.g:680:11: lv_initModules_51_0= ruleBootstrapModule
            	            {

            	            											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getInitModulesBootstrapModuleParserRuleCall_12_2_0_0());
            	            										
            	            pushFollow(FOLLOW_11);
            	            lv_initModules_51_0=ruleBootstrapModule();

            	            state._fsp--;


            	            											if (current==null) {
            	            												current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            											}
            	            											add(
            	            												current,
            	            												"initModules",
            	            												lv_initModules_51_0,
            	            												"org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
            	            											afterParserOrEnumRuleCall();
            	            										

            	            }


            	            }

            	            // InternalN4MFParser.g:697:9: (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )*
            	            loop9:
            	            do {
            	                int alt9=2;
            	                int LA9_0 = input.LA(1);

            	                if ( (LA9_0==Comma) ) {
            	                    alt9=1;
            	                }


            	                switch (alt9) {
            	            	case 1 :
            	            	    // InternalN4MFParser.g:698:10: otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) )
            	            	    {
            	            	    otherlv_52=(Token)match(input,Comma,FOLLOW_8); 

            	            	    										newLeafNode(otherlv_52, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_12_2_1_0());
            	            	    									
            	            	    // InternalN4MFParser.g:702:10: ( (lv_initModules_53_0= ruleBootstrapModule ) )
            	            	    // InternalN4MFParser.g:703:11: (lv_initModules_53_0= ruleBootstrapModule )
            	            	    {
            	            	    // InternalN4MFParser.g:703:11: (lv_initModules_53_0= ruleBootstrapModule )
            	            	    // InternalN4MFParser.g:704:12: lv_initModules_53_0= ruleBootstrapModule
            	            	    {

            	            	    												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getInitModulesBootstrapModuleParserRuleCall_12_2_1_1_0());
            	            	    											
            	            	    pushFollow(FOLLOW_11);
            	            	    lv_initModules_53_0=ruleBootstrapModule();

            	            	    state._fsp--;


            	            	    												if (current==null) {
            	            	    													current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            	    												}
            	            	    												add(
            	            	    													current,
            	            	    													"initModules",
            	            	    													lv_initModules_53_0,
            	            	    													"org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
            	            	    												afterParserOrEnumRuleCall();
            	            	    											

            	            	    }


            	            	    }


            	            	    }
            	            	    break;

            	            	default :
            	            	    break loop9;
            	                }
            	            } while (true);


            	            }
            	            break;

            	    }

            	    otherlv_54=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_54, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_12_3());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 14 :
            	    // InternalN4MFParser.g:733:3: ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:733:3: ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) )
            	    // InternalN4MFParser.g:734:4: {...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13)");
            	    }
            	    // InternalN4MFParser.g:734:113: ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) )
            	    // InternalN4MFParser.g:735:5: ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13);
            	    				
            	    // InternalN4MFParser.g:738:8: ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) )
            	    // InternalN4MFParser.g:738:9: {...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:738:18: (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) )
            	    // InternalN4MFParser.g:738:19: otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) )
            	    {
            	    otherlv_55=(Token)match(input,ExecModule,FOLLOW_3); 

            	    								newLeafNode(otherlv_55, grammarAccess.getProjectDescriptionAccess().getExecModuleKeyword_13_0());
            	    							
            	    otherlv_56=(Token)match(input,Colon,FOLLOW_8); 

            	    								newLeafNode(otherlv_56, grammarAccess.getProjectDescriptionAccess().getColonKeyword_13_1());
            	    							
            	    // InternalN4MFParser.g:746:8: ( (lv_execModule_57_0= ruleBootstrapModule ) )
            	    // InternalN4MFParser.g:747:9: (lv_execModule_57_0= ruleBootstrapModule )
            	    {
            	    // InternalN4MFParser.g:747:9: (lv_execModule_57_0= ruleBootstrapModule )
            	    // InternalN4MFParser.g:748:10: lv_execModule_57_0= ruleBootstrapModule
            	    {

            	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getExecModuleBootstrapModuleParserRuleCall_13_2_0());
            	    									
            	    pushFollow(FOLLOW_5);
            	    lv_execModule_57_0=ruleBootstrapModule();

            	    state._fsp--;


            	    										if (current==null) {
            	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										set(
            	    											current,
            	    											"execModule",
            	    											lv_execModule_57_0,
            	    											"org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
            	    										afterParserOrEnumRuleCall();
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 15 :
            	    // InternalN4MFParser.g:771:3: ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:771:3: ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) )
            	    // InternalN4MFParser.g:772:4: {...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14)");
            	    }
            	    // InternalN4MFParser.g:772:113: ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) )
            	    // InternalN4MFParser.g:773:5: ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14);
            	    				
            	    // InternalN4MFParser.g:776:8: ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) )
            	    // InternalN4MFParser.g:776:9: {...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:776:18: (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) )
            	    // InternalN4MFParser.g:776:19: otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) )
            	    {
            	    otherlv_58=(Token)match(input,Output,FOLLOW_3); 

            	    								newLeafNode(otherlv_58, grammarAccess.getProjectDescriptionAccess().getOutputKeyword_14_0());
            	    							
            	    otherlv_59=(Token)match(input,Colon,FOLLOW_8); 

            	    								newLeafNode(otherlv_59, grammarAccess.getProjectDescriptionAccess().getColonKeyword_14_1());
            	    							
            	    // InternalN4MFParser.g:784:8: ( (lv_outputPathRaw_60_0= RULE_STRING ) )
            	    // InternalN4MFParser.g:785:9: (lv_outputPathRaw_60_0= RULE_STRING )
            	    {
            	    // InternalN4MFParser.g:785:9: (lv_outputPathRaw_60_0= RULE_STRING )
            	    // InternalN4MFParser.g:786:10: lv_outputPathRaw_60_0= RULE_STRING
            	    {
            	    lv_outputPathRaw_60_0=(Token)match(input,RULE_STRING,FOLLOW_5); 

            	    										newLeafNode(lv_outputPathRaw_60_0, grammarAccess.getProjectDescriptionAccess().getOutputPathRawSTRINGTerminalRuleCall_14_2_0());
            	    									

            	    										if (current==null) {
            	    											current = createModelElement(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										setWithLastConsumed(
            	    											current,
            	    											"outputPathRaw",
            	    											lv_outputPathRaw_60_0,
            	    											"org.eclipse.xtext.common.Terminals.STRING");
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 16 :
            	    // InternalN4MFParser.g:808:3: ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:808:3: ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:809:4: {...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15)");
            	    }
            	    // InternalN4MFParser.g:809:113: ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:810:5: ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15);
            	    				
            	    // InternalN4MFParser.g:813:8: ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:813:9: {...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:813:18: (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket )
            	    // InternalN4MFParser.g:813:19: otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket
            	    {
            	    otherlv_61=(Token)match(input,Libraries,FOLLOW_9); 

            	    								newLeafNode(otherlv_61, grammarAccess.getProjectDescriptionAccess().getLibrariesKeyword_15_0());
            	    							
            	    otherlv_62=(Token)match(input,LeftCurlyBracket,FOLLOW_8); 

            	    								newLeafNode(otherlv_62, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_15_1());
            	    							
            	    // InternalN4MFParser.g:821:8: ( (lv_libraryPathsRaw_63_0= RULE_STRING ) )
            	    // InternalN4MFParser.g:822:9: (lv_libraryPathsRaw_63_0= RULE_STRING )
            	    {
            	    // InternalN4MFParser.g:822:9: (lv_libraryPathsRaw_63_0= RULE_STRING )
            	    // InternalN4MFParser.g:823:10: lv_libraryPathsRaw_63_0= RULE_STRING
            	    {
            	    lv_libraryPathsRaw_63_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

            	    										newLeafNode(lv_libraryPathsRaw_63_0, grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_2_0());
            	    									

            	    										if (current==null) {
            	    											current = createModelElement(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										addWithLastConsumed(
            	    											current,
            	    											"libraryPathsRaw",
            	    											lv_libraryPathsRaw_63_0,
            	    											"org.eclipse.xtext.common.Terminals.STRING");
            	    									

            	    }


            	    }

            	    // InternalN4MFParser.g:839:8: (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )*
            	    loop11:
            	    do {
            	        int alt11=2;
            	        int LA11_0 = input.LA(1);

            	        if ( (LA11_0==Comma) ) {
            	            alt11=1;
            	        }


            	        switch (alt11) {
            	    	case 1 :
            	    	    // InternalN4MFParser.g:840:9: otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_64=(Token)match(input,Comma,FOLLOW_8); 

            	    	    									newLeafNode(otherlv_64, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_15_3_0());
            	    	    								
            	    	    // InternalN4MFParser.g:844:9: ( (lv_libraryPathsRaw_65_0= RULE_STRING ) )
            	    	    // InternalN4MFParser.g:845:10: (lv_libraryPathsRaw_65_0= RULE_STRING )
            	    	    {
            	    	    // InternalN4MFParser.g:845:10: (lv_libraryPathsRaw_65_0= RULE_STRING )
            	    	    // InternalN4MFParser.g:846:11: lv_libraryPathsRaw_65_0= RULE_STRING
            	    	    {
            	    	    lv_libraryPathsRaw_65_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

            	    	    											newLeafNode(lv_libraryPathsRaw_65_0, grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_3_1_0());
            	    	    										

            	    	    											if (current==null) {
            	    	    												current = createModelElement(grammarAccess.getProjectDescriptionRule());
            	    	    											}
            	    	    											addWithLastConsumed(
            	    	    												current,
            	    	    												"libraryPathsRaw",
            	    	    												lv_libraryPathsRaw_65_0,
            	    	    												"org.eclipse.xtext.common.Terminals.STRING");
            	    	    										

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop11;
            	        }
            	    } while (true);

            	    otherlv_66=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_66, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_15_4());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 17 :
            	    // InternalN4MFParser.g:873:3: ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:873:3: ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:874:4: {...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16)");
            	    }
            	    // InternalN4MFParser.g:874:113: ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:875:5: ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16);
            	    				
            	    // InternalN4MFParser.g:878:8: ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:878:9: {...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:878:18: (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket )
            	    // InternalN4MFParser.g:878:19: otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket
            	    {
            	    otherlv_67=(Token)match(input,Resources,FOLLOW_9); 

            	    								newLeafNode(otherlv_67, grammarAccess.getProjectDescriptionAccess().getResourcesKeyword_16_0());
            	    							
            	    otherlv_68=(Token)match(input,LeftCurlyBracket,FOLLOW_8); 

            	    								newLeafNode(otherlv_68, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_16_1());
            	    							
            	    // InternalN4MFParser.g:886:8: ( (lv_resourcePathsRaw_69_0= RULE_STRING ) )
            	    // InternalN4MFParser.g:887:9: (lv_resourcePathsRaw_69_0= RULE_STRING )
            	    {
            	    // InternalN4MFParser.g:887:9: (lv_resourcePathsRaw_69_0= RULE_STRING )
            	    // InternalN4MFParser.g:888:10: lv_resourcePathsRaw_69_0= RULE_STRING
            	    {
            	    lv_resourcePathsRaw_69_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

            	    										newLeafNode(lv_resourcePathsRaw_69_0, grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_2_0());
            	    									

            	    										if (current==null) {
            	    											current = createModelElement(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										addWithLastConsumed(
            	    											current,
            	    											"resourcePathsRaw",
            	    											lv_resourcePathsRaw_69_0,
            	    											"org.eclipse.xtext.common.Terminals.STRING");
            	    									

            	    }


            	    }

            	    // InternalN4MFParser.g:904:8: (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )*
            	    loop12:
            	    do {
            	        int alt12=2;
            	        int LA12_0 = input.LA(1);

            	        if ( (LA12_0==Comma) ) {
            	            alt12=1;
            	        }


            	        switch (alt12) {
            	    	case 1 :
            	    	    // InternalN4MFParser.g:905:9: otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) )
            	    	    {
            	    	    otherlv_70=(Token)match(input,Comma,FOLLOW_8); 

            	    	    									newLeafNode(otherlv_70, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_16_3_0());
            	    	    								
            	    	    // InternalN4MFParser.g:909:9: ( (lv_resourcePathsRaw_71_0= RULE_STRING ) )
            	    	    // InternalN4MFParser.g:910:10: (lv_resourcePathsRaw_71_0= RULE_STRING )
            	    	    {
            	    	    // InternalN4MFParser.g:910:10: (lv_resourcePathsRaw_71_0= RULE_STRING )
            	    	    // InternalN4MFParser.g:911:11: lv_resourcePathsRaw_71_0= RULE_STRING
            	    	    {
            	    	    lv_resourcePathsRaw_71_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

            	    	    											newLeafNode(lv_resourcePathsRaw_71_0, grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_3_1_0());
            	    	    										

            	    	    											if (current==null) {
            	    	    												current = createModelElement(grammarAccess.getProjectDescriptionRule());
            	    	    											}
            	    	    											addWithLastConsumed(
            	    	    												current,
            	    	    												"resourcePathsRaw",
            	    	    												lv_resourcePathsRaw_71_0,
            	    	    												"org.eclipse.xtext.common.Terminals.STRING");
            	    	    										

            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop12;
            	        }
            	    } while (true);

            	    otherlv_72=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_72, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_16_4());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 18 :
            	    // InternalN4MFParser.g:938:3: ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:938:3: ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:939:4: {...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17)");
            	    }
            	    // InternalN4MFParser.g:939:113: ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:940:5: ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17);
            	    				
            	    // InternalN4MFParser.g:943:8: ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:943:9: {...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:943:18: (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket )
            	    // InternalN4MFParser.g:943:19: otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket
            	    {
            	    otherlv_73=(Token)match(input,Sources,FOLLOW_9); 

            	    								newLeafNode(otherlv_73, grammarAccess.getProjectDescriptionAccess().getSourcesKeyword_17_0());
            	    							
            	    otherlv_74=(Token)match(input,LeftCurlyBracket,FOLLOW_13); 

            	    								newLeafNode(otherlv_74, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_17_1());
            	    							
            	    // InternalN4MFParser.g:951:8: ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+
            	    int cnt13=0;
            	    loop13:
            	    do {
            	        int alt13=2;
            	        int LA13_0 = input.LA(1);

            	        if ( (LA13_0==External||LA13_0==Source||LA13_0==Test) ) {
            	            alt13=1;
            	        }


            	        switch (alt13) {
            	    	case 1 :
            	    	    // InternalN4MFParser.g:952:9: (lv_sourceFragment_75_0= ruleSourceFragment )
            	    	    {
            	    	    // InternalN4MFParser.g:952:9: (lv_sourceFragment_75_0= ruleSourceFragment )
            	    	    // InternalN4MFParser.g:953:10: lv_sourceFragment_75_0= ruleSourceFragment
            	    	    {

            	    	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getSourceFragmentSourceFragmentParserRuleCall_17_2_0());
            	    	    									
            	    	    pushFollow(FOLLOW_14);
            	    	    lv_sourceFragment_75_0=ruleSourceFragment();

            	    	    state._fsp--;


            	    	    										if (current==null) {
            	    	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    	    										}
            	    	    										add(
            	    	    											current,
            	    	    											"sourceFragment",
            	    	    											lv_sourceFragment_75_0,
            	    	    											"org.eclipse.n4js.n4mf.N4MF.SourceFragment");
            	    	    										afterParserOrEnumRuleCall();
            	    	    									

            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt13 >= 1 ) break loop13;
            	                EarlyExitException eee =
            	                    new EarlyExitException(13, input);
            	                throw eee;
            	        }
            	        cnt13++;
            	    } while (true);

            	    otherlv_76=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_76, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_17_3());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 19 :
            	    // InternalN4MFParser.g:980:3: ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:980:3: ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:981:4: {...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18)");
            	    }
            	    // InternalN4MFParser.g:981:113: ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:982:5: ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18);
            	    				
            	    // InternalN4MFParser.g:985:8: ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:985:9: {...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:985:18: (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket )
            	    // InternalN4MFParser.g:985:19: otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket
            	    {
            	    otherlv_77=(Token)match(input,ModuleFilters,FOLLOW_9); 

            	    								newLeafNode(otherlv_77, grammarAccess.getProjectDescriptionAccess().getModuleFiltersKeyword_18_0());
            	    							
            	    otherlv_78=(Token)match(input,LeftCurlyBracket,FOLLOW_15); 

            	    								newLeafNode(otherlv_78, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_18_1());
            	    							
            	    // InternalN4MFParser.g:993:8: ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+
            	    int cnt14=0;
            	    loop14:
            	    do {
            	        int alt14=2;
            	        int LA14_0 = input.LA(1);

            	        if ( (LA14_0==NoModuleWrap||LA14_0==NoValidate) ) {
            	            alt14=1;
            	        }


            	        switch (alt14) {
            	    	case 1 :
            	    	    // InternalN4MFParser.g:994:9: (lv_moduleFilters_79_0= ruleModuleFilter )
            	    	    {
            	    	    // InternalN4MFParser.g:994:9: (lv_moduleFilters_79_0= ruleModuleFilter )
            	    	    // InternalN4MFParser.g:995:10: lv_moduleFilters_79_0= ruleModuleFilter
            	    	    {

            	    	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getModuleFiltersModuleFilterParserRuleCall_18_2_0());
            	    	    									
            	    	    pushFollow(FOLLOW_16);
            	    	    lv_moduleFilters_79_0=ruleModuleFilter();

            	    	    state._fsp--;


            	    	    										if (current==null) {
            	    	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    	    										}
            	    	    										add(
            	    	    											current,
            	    	    											"moduleFilters",
            	    	    											lv_moduleFilters_79_0,
            	    	    											"org.eclipse.n4js.n4mf.N4MF.ModuleFilter");
            	    	    										afterParserOrEnumRuleCall();
            	    	    									

            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt14 >= 1 ) break loop14;
            	                EarlyExitException eee =
            	                    new EarlyExitException(14, input);
            	                throw eee;
            	        }
            	        cnt14++;
            	    } while (true);

            	    otherlv_80=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_80, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_18_3());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 20 :
            	    // InternalN4MFParser.g:1022:3: ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) )
            	    {
            	    // InternalN4MFParser.g:1022:3: ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) )
            	    // InternalN4MFParser.g:1023:4: {...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19)");
            	    }
            	    // InternalN4MFParser.g:1023:113: ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) )
            	    // InternalN4MFParser.g:1024:5: ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19);
            	    				
            	    // InternalN4MFParser.g:1027:8: ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) )
            	    // InternalN4MFParser.g:1027:9: {...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:1027:18: (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket )
            	    // InternalN4MFParser.g:1027:19: otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket
            	    {
            	    otherlv_81=(Token)match(input,TestedProjects,FOLLOW_9); 

            	    								newLeafNode(otherlv_81, grammarAccess.getProjectDescriptionAccess().getTestedProjectsKeyword_19_0());
            	    							
            	    otherlv_82=(Token)match(input,LeftCurlyBracket,FOLLOW_10); 

            	    								newLeafNode(otherlv_82, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_19_1());
            	    							
            	    // InternalN4MFParser.g:1035:8: ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )?
            	    int alt16=2;
            	    int LA16_0 = input.LA(1);

            	    if ( (LA16_0==ProjectDependencies||LA16_0==ProjectVersion||LA16_0==ModuleFilters||(LA16_0>=ProjectType && LA16_0<=Application)||LA16_0==VendorName||(LA16_0>=Libraries && LA16_0<=VendorId)||LA16_0==Sources||LA16_0==Content||LA16_0==Output||(LA16_0>=Test && LA16_0<=API)||LA16_0==RULE_ID) ) {
            	        alt16=1;
            	    }
            	    switch (alt16) {
            	        case 1 :
            	            // InternalN4MFParser.g:1036:9: ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )*
            	            {
            	            // InternalN4MFParser.g:1036:9: ( (lv_testedProjects_83_0= ruleProjectDependency ) )
            	            // InternalN4MFParser.g:1037:10: (lv_testedProjects_83_0= ruleProjectDependency )
            	            {
            	            // InternalN4MFParser.g:1037:10: (lv_testedProjects_83_0= ruleProjectDependency )
            	            // InternalN4MFParser.g:1038:11: lv_testedProjects_83_0= ruleProjectDependency
            	            {

            	            											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getTestedProjectsProjectDependencyParserRuleCall_19_2_0_0());
            	            										
            	            pushFollow(FOLLOW_11);
            	            lv_testedProjects_83_0=ruleProjectDependency();

            	            state._fsp--;


            	            											if (current==null) {
            	            												current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            											}
            	            											add(
            	            												current,
            	            												"testedProjects",
            	            												lv_testedProjects_83_0,
            	            												"org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
            	            											afterParserOrEnumRuleCall();
            	            										

            	            }


            	            }

            	            // InternalN4MFParser.g:1055:9: (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )*
            	            loop15:
            	            do {
            	                int alt15=2;
            	                int LA15_0 = input.LA(1);

            	                if ( (LA15_0==Comma) ) {
            	                    alt15=1;
            	                }


            	                switch (alt15) {
            	            	case 1 :
            	            	    // InternalN4MFParser.g:1056:10: otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) )
            	            	    {
            	            	    otherlv_84=(Token)match(input,Comma,FOLLOW_4); 

            	            	    										newLeafNode(otherlv_84, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_19_2_1_0());
            	            	    									
            	            	    // InternalN4MFParser.g:1060:10: ( (lv_testedProjects_85_0= ruleProjectDependency ) )
            	            	    // InternalN4MFParser.g:1061:11: (lv_testedProjects_85_0= ruleProjectDependency )
            	            	    {
            	            	    // InternalN4MFParser.g:1061:11: (lv_testedProjects_85_0= ruleProjectDependency )
            	            	    // InternalN4MFParser.g:1062:12: lv_testedProjects_85_0= ruleProjectDependency
            	            	    {

            	            	    												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getTestedProjectsProjectDependencyParserRuleCall_19_2_1_1_0());
            	            	    											
            	            	    pushFollow(FOLLOW_11);
            	            	    lv_testedProjects_85_0=ruleProjectDependency();

            	            	    state._fsp--;


            	            	    												if (current==null) {
            	            	    													current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	            	    												}
            	            	    												add(
            	            	    													current,
            	            	    													"testedProjects",
            	            	    													lv_testedProjects_85_0,
            	            	    													"org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
            	            	    												afterParserOrEnumRuleCall();
            	            	    											

            	            	    }


            	            	    }


            	            	    }
            	            	    break;

            	            	default :
            	            	    break loop15;
            	                }
            	            } while (true);


            	            }
            	            break;

            	    }

            	    otherlv_86=(Token)match(input,RightCurlyBracket,FOLLOW_5); 

            	    								newLeafNode(otherlv_86, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_19_3());
            	    							

            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;
            	case 21 :
            	    // InternalN4MFParser.g:1091:3: ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) )
            	    {
            	    // InternalN4MFParser.g:1091:3: ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) )
            	    // InternalN4MFParser.g:1092:4: {...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) )
            	    {
            	    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20)");
            	    }
            	    // InternalN4MFParser.g:1092:113: ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) )
            	    // InternalN4MFParser.g:1093:5: ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) )
            	    {

            	    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20);
            	    				
            	    // InternalN4MFParser.g:1096:8: ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) )
            	    // InternalN4MFParser.g:1096:9: {...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) )
            	    {
            	    if ( !((true)) ) {
            	        throw new FailedPredicateException(input, "ruleProjectDescription", "true");
            	    }
            	    // InternalN4MFParser.g:1096:18: (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) )
            	    // InternalN4MFParser.g:1096:19: otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) )
            	    {
            	    otherlv_87=(Token)match(input,ModuleLoader,FOLLOW_3); 

            	    								newLeafNode(otherlv_87, grammarAccess.getProjectDescriptionAccess().getModuleLoaderKeyword_20_0());
            	    							
            	    otherlv_88=(Token)match(input,Colon,FOLLOW_17); 

            	    								newLeafNode(otherlv_88, grammarAccess.getProjectDescriptionAccess().getColonKeyword_20_1());
            	    							
            	    // InternalN4MFParser.g:1104:8: ( (lv_moduleLoader_89_0= ruleModuleLoader ) )
            	    // InternalN4MFParser.g:1105:9: (lv_moduleLoader_89_0= ruleModuleLoader )
            	    {
            	    // InternalN4MFParser.g:1105:9: (lv_moduleLoader_89_0= ruleModuleLoader )
            	    // InternalN4MFParser.g:1106:10: lv_moduleLoader_89_0= ruleModuleLoader
            	    {

            	    										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getModuleLoaderModuleLoaderEnumRuleCall_20_2_0());
            	    									
            	    pushFollow(FOLLOW_5);
            	    lv_moduleLoader_89_0=ruleModuleLoader();

            	    state._fsp--;


            	    										if (current==null) {
            	    											current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
            	    										}
            	    										set(
            	    											current,
            	    											"moduleLoader",
            	    											lv_moduleLoader_89_0,
            	    											"org.eclipse.n4js.n4mf.N4MF.ModuleLoader");
            	    										afterParserOrEnumRuleCall();
            	    									

            	    }


            	    }


            	    }


            	    }

            	     
            	    					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	    				

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
            } while (true);

            if ( ! getUnorderedGroupHelper().canLeave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup()) ) {
                throw new FailedPredicateException(input, "ruleProjectDescription", "getUnorderedGroupHelper().canLeave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup())");
            }

            }


            }

             
            			  getUnorderedGroupHelper().leave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            			

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProjectDescription"


    // $ANTLR start "entryRuleDeclaredVersion"
    // InternalN4MFParser.g:1140:1: entryRuleDeclaredVersion returns [EObject current=null] : iv_ruleDeclaredVersion= ruleDeclaredVersion EOF ;
    public final EObject entryRuleDeclaredVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDeclaredVersion = null;


        try {
            // InternalN4MFParser.g:1140:56: (iv_ruleDeclaredVersion= ruleDeclaredVersion EOF )
            // InternalN4MFParser.g:1141:2: iv_ruleDeclaredVersion= ruleDeclaredVersion EOF
            {
             newCompositeNode(grammarAccess.getDeclaredVersionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDeclaredVersion=ruleDeclaredVersion();

            state._fsp--;

             current =iv_ruleDeclaredVersion; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDeclaredVersion"


    // $ANTLR start "ruleDeclaredVersion"
    // InternalN4MFParser.g:1147:1: ruleDeclaredVersion returns [EObject current=null] : ( ( (lv_major_0_0= RULE_INT ) ) (otherlv_1= FullStop ( (lv_minor_2_0= RULE_INT ) ) (otherlv_3= FullStop ( (lv_micro_4_0= RULE_INT ) ) )? )? (otherlv_5= HyphenMinus ( (lv_qualifier_6_0= ruleN4mfIdentifier ) ) )? ) ;
    public final EObject ruleDeclaredVersion() throws RecognitionException {
        EObject current = null;

        Token lv_major_0_0=null;
        Token otherlv_1=null;
        Token lv_minor_2_0=null;
        Token otherlv_3=null;
        Token lv_micro_4_0=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_qualifier_6_0 = null;



        	enterRule();

        try {
            // InternalN4MFParser.g:1153:2: ( ( ( (lv_major_0_0= RULE_INT ) ) (otherlv_1= FullStop ( (lv_minor_2_0= RULE_INT ) ) (otherlv_3= FullStop ( (lv_micro_4_0= RULE_INT ) ) )? )? (otherlv_5= HyphenMinus ( (lv_qualifier_6_0= ruleN4mfIdentifier ) ) )? ) )
            // InternalN4MFParser.g:1154:2: ( ( (lv_major_0_0= RULE_INT ) ) (otherlv_1= FullStop ( (lv_minor_2_0= RULE_INT ) ) (otherlv_3= FullStop ( (lv_micro_4_0= RULE_INT ) ) )? )? (otherlv_5= HyphenMinus ( (lv_qualifier_6_0= ruleN4mfIdentifier ) ) )? )
            {
            // InternalN4MFParser.g:1154:2: ( ( (lv_major_0_0= RULE_INT ) ) (otherlv_1= FullStop ( (lv_minor_2_0= RULE_INT ) ) (otherlv_3= FullStop ( (lv_micro_4_0= RULE_INT ) ) )? )? (otherlv_5= HyphenMinus ( (lv_qualifier_6_0= ruleN4mfIdentifier ) ) )? )
            // InternalN4MFParser.g:1155:3: ( (lv_major_0_0= RULE_INT ) ) (otherlv_1= FullStop ( (lv_minor_2_0= RULE_INT ) ) (otherlv_3= FullStop ( (lv_micro_4_0= RULE_INT ) ) )? )? (otherlv_5= HyphenMinus ( (lv_qualifier_6_0= ruleN4mfIdentifier ) ) )?
            {
            // InternalN4MFParser.g:1155:3: ( (lv_major_0_0= RULE_INT ) )
            // InternalN4MFParser.g:1156:4: (lv_major_0_0= RULE_INT )
            {
            // InternalN4MFParser.g:1156:4: (lv_major_0_0= RULE_INT )
            // InternalN4MFParser.g:1157:5: lv_major_0_0= RULE_INT
            {
            lv_major_0_0=(Token)match(input,RULE_INT,FOLLOW_18); 

            					newLeafNode(lv_major_0_0, grammarAccess.getDeclaredVersionAccess().getMajorINTTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDeclaredVersionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"major",
            						lv_major_0_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            // InternalN4MFParser.g:1173:3: (otherlv_1= FullStop ( (lv_minor_2_0= RULE_INT ) ) (otherlv_3= FullStop ( (lv_micro_4_0= RULE_INT ) ) )? )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==FullStop) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalN4MFParser.g:1174:4: otherlv_1= FullStop ( (lv_minor_2_0= RULE_INT ) ) (otherlv_3= FullStop ( (lv_micro_4_0= RULE_INT ) ) )?
                    {
                    otherlv_1=(Token)match(input,FullStop,FOLLOW_7); 

                    				newLeafNode(otherlv_1, grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_0());
                    			
                    // InternalN4MFParser.g:1178:4: ( (lv_minor_2_0= RULE_INT ) )
                    // InternalN4MFParser.g:1179:5: (lv_minor_2_0= RULE_INT )
                    {
                    // InternalN4MFParser.g:1179:5: (lv_minor_2_0= RULE_INT )
                    // InternalN4MFParser.g:1180:6: lv_minor_2_0= RULE_INT
                    {
                    lv_minor_2_0=(Token)match(input,RULE_INT,FOLLOW_18); 

                    						newLeafNode(lv_minor_2_0, grammarAccess.getDeclaredVersionAccess().getMinorINTTerminalRuleCall_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDeclaredVersionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"minor",
                    							lv_minor_2_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }

                    // InternalN4MFParser.g:1196:4: (otherlv_3= FullStop ( (lv_micro_4_0= RULE_INT ) ) )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==FullStop) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalN4MFParser.g:1197:5: otherlv_3= FullStop ( (lv_micro_4_0= RULE_INT ) )
                            {
                            otherlv_3=(Token)match(input,FullStop,FOLLOW_7); 

                            					newLeafNode(otherlv_3, grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_2_0());
                            				
                            // InternalN4MFParser.g:1201:5: ( (lv_micro_4_0= RULE_INT ) )
                            // InternalN4MFParser.g:1202:6: (lv_micro_4_0= RULE_INT )
                            {
                            // InternalN4MFParser.g:1202:6: (lv_micro_4_0= RULE_INT )
                            // InternalN4MFParser.g:1203:7: lv_micro_4_0= RULE_INT
                            {
                            lv_micro_4_0=(Token)match(input,RULE_INT,FOLLOW_19); 

                            							newLeafNode(lv_micro_4_0, grammarAccess.getDeclaredVersionAccess().getMicroINTTerminalRuleCall_1_2_1_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getDeclaredVersionRule());
                            							}
                            							setWithLastConsumed(
                            								current,
                            								"micro",
                            								lv_micro_4_0,
                            								"org.eclipse.xtext.common.Terminals.INT");
                            						

                            }


                            }


                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalN4MFParser.g:1221:3: (otherlv_5= HyphenMinus ( (lv_qualifier_6_0= ruleN4mfIdentifier ) ) )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==HyphenMinus) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalN4MFParser.g:1222:4: otherlv_5= HyphenMinus ( (lv_qualifier_6_0= ruleN4mfIdentifier ) )
                    {
                    otherlv_5=(Token)match(input,HyphenMinus,FOLLOW_4); 

                    				newLeafNode(otherlv_5, grammarAccess.getDeclaredVersionAccess().getHyphenMinusKeyword_2_0());
                    			
                    // InternalN4MFParser.g:1226:4: ( (lv_qualifier_6_0= ruleN4mfIdentifier ) )
                    // InternalN4MFParser.g:1227:5: (lv_qualifier_6_0= ruleN4mfIdentifier )
                    {
                    // InternalN4MFParser.g:1227:5: (lv_qualifier_6_0= ruleN4mfIdentifier )
                    // InternalN4MFParser.g:1228:6: lv_qualifier_6_0= ruleN4mfIdentifier
                    {

                    						newCompositeNode(grammarAccess.getDeclaredVersionAccess().getQualifierN4mfIdentifierParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_qualifier_6_0=ruleN4mfIdentifier();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDeclaredVersionRule());
                    						}
                    						set(
                    							current,
                    							"qualifier",
                    							lv_qualifier_6_0,
                    							"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDeclaredVersion"


    // $ANTLR start "entryRuleSourceFragment"
    // InternalN4MFParser.g:1250:1: entryRuleSourceFragment returns [EObject current=null] : iv_ruleSourceFragment= ruleSourceFragment EOF ;
    public final EObject entryRuleSourceFragment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSourceFragment = null;


        try {
            // InternalN4MFParser.g:1250:55: (iv_ruleSourceFragment= ruleSourceFragment EOF )
            // InternalN4MFParser.g:1251:2: iv_ruleSourceFragment= ruleSourceFragment EOF
            {
             newCompositeNode(grammarAccess.getSourceFragmentRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSourceFragment=ruleSourceFragment();

            state._fsp--;

             current =iv_ruleSourceFragment; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSourceFragment"


    // $ANTLR start "ruleSourceFragment"
    // InternalN4MFParser.g:1257:1: ruleSourceFragment returns [EObject current=null] : ( ( (lv_sourceFragmentType_0_0= ruleSourceFragmentType ) ) otherlv_1= LeftCurlyBracket ( (lv_pathsRaw_2_0= RULE_STRING ) ) (otherlv_3= Comma ( (lv_pathsRaw_4_0= RULE_STRING ) ) )* otherlv_5= RightCurlyBracket ) ;
    public final EObject ruleSourceFragment() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_pathsRaw_2_0=null;
        Token otherlv_3=null;
        Token lv_pathsRaw_4_0=null;
        Token otherlv_5=null;
        Enumerator lv_sourceFragmentType_0_0 = null;



        	enterRule();

        try {
            // InternalN4MFParser.g:1263:2: ( ( ( (lv_sourceFragmentType_0_0= ruleSourceFragmentType ) ) otherlv_1= LeftCurlyBracket ( (lv_pathsRaw_2_0= RULE_STRING ) ) (otherlv_3= Comma ( (lv_pathsRaw_4_0= RULE_STRING ) ) )* otherlv_5= RightCurlyBracket ) )
            // InternalN4MFParser.g:1264:2: ( ( (lv_sourceFragmentType_0_0= ruleSourceFragmentType ) ) otherlv_1= LeftCurlyBracket ( (lv_pathsRaw_2_0= RULE_STRING ) ) (otherlv_3= Comma ( (lv_pathsRaw_4_0= RULE_STRING ) ) )* otherlv_5= RightCurlyBracket )
            {
            // InternalN4MFParser.g:1264:2: ( ( (lv_sourceFragmentType_0_0= ruleSourceFragmentType ) ) otherlv_1= LeftCurlyBracket ( (lv_pathsRaw_2_0= RULE_STRING ) ) (otherlv_3= Comma ( (lv_pathsRaw_4_0= RULE_STRING ) ) )* otherlv_5= RightCurlyBracket )
            // InternalN4MFParser.g:1265:3: ( (lv_sourceFragmentType_0_0= ruleSourceFragmentType ) ) otherlv_1= LeftCurlyBracket ( (lv_pathsRaw_2_0= RULE_STRING ) ) (otherlv_3= Comma ( (lv_pathsRaw_4_0= RULE_STRING ) ) )* otherlv_5= RightCurlyBracket
            {
            // InternalN4MFParser.g:1265:3: ( (lv_sourceFragmentType_0_0= ruleSourceFragmentType ) )
            // InternalN4MFParser.g:1266:4: (lv_sourceFragmentType_0_0= ruleSourceFragmentType )
            {
            // InternalN4MFParser.g:1266:4: (lv_sourceFragmentType_0_0= ruleSourceFragmentType )
            // InternalN4MFParser.g:1267:5: lv_sourceFragmentType_0_0= ruleSourceFragmentType
            {

            					newCompositeNode(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeSourceFragmentTypeEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_9);
            lv_sourceFragmentType_0_0=ruleSourceFragmentType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSourceFragmentRule());
            					}
            					set(
            						current,
            						"sourceFragmentType",
            						lv_sourceFragmentType_0_0,
            						"org.eclipse.n4js.n4mf.N4MF.SourceFragmentType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,LeftCurlyBracket,FOLLOW_8); 

            			newLeafNode(otherlv_1, grammarAccess.getSourceFragmentAccess().getLeftCurlyBracketKeyword_1());
            		
            // InternalN4MFParser.g:1288:3: ( (lv_pathsRaw_2_0= RULE_STRING ) )
            // InternalN4MFParser.g:1289:4: (lv_pathsRaw_2_0= RULE_STRING )
            {
            // InternalN4MFParser.g:1289:4: (lv_pathsRaw_2_0= RULE_STRING )
            // InternalN4MFParser.g:1290:5: lv_pathsRaw_2_0= RULE_STRING
            {
            lv_pathsRaw_2_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

            					newLeafNode(lv_pathsRaw_2_0, grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSourceFragmentRule());
            					}
            					addWithLastConsumed(
            						current,
            						"pathsRaw",
            						lv_pathsRaw_2_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalN4MFParser.g:1306:3: (otherlv_3= Comma ( (lv_pathsRaw_4_0= RULE_STRING ) ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==Comma) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalN4MFParser.g:1307:4: otherlv_3= Comma ( (lv_pathsRaw_4_0= RULE_STRING ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_8); 

            	    				newLeafNode(otherlv_3, grammarAccess.getSourceFragmentAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalN4MFParser.g:1311:4: ( (lv_pathsRaw_4_0= RULE_STRING ) )
            	    // InternalN4MFParser.g:1312:5: (lv_pathsRaw_4_0= RULE_STRING )
            	    {
            	    // InternalN4MFParser.g:1312:5: (lv_pathsRaw_4_0= RULE_STRING )
            	    // InternalN4MFParser.g:1313:6: lv_pathsRaw_4_0= RULE_STRING
            	    {
            	    lv_pathsRaw_4_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

            	    						newLeafNode(lv_pathsRaw_4_0, grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_3_1_0());
            	    					

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getSourceFragmentRule());
            	    						}
            	    						addWithLastConsumed(
            	    							current,
            	    							"pathsRaw",
            	    							lv_pathsRaw_4_0,
            	    							"org.eclipse.xtext.common.Terminals.STRING");
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            otherlv_5=(Token)match(input,RightCurlyBracket,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getSourceFragmentAccess().getRightCurlyBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSourceFragment"


    // $ANTLR start "entryRuleModuleFilter"
    // InternalN4MFParser.g:1338:1: entryRuleModuleFilter returns [EObject current=null] : iv_ruleModuleFilter= ruleModuleFilter EOF ;
    public final EObject entryRuleModuleFilter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModuleFilter = null;


        try {
            // InternalN4MFParser.g:1338:53: (iv_ruleModuleFilter= ruleModuleFilter EOF )
            // InternalN4MFParser.g:1339:2: iv_ruleModuleFilter= ruleModuleFilter EOF
            {
             newCompositeNode(grammarAccess.getModuleFilterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModuleFilter=ruleModuleFilter();

            state._fsp--;

             current =iv_ruleModuleFilter; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModuleFilter"


    // $ANTLR start "ruleModuleFilter"
    // InternalN4MFParser.g:1345:1: ruleModuleFilter returns [EObject current=null] : ( ( (lv_moduleFilterType_0_0= ruleModuleFilterType ) ) otherlv_1= LeftCurlyBracket ( (lv_moduleSpecifiers_2_0= ruleModuleFilterSpecifier ) ) (otherlv_3= Comma ( (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier ) ) )* otherlv_5= RightCurlyBracket ) ;
    public final EObject ruleModuleFilter() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_moduleFilterType_0_0 = null;

        EObject lv_moduleSpecifiers_2_0 = null;

        EObject lv_moduleSpecifiers_4_0 = null;



        	enterRule();

        try {
            // InternalN4MFParser.g:1351:2: ( ( ( (lv_moduleFilterType_0_0= ruleModuleFilterType ) ) otherlv_1= LeftCurlyBracket ( (lv_moduleSpecifiers_2_0= ruleModuleFilterSpecifier ) ) (otherlv_3= Comma ( (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier ) ) )* otherlv_5= RightCurlyBracket ) )
            // InternalN4MFParser.g:1352:2: ( ( (lv_moduleFilterType_0_0= ruleModuleFilterType ) ) otherlv_1= LeftCurlyBracket ( (lv_moduleSpecifiers_2_0= ruleModuleFilterSpecifier ) ) (otherlv_3= Comma ( (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier ) ) )* otherlv_5= RightCurlyBracket )
            {
            // InternalN4MFParser.g:1352:2: ( ( (lv_moduleFilterType_0_0= ruleModuleFilterType ) ) otherlv_1= LeftCurlyBracket ( (lv_moduleSpecifiers_2_0= ruleModuleFilterSpecifier ) ) (otherlv_3= Comma ( (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier ) ) )* otherlv_5= RightCurlyBracket )
            // InternalN4MFParser.g:1353:3: ( (lv_moduleFilterType_0_0= ruleModuleFilterType ) ) otherlv_1= LeftCurlyBracket ( (lv_moduleSpecifiers_2_0= ruleModuleFilterSpecifier ) ) (otherlv_3= Comma ( (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier ) ) )* otherlv_5= RightCurlyBracket
            {
            // InternalN4MFParser.g:1353:3: ( (lv_moduleFilterType_0_0= ruleModuleFilterType ) )
            // InternalN4MFParser.g:1354:4: (lv_moduleFilterType_0_0= ruleModuleFilterType )
            {
            // InternalN4MFParser.g:1354:4: (lv_moduleFilterType_0_0= ruleModuleFilterType )
            // InternalN4MFParser.g:1355:5: lv_moduleFilterType_0_0= ruleModuleFilterType
            {

            					newCompositeNode(grammarAccess.getModuleFilterAccess().getModuleFilterTypeModuleFilterTypeEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_9);
            lv_moduleFilterType_0_0=ruleModuleFilterType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getModuleFilterRule());
            					}
            					set(
            						current,
            						"moduleFilterType",
            						lv_moduleFilterType_0_0,
            						"org.eclipse.n4js.n4mf.N4MF.ModuleFilterType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,LeftCurlyBracket,FOLLOW_8); 

            			newLeafNode(otherlv_1, grammarAccess.getModuleFilterAccess().getLeftCurlyBracketKeyword_1());
            		
            // InternalN4MFParser.g:1376:3: ( (lv_moduleSpecifiers_2_0= ruleModuleFilterSpecifier ) )
            // InternalN4MFParser.g:1377:4: (lv_moduleSpecifiers_2_0= ruleModuleFilterSpecifier )
            {
            // InternalN4MFParser.g:1377:4: (lv_moduleSpecifiers_2_0= ruleModuleFilterSpecifier )
            // InternalN4MFParser.g:1378:5: lv_moduleSpecifiers_2_0= ruleModuleFilterSpecifier
            {

            					newCompositeNode(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_11);
            lv_moduleSpecifiers_2_0=ruleModuleFilterSpecifier();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getModuleFilterRule());
            					}
            					add(
            						current,
            						"moduleSpecifiers",
            						lv_moduleSpecifiers_2_0,
            						"org.eclipse.n4js.n4mf.N4MF.ModuleFilterSpecifier");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalN4MFParser.g:1395:3: (otherlv_3= Comma ( (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==Comma) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalN4MFParser.g:1396:4: otherlv_3= Comma ( (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_8); 

            	    				newLeafNode(otherlv_3, grammarAccess.getModuleFilterAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalN4MFParser.g:1400:4: ( (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier ) )
            	    // InternalN4MFParser.g:1401:5: (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier )
            	    {
            	    // InternalN4MFParser.g:1401:5: (lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier )
            	    // InternalN4MFParser.g:1402:6: lv_moduleSpecifiers_4_0= ruleModuleFilterSpecifier
            	    {

            	    						newCompositeNode(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_11);
            	    lv_moduleSpecifiers_4_0=ruleModuleFilterSpecifier();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getModuleFilterRule());
            	    						}
            	    						add(
            	    							current,
            	    							"moduleSpecifiers",
            	    							lv_moduleSpecifiers_4_0,
            	    							"org.eclipse.n4js.n4mf.N4MF.ModuleFilterSpecifier");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

            otherlv_5=(Token)match(input,RightCurlyBracket,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getModuleFilterAccess().getRightCurlyBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModuleFilter"


    // $ANTLR start "entryRuleBootstrapModule"
    // InternalN4MFParser.g:1428:1: entryRuleBootstrapModule returns [EObject current=null] : iv_ruleBootstrapModule= ruleBootstrapModule EOF ;
    public final EObject entryRuleBootstrapModule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBootstrapModule = null;


        try {
            // InternalN4MFParser.g:1428:56: (iv_ruleBootstrapModule= ruleBootstrapModule EOF )
            // InternalN4MFParser.g:1429:2: iv_ruleBootstrapModule= ruleBootstrapModule EOF
            {
             newCompositeNode(grammarAccess.getBootstrapModuleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBootstrapModule=ruleBootstrapModule();

            state._fsp--;

             current =iv_ruleBootstrapModule; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBootstrapModule"


    // $ANTLR start "ruleBootstrapModule"
    // InternalN4MFParser.g:1435:1: ruleBootstrapModule returns [EObject current=null] : ( ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )? ) ;
    public final EObject ruleBootstrapModule() throws RecognitionException {
        EObject current = null;

        Token lv_moduleSpecifierWithWildcard_0_0=null;
        Token otherlv_1=null;
        Token lv_sourcePath_2_0=null;


        	enterRule();

        try {
            // InternalN4MFParser.g:1441:2: ( ( ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )? ) )
            // InternalN4MFParser.g:1442:2: ( ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )? )
            {
            // InternalN4MFParser.g:1442:2: ( ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )? )
            // InternalN4MFParser.g:1443:3: ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )?
            {
            // InternalN4MFParser.g:1443:3: ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) )
            // InternalN4MFParser.g:1444:4: (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING )
            {
            // InternalN4MFParser.g:1444:4: (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING )
            // InternalN4MFParser.g:1445:5: lv_moduleSpecifierWithWildcard_0_0= RULE_STRING
            {
            lv_moduleSpecifierWithWildcard_0_0=(Token)match(input,RULE_STRING,FOLLOW_20); 

            					newLeafNode(lv_moduleSpecifierWithWildcard_0_0, grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBootstrapModuleRule());
            					}
            					setWithLastConsumed(
            						current,
            						"moduleSpecifierWithWildcard",
            						lv_moduleSpecifierWithWildcard_0_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalN4MFParser.g:1461:3: (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==In) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalN4MFParser.g:1462:4: otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) )
                    {
                    otherlv_1=(Token)match(input,In,FOLLOW_8); 

                    				newLeafNode(otherlv_1, grammarAccess.getBootstrapModuleAccess().getInKeyword_1_0());
                    			
                    // InternalN4MFParser.g:1466:4: ( (lv_sourcePath_2_0= RULE_STRING ) )
                    // InternalN4MFParser.g:1467:5: (lv_sourcePath_2_0= RULE_STRING )
                    {
                    // InternalN4MFParser.g:1467:5: (lv_sourcePath_2_0= RULE_STRING )
                    // InternalN4MFParser.g:1468:6: lv_sourcePath_2_0= RULE_STRING
                    {
                    lv_sourcePath_2_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    						newLeafNode(lv_sourcePath_2_0, grammarAccess.getBootstrapModuleAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getBootstrapModuleRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"sourcePath",
                    							lv_sourcePath_2_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBootstrapModule"


    // $ANTLR start "entryRuleModuleFilterSpecifier"
    // InternalN4MFParser.g:1489:1: entryRuleModuleFilterSpecifier returns [EObject current=null] : iv_ruleModuleFilterSpecifier= ruleModuleFilterSpecifier EOF ;
    public final EObject entryRuleModuleFilterSpecifier() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModuleFilterSpecifier = null;


        try {
            // InternalN4MFParser.g:1489:62: (iv_ruleModuleFilterSpecifier= ruleModuleFilterSpecifier EOF )
            // InternalN4MFParser.g:1490:2: iv_ruleModuleFilterSpecifier= ruleModuleFilterSpecifier EOF
            {
             newCompositeNode(grammarAccess.getModuleFilterSpecifierRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModuleFilterSpecifier=ruleModuleFilterSpecifier();

            state._fsp--;

             current =iv_ruleModuleFilterSpecifier; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModuleFilterSpecifier"


    // $ANTLR start "ruleModuleFilterSpecifier"
    // InternalN4MFParser.g:1496:1: ruleModuleFilterSpecifier returns [EObject current=null] : ( ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )? ) ;
    public final EObject ruleModuleFilterSpecifier() throws RecognitionException {
        EObject current = null;

        Token lv_moduleSpecifierWithWildcard_0_0=null;
        Token otherlv_1=null;
        Token lv_sourcePath_2_0=null;


        	enterRule();

        try {
            // InternalN4MFParser.g:1502:2: ( ( ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )? ) )
            // InternalN4MFParser.g:1503:2: ( ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )? )
            {
            // InternalN4MFParser.g:1503:2: ( ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )? )
            // InternalN4MFParser.g:1504:3: ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) ) (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )?
            {
            // InternalN4MFParser.g:1504:3: ( (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING ) )
            // InternalN4MFParser.g:1505:4: (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING )
            {
            // InternalN4MFParser.g:1505:4: (lv_moduleSpecifierWithWildcard_0_0= RULE_STRING )
            // InternalN4MFParser.g:1506:5: lv_moduleSpecifierWithWildcard_0_0= RULE_STRING
            {
            lv_moduleSpecifierWithWildcard_0_0=(Token)match(input,RULE_STRING,FOLLOW_20); 

            					newLeafNode(lv_moduleSpecifierWithWildcard_0_0, grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getModuleFilterSpecifierRule());
            					}
            					setWithLastConsumed(
            						current,
            						"moduleSpecifierWithWildcard",
            						lv_moduleSpecifierWithWildcard_0_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalN4MFParser.g:1522:3: (otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==In) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalN4MFParser.g:1523:4: otherlv_1= In ( (lv_sourcePath_2_0= RULE_STRING ) )
                    {
                    otherlv_1=(Token)match(input,In,FOLLOW_8); 

                    				newLeafNode(otherlv_1, grammarAccess.getModuleFilterSpecifierAccess().getInKeyword_1_0());
                    			
                    // InternalN4MFParser.g:1527:4: ( (lv_sourcePath_2_0= RULE_STRING ) )
                    // InternalN4MFParser.g:1528:5: (lv_sourcePath_2_0= RULE_STRING )
                    {
                    // InternalN4MFParser.g:1528:5: (lv_sourcePath_2_0= RULE_STRING )
                    // InternalN4MFParser.g:1529:6: lv_sourcePath_2_0= RULE_STRING
                    {
                    lv_sourcePath_2_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    						newLeafNode(lv_sourcePath_2_0, grammarAccess.getModuleFilterSpecifierAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getModuleFilterSpecifierRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"sourcePath",
                    							lv_sourcePath_2_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModuleFilterSpecifier"


    // $ANTLR start "entryRuleProjectReference"
    // InternalN4MFParser.g:1550:1: entryRuleProjectReference returns [EObject current=null] : iv_ruleProjectReference= ruleProjectReference EOF ;
    public final EObject entryRuleProjectReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProjectReference = null;


        try {
            // InternalN4MFParser.g:1550:57: (iv_ruleProjectReference= ruleProjectReference EOF )
            // InternalN4MFParser.g:1551:2: iv_ruleProjectReference= ruleProjectReference EOF
            {
             newCompositeNode(grammarAccess.getProjectReferenceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleProjectReference=ruleProjectReference();

            state._fsp--;

             current =iv_ruleProjectReference; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProjectReference"


    // $ANTLR start "ruleProjectReference"
    // InternalN4MFParser.g:1557:1: ruleProjectReference returns [EObject current=null] : this_ProjectIdWithOptionalVendor_0= ruleProjectIdWithOptionalVendor[$current] ;
    public final EObject ruleProjectReference() throws RecognitionException {
        EObject current = null;

        EObject this_ProjectIdWithOptionalVendor_0 = null;



        	enterRule();

        try {
            // InternalN4MFParser.g:1563:2: (this_ProjectIdWithOptionalVendor_0= ruleProjectIdWithOptionalVendor[$current] )
            // InternalN4MFParser.g:1564:2: this_ProjectIdWithOptionalVendor_0= ruleProjectIdWithOptionalVendor[$current]
            {

            		if (current==null) {
            			current = createModelElement(grammarAccess.getProjectReferenceRule());
            		}
            		newCompositeNode(grammarAccess.getProjectReferenceAccess().getProjectIdWithOptionalVendorParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_ProjectIdWithOptionalVendor_0=ruleProjectIdWithOptionalVendor(current);

            state._fsp--;


            		current = this_ProjectIdWithOptionalVendor_0;
            		afterParserOrEnumRuleCall();
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProjectReference"


    // $ANTLR start "entryRuleProjectDependency"
    // InternalN4MFParser.g:1578:1: entryRuleProjectDependency returns [EObject current=null] : iv_ruleProjectDependency= ruleProjectDependency EOF ;
    public final EObject entryRuleProjectDependency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProjectDependency = null;


        try {
            // InternalN4MFParser.g:1578:58: (iv_ruleProjectDependency= ruleProjectDependency EOF )
            // InternalN4MFParser.g:1579:2: iv_ruleProjectDependency= ruleProjectDependency EOF
            {
             newCompositeNode(grammarAccess.getProjectDependencyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleProjectDependency=ruleProjectDependency();

            state._fsp--;

             current =iv_ruleProjectDependency; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProjectDependency"


    // $ANTLR start "ruleProjectDependency"
    // InternalN4MFParser.g:1585:1: ruleProjectDependency returns [EObject current=null] : (this_ProjectIdWithOptionalVendor_0= ruleProjectIdWithOptionalVendor[$current] ( (lv_versionConstraint_1_0= ruleVersionConstraint ) )? ( (lv_declaredScope_2_0= ruleProjectDependencyScope ) )? ) ;
    public final EObject ruleProjectDependency() throws RecognitionException {
        EObject current = null;

        EObject this_ProjectIdWithOptionalVendor_0 = null;

        EObject lv_versionConstraint_1_0 = null;

        Enumerator lv_declaredScope_2_0 = null;



        	enterRule();

        try {
            // InternalN4MFParser.g:1591:2: ( (this_ProjectIdWithOptionalVendor_0= ruleProjectIdWithOptionalVendor[$current] ( (lv_versionConstraint_1_0= ruleVersionConstraint ) )? ( (lv_declaredScope_2_0= ruleProjectDependencyScope ) )? ) )
            // InternalN4MFParser.g:1592:2: (this_ProjectIdWithOptionalVendor_0= ruleProjectIdWithOptionalVendor[$current] ( (lv_versionConstraint_1_0= ruleVersionConstraint ) )? ( (lv_declaredScope_2_0= ruleProjectDependencyScope ) )? )
            {
            // InternalN4MFParser.g:1592:2: (this_ProjectIdWithOptionalVendor_0= ruleProjectIdWithOptionalVendor[$current] ( (lv_versionConstraint_1_0= ruleVersionConstraint ) )? ( (lv_declaredScope_2_0= ruleProjectDependencyScope ) )? )
            // InternalN4MFParser.g:1593:3: this_ProjectIdWithOptionalVendor_0= ruleProjectIdWithOptionalVendor[$current] ( (lv_versionConstraint_1_0= ruleVersionConstraint ) )? ( (lv_declaredScope_2_0= ruleProjectDependencyScope ) )?
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getProjectDependencyRule());
            			}
            			newCompositeNode(grammarAccess.getProjectDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall_0());
            		
            pushFollow(FOLLOW_21);
            this_ProjectIdWithOptionalVendor_0=ruleProjectIdWithOptionalVendor(current);

            state._fsp--;


            			current = this_ProjectIdWithOptionalVendor_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalN4MFParser.g:1604:3: ( (lv_versionConstraint_1_0= ruleVersionConstraint ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==LeftParenthesis||LA25_0==LeftSquareBracket||LA25_0==RULE_INT) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalN4MFParser.g:1605:4: (lv_versionConstraint_1_0= ruleVersionConstraint )
                    {
                    // InternalN4MFParser.g:1605:4: (lv_versionConstraint_1_0= ruleVersionConstraint )
                    // InternalN4MFParser.g:1606:5: lv_versionConstraint_1_0= ruleVersionConstraint
                    {

                    					newCompositeNode(grammarAccess.getProjectDependencyAccess().getVersionConstraintVersionConstraintParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_22);
                    lv_versionConstraint_1_0=ruleVersionConstraint();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getProjectDependencyRule());
                    					}
                    					set(
                    						current,
                    						"versionConstraint",
                    						lv_versionConstraint_1_0,
                    						"org.eclipse.n4js.n4mf.N4MF.VersionConstraint");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalN4MFParser.g:1623:3: ( (lv_declaredScope_2_0= ruleProjectDependencyScope ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==Compile||LA26_0==Test) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalN4MFParser.g:1624:4: (lv_declaredScope_2_0= ruleProjectDependencyScope )
                    {
                    // InternalN4MFParser.g:1624:4: (lv_declaredScope_2_0= ruleProjectDependencyScope )
                    // InternalN4MFParser.g:1625:5: lv_declaredScope_2_0= ruleProjectDependencyScope
                    {

                    					newCompositeNode(grammarAccess.getProjectDependencyAccess().getDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_declaredScope_2_0=ruleProjectDependencyScope();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getProjectDependencyRule());
                    					}
                    					set(
                    						current,
                    						"declaredScope",
                    						lv_declaredScope_2_0,
                    						"org.eclipse.n4js.n4mf.N4MF.ProjectDependencyScope");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProjectDependency"


    // $ANTLR start "ruleProjectIdWithOptionalVendor"
    // InternalN4MFParser.g:1647:1: ruleProjectIdWithOptionalVendor[EObject in_current] returns [EObject current=in_current] : ( ( ( (lv_declaredVendorId_0_0= ruleN4mfIdentifier ) ) otherlv_1= Colon )? ( (lv_projectId_2_0= ruleN4mfIdentifier ) ) ) ;
    public final EObject ruleProjectIdWithOptionalVendor(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_declaredVendorId_0_0 = null;

        AntlrDatatypeRuleToken lv_projectId_2_0 = null;



        	enterRule();

        try {
            // InternalN4MFParser.g:1653:2: ( ( ( ( (lv_declaredVendorId_0_0= ruleN4mfIdentifier ) ) otherlv_1= Colon )? ( (lv_projectId_2_0= ruleN4mfIdentifier ) ) ) )
            // InternalN4MFParser.g:1654:2: ( ( ( (lv_declaredVendorId_0_0= ruleN4mfIdentifier ) ) otherlv_1= Colon )? ( (lv_projectId_2_0= ruleN4mfIdentifier ) ) )
            {
            // InternalN4MFParser.g:1654:2: ( ( ( (lv_declaredVendorId_0_0= ruleN4mfIdentifier ) ) otherlv_1= Colon )? ( (lv_projectId_2_0= ruleN4mfIdentifier ) ) )
            // InternalN4MFParser.g:1655:3: ( ( (lv_declaredVendorId_0_0= ruleN4mfIdentifier ) ) otherlv_1= Colon )? ( (lv_projectId_2_0= ruleN4mfIdentifier ) )
            {
            // InternalN4MFParser.g:1655:3: ( ( (lv_declaredVendorId_0_0= ruleN4mfIdentifier ) ) otherlv_1= Colon )?
            int alt27=2;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // InternalN4MFParser.g:1656:4: ( (lv_declaredVendorId_0_0= ruleN4mfIdentifier ) ) otherlv_1= Colon
                    {
                    // InternalN4MFParser.g:1656:4: ( (lv_declaredVendorId_0_0= ruleN4mfIdentifier ) )
                    // InternalN4MFParser.g:1657:5: (lv_declaredVendorId_0_0= ruleN4mfIdentifier )
                    {
                    // InternalN4MFParser.g:1657:5: (lv_declaredVendorId_0_0= ruleN4mfIdentifier )
                    // InternalN4MFParser.g:1658:6: lv_declaredVendorId_0_0= ruleN4mfIdentifier
                    {

                    						newCompositeNode(grammarAccess.getProjectIdWithOptionalVendorAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_3);
                    lv_declaredVendorId_0_0=ruleN4mfIdentifier();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getProjectIdWithOptionalVendorRule());
                    						}
                    						set(
                    							current,
                    							"declaredVendorId",
                    							lv_declaredVendorId_0_0,
                    							"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,Colon,FOLLOW_4); 

                    				newLeafNode(otherlv_1, grammarAccess.getProjectIdWithOptionalVendorAccess().getColonKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalN4MFParser.g:1680:3: ( (lv_projectId_2_0= ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:1681:4: (lv_projectId_2_0= ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:1681:4: (lv_projectId_2_0= ruleN4mfIdentifier )
            // InternalN4MFParser.g:1682:5: lv_projectId_2_0= ruleN4mfIdentifier
            {

            					newCompositeNode(grammarAccess.getProjectIdWithOptionalVendorAccess().getProjectIdN4mfIdentifierParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_projectId_2_0=ruleN4mfIdentifier();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getProjectIdWithOptionalVendorRule());
            					}
            					set(
            						current,
            						"projectId",
            						lv_projectId_2_0,
            						"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProjectIdWithOptionalVendor"


    // $ANTLR start "entryRuleVersionConstraint"
    // InternalN4MFParser.g:1703:1: entryRuleVersionConstraint returns [EObject current=null] : iv_ruleVersionConstraint= ruleVersionConstraint EOF ;
    public final EObject entryRuleVersionConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionConstraint = null;


        try {
            // InternalN4MFParser.g:1703:58: (iv_ruleVersionConstraint= ruleVersionConstraint EOF )
            // InternalN4MFParser.g:1704:2: iv_ruleVersionConstraint= ruleVersionConstraint EOF
            {
             newCompositeNode(grammarAccess.getVersionConstraintRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVersionConstraint=ruleVersionConstraint();

            state._fsp--;

             current =iv_ruleVersionConstraint; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersionConstraint"


    // $ANTLR start "ruleVersionConstraint"
    // InternalN4MFParser.g:1710:1: ruleVersionConstraint returns [EObject current=null] : ( ( ( ( (lv_exclLowerBound_0_0= LeftParenthesis ) ) | otherlv_1= LeftSquareBracket ) ( (lv_lowerVersion_2_0= ruleDeclaredVersion ) ) ( (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )? | otherlv_7= RightParenthesis ) ) | ( (lv_lowerVersion_8_0= ruleDeclaredVersion ) ) ) ;
    public final EObject ruleVersionConstraint() throws RecognitionException {
        EObject current = null;

        Token lv_exclLowerBound_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token lv_exclUpperBound_5_0=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        EObject lv_lowerVersion_2_0 = null;

        EObject lv_upperVersion_4_0 = null;

        EObject lv_lowerVersion_8_0 = null;



        	enterRule();

        try {
            // InternalN4MFParser.g:1716:2: ( ( ( ( ( (lv_exclLowerBound_0_0= LeftParenthesis ) ) | otherlv_1= LeftSquareBracket ) ( (lv_lowerVersion_2_0= ruleDeclaredVersion ) ) ( (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )? | otherlv_7= RightParenthesis ) ) | ( (lv_lowerVersion_8_0= ruleDeclaredVersion ) ) ) )
            // InternalN4MFParser.g:1717:2: ( ( ( ( (lv_exclLowerBound_0_0= LeftParenthesis ) ) | otherlv_1= LeftSquareBracket ) ( (lv_lowerVersion_2_0= ruleDeclaredVersion ) ) ( (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )? | otherlv_7= RightParenthesis ) ) | ( (lv_lowerVersion_8_0= ruleDeclaredVersion ) ) )
            {
            // InternalN4MFParser.g:1717:2: ( ( ( ( (lv_exclLowerBound_0_0= LeftParenthesis ) ) | otherlv_1= LeftSquareBracket ) ( (lv_lowerVersion_2_0= ruleDeclaredVersion ) ) ( (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )? | otherlv_7= RightParenthesis ) ) | ( (lv_lowerVersion_8_0= ruleDeclaredVersion ) ) )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==LeftParenthesis||LA32_0==LeftSquareBracket) ) {
                alt32=1;
            }
            else if ( (LA32_0==RULE_INT) ) {
                alt32=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // InternalN4MFParser.g:1718:3: ( ( ( (lv_exclLowerBound_0_0= LeftParenthesis ) ) | otherlv_1= LeftSquareBracket ) ( (lv_lowerVersion_2_0= ruleDeclaredVersion ) ) ( (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )? | otherlv_7= RightParenthesis ) )
                    {
                    // InternalN4MFParser.g:1718:3: ( ( ( (lv_exclLowerBound_0_0= LeftParenthesis ) ) | otherlv_1= LeftSquareBracket ) ( (lv_lowerVersion_2_0= ruleDeclaredVersion ) ) ( (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )? | otherlv_7= RightParenthesis ) )
                    // InternalN4MFParser.g:1719:4: ( ( (lv_exclLowerBound_0_0= LeftParenthesis ) ) | otherlv_1= LeftSquareBracket ) ( (lv_lowerVersion_2_0= ruleDeclaredVersion ) ) ( (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )? | otherlv_7= RightParenthesis )
                    {
                    // InternalN4MFParser.g:1719:4: ( ( (lv_exclLowerBound_0_0= LeftParenthesis ) ) | otherlv_1= LeftSquareBracket )
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==LeftParenthesis) ) {
                        alt28=1;
                    }
                    else if ( (LA28_0==LeftSquareBracket) ) {
                        alt28=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 0, input);

                        throw nvae;
                    }
                    switch (alt28) {
                        case 1 :
                            // InternalN4MFParser.g:1720:5: ( (lv_exclLowerBound_0_0= LeftParenthesis ) )
                            {
                            // InternalN4MFParser.g:1720:5: ( (lv_exclLowerBound_0_0= LeftParenthesis ) )
                            // InternalN4MFParser.g:1721:6: (lv_exclLowerBound_0_0= LeftParenthesis )
                            {
                            // InternalN4MFParser.g:1721:6: (lv_exclLowerBound_0_0= LeftParenthesis )
                            // InternalN4MFParser.g:1722:7: lv_exclLowerBound_0_0= LeftParenthesis
                            {
                            lv_exclLowerBound_0_0=(Token)match(input,LeftParenthesis,FOLLOW_7); 

                            							newLeafNode(lv_exclLowerBound_0_0, grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getVersionConstraintRule());
                            							}
                            							setWithLastConsumed(current, "exclLowerBound", true, "(");
                            						

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalN4MFParser.g:1735:5: otherlv_1= LeftSquareBracket
                            {
                            otherlv_1=(Token)match(input,LeftSquareBracket,FOLLOW_7); 

                            					newLeafNode(otherlv_1, grammarAccess.getVersionConstraintAccess().getLeftSquareBracketKeyword_0_0_1());
                            				

                            }
                            break;

                    }

                    // InternalN4MFParser.g:1740:4: ( (lv_lowerVersion_2_0= ruleDeclaredVersion ) )
                    // InternalN4MFParser.g:1741:5: (lv_lowerVersion_2_0= ruleDeclaredVersion )
                    {
                    // InternalN4MFParser.g:1741:5: (lv_lowerVersion_2_0= ruleDeclaredVersion )
                    // InternalN4MFParser.g:1742:6: lv_lowerVersion_2_0= ruleDeclaredVersion
                    {

                    						newCompositeNode(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_23);
                    lv_lowerVersion_2_0=ruleDeclaredVersion();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVersionConstraintRule());
                    						}
                    						set(
                    							current,
                    							"lowerVersion",
                    							lv_lowerVersion_2_0,
                    							"org.eclipse.n4js.n4mf.N4MF.DeclaredVersion");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalN4MFParser.g:1759:4: ( (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )? | otherlv_7= RightParenthesis )
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==EOF||LA31_0==Compile||LA31_0==Test||LA31_0==Comma||LA31_0==RightCurlyBracket) ) {
                        alt31=1;
                    }
                    else if ( (LA31_0==RightParenthesis) ) {
                        alt31=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 0, input);

                        throw nvae;
                    }
                    switch (alt31) {
                        case 1 :
                            // InternalN4MFParser.g:1760:5: (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )?
                            {
                            // InternalN4MFParser.g:1760:5: (otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket ) )?
                            int alt30=2;
                            int LA30_0 = input.LA(1);

                            if ( (LA30_0==Comma) ) {
                                int LA30_1 = input.LA(2);

                                if ( (LA30_1==RULE_INT) ) {
                                    alt30=1;
                                }
                            }
                            switch (alt30) {
                                case 1 :
                                    // InternalN4MFParser.g:1761:6: otherlv_3= Comma ( (lv_upperVersion_4_0= ruleDeclaredVersion ) ) ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket )
                                    {
                                    otherlv_3=(Token)match(input,Comma,FOLLOW_7); 

                                    						newLeafNode(otherlv_3, grammarAccess.getVersionConstraintAccess().getCommaKeyword_0_2_0_0());
                                    					
                                    // InternalN4MFParser.g:1765:6: ( (lv_upperVersion_4_0= ruleDeclaredVersion ) )
                                    // InternalN4MFParser.g:1766:7: (lv_upperVersion_4_0= ruleDeclaredVersion )
                                    {
                                    // InternalN4MFParser.g:1766:7: (lv_upperVersion_4_0= ruleDeclaredVersion )
                                    // InternalN4MFParser.g:1767:8: lv_upperVersion_4_0= ruleDeclaredVersion
                                    {

                                    								newCompositeNode(grammarAccess.getVersionConstraintAccess().getUpperVersionDeclaredVersionParserRuleCall_0_2_0_1_0());
                                    							
                                    pushFollow(FOLLOW_24);
                                    lv_upperVersion_4_0=ruleDeclaredVersion();

                                    state._fsp--;


                                    								if (current==null) {
                                    									current = createModelElementForParent(grammarAccess.getVersionConstraintRule());
                                    								}
                                    								set(
                                    									current,
                                    									"upperVersion",
                                    									lv_upperVersion_4_0,
                                    									"org.eclipse.n4js.n4mf.N4MF.DeclaredVersion");
                                    								afterParserOrEnumRuleCall();
                                    							

                                    }


                                    }

                                    // InternalN4MFParser.g:1784:6: ( ( (lv_exclUpperBound_5_0= RightParenthesis ) ) | otherlv_6= RightSquareBracket )
                                    int alt29=2;
                                    int LA29_0 = input.LA(1);

                                    if ( (LA29_0==RightParenthesis) ) {
                                        alt29=1;
                                    }
                                    else if ( (LA29_0==RightSquareBracket) ) {
                                        alt29=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 29, 0, input);

                                        throw nvae;
                                    }
                                    switch (alt29) {
                                        case 1 :
                                            // InternalN4MFParser.g:1785:7: ( (lv_exclUpperBound_5_0= RightParenthesis ) )
                                            {
                                            // InternalN4MFParser.g:1785:7: ( (lv_exclUpperBound_5_0= RightParenthesis ) )
                                            // InternalN4MFParser.g:1786:8: (lv_exclUpperBound_5_0= RightParenthesis )
                                            {
                                            // InternalN4MFParser.g:1786:8: (lv_exclUpperBound_5_0= RightParenthesis )
                                            // InternalN4MFParser.g:1787:9: lv_exclUpperBound_5_0= RightParenthesis
                                            {
                                            lv_exclUpperBound_5_0=(Token)match(input,RightParenthesis,FOLLOW_2); 

                                            									newLeafNode(lv_exclUpperBound_5_0, grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0());
                                            								

                                            									if (current==null) {
                                            										current = createModelElement(grammarAccess.getVersionConstraintRule());
                                            									}
                                            									setWithLastConsumed(current, "exclUpperBound", true, ")");
                                            								

                                            }


                                            }


                                            }
                                            break;
                                        case 2 :
                                            // InternalN4MFParser.g:1800:7: otherlv_6= RightSquareBracket
                                            {
                                            otherlv_6=(Token)match(input,RightSquareBracket,FOLLOW_2); 

                                            							newLeafNode(otherlv_6, grammarAccess.getVersionConstraintAccess().getRightSquareBracketKeyword_0_2_0_2_1());
                                            						

                                            }
                                            break;

                                    }


                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // InternalN4MFParser.g:1807:5: otherlv_7= RightParenthesis
                            {
                            otherlv_7=(Token)match(input,RightParenthesis,FOLLOW_2); 

                            					newLeafNode(otherlv_7, grammarAccess.getVersionConstraintAccess().getRightParenthesisKeyword_0_2_1());
                            				

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:1814:3: ( (lv_lowerVersion_8_0= ruleDeclaredVersion ) )
                    {
                    // InternalN4MFParser.g:1814:3: ( (lv_lowerVersion_8_0= ruleDeclaredVersion ) )
                    // InternalN4MFParser.g:1815:4: (lv_lowerVersion_8_0= ruleDeclaredVersion )
                    {
                    // InternalN4MFParser.g:1815:4: (lv_lowerVersion_8_0= ruleDeclaredVersion )
                    // InternalN4MFParser.g:1816:5: lv_lowerVersion_8_0= ruleDeclaredVersion
                    {

                    					newCompositeNode(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_lowerVersion_8_0=ruleDeclaredVersion();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getVersionConstraintRule());
                    					}
                    					set(
                    						current,
                    						"lowerVersion",
                    						lv_lowerVersion_8_0,
                    						"org.eclipse.n4js.n4mf.N4MF.DeclaredVersion");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionConstraint"


    // $ANTLR start "entryRuleN4mfIdentifier"
    // InternalN4MFParser.g:1837:1: entryRuleN4mfIdentifier returns [String current=null] : iv_ruleN4mfIdentifier= ruleN4mfIdentifier EOF ;
    public final String entryRuleN4mfIdentifier() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleN4mfIdentifier = null;


        try {
            // InternalN4MFParser.g:1837:54: (iv_ruleN4mfIdentifier= ruleN4mfIdentifier EOF )
            // InternalN4MFParser.g:1838:2: iv_ruleN4mfIdentifier= ruleN4mfIdentifier EOF
            {
             newCompositeNode(grammarAccess.getN4mfIdentifierRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleN4mfIdentifier=ruleN4mfIdentifier();

            state._fsp--;

             current =iv_ruleN4mfIdentifier.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleN4mfIdentifier"


    // $ANTLR start "ruleN4mfIdentifier"
    // InternalN4MFParser.g:1844:1: ruleN4mfIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= ProjectId | kw= ProjectType | kw= ProjectVersion | kw= VendorId | kw= VendorName | kw= Output | kw= Libraries | kw= Resources | kw= Sources | kw= ModuleFilters | (kw= ProjectDependencies kw= KW_System ) | kw= API | kw= User | kw= Application | (kw= Processor kw= Source ) | kw= Content | kw= Test ) ;
    public final AntlrDatatypeRuleToken ruleN4mfIdentifier() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalN4MFParser.g:1850:2: ( (this_ID_0= RULE_ID | kw= ProjectId | kw= ProjectType | kw= ProjectVersion | kw= VendorId | kw= VendorName | kw= Output | kw= Libraries | kw= Resources | kw= Sources | kw= ModuleFilters | (kw= ProjectDependencies kw= KW_System ) | kw= API | kw= User | kw= Application | (kw= Processor kw= Source ) | kw= Content | kw= Test ) )
            // InternalN4MFParser.g:1851:2: (this_ID_0= RULE_ID | kw= ProjectId | kw= ProjectType | kw= ProjectVersion | kw= VendorId | kw= VendorName | kw= Output | kw= Libraries | kw= Resources | kw= Sources | kw= ModuleFilters | (kw= ProjectDependencies kw= KW_System ) | kw= API | kw= User | kw= Application | (kw= Processor kw= Source ) | kw= Content | kw= Test )
            {
            // InternalN4MFParser.g:1851:2: (this_ID_0= RULE_ID | kw= ProjectId | kw= ProjectType | kw= ProjectVersion | kw= VendorId | kw= VendorName | kw= Output | kw= Libraries | kw= Resources | kw= Sources | kw= ModuleFilters | (kw= ProjectDependencies kw= KW_System ) | kw= API | kw= User | kw= Application | (kw= Processor kw= Source ) | kw= Content | kw= Test )
            int alt33=18;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt33=1;
                }
                break;
            case ProjectId:
                {
                alt33=2;
                }
                break;
            case ProjectType:
                {
                alt33=3;
                }
                break;
            case ProjectVersion:
                {
                alt33=4;
                }
                break;
            case VendorId:
                {
                alt33=5;
                }
                break;
            case VendorName:
                {
                alt33=6;
                }
                break;
            case Output:
                {
                alt33=7;
                }
                break;
            case Libraries:
                {
                alt33=8;
                }
                break;
            case Resources:
                {
                alt33=9;
                }
                break;
            case Sources:
                {
                alt33=10;
                }
                break;
            case ModuleFilters:
                {
                alt33=11;
                }
                break;
            case ProjectDependencies:
                {
                alt33=12;
                }
                break;
            case API:
                {
                alt33=13;
                }
                break;
            case User:
                {
                alt33=14;
                }
                break;
            case Application:
                {
                alt33=15;
                }
                break;
            case Processor:
                {
                alt33=16;
                }
                break;
            case Content:
                {
                alt33=17;
                }
                break;
            case Test:
                {
                alt33=18;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }

            switch (alt33) {
                case 1 :
                    // InternalN4MFParser.g:1852:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:1860:3: kw= ProjectId
                    {
                    kw=(Token)match(input,ProjectId,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:1866:3: kw= ProjectType
                    {
                    kw=(Token)match(input,ProjectType,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalN4MFParser.g:1872:3: kw= ProjectVersion
                    {
                    kw=(Token)match(input,ProjectVersion,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalN4MFParser.g:1878:3: kw= VendorId
                    {
                    kw=(Token)match(input,VendorId,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalN4MFParser.g:1884:3: kw= VendorName
                    {
                    kw=(Token)match(input,VendorName,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalN4MFParser.g:1890:3: kw= Output
                    {
                    kw=(Token)match(input,Output,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalN4MFParser.g:1896:3: kw= Libraries
                    {
                    kw=(Token)match(input,Libraries,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalN4MFParser.g:1902:3: kw= Resources
                    {
                    kw=(Token)match(input,Resources,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalN4MFParser.g:1908:3: kw= Sources
                    {
                    kw=(Token)match(input,Sources,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalN4MFParser.g:1914:3: kw= ModuleFilters
                    {
                    kw=(Token)match(input,ModuleFilters,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalN4MFParser.g:1920:3: (kw= ProjectDependencies kw= KW_System )
                    {
                    // InternalN4MFParser.g:1920:3: (kw= ProjectDependencies kw= KW_System )
                    // InternalN4MFParser.g:1921:4: kw= ProjectDependencies kw= KW_System
                    {
                    kw=(Token)match(input,ProjectDependencies,FOLLOW_25); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProjectDependenciesKeyword_11_0());
                    			
                    kw=(Token)match(input,KW_System,FOLLOW_2); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getSystemKeyword_11_1());
                    			

                    }


                    }
                    break;
                case 13 :
                    // InternalN4MFParser.g:1933:3: kw= API
                    {
                    kw=(Token)match(input,API,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalN4MFParser.g:1939:3: kw= User
                    {
                    kw=(Token)match(input,User,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalN4MFParser.g:1945:3: kw= Application
                    {
                    kw=(Token)match(input,Application,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalN4MFParser.g:1951:3: (kw= Processor kw= Source )
                    {
                    // InternalN4MFParser.g:1951:3: (kw= Processor kw= Source )
                    // InternalN4MFParser.g:1952:4: kw= Processor kw= Source
                    {
                    kw=(Token)match(input,Processor,FOLLOW_26); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProcessorKeyword_15_0());
                    			
                    kw=(Token)match(input,Source,FOLLOW_2); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getSourceKeyword_15_1());
                    			

                    }


                    }
                    break;
                case 17 :
                    // InternalN4MFParser.g:1964:3: kw= Content
                    {
                    kw=(Token)match(input,Content,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalN4MFParser.g:1970:3: kw= Test
                    {
                    kw=(Token)match(input,Test,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getTestKeyword_17());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleN4mfIdentifier"


    // $ANTLR start "ruleProjectType"
    // InternalN4MFParser.g:1979:1: ruleProjectType returns [Enumerator current=null] : ( (enumLiteral_0= Application ) | (enumLiteral_1= Processor ) | (enumLiteral_2= Library ) | (enumLiteral_3= API ) | (enumLiteral_4= RuntimeEnvironment ) | (enumLiteral_5= RuntimeLibrary ) | (enumLiteral_6= Test ) | (enumLiteral_7= Validation ) ) ;
    public final Enumerator ruleProjectType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;


        	enterRule();

        try {
            // InternalN4MFParser.g:1985:2: ( ( (enumLiteral_0= Application ) | (enumLiteral_1= Processor ) | (enumLiteral_2= Library ) | (enumLiteral_3= API ) | (enumLiteral_4= RuntimeEnvironment ) | (enumLiteral_5= RuntimeLibrary ) | (enumLiteral_6= Test ) | (enumLiteral_7= Validation ) ) )
            // InternalN4MFParser.g:1986:2: ( (enumLiteral_0= Application ) | (enumLiteral_1= Processor ) | (enumLiteral_2= Library ) | (enumLiteral_3= API ) | (enumLiteral_4= RuntimeEnvironment ) | (enumLiteral_5= RuntimeLibrary ) | (enumLiteral_6= Test ) | (enumLiteral_7= Validation ) )
            {
            // InternalN4MFParser.g:1986:2: ( (enumLiteral_0= Application ) | (enumLiteral_1= Processor ) | (enumLiteral_2= Library ) | (enumLiteral_3= API ) | (enumLiteral_4= RuntimeEnvironment ) | (enumLiteral_5= RuntimeLibrary ) | (enumLiteral_6= Test ) | (enumLiteral_7= Validation ) )
            int alt34=8;
            switch ( input.LA(1) ) {
            case Application:
                {
                alt34=1;
                }
                break;
            case Processor:
                {
                alt34=2;
                }
                break;
            case Library:
                {
                alt34=3;
                }
                break;
            case API:
                {
                alt34=4;
                }
                break;
            case RuntimeEnvironment:
                {
                alt34=5;
                }
                break;
            case RuntimeLibrary:
                {
                alt34=6;
                }
                break;
            case Test:
                {
                alt34=7;
                }
                break;
            case Validation:
                {
                alt34=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }

            switch (alt34) {
                case 1 :
                    // InternalN4MFParser.g:1987:3: (enumLiteral_0= Application )
                    {
                    // InternalN4MFParser.g:1987:3: (enumLiteral_0= Application )
                    // InternalN4MFParser.g:1988:4: enumLiteral_0= Application
                    {
                    enumLiteral_0=(Token)match(input,Application,FOLLOW_2); 

                    				current = grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:1995:3: (enumLiteral_1= Processor )
                    {
                    // InternalN4MFParser.g:1995:3: (enumLiteral_1= Processor )
                    // InternalN4MFParser.g:1996:4: enumLiteral_1= Processor
                    {
                    enumLiteral_1=(Token)match(input,Processor,FOLLOW_2); 

                    				current = grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:2003:3: (enumLiteral_2= Library )
                    {
                    // InternalN4MFParser.g:2003:3: (enumLiteral_2= Library )
                    // InternalN4MFParser.g:2004:4: enumLiteral_2= Library
                    {
                    enumLiteral_2=(Token)match(input,Library,FOLLOW_2); 

                    				current = grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalN4MFParser.g:2011:3: (enumLiteral_3= API )
                    {
                    // InternalN4MFParser.g:2011:3: (enumLiteral_3= API )
                    // InternalN4MFParser.g:2012:4: enumLiteral_3= API
                    {
                    enumLiteral_3=(Token)match(input,API,FOLLOW_2); 

                    				current = grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalN4MFParser.g:2019:3: (enumLiteral_4= RuntimeEnvironment )
                    {
                    // InternalN4MFParser.g:2019:3: (enumLiteral_4= RuntimeEnvironment )
                    // InternalN4MFParser.g:2020:4: enumLiteral_4= RuntimeEnvironment
                    {
                    enumLiteral_4=(Token)match(input,RuntimeEnvironment,FOLLOW_2); 

                    				current = grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalN4MFParser.g:2027:3: (enumLiteral_5= RuntimeLibrary )
                    {
                    // InternalN4MFParser.g:2027:3: (enumLiteral_5= RuntimeLibrary )
                    // InternalN4MFParser.g:2028:4: enumLiteral_5= RuntimeLibrary
                    {
                    enumLiteral_5=(Token)match(input,RuntimeLibrary,FOLLOW_2); 

                    				current = grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalN4MFParser.g:2035:3: (enumLiteral_6= Test )
                    {
                    // InternalN4MFParser.g:2035:3: (enumLiteral_6= Test )
                    // InternalN4MFParser.g:2036:4: enumLiteral_6= Test
                    {
                    enumLiteral_6=(Token)match(input,Test,FOLLOW_2); 

                    				current = grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalN4MFParser.g:2043:3: (enumLiteral_7= Validation )
                    {
                    // InternalN4MFParser.g:2043:3: (enumLiteral_7= Validation )
                    // InternalN4MFParser.g:2044:4: enumLiteral_7= Validation
                    {
                    enumLiteral_7=(Token)match(input,Validation,FOLLOW_2); 

                    				current = grammarAccess.getProjectTypeAccess().getVALIDATIONEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getProjectTypeAccess().getVALIDATIONEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProjectType"


    // $ANTLR start "ruleSourceFragmentType"
    // InternalN4MFParser.g:2054:1: ruleSourceFragmentType returns [Enumerator current=null] : ( (enumLiteral_0= Source ) | (enumLiteral_1= External ) | (enumLiteral_2= Test ) ) ;
    public final Enumerator ruleSourceFragmentType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalN4MFParser.g:2060:2: ( ( (enumLiteral_0= Source ) | (enumLiteral_1= External ) | (enumLiteral_2= Test ) ) )
            // InternalN4MFParser.g:2061:2: ( (enumLiteral_0= Source ) | (enumLiteral_1= External ) | (enumLiteral_2= Test ) )
            {
            // InternalN4MFParser.g:2061:2: ( (enumLiteral_0= Source ) | (enumLiteral_1= External ) | (enumLiteral_2= Test ) )
            int alt35=3;
            switch ( input.LA(1) ) {
            case Source:
                {
                alt35=1;
                }
                break;
            case External:
                {
                alt35=2;
                }
                break;
            case Test:
                {
                alt35=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }

            switch (alt35) {
                case 1 :
                    // InternalN4MFParser.g:2062:3: (enumLiteral_0= Source )
                    {
                    // InternalN4MFParser.g:2062:3: (enumLiteral_0= Source )
                    // InternalN4MFParser.g:2063:4: enumLiteral_0= Source
                    {
                    enumLiteral_0=(Token)match(input,Source,FOLLOW_2); 

                    				current = grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:2070:3: (enumLiteral_1= External )
                    {
                    // InternalN4MFParser.g:2070:3: (enumLiteral_1= External )
                    // InternalN4MFParser.g:2071:4: enumLiteral_1= External
                    {
                    enumLiteral_1=(Token)match(input,External,FOLLOW_2); 

                    				current = grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:2078:3: (enumLiteral_2= Test )
                    {
                    // InternalN4MFParser.g:2078:3: (enumLiteral_2= Test )
                    // InternalN4MFParser.g:2079:4: enumLiteral_2= Test
                    {
                    enumLiteral_2=(Token)match(input,Test,FOLLOW_2); 

                    				current = grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSourceFragmentType"


    // $ANTLR start "ruleModuleFilterType"
    // InternalN4MFParser.g:2089:1: ruleModuleFilterType returns [Enumerator current=null] : ( (enumLiteral_0= NoValidate ) | (enumLiteral_1= NoModuleWrap ) ) ;
    public final Enumerator ruleModuleFilterType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalN4MFParser.g:2095:2: ( ( (enumLiteral_0= NoValidate ) | (enumLiteral_1= NoModuleWrap ) ) )
            // InternalN4MFParser.g:2096:2: ( (enumLiteral_0= NoValidate ) | (enumLiteral_1= NoModuleWrap ) )
            {
            // InternalN4MFParser.g:2096:2: ( (enumLiteral_0= NoValidate ) | (enumLiteral_1= NoModuleWrap ) )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==NoValidate) ) {
                alt36=1;
            }
            else if ( (LA36_0==NoModuleWrap) ) {
                alt36=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // InternalN4MFParser.g:2097:3: (enumLiteral_0= NoValidate )
                    {
                    // InternalN4MFParser.g:2097:3: (enumLiteral_0= NoValidate )
                    // InternalN4MFParser.g:2098:4: enumLiteral_0= NoValidate
                    {
                    enumLiteral_0=(Token)match(input,NoValidate,FOLLOW_2); 

                    				current = grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:2105:3: (enumLiteral_1= NoModuleWrap )
                    {
                    // InternalN4MFParser.g:2105:3: (enumLiteral_1= NoModuleWrap )
                    // InternalN4MFParser.g:2106:4: enumLiteral_1= NoModuleWrap
                    {
                    enumLiteral_1=(Token)match(input,NoModuleWrap,FOLLOW_2); 

                    				current = grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModuleFilterType"


    // $ANTLR start "ruleProjectDependencyScope"
    // InternalN4MFParser.g:2116:1: ruleProjectDependencyScope returns [Enumerator current=null] : ( (enumLiteral_0= Compile ) | (enumLiteral_1= Test ) ) ;
    public final Enumerator ruleProjectDependencyScope() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalN4MFParser.g:2122:2: ( ( (enumLiteral_0= Compile ) | (enumLiteral_1= Test ) ) )
            // InternalN4MFParser.g:2123:2: ( (enumLiteral_0= Compile ) | (enumLiteral_1= Test ) )
            {
            // InternalN4MFParser.g:2123:2: ( (enumLiteral_0= Compile ) | (enumLiteral_1= Test ) )
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==Compile) ) {
                alt37=1;
            }
            else if ( (LA37_0==Test) ) {
                alt37=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // InternalN4MFParser.g:2124:3: (enumLiteral_0= Compile )
                    {
                    // InternalN4MFParser.g:2124:3: (enumLiteral_0= Compile )
                    // InternalN4MFParser.g:2125:4: enumLiteral_0= Compile
                    {
                    enumLiteral_0=(Token)match(input,Compile,FOLLOW_2); 

                    				current = grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:2132:3: (enumLiteral_1= Test )
                    {
                    // InternalN4MFParser.g:2132:3: (enumLiteral_1= Test )
                    // InternalN4MFParser.g:2133:4: enumLiteral_1= Test
                    {
                    enumLiteral_1=(Token)match(input,Test,FOLLOW_2); 

                    				current = grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProjectDependencyScope"


    // $ANTLR start "ruleModuleLoader"
    // InternalN4MFParser.g:2143:1: ruleModuleLoader returns [Enumerator current=null] : ( (enumLiteral_0= N4js ) | (enumLiteral_1= Commonjs ) | (enumLiteral_2= Node_builtin ) ) ;
    public final Enumerator ruleModuleLoader() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalN4MFParser.g:2149:2: ( ( (enumLiteral_0= N4js ) | (enumLiteral_1= Commonjs ) | (enumLiteral_2= Node_builtin ) ) )
            // InternalN4MFParser.g:2150:2: ( (enumLiteral_0= N4js ) | (enumLiteral_1= Commonjs ) | (enumLiteral_2= Node_builtin ) )
            {
            // InternalN4MFParser.g:2150:2: ( (enumLiteral_0= N4js ) | (enumLiteral_1= Commonjs ) | (enumLiteral_2= Node_builtin ) )
            int alt38=3;
            switch ( input.LA(1) ) {
            case N4js:
                {
                alt38=1;
                }
                break;
            case Commonjs:
                {
                alt38=2;
                }
                break;
            case Node_builtin:
                {
                alt38=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }

            switch (alt38) {
                case 1 :
                    // InternalN4MFParser.g:2151:3: (enumLiteral_0= N4js )
                    {
                    // InternalN4MFParser.g:2151:3: (enumLiteral_0= N4js )
                    // InternalN4MFParser.g:2152:4: enumLiteral_0= N4js
                    {
                    enumLiteral_0=(Token)match(input,N4js,FOLLOW_2); 

                    				current = grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:2159:3: (enumLiteral_1= Commonjs )
                    {
                    // InternalN4MFParser.g:2159:3: (enumLiteral_1= Commonjs )
                    // InternalN4MFParser.g:2160:4: enumLiteral_1= Commonjs
                    {
                    enumLiteral_1=(Token)match(input,Commonjs,FOLLOW_2); 

                    				current = grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:2167:3: (enumLiteral_2= Node_builtin )
                    {
                    // InternalN4MFParser.g:2167:3: (enumLiteral_2= Node_builtin )
                    // InternalN4MFParser.g:2168:4: enumLiteral_2= Node_builtin
                    {
                    enumLiteral_2=(Token)match(input,Node_builtin,FOLLOW_2); 

                    				current = grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModuleLoader"

    // Delegated rules


    protected DFA17 dfa17 = new DFA17(this);
    protected DFA27 dfa27 = new DFA27(this);
    static final String dfa_1s = "\27\uffff";
    static final String dfa_2s = "\1\1\26\uffff";
    static final String dfa_3s = "\1\4\26\uffff";
    static final String dfa_4s = "\1\45\26\uffff";
    static final String dfa_5s = "\1\uffff\1\26\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25";
    static final String dfa_6s = "\1\0\26\uffff}>";
    static final String[] dfa_7s = {
            "\1\10\1\11\1\12\1\15\1\13\1\uffff\1\14\1\4\1\25\1\uffff\1\24\1\26\2\uffff\1\16\1\3\1\uffff\1\17\1\7\1\6\2\uffff\1\21\1\2\1\22\1\uffff\1\5\2\uffff\1\23\3\uffff\1\20",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "()+ loopback of 85:5: ( ({...}? => ( ({...}? => (otherlv_1= ProjectId otherlv_2= Colon ( (lv_projectId_3_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_4= ProjectType otherlv_5= Colon ( (lv_projectType_6_0= ruleProjectType ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_7= ProjectVersion otherlv_8= Colon ( (lv_projectVersion_9_0= ruleDeclaredVersion ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_10= VendorId otherlv_11= Colon ( (lv_declaredVendorId_12_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_13= VendorName otherlv_14= Colon ( (lv_vendorName_15_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_16= MainModule otherlv_17= Colon ( (lv_mainModule_18_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_19= ExtendedRuntimeEnvironment otherlv_20= Colon ( (lv_extendedRuntimeEnvironment_21_0= ruleProjectReference ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_22= ProvidedRuntimeLibraries otherlv_23= LeftCurlyBracket ( ( (lv_providedRuntimeLibraries_24_0= ruleProjectReference ) ) (otherlv_25= Comma ( (lv_providedRuntimeLibraries_26_0= ruleProjectReference ) ) )* )? otherlv_27= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_28= RequiredRuntimeLibraries otherlv_29= LeftCurlyBracket ( ( (lv_requiredRuntimeLibraries_30_0= ruleProjectReference ) ) (otherlv_31= Comma ( (lv_requiredRuntimeLibraries_32_0= ruleProjectReference ) ) )* )? otherlv_33= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_34= ProjectDependencies otherlv_35= LeftCurlyBracket ( ( (lv_projectDependencies_36_0= ruleProjectDependency ) ) (otherlv_37= Comma ( (lv_projectDependencies_38_0= ruleProjectDependency ) ) )* )? otherlv_39= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_40= ImplementationId otherlv_41= Colon ( (lv_implementationId_42_0= ruleN4mfIdentifier ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_43= ImplementedProjects otherlv_44= LeftCurlyBracket ( ( (lv_implementedProjects_45_0= ruleProjectReference ) ) (otherlv_46= Comma ( (lv_implementedProjects_47_0= ruleProjectReference ) ) )* )? otherlv_48= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_49= InitModules otherlv_50= LeftCurlyBracket ( ( (lv_initModules_51_0= ruleBootstrapModule ) ) (otherlv_52= Comma ( (lv_initModules_53_0= ruleBootstrapModule ) ) )* )? otherlv_54= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_55= ExecModule otherlv_56= Colon ( (lv_execModule_57_0= ruleBootstrapModule ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_58= Output otherlv_59= Colon ( (lv_outputPathRaw_60_0= RULE_STRING ) ) ) ) ) ) | ({...}? => ( ({...}? => (otherlv_61= Libraries otherlv_62= LeftCurlyBracket ( (lv_libraryPathsRaw_63_0= RULE_STRING ) ) (otherlv_64= Comma ( (lv_libraryPathsRaw_65_0= RULE_STRING ) ) )* otherlv_66= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_67= Resources otherlv_68= LeftCurlyBracket ( (lv_resourcePathsRaw_69_0= RULE_STRING ) ) (otherlv_70= Comma ( (lv_resourcePathsRaw_71_0= RULE_STRING ) ) )* otherlv_72= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_73= Sources otherlv_74= LeftCurlyBracket ( (lv_sourceFragment_75_0= ruleSourceFragment ) )+ otherlv_76= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_77= ModuleFilters otherlv_78= LeftCurlyBracket ( (lv_moduleFilters_79_0= ruleModuleFilter ) )+ otherlv_80= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_81= TestedProjects otherlv_82= LeftCurlyBracket ( ( (lv_testedProjects_83_0= ruleProjectDependency ) ) (otherlv_84= Comma ( (lv_testedProjects_85_0= ruleProjectDependency ) ) )* )? otherlv_86= RightCurlyBracket ) ) ) ) | ({...}? => ( ({...}? => (otherlv_87= ModuleLoader otherlv_88= Colon ( (lv_moduleLoader_89_0= ruleModuleLoader ) ) ) ) ) ) )+";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA17_0 = input.LA(1);

                         
                        int index17_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA17_0==EOF) ) {s = 1;}

                        else if ( LA17_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 2;}

                        else if ( LA17_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 3;}

                        else if ( LA17_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 4;}

                        else if ( LA17_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 5;}

                        else if ( LA17_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 6;}

                        else if ( LA17_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 7;}

                        else if ( LA17_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 8;}

                        else if ( LA17_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 9;}

                        else if ( LA17_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 10;}

                        else if ( LA17_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 11;}

                        else if ( LA17_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 12;}

                        else if ( LA17_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 13;}

                        else if ( LA17_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 14;}

                        else if ( LA17_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 15;}

                        else if ( LA17_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 16;}

                        else if ( LA17_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 17;}

                        else if ( LA17_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 18;}

                        else if ( LA17_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 19;}

                        else if ( LA17_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 20;}

                        else if ( LA17_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 21;}

                        else if ( LA17_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 22;}

                         
                        input.seek(index17_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 17, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_8s = "\1\uffff\13\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24";
    static final String dfa_9s = "\1\10\13\4\1\47\3\4\1\46\2\4\2\uffff\2\4";
    static final String dfa_10s = "\1\67\13\70\1\47\3\70\1\46\2\70\2\uffff\2\70";
    static final String dfa_11s = "\23\uffff\1\1\1\2\2\uffff";
    static final String dfa_12s = "\27\uffff}>";
    static final String[] dfa_13s = {
            "\1\14\2\uffff\1\4\2\uffff\1\13\4\uffff\1\3\1\17\2\uffff\1\6\2\uffff\1\10\1\2\1\11\1\20\1\5\2\uffff\1\12\1\uffff\1\21\1\uffff\1\7\3\uffff\1\22\1\16\1\15\13\uffff\1\1",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\1\25",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\1\26",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "",
            "",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\2\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24"
    };
    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = dfa_1;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "1655:3: ( ( (lv_declaredVendorId_0_0= ruleN4mfIdentifier ) ) otherlv_1= Colon )?";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x00800E2A7C984900L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x000000225CECDDF2L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x00000A1022102200L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00C00E2A7C984900L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0040800000000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0240000000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000024100000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0040024100000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000001010000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0040000001010000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000010080020000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0003000000000002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0108220400000002L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000020400000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000C00000000002L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0010400000000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000004000000000L});

}
