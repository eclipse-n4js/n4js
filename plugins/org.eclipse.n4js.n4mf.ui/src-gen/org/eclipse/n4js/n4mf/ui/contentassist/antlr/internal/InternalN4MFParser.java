package org.eclipse.n4js.n4mf.ui.contentassist.antlr.internal;
import java.util.Map;
import java.util.HashMap;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
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
public class InternalN4MFParser extends AbstractInternalContentAssistParser {
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
    	private final Map<String, String> tokenNameToValue = new HashMap<String, String>();
    	
    	{
    		tokenNameToValue.put("LeftParenthesis", "'('");
    		tokenNameToValue.put("RightParenthesis", "')'");
    		tokenNameToValue.put("Comma", "','");
    		tokenNameToValue.put("HyphenMinus", "'-'");
    		tokenNameToValue.put("FullStop", "'.'");
    		tokenNameToValue.put("Colon", "':'");
    		tokenNameToValue.put("LeftSquareBracket", "'['");
    		tokenNameToValue.put("RightSquareBracket", "']'");
    		tokenNameToValue.put("LeftCurlyBracket", "'{'");
    		tokenNameToValue.put("RightCurlyBracket", "'}'");
    		tokenNameToValue.put("In", "'in'");
    		tokenNameToValue.put("API", "'API'");
    		tokenNameToValue.put("N4js", "'n4js'");
    		tokenNameToValue.put("Test", "'test'");
    		tokenNameToValue.put("User", "'user'");
    		tokenNameToValue.put("Output", "'Output'");
    		tokenNameToValue.put("Source", "'source'");
    		tokenNameToValue.put("KW_System", "'system'");
    		tokenNameToValue.put("Sources", "'Sources'");
    		tokenNameToValue.put("Compile", "'compile'");
    		tokenNameToValue.put("Content", "'content'");
    		tokenNameToValue.put("Library", "'library'");
    		tokenNameToValue.put("VendorId", "'VendorId'");
    		tokenNameToValue.put("Commonjs", "'commonjs'");
    		tokenNameToValue.put("External", "'external'");
    		tokenNameToValue.put("Libraries", "'Libraries'");
    		tokenNameToValue.put("ProjectId", "'ProjectId'");
    		tokenNameToValue.put("Resources", "'Resources'");
    		tokenNameToValue.put("Processor", "'processor'");
    		tokenNameToValue.put("ExecModule", "'ExecModule'");
    		tokenNameToValue.put("MainModule", "'MainModule'");
    		tokenNameToValue.put("VendorName", "'VendorName'");
    		tokenNameToValue.put("NoValidate", "'noValidate'");
    		tokenNameToValue.put("Validation", "'validation'");
    		tokenNameToValue.put("InitModules", "'InitModules'");
    		tokenNameToValue.put("ProjectType", "'ProjectType'");
    		tokenNameToValue.put("Application", "'application'");
    		tokenNameToValue.put("ModuleLoader", "'ModuleLoader'");
    		tokenNameToValue.put("NoModuleWrap", "'noModuleWrap'");
    		tokenNameToValue.put("Node_builtin", "'node_builtin'");
    		tokenNameToValue.put("ModuleFilters", "'ModuleFilters'");
    		tokenNameToValue.put("ProjectVersion", "'ProjectVersion'");
    		tokenNameToValue.put("TestedProjects", "'TestedProjects'");
    		tokenNameToValue.put("RuntimeLibrary", "'runtimeLibrary'");
    		tokenNameToValue.put("ImplementationId", "'ImplementationId'");
    		tokenNameToValue.put("RuntimeEnvironment", "'runtimeEnvironment'");
    		tokenNameToValue.put("ImplementedProjects", "'ImplementedProjects'");
    		tokenNameToValue.put("ProjectDependencies", "'ProjectDependencies'");
    		tokenNameToValue.put("ProvidedRuntimeLibraries", "'ProvidedRuntimeLibraries'");
    		tokenNameToValue.put("RequiredRuntimeLibraries", "'RequiredRuntimeLibraries'");
    		tokenNameToValue.put("ExtendedRuntimeEnvironment", "'ExtendedRuntimeEnvironment'");
    	}

    	public void setGrammarAccess(N4MFGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		String result = tokenNameToValue.get(tokenName);
    		if (result == null)
    			result = tokenName;
    		return result;
    	}



    // $ANTLR start "entryRuleProjectDescription"
    // InternalN4MFParser.g:113:1: entryRuleProjectDescription : ruleProjectDescription EOF ;
    public final void entryRuleProjectDescription() throws RecognitionException {
        try {
            // InternalN4MFParser.g:114:1: ( ruleProjectDescription EOF )
            // InternalN4MFParser.g:115:1: ruleProjectDescription EOF
            {
             before(grammarAccess.getProjectDescriptionRule()); 
            pushFollow(FOLLOW_1);
            ruleProjectDescription();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleProjectDescription"


    // $ANTLR start "ruleProjectDescription"
    // InternalN4MFParser.g:122:1: ruleProjectDescription : ( ( rule__ProjectDescription__UnorderedGroup ) ) ;
    public final void ruleProjectDescription() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:126:2: ( ( ( rule__ProjectDescription__UnorderedGroup ) ) )
            // InternalN4MFParser.g:127:2: ( ( rule__ProjectDescription__UnorderedGroup ) )
            {
            // InternalN4MFParser.g:127:2: ( ( rule__ProjectDescription__UnorderedGroup ) )
            // InternalN4MFParser.g:128:3: ( rule__ProjectDescription__UnorderedGroup )
            {
             before(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup()); 
            // InternalN4MFParser.g:129:3: ( rule__ProjectDescription__UnorderedGroup )
            // InternalN4MFParser.g:129:4: rule__ProjectDescription__UnorderedGroup
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__UnorderedGroup();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProjectDescription"


    // $ANTLR start "entryRuleDeclaredVersion"
    // InternalN4MFParser.g:138:1: entryRuleDeclaredVersion : ruleDeclaredVersion EOF ;
    public final void entryRuleDeclaredVersion() throws RecognitionException {
        try {
            // InternalN4MFParser.g:139:1: ( ruleDeclaredVersion EOF )
            // InternalN4MFParser.g:140:1: ruleDeclaredVersion EOF
            {
             before(grammarAccess.getDeclaredVersionRule()); 
            pushFollow(FOLLOW_1);
            ruleDeclaredVersion();

            state._fsp--;

             after(grammarAccess.getDeclaredVersionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDeclaredVersion"


    // $ANTLR start "ruleDeclaredVersion"
    // InternalN4MFParser.g:147:1: ruleDeclaredVersion : ( ( rule__DeclaredVersion__Group__0 ) ) ;
    public final void ruleDeclaredVersion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:151:2: ( ( ( rule__DeclaredVersion__Group__0 ) ) )
            // InternalN4MFParser.g:152:2: ( ( rule__DeclaredVersion__Group__0 ) )
            {
            // InternalN4MFParser.g:152:2: ( ( rule__DeclaredVersion__Group__0 ) )
            // InternalN4MFParser.g:153:3: ( rule__DeclaredVersion__Group__0 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup()); 
            // InternalN4MFParser.g:154:3: ( rule__DeclaredVersion__Group__0 )
            // InternalN4MFParser.g:154:4: rule__DeclaredVersion__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDeclaredVersionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDeclaredVersion"


    // $ANTLR start "entryRuleSourceFragment"
    // InternalN4MFParser.g:163:1: entryRuleSourceFragment : ruleSourceFragment EOF ;
    public final void entryRuleSourceFragment() throws RecognitionException {
        try {
            // InternalN4MFParser.g:164:1: ( ruleSourceFragment EOF )
            // InternalN4MFParser.g:165:1: ruleSourceFragment EOF
            {
             before(grammarAccess.getSourceFragmentRule()); 
            pushFollow(FOLLOW_1);
            ruleSourceFragment();

            state._fsp--;

             after(grammarAccess.getSourceFragmentRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSourceFragment"


    // $ANTLR start "ruleSourceFragment"
    // InternalN4MFParser.g:172:1: ruleSourceFragment : ( ( rule__SourceFragment__Group__0 ) ) ;
    public final void ruleSourceFragment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:176:2: ( ( ( rule__SourceFragment__Group__0 ) ) )
            // InternalN4MFParser.g:177:2: ( ( rule__SourceFragment__Group__0 ) )
            {
            // InternalN4MFParser.g:177:2: ( ( rule__SourceFragment__Group__0 ) )
            // InternalN4MFParser.g:178:3: ( rule__SourceFragment__Group__0 )
            {
             before(grammarAccess.getSourceFragmentAccess().getGroup()); 
            // InternalN4MFParser.g:179:3: ( rule__SourceFragment__Group__0 )
            // InternalN4MFParser.g:179:4: rule__SourceFragment__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SourceFragment__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSourceFragmentAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSourceFragment"


    // $ANTLR start "entryRuleModuleFilter"
    // InternalN4MFParser.g:188:1: entryRuleModuleFilter : ruleModuleFilter EOF ;
    public final void entryRuleModuleFilter() throws RecognitionException {
        try {
            // InternalN4MFParser.g:189:1: ( ruleModuleFilter EOF )
            // InternalN4MFParser.g:190:1: ruleModuleFilter EOF
            {
             before(grammarAccess.getModuleFilterRule()); 
            pushFollow(FOLLOW_1);
            ruleModuleFilter();

            state._fsp--;

             after(grammarAccess.getModuleFilterRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModuleFilter"


    // $ANTLR start "ruleModuleFilter"
    // InternalN4MFParser.g:197:1: ruleModuleFilter : ( ( rule__ModuleFilter__Group__0 ) ) ;
    public final void ruleModuleFilter() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:201:2: ( ( ( rule__ModuleFilter__Group__0 ) ) )
            // InternalN4MFParser.g:202:2: ( ( rule__ModuleFilter__Group__0 ) )
            {
            // InternalN4MFParser.g:202:2: ( ( rule__ModuleFilter__Group__0 ) )
            // InternalN4MFParser.g:203:3: ( rule__ModuleFilter__Group__0 )
            {
             before(grammarAccess.getModuleFilterAccess().getGroup()); 
            // InternalN4MFParser.g:204:3: ( rule__ModuleFilter__Group__0 )
            // InternalN4MFParser.g:204:4: rule__ModuleFilter__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilter__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getModuleFilterAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModuleFilter"


    // $ANTLR start "entryRuleBootstrapModule"
    // InternalN4MFParser.g:213:1: entryRuleBootstrapModule : ruleBootstrapModule EOF ;
    public final void entryRuleBootstrapModule() throws RecognitionException {
        try {
            // InternalN4MFParser.g:214:1: ( ruleBootstrapModule EOF )
            // InternalN4MFParser.g:215:1: ruleBootstrapModule EOF
            {
             before(grammarAccess.getBootstrapModuleRule()); 
            pushFollow(FOLLOW_1);
            ruleBootstrapModule();

            state._fsp--;

             after(grammarAccess.getBootstrapModuleRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBootstrapModule"


    // $ANTLR start "ruleBootstrapModule"
    // InternalN4MFParser.g:222:1: ruleBootstrapModule : ( ( rule__BootstrapModule__Group__0 ) ) ;
    public final void ruleBootstrapModule() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:226:2: ( ( ( rule__BootstrapModule__Group__0 ) ) )
            // InternalN4MFParser.g:227:2: ( ( rule__BootstrapModule__Group__0 ) )
            {
            // InternalN4MFParser.g:227:2: ( ( rule__BootstrapModule__Group__0 ) )
            // InternalN4MFParser.g:228:3: ( rule__BootstrapModule__Group__0 )
            {
             before(grammarAccess.getBootstrapModuleAccess().getGroup()); 
            // InternalN4MFParser.g:229:3: ( rule__BootstrapModule__Group__0 )
            // InternalN4MFParser.g:229:4: rule__BootstrapModule__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__BootstrapModule__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getBootstrapModuleAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBootstrapModule"


    // $ANTLR start "entryRuleModuleFilterSpecifier"
    // InternalN4MFParser.g:238:1: entryRuleModuleFilterSpecifier : ruleModuleFilterSpecifier EOF ;
    public final void entryRuleModuleFilterSpecifier() throws RecognitionException {
        try {
            // InternalN4MFParser.g:239:1: ( ruleModuleFilterSpecifier EOF )
            // InternalN4MFParser.g:240:1: ruleModuleFilterSpecifier EOF
            {
             before(grammarAccess.getModuleFilterSpecifierRule()); 
            pushFollow(FOLLOW_1);
            ruleModuleFilterSpecifier();

            state._fsp--;

             after(grammarAccess.getModuleFilterSpecifierRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModuleFilterSpecifier"


    // $ANTLR start "ruleModuleFilterSpecifier"
    // InternalN4MFParser.g:247:1: ruleModuleFilterSpecifier : ( ( rule__ModuleFilterSpecifier__Group__0 ) ) ;
    public final void ruleModuleFilterSpecifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:251:2: ( ( ( rule__ModuleFilterSpecifier__Group__0 ) ) )
            // InternalN4MFParser.g:252:2: ( ( rule__ModuleFilterSpecifier__Group__0 ) )
            {
            // InternalN4MFParser.g:252:2: ( ( rule__ModuleFilterSpecifier__Group__0 ) )
            // InternalN4MFParser.g:253:3: ( rule__ModuleFilterSpecifier__Group__0 )
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getGroup()); 
            // InternalN4MFParser.g:254:3: ( rule__ModuleFilterSpecifier__Group__0 )
            // InternalN4MFParser.g:254:4: rule__ModuleFilterSpecifier__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilterSpecifier__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getModuleFilterSpecifierAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModuleFilterSpecifier"


    // $ANTLR start "entryRuleProvidedRuntimeLibraryDependency"
    // InternalN4MFParser.g:263:1: entryRuleProvidedRuntimeLibraryDependency : ruleProvidedRuntimeLibraryDependency EOF ;
    public final void entryRuleProvidedRuntimeLibraryDependency() throws RecognitionException {
        try {
            // InternalN4MFParser.g:264:1: ( ruleProvidedRuntimeLibraryDependency EOF )
            // InternalN4MFParser.g:265:1: ruleProvidedRuntimeLibraryDependency EOF
            {
             before(grammarAccess.getProvidedRuntimeLibraryDependencyRule()); 
            pushFollow(FOLLOW_1);
            ruleProvidedRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getProvidedRuntimeLibraryDependencyRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleProvidedRuntimeLibraryDependency"


    // $ANTLR start "ruleProvidedRuntimeLibraryDependency"
    // InternalN4MFParser.g:272:1: ruleProvidedRuntimeLibraryDependency : ( ruleProjectIdWithOptionalVendor ) ;
    public final void ruleProvidedRuntimeLibraryDependency() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:276:2: ( ( ruleProjectIdWithOptionalVendor ) )
            // InternalN4MFParser.g:277:2: ( ruleProjectIdWithOptionalVendor )
            {
            // InternalN4MFParser.g:277:2: ( ruleProjectIdWithOptionalVendor )
            // InternalN4MFParser.g:278:3: ruleProjectIdWithOptionalVendor
            {
             before(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall()); 
            pushFollow(FOLLOW_2);
            ruleProjectIdWithOptionalVendor();

            state._fsp--;

             after(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProvidedRuntimeLibraryDependency"


    // $ANTLR start "entryRuleRequiredRuntimeLibraryDependency"
    // InternalN4MFParser.g:288:1: entryRuleRequiredRuntimeLibraryDependency : ruleRequiredRuntimeLibraryDependency EOF ;
    public final void entryRuleRequiredRuntimeLibraryDependency() throws RecognitionException {
        try {
            // InternalN4MFParser.g:289:1: ( ruleRequiredRuntimeLibraryDependency EOF )
            // InternalN4MFParser.g:290:1: ruleRequiredRuntimeLibraryDependency EOF
            {
             before(grammarAccess.getRequiredRuntimeLibraryDependencyRule()); 
            pushFollow(FOLLOW_1);
            ruleRequiredRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getRequiredRuntimeLibraryDependencyRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRequiredRuntimeLibraryDependency"


    // $ANTLR start "ruleRequiredRuntimeLibraryDependency"
    // InternalN4MFParser.g:297:1: ruleRequiredRuntimeLibraryDependency : ( ruleProjectIdWithOptionalVendor ) ;
    public final void ruleRequiredRuntimeLibraryDependency() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:301:2: ( ( ruleProjectIdWithOptionalVendor ) )
            // InternalN4MFParser.g:302:2: ( ruleProjectIdWithOptionalVendor )
            {
            // InternalN4MFParser.g:302:2: ( ruleProjectIdWithOptionalVendor )
            // InternalN4MFParser.g:303:3: ruleProjectIdWithOptionalVendor
            {
             before(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall()); 
            pushFollow(FOLLOW_2);
            ruleProjectIdWithOptionalVendor();

            state._fsp--;

             after(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRequiredRuntimeLibraryDependency"


    // $ANTLR start "entryRuleTestedProject"
    // InternalN4MFParser.g:313:1: entryRuleTestedProject : ruleTestedProject EOF ;
    public final void entryRuleTestedProject() throws RecognitionException {
        try {
            // InternalN4MFParser.g:314:1: ( ruleTestedProject EOF )
            // InternalN4MFParser.g:315:1: ruleTestedProject EOF
            {
             before(grammarAccess.getTestedProjectRule()); 
            pushFollow(FOLLOW_1);
            ruleTestedProject();

            state._fsp--;

             after(grammarAccess.getTestedProjectRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTestedProject"


    // $ANTLR start "ruleTestedProject"
    // InternalN4MFParser.g:322:1: ruleTestedProject : ( ruleProjectIdWithOptionalVendor ) ;
    public final void ruleTestedProject() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:326:2: ( ( ruleProjectIdWithOptionalVendor ) )
            // InternalN4MFParser.g:327:2: ( ruleProjectIdWithOptionalVendor )
            {
            // InternalN4MFParser.g:327:2: ( ruleProjectIdWithOptionalVendor )
            // InternalN4MFParser.g:328:3: ruleProjectIdWithOptionalVendor
            {
             before(grammarAccess.getTestedProjectAccess().getProjectIdWithOptionalVendorParserRuleCall()); 
            pushFollow(FOLLOW_2);
            ruleProjectIdWithOptionalVendor();

            state._fsp--;

             after(grammarAccess.getTestedProjectAccess().getProjectIdWithOptionalVendorParserRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTestedProject"


    // $ANTLR start "entryRuleProjectReference"
    // InternalN4MFParser.g:338:1: entryRuleProjectReference : ruleProjectReference EOF ;
    public final void entryRuleProjectReference() throws RecognitionException {
        try {
            // InternalN4MFParser.g:339:1: ( ruleProjectReference EOF )
            // InternalN4MFParser.g:340:1: ruleProjectReference EOF
            {
             before(grammarAccess.getProjectReferenceRule()); 
            pushFollow(FOLLOW_1);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getProjectReferenceRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleProjectReference"


    // $ANTLR start "ruleProjectReference"
    // InternalN4MFParser.g:347:1: ruleProjectReference : ( ruleProjectIdWithOptionalVendor ) ;
    public final void ruleProjectReference() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:351:2: ( ( ruleProjectIdWithOptionalVendor ) )
            // InternalN4MFParser.g:352:2: ( ruleProjectIdWithOptionalVendor )
            {
            // InternalN4MFParser.g:352:2: ( ruleProjectIdWithOptionalVendor )
            // InternalN4MFParser.g:353:3: ruleProjectIdWithOptionalVendor
            {
             before(grammarAccess.getProjectReferenceAccess().getProjectIdWithOptionalVendorParserRuleCall()); 
            pushFollow(FOLLOW_2);
            ruleProjectIdWithOptionalVendor();

            state._fsp--;

             after(grammarAccess.getProjectReferenceAccess().getProjectIdWithOptionalVendorParserRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProjectReference"


    // $ANTLR start "entryRuleProjectDependency"
    // InternalN4MFParser.g:363:1: entryRuleProjectDependency : ruleProjectDependency EOF ;
    public final void entryRuleProjectDependency() throws RecognitionException {
        try {
            // InternalN4MFParser.g:364:1: ( ruleProjectDependency EOF )
            // InternalN4MFParser.g:365:1: ruleProjectDependency EOF
            {
             before(grammarAccess.getProjectDependencyRule()); 
            pushFollow(FOLLOW_1);
            ruleProjectDependency();

            state._fsp--;

             after(grammarAccess.getProjectDependencyRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleProjectDependency"


    // $ANTLR start "ruleProjectDependency"
    // InternalN4MFParser.g:372:1: ruleProjectDependency : ( ( rule__ProjectDependency__Group__0 ) ) ;
    public final void ruleProjectDependency() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:376:2: ( ( ( rule__ProjectDependency__Group__0 ) ) )
            // InternalN4MFParser.g:377:2: ( ( rule__ProjectDependency__Group__0 ) )
            {
            // InternalN4MFParser.g:377:2: ( ( rule__ProjectDependency__Group__0 ) )
            // InternalN4MFParser.g:378:3: ( rule__ProjectDependency__Group__0 )
            {
             before(grammarAccess.getProjectDependencyAccess().getGroup()); 
            // InternalN4MFParser.g:379:3: ( rule__ProjectDependency__Group__0 )
            // InternalN4MFParser.g:379:4: rule__ProjectDependency__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependency__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDependencyAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProjectDependency"


    // $ANTLR start "ruleProjectIdWithOptionalVendor"
    // InternalN4MFParser.g:389:1: ruleProjectIdWithOptionalVendor : ( ( rule__ProjectIdWithOptionalVendor__Group__0 ) ) ;
    public final void ruleProjectIdWithOptionalVendor() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:393:2: ( ( ( rule__ProjectIdWithOptionalVendor__Group__0 ) ) )
            // InternalN4MFParser.g:394:2: ( ( rule__ProjectIdWithOptionalVendor__Group__0 ) )
            {
            // InternalN4MFParser.g:394:2: ( ( rule__ProjectIdWithOptionalVendor__Group__0 ) )
            // InternalN4MFParser.g:395:3: ( rule__ProjectIdWithOptionalVendor__Group__0 )
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getGroup()); 
            // InternalN4MFParser.g:396:3: ( rule__ProjectIdWithOptionalVendor__Group__0 )
            // InternalN4MFParser.g:396:4: rule__ProjectIdWithOptionalVendor__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectIdWithOptionalVendor__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getProjectIdWithOptionalVendorAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProjectIdWithOptionalVendor"


    // $ANTLR start "entryRuleVersionConstraint"
    // InternalN4MFParser.g:405:1: entryRuleVersionConstraint : ruleVersionConstraint EOF ;
    public final void entryRuleVersionConstraint() throws RecognitionException {
        try {
            // InternalN4MFParser.g:406:1: ( ruleVersionConstraint EOF )
            // InternalN4MFParser.g:407:1: ruleVersionConstraint EOF
            {
             before(grammarAccess.getVersionConstraintRule()); 
            pushFollow(FOLLOW_1);
            ruleVersionConstraint();

            state._fsp--;

             after(grammarAccess.getVersionConstraintRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVersionConstraint"


    // $ANTLR start "ruleVersionConstraint"
    // InternalN4MFParser.g:414:1: ruleVersionConstraint : ( ( rule__VersionConstraint__Alternatives ) ) ;
    public final void ruleVersionConstraint() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:418:2: ( ( ( rule__VersionConstraint__Alternatives ) ) )
            // InternalN4MFParser.g:419:2: ( ( rule__VersionConstraint__Alternatives ) )
            {
            // InternalN4MFParser.g:419:2: ( ( rule__VersionConstraint__Alternatives ) )
            // InternalN4MFParser.g:420:3: ( rule__VersionConstraint__Alternatives )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives()); 
            // InternalN4MFParser.g:421:3: ( rule__VersionConstraint__Alternatives )
            // InternalN4MFParser.g:421:4: rule__VersionConstraint__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getVersionConstraintAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVersionConstraint"


    // $ANTLR start "entryRuleN4mfIdentifier"
    // InternalN4MFParser.g:430:1: entryRuleN4mfIdentifier : ruleN4mfIdentifier EOF ;
    public final void entryRuleN4mfIdentifier() throws RecognitionException {
        try {
            // InternalN4MFParser.g:431:1: ( ruleN4mfIdentifier EOF )
            // InternalN4MFParser.g:432:1: ruleN4mfIdentifier EOF
            {
             before(grammarAccess.getN4mfIdentifierRule()); 
            pushFollow(FOLLOW_1);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getN4mfIdentifierRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleN4mfIdentifier"


    // $ANTLR start "ruleN4mfIdentifier"
    // InternalN4MFParser.g:439:1: ruleN4mfIdentifier : ( ( rule__N4mfIdentifier__Alternatives ) ) ;
    public final void ruleN4mfIdentifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:443:2: ( ( ( rule__N4mfIdentifier__Alternatives ) ) )
            // InternalN4MFParser.g:444:2: ( ( rule__N4mfIdentifier__Alternatives ) )
            {
            // InternalN4MFParser.g:444:2: ( ( rule__N4mfIdentifier__Alternatives ) )
            // InternalN4MFParser.g:445:3: ( rule__N4mfIdentifier__Alternatives )
            {
             before(grammarAccess.getN4mfIdentifierAccess().getAlternatives()); 
            // InternalN4MFParser.g:446:3: ( rule__N4mfIdentifier__Alternatives )
            // InternalN4MFParser.g:446:4: rule__N4mfIdentifier__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__N4mfIdentifier__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getN4mfIdentifierAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleN4mfIdentifier"


    // $ANTLR start "ruleProjectType"
    // InternalN4MFParser.g:455:1: ruleProjectType : ( ( rule__ProjectType__Alternatives ) ) ;
    public final void ruleProjectType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:459:1: ( ( ( rule__ProjectType__Alternatives ) ) )
            // InternalN4MFParser.g:460:2: ( ( rule__ProjectType__Alternatives ) )
            {
            // InternalN4MFParser.g:460:2: ( ( rule__ProjectType__Alternatives ) )
            // InternalN4MFParser.g:461:3: ( rule__ProjectType__Alternatives )
            {
             before(grammarAccess.getProjectTypeAccess().getAlternatives()); 
            // InternalN4MFParser.g:462:3: ( rule__ProjectType__Alternatives )
            // InternalN4MFParser.g:462:4: rule__ProjectType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__ProjectType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getProjectTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProjectType"


    // $ANTLR start "ruleSourceFragmentType"
    // InternalN4MFParser.g:471:1: ruleSourceFragmentType : ( ( rule__SourceFragmentType__Alternatives ) ) ;
    public final void ruleSourceFragmentType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:475:1: ( ( ( rule__SourceFragmentType__Alternatives ) ) )
            // InternalN4MFParser.g:476:2: ( ( rule__SourceFragmentType__Alternatives ) )
            {
            // InternalN4MFParser.g:476:2: ( ( rule__SourceFragmentType__Alternatives ) )
            // InternalN4MFParser.g:477:3: ( rule__SourceFragmentType__Alternatives )
            {
             before(grammarAccess.getSourceFragmentTypeAccess().getAlternatives()); 
            // InternalN4MFParser.g:478:3: ( rule__SourceFragmentType__Alternatives )
            // InternalN4MFParser.g:478:4: rule__SourceFragmentType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__SourceFragmentType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getSourceFragmentTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSourceFragmentType"


    // $ANTLR start "ruleModuleFilterType"
    // InternalN4MFParser.g:487:1: ruleModuleFilterType : ( ( rule__ModuleFilterType__Alternatives ) ) ;
    public final void ruleModuleFilterType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:491:1: ( ( ( rule__ModuleFilterType__Alternatives ) ) )
            // InternalN4MFParser.g:492:2: ( ( rule__ModuleFilterType__Alternatives ) )
            {
            // InternalN4MFParser.g:492:2: ( ( rule__ModuleFilterType__Alternatives ) )
            // InternalN4MFParser.g:493:3: ( rule__ModuleFilterType__Alternatives )
            {
             before(grammarAccess.getModuleFilterTypeAccess().getAlternatives()); 
            // InternalN4MFParser.g:494:3: ( rule__ModuleFilterType__Alternatives )
            // InternalN4MFParser.g:494:4: rule__ModuleFilterType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilterType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getModuleFilterTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModuleFilterType"


    // $ANTLR start "ruleProjectDependencyScope"
    // InternalN4MFParser.g:503:1: ruleProjectDependencyScope : ( ( rule__ProjectDependencyScope__Alternatives ) ) ;
    public final void ruleProjectDependencyScope() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:507:1: ( ( ( rule__ProjectDependencyScope__Alternatives ) ) )
            // InternalN4MFParser.g:508:2: ( ( rule__ProjectDependencyScope__Alternatives ) )
            {
            // InternalN4MFParser.g:508:2: ( ( rule__ProjectDependencyScope__Alternatives ) )
            // InternalN4MFParser.g:509:3: ( rule__ProjectDependencyScope__Alternatives )
            {
             before(grammarAccess.getProjectDependencyScopeAccess().getAlternatives()); 
            // InternalN4MFParser.g:510:3: ( rule__ProjectDependencyScope__Alternatives )
            // InternalN4MFParser.g:510:4: rule__ProjectDependencyScope__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependencyScope__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getProjectDependencyScopeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProjectDependencyScope"


    // $ANTLR start "ruleModuleLoader"
    // InternalN4MFParser.g:519:1: ruleModuleLoader : ( ( rule__ModuleLoader__Alternatives ) ) ;
    public final void ruleModuleLoader() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:523:1: ( ( ( rule__ModuleLoader__Alternatives ) ) )
            // InternalN4MFParser.g:524:2: ( ( rule__ModuleLoader__Alternatives ) )
            {
            // InternalN4MFParser.g:524:2: ( ( rule__ModuleLoader__Alternatives ) )
            // InternalN4MFParser.g:525:3: ( rule__ModuleLoader__Alternatives )
            {
             before(grammarAccess.getModuleLoaderAccess().getAlternatives()); 
            // InternalN4MFParser.g:526:3: ( rule__ModuleLoader__Alternatives )
            // InternalN4MFParser.g:526:4: rule__ModuleLoader__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__ModuleLoader__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getModuleLoaderAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModuleLoader"


    // $ANTLR start "rule__VersionConstraint__Alternatives"
    // InternalN4MFParser.g:534:1: rule__VersionConstraint__Alternatives : ( ( ( rule__VersionConstraint__Group_0__0 ) ) | ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) ) );
    public final void rule__VersionConstraint__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:538:1: ( ( ( rule__VersionConstraint__Group_0__0 ) ) | ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==LeftParenthesis||LA1_0==LeftSquareBracket) ) {
                alt1=1;
            }
            else if ( (LA1_0==RULE_INT) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalN4MFParser.g:539:2: ( ( rule__VersionConstraint__Group_0__0 ) )
                    {
                    // InternalN4MFParser.g:539:2: ( ( rule__VersionConstraint__Group_0__0 ) )
                    // InternalN4MFParser.g:540:3: ( rule__VersionConstraint__Group_0__0 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getGroup_0()); 
                    // InternalN4MFParser.g:541:3: ( rule__VersionConstraint__Group_0__0 )
                    // InternalN4MFParser.g:541:4: rule__VersionConstraint__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionConstraint__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getVersionConstraintAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:545:2: ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) )
                    {
                    // InternalN4MFParser.g:545:2: ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) )
                    // InternalN4MFParser.g:546:3: ( rule__VersionConstraint__LowerVersionAssignment_1 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_1()); 
                    // InternalN4MFParser.g:547:3: ( rule__VersionConstraint__LowerVersionAssignment_1 )
                    // InternalN4MFParser.g:547:4: rule__VersionConstraint__LowerVersionAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionConstraint__LowerVersionAssignment_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Alternatives"


    // $ANTLR start "rule__VersionConstraint__Alternatives_0_0"
    // InternalN4MFParser.g:555:1: rule__VersionConstraint__Alternatives_0_0 : ( ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) ) | ( LeftSquareBracket ) );
    public final void rule__VersionConstraint__Alternatives_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:559:1: ( ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) ) | ( LeftSquareBracket ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==LeftParenthesis) ) {
                alt2=1;
            }
            else if ( (LA2_0==LeftSquareBracket) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalN4MFParser.g:560:2: ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) )
                    {
                    // InternalN4MFParser.g:560:2: ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) )
                    // InternalN4MFParser.g:561:3: ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundAssignment_0_0_0()); 
                    // InternalN4MFParser.g:562:3: ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 )
                    // InternalN4MFParser.g:562:4: rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getVersionConstraintAccess().getExclLowerBoundAssignment_0_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:566:2: ( LeftSquareBracket )
                    {
                    // InternalN4MFParser.g:566:2: ( LeftSquareBracket )
                    // InternalN4MFParser.g:567:3: LeftSquareBracket
                    {
                     before(grammarAccess.getVersionConstraintAccess().getLeftSquareBracketKeyword_0_0_1()); 
                    match(input,LeftSquareBracket,FOLLOW_2); 
                     after(grammarAccess.getVersionConstraintAccess().getLeftSquareBracketKeyword_0_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Alternatives_0_0"


    // $ANTLR start "rule__VersionConstraint__Alternatives_0_2"
    // InternalN4MFParser.g:576:1: rule__VersionConstraint__Alternatives_0_2 : ( ( ( rule__VersionConstraint__Group_0_2_0__0 )? ) | ( RightParenthesis ) );
    public final void rule__VersionConstraint__Alternatives_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:580:1: ( ( ( rule__VersionConstraint__Group_0_2_0__0 )? ) | ( RightParenthesis ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==EOF||LA4_0==Compile||LA4_0==Test||LA4_0==Comma||LA4_0==RightCurlyBracket) ) {
                alt4=1;
            }
            else if ( (LA4_0==RightParenthesis) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // InternalN4MFParser.g:581:2: ( ( rule__VersionConstraint__Group_0_2_0__0 )? )
                    {
                    // InternalN4MFParser.g:581:2: ( ( rule__VersionConstraint__Group_0_2_0__0 )? )
                    // InternalN4MFParser.g:582:3: ( rule__VersionConstraint__Group_0_2_0__0 )?
                    {
                     before(grammarAccess.getVersionConstraintAccess().getGroup_0_2_0()); 
                    // InternalN4MFParser.g:583:3: ( rule__VersionConstraint__Group_0_2_0__0 )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==Comma) ) {
                        int LA3_1 = input.LA(2);

                        if ( (LA3_1==RULE_INT) ) {
                            alt3=1;
                        }
                    }
                    switch (alt3) {
                        case 1 :
                            // InternalN4MFParser.g:583:4: rule__VersionConstraint__Group_0_2_0__0
                            {
                            pushFollow(FOLLOW_2);
                            rule__VersionConstraint__Group_0_2_0__0();

                            state._fsp--;


                            }
                            break;

                    }

                     after(grammarAccess.getVersionConstraintAccess().getGroup_0_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:587:2: ( RightParenthesis )
                    {
                    // InternalN4MFParser.g:587:2: ( RightParenthesis )
                    // InternalN4MFParser.g:588:3: RightParenthesis
                    {
                     before(grammarAccess.getVersionConstraintAccess().getRightParenthesisKeyword_0_2_1()); 
                    match(input,RightParenthesis,FOLLOW_2); 
                     after(grammarAccess.getVersionConstraintAccess().getRightParenthesisKeyword_0_2_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Alternatives_0_2"


    // $ANTLR start "rule__VersionConstraint__Alternatives_0_2_0_2"
    // InternalN4MFParser.g:597:1: rule__VersionConstraint__Alternatives_0_2_0_2 : ( ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) ) | ( RightSquareBracket ) );
    public final void rule__VersionConstraint__Alternatives_0_2_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:601:1: ( ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) ) | ( RightSquareBracket ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RightParenthesis) ) {
                alt5=1;
            }
            else if ( (LA5_0==RightSquareBracket) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalN4MFParser.g:602:2: ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) )
                    {
                    // InternalN4MFParser.g:602:2: ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) )
                    // InternalN4MFParser.g:603:3: ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundAssignment_0_2_0_2_0()); 
                    // InternalN4MFParser.g:604:3: ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 )
                    // InternalN4MFParser.g:604:4: rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getVersionConstraintAccess().getExclUpperBoundAssignment_0_2_0_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:608:2: ( RightSquareBracket )
                    {
                    // InternalN4MFParser.g:608:2: ( RightSquareBracket )
                    // InternalN4MFParser.g:609:3: RightSquareBracket
                    {
                     before(grammarAccess.getVersionConstraintAccess().getRightSquareBracketKeyword_0_2_0_2_1()); 
                    match(input,RightSquareBracket,FOLLOW_2); 
                     after(grammarAccess.getVersionConstraintAccess().getRightSquareBracketKeyword_0_2_0_2_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Alternatives_0_2_0_2"


    // $ANTLR start "rule__N4mfIdentifier__Alternatives"
    // InternalN4MFParser.g:618:1: rule__N4mfIdentifier__Alternatives : ( ( RULE_ID ) | ( ProjectId ) | ( ProjectType ) | ( ProjectVersion ) | ( VendorId ) | ( VendorName ) | ( Output ) | ( Libraries ) | ( Resources ) | ( Sources ) | ( ModuleFilters ) | ( ( rule__N4mfIdentifier__Group_11__0 ) ) | ( API ) | ( User ) | ( Application ) | ( ( rule__N4mfIdentifier__Group_15__0 ) ) | ( Content ) | ( Test ) );
    public final void rule__N4mfIdentifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:622:1: ( ( RULE_ID ) | ( ProjectId ) | ( ProjectType ) | ( ProjectVersion ) | ( VendorId ) | ( VendorName ) | ( Output ) | ( Libraries ) | ( Resources ) | ( Sources ) | ( ModuleFilters ) | ( ( rule__N4mfIdentifier__Group_11__0 ) ) | ( API ) | ( User ) | ( Application ) | ( ( rule__N4mfIdentifier__Group_15__0 ) ) | ( Content ) | ( Test ) )
            int alt6=18;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt6=1;
                }
                break;
            case ProjectId:
                {
                alt6=2;
                }
                break;
            case ProjectType:
                {
                alt6=3;
                }
                break;
            case ProjectVersion:
                {
                alt6=4;
                }
                break;
            case VendorId:
                {
                alt6=5;
                }
                break;
            case VendorName:
                {
                alt6=6;
                }
                break;
            case Output:
                {
                alt6=7;
                }
                break;
            case Libraries:
                {
                alt6=8;
                }
                break;
            case Resources:
                {
                alt6=9;
                }
                break;
            case Sources:
                {
                alt6=10;
                }
                break;
            case ModuleFilters:
                {
                alt6=11;
                }
                break;
            case ProjectDependencies:
                {
                alt6=12;
                }
                break;
            case API:
                {
                alt6=13;
                }
                break;
            case User:
                {
                alt6=14;
                }
                break;
            case Application:
                {
                alt6=15;
                }
                break;
            case Processor:
                {
                alt6=16;
                }
                break;
            case Content:
                {
                alt6=17;
                }
                break;
            case Test:
                {
                alt6=18;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalN4MFParser.g:623:2: ( RULE_ID )
                    {
                    // InternalN4MFParser.g:623:2: ( RULE_ID )
                    // InternalN4MFParser.g:624:3: RULE_ID
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:629:2: ( ProjectId )
                    {
                    // InternalN4MFParser.g:629:2: ( ProjectId )
                    // InternalN4MFParser.g:630:3: ProjectId
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1()); 
                    match(input,ProjectId,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:635:2: ( ProjectType )
                    {
                    // InternalN4MFParser.g:635:2: ( ProjectType )
                    // InternalN4MFParser.g:636:3: ProjectType
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2()); 
                    match(input,ProjectType,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalN4MFParser.g:641:2: ( ProjectVersion )
                    {
                    // InternalN4MFParser.g:641:2: ( ProjectVersion )
                    // InternalN4MFParser.g:642:3: ProjectVersion
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3()); 
                    match(input,ProjectVersion,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalN4MFParser.g:647:2: ( VendorId )
                    {
                    // InternalN4MFParser.g:647:2: ( VendorId )
                    // InternalN4MFParser.g:648:3: VendorId
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4()); 
                    match(input,VendorId,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalN4MFParser.g:653:2: ( VendorName )
                    {
                    // InternalN4MFParser.g:653:2: ( VendorName )
                    // InternalN4MFParser.g:654:3: VendorName
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5()); 
                    match(input,VendorName,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalN4MFParser.g:659:2: ( Output )
                    {
                    // InternalN4MFParser.g:659:2: ( Output )
                    // InternalN4MFParser.g:660:3: Output
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6()); 
                    match(input,Output,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalN4MFParser.g:665:2: ( Libraries )
                    {
                    // InternalN4MFParser.g:665:2: ( Libraries )
                    // InternalN4MFParser.g:666:3: Libraries
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7()); 
                    match(input,Libraries,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7()); 

                    }


                    }
                    break;
                case 9 :
                    // InternalN4MFParser.g:671:2: ( Resources )
                    {
                    // InternalN4MFParser.g:671:2: ( Resources )
                    // InternalN4MFParser.g:672:3: Resources
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8()); 
                    match(input,Resources,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8()); 

                    }


                    }
                    break;
                case 10 :
                    // InternalN4MFParser.g:677:2: ( Sources )
                    {
                    // InternalN4MFParser.g:677:2: ( Sources )
                    // InternalN4MFParser.g:678:3: Sources
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9()); 
                    match(input,Sources,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9()); 

                    }


                    }
                    break;
                case 11 :
                    // InternalN4MFParser.g:683:2: ( ModuleFilters )
                    {
                    // InternalN4MFParser.g:683:2: ( ModuleFilters )
                    // InternalN4MFParser.g:684:3: ModuleFilters
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10()); 
                    match(input,ModuleFilters,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10()); 

                    }


                    }
                    break;
                case 12 :
                    // InternalN4MFParser.g:689:2: ( ( rule__N4mfIdentifier__Group_11__0 ) )
                    {
                    // InternalN4MFParser.g:689:2: ( ( rule__N4mfIdentifier__Group_11__0 ) )
                    // InternalN4MFParser.g:690:3: ( rule__N4mfIdentifier__Group_11__0 )
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getGroup_11()); 
                    // InternalN4MFParser.g:691:3: ( rule__N4mfIdentifier__Group_11__0 )
                    // InternalN4MFParser.g:691:4: rule__N4mfIdentifier__Group_11__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__N4mfIdentifier__Group_11__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getN4mfIdentifierAccess().getGroup_11()); 

                    }


                    }
                    break;
                case 13 :
                    // InternalN4MFParser.g:695:2: ( API )
                    {
                    // InternalN4MFParser.g:695:2: ( API )
                    // InternalN4MFParser.g:696:3: API
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12()); 
                    match(input,API,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12()); 

                    }


                    }
                    break;
                case 14 :
                    // InternalN4MFParser.g:701:2: ( User )
                    {
                    // InternalN4MFParser.g:701:2: ( User )
                    // InternalN4MFParser.g:702:3: User
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13()); 
                    match(input,User,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13()); 

                    }


                    }
                    break;
                case 15 :
                    // InternalN4MFParser.g:707:2: ( Application )
                    {
                    // InternalN4MFParser.g:707:2: ( Application )
                    // InternalN4MFParser.g:708:3: Application
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14()); 
                    match(input,Application,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14()); 

                    }


                    }
                    break;
                case 16 :
                    // InternalN4MFParser.g:713:2: ( ( rule__N4mfIdentifier__Group_15__0 ) )
                    {
                    // InternalN4MFParser.g:713:2: ( ( rule__N4mfIdentifier__Group_15__0 ) )
                    // InternalN4MFParser.g:714:3: ( rule__N4mfIdentifier__Group_15__0 )
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getGroup_15()); 
                    // InternalN4MFParser.g:715:3: ( rule__N4mfIdentifier__Group_15__0 )
                    // InternalN4MFParser.g:715:4: rule__N4mfIdentifier__Group_15__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__N4mfIdentifier__Group_15__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getN4mfIdentifierAccess().getGroup_15()); 

                    }


                    }
                    break;
                case 17 :
                    // InternalN4MFParser.g:719:2: ( Content )
                    {
                    // InternalN4MFParser.g:719:2: ( Content )
                    // InternalN4MFParser.g:720:3: Content
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16()); 
                    match(input,Content,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16()); 

                    }


                    }
                    break;
                case 18 :
                    // InternalN4MFParser.g:725:2: ( Test )
                    {
                    // InternalN4MFParser.g:725:2: ( Test )
                    // InternalN4MFParser.g:726:3: Test
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getTestKeyword_17()); 
                    match(input,Test,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getTestKeyword_17()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__N4mfIdentifier__Alternatives"


    // $ANTLR start "rule__ProjectType__Alternatives"
    // InternalN4MFParser.g:735:1: rule__ProjectType__Alternatives : ( ( ( Application ) ) | ( ( Processor ) ) | ( ( Library ) ) | ( ( API ) ) | ( ( RuntimeEnvironment ) ) | ( ( RuntimeLibrary ) ) | ( ( Test ) ) | ( ( Validation ) ) );
    public final void rule__ProjectType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:739:1: ( ( ( Application ) ) | ( ( Processor ) ) | ( ( Library ) ) | ( ( API ) ) | ( ( RuntimeEnvironment ) ) | ( ( RuntimeLibrary ) ) | ( ( Test ) ) | ( ( Validation ) ) )
            int alt7=8;
            switch ( input.LA(1) ) {
            case Application:
                {
                alt7=1;
                }
                break;
            case Processor:
                {
                alt7=2;
                }
                break;
            case Library:
                {
                alt7=3;
                }
                break;
            case API:
                {
                alt7=4;
                }
                break;
            case RuntimeEnvironment:
                {
                alt7=5;
                }
                break;
            case RuntimeLibrary:
                {
                alt7=6;
                }
                break;
            case Test:
                {
                alt7=7;
                }
                break;
            case Validation:
                {
                alt7=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // InternalN4MFParser.g:740:2: ( ( Application ) )
                    {
                    // InternalN4MFParser.g:740:2: ( ( Application ) )
                    // InternalN4MFParser.g:741:3: ( Application )
                    {
                     before(grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:742:3: ( Application )
                    // InternalN4MFParser.g:742:4: Application
                    {
                    match(input,Application,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:746:2: ( ( Processor ) )
                    {
                    // InternalN4MFParser.g:746:2: ( ( Processor ) )
                    // InternalN4MFParser.g:747:3: ( Processor )
                    {
                     before(grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:748:3: ( Processor )
                    // InternalN4MFParser.g:748:4: Processor
                    {
                    match(input,Processor,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:752:2: ( ( Library ) )
                    {
                    // InternalN4MFParser.g:752:2: ( ( Library ) )
                    // InternalN4MFParser.g:753:3: ( Library )
                    {
                     before(grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2()); 
                    // InternalN4MFParser.g:754:3: ( Library )
                    // InternalN4MFParser.g:754:4: Library
                    {
                    match(input,Library,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalN4MFParser.g:758:2: ( ( API ) )
                    {
                    // InternalN4MFParser.g:758:2: ( ( API ) )
                    // InternalN4MFParser.g:759:3: ( API )
                    {
                     before(grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3()); 
                    // InternalN4MFParser.g:760:3: ( API )
                    // InternalN4MFParser.g:760:4: API
                    {
                    match(input,API,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalN4MFParser.g:764:2: ( ( RuntimeEnvironment ) )
                    {
                    // InternalN4MFParser.g:764:2: ( ( RuntimeEnvironment ) )
                    // InternalN4MFParser.g:765:3: ( RuntimeEnvironment )
                    {
                     before(grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4()); 
                    // InternalN4MFParser.g:766:3: ( RuntimeEnvironment )
                    // InternalN4MFParser.g:766:4: RuntimeEnvironment
                    {
                    match(input,RuntimeEnvironment,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalN4MFParser.g:770:2: ( ( RuntimeLibrary ) )
                    {
                    // InternalN4MFParser.g:770:2: ( ( RuntimeLibrary ) )
                    // InternalN4MFParser.g:771:3: ( RuntimeLibrary )
                    {
                     before(grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5()); 
                    // InternalN4MFParser.g:772:3: ( RuntimeLibrary )
                    // InternalN4MFParser.g:772:4: RuntimeLibrary
                    {
                    match(input,RuntimeLibrary,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalN4MFParser.g:776:2: ( ( Test ) )
                    {
                    // InternalN4MFParser.g:776:2: ( ( Test ) )
                    // InternalN4MFParser.g:777:3: ( Test )
                    {
                     before(grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6()); 
                    // InternalN4MFParser.g:778:3: ( Test )
                    // InternalN4MFParser.g:778:4: Test
                    {
                    match(input,Test,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalN4MFParser.g:782:2: ( ( Validation ) )
                    {
                    // InternalN4MFParser.g:782:2: ( ( Validation ) )
                    // InternalN4MFParser.g:783:3: ( Validation )
                    {
                     before(grammarAccess.getProjectTypeAccess().getVALIDATIONEnumLiteralDeclaration_7()); 
                    // InternalN4MFParser.g:784:3: ( Validation )
                    // InternalN4MFParser.g:784:4: Validation
                    {
                    match(input,Validation,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getVALIDATIONEnumLiteralDeclaration_7()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectType__Alternatives"


    // $ANTLR start "rule__SourceFragmentType__Alternatives"
    // InternalN4MFParser.g:792:1: rule__SourceFragmentType__Alternatives : ( ( ( Source ) ) | ( ( External ) ) | ( ( Test ) ) );
    public final void rule__SourceFragmentType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:796:1: ( ( ( Source ) ) | ( ( External ) ) | ( ( Test ) ) )
            int alt8=3;
            switch ( input.LA(1) ) {
            case Source:
                {
                alt8=1;
                }
                break;
            case External:
                {
                alt8=2;
                }
                break;
            case Test:
                {
                alt8=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalN4MFParser.g:797:2: ( ( Source ) )
                    {
                    // InternalN4MFParser.g:797:2: ( ( Source ) )
                    // InternalN4MFParser.g:798:3: ( Source )
                    {
                     before(grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:799:3: ( Source )
                    // InternalN4MFParser.g:799:4: Source
                    {
                    match(input,Source,FOLLOW_2); 

                    }

                     after(grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:803:2: ( ( External ) )
                    {
                    // InternalN4MFParser.g:803:2: ( ( External ) )
                    // InternalN4MFParser.g:804:3: ( External )
                    {
                     before(grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:805:3: ( External )
                    // InternalN4MFParser.g:805:4: External
                    {
                    match(input,External,FOLLOW_2); 

                    }

                     after(grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:809:2: ( ( Test ) )
                    {
                    // InternalN4MFParser.g:809:2: ( ( Test ) )
                    // InternalN4MFParser.g:810:3: ( Test )
                    {
                     before(grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2()); 
                    // InternalN4MFParser.g:811:3: ( Test )
                    // InternalN4MFParser.g:811:4: Test
                    {
                    match(input,Test,FOLLOW_2); 

                    }

                     after(grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragmentType__Alternatives"


    // $ANTLR start "rule__ModuleFilterType__Alternatives"
    // InternalN4MFParser.g:819:1: rule__ModuleFilterType__Alternatives : ( ( ( NoValidate ) ) | ( ( NoModuleWrap ) ) );
    public final void rule__ModuleFilterType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:823:1: ( ( ( NoValidate ) ) | ( ( NoModuleWrap ) ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==NoValidate) ) {
                alt9=1;
            }
            else if ( (LA9_0==NoModuleWrap) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalN4MFParser.g:824:2: ( ( NoValidate ) )
                    {
                    // InternalN4MFParser.g:824:2: ( ( NoValidate ) )
                    // InternalN4MFParser.g:825:3: ( NoValidate )
                    {
                     before(grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:826:3: ( NoValidate )
                    // InternalN4MFParser.g:826:4: NoValidate
                    {
                    match(input,NoValidate,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:830:2: ( ( NoModuleWrap ) )
                    {
                    // InternalN4MFParser.g:830:2: ( ( NoModuleWrap ) )
                    // InternalN4MFParser.g:831:3: ( NoModuleWrap )
                    {
                     before(grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:832:3: ( NoModuleWrap )
                    // InternalN4MFParser.g:832:4: NoModuleWrap
                    {
                    match(input,NoModuleWrap,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterType__Alternatives"


    // $ANTLR start "rule__ProjectDependencyScope__Alternatives"
    // InternalN4MFParser.g:840:1: rule__ProjectDependencyScope__Alternatives : ( ( ( Compile ) ) | ( ( Test ) ) );
    public final void rule__ProjectDependencyScope__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:844:1: ( ( ( Compile ) ) | ( ( Test ) ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==Compile) ) {
                alt10=1;
            }
            else if ( (LA10_0==Test) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalN4MFParser.g:845:2: ( ( Compile ) )
                    {
                    // InternalN4MFParser.g:845:2: ( ( Compile ) )
                    // InternalN4MFParser.g:846:3: ( Compile )
                    {
                     before(grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:847:3: ( Compile )
                    // InternalN4MFParser.g:847:4: Compile
                    {
                    match(input,Compile,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:851:2: ( ( Test ) )
                    {
                    // InternalN4MFParser.g:851:2: ( ( Test ) )
                    // InternalN4MFParser.g:852:3: ( Test )
                    {
                     before(grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:853:3: ( Test )
                    // InternalN4MFParser.g:853:4: Test
                    {
                    match(input,Test,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencyScope__Alternatives"


    // $ANTLR start "rule__ModuleLoader__Alternatives"
    // InternalN4MFParser.g:861:1: rule__ModuleLoader__Alternatives : ( ( ( N4js ) ) | ( ( Commonjs ) ) | ( ( Node_builtin ) ) );
    public final void rule__ModuleLoader__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:865:1: ( ( ( N4js ) ) | ( ( Commonjs ) ) | ( ( Node_builtin ) ) )
            int alt11=3;
            switch ( input.LA(1) ) {
            case N4js:
                {
                alt11=1;
                }
                break;
            case Commonjs:
                {
                alt11=2;
                }
                break;
            case Node_builtin:
                {
                alt11=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // InternalN4MFParser.g:866:2: ( ( N4js ) )
                    {
                    // InternalN4MFParser.g:866:2: ( ( N4js ) )
                    // InternalN4MFParser.g:867:3: ( N4js )
                    {
                     before(grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:868:3: ( N4js )
                    // InternalN4MFParser.g:868:4: N4js
                    {
                    match(input,N4js,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:872:2: ( ( Commonjs ) )
                    {
                    // InternalN4MFParser.g:872:2: ( ( Commonjs ) )
                    // InternalN4MFParser.g:873:3: ( Commonjs )
                    {
                     before(grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:874:3: ( Commonjs )
                    // InternalN4MFParser.g:874:4: Commonjs
                    {
                    match(input,Commonjs,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:878:2: ( ( Node_builtin ) )
                    {
                    // InternalN4MFParser.g:878:2: ( ( Node_builtin ) )
                    // InternalN4MFParser.g:879:3: ( Node_builtin )
                    {
                     before(grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2()); 
                    // InternalN4MFParser.g:880:3: ( Node_builtin )
                    // InternalN4MFParser.g:880:4: Node_builtin
                    {
                    match(input,Node_builtin,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleLoader__Alternatives"


    // $ANTLR start "rule__ProjectDescription__Group_0__0"
    // InternalN4MFParser.g:888:1: rule__ProjectDescription__Group_0__0 : rule__ProjectDescription__Group_0__0__Impl rule__ProjectDescription__Group_0__1 ;
    public final void rule__ProjectDescription__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:892:1: ( rule__ProjectDescription__Group_0__0__Impl rule__ProjectDescription__Group_0__1 )
            // InternalN4MFParser.g:893:2: rule__ProjectDescription__Group_0__0__Impl rule__ProjectDescription__Group_0__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_0__0"


    // $ANTLR start "rule__ProjectDescription__Group_0__0__Impl"
    // InternalN4MFParser.g:900:1: rule__ProjectDescription__Group_0__0__Impl : ( ProjectId ) ;
    public final void rule__ProjectDescription__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:904:1: ( ( ProjectId ) )
            // InternalN4MFParser.g:905:1: ( ProjectId )
            {
            // InternalN4MFParser.g:905:1: ( ProjectId )
            // InternalN4MFParser.g:906:2: ProjectId
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectIdKeyword_0_0()); 
            match(input,ProjectId,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getProjectIdKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_0__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_0__1"
    // InternalN4MFParser.g:915:1: rule__ProjectDescription__Group_0__1 : rule__ProjectDescription__Group_0__1__Impl rule__ProjectDescription__Group_0__2 ;
    public final void rule__ProjectDescription__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:919:1: ( rule__ProjectDescription__Group_0__1__Impl rule__ProjectDescription__Group_0__2 )
            // InternalN4MFParser.g:920:2: rule__ProjectDescription__Group_0__1__Impl rule__ProjectDescription__Group_0__2
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDescription__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_0__1"


    // $ANTLR start "rule__ProjectDescription__Group_0__1__Impl"
    // InternalN4MFParser.g:927:1: rule__ProjectDescription__Group_0__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:931:1: ( ( Colon ) )
            // InternalN4MFParser.g:932:1: ( Colon )
            {
            // InternalN4MFParser.g:932:1: ( Colon )
            // InternalN4MFParser.g:933:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_0_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_0__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_0__2"
    // InternalN4MFParser.g:942:1: rule__ProjectDescription__Group_0__2 : rule__ProjectDescription__Group_0__2__Impl ;
    public final void rule__ProjectDescription__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:946:1: ( rule__ProjectDescription__Group_0__2__Impl )
            // InternalN4MFParser.g:947:2: rule__ProjectDescription__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_0__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_0__2"


    // $ANTLR start "rule__ProjectDescription__Group_0__2__Impl"
    // InternalN4MFParser.g:953:1: rule__ProjectDescription__Group_0__2__Impl : ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) ) ;
    public final void rule__ProjectDescription__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:957:1: ( ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) ) )
            // InternalN4MFParser.g:958:1: ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) )
            {
            // InternalN4MFParser.g:958:1: ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) )
            // InternalN4MFParser.g:959:2: ( rule__ProjectDescription__ProjectIdAssignment_0_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectIdAssignment_0_2()); 
            // InternalN4MFParser.g:960:2: ( rule__ProjectDescription__ProjectIdAssignment_0_2 )
            // InternalN4MFParser.g:960:3: rule__ProjectDescription__ProjectIdAssignment_0_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ProjectIdAssignment_0_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getProjectIdAssignment_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_0__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_1__0"
    // InternalN4MFParser.g:969:1: rule__ProjectDescription__Group_1__0 : rule__ProjectDescription__Group_1__0__Impl rule__ProjectDescription__Group_1__1 ;
    public final void rule__ProjectDescription__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:973:1: ( rule__ProjectDescription__Group_1__0__Impl rule__ProjectDescription__Group_1__1 )
            // InternalN4MFParser.g:974:2: rule__ProjectDescription__Group_1__0__Impl rule__ProjectDescription__Group_1__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_1__0"


    // $ANTLR start "rule__ProjectDescription__Group_1__0__Impl"
    // InternalN4MFParser.g:981:1: rule__ProjectDescription__Group_1__0__Impl : ( ProjectType ) ;
    public final void rule__ProjectDescription__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:985:1: ( ( ProjectType ) )
            // InternalN4MFParser.g:986:1: ( ProjectType )
            {
            // InternalN4MFParser.g:986:1: ( ProjectType )
            // InternalN4MFParser.g:987:2: ProjectType
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectTypeKeyword_1_0()); 
            match(input,ProjectType,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getProjectTypeKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_1__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_1__1"
    // InternalN4MFParser.g:996:1: rule__ProjectDescription__Group_1__1 : rule__ProjectDescription__Group_1__1__Impl rule__ProjectDescription__Group_1__2 ;
    public final void rule__ProjectDescription__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1000:1: ( rule__ProjectDescription__Group_1__1__Impl rule__ProjectDescription__Group_1__2 )
            // InternalN4MFParser.g:1001:2: rule__ProjectDescription__Group_1__1__Impl rule__ProjectDescription__Group_1__2
            {
            pushFollow(FOLLOW_5);
            rule__ProjectDescription__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_1__1"


    // $ANTLR start "rule__ProjectDescription__Group_1__1__Impl"
    // InternalN4MFParser.g:1008:1: rule__ProjectDescription__Group_1__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1012:1: ( ( Colon ) )
            // InternalN4MFParser.g:1013:1: ( Colon )
            {
            // InternalN4MFParser.g:1013:1: ( Colon )
            // InternalN4MFParser.g:1014:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_1_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_1__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_1__2"
    // InternalN4MFParser.g:1023:1: rule__ProjectDescription__Group_1__2 : rule__ProjectDescription__Group_1__2__Impl ;
    public final void rule__ProjectDescription__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1027:1: ( rule__ProjectDescription__Group_1__2__Impl )
            // InternalN4MFParser.g:1028:2: rule__ProjectDescription__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_1__2"


    // $ANTLR start "rule__ProjectDescription__Group_1__2__Impl"
    // InternalN4MFParser.g:1034:1: rule__ProjectDescription__Group_1__2__Impl : ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) ) ;
    public final void rule__ProjectDescription__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1038:1: ( ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) ) )
            // InternalN4MFParser.g:1039:1: ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) )
            {
            // InternalN4MFParser.g:1039:1: ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) )
            // InternalN4MFParser.g:1040:2: ( rule__ProjectDescription__ProjectTypeAssignment_1_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectTypeAssignment_1_2()); 
            // InternalN4MFParser.g:1041:2: ( rule__ProjectDescription__ProjectTypeAssignment_1_2 )
            // InternalN4MFParser.g:1041:3: rule__ProjectDescription__ProjectTypeAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ProjectTypeAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getProjectTypeAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_1__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_2__0"
    // InternalN4MFParser.g:1050:1: rule__ProjectDescription__Group_2__0 : rule__ProjectDescription__Group_2__0__Impl rule__ProjectDescription__Group_2__1 ;
    public final void rule__ProjectDescription__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1054:1: ( rule__ProjectDescription__Group_2__0__Impl rule__ProjectDescription__Group_2__1 )
            // InternalN4MFParser.g:1055:2: rule__ProjectDescription__Group_2__0__Impl rule__ProjectDescription__Group_2__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_2__0"


    // $ANTLR start "rule__ProjectDescription__Group_2__0__Impl"
    // InternalN4MFParser.g:1062:1: rule__ProjectDescription__Group_2__0__Impl : ( ProjectVersion ) ;
    public final void rule__ProjectDescription__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1066:1: ( ( ProjectVersion ) )
            // InternalN4MFParser.g:1067:1: ( ProjectVersion )
            {
            // InternalN4MFParser.g:1067:1: ( ProjectVersion )
            // InternalN4MFParser.g:1068:2: ProjectVersion
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectVersionKeyword_2_0()); 
            match(input,ProjectVersion,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getProjectVersionKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_2__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_2__1"
    // InternalN4MFParser.g:1077:1: rule__ProjectDescription__Group_2__1 : rule__ProjectDescription__Group_2__1__Impl rule__ProjectDescription__Group_2__2 ;
    public final void rule__ProjectDescription__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1081:1: ( rule__ProjectDescription__Group_2__1__Impl rule__ProjectDescription__Group_2__2 )
            // InternalN4MFParser.g:1082:2: rule__ProjectDescription__Group_2__1__Impl rule__ProjectDescription__Group_2__2
            {
            pushFollow(FOLLOW_6);
            rule__ProjectDescription__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_2__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_2__1"


    // $ANTLR start "rule__ProjectDescription__Group_2__1__Impl"
    // InternalN4MFParser.g:1089:1: rule__ProjectDescription__Group_2__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1093:1: ( ( Colon ) )
            // InternalN4MFParser.g:1094:1: ( Colon )
            {
            // InternalN4MFParser.g:1094:1: ( Colon )
            // InternalN4MFParser.g:1095:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_2_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_2__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_2__2"
    // InternalN4MFParser.g:1104:1: rule__ProjectDescription__Group_2__2 : rule__ProjectDescription__Group_2__2__Impl ;
    public final void rule__ProjectDescription__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1108:1: ( rule__ProjectDescription__Group_2__2__Impl )
            // InternalN4MFParser.g:1109:2: rule__ProjectDescription__Group_2__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_2__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_2__2"


    // $ANTLR start "rule__ProjectDescription__Group_2__2__Impl"
    // InternalN4MFParser.g:1115:1: rule__ProjectDescription__Group_2__2__Impl : ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) ) ;
    public final void rule__ProjectDescription__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1119:1: ( ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) ) )
            // InternalN4MFParser.g:1120:1: ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) )
            {
            // InternalN4MFParser.g:1120:1: ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) )
            // InternalN4MFParser.g:1121:2: ( rule__ProjectDescription__ProjectVersionAssignment_2_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectVersionAssignment_2_2()); 
            // InternalN4MFParser.g:1122:2: ( rule__ProjectDescription__ProjectVersionAssignment_2_2 )
            // InternalN4MFParser.g:1122:3: rule__ProjectDescription__ProjectVersionAssignment_2_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ProjectVersionAssignment_2_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getProjectVersionAssignment_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_2__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_3__0"
    // InternalN4MFParser.g:1131:1: rule__ProjectDescription__Group_3__0 : rule__ProjectDescription__Group_3__0__Impl rule__ProjectDescription__Group_3__1 ;
    public final void rule__ProjectDescription__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1135:1: ( rule__ProjectDescription__Group_3__0__Impl rule__ProjectDescription__Group_3__1 )
            // InternalN4MFParser.g:1136:2: rule__ProjectDescription__Group_3__0__Impl rule__ProjectDescription__Group_3__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_3__0"


    // $ANTLR start "rule__ProjectDescription__Group_3__0__Impl"
    // InternalN4MFParser.g:1143:1: rule__ProjectDescription__Group_3__0__Impl : ( VendorId ) ;
    public final void rule__ProjectDescription__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1147:1: ( ( VendorId ) )
            // InternalN4MFParser.g:1148:1: ( VendorId )
            {
            // InternalN4MFParser.g:1148:1: ( VendorId )
            // InternalN4MFParser.g:1149:2: VendorId
            {
             before(grammarAccess.getProjectDescriptionAccess().getVendorIdKeyword_3_0()); 
            match(input,VendorId,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getVendorIdKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_3__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_3__1"
    // InternalN4MFParser.g:1158:1: rule__ProjectDescription__Group_3__1 : rule__ProjectDescription__Group_3__1__Impl rule__ProjectDescription__Group_3__2 ;
    public final void rule__ProjectDescription__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1162:1: ( rule__ProjectDescription__Group_3__1__Impl rule__ProjectDescription__Group_3__2 )
            // InternalN4MFParser.g:1163:2: rule__ProjectDescription__Group_3__1__Impl rule__ProjectDescription__Group_3__2
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDescription__Group_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_3__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_3__1"


    // $ANTLR start "rule__ProjectDescription__Group_3__1__Impl"
    // InternalN4MFParser.g:1170:1: rule__ProjectDescription__Group_3__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1174:1: ( ( Colon ) )
            // InternalN4MFParser.g:1175:1: ( Colon )
            {
            // InternalN4MFParser.g:1175:1: ( Colon )
            // InternalN4MFParser.g:1176:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_3_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_3__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_3__2"
    // InternalN4MFParser.g:1185:1: rule__ProjectDescription__Group_3__2 : rule__ProjectDescription__Group_3__2__Impl ;
    public final void rule__ProjectDescription__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1189:1: ( rule__ProjectDescription__Group_3__2__Impl )
            // InternalN4MFParser.g:1190:2: rule__ProjectDescription__Group_3__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_3__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_3__2"


    // $ANTLR start "rule__ProjectDescription__Group_3__2__Impl"
    // InternalN4MFParser.g:1196:1: rule__ProjectDescription__Group_3__2__Impl : ( ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 ) ) ;
    public final void rule__ProjectDescription__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1200:1: ( ( ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 ) ) )
            // InternalN4MFParser.g:1201:1: ( ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 ) )
            {
            // InternalN4MFParser.g:1201:1: ( ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 ) )
            // InternalN4MFParser.g:1202:2: ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdAssignment_3_2()); 
            // InternalN4MFParser.g:1203:2: ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 )
            // InternalN4MFParser.g:1203:3: rule__ProjectDescription__DeclaredVendorIdAssignment_3_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__DeclaredVendorIdAssignment_3_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdAssignment_3_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_3__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_4__0"
    // InternalN4MFParser.g:1212:1: rule__ProjectDescription__Group_4__0 : rule__ProjectDescription__Group_4__0__Impl rule__ProjectDescription__Group_4__1 ;
    public final void rule__ProjectDescription__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1216:1: ( rule__ProjectDescription__Group_4__0__Impl rule__ProjectDescription__Group_4__1 )
            // InternalN4MFParser.g:1217:2: rule__ProjectDescription__Group_4__0__Impl rule__ProjectDescription__Group_4__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_4__0"


    // $ANTLR start "rule__ProjectDescription__Group_4__0__Impl"
    // InternalN4MFParser.g:1224:1: rule__ProjectDescription__Group_4__0__Impl : ( VendorName ) ;
    public final void rule__ProjectDescription__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1228:1: ( ( VendorName ) )
            // InternalN4MFParser.g:1229:1: ( VendorName )
            {
            // InternalN4MFParser.g:1229:1: ( VendorName )
            // InternalN4MFParser.g:1230:2: VendorName
            {
             before(grammarAccess.getProjectDescriptionAccess().getVendorNameKeyword_4_0()); 
            match(input,VendorName,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getVendorNameKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_4__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_4__1"
    // InternalN4MFParser.g:1239:1: rule__ProjectDescription__Group_4__1 : rule__ProjectDescription__Group_4__1__Impl rule__ProjectDescription__Group_4__2 ;
    public final void rule__ProjectDescription__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1243:1: ( rule__ProjectDescription__Group_4__1__Impl rule__ProjectDescription__Group_4__2 )
            // InternalN4MFParser.g:1244:2: rule__ProjectDescription__Group_4__1__Impl rule__ProjectDescription__Group_4__2
            {
            pushFollow(FOLLOW_7);
            rule__ProjectDescription__Group_4__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_4__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_4__1"


    // $ANTLR start "rule__ProjectDescription__Group_4__1__Impl"
    // InternalN4MFParser.g:1251:1: rule__ProjectDescription__Group_4__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1255:1: ( ( Colon ) )
            // InternalN4MFParser.g:1256:1: ( Colon )
            {
            // InternalN4MFParser.g:1256:1: ( Colon )
            // InternalN4MFParser.g:1257:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_4_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_4__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_4__2"
    // InternalN4MFParser.g:1266:1: rule__ProjectDescription__Group_4__2 : rule__ProjectDescription__Group_4__2__Impl ;
    public final void rule__ProjectDescription__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1270:1: ( rule__ProjectDescription__Group_4__2__Impl )
            // InternalN4MFParser.g:1271:2: rule__ProjectDescription__Group_4__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_4__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_4__2"


    // $ANTLR start "rule__ProjectDescription__Group_4__2__Impl"
    // InternalN4MFParser.g:1277:1: rule__ProjectDescription__Group_4__2__Impl : ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) ) ;
    public final void rule__ProjectDescription__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1281:1: ( ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) ) )
            // InternalN4MFParser.g:1282:1: ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) )
            {
            // InternalN4MFParser.g:1282:1: ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) )
            // InternalN4MFParser.g:1283:2: ( rule__ProjectDescription__VendorNameAssignment_4_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getVendorNameAssignment_4_2()); 
            // InternalN4MFParser.g:1284:2: ( rule__ProjectDescription__VendorNameAssignment_4_2 )
            // InternalN4MFParser.g:1284:3: rule__ProjectDescription__VendorNameAssignment_4_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__VendorNameAssignment_4_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getVendorNameAssignment_4_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_4__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_5__0"
    // InternalN4MFParser.g:1293:1: rule__ProjectDescription__Group_5__0 : rule__ProjectDescription__Group_5__0__Impl rule__ProjectDescription__Group_5__1 ;
    public final void rule__ProjectDescription__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1297:1: ( rule__ProjectDescription__Group_5__0__Impl rule__ProjectDescription__Group_5__1 )
            // InternalN4MFParser.g:1298:2: rule__ProjectDescription__Group_5__0__Impl rule__ProjectDescription__Group_5__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_5__0"


    // $ANTLR start "rule__ProjectDescription__Group_5__0__Impl"
    // InternalN4MFParser.g:1305:1: rule__ProjectDescription__Group_5__0__Impl : ( MainModule ) ;
    public final void rule__ProjectDescription__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1309:1: ( ( MainModule ) )
            // InternalN4MFParser.g:1310:1: ( MainModule )
            {
            // InternalN4MFParser.g:1310:1: ( MainModule )
            // InternalN4MFParser.g:1311:2: MainModule
            {
             before(grammarAccess.getProjectDescriptionAccess().getMainModuleKeyword_5_0()); 
            match(input,MainModule,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getMainModuleKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_5__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_5__1"
    // InternalN4MFParser.g:1320:1: rule__ProjectDescription__Group_5__1 : rule__ProjectDescription__Group_5__1__Impl rule__ProjectDescription__Group_5__2 ;
    public final void rule__ProjectDescription__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1324:1: ( rule__ProjectDescription__Group_5__1__Impl rule__ProjectDescription__Group_5__2 )
            // InternalN4MFParser.g:1325:2: rule__ProjectDescription__Group_5__1__Impl rule__ProjectDescription__Group_5__2
            {
            pushFollow(FOLLOW_7);
            rule__ProjectDescription__Group_5__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_5__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_5__1"


    // $ANTLR start "rule__ProjectDescription__Group_5__1__Impl"
    // InternalN4MFParser.g:1332:1: rule__ProjectDescription__Group_5__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1336:1: ( ( Colon ) )
            // InternalN4MFParser.g:1337:1: ( Colon )
            {
            // InternalN4MFParser.g:1337:1: ( Colon )
            // InternalN4MFParser.g:1338:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_5_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_5__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_5__2"
    // InternalN4MFParser.g:1347:1: rule__ProjectDescription__Group_5__2 : rule__ProjectDescription__Group_5__2__Impl ;
    public final void rule__ProjectDescription__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1351:1: ( rule__ProjectDescription__Group_5__2__Impl )
            // InternalN4MFParser.g:1352:2: rule__ProjectDescription__Group_5__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_5__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_5__2"


    // $ANTLR start "rule__ProjectDescription__Group_5__2__Impl"
    // InternalN4MFParser.g:1358:1: rule__ProjectDescription__Group_5__2__Impl : ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) ) ;
    public final void rule__ProjectDescription__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1362:1: ( ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) ) )
            // InternalN4MFParser.g:1363:1: ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) )
            {
            // InternalN4MFParser.g:1363:1: ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) )
            // InternalN4MFParser.g:1364:2: ( rule__ProjectDescription__MainModuleAssignment_5_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getMainModuleAssignment_5_2()); 
            // InternalN4MFParser.g:1365:2: ( rule__ProjectDescription__MainModuleAssignment_5_2 )
            // InternalN4MFParser.g:1365:3: rule__ProjectDescription__MainModuleAssignment_5_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__MainModuleAssignment_5_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getMainModuleAssignment_5_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_5__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_6__0"
    // InternalN4MFParser.g:1374:1: rule__ProjectDescription__Group_6__0 : rule__ProjectDescription__Group_6__0__Impl rule__ProjectDescription__Group_6__1 ;
    public final void rule__ProjectDescription__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1378:1: ( rule__ProjectDescription__Group_6__0__Impl rule__ProjectDescription__Group_6__1 )
            // InternalN4MFParser.g:1379:2: rule__ProjectDescription__Group_6__0__Impl rule__ProjectDescription__Group_6__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_6__0"


    // $ANTLR start "rule__ProjectDescription__Group_6__0__Impl"
    // InternalN4MFParser.g:1386:1: rule__ProjectDescription__Group_6__0__Impl : ( ExtendedRuntimeEnvironment ) ;
    public final void rule__ProjectDescription__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1390:1: ( ( ExtendedRuntimeEnvironment ) )
            // InternalN4MFParser.g:1391:1: ( ExtendedRuntimeEnvironment )
            {
            // InternalN4MFParser.g:1391:1: ( ExtendedRuntimeEnvironment )
            // InternalN4MFParser.g:1392:2: ExtendedRuntimeEnvironment
            {
             before(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentKeyword_6_0()); 
            match(input,ExtendedRuntimeEnvironment,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_6__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_6__1"
    // InternalN4MFParser.g:1401:1: rule__ProjectDescription__Group_6__1 : rule__ProjectDescription__Group_6__1__Impl rule__ProjectDescription__Group_6__2 ;
    public final void rule__ProjectDescription__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1405:1: ( rule__ProjectDescription__Group_6__1__Impl rule__ProjectDescription__Group_6__2 )
            // InternalN4MFParser.g:1406:2: rule__ProjectDescription__Group_6__1__Impl rule__ProjectDescription__Group_6__2
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDescription__Group_6__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_6__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_6__1"


    // $ANTLR start "rule__ProjectDescription__Group_6__1__Impl"
    // InternalN4MFParser.g:1413:1: rule__ProjectDescription__Group_6__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1417:1: ( ( Colon ) )
            // InternalN4MFParser.g:1418:1: ( Colon )
            {
            // InternalN4MFParser.g:1418:1: ( Colon )
            // InternalN4MFParser.g:1419:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_6_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_6__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_6__2"
    // InternalN4MFParser.g:1428:1: rule__ProjectDescription__Group_6__2 : rule__ProjectDescription__Group_6__2__Impl ;
    public final void rule__ProjectDescription__Group_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1432:1: ( rule__ProjectDescription__Group_6__2__Impl )
            // InternalN4MFParser.g:1433:2: rule__ProjectDescription__Group_6__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_6__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_6__2"


    // $ANTLR start "rule__ProjectDescription__Group_6__2__Impl"
    // InternalN4MFParser.g:1439:1: rule__ProjectDescription__Group_6__2__Impl : ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 ) ) ;
    public final void rule__ProjectDescription__Group_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1443:1: ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 ) ) )
            // InternalN4MFParser.g:1444:1: ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 ) )
            {
            // InternalN4MFParser.g:1444:1: ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 ) )
            // InternalN4MFParser.g:1445:2: ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_6_2()); 
            // InternalN4MFParser.g:1446:2: ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 )
            // InternalN4MFParser.g:1446:3: rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_6_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_6__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_7__0"
    // InternalN4MFParser.g:1455:1: rule__ProjectDescription__Group_7__0 : rule__ProjectDescription__Group_7__0__Impl rule__ProjectDescription__Group_7__1 ;
    public final void rule__ProjectDescription__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1459:1: ( rule__ProjectDescription__Group_7__0__Impl rule__ProjectDescription__Group_7__1 )
            // InternalN4MFParser.g:1460:2: rule__ProjectDescription__Group_7__0__Impl rule__ProjectDescription__Group_7__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7__0"


    // $ANTLR start "rule__ProjectDescription__Group_7__0__Impl"
    // InternalN4MFParser.g:1467:1: rule__ProjectDescription__Group_7__0__Impl : ( ProvidedRuntimeLibraries ) ;
    public final void rule__ProjectDescription__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1471:1: ( ( ProvidedRuntimeLibraries ) )
            // InternalN4MFParser.g:1472:1: ( ProvidedRuntimeLibraries )
            {
            // InternalN4MFParser.g:1472:1: ( ProvidedRuntimeLibraries )
            // InternalN4MFParser.g:1473:2: ProvidedRuntimeLibraries
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesKeyword_7_0()); 
            match(input,ProvidedRuntimeLibraries,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_7__1"
    // InternalN4MFParser.g:1482:1: rule__ProjectDescription__Group_7__1 : rule__ProjectDescription__Group_7__1__Impl rule__ProjectDescription__Group_7__2 ;
    public final void rule__ProjectDescription__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1486:1: ( rule__ProjectDescription__Group_7__1__Impl rule__ProjectDescription__Group_7__2 )
            // InternalN4MFParser.g:1487:2: rule__ProjectDescription__Group_7__1__Impl rule__ProjectDescription__Group_7__2
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_7__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_7__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7__1"


    // $ANTLR start "rule__ProjectDescription__Group_7__1__Impl"
    // InternalN4MFParser.g:1494:1: rule__ProjectDescription__Group_7__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1498:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:1499:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:1499:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:1500:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_7_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_7__2"
    // InternalN4MFParser.g:1509:1: rule__ProjectDescription__Group_7__2 : rule__ProjectDescription__Group_7__2__Impl rule__ProjectDescription__Group_7__3 ;
    public final void rule__ProjectDescription__Group_7__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1513:1: ( rule__ProjectDescription__Group_7__2__Impl rule__ProjectDescription__Group_7__3 )
            // InternalN4MFParser.g:1514:2: rule__ProjectDescription__Group_7__2__Impl rule__ProjectDescription__Group_7__3
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_7__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_7__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7__2"


    // $ANTLR start "rule__ProjectDescription__Group_7__2__Impl"
    // InternalN4MFParser.g:1521:1: rule__ProjectDescription__Group_7__2__Impl : ( ( rule__ProjectDescription__Group_7_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_7__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1525:1: ( ( ( rule__ProjectDescription__Group_7_2__0 )? ) )
            // InternalN4MFParser.g:1526:1: ( ( rule__ProjectDescription__Group_7_2__0 )? )
            {
            // InternalN4MFParser.g:1526:1: ( ( rule__ProjectDescription__Group_7_2__0 )? )
            // InternalN4MFParser.g:1527:2: ( rule__ProjectDescription__Group_7_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_7_2()); 
            // InternalN4MFParser.g:1528:2: ( rule__ProjectDescription__Group_7_2__0 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ProjectDependencies||LA12_0==ProjectVersion||LA12_0==ModuleFilters||(LA12_0>=ProjectType && LA12_0<=Application)||LA12_0==VendorName||(LA12_0>=Libraries && LA12_0<=VendorId)||LA12_0==Sources||LA12_0==Content||LA12_0==Output||(LA12_0>=Test && LA12_0<=API)||LA12_0==RULE_ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalN4MFParser.g:1528:3: rule__ProjectDescription__Group_7_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_7_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectDescriptionAccess().getGroup_7_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_7__3"
    // InternalN4MFParser.g:1536:1: rule__ProjectDescription__Group_7__3 : rule__ProjectDescription__Group_7__3__Impl ;
    public final void rule__ProjectDescription__Group_7__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1540:1: ( rule__ProjectDescription__Group_7__3__Impl )
            // InternalN4MFParser.g:1541:2: rule__ProjectDescription__Group_7__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_7__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7__3"


    // $ANTLR start "rule__ProjectDescription__Group_7__3__Impl"
    // InternalN4MFParser.g:1547:1: rule__ProjectDescription__Group_7__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_7__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1551:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:1552:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:1552:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:1553:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_7_3()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_7_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_7_2__0"
    // InternalN4MFParser.g:1563:1: rule__ProjectDescription__Group_7_2__0 : rule__ProjectDescription__Group_7_2__0__Impl rule__ProjectDescription__Group_7_2__1 ;
    public final void rule__ProjectDescription__Group_7_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1567:1: ( rule__ProjectDescription__Group_7_2__0__Impl rule__ProjectDescription__Group_7_2__1 )
            // InternalN4MFParser.g:1568:2: rule__ProjectDescription__Group_7_2__0__Impl rule__ProjectDescription__Group_7_2__1
            {
            pushFollow(FOLLOW_10);
            rule__ProjectDescription__Group_7_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_7_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7_2__0"


    // $ANTLR start "rule__ProjectDescription__Group_7_2__0__Impl"
    // InternalN4MFParser.g:1575:1: rule__ProjectDescription__Group_7_2__0__Impl : ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_7_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1579:1: ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 ) ) )
            // InternalN4MFParser.g:1580:1: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 ) )
            {
            // InternalN4MFParser.g:1580:1: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 ) )
            // InternalN4MFParser.g:1581:2: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7_2_0()); 
            // InternalN4MFParser.g:1582:2: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 )
            // InternalN4MFParser.g:1582:3: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7_2__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_7_2__1"
    // InternalN4MFParser.g:1590:1: rule__ProjectDescription__Group_7_2__1 : rule__ProjectDescription__Group_7_2__1__Impl ;
    public final void rule__ProjectDescription__Group_7_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1594:1: ( rule__ProjectDescription__Group_7_2__1__Impl )
            // InternalN4MFParser.g:1595:2: rule__ProjectDescription__Group_7_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_7_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7_2__1"


    // $ANTLR start "rule__ProjectDescription__Group_7_2__1__Impl"
    // InternalN4MFParser.g:1601:1: rule__ProjectDescription__Group_7_2__1__Impl : ( ( rule__ProjectDescription__Group_7_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_7_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1605:1: ( ( ( rule__ProjectDescription__Group_7_2_1__0 )* ) )
            // InternalN4MFParser.g:1606:1: ( ( rule__ProjectDescription__Group_7_2_1__0 )* )
            {
            // InternalN4MFParser.g:1606:1: ( ( rule__ProjectDescription__Group_7_2_1__0 )* )
            // InternalN4MFParser.g:1607:2: ( rule__ProjectDescription__Group_7_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_7_2_1()); 
            // InternalN4MFParser.g:1608:2: ( rule__ProjectDescription__Group_7_2_1__0 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==Comma) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalN4MFParser.g:1608:3: rule__ProjectDescription__Group_7_2_1__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ProjectDescription__Group_7_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getGroup_7_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7_2__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_7_2_1__0"
    // InternalN4MFParser.g:1617:1: rule__ProjectDescription__Group_7_2_1__0 : rule__ProjectDescription__Group_7_2_1__0__Impl rule__ProjectDescription__Group_7_2_1__1 ;
    public final void rule__ProjectDescription__Group_7_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1621:1: ( rule__ProjectDescription__Group_7_2_1__0__Impl rule__ProjectDescription__Group_7_2_1__1 )
            // InternalN4MFParser.g:1622:2: rule__ProjectDescription__Group_7_2_1__0__Impl rule__ProjectDescription__Group_7_2_1__1
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDescription__Group_7_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_7_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7_2_1__0"


    // $ANTLR start "rule__ProjectDescription__Group_7_2_1__0__Impl"
    // InternalN4MFParser.g:1629:1: rule__ProjectDescription__Group_7_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_7_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1633:1: ( ( Comma ) )
            // InternalN4MFParser.g:1634:1: ( Comma )
            {
            // InternalN4MFParser.g:1634:1: ( Comma )
            // InternalN4MFParser.g:1635:2: Comma
            {
             before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_7_2_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_7_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7_2_1__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_7_2_1__1"
    // InternalN4MFParser.g:1644:1: rule__ProjectDescription__Group_7_2_1__1 : rule__ProjectDescription__Group_7_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_7_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1648:1: ( rule__ProjectDescription__Group_7_2_1__1__Impl )
            // InternalN4MFParser.g:1649:2: rule__ProjectDescription__Group_7_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_7_2_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7_2_1__1"


    // $ANTLR start "rule__ProjectDescription__Group_7_2_1__1__Impl"
    // InternalN4MFParser.g:1655:1: rule__ProjectDescription__Group_7_2_1__1__Impl : ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_7_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1659:1: ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 ) ) )
            // InternalN4MFParser.g:1660:1: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 ) )
            {
            // InternalN4MFParser.g:1660:1: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 ) )
            // InternalN4MFParser.g:1661:2: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7_2_1_1()); 
            // InternalN4MFParser.g:1662:2: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 )
            // InternalN4MFParser.g:1662:3: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_7_2_1__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_8__0"
    // InternalN4MFParser.g:1671:1: rule__ProjectDescription__Group_8__0 : rule__ProjectDescription__Group_8__0__Impl rule__ProjectDescription__Group_8__1 ;
    public final void rule__ProjectDescription__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1675:1: ( rule__ProjectDescription__Group_8__0__Impl rule__ProjectDescription__Group_8__1 )
            // InternalN4MFParser.g:1676:2: rule__ProjectDescription__Group_8__0__Impl rule__ProjectDescription__Group_8__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8__0"


    // $ANTLR start "rule__ProjectDescription__Group_8__0__Impl"
    // InternalN4MFParser.g:1683:1: rule__ProjectDescription__Group_8__0__Impl : ( RequiredRuntimeLibraries ) ;
    public final void rule__ProjectDescription__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1687:1: ( ( RequiredRuntimeLibraries ) )
            // InternalN4MFParser.g:1688:1: ( RequiredRuntimeLibraries )
            {
            // InternalN4MFParser.g:1688:1: ( RequiredRuntimeLibraries )
            // InternalN4MFParser.g:1689:2: RequiredRuntimeLibraries
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesKeyword_8_0()); 
            match(input,RequiredRuntimeLibraries,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_8__1"
    // InternalN4MFParser.g:1698:1: rule__ProjectDescription__Group_8__1 : rule__ProjectDescription__Group_8__1__Impl rule__ProjectDescription__Group_8__2 ;
    public final void rule__ProjectDescription__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1702:1: ( rule__ProjectDescription__Group_8__1__Impl rule__ProjectDescription__Group_8__2 )
            // InternalN4MFParser.g:1703:2: rule__ProjectDescription__Group_8__1__Impl rule__ProjectDescription__Group_8__2
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_8__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_8__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8__1"


    // $ANTLR start "rule__ProjectDescription__Group_8__1__Impl"
    // InternalN4MFParser.g:1710:1: rule__ProjectDescription__Group_8__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1714:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:1715:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:1715:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:1716:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_8_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_8__2"
    // InternalN4MFParser.g:1725:1: rule__ProjectDescription__Group_8__2 : rule__ProjectDescription__Group_8__2__Impl rule__ProjectDescription__Group_8__3 ;
    public final void rule__ProjectDescription__Group_8__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1729:1: ( rule__ProjectDescription__Group_8__2__Impl rule__ProjectDescription__Group_8__3 )
            // InternalN4MFParser.g:1730:2: rule__ProjectDescription__Group_8__2__Impl rule__ProjectDescription__Group_8__3
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_8__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_8__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8__2"


    // $ANTLR start "rule__ProjectDescription__Group_8__2__Impl"
    // InternalN4MFParser.g:1737:1: rule__ProjectDescription__Group_8__2__Impl : ( ( rule__ProjectDescription__Group_8_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_8__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1741:1: ( ( ( rule__ProjectDescription__Group_8_2__0 )? ) )
            // InternalN4MFParser.g:1742:1: ( ( rule__ProjectDescription__Group_8_2__0 )? )
            {
            // InternalN4MFParser.g:1742:1: ( ( rule__ProjectDescription__Group_8_2__0 )? )
            // InternalN4MFParser.g:1743:2: ( rule__ProjectDescription__Group_8_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_8_2()); 
            // InternalN4MFParser.g:1744:2: ( rule__ProjectDescription__Group_8_2__0 )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ProjectDependencies||LA14_0==ProjectVersion||LA14_0==ModuleFilters||(LA14_0>=ProjectType && LA14_0<=Application)||LA14_0==VendorName||(LA14_0>=Libraries && LA14_0<=VendorId)||LA14_0==Sources||LA14_0==Content||LA14_0==Output||(LA14_0>=Test && LA14_0<=API)||LA14_0==RULE_ID) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalN4MFParser.g:1744:3: rule__ProjectDescription__Group_8_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_8_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectDescriptionAccess().getGroup_8_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_8__3"
    // InternalN4MFParser.g:1752:1: rule__ProjectDescription__Group_8__3 : rule__ProjectDescription__Group_8__3__Impl ;
    public final void rule__ProjectDescription__Group_8__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1756:1: ( rule__ProjectDescription__Group_8__3__Impl )
            // InternalN4MFParser.g:1757:2: rule__ProjectDescription__Group_8__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_8__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8__3"


    // $ANTLR start "rule__ProjectDescription__Group_8__3__Impl"
    // InternalN4MFParser.g:1763:1: rule__ProjectDescription__Group_8__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_8__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1767:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:1768:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:1768:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:1769:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_8_3()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_8_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_8_2__0"
    // InternalN4MFParser.g:1779:1: rule__ProjectDescription__Group_8_2__0 : rule__ProjectDescription__Group_8_2__0__Impl rule__ProjectDescription__Group_8_2__1 ;
    public final void rule__ProjectDescription__Group_8_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1783:1: ( rule__ProjectDescription__Group_8_2__0__Impl rule__ProjectDescription__Group_8_2__1 )
            // InternalN4MFParser.g:1784:2: rule__ProjectDescription__Group_8_2__0__Impl rule__ProjectDescription__Group_8_2__1
            {
            pushFollow(FOLLOW_10);
            rule__ProjectDescription__Group_8_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_8_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8_2__0"


    // $ANTLR start "rule__ProjectDescription__Group_8_2__0__Impl"
    // InternalN4MFParser.g:1791:1: rule__ProjectDescription__Group_8_2__0__Impl : ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_8_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1795:1: ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 ) ) )
            // InternalN4MFParser.g:1796:1: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 ) )
            {
            // InternalN4MFParser.g:1796:1: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 ) )
            // InternalN4MFParser.g:1797:2: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8_2_0()); 
            // InternalN4MFParser.g:1798:2: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 )
            // InternalN4MFParser.g:1798:3: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8_2__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_8_2__1"
    // InternalN4MFParser.g:1806:1: rule__ProjectDescription__Group_8_2__1 : rule__ProjectDescription__Group_8_2__1__Impl ;
    public final void rule__ProjectDescription__Group_8_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1810:1: ( rule__ProjectDescription__Group_8_2__1__Impl )
            // InternalN4MFParser.g:1811:2: rule__ProjectDescription__Group_8_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_8_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8_2__1"


    // $ANTLR start "rule__ProjectDescription__Group_8_2__1__Impl"
    // InternalN4MFParser.g:1817:1: rule__ProjectDescription__Group_8_2__1__Impl : ( ( rule__ProjectDescription__Group_8_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_8_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1821:1: ( ( ( rule__ProjectDescription__Group_8_2_1__0 )* ) )
            // InternalN4MFParser.g:1822:1: ( ( rule__ProjectDescription__Group_8_2_1__0 )* )
            {
            // InternalN4MFParser.g:1822:1: ( ( rule__ProjectDescription__Group_8_2_1__0 )* )
            // InternalN4MFParser.g:1823:2: ( rule__ProjectDescription__Group_8_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_8_2_1()); 
            // InternalN4MFParser.g:1824:2: ( rule__ProjectDescription__Group_8_2_1__0 )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==Comma) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalN4MFParser.g:1824:3: rule__ProjectDescription__Group_8_2_1__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ProjectDescription__Group_8_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getGroup_8_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8_2__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_8_2_1__0"
    // InternalN4MFParser.g:1833:1: rule__ProjectDescription__Group_8_2_1__0 : rule__ProjectDescription__Group_8_2_1__0__Impl rule__ProjectDescription__Group_8_2_1__1 ;
    public final void rule__ProjectDescription__Group_8_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1837:1: ( rule__ProjectDescription__Group_8_2_1__0__Impl rule__ProjectDescription__Group_8_2_1__1 )
            // InternalN4MFParser.g:1838:2: rule__ProjectDescription__Group_8_2_1__0__Impl rule__ProjectDescription__Group_8_2_1__1
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDescription__Group_8_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_8_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8_2_1__0"


    // $ANTLR start "rule__ProjectDescription__Group_8_2_1__0__Impl"
    // InternalN4MFParser.g:1845:1: rule__ProjectDescription__Group_8_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_8_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1849:1: ( ( Comma ) )
            // InternalN4MFParser.g:1850:1: ( Comma )
            {
            // InternalN4MFParser.g:1850:1: ( Comma )
            // InternalN4MFParser.g:1851:2: Comma
            {
             before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_8_2_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_8_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8_2_1__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_8_2_1__1"
    // InternalN4MFParser.g:1860:1: rule__ProjectDescription__Group_8_2_1__1 : rule__ProjectDescription__Group_8_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_8_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1864:1: ( rule__ProjectDescription__Group_8_2_1__1__Impl )
            // InternalN4MFParser.g:1865:2: rule__ProjectDescription__Group_8_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_8_2_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8_2_1__1"


    // $ANTLR start "rule__ProjectDescription__Group_8_2_1__1__Impl"
    // InternalN4MFParser.g:1871:1: rule__ProjectDescription__Group_8_2_1__1__Impl : ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_8_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1875:1: ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 ) ) )
            // InternalN4MFParser.g:1876:1: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 ) )
            {
            // InternalN4MFParser.g:1876:1: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 ) )
            // InternalN4MFParser.g:1877:2: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8_2_1_1()); 
            // InternalN4MFParser.g:1878:2: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 )
            // InternalN4MFParser.g:1878:3: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_8_2_1__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_9__0"
    // InternalN4MFParser.g:1887:1: rule__ProjectDescription__Group_9__0 : rule__ProjectDescription__Group_9__0__Impl rule__ProjectDescription__Group_9__1 ;
    public final void rule__ProjectDescription__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1891:1: ( rule__ProjectDescription__Group_9__0__Impl rule__ProjectDescription__Group_9__1 )
            // InternalN4MFParser.g:1892:2: rule__ProjectDescription__Group_9__0__Impl rule__ProjectDescription__Group_9__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_9__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_9__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9__0"


    // $ANTLR start "rule__ProjectDescription__Group_9__0__Impl"
    // InternalN4MFParser.g:1899:1: rule__ProjectDescription__Group_9__0__Impl : ( ProjectDependencies ) ;
    public final void rule__ProjectDescription__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1903:1: ( ( ProjectDependencies ) )
            // InternalN4MFParser.g:1904:1: ( ProjectDependencies )
            {
            // InternalN4MFParser.g:1904:1: ( ProjectDependencies )
            // InternalN4MFParser.g:1905:2: ProjectDependencies
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesKeyword_9_0()); 
            match(input,ProjectDependencies,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesKeyword_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_9__1"
    // InternalN4MFParser.g:1914:1: rule__ProjectDescription__Group_9__1 : rule__ProjectDescription__Group_9__1__Impl rule__ProjectDescription__Group_9__2 ;
    public final void rule__ProjectDescription__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1918:1: ( rule__ProjectDescription__Group_9__1__Impl rule__ProjectDescription__Group_9__2 )
            // InternalN4MFParser.g:1919:2: rule__ProjectDescription__Group_9__1__Impl rule__ProjectDescription__Group_9__2
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_9__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_9__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9__1"


    // $ANTLR start "rule__ProjectDescription__Group_9__1__Impl"
    // InternalN4MFParser.g:1926:1: rule__ProjectDescription__Group_9__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1930:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:1931:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:1931:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:1932:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_9_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_9_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_9__2"
    // InternalN4MFParser.g:1941:1: rule__ProjectDescription__Group_9__2 : rule__ProjectDescription__Group_9__2__Impl rule__ProjectDescription__Group_9__3 ;
    public final void rule__ProjectDescription__Group_9__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1945:1: ( rule__ProjectDescription__Group_9__2__Impl rule__ProjectDescription__Group_9__3 )
            // InternalN4MFParser.g:1946:2: rule__ProjectDescription__Group_9__2__Impl rule__ProjectDescription__Group_9__3
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_9__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_9__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9__2"


    // $ANTLR start "rule__ProjectDescription__Group_9__2__Impl"
    // InternalN4MFParser.g:1953:1: rule__ProjectDescription__Group_9__2__Impl : ( ( rule__ProjectDescription__Group_9_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_9__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1957:1: ( ( ( rule__ProjectDescription__Group_9_2__0 )? ) )
            // InternalN4MFParser.g:1958:1: ( ( rule__ProjectDescription__Group_9_2__0 )? )
            {
            // InternalN4MFParser.g:1958:1: ( ( rule__ProjectDescription__Group_9_2__0 )? )
            // InternalN4MFParser.g:1959:2: ( rule__ProjectDescription__Group_9_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_9_2()); 
            // InternalN4MFParser.g:1960:2: ( rule__ProjectDescription__Group_9_2__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ProjectDependencies||LA16_0==ProjectVersion||LA16_0==ModuleFilters||(LA16_0>=ProjectType && LA16_0<=Application)||LA16_0==VendorName||(LA16_0>=Libraries && LA16_0<=VendorId)||LA16_0==Sources||LA16_0==Content||LA16_0==Output||(LA16_0>=Test && LA16_0<=API)||LA16_0==RULE_ID) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalN4MFParser.g:1960:3: rule__ProjectDescription__Group_9_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_9_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectDescriptionAccess().getGroup_9_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_9__3"
    // InternalN4MFParser.g:1968:1: rule__ProjectDescription__Group_9__3 : rule__ProjectDescription__Group_9__3__Impl ;
    public final void rule__ProjectDescription__Group_9__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1972:1: ( rule__ProjectDescription__Group_9__3__Impl )
            // InternalN4MFParser.g:1973:2: rule__ProjectDescription__Group_9__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_9__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9__3"


    // $ANTLR start "rule__ProjectDescription__Group_9__3__Impl"
    // InternalN4MFParser.g:1979:1: rule__ProjectDescription__Group_9__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_9__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1983:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:1984:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:1984:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:1985:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_9_3()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_9_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_9_2__0"
    // InternalN4MFParser.g:1995:1: rule__ProjectDescription__Group_9_2__0 : rule__ProjectDescription__Group_9_2__0__Impl rule__ProjectDescription__Group_9_2__1 ;
    public final void rule__ProjectDescription__Group_9_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1999:1: ( rule__ProjectDescription__Group_9_2__0__Impl rule__ProjectDescription__Group_9_2__1 )
            // InternalN4MFParser.g:2000:2: rule__ProjectDescription__Group_9_2__0__Impl rule__ProjectDescription__Group_9_2__1
            {
            pushFollow(FOLLOW_10);
            rule__ProjectDescription__Group_9_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_9_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9_2__0"


    // $ANTLR start "rule__ProjectDescription__Group_9_2__0__Impl"
    // InternalN4MFParser.g:2007:1: rule__ProjectDescription__Group_9_2__0__Impl : ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_9_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2011:1: ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 ) ) )
            // InternalN4MFParser.g:2012:1: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 ) )
            {
            // InternalN4MFParser.g:2012:1: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 ) )
            // InternalN4MFParser.g:2013:2: ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9_2_0()); 
            // InternalN4MFParser.g:2014:2: ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 )
            // InternalN4MFParser.g:2014:3: rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9_2__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_9_2__1"
    // InternalN4MFParser.g:2022:1: rule__ProjectDescription__Group_9_2__1 : rule__ProjectDescription__Group_9_2__1__Impl ;
    public final void rule__ProjectDescription__Group_9_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2026:1: ( rule__ProjectDescription__Group_9_2__1__Impl )
            // InternalN4MFParser.g:2027:2: rule__ProjectDescription__Group_9_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_9_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9_2__1"


    // $ANTLR start "rule__ProjectDescription__Group_9_2__1__Impl"
    // InternalN4MFParser.g:2033:1: rule__ProjectDescription__Group_9_2__1__Impl : ( ( rule__ProjectDescription__Group_9_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_9_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2037:1: ( ( ( rule__ProjectDescription__Group_9_2_1__0 )* ) )
            // InternalN4MFParser.g:2038:1: ( ( rule__ProjectDescription__Group_9_2_1__0 )* )
            {
            // InternalN4MFParser.g:2038:1: ( ( rule__ProjectDescription__Group_9_2_1__0 )* )
            // InternalN4MFParser.g:2039:2: ( rule__ProjectDescription__Group_9_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_9_2_1()); 
            // InternalN4MFParser.g:2040:2: ( rule__ProjectDescription__Group_9_2_1__0 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==Comma) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalN4MFParser.g:2040:3: rule__ProjectDescription__Group_9_2_1__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ProjectDescription__Group_9_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getGroup_9_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9_2__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_9_2_1__0"
    // InternalN4MFParser.g:2049:1: rule__ProjectDescription__Group_9_2_1__0 : rule__ProjectDescription__Group_9_2_1__0__Impl rule__ProjectDescription__Group_9_2_1__1 ;
    public final void rule__ProjectDescription__Group_9_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2053:1: ( rule__ProjectDescription__Group_9_2_1__0__Impl rule__ProjectDescription__Group_9_2_1__1 )
            // InternalN4MFParser.g:2054:2: rule__ProjectDescription__Group_9_2_1__0__Impl rule__ProjectDescription__Group_9_2_1__1
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDescription__Group_9_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_9_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9_2_1__0"


    // $ANTLR start "rule__ProjectDescription__Group_9_2_1__0__Impl"
    // InternalN4MFParser.g:2061:1: rule__ProjectDescription__Group_9_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_9_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2065:1: ( ( Comma ) )
            // InternalN4MFParser.g:2066:1: ( Comma )
            {
            // InternalN4MFParser.g:2066:1: ( Comma )
            // InternalN4MFParser.g:2067:2: Comma
            {
             before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_9_2_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_9_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9_2_1__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_9_2_1__1"
    // InternalN4MFParser.g:2076:1: rule__ProjectDescription__Group_9_2_1__1 : rule__ProjectDescription__Group_9_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_9_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2080:1: ( rule__ProjectDescription__Group_9_2_1__1__Impl )
            // InternalN4MFParser.g:2081:2: rule__ProjectDescription__Group_9_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_9_2_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9_2_1__1"


    // $ANTLR start "rule__ProjectDescription__Group_9_2_1__1__Impl"
    // InternalN4MFParser.g:2087:1: rule__ProjectDescription__Group_9_2_1__1__Impl : ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_9_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2091:1: ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 ) ) )
            // InternalN4MFParser.g:2092:1: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 ) )
            {
            // InternalN4MFParser.g:2092:1: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 ) )
            // InternalN4MFParser.g:2093:2: ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9_2_1_1()); 
            // InternalN4MFParser.g:2094:2: ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 )
            // InternalN4MFParser.g:2094:3: rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_9_2_1__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_10__0"
    // InternalN4MFParser.g:2103:1: rule__ProjectDescription__Group_10__0 : rule__ProjectDescription__Group_10__0__Impl rule__ProjectDescription__Group_10__1 ;
    public final void rule__ProjectDescription__Group_10__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2107:1: ( rule__ProjectDescription__Group_10__0__Impl rule__ProjectDescription__Group_10__1 )
            // InternalN4MFParser.g:2108:2: rule__ProjectDescription__Group_10__0__Impl rule__ProjectDescription__Group_10__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_10__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_10__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_10__0"


    // $ANTLR start "rule__ProjectDescription__Group_10__0__Impl"
    // InternalN4MFParser.g:2115:1: rule__ProjectDescription__Group_10__0__Impl : ( ImplementationId ) ;
    public final void rule__ProjectDescription__Group_10__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2119:1: ( ( ImplementationId ) )
            // InternalN4MFParser.g:2120:1: ( ImplementationId )
            {
            // InternalN4MFParser.g:2120:1: ( ImplementationId )
            // InternalN4MFParser.g:2121:2: ImplementationId
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementationIdKeyword_10_0()); 
            match(input,ImplementationId,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getImplementationIdKeyword_10_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_10__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_10__1"
    // InternalN4MFParser.g:2130:1: rule__ProjectDescription__Group_10__1 : rule__ProjectDescription__Group_10__1__Impl rule__ProjectDescription__Group_10__2 ;
    public final void rule__ProjectDescription__Group_10__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2134:1: ( rule__ProjectDescription__Group_10__1__Impl rule__ProjectDescription__Group_10__2 )
            // InternalN4MFParser.g:2135:2: rule__ProjectDescription__Group_10__1__Impl rule__ProjectDescription__Group_10__2
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDescription__Group_10__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_10__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_10__1"


    // $ANTLR start "rule__ProjectDescription__Group_10__1__Impl"
    // InternalN4MFParser.g:2142:1: rule__ProjectDescription__Group_10__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_10__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2146:1: ( ( Colon ) )
            // InternalN4MFParser.g:2147:1: ( Colon )
            {
            // InternalN4MFParser.g:2147:1: ( Colon )
            // InternalN4MFParser.g:2148:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_10_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_10_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_10__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_10__2"
    // InternalN4MFParser.g:2157:1: rule__ProjectDescription__Group_10__2 : rule__ProjectDescription__Group_10__2__Impl ;
    public final void rule__ProjectDescription__Group_10__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2161:1: ( rule__ProjectDescription__Group_10__2__Impl )
            // InternalN4MFParser.g:2162:2: rule__ProjectDescription__Group_10__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_10__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_10__2"


    // $ANTLR start "rule__ProjectDescription__Group_10__2__Impl"
    // InternalN4MFParser.g:2168:1: rule__ProjectDescription__Group_10__2__Impl : ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) ) ;
    public final void rule__ProjectDescription__Group_10__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2172:1: ( ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) ) )
            // InternalN4MFParser.g:2173:1: ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) )
            {
            // InternalN4MFParser.g:2173:1: ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) )
            // InternalN4MFParser.g:2174:2: ( rule__ProjectDescription__ImplementationIdAssignment_10_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementationIdAssignment_10_2()); 
            // InternalN4MFParser.g:2175:2: ( rule__ProjectDescription__ImplementationIdAssignment_10_2 )
            // InternalN4MFParser.g:2175:3: rule__ProjectDescription__ImplementationIdAssignment_10_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ImplementationIdAssignment_10_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getImplementationIdAssignment_10_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_10__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_11__0"
    // InternalN4MFParser.g:2184:1: rule__ProjectDescription__Group_11__0 : rule__ProjectDescription__Group_11__0__Impl rule__ProjectDescription__Group_11__1 ;
    public final void rule__ProjectDescription__Group_11__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2188:1: ( rule__ProjectDescription__Group_11__0__Impl rule__ProjectDescription__Group_11__1 )
            // InternalN4MFParser.g:2189:2: rule__ProjectDescription__Group_11__0__Impl rule__ProjectDescription__Group_11__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_11__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_11__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11__0"


    // $ANTLR start "rule__ProjectDescription__Group_11__0__Impl"
    // InternalN4MFParser.g:2196:1: rule__ProjectDescription__Group_11__0__Impl : ( ImplementedProjects ) ;
    public final void rule__ProjectDescription__Group_11__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2200:1: ( ( ImplementedProjects ) )
            // InternalN4MFParser.g:2201:1: ( ImplementedProjects )
            {
            // InternalN4MFParser.g:2201:1: ( ImplementedProjects )
            // InternalN4MFParser.g:2202:2: ImplementedProjects
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsKeyword_11_0()); 
            match(input,ImplementedProjects,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsKeyword_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_11__1"
    // InternalN4MFParser.g:2211:1: rule__ProjectDescription__Group_11__1 : rule__ProjectDescription__Group_11__1__Impl rule__ProjectDescription__Group_11__2 ;
    public final void rule__ProjectDescription__Group_11__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2215:1: ( rule__ProjectDescription__Group_11__1__Impl rule__ProjectDescription__Group_11__2 )
            // InternalN4MFParser.g:2216:2: rule__ProjectDescription__Group_11__1__Impl rule__ProjectDescription__Group_11__2
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_11__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_11__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11__1"


    // $ANTLR start "rule__ProjectDescription__Group_11__1__Impl"
    // InternalN4MFParser.g:2223:1: rule__ProjectDescription__Group_11__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_11__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2227:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2228:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2228:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2229:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_11_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_11_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_11__2"
    // InternalN4MFParser.g:2238:1: rule__ProjectDescription__Group_11__2 : rule__ProjectDescription__Group_11__2__Impl rule__ProjectDescription__Group_11__3 ;
    public final void rule__ProjectDescription__Group_11__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2242:1: ( rule__ProjectDescription__Group_11__2__Impl rule__ProjectDescription__Group_11__3 )
            // InternalN4MFParser.g:2243:2: rule__ProjectDescription__Group_11__2__Impl rule__ProjectDescription__Group_11__3
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_11__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_11__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11__2"


    // $ANTLR start "rule__ProjectDescription__Group_11__2__Impl"
    // InternalN4MFParser.g:2250:1: rule__ProjectDescription__Group_11__2__Impl : ( ( rule__ProjectDescription__Group_11_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_11__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2254:1: ( ( ( rule__ProjectDescription__Group_11_2__0 )? ) )
            // InternalN4MFParser.g:2255:1: ( ( rule__ProjectDescription__Group_11_2__0 )? )
            {
            // InternalN4MFParser.g:2255:1: ( ( rule__ProjectDescription__Group_11_2__0 )? )
            // InternalN4MFParser.g:2256:2: ( rule__ProjectDescription__Group_11_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_11_2()); 
            // InternalN4MFParser.g:2257:2: ( rule__ProjectDescription__Group_11_2__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==ProjectDependencies||LA18_0==ProjectVersion||LA18_0==ModuleFilters||(LA18_0>=ProjectType && LA18_0<=Application)||LA18_0==VendorName||(LA18_0>=Libraries && LA18_0<=VendorId)||LA18_0==Sources||LA18_0==Content||LA18_0==Output||(LA18_0>=Test && LA18_0<=API)||LA18_0==RULE_ID) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalN4MFParser.g:2257:3: rule__ProjectDescription__Group_11_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_11_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectDescriptionAccess().getGroup_11_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_11__3"
    // InternalN4MFParser.g:2265:1: rule__ProjectDescription__Group_11__3 : rule__ProjectDescription__Group_11__3__Impl ;
    public final void rule__ProjectDescription__Group_11__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2269:1: ( rule__ProjectDescription__Group_11__3__Impl )
            // InternalN4MFParser.g:2270:2: rule__ProjectDescription__Group_11__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_11__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11__3"


    // $ANTLR start "rule__ProjectDescription__Group_11__3__Impl"
    // InternalN4MFParser.g:2276:1: rule__ProjectDescription__Group_11__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_11__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2280:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2281:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2281:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2282:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_11_3()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_11_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_11_2__0"
    // InternalN4MFParser.g:2292:1: rule__ProjectDescription__Group_11_2__0 : rule__ProjectDescription__Group_11_2__0__Impl rule__ProjectDescription__Group_11_2__1 ;
    public final void rule__ProjectDescription__Group_11_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2296:1: ( rule__ProjectDescription__Group_11_2__0__Impl rule__ProjectDescription__Group_11_2__1 )
            // InternalN4MFParser.g:2297:2: rule__ProjectDescription__Group_11_2__0__Impl rule__ProjectDescription__Group_11_2__1
            {
            pushFollow(FOLLOW_10);
            rule__ProjectDescription__Group_11_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_11_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11_2__0"


    // $ANTLR start "rule__ProjectDescription__Group_11_2__0__Impl"
    // InternalN4MFParser.g:2304:1: rule__ProjectDescription__Group_11_2__0__Impl : ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_11_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2308:1: ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 ) ) )
            // InternalN4MFParser.g:2309:1: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 ) )
            {
            // InternalN4MFParser.g:2309:1: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 ) )
            // InternalN4MFParser.g:2310:2: ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11_2_0()); 
            // InternalN4MFParser.g:2311:2: ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 )
            // InternalN4MFParser.g:2311:3: rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11_2__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_11_2__1"
    // InternalN4MFParser.g:2319:1: rule__ProjectDescription__Group_11_2__1 : rule__ProjectDescription__Group_11_2__1__Impl ;
    public final void rule__ProjectDescription__Group_11_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2323:1: ( rule__ProjectDescription__Group_11_2__1__Impl )
            // InternalN4MFParser.g:2324:2: rule__ProjectDescription__Group_11_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_11_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11_2__1"


    // $ANTLR start "rule__ProjectDescription__Group_11_2__1__Impl"
    // InternalN4MFParser.g:2330:1: rule__ProjectDescription__Group_11_2__1__Impl : ( ( rule__ProjectDescription__Group_11_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_11_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2334:1: ( ( ( rule__ProjectDescription__Group_11_2_1__0 )* ) )
            // InternalN4MFParser.g:2335:1: ( ( rule__ProjectDescription__Group_11_2_1__0 )* )
            {
            // InternalN4MFParser.g:2335:1: ( ( rule__ProjectDescription__Group_11_2_1__0 )* )
            // InternalN4MFParser.g:2336:2: ( rule__ProjectDescription__Group_11_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_11_2_1()); 
            // InternalN4MFParser.g:2337:2: ( rule__ProjectDescription__Group_11_2_1__0 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==Comma) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalN4MFParser.g:2337:3: rule__ProjectDescription__Group_11_2_1__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ProjectDescription__Group_11_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getGroup_11_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11_2__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_11_2_1__0"
    // InternalN4MFParser.g:2346:1: rule__ProjectDescription__Group_11_2_1__0 : rule__ProjectDescription__Group_11_2_1__0__Impl rule__ProjectDescription__Group_11_2_1__1 ;
    public final void rule__ProjectDescription__Group_11_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2350:1: ( rule__ProjectDescription__Group_11_2_1__0__Impl rule__ProjectDescription__Group_11_2_1__1 )
            // InternalN4MFParser.g:2351:2: rule__ProjectDescription__Group_11_2_1__0__Impl rule__ProjectDescription__Group_11_2_1__1
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDescription__Group_11_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_11_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11_2_1__0"


    // $ANTLR start "rule__ProjectDescription__Group_11_2_1__0__Impl"
    // InternalN4MFParser.g:2358:1: rule__ProjectDescription__Group_11_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_11_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2362:1: ( ( Comma ) )
            // InternalN4MFParser.g:2363:1: ( Comma )
            {
            // InternalN4MFParser.g:2363:1: ( Comma )
            // InternalN4MFParser.g:2364:2: Comma
            {
             before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_11_2_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_11_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11_2_1__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_11_2_1__1"
    // InternalN4MFParser.g:2373:1: rule__ProjectDescription__Group_11_2_1__1 : rule__ProjectDescription__Group_11_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_11_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2377:1: ( rule__ProjectDescription__Group_11_2_1__1__Impl )
            // InternalN4MFParser.g:2378:2: rule__ProjectDescription__Group_11_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_11_2_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11_2_1__1"


    // $ANTLR start "rule__ProjectDescription__Group_11_2_1__1__Impl"
    // InternalN4MFParser.g:2384:1: rule__ProjectDescription__Group_11_2_1__1__Impl : ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_11_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2388:1: ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 ) ) )
            // InternalN4MFParser.g:2389:1: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 ) )
            {
            // InternalN4MFParser.g:2389:1: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 ) )
            // InternalN4MFParser.g:2390:2: ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11_2_1_1()); 
            // InternalN4MFParser.g:2391:2: ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 )
            // InternalN4MFParser.g:2391:3: rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_11_2_1__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_12__0"
    // InternalN4MFParser.g:2400:1: rule__ProjectDescription__Group_12__0 : rule__ProjectDescription__Group_12__0__Impl rule__ProjectDescription__Group_12__1 ;
    public final void rule__ProjectDescription__Group_12__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2404:1: ( rule__ProjectDescription__Group_12__0__Impl rule__ProjectDescription__Group_12__1 )
            // InternalN4MFParser.g:2405:2: rule__ProjectDescription__Group_12__0__Impl rule__ProjectDescription__Group_12__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_12__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_12__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12__0"


    // $ANTLR start "rule__ProjectDescription__Group_12__0__Impl"
    // InternalN4MFParser.g:2412:1: rule__ProjectDescription__Group_12__0__Impl : ( InitModules ) ;
    public final void rule__ProjectDescription__Group_12__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2416:1: ( ( InitModules ) )
            // InternalN4MFParser.g:2417:1: ( InitModules )
            {
            // InternalN4MFParser.g:2417:1: ( InitModules )
            // InternalN4MFParser.g:2418:2: InitModules
            {
             before(grammarAccess.getProjectDescriptionAccess().getInitModulesKeyword_12_0()); 
            match(input,InitModules,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getInitModulesKeyword_12_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_12__1"
    // InternalN4MFParser.g:2427:1: rule__ProjectDescription__Group_12__1 : rule__ProjectDescription__Group_12__1__Impl rule__ProjectDescription__Group_12__2 ;
    public final void rule__ProjectDescription__Group_12__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2431:1: ( rule__ProjectDescription__Group_12__1__Impl rule__ProjectDescription__Group_12__2 )
            // InternalN4MFParser.g:2432:2: rule__ProjectDescription__Group_12__1__Impl rule__ProjectDescription__Group_12__2
            {
            pushFollow(FOLLOW_12);
            rule__ProjectDescription__Group_12__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_12__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12__1"


    // $ANTLR start "rule__ProjectDescription__Group_12__1__Impl"
    // InternalN4MFParser.g:2439:1: rule__ProjectDescription__Group_12__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_12__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2443:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2444:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2444:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2445:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_12_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_12_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_12__2"
    // InternalN4MFParser.g:2454:1: rule__ProjectDescription__Group_12__2 : rule__ProjectDescription__Group_12__2__Impl rule__ProjectDescription__Group_12__3 ;
    public final void rule__ProjectDescription__Group_12__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2458:1: ( rule__ProjectDescription__Group_12__2__Impl rule__ProjectDescription__Group_12__3 )
            // InternalN4MFParser.g:2459:2: rule__ProjectDescription__Group_12__2__Impl rule__ProjectDescription__Group_12__3
            {
            pushFollow(FOLLOW_12);
            rule__ProjectDescription__Group_12__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_12__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12__2"


    // $ANTLR start "rule__ProjectDescription__Group_12__2__Impl"
    // InternalN4MFParser.g:2466:1: rule__ProjectDescription__Group_12__2__Impl : ( ( rule__ProjectDescription__Group_12_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_12__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2470:1: ( ( ( rule__ProjectDescription__Group_12_2__0 )? ) )
            // InternalN4MFParser.g:2471:1: ( ( rule__ProjectDescription__Group_12_2__0 )? )
            {
            // InternalN4MFParser.g:2471:1: ( ( rule__ProjectDescription__Group_12_2__0 )? )
            // InternalN4MFParser.g:2472:2: ( rule__ProjectDescription__Group_12_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_12_2()); 
            // InternalN4MFParser.g:2473:2: ( rule__ProjectDescription__Group_12_2__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_STRING) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalN4MFParser.g:2473:3: rule__ProjectDescription__Group_12_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_12_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectDescriptionAccess().getGroup_12_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_12__3"
    // InternalN4MFParser.g:2481:1: rule__ProjectDescription__Group_12__3 : rule__ProjectDescription__Group_12__3__Impl ;
    public final void rule__ProjectDescription__Group_12__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2485:1: ( rule__ProjectDescription__Group_12__3__Impl )
            // InternalN4MFParser.g:2486:2: rule__ProjectDescription__Group_12__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_12__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12__3"


    // $ANTLR start "rule__ProjectDescription__Group_12__3__Impl"
    // InternalN4MFParser.g:2492:1: rule__ProjectDescription__Group_12__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_12__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2496:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2497:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2497:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2498:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_12_3()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_12_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_12_2__0"
    // InternalN4MFParser.g:2508:1: rule__ProjectDescription__Group_12_2__0 : rule__ProjectDescription__Group_12_2__0__Impl rule__ProjectDescription__Group_12_2__1 ;
    public final void rule__ProjectDescription__Group_12_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2512:1: ( rule__ProjectDescription__Group_12_2__0__Impl rule__ProjectDescription__Group_12_2__1 )
            // InternalN4MFParser.g:2513:2: rule__ProjectDescription__Group_12_2__0__Impl rule__ProjectDescription__Group_12_2__1
            {
            pushFollow(FOLLOW_10);
            rule__ProjectDescription__Group_12_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_12_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12_2__0"


    // $ANTLR start "rule__ProjectDescription__Group_12_2__0__Impl"
    // InternalN4MFParser.g:2520:1: rule__ProjectDescription__Group_12_2__0__Impl : ( ( rule__ProjectDescription__InitModulesAssignment_12_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_12_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2524:1: ( ( ( rule__ProjectDescription__InitModulesAssignment_12_2_0 ) ) )
            // InternalN4MFParser.g:2525:1: ( ( rule__ProjectDescription__InitModulesAssignment_12_2_0 ) )
            {
            // InternalN4MFParser.g:2525:1: ( ( rule__ProjectDescription__InitModulesAssignment_12_2_0 ) )
            // InternalN4MFParser.g:2526:2: ( rule__ProjectDescription__InitModulesAssignment_12_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12_2_0()); 
            // InternalN4MFParser.g:2527:2: ( rule__ProjectDescription__InitModulesAssignment_12_2_0 )
            // InternalN4MFParser.g:2527:3: rule__ProjectDescription__InitModulesAssignment_12_2_0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__InitModulesAssignment_12_2_0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12_2__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_12_2__1"
    // InternalN4MFParser.g:2535:1: rule__ProjectDescription__Group_12_2__1 : rule__ProjectDescription__Group_12_2__1__Impl ;
    public final void rule__ProjectDescription__Group_12_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2539:1: ( rule__ProjectDescription__Group_12_2__1__Impl )
            // InternalN4MFParser.g:2540:2: rule__ProjectDescription__Group_12_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_12_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12_2__1"


    // $ANTLR start "rule__ProjectDescription__Group_12_2__1__Impl"
    // InternalN4MFParser.g:2546:1: rule__ProjectDescription__Group_12_2__1__Impl : ( ( rule__ProjectDescription__Group_12_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_12_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2550:1: ( ( ( rule__ProjectDescription__Group_12_2_1__0 )* ) )
            // InternalN4MFParser.g:2551:1: ( ( rule__ProjectDescription__Group_12_2_1__0 )* )
            {
            // InternalN4MFParser.g:2551:1: ( ( rule__ProjectDescription__Group_12_2_1__0 )* )
            // InternalN4MFParser.g:2552:2: ( rule__ProjectDescription__Group_12_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_12_2_1()); 
            // InternalN4MFParser.g:2553:2: ( rule__ProjectDescription__Group_12_2_1__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==Comma) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalN4MFParser.g:2553:3: rule__ProjectDescription__Group_12_2_1__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ProjectDescription__Group_12_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getGroup_12_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12_2__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_12_2_1__0"
    // InternalN4MFParser.g:2562:1: rule__ProjectDescription__Group_12_2_1__0 : rule__ProjectDescription__Group_12_2_1__0__Impl rule__ProjectDescription__Group_12_2_1__1 ;
    public final void rule__ProjectDescription__Group_12_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2566:1: ( rule__ProjectDescription__Group_12_2_1__0__Impl rule__ProjectDescription__Group_12_2_1__1 )
            // InternalN4MFParser.g:2567:2: rule__ProjectDescription__Group_12_2_1__0__Impl rule__ProjectDescription__Group_12_2_1__1
            {
            pushFollow(FOLLOW_7);
            rule__ProjectDescription__Group_12_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_12_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12_2_1__0"


    // $ANTLR start "rule__ProjectDescription__Group_12_2_1__0__Impl"
    // InternalN4MFParser.g:2574:1: rule__ProjectDescription__Group_12_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_12_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2578:1: ( ( Comma ) )
            // InternalN4MFParser.g:2579:1: ( Comma )
            {
            // InternalN4MFParser.g:2579:1: ( Comma )
            // InternalN4MFParser.g:2580:2: Comma
            {
             before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_12_2_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_12_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12_2_1__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_12_2_1__1"
    // InternalN4MFParser.g:2589:1: rule__ProjectDescription__Group_12_2_1__1 : rule__ProjectDescription__Group_12_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_12_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2593:1: ( rule__ProjectDescription__Group_12_2_1__1__Impl )
            // InternalN4MFParser.g:2594:2: rule__ProjectDescription__Group_12_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_12_2_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12_2_1__1"


    // $ANTLR start "rule__ProjectDescription__Group_12_2_1__1__Impl"
    // InternalN4MFParser.g:2600:1: rule__ProjectDescription__Group_12_2_1__1__Impl : ( ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_12_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2604:1: ( ( ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 ) ) )
            // InternalN4MFParser.g:2605:1: ( ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 ) )
            {
            // InternalN4MFParser.g:2605:1: ( ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 ) )
            // InternalN4MFParser.g:2606:2: ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12_2_1_1()); 
            // InternalN4MFParser.g:2607:2: ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 )
            // InternalN4MFParser.g:2607:3: rule__ProjectDescription__InitModulesAssignment_12_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__InitModulesAssignment_12_2_1_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_12_2_1__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_13__0"
    // InternalN4MFParser.g:2616:1: rule__ProjectDescription__Group_13__0 : rule__ProjectDescription__Group_13__0__Impl rule__ProjectDescription__Group_13__1 ;
    public final void rule__ProjectDescription__Group_13__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2620:1: ( rule__ProjectDescription__Group_13__0__Impl rule__ProjectDescription__Group_13__1 )
            // InternalN4MFParser.g:2621:2: rule__ProjectDescription__Group_13__0__Impl rule__ProjectDescription__Group_13__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_13__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_13__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_13__0"


    // $ANTLR start "rule__ProjectDescription__Group_13__0__Impl"
    // InternalN4MFParser.g:2628:1: rule__ProjectDescription__Group_13__0__Impl : ( ExecModule ) ;
    public final void rule__ProjectDescription__Group_13__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2632:1: ( ( ExecModule ) )
            // InternalN4MFParser.g:2633:1: ( ExecModule )
            {
            // InternalN4MFParser.g:2633:1: ( ExecModule )
            // InternalN4MFParser.g:2634:2: ExecModule
            {
             before(grammarAccess.getProjectDescriptionAccess().getExecModuleKeyword_13_0()); 
            match(input,ExecModule,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getExecModuleKeyword_13_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_13__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_13__1"
    // InternalN4MFParser.g:2643:1: rule__ProjectDescription__Group_13__1 : rule__ProjectDescription__Group_13__1__Impl rule__ProjectDescription__Group_13__2 ;
    public final void rule__ProjectDescription__Group_13__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2647:1: ( rule__ProjectDescription__Group_13__1__Impl rule__ProjectDescription__Group_13__2 )
            // InternalN4MFParser.g:2648:2: rule__ProjectDescription__Group_13__1__Impl rule__ProjectDescription__Group_13__2
            {
            pushFollow(FOLLOW_7);
            rule__ProjectDescription__Group_13__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_13__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_13__1"


    // $ANTLR start "rule__ProjectDescription__Group_13__1__Impl"
    // InternalN4MFParser.g:2655:1: rule__ProjectDescription__Group_13__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_13__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2659:1: ( ( Colon ) )
            // InternalN4MFParser.g:2660:1: ( Colon )
            {
            // InternalN4MFParser.g:2660:1: ( Colon )
            // InternalN4MFParser.g:2661:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_13_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_13_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_13__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_13__2"
    // InternalN4MFParser.g:2670:1: rule__ProjectDescription__Group_13__2 : rule__ProjectDescription__Group_13__2__Impl ;
    public final void rule__ProjectDescription__Group_13__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2674:1: ( rule__ProjectDescription__Group_13__2__Impl )
            // InternalN4MFParser.g:2675:2: rule__ProjectDescription__Group_13__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_13__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_13__2"


    // $ANTLR start "rule__ProjectDescription__Group_13__2__Impl"
    // InternalN4MFParser.g:2681:1: rule__ProjectDescription__Group_13__2__Impl : ( ( rule__ProjectDescription__ExecModuleAssignment_13_2 ) ) ;
    public final void rule__ProjectDescription__Group_13__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2685:1: ( ( ( rule__ProjectDescription__ExecModuleAssignment_13_2 ) ) )
            // InternalN4MFParser.g:2686:1: ( ( rule__ProjectDescription__ExecModuleAssignment_13_2 ) )
            {
            // InternalN4MFParser.g:2686:1: ( ( rule__ProjectDescription__ExecModuleAssignment_13_2 ) )
            // InternalN4MFParser.g:2687:2: ( rule__ProjectDescription__ExecModuleAssignment_13_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_13_2()); 
            // InternalN4MFParser.g:2688:2: ( rule__ProjectDescription__ExecModuleAssignment_13_2 )
            // InternalN4MFParser.g:2688:3: rule__ProjectDescription__ExecModuleAssignment_13_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ExecModuleAssignment_13_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_13_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_13__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_14__0"
    // InternalN4MFParser.g:2697:1: rule__ProjectDescription__Group_14__0 : rule__ProjectDescription__Group_14__0__Impl rule__ProjectDescription__Group_14__1 ;
    public final void rule__ProjectDescription__Group_14__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2701:1: ( rule__ProjectDescription__Group_14__0__Impl rule__ProjectDescription__Group_14__1 )
            // InternalN4MFParser.g:2702:2: rule__ProjectDescription__Group_14__0__Impl rule__ProjectDescription__Group_14__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_14__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_14__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_14__0"


    // $ANTLR start "rule__ProjectDescription__Group_14__0__Impl"
    // InternalN4MFParser.g:2709:1: rule__ProjectDescription__Group_14__0__Impl : ( Output ) ;
    public final void rule__ProjectDescription__Group_14__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2713:1: ( ( Output ) )
            // InternalN4MFParser.g:2714:1: ( Output )
            {
            // InternalN4MFParser.g:2714:1: ( Output )
            // InternalN4MFParser.g:2715:2: Output
            {
             before(grammarAccess.getProjectDescriptionAccess().getOutputKeyword_14_0()); 
            match(input,Output,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getOutputKeyword_14_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_14__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_14__1"
    // InternalN4MFParser.g:2724:1: rule__ProjectDescription__Group_14__1 : rule__ProjectDescription__Group_14__1__Impl rule__ProjectDescription__Group_14__2 ;
    public final void rule__ProjectDescription__Group_14__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2728:1: ( rule__ProjectDescription__Group_14__1__Impl rule__ProjectDescription__Group_14__2 )
            // InternalN4MFParser.g:2729:2: rule__ProjectDescription__Group_14__1__Impl rule__ProjectDescription__Group_14__2
            {
            pushFollow(FOLLOW_7);
            rule__ProjectDescription__Group_14__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_14__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_14__1"


    // $ANTLR start "rule__ProjectDescription__Group_14__1__Impl"
    // InternalN4MFParser.g:2736:1: rule__ProjectDescription__Group_14__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_14__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2740:1: ( ( Colon ) )
            // InternalN4MFParser.g:2741:1: ( Colon )
            {
            // InternalN4MFParser.g:2741:1: ( Colon )
            // InternalN4MFParser.g:2742:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_14_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_14_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_14__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_14__2"
    // InternalN4MFParser.g:2751:1: rule__ProjectDescription__Group_14__2 : rule__ProjectDescription__Group_14__2__Impl ;
    public final void rule__ProjectDescription__Group_14__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2755:1: ( rule__ProjectDescription__Group_14__2__Impl )
            // InternalN4MFParser.g:2756:2: rule__ProjectDescription__Group_14__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_14__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_14__2"


    // $ANTLR start "rule__ProjectDescription__Group_14__2__Impl"
    // InternalN4MFParser.g:2762:1: rule__ProjectDescription__Group_14__2__Impl : ( ( rule__ProjectDescription__OutputPathRawAssignment_14_2 ) ) ;
    public final void rule__ProjectDescription__Group_14__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2766:1: ( ( ( rule__ProjectDescription__OutputPathRawAssignment_14_2 ) ) )
            // InternalN4MFParser.g:2767:1: ( ( rule__ProjectDescription__OutputPathRawAssignment_14_2 ) )
            {
            // InternalN4MFParser.g:2767:1: ( ( rule__ProjectDescription__OutputPathRawAssignment_14_2 ) )
            // InternalN4MFParser.g:2768:2: ( rule__ProjectDescription__OutputPathRawAssignment_14_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getOutputPathRawAssignment_14_2()); 
            // InternalN4MFParser.g:2769:2: ( rule__ProjectDescription__OutputPathRawAssignment_14_2 )
            // InternalN4MFParser.g:2769:3: rule__ProjectDescription__OutputPathRawAssignment_14_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__OutputPathRawAssignment_14_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getOutputPathRawAssignment_14_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_14__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_15__0"
    // InternalN4MFParser.g:2778:1: rule__ProjectDescription__Group_15__0 : rule__ProjectDescription__Group_15__0__Impl rule__ProjectDescription__Group_15__1 ;
    public final void rule__ProjectDescription__Group_15__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2782:1: ( rule__ProjectDescription__Group_15__0__Impl rule__ProjectDescription__Group_15__1 )
            // InternalN4MFParser.g:2783:2: rule__ProjectDescription__Group_15__0__Impl rule__ProjectDescription__Group_15__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_15__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_15__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__0"


    // $ANTLR start "rule__ProjectDescription__Group_15__0__Impl"
    // InternalN4MFParser.g:2790:1: rule__ProjectDescription__Group_15__0__Impl : ( Libraries ) ;
    public final void rule__ProjectDescription__Group_15__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2794:1: ( ( Libraries ) )
            // InternalN4MFParser.g:2795:1: ( Libraries )
            {
            // InternalN4MFParser.g:2795:1: ( Libraries )
            // InternalN4MFParser.g:2796:2: Libraries
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibrariesKeyword_15_0()); 
            match(input,Libraries,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLibrariesKeyword_15_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_15__1"
    // InternalN4MFParser.g:2805:1: rule__ProjectDescription__Group_15__1 : rule__ProjectDescription__Group_15__1__Impl rule__ProjectDescription__Group_15__2 ;
    public final void rule__ProjectDescription__Group_15__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2809:1: ( rule__ProjectDescription__Group_15__1__Impl rule__ProjectDescription__Group_15__2 )
            // InternalN4MFParser.g:2810:2: rule__ProjectDescription__Group_15__1__Impl rule__ProjectDescription__Group_15__2
            {
            pushFollow(FOLLOW_7);
            rule__ProjectDescription__Group_15__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_15__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__1"


    // $ANTLR start "rule__ProjectDescription__Group_15__1__Impl"
    // InternalN4MFParser.g:2817:1: rule__ProjectDescription__Group_15__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_15__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2821:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2822:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2822:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2823:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_15_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_15_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_15__2"
    // InternalN4MFParser.g:2832:1: rule__ProjectDescription__Group_15__2 : rule__ProjectDescription__Group_15__2__Impl rule__ProjectDescription__Group_15__3 ;
    public final void rule__ProjectDescription__Group_15__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2836:1: ( rule__ProjectDescription__Group_15__2__Impl rule__ProjectDescription__Group_15__3 )
            // InternalN4MFParser.g:2837:2: rule__ProjectDescription__Group_15__2__Impl rule__ProjectDescription__Group_15__3
            {
            pushFollow(FOLLOW_13);
            rule__ProjectDescription__Group_15__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_15__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__2"


    // $ANTLR start "rule__ProjectDescription__Group_15__2__Impl"
    // InternalN4MFParser.g:2844:1: rule__ProjectDescription__Group_15__2__Impl : ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 ) ) ;
    public final void rule__ProjectDescription__Group_15__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2848:1: ( ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 ) ) )
            // InternalN4MFParser.g:2849:1: ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 ) )
            {
            // InternalN4MFParser.g:2849:1: ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 ) )
            // InternalN4MFParser.g:2850:2: ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_2()); 
            // InternalN4MFParser.g:2851:2: ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 )
            // InternalN4MFParser.g:2851:3: rule__ProjectDescription__LibraryPathsRawAssignment_15_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__LibraryPathsRawAssignment_15_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_15__3"
    // InternalN4MFParser.g:2859:1: rule__ProjectDescription__Group_15__3 : rule__ProjectDescription__Group_15__3__Impl rule__ProjectDescription__Group_15__4 ;
    public final void rule__ProjectDescription__Group_15__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2863:1: ( rule__ProjectDescription__Group_15__3__Impl rule__ProjectDescription__Group_15__4 )
            // InternalN4MFParser.g:2864:2: rule__ProjectDescription__Group_15__3__Impl rule__ProjectDescription__Group_15__4
            {
            pushFollow(FOLLOW_13);
            rule__ProjectDescription__Group_15__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_15__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__3"


    // $ANTLR start "rule__ProjectDescription__Group_15__3__Impl"
    // InternalN4MFParser.g:2871:1: rule__ProjectDescription__Group_15__3__Impl : ( ( rule__ProjectDescription__Group_15_3__0 )* ) ;
    public final void rule__ProjectDescription__Group_15__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2875:1: ( ( ( rule__ProjectDescription__Group_15_3__0 )* ) )
            // InternalN4MFParser.g:2876:1: ( ( rule__ProjectDescription__Group_15_3__0 )* )
            {
            // InternalN4MFParser.g:2876:1: ( ( rule__ProjectDescription__Group_15_3__0 )* )
            // InternalN4MFParser.g:2877:2: ( rule__ProjectDescription__Group_15_3__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_15_3()); 
            // InternalN4MFParser.g:2878:2: ( rule__ProjectDescription__Group_15_3__0 )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==Comma) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalN4MFParser.g:2878:3: rule__ProjectDescription__Group_15_3__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ProjectDescription__Group_15_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getGroup_15_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_15__4"
    // InternalN4MFParser.g:2886:1: rule__ProjectDescription__Group_15__4 : rule__ProjectDescription__Group_15__4__Impl ;
    public final void rule__ProjectDescription__Group_15__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2890:1: ( rule__ProjectDescription__Group_15__4__Impl )
            // InternalN4MFParser.g:2891:2: rule__ProjectDescription__Group_15__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_15__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__4"


    // $ANTLR start "rule__ProjectDescription__Group_15__4__Impl"
    // InternalN4MFParser.g:2897:1: rule__ProjectDescription__Group_15__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_15__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2901:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2902:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2902:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2903:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_15_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_15_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15__4__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_15_3__0"
    // InternalN4MFParser.g:2913:1: rule__ProjectDescription__Group_15_3__0 : rule__ProjectDescription__Group_15_3__0__Impl rule__ProjectDescription__Group_15_3__1 ;
    public final void rule__ProjectDescription__Group_15_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2917:1: ( rule__ProjectDescription__Group_15_3__0__Impl rule__ProjectDescription__Group_15_3__1 )
            // InternalN4MFParser.g:2918:2: rule__ProjectDescription__Group_15_3__0__Impl rule__ProjectDescription__Group_15_3__1
            {
            pushFollow(FOLLOW_7);
            rule__ProjectDescription__Group_15_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_15_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15_3__0"


    // $ANTLR start "rule__ProjectDescription__Group_15_3__0__Impl"
    // InternalN4MFParser.g:2925:1: rule__ProjectDescription__Group_15_3__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_15_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2929:1: ( ( Comma ) )
            // InternalN4MFParser.g:2930:1: ( Comma )
            {
            // InternalN4MFParser.g:2930:1: ( Comma )
            // InternalN4MFParser.g:2931:2: Comma
            {
             before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_15_3_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_15_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15_3__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_15_3__1"
    // InternalN4MFParser.g:2940:1: rule__ProjectDescription__Group_15_3__1 : rule__ProjectDescription__Group_15_3__1__Impl ;
    public final void rule__ProjectDescription__Group_15_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2944:1: ( rule__ProjectDescription__Group_15_3__1__Impl )
            // InternalN4MFParser.g:2945:2: rule__ProjectDescription__Group_15_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_15_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15_3__1"


    // $ANTLR start "rule__ProjectDescription__Group_15_3__1__Impl"
    // InternalN4MFParser.g:2951:1: rule__ProjectDescription__Group_15_3__1__Impl : ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 ) ) ;
    public final void rule__ProjectDescription__Group_15_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2955:1: ( ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 ) ) )
            // InternalN4MFParser.g:2956:1: ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 ) )
            {
            // InternalN4MFParser.g:2956:1: ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 ) )
            // InternalN4MFParser.g:2957:2: ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_3_1()); 
            // InternalN4MFParser.g:2958:2: ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 )
            // InternalN4MFParser.g:2958:3: rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_15_3__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_16__0"
    // InternalN4MFParser.g:2967:1: rule__ProjectDescription__Group_16__0 : rule__ProjectDescription__Group_16__0__Impl rule__ProjectDescription__Group_16__1 ;
    public final void rule__ProjectDescription__Group_16__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2971:1: ( rule__ProjectDescription__Group_16__0__Impl rule__ProjectDescription__Group_16__1 )
            // InternalN4MFParser.g:2972:2: rule__ProjectDescription__Group_16__0__Impl rule__ProjectDescription__Group_16__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_16__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_16__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__0"


    // $ANTLR start "rule__ProjectDescription__Group_16__0__Impl"
    // InternalN4MFParser.g:2979:1: rule__ProjectDescription__Group_16__0__Impl : ( Resources ) ;
    public final void rule__ProjectDescription__Group_16__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2983:1: ( ( Resources ) )
            // InternalN4MFParser.g:2984:1: ( Resources )
            {
            // InternalN4MFParser.g:2984:1: ( Resources )
            // InternalN4MFParser.g:2985:2: Resources
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcesKeyword_16_0()); 
            match(input,Resources,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getResourcesKeyword_16_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_16__1"
    // InternalN4MFParser.g:2994:1: rule__ProjectDescription__Group_16__1 : rule__ProjectDescription__Group_16__1__Impl rule__ProjectDescription__Group_16__2 ;
    public final void rule__ProjectDescription__Group_16__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2998:1: ( rule__ProjectDescription__Group_16__1__Impl rule__ProjectDescription__Group_16__2 )
            // InternalN4MFParser.g:2999:2: rule__ProjectDescription__Group_16__1__Impl rule__ProjectDescription__Group_16__2
            {
            pushFollow(FOLLOW_7);
            rule__ProjectDescription__Group_16__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_16__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__1"


    // $ANTLR start "rule__ProjectDescription__Group_16__1__Impl"
    // InternalN4MFParser.g:3006:1: rule__ProjectDescription__Group_16__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_16__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3010:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3011:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3011:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3012:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_16_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_16_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_16__2"
    // InternalN4MFParser.g:3021:1: rule__ProjectDescription__Group_16__2 : rule__ProjectDescription__Group_16__2__Impl rule__ProjectDescription__Group_16__3 ;
    public final void rule__ProjectDescription__Group_16__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3025:1: ( rule__ProjectDescription__Group_16__2__Impl rule__ProjectDescription__Group_16__3 )
            // InternalN4MFParser.g:3026:2: rule__ProjectDescription__Group_16__2__Impl rule__ProjectDescription__Group_16__3
            {
            pushFollow(FOLLOW_13);
            rule__ProjectDescription__Group_16__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_16__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__2"


    // $ANTLR start "rule__ProjectDescription__Group_16__2__Impl"
    // InternalN4MFParser.g:3033:1: rule__ProjectDescription__Group_16__2__Impl : ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 ) ) ;
    public final void rule__ProjectDescription__Group_16__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3037:1: ( ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 ) ) )
            // InternalN4MFParser.g:3038:1: ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 ) )
            {
            // InternalN4MFParser.g:3038:1: ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 ) )
            // InternalN4MFParser.g:3039:2: ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_2()); 
            // InternalN4MFParser.g:3040:2: ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 )
            // InternalN4MFParser.g:3040:3: rule__ProjectDescription__ResourcePathsRawAssignment_16_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ResourcePathsRawAssignment_16_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_16__3"
    // InternalN4MFParser.g:3048:1: rule__ProjectDescription__Group_16__3 : rule__ProjectDescription__Group_16__3__Impl rule__ProjectDescription__Group_16__4 ;
    public final void rule__ProjectDescription__Group_16__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3052:1: ( rule__ProjectDescription__Group_16__3__Impl rule__ProjectDescription__Group_16__4 )
            // InternalN4MFParser.g:3053:2: rule__ProjectDescription__Group_16__3__Impl rule__ProjectDescription__Group_16__4
            {
            pushFollow(FOLLOW_13);
            rule__ProjectDescription__Group_16__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_16__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__3"


    // $ANTLR start "rule__ProjectDescription__Group_16__3__Impl"
    // InternalN4MFParser.g:3060:1: rule__ProjectDescription__Group_16__3__Impl : ( ( rule__ProjectDescription__Group_16_3__0 )* ) ;
    public final void rule__ProjectDescription__Group_16__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3064:1: ( ( ( rule__ProjectDescription__Group_16_3__0 )* ) )
            // InternalN4MFParser.g:3065:1: ( ( rule__ProjectDescription__Group_16_3__0 )* )
            {
            // InternalN4MFParser.g:3065:1: ( ( rule__ProjectDescription__Group_16_3__0 )* )
            // InternalN4MFParser.g:3066:2: ( rule__ProjectDescription__Group_16_3__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_16_3()); 
            // InternalN4MFParser.g:3067:2: ( rule__ProjectDescription__Group_16_3__0 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==Comma) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalN4MFParser.g:3067:3: rule__ProjectDescription__Group_16_3__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ProjectDescription__Group_16_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getGroup_16_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_16__4"
    // InternalN4MFParser.g:3075:1: rule__ProjectDescription__Group_16__4 : rule__ProjectDescription__Group_16__4__Impl ;
    public final void rule__ProjectDescription__Group_16__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3079:1: ( rule__ProjectDescription__Group_16__4__Impl )
            // InternalN4MFParser.g:3080:2: rule__ProjectDescription__Group_16__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_16__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__4"


    // $ANTLR start "rule__ProjectDescription__Group_16__4__Impl"
    // InternalN4MFParser.g:3086:1: rule__ProjectDescription__Group_16__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_16__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3090:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3091:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3091:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3092:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_16_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_16_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16__4__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_16_3__0"
    // InternalN4MFParser.g:3102:1: rule__ProjectDescription__Group_16_3__0 : rule__ProjectDescription__Group_16_3__0__Impl rule__ProjectDescription__Group_16_3__1 ;
    public final void rule__ProjectDescription__Group_16_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3106:1: ( rule__ProjectDescription__Group_16_3__0__Impl rule__ProjectDescription__Group_16_3__1 )
            // InternalN4MFParser.g:3107:2: rule__ProjectDescription__Group_16_3__0__Impl rule__ProjectDescription__Group_16_3__1
            {
            pushFollow(FOLLOW_7);
            rule__ProjectDescription__Group_16_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_16_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16_3__0"


    // $ANTLR start "rule__ProjectDescription__Group_16_3__0__Impl"
    // InternalN4MFParser.g:3114:1: rule__ProjectDescription__Group_16_3__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_16_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3118:1: ( ( Comma ) )
            // InternalN4MFParser.g:3119:1: ( Comma )
            {
            // InternalN4MFParser.g:3119:1: ( Comma )
            // InternalN4MFParser.g:3120:2: Comma
            {
             before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_16_3_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_16_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16_3__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_16_3__1"
    // InternalN4MFParser.g:3129:1: rule__ProjectDescription__Group_16_3__1 : rule__ProjectDescription__Group_16_3__1__Impl ;
    public final void rule__ProjectDescription__Group_16_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3133:1: ( rule__ProjectDescription__Group_16_3__1__Impl )
            // InternalN4MFParser.g:3134:2: rule__ProjectDescription__Group_16_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_16_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16_3__1"


    // $ANTLR start "rule__ProjectDescription__Group_16_3__1__Impl"
    // InternalN4MFParser.g:3140:1: rule__ProjectDescription__Group_16_3__1__Impl : ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 ) ) ;
    public final void rule__ProjectDescription__Group_16_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3144:1: ( ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 ) ) )
            // InternalN4MFParser.g:3145:1: ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 ) )
            {
            // InternalN4MFParser.g:3145:1: ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 ) )
            // InternalN4MFParser.g:3146:2: ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_3_1()); 
            // InternalN4MFParser.g:3147:2: ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 )
            // InternalN4MFParser.g:3147:3: rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_16_3__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_17__0"
    // InternalN4MFParser.g:3156:1: rule__ProjectDescription__Group_17__0 : rule__ProjectDescription__Group_17__0__Impl rule__ProjectDescription__Group_17__1 ;
    public final void rule__ProjectDescription__Group_17__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3160:1: ( rule__ProjectDescription__Group_17__0__Impl rule__ProjectDescription__Group_17__1 )
            // InternalN4MFParser.g:3161:2: rule__ProjectDescription__Group_17__0__Impl rule__ProjectDescription__Group_17__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_17__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_17__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_17__0"


    // $ANTLR start "rule__ProjectDescription__Group_17__0__Impl"
    // InternalN4MFParser.g:3168:1: rule__ProjectDescription__Group_17__0__Impl : ( Sources ) ;
    public final void rule__ProjectDescription__Group_17__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3172:1: ( ( Sources ) )
            // InternalN4MFParser.g:3173:1: ( Sources )
            {
            // InternalN4MFParser.g:3173:1: ( Sources )
            // InternalN4MFParser.g:3174:2: Sources
            {
             before(grammarAccess.getProjectDescriptionAccess().getSourcesKeyword_17_0()); 
            match(input,Sources,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getSourcesKeyword_17_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_17__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_17__1"
    // InternalN4MFParser.g:3183:1: rule__ProjectDescription__Group_17__1 : rule__ProjectDescription__Group_17__1__Impl rule__ProjectDescription__Group_17__2 ;
    public final void rule__ProjectDescription__Group_17__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3187:1: ( rule__ProjectDescription__Group_17__1__Impl rule__ProjectDescription__Group_17__2 )
            // InternalN4MFParser.g:3188:2: rule__ProjectDescription__Group_17__1__Impl rule__ProjectDescription__Group_17__2
            {
            pushFollow(FOLLOW_14);
            rule__ProjectDescription__Group_17__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_17__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_17__1"


    // $ANTLR start "rule__ProjectDescription__Group_17__1__Impl"
    // InternalN4MFParser.g:3195:1: rule__ProjectDescription__Group_17__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_17__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3199:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3200:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3200:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3201:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_17_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_17_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_17__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_17__2"
    // InternalN4MFParser.g:3210:1: rule__ProjectDescription__Group_17__2 : rule__ProjectDescription__Group_17__2__Impl rule__ProjectDescription__Group_17__3 ;
    public final void rule__ProjectDescription__Group_17__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3214:1: ( rule__ProjectDescription__Group_17__2__Impl rule__ProjectDescription__Group_17__3 )
            // InternalN4MFParser.g:3215:2: rule__ProjectDescription__Group_17__2__Impl rule__ProjectDescription__Group_17__3
            {
            pushFollow(FOLLOW_15);
            rule__ProjectDescription__Group_17__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_17__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_17__2"


    // $ANTLR start "rule__ProjectDescription__Group_17__2__Impl"
    // InternalN4MFParser.g:3222:1: rule__ProjectDescription__Group_17__2__Impl : ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) ) ;
    public final void rule__ProjectDescription__Group_17__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3226:1: ( ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) ) )
            // InternalN4MFParser.g:3227:1: ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) )
            {
            // InternalN4MFParser.g:3227:1: ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) )
            // InternalN4MFParser.g:3228:2: ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* )
            {
            // InternalN4MFParser.g:3228:2: ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) )
            // InternalN4MFParser.g:3229:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 
            // InternalN4MFParser.g:3230:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )
            // InternalN4MFParser.g:3230:4: rule__ProjectDescription__SourceFragmentAssignment_17_2
            {
            pushFollow(FOLLOW_16);
            rule__ProjectDescription__SourceFragmentAssignment_17_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 

            }

            // InternalN4MFParser.g:3233:2: ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* )
            // InternalN4MFParser.g:3234:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 
            // InternalN4MFParser.g:3235:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==External||LA24_0==Source||LA24_0==Test) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalN4MFParser.g:3235:4: rule__ProjectDescription__SourceFragmentAssignment_17_2
            	    {
            	    pushFollow(FOLLOW_16);
            	    rule__ProjectDescription__SourceFragmentAssignment_17_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_17__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_17__3"
    // InternalN4MFParser.g:3244:1: rule__ProjectDescription__Group_17__3 : rule__ProjectDescription__Group_17__3__Impl ;
    public final void rule__ProjectDescription__Group_17__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3248:1: ( rule__ProjectDescription__Group_17__3__Impl )
            // InternalN4MFParser.g:3249:2: rule__ProjectDescription__Group_17__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_17__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_17__3"


    // $ANTLR start "rule__ProjectDescription__Group_17__3__Impl"
    // InternalN4MFParser.g:3255:1: rule__ProjectDescription__Group_17__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_17__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3259:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3260:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3260:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3261:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_17_3()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_17_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_17__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_18__0"
    // InternalN4MFParser.g:3271:1: rule__ProjectDescription__Group_18__0 : rule__ProjectDescription__Group_18__0__Impl rule__ProjectDescription__Group_18__1 ;
    public final void rule__ProjectDescription__Group_18__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3275:1: ( rule__ProjectDescription__Group_18__0__Impl rule__ProjectDescription__Group_18__1 )
            // InternalN4MFParser.g:3276:2: rule__ProjectDescription__Group_18__0__Impl rule__ProjectDescription__Group_18__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_18__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_18__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_18__0"


    // $ANTLR start "rule__ProjectDescription__Group_18__0__Impl"
    // InternalN4MFParser.g:3283:1: rule__ProjectDescription__Group_18__0__Impl : ( ModuleFilters ) ;
    public final void rule__ProjectDescription__Group_18__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3287:1: ( ( ModuleFilters ) )
            // InternalN4MFParser.g:3288:1: ( ModuleFilters )
            {
            // InternalN4MFParser.g:3288:1: ( ModuleFilters )
            // InternalN4MFParser.g:3289:2: ModuleFilters
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersKeyword_18_0()); 
            match(input,ModuleFilters,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersKeyword_18_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_18__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_18__1"
    // InternalN4MFParser.g:3298:1: rule__ProjectDescription__Group_18__1 : rule__ProjectDescription__Group_18__1__Impl rule__ProjectDescription__Group_18__2 ;
    public final void rule__ProjectDescription__Group_18__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3302:1: ( rule__ProjectDescription__Group_18__1__Impl rule__ProjectDescription__Group_18__2 )
            // InternalN4MFParser.g:3303:2: rule__ProjectDescription__Group_18__1__Impl rule__ProjectDescription__Group_18__2
            {
            pushFollow(FOLLOW_17);
            rule__ProjectDescription__Group_18__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_18__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_18__1"


    // $ANTLR start "rule__ProjectDescription__Group_18__1__Impl"
    // InternalN4MFParser.g:3310:1: rule__ProjectDescription__Group_18__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_18__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3314:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3315:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3315:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3316:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_18_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_18_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_18__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_18__2"
    // InternalN4MFParser.g:3325:1: rule__ProjectDescription__Group_18__2 : rule__ProjectDescription__Group_18__2__Impl rule__ProjectDescription__Group_18__3 ;
    public final void rule__ProjectDescription__Group_18__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3329:1: ( rule__ProjectDescription__Group_18__2__Impl rule__ProjectDescription__Group_18__3 )
            // InternalN4MFParser.g:3330:2: rule__ProjectDescription__Group_18__2__Impl rule__ProjectDescription__Group_18__3
            {
            pushFollow(FOLLOW_15);
            rule__ProjectDescription__Group_18__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_18__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_18__2"


    // $ANTLR start "rule__ProjectDescription__Group_18__2__Impl"
    // InternalN4MFParser.g:3337:1: rule__ProjectDescription__Group_18__2__Impl : ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) ) ;
    public final void rule__ProjectDescription__Group_18__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3341:1: ( ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) ) )
            // InternalN4MFParser.g:3342:1: ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) )
            {
            // InternalN4MFParser.g:3342:1: ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) )
            // InternalN4MFParser.g:3343:2: ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* )
            {
            // InternalN4MFParser.g:3343:2: ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) )
            // InternalN4MFParser.g:3344:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 
            // InternalN4MFParser.g:3345:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )
            // InternalN4MFParser.g:3345:4: rule__ProjectDescription__ModuleFiltersAssignment_18_2
            {
            pushFollow(FOLLOW_18);
            rule__ProjectDescription__ModuleFiltersAssignment_18_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 

            }

            // InternalN4MFParser.g:3348:2: ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* )
            // InternalN4MFParser.g:3349:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 
            // InternalN4MFParser.g:3350:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==NoModuleWrap||LA25_0==NoValidate) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalN4MFParser.g:3350:4: rule__ProjectDescription__ModuleFiltersAssignment_18_2
            	    {
            	    pushFollow(FOLLOW_18);
            	    rule__ProjectDescription__ModuleFiltersAssignment_18_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_18__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_18__3"
    // InternalN4MFParser.g:3359:1: rule__ProjectDescription__Group_18__3 : rule__ProjectDescription__Group_18__3__Impl ;
    public final void rule__ProjectDescription__Group_18__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3363:1: ( rule__ProjectDescription__Group_18__3__Impl )
            // InternalN4MFParser.g:3364:2: rule__ProjectDescription__Group_18__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_18__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_18__3"


    // $ANTLR start "rule__ProjectDescription__Group_18__3__Impl"
    // InternalN4MFParser.g:3370:1: rule__ProjectDescription__Group_18__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_18__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3374:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3375:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3375:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3376:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_18_3()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_18_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_18__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_19__0"
    // InternalN4MFParser.g:3386:1: rule__ProjectDescription__Group_19__0 : rule__ProjectDescription__Group_19__0__Impl rule__ProjectDescription__Group_19__1 ;
    public final void rule__ProjectDescription__Group_19__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3390:1: ( rule__ProjectDescription__Group_19__0__Impl rule__ProjectDescription__Group_19__1 )
            // InternalN4MFParser.g:3391:2: rule__ProjectDescription__Group_19__0__Impl rule__ProjectDescription__Group_19__1
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDescription__Group_19__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_19__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19__0"


    // $ANTLR start "rule__ProjectDescription__Group_19__0__Impl"
    // InternalN4MFParser.g:3398:1: rule__ProjectDescription__Group_19__0__Impl : ( TestedProjects ) ;
    public final void rule__ProjectDescription__Group_19__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3402:1: ( ( TestedProjects ) )
            // InternalN4MFParser.g:3403:1: ( TestedProjects )
            {
            // InternalN4MFParser.g:3403:1: ( TestedProjects )
            // InternalN4MFParser.g:3404:2: TestedProjects
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsKeyword_19_0()); 
            match(input,TestedProjects,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsKeyword_19_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_19__1"
    // InternalN4MFParser.g:3413:1: rule__ProjectDescription__Group_19__1 : rule__ProjectDescription__Group_19__1__Impl rule__ProjectDescription__Group_19__2 ;
    public final void rule__ProjectDescription__Group_19__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3417:1: ( rule__ProjectDescription__Group_19__1__Impl rule__ProjectDescription__Group_19__2 )
            // InternalN4MFParser.g:3418:2: rule__ProjectDescription__Group_19__1__Impl rule__ProjectDescription__Group_19__2
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_19__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_19__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19__1"


    // $ANTLR start "rule__ProjectDescription__Group_19__1__Impl"
    // InternalN4MFParser.g:3425:1: rule__ProjectDescription__Group_19__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_19__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3429:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3430:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3430:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3431:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_19_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_19_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_19__2"
    // InternalN4MFParser.g:3440:1: rule__ProjectDescription__Group_19__2 : rule__ProjectDescription__Group_19__2__Impl rule__ProjectDescription__Group_19__3 ;
    public final void rule__ProjectDescription__Group_19__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3444:1: ( rule__ProjectDescription__Group_19__2__Impl rule__ProjectDescription__Group_19__3 )
            // InternalN4MFParser.g:3445:2: rule__ProjectDescription__Group_19__2__Impl rule__ProjectDescription__Group_19__3
            {
            pushFollow(FOLLOW_9);
            rule__ProjectDescription__Group_19__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_19__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19__2"


    // $ANTLR start "rule__ProjectDescription__Group_19__2__Impl"
    // InternalN4MFParser.g:3452:1: rule__ProjectDescription__Group_19__2__Impl : ( ( rule__ProjectDescription__Group_19_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_19__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3456:1: ( ( ( rule__ProjectDescription__Group_19_2__0 )? ) )
            // InternalN4MFParser.g:3457:1: ( ( rule__ProjectDescription__Group_19_2__0 )? )
            {
            // InternalN4MFParser.g:3457:1: ( ( rule__ProjectDescription__Group_19_2__0 )? )
            // InternalN4MFParser.g:3458:2: ( rule__ProjectDescription__Group_19_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_19_2()); 
            // InternalN4MFParser.g:3459:2: ( rule__ProjectDescription__Group_19_2__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==ProjectDependencies||LA26_0==ProjectVersion||LA26_0==ModuleFilters||(LA26_0>=ProjectType && LA26_0<=Application)||LA26_0==VendorName||(LA26_0>=Libraries && LA26_0<=VendorId)||LA26_0==Sources||LA26_0==Content||LA26_0==Output||(LA26_0>=Test && LA26_0<=API)||LA26_0==RULE_ID) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalN4MFParser.g:3459:3: rule__ProjectDescription__Group_19_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_19_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectDescriptionAccess().getGroup_19_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19__2__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_19__3"
    // InternalN4MFParser.g:3467:1: rule__ProjectDescription__Group_19__3 : rule__ProjectDescription__Group_19__3__Impl ;
    public final void rule__ProjectDescription__Group_19__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3471:1: ( rule__ProjectDescription__Group_19__3__Impl )
            // InternalN4MFParser.g:3472:2: rule__ProjectDescription__Group_19__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_19__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19__3"


    // $ANTLR start "rule__ProjectDescription__Group_19__3__Impl"
    // InternalN4MFParser.g:3478:1: rule__ProjectDescription__Group_19__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_19__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3482:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3483:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3483:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3484:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_19_3()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_19_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19__3__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_19_2__0"
    // InternalN4MFParser.g:3494:1: rule__ProjectDescription__Group_19_2__0 : rule__ProjectDescription__Group_19_2__0__Impl rule__ProjectDescription__Group_19_2__1 ;
    public final void rule__ProjectDescription__Group_19_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3498:1: ( rule__ProjectDescription__Group_19_2__0__Impl rule__ProjectDescription__Group_19_2__1 )
            // InternalN4MFParser.g:3499:2: rule__ProjectDescription__Group_19_2__0__Impl rule__ProjectDescription__Group_19_2__1
            {
            pushFollow(FOLLOW_10);
            rule__ProjectDescription__Group_19_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_19_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19_2__0"


    // $ANTLR start "rule__ProjectDescription__Group_19_2__0__Impl"
    // InternalN4MFParser.g:3506:1: rule__ProjectDescription__Group_19_2__0__Impl : ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_19_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3510:1: ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 ) ) )
            // InternalN4MFParser.g:3511:1: ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 ) )
            {
            // InternalN4MFParser.g:3511:1: ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 ) )
            // InternalN4MFParser.g:3512:2: ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19_2_0()); 
            // InternalN4MFParser.g:3513:2: ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 )
            // InternalN4MFParser.g:3513:3: rule__ProjectDescription__TestedProjectsAssignment_19_2_0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__TestedProjectsAssignment_19_2_0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19_2__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_19_2__1"
    // InternalN4MFParser.g:3521:1: rule__ProjectDescription__Group_19_2__1 : rule__ProjectDescription__Group_19_2__1__Impl ;
    public final void rule__ProjectDescription__Group_19_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3525:1: ( rule__ProjectDescription__Group_19_2__1__Impl )
            // InternalN4MFParser.g:3526:2: rule__ProjectDescription__Group_19_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_19_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19_2__1"


    // $ANTLR start "rule__ProjectDescription__Group_19_2__1__Impl"
    // InternalN4MFParser.g:3532:1: rule__ProjectDescription__Group_19_2__1__Impl : ( ( rule__ProjectDescription__Group_19_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_19_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3536:1: ( ( ( rule__ProjectDescription__Group_19_2_1__0 )* ) )
            // InternalN4MFParser.g:3537:1: ( ( rule__ProjectDescription__Group_19_2_1__0 )* )
            {
            // InternalN4MFParser.g:3537:1: ( ( rule__ProjectDescription__Group_19_2_1__0 )* )
            // InternalN4MFParser.g:3538:2: ( rule__ProjectDescription__Group_19_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_19_2_1()); 
            // InternalN4MFParser.g:3539:2: ( rule__ProjectDescription__Group_19_2_1__0 )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==Comma) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalN4MFParser.g:3539:3: rule__ProjectDescription__Group_19_2_1__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ProjectDescription__Group_19_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

             after(grammarAccess.getProjectDescriptionAccess().getGroup_19_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19_2__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_19_2_1__0"
    // InternalN4MFParser.g:3548:1: rule__ProjectDescription__Group_19_2_1__0 : rule__ProjectDescription__Group_19_2_1__0__Impl rule__ProjectDescription__Group_19_2_1__1 ;
    public final void rule__ProjectDescription__Group_19_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3552:1: ( rule__ProjectDescription__Group_19_2_1__0__Impl rule__ProjectDescription__Group_19_2_1__1 )
            // InternalN4MFParser.g:3553:2: rule__ProjectDescription__Group_19_2_1__0__Impl rule__ProjectDescription__Group_19_2_1__1
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDescription__Group_19_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_19_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19_2_1__0"


    // $ANTLR start "rule__ProjectDescription__Group_19_2_1__0__Impl"
    // InternalN4MFParser.g:3560:1: rule__ProjectDescription__Group_19_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_19_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3564:1: ( ( Comma ) )
            // InternalN4MFParser.g:3565:1: ( Comma )
            {
            // InternalN4MFParser.g:3565:1: ( Comma )
            // InternalN4MFParser.g:3566:2: Comma
            {
             before(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_19_2_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getCommaKeyword_19_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19_2_1__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_19_2_1__1"
    // InternalN4MFParser.g:3575:1: rule__ProjectDescription__Group_19_2_1__1 : rule__ProjectDescription__Group_19_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_19_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3579:1: ( rule__ProjectDescription__Group_19_2_1__1__Impl )
            // InternalN4MFParser.g:3580:2: rule__ProjectDescription__Group_19_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_19_2_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19_2_1__1"


    // $ANTLR start "rule__ProjectDescription__Group_19_2_1__1__Impl"
    // InternalN4MFParser.g:3586:1: rule__ProjectDescription__Group_19_2_1__1__Impl : ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_19_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3590:1: ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 ) ) )
            // InternalN4MFParser.g:3591:1: ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 ) )
            {
            // InternalN4MFParser.g:3591:1: ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 ) )
            // InternalN4MFParser.g:3592:2: ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19_2_1_1()); 
            // InternalN4MFParser.g:3593:2: ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 )
            // InternalN4MFParser.g:3593:3: rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_19_2_1__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_20__0"
    // InternalN4MFParser.g:3602:1: rule__ProjectDescription__Group_20__0 : rule__ProjectDescription__Group_20__0__Impl rule__ProjectDescription__Group_20__1 ;
    public final void rule__ProjectDescription__Group_20__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3606:1: ( rule__ProjectDescription__Group_20__0__Impl rule__ProjectDescription__Group_20__1 )
            // InternalN4MFParser.g:3607:2: rule__ProjectDescription__Group_20__0__Impl rule__ProjectDescription__Group_20__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectDescription__Group_20__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_20__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_20__0"


    // $ANTLR start "rule__ProjectDescription__Group_20__0__Impl"
    // InternalN4MFParser.g:3614:1: rule__ProjectDescription__Group_20__0__Impl : ( ModuleLoader ) ;
    public final void rule__ProjectDescription__Group_20__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3618:1: ( ( ModuleLoader ) )
            // InternalN4MFParser.g:3619:1: ( ModuleLoader )
            {
            // InternalN4MFParser.g:3619:1: ( ModuleLoader )
            // InternalN4MFParser.g:3620:2: ModuleLoader
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleLoaderKeyword_20_0()); 
            match(input,ModuleLoader,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getModuleLoaderKeyword_20_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_20__0__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_20__1"
    // InternalN4MFParser.g:3629:1: rule__ProjectDescription__Group_20__1 : rule__ProjectDescription__Group_20__1__Impl rule__ProjectDescription__Group_20__2 ;
    public final void rule__ProjectDescription__Group_20__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3633:1: ( rule__ProjectDescription__Group_20__1__Impl rule__ProjectDescription__Group_20__2 )
            // InternalN4MFParser.g:3634:2: rule__ProjectDescription__Group_20__1__Impl rule__ProjectDescription__Group_20__2
            {
            pushFollow(FOLLOW_19);
            rule__ProjectDescription__Group_20__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_20__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_20__1"


    // $ANTLR start "rule__ProjectDescription__Group_20__1__Impl"
    // InternalN4MFParser.g:3641:1: rule__ProjectDescription__Group_20__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_20__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3645:1: ( ( Colon ) )
            // InternalN4MFParser.g:3646:1: ( Colon )
            {
            // InternalN4MFParser.g:3646:1: ( Colon )
            // InternalN4MFParser.g:3647:2: Colon
            {
             before(grammarAccess.getProjectDescriptionAccess().getColonKeyword_20_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getColonKeyword_20_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_20__1__Impl"


    // $ANTLR start "rule__ProjectDescription__Group_20__2"
    // InternalN4MFParser.g:3656:1: rule__ProjectDescription__Group_20__2 : rule__ProjectDescription__Group_20__2__Impl ;
    public final void rule__ProjectDescription__Group_20__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3660:1: ( rule__ProjectDescription__Group_20__2__Impl )
            // InternalN4MFParser.g:3661:2: rule__ProjectDescription__Group_20__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__Group_20__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_20__2"


    // $ANTLR start "rule__ProjectDescription__Group_20__2__Impl"
    // InternalN4MFParser.g:3667:1: rule__ProjectDescription__Group_20__2__Impl : ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) ) ;
    public final void rule__ProjectDescription__Group_20__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3671:1: ( ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) ) )
            // InternalN4MFParser.g:3672:1: ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) )
            {
            // InternalN4MFParser.g:3672:1: ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) )
            // InternalN4MFParser.g:3673:2: ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleLoaderAssignment_20_2()); 
            // InternalN4MFParser.g:3674:2: ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 )
            // InternalN4MFParser.g:3674:3: rule__ProjectDescription__ModuleLoaderAssignment_20_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ModuleLoaderAssignment_20_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getModuleLoaderAssignment_20_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__Group_20__2__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group__0"
    // InternalN4MFParser.g:3683:1: rule__DeclaredVersion__Group__0 : rule__DeclaredVersion__Group__0__Impl rule__DeclaredVersion__Group__1 ;
    public final void rule__DeclaredVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3687:1: ( rule__DeclaredVersion__Group__0__Impl rule__DeclaredVersion__Group__1 )
            // InternalN4MFParser.g:3688:2: rule__DeclaredVersion__Group__0__Impl rule__DeclaredVersion__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__DeclaredVersion__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group__0"


    // $ANTLR start "rule__DeclaredVersion__Group__0__Impl"
    // InternalN4MFParser.g:3695:1: rule__DeclaredVersion__Group__0__Impl : ( ( rule__DeclaredVersion__MajorAssignment_0 ) ) ;
    public final void rule__DeclaredVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3699:1: ( ( ( rule__DeclaredVersion__MajorAssignment_0 ) ) )
            // InternalN4MFParser.g:3700:1: ( ( rule__DeclaredVersion__MajorAssignment_0 ) )
            {
            // InternalN4MFParser.g:3700:1: ( ( rule__DeclaredVersion__MajorAssignment_0 ) )
            // InternalN4MFParser.g:3701:2: ( rule__DeclaredVersion__MajorAssignment_0 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getMajorAssignment_0()); 
            // InternalN4MFParser.g:3702:2: ( rule__DeclaredVersion__MajorAssignment_0 )
            // InternalN4MFParser.g:3702:3: rule__DeclaredVersion__MajorAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__MajorAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getDeclaredVersionAccess().getMajorAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group__0__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group__1"
    // InternalN4MFParser.g:3710:1: rule__DeclaredVersion__Group__1 : rule__DeclaredVersion__Group__1__Impl rule__DeclaredVersion__Group__2 ;
    public final void rule__DeclaredVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3714:1: ( rule__DeclaredVersion__Group__1__Impl rule__DeclaredVersion__Group__2 )
            // InternalN4MFParser.g:3715:2: rule__DeclaredVersion__Group__1__Impl rule__DeclaredVersion__Group__2
            {
            pushFollow(FOLLOW_20);
            rule__DeclaredVersion__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group__1"


    // $ANTLR start "rule__DeclaredVersion__Group__1__Impl"
    // InternalN4MFParser.g:3722:1: rule__DeclaredVersion__Group__1__Impl : ( ( rule__DeclaredVersion__Group_1__0 )? ) ;
    public final void rule__DeclaredVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3726:1: ( ( ( rule__DeclaredVersion__Group_1__0 )? ) )
            // InternalN4MFParser.g:3727:1: ( ( rule__DeclaredVersion__Group_1__0 )? )
            {
            // InternalN4MFParser.g:3727:1: ( ( rule__DeclaredVersion__Group_1__0 )? )
            // InternalN4MFParser.g:3728:2: ( rule__DeclaredVersion__Group_1__0 )?
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup_1()); 
            // InternalN4MFParser.g:3729:2: ( rule__DeclaredVersion__Group_1__0 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==FullStop) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalN4MFParser.g:3729:3: rule__DeclaredVersion__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DeclaredVersion__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDeclaredVersionAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group__1__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group__2"
    // InternalN4MFParser.g:3737:1: rule__DeclaredVersion__Group__2 : rule__DeclaredVersion__Group__2__Impl ;
    public final void rule__DeclaredVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3741:1: ( rule__DeclaredVersion__Group__2__Impl )
            // InternalN4MFParser.g:3742:2: rule__DeclaredVersion__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group__2"


    // $ANTLR start "rule__DeclaredVersion__Group__2__Impl"
    // InternalN4MFParser.g:3748:1: rule__DeclaredVersion__Group__2__Impl : ( ( rule__DeclaredVersion__Group_2__0 )? ) ;
    public final void rule__DeclaredVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3752:1: ( ( ( rule__DeclaredVersion__Group_2__0 )? ) )
            // InternalN4MFParser.g:3753:1: ( ( rule__DeclaredVersion__Group_2__0 )? )
            {
            // InternalN4MFParser.g:3753:1: ( ( rule__DeclaredVersion__Group_2__0 )? )
            // InternalN4MFParser.g:3754:2: ( rule__DeclaredVersion__Group_2__0 )?
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup_2()); 
            // InternalN4MFParser.g:3755:2: ( rule__DeclaredVersion__Group_2__0 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==HyphenMinus) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalN4MFParser.g:3755:3: rule__DeclaredVersion__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DeclaredVersion__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDeclaredVersionAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group__2__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group_1__0"
    // InternalN4MFParser.g:3764:1: rule__DeclaredVersion__Group_1__0 : rule__DeclaredVersion__Group_1__0__Impl rule__DeclaredVersion__Group_1__1 ;
    public final void rule__DeclaredVersion__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3768:1: ( rule__DeclaredVersion__Group_1__0__Impl rule__DeclaredVersion__Group_1__1 )
            // InternalN4MFParser.g:3769:2: rule__DeclaredVersion__Group_1__0__Impl rule__DeclaredVersion__Group_1__1
            {
            pushFollow(FOLLOW_6);
            rule__DeclaredVersion__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1__0"


    // $ANTLR start "rule__DeclaredVersion__Group_1__0__Impl"
    // InternalN4MFParser.g:3776:1: rule__DeclaredVersion__Group_1__0__Impl : ( FullStop ) ;
    public final void rule__DeclaredVersion__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3780:1: ( ( FullStop ) )
            // InternalN4MFParser.g:3781:1: ( FullStop )
            {
            // InternalN4MFParser.g:3781:1: ( FullStop )
            // InternalN4MFParser.g:3782:2: FullStop
            {
             before(grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_0()); 
            match(input,FullStop,FOLLOW_2); 
             after(grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1__0__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group_1__1"
    // InternalN4MFParser.g:3791:1: rule__DeclaredVersion__Group_1__1 : rule__DeclaredVersion__Group_1__1__Impl rule__DeclaredVersion__Group_1__2 ;
    public final void rule__DeclaredVersion__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3795:1: ( rule__DeclaredVersion__Group_1__1__Impl rule__DeclaredVersion__Group_1__2 )
            // InternalN4MFParser.g:3796:2: rule__DeclaredVersion__Group_1__1__Impl rule__DeclaredVersion__Group_1__2
            {
            pushFollow(FOLLOW_21);
            rule__DeclaredVersion__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1__1"


    // $ANTLR start "rule__DeclaredVersion__Group_1__1__Impl"
    // InternalN4MFParser.g:3803:1: rule__DeclaredVersion__Group_1__1__Impl : ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) ) ;
    public final void rule__DeclaredVersion__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3807:1: ( ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) ) )
            // InternalN4MFParser.g:3808:1: ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) )
            {
            // InternalN4MFParser.g:3808:1: ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) )
            // InternalN4MFParser.g:3809:2: ( rule__DeclaredVersion__MinorAssignment_1_1 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getMinorAssignment_1_1()); 
            // InternalN4MFParser.g:3810:2: ( rule__DeclaredVersion__MinorAssignment_1_1 )
            // InternalN4MFParser.g:3810:3: rule__DeclaredVersion__MinorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__MinorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getDeclaredVersionAccess().getMinorAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1__1__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group_1__2"
    // InternalN4MFParser.g:3818:1: rule__DeclaredVersion__Group_1__2 : rule__DeclaredVersion__Group_1__2__Impl ;
    public final void rule__DeclaredVersion__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3822:1: ( rule__DeclaredVersion__Group_1__2__Impl )
            // InternalN4MFParser.g:3823:2: rule__DeclaredVersion__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1__2"


    // $ANTLR start "rule__DeclaredVersion__Group_1__2__Impl"
    // InternalN4MFParser.g:3829:1: rule__DeclaredVersion__Group_1__2__Impl : ( ( rule__DeclaredVersion__Group_1_2__0 )? ) ;
    public final void rule__DeclaredVersion__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3833:1: ( ( ( rule__DeclaredVersion__Group_1_2__0 )? ) )
            // InternalN4MFParser.g:3834:1: ( ( rule__DeclaredVersion__Group_1_2__0 )? )
            {
            // InternalN4MFParser.g:3834:1: ( ( rule__DeclaredVersion__Group_1_2__0 )? )
            // InternalN4MFParser.g:3835:2: ( rule__DeclaredVersion__Group_1_2__0 )?
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup_1_2()); 
            // InternalN4MFParser.g:3836:2: ( rule__DeclaredVersion__Group_1_2__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==FullStop) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalN4MFParser.g:3836:3: rule__DeclaredVersion__Group_1_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DeclaredVersion__Group_1_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDeclaredVersionAccess().getGroup_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1__2__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group_1_2__0"
    // InternalN4MFParser.g:3845:1: rule__DeclaredVersion__Group_1_2__0 : rule__DeclaredVersion__Group_1_2__0__Impl rule__DeclaredVersion__Group_1_2__1 ;
    public final void rule__DeclaredVersion__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3849:1: ( rule__DeclaredVersion__Group_1_2__0__Impl rule__DeclaredVersion__Group_1_2__1 )
            // InternalN4MFParser.g:3850:2: rule__DeclaredVersion__Group_1_2__0__Impl rule__DeclaredVersion__Group_1_2__1
            {
            pushFollow(FOLLOW_6);
            rule__DeclaredVersion__Group_1_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group_1_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1_2__0"


    // $ANTLR start "rule__DeclaredVersion__Group_1_2__0__Impl"
    // InternalN4MFParser.g:3857:1: rule__DeclaredVersion__Group_1_2__0__Impl : ( FullStop ) ;
    public final void rule__DeclaredVersion__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3861:1: ( ( FullStop ) )
            // InternalN4MFParser.g:3862:1: ( FullStop )
            {
            // InternalN4MFParser.g:3862:1: ( FullStop )
            // InternalN4MFParser.g:3863:2: FullStop
            {
             before(grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_2_0()); 
            match(input,FullStop,FOLLOW_2); 
             after(grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1_2__0__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group_1_2__1"
    // InternalN4MFParser.g:3872:1: rule__DeclaredVersion__Group_1_2__1 : rule__DeclaredVersion__Group_1_2__1__Impl ;
    public final void rule__DeclaredVersion__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3876:1: ( rule__DeclaredVersion__Group_1_2__1__Impl )
            // InternalN4MFParser.g:3877:2: rule__DeclaredVersion__Group_1_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group_1_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1_2__1"


    // $ANTLR start "rule__DeclaredVersion__Group_1_2__1__Impl"
    // InternalN4MFParser.g:3883:1: rule__DeclaredVersion__Group_1_2__1__Impl : ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) ) ;
    public final void rule__DeclaredVersion__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3887:1: ( ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) ) )
            // InternalN4MFParser.g:3888:1: ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) )
            {
            // InternalN4MFParser.g:3888:1: ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) )
            // InternalN4MFParser.g:3889:2: ( rule__DeclaredVersion__MicroAssignment_1_2_1 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getMicroAssignment_1_2_1()); 
            // InternalN4MFParser.g:3890:2: ( rule__DeclaredVersion__MicroAssignment_1_2_1 )
            // InternalN4MFParser.g:3890:3: rule__DeclaredVersion__MicroAssignment_1_2_1
            {
            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__MicroAssignment_1_2_1();

            state._fsp--;


            }

             after(grammarAccess.getDeclaredVersionAccess().getMicroAssignment_1_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_1_2__1__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group_2__0"
    // InternalN4MFParser.g:3899:1: rule__DeclaredVersion__Group_2__0 : rule__DeclaredVersion__Group_2__0__Impl rule__DeclaredVersion__Group_2__1 ;
    public final void rule__DeclaredVersion__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3903:1: ( rule__DeclaredVersion__Group_2__0__Impl rule__DeclaredVersion__Group_2__1 )
            // InternalN4MFParser.g:3904:2: rule__DeclaredVersion__Group_2__0__Impl rule__DeclaredVersion__Group_2__1
            {
            pushFollow(FOLLOW_4);
            rule__DeclaredVersion__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_2__0"


    // $ANTLR start "rule__DeclaredVersion__Group_2__0__Impl"
    // InternalN4MFParser.g:3911:1: rule__DeclaredVersion__Group_2__0__Impl : ( HyphenMinus ) ;
    public final void rule__DeclaredVersion__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3915:1: ( ( HyphenMinus ) )
            // InternalN4MFParser.g:3916:1: ( HyphenMinus )
            {
            // InternalN4MFParser.g:3916:1: ( HyphenMinus )
            // InternalN4MFParser.g:3917:2: HyphenMinus
            {
             before(grammarAccess.getDeclaredVersionAccess().getHyphenMinusKeyword_2_0()); 
            match(input,HyphenMinus,FOLLOW_2); 
             after(grammarAccess.getDeclaredVersionAccess().getHyphenMinusKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_2__0__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group_2__1"
    // InternalN4MFParser.g:3926:1: rule__DeclaredVersion__Group_2__1 : rule__DeclaredVersion__Group_2__1__Impl ;
    public final void rule__DeclaredVersion__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3930:1: ( rule__DeclaredVersion__Group_2__1__Impl )
            // InternalN4MFParser.g:3931:2: rule__DeclaredVersion__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_2__1"


    // $ANTLR start "rule__DeclaredVersion__Group_2__1__Impl"
    // InternalN4MFParser.g:3937:1: rule__DeclaredVersion__Group_2__1__Impl : ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) ) ;
    public final void rule__DeclaredVersion__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3941:1: ( ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) ) )
            // InternalN4MFParser.g:3942:1: ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) )
            {
            // InternalN4MFParser.g:3942:1: ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) )
            // InternalN4MFParser.g:3943:2: ( rule__DeclaredVersion__QualifierAssignment_2_1 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getQualifierAssignment_2_1()); 
            // InternalN4MFParser.g:3944:2: ( rule__DeclaredVersion__QualifierAssignment_2_1 )
            // InternalN4MFParser.g:3944:3: rule__DeclaredVersion__QualifierAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__DeclaredVersion__QualifierAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getDeclaredVersionAccess().getQualifierAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__Group_2__1__Impl"


    // $ANTLR start "rule__SourceFragment__Group__0"
    // InternalN4MFParser.g:3953:1: rule__SourceFragment__Group__0 : rule__SourceFragment__Group__0__Impl rule__SourceFragment__Group__1 ;
    public final void rule__SourceFragment__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3957:1: ( rule__SourceFragment__Group__0__Impl rule__SourceFragment__Group__1 )
            // InternalN4MFParser.g:3958:2: rule__SourceFragment__Group__0__Impl rule__SourceFragment__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__SourceFragment__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SourceFragment__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__0"


    // $ANTLR start "rule__SourceFragment__Group__0__Impl"
    // InternalN4MFParser.g:3965:1: rule__SourceFragment__Group__0__Impl : ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) ) ;
    public final void rule__SourceFragment__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3969:1: ( ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) ) )
            // InternalN4MFParser.g:3970:1: ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) )
            {
            // InternalN4MFParser.g:3970:1: ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) )
            // InternalN4MFParser.g:3971:2: ( rule__SourceFragment__SourceFragmentTypeAssignment_0 )
            {
             before(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeAssignment_0()); 
            // InternalN4MFParser.g:3972:2: ( rule__SourceFragment__SourceFragmentTypeAssignment_0 )
            // InternalN4MFParser.g:3972:3: rule__SourceFragment__SourceFragmentTypeAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__SourceFragment__SourceFragmentTypeAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__0__Impl"


    // $ANTLR start "rule__SourceFragment__Group__1"
    // InternalN4MFParser.g:3980:1: rule__SourceFragment__Group__1 : rule__SourceFragment__Group__1__Impl rule__SourceFragment__Group__2 ;
    public final void rule__SourceFragment__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3984:1: ( rule__SourceFragment__Group__1__Impl rule__SourceFragment__Group__2 )
            // InternalN4MFParser.g:3985:2: rule__SourceFragment__Group__1__Impl rule__SourceFragment__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__SourceFragment__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SourceFragment__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__1"


    // $ANTLR start "rule__SourceFragment__Group__1__Impl"
    // InternalN4MFParser.g:3992:1: rule__SourceFragment__Group__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__SourceFragment__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3996:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3997:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3997:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3998:2: LeftCurlyBracket
            {
             before(grammarAccess.getSourceFragmentAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getSourceFragmentAccess().getLeftCurlyBracketKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__1__Impl"


    // $ANTLR start "rule__SourceFragment__Group__2"
    // InternalN4MFParser.g:4007:1: rule__SourceFragment__Group__2 : rule__SourceFragment__Group__2__Impl rule__SourceFragment__Group__3 ;
    public final void rule__SourceFragment__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4011:1: ( rule__SourceFragment__Group__2__Impl rule__SourceFragment__Group__3 )
            // InternalN4MFParser.g:4012:2: rule__SourceFragment__Group__2__Impl rule__SourceFragment__Group__3
            {
            pushFollow(FOLLOW_13);
            rule__SourceFragment__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SourceFragment__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__2"


    // $ANTLR start "rule__SourceFragment__Group__2__Impl"
    // InternalN4MFParser.g:4019:1: rule__SourceFragment__Group__2__Impl : ( ( rule__SourceFragment__PathsRawAssignment_2 ) ) ;
    public final void rule__SourceFragment__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4023:1: ( ( ( rule__SourceFragment__PathsRawAssignment_2 ) ) )
            // InternalN4MFParser.g:4024:1: ( ( rule__SourceFragment__PathsRawAssignment_2 ) )
            {
            // InternalN4MFParser.g:4024:1: ( ( rule__SourceFragment__PathsRawAssignment_2 ) )
            // InternalN4MFParser.g:4025:2: ( rule__SourceFragment__PathsRawAssignment_2 )
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_2()); 
            // InternalN4MFParser.g:4026:2: ( rule__SourceFragment__PathsRawAssignment_2 )
            // InternalN4MFParser.g:4026:3: rule__SourceFragment__PathsRawAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__SourceFragment__PathsRawAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__2__Impl"


    // $ANTLR start "rule__SourceFragment__Group__3"
    // InternalN4MFParser.g:4034:1: rule__SourceFragment__Group__3 : rule__SourceFragment__Group__3__Impl rule__SourceFragment__Group__4 ;
    public final void rule__SourceFragment__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4038:1: ( rule__SourceFragment__Group__3__Impl rule__SourceFragment__Group__4 )
            // InternalN4MFParser.g:4039:2: rule__SourceFragment__Group__3__Impl rule__SourceFragment__Group__4
            {
            pushFollow(FOLLOW_13);
            rule__SourceFragment__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SourceFragment__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__3"


    // $ANTLR start "rule__SourceFragment__Group__3__Impl"
    // InternalN4MFParser.g:4046:1: rule__SourceFragment__Group__3__Impl : ( ( rule__SourceFragment__Group_3__0 )* ) ;
    public final void rule__SourceFragment__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4050:1: ( ( ( rule__SourceFragment__Group_3__0 )* ) )
            // InternalN4MFParser.g:4051:1: ( ( rule__SourceFragment__Group_3__0 )* )
            {
            // InternalN4MFParser.g:4051:1: ( ( rule__SourceFragment__Group_3__0 )* )
            // InternalN4MFParser.g:4052:2: ( rule__SourceFragment__Group_3__0 )*
            {
             before(grammarAccess.getSourceFragmentAccess().getGroup_3()); 
            // InternalN4MFParser.g:4053:2: ( rule__SourceFragment__Group_3__0 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==Comma) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalN4MFParser.g:4053:3: rule__SourceFragment__Group_3__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__SourceFragment__Group_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

             after(grammarAccess.getSourceFragmentAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__3__Impl"


    // $ANTLR start "rule__SourceFragment__Group__4"
    // InternalN4MFParser.g:4061:1: rule__SourceFragment__Group__4 : rule__SourceFragment__Group__4__Impl ;
    public final void rule__SourceFragment__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4065:1: ( rule__SourceFragment__Group__4__Impl )
            // InternalN4MFParser.g:4066:2: rule__SourceFragment__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SourceFragment__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__4"


    // $ANTLR start "rule__SourceFragment__Group__4__Impl"
    // InternalN4MFParser.g:4072:1: rule__SourceFragment__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__SourceFragment__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4076:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:4077:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:4077:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:4078:2: RightCurlyBracket
            {
             before(grammarAccess.getSourceFragmentAccess().getRightCurlyBracketKeyword_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getSourceFragmentAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group__4__Impl"


    // $ANTLR start "rule__SourceFragment__Group_3__0"
    // InternalN4MFParser.g:4088:1: rule__SourceFragment__Group_3__0 : rule__SourceFragment__Group_3__0__Impl rule__SourceFragment__Group_3__1 ;
    public final void rule__SourceFragment__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4092:1: ( rule__SourceFragment__Group_3__0__Impl rule__SourceFragment__Group_3__1 )
            // InternalN4MFParser.g:4093:2: rule__SourceFragment__Group_3__0__Impl rule__SourceFragment__Group_3__1
            {
            pushFollow(FOLLOW_7);
            rule__SourceFragment__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SourceFragment__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group_3__0"


    // $ANTLR start "rule__SourceFragment__Group_3__0__Impl"
    // InternalN4MFParser.g:4100:1: rule__SourceFragment__Group_3__0__Impl : ( Comma ) ;
    public final void rule__SourceFragment__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4104:1: ( ( Comma ) )
            // InternalN4MFParser.g:4105:1: ( Comma )
            {
            // InternalN4MFParser.g:4105:1: ( Comma )
            // InternalN4MFParser.g:4106:2: Comma
            {
             before(grammarAccess.getSourceFragmentAccess().getCommaKeyword_3_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getSourceFragmentAccess().getCommaKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group_3__0__Impl"


    // $ANTLR start "rule__SourceFragment__Group_3__1"
    // InternalN4MFParser.g:4115:1: rule__SourceFragment__Group_3__1 : rule__SourceFragment__Group_3__1__Impl ;
    public final void rule__SourceFragment__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4119:1: ( rule__SourceFragment__Group_3__1__Impl )
            // InternalN4MFParser.g:4120:2: rule__SourceFragment__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SourceFragment__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group_3__1"


    // $ANTLR start "rule__SourceFragment__Group_3__1__Impl"
    // InternalN4MFParser.g:4126:1: rule__SourceFragment__Group_3__1__Impl : ( ( rule__SourceFragment__PathsRawAssignment_3_1 ) ) ;
    public final void rule__SourceFragment__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4130:1: ( ( ( rule__SourceFragment__PathsRawAssignment_3_1 ) ) )
            // InternalN4MFParser.g:4131:1: ( ( rule__SourceFragment__PathsRawAssignment_3_1 ) )
            {
            // InternalN4MFParser.g:4131:1: ( ( rule__SourceFragment__PathsRawAssignment_3_1 ) )
            // InternalN4MFParser.g:4132:2: ( rule__SourceFragment__PathsRawAssignment_3_1 )
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_3_1()); 
            // InternalN4MFParser.g:4133:2: ( rule__SourceFragment__PathsRawAssignment_3_1 )
            // InternalN4MFParser.g:4133:3: rule__SourceFragment__PathsRawAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__SourceFragment__PathsRawAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__Group_3__1__Impl"


    // $ANTLR start "rule__ModuleFilter__Group__0"
    // InternalN4MFParser.g:4142:1: rule__ModuleFilter__Group__0 : rule__ModuleFilter__Group__0__Impl rule__ModuleFilter__Group__1 ;
    public final void rule__ModuleFilter__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4146:1: ( rule__ModuleFilter__Group__0__Impl rule__ModuleFilter__Group__1 )
            // InternalN4MFParser.g:4147:2: rule__ModuleFilter__Group__0__Impl rule__ModuleFilter__Group__1
            {
            pushFollow(FOLLOW_8);
            rule__ModuleFilter__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ModuleFilter__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__0"


    // $ANTLR start "rule__ModuleFilter__Group__0__Impl"
    // InternalN4MFParser.g:4154:1: rule__ModuleFilter__Group__0__Impl : ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) ) ;
    public final void rule__ModuleFilter__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4158:1: ( ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) ) )
            // InternalN4MFParser.g:4159:1: ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) )
            {
            // InternalN4MFParser.g:4159:1: ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) )
            // InternalN4MFParser.g:4160:2: ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 )
            {
             before(grammarAccess.getModuleFilterAccess().getModuleFilterTypeAssignment_0()); 
            // InternalN4MFParser.g:4161:2: ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 )
            // InternalN4MFParser.g:4161:3: rule__ModuleFilter__ModuleFilterTypeAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilter__ModuleFilterTypeAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getModuleFilterAccess().getModuleFilterTypeAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__0__Impl"


    // $ANTLR start "rule__ModuleFilter__Group__1"
    // InternalN4MFParser.g:4169:1: rule__ModuleFilter__Group__1 : rule__ModuleFilter__Group__1__Impl rule__ModuleFilter__Group__2 ;
    public final void rule__ModuleFilter__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4173:1: ( rule__ModuleFilter__Group__1__Impl rule__ModuleFilter__Group__2 )
            // InternalN4MFParser.g:4174:2: rule__ModuleFilter__Group__1__Impl rule__ModuleFilter__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__ModuleFilter__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ModuleFilter__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__1"


    // $ANTLR start "rule__ModuleFilter__Group__1__Impl"
    // InternalN4MFParser.g:4181:1: rule__ModuleFilter__Group__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ModuleFilter__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4185:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:4186:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:4186:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:4187:2: LeftCurlyBracket
            {
             before(grammarAccess.getModuleFilterAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getModuleFilterAccess().getLeftCurlyBracketKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__1__Impl"


    // $ANTLR start "rule__ModuleFilter__Group__2"
    // InternalN4MFParser.g:4196:1: rule__ModuleFilter__Group__2 : rule__ModuleFilter__Group__2__Impl rule__ModuleFilter__Group__3 ;
    public final void rule__ModuleFilter__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4200:1: ( rule__ModuleFilter__Group__2__Impl rule__ModuleFilter__Group__3 )
            // InternalN4MFParser.g:4201:2: rule__ModuleFilter__Group__2__Impl rule__ModuleFilter__Group__3
            {
            pushFollow(FOLLOW_13);
            rule__ModuleFilter__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ModuleFilter__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__2"


    // $ANTLR start "rule__ModuleFilter__Group__2__Impl"
    // InternalN4MFParser.g:4208:1: rule__ModuleFilter__Group__2__Impl : ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) ) ;
    public final void rule__ModuleFilter__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4212:1: ( ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) ) )
            // InternalN4MFParser.g:4213:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) )
            {
            // InternalN4MFParser.g:4213:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) )
            // InternalN4MFParser.g:4214:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 )
            {
             before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_2()); 
            // InternalN4MFParser.g:4215:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 )
            // InternalN4MFParser.g:4215:3: rule__ModuleFilter__ModuleSpecifiersAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilter__ModuleSpecifiersAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__2__Impl"


    // $ANTLR start "rule__ModuleFilter__Group__3"
    // InternalN4MFParser.g:4223:1: rule__ModuleFilter__Group__3 : rule__ModuleFilter__Group__3__Impl rule__ModuleFilter__Group__4 ;
    public final void rule__ModuleFilter__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4227:1: ( rule__ModuleFilter__Group__3__Impl rule__ModuleFilter__Group__4 )
            // InternalN4MFParser.g:4228:2: rule__ModuleFilter__Group__3__Impl rule__ModuleFilter__Group__4
            {
            pushFollow(FOLLOW_13);
            rule__ModuleFilter__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ModuleFilter__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__3"


    // $ANTLR start "rule__ModuleFilter__Group__3__Impl"
    // InternalN4MFParser.g:4235:1: rule__ModuleFilter__Group__3__Impl : ( ( rule__ModuleFilter__Group_3__0 )* ) ;
    public final void rule__ModuleFilter__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4239:1: ( ( ( rule__ModuleFilter__Group_3__0 )* ) )
            // InternalN4MFParser.g:4240:1: ( ( rule__ModuleFilter__Group_3__0 )* )
            {
            // InternalN4MFParser.g:4240:1: ( ( rule__ModuleFilter__Group_3__0 )* )
            // InternalN4MFParser.g:4241:2: ( rule__ModuleFilter__Group_3__0 )*
            {
             before(grammarAccess.getModuleFilterAccess().getGroup_3()); 
            // InternalN4MFParser.g:4242:2: ( rule__ModuleFilter__Group_3__0 )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==Comma) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalN4MFParser.g:4242:3: rule__ModuleFilter__Group_3__0
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__ModuleFilter__Group_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);

             after(grammarAccess.getModuleFilterAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__3__Impl"


    // $ANTLR start "rule__ModuleFilter__Group__4"
    // InternalN4MFParser.g:4250:1: rule__ModuleFilter__Group__4 : rule__ModuleFilter__Group__4__Impl ;
    public final void rule__ModuleFilter__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4254:1: ( rule__ModuleFilter__Group__4__Impl )
            // InternalN4MFParser.g:4255:2: rule__ModuleFilter__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilter__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__4"


    // $ANTLR start "rule__ModuleFilter__Group__4__Impl"
    // InternalN4MFParser.g:4261:1: rule__ModuleFilter__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ModuleFilter__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4265:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:4266:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:4266:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:4267:2: RightCurlyBracket
            {
             before(grammarAccess.getModuleFilterAccess().getRightCurlyBracketKeyword_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getModuleFilterAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group__4__Impl"


    // $ANTLR start "rule__ModuleFilter__Group_3__0"
    // InternalN4MFParser.g:4277:1: rule__ModuleFilter__Group_3__0 : rule__ModuleFilter__Group_3__0__Impl rule__ModuleFilter__Group_3__1 ;
    public final void rule__ModuleFilter__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4281:1: ( rule__ModuleFilter__Group_3__0__Impl rule__ModuleFilter__Group_3__1 )
            // InternalN4MFParser.g:4282:2: rule__ModuleFilter__Group_3__0__Impl rule__ModuleFilter__Group_3__1
            {
            pushFollow(FOLLOW_7);
            rule__ModuleFilter__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ModuleFilter__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group_3__0"


    // $ANTLR start "rule__ModuleFilter__Group_3__0__Impl"
    // InternalN4MFParser.g:4289:1: rule__ModuleFilter__Group_3__0__Impl : ( Comma ) ;
    public final void rule__ModuleFilter__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4293:1: ( ( Comma ) )
            // InternalN4MFParser.g:4294:1: ( Comma )
            {
            // InternalN4MFParser.g:4294:1: ( Comma )
            // InternalN4MFParser.g:4295:2: Comma
            {
             before(grammarAccess.getModuleFilterAccess().getCommaKeyword_3_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getModuleFilterAccess().getCommaKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group_3__0__Impl"


    // $ANTLR start "rule__ModuleFilter__Group_3__1"
    // InternalN4MFParser.g:4304:1: rule__ModuleFilter__Group_3__1 : rule__ModuleFilter__Group_3__1__Impl ;
    public final void rule__ModuleFilter__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4308:1: ( rule__ModuleFilter__Group_3__1__Impl )
            // InternalN4MFParser.g:4309:2: rule__ModuleFilter__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilter__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group_3__1"


    // $ANTLR start "rule__ModuleFilter__Group_3__1__Impl"
    // InternalN4MFParser.g:4315:1: rule__ModuleFilter__Group_3__1__Impl : ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) ) ;
    public final void rule__ModuleFilter__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4319:1: ( ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) ) )
            // InternalN4MFParser.g:4320:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) )
            {
            // InternalN4MFParser.g:4320:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) )
            // InternalN4MFParser.g:4321:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 )
            {
             before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_3_1()); 
            // InternalN4MFParser.g:4322:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 )
            // InternalN4MFParser.g:4322:3: rule__ModuleFilter__ModuleSpecifiersAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilter__ModuleSpecifiersAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__Group_3__1__Impl"


    // $ANTLR start "rule__BootstrapModule__Group__0"
    // InternalN4MFParser.g:4331:1: rule__BootstrapModule__Group__0 : rule__BootstrapModule__Group__0__Impl rule__BootstrapModule__Group__1 ;
    public final void rule__BootstrapModule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4335:1: ( rule__BootstrapModule__Group__0__Impl rule__BootstrapModule__Group__1 )
            // InternalN4MFParser.g:4336:2: rule__BootstrapModule__Group__0__Impl rule__BootstrapModule__Group__1
            {
            pushFollow(FOLLOW_22);
            rule__BootstrapModule__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__BootstrapModule__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__Group__0"


    // $ANTLR start "rule__BootstrapModule__Group__0__Impl"
    // InternalN4MFParser.g:4343:1: rule__BootstrapModule__Group__0__Impl : ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) ) ;
    public final void rule__BootstrapModule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4347:1: ( ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) ) )
            // InternalN4MFParser.g:4348:1: ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) )
            {
            // InternalN4MFParser.g:4348:1: ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) )
            // InternalN4MFParser.g:4349:2: ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 )
            {
             before(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardAssignment_0()); 
            // InternalN4MFParser.g:4350:2: ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 )
            // InternalN4MFParser.g:4350:3: rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__Group__0__Impl"


    // $ANTLR start "rule__BootstrapModule__Group__1"
    // InternalN4MFParser.g:4358:1: rule__BootstrapModule__Group__1 : rule__BootstrapModule__Group__1__Impl ;
    public final void rule__BootstrapModule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4362:1: ( rule__BootstrapModule__Group__1__Impl )
            // InternalN4MFParser.g:4363:2: rule__BootstrapModule__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__BootstrapModule__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__Group__1"


    // $ANTLR start "rule__BootstrapModule__Group__1__Impl"
    // InternalN4MFParser.g:4369:1: rule__BootstrapModule__Group__1__Impl : ( ( rule__BootstrapModule__Group_1__0 )? ) ;
    public final void rule__BootstrapModule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4373:1: ( ( ( rule__BootstrapModule__Group_1__0 )? ) )
            // InternalN4MFParser.g:4374:1: ( ( rule__BootstrapModule__Group_1__0 )? )
            {
            // InternalN4MFParser.g:4374:1: ( ( rule__BootstrapModule__Group_1__0 )? )
            // InternalN4MFParser.g:4375:2: ( rule__BootstrapModule__Group_1__0 )?
            {
             before(grammarAccess.getBootstrapModuleAccess().getGroup_1()); 
            // InternalN4MFParser.g:4376:2: ( rule__BootstrapModule__Group_1__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==In) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalN4MFParser.g:4376:3: rule__BootstrapModule__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__BootstrapModule__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getBootstrapModuleAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__Group__1__Impl"


    // $ANTLR start "rule__BootstrapModule__Group_1__0"
    // InternalN4MFParser.g:4385:1: rule__BootstrapModule__Group_1__0 : rule__BootstrapModule__Group_1__0__Impl rule__BootstrapModule__Group_1__1 ;
    public final void rule__BootstrapModule__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4389:1: ( rule__BootstrapModule__Group_1__0__Impl rule__BootstrapModule__Group_1__1 )
            // InternalN4MFParser.g:4390:2: rule__BootstrapModule__Group_1__0__Impl rule__BootstrapModule__Group_1__1
            {
            pushFollow(FOLLOW_7);
            rule__BootstrapModule__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__BootstrapModule__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__Group_1__0"


    // $ANTLR start "rule__BootstrapModule__Group_1__0__Impl"
    // InternalN4MFParser.g:4397:1: rule__BootstrapModule__Group_1__0__Impl : ( In ) ;
    public final void rule__BootstrapModule__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4401:1: ( ( In ) )
            // InternalN4MFParser.g:4402:1: ( In )
            {
            // InternalN4MFParser.g:4402:1: ( In )
            // InternalN4MFParser.g:4403:2: In
            {
             before(grammarAccess.getBootstrapModuleAccess().getInKeyword_1_0()); 
            match(input,In,FOLLOW_2); 
             after(grammarAccess.getBootstrapModuleAccess().getInKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__Group_1__0__Impl"


    // $ANTLR start "rule__BootstrapModule__Group_1__1"
    // InternalN4MFParser.g:4412:1: rule__BootstrapModule__Group_1__1 : rule__BootstrapModule__Group_1__1__Impl ;
    public final void rule__BootstrapModule__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4416:1: ( rule__BootstrapModule__Group_1__1__Impl )
            // InternalN4MFParser.g:4417:2: rule__BootstrapModule__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__BootstrapModule__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__Group_1__1"


    // $ANTLR start "rule__BootstrapModule__Group_1__1__Impl"
    // InternalN4MFParser.g:4423:1: rule__BootstrapModule__Group_1__1__Impl : ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) ) ;
    public final void rule__BootstrapModule__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4427:1: ( ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) ) )
            // InternalN4MFParser.g:4428:1: ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) )
            {
            // InternalN4MFParser.g:4428:1: ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) )
            // InternalN4MFParser.g:4429:2: ( rule__BootstrapModule__SourcePathAssignment_1_1 )
            {
             before(grammarAccess.getBootstrapModuleAccess().getSourcePathAssignment_1_1()); 
            // InternalN4MFParser.g:4430:2: ( rule__BootstrapModule__SourcePathAssignment_1_1 )
            // InternalN4MFParser.g:4430:3: rule__BootstrapModule__SourcePathAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__BootstrapModule__SourcePathAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getBootstrapModuleAccess().getSourcePathAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__Group_1__1__Impl"


    // $ANTLR start "rule__ModuleFilterSpecifier__Group__0"
    // InternalN4MFParser.g:4439:1: rule__ModuleFilterSpecifier__Group__0 : rule__ModuleFilterSpecifier__Group__0__Impl rule__ModuleFilterSpecifier__Group__1 ;
    public final void rule__ModuleFilterSpecifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4443:1: ( rule__ModuleFilterSpecifier__Group__0__Impl rule__ModuleFilterSpecifier__Group__1 )
            // InternalN4MFParser.g:4444:2: rule__ModuleFilterSpecifier__Group__0__Impl rule__ModuleFilterSpecifier__Group__1
            {
            pushFollow(FOLLOW_22);
            rule__ModuleFilterSpecifier__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ModuleFilterSpecifier__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__Group__0"


    // $ANTLR start "rule__ModuleFilterSpecifier__Group__0__Impl"
    // InternalN4MFParser.g:4451:1: rule__ModuleFilterSpecifier__Group__0__Impl : ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) ) ;
    public final void rule__ModuleFilterSpecifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4455:1: ( ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) ) )
            // InternalN4MFParser.g:4456:1: ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) )
            {
            // InternalN4MFParser.g:4456:1: ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) )
            // InternalN4MFParser.g:4457:2: ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 )
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardAssignment_0()); 
            // InternalN4MFParser.g:4458:2: ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 )
            // InternalN4MFParser.g:4458:3: rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__Group__0__Impl"


    // $ANTLR start "rule__ModuleFilterSpecifier__Group__1"
    // InternalN4MFParser.g:4466:1: rule__ModuleFilterSpecifier__Group__1 : rule__ModuleFilterSpecifier__Group__1__Impl ;
    public final void rule__ModuleFilterSpecifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4470:1: ( rule__ModuleFilterSpecifier__Group__1__Impl )
            // InternalN4MFParser.g:4471:2: rule__ModuleFilterSpecifier__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilterSpecifier__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__Group__1"


    // $ANTLR start "rule__ModuleFilterSpecifier__Group__1__Impl"
    // InternalN4MFParser.g:4477:1: rule__ModuleFilterSpecifier__Group__1__Impl : ( ( rule__ModuleFilterSpecifier__Group_1__0 )? ) ;
    public final void rule__ModuleFilterSpecifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4481:1: ( ( ( rule__ModuleFilterSpecifier__Group_1__0 )? ) )
            // InternalN4MFParser.g:4482:1: ( ( rule__ModuleFilterSpecifier__Group_1__0 )? )
            {
            // InternalN4MFParser.g:4482:1: ( ( rule__ModuleFilterSpecifier__Group_1__0 )? )
            // InternalN4MFParser.g:4483:2: ( rule__ModuleFilterSpecifier__Group_1__0 )?
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getGroup_1()); 
            // InternalN4MFParser.g:4484:2: ( rule__ModuleFilterSpecifier__Group_1__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==In) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalN4MFParser.g:4484:3: rule__ModuleFilterSpecifier__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ModuleFilterSpecifier__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getModuleFilterSpecifierAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__Group__1__Impl"


    // $ANTLR start "rule__ModuleFilterSpecifier__Group_1__0"
    // InternalN4MFParser.g:4493:1: rule__ModuleFilterSpecifier__Group_1__0 : rule__ModuleFilterSpecifier__Group_1__0__Impl rule__ModuleFilterSpecifier__Group_1__1 ;
    public final void rule__ModuleFilterSpecifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4497:1: ( rule__ModuleFilterSpecifier__Group_1__0__Impl rule__ModuleFilterSpecifier__Group_1__1 )
            // InternalN4MFParser.g:4498:2: rule__ModuleFilterSpecifier__Group_1__0__Impl rule__ModuleFilterSpecifier__Group_1__1
            {
            pushFollow(FOLLOW_7);
            rule__ModuleFilterSpecifier__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ModuleFilterSpecifier__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__Group_1__0"


    // $ANTLR start "rule__ModuleFilterSpecifier__Group_1__0__Impl"
    // InternalN4MFParser.g:4505:1: rule__ModuleFilterSpecifier__Group_1__0__Impl : ( In ) ;
    public final void rule__ModuleFilterSpecifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4509:1: ( ( In ) )
            // InternalN4MFParser.g:4510:1: ( In )
            {
            // InternalN4MFParser.g:4510:1: ( In )
            // InternalN4MFParser.g:4511:2: In
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getInKeyword_1_0()); 
            match(input,In,FOLLOW_2); 
             after(grammarAccess.getModuleFilterSpecifierAccess().getInKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__Group_1__0__Impl"


    // $ANTLR start "rule__ModuleFilterSpecifier__Group_1__1"
    // InternalN4MFParser.g:4520:1: rule__ModuleFilterSpecifier__Group_1__1 : rule__ModuleFilterSpecifier__Group_1__1__Impl ;
    public final void rule__ModuleFilterSpecifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4524:1: ( rule__ModuleFilterSpecifier__Group_1__1__Impl )
            // InternalN4MFParser.g:4525:2: rule__ModuleFilterSpecifier__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilterSpecifier__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__Group_1__1"


    // $ANTLR start "rule__ModuleFilterSpecifier__Group_1__1__Impl"
    // InternalN4MFParser.g:4531:1: rule__ModuleFilterSpecifier__Group_1__1__Impl : ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) ) ;
    public final void rule__ModuleFilterSpecifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4535:1: ( ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) ) )
            // InternalN4MFParser.g:4536:1: ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) )
            {
            // InternalN4MFParser.g:4536:1: ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) )
            // InternalN4MFParser.g:4537:2: ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 )
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathAssignment_1_1()); 
            // InternalN4MFParser.g:4538:2: ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 )
            // InternalN4MFParser.g:4538:3: rule__ModuleFilterSpecifier__SourcePathAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ModuleFilterSpecifier__SourcePathAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__Group_1__1__Impl"


    // $ANTLR start "rule__ProjectDependency__Group__0"
    // InternalN4MFParser.g:4547:1: rule__ProjectDependency__Group__0 : rule__ProjectDependency__Group__0__Impl rule__ProjectDependency__Group__1 ;
    public final void rule__ProjectDependency__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4551:1: ( rule__ProjectDependency__Group__0__Impl rule__ProjectDependency__Group__1 )
            // InternalN4MFParser.g:4552:2: rule__ProjectDependency__Group__0__Impl rule__ProjectDependency__Group__1
            {
            pushFollow(FOLLOW_23);
            rule__ProjectDependency__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDependency__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependency__Group__0"


    // $ANTLR start "rule__ProjectDependency__Group__0__Impl"
    // InternalN4MFParser.g:4559:1: rule__ProjectDependency__Group__0__Impl : ( ruleProjectIdWithOptionalVendor ) ;
    public final void rule__ProjectDependency__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4563:1: ( ( ruleProjectIdWithOptionalVendor ) )
            // InternalN4MFParser.g:4564:1: ( ruleProjectIdWithOptionalVendor )
            {
            // InternalN4MFParser.g:4564:1: ( ruleProjectIdWithOptionalVendor )
            // InternalN4MFParser.g:4565:2: ruleProjectIdWithOptionalVendor
            {
             before(grammarAccess.getProjectDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectIdWithOptionalVendor();

            state._fsp--;

             after(grammarAccess.getProjectDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependency__Group__0__Impl"


    // $ANTLR start "rule__ProjectDependency__Group__1"
    // InternalN4MFParser.g:4574:1: rule__ProjectDependency__Group__1 : rule__ProjectDependency__Group__1__Impl rule__ProjectDependency__Group__2 ;
    public final void rule__ProjectDependency__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4578:1: ( rule__ProjectDependency__Group__1__Impl rule__ProjectDependency__Group__2 )
            // InternalN4MFParser.g:4579:2: rule__ProjectDependency__Group__1__Impl rule__ProjectDependency__Group__2
            {
            pushFollow(FOLLOW_23);
            rule__ProjectDependency__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDependency__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependency__Group__1"


    // $ANTLR start "rule__ProjectDependency__Group__1__Impl"
    // InternalN4MFParser.g:4586:1: rule__ProjectDependency__Group__1__Impl : ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? ) ;
    public final void rule__ProjectDependency__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4590:1: ( ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? ) )
            // InternalN4MFParser.g:4591:1: ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? )
            {
            // InternalN4MFParser.g:4591:1: ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? )
            // InternalN4MFParser.g:4592:2: ( rule__ProjectDependency__VersionConstraintAssignment_1 )?
            {
             before(grammarAccess.getProjectDependencyAccess().getVersionConstraintAssignment_1()); 
            // InternalN4MFParser.g:4593:2: ( rule__ProjectDependency__VersionConstraintAssignment_1 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==LeftParenthesis||LA35_0==LeftSquareBracket||LA35_0==RULE_INT) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalN4MFParser.g:4593:3: rule__ProjectDependency__VersionConstraintAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDependency__VersionConstraintAssignment_1();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectDependencyAccess().getVersionConstraintAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependency__Group__1__Impl"


    // $ANTLR start "rule__ProjectDependency__Group__2"
    // InternalN4MFParser.g:4601:1: rule__ProjectDependency__Group__2 : rule__ProjectDependency__Group__2__Impl ;
    public final void rule__ProjectDependency__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4605:1: ( rule__ProjectDependency__Group__2__Impl )
            // InternalN4MFParser.g:4606:2: rule__ProjectDependency__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependency__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependency__Group__2"


    // $ANTLR start "rule__ProjectDependency__Group__2__Impl"
    // InternalN4MFParser.g:4612:1: rule__ProjectDependency__Group__2__Impl : ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? ) ;
    public final void rule__ProjectDependency__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4616:1: ( ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? ) )
            // InternalN4MFParser.g:4617:1: ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? )
            {
            // InternalN4MFParser.g:4617:1: ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? )
            // InternalN4MFParser.g:4618:2: ( rule__ProjectDependency__DeclaredScopeAssignment_2 )?
            {
             before(grammarAccess.getProjectDependencyAccess().getDeclaredScopeAssignment_2()); 
            // InternalN4MFParser.g:4619:2: ( rule__ProjectDependency__DeclaredScopeAssignment_2 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==Compile||LA36_0==Test) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalN4MFParser.g:4619:3: rule__ProjectDependency__DeclaredScopeAssignment_2
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDependency__DeclaredScopeAssignment_2();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectDependencyAccess().getDeclaredScopeAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependency__Group__2__Impl"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__Group__0"
    // InternalN4MFParser.g:4628:1: rule__ProjectIdWithOptionalVendor__Group__0 : rule__ProjectIdWithOptionalVendor__Group__0__Impl rule__ProjectIdWithOptionalVendor__Group__1 ;
    public final void rule__ProjectIdWithOptionalVendor__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4632:1: ( rule__ProjectIdWithOptionalVendor__Group__0__Impl rule__ProjectIdWithOptionalVendor__Group__1 )
            // InternalN4MFParser.g:4633:2: rule__ProjectIdWithOptionalVendor__Group__0__Impl rule__ProjectIdWithOptionalVendor__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__ProjectIdWithOptionalVendor__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectIdWithOptionalVendor__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__Group__0"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__Group__0__Impl"
    // InternalN4MFParser.g:4640:1: rule__ProjectIdWithOptionalVendor__Group__0__Impl : ( ( rule__ProjectIdWithOptionalVendor__Group_0__0 )? ) ;
    public final void rule__ProjectIdWithOptionalVendor__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4644:1: ( ( ( rule__ProjectIdWithOptionalVendor__Group_0__0 )? ) )
            // InternalN4MFParser.g:4645:1: ( ( rule__ProjectIdWithOptionalVendor__Group_0__0 )? )
            {
            // InternalN4MFParser.g:4645:1: ( ( rule__ProjectIdWithOptionalVendor__Group_0__0 )? )
            // InternalN4MFParser.g:4646:2: ( rule__ProjectIdWithOptionalVendor__Group_0__0 )?
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getGroup_0()); 
            // InternalN4MFParser.g:4647:2: ( rule__ProjectIdWithOptionalVendor__Group_0__0 )?
            int alt37=2;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // InternalN4MFParser.g:4647:3: rule__ProjectIdWithOptionalVendor__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectIdWithOptionalVendor__Group_0__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectIdWithOptionalVendorAccess().getGroup_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__Group__0__Impl"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__Group__1"
    // InternalN4MFParser.g:4655:1: rule__ProjectIdWithOptionalVendor__Group__1 : rule__ProjectIdWithOptionalVendor__Group__1__Impl ;
    public final void rule__ProjectIdWithOptionalVendor__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4659:1: ( rule__ProjectIdWithOptionalVendor__Group__1__Impl )
            // InternalN4MFParser.g:4660:2: rule__ProjectIdWithOptionalVendor__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectIdWithOptionalVendor__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__Group__1"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__Group__1__Impl"
    // InternalN4MFParser.g:4666:1: rule__ProjectIdWithOptionalVendor__Group__1__Impl : ( ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 ) ) ;
    public final void rule__ProjectIdWithOptionalVendor__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4670:1: ( ( ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 ) ) )
            // InternalN4MFParser.g:4671:1: ( ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 ) )
            {
            // InternalN4MFParser.g:4671:1: ( ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 ) )
            // InternalN4MFParser.g:4672:2: ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 )
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getProjectIdAssignment_1()); 
            // InternalN4MFParser.g:4673:2: ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 )
            // InternalN4MFParser.g:4673:3: rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectIdWithOptionalVendorAccess().getProjectIdAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__Group__1__Impl"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__Group_0__0"
    // InternalN4MFParser.g:4682:1: rule__ProjectIdWithOptionalVendor__Group_0__0 : rule__ProjectIdWithOptionalVendor__Group_0__0__Impl rule__ProjectIdWithOptionalVendor__Group_0__1 ;
    public final void rule__ProjectIdWithOptionalVendor__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4686:1: ( rule__ProjectIdWithOptionalVendor__Group_0__0__Impl rule__ProjectIdWithOptionalVendor__Group_0__1 )
            // InternalN4MFParser.g:4687:2: rule__ProjectIdWithOptionalVendor__Group_0__0__Impl rule__ProjectIdWithOptionalVendor__Group_0__1
            {
            pushFollow(FOLLOW_3);
            rule__ProjectIdWithOptionalVendor__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectIdWithOptionalVendor__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__Group_0__0"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__Group_0__0__Impl"
    // InternalN4MFParser.g:4694:1: rule__ProjectIdWithOptionalVendor__Group_0__0__Impl : ( ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 ) ) ;
    public final void rule__ProjectIdWithOptionalVendor__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4698:1: ( ( ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 ) ) )
            // InternalN4MFParser.g:4699:1: ( ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 ) )
            {
            // InternalN4MFParser.g:4699:1: ( ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 ) )
            // InternalN4MFParser.g:4700:2: ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 )
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getDeclaredVendorIdAssignment_0_0()); 
            // InternalN4MFParser.g:4701:2: ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 )
            // InternalN4MFParser.g:4701:3: rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getProjectIdWithOptionalVendorAccess().getDeclaredVendorIdAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__Group_0__0__Impl"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__Group_0__1"
    // InternalN4MFParser.g:4709:1: rule__ProjectIdWithOptionalVendor__Group_0__1 : rule__ProjectIdWithOptionalVendor__Group_0__1__Impl ;
    public final void rule__ProjectIdWithOptionalVendor__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4713:1: ( rule__ProjectIdWithOptionalVendor__Group_0__1__Impl )
            // InternalN4MFParser.g:4714:2: rule__ProjectIdWithOptionalVendor__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectIdWithOptionalVendor__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__Group_0__1"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__Group_0__1__Impl"
    // InternalN4MFParser.g:4720:1: rule__ProjectIdWithOptionalVendor__Group_0__1__Impl : ( Colon ) ;
    public final void rule__ProjectIdWithOptionalVendor__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4724:1: ( ( Colon ) )
            // InternalN4MFParser.g:4725:1: ( Colon )
            {
            // InternalN4MFParser.g:4725:1: ( Colon )
            // InternalN4MFParser.g:4726:2: Colon
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getColonKeyword_0_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getProjectIdWithOptionalVendorAccess().getColonKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__Group_0__1__Impl"


    // $ANTLR start "rule__VersionConstraint__Group_0__0"
    // InternalN4MFParser.g:4736:1: rule__VersionConstraint__Group_0__0 : rule__VersionConstraint__Group_0__0__Impl rule__VersionConstraint__Group_0__1 ;
    public final void rule__VersionConstraint__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4740:1: ( rule__VersionConstraint__Group_0__0__Impl rule__VersionConstraint__Group_0__1 )
            // InternalN4MFParser.g:4741:2: rule__VersionConstraint__Group_0__0__Impl rule__VersionConstraint__Group_0__1
            {
            pushFollow(FOLLOW_6);
            rule__VersionConstraint__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0__0"


    // $ANTLR start "rule__VersionConstraint__Group_0__0__Impl"
    // InternalN4MFParser.g:4748:1: rule__VersionConstraint__Group_0__0__Impl : ( ( rule__VersionConstraint__Alternatives_0_0 ) ) ;
    public final void rule__VersionConstraint__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4752:1: ( ( ( rule__VersionConstraint__Alternatives_0_0 ) ) )
            // InternalN4MFParser.g:4753:1: ( ( rule__VersionConstraint__Alternatives_0_0 ) )
            {
            // InternalN4MFParser.g:4753:1: ( ( rule__VersionConstraint__Alternatives_0_0 ) )
            // InternalN4MFParser.g:4754:2: ( rule__VersionConstraint__Alternatives_0_0 )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_0()); 
            // InternalN4MFParser.g:4755:2: ( rule__VersionConstraint__Alternatives_0_0 )
            // InternalN4MFParser.g:4755:3: rule__VersionConstraint__Alternatives_0_0
            {
            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Alternatives_0_0();

            state._fsp--;


            }

             after(grammarAccess.getVersionConstraintAccess().getAlternatives_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0__0__Impl"


    // $ANTLR start "rule__VersionConstraint__Group_0__1"
    // InternalN4MFParser.g:4763:1: rule__VersionConstraint__Group_0__1 : rule__VersionConstraint__Group_0__1__Impl rule__VersionConstraint__Group_0__2 ;
    public final void rule__VersionConstraint__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4767:1: ( rule__VersionConstraint__Group_0__1__Impl rule__VersionConstraint__Group_0__2 )
            // InternalN4MFParser.g:4768:2: rule__VersionConstraint__Group_0__1__Impl rule__VersionConstraint__Group_0__2
            {
            pushFollow(FOLLOW_24);
            rule__VersionConstraint__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Group_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0__1"


    // $ANTLR start "rule__VersionConstraint__Group_0__1__Impl"
    // InternalN4MFParser.g:4775:1: rule__VersionConstraint__Group_0__1__Impl : ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) ) ;
    public final void rule__VersionConstraint__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4779:1: ( ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) ) )
            // InternalN4MFParser.g:4780:1: ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) )
            {
            // InternalN4MFParser.g:4780:1: ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) )
            // InternalN4MFParser.g:4781:2: ( rule__VersionConstraint__LowerVersionAssignment_0_1 )
            {
             before(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_0_1()); 
            // InternalN4MFParser.g:4782:2: ( rule__VersionConstraint__LowerVersionAssignment_0_1 )
            // InternalN4MFParser.g:4782:3: rule__VersionConstraint__LowerVersionAssignment_0_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionConstraint__LowerVersionAssignment_0_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0__1__Impl"


    // $ANTLR start "rule__VersionConstraint__Group_0__2"
    // InternalN4MFParser.g:4790:1: rule__VersionConstraint__Group_0__2 : rule__VersionConstraint__Group_0__2__Impl ;
    public final void rule__VersionConstraint__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4794:1: ( rule__VersionConstraint__Group_0__2__Impl )
            // InternalN4MFParser.g:4795:2: rule__VersionConstraint__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Group_0__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0__2"


    // $ANTLR start "rule__VersionConstraint__Group_0__2__Impl"
    // InternalN4MFParser.g:4801:1: rule__VersionConstraint__Group_0__2__Impl : ( ( rule__VersionConstraint__Alternatives_0_2 ) ) ;
    public final void rule__VersionConstraint__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4805:1: ( ( ( rule__VersionConstraint__Alternatives_0_2 ) ) )
            // InternalN4MFParser.g:4806:1: ( ( rule__VersionConstraint__Alternatives_0_2 ) )
            {
            // InternalN4MFParser.g:4806:1: ( ( rule__VersionConstraint__Alternatives_0_2 ) )
            // InternalN4MFParser.g:4807:2: ( rule__VersionConstraint__Alternatives_0_2 )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2()); 
            // InternalN4MFParser.g:4808:2: ( rule__VersionConstraint__Alternatives_0_2 )
            // InternalN4MFParser.g:4808:3: rule__VersionConstraint__Alternatives_0_2
            {
            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Alternatives_0_2();

            state._fsp--;


            }

             after(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0__2__Impl"


    // $ANTLR start "rule__VersionConstraint__Group_0_2_0__0"
    // InternalN4MFParser.g:4817:1: rule__VersionConstraint__Group_0_2_0__0 : rule__VersionConstraint__Group_0_2_0__0__Impl rule__VersionConstraint__Group_0_2_0__1 ;
    public final void rule__VersionConstraint__Group_0_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4821:1: ( rule__VersionConstraint__Group_0_2_0__0__Impl rule__VersionConstraint__Group_0_2_0__1 )
            // InternalN4MFParser.g:4822:2: rule__VersionConstraint__Group_0_2_0__0__Impl rule__VersionConstraint__Group_0_2_0__1
            {
            pushFollow(FOLLOW_6);
            rule__VersionConstraint__Group_0_2_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Group_0_2_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0_2_0__0"


    // $ANTLR start "rule__VersionConstraint__Group_0_2_0__0__Impl"
    // InternalN4MFParser.g:4829:1: rule__VersionConstraint__Group_0_2_0__0__Impl : ( Comma ) ;
    public final void rule__VersionConstraint__Group_0_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4833:1: ( ( Comma ) )
            // InternalN4MFParser.g:4834:1: ( Comma )
            {
            // InternalN4MFParser.g:4834:1: ( Comma )
            // InternalN4MFParser.g:4835:2: Comma
            {
             before(grammarAccess.getVersionConstraintAccess().getCommaKeyword_0_2_0_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getVersionConstraintAccess().getCommaKeyword_0_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0_2_0__0__Impl"


    // $ANTLR start "rule__VersionConstraint__Group_0_2_0__1"
    // InternalN4MFParser.g:4844:1: rule__VersionConstraint__Group_0_2_0__1 : rule__VersionConstraint__Group_0_2_0__1__Impl rule__VersionConstraint__Group_0_2_0__2 ;
    public final void rule__VersionConstraint__Group_0_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4848:1: ( rule__VersionConstraint__Group_0_2_0__1__Impl rule__VersionConstraint__Group_0_2_0__2 )
            // InternalN4MFParser.g:4849:2: rule__VersionConstraint__Group_0_2_0__1__Impl rule__VersionConstraint__Group_0_2_0__2
            {
            pushFollow(FOLLOW_25);
            rule__VersionConstraint__Group_0_2_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Group_0_2_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0_2_0__1"


    // $ANTLR start "rule__VersionConstraint__Group_0_2_0__1__Impl"
    // InternalN4MFParser.g:4856:1: rule__VersionConstraint__Group_0_2_0__1__Impl : ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) ) ;
    public final void rule__VersionConstraint__Group_0_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4860:1: ( ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) ) )
            // InternalN4MFParser.g:4861:1: ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) )
            {
            // InternalN4MFParser.g:4861:1: ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) )
            // InternalN4MFParser.g:4862:2: ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 )
            {
             before(grammarAccess.getVersionConstraintAccess().getUpperVersionAssignment_0_2_0_1()); 
            // InternalN4MFParser.g:4863:2: ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 )
            // InternalN4MFParser.g:4863:3: rule__VersionConstraint__UpperVersionAssignment_0_2_0_1
            {
            pushFollow(FOLLOW_2);
            rule__VersionConstraint__UpperVersionAssignment_0_2_0_1();

            state._fsp--;


            }

             after(grammarAccess.getVersionConstraintAccess().getUpperVersionAssignment_0_2_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0_2_0__1__Impl"


    // $ANTLR start "rule__VersionConstraint__Group_0_2_0__2"
    // InternalN4MFParser.g:4871:1: rule__VersionConstraint__Group_0_2_0__2 : rule__VersionConstraint__Group_0_2_0__2__Impl ;
    public final void rule__VersionConstraint__Group_0_2_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4875:1: ( rule__VersionConstraint__Group_0_2_0__2__Impl )
            // InternalN4MFParser.g:4876:2: rule__VersionConstraint__Group_0_2_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Group_0_2_0__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0_2_0__2"


    // $ANTLR start "rule__VersionConstraint__Group_0_2_0__2__Impl"
    // InternalN4MFParser.g:4882:1: rule__VersionConstraint__Group_0_2_0__2__Impl : ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) ) ;
    public final void rule__VersionConstraint__Group_0_2_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4886:1: ( ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) ) )
            // InternalN4MFParser.g:4887:1: ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) )
            {
            // InternalN4MFParser.g:4887:1: ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) )
            // InternalN4MFParser.g:4888:2: ( rule__VersionConstraint__Alternatives_0_2_0_2 )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2_0_2()); 
            // InternalN4MFParser.g:4889:2: ( rule__VersionConstraint__Alternatives_0_2_0_2 )
            // InternalN4MFParser.g:4889:3: rule__VersionConstraint__Alternatives_0_2_0_2
            {
            pushFollow(FOLLOW_2);
            rule__VersionConstraint__Alternatives_0_2_0_2();

            state._fsp--;


            }

             after(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__Group_0_2_0__2__Impl"


    // $ANTLR start "rule__N4mfIdentifier__Group_11__0"
    // InternalN4MFParser.g:4898:1: rule__N4mfIdentifier__Group_11__0 : rule__N4mfIdentifier__Group_11__0__Impl rule__N4mfIdentifier__Group_11__1 ;
    public final void rule__N4mfIdentifier__Group_11__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4902:1: ( rule__N4mfIdentifier__Group_11__0__Impl rule__N4mfIdentifier__Group_11__1 )
            // InternalN4MFParser.g:4903:2: rule__N4mfIdentifier__Group_11__0__Impl rule__N4mfIdentifier__Group_11__1
            {
            pushFollow(FOLLOW_26);
            rule__N4mfIdentifier__Group_11__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__N4mfIdentifier__Group_11__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__N4mfIdentifier__Group_11__0"


    // $ANTLR start "rule__N4mfIdentifier__Group_11__0__Impl"
    // InternalN4MFParser.g:4910:1: rule__N4mfIdentifier__Group_11__0__Impl : ( ProjectDependencies ) ;
    public final void rule__N4mfIdentifier__Group_11__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4914:1: ( ( ProjectDependencies ) )
            // InternalN4MFParser.g:4915:1: ( ProjectDependencies )
            {
            // InternalN4MFParser.g:4915:1: ( ProjectDependencies )
            // InternalN4MFParser.g:4916:2: ProjectDependencies
            {
             before(grammarAccess.getN4mfIdentifierAccess().getProjectDependenciesKeyword_11_0()); 
            match(input,ProjectDependencies,FOLLOW_2); 
             after(grammarAccess.getN4mfIdentifierAccess().getProjectDependenciesKeyword_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__N4mfIdentifier__Group_11__0__Impl"


    // $ANTLR start "rule__N4mfIdentifier__Group_11__1"
    // InternalN4MFParser.g:4925:1: rule__N4mfIdentifier__Group_11__1 : rule__N4mfIdentifier__Group_11__1__Impl ;
    public final void rule__N4mfIdentifier__Group_11__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4929:1: ( rule__N4mfIdentifier__Group_11__1__Impl )
            // InternalN4MFParser.g:4930:2: rule__N4mfIdentifier__Group_11__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__N4mfIdentifier__Group_11__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__N4mfIdentifier__Group_11__1"


    // $ANTLR start "rule__N4mfIdentifier__Group_11__1__Impl"
    // InternalN4MFParser.g:4936:1: rule__N4mfIdentifier__Group_11__1__Impl : ( KW_System ) ;
    public final void rule__N4mfIdentifier__Group_11__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4940:1: ( ( KW_System ) )
            // InternalN4MFParser.g:4941:1: ( KW_System )
            {
            // InternalN4MFParser.g:4941:1: ( KW_System )
            // InternalN4MFParser.g:4942:2: KW_System
            {
             before(grammarAccess.getN4mfIdentifierAccess().getSystemKeyword_11_1()); 
            match(input,KW_System,FOLLOW_2); 
             after(grammarAccess.getN4mfIdentifierAccess().getSystemKeyword_11_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__N4mfIdentifier__Group_11__1__Impl"


    // $ANTLR start "rule__N4mfIdentifier__Group_15__0"
    // InternalN4MFParser.g:4952:1: rule__N4mfIdentifier__Group_15__0 : rule__N4mfIdentifier__Group_15__0__Impl rule__N4mfIdentifier__Group_15__1 ;
    public final void rule__N4mfIdentifier__Group_15__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4956:1: ( rule__N4mfIdentifier__Group_15__0__Impl rule__N4mfIdentifier__Group_15__1 )
            // InternalN4MFParser.g:4957:2: rule__N4mfIdentifier__Group_15__0__Impl rule__N4mfIdentifier__Group_15__1
            {
            pushFollow(FOLLOW_27);
            rule__N4mfIdentifier__Group_15__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__N4mfIdentifier__Group_15__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__N4mfIdentifier__Group_15__0"


    // $ANTLR start "rule__N4mfIdentifier__Group_15__0__Impl"
    // InternalN4MFParser.g:4964:1: rule__N4mfIdentifier__Group_15__0__Impl : ( Processor ) ;
    public final void rule__N4mfIdentifier__Group_15__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4968:1: ( ( Processor ) )
            // InternalN4MFParser.g:4969:1: ( Processor )
            {
            // InternalN4MFParser.g:4969:1: ( Processor )
            // InternalN4MFParser.g:4970:2: Processor
            {
             before(grammarAccess.getN4mfIdentifierAccess().getProcessorKeyword_15_0()); 
            match(input,Processor,FOLLOW_2); 
             after(grammarAccess.getN4mfIdentifierAccess().getProcessorKeyword_15_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__N4mfIdentifier__Group_15__0__Impl"


    // $ANTLR start "rule__N4mfIdentifier__Group_15__1"
    // InternalN4MFParser.g:4979:1: rule__N4mfIdentifier__Group_15__1 : rule__N4mfIdentifier__Group_15__1__Impl ;
    public final void rule__N4mfIdentifier__Group_15__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4983:1: ( rule__N4mfIdentifier__Group_15__1__Impl )
            // InternalN4MFParser.g:4984:2: rule__N4mfIdentifier__Group_15__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__N4mfIdentifier__Group_15__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__N4mfIdentifier__Group_15__1"


    // $ANTLR start "rule__N4mfIdentifier__Group_15__1__Impl"
    // InternalN4MFParser.g:4990:1: rule__N4mfIdentifier__Group_15__1__Impl : ( Source ) ;
    public final void rule__N4mfIdentifier__Group_15__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4994:1: ( ( Source ) )
            // InternalN4MFParser.g:4995:1: ( Source )
            {
            // InternalN4MFParser.g:4995:1: ( Source )
            // InternalN4MFParser.g:4996:2: Source
            {
             before(grammarAccess.getN4mfIdentifierAccess().getSourceKeyword_15_1()); 
            match(input,Source,FOLLOW_2); 
             after(grammarAccess.getN4mfIdentifierAccess().getSourceKeyword_15_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__N4mfIdentifier__Group_15__1__Impl"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup"
    // InternalN4MFParser.g:5006:1: rule__ProjectDescription__UnorderedGroup : rule__ProjectDescription__UnorderedGroup__0 {...}?;
    public final void rule__ProjectDescription__UnorderedGroup() throws RecognitionException {

        		int stackSize = keepStackSize();
        		getUnorderedGroupHelper().enter(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
        	
        try {
            // InternalN4MFParser.g:5011:1: ( rule__ProjectDescription__UnorderedGroup__0 {...}?)
            // InternalN4MFParser.g:5012:2: rule__ProjectDescription__UnorderedGroup__0 {...}?
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__UnorderedGroup__0();

            state._fsp--;

            if ( ! getUnorderedGroupHelper().canLeave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup()) ) {
                throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup", "getUnorderedGroupHelper().canLeave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup())");
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	getUnorderedGroupHelper().leave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__Impl"
    // InternalN4MFParser.g:5020:1: rule__ProjectDescription__UnorderedGroup__Impl : ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) ) ;
    public final void rule__ProjectDescription__UnorderedGroup__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        		boolean selected = false;
        	
        try {
            // InternalN4MFParser.g:5025:1: ( ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) ) )
            // InternalN4MFParser.g:5026:3: ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) )
            {
            // InternalN4MFParser.g:5026:3: ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) )
            int alt38=21;
            alt38 = dfa38.predict(input);
            switch (alt38) {
                case 1 :
                    // InternalN4MFParser.g:5027:3: ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5027:3: ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) )
                    // InternalN4MFParser.g:5028:4: {...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0)");
                    }
                    // InternalN4MFParser.g:5028:112: ( ( ( rule__ProjectDescription__Group_0__0 ) ) )
                    // InternalN4MFParser.g:5029:5: ( ( rule__ProjectDescription__Group_0__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5035:5: ( ( rule__ProjectDescription__Group_0__0 ) )
                    // InternalN4MFParser.g:5036:6: ( rule__ProjectDescription__Group_0__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_0()); 
                    // InternalN4MFParser.g:5037:6: ( rule__ProjectDescription__Group_0__0 )
                    // InternalN4MFParser.g:5037:7: rule__ProjectDescription__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_0()); 

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:5042:3: ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5042:3: ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) )
                    // InternalN4MFParser.g:5043:4: {...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1)");
                    }
                    // InternalN4MFParser.g:5043:112: ( ( ( rule__ProjectDescription__Group_1__0 ) ) )
                    // InternalN4MFParser.g:5044:5: ( ( rule__ProjectDescription__Group_1__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5050:5: ( ( rule__ProjectDescription__Group_1__0 ) )
                    // InternalN4MFParser.g:5051:6: ( rule__ProjectDescription__Group_1__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_1()); 
                    // InternalN4MFParser.g:5052:6: ( rule__ProjectDescription__Group_1__0 )
                    // InternalN4MFParser.g:5052:7: rule__ProjectDescription__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_1()); 

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:5057:3: ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5057:3: ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) )
                    // InternalN4MFParser.g:5058:4: {...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2)");
                    }
                    // InternalN4MFParser.g:5058:112: ( ( ( rule__ProjectDescription__Group_2__0 ) ) )
                    // InternalN4MFParser.g:5059:5: ( ( rule__ProjectDescription__Group_2__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5065:5: ( ( rule__ProjectDescription__Group_2__0 ) )
                    // InternalN4MFParser.g:5066:6: ( rule__ProjectDescription__Group_2__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_2()); 
                    // InternalN4MFParser.g:5067:6: ( rule__ProjectDescription__Group_2__0 )
                    // InternalN4MFParser.g:5067:7: rule__ProjectDescription__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_2()); 

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalN4MFParser.g:5072:3: ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5072:3: ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) )
                    // InternalN4MFParser.g:5073:4: {...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3)");
                    }
                    // InternalN4MFParser.g:5073:112: ( ( ( rule__ProjectDescription__Group_3__0 ) ) )
                    // InternalN4MFParser.g:5074:5: ( ( rule__ProjectDescription__Group_3__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5080:5: ( ( rule__ProjectDescription__Group_3__0 ) )
                    // InternalN4MFParser.g:5081:6: ( rule__ProjectDescription__Group_3__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_3()); 
                    // InternalN4MFParser.g:5082:6: ( rule__ProjectDescription__Group_3__0 )
                    // InternalN4MFParser.g:5082:7: rule__ProjectDescription__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_3__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_3()); 

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalN4MFParser.g:5087:3: ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5087:3: ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) )
                    // InternalN4MFParser.g:5088:4: {...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4)");
                    }
                    // InternalN4MFParser.g:5088:112: ( ( ( rule__ProjectDescription__Group_4__0 ) ) )
                    // InternalN4MFParser.g:5089:5: ( ( rule__ProjectDescription__Group_4__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5095:5: ( ( rule__ProjectDescription__Group_4__0 ) )
                    // InternalN4MFParser.g:5096:6: ( rule__ProjectDescription__Group_4__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_4()); 
                    // InternalN4MFParser.g:5097:6: ( rule__ProjectDescription__Group_4__0 )
                    // InternalN4MFParser.g:5097:7: rule__ProjectDescription__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_4__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_4()); 

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalN4MFParser.g:5102:3: ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5102:3: ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) )
                    // InternalN4MFParser.g:5103:4: {...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5)");
                    }
                    // InternalN4MFParser.g:5103:112: ( ( ( rule__ProjectDescription__Group_5__0 ) ) )
                    // InternalN4MFParser.g:5104:5: ( ( rule__ProjectDescription__Group_5__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5110:5: ( ( rule__ProjectDescription__Group_5__0 ) )
                    // InternalN4MFParser.g:5111:6: ( rule__ProjectDescription__Group_5__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_5()); 
                    // InternalN4MFParser.g:5112:6: ( rule__ProjectDescription__Group_5__0 )
                    // InternalN4MFParser.g:5112:7: rule__ProjectDescription__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_5__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_5()); 

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalN4MFParser.g:5117:3: ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5117:3: ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) )
                    // InternalN4MFParser.g:5118:4: {...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6)");
                    }
                    // InternalN4MFParser.g:5118:112: ( ( ( rule__ProjectDescription__Group_6__0 ) ) )
                    // InternalN4MFParser.g:5119:5: ( ( rule__ProjectDescription__Group_6__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5125:5: ( ( rule__ProjectDescription__Group_6__0 ) )
                    // InternalN4MFParser.g:5126:6: ( rule__ProjectDescription__Group_6__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_6()); 
                    // InternalN4MFParser.g:5127:6: ( rule__ProjectDescription__Group_6__0 )
                    // InternalN4MFParser.g:5127:7: rule__ProjectDescription__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_6__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_6()); 

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalN4MFParser.g:5132:3: ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5132:3: ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) )
                    // InternalN4MFParser.g:5133:4: {...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7)");
                    }
                    // InternalN4MFParser.g:5133:112: ( ( ( rule__ProjectDescription__Group_7__0 ) ) )
                    // InternalN4MFParser.g:5134:5: ( ( rule__ProjectDescription__Group_7__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5140:5: ( ( rule__ProjectDescription__Group_7__0 ) )
                    // InternalN4MFParser.g:5141:6: ( rule__ProjectDescription__Group_7__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_7()); 
                    // InternalN4MFParser.g:5142:6: ( rule__ProjectDescription__Group_7__0 )
                    // InternalN4MFParser.g:5142:7: rule__ProjectDescription__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_7__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_7()); 

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalN4MFParser.g:5147:3: ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5147:3: ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) )
                    // InternalN4MFParser.g:5148:4: {...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8)");
                    }
                    // InternalN4MFParser.g:5148:112: ( ( ( rule__ProjectDescription__Group_8__0 ) ) )
                    // InternalN4MFParser.g:5149:5: ( ( rule__ProjectDescription__Group_8__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5155:5: ( ( rule__ProjectDescription__Group_8__0 ) )
                    // InternalN4MFParser.g:5156:6: ( rule__ProjectDescription__Group_8__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_8()); 
                    // InternalN4MFParser.g:5157:6: ( rule__ProjectDescription__Group_8__0 )
                    // InternalN4MFParser.g:5157:7: rule__ProjectDescription__Group_8__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_8__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_8()); 

                    }


                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalN4MFParser.g:5162:3: ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5162:3: ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) )
                    // InternalN4MFParser.g:5163:4: {...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9)");
                    }
                    // InternalN4MFParser.g:5163:112: ( ( ( rule__ProjectDescription__Group_9__0 ) ) )
                    // InternalN4MFParser.g:5164:5: ( ( rule__ProjectDescription__Group_9__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5170:5: ( ( rule__ProjectDescription__Group_9__0 ) )
                    // InternalN4MFParser.g:5171:6: ( rule__ProjectDescription__Group_9__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_9()); 
                    // InternalN4MFParser.g:5172:6: ( rule__ProjectDescription__Group_9__0 )
                    // InternalN4MFParser.g:5172:7: rule__ProjectDescription__Group_9__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_9__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_9()); 

                    }


                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalN4MFParser.g:5177:3: ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5177:3: ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) )
                    // InternalN4MFParser.g:5178:4: {...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10)");
                    }
                    // InternalN4MFParser.g:5178:113: ( ( ( rule__ProjectDescription__Group_10__0 ) ) )
                    // InternalN4MFParser.g:5179:5: ( ( rule__ProjectDescription__Group_10__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5185:5: ( ( rule__ProjectDescription__Group_10__0 ) )
                    // InternalN4MFParser.g:5186:6: ( rule__ProjectDescription__Group_10__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_10()); 
                    // InternalN4MFParser.g:5187:6: ( rule__ProjectDescription__Group_10__0 )
                    // InternalN4MFParser.g:5187:7: rule__ProjectDescription__Group_10__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_10__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_10()); 

                    }


                    }


                    }


                    }
                    break;
                case 12 :
                    // InternalN4MFParser.g:5192:3: ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5192:3: ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) )
                    // InternalN4MFParser.g:5193:4: {...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11)");
                    }
                    // InternalN4MFParser.g:5193:113: ( ( ( rule__ProjectDescription__Group_11__0 ) ) )
                    // InternalN4MFParser.g:5194:5: ( ( rule__ProjectDescription__Group_11__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5200:5: ( ( rule__ProjectDescription__Group_11__0 ) )
                    // InternalN4MFParser.g:5201:6: ( rule__ProjectDescription__Group_11__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_11()); 
                    // InternalN4MFParser.g:5202:6: ( rule__ProjectDescription__Group_11__0 )
                    // InternalN4MFParser.g:5202:7: rule__ProjectDescription__Group_11__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_11__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_11()); 

                    }


                    }


                    }


                    }
                    break;
                case 13 :
                    // InternalN4MFParser.g:5207:3: ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5207:3: ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) )
                    // InternalN4MFParser.g:5208:4: {...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12)");
                    }
                    // InternalN4MFParser.g:5208:113: ( ( ( rule__ProjectDescription__Group_12__0 ) ) )
                    // InternalN4MFParser.g:5209:5: ( ( rule__ProjectDescription__Group_12__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5215:5: ( ( rule__ProjectDescription__Group_12__0 ) )
                    // InternalN4MFParser.g:5216:6: ( rule__ProjectDescription__Group_12__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_12()); 
                    // InternalN4MFParser.g:5217:6: ( rule__ProjectDescription__Group_12__0 )
                    // InternalN4MFParser.g:5217:7: rule__ProjectDescription__Group_12__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_12__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_12()); 

                    }


                    }


                    }


                    }
                    break;
                case 14 :
                    // InternalN4MFParser.g:5222:3: ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5222:3: ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) )
                    // InternalN4MFParser.g:5223:4: {...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13)");
                    }
                    // InternalN4MFParser.g:5223:113: ( ( ( rule__ProjectDescription__Group_13__0 ) ) )
                    // InternalN4MFParser.g:5224:5: ( ( rule__ProjectDescription__Group_13__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5230:5: ( ( rule__ProjectDescription__Group_13__0 ) )
                    // InternalN4MFParser.g:5231:6: ( rule__ProjectDescription__Group_13__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_13()); 
                    // InternalN4MFParser.g:5232:6: ( rule__ProjectDescription__Group_13__0 )
                    // InternalN4MFParser.g:5232:7: rule__ProjectDescription__Group_13__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_13__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_13()); 

                    }


                    }


                    }


                    }
                    break;
                case 15 :
                    // InternalN4MFParser.g:5237:3: ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5237:3: ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) )
                    // InternalN4MFParser.g:5238:4: {...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14)");
                    }
                    // InternalN4MFParser.g:5238:113: ( ( ( rule__ProjectDescription__Group_14__0 ) ) )
                    // InternalN4MFParser.g:5239:5: ( ( rule__ProjectDescription__Group_14__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5245:5: ( ( rule__ProjectDescription__Group_14__0 ) )
                    // InternalN4MFParser.g:5246:6: ( rule__ProjectDescription__Group_14__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_14()); 
                    // InternalN4MFParser.g:5247:6: ( rule__ProjectDescription__Group_14__0 )
                    // InternalN4MFParser.g:5247:7: rule__ProjectDescription__Group_14__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_14__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_14()); 

                    }


                    }


                    }


                    }
                    break;
                case 16 :
                    // InternalN4MFParser.g:5252:3: ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5252:3: ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) )
                    // InternalN4MFParser.g:5253:4: {...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15)");
                    }
                    // InternalN4MFParser.g:5253:113: ( ( ( rule__ProjectDescription__Group_15__0 ) ) )
                    // InternalN4MFParser.g:5254:5: ( ( rule__ProjectDescription__Group_15__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5260:5: ( ( rule__ProjectDescription__Group_15__0 ) )
                    // InternalN4MFParser.g:5261:6: ( rule__ProjectDescription__Group_15__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_15()); 
                    // InternalN4MFParser.g:5262:6: ( rule__ProjectDescription__Group_15__0 )
                    // InternalN4MFParser.g:5262:7: rule__ProjectDescription__Group_15__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_15__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_15()); 

                    }


                    }


                    }


                    }
                    break;
                case 17 :
                    // InternalN4MFParser.g:5267:3: ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5267:3: ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) )
                    // InternalN4MFParser.g:5268:4: {...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16)");
                    }
                    // InternalN4MFParser.g:5268:113: ( ( ( rule__ProjectDescription__Group_16__0 ) ) )
                    // InternalN4MFParser.g:5269:5: ( ( rule__ProjectDescription__Group_16__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5275:5: ( ( rule__ProjectDescription__Group_16__0 ) )
                    // InternalN4MFParser.g:5276:6: ( rule__ProjectDescription__Group_16__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_16()); 
                    // InternalN4MFParser.g:5277:6: ( rule__ProjectDescription__Group_16__0 )
                    // InternalN4MFParser.g:5277:7: rule__ProjectDescription__Group_16__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_16__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_16()); 

                    }


                    }


                    }


                    }
                    break;
                case 18 :
                    // InternalN4MFParser.g:5282:3: ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5282:3: ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) )
                    // InternalN4MFParser.g:5283:4: {...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17)");
                    }
                    // InternalN4MFParser.g:5283:113: ( ( ( rule__ProjectDescription__Group_17__0 ) ) )
                    // InternalN4MFParser.g:5284:5: ( ( rule__ProjectDescription__Group_17__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5290:5: ( ( rule__ProjectDescription__Group_17__0 ) )
                    // InternalN4MFParser.g:5291:6: ( rule__ProjectDescription__Group_17__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_17()); 
                    // InternalN4MFParser.g:5292:6: ( rule__ProjectDescription__Group_17__0 )
                    // InternalN4MFParser.g:5292:7: rule__ProjectDescription__Group_17__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_17__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_17()); 

                    }


                    }


                    }


                    }
                    break;
                case 19 :
                    // InternalN4MFParser.g:5297:3: ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5297:3: ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) )
                    // InternalN4MFParser.g:5298:4: {...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18)");
                    }
                    // InternalN4MFParser.g:5298:113: ( ( ( rule__ProjectDescription__Group_18__0 ) ) )
                    // InternalN4MFParser.g:5299:5: ( ( rule__ProjectDescription__Group_18__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5305:5: ( ( rule__ProjectDescription__Group_18__0 ) )
                    // InternalN4MFParser.g:5306:6: ( rule__ProjectDescription__Group_18__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_18()); 
                    // InternalN4MFParser.g:5307:6: ( rule__ProjectDescription__Group_18__0 )
                    // InternalN4MFParser.g:5307:7: rule__ProjectDescription__Group_18__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_18__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_18()); 

                    }


                    }


                    }


                    }
                    break;
                case 20 :
                    // InternalN4MFParser.g:5312:3: ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5312:3: ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) )
                    // InternalN4MFParser.g:5313:4: {...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19)");
                    }
                    // InternalN4MFParser.g:5313:113: ( ( ( rule__ProjectDescription__Group_19__0 ) ) )
                    // InternalN4MFParser.g:5314:5: ( ( rule__ProjectDescription__Group_19__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5320:5: ( ( rule__ProjectDescription__Group_19__0 ) )
                    // InternalN4MFParser.g:5321:6: ( rule__ProjectDescription__Group_19__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_19()); 
                    // InternalN4MFParser.g:5322:6: ( rule__ProjectDescription__Group_19__0 )
                    // InternalN4MFParser.g:5322:7: rule__ProjectDescription__Group_19__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_19__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_19()); 

                    }


                    }


                    }


                    }
                    break;
                case 21 :
                    // InternalN4MFParser.g:5327:3: ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5327:3: ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) )
                    // InternalN4MFParser.g:5328:4: {...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20)");
                    }
                    // InternalN4MFParser.g:5328:113: ( ( ( rule__ProjectDescription__Group_20__0 ) ) )
                    // InternalN4MFParser.g:5329:5: ( ( rule__ProjectDescription__Group_20__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5335:5: ( ( rule__ProjectDescription__Group_20__0 ) )
                    // InternalN4MFParser.g:5336:6: ( rule__ProjectDescription__Group_20__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_20()); 
                    // InternalN4MFParser.g:5337:6: ( rule__ProjectDescription__Group_20__0 )
                    // InternalN4MFParser.g:5337:7: rule__ProjectDescription__Group_20__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__Group_20__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getGroup_20()); 

                    }


                    }


                    }


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	if (selected)
            		getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__Impl"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__0"
    // InternalN4MFParser.g:5350:1: rule__ProjectDescription__UnorderedGroup__0 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__1 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5354:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__1 )? )
            // InternalN4MFParser.g:5355:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__1 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5356:2: ( rule__ProjectDescription__UnorderedGroup__1 )?
            int alt39=2;
            alt39 = dfa39.predict(input);
            switch (alt39) {
                case 1 :
                    // InternalN4MFParser.g:5356:2: rule__ProjectDescription__UnorderedGroup__1
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__1();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__0"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__1"
    // InternalN4MFParser.g:5362:1: rule__ProjectDescription__UnorderedGroup__1 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__2 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5366:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__2 )? )
            // InternalN4MFParser.g:5367:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__2 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5368:2: ( rule__ProjectDescription__UnorderedGroup__2 )?
            int alt40=2;
            alt40 = dfa40.predict(input);
            switch (alt40) {
                case 1 :
                    // InternalN4MFParser.g:5368:2: rule__ProjectDescription__UnorderedGroup__2
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__2();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__1"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__2"
    // InternalN4MFParser.g:5374:1: rule__ProjectDescription__UnorderedGroup__2 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__3 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5378:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__3 )? )
            // InternalN4MFParser.g:5379:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__3 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5380:2: ( rule__ProjectDescription__UnorderedGroup__3 )?
            int alt41=2;
            alt41 = dfa41.predict(input);
            switch (alt41) {
                case 1 :
                    // InternalN4MFParser.g:5380:2: rule__ProjectDescription__UnorderedGroup__3
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__3();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__2"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__3"
    // InternalN4MFParser.g:5386:1: rule__ProjectDescription__UnorderedGroup__3 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__4 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5390:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__4 )? )
            // InternalN4MFParser.g:5391:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__4 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5392:2: ( rule__ProjectDescription__UnorderedGroup__4 )?
            int alt42=2;
            alt42 = dfa42.predict(input);
            switch (alt42) {
                case 1 :
                    // InternalN4MFParser.g:5392:2: rule__ProjectDescription__UnorderedGroup__4
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__4();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__3"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__4"
    // InternalN4MFParser.g:5398:1: rule__ProjectDescription__UnorderedGroup__4 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__5 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5402:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__5 )? )
            // InternalN4MFParser.g:5403:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__5 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5404:2: ( rule__ProjectDescription__UnorderedGroup__5 )?
            int alt43=2;
            alt43 = dfa43.predict(input);
            switch (alt43) {
                case 1 :
                    // InternalN4MFParser.g:5404:2: rule__ProjectDescription__UnorderedGroup__5
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__5();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__4"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__5"
    // InternalN4MFParser.g:5410:1: rule__ProjectDescription__UnorderedGroup__5 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__6 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5414:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__6 )? )
            // InternalN4MFParser.g:5415:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__6 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5416:2: ( rule__ProjectDescription__UnorderedGroup__6 )?
            int alt44=2;
            alt44 = dfa44.predict(input);
            switch (alt44) {
                case 1 :
                    // InternalN4MFParser.g:5416:2: rule__ProjectDescription__UnorderedGroup__6
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__6();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__5"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__6"
    // InternalN4MFParser.g:5422:1: rule__ProjectDescription__UnorderedGroup__6 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__7 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5426:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__7 )? )
            // InternalN4MFParser.g:5427:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__7 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5428:2: ( rule__ProjectDescription__UnorderedGroup__7 )?
            int alt45=2;
            alt45 = dfa45.predict(input);
            switch (alt45) {
                case 1 :
                    // InternalN4MFParser.g:5428:2: rule__ProjectDescription__UnorderedGroup__7
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__7();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__6"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__7"
    // InternalN4MFParser.g:5434:1: rule__ProjectDescription__UnorderedGroup__7 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__8 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5438:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__8 )? )
            // InternalN4MFParser.g:5439:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__8 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5440:2: ( rule__ProjectDescription__UnorderedGroup__8 )?
            int alt46=2;
            alt46 = dfa46.predict(input);
            switch (alt46) {
                case 1 :
                    // InternalN4MFParser.g:5440:2: rule__ProjectDescription__UnorderedGroup__8
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__8();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__7"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__8"
    // InternalN4MFParser.g:5446:1: rule__ProjectDescription__UnorderedGroup__8 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__9 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5450:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__9 )? )
            // InternalN4MFParser.g:5451:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__9 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5452:2: ( rule__ProjectDescription__UnorderedGroup__9 )?
            int alt47=2;
            alt47 = dfa47.predict(input);
            switch (alt47) {
                case 1 :
                    // InternalN4MFParser.g:5452:2: rule__ProjectDescription__UnorderedGroup__9
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__9();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__8"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__9"
    // InternalN4MFParser.g:5458:1: rule__ProjectDescription__UnorderedGroup__9 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__10 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5462:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__10 )? )
            // InternalN4MFParser.g:5463:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__10 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5464:2: ( rule__ProjectDescription__UnorderedGroup__10 )?
            int alt48=2;
            alt48 = dfa48.predict(input);
            switch (alt48) {
                case 1 :
                    // InternalN4MFParser.g:5464:2: rule__ProjectDescription__UnorderedGroup__10
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__10();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__9"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__10"
    // InternalN4MFParser.g:5470:1: rule__ProjectDescription__UnorderedGroup__10 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__11 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5474:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__11 )? )
            // InternalN4MFParser.g:5475:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__11 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5476:2: ( rule__ProjectDescription__UnorderedGroup__11 )?
            int alt49=2;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // InternalN4MFParser.g:5476:2: rule__ProjectDescription__UnorderedGroup__11
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__11();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__10"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__11"
    // InternalN4MFParser.g:5482:1: rule__ProjectDescription__UnorderedGroup__11 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__12 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5486:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__12 )? )
            // InternalN4MFParser.g:5487:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__12 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5488:2: ( rule__ProjectDescription__UnorderedGroup__12 )?
            int alt50=2;
            alt50 = dfa50.predict(input);
            switch (alt50) {
                case 1 :
                    // InternalN4MFParser.g:5488:2: rule__ProjectDescription__UnorderedGroup__12
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__12();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__11"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__12"
    // InternalN4MFParser.g:5494:1: rule__ProjectDescription__UnorderedGroup__12 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__13 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5498:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__13 )? )
            // InternalN4MFParser.g:5499:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__13 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5500:2: ( rule__ProjectDescription__UnorderedGroup__13 )?
            int alt51=2;
            alt51 = dfa51.predict(input);
            switch (alt51) {
                case 1 :
                    // InternalN4MFParser.g:5500:2: rule__ProjectDescription__UnorderedGroup__13
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__13();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__12"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__13"
    // InternalN4MFParser.g:5506:1: rule__ProjectDescription__UnorderedGroup__13 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__14 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5510:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__14 )? )
            // InternalN4MFParser.g:5511:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__14 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5512:2: ( rule__ProjectDescription__UnorderedGroup__14 )?
            int alt52=2;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // InternalN4MFParser.g:5512:2: rule__ProjectDescription__UnorderedGroup__14
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__14();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__13"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__14"
    // InternalN4MFParser.g:5518:1: rule__ProjectDescription__UnorderedGroup__14 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__15 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5522:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__15 )? )
            // InternalN4MFParser.g:5523:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__15 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5524:2: ( rule__ProjectDescription__UnorderedGroup__15 )?
            int alt53=2;
            alt53 = dfa53.predict(input);
            switch (alt53) {
                case 1 :
                    // InternalN4MFParser.g:5524:2: rule__ProjectDescription__UnorderedGroup__15
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__15();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__14"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__15"
    // InternalN4MFParser.g:5530:1: rule__ProjectDescription__UnorderedGroup__15 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__16 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5534:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__16 )? )
            // InternalN4MFParser.g:5535:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__16 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5536:2: ( rule__ProjectDescription__UnorderedGroup__16 )?
            int alt54=2;
            alt54 = dfa54.predict(input);
            switch (alt54) {
                case 1 :
                    // InternalN4MFParser.g:5536:2: rule__ProjectDescription__UnorderedGroup__16
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__16();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__15"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__16"
    // InternalN4MFParser.g:5542:1: rule__ProjectDescription__UnorderedGroup__16 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__17 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5546:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__17 )? )
            // InternalN4MFParser.g:5547:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__17 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5548:2: ( rule__ProjectDescription__UnorderedGroup__17 )?
            int alt55=2;
            alt55 = dfa55.predict(input);
            switch (alt55) {
                case 1 :
                    // InternalN4MFParser.g:5548:2: rule__ProjectDescription__UnorderedGroup__17
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__17();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__16"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__17"
    // InternalN4MFParser.g:5554:1: rule__ProjectDescription__UnorderedGroup__17 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__18 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__17() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5558:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__18 )? )
            // InternalN4MFParser.g:5559:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__18 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5560:2: ( rule__ProjectDescription__UnorderedGroup__18 )?
            int alt56=2;
            alt56 = dfa56.predict(input);
            switch (alt56) {
                case 1 :
                    // InternalN4MFParser.g:5560:2: rule__ProjectDescription__UnorderedGroup__18
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__18();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__17"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__18"
    // InternalN4MFParser.g:5566:1: rule__ProjectDescription__UnorderedGroup__18 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__19 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__18() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5570:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__19 )? )
            // InternalN4MFParser.g:5571:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__19 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5572:2: ( rule__ProjectDescription__UnorderedGroup__19 )?
            int alt57=2;
            alt57 = dfa57.predict(input);
            switch (alt57) {
                case 1 :
                    // InternalN4MFParser.g:5572:2: rule__ProjectDescription__UnorderedGroup__19
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__19();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__18"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__19"
    // InternalN4MFParser.g:5578:1: rule__ProjectDescription__UnorderedGroup__19 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__20 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__19() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5582:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__20 )? )
            // InternalN4MFParser.g:5583:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__20 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5584:2: ( rule__ProjectDescription__UnorderedGroup__20 )?
            int alt58=2;
            alt58 = dfa58.predict(input);
            switch (alt58) {
                case 1 :
                    // InternalN4MFParser.g:5584:2: rule__ProjectDescription__UnorderedGroup__20
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__UnorderedGroup__20();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__19"


    // $ANTLR start "rule__ProjectDescription__UnorderedGroup__20"
    // InternalN4MFParser.g:5590:1: rule__ProjectDescription__UnorderedGroup__20 : rule__ProjectDescription__UnorderedGroup__Impl ;
    public final void rule__ProjectDescription__UnorderedGroup__20() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5594:1: ( rule__ProjectDescription__UnorderedGroup__Impl )
            // InternalN4MFParser.g:5595:2: rule__ProjectDescription__UnorderedGroup__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__UnorderedGroup__20"


    // $ANTLR start "rule__ProjectDescription__ProjectIdAssignment_0_2"
    // InternalN4MFParser.g:5602:1: rule__ProjectDescription__ProjectIdAssignment_0_2 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectDescription__ProjectIdAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5606:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:5607:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:5607:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:5608:3: ruleN4mfIdentifier
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_0_2_0()); 
            pushFollow(FOLLOW_2);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_0_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ProjectIdAssignment_0_2"


    // $ANTLR start "rule__ProjectDescription__ProjectTypeAssignment_1_2"
    // InternalN4MFParser.g:5617:1: rule__ProjectDescription__ProjectTypeAssignment_1_2 : ( ruleProjectType ) ;
    public final void rule__ProjectDescription__ProjectTypeAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5621:1: ( ( ruleProjectType ) )
            // InternalN4MFParser.g:5622:2: ( ruleProjectType )
            {
            // InternalN4MFParser.g:5622:2: ( ruleProjectType )
            // InternalN4MFParser.g:5623:3: ruleProjectType
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectTypeProjectTypeEnumRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectType();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProjectTypeProjectTypeEnumRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ProjectTypeAssignment_1_2"


    // $ANTLR start "rule__ProjectDescription__ProjectVersionAssignment_2_2"
    // InternalN4MFParser.g:5632:1: rule__ProjectDescription__ProjectVersionAssignment_2_2 : ( ruleDeclaredVersion ) ;
    public final void rule__ProjectDescription__ProjectVersionAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5636:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:5637:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:5637:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:5638:3: ruleDeclaredVersion
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectVersionDeclaredVersionParserRuleCall_2_2_0()); 
            pushFollow(FOLLOW_2);
            ruleDeclaredVersion();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProjectVersionDeclaredVersionParserRuleCall_2_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ProjectVersionAssignment_2_2"


    // $ANTLR start "rule__ProjectDescription__DeclaredVendorIdAssignment_3_2"
    // InternalN4MFParser.g:5647:1: rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectDescription__DeclaredVendorIdAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5651:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:5652:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:5652:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:5653:3: ruleN4mfIdentifier
            {
             before(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_3_2_0()); 
            pushFollow(FOLLOW_2);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_3_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__DeclaredVendorIdAssignment_3_2"


    // $ANTLR start "rule__ProjectDescription__VendorNameAssignment_4_2"
    // InternalN4MFParser.g:5662:1: rule__ProjectDescription__VendorNameAssignment_4_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__VendorNameAssignment_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5666:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5667:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5667:2: ( RULE_STRING )
            // InternalN4MFParser.g:5668:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getVendorNameSTRINGTerminalRuleCall_4_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getVendorNameSTRINGTerminalRuleCall_4_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__VendorNameAssignment_4_2"


    // $ANTLR start "rule__ProjectDescription__MainModuleAssignment_5_2"
    // InternalN4MFParser.g:5677:1: rule__ProjectDescription__MainModuleAssignment_5_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__MainModuleAssignment_5_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5681:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5682:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5682:2: ( RULE_STRING )
            // InternalN4MFParser.g:5683:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getMainModuleSTRINGTerminalRuleCall_5_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getMainModuleSTRINGTerminalRuleCall_5_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__MainModuleAssignment_5_2"


    // $ANTLR start "rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2"
    // InternalN4MFParser.g:5692:1: rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5696:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5697:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5697:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5698:3: ruleProjectReference
            {
             before(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_6_2_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_6_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2"


    // $ANTLR start "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0"
    // InternalN4MFParser.g:5707:1: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 : ( ruleProvidedRuntimeLibraryDependency ) ;
    public final void rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5711:1: ( ( ruleProvidedRuntimeLibraryDependency ) )
            // InternalN4MFParser.g:5712:2: ( ruleProvidedRuntimeLibraryDependency )
            {
            // InternalN4MFParser.g:5712:2: ( ruleProvidedRuntimeLibraryDependency )
            // InternalN4MFParser.g:5713:3: ruleProvidedRuntimeLibraryDependency
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_7_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProvidedRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_7_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0"


    // $ANTLR start "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1"
    // InternalN4MFParser.g:5722:1: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 : ( ruleProvidedRuntimeLibraryDependency ) ;
    public final void rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5726:1: ( ( ruleProvidedRuntimeLibraryDependency ) )
            // InternalN4MFParser.g:5727:2: ( ruleProvidedRuntimeLibraryDependency )
            {
            // InternalN4MFParser.g:5727:2: ( ruleProvidedRuntimeLibraryDependency )
            // InternalN4MFParser.g:5728:3: ruleProvidedRuntimeLibraryDependency
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_7_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProvidedRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_7_2_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1"


    // $ANTLR start "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0"
    // InternalN4MFParser.g:5737:1: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 : ( ruleRequiredRuntimeLibraryDependency ) ;
    public final void rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5741:1: ( ( ruleRequiredRuntimeLibraryDependency ) )
            // InternalN4MFParser.g:5742:2: ( ruleRequiredRuntimeLibraryDependency )
            {
            // InternalN4MFParser.g:5742:2: ( ruleRequiredRuntimeLibraryDependency )
            // InternalN4MFParser.g:5743:3: ruleRequiredRuntimeLibraryDependency
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_8_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleRequiredRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_8_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0"


    // $ANTLR start "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1"
    // InternalN4MFParser.g:5752:1: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 : ( ruleRequiredRuntimeLibraryDependency ) ;
    public final void rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5756:1: ( ( ruleRequiredRuntimeLibraryDependency ) )
            // InternalN4MFParser.g:5757:2: ( ruleRequiredRuntimeLibraryDependency )
            {
            // InternalN4MFParser.g:5757:2: ( ruleRequiredRuntimeLibraryDependency )
            // InternalN4MFParser.g:5758:3: ruleRequiredRuntimeLibraryDependency
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_8_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleRequiredRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_8_2_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1"


    // $ANTLR start "rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0"
    // InternalN4MFParser.g:5767:1: rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 : ( ruleProjectDependency ) ;
    public final void rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5771:1: ( ( ruleProjectDependency ) )
            // InternalN4MFParser.g:5772:2: ( ruleProjectDependency )
            {
            // InternalN4MFParser.g:5772:2: ( ruleProjectDependency )
            // InternalN4MFParser.g:5773:3: ruleProjectDependency
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependencyParserRuleCall_9_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectDependency();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependencyParserRuleCall_9_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0"


    // $ANTLR start "rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1"
    // InternalN4MFParser.g:5782:1: rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 : ( ruleProjectDependency ) ;
    public final void rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5786:1: ( ( ruleProjectDependency ) )
            // InternalN4MFParser.g:5787:2: ( ruleProjectDependency )
            {
            // InternalN4MFParser.g:5787:2: ( ruleProjectDependency )
            // InternalN4MFParser.g:5788:3: ruleProjectDependency
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependencyParserRuleCall_9_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectDependency();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependencyParserRuleCall_9_2_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1"


    // $ANTLR start "rule__ProjectDescription__ImplementationIdAssignment_10_2"
    // InternalN4MFParser.g:5797:1: rule__ProjectDescription__ImplementationIdAssignment_10_2 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectDescription__ImplementationIdAssignment_10_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5801:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:5802:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:5802:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:5803:3: ruleN4mfIdentifier
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementationIdN4mfIdentifierParserRuleCall_10_2_0()); 
            pushFollow(FOLLOW_2);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getImplementationIdN4mfIdentifierParserRuleCall_10_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ImplementationIdAssignment_10_2"


    // $ANTLR start "rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0"
    // InternalN4MFParser.g:5812:1: rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5816:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5817:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5817:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5818:3: ruleProjectReference
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsProjectReferenceParserRuleCall_11_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsProjectReferenceParserRuleCall_11_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0"


    // $ANTLR start "rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1"
    // InternalN4MFParser.g:5827:1: rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5831:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5832:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5832:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5833:3: ruleProjectReference
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsProjectReferenceParserRuleCall_11_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsProjectReferenceParserRuleCall_11_2_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1"


    // $ANTLR start "rule__ProjectDescription__InitModulesAssignment_12_2_0"
    // InternalN4MFParser.g:5842:1: rule__ProjectDescription__InitModulesAssignment_12_2_0 : ( ruleBootstrapModule ) ;
    public final void rule__ProjectDescription__InitModulesAssignment_12_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5846:1: ( ( ruleBootstrapModule ) )
            // InternalN4MFParser.g:5847:2: ( ruleBootstrapModule )
            {
            // InternalN4MFParser.g:5847:2: ( ruleBootstrapModule )
            // InternalN4MFParser.g:5848:3: ruleBootstrapModule
            {
             before(grammarAccess.getProjectDescriptionAccess().getInitModulesBootstrapModuleParserRuleCall_12_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleBootstrapModule();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getInitModulesBootstrapModuleParserRuleCall_12_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__InitModulesAssignment_12_2_0"


    // $ANTLR start "rule__ProjectDescription__InitModulesAssignment_12_2_1_1"
    // InternalN4MFParser.g:5857:1: rule__ProjectDescription__InitModulesAssignment_12_2_1_1 : ( ruleBootstrapModule ) ;
    public final void rule__ProjectDescription__InitModulesAssignment_12_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5861:1: ( ( ruleBootstrapModule ) )
            // InternalN4MFParser.g:5862:2: ( ruleBootstrapModule )
            {
            // InternalN4MFParser.g:5862:2: ( ruleBootstrapModule )
            // InternalN4MFParser.g:5863:3: ruleBootstrapModule
            {
             before(grammarAccess.getProjectDescriptionAccess().getInitModulesBootstrapModuleParserRuleCall_12_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBootstrapModule();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getInitModulesBootstrapModuleParserRuleCall_12_2_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__InitModulesAssignment_12_2_1_1"


    // $ANTLR start "rule__ProjectDescription__ExecModuleAssignment_13_2"
    // InternalN4MFParser.g:5872:1: rule__ProjectDescription__ExecModuleAssignment_13_2 : ( ruleBootstrapModule ) ;
    public final void rule__ProjectDescription__ExecModuleAssignment_13_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5876:1: ( ( ruleBootstrapModule ) )
            // InternalN4MFParser.g:5877:2: ( ruleBootstrapModule )
            {
            // InternalN4MFParser.g:5877:2: ( ruleBootstrapModule )
            // InternalN4MFParser.g:5878:3: ruleBootstrapModule
            {
             before(grammarAccess.getProjectDescriptionAccess().getExecModuleBootstrapModuleParserRuleCall_13_2_0()); 
            pushFollow(FOLLOW_2);
            ruleBootstrapModule();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getExecModuleBootstrapModuleParserRuleCall_13_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ExecModuleAssignment_13_2"


    // $ANTLR start "rule__ProjectDescription__OutputPathRawAssignment_14_2"
    // InternalN4MFParser.g:5887:1: rule__ProjectDescription__OutputPathRawAssignment_14_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__OutputPathRawAssignment_14_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5891:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5892:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5892:2: ( RULE_STRING )
            // InternalN4MFParser.g:5893:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getOutputPathRawSTRINGTerminalRuleCall_14_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getOutputPathRawSTRINGTerminalRuleCall_14_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__OutputPathRawAssignment_14_2"


    // $ANTLR start "rule__ProjectDescription__LibraryPathsRawAssignment_15_2"
    // InternalN4MFParser.g:5902:1: rule__ProjectDescription__LibraryPathsRawAssignment_15_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__LibraryPathsRawAssignment_15_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5906:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5907:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5907:2: ( RULE_STRING )
            // InternalN4MFParser.g:5908:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__LibraryPathsRawAssignment_15_2"


    // $ANTLR start "rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1"
    // InternalN4MFParser.g:5917:1: rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5921:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5922:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5922:2: ( RULE_STRING )
            // InternalN4MFParser.g:5923:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_3_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1"


    // $ANTLR start "rule__ProjectDescription__ResourcePathsRawAssignment_16_2"
    // InternalN4MFParser.g:5932:1: rule__ProjectDescription__ResourcePathsRawAssignment_16_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__ResourcePathsRawAssignment_16_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5936:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5937:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5937:2: ( RULE_STRING )
            // InternalN4MFParser.g:5938:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ResourcePathsRawAssignment_16_2"


    // $ANTLR start "rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1"
    // InternalN4MFParser.g:5947:1: rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5951:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5952:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5952:2: ( RULE_STRING )
            // InternalN4MFParser.g:5953:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_3_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1"


    // $ANTLR start "rule__ProjectDescription__SourceFragmentAssignment_17_2"
    // InternalN4MFParser.g:5962:1: rule__ProjectDescription__SourceFragmentAssignment_17_2 : ( ruleSourceFragment ) ;
    public final void rule__ProjectDescription__SourceFragmentAssignment_17_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5966:1: ( ( ruleSourceFragment ) )
            // InternalN4MFParser.g:5967:2: ( ruleSourceFragment )
            {
            // InternalN4MFParser.g:5967:2: ( ruleSourceFragment )
            // InternalN4MFParser.g:5968:3: ruleSourceFragment
            {
             before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentSourceFragmentParserRuleCall_17_2_0()); 
            pushFollow(FOLLOW_2);
            ruleSourceFragment();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getSourceFragmentSourceFragmentParserRuleCall_17_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__SourceFragmentAssignment_17_2"


    // $ANTLR start "rule__ProjectDescription__ModuleFiltersAssignment_18_2"
    // InternalN4MFParser.g:5977:1: rule__ProjectDescription__ModuleFiltersAssignment_18_2 : ( ruleModuleFilter ) ;
    public final void rule__ProjectDescription__ModuleFiltersAssignment_18_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5981:1: ( ( ruleModuleFilter ) )
            // InternalN4MFParser.g:5982:2: ( ruleModuleFilter )
            {
            // InternalN4MFParser.g:5982:2: ( ruleModuleFilter )
            // InternalN4MFParser.g:5983:3: ruleModuleFilter
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersModuleFilterParserRuleCall_18_2_0()); 
            pushFollow(FOLLOW_2);
            ruleModuleFilter();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersModuleFilterParserRuleCall_18_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ModuleFiltersAssignment_18_2"


    // $ANTLR start "rule__ProjectDescription__TestedProjectsAssignment_19_2_0"
    // InternalN4MFParser.g:5992:1: rule__ProjectDescription__TestedProjectsAssignment_19_2_0 : ( ruleTestedProject ) ;
    public final void rule__ProjectDescription__TestedProjectsAssignment_19_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5996:1: ( ( ruleTestedProject ) )
            // InternalN4MFParser.g:5997:2: ( ruleTestedProject )
            {
            // InternalN4MFParser.g:5997:2: ( ruleTestedProject )
            // InternalN4MFParser.g:5998:3: ruleTestedProject
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectParserRuleCall_19_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleTestedProject();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectParserRuleCall_19_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__TestedProjectsAssignment_19_2_0"


    // $ANTLR start "rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1"
    // InternalN4MFParser.g:6007:1: rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 : ( ruleTestedProject ) ;
    public final void rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6011:1: ( ( ruleTestedProject ) )
            // InternalN4MFParser.g:6012:2: ( ruleTestedProject )
            {
            // InternalN4MFParser.g:6012:2: ( ruleTestedProject )
            // InternalN4MFParser.g:6013:3: ruleTestedProject
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectParserRuleCall_19_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleTestedProject();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectParserRuleCall_19_2_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1"


    // $ANTLR start "rule__ProjectDescription__ModuleLoaderAssignment_20_2"
    // InternalN4MFParser.g:6022:1: rule__ProjectDescription__ModuleLoaderAssignment_20_2 : ( ruleModuleLoader ) ;
    public final void rule__ProjectDescription__ModuleLoaderAssignment_20_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6026:1: ( ( ruleModuleLoader ) )
            // InternalN4MFParser.g:6027:2: ( ruleModuleLoader )
            {
            // InternalN4MFParser.g:6027:2: ( ruleModuleLoader )
            // InternalN4MFParser.g:6028:3: ruleModuleLoader
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleLoaderModuleLoaderEnumRuleCall_20_2_0()); 
            pushFollow(FOLLOW_2);
            ruleModuleLoader();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getModuleLoaderModuleLoaderEnumRuleCall_20_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ModuleLoaderAssignment_20_2"


    // $ANTLR start "rule__DeclaredVersion__MajorAssignment_0"
    // InternalN4MFParser.g:6037:1: rule__DeclaredVersion__MajorAssignment_0 : ( RULE_INT ) ;
    public final void rule__DeclaredVersion__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6041:1: ( ( RULE_INT ) )
            // InternalN4MFParser.g:6042:2: ( RULE_INT )
            {
            // InternalN4MFParser.g:6042:2: ( RULE_INT )
            // InternalN4MFParser.g:6043:3: RULE_INT
            {
             before(grammarAccess.getDeclaredVersionAccess().getMajorINTTerminalRuleCall_0_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDeclaredVersionAccess().getMajorINTTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__MajorAssignment_0"


    // $ANTLR start "rule__DeclaredVersion__MinorAssignment_1_1"
    // InternalN4MFParser.g:6052:1: rule__DeclaredVersion__MinorAssignment_1_1 : ( RULE_INT ) ;
    public final void rule__DeclaredVersion__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6056:1: ( ( RULE_INT ) )
            // InternalN4MFParser.g:6057:2: ( RULE_INT )
            {
            // InternalN4MFParser.g:6057:2: ( RULE_INT )
            // InternalN4MFParser.g:6058:3: RULE_INT
            {
             before(grammarAccess.getDeclaredVersionAccess().getMinorINTTerminalRuleCall_1_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDeclaredVersionAccess().getMinorINTTerminalRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__MinorAssignment_1_1"


    // $ANTLR start "rule__DeclaredVersion__MicroAssignment_1_2_1"
    // InternalN4MFParser.g:6067:1: rule__DeclaredVersion__MicroAssignment_1_2_1 : ( RULE_INT ) ;
    public final void rule__DeclaredVersion__MicroAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6071:1: ( ( RULE_INT ) )
            // InternalN4MFParser.g:6072:2: ( RULE_INT )
            {
            // InternalN4MFParser.g:6072:2: ( RULE_INT )
            // InternalN4MFParser.g:6073:3: RULE_INT
            {
             before(grammarAccess.getDeclaredVersionAccess().getMicroINTTerminalRuleCall_1_2_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDeclaredVersionAccess().getMicroINTTerminalRuleCall_1_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__MicroAssignment_1_2_1"


    // $ANTLR start "rule__DeclaredVersion__QualifierAssignment_2_1"
    // InternalN4MFParser.g:6082:1: rule__DeclaredVersion__QualifierAssignment_2_1 : ( ruleN4mfIdentifier ) ;
    public final void rule__DeclaredVersion__QualifierAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6086:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6087:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6087:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6088:3: ruleN4mfIdentifier
            {
             before(grammarAccess.getDeclaredVersionAccess().getQualifierN4mfIdentifierParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getDeclaredVersionAccess().getQualifierN4mfIdentifierParserRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DeclaredVersion__QualifierAssignment_2_1"


    // $ANTLR start "rule__SourceFragment__SourceFragmentTypeAssignment_0"
    // InternalN4MFParser.g:6097:1: rule__SourceFragment__SourceFragmentTypeAssignment_0 : ( ruleSourceFragmentType ) ;
    public final void rule__SourceFragment__SourceFragmentTypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6101:1: ( ( ruleSourceFragmentType ) )
            // InternalN4MFParser.g:6102:2: ( ruleSourceFragmentType )
            {
            // InternalN4MFParser.g:6102:2: ( ruleSourceFragmentType )
            // InternalN4MFParser.g:6103:3: ruleSourceFragmentType
            {
             before(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeSourceFragmentTypeEnumRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleSourceFragmentType();

            state._fsp--;

             after(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeSourceFragmentTypeEnumRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__SourceFragmentTypeAssignment_0"


    // $ANTLR start "rule__SourceFragment__PathsRawAssignment_2"
    // InternalN4MFParser.g:6112:1: rule__SourceFragment__PathsRawAssignment_2 : ( RULE_STRING ) ;
    public final void rule__SourceFragment__PathsRawAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6116:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6117:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6117:2: ( RULE_STRING )
            // InternalN4MFParser.g:6118:3: RULE_STRING
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__PathsRawAssignment_2"


    // $ANTLR start "rule__SourceFragment__PathsRawAssignment_3_1"
    // InternalN4MFParser.g:6127:1: rule__SourceFragment__PathsRawAssignment_3_1 : ( RULE_STRING ) ;
    public final void rule__SourceFragment__PathsRawAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6131:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6132:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6132:2: ( RULE_STRING )
            // InternalN4MFParser.g:6133:3: RULE_STRING
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_3_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__PathsRawAssignment_3_1"


    // $ANTLR start "rule__ModuleFilter__ModuleFilterTypeAssignment_0"
    // InternalN4MFParser.g:6142:1: rule__ModuleFilter__ModuleFilterTypeAssignment_0 : ( ruleModuleFilterType ) ;
    public final void rule__ModuleFilter__ModuleFilterTypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6146:1: ( ( ruleModuleFilterType ) )
            // InternalN4MFParser.g:6147:2: ( ruleModuleFilterType )
            {
            // InternalN4MFParser.g:6147:2: ( ruleModuleFilterType )
            // InternalN4MFParser.g:6148:3: ruleModuleFilterType
            {
             before(grammarAccess.getModuleFilterAccess().getModuleFilterTypeModuleFilterTypeEnumRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleModuleFilterType();

            state._fsp--;

             after(grammarAccess.getModuleFilterAccess().getModuleFilterTypeModuleFilterTypeEnumRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__ModuleFilterTypeAssignment_0"


    // $ANTLR start "rule__ModuleFilter__ModuleSpecifiersAssignment_2"
    // InternalN4MFParser.g:6157:1: rule__ModuleFilter__ModuleSpecifiersAssignment_2 : ( ruleModuleFilterSpecifier ) ;
    public final void rule__ModuleFilter__ModuleSpecifiersAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6161:1: ( ( ruleModuleFilterSpecifier ) )
            // InternalN4MFParser.g:6162:2: ( ruleModuleFilterSpecifier )
            {
            // InternalN4MFParser.g:6162:2: ( ruleModuleFilterSpecifier )
            // InternalN4MFParser.g:6163:3: ruleModuleFilterSpecifier
            {
             before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleModuleFilterSpecifier();

            state._fsp--;

             after(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__ModuleSpecifiersAssignment_2"


    // $ANTLR start "rule__ModuleFilter__ModuleSpecifiersAssignment_3_1"
    // InternalN4MFParser.g:6172:1: rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 : ( ruleModuleFilterSpecifier ) ;
    public final void rule__ModuleFilter__ModuleSpecifiersAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6176:1: ( ( ruleModuleFilterSpecifier ) )
            // InternalN4MFParser.g:6177:2: ( ruleModuleFilterSpecifier )
            {
            // InternalN4MFParser.g:6177:2: ( ruleModuleFilterSpecifier )
            // InternalN4MFParser.g:6178:3: ruleModuleFilterSpecifier
            {
             before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleModuleFilterSpecifier();

            state._fsp--;

             after(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilter__ModuleSpecifiersAssignment_3_1"


    // $ANTLR start "rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0"
    // InternalN4MFParser.g:6187:1: rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 : ( RULE_STRING ) ;
    public final void rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6191:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6192:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6192:2: ( RULE_STRING )
            // InternalN4MFParser.g:6193:3: RULE_STRING
            {
             before(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0"


    // $ANTLR start "rule__BootstrapModule__SourcePathAssignment_1_1"
    // InternalN4MFParser.g:6202:1: rule__BootstrapModule__SourcePathAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__BootstrapModule__SourcePathAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6206:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6207:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6207:2: ( RULE_STRING )
            // InternalN4MFParser.g:6208:3: RULE_STRING
            {
             before(grammarAccess.getBootstrapModuleAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getBootstrapModuleAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BootstrapModule__SourcePathAssignment_1_1"


    // $ANTLR start "rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0"
    // InternalN4MFParser.g:6217:1: rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 : ( RULE_STRING ) ;
    public final void rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6221:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6222:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6222:2: ( RULE_STRING )
            // InternalN4MFParser.g:6223:3: RULE_STRING
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0"


    // $ANTLR start "rule__ModuleFilterSpecifier__SourcePathAssignment_1_1"
    // InternalN4MFParser.g:6232:1: rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__ModuleFilterSpecifier__SourcePathAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6236:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6237:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6237:2: ( RULE_STRING )
            // InternalN4MFParser.g:6238:3: RULE_STRING
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ModuleFilterSpecifier__SourcePathAssignment_1_1"


    // $ANTLR start "rule__ProjectDependency__VersionConstraintAssignment_1"
    // InternalN4MFParser.g:6247:1: rule__ProjectDependency__VersionConstraintAssignment_1 : ( ruleVersionConstraint ) ;
    public final void rule__ProjectDependency__VersionConstraintAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6251:1: ( ( ruleVersionConstraint ) )
            // InternalN4MFParser.g:6252:2: ( ruleVersionConstraint )
            {
            // InternalN4MFParser.g:6252:2: ( ruleVersionConstraint )
            // InternalN4MFParser.g:6253:3: ruleVersionConstraint
            {
             before(grammarAccess.getProjectDependencyAccess().getVersionConstraintVersionConstraintParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVersionConstraint();

            state._fsp--;

             after(grammarAccess.getProjectDependencyAccess().getVersionConstraintVersionConstraintParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependency__VersionConstraintAssignment_1"


    // $ANTLR start "rule__ProjectDependency__DeclaredScopeAssignment_2"
    // InternalN4MFParser.g:6262:1: rule__ProjectDependency__DeclaredScopeAssignment_2 : ( ruleProjectDependencyScope ) ;
    public final void rule__ProjectDependency__DeclaredScopeAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6266:1: ( ( ruleProjectDependencyScope ) )
            // InternalN4MFParser.g:6267:2: ( ruleProjectDependencyScope )
            {
            // InternalN4MFParser.g:6267:2: ( ruleProjectDependencyScope )
            // InternalN4MFParser.g:6268:3: ruleProjectDependencyScope
            {
             before(grammarAccess.getProjectDependencyAccess().getDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectDependencyScope();

            state._fsp--;

             after(grammarAccess.getProjectDependencyAccess().getDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependency__DeclaredScopeAssignment_2"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0"
    // InternalN4MFParser.g:6277:1: rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6281:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6282:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6282:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6283:3: ruleN4mfIdentifier
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0()); 
            pushFollow(FOLLOW_2);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getProjectIdWithOptionalVendorAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0"


    // $ANTLR start "rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1"
    // InternalN4MFParser.g:6292:1: rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6296:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6297:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6297:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6298:3: ruleN4mfIdentifier
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getProjectIdN4mfIdentifierParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getProjectIdWithOptionalVendorAccess().getProjectIdN4mfIdentifierParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1"


    // $ANTLR start "rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0"
    // InternalN4MFParser.g:6307:1: rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 : ( ( LeftParenthesis ) ) ;
    public final void rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6311:1: ( ( ( LeftParenthesis ) ) )
            // InternalN4MFParser.g:6312:2: ( ( LeftParenthesis ) )
            {
            // InternalN4MFParser.g:6312:2: ( ( LeftParenthesis ) )
            // InternalN4MFParser.g:6313:3: ( LeftParenthesis )
            {
             before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); 
            // InternalN4MFParser.g:6314:3: ( LeftParenthesis )
            // InternalN4MFParser.g:6315:4: LeftParenthesis
            {
             before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); 
            match(input,LeftParenthesis,FOLLOW_2); 
             after(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); 

            }

             after(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0"


    // $ANTLR start "rule__VersionConstraint__LowerVersionAssignment_0_1"
    // InternalN4MFParser.g:6326:1: rule__VersionConstraint__LowerVersionAssignment_0_1 : ( ruleDeclaredVersion ) ;
    public final void rule__VersionConstraint__LowerVersionAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6330:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6331:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6331:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6332:3: ruleDeclaredVersion
            {
             before(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDeclaredVersion();

            state._fsp--;

             after(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__LowerVersionAssignment_0_1"


    // $ANTLR start "rule__VersionConstraint__UpperVersionAssignment_0_2_0_1"
    // InternalN4MFParser.g:6341:1: rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 : ( ruleDeclaredVersion ) ;
    public final void rule__VersionConstraint__UpperVersionAssignment_0_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6345:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6346:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6346:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6347:3: ruleDeclaredVersion
            {
             before(grammarAccess.getVersionConstraintAccess().getUpperVersionDeclaredVersionParserRuleCall_0_2_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDeclaredVersion();

            state._fsp--;

             after(grammarAccess.getVersionConstraintAccess().getUpperVersionDeclaredVersionParserRuleCall_0_2_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__UpperVersionAssignment_0_2_0_1"


    // $ANTLR start "rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0"
    // InternalN4MFParser.g:6356:1: rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 : ( ( RightParenthesis ) ) ;
    public final void rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6360:1: ( ( ( RightParenthesis ) ) )
            // InternalN4MFParser.g:6361:2: ( ( RightParenthesis ) )
            {
            // InternalN4MFParser.g:6361:2: ( ( RightParenthesis ) )
            // InternalN4MFParser.g:6362:3: ( RightParenthesis )
            {
             before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); 
            // InternalN4MFParser.g:6363:3: ( RightParenthesis )
            // InternalN4MFParser.g:6364:4: RightParenthesis
            {
             before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); 
            match(input,RightParenthesis,FOLLOW_2); 
             after(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); 

            }

             after(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0"


    // $ANTLR start "rule__VersionConstraint__LowerVersionAssignment_1"
    // InternalN4MFParser.g:6375:1: rule__VersionConstraint__LowerVersionAssignment_1 : ( ruleDeclaredVersion ) ;
    public final void rule__VersionConstraint__LowerVersionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6379:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6380:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6380:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6381:3: ruleDeclaredVersion
            {
             before(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDeclaredVersion();

            state._fsp--;

             after(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__VersionConstraint__LowerVersionAssignment_1"

    // Delegated rules


    protected DFA37 dfa37 = new DFA37(this);
    protected DFA38 dfa38 = new DFA38(this);
    protected DFA39 dfa39 = new DFA39(this);
    protected DFA40 dfa40 = new DFA40(this);
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA42 dfa42 = new DFA42(this);
    protected DFA43 dfa43 = new DFA43(this);
    protected DFA44 dfa44 = new DFA44(this);
    protected DFA45 dfa45 = new DFA45(this);
    protected DFA46 dfa46 = new DFA46(this);
    protected DFA47 dfa47 = new DFA47(this);
    protected DFA48 dfa48 = new DFA48(this);
    protected DFA49 dfa49 = new DFA49(this);
    protected DFA50 dfa50 = new DFA50(this);
    protected DFA51 dfa51 = new DFA51(this);
    protected DFA52 dfa52 = new DFA52(this);
    protected DFA53 dfa53 = new DFA53(this);
    protected DFA54 dfa54 = new DFA54(this);
    protected DFA55 dfa55 = new DFA55(this);
    protected DFA56 dfa56 = new DFA56(this);
    protected DFA57 dfa57 = new DFA57(this);
    protected DFA58 dfa58 = new DFA58(this);
    static final String dfa_1s = "\27\uffff";
    static final String dfa_2s = "\1\uffff\13\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23";
    static final String dfa_3s = "\1\10\13\4\1\47\3\4\1\46\2\4\2\uffff\2\4";
    static final String dfa_4s = "\1\67\13\70\1\47\3\70\1\46\2\70\2\uffff\2\70";
    static final String dfa_5s = "\23\uffff\1\2\1\1\2\uffff";
    static final String dfa_6s = "\27\uffff}>";
    static final String[] dfa_7s = {
            "\1\14\2\uffff\1\4\2\uffff\1\13\4\uffff\1\3\1\17\2\uffff\1\6\2\uffff\1\10\1\2\1\11\1\20\1\5\2\uffff\1\12\1\uffff\1\21\1\uffff\1\7\3\uffff\1\22\1\16\1\15\13\uffff\1\1",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\1\25",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\1\26",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "",
            "",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23",
            "\5\23\1\uffff\3\23\1\uffff\2\23\2\uffff\2\23\1\uffff\3\23\2\uffff\3\23\1\uffff\1\23\2\uffff\2\23\2\uffff\1\23\3\uffff\1\23\3\uffff\1\23\1\uffff\1\23\2\uffff\1\24\1\23\2\uffff\1\23\1\uffff\1\23"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "4647:2: ( rule__ProjectIdWithOptionalVendor__Group_0__0 )?";
        }
    }
    static final String dfa_8s = "\26\uffff";
    static final String dfa_9s = "\1\4\25\uffff";
    static final String dfa_10s = "\1\45\25\uffff";
    static final String dfa_11s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25";
    static final String dfa_12s = "\1\0\25\uffff}>";
    static final String[] dfa_13s = {
            "\1\7\1\10\1\11\1\14\1\12\1\uffff\1\13\1\3\1\24\1\uffff\1\23\1\25\2\uffff\1\15\1\2\1\uffff\1\16\1\6\1\5\2\uffff\1\20\1\1\1\21\1\uffff\1\4\2\uffff\1\22\3\uffff\1\17",
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

    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA38 extends DFA {

        public DFA38(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 38;
            this.eot = dfa_8;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "5026:3: ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA38_0 = input.LA(1);

                         
                        int index38_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA38_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA38_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA38_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA38_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA38_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA38_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA38_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA38_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA38_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA38_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA38_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA38_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA38_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA38_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA38_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA38_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA38_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA38_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA38_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA38_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA38_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                         
                        input.seek(index38_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 38, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_14s = "\1\26\26\uffff";
    static final String dfa_15s = "\1\4\26\uffff";
    static final String dfa_16s = "\1\45\26\uffff";
    static final String dfa_17s = "\1\uffff\25\1\1\2";
    static final String dfa_18s = "\1\0\26\uffff}>";
    static final String[] dfa_19s = {
            "\1\7\1\10\1\11\1\14\1\12\1\uffff\1\13\1\3\1\24\1\uffff\1\23\1\25\2\uffff\1\15\1\2\1\uffff\1\16\1\6\1\5\2\uffff\1\20\1\1\1\21\1\uffff\1\4\2\uffff\1\22\3\uffff\1\17",
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
    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[][] dfa_19 = unpackEncodedStringArray(dfa_19s);

    class DFA39 extends DFA {

        public DFA39(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 39;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5356:2: ( rule__ProjectDescription__UnorderedGroup__1 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA39_0 = input.LA(1);

                         
                        int index39_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA39_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA39_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA39_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA39_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA39_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA39_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA39_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA39_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA39_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA39_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA39_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA39_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA39_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA39_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA39_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA39_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA39_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA39_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA39_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA39_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA39_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA39_0==EOF) ) {s = 22;}

                         
                        input.seek(index39_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 39, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA40 extends DFA {

        public DFA40(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 40;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5368:2: ( rule__ProjectDescription__UnorderedGroup__2 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA40_0 = input.LA(1);

                         
                        int index40_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA40_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA40_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA40_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA40_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA40_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA40_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA40_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA40_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA40_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA40_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA40_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA40_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA40_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA40_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA40_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA40_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA40_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA40_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA40_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA40_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA40_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA40_0==EOF) ) {s = 22;}

                         
                        input.seek(index40_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 40, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5380:2: ( rule__ProjectDescription__UnorderedGroup__3 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA41_0 = input.LA(1);

                         
                        int index41_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA41_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA41_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA41_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA41_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA41_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA41_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA41_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA41_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA41_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA41_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA41_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA41_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA41_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA41_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA41_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA41_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA41_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA41_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA41_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA41_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA41_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA41_0==EOF) ) {s = 22;}

                         
                        input.seek(index41_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 41, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA42 extends DFA {

        public DFA42(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 42;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5392:2: ( rule__ProjectDescription__UnorderedGroup__4 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA42_0 = input.LA(1);

                         
                        int index42_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA42_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA42_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA42_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA42_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA42_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA42_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA42_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA42_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA42_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA42_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA42_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA42_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA42_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA42_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA42_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA42_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA42_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA42_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA42_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA42_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA42_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA42_0==EOF) ) {s = 22;}

                         
                        input.seek(index42_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 42, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA43 extends DFA {

        public DFA43(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 43;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5404:2: ( rule__ProjectDescription__UnorderedGroup__5 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA43_0 = input.LA(1);

                         
                        int index43_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA43_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA43_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA43_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA43_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA43_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA43_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA43_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA43_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA43_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA43_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA43_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA43_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA43_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA43_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA43_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA43_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA43_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA43_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA43_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA43_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA43_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA43_0==EOF) ) {s = 22;}

                         
                        input.seek(index43_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 43, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA44 extends DFA {

        public DFA44(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 44;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5416:2: ( rule__ProjectDescription__UnorderedGroup__6 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA44_0 = input.LA(1);

                         
                        int index44_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA44_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA44_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA44_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA44_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA44_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA44_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA44_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA44_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA44_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA44_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA44_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA44_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA44_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA44_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA44_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA44_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA44_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA44_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA44_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA44_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA44_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA44_0==EOF) ) {s = 22;}

                         
                        input.seek(index44_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 44, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA45 extends DFA {

        public DFA45(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 45;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5428:2: ( rule__ProjectDescription__UnorderedGroup__7 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA45_0 = input.LA(1);

                         
                        int index45_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA45_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA45_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA45_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA45_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA45_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA45_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA45_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA45_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA45_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA45_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA45_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA45_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA45_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA45_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA45_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA45_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA45_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA45_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA45_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA45_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA45_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA45_0==EOF) ) {s = 22;}

                         
                        input.seek(index45_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 45, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA46 extends DFA {

        public DFA46(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 46;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5440:2: ( rule__ProjectDescription__UnorderedGroup__8 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA46_0 = input.LA(1);

                         
                        int index46_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA46_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA46_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA46_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA46_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA46_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA46_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA46_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA46_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA46_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA46_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA46_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA46_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA46_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA46_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA46_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA46_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA46_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA46_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA46_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA46_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA46_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA46_0==EOF) ) {s = 22;}

                         
                        input.seek(index46_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 46, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA47 extends DFA {

        public DFA47(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 47;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5452:2: ( rule__ProjectDescription__UnorderedGroup__9 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA47_0 = input.LA(1);

                         
                        int index47_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA47_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA47_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA47_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA47_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA47_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA47_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA47_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA47_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA47_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA47_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA47_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA47_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA47_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA47_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA47_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA47_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA47_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA47_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA47_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA47_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA47_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA47_0==EOF) ) {s = 22;}

                         
                        input.seek(index47_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 47, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA48 extends DFA {

        public DFA48(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 48;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5464:2: ( rule__ProjectDescription__UnorderedGroup__10 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA48_0 = input.LA(1);

                         
                        int index48_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA48_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA48_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA48_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA48_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA48_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA48_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA48_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA48_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA48_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA48_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA48_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA48_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA48_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA48_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA48_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA48_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA48_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA48_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA48_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA48_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA48_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA48_0==EOF) ) {s = 22;}

                         
                        input.seek(index48_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 48, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA49 extends DFA {

        public DFA49(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 49;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5476:2: ( rule__ProjectDescription__UnorderedGroup__11 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA49_0 = input.LA(1);

                         
                        int index49_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA49_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA49_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA49_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA49_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA49_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA49_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA49_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA49_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA49_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA49_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA49_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA49_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA49_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA49_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA49_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA49_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA49_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA49_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA49_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA49_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA49_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA49_0==EOF) ) {s = 22;}

                         
                        input.seek(index49_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 49, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA50 extends DFA {

        public DFA50(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 50;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5488:2: ( rule__ProjectDescription__UnorderedGroup__12 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA50_0 = input.LA(1);

                         
                        int index50_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA50_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA50_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA50_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA50_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA50_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA50_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA50_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA50_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA50_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA50_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA50_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA50_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA50_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA50_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA50_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA50_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA50_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA50_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA50_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA50_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA50_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA50_0==EOF) ) {s = 22;}

                         
                        input.seek(index50_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 50, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA51 extends DFA {

        public DFA51(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 51;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5500:2: ( rule__ProjectDescription__UnorderedGroup__13 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA51_0 = input.LA(1);

                         
                        int index51_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA51_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA51_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA51_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA51_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA51_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA51_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA51_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA51_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA51_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA51_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA51_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA51_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA51_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA51_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA51_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA51_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA51_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA51_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA51_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA51_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA51_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA51_0==EOF) ) {s = 22;}

                         
                        input.seek(index51_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 51, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA52 extends DFA {

        public DFA52(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 52;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5512:2: ( rule__ProjectDescription__UnorderedGroup__14 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA52_0 = input.LA(1);

                         
                        int index52_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA52_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA52_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA52_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA52_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA52_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA52_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA52_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA52_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA52_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA52_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA52_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA52_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA52_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA52_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA52_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA52_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA52_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA52_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA52_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA52_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA52_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA52_0==EOF) ) {s = 22;}

                         
                        input.seek(index52_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 52, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA53 extends DFA {

        public DFA53(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 53;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5524:2: ( rule__ProjectDescription__UnorderedGroup__15 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA53_0 = input.LA(1);

                         
                        int index53_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA53_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA53_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA53_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA53_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA53_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA53_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA53_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA53_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA53_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA53_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA53_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA53_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA53_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA53_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA53_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA53_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA53_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA53_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA53_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA53_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA53_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA53_0==EOF) ) {s = 22;}

                         
                        input.seek(index53_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 53, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA54 extends DFA {

        public DFA54(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 54;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5536:2: ( rule__ProjectDescription__UnorderedGroup__16 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA54_0 = input.LA(1);

                         
                        int index54_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA54_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA54_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA54_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA54_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA54_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA54_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA54_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA54_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA54_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA54_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA54_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA54_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA54_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA54_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA54_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA54_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA54_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA54_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA54_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA54_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA54_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA54_0==EOF) ) {s = 22;}

                         
                        input.seek(index54_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 54, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA55 extends DFA {

        public DFA55(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 55;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5548:2: ( rule__ProjectDescription__UnorderedGroup__17 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA55_0 = input.LA(1);

                         
                        int index55_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA55_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA55_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA55_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA55_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA55_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA55_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA55_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA55_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA55_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA55_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA55_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA55_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA55_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA55_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA55_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA55_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA55_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA55_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA55_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA55_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA55_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA55_0==EOF) ) {s = 22;}

                         
                        input.seek(index55_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 55, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA56 extends DFA {

        public DFA56(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 56;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5560:2: ( rule__ProjectDescription__UnorderedGroup__18 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA56_0 = input.LA(1);

                         
                        int index56_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA56_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA56_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA56_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA56_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA56_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA56_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA56_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA56_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA56_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA56_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA56_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA56_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA56_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA56_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA56_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA56_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA56_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA56_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA56_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA56_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA56_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA56_0==EOF) ) {s = 22;}

                         
                        input.seek(index56_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 56, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA57 extends DFA {

        public DFA57(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 57;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5572:2: ( rule__ProjectDescription__UnorderedGroup__19 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA57_0 = input.LA(1);

                         
                        int index57_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA57_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA57_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA57_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA57_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA57_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA57_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA57_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA57_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA57_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA57_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA57_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA57_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA57_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA57_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA57_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA57_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA57_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA57_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA57_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA57_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA57_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA57_0==EOF) ) {s = 22;}

                         
                        input.seek(index57_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 57, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = dfa_1;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "5584:2: ( rule__ProjectDescription__UnorderedGroup__20 )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA58_0 = input.LA(1);

                         
                        int index58_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( LA58_0 == ProjectId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {s = 1;}

                        else if ( LA58_0 == ProjectType && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {s = 2;}

                        else if ( LA58_0 == ProjectVersion && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {s = 3;}

                        else if ( LA58_0 == VendorId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {s = 4;}

                        else if ( LA58_0 == VendorName && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {s = 5;}

                        else if ( LA58_0 == MainModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {s = 6;}

                        else if ( LA58_0 == ExtendedRuntimeEnvironment && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {s = 7;}

                        else if ( LA58_0 == ProvidedRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {s = 8;}

                        else if ( LA58_0 == RequiredRuntimeLibraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {s = 9;}

                        else if ( LA58_0 == ProjectDependencies && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {s = 10;}

                        else if ( LA58_0 == ImplementationId && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {s = 11;}

                        else if ( LA58_0 == ImplementedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {s = 12;}

                        else if ( LA58_0 == InitModules && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {s = 13;}

                        else if ( LA58_0 == ExecModule && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {s = 14;}

                        else if ( LA58_0 == Output && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {s = 15;}

                        else if ( LA58_0 == Libraries && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {s = 16;}

                        else if ( LA58_0 == Resources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {s = 17;}

                        else if ( LA58_0 == Sources && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {s = 18;}

                        else if ( LA58_0 == ModuleFilters && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {s = 19;}

                        else if ( LA58_0 == TestedProjects && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {s = 20;}

                        else if ( LA58_0 == ModuleLoader && getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {s = 21;}

                        else if ( (LA58_0==EOF) ) {s = 22;}

                         
                        input.seek(index58_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 58, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x00800E2A7C984900L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00000A1022102200L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00C00E2A7C984900L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0240000000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0040800000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000024100000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000024100000002L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000001010000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000001010002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000010080020000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0003000000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0108220400000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000C00000000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0010400000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x000000225CECDDF2L});

}
