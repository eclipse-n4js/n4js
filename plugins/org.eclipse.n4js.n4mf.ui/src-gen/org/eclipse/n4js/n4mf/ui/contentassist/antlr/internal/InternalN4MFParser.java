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


    // $ANTLR start "entryRuleProjectReference"
    // InternalN4MFParser.g:263:1: entryRuleProjectReference : ruleProjectReference EOF ;
    public final void entryRuleProjectReference() throws RecognitionException {
        try {
            // InternalN4MFParser.g:264:1: ( ruleProjectReference EOF )
            // InternalN4MFParser.g:265:1: ruleProjectReference EOF
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
    // InternalN4MFParser.g:272:1: ruleProjectReference : ( ruleProjectIdWithOptionalVendor ) ;
    public final void ruleProjectReference() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:276:2: ( ( ruleProjectIdWithOptionalVendor ) )
            // InternalN4MFParser.g:277:2: ( ruleProjectIdWithOptionalVendor )
            {
            // InternalN4MFParser.g:277:2: ( ruleProjectIdWithOptionalVendor )
            // InternalN4MFParser.g:278:3: ruleProjectIdWithOptionalVendor
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
    // InternalN4MFParser.g:288:1: entryRuleProjectDependency : ruleProjectDependency EOF ;
    public final void entryRuleProjectDependency() throws RecognitionException {
        try {
            // InternalN4MFParser.g:289:1: ( ruleProjectDependency EOF )
            // InternalN4MFParser.g:290:1: ruleProjectDependency EOF
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
    // InternalN4MFParser.g:297:1: ruleProjectDependency : ( ( rule__ProjectDependency__Group__0 ) ) ;
    public final void ruleProjectDependency() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:301:2: ( ( ( rule__ProjectDependency__Group__0 ) ) )
            // InternalN4MFParser.g:302:2: ( ( rule__ProjectDependency__Group__0 ) )
            {
            // InternalN4MFParser.g:302:2: ( ( rule__ProjectDependency__Group__0 ) )
            // InternalN4MFParser.g:303:3: ( rule__ProjectDependency__Group__0 )
            {
             before(grammarAccess.getProjectDependencyAccess().getGroup()); 
            // InternalN4MFParser.g:304:3: ( rule__ProjectDependency__Group__0 )
            // InternalN4MFParser.g:304:4: rule__ProjectDependency__Group__0
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
    // InternalN4MFParser.g:314:1: ruleProjectIdWithOptionalVendor : ( ( rule__ProjectIdWithOptionalVendor__Group__0 ) ) ;
    public final void ruleProjectIdWithOptionalVendor() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:318:2: ( ( ( rule__ProjectIdWithOptionalVendor__Group__0 ) ) )
            // InternalN4MFParser.g:319:2: ( ( rule__ProjectIdWithOptionalVendor__Group__0 ) )
            {
            // InternalN4MFParser.g:319:2: ( ( rule__ProjectIdWithOptionalVendor__Group__0 ) )
            // InternalN4MFParser.g:320:3: ( rule__ProjectIdWithOptionalVendor__Group__0 )
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getGroup()); 
            // InternalN4MFParser.g:321:3: ( rule__ProjectIdWithOptionalVendor__Group__0 )
            // InternalN4MFParser.g:321:4: rule__ProjectIdWithOptionalVendor__Group__0
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
    // InternalN4MFParser.g:330:1: entryRuleVersionConstraint : ruleVersionConstraint EOF ;
    public final void entryRuleVersionConstraint() throws RecognitionException {
        try {
            // InternalN4MFParser.g:331:1: ( ruleVersionConstraint EOF )
            // InternalN4MFParser.g:332:1: ruleVersionConstraint EOF
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
    // InternalN4MFParser.g:339:1: ruleVersionConstraint : ( ( rule__VersionConstraint__Alternatives ) ) ;
    public final void ruleVersionConstraint() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:343:2: ( ( ( rule__VersionConstraint__Alternatives ) ) )
            // InternalN4MFParser.g:344:2: ( ( rule__VersionConstraint__Alternatives ) )
            {
            // InternalN4MFParser.g:344:2: ( ( rule__VersionConstraint__Alternatives ) )
            // InternalN4MFParser.g:345:3: ( rule__VersionConstraint__Alternatives )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives()); 
            // InternalN4MFParser.g:346:3: ( rule__VersionConstraint__Alternatives )
            // InternalN4MFParser.g:346:4: rule__VersionConstraint__Alternatives
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
    // InternalN4MFParser.g:355:1: entryRuleN4mfIdentifier : ruleN4mfIdentifier EOF ;
    public final void entryRuleN4mfIdentifier() throws RecognitionException {
        try {
            // InternalN4MFParser.g:356:1: ( ruleN4mfIdentifier EOF )
            // InternalN4MFParser.g:357:1: ruleN4mfIdentifier EOF
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
    // InternalN4MFParser.g:364:1: ruleN4mfIdentifier : ( ( rule__N4mfIdentifier__Alternatives ) ) ;
    public final void ruleN4mfIdentifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:368:2: ( ( ( rule__N4mfIdentifier__Alternatives ) ) )
            // InternalN4MFParser.g:369:2: ( ( rule__N4mfIdentifier__Alternatives ) )
            {
            // InternalN4MFParser.g:369:2: ( ( rule__N4mfIdentifier__Alternatives ) )
            // InternalN4MFParser.g:370:3: ( rule__N4mfIdentifier__Alternatives )
            {
             before(grammarAccess.getN4mfIdentifierAccess().getAlternatives()); 
            // InternalN4MFParser.g:371:3: ( rule__N4mfIdentifier__Alternatives )
            // InternalN4MFParser.g:371:4: rule__N4mfIdentifier__Alternatives
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
    // InternalN4MFParser.g:380:1: ruleProjectType : ( ( rule__ProjectType__Alternatives ) ) ;
    public final void ruleProjectType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:384:1: ( ( ( rule__ProjectType__Alternatives ) ) )
            // InternalN4MFParser.g:385:2: ( ( rule__ProjectType__Alternatives ) )
            {
            // InternalN4MFParser.g:385:2: ( ( rule__ProjectType__Alternatives ) )
            // InternalN4MFParser.g:386:3: ( rule__ProjectType__Alternatives )
            {
             before(grammarAccess.getProjectTypeAccess().getAlternatives()); 
            // InternalN4MFParser.g:387:3: ( rule__ProjectType__Alternatives )
            // InternalN4MFParser.g:387:4: rule__ProjectType__Alternatives
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
    // InternalN4MFParser.g:396:1: ruleSourceFragmentType : ( ( rule__SourceFragmentType__Alternatives ) ) ;
    public final void ruleSourceFragmentType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:400:1: ( ( ( rule__SourceFragmentType__Alternatives ) ) )
            // InternalN4MFParser.g:401:2: ( ( rule__SourceFragmentType__Alternatives ) )
            {
            // InternalN4MFParser.g:401:2: ( ( rule__SourceFragmentType__Alternatives ) )
            // InternalN4MFParser.g:402:3: ( rule__SourceFragmentType__Alternatives )
            {
             before(grammarAccess.getSourceFragmentTypeAccess().getAlternatives()); 
            // InternalN4MFParser.g:403:3: ( rule__SourceFragmentType__Alternatives )
            // InternalN4MFParser.g:403:4: rule__SourceFragmentType__Alternatives
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
    // InternalN4MFParser.g:412:1: ruleModuleFilterType : ( ( rule__ModuleFilterType__Alternatives ) ) ;
    public final void ruleModuleFilterType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:416:1: ( ( ( rule__ModuleFilterType__Alternatives ) ) )
            // InternalN4MFParser.g:417:2: ( ( rule__ModuleFilterType__Alternatives ) )
            {
            // InternalN4MFParser.g:417:2: ( ( rule__ModuleFilterType__Alternatives ) )
            // InternalN4MFParser.g:418:3: ( rule__ModuleFilterType__Alternatives )
            {
             before(grammarAccess.getModuleFilterTypeAccess().getAlternatives()); 
            // InternalN4MFParser.g:419:3: ( rule__ModuleFilterType__Alternatives )
            // InternalN4MFParser.g:419:4: rule__ModuleFilterType__Alternatives
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
    // InternalN4MFParser.g:428:1: ruleProjectDependencyScope : ( ( rule__ProjectDependencyScope__Alternatives ) ) ;
    public final void ruleProjectDependencyScope() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:432:1: ( ( ( rule__ProjectDependencyScope__Alternatives ) ) )
            // InternalN4MFParser.g:433:2: ( ( rule__ProjectDependencyScope__Alternatives ) )
            {
            // InternalN4MFParser.g:433:2: ( ( rule__ProjectDependencyScope__Alternatives ) )
            // InternalN4MFParser.g:434:3: ( rule__ProjectDependencyScope__Alternatives )
            {
             before(grammarAccess.getProjectDependencyScopeAccess().getAlternatives()); 
            // InternalN4MFParser.g:435:3: ( rule__ProjectDependencyScope__Alternatives )
            // InternalN4MFParser.g:435:4: rule__ProjectDependencyScope__Alternatives
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
    // InternalN4MFParser.g:444:1: ruleModuleLoader : ( ( rule__ModuleLoader__Alternatives ) ) ;
    public final void ruleModuleLoader() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:448:1: ( ( ( rule__ModuleLoader__Alternatives ) ) )
            // InternalN4MFParser.g:449:2: ( ( rule__ModuleLoader__Alternatives ) )
            {
            // InternalN4MFParser.g:449:2: ( ( rule__ModuleLoader__Alternatives ) )
            // InternalN4MFParser.g:450:3: ( rule__ModuleLoader__Alternatives )
            {
             before(grammarAccess.getModuleLoaderAccess().getAlternatives()); 
            // InternalN4MFParser.g:451:3: ( rule__ModuleLoader__Alternatives )
            // InternalN4MFParser.g:451:4: rule__ModuleLoader__Alternatives
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
    // InternalN4MFParser.g:459:1: rule__VersionConstraint__Alternatives : ( ( ( rule__VersionConstraint__Group_0__0 ) ) | ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) ) );
    public final void rule__VersionConstraint__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:463:1: ( ( ( rule__VersionConstraint__Group_0__0 ) ) | ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) ) )
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
                    // InternalN4MFParser.g:464:2: ( ( rule__VersionConstraint__Group_0__0 ) )
                    {
                    // InternalN4MFParser.g:464:2: ( ( rule__VersionConstraint__Group_0__0 ) )
                    // InternalN4MFParser.g:465:3: ( rule__VersionConstraint__Group_0__0 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getGroup_0()); 
                    // InternalN4MFParser.g:466:3: ( rule__VersionConstraint__Group_0__0 )
                    // InternalN4MFParser.g:466:4: rule__VersionConstraint__Group_0__0
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
                    // InternalN4MFParser.g:470:2: ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) )
                    {
                    // InternalN4MFParser.g:470:2: ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) )
                    // InternalN4MFParser.g:471:3: ( rule__VersionConstraint__LowerVersionAssignment_1 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_1()); 
                    // InternalN4MFParser.g:472:3: ( rule__VersionConstraint__LowerVersionAssignment_1 )
                    // InternalN4MFParser.g:472:4: rule__VersionConstraint__LowerVersionAssignment_1
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
    // InternalN4MFParser.g:480:1: rule__VersionConstraint__Alternatives_0_0 : ( ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) ) | ( LeftSquareBracket ) );
    public final void rule__VersionConstraint__Alternatives_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:484:1: ( ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) ) | ( LeftSquareBracket ) )
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
                    // InternalN4MFParser.g:485:2: ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) )
                    {
                    // InternalN4MFParser.g:485:2: ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) )
                    // InternalN4MFParser.g:486:3: ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundAssignment_0_0_0()); 
                    // InternalN4MFParser.g:487:3: ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 )
                    // InternalN4MFParser.g:487:4: rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0
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
                    // InternalN4MFParser.g:491:2: ( LeftSquareBracket )
                    {
                    // InternalN4MFParser.g:491:2: ( LeftSquareBracket )
                    // InternalN4MFParser.g:492:3: LeftSquareBracket
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
    // InternalN4MFParser.g:501:1: rule__VersionConstraint__Alternatives_0_2 : ( ( ( rule__VersionConstraint__Group_0_2_0__0 )? ) | ( RightParenthesis ) );
    public final void rule__VersionConstraint__Alternatives_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:505:1: ( ( ( rule__VersionConstraint__Group_0_2_0__0 )? ) | ( RightParenthesis ) )
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
                    // InternalN4MFParser.g:506:2: ( ( rule__VersionConstraint__Group_0_2_0__0 )? )
                    {
                    // InternalN4MFParser.g:506:2: ( ( rule__VersionConstraint__Group_0_2_0__0 )? )
                    // InternalN4MFParser.g:507:3: ( rule__VersionConstraint__Group_0_2_0__0 )?
                    {
                     before(grammarAccess.getVersionConstraintAccess().getGroup_0_2_0()); 
                    // InternalN4MFParser.g:508:3: ( rule__VersionConstraint__Group_0_2_0__0 )?
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
                            // InternalN4MFParser.g:508:4: rule__VersionConstraint__Group_0_2_0__0
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
                    // InternalN4MFParser.g:512:2: ( RightParenthesis )
                    {
                    // InternalN4MFParser.g:512:2: ( RightParenthesis )
                    // InternalN4MFParser.g:513:3: RightParenthesis
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
    // InternalN4MFParser.g:522:1: rule__VersionConstraint__Alternatives_0_2_0_2 : ( ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) ) | ( RightSquareBracket ) );
    public final void rule__VersionConstraint__Alternatives_0_2_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:526:1: ( ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) ) | ( RightSquareBracket ) )
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
                    // InternalN4MFParser.g:527:2: ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) )
                    {
                    // InternalN4MFParser.g:527:2: ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) )
                    // InternalN4MFParser.g:528:3: ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundAssignment_0_2_0_2_0()); 
                    // InternalN4MFParser.g:529:3: ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 )
                    // InternalN4MFParser.g:529:4: rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0
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
                    // InternalN4MFParser.g:533:2: ( RightSquareBracket )
                    {
                    // InternalN4MFParser.g:533:2: ( RightSquareBracket )
                    // InternalN4MFParser.g:534:3: RightSquareBracket
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
    // InternalN4MFParser.g:543:1: rule__N4mfIdentifier__Alternatives : ( ( RULE_ID ) | ( ProjectId ) | ( ProjectType ) | ( ProjectVersion ) | ( VendorId ) | ( VendorName ) | ( Output ) | ( Libraries ) | ( Resources ) | ( Sources ) | ( ModuleFilters ) | ( ( rule__N4mfIdentifier__Group_11__0 ) ) | ( API ) | ( User ) | ( Application ) | ( ( rule__N4mfIdentifier__Group_15__0 ) ) | ( Content ) | ( Test ) );
    public final void rule__N4mfIdentifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:547:1: ( ( RULE_ID ) | ( ProjectId ) | ( ProjectType ) | ( ProjectVersion ) | ( VendorId ) | ( VendorName ) | ( Output ) | ( Libraries ) | ( Resources ) | ( Sources ) | ( ModuleFilters ) | ( ( rule__N4mfIdentifier__Group_11__0 ) ) | ( API ) | ( User ) | ( Application ) | ( ( rule__N4mfIdentifier__Group_15__0 ) ) | ( Content ) | ( Test ) )
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
                    // InternalN4MFParser.g:548:2: ( RULE_ID )
                    {
                    // InternalN4MFParser.g:548:2: ( RULE_ID )
                    // InternalN4MFParser.g:549:3: RULE_ID
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:554:2: ( ProjectId )
                    {
                    // InternalN4MFParser.g:554:2: ( ProjectId )
                    // InternalN4MFParser.g:555:3: ProjectId
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1()); 
                    match(input,ProjectId,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:560:2: ( ProjectType )
                    {
                    // InternalN4MFParser.g:560:2: ( ProjectType )
                    // InternalN4MFParser.g:561:3: ProjectType
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2()); 
                    match(input,ProjectType,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalN4MFParser.g:566:2: ( ProjectVersion )
                    {
                    // InternalN4MFParser.g:566:2: ( ProjectVersion )
                    // InternalN4MFParser.g:567:3: ProjectVersion
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3()); 
                    match(input,ProjectVersion,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalN4MFParser.g:572:2: ( VendorId )
                    {
                    // InternalN4MFParser.g:572:2: ( VendorId )
                    // InternalN4MFParser.g:573:3: VendorId
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4()); 
                    match(input,VendorId,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalN4MFParser.g:578:2: ( VendorName )
                    {
                    // InternalN4MFParser.g:578:2: ( VendorName )
                    // InternalN4MFParser.g:579:3: VendorName
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5()); 
                    match(input,VendorName,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalN4MFParser.g:584:2: ( Output )
                    {
                    // InternalN4MFParser.g:584:2: ( Output )
                    // InternalN4MFParser.g:585:3: Output
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6()); 
                    match(input,Output,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalN4MFParser.g:590:2: ( Libraries )
                    {
                    // InternalN4MFParser.g:590:2: ( Libraries )
                    // InternalN4MFParser.g:591:3: Libraries
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7()); 
                    match(input,Libraries,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7()); 

                    }


                    }
                    break;
                case 9 :
                    // InternalN4MFParser.g:596:2: ( Resources )
                    {
                    // InternalN4MFParser.g:596:2: ( Resources )
                    // InternalN4MFParser.g:597:3: Resources
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8()); 
                    match(input,Resources,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8()); 

                    }


                    }
                    break;
                case 10 :
                    // InternalN4MFParser.g:602:2: ( Sources )
                    {
                    // InternalN4MFParser.g:602:2: ( Sources )
                    // InternalN4MFParser.g:603:3: Sources
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9()); 
                    match(input,Sources,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9()); 

                    }


                    }
                    break;
                case 11 :
                    // InternalN4MFParser.g:608:2: ( ModuleFilters )
                    {
                    // InternalN4MFParser.g:608:2: ( ModuleFilters )
                    // InternalN4MFParser.g:609:3: ModuleFilters
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10()); 
                    match(input,ModuleFilters,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10()); 

                    }


                    }
                    break;
                case 12 :
                    // InternalN4MFParser.g:614:2: ( ( rule__N4mfIdentifier__Group_11__0 ) )
                    {
                    // InternalN4MFParser.g:614:2: ( ( rule__N4mfIdentifier__Group_11__0 ) )
                    // InternalN4MFParser.g:615:3: ( rule__N4mfIdentifier__Group_11__0 )
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getGroup_11()); 
                    // InternalN4MFParser.g:616:3: ( rule__N4mfIdentifier__Group_11__0 )
                    // InternalN4MFParser.g:616:4: rule__N4mfIdentifier__Group_11__0
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
                    // InternalN4MFParser.g:620:2: ( API )
                    {
                    // InternalN4MFParser.g:620:2: ( API )
                    // InternalN4MFParser.g:621:3: API
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12()); 
                    match(input,API,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12()); 

                    }


                    }
                    break;
                case 14 :
                    // InternalN4MFParser.g:626:2: ( User )
                    {
                    // InternalN4MFParser.g:626:2: ( User )
                    // InternalN4MFParser.g:627:3: User
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13()); 
                    match(input,User,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13()); 

                    }


                    }
                    break;
                case 15 :
                    // InternalN4MFParser.g:632:2: ( Application )
                    {
                    // InternalN4MFParser.g:632:2: ( Application )
                    // InternalN4MFParser.g:633:3: Application
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14()); 
                    match(input,Application,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14()); 

                    }


                    }
                    break;
                case 16 :
                    // InternalN4MFParser.g:638:2: ( ( rule__N4mfIdentifier__Group_15__0 ) )
                    {
                    // InternalN4MFParser.g:638:2: ( ( rule__N4mfIdentifier__Group_15__0 ) )
                    // InternalN4MFParser.g:639:3: ( rule__N4mfIdentifier__Group_15__0 )
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getGroup_15()); 
                    // InternalN4MFParser.g:640:3: ( rule__N4mfIdentifier__Group_15__0 )
                    // InternalN4MFParser.g:640:4: rule__N4mfIdentifier__Group_15__0
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
                    // InternalN4MFParser.g:644:2: ( Content )
                    {
                    // InternalN4MFParser.g:644:2: ( Content )
                    // InternalN4MFParser.g:645:3: Content
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16()); 
                    match(input,Content,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16()); 

                    }


                    }
                    break;
                case 18 :
                    // InternalN4MFParser.g:650:2: ( Test )
                    {
                    // InternalN4MFParser.g:650:2: ( Test )
                    // InternalN4MFParser.g:651:3: Test
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
    // InternalN4MFParser.g:660:1: rule__ProjectType__Alternatives : ( ( ( Application ) ) | ( ( Processor ) ) | ( ( Library ) ) | ( ( API ) ) | ( ( RuntimeEnvironment ) ) | ( ( RuntimeLibrary ) ) | ( ( Test ) ) | ( ( Validation ) ) );
    public final void rule__ProjectType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:664:1: ( ( ( Application ) ) | ( ( Processor ) ) | ( ( Library ) ) | ( ( API ) ) | ( ( RuntimeEnvironment ) ) | ( ( RuntimeLibrary ) ) | ( ( Test ) ) | ( ( Validation ) ) )
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
                    // InternalN4MFParser.g:665:2: ( ( Application ) )
                    {
                    // InternalN4MFParser.g:665:2: ( ( Application ) )
                    // InternalN4MFParser.g:666:3: ( Application )
                    {
                     before(grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:667:3: ( Application )
                    // InternalN4MFParser.g:667:4: Application
                    {
                    match(input,Application,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:671:2: ( ( Processor ) )
                    {
                    // InternalN4MFParser.g:671:2: ( ( Processor ) )
                    // InternalN4MFParser.g:672:3: ( Processor )
                    {
                     before(grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:673:3: ( Processor )
                    // InternalN4MFParser.g:673:4: Processor
                    {
                    match(input,Processor,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:677:2: ( ( Library ) )
                    {
                    // InternalN4MFParser.g:677:2: ( ( Library ) )
                    // InternalN4MFParser.g:678:3: ( Library )
                    {
                     before(grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2()); 
                    // InternalN4MFParser.g:679:3: ( Library )
                    // InternalN4MFParser.g:679:4: Library
                    {
                    match(input,Library,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalN4MFParser.g:683:2: ( ( API ) )
                    {
                    // InternalN4MFParser.g:683:2: ( ( API ) )
                    // InternalN4MFParser.g:684:3: ( API )
                    {
                     before(grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3()); 
                    // InternalN4MFParser.g:685:3: ( API )
                    // InternalN4MFParser.g:685:4: API
                    {
                    match(input,API,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalN4MFParser.g:689:2: ( ( RuntimeEnvironment ) )
                    {
                    // InternalN4MFParser.g:689:2: ( ( RuntimeEnvironment ) )
                    // InternalN4MFParser.g:690:3: ( RuntimeEnvironment )
                    {
                     before(grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4()); 
                    // InternalN4MFParser.g:691:3: ( RuntimeEnvironment )
                    // InternalN4MFParser.g:691:4: RuntimeEnvironment
                    {
                    match(input,RuntimeEnvironment,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalN4MFParser.g:695:2: ( ( RuntimeLibrary ) )
                    {
                    // InternalN4MFParser.g:695:2: ( ( RuntimeLibrary ) )
                    // InternalN4MFParser.g:696:3: ( RuntimeLibrary )
                    {
                     before(grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5()); 
                    // InternalN4MFParser.g:697:3: ( RuntimeLibrary )
                    // InternalN4MFParser.g:697:4: RuntimeLibrary
                    {
                    match(input,RuntimeLibrary,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalN4MFParser.g:701:2: ( ( Test ) )
                    {
                    // InternalN4MFParser.g:701:2: ( ( Test ) )
                    // InternalN4MFParser.g:702:3: ( Test )
                    {
                     before(grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6()); 
                    // InternalN4MFParser.g:703:3: ( Test )
                    // InternalN4MFParser.g:703:4: Test
                    {
                    match(input,Test,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalN4MFParser.g:707:2: ( ( Validation ) )
                    {
                    // InternalN4MFParser.g:707:2: ( ( Validation ) )
                    // InternalN4MFParser.g:708:3: ( Validation )
                    {
                     before(grammarAccess.getProjectTypeAccess().getVALIDATIONEnumLiteralDeclaration_7()); 
                    // InternalN4MFParser.g:709:3: ( Validation )
                    // InternalN4MFParser.g:709:4: Validation
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
    // InternalN4MFParser.g:717:1: rule__SourceFragmentType__Alternatives : ( ( ( Source ) ) | ( ( External ) ) | ( ( Test ) ) );
    public final void rule__SourceFragmentType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:721:1: ( ( ( Source ) ) | ( ( External ) ) | ( ( Test ) ) )
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
                    // InternalN4MFParser.g:722:2: ( ( Source ) )
                    {
                    // InternalN4MFParser.g:722:2: ( ( Source ) )
                    // InternalN4MFParser.g:723:3: ( Source )
                    {
                     before(grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:724:3: ( Source )
                    // InternalN4MFParser.g:724:4: Source
                    {
                    match(input,Source,FOLLOW_2); 

                    }

                     after(grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:728:2: ( ( External ) )
                    {
                    // InternalN4MFParser.g:728:2: ( ( External ) )
                    // InternalN4MFParser.g:729:3: ( External )
                    {
                     before(grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:730:3: ( External )
                    // InternalN4MFParser.g:730:4: External
                    {
                    match(input,External,FOLLOW_2); 

                    }

                     after(grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:734:2: ( ( Test ) )
                    {
                    // InternalN4MFParser.g:734:2: ( ( Test ) )
                    // InternalN4MFParser.g:735:3: ( Test )
                    {
                     before(grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2()); 
                    // InternalN4MFParser.g:736:3: ( Test )
                    // InternalN4MFParser.g:736:4: Test
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
    // InternalN4MFParser.g:744:1: rule__ModuleFilterType__Alternatives : ( ( ( NoValidate ) ) | ( ( NoModuleWrap ) ) );
    public final void rule__ModuleFilterType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:748:1: ( ( ( NoValidate ) ) | ( ( NoModuleWrap ) ) )
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
                    // InternalN4MFParser.g:749:2: ( ( NoValidate ) )
                    {
                    // InternalN4MFParser.g:749:2: ( ( NoValidate ) )
                    // InternalN4MFParser.g:750:3: ( NoValidate )
                    {
                     before(grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:751:3: ( NoValidate )
                    // InternalN4MFParser.g:751:4: NoValidate
                    {
                    match(input,NoValidate,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:755:2: ( ( NoModuleWrap ) )
                    {
                    // InternalN4MFParser.g:755:2: ( ( NoModuleWrap ) )
                    // InternalN4MFParser.g:756:3: ( NoModuleWrap )
                    {
                     before(grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:757:3: ( NoModuleWrap )
                    // InternalN4MFParser.g:757:4: NoModuleWrap
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
    // InternalN4MFParser.g:765:1: rule__ProjectDependencyScope__Alternatives : ( ( ( Compile ) ) | ( ( Test ) ) );
    public final void rule__ProjectDependencyScope__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:769:1: ( ( ( Compile ) ) | ( ( Test ) ) )
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
                    // InternalN4MFParser.g:770:2: ( ( Compile ) )
                    {
                    // InternalN4MFParser.g:770:2: ( ( Compile ) )
                    // InternalN4MFParser.g:771:3: ( Compile )
                    {
                     before(grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:772:3: ( Compile )
                    // InternalN4MFParser.g:772:4: Compile
                    {
                    match(input,Compile,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:776:2: ( ( Test ) )
                    {
                    // InternalN4MFParser.g:776:2: ( ( Test ) )
                    // InternalN4MFParser.g:777:3: ( Test )
                    {
                     before(grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:778:3: ( Test )
                    // InternalN4MFParser.g:778:4: Test
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
    // InternalN4MFParser.g:786:1: rule__ModuleLoader__Alternatives : ( ( ( N4js ) ) | ( ( Commonjs ) ) | ( ( Node_builtin ) ) );
    public final void rule__ModuleLoader__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:790:1: ( ( ( N4js ) ) | ( ( Commonjs ) ) | ( ( Node_builtin ) ) )
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
                    // InternalN4MFParser.g:791:2: ( ( N4js ) )
                    {
                    // InternalN4MFParser.g:791:2: ( ( N4js ) )
                    // InternalN4MFParser.g:792:3: ( N4js )
                    {
                     before(grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:793:3: ( N4js )
                    // InternalN4MFParser.g:793:4: N4js
                    {
                    match(input,N4js,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:797:2: ( ( Commonjs ) )
                    {
                    // InternalN4MFParser.g:797:2: ( ( Commonjs ) )
                    // InternalN4MFParser.g:798:3: ( Commonjs )
                    {
                     before(grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:799:3: ( Commonjs )
                    // InternalN4MFParser.g:799:4: Commonjs
                    {
                    match(input,Commonjs,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:803:2: ( ( Node_builtin ) )
                    {
                    // InternalN4MFParser.g:803:2: ( ( Node_builtin ) )
                    // InternalN4MFParser.g:804:3: ( Node_builtin )
                    {
                     before(grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2()); 
                    // InternalN4MFParser.g:805:3: ( Node_builtin )
                    // InternalN4MFParser.g:805:4: Node_builtin
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
    // InternalN4MFParser.g:813:1: rule__ProjectDescription__Group_0__0 : rule__ProjectDescription__Group_0__0__Impl rule__ProjectDescription__Group_0__1 ;
    public final void rule__ProjectDescription__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:817:1: ( rule__ProjectDescription__Group_0__0__Impl rule__ProjectDescription__Group_0__1 )
            // InternalN4MFParser.g:818:2: rule__ProjectDescription__Group_0__0__Impl rule__ProjectDescription__Group_0__1
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
    // InternalN4MFParser.g:825:1: rule__ProjectDescription__Group_0__0__Impl : ( ProjectId ) ;
    public final void rule__ProjectDescription__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:829:1: ( ( ProjectId ) )
            // InternalN4MFParser.g:830:1: ( ProjectId )
            {
            // InternalN4MFParser.g:830:1: ( ProjectId )
            // InternalN4MFParser.g:831:2: ProjectId
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
    // InternalN4MFParser.g:840:1: rule__ProjectDescription__Group_0__1 : rule__ProjectDescription__Group_0__1__Impl rule__ProjectDescription__Group_0__2 ;
    public final void rule__ProjectDescription__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:844:1: ( rule__ProjectDescription__Group_0__1__Impl rule__ProjectDescription__Group_0__2 )
            // InternalN4MFParser.g:845:2: rule__ProjectDescription__Group_0__1__Impl rule__ProjectDescription__Group_0__2
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
    // InternalN4MFParser.g:852:1: rule__ProjectDescription__Group_0__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:856:1: ( ( Colon ) )
            // InternalN4MFParser.g:857:1: ( Colon )
            {
            // InternalN4MFParser.g:857:1: ( Colon )
            // InternalN4MFParser.g:858:2: Colon
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
    // InternalN4MFParser.g:867:1: rule__ProjectDescription__Group_0__2 : rule__ProjectDescription__Group_0__2__Impl ;
    public final void rule__ProjectDescription__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:871:1: ( rule__ProjectDescription__Group_0__2__Impl )
            // InternalN4MFParser.g:872:2: rule__ProjectDescription__Group_0__2__Impl
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
    // InternalN4MFParser.g:878:1: rule__ProjectDescription__Group_0__2__Impl : ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) ) ;
    public final void rule__ProjectDescription__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:882:1: ( ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) ) )
            // InternalN4MFParser.g:883:1: ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) )
            {
            // InternalN4MFParser.g:883:1: ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) )
            // InternalN4MFParser.g:884:2: ( rule__ProjectDescription__ProjectIdAssignment_0_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectIdAssignment_0_2()); 
            // InternalN4MFParser.g:885:2: ( rule__ProjectDescription__ProjectIdAssignment_0_2 )
            // InternalN4MFParser.g:885:3: rule__ProjectDescription__ProjectIdAssignment_0_2
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
    // InternalN4MFParser.g:894:1: rule__ProjectDescription__Group_1__0 : rule__ProjectDescription__Group_1__0__Impl rule__ProjectDescription__Group_1__1 ;
    public final void rule__ProjectDescription__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:898:1: ( rule__ProjectDescription__Group_1__0__Impl rule__ProjectDescription__Group_1__1 )
            // InternalN4MFParser.g:899:2: rule__ProjectDescription__Group_1__0__Impl rule__ProjectDescription__Group_1__1
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
    // InternalN4MFParser.g:906:1: rule__ProjectDescription__Group_1__0__Impl : ( ProjectType ) ;
    public final void rule__ProjectDescription__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:910:1: ( ( ProjectType ) )
            // InternalN4MFParser.g:911:1: ( ProjectType )
            {
            // InternalN4MFParser.g:911:1: ( ProjectType )
            // InternalN4MFParser.g:912:2: ProjectType
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
    // InternalN4MFParser.g:921:1: rule__ProjectDescription__Group_1__1 : rule__ProjectDescription__Group_1__1__Impl rule__ProjectDescription__Group_1__2 ;
    public final void rule__ProjectDescription__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:925:1: ( rule__ProjectDescription__Group_1__1__Impl rule__ProjectDescription__Group_1__2 )
            // InternalN4MFParser.g:926:2: rule__ProjectDescription__Group_1__1__Impl rule__ProjectDescription__Group_1__2
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
    // InternalN4MFParser.g:933:1: rule__ProjectDescription__Group_1__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:937:1: ( ( Colon ) )
            // InternalN4MFParser.g:938:1: ( Colon )
            {
            // InternalN4MFParser.g:938:1: ( Colon )
            // InternalN4MFParser.g:939:2: Colon
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
    // InternalN4MFParser.g:948:1: rule__ProjectDescription__Group_1__2 : rule__ProjectDescription__Group_1__2__Impl ;
    public final void rule__ProjectDescription__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:952:1: ( rule__ProjectDescription__Group_1__2__Impl )
            // InternalN4MFParser.g:953:2: rule__ProjectDescription__Group_1__2__Impl
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
    // InternalN4MFParser.g:959:1: rule__ProjectDescription__Group_1__2__Impl : ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) ) ;
    public final void rule__ProjectDescription__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:963:1: ( ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) ) )
            // InternalN4MFParser.g:964:1: ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) )
            {
            // InternalN4MFParser.g:964:1: ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) )
            // InternalN4MFParser.g:965:2: ( rule__ProjectDescription__ProjectTypeAssignment_1_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectTypeAssignment_1_2()); 
            // InternalN4MFParser.g:966:2: ( rule__ProjectDescription__ProjectTypeAssignment_1_2 )
            // InternalN4MFParser.g:966:3: rule__ProjectDescription__ProjectTypeAssignment_1_2
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
    // InternalN4MFParser.g:975:1: rule__ProjectDescription__Group_2__0 : rule__ProjectDescription__Group_2__0__Impl rule__ProjectDescription__Group_2__1 ;
    public final void rule__ProjectDescription__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:979:1: ( rule__ProjectDescription__Group_2__0__Impl rule__ProjectDescription__Group_2__1 )
            // InternalN4MFParser.g:980:2: rule__ProjectDescription__Group_2__0__Impl rule__ProjectDescription__Group_2__1
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
    // InternalN4MFParser.g:987:1: rule__ProjectDescription__Group_2__0__Impl : ( ProjectVersion ) ;
    public final void rule__ProjectDescription__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:991:1: ( ( ProjectVersion ) )
            // InternalN4MFParser.g:992:1: ( ProjectVersion )
            {
            // InternalN4MFParser.g:992:1: ( ProjectVersion )
            // InternalN4MFParser.g:993:2: ProjectVersion
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
    // InternalN4MFParser.g:1002:1: rule__ProjectDescription__Group_2__1 : rule__ProjectDescription__Group_2__1__Impl rule__ProjectDescription__Group_2__2 ;
    public final void rule__ProjectDescription__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1006:1: ( rule__ProjectDescription__Group_2__1__Impl rule__ProjectDescription__Group_2__2 )
            // InternalN4MFParser.g:1007:2: rule__ProjectDescription__Group_2__1__Impl rule__ProjectDescription__Group_2__2
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
    // InternalN4MFParser.g:1014:1: rule__ProjectDescription__Group_2__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1018:1: ( ( Colon ) )
            // InternalN4MFParser.g:1019:1: ( Colon )
            {
            // InternalN4MFParser.g:1019:1: ( Colon )
            // InternalN4MFParser.g:1020:2: Colon
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
    // InternalN4MFParser.g:1029:1: rule__ProjectDescription__Group_2__2 : rule__ProjectDescription__Group_2__2__Impl ;
    public final void rule__ProjectDescription__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1033:1: ( rule__ProjectDescription__Group_2__2__Impl )
            // InternalN4MFParser.g:1034:2: rule__ProjectDescription__Group_2__2__Impl
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
    // InternalN4MFParser.g:1040:1: rule__ProjectDescription__Group_2__2__Impl : ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) ) ;
    public final void rule__ProjectDescription__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1044:1: ( ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) ) )
            // InternalN4MFParser.g:1045:1: ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) )
            {
            // InternalN4MFParser.g:1045:1: ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) )
            // InternalN4MFParser.g:1046:2: ( rule__ProjectDescription__ProjectVersionAssignment_2_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectVersionAssignment_2_2()); 
            // InternalN4MFParser.g:1047:2: ( rule__ProjectDescription__ProjectVersionAssignment_2_2 )
            // InternalN4MFParser.g:1047:3: rule__ProjectDescription__ProjectVersionAssignment_2_2
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
    // InternalN4MFParser.g:1056:1: rule__ProjectDescription__Group_3__0 : rule__ProjectDescription__Group_3__0__Impl rule__ProjectDescription__Group_3__1 ;
    public final void rule__ProjectDescription__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1060:1: ( rule__ProjectDescription__Group_3__0__Impl rule__ProjectDescription__Group_3__1 )
            // InternalN4MFParser.g:1061:2: rule__ProjectDescription__Group_3__0__Impl rule__ProjectDescription__Group_3__1
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
    // InternalN4MFParser.g:1068:1: rule__ProjectDescription__Group_3__0__Impl : ( VendorId ) ;
    public final void rule__ProjectDescription__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1072:1: ( ( VendorId ) )
            // InternalN4MFParser.g:1073:1: ( VendorId )
            {
            // InternalN4MFParser.g:1073:1: ( VendorId )
            // InternalN4MFParser.g:1074:2: VendorId
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
    // InternalN4MFParser.g:1083:1: rule__ProjectDescription__Group_3__1 : rule__ProjectDescription__Group_3__1__Impl rule__ProjectDescription__Group_3__2 ;
    public final void rule__ProjectDescription__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1087:1: ( rule__ProjectDescription__Group_3__1__Impl rule__ProjectDescription__Group_3__2 )
            // InternalN4MFParser.g:1088:2: rule__ProjectDescription__Group_3__1__Impl rule__ProjectDescription__Group_3__2
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
    // InternalN4MFParser.g:1095:1: rule__ProjectDescription__Group_3__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1099:1: ( ( Colon ) )
            // InternalN4MFParser.g:1100:1: ( Colon )
            {
            // InternalN4MFParser.g:1100:1: ( Colon )
            // InternalN4MFParser.g:1101:2: Colon
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
    // InternalN4MFParser.g:1110:1: rule__ProjectDescription__Group_3__2 : rule__ProjectDescription__Group_3__2__Impl ;
    public final void rule__ProjectDescription__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1114:1: ( rule__ProjectDescription__Group_3__2__Impl )
            // InternalN4MFParser.g:1115:2: rule__ProjectDescription__Group_3__2__Impl
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
    // InternalN4MFParser.g:1121:1: rule__ProjectDescription__Group_3__2__Impl : ( ( rule__ProjectDescription__VendorIdAssignment_3_2 ) ) ;
    public final void rule__ProjectDescription__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1125:1: ( ( ( rule__ProjectDescription__VendorIdAssignment_3_2 ) ) )
            // InternalN4MFParser.g:1126:1: ( ( rule__ProjectDescription__VendorIdAssignment_3_2 ) )
            {
            // InternalN4MFParser.g:1126:1: ( ( rule__ProjectDescription__VendorIdAssignment_3_2 ) )
            // InternalN4MFParser.g:1127:2: ( rule__ProjectDescription__VendorIdAssignment_3_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getVendorIdAssignment_3_2()); 
            // InternalN4MFParser.g:1128:2: ( rule__ProjectDescription__VendorIdAssignment_3_2 )
            // InternalN4MFParser.g:1128:3: rule__ProjectDescription__VendorIdAssignment_3_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__VendorIdAssignment_3_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getVendorIdAssignment_3_2()); 

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
    // InternalN4MFParser.g:1137:1: rule__ProjectDescription__Group_4__0 : rule__ProjectDescription__Group_4__0__Impl rule__ProjectDescription__Group_4__1 ;
    public final void rule__ProjectDescription__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1141:1: ( rule__ProjectDescription__Group_4__0__Impl rule__ProjectDescription__Group_4__1 )
            // InternalN4MFParser.g:1142:2: rule__ProjectDescription__Group_4__0__Impl rule__ProjectDescription__Group_4__1
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
    // InternalN4MFParser.g:1149:1: rule__ProjectDescription__Group_4__0__Impl : ( VendorName ) ;
    public final void rule__ProjectDescription__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1153:1: ( ( VendorName ) )
            // InternalN4MFParser.g:1154:1: ( VendorName )
            {
            // InternalN4MFParser.g:1154:1: ( VendorName )
            // InternalN4MFParser.g:1155:2: VendorName
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
    // InternalN4MFParser.g:1164:1: rule__ProjectDescription__Group_4__1 : rule__ProjectDescription__Group_4__1__Impl rule__ProjectDescription__Group_4__2 ;
    public final void rule__ProjectDescription__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1168:1: ( rule__ProjectDescription__Group_4__1__Impl rule__ProjectDescription__Group_4__2 )
            // InternalN4MFParser.g:1169:2: rule__ProjectDescription__Group_4__1__Impl rule__ProjectDescription__Group_4__2
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
    // InternalN4MFParser.g:1176:1: rule__ProjectDescription__Group_4__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1180:1: ( ( Colon ) )
            // InternalN4MFParser.g:1181:1: ( Colon )
            {
            // InternalN4MFParser.g:1181:1: ( Colon )
            // InternalN4MFParser.g:1182:2: Colon
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
    // InternalN4MFParser.g:1191:1: rule__ProjectDescription__Group_4__2 : rule__ProjectDescription__Group_4__2__Impl ;
    public final void rule__ProjectDescription__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1195:1: ( rule__ProjectDescription__Group_4__2__Impl )
            // InternalN4MFParser.g:1196:2: rule__ProjectDescription__Group_4__2__Impl
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
    // InternalN4MFParser.g:1202:1: rule__ProjectDescription__Group_4__2__Impl : ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) ) ;
    public final void rule__ProjectDescription__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1206:1: ( ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) ) )
            // InternalN4MFParser.g:1207:1: ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) )
            {
            // InternalN4MFParser.g:1207:1: ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) )
            // InternalN4MFParser.g:1208:2: ( rule__ProjectDescription__VendorNameAssignment_4_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getVendorNameAssignment_4_2()); 
            // InternalN4MFParser.g:1209:2: ( rule__ProjectDescription__VendorNameAssignment_4_2 )
            // InternalN4MFParser.g:1209:3: rule__ProjectDescription__VendorNameAssignment_4_2
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
    // InternalN4MFParser.g:1218:1: rule__ProjectDescription__Group_5__0 : rule__ProjectDescription__Group_5__0__Impl rule__ProjectDescription__Group_5__1 ;
    public final void rule__ProjectDescription__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1222:1: ( rule__ProjectDescription__Group_5__0__Impl rule__ProjectDescription__Group_5__1 )
            // InternalN4MFParser.g:1223:2: rule__ProjectDescription__Group_5__0__Impl rule__ProjectDescription__Group_5__1
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
    // InternalN4MFParser.g:1230:1: rule__ProjectDescription__Group_5__0__Impl : ( MainModule ) ;
    public final void rule__ProjectDescription__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1234:1: ( ( MainModule ) )
            // InternalN4MFParser.g:1235:1: ( MainModule )
            {
            // InternalN4MFParser.g:1235:1: ( MainModule )
            // InternalN4MFParser.g:1236:2: MainModule
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
    // InternalN4MFParser.g:1245:1: rule__ProjectDescription__Group_5__1 : rule__ProjectDescription__Group_5__1__Impl rule__ProjectDescription__Group_5__2 ;
    public final void rule__ProjectDescription__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1249:1: ( rule__ProjectDescription__Group_5__1__Impl rule__ProjectDescription__Group_5__2 )
            // InternalN4MFParser.g:1250:2: rule__ProjectDescription__Group_5__1__Impl rule__ProjectDescription__Group_5__2
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
    // InternalN4MFParser.g:1257:1: rule__ProjectDescription__Group_5__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1261:1: ( ( Colon ) )
            // InternalN4MFParser.g:1262:1: ( Colon )
            {
            // InternalN4MFParser.g:1262:1: ( Colon )
            // InternalN4MFParser.g:1263:2: Colon
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
    // InternalN4MFParser.g:1272:1: rule__ProjectDescription__Group_5__2 : rule__ProjectDescription__Group_5__2__Impl ;
    public final void rule__ProjectDescription__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1276:1: ( rule__ProjectDescription__Group_5__2__Impl )
            // InternalN4MFParser.g:1277:2: rule__ProjectDescription__Group_5__2__Impl
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
    // InternalN4MFParser.g:1283:1: rule__ProjectDescription__Group_5__2__Impl : ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) ) ;
    public final void rule__ProjectDescription__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1287:1: ( ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) ) )
            // InternalN4MFParser.g:1288:1: ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) )
            {
            // InternalN4MFParser.g:1288:1: ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) )
            // InternalN4MFParser.g:1289:2: ( rule__ProjectDescription__MainModuleAssignment_5_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getMainModuleAssignment_5_2()); 
            // InternalN4MFParser.g:1290:2: ( rule__ProjectDescription__MainModuleAssignment_5_2 )
            // InternalN4MFParser.g:1290:3: rule__ProjectDescription__MainModuleAssignment_5_2
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
    // InternalN4MFParser.g:1299:1: rule__ProjectDescription__Group_6__0 : rule__ProjectDescription__Group_6__0__Impl rule__ProjectDescription__Group_6__1 ;
    public final void rule__ProjectDescription__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1303:1: ( rule__ProjectDescription__Group_6__0__Impl rule__ProjectDescription__Group_6__1 )
            // InternalN4MFParser.g:1304:2: rule__ProjectDescription__Group_6__0__Impl rule__ProjectDescription__Group_6__1
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
    // InternalN4MFParser.g:1311:1: rule__ProjectDescription__Group_6__0__Impl : ( ExtendedRuntimeEnvironment ) ;
    public final void rule__ProjectDescription__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1315:1: ( ( ExtendedRuntimeEnvironment ) )
            // InternalN4MFParser.g:1316:1: ( ExtendedRuntimeEnvironment )
            {
            // InternalN4MFParser.g:1316:1: ( ExtendedRuntimeEnvironment )
            // InternalN4MFParser.g:1317:2: ExtendedRuntimeEnvironment
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
    // InternalN4MFParser.g:1326:1: rule__ProjectDescription__Group_6__1 : rule__ProjectDescription__Group_6__1__Impl rule__ProjectDescription__Group_6__2 ;
    public final void rule__ProjectDescription__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1330:1: ( rule__ProjectDescription__Group_6__1__Impl rule__ProjectDescription__Group_6__2 )
            // InternalN4MFParser.g:1331:2: rule__ProjectDescription__Group_6__1__Impl rule__ProjectDescription__Group_6__2
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
    // InternalN4MFParser.g:1338:1: rule__ProjectDescription__Group_6__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1342:1: ( ( Colon ) )
            // InternalN4MFParser.g:1343:1: ( Colon )
            {
            // InternalN4MFParser.g:1343:1: ( Colon )
            // InternalN4MFParser.g:1344:2: Colon
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
    // InternalN4MFParser.g:1353:1: rule__ProjectDescription__Group_6__2 : rule__ProjectDescription__Group_6__2__Impl ;
    public final void rule__ProjectDescription__Group_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1357:1: ( rule__ProjectDescription__Group_6__2__Impl )
            // InternalN4MFParser.g:1358:2: rule__ProjectDescription__Group_6__2__Impl
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
    // InternalN4MFParser.g:1364:1: rule__ProjectDescription__Group_6__2__Impl : ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 ) ) ;
    public final void rule__ProjectDescription__Group_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1368:1: ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 ) ) )
            // InternalN4MFParser.g:1369:1: ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 ) )
            {
            // InternalN4MFParser.g:1369:1: ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 ) )
            // InternalN4MFParser.g:1370:2: ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_6_2()); 
            // InternalN4MFParser.g:1371:2: ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 )
            // InternalN4MFParser.g:1371:3: rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2
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
    // InternalN4MFParser.g:1380:1: rule__ProjectDescription__Group_7__0 : rule__ProjectDescription__Group_7__0__Impl rule__ProjectDescription__Group_7__1 ;
    public final void rule__ProjectDescription__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1384:1: ( rule__ProjectDescription__Group_7__0__Impl rule__ProjectDescription__Group_7__1 )
            // InternalN4MFParser.g:1385:2: rule__ProjectDescription__Group_7__0__Impl rule__ProjectDescription__Group_7__1
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
    // InternalN4MFParser.g:1392:1: rule__ProjectDescription__Group_7__0__Impl : ( ProvidedRuntimeLibraries ) ;
    public final void rule__ProjectDescription__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1396:1: ( ( ProvidedRuntimeLibraries ) )
            // InternalN4MFParser.g:1397:1: ( ProvidedRuntimeLibraries )
            {
            // InternalN4MFParser.g:1397:1: ( ProvidedRuntimeLibraries )
            // InternalN4MFParser.g:1398:2: ProvidedRuntimeLibraries
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
    // InternalN4MFParser.g:1407:1: rule__ProjectDescription__Group_7__1 : rule__ProjectDescription__Group_7__1__Impl rule__ProjectDescription__Group_7__2 ;
    public final void rule__ProjectDescription__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1411:1: ( rule__ProjectDescription__Group_7__1__Impl rule__ProjectDescription__Group_7__2 )
            // InternalN4MFParser.g:1412:2: rule__ProjectDescription__Group_7__1__Impl rule__ProjectDescription__Group_7__2
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
    // InternalN4MFParser.g:1419:1: rule__ProjectDescription__Group_7__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1423:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:1424:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:1424:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:1425:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:1434:1: rule__ProjectDescription__Group_7__2 : rule__ProjectDescription__Group_7__2__Impl rule__ProjectDescription__Group_7__3 ;
    public final void rule__ProjectDescription__Group_7__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1438:1: ( rule__ProjectDescription__Group_7__2__Impl rule__ProjectDescription__Group_7__3 )
            // InternalN4MFParser.g:1439:2: rule__ProjectDescription__Group_7__2__Impl rule__ProjectDescription__Group_7__3
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
    // InternalN4MFParser.g:1446:1: rule__ProjectDescription__Group_7__2__Impl : ( ( rule__ProjectDescription__Group_7_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_7__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1450:1: ( ( ( rule__ProjectDescription__Group_7_2__0 )? ) )
            // InternalN4MFParser.g:1451:1: ( ( rule__ProjectDescription__Group_7_2__0 )? )
            {
            // InternalN4MFParser.g:1451:1: ( ( rule__ProjectDescription__Group_7_2__0 )? )
            // InternalN4MFParser.g:1452:2: ( rule__ProjectDescription__Group_7_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_7_2()); 
            // InternalN4MFParser.g:1453:2: ( rule__ProjectDescription__Group_7_2__0 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ProjectDependencies||LA12_0==ProjectVersion||LA12_0==ModuleFilters||(LA12_0>=ProjectType && LA12_0<=Application)||LA12_0==VendorName||(LA12_0>=Libraries && LA12_0<=VendorId)||LA12_0==Sources||LA12_0==Content||LA12_0==Output||(LA12_0>=Test && LA12_0<=API)||LA12_0==RULE_ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalN4MFParser.g:1453:3: rule__ProjectDescription__Group_7_2__0
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
    // InternalN4MFParser.g:1461:1: rule__ProjectDescription__Group_7__3 : rule__ProjectDescription__Group_7__3__Impl ;
    public final void rule__ProjectDescription__Group_7__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1465:1: ( rule__ProjectDescription__Group_7__3__Impl )
            // InternalN4MFParser.g:1466:2: rule__ProjectDescription__Group_7__3__Impl
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
    // InternalN4MFParser.g:1472:1: rule__ProjectDescription__Group_7__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_7__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1476:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:1477:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:1477:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:1478:2: RightCurlyBracket
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
    // InternalN4MFParser.g:1488:1: rule__ProjectDescription__Group_7_2__0 : rule__ProjectDescription__Group_7_2__0__Impl rule__ProjectDescription__Group_7_2__1 ;
    public final void rule__ProjectDescription__Group_7_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1492:1: ( rule__ProjectDescription__Group_7_2__0__Impl rule__ProjectDescription__Group_7_2__1 )
            // InternalN4MFParser.g:1493:2: rule__ProjectDescription__Group_7_2__0__Impl rule__ProjectDescription__Group_7_2__1
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
    // InternalN4MFParser.g:1500:1: rule__ProjectDescription__Group_7_2__0__Impl : ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_7_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1504:1: ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 ) ) )
            // InternalN4MFParser.g:1505:1: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 ) )
            {
            // InternalN4MFParser.g:1505:1: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 ) )
            // InternalN4MFParser.g:1506:2: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7_2_0()); 
            // InternalN4MFParser.g:1507:2: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 )
            // InternalN4MFParser.g:1507:3: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0
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
    // InternalN4MFParser.g:1515:1: rule__ProjectDescription__Group_7_2__1 : rule__ProjectDescription__Group_7_2__1__Impl ;
    public final void rule__ProjectDescription__Group_7_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1519:1: ( rule__ProjectDescription__Group_7_2__1__Impl )
            // InternalN4MFParser.g:1520:2: rule__ProjectDescription__Group_7_2__1__Impl
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
    // InternalN4MFParser.g:1526:1: rule__ProjectDescription__Group_7_2__1__Impl : ( ( rule__ProjectDescription__Group_7_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_7_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1530:1: ( ( ( rule__ProjectDescription__Group_7_2_1__0 )* ) )
            // InternalN4MFParser.g:1531:1: ( ( rule__ProjectDescription__Group_7_2_1__0 )* )
            {
            // InternalN4MFParser.g:1531:1: ( ( rule__ProjectDescription__Group_7_2_1__0 )* )
            // InternalN4MFParser.g:1532:2: ( rule__ProjectDescription__Group_7_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_7_2_1()); 
            // InternalN4MFParser.g:1533:2: ( rule__ProjectDescription__Group_7_2_1__0 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==Comma) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalN4MFParser.g:1533:3: rule__ProjectDescription__Group_7_2_1__0
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
    // InternalN4MFParser.g:1542:1: rule__ProjectDescription__Group_7_2_1__0 : rule__ProjectDescription__Group_7_2_1__0__Impl rule__ProjectDescription__Group_7_2_1__1 ;
    public final void rule__ProjectDescription__Group_7_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1546:1: ( rule__ProjectDescription__Group_7_2_1__0__Impl rule__ProjectDescription__Group_7_2_1__1 )
            // InternalN4MFParser.g:1547:2: rule__ProjectDescription__Group_7_2_1__0__Impl rule__ProjectDescription__Group_7_2_1__1
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
    // InternalN4MFParser.g:1554:1: rule__ProjectDescription__Group_7_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_7_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1558:1: ( ( Comma ) )
            // InternalN4MFParser.g:1559:1: ( Comma )
            {
            // InternalN4MFParser.g:1559:1: ( Comma )
            // InternalN4MFParser.g:1560:2: Comma
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
    // InternalN4MFParser.g:1569:1: rule__ProjectDescription__Group_7_2_1__1 : rule__ProjectDescription__Group_7_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_7_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1573:1: ( rule__ProjectDescription__Group_7_2_1__1__Impl )
            // InternalN4MFParser.g:1574:2: rule__ProjectDescription__Group_7_2_1__1__Impl
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
    // InternalN4MFParser.g:1580:1: rule__ProjectDescription__Group_7_2_1__1__Impl : ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_7_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1584:1: ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 ) ) )
            // InternalN4MFParser.g:1585:1: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 ) )
            {
            // InternalN4MFParser.g:1585:1: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 ) )
            // InternalN4MFParser.g:1586:2: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7_2_1_1()); 
            // InternalN4MFParser.g:1587:2: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 )
            // InternalN4MFParser.g:1587:3: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1
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
    // InternalN4MFParser.g:1596:1: rule__ProjectDescription__Group_8__0 : rule__ProjectDescription__Group_8__0__Impl rule__ProjectDescription__Group_8__1 ;
    public final void rule__ProjectDescription__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1600:1: ( rule__ProjectDescription__Group_8__0__Impl rule__ProjectDescription__Group_8__1 )
            // InternalN4MFParser.g:1601:2: rule__ProjectDescription__Group_8__0__Impl rule__ProjectDescription__Group_8__1
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
    // InternalN4MFParser.g:1608:1: rule__ProjectDescription__Group_8__0__Impl : ( RequiredRuntimeLibraries ) ;
    public final void rule__ProjectDescription__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1612:1: ( ( RequiredRuntimeLibraries ) )
            // InternalN4MFParser.g:1613:1: ( RequiredRuntimeLibraries )
            {
            // InternalN4MFParser.g:1613:1: ( RequiredRuntimeLibraries )
            // InternalN4MFParser.g:1614:2: RequiredRuntimeLibraries
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
    // InternalN4MFParser.g:1623:1: rule__ProjectDescription__Group_8__1 : rule__ProjectDescription__Group_8__1__Impl rule__ProjectDescription__Group_8__2 ;
    public final void rule__ProjectDescription__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1627:1: ( rule__ProjectDescription__Group_8__1__Impl rule__ProjectDescription__Group_8__2 )
            // InternalN4MFParser.g:1628:2: rule__ProjectDescription__Group_8__1__Impl rule__ProjectDescription__Group_8__2
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
    // InternalN4MFParser.g:1635:1: rule__ProjectDescription__Group_8__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1639:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:1640:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:1640:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:1641:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:1650:1: rule__ProjectDescription__Group_8__2 : rule__ProjectDescription__Group_8__2__Impl rule__ProjectDescription__Group_8__3 ;
    public final void rule__ProjectDescription__Group_8__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1654:1: ( rule__ProjectDescription__Group_8__2__Impl rule__ProjectDescription__Group_8__3 )
            // InternalN4MFParser.g:1655:2: rule__ProjectDescription__Group_8__2__Impl rule__ProjectDescription__Group_8__3
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
    // InternalN4MFParser.g:1662:1: rule__ProjectDescription__Group_8__2__Impl : ( ( rule__ProjectDescription__Group_8_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_8__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1666:1: ( ( ( rule__ProjectDescription__Group_8_2__0 )? ) )
            // InternalN4MFParser.g:1667:1: ( ( rule__ProjectDescription__Group_8_2__0 )? )
            {
            // InternalN4MFParser.g:1667:1: ( ( rule__ProjectDescription__Group_8_2__0 )? )
            // InternalN4MFParser.g:1668:2: ( rule__ProjectDescription__Group_8_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_8_2()); 
            // InternalN4MFParser.g:1669:2: ( rule__ProjectDescription__Group_8_2__0 )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==ProjectDependencies||LA14_0==ProjectVersion||LA14_0==ModuleFilters||(LA14_0>=ProjectType && LA14_0<=Application)||LA14_0==VendorName||(LA14_0>=Libraries && LA14_0<=VendorId)||LA14_0==Sources||LA14_0==Content||LA14_0==Output||(LA14_0>=Test && LA14_0<=API)||LA14_0==RULE_ID) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalN4MFParser.g:1669:3: rule__ProjectDescription__Group_8_2__0
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
    // InternalN4MFParser.g:1677:1: rule__ProjectDescription__Group_8__3 : rule__ProjectDescription__Group_8__3__Impl ;
    public final void rule__ProjectDescription__Group_8__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1681:1: ( rule__ProjectDescription__Group_8__3__Impl )
            // InternalN4MFParser.g:1682:2: rule__ProjectDescription__Group_8__3__Impl
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
    // InternalN4MFParser.g:1688:1: rule__ProjectDescription__Group_8__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_8__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1692:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:1693:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:1693:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:1694:2: RightCurlyBracket
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
    // InternalN4MFParser.g:1704:1: rule__ProjectDescription__Group_8_2__0 : rule__ProjectDescription__Group_8_2__0__Impl rule__ProjectDescription__Group_8_2__1 ;
    public final void rule__ProjectDescription__Group_8_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1708:1: ( rule__ProjectDescription__Group_8_2__0__Impl rule__ProjectDescription__Group_8_2__1 )
            // InternalN4MFParser.g:1709:2: rule__ProjectDescription__Group_8_2__0__Impl rule__ProjectDescription__Group_8_2__1
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
    // InternalN4MFParser.g:1716:1: rule__ProjectDescription__Group_8_2__0__Impl : ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_8_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1720:1: ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 ) ) )
            // InternalN4MFParser.g:1721:1: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 ) )
            {
            // InternalN4MFParser.g:1721:1: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 ) )
            // InternalN4MFParser.g:1722:2: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8_2_0()); 
            // InternalN4MFParser.g:1723:2: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 )
            // InternalN4MFParser.g:1723:3: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0
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
    // InternalN4MFParser.g:1731:1: rule__ProjectDescription__Group_8_2__1 : rule__ProjectDescription__Group_8_2__1__Impl ;
    public final void rule__ProjectDescription__Group_8_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1735:1: ( rule__ProjectDescription__Group_8_2__1__Impl )
            // InternalN4MFParser.g:1736:2: rule__ProjectDescription__Group_8_2__1__Impl
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
    // InternalN4MFParser.g:1742:1: rule__ProjectDescription__Group_8_2__1__Impl : ( ( rule__ProjectDescription__Group_8_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_8_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1746:1: ( ( ( rule__ProjectDescription__Group_8_2_1__0 )* ) )
            // InternalN4MFParser.g:1747:1: ( ( rule__ProjectDescription__Group_8_2_1__0 )* )
            {
            // InternalN4MFParser.g:1747:1: ( ( rule__ProjectDescription__Group_8_2_1__0 )* )
            // InternalN4MFParser.g:1748:2: ( rule__ProjectDescription__Group_8_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_8_2_1()); 
            // InternalN4MFParser.g:1749:2: ( rule__ProjectDescription__Group_8_2_1__0 )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==Comma) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalN4MFParser.g:1749:3: rule__ProjectDescription__Group_8_2_1__0
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
    // InternalN4MFParser.g:1758:1: rule__ProjectDescription__Group_8_2_1__0 : rule__ProjectDescription__Group_8_2_1__0__Impl rule__ProjectDescription__Group_8_2_1__1 ;
    public final void rule__ProjectDescription__Group_8_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1762:1: ( rule__ProjectDescription__Group_8_2_1__0__Impl rule__ProjectDescription__Group_8_2_1__1 )
            // InternalN4MFParser.g:1763:2: rule__ProjectDescription__Group_8_2_1__0__Impl rule__ProjectDescription__Group_8_2_1__1
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
    // InternalN4MFParser.g:1770:1: rule__ProjectDescription__Group_8_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_8_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1774:1: ( ( Comma ) )
            // InternalN4MFParser.g:1775:1: ( Comma )
            {
            // InternalN4MFParser.g:1775:1: ( Comma )
            // InternalN4MFParser.g:1776:2: Comma
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
    // InternalN4MFParser.g:1785:1: rule__ProjectDescription__Group_8_2_1__1 : rule__ProjectDescription__Group_8_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_8_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1789:1: ( rule__ProjectDescription__Group_8_2_1__1__Impl )
            // InternalN4MFParser.g:1790:2: rule__ProjectDescription__Group_8_2_1__1__Impl
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
    // InternalN4MFParser.g:1796:1: rule__ProjectDescription__Group_8_2_1__1__Impl : ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_8_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1800:1: ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 ) ) )
            // InternalN4MFParser.g:1801:1: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 ) )
            {
            // InternalN4MFParser.g:1801:1: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 ) )
            // InternalN4MFParser.g:1802:2: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8_2_1_1()); 
            // InternalN4MFParser.g:1803:2: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 )
            // InternalN4MFParser.g:1803:3: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1
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
    // InternalN4MFParser.g:1812:1: rule__ProjectDescription__Group_9__0 : rule__ProjectDescription__Group_9__0__Impl rule__ProjectDescription__Group_9__1 ;
    public final void rule__ProjectDescription__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1816:1: ( rule__ProjectDescription__Group_9__0__Impl rule__ProjectDescription__Group_9__1 )
            // InternalN4MFParser.g:1817:2: rule__ProjectDescription__Group_9__0__Impl rule__ProjectDescription__Group_9__1
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
    // InternalN4MFParser.g:1824:1: rule__ProjectDescription__Group_9__0__Impl : ( ProjectDependencies ) ;
    public final void rule__ProjectDescription__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1828:1: ( ( ProjectDependencies ) )
            // InternalN4MFParser.g:1829:1: ( ProjectDependencies )
            {
            // InternalN4MFParser.g:1829:1: ( ProjectDependencies )
            // InternalN4MFParser.g:1830:2: ProjectDependencies
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
    // InternalN4MFParser.g:1839:1: rule__ProjectDescription__Group_9__1 : rule__ProjectDescription__Group_9__1__Impl rule__ProjectDescription__Group_9__2 ;
    public final void rule__ProjectDescription__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1843:1: ( rule__ProjectDescription__Group_9__1__Impl rule__ProjectDescription__Group_9__2 )
            // InternalN4MFParser.g:1844:2: rule__ProjectDescription__Group_9__1__Impl rule__ProjectDescription__Group_9__2
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
    // InternalN4MFParser.g:1851:1: rule__ProjectDescription__Group_9__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1855:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:1856:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:1856:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:1857:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:1866:1: rule__ProjectDescription__Group_9__2 : rule__ProjectDescription__Group_9__2__Impl rule__ProjectDescription__Group_9__3 ;
    public final void rule__ProjectDescription__Group_9__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1870:1: ( rule__ProjectDescription__Group_9__2__Impl rule__ProjectDescription__Group_9__3 )
            // InternalN4MFParser.g:1871:2: rule__ProjectDescription__Group_9__2__Impl rule__ProjectDescription__Group_9__3
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
    // InternalN4MFParser.g:1878:1: rule__ProjectDescription__Group_9__2__Impl : ( ( rule__ProjectDescription__Group_9_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_9__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1882:1: ( ( ( rule__ProjectDescription__Group_9_2__0 )? ) )
            // InternalN4MFParser.g:1883:1: ( ( rule__ProjectDescription__Group_9_2__0 )? )
            {
            // InternalN4MFParser.g:1883:1: ( ( rule__ProjectDescription__Group_9_2__0 )? )
            // InternalN4MFParser.g:1884:2: ( rule__ProjectDescription__Group_9_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_9_2()); 
            // InternalN4MFParser.g:1885:2: ( rule__ProjectDescription__Group_9_2__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ProjectDependencies||LA16_0==ProjectVersion||LA16_0==ModuleFilters||(LA16_0>=ProjectType && LA16_0<=Application)||LA16_0==VendorName||(LA16_0>=Libraries && LA16_0<=VendorId)||LA16_0==Sources||LA16_0==Content||LA16_0==Output||(LA16_0>=Test && LA16_0<=API)||LA16_0==RULE_ID) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalN4MFParser.g:1885:3: rule__ProjectDescription__Group_9_2__0
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
    // InternalN4MFParser.g:1893:1: rule__ProjectDescription__Group_9__3 : rule__ProjectDescription__Group_9__3__Impl ;
    public final void rule__ProjectDescription__Group_9__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1897:1: ( rule__ProjectDescription__Group_9__3__Impl )
            // InternalN4MFParser.g:1898:2: rule__ProjectDescription__Group_9__3__Impl
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
    // InternalN4MFParser.g:1904:1: rule__ProjectDescription__Group_9__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_9__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1908:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:1909:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:1909:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:1910:2: RightCurlyBracket
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
    // InternalN4MFParser.g:1920:1: rule__ProjectDescription__Group_9_2__0 : rule__ProjectDescription__Group_9_2__0__Impl rule__ProjectDescription__Group_9_2__1 ;
    public final void rule__ProjectDescription__Group_9_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1924:1: ( rule__ProjectDescription__Group_9_2__0__Impl rule__ProjectDescription__Group_9_2__1 )
            // InternalN4MFParser.g:1925:2: rule__ProjectDescription__Group_9_2__0__Impl rule__ProjectDescription__Group_9_2__1
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
    // InternalN4MFParser.g:1932:1: rule__ProjectDescription__Group_9_2__0__Impl : ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_9_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1936:1: ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 ) ) )
            // InternalN4MFParser.g:1937:1: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 ) )
            {
            // InternalN4MFParser.g:1937:1: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 ) )
            // InternalN4MFParser.g:1938:2: ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9_2_0()); 
            // InternalN4MFParser.g:1939:2: ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 )
            // InternalN4MFParser.g:1939:3: rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0
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
    // InternalN4MFParser.g:1947:1: rule__ProjectDescription__Group_9_2__1 : rule__ProjectDescription__Group_9_2__1__Impl ;
    public final void rule__ProjectDescription__Group_9_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1951:1: ( rule__ProjectDescription__Group_9_2__1__Impl )
            // InternalN4MFParser.g:1952:2: rule__ProjectDescription__Group_9_2__1__Impl
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
    // InternalN4MFParser.g:1958:1: rule__ProjectDescription__Group_9_2__1__Impl : ( ( rule__ProjectDescription__Group_9_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_9_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1962:1: ( ( ( rule__ProjectDescription__Group_9_2_1__0 )* ) )
            // InternalN4MFParser.g:1963:1: ( ( rule__ProjectDescription__Group_9_2_1__0 )* )
            {
            // InternalN4MFParser.g:1963:1: ( ( rule__ProjectDescription__Group_9_2_1__0 )* )
            // InternalN4MFParser.g:1964:2: ( rule__ProjectDescription__Group_9_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_9_2_1()); 
            // InternalN4MFParser.g:1965:2: ( rule__ProjectDescription__Group_9_2_1__0 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==Comma) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalN4MFParser.g:1965:3: rule__ProjectDescription__Group_9_2_1__0
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
    // InternalN4MFParser.g:1974:1: rule__ProjectDescription__Group_9_2_1__0 : rule__ProjectDescription__Group_9_2_1__0__Impl rule__ProjectDescription__Group_9_2_1__1 ;
    public final void rule__ProjectDescription__Group_9_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1978:1: ( rule__ProjectDescription__Group_9_2_1__0__Impl rule__ProjectDescription__Group_9_2_1__1 )
            // InternalN4MFParser.g:1979:2: rule__ProjectDescription__Group_9_2_1__0__Impl rule__ProjectDescription__Group_9_2_1__1
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
    // InternalN4MFParser.g:1986:1: rule__ProjectDescription__Group_9_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_9_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1990:1: ( ( Comma ) )
            // InternalN4MFParser.g:1991:1: ( Comma )
            {
            // InternalN4MFParser.g:1991:1: ( Comma )
            // InternalN4MFParser.g:1992:2: Comma
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
    // InternalN4MFParser.g:2001:1: rule__ProjectDescription__Group_9_2_1__1 : rule__ProjectDescription__Group_9_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_9_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2005:1: ( rule__ProjectDescription__Group_9_2_1__1__Impl )
            // InternalN4MFParser.g:2006:2: rule__ProjectDescription__Group_9_2_1__1__Impl
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
    // InternalN4MFParser.g:2012:1: rule__ProjectDescription__Group_9_2_1__1__Impl : ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_9_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2016:1: ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 ) ) )
            // InternalN4MFParser.g:2017:1: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 ) )
            {
            // InternalN4MFParser.g:2017:1: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 ) )
            // InternalN4MFParser.g:2018:2: ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9_2_1_1()); 
            // InternalN4MFParser.g:2019:2: ( rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 )
            // InternalN4MFParser.g:2019:3: rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1
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
    // InternalN4MFParser.g:2028:1: rule__ProjectDescription__Group_10__0 : rule__ProjectDescription__Group_10__0__Impl rule__ProjectDescription__Group_10__1 ;
    public final void rule__ProjectDescription__Group_10__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2032:1: ( rule__ProjectDescription__Group_10__0__Impl rule__ProjectDescription__Group_10__1 )
            // InternalN4MFParser.g:2033:2: rule__ProjectDescription__Group_10__0__Impl rule__ProjectDescription__Group_10__1
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
    // InternalN4MFParser.g:2040:1: rule__ProjectDescription__Group_10__0__Impl : ( ImplementationId ) ;
    public final void rule__ProjectDescription__Group_10__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2044:1: ( ( ImplementationId ) )
            // InternalN4MFParser.g:2045:1: ( ImplementationId )
            {
            // InternalN4MFParser.g:2045:1: ( ImplementationId )
            // InternalN4MFParser.g:2046:2: ImplementationId
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
    // InternalN4MFParser.g:2055:1: rule__ProjectDescription__Group_10__1 : rule__ProjectDescription__Group_10__1__Impl rule__ProjectDescription__Group_10__2 ;
    public final void rule__ProjectDescription__Group_10__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2059:1: ( rule__ProjectDescription__Group_10__1__Impl rule__ProjectDescription__Group_10__2 )
            // InternalN4MFParser.g:2060:2: rule__ProjectDescription__Group_10__1__Impl rule__ProjectDescription__Group_10__2
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
    // InternalN4MFParser.g:2067:1: rule__ProjectDescription__Group_10__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_10__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2071:1: ( ( Colon ) )
            // InternalN4MFParser.g:2072:1: ( Colon )
            {
            // InternalN4MFParser.g:2072:1: ( Colon )
            // InternalN4MFParser.g:2073:2: Colon
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
    // InternalN4MFParser.g:2082:1: rule__ProjectDescription__Group_10__2 : rule__ProjectDescription__Group_10__2__Impl ;
    public final void rule__ProjectDescription__Group_10__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2086:1: ( rule__ProjectDescription__Group_10__2__Impl )
            // InternalN4MFParser.g:2087:2: rule__ProjectDescription__Group_10__2__Impl
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
    // InternalN4MFParser.g:2093:1: rule__ProjectDescription__Group_10__2__Impl : ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) ) ;
    public final void rule__ProjectDescription__Group_10__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2097:1: ( ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) ) )
            // InternalN4MFParser.g:2098:1: ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) )
            {
            // InternalN4MFParser.g:2098:1: ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) )
            // InternalN4MFParser.g:2099:2: ( rule__ProjectDescription__ImplementationIdAssignment_10_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementationIdAssignment_10_2()); 
            // InternalN4MFParser.g:2100:2: ( rule__ProjectDescription__ImplementationIdAssignment_10_2 )
            // InternalN4MFParser.g:2100:3: rule__ProjectDescription__ImplementationIdAssignment_10_2
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
    // InternalN4MFParser.g:2109:1: rule__ProjectDescription__Group_11__0 : rule__ProjectDescription__Group_11__0__Impl rule__ProjectDescription__Group_11__1 ;
    public final void rule__ProjectDescription__Group_11__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2113:1: ( rule__ProjectDescription__Group_11__0__Impl rule__ProjectDescription__Group_11__1 )
            // InternalN4MFParser.g:2114:2: rule__ProjectDescription__Group_11__0__Impl rule__ProjectDescription__Group_11__1
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
    // InternalN4MFParser.g:2121:1: rule__ProjectDescription__Group_11__0__Impl : ( ImplementedProjects ) ;
    public final void rule__ProjectDescription__Group_11__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2125:1: ( ( ImplementedProjects ) )
            // InternalN4MFParser.g:2126:1: ( ImplementedProjects )
            {
            // InternalN4MFParser.g:2126:1: ( ImplementedProjects )
            // InternalN4MFParser.g:2127:2: ImplementedProjects
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
    // InternalN4MFParser.g:2136:1: rule__ProjectDescription__Group_11__1 : rule__ProjectDescription__Group_11__1__Impl rule__ProjectDescription__Group_11__2 ;
    public final void rule__ProjectDescription__Group_11__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2140:1: ( rule__ProjectDescription__Group_11__1__Impl rule__ProjectDescription__Group_11__2 )
            // InternalN4MFParser.g:2141:2: rule__ProjectDescription__Group_11__1__Impl rule__ProjectDescription__Group_11__2
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
    // InternalN4MFParser.g:2148:1: rule__ProjectDescription__Group_11__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_11__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2152:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2153:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2153:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2154:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:2163:1: rule__ProjectDescription__Group_11__2 : rule__ProjectDescription__Group_11__2__Impl rule__ProjectDescription__Group_11__3 ;
    public final void rule__ProjectDescription__Group_11__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2167:1: ( rule__ProjectDescription__Group_11__2__Impl rule__ProjectDescription__Group_11__3 )
            // InternalN4MFParser.g:2168:2: rule__ProjectDescription__Group_11__2__Impl rule__ProjectDescription__Group_11__3
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
    // InternalN4MFParser.g:2175:1: rule__ProjectDescription__Group_11__2__Impl : ( ( rule__ProjectDescription__Group_11_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_11__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2179:1: ( ( ( rule__ProjectDescription__Group_11_2__0 )? ) )
            // InternalN4MFParser.g:2180:1: ( ( rule__ProjectDescription__Group_11_2__0 )? )
            {
            // InternalN4MFParser.g:2180:1: ( ( rule__ProjectDescription__Group_11_2__0 )? )
            // InternalN4MFParser.g:2181:2: ( rule__ProjectDescription__Group_11_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_11_2()); 
            // InternalN4MFParser.g:2182:2: ( rule__ProjectDescription__Group_11_2__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==ProjectDependencies||LA18_0==ProjectVersion||LA18_0==ModuleFilters||(LA18_0>=ProjectType && LA18_0<=Application)||LA18_0==VendorName||(LA18_0>=Libraries && LA18_0<=VendorId)||LA18_0==Sources||LA18_0==Content||LA18_0==Output||(LA18_0>=Test && LA18_0<=API)||LA18_0==RULE_ID) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalN4MFParser.g:2182:3: rule__ProjectDescription__Group_11_2__0
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
    // InternalN4MFParser.g:2190:1: rule__ProjectDescription__Group_11__3 : rule__ProjectDescription__Group_11__3__Impl ;
    public final void rule__ProjectDescription__Group_11__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2194:1: ( rule__ProjectDescription__Group_11__3__Impl )
            // InternalN4MFParser.g:2195:2: rule__ProjectDescription__Group_11__3__Impl
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
    // InternalN4MFParser.g:2201:1: rule__ProjectDescription__Group_11__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_11__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2205:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2206:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2206:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2207:2: RightCurlyBracket
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
    // InternalN4MFParser.g:2217:1: rule__ProjectDescription__Group_11_2__0 : rule__ProjectDescription__Group_11_2__0__Impl rule__ProjectDescription__Group_11_2__1 ;
    public final void rule__ProjectDescription__Group_11_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2221:1: ( rule__ProjectDescription__Group_11_2__0__Impl rule__ProjectDescription__Group_11_2__1 )
            // InternalN4MFParser.g:2222:2: rule__ProjectDescription__Group_11_2__0__Impl rule__ProjectDescription__Group_11_2__1
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
    // InternalN4MFParser.g:2229:1: rule__ProjectDescription__Group_11_2__0__Impl : ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_11_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2233:1: ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 ) ) )
            // InternalN4MFParser.g:2234:1: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 ) )
            {
            // InternalN4MFParser.g:2234:1: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 ) )
            // InternalN4MFParser.g:2235:2: ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11_2_0()); 
            // InternalN4MFParser.g:2236:2: ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 )
            // InternalN4MFParser.g:2236:3: rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0
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
    // InternalN4MFParser.g:2244:1: rule__ProjectDescription__Group_11_2__1 : rule__ProjectDescription__Group_11_2__1__Impl ;
    public final void rule__ProjectDescription__Group_11_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2248:1: ( rule__ProjectDescription__Group_11_2__1__Impl )
            // InternalN4MFParser.g:2249:2: rule__ProjectDescription__Group_11_2__1__Impl
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
    // InternalN4MFParser.g:2255:1: rule__ProjectDescription__Group_11_2__1__Impl : ( ( rule__ProjectDescription__Group_11_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_11_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2259:1: ( ( ( rule__ProjectDescription__Group_11_2_1__0 )* ) )
            // InternalN4MFParser.g:2260:1: ( ( rule__ProjectDescription__Group_11_2_1__0 )* )
            {
            // InternalN4MFParser.g:2260:1: ( ( rule__ProjectDescription__Group_11_2_1__0 )* )
            // InternalN4MFParser.g:2261:2: ( rule__ProjectDescription__Group_11_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_11_2_1()); 
            // InternalN4MFParser.g:2262:2: ( rule__ProjectDescription__Group_11_2_1__0 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==Comma) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalN4MFParser.g:2262:3: rule__ProjectDescription__Group_11_2_1__0
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
    // InternalN4MFParser.g:2271:1: rule__ProjectDescription__Group_11_2_1__0 : rule__ProjectDescription__Group_11_2_1__0__Impl rule__ProjectDescription__Group_11_2_1__1 ;
    public final void rule__ProjectDescription__Group_11_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2275:1: ( rule__ProjectDescription__Group_11_2_1__0__Impl rule__ProjectDescription__Group_11_2_1__1 )
            // InternalN4MFParser.g:2276:2: rule__ProjectDescription__Group_11_2_1__0__Impl rule__ProjectDescription__Group_11_2_1__1
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
    // InternalN4MFParser.g:2283:1: rule__ProjectDescription__Group_11_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_11_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2287:1: ( ( Comma ) )
            // InternalN4MFParser.g:2288:1: ( Comma )
            {
            // InternalN4MFParser.g:2288:1: ( Comma )
            // InternalN4MFParser.g:2289:2: Comma
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
    // InternalN4MFParser.g:2298:1: rule__ProjectDescription__Group_11_2_1__1 : rule__ProjectDescription__Group_11_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_11_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2302:1: ( rule__ProjectDescription__Group_11_2_1__1__Impl )
            // InternalN4MFParser.g:2303:2: rule__ProjectDescription__Group_11_2_1__1__Impl
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
    // InternalN4MFParser.g:2309:1: rule__ProjectDescription__Group_11_2_1__1__Impl : ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_11_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2313:1: ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 ) ) )
            // InternalN4MFParser.g:2314:1: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 ) )
            {
            // InternalN4MFParser.g:2314:1: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 ) )
            // InternalN4MFParser.g:2315:2: ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11_2_1_1()); 
            // InternalN4MFParser.g:2316:2: ( rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 )
            // InternalN4MFParser.g:2316:3: rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1
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
    // InternalN4MFParser.g:2325:1: rule__ProjectDescription__Group_12__0 : rule__ProjectDescription__Group_12__0__Impl rule__ProjectDescription__Group_12__1 ;
    public final void rule__ProjectDescription__Group_12__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2329:1: ( rule__ProjectDescription__Group_12__0__Impl rule__ProjectDescription__Group_12__1 )
            // InternalN4MFParser.g:2330:2: rule__ProjectDescription__Group_12__0__Impl rule__ProjectDescription__Group_12__1
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
    // InternalN4MFParser.g:2337:1: rule__ProjectDescription__Group_12__0__Impl : ( InitModules ) ;
    public final void rule__ProjectDescription__Group_12__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2341:1: ( ( InitModules ) )
            // InternalN4MFParser.g:2342:1: ( InitModules )
            {
            // InternalN4MFParser.g:2342:1: ( InitModules )
            // InternalN4MFParser.g:2343:2: InitModules
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
    // InternalN4MFParser.g:2352:1: rule__ProjectDescription__Group_12__1 : rule__ProjectDescription__Group_12__1__Impl rule__ProjectDescription__Group_12__2 ;
    public final void rule__ProjectDescription__Group_12__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2356:1: ( rule__ProjectDescription__Group_12__1__Impl rule__ProjectDescription__Group_12__2 )
            // InternalN4MFParser.g:2357:2: rule__ProjectDescription__Group_12__1__Impl rule__ProjectDescription__Group_12__2
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
    // InternalN4MFParser.g:2364:1: rule__ProjectDescription__Group_12__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_12__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2368:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2369:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2369:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2370:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:2379:1: rule__ProjectDescription__Group_12__2 : rule__ProjectDescription__Group_12__2__Impl rule__ProjectDescription__Group_12__3 ;
    public final void rule__ProjectDescription__Group_12__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2383:1: ( rule__ProjectDescription__Group_12__2__Impl rule__ProjectDescription__Group_12__3 )
            // InternalN4MFParser.g:2384:2: rule__ProjectDescription__Group_12__2__Impl rule__ProjectDescription__Group_12__3
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
    // InternalN4MFParser.g:2391:1: rule__ProjectDescription__Group_12__2__Impl : ( ( rule__ProjectDescription__Group_12_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_12__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2395:1: ( ( ( rule__ProjectDescription__Group_12_2__0 )? ) )
            // InternalN4MFParser.g:2396:1: ( ( rule__ProjectDescription__Group_12_2__0 )? )
            {
            // InternalN4MFParser.g:2396:1: ( ( rule__ProjectDescription__Group_12_2__0 )? )
            // InternalN4MFParser.g:2397:2: ( rule__ProjectDescription__Group_12_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_12_2()); 
            // InternalN4MFParser.g:2398:2: ( rule__ProjectDescription__Group_12_2__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_STRING) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalN4MFParser.g:2398:3: rule__ProjectDescription__Group_12_2__0
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
    // InternalN4MFParser.g:2406:1: rule__ProjectDescription__Group_12__3 : rule__ProjectDescription__Group_12__3__Impl ;
    public final void rule__ProjectDescription__Group_12__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2410:1: ( rule__ProjectDescription__Group_12__3__Impl )
            // InternalN4MFParser.g:2411:2: rule__ProjectDescription__Group_12__3__Impl
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
    // InternalN4MFParser.g:2417:1: rule__ProjectDescription__Group_12__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_12__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2421:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2422:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2422:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2423:2: RightCurlyBracket
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
    // InternalN4MFParser.g:2433:1: rule__ProjectDescription__Group_12_2__0 : rule__ProjectDescription__Group_12_2__0__Impl rule__ProjectDescription__Group_12_2__1 ;
    public final void rule__ProjectDescription__Group_12_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2437:1: ( rule__ProjectDescription__Group_12_2__0__Impl rule__ProjectDescription__Group_12_2__1 )
            // InternalN4MFParser.g:2438:2: rule__ProjectDescription__Group_12_2__0__Impl rule__ProjectDescription__Group_12_2__1
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
    // InternalN4MFParser.g:2445:1: rule__ProjectDescription__Group_12_2__0__Impl : ( ( rule__ProjectDescription__InitModulesAssignment_12_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_12_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2449:1: ( ( ( rule__ProjectDescription__InitModulesAssignment_12_2_0 ) ) )
            // InternalN4MFParser.g:2450:1: ( ( rule__ProjectDescription__InitModulesAssignment_12_2_0 ) )
            {
            // InternalN4MFParser.g:2450:1: ( ( rule__ProjectDescription__InitModulesAssignment_12_2_0 ) )
            // InternalN4MFParser.g:2451:2: ( rule__ProjectDescription__InitModulesAssignment_12_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12_2_0()); 
            // InternalN4MFParser.g:2452:2: ( rule__ProjectDescription__InitModulesAssignment_12_2_0 )
            // InternalN4MFParser.g:2452:3: rule__ProjectDescription__InitModulesAssignment_12_2_0
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
    // InternalN4MFParser.g:2460:1: rule__ProjectDescription__Group_12_2__1 : rule__ProjectDescription__Group_12_2__1__Impl ;
    public final void rule__ProjectDescription__Group_12_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2464:1: ( rule__ProjectDescription__Group_12_2__1__Impl )
            // InternalN4MFParser.g:2465:2: rule__ProjectDescription__Group_12_2__1__Impl
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
    // InternalN4MFParser.g:2471:1: rule__ProjectDescription__Group_12_2__1__Impl : ( ( rule__ProjectDescription__Group_12_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_12_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2475:1: ( ( ( rule__ProjectDescription__Group_12_2_1__0 )* ) )
            // InternalN4MFParser.g:2476:1: ( ( rule__ProjectDescription__Group_12_2_1__0 )* )
            {
            // InternalN4MFParser.g:2476:1: ( ( rule__ProjectDescription__Group_12_2_1__0 )* )
            // InternalN4MFParser.g:2477:2: ( rule__ProjectDescription__Group_12_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_12_2_1()); 
            // InternalN4MFParser.g:2478:2: ( rule__ProjectDescription__Group_12_2_1__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==Comma) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalN4MFParser.g:2478:3: rule__ProjectDescription__Group_12_2_1__0
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
    // InternalN4MFParser.g:2487:1: rule__ProjectDescription__Group_12_2_1__0 : rule__ProjectDescription__Group_12_2_1__0__Impl rule__ProjectDescription__Group_12_2_1__1 ;
    public final void rule__ProjectDescription__Group_12_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2491:1: ( rule__ProjectDescription__Group_12_2_1__0__Impl rule__ProjectDescription__Group_12_2_1__1 )
            // InternalN4MFParser.g:2492:2: rule__ProjectDescription__Group_12_2_1__0__Impl rule__ProjectDescription__Group_12_2_1__1
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
    // InternalN4MFParser.g:2499:1: rule__ProjectDescription__Group_12_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_12_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2503:1: ( ( Comma ) )
            // InternalN4MFParser.g:2504:1: ( Comma )
            {
            // InternalN4MFParser.g:2504:1: ( Comma )
            // InternalN4MFParser.g:2505:2: Comma
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
    // InternalN4MFParser.g:2514:1: rule__ProjectDescription__Group_12_2_1__1 : rule__ProjectDescription__Group_12_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_12_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2518:1: ( rule__ProjectDescription__Group_12_2_1__1__Impl )
            // InternalN4MFParser.g:2519:2: rule__ProjectDescription__Group_12_2_1__1__Impl
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
    // InternalN4MFParser.g:2525:1: rule__ProjectDescription__Group_12_2_1__1__Impl : ( ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_12_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2529:1: ( ( ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 ) ) )
            // InternalN4MFParser.g:2530:1: ( ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 ) )
            {
            // InternalN4MFParser.g:2530:1: ( ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 ) )
            // InternalN4MFParser.g:2531:2: ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12_2_1_1()); 
            // InternalN4MFParser.g:2532:2: ( rule__ProjectDescription__InitModulesAssignment_12_2_1_1 )
            // InternalN4MFParser.g:2532:3: rule__ProjectDescription__InitModulesAssignment_12_2_1_1
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
    // InternalN4MFParser.g:2541:1: rule__ProjectDescription__Group_13__0 : rule__ProjectDescription__Group_13__0__Impl rule__ProjectDescription__Group_13__1 ;
    public final void rule__ProjectDescription__Group_13__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2545:1: ( rule__ProjectDescription__Group_13__0__Impl rule__ProjectDescription__Group_13__1 )
            // InternalN4MFParser.g:2546:2: rule__ProjectDescription__Group_13__0__Impl rule__ProjectDescription__Group_13__1
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
    // InternalN4MFParser.g:2553:1: rule__ProjectDescription__Group_13__0__Impl : ( ExecModule ) ;
    public final void rule__ProjectDescription__Group_13__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2557:1: ( ( ExecModule ) )
            // InternalN4MFParser.g:2558:1: ( ExecModule )
            {
            // InternalN4MFParser.g:2558:1: ( ExecModule )
            // InternalN4MFParser.g:2559:2: ExecModule
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
    // InternalN4MFParser.g:2568:1: rule__ProjectDescription__Group_13__1 : rule__ProjectDescription__Group_13__1__Impl rule__ProjectDescription__Group_13__2 ;
    public final void rule__ProjectDescription__Group_13__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2572:1: ( rule__ProjectDescription__Group_13__1__Impl rule__ProjectDescription__Group_13__2 )
            // InternalN4MFParser.g:2573:2: rule__ProjectDescription__Group_13__1__Impl rule__ProjectDescription__Group_13__2
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
    // InternalN4MFParser.g:2580:1: rule__ProjectDescription__Group_13__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_13__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2584:1: ( ( Colon ) )
            // InternalN4MFParser.g:2585:1: ( Colon )
            {
            // InternalN4MFParser.g:2585:1: ( Colon )
            // InternalN4MFParser.g:2586:2: Colon
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
    // InternalN4MFParser.g:2595:1: rule__ProjectDescription__Group_13__2 : rule__ProjectDescription__Group_13__2__Impl ;
    public final void rule__ProjectDescription__Group_13__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2599:1: ( rule__ProjectDescription__Group_13__2__Impl )
            // InternalN4MFParser.g:2600:2: rule__ProjectDescription__Group_13__2__Impl
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
    // InternalN4MFParser.g:2606:1: rule__ProjectDescription__Group_13__2__Impl : ( ( rule__ProjectDescription__ExecModuleAssignment_13_2 ) ) ;
    public final void rule__ProjectDescription__Group_13__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2610:1: ( ( ( rule__ProjectDescription__ExecModuleAssignment_13_2 ) ) )
            // InternalN4MFParser.g:2611:1: ( ( rule__ProjectDescription__ExecModuleAssignment_13_2 ) )
            {
            // InternalN4MFParser.g:2611:1: ( ( rule__ProjectDescription__ExecModuleAssignment_13_2 ) )
            // InternalN4MFParser.g:2612:2: ( rule__ProjectDescription__ExecModuleAssignment_13_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_13_2()); 
            // InternalN4MFParser.g:2613:2: ( rule__ProjectDescription__ExecModuleAssignment_13_2 )
            // InternalN4MFParser.g:2613:3: rule__ProjectDescription__ExecModuleAssignment_13_2
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
    // InternalN4MFParser.g:2622:1: rule__ProjectDescription__Group_14__0 : rule__ProjectDescription__Group_14__0__Impl rule__ProjectDescription__Group_14__1 ;
    public final void rule__ProjectDescription__Group_14__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2626:1: ( rule__ProjectDescription__Group_14__0__Impl rule__ProjectDescription__Group_14__1 )
            // InternalN4MFParser.g:2627:2: rule__ProjectDescription__Group_14__0__Impl rule__ProjectDescription__Group_14__1
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
    // InternalN4MFParser.g:2634:1: rule__ProjectDescription__Group_14__0__Impl : ( Output ) ;
    public final void rule__ProjectDescription__Group_14__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2638:1: ( ( Output ) )
            // InternalN4MFParser.g:2639:1: ( Output )
            {
            // InternalN4MFParser.g:2639:1: ( Output )
            // InternalN4MFParser.g:2640:2: Output
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
    // InternalN4MFParser.g:2649:1: rule__ProjectDescription__Group_14__1 : rule__ProjectDescription__Group_14__1__Impl rule__ProjectDescription__Group_14__2 ;
    public final void rule__ProjectDescription__Group_14__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2653:1: ( rule__ProjectDescription__Group_14__1__Impl rule__ProjectDescription__Group_14__2 )
            // InternalN4MFParser.g:2654:2: rule__ProjectDescription__Group_14__1__Impl rule__ProjectDescription__Group_14__2
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
    // InternalN4MFParser.g:2661:1: rule__ProjectDescription__Group_14__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_14__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2665:1: ( ( Colon ) )
            // InternalN4MFParser.g:2666:1: ( Colon )
            {
            // InternalN4MFParser.g:2666:1: ( Colon )
            // InternalN4MFParser.g:2667:2: Colon
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
    // InternalN4MFParser.g:2676:1: rule__ProjectDescription__Group_14__2 : rule__ProjectDescription__Group_14__2__Impl ;
    public final void rule__ProjectDescription__Group_14__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2680:1: ( rule__ProjectDescription__Group_14__2__Impl )
            // InternalN4MFParser.g:2681:2: rule__ProjectDescription__Group_14__2__Impl
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
    // InternalN4MFParser.g:2687:1: rule__ProjectDescription__Group_14__2__Impl : ( ( rule__ProjectDescription__OutputPathRawAssignment_14_2 ) ) ;
    public final void rule__ProjectDescription__Group_14__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2691:1: ( ( ( rule__ProjectDescription__OutputPathRawAssignment_14_2 ) ) )
            // InternalN4MFParser.g:2692:1: ( ( rule__ProjectDescription__OutputPathRawAssignment_14_2 ) )
            {
            // InternalN4MFParser.g:2692:1: ( ( rule__ProjectDescription__OutputPathRawAssignment_14_2 ) )
            // InternalN4MFParser.g:2693:2: ( rule__ProjectDescription__OutputPathRawAssignment_14_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getOutputPathRawAssignment_14_2()); 
            // InternalN4MFParser.g:2694:2: ( rule__ProjectDescription__OutputPathRawAssignment_14_2 )
            // InternalN4MFParser.g:2694:3: rule__ProjectDescription__OutputPathRawAssignment_14_2
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
    // InternalN4MFParser.g:2703:1: rule__ProjectDescription__Group_15__0 : rule__ProjectDescription__Group_15__0__Impl rule__ProjectDescription__Group_15__1 ;
    public final void rule__ProjectDescription__Group_15__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2707:1: ( rule__ProjectDescription__Group_15__0__Impl rule__ProjectDescription__Group_15__1 )
            // InternalN4MFParser.g:2708:2: rule__ProjectDescription__Group_15__0__Impl rule__ProjectDescription__Group_15__1
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
    // InternalN4MFParser.g:2715:1: rule__ProjectDescription__Group_15__0__Impl : ( Libraries ) ;
    public final void rule__ProjectDescription__Group_15__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2719:1: ( ( Libraries ) )
            // InternalN4MFParser.g:2720:1: ( Libraries )
            {
            // InternalN4MFParser.g:2720:1: ( Libraries )
            // InternalN4MFParser.g:2721:2: Libraries
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
    // InternalN4MFParser.g:2730:1: rule__ProjectDescription__Group_15__1 : rule__ProjectDescription__Group_15__1__Impl rule__ProjectDescription__Group_15__2 ;
    public final void rule__ProjectDescription__Group_15__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2734:1: ( rule__ProjectDescription__Group_15__1__Impl rule__ProjectDescription__Group_15__2 )
            // InternalN4MFParser.g:2735:2: rule__ProjectDescription__Group_15__1__Impl rule__ProjectDescription__Group_15__2
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
    // InternalN4MFParser.g:2742:1: rule__ProjectDescription__Group_15__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_15__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2746:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2747:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2747:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2748:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:2757:1: rule__ProjectDescription__Group_15__2 : rule__ProjectDescription__Group_15__2__Impl rule__ProjectDescription__Group_15__3 ;
    public final void rule__ProjectDescription__Group_15__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2761:1: ( rule__ProjectDescription__Group_15__2__Impl rule__ProjectDescription__Group_15__3 )
            // InternalN4MFParser.g:2762:2: rule__ProjectDescription__Group_15__2__Impl rule__ProjectDescription__Group_15__3
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
    // InternalN4MFParser.g:2769:1: rule__ProjectDescription__Group_15__2__Impl : ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 ) ) ;
    public final void rule__ProjectDescription__Group_15__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2773:1: ( ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 ) ) )
            // InternalN4MFParser.g:2774:1: ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 ) )
            {
            // InternalN4MFParser.g:2774:1: ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 ) )
            // InternalN4MFParser.g:2775:2: ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_2()); 
            // InternalN4MFParser.g:2776:2: ( rule__ProjectDescription__LibraryPathsRawAssignment_15_2 )
            // InternalN4MFParser.g:2776:3: rule__ProjectDescription__LibraryPathsRawAssignment_15_2
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
    // InternalN4MFParser.g:2784:1: rule__ProjectDescription__Group_15__3 : rule__ProjectDescription__Group_15__3__Impl rule__ProjectDescription__Group_15__4 ;
    public final void rule__ProjectDescription__Group_15__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2788:1: ( rule__ProjectDescription__Group_15__3__Impl rule__ProjectDescription__Group_15__4 )
            // InternalN4MFParser.g:2789:2: rule__ProjectDescription__Group_15__3__Impl rule__ProjectDescription__Group_15__4
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
    // InternalN4MFParser.g:2796:1: rule__ProjectDescription__Group_15__3__Impl : ( ( rule__ProjectDescription__Group_15_3__0 )* ) ;
    public final void rule__ProjectDescription__Group_15__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2800:1: ( ( ( rule__ProjectDescription__Group_15_3__0 )* ) )
            // InternalN4MFParser.g:2801:1: ( ( rule__ProjectDescription__Group_15_3__0 )* )
            {
            // InternalN4MFParser.g:2801:1: ( ( rule__ProjectDescription__Group_15_3__0 )* )
            // InternalN4MFParser.g:2802:2: ( rule__ProjectDescription__Group_15_3__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_15_3()); 
            // InternalN4MFParser.g:2803:2: ( rule__ProjectDescription__Group_15_3__0 )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==Comma) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalN4MFParser.g:2803:3: rule__ProjectDescription__Group_15_3__0
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
    // InternalN4MFParser.g:2811:1: rule__ProjectDescription__Group_15__4 : rule__ProjectDescription__Group_15__4__Impl ;
    public final void rule__ProjectDescription__Group_15__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2815:1: ( rule__ProjectDescription__Group_15__4__Impl )
            // InternalN4MFParser.g:2816:2: rule__ProjectDescription__Group_15__4__Impl
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
    // InternalN4MFParser.g:2822:1: rule__ProjectDescription__Group_15__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_15__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2826:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2827:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2827:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2828:2: RightCurlyBracket
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
    // InternalN4MFParser.g:2838:1: rule__ProjectDescription__Group_15_3__0 : rule__ProjectDescription__Group_15_3__0__Impl rule__ProjectDescription__Group_15_3__1 ;
    public final void rule__ProjectDescription__Group_15_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2842:1: ( rule__ProjectDescription__Group_15_3__0__Impl rule__ProjectDescription__Group_15_3__1 )
            // InternalN4MFParser.g:2843:2: rule__ProjectDescription__Group_15_3__0__Impl rule__ProjectDescription__Group_15_3__1
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
    // InternalN4MFParser.g:2850:1: rule__ProjectDescription__Group_15_3__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_15_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2854:1: ( ( Comma ) )
            // InternalN4MFParser.g:2855:1: ( Comma )
            {
            // InternalN4MFParser.g:2855:1: ( Comma )
            // InternalN4MFParser.g:2856:2: Comma
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
    // InternalN4MFParser.g:2865:1: rule__ProjectDescription__Group_15_3__1 : rule__ProjectDescription__Group_15_3__1__Impl ;
    public final void rule__ProjectDescription__Group_15_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2869:1: ( rule__ProjectDescription__Group_15_3__1__Impl )
            // InternalN4MFParser.g:2870:2: rule__ProjectDescription__Group_15_3__1__Impl
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
    // InternalN4MFParser.g:2876:1: rule__ProjectDescription__Group_15_3__1__Impl : ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 ) ) ;
    public final void rule__ProjectDescription__Group_15_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2880:1: ( ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 ) ) )
            // InternalN4MFParser.g:2881:1: ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 ) )
            {
            // InternalN4MFParser.g:2881:1: ( ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 ) )
            // InternalN4MFParser.g:2882:2: ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_3_1()); 
            // InternalN4MFParser.g:2883:2: ( rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 )
            // InternalN4MFParser.g:2883:3: rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1
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
    // InternalN4MFParser.g:2892:1: rule__ProjectDescription__Group_16__0 : rule__ProjectDescription__Group_16__0__Impl rule__ProjectDescription__Group_16__1 ;
    public final void rule__ProjectDescription__Group_16__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2896:1: ( rule__ProjectDescription__Group_16__0__Impl rule__ProjectDescription__Group_16__1 )
            // InternalN4MFParser.g:2897:2: rule__ProjectDescription__Group_16__0__Impl rule__ProjectDescription__Group_16__1
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
    // InternalN4MFParser.g:2904:1: rule__ProjectDescription__Group_16__0__Impl : ( Resources ) ;
    public final void rule__ProjectDescription__Group_16__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2908:1: ( ( Resources ) )
            // InternalN4MFParser.g:2909:1: ( Resources )
            {
            // InternalN4MFParser.g:2909:1: ( Resources )
            // InternalN4MFParser.g:2910:2: Resources
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
    // InternalN4MFParser.g:2919:1: rule__ProjectDescription__Group_16__1 : rule__ProjectDescription__Group_16__1__Impl rule__ProjectDescription__Group_16__2 ;
    public final void rule__ProjectDescription__Group_16__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2923:1: ( rule__ProjectDescription__Group_16__1__Impl rule__ProjectDescription__Group_16__2 )
            // InternalN4MFParser.g:2924:2: rule__ProjectDescription__Group_16__1__Impl rule__ProjectDescription__Group_16__2
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
    // InternalN4MFParser.g:2931:1: rule__ProjectDescription__Group_16__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_16__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2935:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2936:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2936:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2937:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:2946:1: rule__ProjectDescription__Group_16__2 : rule__ProjectDescription__Group_16__2__Impl rule__ProjectDescription__Group_16__3 ;
    public final void rule__ProjectDescription__Group_16__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2950:1: ( rule__ProjectDescription__Group_16__2__Impl rule__ProjectDescription__Group_16__3 )
            // InternalN4MFParser.g:2951:2: rule__ProjectDescription__Group_16__2__Impl rule__ProjectDescription__Group_16__3
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
    // InternalN4MFParser.g:2958:1: rule__ProjectDescription__Group_16__2__Impl : ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 ) ) ;
    public final void rule__ProjectDescription__Group_16__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2962:1: ( ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 ) ) )
            // InternalN4MFParser.g:2963:1: ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 ) )
            {
            // InternalN4MFParser.g:2963:1: ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 ) )
            // InternalN4MFParser.g:2964:2: ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_2()); 
            // InternalN4MFParser.g:2965:2: ( rule__ProjectDescription__ResourcePathsRawAssignment_16_2 )
            // InternalN4MFParser.g:2965:3: rule__ProjectDescription__ResourcePathsRawAssignment_16_2
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
    // InternalN4MFParser.g:2973:1: rule__ProjectDescription__Group_16__3 : rule__ProjectDescription__Group_16__3__Impl rule__ProjectDescription__Group_16__4 ;
    public final void rule__ProjectDescription__Group_16__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2977:1: ( rule__ProjectDescription__Group_16__3__Impl rule__ProjectDescription__Group_16__4 )
            // InternalN4MFParser.g:2978:2: rule__ProjectDescription__Group_16__3__Impl rule__ProjectDescription__Group_16__4
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
    // InternalN4MFParser.g:2985:1: rule__ProjectDescription__Group_16__3__Impl : ( ( rule__ProjectDescription__Group_16_3__0 )* ) ;
    public final void rule__ProjectDescription__Group_16__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2989:1: ( ( ( rule__ProjectDescription__Group_16_3__0 )* ) )
            // InternalN4MFParser.g:2990:1: ( ( rule__ProjectDescription__Group_16_3__0 )* )
            {
            // InternalN4MFParser.g:2990:1: ( ( rule__ProjectDescription__Group_16_3__0 )* )
            // InternalN4MFParser.g:2991:2: ( rule__ProjectDescription__Group_16_3__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_16_3()); 
            // InternalN4MFParser.g:2992:2: ( rule__ProjectDescription__Group_16_3__0 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==Comma) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalN4MFParser.g:2992:3: rule__ProjectDescription__Group_16_3__0
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
    // InternalN4MFParser.g:3000:1: rule__ProjectDescription__Group_16__4 : rule__ProjectDescription__Group_16__4__Impl ;
    public final void rule__ProjectDescription__Group_16__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3004:1: ( rule__ProjectDescription__Group_16__4__Impl )
            // InternalN4MFParser.g:3005:2: rule__ProjectDescription__Group_16__4__Impl
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
    // InternalN4MFParser.g:3011:1: rule__ProjectDescription__Group_16__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_16__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3015:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3016:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3016:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3017:2: RightCurlyBracket
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
    // InternalN4MFParser.g:3027:1: rule__ProjectDescription__Group_16_3__0 : rule__ProjectDescription__Group_16_3__0__Impl rule__ProjectDescription__Group_16_3__1 ;
    public final void rule__ProjectDescription__Group_16_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3031:1: ( rule__ProjectDescription__Group_16_3__0__Impl rule__ProjectDescription__Group_16_3__1 )
            // InternalN4MFParser.g:3032:2: rule__ProjectDescription__Group_16_3__0__Impl rule__ProjectDescription__Group_16_3__1
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
    // InternalN4MFParser.g:3039:1: rule__ProjectDescription__Group_16_3__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_16_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3043:1: ( ( Comma ) )
            // InternalN4MFParser.g:3044:1: ( Comma )
            {
            // InternalN4MFParser.g:3044:1: ( Comma )
            // InternalN4MFParser.g:3045:2: Comma
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
    // InternalN4MFParser.g:3054:1: rule__ProjectDescription__Group_16_3__1 : rule__ProjectDescription__Group_16_3__1__Impl ;
    public final void rule__ProjectDescription__Group_16_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3058:1: ( rule__ProjectDescription__Group_16_3__1__Impl )
            // InternalN4MFParser.g:3059:2: rule__ProjectDescription__Group_16_3__1__Impl
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
    // InternalN4MFParser.g:3065:1: rule__ProjectDescription__Group_16_3__1__Impl : ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 ) ) ;
    public final void rule__ProjectDescription__Group_16_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3069:1: ( ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 ) ) )
            // InternalN4MFParser.g:3070:1: ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 ) )
            {
            // InternalN4MFParser.g:3070:1: ( ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 ) )
            // InternalN4MFParser.g:3071:2: ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_3_1()); 
            // InternalN4MFParser.g:3072:2: ( rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 )
            // InternalN4MFParser.g:3072:3: rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1
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
    // InternalN4MFParser.g:3081:1: rule__ProjectDescription__Group_17__0 : rule__ProjectDescription__Group_17__0__Impl rule__ProjectDescription__Group_17__1 ;
    public final void rule__ProjectDescription__Group_17__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3085:1: ( rule__ProjectDescription__Group_17__0__Impl rule__ProjectDescription__Group_17__1 )
            // InternalN4MFParser.g:3086:2: rule__ProjectDescription__Group_17__0__Impl rule__ProjectDescription__Group_17__1
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
    // InternalN4MFParser.g:3093:1: rule__ProjectDescription__Group_17__0__Impl : ( Sources ) ;
    public final void rule__ProjectDescription__Group_17__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3097:1: ( ( Sources ) )
            // InternalN4MFParser.g:3098:1: ( Sources )
            {
            // InternalN4MFParser.g:3098:1: ( Sources )
            // InternalN4MFParser.g:3099:2: Sources
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
    // InternalN4MFParser.g:3108:1: rule__ProjectDescription__Group_17__1 : rule__ProjectDescription__Group_17__1__Impl rule__ProjectDescription__Group_17__2 ;
    public final void rule__ProjectDescription__Group_17__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3112:1: ( rule__ProjectDescription__Group_17__1__Impl rule__ProjectDescription__Group_17__2 )
            // InternalN4MFParser.g:3113:2: rule__ProjectDescription__Group_17__1__Impl rule__ProjectDescription__Group_17__2
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
    // InternalN4MFParser.g:3120:1: rule__ProjectDescription__Group_17__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_17__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3124:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3125:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3125:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3126:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:3135:1: rule__ProjectDescription__Group_17__2 : rule__ProjectDescription__Group_17__2__Impl rule__ProjectDescription__Group_17__3 ;
    public final void rule__ProjectDescription__Group_17__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3139:1: ( rule__ProjectDescription__Group_17__2__Impl rule__ProjectDescription__Group_17__3 )
            // InternalN4MFParser.g:3140:2: rule__ProjectDescription__Group_17__2__Impl rule__ProjectDescription__Group_17__3
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
    // InternalN4MFParser.g:3147:1: rule__ProjectDescription__Group_17__2__Impl : ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) ) ;
    public final void rule__ProjectDescription__Group_17__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3151:1: ( ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) ) )
            // InternalN4MFParser.g:3152:1: ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) )
            {
            // InternalN4MFParser.g:3152:1: ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) )
            // InternalN4MFParser.g:3153:2: ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* )
            {
            // InternalN4MFParser.g:3153:2: ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) )
            // InternalN4MFParser.g:3154:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 
            // InternalN4MFParser.g:3155:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )
            // InternalN4MFParser.g:3155:4: rule__ProjectDescription__SourceFragmentAssignment_17_2
            {
            pushFollow(FOLLOW_16);
            rule__ProjectDescription__SourceFragmentAssignment_17_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 

            }

            // InternalN4MFParser.g:3158:2: ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* )
            // InternalN4MFParser.g:3159:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 
            // InternalN4MFParser.g:3160:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==External||LA24_0==Source||LA24_0==Test) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // InternalN4MFParser.g:3160:4: rule__ProjectDescription__SourceFragmentAssignment_17_2
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
    // InternalN4MFParser.g:3169:1: rule__ProjectDescription__Group_17__3 : rule__ProjectDescription__Group_17__3__Impl ;
    public final void rule__ProjectDescription__Group_17__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3173:1: ( rule__ProjectDescription__Group_17__3__Impl )
            // InternalN4MFParser.g:3174:2: rule__ProjectDescription__Group_17__3__Impl
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
    // InternalN4MFParser.g:3180:1: rule__ProjectDescription__Group_17__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_17__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3184:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3185:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3185:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3186:2: RightCurlyBracket
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
    // InternalN4MFParser.g:3196:1: rule__ProjectDescription__Group_18__0 : rule__ProjectDescription__Group_18__0__Impl rule__ProjectDescription__Group_18__1 ;
    public final void rule__ProjectDescription__Group_18__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3200:1: ( rule__ProjectDescription__Group_18__0__Impl rule__ProjectDescription__Group_18__1 )
            // InternalN4MFParser.g:3201:2: rule__ProjectDescription__Group_18__0__Impl rule__ProjectDescription__Group_18__1
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
    // InternalN4MFParser.g:3208:1: rule__ProjectDescription__Group_18__0__Impl : ( ModuleFilters ) ;
    public final void rule__ProjectDescription__Group_18__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3212:1: ( ( ModuleFilters ) )
            // InternalN4MFParser.g:3213:1: ( ModuleFilters )
            {
            // InternalN4MFParser.g:3213:1: ( ModuleFilters )
            // InternalN4MFParser.g:3214:2: ModuleFilters
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
    // InternalN4MFParser.g:3223:1: rule__ProjectDescription__Group_18__1 : rule__ProjectDescription__Group_18__1__Impl rule__ProjectDescription__Group_18__2 ;
    public final void rule__ProjectDescription__Group_18__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3227:1: ( rule__ProjectDescription__Group_18__1__Impl rule__ProjectDescription__Group_18__2 )
            // InternalN4MFParser.g:3228:2: rule__ProjectDescription__Group_18__1__Impl rule__ProjectDescription__Group_18__2
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
    // InternalN4MFParser.g:3235:1: rule__ProjectDescription__Group_18__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_18__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3239:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3240:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3240:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3241:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:3250:1: rule__ProjectDescription__Group_18__2 : rule__ProjectDescription__Group_18__2__Impl rule__ProjectDescription__Group_18__3 ;
    public final void rule__ProjectDescription__Group_18__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3254:1: ( rule__ProjectDescription__Group_18__2__Impl rule__ProjectDescription__Group_18__3 )
            // InternalN4MFParser.g:3255:2: rule__ProjectDescription__Group_18__2__Impl rule__ProjectDescription__Group_18__3
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
    // InternalN4MFParser.g:3262:1: rule__ProjectDescription__Group_18__2__Impl : ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) ) ;
    public final void rule__ProjectDescription__Group_18__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3266:1: ( ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) ) )
            // InternalN4MFParser.g:3267:1: ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) )
            {
            // InternalN4MFParser.g:3267:1: ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) )
            // InternalN4MFParser.g:3268:2: ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* )
            {
            // InternalN4MFParser.g:3268:2: ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) )
            // InternalN4MFParser.g:3269:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 
            // InternalN4MFParser.g:3270:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )
            // InternalN4MFParser.g:3270:4: rule__ProjectDescription__ModuleFiltersAssignment_18_2
            {
            pushFollow(FOLLOW_18);
            rule__ProjectDescription__ModuleFiltersAssignment_18_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 

            }

            // InternalN4MFParser.g:3273:2: ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* )
            // InternalN4MFParser.g:3274:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 
            // InternalN4MFParser.g:3275:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==NoModuleWrap||LA25_0==NoValidate) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalN4MFParser.g:3275:4: rule__ProjectDescription__ModuleFiltersAssignment_18_2
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
    // InternalN4MFParser.g:3284:1: rule__ProjectDescription__Group_18__3 : rule__ProjectDescription__Group_18__3__Impl ;
    public final void rule__ProjectDescription__Group_18__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3288:1: ( rule__ProjectDescription__Group_18__3__Impl )
            // InternalN4MFParser.g:3289:2: rule__ProjectDescription__Group_18__3__Impl
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
    // InternalN4MFParser.g:3295:1: rule__ProjectDescription__Group_18__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_18__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3299:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3300:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3300:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3301:2: RightCurlyBracket
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
    // InternalN4MFParser.g:3311:1: rule__ProjectDescription__Group_19__0 : rule__ProjectDescription__Group_19__0__Impl rule__ProjectDescription__Group_19__1 ;
    public final void rule__ProjectDescription__Group_19__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3315:1: ( rule__ProjectDescription__Group_19__0__Impl rule__ProjectDescription__Group_19__1 )
            // InternalN4MFParser.g:3316:2: rule__ProjectDescription__Group_19__0__Impl rule__ProjectDescription__Group_19__1
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
    // InternalN4MFParser.g:3323:1: rule__ProjectDescription__Group_19__0__Impl : ( TestedProjects ) ;
    public final void rule__ProjectDescription__Group_19__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3327:1: ( ( TestedProjects ) )
            // InternalN4MFParser.g:3328:1: ( TestedProjects )
            {
            // InternalN4MFParser.g:3328:1: ( TestedProjects )
            // InternalN4MFParser.g:3329:2: TestedProjects
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
    // InternalN4MFParser.g:3338:1: rule__ProjectDescription__Group_19__1 : rule__ProjectDescription__Group_19__1__Impl rule__ProjectDescription__Group_19__2 ;
    public final void rule__ProjectDescription__Group_19__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3342:1: ( rule__ProjectDescription__Group_19__1__Impl rule__ProjectDescription__Group_19__2 )
            // InternalN4MFParser.g:3343:2: rule__ProjectDescription__Group_19__1__Impl rule__ProjectDescription__Group_19__2
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
    // InternalN4MFParser.g:3350:1: rule__ProjectDescription__Group_19__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_19__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3354:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3355:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3355:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3356:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:3365:1: rule__ProjectDescription__Group_19__2 : rule__ProjectDescription__Group_19__2__Impl rule__ProjectDescription__Group_19__3 ;
    public final void rule__ProjectDescription__Group_19__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3369:1: ( rule__ProjectDescription__Group_19__2__Impl rule__ProjectDescription__Group_19__3 )
            // InternalN4MFParser.g:3370:2: rule__ProjectDescription__Group_19__2__Impl rule__ProjectDescription__Group_19__3
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
    // InternalN4MFParser.g:3377:1: rule__ProjectDescription__Group_19__2__Impl : ( ( rule__ProjectDescription__Group_19_2__0 )? ) ;
    public final void rule__ProjectDescription__Group_19__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3381:1: ( ( ( rule__ProjectDescription__Group_19_2__0 )? ) )
            // InternalN4MFParser.g:3382:1: ( ( rule__ProjectDescription__Group_19_2__0 )? )
            {
            // InternalN4MFParser.g:3382:1: ( ( rule__ProjectDescription__Group_19_2__0 )? )
            // InternalN4MFParser.g:3383:2: ( rule__ProjectDescription__Group_19_2__0 )?
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_19_2()); 
            // InternalN4MFParser.g:3384:2: ( rule__ProjectDescription__Group_19_2__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==ProjectDependencies||LA26_0==ProjectVersion||LA26_0==ModuleFilters||(LA26_0>=ProjectType && LA26_0<=Application)||LA26_0==VendorName||(LA26_0>=Libraries && LA26_0<=VendorId)||LA26_0==Sources||LA26_0==Content||LA26_0==Output||(LA26_0>=Test && LA26_0<=API)||LA26_0==RULE_ID) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalN4MFParser.g:3384:3: rule__ProjectDescription__Group_19_2__0
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
    // InternalN4MFParser.g:3392:1: rule__ProjectDescription__Group_19__3 : rule__ProjectDescription__Group_19__3__Impl ;
    public final void rule__ProjectDescription__Group_19__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3396:1: ( rule__ProjectDescription__Group_19__3__Impl )
            // InternalN4MFParser.g:3397:2: rule__ProjectDescription__Group_19__3__Impl
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
    // InternalN4MFParser.g:3403:1: rule__ProjectDescription__Group_19__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_19__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3407:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3408:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3408:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3409:2: RightCurlyBracket
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
    // InternalN4MFParser.g:3419:1: rule__ProjectDescription__Group_19_2__0 : rule__ProjectDescription__Group_19_2__0__Impl rule__ProjectDescription__Group_19_2__1 ;
    public final void rule__ProjectDescription__Group_19_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3423:1: ( rule__ProjectDescription__Group_19_2__0__Impl rule__ProjectDescription__Group_19_2__1 )
            // InternalN4MFParser.g:3424:2: rule__ProjectDescription__Group_19_2__0__Impl rule__ProjectDescription__Group_19_2__1
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
    // InternalN4MFParser.g:3431:1: rule__ProjectDescription__Group_19_2__0__Impl : ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 ) ) ;
    public final void rule__ProjectDescription__Group_19_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3435:1: ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 ) ) )
            // InternalN4MFParser.g:3436:1: ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 ) )
            {
            // InternalN4MFParser.g:3436:1: ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 ) )
            // InternalN4MFParser.g:3437:2: ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19_2_0()); 
            // InternalN4MFParser.g:3438:2: ( rule__ProjectDescription__TestedProjectsAssignment_19_2_0 )
            // InternalN4MFParser.g:3438:3: rule__ProjectDescription__TestedProjectsAssignment_19_2_0
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
    // InternalN4MFParser.g:3446:1: rule__ProjectDescription__Group_19_2__1 : rule__ProjectDescription__Group_19_2__1__Impl ;
    public final void rule__ProjectDescription__Group_19_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3450:1: ( rule__ProjectDescription__Group_19_2__1__Impl )
            // InternalN4MFParser.g:3451:2: rule__ProjectDescription__Group_19_2__1__Impl
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
    // InternalN4MFParser.g:3457:1: rule__ProjectDescription__Group_19_2__1__Impl : ( ( rule__ProjectDescription__Group_19_2_1__0 )* ) ;
    public final void rule__ProjectDescription__Group_19_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3461:1: ( ( ( rule__ProjectDescription__Group_19_2_1__0 )* ) )
            // InternalN4MFParser.g:3462:1: ( ( rule__ProjectDescription__Group_19_2_1__0 )* )
            {
            // InternalN4MFParser.g:3462:1: ( ( rule__ProjectDescription__Group_19_2_1__0 )* )
            // InternalN4MFParser.g:3463:2: ( rule__ProjectDescription__Group_19_2_1__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_19_2_1()); 
            // InternalN4MFParser.g:3464:2: ( rule__ProjectDescription__Group_19_2_1__0 )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==Comma) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalN4MFParser.g:3464:3: rule__ProjectDescription__Group_19_2_1__0
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
    // InternalN4MFParser.g:3473:1: rule__ProjectDescription__Group_19_2_1__0 : rule__ProjectDescription__Group_19_2_1__0__Impl rule__ProjectDescription__Group_19_2_1__1 ;
    public final void rule__ProjectDescription__Group_19_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3477:1: ( rule__ProjectDescription__Group_19_2_1__0__Impl rule__ProjectDescription__Group_19_2_1__1 )
            // InternalN4MFParser.g:3478:2: rule__ProjectDescription__Group_19_2_1__0__Impl rule__ProjectDescription__Group_19_2_1__1
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
    // InternalN4MFParser.g:3485:1: rule__ProjectDescription__Group_19_2_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_19_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3489:1: ( ( Comma ) )
            // InternalN4MFParser.g:3490:1: ( Comma )
            {
            // InternalN4MFParser.g:3490:1: ( Comma )
            // InternalN4MFParser.g:3491:2: Comma
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
    // InternalN4MFParser.g:3500:1: rule__ProjectDescription__Group_19_2_1__1 : rule__ProjectDescription__Group_19_2_1__1__Impl ;
    public final void rule__ProjectDescription__Group_19_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3504:1: ( rule__ProjectDescription__Group_19_2_1__1__Impl )
            // InternalN4MFParser.g:3505:2: rule__ProjectDescription__Group_19_2_1__1__Impl
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
    // InternalN4MFParser.g:3511:1: rule__ProjectDescription__Group_19_2_1__1__Impl : ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 ) ) ;
    public final void rule__ProjectDescription__Group_19_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3515:1: ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 ) ) )
            // InternalN4MFParser.g:3516:1: ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 ) )
            {
            // InternalN4MFParser.g:3516:1: ( ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 ) )
            // InternalN4MFParser.g:3517:2: ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19_2_1_1()); 
            // InternalN4MFParser.g:3518:2: ( rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 )
            // InternalN4MFParser.g:3518:3: rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1
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
    // InternalN4MFParser.g:3527:1: rule__ProjectDescription__Group_20__0 : rule__ProjectDescription__Group_20__0__Impl rule__ProjectDescription__Group_20__1 ;
    public final void rule__ProjectDescription__Group_20__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3531:1: ( rule__ProjectDescription__Group_20__0__Impl rule__ProjectDescription__Group_20__1 )
            // InternalN4MFParser.g:3532:2: rule__ProjectDescription__Group_20__0__Impl rule__ProjectDescription__Group_20__1
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
    // InternalN4MFParser.g:3539:1: rule__ProjectDescription__Group_20__0__Impl : ( ModuleLoader ) ;
    public final void rule__ProjectDescription__Group_20__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3543:1: ( ( ModuleLoader ) )
            // InternalN4MFParser.g:3544:1: ( ModuleLoader )
            {
            // InternalN4MFParser.g:3544:1: ( ModuleLoader )
            // InternalN4MFParser.g:3545:2: ModuleLoader
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
    // InternalN4MFParser.g:3554:1: rule__ProjectDescription__Group_20__1 : rule__ProjectDescription__Group_20__1__Impl rule__ProjectDescription__Group_20__2 ;
    public final void rule__ProjectDescription__Group_20__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3558:1: ( rule__ProjectDescription__Group_20__1__Impl rule__ProjectDescription__Group_20__2 )
            // InternalN4MFParser.g:3559:2: rule__ProjectDescription__Group_20__1__Impl rule__ProjectDescription__Group_20__2
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
    // InternalN4MFParser.g:3566:1: rule__ProjectDescription__Group_20__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_20__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3570:1: ( ( Colon ) )
            // InternalN4MFParser.g:3571:1: ( Colon )
            {
            // InternalN4MFParser.g:3571:1: ( Colon )
            // InternalN4MFParser.g:3572:2: Colon
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
    // InternalN4MFParser.g:3581:1: rule__ProjectDescription__Group_20__2 : rule__ProjectDescription__Group_20__2__Impl ;
    public final void rule__ProjectDescription__Group_20__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3585:1: ( rule__ProjectDescription__Group_20__2__Impl )
            // InternalN4MFParser.g:3586:2: rule__ProjectDescription__Group_20__2__Impl
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
    // InternalN4MFParser.g:3592:1: rule__ProjectDescription__Group_20__2__Impl : ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) ) ;
    public final void rule__ProjectDescription__Group_20__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3596:1: ( ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) ) )
            // InternalN4MFParser.g:3597:1: ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) )
            {
            // InternalN4MFParser.g:3597:1: ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) )
            // InternalN4MFParser.g:3598:2: ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleLoaderAssignment_20_2()); 
            // InternalN4MFParser.g:3599:2: ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 )
            // InternalN4MFParser.g:3599:3: rule__ProjectDescription__ModuleLoaderAssignment_20_2
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
    // InternalN4MFParser.g:3608:1: rule__DeclaredVersion__Group__0 : rule__DeclaredVersion__Group__0__Impl rule__DeclaredVersion__Group__1 ;
    public final void rule__DeclaredVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3612:1: ( rule__DeclaredVersion__Group__0__Impl rule__DeclaredVersion__Group__1 )
            // InternalN4MFParser.g:3613:2: rule__DeclaredVersion__Group__0__Impl rule__DeclaredVersion__Group__1
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
    // InternalN4MFParser.g:3620:1: rule__DeclaredVersion__Group__0__Impl : ( ( rule__DeclaredVersion__MajorAssignment_0 ) ) ;
    public final void rule__DeclaredVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3624:1: ( ( ( rule__DeclaredVersion__MajorAssignment_0 ) ) )
            // InternalN4MFParser.g:3625:1: ( ( rule__DeclaredVersion__MajorAssignment_0 ) )
            {
            // InternalN4MFParser.g:3625:1: ( ( rule__DeclaredVersion__MajorAssignment_0 ) )
            // InternalN4MFParser.g:3626:2: ( rule__DeclaredVersion__MajorAssignment_0 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getMajorAssignment_0()); 
            // InternalN4MFParser.g:3627:2: ( rule__DeclaredVersion__MajorAssignment_0 )
            // InternalN4MFParser.g:3627:3: rule__DeclaredVersion__MajorAssignment_0
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
    // InternalN4MFParser.g:3635:1: rule__DeclaredVersion__Group__1 : rule__DeclaredVersion__Group__1__Impl rule__DeclaredVersion__Group__2 ;
    public final void rule__DeclaredVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3639:1: ( rule__DeclaredVersion__Group__1__Impl rule__DeclaredVersion__Group__2 )
            // InternalN4MFParser.g:3640:2: rule__DeclaredVersion__Group__1__Impl rule__DeclaredVersion__Group__2
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
    // InternalN4MFParser.g:3647:1: rule__DeclaredVersion__Group__1__Impl : ( ( rule__DeclaredVersion__Group_1__0 )? ) ;
    public final void rule__DeclaredVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3651:1: ( ( ( rule__DeclaredVersion__Group_1__0 )? ) )
            // InternalN4MFParser.g:3652:1: ( ( rule__DeclaredVersion__Group_1__0 )? )
            {
            // InternalN4MFParser.g:3652:1: ( ( rule__DeclaredVersion__Group_1__0 )? )
            // InternalN4MFParser.g:3653:2: ( rule__DeclaredVersion__Group_1__0 )?
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup_1()); 
            // InternalN4MFParser.g:3654:2: ( rule__DeclaredVersion__Group_1__0 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==FullStop) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalN4MFParser.g:3654:3: rule__DeclaredVersion__Group_1__0
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
    // InternalN4MFParser.g:3662:1: rule__DeclaredVersion__Group__2 : rule__DeclaredVersion__Group__2__Impl ;
    public final void rule__DeclaredVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3666:1: ( rule__DeclaredVersion__Group__2__Impl )
            // InternalN4MFParser.g:3667:2: rule__DeclaredVersion__Group__2__Impl
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
    // InternalN4MFParser.g:3673:1: rule__DeclaredVersion__Group__2__Impl : ( ( rule__DeclaredVersion__Group_2__0 )? ) ;
    public final void rule__DeclaredVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3677:1: ( ( ( rule__DeclaredVersion__Group_2__0 )? ) )
            // InternalN4MFParser.g:3678:1: ( ( rule__DeclaredVersion__Group_2__0 )? )
            {
            // InternalN4MFParser.g:3678:1: ( ( rule__DeclaredVersion__Group_2__0 )? )
            // InternalN4MFParser.g:3679:2: ( rule__DeclaredVersion__Group_2__0 )?
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup_2()); 
            // InternalN4MFParser.g:3680:2: ( rule__DeclaredVersion__Group_2__0 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==HyphenMinus) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalN4MFParser.g:3680:3: rule__DeclaredVersion__Group_2__0
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
    // InternalN4MFParser.g:3689:1: rule__DeclaredVersion__Group_1__0 : rule__DeclaredVersion__Group_1__0__Impl rule__DeclaredVersion__Group_1__1 ;
    public final void rule__DeclaredVersion__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3693:1: ( rule__DeclaredVersion__Group_1__0__Impl rule__DeclaredVersion__Group_1__1 )
            // InternalN4MFParser.g:3694:2: rule__DeclaredVersion__Group_1__0__Impl rule__DeclaredVersion__Group_1__1
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
    // InternalN4MFParser.g:3701:1: rule__DeclaredVersion__Group_1__0__Impl : ( FullStop ) ;
    public final void rule__DeclaredVersion__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3705:1: ( ( FullStop ) )
            // InternalN4MFParser.g:3706:1: ( FullStop )
            {
            // InternalN4MFParser.g:3706:1: ( FullStop )
            // InternalN4MFParser.g:3707:2: FullStop
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
    // InternalN4MFParser.g:3716:1: rule__DeclaredVersion__Group_1__1 : rule__DeclaredVersion__Group_1__1__Impl rule__DeclaredVersion__Group_1__2 ;
    public final void rule__DeclaredVersion__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3720:1: ( rule__DeclaredVersion__Group_1__1__Impl rule__DeclaredVersion__Group_1__2 )
            // InternalN4MFParser.g:3721:2: rule__DeclaredVersion__Group_1__1__Impl rule__DeclaredVersion__Group_1__2
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
    // InternalN4MFParser.g:3728:1: rule__DeclaredVersion__Group_1__1__Impl : ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) ) ;
    public final void rule__DeclaredVersion__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3732:1: ( ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) ) )
            // InternalN4MFParser.g:3733:1: ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) )
            {
            // InternalN4MFParser.g:3733:1: ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) )
            // InternalN4MFParser.g:3734:2: ( rule__DeclaredVersion__MinorAssignment_1_1 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getMinorAssignment_1_1()); 
            // InternalN4MFParser.g:3735:2: ( rule__DeclaredVersion__MinorAssignment_1_1 )
            // InternalN4MFParser.g:3735:3: rule__DeclaredVersion__MinorAssignment_1_1
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
    // InternalN4MFParser.g:3743:1: rule__DeclaredVersion__Group_1__2 : rule__DeclaredVersion__Group_1__2__Impl ;
    public final void rule__DeclaredVersion__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3747:1: ( rule__DeclaredVersion__Group_1__2__Impl )
            // InternalN4MFParser.g:3748:2: rule__DeclaredVersion__Group_1__2__Impl
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
    // InternalN4MFParser.g:3754:1: rule__DeclaredVersion__Group_1__2__Impl : ( ( rule__DeclaredVersion__Group_1_2__0 )? ) ;
    public final void rule__DeclaredVersion__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3758:1: ( ( ( rule__DeclaredVersion__Group_1_2__0 )? ) )
            // InternalN4MFParser.g:3759:1: ( ( rule__DeclaredVersion__Group_1_2__0 )? )
            {
            // InternalN4MFParser.g:3759:1: ( ( rule__DeclaredVersion__Group_1_2__0 )? )
            // InternalN4MFParser.g:3760:2: ( rule__DeclaredVersion__Group_1_2__0 )?
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup_1_2()); 
            // InternalN4MFParser.g:3761:2: ( rule__DeclaredVersion__Group_1_2__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==FullStop) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalN4MFParser.g:3761:3: rule__DeclaredVersion__Group_1_2__0
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
    // InternalN4MFParser.g:3770:1: rule__DeclaredVersion__Group_1_2__0 : rule__DeclaredVersion__Group_1_2__0__Impl rule__DeclaredVersion__Group_1_2__1 ;
    public final void rule__DeclaredVersion__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3774:1: ( rule__DeclaredVersion__Group_1_2__0__Impl rule__DeclaredVersion__Group_1_2__1 )
            // InternalN4MFParser.g:3775:2: rule__DeclaredVersion__Group_1_2__0__Impl rule__DeclaredVersion__Group_1_2__1
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
    // InternalN4MFParser.g:3782:1: rule__DeclaredVersion__Group_1_2__0__Impl : ( FullStop ) ;
    public final void rule__DeclaredVersion__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3786:1: ( ( FullStop ) )
            // InternalN4MFParser.g:3787:1: ( FullStop )
            {
            // InternalN4MFParser.g:3787:1: ( FullStop )
            // InternalN4MFParser.g:3788:2: FullStop
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
    // InternalN4MFParser.g:3797:1: rule__DeclaredVersion__Group_1_2__1 : rule__DeclaredVersion__Group_1_2__1__Impl ;
    public final void rule__DeclaredVersion__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3801:1: ( rule__DeclaredVersion__Group_1_2__1__Impl )
            // InternalN4MFParser.g:3802:2: rule__DeclaredVersion__Group_1_2__1__Impl
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
    // InternalN4MFParser.g:3808:1: rule__DeclaredVersion__Group_1_2__1__Impl : ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) ) ;
    public final void rule__DeclaredVersion__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3812:1: ( ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) ) )
            // InternalN4MFParser.g:3813:1: ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) )
            {
            // InternalN4MFParser.g:3813:1: ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) )
            // InternalN4MFParser.g:3814:2: ( rule__DeclaredVersion__MicroAssignment_1_2_1 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getMicroAssignment_1_2_1()); 
            // InternalN4MFParser.g:3815:2: ( rule__DeclaredVersion__MicroAssignment_1_2_1 )
            // InternalN4MFParser.g:3815:3: rule__DeclaredVersion__MicroAssignment_1_2_1
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
    // InternalN4MFParser.g:3824:1: rule__DeclaredVersion__Group_2__0 : rule__DeclaredVersion__Group_2__0__Impl rule__DeclaredVersion__Group_2__1 ;
    public final void rule__DeclaredVersion__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3828:1: ( rule__DeclaredVersion__Group_2__0__Impl rule__DeclaredVersion__Group_2__1 )
            // InternalN4MFParser.g:3829:2: rule__DeclaredVersion__Group_2__0__Impl rule__DeclaredVersion__Group_2__1
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
    // InternalN4MFParser.g:3836:1: rule__DeclaredVersion__Group_2__0__Impl : ( HyphenMinus ) ;
    public final void rule__DeclaredVersion__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3840:1: ( ( HyphenMinus ) )
            // InternalN4MFParser.g:3841:1: ( HyphenMinus )
            {
            // InternalN4MFParser.g:3841:1: ( HyphenMinus )
            // InternalN4MFParser.g:3842:2: HyphenMinus
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
    // InternalN4MFParser.g:3851:1: rule__DeclaredVersion__Group_2__1 : rule__DeclaredVersion__Group_2__1__Impl ;
    public final void rule__DeclaredVersion__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3855:1: ( rule__DeclaredVersion__Group_2__1__Impl )
            // InternalN4MFParser.g:3856:2: rule__DeclaredVersion__Group_2__1__Impl
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
    // InternalN4MFParser.g:3862:1: rule__DeclaredVersion__Group_2__1__Impl : ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) ) ;
    public final void rule__DeclaredVersion__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3866:1: ( ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) ) )
            // InternalN4MFParser.g:3867:1: ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) )
            {
            // InternalN4MFParser.g:3867:1: ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) )
            // InternalN4MFParser.g:3868:2: ( rule__DeclaredVersion__QualifierAssignment_2_1 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getQualifierAssignment_2_1()); 
            // InternalN4MFParser.g:3869:2: ( rule__DeclaredVersion__QualifierAssignment_2_1 )
            // InternalN4MFParser.g:3869:3: rule__DeclaredVersion__QualifierAssignment_2_1
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
    // InternalN4MFParser.g:3878:1: rule__SourceFragment__Group__0 : rule__SourceFragment__Group__0__Impl rule__SourceFragment__Group__1 ;
    public final void rule__SourceFragment__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3882:1: ( rule__SourceFragment__Group__0__Impl rule__SourceFragment__Group__1 )
            // InternalN4MFParser.g:3883:2: rule__SourceFragment__Group__0__Impl rule__SourceFragment__Group__1
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
    // InternalN4MFParser.g:3890:1: rule__SourceFragment__Group__0__Impl : ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) ) ;
    public final void rule__SourceFragment__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3894:1: ( ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) ) )
            // InternalN4MFParser.g:3895:1: ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) )
            {
            // InternalN4MFParser.g:3895:1: ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) )
            // InternalN4MFParser.g:3896:2: ( rule__SourceFragment__SourceFragmentTypeAssignment_0 )
            {
             before(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeAssignment_0()); 
            // InternalN4MFParser.g:3897:2: ( rule__SourceFragment__SourceFragmentTypeAssignment_0 )
            // InternalN4MFParser.g:3897:3: rule__SourceFragment__SourceFragmentTypeAssignment_0
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
    // InternalN4MFParser.g:3905:1: rule__SourceFragment__Group__1 : rule__SourceFragment__Group__1__Impl rule__SourceFragment__Group__2 ;
    public final void rule__SourceFragment__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3909:1: ( rule__SourceFragment__Group__1__Impl rule__SourceFragment__Group__2 )
            // InternalN4MFParser.g:3910:2: rule__SourceFragment__Group__1__Impl rule__SourceFragment__Group__2
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
    // InternalN4MFParser.g:3917:1: rule__SourceFragment__Group__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__SourceFragment__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3921:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3922:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3922:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3923:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:3932:1: rule__SourceFragment__Group__2 : rule__SourceFragment__Group__2__Impl rule__SourceFragment__Group__3 ;
    public final void rule__SourceFragment__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3936:1: ( rule__SourceFragment__Group__2__Impl rule__SourceFragment__Group__3 )
            // InternalN4MFParser.g:3937:2: rule__SourceFragment__Group__2__Impl rule__SourceFragment__Group__3
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
    // InternalN4MFParser.g:3944:1: rule__SourceFragment__Group__2__Impl : ( ( rule__SourceFragment__PathsRawAssignment_2 ) ) ;
    public final void rule__SourceFragment__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3948:1: ( ( ( rule__SourceFragment__PathsRawAssignment_2 ) ) )
            // InternalN4MFParser.g:3949:1: ( ( rule__SourceFragment__PathsRawAssignment_2 ) )
            {
            // InternalN4MFParser.g:3949:1: ( ( rule__SourceFragment__PathsRawAssignment_2 ) )
            // InternalN4MFParser.g:3950:2: ( rule__SourceFragment__PathsRawAssignment_2 )
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_2()); 
            // InternalN4MFParser.g:3951:2: ( rule__SourceFragment__PathsRawAssignment_2 )
            // InternalN4MFParser.g:3951:3: rule__SourceFragment__PathsRawAssignment_2
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
    // InternalN4MFParser.g:3959:1: rule__SourceFragment__Group__3 : rule__SourceFragment__Group__3__Impl rule__SourceFragment__Group__4 ;
    public final void rule__SourceFragment__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3963:1: ( rule__SourceFragment__Group__3__Impl rule__SourceFragment__Group__4 )
            // InternalN4MFParser.g:3964:2: rule__SourceFragment__Group__3__Impl rule__SourceFragment__Group__4
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
    // InternalN4MFParser.g:3971:1: rule__SourceFragment__Group__3__Impl : ( ( rule__SourceFragment__Group_3__0 )* ) ;
    public final void rule__SourceFragment__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3975:1: ( ( ( rule__SourceFragment__Group_3__0 )* ) )
            // InternalN4MFParser.g:3976:1: ( ( rule__SourceFragment__Group_3__0 )* )
            {
            // InternalN4MFParser.g:3976:1: ( ( rule__SourceFragment__Group_3__0 )* )
            // InternalN4MFParser.g:3977:2: ( rule__SourceFragment__Group_3__0 )*
            {
             before(grammarAccess.getSourceFragmentAccess().getGroup_3()); 
            // InternalN4MFParser.g:3978:2: ( rule__SourceFragment__Group_3__0 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==Comma) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalN4MFParser.g:3978:3: rule__SourceFragment__Group_3__0
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
    // InternalN4MFParser.g:3986:1: rule__SourceFragment__Group__4 : rule__SourceFragment__Group__4__Impl ;
    public final void rule__SourceFragment__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3990:1: ( rule__SourceFragment__Group__4__Impl )
            // InternalN4MFParser.g:3991:2: rule__SourceFragment__Group__4__Impl
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
    // InternalN4MFParser.g:3997:1: rule__SourceFragment__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__SourceFragment__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4001:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:4002:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:4002:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:4003:2: RightCurlyBracket
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
    // InternalN4MFParser.g:4013:1: rule__SourceFragment__Group_3__0 : rule__SourceFragment__Group_3__0__Impl rule__SourceFragment__Group_3__1 ;
    public final void rule__SourceFragment__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4017:1: ( rule__SourceFragment__Group_3__0__Impl rule__SourceFragment__Group_3__1 )
            // InternalN4MFParser.g:4018:2: rule__SourceFragment__Group_3__0__Impl rule__SourceFragment__Group_3__1
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
    // InternalN4MFParser.g:4025:1: rule__SourceFragment__Group_3__0__Impl : ( Comma ) ;
    public final void rule__SourceFragment__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4029:1: ( ( Comma ) )
            // InternalN4MFParser.g:4030:1: ( Comma )
            {
            // InternalN4MFParser.g:4030:1: ( Comma )
            // InternalN4MFParser.g:4031:2: Comma
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
    // InternalN4MFParser.g:4040:1: rule__SourceFragment__Group_3__1 : rule__SourceFragment__Group_3__1__Impl ;
    public final void rule__SourceFragment__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4044:1: ( rule__SourceFragment__Group_3__1__Impl )
            // InternalN4MFParser.g:4045:2: rule__SourceFragment__Group_3__1__Impl
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
    // InternalN4MFParser.g:4051:1: rule__SourceFragment__Group_3__1__Impl : ( ( rule__SourceFragment__PathsRawAssignment_3_1 ) ) ;
    public final void rule__SourceFragment__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4055:1: ( ( ( rule__SourceFragment__PathsRawAssignment_3_1 ) ) )
            // InternalN4MFParser.g:4056:1: ( ( rule__SourceFragment__PathsRawAssignment_3_1 ) )
            {
            // InternalN4MFParser.g:4056:1: ( ( rule__SourceFragment__PathsRawAssignment_3_1 ) )
            // InternalN4MFParser.g:4057:2: ( rule__SourceFragment__PathsRawAssignment_3_1 )
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsRawAssignment_3_1()); 
            // InternalN4MFParser.g:4058:2: ( rule__SourceFragment__PathsRawAssignment_3_1 )
            // InternalN4MFParser.g:4058:3: rule__SourceFragment__PathsRawAssignment_3_1
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
    // InternalN4MFParser.g:4067:1: rule__ModuleFilter__Group__0 : rule__ModuleFilter__Group__0__Impl rule__ModuleFilter__Group__1 ;
    public final void rule__ModuleFilter__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4071:1: ( rule__ModuleFilter__Group__0__Impl rule__ModuleFilter__Group__1 )
            // InternalN4MFParser.g:4072:2: rule__ModuleFilter__Group__0__Impl rule__ModuleFilter__Group__1
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
    // InternalN4MFParser.g:4079:1: rule__ModuleFilter__Group__0__Impl : ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) ) ;
    public final void rule__ModuleFilter__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4083:1: ( ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) ) )
            // InternalN4MFParser.g:4084:1: ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) )
            {
            // InternalN4MFParser.g:4084:1: ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) )
            // InternalN4MFParser.g:4085:2: ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 )
            {
             before(grammarAccess.getModuleFilterAccess().getModuleFilterTypeAssignment_0()); 
            // InternalN4MFParser.g:4086:2: ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 )
            // InternalN4MFParser.g:4086:3: rule__ModuleFilter__ModuleFilterTypeAssignment_0
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
    // InternalN4MFParser.g:4094:1: rule__ModuleFilter__Group__1 : rule__ModuleFilter__Group__1__Impl rule__ModuleFilter__Group__2 ;
    public final void rule__ModuleFilter__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4098:1: ( rule__ModuleFilter__Group__1__Impl rule__ModuleFilter__Group__2 )
            // InternalN4MFParser.g:4099:2: rule__ModuleFilter__Group__1__Impl rule__ModuleFilter__Group__2
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
    // InternalN4MFParser.g:4106:1: rule__ModuleFilter__Group__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ModuleFilter__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4110:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:4111:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:4111:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:4112:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:4121:1: rule__ModuleFilter__Group__2 : rule__ModuleFilter__Group__2__Impl rule__ModuleFilter__Group__3 ;
    public final void rule__ModuleFilter__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4125:1: ( rule__ModuleFilter__Group__2__Impl rule__ModuleFilter__Group__3 )
            // InternalN4MFParser.g:4126:2: rule__ModuleFilter__Group__2__Impl rule__ModuleFilter__Group__3
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
    // InternalN4MFParser.g:4133:1: rule__ModuleFilter__Group__2__Impl : ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) ) ;
    public final void rule__ModuleFilter__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4137:1: ( ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) ) )
            // InternalN4MFParser.g:4138:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) )
            {
            // InternalN4MFParser.g:4138:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) )
            // InternalN4MFParser.g:4139:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 )
            {
             before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_2()); 
            // InternalN4MFParser.g:4140:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 )
            // InternalN4MFParser.g:4140:3: rule__ModuleFilter__ModuleSpecifiersAssignment_2
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
    // InternalN4MFParser.g:4148:1: rule__ModuleFilter__Group__3 : rule__ModuleFilter__Group__3__Impl rule__ModuleFilter__Group__4 ;
    public final void rule__ModuleFilter__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4152:1: ( rule__ModuleFilter__Group__3__Impl rule__ModuleFilter__Group__4 )
            // InternalN4MFParser.g:4153:2: rule__ModuleFilter__Group__3__Impl rule__ModuleFilter__Group__4
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
    // InternalN4MFParser.g:4160:1: rule__ModuleFilter__Group__3__Impl : ( ( rule__ModuleFilter__Group_3__0 )* ) ;
    public final void rule__ModuleFilter__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4164:1: ( ( ( rule__ModuleFilter__Group_3__0 )* ) )
            // InternalN4MFParser.g:4165:1: ( ( rule__ModuleFilter__Group_3__0 )* )
            {
            // InternalN4MFParser.g:4165:1: ( ( rule__ModuleFilter__Group_3__0 )* )
            // InternalN4MFParser.g:4166:2: ( rule__ModuleFilter__Group_3__0 )*
            {
             before(grammarAccess.getModuleFilterAccess().getGroup_3()); 
            // InternalN4MFParser.g:4167:2: ( rule__ModuleFilter__Group_3__0 )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==Comma) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalN4MFParser.g:4167:3: rule__ModuleFilter__Group_3__0
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
    // InternalN4MFParser.g:4175:1: rule__ModuleFilter__Group__4 : rule__ModuleFilter__Group__4__Impl ;
    public final void rule__ModuleFilter__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4179:1: ( rule__ModuleFilter__Group__4__Impl )
            // InternalN4MFParser.g:4180:2: rule__ModuleFilter__Group__4__Impl
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
    // InternalN4MFParser.g:4186:1: rule__ModuleFilter__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ModuleFilter__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4190:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:4191:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:4191:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:4192:2: RightCurlyBracket
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
    // InternalN4MFParser.g:4202:1: rule__ModuleFilter__Group_3__0 : rule__ModuleFilter__Group_3__0__Impl rule__ModuleFilter__Group_3__1 ;
    public final void rule__ModuleFilter__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4206:1: ( rule__ModuleFilter__Group_3__0__Impl rule__ModuleFilter__Group_3__1 )
            // InternalN4MFParser.g:4207:2: rule__ModuleFilter__Group_3__0__Impl rule__ModuleFilter__Group_3__1
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
    // InternalN4MFParser.g:4214:1: rule__ModuleFilter__Group_3__0__Impl : ( Comma ) ;
    public final void rule__ModuleFilter__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4218:1: ( ( Comma ) )
            // InternalN4MFParser.g:4219:1: ( Comma )
            {
            // InternalN4MFParser.g:4219:1: ( Comma )
            // InternalN4MFParser.g:4220:2: Comma
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
    // InternalN4MFParser.g:4229:1: rule__ModuleFilter__Group_3__1 : rule__ModuleFilter__Group_3__1__Impl ;
    public final void rule__ModuleFilter__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4233:1: ( rule__ModuleFilter__Group_3__1__Impl )
            // InternalN4MFParser.g:4234:2: rule__ModuleFilter__Group_3__1__Impl
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
    // InternalN4MFParser.g:4240:1: rule__ModuleFilter__Group_3__1__Impl : ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) ) ;
    public final void rule__ModuleFilter__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4244:1: ( ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) ) )
            // InternalN4MFParser.g:4245:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) )
            {
            // InternalN4MFParser.g:4245:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) )
            // InternalN4MFParser.g:4246:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 )
            {
             before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_3_1()); 
            // InternalN4MFParser.g:4247:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 )
            // InternalN4MFParser.g:4247:3: rule__ModuleFilter__ModuleSpecifiersAssignment_3_1
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
    // InternalN4MFParser.g:4256:1: rule__BootstrapModule__Group__0 : rule__BootstrapModule__Group__0__Impl rule__BootstrapModule__Group__1 ;
    public final void rule__BootstrapModule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4260:1: ( rule__BootstrapModule__Group__0__Impl rule__BootstrapModule__Group__1 )
            // InternalN4MFParser.g:4261:2: rule__BootstrapModule__Group__0__Impl rule__BootstrapModule__Group__1
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
    // InternalN4MFParser.g:4268:1: rule__BootstrapModule__Group__0__Impl : ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) ) ;
    public final void rule__BootstrapModule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4272:1: ( ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) ) )
            // InternalN4MFParser.g:4273:1: ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) )
            {
            // InternalN4MFParser.g:4273:1: ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) )
            // InternalN4MFParser.g:4274:2: ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 )
            {
             before(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardAssignment_0()); 
            // InternalN4MFParser.g:4275:2: ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 )
            // InternalN4MFParser.g:4275:3: rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0
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
    // InternalN4MFParser.g:4283:1: rule__BootstrapModule__Group__1 : rule__BootstrapModule__Group__1__Impl ;
    public final void rule__BootstrapModule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4287:1: ( rule__BootstrapModule__Group__1__Impl )
            // InternalN4MFParser.g:4288:2: rule__BootstrapModule__Group__1__Impl
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
    // InternalN4MFParser.g:4294:1: rule__BootstrapModule__Group__1__Impl : ( ( rule__BootstrapModule__Group_1__0 )? ) ;
    public final void rule__BootstrapModule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4298:1: ( ( ( rule__BootstrapModule__Group_1__0 )? ) )
            // InternalN4MFParser.g:4299:1: ( ( rule__BootstrapModule__Group_1__0 )? )
            {
            // InternalN4MFParser.g:4299:1: ( ( rule__BootstrapModule__Group_1__0 )? )
            // InternalN4MFParser.g:4300:2: ( rule__BootstrapModule__Group_1__0 )?
            {
             before(grammarAccess.getBootstrapModuleAccess().getGroup_1()); 
            // InternalN4MFParser.g:4301:2: ( rule__BootstrapModule__Group_1__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==In) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalN4MFParser.g:4301:3: rule__BootstrapModule__Group_1__0
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
    // InternalN4MFParser.g:4310:1: rule__BootstrapModule__Group_1__0 : rule__BootstrapModule__Group_1__0__Impl rule__BootstrapModule__Group_1__1 ;
    public final void rule__BootstrapModule__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4314:1: ( rule__BootstrapModule__Group_1__0__Impl rule__BootstrapModule__Group_1__1 )
            // InternalN4MFParser.g:4315:2: rule__BootstrapModule__Group_1__0__Impl rule__BootstrapModule__Group_1__1
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
    // InternalN4MFParser.g:4322:1: rule__BootstrapModule__Group_1__0__Impl : ( In ) ;
    public final void rule__BootstrapModule__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4326:1: ( ( In ) )
            // InternalN4MFParser.g:4327:1: ( In )
            {
            // InternalN4MFParser.g:4327:1: ( In )
            // InternalN4MFParser.g:4328:2: In
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
    // InternalN4MFParser.g:4337:1: rule__BootstrapModule__Group_1__1 : rule__BootstrapModule__Group_1__1__Impl ;
    public final void rule__BootstrapModule__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4341:1: ( rule__BootstrapModule__Group_1__1__Impl )
            // InternalN4MFParser.g:4342:2: rule__BootstrapModule__Group_1__1__Impl
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
    // InternalN4MFParser.g:4348:1: rule__BootstrapModule__Group_1__1__Impl : ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) ) ;
    public final void rule__BootstrapModule__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4352:1: ( ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) ) )
            // InternalN4MFParser.g:4353:1: ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) )
            {
            // InternalN4MFParser.g:4353:1: ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) )
            // InternalN4MFParser.g:4354:2: ( rule__BootstrapModule__SourcePathAssignment_1_1 )
            {
             before(grammarAccess.getBootstrapModuleAccess().getSourcePathAssignment_1_1()); 
            // InternalN4MFParser.g:4355:2: ( rule__BootstrapModule__SourcePathAssignment_1_1 )
            // InternalN4MFParser.g:4355:3: rule__BootstrapModule__SourcePathAssignment_1_1
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
    // InternalN4MFParser.g:4364:1: rule__ModuleFilterSpecifier__Group__0 : rule__ModuleFilterSpecifier__Group__0__Impl rule__ModuleFilterSpecifier__Group__1 ;
    public final void rule__ModuleFilterSpecifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4368:1: ( rule__ModuleFilterSpecifier__Group__0__Impl rule__ModuleFilterSpecifier__Group__1 )
            // InternalN4MFParser.g:4369:2: rule__ModuleFilterSpecifier__Group__0__Impl rule__ModuleFilterSpecifier__Group__1
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
    // InternalN4MFParser.g:4376:1: rule__ModuleFilterSpecifier__Group__0__Impl : ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) ) ;
    public final void rule__ModuleFilterSpecifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4380:1: ( ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) ) )
            // InternalN4MFParser.g:4381:1: ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) )
            {
            // InternalN4MFParser.g:4381:1: ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) )
            // InternalN4MFParser.g:4382:2: ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 )
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardAssignment_0()); 
            // InternalN4MFParser.g:4383:2: ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 )
            // InternalN4MFParser.g:4383:3: rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0
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
    // InternalN4MFParser.g:4391:1: rule__ModuleFilterSpecifier__Group__1 : rule__ModuleFilterSpecifier__Group__1__Impl ;
    public final void rule__ModuleFilterSpecifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4395:1: ( rule__ModuleFilterSpecifier__Group__1__Impl )
            // InternalN4MFParser.g:4396:2: rule__ModuleFilterSpecifier__Group__1__Impl
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
    // InternalN4MFParser.g:4402:1: rule__ModuleFilterSpecifier__Group__1__Impl : ( ( rule__ModuleFilterSpecifier__Group_1__0 )? ) ;
    public final void rule__ModuleFilterSpecifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4406:1: ( ( ( rule__ModuleFilterSpecifier__Group_1__0 )? ) )
            // InternalN4MFParser.g:4407:1: ( ( rule__ModuleFilterSpecifier__Group_1__0 )? )
            {
            // InternalN4MFParser.g:4407:1: ( ( rule__ModuleFilterSpecifier__Group_1__0 )? )
            // InternalN4MFParser.g:4408:2: ( rule__ModuleFilterSpecifier__Group_1__0 )?
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getGroup_1()); 
            // InternalN4MFParser.g:4409:2: ( rule__ModuleFilterSpecifier__Group_1__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==In) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalN4MFParser.g:4409:3: rule__ModuleFilterSpecifier__Group_1__0
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
    // InternalN4MFParser.g:4418:1: rule__ModuleFilterSpecifier__Group_1__0 : rule__ModuleFilterSpecifier__Group_1__0__Impl rule__ModuleFilterSpecifier__Group_1__1 ;
    public final void rule__ModuleFilterSpecifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4422:1: ( rule__ModuleFilterSpecifier__Group_1__0__Impl rule__ModuleFilterSpecifier__Group_1__1 )
            // InternalN4MFParser.g:4423:2: rule__ModuleFilterSpecifier__Group_1__0__Impl rule__ModuleFilterSpecifier__Group_1__1
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
    // InternalN4MFParser.g:4430:1: rule__ModuleFilterSpecifier__Group_1__0__Impl : ( In ) ;
    public final void rule__ModuleFilterSpecifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4434:1: ( ( In ) )
            // InternalN4MFParser.g:4435:1: ( In )
            {
            // InternalN4MFParser.g:4435:1: ( In )
            // InternalN4MFParser.g:4436:2: In
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
    // InternalN4MFParser.g:4445:1: rule__ModuleFilterSpecifier__Group_1__1 : rule__ModuleFilterSpecifier__Group_1__1__Impl ;
    public final void rule__ModuleFilterSpecifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4449:1: ( rule__ModuleFilterSpecifier__Group_1__1__Impl )
            // InternalN4MFParser.g:4450:2: rule__ModuleFilterSpecifier__Group_1__1__Impl
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
    // InternalN4MFParser.g:4456:1: rule__ModuleFilterSpecifier__Group_1__1__Impl : ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) ) ;
    public final void rule__ModuleFilterSpecifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4460:1: ( ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) ) )
            // InternalN4MFParser.g:4461:1: ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) )
            {
            // InternalN4MFParser.g:4461:1: ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) )
            // InternalN4MFParser.g:4462:2: ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 )
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathAssignment_1_1()); 
            // InternalN4MFParser.g:4463:2: ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 )
            // InternalN4MFParser.g:4463:3: rule__ModuleFilterSpecifier__SourcePathAssignment_1_1
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
    // InternalN4MFParser.g:4472:1: rule__ProjectDependency__Group__0 : rule__ProjectDependency__Group__0__Impl rule__ProjectDependency__Group__1 ;
    public final void rule__ProjectDependency__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4476:1: ( rule__ProjectDependency__Group__0__Impl rule__ProjectDependency__Group__1 )
            // InternalN4MFParser.g:4477:2: rule__ProjectDependency__Group__0__Impl rule__ProjectDependency__Group__1
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
    // InternalN4MFParser.g:4484:1: rule__ProjectDependency__Group__0__Impl : ( ruleProjectIdWithOptionalVendor ) ;
    public final void rule__ProjectDependency__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4488:1: ( ( ruleProjectIdWithOptionalVendor ) )
            // InternalN4MFParser.g:4489:1: ( ruleProjectIdWithOptionalVendor )
            {
            // InternalN4MFParser.g:4489:1: ( ruleProjectIdWithOptionalVendor )
            // InternalN4MFParser.g:4490:2: ruleProjectIdWithOptionalVendor
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
    // InternalN4MFParser.g:4499:1: rule__ProjectDependency__Group__1 : rule__ProjectDependency__Group__1__Impl rule__ProjectDependency__Group__2 ;
    public final void rule__ProjectDependency__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4503:1: ( rule__ProjectDependency__Group__1__Impl rule__ProjectDependency__Group__2 )
            // InternalN4MFParser.g:4504:2: rule__ProjectDependency__Group__1__Impl rule__ProjectDependency__Group__2
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
    // InternalN4MFParser.g:4511:1: rule__ProjectDependency__Group__1__Impl : ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? ) ;
    public final void rule__ProjectDependency__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4515:1: ( ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? ) )
            // InternalN4MFParser.g:4516:1: ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? )
            {
            // InternalN4MFParser.g:4516:1: ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? )
            // InternalN4MFParser.g:4517:2: ( rule__ProjectDependency__VersionConstraintAssignment_1 )?
            {
             before(grammarAccess.getProjectDependencyAccess().getVersionConstraintAssignment_1()); 
            // InternalN4MFParser.g:4518:2: ( rule__ProjectDependency__VersionConstraintAssignment_1 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==LeftParenthesis||LA35_0==LeftSquareBracket||LA35_0==RULE_INT) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalN4MFParser.g:4518:3: rule__ProjectDependency__VersionConstraintAssignment_1
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
    // InternalN4MFParser.g:4526:1: rule__ProjectDependency__Group__2 : rule__ProjectDependency__Group__2__Impl ;
    public final void rule__ProjectDependency__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4530:1: ( rule__ProjectDependency__Group__2__Impl )
            // InternalN4MFParser.g:4531:2: rule__ProjectDependency__Group__2__Impl
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
    // InternalN4MFParser.g:4537:1: rule__ProjectDependency__Group__2__Impl : ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? ) ;
    public final void rule__ProjectDependency__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4541:1: ( ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? ) )
            // InternalN4MFParser.g:4542:1: ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? )
            {
            // InternalN4MFParser.g:4542:1: ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? )
            // InternalN4MFParser.g:4543:2: ( rule__ProjectDependency__DeclaredScopeAssignment_2 )?
            {
             before(grammarAccess.getProjectDependencyAccess().getDeclaredScopeAssignment_2()); 
            // InternalN4MFParser.g:4544:2: ( rule__ProjectDependency__DeclaredScopeAssignment_2 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==Compile||LA36_0==Test) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalN4MFParser.g:4544:3: rule__ProjectDependency__DeclaredScopeAssignment_2
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
    // InternalN4MFParser.g:4553:1: rule__ProjectIdWithOptionalVendor__Group__0 : rule__ProjectIdWithOptionalVendor__Group__0__Impl rule__ProjectIdWithOptionalVendor__Group__1 ;
    public final void rule__ProjectIdWithOptionalVendor__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4557:1: ( rule__ProjectIdWithOptionalVendor__Group__0__Impl rule__ProjectIdWithOptionalVendor__Group__1 )
            // InternalN4MFParser.g:4558:2: rule__ProjectIdWithOptionalVendor__Group__0__Impl rule__ProjectIdWithOptionalVendor__Group__1
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
    // InternalN4MFParser.g:4565:1: rule__ProjectIdWithOptionalVendor__Group__0__Impl : ( ( rule__ProjectIdWithOptionalVendor__Group_0__0 )? ) ;
    public final void rule__ProjectIdWithOptionalVendor__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4569:1: ( ( ( rule__ProjectIdWithOptionalVendor__Group_0__0 )? ) )
            // InternalN4MFParser.g:4570:1: ( ( rule__ProjectIdWithOptionalVendor__Group_0__0 )? )
            {
            // InternalN4MFParser.g:4570:1: ( ( rule__ProjectIdWithOptionalVendor__Group_0__0 )? )
            // InternalN4MFParser.g:4571:2: ( rule__ProjectIdWithOptionalVendor__Group_0__0 )?
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getGroup_0()); 
            // InternalN4MFParser.g:4572:2: ( rule__ProjectIdWithOptionalVendor__Group_0__0 )?
            int alt37=2;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // InternalN4MFParser.g:4572:3: rule__ProjectIdWithOptionalVendor__Group_0__0
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
    // InternalN4MFParser.g:4580:1: rule__ProjectIdWithOptionalVendor__Group__1 : rule__ProjectIdWithOptionalVendor__Group__1__Impl ;
    public final void rule__ProjectIdWithOptionalVendor__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4584:1: ( rule__ProjectIdWithOptionalVendor__Group__1__Impl )
            // InternalN4MFParser.g:4585:2: rule__ProjectIdWithOptionalVendor__Group__1__Impl
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
    // InternalN4MFParser.g:4591:1: rule__ProjectIdWithOptionalVendor__Group__1__Impl : ( ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 ) ) ;
    public final void rule__ProjectIdWithOptionalVendor__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4595:1: ( ( ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 ) ) )
            // InternalN4MFParser.g:4596:1: ( ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 ) )
            {
            // InternalN4MFParser.g:4596:1: ( ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 ) )
            // InternalN4MFParser.g:4597:2: ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 )
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getProjectIdAssignment_1()); 
            // InternalN4MFParser.g:4598:2: ( rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 )
            // InternalN4MFParser.g:4598:3: rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1
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
    // InternalN4MFParser.g:4607:1: rule__ProjectIdWithOptionalVendor__Group_0__0 : rule__ProjectIdWithOptionalVendor__Group_0__0__Impl rule__ProjectIdWithOptionalVendor__Group_0__1 ;
    public final void rule__ProjectIdWithOptionalVendor__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4611:1: ( rule__ProjectIdWithOptionalVendor__Group_0__0__Impl rule__ProjectIdWithOptionalVendor__Group_0__1 )
            // InternalN4MFParser.g:4612:2: rule__ProjectIdWithOptionalVendor__Group_0__0__Impl rule__ProjectIdWithOptionalVendor__Group_0__1
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
    // InternalN4MFParser.g:4619:1: rule__ProjectIdWithOptionalVendor__Group_0__0__Impl : ( ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 ) ) ;
    public final void rule__ProjectIdWithOptionalVendor__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4623:1: ( ( ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 ) ) )
            // InternalN4MFParser.g:4624:1: ( ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 ) )
            {
            // InternalN4MFParser.g:4624:1: ( ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 ) )
            // InternalN4MFParser.g:4625:2: ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 )
            {
             before(grammarAccess.getProjectIdWithOptionalVendorAccess().getDeclaredVendorIdAssignment_0_0()); 
            // InternalN4MFParser.g:4626:2: ( rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 )
            // InternalN4MFParser.g:4626:3: rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0
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
    // InternalN4MFParser.g:4634:1: rule__ProjectIdWithOptionalVendor__Group_0__1 : rule__ProjectIdWithOptionalVendor__Group_0__1__Impl ;
    public final void rule__ProjectIdWithOptionalVendor__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4638:1: ( rule__ProjectIdWithOptionalVendor__Group_0__1__Impl )
            // InternalN4MFParser.g:4639:2: rule__ProjectIdWithOptionalVendor__Group_0__1__Impl
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
    // InternalN4MFParser.g:4645:1: rule__ProjectIdWithOptionalVendor__Group_0__1__Impl : ( Colon ) ;
    public final void rule__ProjectIdWithOptionalVendor__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4649:1: ( ( Colon ) )
            // InternalN4MFParser.g:4650:1: ( Colon )
            {
            // InternalN4MFParser.g:4650:1: ( Colon )
            // InternalN4MFParser.g:4651:2: Colon
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
    // InternalN4MFParser.g:4661:1: rule__VersionConstraint__Group_0__0 : rule__VersionConstraint__Group_0__0__Impl rule__VersionConstraint__Group_0__1 ;
    public final void rule__VersionConstraint__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4665:1: ( rule__VersionConstraint__Group_0__0__Impl rule__VersionConstraint__Group_0__1 )
            // InternalN4MFParser.g:4666:2: rule__VersionConstraint__Group_0__0__Impl rule__VersionConstraint__Group_0__1
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
    // InternalN4MFParser.g:4673:1: rule__VersionConstraint__Group_0__0__Impl : ( ( rule__VersionConstraint__Alternatives_0_0 ) ) ;
    public final void rule__VersionConstraint__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4677:1: ( ( ( rule__VersionConstraint__Alternatives_0_0 ) ) )
            // InternalN4MFParser.g:4678:1: ( ( rule__VersionConstraint__Alternatives_0_0 ) )
            {
            // InternalN4MFParser.g:4678:1: ( ( rule__VersionConstraint__Alternatives_0_0 ) )
            // InternalN4MFParser.g:4679:2: ( rule__VersionConstraint__Alternatives_0_0 )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_0()); 
            // InternalN4MFParser.g:4680:2: ( rule__VersionConstraint__Alternatives_0_0 )
            // InternalN4MFParser.g:4680:3: rule__VersionConstraint__Alternatives_0_0
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
    // InternalN4MFParser.g:4688:1: rule__VersionConstraint__Group_0__1 : rule__VersionConstraint__Group_0__1__Impl rule__VersionConstraint__Group_0__2 ;
    public final void rule__VersionConstraint__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4692:1: ( rule__VersionConstraint__Group_0__1__Impl rule__VersionConstraint__Group_0__2 )
            // InternalN4MFParser.g:4693:2: rule__VersionConstraint__Group_0__1__Impl rule__VersionConstraint__Group_0__2
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
    // InternalN4MFParser.g:4700:1: rule__VersionConstraint__Group_0__1__Impl : ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) ) ;
    public final void rule__VersionConstraint__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4704:1: ( ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) ) )
            // InternalN4MFParser.g:4705:1: ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) )
            {
            // InternalN4MFParser.g:4705:1: ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) )
            // InternalN4MFParser.g:4706:2: ( rule__VersionConstraint__LowerVersionAssignment_0_1 )
            {
             before(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_0_1()); 
            // InternalN4MFParser.g:4707:2: ( rule__VersionConstraint__LowerVersionAssignment_0_1 )
            // InternalN4MFParser.g:4707:3: rule__VersionConstraint__LowerVersionAssignment_0_1
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
    // InternalN4MFParser.g:4715:1: rule__VersionConstraint__Group_0__2 : rule__VersionConstraint__Group_0__2__Impl ;
    public final void rule__VersionConstraint__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4719:1: ( rule__VersionConstraint__Group_0__2__Impl )
            // InternalN4MFParser.g:4720:2: rule__VersionConstraint__Group_0__2__Impl
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
    // InternalN4MFParser.g:4726:1: rule__VersionConstraint__Group_0__2__Impl : ( ( rule__VersionConstraint__Alternatives_0_2 ) ) ;
    public final void rule__VersionConstraint__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4730:1: ( ( ( rule__VersionConstraint__Alternatives_0_2 ) ) )
            // InternalN4MFParser.g:4731:1: ( ( rule__VersionConstraint__Alternatives_0_2 ) )
            {
            // InternalN4MFParser.g:4731:1: ( ( rule__VersionConstraint__Alternatives_0_2 ) )
            // InternalN4MFParser.g:4732:2: ( rule__VersionConstraint__Alternatives_0_2 )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2()); 
            // InternalN4MFParser.g:4733:2: ( rule__VersionConstraint__Alternatives_0_2 )
            // InternalN4MFParser.g:4733:3: rule__VersionConstraint__Alternatives_0_2
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
    // InternalN4MFParser.g:4742:1: rule__VersionConstraint__Group_0_2_0__0 : rule__VersionConstraint__Group_0_2_0__0__Impl rule__VersionConstraint__Group_0_2_0__1 ;
    public final void rule__VersionConstraint__Group_0_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4746:1: ( rule__VersionConstraint__Group_0_2_0__0__Impl rule__VersionConstraint__Group_0_2_0__1 )
            // InternalN4MFParser.g:4747:2: rule__VersionConstraint__Group_0_2_0__0__Impl rule__VersionConstraint__Group_0_2_0__1
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
    // InternalN4MFParser.g:4754:1: rule__VersionConstraint__Group_0_2_0__0__Impl : ( Comma ) ;
    public final void rule__VersionConstraint__Group_0_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4758:1: ( ( Comma ) )
            // InternalN4MFParser.g:4759:1: ( Comma )
            {
            // InternalN4MFParser.g:4759:1: ( Comma )
            // InternalN4MFParser.g:4760:2: Comma
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
    // InternalN4MFParser.g:4769:1: rule__VersionConstraint__Group_0_2_0__1 : rule__VersionConstraint__Group_0_2_0__1__Impl rule__VersionConstraint__Group_0_2_0__2 ;
    public final void rule__VersionConstraint__Group_0_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4773:1: ( rule__VersionConstraint__Group_0_2_0__1__Impl rule__VersionConstraint__Group_0_2_0__2 )
            // InternalN4MFParser.g:4774:2: rule__VersionConstraint__Group_0_2_0__1__Impl rule__VersionConstraint__Group_0_2_0__2
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
    // InternalN4MFParser.g:4781:1: rule__VersionConstraint__Group_0_2_0__1__Impl : ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) ) ;
    public final void rule__VersionConstraint__Group_0_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4785:1: ( ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) ) )
            // InternalN4MFParser.g:4786:1: ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) )
            {
            // InternalN4MFParser.g:4786:1: ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) )
            // InternalN4MFParser.g:4787:2: ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 )
            {
             before(grammarAccess.getVersionConstraintAccess().getUpperVersionAssignment_0_2_0_1()); 
            // InternalN4MFParser.g:4788:2: ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 )
            // InternalN4MFParser.g:4788:3: rule__VersionConstraint__UpperVersionAssignment_0_2_0_1
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
    // InternalN4MFParser.g:4796:1: rule__VersionConstraint__Group_0_2_0__2 : rule__VersionConstraint__Group_0_2_0__2__Impl ;
    public final void rule__VersionConstraint__Group_0_2_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4800:1: ( rule__VersionConstraint__Group_0_2_0__2__Impl )
            // InternalN4MFParser.g:4801:2: rule__VersionConstraint__Group_0_2_0__2__Impl
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
    // InternalN4MFParser.g:4807:1: rule__VersionConstraint__Group_0_2_0__2__Impl : ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) ) ;
    public final void rule__VersionConstraint__Group_0_2_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4811:1: ( ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) ) )
            // InternalN4MFParser.g:4812:1: ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) )
            {
            // InternalN4MFParser.g:4812:1: ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) )
            // InternalN4MFParser.g:4813:2: ( rule__VersionConstraint__Alternatives_0_2_0_2 )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2_0_2()); 
            // InternalN4MFParser.g:4814:2: ( rule__VersionConstraint__Alternatives_0_2_0_2 )
            // InternalN4MFParser.g:4814:3: rule__VersionConstraint__Alternatives_0_2_0_2
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
    // InternalN4MFParser.g:4823:1: rule__N4mfIdentifier__Group_11__0 : rule__N4mfIdentifier__Group_11__0__Impl rule__N4mfIdentifier__Group_11__1 ;
    public final void rule__N4mfIdentifier__Group_11__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4827:1: ( rule__N4mfIdentifier__Group_11__0__Impl rule__N4mfIdentifier__Group_11__1 )
            // InternalN4MFParser.g:4828:2: rule__N4mfIdentifier__Group_11__0__Impl rule__N4mfIdentifier__Group_11__1
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
    // InternalN4MFParser.g:4835:1: rule__N4mfIdentifier__Group_11__0__Impl : ( ProjectDependencies ) ;
    public final void rule__N4mfIdentifier__Group_11__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4839:1: ( ( ProjectDependencies ) )
            // InternalN4MFParser.g:4840:1: ( ProjectDependencies )
            {
            // InternalN4MFParser.g:4840:1: ( ProjectDependencies )
            // InternalN4MFParser.g:4841:2: ProjectDependencies
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
    // InternalN4MFParser.g:4850:1: rule__N4mfIdentifier__Group_11__1 : rule__N4mfIdentifier__Group_11__1__Impl ;
    public final void rule__N4mfIdentifier__Group_11__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4854:1: ( rule__N4mfIdentifier__Group_11__1__Impl )
            // InternalN4MFParser.g:4855:2: rule__N4mfIdentifier__Group_11__1__Impl
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
    // InternalN4MFParser.g:4861:1: rule__N4mfIdentifier__Group_11__1__Impl : ( KW_System ) ;
    public final void rule__N4mfIdentifier__Group_11__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4865:1: ( ( KW_System ) )
            // InternalN4MFParser.g:4866:1: ( KW_System )
            {
            // InternalN4MFParser.g:4866:1: ( KW_System )
            // InternalN4MFParser.g:4867:2: KW_System
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
    // InternalN4MFParser.g:4877:1: rule__N4mfIdentifier__Group_15__0 : rule__N4mfIdentifier__Group_15__0__Impl rule__N4mfIdentifier__Group_15__1 ;
    public final void rule__N4mfIdentifier__Group_15__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4881:1: ( rule__N4mfIdentifier__Group_15__0__Impl rule__N4mfIdentifier__Group_15__1 )
            // InternalN4MFParser.g:4882:2: rule__N4mfIdentifier__Group_15__0__Impl rule__N4mfIdentifier__Group_15__1
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
    // InternalN4MFParser.g:4889:1: rule__N4mfIdentifier__Group_15__0__Impl : ( Processor ) ;
    public final void rule__N4mfIdentifier__Group_15__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4893:1: ( ( Processor ) )
            // InternalN4MFParser.g:4894:1: ( Processor )
            {
            // InternalN4MFParser.g:4894:1: ( Processor )
            // InternalN4MFParser.g:4895:2: Processor
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
    // InternalN4MFParser.g:4904:1: rule__N4mfIdentifier__Group_15__1 : rule__N4mfIdentifier__Group_15__1__Impl ;
    public final void rule__N4mfIdentifier__Group_15__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4908:1: ( rule__N4mfIdentifier__Group_15__1__Impl )
            // InternalN4MFParser.g:4909:2: rule__N4mfIdentifier__Group_15__1__Impl
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
    // InternalN4MFParser.g:4915:1: rule__N4mfIdentifier__Group_15__1__Impl : ( Source ) ;
    public final void rule__N4mfIdentifier__Group_15__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4919:1: ( ( Source ) )
            // InternalN4MFParser.g:4920:1: ( Source )
            {
            // InternalN4MFParser.g:4920:1: ( Source )
            // InternalN4MFParser.g:4921:2: Source
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
    // InternalN4MFParser.g:4931:1: rule__ProjectDescription__UnorderedGroup : rule__ProjectDescription__UnorderedGroup__0 {...}?;
    public final void rule__ProjectDescription__UnorderedGroup() throws RecognitionException {

        		int stackSize = keepStackSize();
        		getUnorderedGroupHelper().enter(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
        	
        try {
            // InternalN4MFParser.g:4936:1: ( rule__ProjectDescription__UnorderedGroup__0 {...}?)
            // InternalN4MFParser.g:4937:2: rule__ProjectDescription__UnorderedGroup__0 {...}?
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
    // InternalN4MFParser.g:4945:1: rule__ProjectDescription__UnorderedGroup__Impl : ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) ) ;
    public final void rule__ProjectDescription__UnorderedGroup__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        		boolean selected = false;
        	
        try {
            // InternalN4MFParser.g:4950:1: ( ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) ) )
            // InternalN4MFParser.g:4951:3: ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) )
            {
            // InternalN4MFParser.g:4951:3: ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) )
            int alt38=21;
            alt38 = dfa38.predict(input);
            switch (alt38) {
                case 1 :
                    // InternalN4MFParser.g:4952:3: ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:4952:3: ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) )
                    // InternalN4MFParser.g:4953:4: {...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0)");
                    }
                    // InternalN4MFParser.g:4953:112: ( ( ( rule__ProjectDescription__Group_0__0 ) ) )
                    // InternalN4MFParser.g:4954:5: ( ( rule__ProjectDescription__Group_0__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:4960:5: ( ( rule__ProjectDescription__Group_0__0 ) )
                    // InternalN4MFParser.g:4961:6: ( rule__ProjectDescription__Group_0__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_0()); 
                    // InternalN4MFParser.g:4962:6: ( rule__ProjectDescription__Group_0__0 )
                    // InternalN4MFParser.g:4962:7: rule__ProjectDescription__Group_0__0
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
                    // InternalN4MFParser.g:4967:3: ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:4967:3: ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) )
                    // InternalN4MFParser.g:4968:4: {...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1)");
                    }
                    // InternalN4MFParser.g:4968:112: ( ( ( rule__ProjectDescription__Group_1__0 ) ) )
                    // InternalN4MFParser.g:4969:5: ( ( rule__ProjectDescription__Group_1__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:4975:5: ( ( rule__ProjectDescription__Group_1__0 ) )
                    // InternalN4MFParser.g:4976:6: ( rule__ProjectDescription__Group_1__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_1()); 
                    // InternalN4MFParser.g:4977:6: ( rule__ProjectDescription__Group_1__0 )
                    // InternalN4MFParser.g:4977:7: rule__ProjectDescription__Group_1__0
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
                    // InternalN4MFParser.g:4982:3: ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:4982:3: ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) )
                    // InternalN4MFParser.g:4983:4: {...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2)");
                    }
                    // InternalN4MFParser.g:4983:112: ( ( ( rule__ProjectDescription__Group_2__0 ) ) )
                    // InternalN4MFParser.g:4984:5: ( ( rule__ProjectDescription__Group_2__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:4990:5: ( ( rule__ProjectDescription__Group_2__0 ) )
                    // InternalN4MFParser.g:4991:6: ( rule__ProjectDescription__Group_2__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_2()); 
                    // InternalN4MFParser.g:4992:6: ( rule__ProjectDescription__Group_2__0 )
                    // InternalN4MFParser.g:4992:7: rule__ProjectDescription__Group_2__0
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
                    // InternalN4MFParser.g:4997:3: ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:4997:3: ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) )
                    // InternalN4MFParser.g:4998:4: {...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3)");
                    }
                    // InternalN4MFParser.g:4998:112: ( ( ( rule__ProjectDescription__Group_3__0 ) ) )
                    // InternalN4MFParser.g:4999:5: ( ( rule__ProjectDescription__Group_3__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5005:5: ( ( rule__ProjectDescription__Group_3__0 ) )
                    // InternalN4MFParser.g:5006:6: ( rule__ProjectDescription__Group_3__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_3()); 
                    // InternalN4MFParser.g:5007:6: ( rule__ProjectDescription__Group_3__0 )
                    // InternalN4MFParser.g:5007:7: rule__ProjectDescription__Group_3__0
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
                    // InternalN4MFParser.g:5012:3: ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5012:3: ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) )
                    // InternalN4MFParser.g:5013:4: {...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4)");
                    }
                    // InternalN4MFParser.g:5013:112: ( ( ( rule__ProjectDescription__Group_4__0 ) ) )
                    // InternalN4MFParser.g:5014:5: ( ( rule__ProjectDescription__Group_4__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5020:5: ( ( rule__ProjectDescription__Group_4__0 ) )
                    // InternalN4MFParser.g:5021:6: ( rule__ProjectDescription__Group_4__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_4()); 
                    // InternalN4MFParser.g:5022:6: ( rule__ProjectDescription__Group_4__0 )
                    // InternalN4MFParser.g:5022:7: rule__ProjectDescription__Group_4__0
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
                    // InternalN4MFParser.g:5027:3: ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5027:3: ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) )
                    // InternalN4MFParser.g:5028:4: {...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5)");
                    }
                    // InternalN4MFParser.g:5028:112: ( ( ( rule__ProjectDescription__Group_5__0 ) ) )
                    // InternalN4MFParser.g:5029:5: ( ( rule__ProjectDescription__Group_5__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5035:5: ( ( rule__ProjectDescription__Group_5__0 ) )
                    // InternalN4MFParser.g:5036:6: ( rule__ProjectDescription__Group_5__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_5()); 
                    // InternalN4MFParser.g:5037:6: ( rule__ProjectDescription__Group_5__0 )
                    // InternalN4MFParser.g:5037:7: rule__ProjectDescription__Group_5__0
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
                    // InternalN4MFParser.g:5042:3: ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5042:3: ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) )
                    // InternalN4MFParser.g:5043:4: {...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6)");
                    }
                    // InternalN4MFParser.g:5043:112: ( ( ( rule__ProjectDescription__Group_6__0 ) ) )
                    // InternalN4MFParser.g:5044:5: ( ( rule__ProjectDescription__Group_6__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5050:5: ( ( rule__ProjectDescription__Group_6__0 ) )
                    // InternalN4MFParser.g:5051:6: ( rule__ProjectDescription__Group_6__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_6()); 
                    // InternalN4MFParser.g:5052:6: ( rule__ProjectDescription__Group_6__0 )
                    // InternalN4MFParser.g:5052:7: rule__ProjectDescription__Group_6__0
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
                    // InternalN4MFParser.g:5057:3: ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5057:3: ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) )
                    // InternalN4MFParser.g:5058:4: {...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7)");
                    }
                    // InternalN4MFParser.g:5058:112: ( ( ( rule__ProjectDescription__Group_7__0 ) ) )
                    // InternalN4MFParser.g:5059:5: ( ( rule__ProjectDescription__Group_7__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5065:5: ( ( rule__ProjectDescription__Group_7__0 ) )
                    // InternalN4MFParser.g:5066:6: ( rule__ProjectDescription__Group_7__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_7()); 
                    // InternalN4MFParser.g:5067:6: ( rule__ProjectDescription__Group_7__0 )
                    // InternalN4MFParser.g:5067:7: rule__ProjectDescription__Group_7__0
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
                    // InternalN4MFParser.g:5072:3: ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5072:3: ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) )
                    // InternalN4MFParser.g:5073:4: {...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8)");
                    }
                    // InternalN4MFParser.g:5073:112: ( ( ( rule__ProjectDescription__Group_8__0 ) ) )
                    // InternalN4MFParser.g:5074:5: ( ( rule__ProjectDescription__Group_8__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5080:5: ( ( rule__ProjectDescription__Group_8__0 ) )
                    // InternalN4MFParser.g:5081:6: ( rule__ProjectDescription__Group_8__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_8()); 
                    // InternalN4MFParser.g:5082:6: ( rule__ProjectDescription__Group_8__0 )
                    // InternalN4MFParser.g:5082:7: rule__ProjectDescription__Group_8__0
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
                    // InternalN4MFParser.g:5087:3: ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5087:3: ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) )
                    // InternalN4MFParser.g:5088:4: {...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9)");
                    }
                    // InternalN4MFParser.g:5088:112: ( ( ( rule__ProjectDescription__Group_9__0 ) ) )
                    // InternalN4MFParser.g:5089:5: ( ( rule__ProjectDescription__Group_9__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5095:5: ( ( rule__ProjectDescription__Group_9__0 ) )
                    // InternalN4MFParser.g:5096:6: ( rule__ProjectDescription__Group_9__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_9()); 
                    // InternalN4MFParser.g:5097:6: ( rule__ProjectDescription__Group_9__0 )
                    // InternalN4MFParser.g:5097:7: rule__ProjectDescription__Group_9__0
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
                    // InternalN4MFParser.g:5102:3: ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5102:3: ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) )
                    // InternalN4MFParser.g:5103:4: {...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10)");
                    }
                    // InternalN4MFParser.g:5103:113: ( ( ( rule__ProjectDescription__Group_10__0 ) ) )
                    // InternalN4MFParser.g:5104:5: ( ( rule__ProjectDescription__Group_10__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5110:5: ( ( rule__ProjectDescription__Group_10__0 ) )
                    // InternalN4MFParser.g:5111:6: ( rule__ProjectDescription__Group_10__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_10()); 
                    // InternalN4MFParser.g:5112:6: ( rule__ProjectDescription__Group_10__0 )
                    // InternalN4MFParser.g:5112:7: rule__ProjectDescription__Group_10__0
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
                    // InternalN4MFParser.g:5117:3: ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5117:3: ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) )
                    // InternalN4MFParser.g:5118:4: {...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11)");
                    }
                    // InternalN4MFParser.g:5118:113: ( ( ( rule__ProjectDescription__Group_11__0 ) ) )
                    // InternalN4MFParser.g:5119:5: ( ( rule__ProjectDescription__Group_11__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5125:5: ( ( rule__ProjectDescription__Group_11__0 ) )
                    // InternalN4MFParser.g:5126:6: ( rule__ProjectDescription__Group_11__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_11()); 
                    // InternalN4MFParser.g:5127:6: ( rule__ProjectDescription__Group_11__0 )
                    // InternalN4MFParser.g:5127:7: rule__ProjectDescription__Group_11__0
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
                    // InternalN4MFParser.g:5132:3: ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5132:3: ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) )
                    // InternalN4MFParser.g:5133:4: {...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12)");
                    }
                    // InternalN4MFParser.g:5133:113: ( ( ( rule__ProjectDescription__Group_12__0 ) ) )
                    // InternalN4MFParser.g:5134:5: ( ( rule__ProjectDescription__Group_12__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5140:5: ( ( rule__ProjectDescription__Group_12__0 ) )
                    // InternalN4MFParser.g:5141:6: ( rule__ProjectDescription__Group_12__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_12()); 
                    // InternalN4MFParser.g:5142:6: ( rule__ProjectDescription__Group_12__0 )
                    // InternalN4MFParser.g:5142:7: rule__ProjectDescription__Group_12__0
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
                    // InternalN4MFParser.g:5147:3: ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5147:3: ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) )
                    // InternalN4MFParser.g:5148:4: {...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13)");
                    }
                    // InternalN4MFParser.g:5148:113: ( ( ( rule__ProjectDescription__Group_13__0 ) ) )
                    // InternalN4MFParser.g:5149:5: ( ( rule__ProjectDescription__Group_13__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5155:5: ( ( rule__ProjectDescription__Group_13__0 ) )
                    // InternalN4MFParser.g:5156:6: ( rule__ProjectDescription__Group_13__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_13()); 
                    // InternalN4MFParser.g:5157:6: ( rule__ProjectDescription__Group_13__0 )
                    // InternalN4MFParser.g:5157:7: rule__ProjectDescription__Group_13__0
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
                    // InternalN4MFParser.g:5162:3: ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5162:3: ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) )
                    // InternalN4MFParser.g:5163:4: {...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14)");
                    }
                    // InternalN4MFParser.g:5163:113: ( ( ( rule__ProjectDescription__Group_14__0 ) ) )
                    // InternalN4MFParser.g:5164:5: ( ( rule__ProjectDescription__Group_14__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5170:5: ( ( rule__ProjectDescription__Group_14__0 ) )
                    // InternalN4MFParser.g:5171:6: ( rule__ProjectDescription__Group_14__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_14()); 
                    // InternalN4MFParser.g:5172:6: ( rule__ProjectDescription__Group_14__0 )
                    // InternalN4MFParser.g:5172:7: rule__ProjectDescription__Group_14__0
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
                    // InternalN4MFParser.g:5177:3: ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5177:3: ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) )
                    // InternalN4MFParser.g:5178:4: {...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15)");
                    }
                    // InternalN4MFParser.g:5178:113: ( ( ( rule__ProjectDescription__Group_15__0 ) ) )
                    // InternalN4MFParser.g:5179:5: ( ( rule__ProjectDescription__Group_15__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5185:5: ( ( rule__ProjectDescription__Group_15__0 ) )
                    // InternalN4MFParser.g:5186:6: ( rule__ProjectDescription__Group_15__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_15()); 
                    // InternalN4MFParser.g:5187:6: ( rule__ProjectDescription__Group_15__0 )
                    // InternalN4MFParser.g:5187:7: rule__ProjectDescription__Group_15__0
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
                    // InternalN4MFParser.g:5192:3: ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5192:3: ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) )
                    // InternalN4MFParser.g:5193:4: {...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16)");
                    }
                    // InternalN4MFParser.g:5193:113: ( ( ( rule__ProjectDescription__Group_16__0 ) ) )
                    // InternalN4MFParser.g:5194:5: ( ( rule__ProjectDescription__Group_16__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5200:5: ( ( rule__ProjectDescription__Group_16__0 ) )
                    // InternalN4MFParser.g:5201:6: ( rule__ProjectDescription__Group_16__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_16()); 
                    // InternalN4MFParser.g:5202:6: ( rule__ProjectDescription__Group_16__0 )
                    // InternalN4MFParser.g:5202:7: rule__ProjectDescription__Group_16__0
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
                    // InternalN4MFParser.g:5207:3: ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5207:3: ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) )
                    // InternalN4MFParser.g:5208:4: {...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17)");
                    }
                    // InternalN4MFParser.g:5208:113: ( ( ( rule__ProjectDescription__Group_17__0 ) ) )
                    // InternalN4MFParser.g:5209:5: ( ( rule__ProjectDescription__Group_17__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5215:5: ( ( rule__ProjectDescription__Group_17__0 ) )
                    // InternalN4MFParser.g:5216:6: ( rule__ProjectDescription__Group_17__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_17()); 
                    // InternalN4MFParser.g:5217:6: ( rule__ProjectDescription__Group_17__0 )
                    // InternalN4MFParser.g:5217:7: rule__ProjectDescription__Group_17__0
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
                    // InternalN4MFParser.g:5222:3: ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5222:3: ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) )
                    // InternalN4MFParser.g:5223:4: {...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18)");
                    }
                    // InternalN4MFParser.g:5223:113: ( ( ( rule__ProjectDescription__Group_18__0 ) ) )
                    // InternalN4MFParser.g:5224:5: ( ( rule__ProjectDescription__Group_18__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5230:5: ( ( rule__ProjectDescription__Group_18__0 ) )
                    // InternalN4MFParser.g:5231:6: ( rule__ProjectDescription__Group_18__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_18()); 
                    // InternalN4MFParser.g:5232:6: ( rule__ProjectDescription__Group_18__0 )
                    // InternalN4MFParser.g:5232:7: rule__ProjectDescription__Group_18__0
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
                    // InternalN4MFParser.g:5237:3: ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5237:3: ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) )
                    // InternalN4MFParser.g:5238:4: {...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19)");
                    }
                    // InternalN4MFParser.g:5238:113: ( ( ( rule__ProjectDescription__Group_19__0 ) ) )
                    // InternalN4MFParser.g:5239:5: ( ( rule__ProjectDescription__Group_19__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5245:5: ( ( rule__ProjectDescription__Group_19__0 ) )
                    // InternalN4MFParser.g:5246:6: ( rule__ProjectDescription__Group_19__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_19()); 
                    // InternalN4MFParser.g:5247:6: ( rule__ProjectDescription__Group_19__0 )
                    // InternalN4MFParser.g:5247:7: rule__ProjectDescription__Group_19__0
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
                    // InternalN4MFParser.g:5252:3: ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5252:3: ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) )
                    // InternalN4MFParser.g:5253:4: {...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20)");
                    }
                    // InternalN4MFParser.g:5253:113: ( ( ( rule__ProjectDescription__Group_20__0 ) ) )
                    // InternalN4MFParser.g:5254:5: ( ( rule__ProjectDescription__Group_20__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5260:5: ( ( rule__ProjectDescription__Group_20__0 ) )
                    // InternalN4MFParser.g:5261:6: ( rule__ProjectDescription__Group_20__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_20()); 
                    // InternalN4MFParser.g:5262:6: ( rule__ProjectDescription__Group_20__0 )
                    // InternalN4MFParser.g:5262:7: rule__ProjectDescription__Group_20__0
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
    // InternalN4MFParser.g:5275:1: rule__ProjectDescription__UnorderedGroup__0 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__1 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5279:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__1 )? )
            // InternalN4MFParser.g:5280:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__1 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5281:2: ( rule__ProjectDescription__UnorderedGroup__1 )?
            int alt39=2;
            alt39 = dfa39.predict(input);
            switch (alt39) {
                case 1 :
                    // InternalN4MFParser.g:5281:2: rule__ProjectDescription__UnorderedGroup__1
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
    // InternalN4MFParser.g:5287:1: rule__ProjectDescription__UnorderedGroup__1 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__2 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5291:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__2 )? )
            // InternalN4MFParser.g:5292:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__2 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5293:2: ( rule__ProjectDescription__UnorderedGroup__2 )?
            int alt40=2;
            alt40 = dfa40.predict(input);
            switch (alt40) {
                case 1 :
                    // InternalN4MFParser.g:5293:2: rule__ProjectDescription__UnorderedGroup__2
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
    // InternalN4MFParser.g:5299:1: rule__ProjectDescription__UnorderedGroup__2 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__3 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5303:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__3 )? )
            // InternalN4MFParser.g:5304:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__3 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5305:2: ( rule__ProjectDescription__UnorderedGroup__3 )?
            int alt41=2;
            alt41 = dfa41.predict(input);
            switch (alt41) {
                case 1 :
                    // InternalN4MFParser.g:5305:2: rule__ProjectDescription__UnorderedGroup__3
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
    // InternalN4MFParser.g:5311:1: rule__ProjectDescription__UnorderedGroup__3 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__4 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5315:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__4 )? )
            // InternalN4MFParser.g:5316:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__4 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5317:2: ( rule__ProjectDescription__UnorderedGroup__4 )?
            int alt42=2;
            alt42 = dfa42.predict(input);
            switch (alt42) {
                case 1 :
                    // InternalN4MFParser.g:5317:2: rule__ProjectDescription__UnorderedGroup__4
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
    // InternalN4MFParser.g:5323:1: rule__ProjectDescription__UnorderedGroup__4 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__5 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5327:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__5 )? )
            // InternalN4MFParser.g:5328:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__5 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5329:2: ( rule__ProjectDescription__UnorderedGroup__5 )?
            int alt43=2;
            alt43 = dfa43.predict(input);
            switch (alt43) {
                case 1 :
                    // InternalN4MFParser.g:5329:2: rule__ProjectDescription__UnorderedGroup__5
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
    // InternalN4MFParser.g:5335:1: rule__ProjectDescription__UnorderedGroup__5 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__6 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5339:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__6 )? )
            // InternalN4MFParser.g:5340:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__6 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5341:2: ( rule__ProjectDescription__UnorderedGroup__6 )?
            int alt44=2;
            alt44 = dfa44.predict(input);
            switch (alt44) {
                case 1 :
                    // InternalN4MFParser.g:5341:2: rule__ProjectDescription__UnorderedGroup__6
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
    // InternalN4MFParser.g:5347:1: rule__ProjectDescription__UnorderedGroup__6 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__7 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5351:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__7 )? )
            // InternalN4MFParser.g:5352:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__7 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5353:2: ( rule__ProjectDescription__UnorderedGroup__7 )?
            int alt45=2;
            alt45 = dfa45.predict(input);
            switch (alt45) {
                case 1 :
                    // InternalN4MFParser.g:5353:2: rule__ProjectDescription__UnorderedGroup__7
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
    // InternalN4MFParser.g:5359:1: rule__ProjectDescription__UnorderedGroup__7 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__8 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5363:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__8 )? )
            // InternalN4MFParser.g:5364:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__8 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5365:2: ( rule__ProjectDescription__UnorderedGroup__8 )?
            int alt46=2;
            alt46 = dfa46.predict(input);
            switch (alt46) {
                case 1 :
                    // InternalN4MFParser.g:5365:2: rule__ProjectDescription__UnorderedGroup__8
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
    // InternalN4MFParser.g:5371:1: rule__ProjectDescription__UnorderedGroup__8 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__9 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5375:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__9 )? )
            // InternalN4MFParser.g:5376:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__9 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5377:2: ( rule__ProjectDescription__UnorderedGroup__9 )?
            int alt47=2;
            alt47 = dfa47.predict(input);
            switch (alt47) {
                case 1 :
                    // InternalN4MFParser.g:5377:2: rule__ProjectDescription__UnorderedGroup__9
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
    // InternalN4MFParser.g:5383:1: rule__ProjectDescription__UnorderedGroup__9 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__10 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5387:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__10 )? )
            // InternalN4MFParser.g:5388:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__10 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5389:2: ( rule__ProjectDescription__UnorderedGroup__10 )?
            int alt48=2;
            alt48 = dfa48.predict(input);
            switch (alt48) {
                case 1 :
                    // InternalN4MFParser.g:5389:2: rule__ProjectDescription__UnorderedGroup__10
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
    // InternalN4MFParser.g:5395:1: rule__ProjectDescription__UnorderedGroup__10 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__11 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5399:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__11 )? )
            // InternalN4MFParser.g:5400:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__11 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5401:2: ( rule__ProjectDescription__UnorderedGroup__11 )?
            int alt49=2;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // InternalN4MFParser.g:5401:2: rule__ProjectDescription__UnorderedGroup__11
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
    // InternalN4MFParser.g:5407:1: rule__ProjectDescription__UnorderedGroup__11 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__12 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5411:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__12 )? )
            // InternalN4MFParser.g:5412:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__12 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5413:2: ( rule__ProjectDescription__UnorderedGroup__12 )?
            int alt50=2;
            alt50 = dfa50.predict(input);
            switch (alt50) {
                case 1 :
                    // InternalN4MFParser.g:5413:2: rule__ProjectDescription__UnorderedGroup__12
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
    // InternalN4MFParser.g:5419:1: rule__ProjectDescription__UnorderedGroup__12 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__13 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5423:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__13 )? )
            // InternalN4MFParser.g:5424:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__13 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5425:2: ( rule__ProjectDescription__UnorderedGroup__13 )?
            int alt51=2;
            alt51 = dfa51.predict(input);
            switch (alt51) {
                case 1 :
                    // InternalN4MFParser.g:5425:2: rule__ProjectDescription__UnorderedGroup__13
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
    // InternalN4MFParser.g:5431:1: rule__ProjectDescription__UnorderedGroup__13 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__14 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5435:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__14 )? )
            // InternalN4MFParser.g:5436:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__14 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5437:2: ( rule__ProjectDescription__UnorderedGroup__14 )?
            int alt52=2;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // InternalN4MFParser.g:5437:2: rule__ProjectDescription__UnorderedGroup__14
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
    // InternalN4MFParser.g:5443:1: rule__ProjectDescription__UnorderedGroup__14 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__15 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5447:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__15 )? )
            // InternalN4MFParser.g:5448:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__15 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5449:2: ( rule__ProjectDescription__UnorderedGroup__15 )?
            int alt53=2;
            alt53 = dfa53.predict(input);
            switch (alt53) {
                case 1 :
                    // InternalN4MFParser.g:5449:2: rule__ProjectDescription__UnorderedGroup__15
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
    // InternalN4MFParser.g:5455:1: rule__ProjectDescription__UnorderedGroup__15 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__16 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5459:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__16 )? )
            // InternalN4MFParser.g:5460:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__16 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5461:2: ( rule__ProjectDescription__UnorderedGroup__16 )?
            int alt54=2;
            alt54 = dfa54.predict(input);
            switch (alt54) {
                case 1 :
                    // InternalN4MFParser.g:5461:2: rule__ProjectDescription__UnorderedGroup__16
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
    // InternalN4MFParser.g:5467:1: rule__ProjectDescription__UnorderedGroup__16 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__17 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5471:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__17 )? )
            // InternalN4MFParser.g:5472:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__17 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5473:2: ( rule__ProjectDescription__UnorderedGroup__17 )?
            int alt55=2;
            alt55 = dfa55.predict(input);
            switch (alt55) {
                case 1 :
                    // InternalN4MFParser.g:5473:2: rule__ProjectDescription__UnorderedGroup__17
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
    // InternalN4MFParser.g:5479:1: rule__ProjectDescription__UnorderedGroup__17 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__18 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__17() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5483:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__18 )? )
            // InternalN4MFParser.g:5484:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__18 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5485:2: ( rule__ProjectDescription__UnorderedGroup__18 )?
            int alt56=2;
            alt56 = dfa56.predict(input);
            switch (alt56) {
                case 1 :
                    // InternalN4MFParser.g:5485:2: rule__ProjectDescription__UnorderedGroup__18
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
    // InternalN4MFParser.g:5491:1: rule__ProjectDescription__UnorderedGroup__18 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__19 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__18() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5495:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__19 )? )
            // InternalN4MFParser.g:5496:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__19 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5497:2: ( rule__ProjectDescription__UnorderedGroup__19 )?
            int alt57=2;
            alt57 = dfa57.predict(input);
            switch (alt57) {
                case 1 :
                    // InternalN4MFParser.g:5497:2: rule__ProjectDescription__UnorderedGroup__19
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
    // InternalN4MFParser.g:5503:1: rule__ProjectDescription__UnorderedGroup__19 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__20 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__19() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5507:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__20 )? )
            // InternalN4MFParser.g:5508:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__20 )?
            {
            pushFollow(FOLLOW_28);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5509:2: ( rule__ProjectDescription__UnorderedGroup__20 )?
            int alt58=2;
            alt58 = dfa58.predict(input);
            switch (alt58) {
                case 1 :
                    // InternalN4MFParser.g:5509:2: rule__ProjectDescription__UnorderedGroup__20
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
    // InternalN4MFParser.g:5515:1: rule__ProjectDescription__UnorderedGroup__20 : rule__ProjectDescription__UnorderedGroup__Impl ;
    public final void rule__ProjectDescription__UnorderedGroup__20() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5519:1: ( rule__ProjectDescription__UnorderedGroup__Impl )
            // InternalN4MFParser.g:5520:2: rule__ProjectDescription__UnorderedGroup__Impl
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
    // InternalN4MFParser.g:5527:1: rule__ProjectDescription__ProjectIdAssignment_0_2 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectDescription__ProjectIdAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5531:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:5532:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:5532:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:5533:3: ruleN4mfIdentifier
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
    // InternalN4MFParser.g:5542:1: rule__ProjectDescription__ProjectTypeAssignment_1_2 : ( ruleProjectType ) ;
    public final void rule__ProjectDescription__ProjectTypeAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5546:1: ( ( ruleProjectType ) )
            // InternalN4MFParser.g:5547:2: ( ruleProjectType )
            {
            // InternalN4MFParser.g:5547:2: ( ruleProjectType )
            // InternalN4MFParser.g:5548:3: ruleProjectType
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
    // InternalN4MFParser.g:5557:1: rule__ProjectDescription__ProjectVersionAssignment_2_2 : ( ruleDeclaredVersion ) ;
    public final void rule__ProjectDescription__ProjectVersionAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5561:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:5562:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:5562:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:5563:3: ruleDeclaredVersion
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


    // $ANTLR start "rule__ProjectDescription__VendorIdAssignment_3_2"
    // InternalN4MFParser.g:5572:1: rule__ProjectDescription__VendorIdAssignment_3_2 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectDescription__VendorIdAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5576:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:5577:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:5577:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:5578:3: ruleN4mfIdentifier
            {
             before(grammarAccess.getProjectDescriptionAccess().getVendorIdN4mfIdentifierParserRuleCall_3_2_0()); 
            pushFollow(FOLLOW_2);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getVendorIdN4mfIdentifierParserRuleCall_3_2_0()); 

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
    // $ANTLR end "rule__ProjectDescription__VendorIdAssignment_3_2"


    // $ANTLR start "rule__ProjectDescription__VendorNameAssignment_4_2"
    // InternalN4MFParser.g:5587:1: rule__ProjectDescription__VendorNameAssignment_4_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__VendorNameAssignment_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5591:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5592:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5592:2: ( RULE_STRING )
            // InternalN4MFParser.g:5593:3: RULE_STRING
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
    // InternalN4MFParser.g:5602:1: rule__ProjectDescription__MainModuleAssignment_5_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__MainModuleAssignment_5_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5606:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5607:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5607:2: ( RULE_STRING )
            // InternalN4MFParser.g:5608:3: RULE_STRING
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
    // InternalN4MFParser.g:5617:1: rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5621:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5622:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5622:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5623:3: ruleProjectReference
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
    // InternalN4MFParser.g:5632:1: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5636:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5637:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5637:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5638:3: ruleProjectReference
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_0_0()); 

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
    // InternalN4MFParser.g:5647:1: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5651:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5652:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5652:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5653:3: ruleProjectReference
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_1_1_0()); 

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
    // InternalN4MFParser.g:5662:1: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5666:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5667:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5667:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5668:3: ruleProjectReference
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_0_0()); 

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
    // InternalN4MFParser.g:5677:1: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5681:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5682:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5682:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5683:3: ruleProjectReference
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_1_1_0()); 

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
    // InternalN4MFParser.g:5692:1: rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0 : ( ruleProjectDependency ) ;
    public final void rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5696:1: ( ( ruleProjectDependency ) )
            // InternalN4MFParser.g:5697:2: ( ruleProjectDependency )
            {
            // InternalN4MFParser.g:5697:2: ( ruleProjectDependency )
            // InternalN4MFParser.g:5698:3: ruleProjectDependency
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
    // InternalN4MFParser.g:5707:1: rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1 : ( ruleProjectDependency ) ;
    public final void rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5711:1: ( ( ruleProjectDependency ) )
            // InternalN4MFParser.g:5712:2: ( ruleProjectDependency )
            {
            // InternalN4MFParser.g:5712:2: ( ruleProjectDependency )
            // InternalN4MFParser.g:5713:3: ruleProjectDependency
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
    // InternalN4MFParser.g:5722:1: rule__ProjectDescription__ImplementationIdAssignment_10_2 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectDescription__ImplementationIdAssignment_10_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5726:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:5727:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:5727:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:5728:3: ruleN4mfIdentifier
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
    // InternalN4MFParser.g:5737:1: rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5741:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5742:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5742:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5743:3: ruleProjectReference
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
    // InternalN4MFParser.g:5752:1: rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1 : ( ruleProjectReference ) ;
    public final void rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5756:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:5757:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:5757:2: ( ruleProjectReference )
            // InternalN4MFParser.g:5758:3: ruleProjectReference
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
    // InternalN4MFParser.g:5767:1: rule__ProjectDescription__InitModulesAssignment_12_2_0 : ( ruleBootstrapModule ) ;
    public final void rule__ProjectDescription__InitModulesAssignment_12_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5771:1: ( ( ruleBootstrapModule ) )
            // InternalN4MFParser.g:5772:2: ( ruleBootstrapModule )
            {
            // InternalN4MFParser.g:5772:2: ( ruleBootstrapModule )
            // InternalN4MFParser.g:5773:3: ruleBootstrapModule
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
    // InternalN4MFParser.g:5782:1: rule__ProjectDescription__InitModulesAssignment_12_2_1_1 : ( ruleBootstrapModule ) ;
    public final void rule__ProjectDescription__InitModulesAssignment_12_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5786:1: ( ( ruleBootstrapModule ) )
            // InternalN4MFParser.g:5787:2: ( ruleBootstrapModule )
            {
            // InternalN4MFParser.g:5787:2: ( ruleBootstrapModule )
            // InternalN4MFParser.g:5788:3: ruleBootstrapModule
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
    // InternalN4MFParser.g:5797:1: rule__ProjectDescription__ExecModuleAssignment_13_2 : ( ruleBootstrapModule ) ;
    public final void rule__ProjectDescription__ExecModuleAssignment_13_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5801:1: ( ( ruleBootstrapModule ) )
            // InternalN4MFParser.g:5802:2: ( ruleBootstrapModule )
            {
            // InternalN4MFParser.g:5802:2: ( ruleBootstrapModule )
            // InternalN4MFParser.g:5803:3: ruleBootstrapModule
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
    // InternalN4MFParser.g:5812:1: rule__ProjectDescription__OutputPathRawAssignment_14_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__OutputPathRawAssignment_14_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5816:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5817:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5817:2: ( RULE_STRING )
            // InternalN4MFParser.g:5818:3: RULE_STRING
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
    // InternalN4MFParser.g:5827:1: rule__ProjectDescription__LibraryPathsRawAssignment_15_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__LibraryPathsRawAssignment_15_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5831:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5832:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5832:2: ( RULE_STRING )
            // InternalN4MFParser.g:5833:3: RULE_STRING
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
    // InternalN4MFParser.g:5842:1: rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5846:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5847:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5847:2: ( RULE_STRING )
            // InternalN4MFParser.g:5848:3: RULE_STRING
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
    // InternalN4MFParser.g:5857:1: rule__ProjectDescription__ResourcePathsRawAssignment_16_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__ResourcePathsRawAssignment_16_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5861:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5862:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5862:2: ( RULE_STRING )
            // InternalN4MFParser.g:5863:3: RULE_STRING
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
    // InternalN4MFParser.g:5872:1: rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5876:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:5877:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:5877:2: ( RULE_STRING )
            // InternalN4MFParser.g:5878:3: RULE_STRING
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
    // InternalN4MFParser.g:5887:1: rule__ProjectDescription__SourceFragmentAssignment_17_2 : ( ruleSourceFragment ) ;
    public final void rule__ProjectDescription__SourceFragmentAssignment_17_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5891:1: ( ( ruleSourceFragment ) )
            // InternalN4MFParser.g:5892:2: ( ruleSourceFragment )
            {
            // InternalN4MFParser.g:5892:2: ( ruleSourceFragment )
            // InternalN4MFParser.g:5893:3: ruleSourceFragment
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
    // InternalN4MFParser.g:5902:1: rule__ProjectDescription__ModuleFiltersAssignment_18_2 : ( ruleModuleFilter ) ;
    public final void rule__ProjectDescription__ModuleFiltersAssignment_18_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5906:1: ( ( ruleModuleFilter ) )
            // InternalN4MFParser.g:5907:2: ( ruleModuleFilter )
            {
            // InternalN4MFParser.g:5907:2: ( ruleModuleFilter )
            // InternalN4MFParser.g:5908:3: ruleModuleFilter
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
    // InternalN4MFParser.g:5917:1: rule__ProjectDescription__TestedProjectsAssignment_19_2_0 : ( ruleProjectDependency ) ;
    public final void rule__ProjectDescription__TestedProjectsAssignment_19_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5921:1: ( ( ruleProjectDependency ) )
            // InternalN4MFParser.g:5922:2: ( ruleProjectDependency )
            {
            // InternalN4MFParser.g:5922:2: ( ruleProjectDependency )
            // InternalN4MFParser.g:5923:3: ruleProjectDependency
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsProjectDependencyParserRuleCall_19_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectDependency();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsProjectDependencyParserRuleCall_19_2_0_0()); 

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
    // InternalN4MFParser.g:5932:1: rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1 : ( ruleProjectDependency ) ;
    public final void rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5936:1: ( ( ruleProjectDependency ) )
            // InternalN4MFParser.g:5937:2: ( ruleProjectDependency )
            {
            // InternalN4MFParser.g:5937:2: ( ruleProjectDependency )
            // InternalN4MFParser.g:5938:3: ruleProjectDependency
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsProjectDependencyParserRuleCall_19_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectDependency();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsProjectDependencyParserRuleCall_19_2_1_1_0()); 

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
    // InternalN4MFParser.g:5947:1: rule__ProjectDescription__ModuleLoaderAssignment_20_2 : ( ruleModuleLoader ) ;
    public final void rule__ProjectDescription__ModuleLoaderAssignment_20_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5951:1: ( ( ruleModuleLoader ) )
            // InternalN4MFParser.g:5952:2: ( ruleModuleLoader )
            {
            // InternalN4MFParser.g:5952:2: ( ruleModuleLoader )
            // InternalN4MFParser.g:5953:3: ruleModuleLoader
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
    // InternalN4MFParser.g:5962:1: rule__DeclaredVersion__MajorAssignment_0 : ( RULE_INT ) ;
    public final void rule__DeclaredVersion__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5966:1: ( ( RULE_INT ) )
            // InternalN4MFParser.g:5967:2: ( RULE_INT )
            {
            // InternalN4MFParser.g:5967:2: ( RULE_INT )
            // InternalN4MFParser.g:5968:3: RULE_INT
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
    // InternalN4MFParser.g:5977:1: rule__DeclaredVersion__MinorAssignment_1_1 : ( RULE_INT ) ;
    public final void rule__DeclaredVersion__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5981:1: ( ( RULE_INT ) )
            // InternalN4MFParser.g:5982:2: ( RULE_INT )
            {
            // InternalN4MFParser.g:5982:2: ( RULE_INT )
            // InternalN4MFParser.g:5983:3: RULE_INT
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
    // InternalN4MFParser.g:5992:1: rule__DeclaredVersion__MicroAssignment_1_2_1 : ( RULE_INT ) ;
    public final void rule__DeclaredVersion__MicroAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5996:1: ( ( RULE_INT ) )
            // InternalN4MFParser.g:5997:2: ( RULE_INT )
            {
            // InternalN4MFParser.g:5997:2: ( RULE_INT )
            // InternalN4MFParser.g:5998:3: RULE_INT
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
    // InternalN4MFParser.g:6007:1: rule__DeclaredVersion__QualifierAssignment_2_1 : ( ruleN4mfIdentifier ) ;
    public final void rule__DeclaredVersion__QualifierAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6011:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6012:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6012:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6013:3: ruleN4mfIdentifier
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
    // InternalN4MFParser.g:6022:1: rule__SourceFragment__SourceFragmentTypeAssignment_0 : ( ruleSourceFragmentType ) ;
    public final void rule__SourceFragment__SourceFragmentTypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6026:1: ( ( ruleSourceFragmentType ) )
            // InternalN4MFParser.g:6027:2: ( ruleSourceFragmentType )
            {
            // InternalN4MFParser.g:6027:2: ( ruleSourceFragmentType )
            // InternalN4MFParser.g:6028:3: ruleSourceFragmentType
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
    // InternalN4MFParser.g:6037:1: rule__SourceFragment__PathsRawAssignment_2 : ( RULE_STRING ) ;
    public final void rule__SourceFragment__PathsRawAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6041:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6042:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6042:2: ( RULE_STRING )
            // InternalN4MFParser.g:6043:3: RULE_STRING
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
    // InternalN4MFParser.g:6052:1: rule__SourceFragment__PathsRawAssignment_3_1 : ( RULE_STRING ) ;
    public final void rule__SourceFragment__PathsRawAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6056:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6057:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6057:2: ( RULE_STRING )
            // InternalN4MFParser.g:6058:3: RULE_STRING
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
    // InternalN4MFParser.g:6067:1: rule__ModuleFilter__ModuleFilterTypeAssignment_0 : ( ruleModuleFilterType ) ;
    public final void rule__ModuleFilter__ModuleFilterTypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6071:1: ( ( ruleModuleFilterType ) )
            // InternalN4MFParser.g:6072:2: ( ruleModuleFilterType )
            {
            // InternalN4MFParser.g:6072:2: ( ruleModuleFilterType )
            // InternalN4MFParser.g:6073:3: ruleModuleFilterType
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
    // InternalN4MFParser.g:6082:1: rule__ModuleFilter__ModuleSpecifiersAssignment_2 : ( ruleModuleFilterSpecifier ) ;
    public final void rule__ModuleFilter__ModuleSpecifiersAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6086:1: ( ( ruleModuleFilterSpecifier ) )
            // InternalN4MFParser.g:6087:2: ( ruleModuleFilterSpecifier )
            {
            // InternalN4MFParser.g:6087:2: ( ruleModuleFilterSpecifier )
            // InternalN4MFParser.g:6088:3: ruleModuleFilterSpecifier
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
    // InternalN4MFParser.g:6097:1: rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 : ( ruleModuleFilterSpecifier ) ;
    public final void rule__ModuleFilter__ModuleSpecifiersAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6101:1: ( ( ruleModuleFilterSpecifier ) )
            // InternalN4MFParser.g:6102:2: ( ruleModuleFilterSpecifier )
            {
            // InternalN4MFParser.g:6102:2: ( ruleModuleFilterSpecifier )
            // InternalN4MFParser.g:6103:3: ruleModuleFilterSpecifier
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
    // InternalN4MFParser.g:6112:1: rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 : ( RULE_STRING ) ;
    public final void rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6116:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6117:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6117:2: ( RULE_STRING )
            // InternalN4MFParser.g:6118:3: RULE_STRING
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
    // InternalN4MFParser.g:6127:1: rule__BootstrapModule__SourcePathAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__BootstrapModule__SourcePathAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6131:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6132:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6132:2: ( RULE_STRING )
            // InternalN4MFParser.g:6133:3: RULE_STRING
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
    // InternalN4MFParser.g:6142:1: rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 : ( RULE_STRING ) ;
    public final void rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6146:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6147:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6147:2: ( RULE_STRING )
            // InternalN4MFParser.g:6148:3: RULE_STRING
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
    // InternalN4MFParser.g:6157:1: rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__ModuleFilterSpecifier__SourcePathAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6161:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6162:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6162:2: ( RULE_STRING )
            // InternalN4MFParser.g:6163:3: RULE_STRING
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
    // InternalN4MFParser.g:6172:1: rule__ProjectDependency__VersionConstraintAssignment_1 : ( ruleVersionConstraint ) ;
    public final void rule__ProjectDependency__VersionConstraintAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6176:1: ( ( ruleVersionConstraint ) )
            // InternalN4MFParser.g:6177:2: ( ruleVersionConstraint )
            {
            // InternalN4MFParser.g:6177:2: ( ruleVersionConstraint )
            // InternalN4MFParser.g:6178:3: ruleVersionConstraint
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
    // InternalN4MFParser.g:6187:1: rule__ProjectDependency__DeclaredScopeAssignment_2 : ( ruleProjectDependencyScope ) ;
    public final void rule__ProjectDependency__DeclaredScopeAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6191:1: ( ( ruleProjectDependencyScope ) )
            // InternalN4MFParser.g:6192:2: ( ruleProjectDependencyScope )
            {
            // InternalN4MFParser.g:6192:2: ( ruleProjectDependencyScope )
            // InternalN4MFParser.g:6193:3: ruleProjectDependencyScope
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
    // InternalN4MFParser.g:6202:1: rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6206:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6207:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6207:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6208:3: ruleN4mfIdentifier
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
    // InternalN4MFParser.g:6217:1: rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6221:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6222:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6222:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6223:3: ruleN4mfIdentifier
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
    // InternalN4MFParser.g:6232:1: rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 : ( ( LeftParenthesis ) ) ;
    public final void rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6236:1: ( ( ( LeftParenthesis ) ) )
            // InternalN4MFParser.g:6237:2: ( ( LeftParenthesis ) )
            {
            // InternalN4MFParser.g:6237:2: ( ( LeftParenthesis ) )
            // InternalN4MFParser.g:6238:3: ( LeftParenthesis )
            {
             before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); 
            // InternalN4MFParser.g:6239:3: ( LeftParenthesis )
            // InternalN4MFParser.g:6240:4: LeftParenthesis
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
    // InternalN4MFParser.g:6251:1: rule__VersionConstraint__LowerVersionAssignment_0_1 : ( ruleDeclaredVersion ) ;
    public final void rule__VersionConstraint__LowerVersionAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6255:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6256:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6256:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6257:3: ruleDeclaredVersion
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
    // InternalN4MFParser.g:6266:1: rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 : ( ruleDeclaredVersion ) ;
    public final void rule__VersionConstraint__UpperVersionAssignment_0_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6270:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6271:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6271:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6272:3: ruleDeclaredVersion
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
    // InternalN4MFParser.g:6281:1: rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 : ( ( RightParenthesis ) ) ;
    public final void rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6285:1: ( ( ( RightParenthesis ) ) )
            // InternalN4MFParser.g:6286:2: ( ( RightParenthesis ) )
            {
            // InternalN4MFParser.g:6286:2: ( ( RightParenthesis ) )
            // InternalN4MFParser.g:6287:3: ( RightParenthesis )
            {
             before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); 
            // InternalN4MFParser.g:6288:3: ( RightParenthesis )
            // InternalN4MFParser.g:6289:4: RightParenthesis
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
    // InternalN4MFParser.g:6300:1: rule__VersionConstraint__LowerVersionAssignment_1 : ( ruleDeclaredVersion ) ;
    public final void rule__VersionConstraint__LowerVersionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6304:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6305:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6305:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6306:3: ruleDeclaredVersion
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
    static final String dfa_2s = "\1\uffff\13\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24";
    static final String dfa_3s = "\1\10\13\4\1\47\3\4\1\46\2\4\2\uffff\2\4";
    static final String dfa_4s = "\1\67\13\70\1\47\3\70\1\46\2\70\2\uffff\2\70";
    static final String dfa_5s = "\23\uffff\1\1\1\2\2\uffff";
    static final String dfa_6s = "\27\uffff}>";
    static final String[] dfa_7s = {
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
            return "4572:2: ( rule__ProjectIdWithOptionalVendor__Group_0__0 )?";
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
            return "4951:3: ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_6__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_7__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_8__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_9__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_11__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_12__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_13__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_19__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) )";
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
            return "5281:2: ( rule__ProjectDescription__UnorderedGroup__1 )?";
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
            return "5293:2: ( rule__ProjectDescription__UnorderedGroup__2 )?";
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
            return "5305:2: ( rule__ProjectDescription__UnorderedGroup__3 )?";
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
            return "5317:2: ( rule__ProjectDescription__UnorderedGroup__4 )?";
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
            return "5329:2: ( rule__ProjectDescription__UnorderedGroup__5 )?";
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
            return "5341:2: ( rule__ProjectDescription__UnorderedGroup__6 )?";
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
            return "5353:2: ( rule__ProjectDescription__UnorderedGroup__7 )?";
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
            return "5365:2: ( rule__ProjectDescription__UnorderedGroup__8 )?";
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
            return "5377:2: ( rule__ProjectDescription__UnorderedGroup__9 )?";
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
            return "5389:2: ( rule__ProjectDescription__UnorderedGroup__10 )?";
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
            return "5401:2: ( rule__ProjectDescription__UnorderedGroup__11 )?";
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
            return "5413:2: ( rule__ProjectDescription__UnorderedGroup__12 )?";
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
            return "5425:2: ( rule__ProjectDescription__UnorderedGroup__13 )?";
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
            return "5437:2: ( rule__ProjectDescription__UnorderedGroup__14 )?";
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
            return "5449:2: ( rule__ProjectDescription__UnorderedGroup__15 )?";
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
            return "5461:2: ( rule__ProjectDescription__UnorderedGroup__16 )?";
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
            return "5473:2: ( rule__ProjectDescription__UnorderedGroup__17 )?";
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
            return "5485:2: ( rule__ProjectDescription__UnorderedGroup__18 )?";
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
            return "5497:2: ( rule__ProjectDescription__UnorderedGroup__19 )?";
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
            return "5509:2: ( rule__ProjectDescription__UnorderedGroup__20 )?";
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
