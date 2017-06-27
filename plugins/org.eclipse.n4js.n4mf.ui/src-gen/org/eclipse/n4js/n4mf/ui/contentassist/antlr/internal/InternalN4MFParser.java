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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ExtendedRuntimeEnvironment", "ProvidedRuntimeLibraries", "RequiredRuntimeLibraries", "ImplementedProjects", "ProjectDependencies", "RuntimeEnvironment", "ImplementationId", "ProjectVersion", "TestedProjects", "RuntimeLibrary", "ModuleFilters", "ModuleLoader", "NoModuleWrap", "Node_builtin", "InitModules", "ProjectType", "Application", "ExecModule", "MainModule", "VendorName", "NoValidate", "Libraries", "ProjectId", "Resources", "Processor", "VendorId", "Commonjs", "External", "Sources", "Compile", "Content", "Library", "Output", "Source", "KW_System", "N4js", "Test", "User", "API", "In", "LeftParenthesis", "RightParenthesis", "Comma", "HyphenMinus", "FullStop", "Colon", "LeftSquareBracket", "RightSquareBracket", "LeftCurlyBracket", "RightCurlyBracket", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int TestedProjects=12;
    public static final int KW_System=38;
    public static final int ProjectDependencies=8;
    public static final int ExecModule=21;
    public static final int LeftParenthesis=44;
    public static final int Test=40;
    public static final int ProjectVersion=11;
    public static final int Libraries=25;
    public static final int ModuleFilters=14;
    public static final int RightSquareBracket=51;
    public static final int VendorName=23;
    public static final int RuntimeEnvironment=9;
    public static final int RULE_ID=54;
    public static final int NoValidate=24;
    public static final int NoModuleWrap=16;
    public static final int RightParenthesis=45;
    public static final int Sources=32;
    public static final int Content=34;
    public static final int RULE_INT=55;
    public static final int ProjectType=19;
    public static final int External=31;
    public static final int RULE_ML_COMMENT=57;
    public static final int LeftSquareBracket=50;
    public static final int Resources=27;
    public static final int Library=35;
    public static final int Application=20;
    public static final int ImplementedProjects=7;
    public static final int Processor=28;
    public static final int User=41;
    public static final int In=43;
    public static final int VendorId=29;
    public static final int RULE_STRING=56;
    public static final int Node_builtin=17;
    public static final int N4js=39;
    public static final int Compile=33;
    public static final int Source=37;
    public static final int RULE_SL_COMMENT=58;
    public static final int ImplementationId=10;
    public static final int Comma=46;
    public static final int HyphenMinus=47;
    public static final int Output=36;
    public static final int MainModule=22;
    public static final int Colon=49;
    public static final int RightCurlyBracket=53;
    public static final int EOF=-1;
    public static final int ExtendedRuntimeEnvironment=4;
    public static final int FullStop=48;
    public static final int ModuleLoader=15;
    public static final int Commonjs=30;
    public static final int RULE_WS=59;
    public static final int ProjectId=26;
    public static final int LeftCurlyBracket=52;
    public static final int ProvidedRuntimeLibraries=5;
    public static final int RULE_ANY_OTHER=60;
    public static final int RequiredRuntimeLibraries=6;
    public static final int InitModules=18;
    public static final int API=42;
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
    // InternalN4MFParser.g:112:1: entryRuleProjectDescription : ruleProjectDescription EOF ;
    public final void entryRuleProjectDescription() throws RecognitionException {
        try {
            // InternalN4MFParser.g:113:1: ( ruleProjectDescription EOF )
            // InternalN4MFParser.g:114:1: ruleProjectDescription EOF
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
    // InternalN4MFParser.g:121:1: ruleProjectDescription : ( ( rule__ProjectDescription__UnorderedGroup ) ) ;
    public final void ruleProjectDescription() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:125:2: ( ( ( rule__ProjectDescription__UnorderedGroup ) ) )
            // InternalN4MFParser.g:126:2: ( ( rule__ProjectDescription__UnorderedGroup ) )
            {
            // InternalN4MFParser.g:126:2: ( ( rule__ProjectDescription__UnorderedGroup ) )
            // InternalN4MFParser.g:127:3: ( rule__ProjectDescription__UnorderedGroup )
            {
             before(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup()); 
            // InternalN4MFParser.g:128:3: ( rule__ProjectDescription__UnorderedGroup )
            // InternalN4MFParser.g:128:4: rule__ProjectDescription__UnorderedGroup
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


    // $ANTLR start "entryRuleExecModule"
    // InternalN4MFParser.g:137:1: entryRuleExecModule : ruleExecModule EOF ;
    public final void entryRuleExecModule() throws RecognitionException {
        try {
            // InternalN4MFParser.g:138:1: ( ruleExecModule EOF )
            // InternalN4MFParser.g:139:1: ruleExecModule EOF
            {
             before(grammarAccess.getExecModuleRule()); 
            pushFollow(FOLLOW_1);
            ruleExecModule();

            state._fsp--;

             after(grammarAccess.getExecModuleRule()); 
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
    // $ANTLR end "entryRuleExecModule"


    // $ANTLR start "ruleExecModule"
    // InternalN4MFParser.g:146:1: ruleExecModule : ( ( rule__ExecModule__Group__0 ) ) ;
    public final void ruleExecModule() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:150:2: ( ( ( rule__ExecModule__Group__0 ) ) )
            // InternalN4MFParser.g:151:2: ( ( rule__ExecModule__Group__0 ) )
            {
            // InternalN4MFParser.g:151:2: ( ( rule__ExecModule__Group__0 ) )
            // InternalN4MFParser.g:152:3: ( rule__ExecModule__Group__0 )
            {
             before(grammarAccess.getExecModuleAccess().getGroup()); 
            // InternalN4MFParser.g:153:3: ( rule__ExecModule__Group__0 )
            // InternalN4MFParser.g:153:4: rule__ExecModule__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ExecModule__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getExecModuleAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleExecModule"


    // $ANTLR start "entryRuleTestedProjects"
    // InternalN4MFParser.g:162:1: entryRuleTestedProjects : ruleTestedProjects EOF ;
    public final void entryRuleTestedProjects() throws RecognitionException {
        try {
            // InternalN4MFParser.g:163:1: ( ruleTestedProjects EOF )
            // InternalN4MFParser.g:164:1: ruleTestedProjects EOF
            {
             before(grammarAccess.getTestedProjectsRule()); 
            pushFollow(FOLLOW_1);
            ruleTestedProjects();

            state._fsp--;

             after(grammarAccess.getTestedProjectsRule()); 
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
    // $ANTLR end "entryRuleTestedProjects"


    // $ANTLR start "ruleTestedProjects"
    // InternalN4MFParser.g:171:1: ruleTestedProjects : ( ( rule__TestedProjects__Group__0 ) ) ;
    public final void ruleTestedProjects() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:175:2: ( ( ( rule__TestedProjects__Group__0 ) ) )
            // InternalN4MFParser.g:176:2: ( ( rule__TestedProjects__Group__0 ) )
            {
            // InternalN4MFParser.g:176:2: ( ( rule__TestedProjects__Group__0 ) )
            // InternalN4MFParser.g:177:3: ( rule__TestedProjects__Group__0 )
            {
             before(grammarAccess.getTestedProjectsAccess().getGroup()); 
            // InternalN4MFParser.g:178:3: ( rule__TestedProjects__Group__0 )
            // InternalN4MFParser.g:178:4: rule__TestedProjects__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTestedProjectsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTestedProjects"


    // $ANTLR start "entryRuleInitModules"
    // InternalN4MFParser.g:187:1: entryRuleInitModules : ruleInitModules EOF ;
    public final void entryRuleInitModules() throws RecognitionException {
        try {
            // InternalN4MFParser.g:188:1: ( ruleInitModules EOF )
            // InternalN4MFParser.g:189:1: ruleInitModules EOF
            {
             before(grammarAccess.getInitModulesRule()); 
            pushFollow(FOLLOW_1);
            ruleInitModules();

            state._fsp--;

             after(grammarAccess.getInitModulesRule()); 
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
    // $ANTLR end "entryRuleInitModules"


    // $ANTLR start "ruleInitModules"
    // InternalN4MFParser.g:196:1: ruleInitModules : ( ( rule__InitModules__Group__0 ) ) ;
    public final void ruleInitModules() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:200:2: ( ( ( rule__InitModules__Group__0 ) ) )
            // InternalN4MFParser.g:201:2: ( ( rule__InitModules__Group__0 ) )
            {
            // InternalN4MFParser.g:201:2: ( ( rule__InitModules__Group__0 ) )
            // InternalN4MFParser.g:202:3: ( rule__InitModules__Group__0 )
            {
             before(grammarAccess.getInitModulesAccess().getGroup()); 
            // InternalN4MFParser.g:203:3: ( rule__InitModules__Group__0 )
            // InternalN4MFParser.g:203:4: rule__InitModules__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__InitModules__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getInitModulesAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleInitModules"


    // $ANTLR start "entryRuleImplementedProjects"
    // InternalN4MFParser.g:212:1: entryRuleImplementedProjects : ruleImplementedProjects EOF ;
    public final void entryRuleImplementedProjects() throws RecognitionException {
        try {
            // InternalN4MFParser.g:213:1: ( ruleImplementedProjects EOF )
            // InternalN4MFParser.g:214:1: ruleImplementedProjects EOF
            {
             before(grammarAccess.getImplementedProjectsRule()); 
            pushFollow(FOLLOW_1);
            ruleImplementedProjects();

            state._fsp--;

             after(grammarAccess.getImplementedProjectsRule()); 
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
    // $ANTLR end "entryRuleImplementedProjects"


    // $ANTLR start "ruleImplementedProjects"
    // InternalN4MFParser.g:221:1: ruleImplementedProjects : ( ( rule__ImplementedProjects__Group__0 ) ) ;
    public final void ruleImplementedProjects() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:225:2: ( ( ( rule__ImplementedProjects__Group__0 ) ) )
            // InternalN4MFParser.g:226:2: ( ( rule__ImplementedProjects__Group__0 ) )
            {
            // InternalN4MFParser.g:226:2: ( ( rule__ImplementedProjects__Group__0 ) )
            // InternalN4MFParser.g:227:3: ( rule__ImplementedProjects__Group__0 )
            {
             before(grammarAccess.getImplementedProjectsAccess().getGroup()); 
            // InternalN4MFParser.g:228:3: ( rule__ImplementedProjects__Group__0 )
            // InternalN4MFParser.g:228:4: rule__ImplementedProjects__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getImplementedProjectsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleImplementedProjects"


    // $ANTLR start "entryRuleProjectDependencies"
    // InternalN4MFParser.g:237:1: entryRuleProjectDependencies : ruleProjectDependencies EOF ;
    public final void entryRuleProjectDependencies() throws RecognitionException {
        try {
            // InternalN4MFParser.g:238:1: ( ruleProjectDependencies EOF )
            // InternalN4MFParser.g:239:1: ruleProjectDependencies EOF
            {
             before(grammarAccess.getProjectDependenciesRule()); 
            pushFollow(FOLLOW_1);
            ruleProjectDependencies();

            state._fsp--;

             after(grammarAccess.getProjectDependenciesRule()); 
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
    // $ANTLR end "entryRuleProjectDependencies"


    // $ANTLR start "ruleProjectDependencies"
    // InternalN4MFParser.g:246:1: ruleProjectDependencies : ( ( rule__ProjectDependencies__Group__0 ) ) ;
    public final void ruleProjectDependencies() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:250:2: ( ( ( rule__ProjectDependencies__Group__0 ) ) )
            // InternalN4MFParser.g:251:2: ( ( rule__ProjectDependencies__Group__0 ) )
            {
            // InternalN4MFParser.g:251:2: ( ( rule__ProjectDependencies__Group__0 ) )
            // InternalN4MFParser.g:252:3: ( rule__ProjectDependencies__Group__0 )
            {
             before(grammarAccess.getProjectDependenciesAccess().getGroup()); 
            // InternalN4MFParser.g:253:3: ( rule__ProjectDependencies__Group__0 )
            // InternalN4MFParser.g:253:4: rule__ProjectDependencies__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDependenciesAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProjectDependencies"


    // $ANTLR start "entryRuleProvidedRuntimeLibraries"
    // InternalN4MFParser.g:262:1: entryRuleProvidedRuntimeLibraries : ruleProvidedRuntimeLibraries EOF ;
    public final void entryRuleProvidedRuntimeLibraries() throws RecognitionException {
        try {
            // InternalN4MFParser.g:263:1: ( ruleProvidedRuntimeLibraries EOF )
            // InternalN4MFParser.g:264:1: ruleProvidedRuntimeLibraries EOF
            {
             before(grammarAccess.getProvidedRuntimeLibrariesRule()); 
            pushFollow(FOLLOW_1);
            ruleProvidedRuntimeLibraries();

            state._fsp--;

             after(grammarAccess.getProvidedRuntimeLibrariesRule()); 
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
    // $ANTLR end "entryRuleProvidedRuntimeLibraries"


    // $ANTLR start "ruleProvidedRuntimeLibraries"
    // InternalN4MFParser.g:271:1: ruleProvidedRuntimeLibraries : ( ( rule__ProvidedRuntimeLibraries__Group__0 ) ) ;
    public final void ruleProvidedRuntimeLibraries() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:275:2: ( ( ( rule__ProvidedRuntimeLibraries__Group__0 ) ) )
            // InternalN4MFParser.g:276:2: ( ( rule__ProvidedRuntimeLibraries__Group__0 ) )
            {
            // InternalN4MFParser.g:276:2: ( ( rule__ProvidedRuntimeLibraries__Group__0 ) )
            // InternalN4MFParser.g:277:3: ( rule__ProvidedRuntimeLibraries__Group__0 )
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup()); 
            // InternalN4MFParser.g:278:3: ( rule__ProvidedRuntimeLibraries__Group__0 )
            // InternalN4MFParser.g:278:4: rule__ProvidedRuntimeLibraries__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProvidedRuntimeLibraries"


    // $ANTLR start "entryRuleRequiredRuntimeLibraries"
    // InternalN4MFParser.g:287:1: entryRuleRequiredRuntimeLibraries : ruleRequiredRuntimeLibraries EOF ;
    public final void entryRuleRequiredRuntimeLibraries() throws RecognitionException {
        try {
            // InternalN4MFParser.g:288:1: ( ruleRequiredRuntimeLibraries EOF )
            // InternalN4MFParser.g:289:1: ruleRequiredRuntimeLibraries EOF
            {
             before(grammarAccess.getRequiredRuntimeLibrariesRule()); 
            pushFollow(FOLLOW_1);
            ruleRequiredRuntimeLibraries();

            state._fsp--;

             after(grammarAccess.getRequiredRuntimeLibrariesRule()); 
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
    // $ANTLR end "entryRuleRequiredRuntimeLibraries"


    // $ANTLR start "ruleRequiredRuntimeLibraries"
    // InternalN4MFParser.g:296:1: ruleRequiredRuntimeLibraries : ( ( rule__RequiredRuntimeLibraries__Group__0 ) ) ;
    public final void ruleRequiredRuntimeLibraries() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:300:2: ( ( ( rule__RequiredRuntimeLibraries__Group__0 ) ) )
            // InternalN4MFParser.g:301:2: ( ( rule__RequiredRuntimeLibraries__Group__0 ) )
            {
            // InternalN4MFParser.g:301:2: ( ( rule__RequiredRuntimeLibraries__Group__0 ) )
            // InternalN4MFParser.g:302:3: ( rule__RequiredRuntimeLibraries__Group__0 )
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup()); 
            // InternalN4MFParser.g:303:3: ( rule__RequiredRuntimeLibraries__Group__0 )
            // InternalN4MFParser.g:303:4: rule__RequiredRuntimeLibraries__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRequiredRuntimeLibraries"


    // $ANTLR start "entryRuleExtendedRuntimeEnvironment"
    // InternalN4MFParser.g:312:1: entryRuleExtendedRuntimeEnvironment : ruleExtendedRuntimeEnvironment EOF ;
    public final void entryRuleExtendedRuntimeEnvironment() throws RecognitionException {
        try {
            // InternalN4MFParser.g:313:1: ( ruleExtendedRuntimeEnvironment EOF )
            // InternalN4MFParser.g:314:1: ruleExtendedRuntimeEnvironment EOF
            {
             before(grammarAccess.getExtendedRuntimeEnvironmentRule()); 
            pushFollow(FOLLOW_1);
            ruleExtendedRuntimeEnvironment();

            state._fsp--;

             after(grammarAccess.getExtendedRuntimeEnvironmentRule()); 
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
    // $ANTLR end "entryRuleExtendedRuntimeEnvironment"


    // $ANTLR start "ruleExtendedRuntimeEnvironment"
    // InternalN4MFParser.g:321:1: ruleExtendedRuntimeEnvironment : ( ( rule__ExtendedRuntimeEnvironment__Group__0 ) ) ;
    public final void ruleExtendedRuntimeEnvironment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:325:2: ( ( ( rule__ExtendedRuntimeEnvironment__Group__0 ) ) )
            // InternalN4MFParser.g:326:2: ( ( rule__ExtendedRuntimeEnvironment__Group__0 ) )
            {
            // InternalN4MFParser.g:326:2: ( ( rule__ExtendedRuntimeEnvironment__Group__0 ) )
            // InternalN4MFParser.g:327:3: ( rule__ExtendedRuntimeEnvironment__Group__0 )
            {
             before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getGroup()); 
            // InternalN4MFParser.g:328:3: ( rule__ExtendedRuntimeEnvironment__Group__0 )
            // InternalN4MFParser.g:328:4: rule__ExtendedRuntimeEnvironment__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ExtendedRuntimeEnvironment__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleExtendedRuntimeEnvironment"


    // $ANTLR start "entryRuleDeclaredVersion"
    // InternalN4MFParser.g:337:1: entryRuleDeclaredVersion : ruleDeclaredVersion EOF ;
    public final void entryRuleDeclaredVersion() throws RecognitionException {
        try {
            // InternalN4MFParser.g:338:1: ( ruleDeclaredVersion EOF )
            // InternalN4MFParser.g:339:1: ruleDeclaredVersion EOF
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
    // InternalN4MFParser.g:346:1: ruleDeclaredVersion : ( ( rule__DeclaredVersion__Group__0 ) ) ;
    public final void ruleDeclaredVersion() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:350:2: ( ( ( rule__DeclaredVersion__Group__0 ) ) )
            // InternalN4MFParser.g:351:2: ( ( rule__DeclaredVersion__Group__0 ) )
            {
            // InternalN4MFParser.g:351:2: ( ( rule__DeclaredVersion__Group__0 ) )
            // InternalN4MFParser.g:352:3: ( rule__DeclaredVersion__Group__0 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup()); 
            // InternalN4MFParser.g:353:3: ( rule__DeclaredVersion__Group__0 )
            // InternalN4MFParser.g:353:4: rule__DeclaredVersion__Group__0
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
    // InternalN4MFParser.g:362:1: entryRuleSourceFragment : ruleSourceFragment EOF ;
    public final void entryRuleSourceFragment() throws RecognitionException {
        try {
            // InternalN4MFParser.g:363:1: ( ruleSourceFragment EOF )
            // InternalN4MFParser.g:364:1: ruleSourceFragment EOF
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
    // InternalN4MFParser.g:371:1: ruleSourceFragment : ( ( rule__SourceFragment__Group__0 ) ) ;
    public final void ruleSourceFragment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:375:2: ( ( ( rule__SourceFragment__Group__0 ) ) )
            // InternalN4MFParser.g:376:2: ( ( rule__SourceFragment__Group__0 ) )
            {
            // InternalN4MFParser.g:376:2: ( ( rule__SourceFragment__Group__0 ) )
            // InternalN4MFParser.g:377:3: ( rule__SourceFragment__Group__0 )
            {
             before(grammarAccess.getSourceFragmentAccess().getGroup()); 
            // InternalN4MFParser.g:378:3: ( rule__SourceFragment__Group__0 )
            // InternalN4MFParser.g:378:4: rule__SourceFragment__Group__0
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
    // InternalN4MFParser.g:387:1: entryRuleModuleFilter : ruleModuleFilter EOF ;
    public final void entryRuleModuleFilter() throws RecognitionException {
        try {
            // InternalN4MFParser.g:388:1: ( ruleModuleFilter EOF )
            // InternalN4MFParser.g:389:1: ruleModuleFilter EOF
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
    // InternalN4MFParser.g:396:1: ruleModuleFilter : ( ( rule__ModuleFilter__Group__0 ) ) ;
    public final void ruleModuleFilter() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:400:2: ( ( ( rule__ModuleFilter__Group__0 ) ) )
            // InternalN4MFParser.g:401:2: ( ( rule__ModuleFilter__Group__0 ) )
            {
            // InternalN4MFParser.g:401:2: ( ( rule__ModuleFilter__Group__0 ) )
            // InternalN4MFParser.g:402:3: ( rule__ModuleFilter__Group__0 )
            {
             before(grammarAccess.getModuleFilterAccess().getGroup()); 
            // InternalN4MFParser.g:403:3: ( rule__ModuleFilter__Group__0 )
            // InternalN4MFParser.g:403:4: rule__ModuleFilter__Group__0
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
    // InternalN4MFParser.g:412:1: entryRuleBootstrapModule : ruleBootstrapModule EOF ;
    public final void entryRuleBootstrapModule() throws RecognitionException {
        try {
            // InternalN4MFParser.g:413:1: ( ruleBootstrapModule EOF )
            // InternalN4MFParser.g:414:1: ruleBootstrapModule EOF
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
    // InternalN4MFParser.g:421:1: ruleBootstrapModule : ( ( rule__BootstrapModule__Group__0 ) ) ;
    public final void ruleBootstrapModule() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:425:2: ( ( ( rule__BootstrapModule__Group__0 ) ) )
            // InternalN4MFParser.g:426:2: ( ( rule__BootstrapModule__Group__0 ) )
            {
            // InternalN4MFParser.g:426:2: ( ( rule__BootstrapModule__Group__0 ) )
            // InternalN4MFParser.g:427:3: ( rule__BootstrapModule__Group__0 )
            {
             before(grammarAccess.getBootstrapModuleAccess().getGroup()); 
            // InternalN4MFParser.g:428:3: ( rule__BootstrapModule__Group__0 )
            // InternalN4MFParser.g:428:4: rule__BootstrapModule__Group__0
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
    // InternalN4MFParser.g:437:1: entryRuleModuleFilterSpecifier : ruleModuleFilterSpecifier EOF ;
    public final void entryRuleModuleFilterSpecifier() throws RecognitionException {
        try {
            // InternalN4MFParser.g:438:1: ( ruleModuleFilterSpecifier EOF )
            // InternalN4MFParser.g:439:1: ruleModuleFilterSpecifier EOF
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
    // InternalN4MFParser.g:446:1: ruleModuleFilterSpecifier : ( ( rule__ModuleFilterSpecifier__Group__0 ) ) ;
    public final void ruleModuleFilterSpecifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:450:2: ( ( ( rule__ModuleFilterSpecifier__Group__0 ) ) )
            // InternalN4MFParser.g:451:2: ( ( rule__ModuleFilterSpecifier__Group__0 ) )
            {
            // InternalN4MFParser.g:451:2: ( ( rule__ModuleFilterSpecifier__Group__0 ) )
            // InternalN4MFParser.g:452:3: ( rule__ModuleFilterSpecifier__Group__0 )
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getGroup()); 
            // InternalN4MFParser.g:453:3: ( rule__ModuleFilterSpecifier__Group__0 )
            // InternalN4MFParser.g:453:4: rule__ModuleFilterSpecifier__Group__0
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
    // InternalN4MFParser.g:462:1: entryRuleProvidedRuntimeLibraryDependency : ruleProvidedRuntimeLibraryDependency EOF ;
    public final void entryRuleProvidedRuntimeLibraryDependency() throws RecognitionException {
        try {
            // InternalN4MFParser.g:463:1: ( ruleProvidedRuntimeLibraryDependency EOF )
            // InternalN4MFParser.g:464:1: ruleProvidedRuntimeLibraryDependency EOF
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
    // InternalN4MFParser.g:471:1: ruleProvidedRuntimeLibraryDependency : ( ( rule__ProvidedRuntimeLibraryDependency__ProjectAssignment ) ) ;
    public final void ruleProvidedRuntimeLibraryDependency() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:475:2: ( ( ( rule__ProvidedRuntimeLibraryDependency__ProjectAssignment ) ) )
            // InternalN4MFParser.g:476:2: ( ( rule__ProvidedRuntimeLibraryDependency__ProjectAssignment ) )
            {
            // InternalN4MFParser.g:476:2: ( ( rule__ProvidedRuntimeLibraryDependency__ProjectAssignment ) )
            // InternalN4MFParser.g:477:3: ( rule__ProvidedRuntimeLibraryDependency__ProjectAssignment )
            {
             before(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectAssignment()); 
            // InternalN4MFParser.g:478:3: ( rule__ProvidedRuntimeLibraryDependency__ProjectAssignment )
            // InternalN4MFParser.g:478:4: rule__ProvidedRuntimeLibraryDependency__ProjectAssignment
            {
            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraryDependency__ProjectAssignment();

            state._fsp--;


            }

             after(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectAssignment()); 

            }


            }

        }
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
    // InternalN4MFParser.g:487:1: entryRuleRequiredRuntimeLibraryDependency : ruleRequiredRuntimeLibraryDependency EOF ;
    public final void entryRuleRequiredRuntimeLibraryDependency() throws RecognitionException {
        try {
            // InternalN4MFParser.g:488:1: ( ruleRequiredRuntimeLibraryDependency EOF )
            // InternalN4MFParser.g:489:1: ruleRequiredRuntimeLibraryDependency EOF
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
    // InternalN4MFParser.g:496:1: ruleRequiredRuntimeLibraryDependency : ( ( rule__RequiredRuntimeLibraryDependency__ProjectAssignment ) ) ;
    public final void ruleRequiredRuntimeLibraryDependency() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:500:2: ( ( ( rule__RequiredRuntimeLibraryDependency__ProjectAssignment ) ) )
            // InternalN4MFParser.g:501:2: ( ( rule__RequiredRuntimeLibraryDependency__ProjectAssignment ) )
            {
            // InternalN4MFParser.g:501:2: ( ( rule__RequiredRuntimeLibraryDependency__ProjectAssignment ) )
            // InternalN4MFParser.g:502:3: ( rule__RequiredRuntimeLibraryDependency__ProjectAssignment )
            {
             before(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectAssignment()); 
            // InternalN4MFParser.g:503:3: ( rule__RequiredRuntimeLibraryDependency__ProjectAssignment )
            // InternalN4MFParser.g:503:4: rule__RequiredRuntimeLibraryDependency__ProjectAssignment
            {
            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraryDependency__ProjectAssignment();

            state._fsp--;


            }

             after(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectAssignment()); 

            }


            }

        }
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
    // InternalN4MFParser.g:512:1: entryRuleTestedProject : ruleTestedProject EOF ;
    public final void entryRuleTestedProject() throws RecognitionException {
        try {
            // InternalN4MFParser.g:513:1: ( ruleTestedProject EOF )
            // InternalN4MFParser.g:514:1: ruleTestedProject EOF
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
    // InternalN4MFParser.g:521:1: ruleTestedProject : ( ( rule__TestedProject__ProjectAssignment ) ) ;
    public final void ruleTestedProject() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:525:2: ( ( ( rule__TestedProject__ProjectAssignment ) ) )
            // InternalN4MFParser.g:526:2: ( ( rule__TestedProject__ProjectAssignment ) )
            {
            // InternalN4MFParser.g:526:2: ( ( rule__TestedProject__ProjectAssignment ) )
            // InternalN4MFParser.g:527:3: ( rule__TestedProject__ProjectAssignment )
            {
             before(grammarAccess.getTestedProjectAccess().getProjectAssignment()); 
            // InternalN4MFParser.g:528:3: ( rule__TestedProject__ProjectAssignment )
            // InternalN4MFParser.g:528:4: rule__TestedProject__ProjectAssignment
            {
            pushFollow(FOLLOW_2);
            rule__TestedProject__ProjectAssignment();

            state._fsp--;


            }

             after(grammarAccess.getTestedProjectAccess().getProjectAssignment()); 

            }


            }

        }
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
    // InternalN4MFParser.g:537:1: entryRuleProjectReference : ruleProjectReference EOF ;
    public final void entryRuleProjectReference() throws RecognitionException {
        try {
            // InternalN4MFParser.g:538:1: ( ruleProjectReference EOF )
            // InternalN4MFParser.g:539:1: ruleProjectReference EOF
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
    // InternalN4MFParser.g:546:1: ruleProjectReference : ( ( rule__ProjectReference__ProjectAssignment ) ) ;
    public final void ruleProjectReference() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:550:2: ( ( ( rule__ProjectReference__ProjectAssignment ) ) )
            // InternalN4MFParser.g:551:2: ( ( rule__ProjectReference__ProjectAssignment ) )
            {
            // InternalN4MFParser.g:551:2: ( ( rule__ProjectReference__ProjectAssignment ) )
            // InternalN4MFParser.g:552:3: ( rule__ProjectReference__ProjectAssignment )
            {
             before(grammarAccess.getProjectReferenceAccess().getProjectAssignment()); 
            // InternalN4MFParser.g:553:3: ( rule__ProjectReference__ProjectAssignment )
            // InternalN4MFParser.g:553:4: rule__ProjectReference__ProjectAssignment
            {
            pushFollow(FOLLOW_2);
            rule__ProjectReference__ProjectAssignment();

            state._fsp--;


            }

             after(grammarAccess.getProjectReferenceAccess().getProjectAssignment()); 

            }


            }

        }
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
    // InternalN4MFParser.g:562:1: entryRuleProjectDependency : ruleProjectDependency EOF ;
    public final void entryRuleProjectDependency() throws RecognitionException {
        try {
            // InternalN4MFParser.g:563:1: ( ruleProjectDependency EOF )
            // InternalN4MFParser.g:564:1: ruleProjectDependency EOF
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
    // InternalN4MFParser.g:571:1: ruleProjectDependency : ( ( rule__ProjectDependency__Group__0 ) ) ;
    public final void ruleProjectDependency() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:575:2: ( ( ( rule__ProjectDependency__Group__0 ) ) )
            // InternalN4MFParser.g:576:2: ( ( rule__ProjectDependency__Group__0 ) )
            {
            // InternalN4MFParser.g:576:2: ( ( rule__ProjectDependency__Group__0 ) )
            // InternalN4MFParser.g:577:3: ( rule__ProjectDependency__Group__0 )
            {
             before(grammarAccess.getProjectDependencyAccess().getGroup()); 
            // InternalN4MFParser.g:578:3: ( rule__ProjectDependency__Group__0 )
            // InternalN4MFParser.g:578:4: rule__ProjectDependency__Group__0
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


    // $ANTLR start "entryRuleSimpleProjectDescription"
    // InternalN4MFParser.g:587:1: entryRuleSimpleProjectDescription : ruleSimpleProjectDescription EOF ;
    public final void entryRuleSimpleProjectDescription() throws RecognitionException {
        try {
            // InternalN4MFParser.g:588:1: ( ruleSimpleProjectDescription EOF )
            // InternalN4MFParser.g:589:1: ruleSimpleProjectDescription EOF
            {
             before(grammarAccess.getSimpleProjectDescriptionRule()); 
            pushFollow(FOLLOW_1);
            ruleSimpleProjectDescription();

            state._fsp--;

             after(grammarAccess.getSimpleProjectDescriptionRule()); 
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
    // $ANTLR end "entryRuleSimpleProjectDescription"


    // $ANTLR start "ruleSimpleProjectDescription"
    // InternalN4MFParser.g:596:1: ruleSimpleProjectDescription : ( ( rule__SimpleProjectDescription__Group__0 ) ) ;
    public final void ruleSimpleProjectDescription() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:600:2: ( ( ( rule__SimpleProjectDescription__Group__0 ) ) )
            // InternalN4MFParser.g:601:2: ( ( rule__SimpleProjectDescription__Group__0 ) )
            {
            // InternalN4MFParser.g:601:2: ( ( rule__SimpleProjectDescription__Group__0 ) )
            // InternalN4MFParser.g:602:3: ( rule__SimpleProjectDescription__Group__0 )
            {
             before(grammarAccess.getSimpleProjectDescriptionAccess().getGroup()); 
            // InternalN4MFParser.g:603:3: ( rule__SimpleProjectDescription__Group__0 )
            // InternalN4MFParser.g:603:4: rule__SimpleProjectDescription__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleProjectDescription__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSimpleProjectDescriptionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSimpleProjectDescription"


    // $ANTLR start "entryRuleVersionConstraint"
    // InternalN4MFParser.g:612:1: entryRuleVersionConstraint : ruleVersionConstraint EOF ;
    public final void entryRuleVersionConstraint() throws RecognitionException {
        try {
            // InternalN4MFParser.g:613:1: ( ruleVersionConstraint EOF )
            // InternalN4MFParser.g:614:1: ruleVersionConstraint EOF
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
    // InternalN4MFParser.g:621:1: ruleVersionConstraint : ( ( rule__VersionConstraint__Alternatives ) ) ;
    public final void ruleVersionConstraint() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:625:2: ( ( ( rule__VersionConstraint__Alternatives ) ) )
            // InternalN4MFParser.g:626:2: ( ( rule__VersionConstraint__Alternatives ) )
            {
            // InternalN4MFParser.g:626:2: ( ( rule__VersionConstraint__Alternatives ) )
            // InternalN4MFParser.g:627:3: ( rule__VersionConstraint__Alternatives )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives()); 
            // InternalN4MFParser.g:628:3: ( rule__VersionConstraint__Alternatives )
            // InternalN4MFParser.g:628:4: rule__VersionConstraint__Alternatives
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
    // InternalN4MFParser.g:637:1: entryRuleN4mfIdentifier : ruleN4mfIdentifier EOF ;
    public final void entryRuleN4mfIdentifier() throws RecognitionException {
        try {
            // InternalN4MFParser.g:638:1: ( ruleN4mfIdentifier EOF )
            // InternalN4MFParser.g:639:1: ruleN4mfIdentifier EOF
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
    // InternalN4MFParser.g:646:1: ruleN4mfIdentifier : ( ( rule__N4mfIdentifier__Alternatives ) ) ;
    public final void ruleN4mfIdentifier() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:650:2: ( ( ( rule__N4mfIdentifier__Alternatives ) ) )
            // InternalN4MFParser.g:651:2: ( ( rule__N4mfIdentifier__Alternatives ) )
            {
            // InternalN4MFParser.g:651:2: ( ( rule__N4mfIdentifier__Alternatives ) )
            // InternalN4MFParser.g:652:3: ( rule__N4mfIdentifier__Alternatives )
            {
             before(grammarAccess.getN4mfIdentifierAccess().getAlternatives()); 
            // InternalN4MFParser.g:653:3: ( rule__N4mfIdentifier__Alternatives )
            // InternalN4MFParser.g:653:4: rule__N4mfIdentifier__Alternatives
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
    // InternalN4MFParser.g:662:1: ruleProjectType : ( ( rule__ProjectType__Alternatives ) ) ;
    public final void ruleProjectType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:666:1: ( ( ( rule__ProjectType__Alternatives ) ) )
            // InternalN4MFParser.g:667:2: ( ( rule__ProjectType__Alternatives ) )
            {
            // InternalN4MFParser.g:667:2: ( ( rule__ProjectType__Alternatives ) )
            // InternalN4MFParser.g:668:3: ( rule__ProjectType__Alternatives )
            {
             before(grammarAccess.getProjectTypeAccess().getAlternatives()); 
            // InternalN4MFParser.g:669:3: ( rule__ProjectType__Alternatives )
            // InternalN4MFParser.g:669:4: rule__ProjectType__Alternatives
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
    // InternalN4MFParser.g:678:1: ruleSourceFragmentType : ( ( rule__SourceFragmentType__Alternatives ) ) ;
    public final void ruleSourceFragmentType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:682:1: ( ( ( rule__SourceFragmentType__Alternatives ) ) )
            // InternalN4MFParser.g:683:2: ( ( rule__SourceFragmentType__Alternatives ) )
            {
            // InternalN4MFParser.g:683:2: ( ( rule__SourceFragmentType__Alternatives ) )
            // InternalN4MFParser.g:684:3: ( rule__SourceFragmentType__Alternatives )
            {
             before(grammarAccess.getSourceFragmentTypeAccess().getAlternatives()); 
            // InternalN4MFParser.g:685:3: ( rule__SourceFragmentType__Alternatives )
            // InternalN4MFParser.g:685:4: rule__SourceFragmentType__Alternatives
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
    // InternalN4MFParser.g:694:1: ruleModuleFilterType : ( ( rule__ModuleFilterType__Alternatives ) ) ;
    public final void ruleModuleFilterType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:698:1: ( ( ( rule__ModuleFilterType__Alternatives ) ) )
            // InternalN4MFParser.g:699:2: ( ( rule__ModuleFilterType__Alternatives ) )
            {
            // InternalN4MFParser.g:699:2: ( ( rule__ModuleFilterType__Alternatives ) )
            // InternalN4MFParser.g:700:3: ( rule__ModuleFilterType__Alternatives )
            {
             before(grammarAccess.getModuleFilterTypeAccess().getAlternatives()); 
            // InternalN4MFParser.g:701:3: ( rule__ModuleFilterType__Alternatives )
            // InternalN4MFParser.g:701:4: rule__ModuleFilterType__Alternatives
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
    // InternalN4MFParser.g:710:1: ruleProjectDependencyScope : ( ( rule__ProjectDependencyScope__Alternatives ) ) ;
    public final void ruleProjectDependencyScope() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:714:1: ( ( ( rule__ProjectDependencyScope__Alternatives ) ) )
            // InternalN4MFParser.g:715:2: ( ( rule__ProjectDependencyScope__Alternatives ) )
            {
            // InternalN4MFParser.g:715:2: ( ( rule__ProjectDependencyScope__Alternatives ) )
            // InternalN4MFParser.g:716:3: ( rule__ProjectDependencyScope__Alternatives )
            {
             before(grammarAccess.getProjectDependencyScopeAccess().getAlternatives()); 
            // InternalN4MFParser.g:717:3: ( rule__ProjectDependencyScope__Alternatives )
            // InternalN4MFParser.g:717:4: rule__ProjectDependencyScope__Alternatives
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
    // InternalN4MFParser.g:726:1: ruleModuleLoader : ( ( rule__ModuleLoader__Alternatives ) ) ;
    public final void ruleModuleLoader() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:730:1: ( ( ( rule__ModuleLoader__Alternatives ) ) )
            // InternalN4MFParser.g:731:2: ( ( rule__ModuleLoader__Alternatives ) )
            {
            // InternalN4MFParser.g:731:2: ( ( rule__ModuleLoader__Alternatives ) )
            // InternalN4MFParser.g:732:3: ( rule__ModuleLoader__Alternatives )
            {
             before(grammarAccess.getModuleLoaderAccess().getAlternatives()); 
            // InternalN4MFParser.g:733:3: ( rule__ModuleLoader__Alternatives )
            // InternalN4MFParser.g:733:4: rule__ModuleLoader__Alternatives
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
    // InternalN4MFParser.g:741:1: rule__VersionConstraint__Alternatives : ( ( ( rule__VersionConstraint__Group_0__0 ) ) | ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) ) );
    public final void rule__VersionConstraint__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:745:1: ( ( ( rule__VersionConstraint__Group_0__0 ) ) | ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) ) )
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
                    // InternalN4MFParser.g:746:2: ( ( rule__VersionConstraint__Group_0__0 ) )
                    {
                    // InternalN4MFParser.g:746:2: ( ( rule__VersionConstraint__Group_0__0 ) )
                    // InternalN4MFParser.g:747:3: ( rule__VersionConstraint__Group_0__0 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getGroup_0()); 
                    // InternalN4MFParser.g:748:3: ( rule__VersionConstraint__Group_0__0 )
                    // InternalN4MFParser.g:748:4: rule__VersionConstraint__Group_0__0
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
                    // InternalN4MFParser.g:752:2: ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) )
                    {
                    // InternalN4MFParser.g:752:2: ( ( rule__VersionConstraint__LowerVersionAssignment_1 ) )
                    // InternalN4MFParser.g:753:3: ( rule__VersionConstraint__LowerVersionAssignment_1 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_1()); 
                    // InternalN4MFParser.g:754:3: ( rule__VersionConstraint__LowerVersionAssignment_1 )
                    // InternalN4MFParser.g:754:4: rule__VersionConstraint__LowerVersionAssignment_1
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
    // InternalN4MFParser.g:762:1: rule__VersionConstraint__Alternatives_0_0 : ( ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) ) | ( LeftSquareBracket ) );
    public final void rule__VersionConstraint__Alternatives_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:766:1: ( ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) ) | ( LeftSquareBracket ) )
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
                    // InternalN4MFParser.g:767:2: ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) )
                    {
                    // InternalN4MFParser.g:767:2: ( ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 ) )
                    // InternalN4MFParser.g:768:3: ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundAssignment_0_0_0()); 
                    // InternalN4MFParser.g:769:3: ( rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 )
                    // InternalN4MFParser.g:769:4: rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0
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
                    // InternalN4MFParser.g:773:2: ( LeftSquareBracket )
                    {
                    // InternalN4MFParser.g:773:2: ( LeftSquareBracket )
                    // InternalN4MFParser.g:774:3: LeftSquareBracket
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
    // InternalN4MFParser.g:783:1: rule__VersionConstraint__Alternatives_0_2 : ( ( ( rule__VersionConstraint__Group_0_2_0__0 )? ) | ( RightParenthesis ) );
    public final void rule__VersionConstraint__Alternatives_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:787:1: ( ( ( rule__VersionConstraint__Group_0_2_0__0 )? ) | ( RightParenthesis ) )
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
                    // InternalN4MFParser.g:788:2: ( ( rule__VersionConstraint__Group_0_2_0__0 )? )
                    {
                    // InternalN4MFParser.g:788:2: ( ( rule__VersionConstraint__Group_0_2_0__0 )? )
                    // InternalN4MFParser.g:789:3: ( rule__VersionConstraint__Group_0_2_0__0 )?
                    {
                     before(grammarAccess.getVersionConstraintAccess().getGroup_0_2_0()); 
                    // InternalN4MFParser.g:790:3: ( rule__VersionConstraint__Group_0_2_0__0 )?
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
                            // InternalN4MFParser.g:790:4: rule__VersionConstraint__Group_0_2_0__0
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
                    // InternalN4MFParser.g:794:2: ( RightParenthesis )
                    {
                    // InternalN4MFParser.g:794:2: ( RightParenthesis )
                    // InternalN4MFParser.g:795:3: RightParenthesis
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
    // InternalN4MFParser.g:804:1: rule__VersionConstraint__Alternatives_0_2_0_2 : ( ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) ) | ( RightSquareBracket ) );
    public final void rule__VersionConstraint__Alternatives_0_2_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:808:1: ( ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) ) | ( RightSquareBracket ) )
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
                    // InternalN4MFParser.g:809:2: ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) )
                    {
                    // InternalN4MFParser.g:809:2: ( ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 ) )
                    // InternalN4MFParser.g:810:3: ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 )
                    {
                     before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundAssignment_0_2_0_2_0()); 
                    // InternalN4MFParser.g:811:3: ( rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 )
                    // InternalN4MFParser.g:811:4: rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0
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
                    // InternalN4MFParser.g:815:2: ( RightSquareBracket )
                    {
                    // InternalN4MFParser.g:815:2: ( RightSquareBracket )
                    // InternalN4MFParser.g:816:3: RightSquareBracket
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
    // InternalN4MFParser.g:825:1: rule__N4mfIdentifier__Alternatives : ( ( RULE_ID ) | ( ProjectId ) | ( ProjectType ) | ( ProjectVersion ) | ( VendorId ) | ( VendorName ) | ( Output ) | ( Libraries ) | ( Resources ) | ( Sources ) | ( ModuleFilters ) | ( ( rule__N4mfIdentifier__Group_11__0 ) ) | ( API ) | ( User ) | ( Application ) | ( ( rule__N4mfIdentifier__Group_15__0 ) ) | ( Content ) | ( Test ) );
    public final void rule__N4mfIdentifier__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:829:1: ( ( RULE_ID ) | ( ProjectId ) | ( ProjectType ) | ( ProjectVersion ) | ( VendorId ) | ( VendorName ) | ( Output ) | ( Libraries ) | ( Resources ) | ( Sources ) | ( ModuleFilters ) | ( ( rule__N4mfIdentifier__Group_11__0 ) ) | ( API ) | ( User ) | ( Application ) | ( ( rule__N4mfIdentifier__Group_15__0 ) ) | ( Content ) | ( Test ) )
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
                    // InternalN4MFParser.g:830:2: ( RULE_ID )
                    {
                    // InternalN4MFParser.g:830:2: ( RULE_ID )
                    // InternalN4MFParser.g:831:3: RULE_ID
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:836:2: ( ProjectId )
                    {
                    // InternalN4MFParser.g:836:2: ( ProjectId )
                    // InternalN4MFParser.g:837:3: ProjectId
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1()); 
                    match(input,ProjectId,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:842:2: ( ProjectType )
                    {
                    // InternalN4MFParser.g:842:2: ( ProjectType )
                    // InternalN4MFParser.g:843:3: ProjectType
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2()); 
                    match(input,ProjectType,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalN4MFParser.g:848:2: ( ProjectVersion )
                    {
                    // InternalN4MFParser.g:848:2: ( ProjectVersion )
                    // InternalN4MFParser.g:849:3: ProjectVersion
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3()); 
                    match(input,ProjectVersion,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalN4MFParser.g:854:2: ( VendorId )
                    {
                    // InternalN4MFParser.g:854:2: ( VendorId )
                    // InternalN4MFParser.g:855:3: VendorId
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4()); 
                    match(input,VendorId,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalN4MFParser.g:860:2: ( VendorName )
                    {
                    // InternalN4MFParser.g:860:2: ( VendorName )
                    // InternalN4MFParser.g:861:3: VendorName
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5()); 
                    match(input,VendorName,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalN4MFParser.g:866:2: ( Output )
                    {
                    // InternalN4MFParser.g:866:2: ( Output )
                    // InternalN4MFParser.g:867:3: Output
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6()); 
                    match(input,Output,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalN4MFParser.g:872:2: ( Libraries )
                    {
                    // InternalN4MFParser.g:872:2: ( Libraries )
                    // InternalN4MFParser.g:873:3: Libraries
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7()); 
                    match(input,Libraries,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7()); 

                    }


                    }
                    break;
                case 9 :
                    // InternalN4MFParser.g:878:2: ( Resources )
                    {
                    // InternalN4MFParser.g:878:2: ( Resources )
                    // InternalN4MFParser.g:879:3: Resources
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8()); 
                    match(input,Resources,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8()); 

                    }


                    }
                    break;
                case 10 :
                    // InternalN4MFParser.g:884:2: ( Sources )
                    {
                    // InternalN4MFParser.g:884:2: ( Sources )
                    // InternalN4MFParser.g:885:3: Sources
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9()); 
                    match(input,Sources,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9()); 

                    }


                    }
                    break;
                case 11 :
                    // InternalN4MFParser.g:890:2: ( ModuleFilters )
                    {
                    // InternalN4MFParser.g:890:2: ( ModuleFilters )
                    // InternalN4MFParser.g:891:3: ModuleFilters
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10()); 
                    match(input,ModuleFilters,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10()); 

                    }


                    }
                    break;
                case 12 :
                    // InternalN4MFParser.g:896:2: ( ( rule__N4mfIdentifier__Group_11__0 ) )
                    {
                    // InternalN4MFParser.g:896:2: ( ( rule__N4mfIdentifier__Group_11__0 ) )
                    // InternalN4MFParser.g:897:3: ( rule__N4mfIdentifier__Group_11__0 )
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getGroup_11()); 
                    // InternalN4MFParser.g:898:3: ( rule__N4mfIdentifier__Group_11__0 )
                    // InternalN4MFParser.g:898:4: rule__N4mfIdentifier__Group_11__0
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
                    // InternalN4MFParser.g:902:2: ( API )
                    {
                    // InternalN4MFParser.g:902:2: ( API )
                    // InternalN4MFParser.g:903:3: API
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12()); 
                    match(input,API,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12()); 

                    }


                    }
                    break;
                case 14 :
                    // InternalN4MFParser.g:908:2: ( User )
                    {
                    // InternalN4MFParser.g:908:2: ( User )
                    // InternalN4MFParser.g:909:3: User
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13()); 
                    match(input,User,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13()); 

                    }


                    }
                    break;
                case 15 :
                    // InternalN4MFParser.g:914:2: ( Application )
                    {
                    // InternalN4MFParser.g:914:2: ( Application )
                    // InternalN4MFParser.g:915:3: Application
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14()); 
                    match(input,Application,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14()); 

                    }


                    }
                    break;
                case 16 :
                    // InternalN4MFParser.g:920:2: ( ( rule__N4mfIdentifier__Group_15__0 ) )
                    {
                    // InternalN4MFParser.g:920:2: ( ( rule__N4mfIdentifier__Group_15__0 ) )
                    // InternalN4MFParser.g:921:3: ( rule__N4mfIdentifier__Group_15__0 )
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getGroup_15()); 
                    // InternalN4MFParser.g:922:3: ( rule__N4mfIdentifier__Group_15__0 )
                    // InternalN4MFParser.g:922:4: rule__N4mfIdentifier__Group_15__0
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
                    // InternalN4MFParser.g:926:2: ( Content )
                    {
                    // InternalN4MFParser.g:926:2: ( Content )
                    // InternalN4MFParser.g:927:3: Content
                    {
                     before(grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16()); 
                    match(input,Content,FOLLOW_2); 
                     after(grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16()); 

                    }


                    }
                    break;
                case 18 :
                    // InternalN4MFParser.g:932:2: ( Test )
                    {
                    // InternalN4MFParser.g:932:2: ( Test )
                    // InternalN4MFParser.g:933:3: Test
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
    // InternalN4MFParser.g:942:1: rule__ProjectType__Alternatives : ( ( ( Application ) ) | ( ( Processor ) ) | ( ( Library ) ) | ( ( API ) ) | ( ( RuntimeEnvironment ) ) | ( ( RuntimeLibrary ) ) | ( ( Test ) ) );
    public final void rule__ProjectType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:946:1: ( ( ( Application ) ) | ( ( Processor ) ) | ( ( Library ) ) | ( ( API ) ) | ( ( RuntimeEnvironment ) ) | ( ( RuntimeLibrary ) ) | ( ( Test ) ) )
            int alt7=7;
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
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // InternalN4MFParser.g:947:2: ( ( Application ) )
                    {
                    // InternalN4MFParser.g:947:2: ( ( Application ) )
                    // InternalN4MFParser.g:948:3: ( Application )
                    {
                     before(grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:949:3: ( Application )
                    // InternalN4MFParser.g:949:4: Application
                    {
                    match(input,Application,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:953:2: ( ( Processor ) )
                    {
                    // InternalN4MFParser.g:953:2: ( ( Processor ) )
                    // InternalN4MFParser.g:954:3: ( Processor )
                    {
                     before(grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:955:3: ( Processor )
                    // InternalN4MFParser.g:955:4: Processor
                    {
                    match(input,Processor,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:959:2: ( ( Library ) )
                    {
                    // InternalN4MFParser.g:959:2: ( ( Library ) )
                    // InternalN4MFParser.g:960:3: ( Library )
                    {
                     before(grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2()); 
                    // InternalN4MFParser.g:961:3: ( Library )
                    // InternalN4MFParser.g:961:4: Library
                    {
                    match(input,Library,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalN4MFParser.g:965:2: ( ( API ) )
                    {
                    // InternalN4MFParser.g:965:2: ( ( API ) )
                    // InternalN4MFParser.g:966:3: ( API )
                    {
                     before(grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3()); 
                    // InternalN4MFParser.g:967:3: ( API )
                    // InternalN4MFParser.g:967:4: API
                    {
                    match(input,API,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalN4MFParser.g:971:2: ( ( RuntimeEnvironment ) )
                    {
                    // InternalN4MFParser.g:971:2: ( ( RuntimeEnvironment ) )
                    // InternalN4MFParser.g:972:3: ( RuntimeEnvironment )
                    {
                     before(grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4()); 
                    // InternalN4MFParser.g:973:3: ( RuntimeEnvironment )
                    // InternalN4MFParser.g:973:4: RuntimeEnvironment
                    {
                    match(input,RuntimeEnvironment,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalN4MFParser.g:977:2: ( ( RuntimeLibrary ) )
                    {
                    // InternalN4MFParser.g:977:2: ( ( RuntimeLibrary ) )
                    // InternalN4MFParser.g:978:3: ( RuntimeLibrary )
                    {
                     before(grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5()); 
                    // InternalN4MFParser.g:979:3: ( RuntimeLibrary )
                    // InternalN4MFParser.g:979:4: RuntimeLibrary
                    {
                    match(input,RuntimeLibrary,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalN4MFParser.g:983:2: ( ( Test ) )
                    {
                    // InternalN4MFParser.g:983:2: ( ( Test ) )
                    // InternalN4MFParser.g:984:3: ( Test )
                    {
                     before(grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6()); 
                    // InternalN4MFParser.g:985:3: ( Test )
                    // InternalN4MFParser.g:985:4: Test
                    {
                    match(input,Test,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6()); 

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
    // InternalN4MFParser.g:993:1: rule__SourceFragmentType__Alternatives : ( ( ( Source ) ) | ( ( External ) ) | ( ( Test ) ) );
    public final void rule__SourceFragmentType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:997:1: ( ( ( Source ) ) | ( ( External ) ) | ( ( Test ) ) )
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
                    // InternalN4MFParser.g:998:2: ( ( Source ) )
                    {
                    // InternalN4MFParser.g:998:2: ( ( Source ) )
                    // InternalN4MFParser.g:999:3: ( Source )
                    {
                     before(grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:1000:3: ( Source )
                    // InternalN4MFParser.g:1000:4: Source
                    {
                    match(input,Source,FOLLOW_2); 

                    }

                     after(grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:1004:2: ( ( External ) )
                    {
                    // InternalN4MFParser.g:1004:2: ( ( External ) )
                    // InternalN4MFParser.g:1005:3: ( External )
                    {
                     before(grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:1006:3: ( External )
                    // InternalN4MFParser.g:1006:4: External
                    {
                    match(input,External,FOLLOW_2); 

                    }

                     after(grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:1010:2: ( ( Test ) )
                    {
                    // InternalN4MFParser.g:1010:2: ( ( Test ) )
                    // InternalN4MFParser.g:1011:3: ( Test )
                    {
                     before(grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2()); 
                    // InternalN4MFParser.g:1012:3: ( Test )
                    // InternalN4MFParser.g:1012:4: Test
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
    // InternalN4MFParser.g:1020:1: rule__ModuleFilterType__Alternatives : ( ( ( NoValidate ) ) | ( ( NoModuleWrap ) ) );
    public final void rule__ModuleFilterType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1024:1: ( ( ( NoValidate ) ) | ( ( NoModuleWrap ) ) )
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
                    // InternalN4MFParser.g:1025:2: ( ( NoValidate ) )
                    {
                    // InternalN4MFParser.g:1025:2: ( ( NoValidate ) )
                    // InternalN4MFParser.g:1026:3: ( NoValidate )
                    {
                     before(grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:1027:3: ( NoValidate )
                    // InternalN4MFParser.g:1027:4: NoValidate
                    {
                    match(input,NoValidate,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:1031:2: ( ( NoModuleWrap ) )
                    {
                    // InternalN4MFParser.g:1031:2: ( ( NoModuleWrap ) )
                    // InternalN4MFParser.g:1032:3: ( NoModuleWrap )
                    {
                     before(grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:1033:3: ( NoModuleWrap )
                    // InternalN4MFParser.g:1033:4: NoModuleWrap
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
    // InternalN4MFParser.g:1041:1: rule__ProjectDependencyScope__Alternatives : ( ( ( Compile ) ) | ( ( Test ) ) );
    public final void rule__ProjectDependencyScope__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1045:1: ( ( ( Compile ) ) | ( ( Test ) ) )
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
                    // InternalN4MFParser.g:1046:2: ( ( Compile ) )
                    {
                    // InternalN4MFParser.g:1046:2: ( ( Compile ) )
                    // InternalN4MFParser.g:1047:3: ( Compile )
                    {
                     before(grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:1048:3: ( Compile )
                    // InternalN4MFParser.g:1048:4: Compile
                    {
                    match(input,Compile,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:1052:2: ( ( Test ) )
                    {
                    // InternalN4MFParser.g:1052:2: ( ( Test ) )
                    // InternalN4MFParser.g:1053:3: ( Test )
                    {
                     before(grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:1054:3: ( Test )
                    // InternalN4MFParser.g:1054:4: Test
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
    // InternalN4MFParser.g:1062:1: rule__ModuleLoader__Alternatives : ( ( ( N4js ) ) | ( ( Commonjs ) ) | ( ( Node_builtin ) ) );
    public final void rule__ModuleLoader__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1066:1: ( ( ( N4js ) ) | ( ( Commonjs ) ) | ( ( Node_builtin ) ) )
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
                    // InternalN4MFParser.g:1067:2: ( ( N4js ) )
                    {
                    // InternalN4MFParser.g:1067:2: ( ( N4js ) )
                    // InternalN4MFParser.g:1068:3: ( N4js )
                    {
                     before(grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0()); 
                    // InternalN4MFParser.g:1069:3: ( N4js )
                    // InternalN4MFParser.g:1069:4: N4js
                    {
                    match(input,N4js,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalN4MFParser.g:1073:2: ( ( Commonjs ) )
                    {
                    // InternalN4MFParser.g:1073:2: ( ( Commonjs ) )
                    // InternalN4MFParser.g:1074:3: ( Commonjs )
                    {
                     before(grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1()); 
                    // InternalN4MFParser.g:1075:3: ( Commonjs )
                    // InternalN4MFParser.g:1075:4: Commonjs
                    {
                    match(input,Commonjs,FOLLOW_2); 

                    }

                     after(grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalN4MFParser.g:1079:2: ( ( Node_builtin ) )
                    {
                    // InternalN4MFParser.g:1079:2: ( ( Node_builtin ) )
                    // InternalN4MFParser.g:1080:3: ( Node_builtin )
                    {
                     before(grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2()); 
                    // InternalN4MFParser.g:1081:3: ( Node_builtin )
                    // InternalN4MFParser.g:1081:4: Node_builtin
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
    // InternalN4MFParser.g:1089:1: rule__ProjectDescription__Group_0__0 : rule__ProjectDescription__Group_0__0__Impl rule__ProjectDescription__Group_0__1 ;
    public final void rule__ProjectDescription__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1093:1: ( rule__ProjectDescription__Group_0__0__Impl rule__ProjectDescription__Group_0__1 )
            // InternalN4MFParser.g:1094:2: rule__ProjectDescription__Group_0__0__Impl rule__ProjectDescription__Group_0__1
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
    // InternalN4MFParser.g:1101:1: rule__ProjectDescription__Group_0__0__Impl : ( ProjectId ) ;
    public final void rule__ProjectDescription__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1105:1: ( ( ProjectId ) )
            // InternalN4MFParser.g:1106:1: ( ProjectId )
            {
            // InternalN4MFParser.g:1106:1: ( ProjectId )
            // InternalN4MFParser.g:1107:2: ProjectId
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
    // InternalN4MFParser.g:1116:1: rule__ProjectDescription__Group_0__1 : rule__ProjectDescription__Group_0__1__Impl rule__ProjectDescription__Group_0__2 ;
    public final void rule__ProjectDescription__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1120:1: ( rule__ProjectDescription__Group_0__1__Impl rule__ProjectDescription__Group_0__2 )
            // InternalN4MFParser.g:1121:2: rule__ProjectDescription__Group_0__1__Impl rule__ProjectDescription__Group_0__2
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
    // InternalN4MFParser.g:1128:1: rule__ProjectDescription__Group_0__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1132:1: ( ( Colon ) )
            // InternalN4MFParser.g:1133:1: ( Colon )
            {
            // InternalN4MFParser.g:1133:1: ( Colon )
            // InternalN4MFParser.g:1134:2: Colon
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
    // InternalN4MFParser.g:1143:1: rule__ProjectDescription__Group_0__2 : rule__ProjectDescription__Group_0__2__Impl ;
    public final void rule__ProjectDescription__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1147:1: ( rule__ProjectDescription__Group_0__2__Impl )
            // InternalN4MFParser.g:1148:2: rule__ProjectDescription__Group_0__2__Impl
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
    // InternalN4MFParser.g:1154:1: rule__ProjectDescription__Group_0__2__Impl : ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) ) ;
    public final void rule__ProjectDescription__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1158:1: ( ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) ) )
            // InternalN4MFParser.g:1159:1: ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) )
            {
            // InternalN4MFParser.g:1159:1: ( ( rule__ProjectDescription__ProjectIdAssignment_0_2 ) )
            // InternalN4MFParser.g:1160:2: ( rule__ProjectDescription__ProjectIdAssignment_0_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectIdAssignment_0_2()); 
            // InternalN4MFParser.g:1161:2: ( rule__ProjectDescription__ProjectIdAssignment_0_2 )
            // InternalN4MFParser.g:1161:3: rule__ProjectDescription__ProjectIdAssignment_0_2
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
    // InternalN4MFParser.g:1170:1: rule__ProjectDescription__Group_1__0 : rule__ProjectDescription__Group_1__0__Impl rule__ProjectDescription__Group_1__1 ;
    public final void rule__ProjectDescription__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1174:1: ( rule__ProjectDescription__Group_1__0__Impl rule__ProjectDescription__Group_1__1 )
            // InternalN4MFParser.g:1175:2: rule__ProjectDescription__Group_1__0__Impl rule__ProjectDescription__Group_1__1
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
    // InternalN4MFParser.g:1182:1: rule__ProjectDescription__Group_1__0__Impl : ( ProjectType ) ;
    public final void rule__ProjectDescription__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1186:1: ( ( ProjectType ) )
            // InternalN4MFParser.g:1187:1: ( ProjectType )
            {
            // InternalN4MFParser.g:1187:1: ( ProjectType )
            // InternalN4MFParser.g:1188:2: ProjectType
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
    // InternalN4MFParser.g:1197:1: rule__ProjectDescription__Group_1__1 : rule__ProjectDescription__Group_1__1__Impl rule__ProjectDescription__Group_1__2 ;
    public final void rule__ProjectDescription__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1201:1: ( rule__ProjectDescription__Group_1__1__Impl rule__ProjectDescription__Group_1__2 )
            // InternalN4MFParser.g:1202:2: rule__ProjectDescription__Group_1__1__Impl rule__ProjectDescription__Group_1__2
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
    // InternalN4MFParser.g:1209:1: rule__ProjectDescription__Group_1__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1213:1: ( ( Colon ) )
            // InternalN4MFParser.g:1214:1: ( Colon )
            {
            // InternalN4MFParser.g:1214:1: ( Colon )
            // InternalN4MFParser.g:1215:2: Colon
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
    // InternalN4MFParser.g:1224:1: rule__ProjectDescription__Group_1__2 : rule__ProjectDescription__Group_1__2__Impl ;
    public final void rule__ProjectDescription__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1228:1: ( rule__ProjectDescription__Group_1__2__Impl )
            // InternalN4MFParser.g:1229:2: rule__ProjectDescription__Group_1__2__Impl
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
    // InternalN4MFParser.g:1235:1: rule__ProjectDescription__Group_1__2__Impl : ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) ) ;
    public final void rule__ProjectDescription__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1239:1: ( ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) ) )
            // InternalN4MFParser.g:1240:1: ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) )
            {
            // InternalN4MFParser.g:1240:1: ( ( rule__ProjectDescription__ProjectTypeAssignment_1_2 ) )
            // InternalN4MFParser.g:1241:2: ( rule__ProjectDescription__ProjectTypeAssignment_1_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectTypeAssignment_1_2()); 
            // InternalN4MFParser.g:1242:2: ( rule__ProjectDescription__ProjectTypeAssignment_1_2 )
            // InternalN4MFParser.g:1242:3: rule__ProjectDescription__ProjectTypeAssignment_1_2
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
    // InternalN4MFParser.g:1251:1: rule__ProjectDescription__Group_2__0 : rule__ProjectDescription__Group_2__0__Impl rule__ProjectDescription__Group_2__1 ;
    public final void rule__ProjectDescription__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1255:1: ( rule__ProjectDescription__Group_2__0__Impl rule__ProjectDescription__Group_2__1 )
            // InternalN4MFParser.g:1256:2: rule__ProjectDescription__Group_2__0__Impl rule__ProjectDescription__Group_2__1
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
    // InternalN4MFParser.g:1263:1: rule__ProjectDescription__Group_2__0__Impl : ( ProjectVersion ) ;
    public final void rule__ProjectDescription__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1267:1: ( ( ProjectVersion ) )
            // InternalN4MFParser.g:1268:1: ( ProjectVersion )
            {
            // InternalN4MFParser.g:1268:1: ( ProjectVersion )
            // InternalN4MFParser.g:1269:2: ProjectVersion
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
    // InternalN4MFParser.g:1278:1: rule__ProjectDescription__Group_2__1 : rule__ProjectDescription__Group_2__1__Impl rule__ProjectDescription__Group_2__2 ;
    public final void rule__ProjectDescription__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1282:1: ( rule__ProjectDescription__Group_2__1__Impl rule__ProjectDescription__Group_2__2 )
            // InternalN4MFParser.g:1283:2: rule__ProjectDescription__Group_2__1__Impl rule__ProjectDescription__Group_2__2
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
    // InternalN4MFParser.g:1290:1: rule__ProjectDescription__Group_2__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1294:1: ( ( Colon ) )
            // InternalN4MFParser.g:1295:1: ( Colon )
            {
            // InternalN4MFParser.g:1295:1: ( Colon )
            // InternalN4MFParser.g:1296:2: Colon
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
    // InternalN4MFParser.g:1305:1: rule__ProjectDescription__Group_2__2 : rule__ProjectDescription__Group_2__2__Impl ;
    public final void rule__ProjectDescription__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1309:1: ( rule__ProjectDescription__Group_2__2__Impl )
            // InternalN4MFParser.g:1310:2: rule__ProjectDescription__Group_2__2__Impl
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
    // InternalN4MFParser.g:1316:1: rule__ProjectDescription__Group_2__2__Impl : ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) ) ;
    public final void rule__ProjectDescription__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1320:1: ( ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) ) )
            // InternalN4MFParser.g:1321:1: ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) )
            {
            // InternalN4MFParser.g:1321:1: ( ( rule__ProjectDescription__ProjectVersionAssignment_2_2 ) )
            // InternalN4MFParser.g:1322:2: ( rule__ProjectDescription__ProjectVersionAssignment_2_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectVersionAssignment_2_2()); 
            // InternalN4MFParser.g:1323:2: ( rule__ProjectDescription__ProjectVersionAssignment_2_2 )
            // InternalN4MFParser.g:1323:3: rule__ProjectDescription__ProjectVersionAssignment_2_2
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
    // InternalN4MFParser.g:1332:1: rule__ProjectDescription__Group_3__0 : rule__ProjectDescription__Group_3__0__Impl rule__ProjectDescription__Group_3__1 ;
    public final void rule__ProjectDescription__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1336:1: ( rule__ProjectDescription__Group_3__0__Impl rule__ProjectDescription__Group_3__1 )
            // InternalN4MFParser.g:1337:2: rule__ProjectDescription__Group_3__0__Impl rule__ProjectDescription__Group_3__1
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
    // InternalN4MFParser.g:1344:1: rule__ProjectDescription__Group_3__0__Impl : ( VendorId ) ;
    public final void rule__ProjectDescription__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1348:1: ( ( VendorId ) )
            // InternalN4MFParser.g:1349:1: ( VendorId )
            {
            // InternalN4MFParser.g:1349:1: ( VendorId )
            // InternalN4MFParser.g:1350:2: VendorId
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
    // InternalN4MFParser.g:1359:1: rule__ProjectDescription__Group_3__1 : rule__ProjectDescription__Group_3__1__Impl rule__ProjectDescription__Group_3__2 ;
    public final void rule__ProjectDescription__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1363:1: ( rule__ProjectDescription__Group_3__1__Impl rule__ProjectDescription__Group_3__2 )
            // InternalN4MFParser.g:1364:2: rule__ProjectDescription__Group_3__1__Impl rule__ProjectDescription__Group_3__2
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
    // InternalN4MFParser.g:1371:1: rule__ProjectDescription__Group_3__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1375:1: ( ( Colon ) )
            // InternalN4MFParser.g:1376:1: ( Colon )
            {
            // InternalN4MFParser.g:1376:1: ( Colon )
            // InternalN4MFParser.g:1377:2: Colon
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
    // InternalN4MFParser.g:1386:1: rule__ProjectDescription__Group_3__2 : rule__ProjectDescription__Group_3__2__Impl ;
    public final void rule__ProjectDescription__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1390:1: ( rule__ProjectDescription__Group_3__2__Impl )
            // InternalN4MFParser.g:1391:2: rule__ProjectDescription__Group_3__2__Impl
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
    // InternalN4MFParser.g:1397:1: rule__ProjectDescription__Group_3__2__Impl : ( ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 ) ) ;
    public final void rule__ProjectDescription__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1401:1: ( ( ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 ) ) )
            // InternalN4MFParser.g:1402:1: ( ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 ) )
            {
            // InternalN4MFParser.g:1402:1: ( ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 ) )
            // InternalN4MFParser.g:1403:2: ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdAssignment_3_2()); 
            // InternalN4MFParser.g:1404:2: ( rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 )
            // InternalN4MFParser.g:1404:3: rule__ProjectDescription__DeclaredVendorIdAssignment_3_2
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
    // InternalN4MFParser.g:1413:1: rule__ProjectDescription__Group_4__0 : rule__ProjectDescription__Group_4__0__Impl rule__ProjectDescription__Group_4__1 ;
    public final void rule__ProjectDescription__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1417:1: ( rule__ProjectDescription__Group_4__0__Impl rule__ProjectDescription__Group_4__1 )
            // InternalN4MFParser.g:1418:2: rule__ProjectDescription__Group_4__0__Impl rule__ProjectDescription__Group_4__1
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
    // InternalN4MFParser.g:1425:1: rule__ProjectDescription__Group_4__0__Impl : ( VendorName ) ;
    public final void rule__ProjectDescription__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1429:1: ( ( VendorName ) )
            // InternalN4MFParser.g:1430:1: ( VendorName )
            {
            // InternalN4MFParser.g:1430:1: ( VendorName )
            // InternalN4MFParser.g:1431:2: VendorName
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
    // InternalN4MFParser.g:1440:1: rule__ProjectDescription__Group_4__1 : rule__ProjectDescription__Group_4__1__Impl rule__ProjectDescription__Group_4__2 ;
    public final void rule__ProjectDescription__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1444:1: ( rule__ProjectDescription__Group_4__1__Impl rule__ProjectDescription__Group_4__2 )
            // InternalN4MFParser.g:1445:2: rule__ProjectDescription__Group_4__1__Impl rule__ProjectDescription__Group_4__2
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
    // InternalN4MFParser.g:1452:1: rule__ProjectDescription__Group_4__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1456:1: ( ( Colon ) )
            // InternalN4MFParser.g:1457:1: ( Colon )
            {
            // InternalN4MFParser.g:1457:1: ( Colon )
            // InternalN4MFParser.g:1458:2: Colon
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
    // InternalN4MFParser.g:1467:1: rule__ProjectDescription__Group_4__2 : rule__ProjectDescription__Group_4__2__Impl ;
    public final void rule__ProjectDescription__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1471:1: ( rule__ProjectDescription__Group_4__2__Impl )
            // InternalN4MFParser.g:1472:2: rule__ProjectDescription__Group_4__2__Impl
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
    // InternalN4MFParser.g:1478:1: rule__ProjectDescription__Group_4__2__Impl : ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) ) ;
    public final void rule__ProjectDescription__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1482:1: ( ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) ) )
            // InternalN4MFParser.g:1483:1: ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) )
            {
            // InternalN4MFParser.g:1483:1: ( ( rule__ProjectDescription__VendorNameAssignment_4_2 ) )
            // InternalN4MFParser.g:1484:2: ( rule__ProjectDescription__VendorNameAssignment_4_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getVendorNameAssignment_4_2()); 
            // InternalN4MFParser.g:1485:2: ( rule__ProjectDescription__VendorNameAssignment_4_2 )
            // InternalN4MFParser.g:1485:3: rule__ProjectDescription__VendorNameAssignment_4_2
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
    // InternalN4MFParser.g:1494:1: rule__ProjectDescription__Group_5__0 : rule__ProjectDescription__Group_5__0__Impl rule__ProjectDescription__Group_5__1 ;
    public final void rule__ProjectDescription__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1498:1: ( rule__ProjectDescription__Group_5__0__Impl rule__ProjectDescription__Group_5__1 )
            // InternalN4MFParser.g:1499:2: rule__ProjectDescription__Group_5__0__Impl rule__ProjectDescription__Group_5__1
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
    // InternalN4MFParser.g:1506:1: rule__ProjectDescription__Group_5__0__Impl : ( MainModule ) ;
    public final void rule__ProjectDescription__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1510:1: ( ( MainModule ) )
            // InternalN4MFParser.g:1511:1: ( MainModule )
            {
            // InternalN4MFParser.g:1511:1: ( MainModule )
            // InternalN4MFParser.g:1512:2: MainModule
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
    // InternalN4MFParser.g:1521:1: rule__ProjectDescription__Group_5__1 : rule__ProjectDescription__Group_5__1__Impl rule__ProjectDescription__Group_5__2 ;
    public final void rule__ProjectDescription__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1525:1: ( rule__ProjectDescription__Group_5__1__Impl rule__ProjectDescription__Group_5__2 )
            // InternalN4MFParser.g:1526:2: rule__ProjectDescription__Group_5__1__Impl rule__ProjectDescription__Group_5__2
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
    // InternalN4MFParser.g:1533:1: rule__ProjectDescription__Group_5__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1537:1: ( ( Colon ) )
            // InternalN4MFParser.g:1538:1: ( Colon )
            {
            // InternalN4MFParser.g:1538:1: ( Colon )
            // InternalN4MFParser.g:1539:2: Colon
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
    // InternalN4MFParser.g:1548:1: rule__ProjectDescription__Group_5__2 : rule__ProjectDescription__Group_5__2__Impl ;
    public final void rule__ProjectDescription__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1552:1: ( rule__ProjectDescription__Group_5__2__Impl )
            // InternalN4MFParser.g:1553:2: rule__ProjectDescription__Group_5__2__Impl
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
    // InternalN4MFParser.g:1559:1: rule__ProjectDescription__Group_5__2__Impl : ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) ) ;
    public final void rule__ProjectDescription__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1563:1: ( ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) ) )
            // InternalN4MFParser.g:1564:1: ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) )
            {
            // InternalN4MFParser.g:1564:1: ( ( rule__ProjectDescription__MainModuleAssignment_5_2 ) )
            // InternalN4MFParser.g:1565:2: ( rule__ProjectDescription__MainModuleAssignment_5_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getMainModuleAssignment_5_2()); 
            // InternalN4MFParser.g:1566:2: ( rule__ProjectDescription__MainModuleAssignment_5_2 )
            // InternalN4MFParser.g:1566:3: rule__ProjectDescription__MainModuleAssignment_5_2
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


    // $ANTLR start "rule__ProjectDescription__Group_10__0"
    // InternalN4MFParser.g:1575:1: rule__ProjectDescription__Group_10__0 : rule__ProjectDescription__Group_10__0__Impl rule__ProjectDescription__Group_10__1 ;
    public final void rule__ProjectDescription__Group_10__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1579:1: ( rule__ProjectDescription__Group_10__0__Impl rule__ProjectDescription__Group_10__1 )
            // InternalN4MFParser.g:1580:2: rule__ProjectDescription__Group_10__0__Impl rule__ProjectDescription__Group_10__1
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
    // InternalN4MFParser.g:1587:1: rule__ProjectDescription__Group_10__0__Impl : ( ImplementationId ) ;
    public final void rule__ProjectDescription__Group_10__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1591:1: ( ( ImplementationId ) )
            // InternalN4MFParser.g:1592:1: ( ImplementationId )
            {
            // InternalN4MFParser.g:1592:1: ( ImplementationId )
            // InternalN4MFParser.g:1593:2: ImplementationId
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
    // InternalN4MFParser.g:1602:1: rule__ProjectDescription__Group_10__1 : rule__ProjectDescription__Group_10__1__Impl rule__ProjectDescription__Group_10__2 ;
    public final void rule__ProjectDescription__Group_10__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1606:1: ( rule__ProjectDescription__Group_10__1__Impl rule__ProjectDescription__Group_10__2 )
            // InternalN4MFParser.g:1607:2: rule__ProjectDescription__Group_10__1__Impl rule__ProjectDescription__Group_10__2
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
    // InternalN4MFParser.g:1614:1: rule__ProjectDescription__Group_10__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_10__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1618:1: ( ( Colon ) )
            // InternalN4MFParser.g:1619:1: ( Colon )
            {
            // InternalN4MFParser.g:1619:1: ( Colon )
            // InternalN4MFParser.g:1620:2: Colon
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
    // InternalN4MFParser.g:1629:1: rule__ProjectDescription__Group_10__2 : rule__ProjectDescription__Group_10__2__Impl ;
    public final void rule__ProjectDescription__Group_10__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1633:1: ( rule__ProjectDescription__Group_10__2__Impl )
            // InternalN4MFParser.g:1634:2: rule__ProjectDescription__Group_10__2__Impl
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
    // InternalN4MFParser.g:1640:1: rule__ProjectDescription__Group_10__2__Impl : ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) ) ;
    public final void rule__ProjectDescription__Group_10__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1644:1: ( ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) ) )
            // InternalN4MFParser.g:1645:1: ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) )
            {
            // InternalN4MFParser.g:1645:1: ( ( rule__ProjectDescription__ImplementationIdAssignment_10_2 ) )
            // InternalN4MFParser.g:1646:2: ( rule__ProjectDescription__ImplementationIdAssignment_10_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementationIdAssignment_10_2()); 
            // InternalN4MFParser.g:1647:2: ( rule__ProjectDescription__ImplementationIdAssignment_10_2 )
            // InternalN4MFParser.g:1647:3: rule__ProjectDescription__ImplementationIdAssignment_10_2
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


    // $ANTLR start "rule__ProjectDescription__Group_14__0"
    // InternalN4MFParser.g:1656:1: rule__ProjectDescription__Group_14__0 : rule__ProjectDescription__Group_14__0__Impl rule__ProjectDescription__Group_14__1 ;
    public final void rule__ProjectDescription__Group_14__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1660:1: ( rule__ProjectDescription__Group_14__0__Impl rule__ProjectDescription__Group_14__1 )
            // InternalN4MFParser.g:1661:2: rule__ProjectDescription__Group_14__0__Impl rule__ProjectDescription__Group_14__1
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
    // InternalN4MFParser.g:1668:1: rule__ProjectDescription__Group_14__0__Impl : ( Output ) ;
    public final void rule__ProjectDescription__Group_14__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1672:1: ( ( Output ) )
            // InternalN4MFParser.g:1673:1: ( Output )
            {
            // InternalN4MFParser.g:1673:1: ( Output )
            // InternalN4MFParser.g:1674:2: Output
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
    // InternalN4MFParser.g:1683:1: rule__ProjectDescription__Group_14__1 : rule__ProjectDescription__Group_14__1__Impl rule__ProjectDescription__Group_14__2 ;
    public final void rule__ProjectDescription__Group_14__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1687:1: ( rule__ProjectDescription__Group_14__1__Impl rule__ProjectDescription__Group_14__2 )
            // InternalN4MFParser.g:1688:2: rule__ProjectDescription__Group_14__1__Impl rule__ProjectDescription__Group_14__2
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
    // InternalN4MFParser.g:1695:1: rule__ProjectDescription__Group_14__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_14__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1699:1: ( ( Colon ) )
            // InternalN4MFParser.g:1700:1: ( Colon )
            {
            // InternalN4MFParser.g:1700:1: ( Colon )
            // InternalN4MFParser.g:1701:2: Colon
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
    // InternalN4MFParser.g:1710:1: rule__ProjectDescription__Group_14__2 : rule__ProjectDescription__Group_14__2__Impl ;
    public final void rule__ProjectDescription__Group_14__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1714:1: ( rule__ProjectDescription__Group_14__2__Impl )
            // InternalN4MFParser.g:1715:2: rule__ProjectDescription__Group_14__2__Impl
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
    // InternalN4MFParser.g:1721:1: rule__ProjectDescription__Group_14__2__Impl : ( ( rule__ProjectDescription__OutputPathAssignment_14_2 ) ) ;
    public final void rule__ProjectDescription__Group_14__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1725:1: ( ( ( rule__ProjectDescription__OutputPathAssignment_14_2 ) ) )
            // InternalN4MFParser.g:1726:1: ( ( rule__ProjectDescription__OutputPathAssignment_14_2 ) )
            {
            // InternalN4MFParser.g:1726:1: ( ( rule__ProjectDescription__OutputPathAssignment_14_2 ) )
            // InternalN4MFParser.g:1727:2: ( rule__ProjectDescription__OutputPathAssignment_14_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getOutputPathAssignment_14_2()); 
            // InternalN4MFParser.g:1728:2: ( rule__ProjectDescription__OutputPathAssignment_14_2 )
            // InternalN4MFParser.g:1728:3: rule__ProjectDescription__OutputPathAssignment_14_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__OutputPathAssignment_14_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getOutputPathAssignment_14_2()); 

            }


            }

        }
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
    // InternalN4MFParser.g:1737:1: rule__ProjectDescription__Group_15__0 : rule__ProjectDescription__Group_15__0__Impl rule__ProjectDescription__Group_15__1 ;
    public final void rule__ProjectDescription__Group_15__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1741:1: ( rule__ProjectDescription__Group_15__0__Impl rule__ProjectDescription__Group_15__1 )
            // InternalN4MFParser.g:1742:2: rule__ProjectDescription__Group_15__0__Impl rule__ProjectDescription__Group_15__1
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
    // InternalN4MFParser.g:1749:1: rule__ProjectDescription__Group_15__0__Impl : ( Libraries ) ;
    public final void rule__ProjectDescription__Group_15__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1753:1: ( ( Libraries ) )
            // InternalN4MFParser.g:1754:1: ( Libraries )
            {
            // InternalN4MFParser.g:1754:1: ( Libraries )
            // InternalN4MFParser.g:1755:2: Libraries
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
    // InternalN4MFParser.g:1764:1: rule__ProjectDescription__Group_15__1 : rule__ProjectDescription__Group_15__1__Impl rule__ProjectDescription__Group_15__2 ;
    public final void rule__ProjectDescription__Group_15__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1768:1: ( rule__ProjectDescription__Group_15__1__Impl rule__ProjectDescription__Group_15__2 )
            // InternalN4MFParser.g:1769:2: rule__ProjectDescription__Group_15__1__Impl rule__ProjectDescription__Group_15__2
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
    // InternalN4MFParser.g:1776:1: rule__ProjectDescription__Group_15__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_15__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1780:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:1781:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:1781:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:1782:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:1791:1: rule__ProjectDescription__Group_15__2 : rule__ProjectDescription__Group_15__2__Impl rule__ProjectDescription__Group_15__3 ;
    public final void rule__ProjectDescription__Group_15__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1795:1: ( rule__ProjectDescription__Group_15__2__Impl rule__ProjectDescription__Group_15__3 )
            // InternalN4MFParser.g:1796:2: rule__ProjectDescription__Group_15__2__Impl rule__ProjectDescription__Group_15__3
            {
            pushFollow(FOLLOW_9);
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
    // InternalN4MFParser.g:1803:1: rule__ProjectDescription__Group_15__2__Impl : ( ( rule__ProjectDescription__LibraryPathsAssignment_15_2 ) ) ;
    public final void rule__ProjectDescription__Group_15__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1807:1: ( ( ( rule__ProjectDescription__LibraryPathsAssignment_15_2 ) ) )
            // InternalN4MFParser.g:1808:1: ( ( rule__ProjectDescription__LibraryPathsAssignment_15_2 ) )
            {
            // InternalN4MFParser.g:1808:1: ( ( rule__ProjectDescription__LibraryPathsAssignment_15_2 ) )
            // InternalN4MFParser.g:1809:2: ( rule__ProjectDescription__LibraryPathsAssignment_15_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsAssignment_15_2()); 
            // InternalN4MFParser.g:1810:2: ( rule__ProjectDescription__LibraryPathsAssignment_15_2 )
            // InternalN4MFParser.g:1810:3: rule__ProjectDescription__LibraryPathsAssignment_15_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__LibraryPathsAssignment_15_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsAssignment_15_2()); 

            }


            }

        }
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
    // InternalN4MFParser.g:1818:1: rule__ProjectDescription__Group_15__3 : rule__ProjectDescription__Group_15__3__Impl rule__ProjectDescription__Group_15__4 ;
    public final void rule__ProjectDescription__Group_15__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1822:1: ( rule__ProjectDescription__Group_15__3__Impl rule__ProjectDescription__Group_15__4 )
            // InternalN4MFParser.g:1823:2: rule__ProjectDescription__Group_15__3__Impl rule__ProjectDescription__Group_15__4
            {
            pushFollow(FOLLOW_9);
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
    // InternalN4MFParser.g:1830:1: rule__ProjectDescription__Group_15__3__Impl : ( ( rule__ProjectDescription__Group_15_3__0 )* ) ;
    public final void rule__ProjectDescription__Group_15__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1834:1: ( ( ( rule__ProjectDescription__Group_15_3__0 )* ) )
            // InternalN4MFParser.g:1835:1: ( ( rule__ProjectDescription__Group_15_3__0 )* )
            {
            // InternalN4MFParser.g:1835:1: ( ( rule__ProjectDescription__Group_15_3__0 )* )
            // InternalN4MFParser.g:1836:2: ( rule__ProjectDescription__Group_15_3__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_15_3()); 
            // InternalN4MFParser.g:1837:2: ( rule__ProjectDescription__Group_15_3__0 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==Comma) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalN4MFParser.g:1837:3: rule__ProjectDescription__Group_15_3__0
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__ProjectDescription__Group_15_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
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
    // InternalN4MFParser.g:1845:1: rule__ProjectDescription__Group_15__4 : rule__ProjectDescription__Group_15__4__Impl ;
    public final void rule__ProjectDescription__Group_15__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1849:1: ( rule__ProjectDescription__Group_15__4__Impl )
            // InternalN4MFParser.g:1850:2: rule__ProjectDescription__Group_15__4__Impl
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
    // InternalN4MFParser.g:1856:1: rule__ProjectDescription__Group_15__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_15__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1860:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:1861:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:1861:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:1862:2: RightCurlyBracket
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
    // InternalN4MFParser.g:1872:1: rule__ProjectDescription__Group_15_3__0 : rule__ProjectDescription__Group_15_3__0__Impl rule__ProjectDescription__Group_15_3__1 ;
    public final void rule__ProjectDescription__Group_15_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1876:1: ( rule__ProjectDescription__Group_15_3__0__Impl rule__ProjectDescription__Group_15_3__1 )
            // InternalN4MFParser.g:1877:2: rule__ProjectDescription__Group_15_3__0__Impl rule__ProjectDescription__Group_15_3__1
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
    // InternalN4MFParser.g:1884:1: rule__ProjectDescription__Group_15_3__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_15_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1888:1: ( ( Comma ) )
            // InternalN4MFParser.g:1889:1: ( Comma )
            {
            // InternalN4MFParser.g:1889:1: ( Comma )
            // InternalN4MFParser.g:1890:2: Comma
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
    // InternalN4MFParser.g:1899:1: rule__ProjectDescription__Group_15_3__1 : rule__ProjectDescription__Group_15_3__1__Impl ;
    public final void rule__ProjectDescription__Group_15_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1903:1: ( rule__ProjectDescription__Group_15_3__1__Impl )
            // InternalN4MFParser.g:1904:2: rule__ProjectDescription__Group_15_3__1__Impl
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
    // InternalN4MFParser.g:1910:1: rule__ProjectDescription__Group_15_3__1__Impl : ( ( rule__ProjectDescription__LibraryPathsAssignment_15_3_1 ) ) ;
    public final void rule__ProjectDescription__Group_15_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1914:1: ( ( ( rule__ProjectDescription__LibraryPathsAssignment_15_3_1 ) ) )
            // InternalN4MFParser.g:1915:1: ( ( rule__ProjectDescription__LibraryPathsAssignment_15_3_1 ) )
            {
            // InternalN4MFParser.g:1915:1: ( ( rule__ProjectDescription__LibraryPathsAssignment_15_3_1 ) )
            // InternalN4MFParser.g:1916:2: ( rule__ProjectDescription__LibraryPathsAssignment_15_3_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsAssignment_15_3_1()); 
            // InternalN4MFParser.g:1917:2: ( rule__ProjectDescription__LibraryPathsAssignment_15_3_1 )
            // InternalN4MFParser.g:1917:3: rule__ProjectDescription__LibraryPathsAssignment_15_3_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__LibraryPathsAssignment_15_3_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsAssignment_15_3_1()); 

            }


            }

        }
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
    // InternalN4MFParser.g:1926:1: rule__ProjectDescription__Group_16__0 : rule__ProjectDescription__Group_16__0__Impl rule__ProjectDescription__Group_16__1 ;
    public final void rule__ProjectDescription__Group_16__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1930:1: ( rule__ProjectDescription__Group_16__0__Impl rule__ProjectDescription__Group_16__1 )
            // InternalN4MFParser.g:1931:2: rule__ProjectDescription__Group_16__0__Impl rule__ProjectDescription__Group_16__1
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
    // InternalN4MFParser.g:1938:1: rule__ProjectDescription__Group_16__0__Impl : ( Resources ) ;
    public final void rule__ProjectDescription__Group_16__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1942:1: ( ( Resources ) )
            // InternalN4MFParser.g:1943:1: ( Resources )
            {
            // InternalN4MFParser.g:1943:1: ( Resources )
            // InternalN4MFParser.g:1944:2: Resources
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
    // InternalN4MFParser.g:1953:1: rule__ProjectDescription__Group_16__1 : rule__ProjectDescription__Group_16__1__Impl rule__ProjectDescription__Group_16__2 ;
    public final void rule__ProjectDescription__Group_16__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1957:1: ( rule__ProjectDescription__Group_16__1__Impl rule__ProjectDescription__Group_16__2 )
            // InternalN4MFParser.g:1958:2: rule__ProjectDescription__Group_16__1__Impl rule__ProjectDescription__Group_16__2
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
    // InternalN4MFParser.g:1965:1: rule__ProjectDescription__Group_16__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_16__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1969:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:1970:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:1970:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:1971:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:1980:1: rule__ProjectDescription__Group_16__2 : rule__ProjectDescription__Group_16__2__Impl rule__ProjectDescription__Group_16__3 ;
    public final void rule__ProjectDescription__Group_16__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1984:1: ( rule__ProjectDescription__Group_16__2__Impl rule__ProjectDescription__Group_16__3 )
            // InternalN4MFParser.g:1985:2: rule__ProjectDescription__Group_16__2__Impl rule__ProjectDescription__Group_16__3
            {
            pushFollow(FOLLOW_9);
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
    // InternalN4MFParser.g:1992:1: rule__ProjectDescription__Group_16__2__Impl : ( ( rule__ProjectDescription__ResourcePathsAssignment_16_2 ) ) ;
    public final void rule__ProjectDescription__Group_16__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:1996:1: ( ( ( rule__ProjectDescription__ResourcePathsAssignment_16_2 ) ) )
            // InternalN4MFParser.g:1997:1: ( ( rule__ProjectDescription__ResourcePathsAssignment_16_2 ) )
            {
            // InternalN4MFParser.g:1997:1: ( ( rule__ProjectDescription__ResourcePathsAssignment_16_2 ) )
            // InternalN4MFParser.g:1998:2: ( rule__ProjectDescription__ResourcePathsAssignment_16_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsAssignment_16_2()); 
            // InternalN4MFParser.g:1999:2: ( rule__ProjectDescription__ResourcePathsAssignment_16_2 )
            // InternalN4MFParser.g:1999:3: rule__ProjectDescription__ResourcePathsAssignment_16_2
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ResourcePathsAssignment_16_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getResourcePathsAssignment_16_2()); 

            }


            }

        }
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
    // InternalN4MFParser.g:2007:1: rule__ProjectDescription__Group_16__3 : rule__ProjectDescription__Group_16__3__Impl rule__ProjectDescription__Group_16__4 ;
    public final void rule__ProjectDescription__Group_16__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2011:1: ( rule__ProjectDescription__Group_16__3__Impl rule__ProjectDescription__Group_16__4 )
            // InternalN4MFParser.g:2012:2: rule__ProjectDescription__Group_16__3__Impl rule__ProjectDescription__Group_16__4
            {
            pushFollow(FOLLOW_9);
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
    // InternalN4MFParser.g:2019:1: rule__ProjectDescription__Group_16__3__Impl : ( ( rule__ProjectDescription__Group_16_3__0 )* ) ;
    public final void rule__ProjectDescription__Group_16__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2023:1: ( ( ( rule__ProjectDescription__Group_16_3__0 )* ) )
            // InternalN4MFParser.g:2024:1: ( ( rule__ProjectDescription__Group_16_3__0 )* )
            {
            // InternalN4MFParser.g:2024:1: ( ( rule__ProjectDescription__Group_16_3__0 )* )
            // InternalN4MFParser.g:2025:2: ( rule__ProjectDescription__Group_16_3__0 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getGroup_16_3()); 
            // InternalN4MFParser.g:2026:2: ( rule__ProjectDescription__Group_16_3__0 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==Comma) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalN4MFParser.g:2026:3: rule__ProjectDescription__Group_16_3__0
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__ProjectDescription__Group_16_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
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
    // InternalN4MFParser.g:2034:1: rule__ProjectDescription__Group_16__4 : rule__ProjectDescription__Group_16__4__Impl ;
    public final void rule__ProjectDescription__Group_16__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2038:1: ( rule__ProjectDescription__Group_16__4__Impl )
            // InternalN4MFParser.g:2039:2: rule__ProjectDescription__Group_16__4__Impl
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
    // InternalN4MFParser.g:2045:1: rule__ProjectDescription__Group_16__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_16__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2049:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2050:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2050:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2051:2: RightCurlyBracket
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
    // InternalN4MFParser.g:2061:1: rule__ProjectDescription__Group_16_3__0 : rule__ProjectDescription__Group_16_3__0__Impl rule__ProjectDescription__Group_16_3__1 ;
    public final void rule__ProjectDescription__Group_16_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2065:1: ( rule__ProjectDescription__Group_16_3__0__Impl rule__ProjectDescription__Group_16_3__1 )
            // InternalN4MFParser.g:2066:2: rule__ProjectDescription__Group_16_3__0__Impl rule__ProjectDescription__Group_16_3__1
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
    // InternalN4MFParser.g:2073:1: rule__ProjectDescription__Group_16_3__0__Impl : ( Comma ) ;
    public final void rule__ProjectDescription__Group_16_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2077:1: ( ( Comma ) )
            // InternalN4MFParser.g:2078:1: ( Comma )
            {
            // InternalN4MFParser.g:2078:1: ( Comma )
            // InternalN4MFParser.g:2079:2: Comma
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
    // InternalN4MFParser.g:2088:1: rule__ProjectDescription__Group_16_3__1 : rule__ProjectDescription__Group_16_3__1__Impl ;
    public final void rule__ProjectDescription__Group_16_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2092:1: ( rule__ProjectDescription__Group_16_3__1__Impl )
            // InternalN4MFParser.g:2093:2: rule__ProjectDescription__Group_16_3__1__Impl
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
    // InternalN4MFParser.g:2099:1: rule__ProjectDescription__Group_16_3__1__Impl : ( ( rule__ProjectDescription__ResourcePathsAssignment_16_3_1 ) ) ;
    public final void rule__ProjectDescription__Group_16_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2103:1: ( ( ( rule__ProjectDescription__ResourcePathsAssignment_16_3_1 ) ) )
            // InternalN4MFParser.g:2104:1: ( ( rule__ProjectDescription__ResourcePathsAssignment_16_3_1 ) )
            {
            // InternalN4MFParser.g:2104:1: ( ( rule__ProjectDescription__ResourcePathsAssignment_16_3_1 ) )
            // InternalN4MFParser.g:2105:2: ( rule__ProjectDescription__ResourcePathsAssignment_16_3_1 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsAssignment_16_3_1()); 
            // InternalN4MFParser.g:2106:2: ( rule__ProjectDescription__ResourcePathsAssignment_16_3_1 )
            // InternalN4MFParser.g:2106:3: rule__ProjectDescription__ResourcePathsAssignment_16_3_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDescription__ResourcePathsAssignment_16_3_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getResourcePathsAssignment_16_3_1()); 

            }


            }

        }
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
    // InternalN4MFParser.g:2115:1: rule__ProjectDescription__Group_17__0 : rule__ProjectDescription__Group_17__0__Impl rule__ProjectDescription__Group_17__1 ;
    public final void rule__ProjectDescription__Group_17__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2119:1: ( rule__ProjectDescription__Group_17__0__Impl rule__ProjectDescription__Group_17__1 )
            // InternalN4MFParser.g:2120:2: rule__ProjectDescription__Group_17__0__Impl rule__ProjectDescription__Group_17__1
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
    // InternalN4MFParser.g:2127:1: rule__ProjectDescription__Group_17__0__Impl : ( Sources ) ;
    public final void rule__ProjectDescription__Group_17__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2131:1: ( ( Sources ) )
            // InternalN4MFParser.g:2132:1: ( Sources )
            {
            // InternalN4MFParser.g:2132:1: ( Sources )
            // InternalN4MFParser.g:2133:2: Sources
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
    // InternalN4MFParser.g:2142:1: rule__ProjectDescription__Group_17__1 : rule__ProjectDescription__Group_17__1__Impl rule__ProjectDescription__Group_17__2 ;
    public final void rule__ProjectDescription__Group_17__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2146:1: ( rule__ProjectDescription__Group_17__1__Impl rule__ProjectDescription__Group_17__2 )
            // InternalN4MFParser.g:2147:2: rule__ProjectDescription__Group_17__1__Impl rule__ProjectDescription__Group_17__2
            {
            pushFollow(FOLLOW_11);
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
    // InternalN4MFParser.g:2154:1: rule__ProjectDescription__Group_17__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_17__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2158:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2159:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2159:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2160:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:2169:1: rule__ProjectDescription__Group_17__2 : rule__ProjectDescription__Group_17__2__Impl rule__ProjectDescription__Group_17__3 ;
    public final void rule__ProjectDescription__Group_17__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2173:1: ( rule__ProjectDescription__Group_17__2__Impl rule__ProjectDescription__Group_17__3 )
            // InternalN4MFParser.g:2174:2: rule__ProjectDescription__Group_17__2__Impl rule__ProjectDescription__Group_17__3
            {
            pushFollow(FOLLOW_12);
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
    // InternalN4MFParser.g:2181:1: rule__ProjectDescription__Group_17__2__Impl : ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) ) ;
    public final void rule__ProjectDescription__Group_17__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2185:1: ( ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) ) )
            // InternalN4MFParser.g:2186:1: ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) )
            {
            // InternalN4MFParser.g:2186:1: ( ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* ) )
            // InternalN4MFParser.g:2187:2: ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) ) ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* )
            {
            // InternalN4MFParser.g:2187:2: ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 ) )
            // InternalN4MFParser.g:2188:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 
            // InternalN4MFParser.g:2189:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )
            // InternalN4MFParser.g:2189:4: rule__ProjectDescription__SourceFragmentAssignment_17_2
            {
            pushFollow(FOLLOW_13);
            rule__ProjectDescription__SourceFragmentAssignment_17_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 

            }

            // InternalN4MFParser.g:2192:2: ( ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )* )
            // InternalN4MFParser.g:2193:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2()); 
            // InternalN4MFParser.g:2194:3: ( rule__ProjectDescription__SourceFragmentAssignment_17_2 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==External||LA14_0==Source||LA14_0==Test) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalN4MFParser.g:2194:4: rule__ProjectDescription__SourceFragmentAssignment_17_2
            	    {
            	    pushFollow(FOLLOW_13);
            	    rule__ProjectDescription__SourceFragmentAssignment_17_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
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
    // InternalN4MFParser.g:2203:1: rule__ProjectDescription__Group_17__3 : rule__ProjectDescription__Group_17__3__Impl ;
    public final void rule__ProjectDescription__Group_17__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2207:1: ( rule__ProjectDescription__Group_17__3__Impl )
            // InternalN4MFParser.g:2208:2: rule__ProjectDescription__Group_17__3__Impl
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
    // InternalN4MFParser.g:2214:1: rule__ProjectDescription__Group_17__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_17__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2218:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2219:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2219:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2220:2: RightCurlyBracket
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
    // InternalN4MFParser.g:2230:1: rule__ProjectDescription__Group_18__0 : rule__ProjectDescription__Group_18__0__Impl rule__ProjectDescription__Group_18__1 ;
    public final void rule__ProjectDescription__Group_18__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2234:1: ( rule__ProjectDescription__Group_18__0__Impl rule__ProjectDescription__Group_18__1 )
            // InternalN4MFParser.g:2235:2: rule__ProjectDescription__Group_18__0__Impl rule__ProjectDescription__Group_18__1
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
    // InternalN4MFParser.g:2242:1: rule__ProjectDescription__Group_18__0__Impl : ( ModuleFilters ) ;
    public final void rule__ProjectDescription__Group_18__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2246:1: ( ( ModuleFilters ) )
            // InternalN4MFParser.g:2247:1: ( ModuleFilters )
            {
            // InternalN4MFParser.g:2247:1: ( ModuleFilters )
            // InternalN4MFParser.g:2248:2: ModuleFilters
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
    // InternalN4MFParser.g:2257:1: rule__ProjectDescription__Group_18__1 : rule__ProjectDescription__Group_18__1__Impl rule__ProjectDescription__Group_18__2 ;
    public final void rule__ProjectDescription__Group_18__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2261:1: ( rule__ProjectDescription__Group_18__1__Impl rule__ProjectDescription__Group_18__2 )
            // InternalN4MFParser.g:2262:2: rule__ProjectDescription__Group_18__1__Impl rule__ProjectDescription__Group_18__2
            {
            pushFollow(FOLLOW_14);
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
    // InternalN4MFParser.g:2269:1: rule__ProjectDescription__Group_18__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_18__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2273:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2274:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2274:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2275:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:2284:1: rule__ProjectDescription__Group_18__2 : rule__ProjectDescription__Group_18__2__Impl rule__ProjectDescription__Group_18__3 ;
    public final void rule__ProjectDescription__Group_18__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2288:1: ( rule__ProjectDescription__Group_18__2__Impl rule__ProjectDescription__Group_18__3 )
            // InternalN4MFParser.g:2289:2: rule__ProjectDescription__Group_18__2__Impl rule__ProjectDescription__Group_18__3
            {
            pushFollow(FOLLOW_12);
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
    // InternalN4MFParser.g:2296:1: rule__ProjectDescription__Group_18__2__Impl : ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) ) ;
    public final void rule__ProjectDescription__Group_18__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2300:1: ( ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) ) )
            // InternalN4MFParser.g:2301:1: ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) )
            {
            // InternalN4MFParser.g:2301:1: ( ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* ) )
            // InternalN4MFParser.g:2302:2: ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) ) ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* )
            {
            // InternalN4MFParser.g:2302:2: ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 ) )
            // InternalN4MFParser.g:2303:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 
            // InternalN4MFParser.g:2304:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )
            // InternalN4MFParser.g:2304:4: rule__ProjectDescription__ModuleFiltersAssignment_18_2
            {
            pushFollow(FOLLOW_15);
            rule__ProjectDescription__ModuleFiltersAssignment_18_2();

            state._fsp--;


            }

             after(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 

            }

            // InternalN4MFParser.g:2307:2: ( ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )* )
            // InternalN4MFParser.g:2308:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )*
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2()); 
            // InternalN4MFParser.g:2309:3: ( rule__ProjectDescription__ModuleFiltersAssignment_18_2 )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==NoModuleWrap||LA15_0==NoValidate) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalN4MFParser.g:2309:4: rule__ProjectDescription__ModuleFiltersAssignment_18_2
            	    {
            	    pushFollow(FOLLOW_15);
            	    rule__ProjectDescription__ModuleFiltersAssignment_18_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
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
    // InternalN4MFParser.g:2318:1: rule__ProjectDescription__Group_18__3 : rule__ProjectDescription__Group_18__3__Impl ;
    public final void rule__ProjectDescription__Group_18__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2322:1: ( rule__ProjectDescription__Group_18__3__Impl )
            // InternalN4MFParser.g:2323:2: rule__ProjectDescription__Group_18__3__Impl
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
    // InternalN4MFParser.g:2329:1: rule__ProjectDescription__Group_18__3__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDescription__Group_18__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2333:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2334:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2334:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2335:2: RightCurlyBracket
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


    // $ANTLR start "rule__ProjectDescription__Group_20__0"
    // InternalN4MFParser.g:2345:1: rule__ProjectDescription__Group_20__0 : rule__ProjectDescription__Group_20__0__Impl rule__ProjectDescription__Group_20__1 ;
    public final void rule__ProjectDescription__Group_20__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2349:1: ( rule__ProjectDescription__Group_20__0__Impl rule__ProjectDescription__Group_20__1 )
            // InternalN4MFParser.g:2350:2: rule__ProjectDescription__Group_20__0__Impl rule__ProjectDescription__Group_20__1
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
    // InternalN4MFParser.g:2357:1: rule__ProjectDescription__Group_20__0__Impl : ( ModuleLoader ) ;
    public final void rule__ProjectDescription__Group_20__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2361:1: ( ( ModuleLoader ) )
            // InternalN4MFParser.g:2362:1: ( ModuleLoader )
            {
            // InternalN4MFParser.g:2362:1: ( ModuleLoader )
            // InternalN4MFParser.g:2363:2: ModuleLoader
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
    // InternalN4MFParser.g:2372:1: rule__ProjectDescription__Group_20__1 : rule__ProjectDescription__Group_20__1__Impl rule__ProjectDescription__Group_20__2 ;
    public final void rule__ProjectDescription__Group_20__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2376:1: ( rule__ProjectDescription__Group_20__1__Impl rule__ProjectDescription__Group_20__2 )
            // InternalN4MFParser.g:2377:2: rule__ProjectDescription__Group_20__1__Impl rule__ProjectDescription__Group_20__2
            {
            pushFollow(FOLLOW_16);
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
    // InternalN4MFParser.g:2384:1: rule__ProjectDescription__Group_20__1__Impl : ( Colon ) ;
    public final void rule__ProjectDescription__Group_20__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2388:1: ( ( Colon ) )
            // InternalN4MFParser.g:2389:1: ( Colon )
            {
            // InternalN4MFParser.g:2389:1: ( Colon )
            // InternalN4MFParser.g:2390:2: Colon
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
    // InternalN4MFParser.g:2399:1: rule__ProjectDescription__Group_20__2 : rule__ProjectDescription__Group_20__2__Impl ;
    public final void rule__ProjectDescription__Group_20__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2403:1: ( rule__ProjectDescription__Group_20__2__Impl )
            // InternalN4MFParser.g:2404:2: rule__ProjectDescription__Group_20__2__Impl
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
    // InternalN4MFParser.g:2410:1: rule__ProjectDescription__Group_20__2__Impl : ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) ) ;
    public final void rule__ProjectDescription__Group_20__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2414:1: ( ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) ) )
            // InternalN4MFParser.g:2415:1: ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) )
            {
            // InternalN4MFParser.g:2415:1: ( ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 ) )
            // InternalN4MFParser.g:2416:2: ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 )
            {
             before(grammarAccess.getProjectDescriptionAccess().getModuleLoaderAssignment_20_2()); 
            // InternalN4MFParser.g:2417:2: ( rule__ProjectDescription__ModuleLoaderAssignment_20_2 )
            // InternalN4MFParser.g:2417:3: rule__ProjectDescription__ModuleLoaderAssignment_20_2
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


    // $ANTLR start "rule__ExecModule__Group__0"
    // InternalN4MFParser.g:2426:1: rule__ExecModule__Group__0 : rule__ExecModule__Group__0__Impl rule__ExecModule__Group__1 ;
    public final void rule__ExecModule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2430:1: ( rule__ExecModule__Group__0__Impl rule__ExecModule__Group__1 )
            // InternalN4MFParser.g:2431:2: rule__ExecModule__Group__0__Impl rule__ExecModule__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__ExecModule__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExecModule__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecModule__Group__0"


    // $ANTLR start "rule__ExecModule__Group__0__Impl"
    // InternalN4MFParser.g:2438:1: rule__ExecModule__Group__0__Impl : ( () ) ;
    public final void rule__ExecModule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2442:1: ( ( () ) )
            // InternalN4MFParser.g:2443:1: ( () )
            {
            // InternalN4MFParser.g:2443:1: ( () )
            // InternalN4MFParser.g:2444:2: ()
            {
             before(grammarAccess.getExecModuleAccess().getExecModuleAction_0()); 
            // InternalN4MFParser.g:2445:2: ()
            // InternalN4MFParser.g:2445:3: 
            {
            }

             after(grammarAccess.getExecModuleAccess().getExecModuleAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecModule__Group__0__Impl"


    // $ANTLR start "rule__ExecModule__Group__1"
    // InternalN4MFParser.g:2453:1: rule__ExecModule__Group__1 : rule__ExecModule__Group__1__Impl rule__ExecModule__Group__2 ;
    public final void rule__ExecModule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2457:1: ( rule__ExecModule__Group__1__Impl rule__ExecModule__Group__2 )
            // InternalN4MFParser.g:2458:2: rule__ExecModule__Group__1__Impl rule__ExecModule__Group__2
            {
            pushFollow(FOLLOW_3);
            rule__ExecModule__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExecModule__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecModule__Group__1"


    // $ANTLR start "rule__ExecModule__Group__1__Impl"
    // InternalN4MFParser.g:2465:1: rule__ExecModule__Group__1__Impl : ( ExecModule ) ;
    public final void rule__ExecModule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2469:1: ( ( ExecModule ) )
            // InternalN4MFParser.g:2470:1: ( ExecModule )
            {
            // InternalN4MFParser.g:2470:1: ( ExecModule )
            // InternalN4MFParser.g:2471:2: ExecModule
            {
             before(grammarAccess.getExecModuleAccess().getExecModuleKeyword_1()); 
            match(input,ExecModule,FOLLOW_2); 
             after(grammarAccess.getExecModuleAccess().getExecModuleKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecModule__Group__1__Impl"


    // $ANTLR start "rule__ExecModule__Group__2"
    // InternalN4MFParser.g:2480:1: rule__ExecModule__Group__2 : rule__ExecModule__Group__2__Impl rule__ExecModule__Group__3 ;
    public final void rule__ExecModule__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2484:1: ( rule__ExecModule__Group__2__Impl rule__ExecModule__Group__3 )
            // InternalN4MFParser.g:2485:2: rule__ExecModule__Group__2__Impl rule__ExecModule__Group__3
            {
            pushFollow(FOLLOW_7);
            rule__ExecModule__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExecModule__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecModule__Group__2"


    // $ANTLR start "rule__ExecModule__Group__2__Impl"
    // InternalN4MFParser.g:2492:1: rule__ExecModule__Group__2__Impl : ( Colon ) ;
    public final void rule__ExecModule__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2496:1: ( ( Colon ) )
            // InternalN4MFParser.g:2497:1: ( Colon )
            {
            // InternalN4MFParser.g:2497:1: ( Colon )
            // InternalN4MFParser.g:2498:2: Colon
            {
             before(grammarAccess.getExecModuleAccess().getColonKeyword_2()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getExecModuleAccess().getColonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecModule__Group__2__Impl"


    // $ANTLR start "rule__ExecModule__Group__3"
    // InternalN4MFParser.g:2507:1: rule__ExecModule__Group__3 : rule__ExecModule__Group__3__Impl ;
    public final void rule__ExecModule__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2511:1: ( rule__ExecModule__Group__3__Impl )
            // InternalN4MFParser.g:2512:2: rule__ExecModule__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ExecModule__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecModule__Group__3"


    // $ANTLR start "rule__ExecModule__Group__3__Impl"
    // InternalN4MFParser.g:2518:1: rule__ExecModule__Group__3__Impl : ( ( rule__ExecModule__ExecModuleAssignment_3 ) ) ;
    public final void rule__ExecModule__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2522:1: ( ( ( rule__ExecModule__ExecModuleAssignment_3 ) ) )
            // InternalN4MFParser.g:2523:1: ( ( rule__ExecModule__ExecModuleAssignment_3 ) )
            {
            // InternalN4MFParser.g:2523:1: ( ( rule__ExecModule__ExecModuleAssignment_3 ) )
            // InternalN4MFParser.g:2524:2: ( rule__ExecModule__ExecModuleAssignment_3 )
            {
             before(grammarAccess.getExecModuleAccess().getExecModuleAssignment_3()); 
            // InternalN4MFParser.g:2525:2: ( rule__ExecModule__ExecModuleAssignment_3 )
            // InternalN4MFParser.g:2525:3: rule__ExecModule__ExecModuleAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__ExecModule__ExecModuleAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getExecModuleAccess().getExecModuleAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecModule__Group__3__Impl"


    // $ANTLR start "rule__TestedProjects__Group__0"
    // InternalN4MFParser.g:2534:1: rule__TestedProjects__Group__0 : rule__TestedProjects__Group__0__Impl rule__TestedProjects__Group__1 ;
    public final void rule__TestedProjects__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2538:1: ( rule__TestedProjects__Group__0__Impl rule__TestedProjects__Group__1 )
            // InternalN4MFParser.g:2539:2: rule__TestedProjects__Group__0__Impl rule__TestedProjects__Group__1
            {
            pushFollow(FOLLOW_18);
            rule__TestedProjects__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__0"


    // $ANTLR start "rule__TestedProjects__Group__0__Impl"
    // InternalN4MFParser.g:2546:1: rule__TestedProjects__Group__0__Impl : ( () ) ;
    public final void rule__TestedProjects__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2550:1: ( ( () ) )
            // InternalN4MFParser.g:2551:1: ( () )
            {
            // InternalN4MFParser.g:2551:1: ( () )
            // InternalN4MFParser.g:2552:2: ()
            {
             before(grammarAccess.getTestedProjectsAccess().getTestedProjectsAction_0()); 
            // InternalN4MFParser.g:2553:2: ()
            // InternalN4MFParser.g:2553:3: 
            {
            }

             after(grammarAccess.getTestedProjectsAccess().getTestedProjectsAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__0__Impl"


    // $ANTLR start "rule__TestedProjects__Group__1"
    // InternalN4MFParser.g:2561:1: rule__TestedProjects__Group__1 : rule__TestedProjects__Group__1__Impl rule__TestedProjects__Group__2 ;
    public final void rule__TestedProjects__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2565:1: ( rule__TestedProjects__Group__1__Impl rule__TestedProjects__Group__2 )
            // InternalN4MFParser.g:2566:2: rule__TestedProjects__Group__1__Impl rule__TestedProjects__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__TestedProjects__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__1"


    // $ANTLR start "rule__TestedProjects__Group__1__Impl"
    // InternalN4MFParser.g:2573:1: rule__TestedProjects__Group__1__Impl : ( TestedProjects ) ;
    public final void rule__TestedProjects__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2577:1: ( ( TestedProjects ) )
            // InternalN4MFParser.g:2578:1: ( TestedProjects )
            {
            // InternalN4MFParser.g:2578:1: ( TestedProjects )
            // InternalN4MFParser.g:2579:2: TestedProjects
            {
             before(grammarAccess.getTestedProjectsAccess().getTestedProjectsKeyword_1()); 
            match(input,TestedProjects,FOLLOW_2); 
             after(grammarAccess.getTestedProjectsAccess().getTestedProjectsKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__1__Impl"


    // $ANTLR start "rule__TestedProjects__Group__2"
    // InternalN4MFParser.g:2588:1: rule__TestedProjects__Group__2 : rule__TestedProjects__Group__2__Impl rule__TestedProjects__Group__3 ;
    public final void rule__TestedProjects__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2592:1: ( rule__TestedProjects__Group__2__Impl rule__TestedProjects__Group__3 )
            // InternalN4MFParser.g:2593:2: rule__TestedProjects__Group__2__Impl rule__TestedProjects__Group__3
            {
            pushFollow(FOLLOW_19);
            rule__TestedProjects__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__2"


    // $ANTLR start "rule__TestedProjects__Group__2__Impl"
    // InternalN4MFParser.g:2600:1: rule__TestedProjects__Group__2__Impl : ( LeftCurlyBracket ) ;
    public final void rule__TestedProjects__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2604:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2605:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2605:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2606:2: LeftCurlyBracket
            {
             before(grammarAccess.getTestedProjectsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getTestedProjectsAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__2__Impl"


    // $ANTLR start "rule__TestedProjects__Group__3"
    // InternalN4MFParser.g:2615:1: rule__TestedProjects__Group__3 : rule__TestedProjects__Group__3__Impl rule__TestedProjects__Group__4 ;
    public final void rule__TestedProjects__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2619:1: ( rule__TestedProjects__Group__3__Impl rule__TestedProjects__Group__4 )
            // InternalN4MFParser.g:2620:2: rule__TestedProjects__Group__3__Impl rule__TestedProjects__Group__4
            {
            pushFollow(FOLLOW_19);
            rule__TestedProjects__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__3"


    // $ANTLR start "rule__TestedProjects__Group__3__Impl"
    // InternalN4MFParser.g:2627:1: rule__TestedProjects__Group__3__Impl : ( ( rule__TestedProjects__Group_3__0 )? ) ;
    public final void rule__TestedProjects__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2631:1: ( ( ( rule__TestedProjects__Group_3__0 )? ) )
            // InternalN4MFParser.g:2632:1: ( ( rule__TestedProjects__Group_3__0 )? )
            {
            // InternalN4MFParser.g:2632:1: ( ( rule__TestedProjects__Group_3__0 )? )
            // InternalN4MFParser.g:2633:2: ( rule__TestedProjects__Group_3__0 )?
            {
             before(grammarAccess.getTestedProjectsAccess().getGroup_3()); 
            // InternalN4MFParser.g:2634:2: ( rule__TestedProjects__Group_3__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==ProjectDependencies||LA16_0==ProjectVersion||LA16_0==ModuleFilters||(LA16_0>=ProjectType && LA16_0<=Application)||LA16_0==VendorName||(LA16_0>=Libraries && LA16_0<=VendorId)||LA16_0==Sources||LA16_0==Content||LA16_0==Output||(LA16_0>=Test && LA16_0<=API)||LA16_0==RULE_ID) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalN4MFParser.g:2634:3: rule__TestedProjects__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__TestedProjects__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTestedProjectsAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__3__Impl"


    // $ANTLR start "rule__TestedProjects__Group__4"
    // InternalN4MFParser.g:2642:1: rule__TestedProjects__Group__4 : rule__TestedProjects__Group__4__Impl ;
    public final void rule__TestedProjects__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2646:1: ( rule__TestedProjects__Group__4__Impl )
            // InternalN4MFParser.g:2647:2: rule__TestedProjects__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__4"


    // $ANTLR start "rule__TestedProjects__Group__4__Impl"
    // InternalN4MFParser.g:2653:1: rule__TestedProjects__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__TestedProjects__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2657:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2658:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2658:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2659:2: RightCurlyBracket
            {
             before(grammarAccess.getTestedProjectsAccess().getRightCurlyBracketKeyword_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getTestedProjectsAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group__4__Impl"


    // $ANTLR start "rule__TestedProjects__Group_3__0"
    // InternalN4MFParser.g:2669:1: rule__TestedProjects__Group_3__0 : rule__TestedProjects__Group_3__0__Impl rule__TestedProjects__Group_3__1 ;
    public final void rule__TestedProjects__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2673:1: ( rule__TestedProjects__Group_3__0__Impl rule__TestedProjects__Group_3__1 )
            // InternalN4MFParser.g:2674:2: rule__TestedProjects__Group_3__0__Impl rule__TestedProjects__Group_3__1
            {
            pushFollow(FOLLOW_20);
            rule__TestedProjects__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group_3__0"


    // $ANTLR start "rule__TestedProjects__Group_3__0__Impl"
    // InternalN4MFParser.g:2681:1: rule__TestedProjects__Group_3__0__Impl : ( ( rule__TestedProjects__TestedProjectsAssignment_3_0 ) ) ;
    public final void rule__TestedProjects__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2685:1: ( ( ( rule__TestedProjects__TestedProjectsAssignment_3_0 ) ) )
            // InternalN4MFParser.g:2686:1: ( ( rule__TestedProjects__TestedProjectsAssignment_3_0 ) )
            {
            // InternalN4MFParser.g:2686:1: ( ( rule__TestedProjects__TestedProjectsAssignment_3_0 ) )
            // InternalN4MFParser.g:2687:2: ( rule__TestedProjects__TestedProjectsAssignment_3_0 )
            {
             before(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_0()); 
            // InternalN4MFParser.g:2688:2: ( rule__TestedProjects__TestedProjectsAssignment_3_0 )
            // InternalN4MFParser.g:2688:3: rule__TestedProjects__TestedProjectsAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__TestedProjects__TestedProjectsAssignment_3_0();

            state._fsp--;


            }

             after(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group_3__0__Impl"


    // $ANTLR start "rule__TestedProjects__Group_3__1"
    // InternalN4MFParser.g:2696:1: rule__TestedProjects__Group_3__1 : rule__TestedProjects__Group_3__1__Impl ;
    public final void rule__TestedProjects__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2700:1: ( rule__TestedProjects__Group_3__1__Impl )
            // InternalN4MFParser.g:2701:2: rule__TestedProjects__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group_3__1"


    // $ANTLR start "rule__TestedProjects__Group_3__1__Impl"
    // InternalN4MFParser.g:2707:1: rule__TestedProjects__Group_3__1__Impl : ( ( rule__TestedProjects__Group_3_1__0 )* ) ;
    public final void rule__TestedProjects__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2711:1: ( ( ( rule__TestedProjects__Group_3_1__0 )* ) )
            // InternalN4MFParser.g:2712:1: ( ( rule__TestedProjects__Group_3_1__0 )* )
            {
            // InternalN4MFParser.g:2712:1: ( ( rule__TestedProjects__Group_3_1__0 )* )
            // InternalN4MFParser.g:2713:2: ( rule__TestedProjects__Group_3_1__0 )*
            {
             before(grammarAccess.getTestedProjectsAccess().getGroup_3_1()); 
            // InternalN4MFParser.g:2714:2: ( rule__TestedProjects__Group_3_1__0 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==Comma) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalN4MFParser.g:2714:3: rule__TestedProjects__Group_3_1__0
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__TestedProjects__Group_3_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

             after(grammarAccess.getTestedProjectsAccess().getGroup_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group_3__1__Impl"


    // $ANTLR start "rule__TestedProjects__Group_3_1__0"
    // InternalN4MFParser.g:2723:1: rule__TestedProjects__Group_3_1__0 : rule__TestedProjects__Group_3_1__0__Impl rule__TestedProjects__Group_3_1__1 ;
    public final void rule__TestedProjects__Group_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2727:1: ( rule__TestedProjects__Group_3_1__0__Impl rule__TestedProjects__Group_3_1__1 )
            // InternalN4MFParser.g:2728:2: rule__TestedProjects__Group_3_1__0__Impl rule__TestedProjects__Group_3_1__1
            {
            pushFollow(FOLLOW_4);
            rule__TestedProjects__Group_3_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group_3_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group_3_1__0"


    // $ANTLR start "rule__TestedProjects__Group_3_1__0__Impl"
    // InternalN4MFParser.g:2735:1: rule__TestedProjects__Group_3_1__0__Impl : ( Comma ) ;
    public final void rule__TestedProjects__Group_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2739:1: ( ( Comma ) )
            // InternalN4MFParser.g:2740:1: ( Comma )
            {
            // InternalN4MFParser.g:2740:1: ( Comma )
            // InternalN4MFParser.g:2741:2: Comma
            {
             before(grammarAccess.getTestedProjectsAccess().getCommaKeyword_3_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getTestedProjectsAccess().getCommaKeyword_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group_3_1__0__Impl"


    // $ANTLR start "rule__TestedProjects__Group_3_1__1"
    // InternalN4MFParser.g:2750:1: rule__TestedProjects__Group_3_1__1 : rule__TestedProjects__Group_3_1__1__Impl ;
    public final void rule__TestedProjects__Group_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2754:1: ( rule__TestedProjects__Group_3_1__1__Impl )
            // InternalN4MFParser.g:2755:2: rule__TestedProjects__Group_3_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TestedProjects__Group_3_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group_3_1__1"


    // $ANTLR start "rule__TestedProjects__Group_3_1__1__Impl"
    // InternalN4MFParser.g:2761:1: rule__TestedProjects__Group_3_1__1__Impl : ( ( rule__TestedProjects__TestedProjectsAssignment_3_1_1 ) ) ;
    public final void rule__TestedProjects__Group_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2765:1: ( ( ( rule__TestedProjects__TestedProjectsAssignment_3_1_1 ) ) )
            // InternalN4MFParser.g:2766:1: ( ( rule__TestedProjects__TestedProjectsAssignment_3_1_1 ) )
            {
            // InternalN4MFParser.g:2766:1: ( ( rule__TestedProjects__TestedProjectsAssignment_3_1_1 ) )
            // InternalN4MFParser.g:2767:2: ( rule__TestedProjects__TestedProjectsAssignment_3_1_1 )
            {
             before(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_1_1()); 
            // InternalN4MFParser.g:2768:2: ( rule__TestedProjects__TestedProjectsAssignment_3_1_1 )
            // InternalN4MFParser.g:2768:3: rule__TestedProjects__TestedProjectsAssignment_3_1_1
            {
            pushFollow(FOLLOW_2);
            rule__TestedProjects__TestedProjectsAssignment_3_1_1();

            state._fsp--;


            }

             after(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__Group_3_1__1__Impl"


    // $ANTLR start "rule__InitModules__Group__0"
    // InternalN4MFParser.g:2777:1: rule__InitModules__Group__0 : rule__InitModules__Group__0__Impl rule__InitModules__Group__1 ;
    public final void rule__InitModules__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2781:1: ( rule__InitModules__Group__0__Impl rule__InitModules__Group__1 )
            // InternalN4MFParser.g:2782:2: rule__InitModules__Group__0__Impl rule__InitModules__Group__1
            {
            pushFollow(FOLLOW_21);
            rule__InitModules__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__InitModules__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__0"


    // $ANTLR start "rule__InitModules__Group__0__Impl"
    // InternalN4MFParser.g:2789:1: rule__InitModules__Group__0__Impl : ( () ) ;
    public final void rule__InitModules__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2793:1: ( ( () ) )
            // InternalN4MFParser.g:2794:1: ( () )
            {
            // InternalN4MFParser.g:2794:1: ( () )
            // InternalN4MFParser.g:2795:2: ()
            {
             before(grammarAccess.getInitModulesAccess().getInitModulesAction_0()); 
            // InternalN4MFParser.g:2796:2: ()
            // InternalN4MFParser.g:2796:3: 
            {
            }

             after(grammarAccess.getInitModulesAccess().getInitModulesAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__0__Impl"


    // $ANTLR start "rule__InitModules__Group__1"
    // InternalN4MFParser.g:2804:1: rule__InitModules__Group__1 : rule__InitModules__Group__1__Impl rule__InitModules__Group__2 ;
    public final void rule__InitModules__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2808:1: ( rule__InitModules__Group__1__Impl rule__InitModules__Group__2 )
            // InternalN4MFParser.g:2809:2: rule__InitModules__Group__1__Impl rule__InitModules__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__InitModules__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__InitModules__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__1"


    // $ANTLR start "rule__InitModules__Group__1__Impl"
    // InternalN4MFParser.g:2816:1: rule__InitModules__Group__1__Impl : ( InitModules ) ;
    public final void rule__InitModules__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2820:1: ( ( InitModules ) )
            // InternalN4MFParser.g:2821:1: ( InitModules )
            {
            // InternalN4MFParser.g:2821:1: ( InitModules )
            // InternalN4MFParser.g:2822:2: InitModules
            {
             before(grammarAccess.getInitModulesAccess().getInitModulesKeyword_1()); 
            match(input,InitModules,FOLLOW_2); 
             after(grammarAccess.getInitModulesAccess().getInitModulesKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__1__Impl"


    // $ANTLR start "rule__InitModules__Group__2"
    // InternalN4MFParser.g:2831:1: rule__InitModules__Group__2 : rule__InitModules__Group__2__Impl rule__InitModules__Group__3 ;
    public final void rule__InitModules__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2835:1: ( rule__InitModules__Group__2__Impl rule__InitModules__Group__3 )
            // InternalN4MFParser.g:2836:2: rule__InitModules__Group__2__Impl rule__InitModules__Group__3
            {
            pushFollow(FOLLOW_22);
            rule__InitModules__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__InitModules__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__2"


    // $ANTLR start "rule__InitModules__Group__2__Impl"
    // InternalN4MFParser.g:2843:1: rule__InitModules__Group__2__Impl : ( LeftCurlyBracket ) ;
    public final void rule__InitModules__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2847:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:2848:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:2848:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:2849:2: LeftCurlyBracket
            {
             before(grammarAccess.getInitModulesAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getInitModulesAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__2__Impl"


    // $ANTLR start "rule__InitModules__Group__3"
    // InternalN4MFParser.g:2858:1: rule__InitModules__Group__3 : rule__InitModules__Group__3__Impl rule__InitModules__Group__4 ;
    public final void rule__InitModules__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2862:1: ( rule__InitModules__Group__3__Impl rule__InitModules__Group__4 )
            // InternalN4MFParser.g:2863:2: rule__InitModules__Group__3__Impl rule__InitModules__Group__4
            {
            pushFollow(FOLLOW_22);
            rule__InitModules__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__InitModules__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__3"


    // $ANTLR start "rule__InitModules__Group__3__Impl"
    // InternalN4MFParser.g:2870:1: rule__InitModules__Group__3__Impl : ( ( rule__InitModules__Group_3__0 )? ) ;
    public final void rule__InitModules__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2874:1: ( ( ( rule__InitModules__Group_3__0 )? ) )
            // InternalN4MFParser.g:2875:1: ( ( rule__InitModules__Group_3__0 )? )
            {
            // InternalN4MFParser.g:2875:1: ( ( rule__InitModules__Group_3__0 )? )
            // InternalN4MFParser.g:2876:2: ( rule__InitModules__Group_3__0 )?
            {
             before(grammarAccess.getInitModulesAccess().getGroup_3()); 
            // InternalN4MFParser.g:2877:2: ( rule__InitModules__Group_3__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_STRING) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalN4MFParser.g:2877:3: rule__InitModules__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__InitModules__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getInitModulesAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__3__Impl"


    // $ANTLR start "rule__InitModules__Group__4"
    // InternalN4MFParser.g:2885:1: rule__InitModules__Group__4 : rule__InitModules__Group__4__Impl ;
    public final void rule__InitModules__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2889:1: ( rule__InitModules__Group__4__Impl )
            // InternalN4MFParser.g:2890:2: rule__InitModules__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__InitModules__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__4"


    // $ANTLR start "rule__InitModules__Group__4__Impl"
    // InternalN4MFParser.g:2896:1: rule__InitModules__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__InitModules__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2900:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:2901:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:2901:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:2902:2: RightCurlyBracket
            {
             before(grammarAccess.getInitModulesAccess().getRightCurlyBracketKeyword_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getInitModulesAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group__4__Impl"


    // $ANTLR start "rule__InitModules__Group_3__0"
    // InternalN4MFParser.g:2912:1: rule__InitModules__Group_3__0 : rule__InitModules__Group_3__0__Impl rule__InitModules__Group_3__1 ;
    public final void rule__InitModules__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2916:1: ( rule__InitModules__Group_3__0__Impl rule__InitModules__Group_3__1 )
            // InternalN4MFParser.g:2917:2: rule__InitModules__Group_3__0__Impl rule__InitModules__Group_3__1
            {
            pushFollow(FOLLOW_20);
            rule__InitModules__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__InitModules__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group_3__0"


    // $ANTLR start "rule__InitModules__Group_3__0__Impl"
    // InternalN4MFParser.g:2924:1: rule__InitModules__Group_3__0__Impl : ( ( rule__InitModules__InitModulesAssignment_3_0 ) ) ;
    public final void rule__InitModules__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2928:1: ( ( ( rule__InitModules__InitModulesAssignment_3_0 ) ) )
            // InternalN4MFParser.g:2929:1: ( ( rule__InitModules__InitModulesAssignment_3_0 ) )
            {
            // InternalN4MFParser.g:2929:1: ( ( rule__InitModules__InitModulesAssignment_3_0 ) )
            // InternalN4MFParser.g:2930:2: ( rule__InitModules__InitModulesAssignment_3_0 )
            {
             before(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_0()); 
            // InternalN4MFParser.g:2931:2: ( rule__InitModules__InitModulesAssignment_3_0 )
            // InternalN4MFParser.g:2931:3: rule__InitModules__InitModulesAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__InitModules__InitModulesAssignment_3_0();

            state._fsp--;


            }

             after(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group_3__0__Impl"


    // $ANTLR start "rule__InitModules__Group_3__1"
    // InternalN4MFParser.g:2939:1: rule__InitModules__Group_3__1 : rule__InitModules__Group_3__1__Impl ;
    public final void rule__InitModules__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2943:1: ( rule__InitModules__Group_3__1__Impl )
            // InternalN4MFParser.g:2944:2: rule__InitModules__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__InitModules__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group_3__1"


    // $ANTLR start "rule__InitModules__Group_3__1__Impl"
    // InternalN4MFParser.g:2950:1: rule__InitModules__Group_3__1__Impl : ( ( rule__InitModules__Group_3_1__0 )* ) ;
    public final void rule__InitModules__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2954:1: ( ( ( rule__InitModules__Group_3_1__0 )* ) )
            // InternalN4MFParser.g:2955:1: ( ( rule__InitModules__Group_3_1__0 )* )
            {
            // InternalN4MFParser.g:2955:1: ( ( rule__InitModules__Group_3_1__0 )* )
            // InternalN4MFParser.g:2956:2: ( rule__InitModules__Group_3_1__0 )*
            {
             before(grammarAccess.getInitModulesAccess().getGroup_3_1()); 
            // InternalN4MFParser.g:2957:2: ( rule__InitModules__Group_3_1__0 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==Comma) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalN4MFParser.g:2957:3: rule__InitModules__Group_3_1__0
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__InitModules__Group_3_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

             after(grammarAccess.getInitModulesAccess().getGroup_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group_3__1__Impl"


    // $ANTLR start "rule__InitModules__Group_3_1__0"
    // InternalN4MFParser.g:2966:1: rule__InitModules__Group_3_1__0 : rule__InitModules__Group_3_1__0__Impl rule__InitModules__Group_3_1__1 ;
    public final void rule__InitModules__Group_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2970:1: ( rule__InitModules__Group_3_1__0__Impl rule__InitModules__Group_3_1__1 )
            // InternalN4MFParser.g:2971:2: rule__InitModules__Group_3_1__0__Impl rule__InitModules__Group_3_1__1
            {
            pushFollow(FOLLOW_7);
            rule__InitModules__Group_3_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__InitModules__Group_3_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group_3_1__0"


    // $ANTLR start "rule__InitModules__Group_3_1__0__Impl"
    // InternalN4MFParser.g:2978:1: rule__InitModules__Group_3_1__0__Impl : ( Comma ) ;
    public final void rule__InitModules__Group_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2982:1: ( ( Comma ) )
            // InternalN4MFParser.g:2983:1: ( Comma )
            {
            // InternalN4MFParser.g:2983:1: ( Comma )
            // InternalN4MFParser.g:2984:2: Comma
            {
             before(grammarAccess.getInitModulesAccess().getCommaKeyword_3_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getInitModulesAccess().getCommaKeyword_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group_3_1__0__Impl"


    // $ANTLR start "rule__InitModules__Group_3_1__1"
    // InternalN4MFParser.g:2993:1: rule__InitModules__Group_3_1__1 : rule__InitModules__Group_3_1__1__Impl ;
    public final void rule__InitModules__Group_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:2997:1: ( rule__InitModules__Group_3_1__1__Impl )
            // InternalN4MFParser.g:2998:2: rule__InitModules__Group_3_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__InitModules__Group_3_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group_3_1__1"


    // $ANTLR start "rule__InitModules__Group_3_1__1__Impl"
    // InternalN4MFParser.g:3004:1: rule__InitModules__Group_3_1__1__Impl : ( ( rule__InitModules__InitModulesAssignment_3_1_1 ) ) ;
    public final void rule__InitModules__Group_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3008:1: ( ( ( rule__InitModules__InitModulesAssignment_3_1_1 ) ) )
            // InternalN4MFParser.g:3009:1: ( ( rule__InitModules__InitModulesAssignment_3_1_1 ) )
            {
            // InternalN4MFParser.g:3009:1: ( ( rule__InitModules__InitModulesAssignment_3_1_1 ) )
            // InternalN4MFParser.g:3010:2: ( rule__InitModules__InitModulesAssignment_3_1_1 )
            {
             before(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_1_1()); 
            // InternalN4MFParser.g:3011:2: ( rule__InitModules__InitModulesAssignment_3_1_1 )
            // InternalN4MFParser.g:3011:3: rule__InitModules__InitModulesAssignment_3_1_1
            {
            pushFollow(FOLLOW_2);
            rule__InitModules__InitModulesAssignment_3_1_1();

            state._fsp--;


            }

             after(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__Group_3_1__1__Impl"


    // $ANTLR start "rule__ImplementedProjects__Group__0"
    // InternalN4MFParser.g:3020:1: rule__ImplementedProjects__Group__0 : rule__ImplementedProjects__Group__0__Impl rule__ImplementedProjects__Group__1 ;
    public final void rule__ImplementedProjects__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3024:1: ( rule__ImplementedProjects__Group__0__Impl rule__ImplementedProjects__Group__1 )
            // InternalN4MFParser.g:3025:2: rule__ImplementedProjects__Group__0__Impl rule__ImplementedProjects__Group__1
            {
            pushFollow(FOLLOW_23);
            rule__ImplementedProjects__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__0"


    // $ANTLR start "rule__ImplementedProjects__Group__0__Impl"
    // InternalN4MFParser.g:3032:1: rule__ImplementedProjects__Group__0__Impl : ( () ) ;
    public final void rule__ImplementedProjects__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3036:1: ( ( () ) )
            // InternalN4MFParser.g:3037:1: ( () )
            {
            // InternalN4MFParser.g:3037:1: ( () )
            // InternalN4MFParser.g:3038:2: ()
            {
             before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAction_0()); 
            // InternalN4MFParser.g:3039:2: ()
            // InternalN4MFParser.g:3039:3: 
            {
            }

             after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__0__Impl"


    // $ANTLR start "rule__ImplementedProjects__Group__1"
    // InternalN4MFParser.g:3047:1: rule__ImplementedProjects__Group__1 : rule__ImplementedProjects__Group__1__Impl rule__ImplementedProjects__Group__2 ;
    public final void rule__ImplementedProjects__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3051:1: ( rule__ImplementedProjects__Group__1__Impl rule__ImplementedProjects__Group__2 )
            // InternalN4MFParser.g:3052:2: rule__ImplementedProjects__Group__1__Impl rule__ImplementedProjects__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__ImplementedProjects__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__1"


    // $ANTLR start "rule__ImplementedProjects__Group__1__Impl"
    // InternalN4MFParser.g:3059:1: rule__ImplementedProjects__Group__1__Impl : ( ImplementedProjects ) ;
    public final void rule__ImplementedProjects__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3063:1: ( ( ImplementedProjects ) )
            // InternalN4MFParser.g:3064:1: ( ImplementedProjects )
            {
            // InternalN4MFParser.g:3064:1: ( ImplementedProjects )
            // InternalN4MFParser.g:3065:2: ImplementedProjects
            {
             before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsKeyword_1()); 
            match(input,ImplementedProjects,FOLLOW_2); 
             after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__1__Impl"


    // $ANTLR start "rule__ImplementedProjects__Group__2"
    // InternalN4MFParser.g:3074:1: rule__ImplementedProjects__Group__2 : rule__ImplementedProjects__Group__2__Impl rule__ImplementedProjects__Group__3 ;
    public final void rule__ImplementedProjects__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3078:1: ( rule__ImplementedProjects__Group__2__Impl rule__ImplementedProjects__Group__3 )
            // InternalN4MFParser.g:3079:2: rule__ImplementedProjects__Group__2__Impl rule__ImplementedProjects__Group__3
            {
            pushFollow(FOLLOW_19);
            rule__ImplementedProjects__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__2"


    // $ANTLR start "rule__ImplementedProjects__Group__2__Impl"
    // InternalN4MFParser.g:3086:1: rule__ImplementedProjects__Group__2__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ImplementedProjects__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3090:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3091:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3091:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3092:2: LeftCurlyBracket
            {
             before(grammarAccess.getImplementedProjectsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getImplementedProjectsAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__2__Impl"


    // $ANTLR start "rule__ImplementedProjects__Group__3"
    // InternalN4MFParser.g:3101:1: rule__ImplementedProjects__Group__3 : rule__ImplementedProjects__Group__3__Impl rule__ImplementedProjects__Group__4 ;
    public final void rule__ImplementedProjects__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3105:1: ( rule__ImplementedProjects__Group__3__Impl rule__ImplementedProjects__Group__4 )
            // InternalN4MFParser.g:3106:2: rule__ImplementedProjects__Group__3__Impl rule__ImplementedProjects__Group__4
            {
            pushFollow(FOLLOW_19);
            rule__ImplementedProjects__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__3"


    // $ANTLR start "rule__ImplementedProjects__Group__3__Impl"
    // InternalN4MFParser.g:3113:1: rule__ImplementedProjects__Group__3__Impl : ( ( rule__ImplementedProjects__Group_3__0 )? ) ;
    public final void rule__ImplementedProjects__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3117:1: ( ( ( rule__ImplementedProjects__Group_3__0 )? ) )
            // InternalN4MFParser.g:3118:1: ( ( rule__ImplementedProjects__Group_3__0 )? )
            {
            // InternalN4MFParser.g:3118:1: ( ( rule__ImplementedProjects__Group_3__0 )? )
            // InternalN4MFParser.g:3119:2: ( rule__ImplementedProjects__Group_3__0 )?
            {
             before(grammarAccess.getImplementedProjectsAccess().getGroup_3()); 
            // InternalN4MFParser.g:3120:2: ( rule__ImplementedProjects__Group_3__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==ProjectDependencies||LA20_0==ProjectVersion||LA20_0==ModuleFilters||(LA20_0>=ProjectType && LA20_0<=Application)||LA20_0==VendorName||(LA20_0>=Libraries && LA20_0<=VendorId)||LA20_0==Sources||LA20_0==Content||LA20_0==Output||(LA20_0>=Test && LA20_0<=API)||LA20_0==RULE_ID) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalN4MFParser.g:3120:3: rule__ImplementedProjects__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ImplementedProjects__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getImplementedProjectsAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__3__Impl"


    // $ANTLR start "rule__ImplementedProjects__Group__4"
    // InternalN4MFParser.g:3128:1: rule__ImplementedProjects__Group__4 : rule__ImplementedProjects__Group__4__Impl ;
    public final void rule__ImplementedProjects__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3132:1: ( rule__ImplementedProjects__Group__4__Impl )
            // InternalN4MFParser.g:3133:2: rule__ImplementedProjects__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__4"


    // $ANTLR start "rule__ImplementedProjects__Group__4__Impl"
    // InternalN4MFParser.g:3139:1: rule__ImplementedProjects__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ImplementedProjects__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3143:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3144:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3144:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3145:2: RightCurlyBracket
            {
             before(grammarAccess.getImplementedProjectsAccess().getRightCurlyBracketKeyword_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getImplementedProjectsAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group__4__Impl"


    // $ANTLR start "rule__ImplementedProjects__Group_3__0"
    // InternalN4MFParser.g:3155:1: rule__ImplementedProjects__Group_3__0 : rule__ImplementedProjects__Group_3__0__Impl rule__ImplementedProjects__Group_3__1 ;
    public final void rule__ImplementedProjects__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3159:1: ( rule__ImplementedProjects__Group_3__0__Impl rule__ImplementedProjects__Group_3__1 )
            // InternalN4MFParser.g:3160:2: rule__ImplementedProjects__Group_3__0__Impl rule__ImplementedProjects__Group_3__1
            {
            pushFollow(FOLLOW_20);
            rule__ImplementedProjects__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group_3__0"


    // $ANTLR start "rule__ImplementedProjects__Group_3__0__Impl"
    // InternalN4MFParser.g:3167:1: rule__ImplementedProjects__Group_3__0__Impl : ( ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_0 ) ) ;
    public final void rule__ImplementedProjects__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3171:1: ( ( ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_0 ) ) )
            // InternalN4MFParser.g:3172:1: ( ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_0 ) )
            {
            // InternalN4MFParser.g:3172:1: ( ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_0 ) )
            // InternalN4MFParser.g:3173:2: ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_0 )
            {
             before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_0()); 
            // InternalN4MFParser.g:3174:2: ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_0 )
            // InternalN4MFParser.g:3174:3: rule__ImplementedProjects__ImplementedProjectsAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__ImplementedProjectsAssignment_3_0();

            state._fsp--;


            }

             after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group_3__0__Impl"


    // $ANTLR start "rule__ImplementedProjects__Group_3__1"
    // InternalN4MFParser.g:3182:1: rule__ImplementedProjects__Group_3__1 : rule__ImplementedProjects__Group_3__1__Impl ;
    public final void rule__ImplementedProjects__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3186:1: ( rule__ImplementedProjects__Group_3__1__Impl )
            // InternalN4MFParser.g:3187:2: rule__ImplementedProjects__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group_3__1"


    // $ANTLR start "rule__ImplementedProjects__Group_3__1__Impl"
    // InternalN4MFParser.g:3193:1: rule__ImplementedProjects__Group_3__1__Impl : ( ( rule__ImplementedProjects__Group_3_1__0 )* ) ;
    public final void rule__ImplementedProjects__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3197:1: ( ( ( rule__ImplementedProjects__Group_3_1__0 )* ) )
            // InternalN4MFParser.g:3198:1: ( ( rule__ImplementedProjects__Group_3_1__0 )* )
            {
            // InternalN4MFParser.g:3198:1: ( ( rule__ImplementedProjects__Group_3_1__0 )* )
            // InternalN4MFParser.g:3199:2: ( rule__ImplementedProjects__Group_3_1__0 )*
            {
             before(grammarAccess.getImplementedProjectsAccess().getGroup_3_1()); 
            // InternalN4MFParser.g:3200:2: ( rule__ImplementedProjects__Group_3_1__0 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==Comma) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalN4MFParser.g:3200:3: rule__ImplementedProjects__Group_3_1__0
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__ImplementedProjects__Group_3_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

             after(grammarAccess.getImplementedProjectsAccess().getGroup_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group_3__1__Impl"


    // $ANTLR start "rule__ImplementedProjects__Group_3_1__0"
    // InternalN4MFParser.g:3209:1: rule__ImplementedProjects__Group_3_1__0 : rule__ImplementedProjects__Group_3_1__0__Impl rule__ImplementedProjects__Group_3_1__1 ;
    public final void rule__ImplementedProjects__Group_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3213:1: ( rule__ImplementedProjects__Group_3_1__0__Impl rule__ImplementedProjects__Group_3_1__1 )
            // InternalN4MFParser.g:3214:2: rule__ImplementedProjects__Group_3_1__0__Impl rule__ImplementedProjects__Group_3_1__1
            {
            pushFollow(FOLLOW_4);
            rule__ImplementedProjects__Group_3_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group_3_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group_3_1__0"


    // $ANTLR start "rule__ImplementedProjects__Group_3_1__0__Impl"
    // InternalN4MFParser.g:3221:1: rule__ImplementedProjects__Group_3_1__0__Impl : ( Comma ) ;
    public final void rule__ImplementedProjects__Group_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3225:1: ( ( Comma ) )
            // InternalN4MFParser.g:3226:1: ( Comma )
            {
            // InternalN4MFParser.g:3226:1: ( Comma )
            // InternalN4MFParser.g:3227:2: Comma
            {
             before(grammarAccess.getImplementedProjectsAccess().getCommaKeyword_3_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getImplementedProjectsAccess().getCommaKeyword_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group_3_1__0__Impl"


    // $ANTLR start "rule__ImplementedProjects__Group_3_1__1"
    // InternalN4MFParser.g:3236:1: rule__ImplementedProjects__Group_3_1__1 : rule__ImplementedProjects__Group_3_1__1__Impl ;
    public final void rule__ImplementedProjects__Group_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3240:1: ( rule__ImplementedProjects__Group_3_1__1__Impl )
            // InternalN4MFParser.g:3241:2: rule__ImplementedProjects__Group_3_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__Group_3_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group_3_1__1"


    // $ANTLR start "rule__ImplementedProjects__Group_3_1__1__Impl"
    // InternalN4MFParser.g:3247:1: rule__ImplementedProjects__Group_3_1__1__Impl : ( ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1 ) ) ;
    public final void rule__ImplementedProjects__Group_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3251:1: ( ( ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1 ) ) )
            // InternalN4MFParser.g:3252:1: ( ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1 ) )
            {
            // InternalN4MFParser.g:3252:1: ( ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1 ) )
            // InternalN4MFParser.g:3253:2: ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1 )
            {
             before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_1_1()); 
            // InternalN4MFParser.g:3254:2: ( rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1 )
            // InternalN4MFParser.g:3254:3: rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1();

            state._fsp--;


            }

             after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__Group_3_1__1__Impl"


    // $ANTLR start "rule__ProjectDependencies__Group__0"
    // InternalN4MFParser.g:3263:1: rule__ProjectDependencies__Group__0 : rule__ProjectDependencies__Group__0__Impl rule__ProjectDependencies__Group__1 ;
    public final void rule__ProjectDependencies__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3267:1: ( rule__ProjectDependencies__Group__0__Impl rule__ProjectDependencies__Group__1 )
            // InternalN4MFParser.g:3268:2: rule__ProjectDependencies__Group__0__Impl rule__ProjectDependencies__Group__1
            {
            pushFollow(FOLLOW_24);
            rule__ProjectDependencies__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__0"


    // $ANTLR start "rule__ProjectDependencies__Group__0__Impl"
    // InternalN4MFParser.g:3275:1: rule__ProjectDependencies__Group__0__Impl : ( () ) ;
    public final void rule__ProjectDependencies__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3279:1: ( ( () ) )
            // InternalN4MFParser.g:3280:1: ( () )
            {
            // InternalN4MFParser.g:3280:1: ( () )
            // InternalN4MFParser.g:3281:2: ()
            {
             before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAction_0()); 
            // InternalN4MFParser.g:3282:2: ()
            // InternalN4MFParser.g:3282:3: 
            {
            }

             after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__0__Impl"


    // $ANTLR start "rule__ProjectDependencies__Group__1"
    // InternalN4MFParser.g:3290:1: rule__ProjectDependencies__Group__1 : rule__ProjectDependencies__Group__1__Impl rule__ProjectDependencies__Group__2 ;
    public final void rule__ProjectDependencies__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3294:1: ( rule__ProjectDependencies__Group__1__Impl rule__ProjectDependencies__Group__2 )
            // InternalN4MFParser.g:3295:2: rule__ProjectDependencies__Group__1__Impl rule__ProjectDependencies__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__ProjectDependencies__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__1"


    // $ANTLR start "rule__ProjectDependencies__Group__1__Impl"
    // InternalN4MFParser.g:3302:1: rule__ProjectDependencies__Group__1__Impl : ( ProjectDependencies ) ;
    public final void rule__ProjectDependencies__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3306:1: ( ( ProjectDependencies ) )
            // InternalN4MFParser.g:3307:1: ( ProjectDependencies )
            {
            // InternalN4MFParser.g:3307:1: ( ProjectDependencies )
            // InternalN4MFParser.g:3308:2: ProjectDependencies
            {
             before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesKeyword_1()); 
            match(input,ProjectDependencies,FOLLOW_2); 
             after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__1__Impl"


    // $ANTLR start "rule__ProjectDependencies__Group__2"
    // InternalN4MFParser.g:3317:1: rule__ProjectDependencies__Group__2 : rule__ProjectDependencies__Group__2__Impl rule__ProjectDependencies__Group__3 ;
    public final void rule__ProjectDependencies__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3321:1: ( rule__ProjectDependencies__Group__2__Impl rule__ProjectDependencies__Group__3 )
            // InternalN4MFParser.g:3322:2: rule__ProjectDependencies__Group__2__Impl rule__ProjectDependencies__Group__3
            {
            pushFollow(FOLLOW_19);
            rule__ProjectDependencies__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__2"


    // $ANTLR start "rule__ProjectDependencies__Group__2__Impl"
    // InternalN4MFParser.g:3329:1: rule__ProjectDependencies__Group__2__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProjectDependencies__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3333:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3334:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3334:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3335:2: LeftCurlyBracket
            {
             before(grammarAccess.getProjectDependenciesAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDependenciesAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__2__Impl"


    // $ANTLR start "rule__ProjectDependencies__Group__3"
    // InternalN4MFParser.g:3344:1: rule__ProjectDependencies__Group__3 : rule__ProjectDependencies__Group__3__Impl rule__ProjectDependencies__Group__4 ;
    public final void rule__ProjectDependencies__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3348:1: ( rule__ProjectDependencies__Group__3__Impl rule__ProjectDependencies__Group__4 )
            // InternalN4MFParser.g:3349:2: rule__ProjectDependencies__Group__3__Impl rule__ProjectDependencies__Group__4
            {
            pushFollow(FOLLOW_19);
            rule__ProjectDependencies__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__3"


    // $ANTLR start "rule__ProjectDependencies__Group__3__Impl"
    // InternalN4MFParser.g:3356:1: rule__ProjectDependencies__Group__3__Impl : ( ( rule__ProjectDependencies__Group_3__0 )? ) ;
    public final void rule__ProjectDependencies__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3360:1: ( ( ( rule__ProjectDependencies__Group_3__0 )? ) )
            // InternalN4MFParser.g:3361:1: ( ( rule__ProjectDependencies__Group_3__0 )? )
            {
            // InternalN4MFParser.g:3361:1: ( ( rule__ProjectDependencies__Group_3__0 )? )
            // InternalN4MFParser.g:3362:2: ( rule__ProjectDependencies__Group_3__0 )?
            {
             before(grammarAccess.getProjectDependenciesAccess().getGroup_3()); 
            // InternalN4MFParser.g:3363:2: ( rule__ProjectDependencies__Group_3__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==ProjectDependencies||LA22_0==ProjectVersion||LA22_0==ModuleFilters||(LA22_0>=ProjectType && LA22_0<=Application)||LA22_0==VendorName||(LA22_0>=Libraries && LA22_0<=VendorId)||LA22_0==Sources||LA22_0==Content||LA22_0==Output||(LA22_0>=Test && LA22_0<=API)||LA22_0==RULE_ID) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalN4MFParser.g:3363:3: rule__ProjectDependencies__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDependencies__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProjectDependenciesAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__3__Impl"


    // $ANTLR start "rule__ProjectDependencies__Group__4"
    // InternalN4MFParser.g:3371:1: rule__ProjectDependencies__Group__4 : rule__ProjectDependencies__Group__4__Impl ;
    public final void rule__ProjectDependencies__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3375:1: ( rule__ProjectDependencies__Group__4__Impl )
            // InternalN4MFParser.g:3376:2: rule__ProjectDependencies__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__4"


    // $ANTLR start "rule__ProjectDependencies__Group__4__Impl"
    // InternalN4MFParser.g:3382:1: rule__ProjectDependencies__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProjectDependencies__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3386:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3387:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3387:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3388:2: RightCurlyBracket
            {
             before(grammarAccess.getProjectDependenciesAccess().getRightCurlyBracketKeyword_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProjectDependenciesAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group__4__Impl"


    // $ANTLR start "rule__ProjectDependencies__Group_3__0"
    // InternalN4MFParser.g:3398:1: rule__ProjectDependencies__Group_3__0 : rule__ProjectDependencies__Group_3__0__Impl rule__ProjectDependencies__Group_3__1 ;
    public final void rule__ProjectDependencies__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3402:1: ( rule__ProjectDependencies__Group_3__0__Impl rule__ProjectDependencies__Group_3__1 )
            // InternalN4MFParser.g:3403:2: rule__ProjectDependencies__Group_3__0__Impl rule__ProjectDependencies__Group_3__1
            {
            pushFollow(FOLLOW_20);
            rule__ProjectDependencies__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group_3__0"


    // $ANTLR start "rule__ProjectDependencies__Group_3__0__Impl"
    // InternalN4MFParser.g:3410:1: rule__ProjectDependencies__Group_3__0__Impl : ( ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_0 ) ) ;
    public final void rule__ProjectDependencies__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3414:1: ( ( ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_0 ) ) )
            // InternalN4MFParser.g:3415:1: ( ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_0 ) )
            {
            // InternalN4MFParser.g:3415:1: ( ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_0 ) )
            // InternalN4MFParser.g:3416:2: ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_0 )
            {
             before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_0()); 
            // InternalN4MFParser.g:3417:2: ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_0 )
            // InternalN4MFParser.g:3417:3: rule__ProjectDependencies__ProjectDependenciesAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__ProjectDependenciesAssignment_3_0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group_3__0__Impl"


    // $ANTLR start "rule__ProjectDependencies__Group_3__1"
    // InternalN4MFParser.g:3425:1: rule__ProjectDependencies__Group_3__1 : rule__ProjectDependencies__Group_3__1__Impl ;
    public final void rule__ProjectDependencies__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3429:1: ( rule__ProjectDependencies__Group_3__1__Impl )
            // InternalN4MFParser.g:3430:2: rule__ProjectDependencies__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group_3__1"


    // $ANTLR start "rule__ProjectDependencies__Group_3__1__Impl"
    // InternalN4MFParser.g:3436:1: rule__ProjectDependencies__Group_3__1__Impl : ( ( rule__ProjectDependencies__Group_3_1__0 )* ) ;
    public final void rule__ProjectDependencies__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3440:1: ( ( ( rule__ProjectDependencies__Group_3_1__0 )* ) )
            // InternalN4MFParser.g:3441:1: ( ( rule__ProjectDependencies__Group_3_1__0 )* )
            {
            // InternalN4MFParser.g:3441:1: ( ( rule__ProjectDependencies__Group_3_1__0 )* )
            // InternalN4MFParser.g:3442:2: ( rule__ProjectDependencies__Group_3_1__0 )*
            {
             before(grammarAccess.getProjectDependenciesAccess().getGroup_3_1()); 
            // InternalN4MFParser.g:3443:2: ( rule__ProjectDependencies__Group_3_1__0 )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==Comma) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalN4MFParser.g:3443:3: rule__ProjectDependencies__Group_3_1__0
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__ProjectDependencies__Group_3_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

             after(grammarAccess.getProjectDependenciesAccess().getGroup_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group_3__1__Impl"


    // $ANTLR start "rule__ProjectDependencies__Group_3_1__0"
    // InternalN4MFParser.g:3452:1: rule__ProjectDependencies__Group_3_1__0 : rule__ProjectDependencies__Group_3_1__0__Impl rule__ProjectDependencies__Group_3_1__1 ;
    public final void rule__ProjectDependencies__Group_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3456:1: ( rule__ProjectDependencies__Group_3_1__0__Impl rule__ProjectDependencies__Group_3_1__1 )
            // InternalN4MFParser.g:3457:2: rule__ProjectDependencies__Group_3_1__0__Impl rule__ProjectDependencies__Group_3_1__1
            {
            pushFollow(FOLLOW_4);
            rule__ProjectDependencies__Group_3_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group_3_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group_3_1__0"


    // $ANTLR start "rule__ProjectDependencies__Group_3_1__0__Impl"
    // InternalN4MFParser.g:3464:1: rule__ProjectDependencies__Group_3_1__0__Impl : ( Comma ) ;
    public final void rule__ProjectDependencies__Group_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3468:1: ( ( Comma ) )
            // InternalN4MFParser.g:3469:1: ( Comma )
            {
            // InternalN4MFParser.g:3469:1: ( Comma )
            // InternalN4MFParser.g:3470:2: Comma
            {
             before(grammarAccess.getProjectDependenciesAccess().getCommaKeyword_3_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProjectDependenciesAccess().getCommaKeyword_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group_3_1__0__Impl"


    // $ANTLR start "rule__ProjectDependencies__Group_3_1__1"
    // InternalN4MFParser.g:3479:1: rule__ProjectDependencies__Group_3_1__1 : rule__ProjectDependencies__Group_3_1__1__Impl ;
    public final void rule__ProjectDependencies__Group_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3483:1: ( rule__ProjectDependencies__Group_3_1__1__Impl )
            // InternalN4MFParser.g:3484:2: rule__ProjectDependencies__Group_3_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__Group_3_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group_3_1__1"


    // $ANTLR start "rule__ProjectDependencies__Group_3_1__1__Impl"
    // InternalN4MFParser.g:3490:1: rule__ProjectDependencies__Group_3_1__1__Impl : ( ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1 ) ) ;
    public final void rule__ProjectDependencies__Group_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3494:1: ( ( ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1 ) ) )
            // InternalN4MFParser.g:3495:1: ( ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1 ) )
            {
            // InternalN4MFParser.g:3495:1: ( ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1 ) )
            // InternalN4MFParser.g:3496:2: ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1 )
            {
             before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_1_1()); 
            // InternalN4MFParser.g:3497:2: ( rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1 )
            // InternalN4MFParser.g:3497:3: rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1();

            state._fsp--;


            }

             after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__Group_3_1__1__Impl"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__0"
    // InternalN4MFParser.g:3506:1: rule__ProvidedRuntimeLibraries__Group__0 : rule__ProvidedRuntimeLibraries__Group__0__Impl rule__ProvidedRuntimeLibraries__Group__1 ;
    public final void rule__ProvidedRuntimeLibraries__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3510:1: ( rule__ProvidedRuntimeLibraries__Group__0__Impl rule__ProvidedRuntimeLibraries__Group__1 )
            // InternalN4MFParser.g:3511:2: rule__ProvidedRuntimeLibraries__Group__0__Impl rule__ProvidedRuntimeLibraries__Group__1
            {
            pushFollow(FOLLOW_25);
            rule__ProvidedRuntimeLibraries__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__0"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__0__Impl"
    // InternalN4MFParser.g:3518:1: rule__ProvidedRuntimeLibraries__Group__0__Impl : ( () ) ;
    public final void rule__ProvidedRuntimeLibraries__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3522:1: ( ( () ) )
            // InternalN4MFParser.g:3523:1: ( () )
            {
            // InternalN4MFParser.g:3523:1: ( () )
            // InternalN4MFParser.g:3524:2: ()
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAction_0()); 
            // InternalN4MFParser.g:3525:2: ()
            // InternalN4MFParser.g:3525:3: 
            {
            }

             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__0__Impl"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__1"
    // InternalN4MFParser.g:3533:1: rule__ProvidedRuntimeLibraries__Group__1 : rule__ProvidedRuntimeLibraries__Group__1__Impl rule__ProvidedRuntimeLibraries__Group__2 ;
    public final void rule__ProvidedRuntimeLibraries__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3537:1: ( rule__ProvidedRuntimeLibraries__Group__1__Impl rule__ProvidedRuntimeLibraries__Group__2 )
            // InternalN4MFParser.g:3538:2: rule__ProvidedRuntimeLibraries__Group__1__Impl rule__ProvidedRuntimeLibraries__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__ProvidedRuntimeLibraries__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__1"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__1__Impl"
    // InternalN4MFParser.g:3545:1: rule__ProvidedRuntimeLibraries__Group__1__Impl : ( ProvidedRuntimeLibraries ) ;
    public final void rule__ProvidedRuntimeLibraries__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3549:1: ( ( ProvidedRuntimeLibraries ) )
            // InternalN4MFParser.g:3550:1: ( ProvidedRuntimeLibraries )
            {
            // InternalN4MFParser.g:3550:1: ( ProvidedRuntimeLibraries )
            // InternalN4MFParser.g:3551:2: ProvidedRuntimeLibraries
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesKeyword_1()); 
            match(input,ProvidedRuntimeLibraries,FOLLOW_2); 
             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__1__Impl"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__2"
    // InternalN4MFParser.g:3560:1: rule__ProvidedRuntimeLibraries__Group__2 : rule__ProvidedRuntimeLibraries__Group__2__Impl rule__ProvidedRuntimeLibraries__Group__3 ;
    public final void rule__ProvidedRuntimeLibraries__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3564:1: ( rule__ProvidedRuntimeLibraries__Group__2__Impl rule__ProvidedRuntimeLibraries__Group__3 )
            // InternalN4MFParser.g:3565:2: rule__ProvidedRuntimeLibraries__Group__2__Impl rule__ProvidedRuntimeLibraries__Group__3
            {
            pushFollow(FOLLOW_19);
            rule__ProvidedRuntimeLibraries__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__2"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__2__Impl"
    // InternalN4MFParser.g:3572:1: rule__ProvidedRuntimeLibraries__Group__2__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ProvidedRuntimeLibraries__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3576:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3577:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3577:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3578:2: LeftCurlyBracket
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__2__Impl"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__3"
    // InternalN4MFParser.g:3587:1: rule__ProvidedRuntimeLibraries__Group__3 : rule__ProvidedRuntimeLibraries__Group__3__Impl rule__ProvidedRuntimeLibraries__Group__4 ;
    public final void rule__ProvidedRuntimeLibraries__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3591:1: ( rule__ProvidedRuntimeLibraries__Group__3__Impl rule__ProvidedRuntimeLibraries__Group__4 )
            // InternalN4MFParser.g:3592:2: rule__ProvidedRuntimeLibraries__Group__3__Impl rule__ProvidedRuntimeLibraries__Group__4
            {
            pushFollow(FOLLOW_19);
            rule__ProvidedRuntimeLibraries__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__3"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__3__Impl"
    // InternalN4MFParser.g:3599:1: rule__ProvidedRuntimeLibraries__Group__3__Impl : ( ( rule__ProvidedRuntimeLibraries__Group_3__0 )? ) ;
    public final void rule__ProvidedRuntimeLibraries__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3603:1: ( ( ( rule__ProvidedRuntimeLibraries__Group_3__0 )? ) )
            // InternalN4MFParser.g:3604:1: ( ( rule__ProvidedRuntimeLibraries__Group_3__0 )? )
            {
            // InternalN4MFParser.g:3604:1: ( ( rule__ProvidedRuntimeLibraries__Group_3__0 )? )
            // InternalN4MFParser.g:3605:2: ( rule__ProvidedRuntimeLibraries__Group_3__0 )?
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3()); 
            // InternalN4MFParser.g:3606:2: ( rule__ProvidedRuntimeLibraries__Group_3__0 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==ProjectDependencies||LA24_0==ProjectVersion||LA24_0==ModuleFilters||(LA24_0>=ProjectType && LA24_0<=Application)||LA24_0==VendorName||(LA24_0>=Libraries && LA24_0<=VendorId)||LA24_0==Sources||LA24_0==Content||LA24_0==Output||(LA24_0>=Test && LA24_0<=API)||LA24_0==RULE_ID) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalN4MFParser.g:3606:3: rule__ProvidedRuntimeLibraries__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProvidedRuntimeLibraries__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__3__Impl"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__4"
    // InternalN4MFParser.g:3614:1: rule__ProvidedRuntimeLibraries__Group__4 : rule__ProvidedRuntimeLibraries__Group__4__Impl ;
    public final void rule__ProvidedRuntimeLibraries__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3618:1: ( rule__ProvidedRuntimeLibraries__Group__4__Impl )
            // InternalN4MFParser.g:3619:2: rule__ProvidedRuntimeLibraries__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__4"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group__4__Impl"
    // InternalN4MFParser.g:3625:1: rule__ProvidedRuntimeLibraries__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ProvidedRuntimeLibraries__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3629:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3630:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3630:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3631:2: RightCurlyBracket
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group__4__Impl"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group_3__0"
    // InternalN4MFParser.g:3641:1: rule__ProvidedRuntimeLibraries__Group_3__0 : rule__ProvidedRuntimeLibraries__Group_3__0__Impl rule__ProvidedRuntimeLibraries__Group_3__1 ;
    public final void rule__ProvidedRuntimeLibraries__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3645:1: ( rule__ProvidedRuntimeLibraries__Group_3__0__Impl rule__ProvidedRuntimeLibraries__Group_3__1 )
            // InternalN4MFParser.g:3646:2: rule__ProvidedRuntimeLibraries__Group_3__0__Impl rule__ProvidedRuntimeLibraries__Group_3__1
            {
            pushFollow(FOLLOW_20);
            rule__ProvidedRuntimeLibraries__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group_3__0"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group_3__0__Impl"
    // InternalN4MFParser.g:3653:1: rule__ProvidedRuntimeLibraries__Group_3__0__Impl : ( ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0 ) ) ;
    public final void rule__ProvidedRuntimeLibraries__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3657:1: ( ( ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0 ) ) )
            // InternalN4MFParser.g:3658:1: ( ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0 ) )
            {
            // InternalN4MFParser.g:3658:1: ( ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0 ) )
            // InternalN4MFParser.g:3659:2: ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0 )
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_0()); 
            // InternalN4MFParser.g:3660:2: ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0 )
            // InternalN4MFParser.g:3660:3: rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0();

            state._fsp--;


            }

             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group_3__0__Impl"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group_3__1"
    // InternalN4MFParser.g:3668:1: rule__ProvidedRuntimeLibraries__Group_3__1 : rule__ProvidedRuntimeLibraries__Group_3__1__Impl ;
    public final void rule__ProvidedRuntimeLibraries__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3672:1: ( rule__ProvidedRuntimeLibraries__Group_3__1__Impl )
            // InternalN4MFParser.g:3673:2: rule__ProvidedRuntimeLibraries__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group_3__1"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group_3__1__Impl"
    // InternalN4MFParser.g:3679:1: rule__ProvidedRuntimeLibraries__Group_3__1__Impl : ( ( rule__ProvidedRuntimeLibraries__Group_3_1__0 )* ) ;
    public final void rule__ProvidedRuntimeLibraries__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3683:1: ( ( ( rule__ProvidedRuntimeLibraries__Group_3_1__0 )* ) )
            // InternalN4MFParser.g:3684:1: ( ( rule__ProvidedRuntimeLibraries__Group_3_1__0 )* )
            {
            // InternalN4MFParser.g:3684:1: ( ( rule__ProvidedRuntimeLibraries__Group_3_1__0 )* )
            // InternalN4MFParser.g:3685:2: ( rule__ProvidedRuntimeLibraries__Group_3_1__0 )*
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3_1()); 
            // InternalN4MFParser.g:3686:2: ( rule__ProvidedRuntimeLibraries__Group_3_1__0 )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==Comma) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalN4MFParser.g:3686:3: rule__ProvidedRuntimeLibraries__Group_3_1__0
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__ProvidedRuntimeLibraries__Group_3_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);

             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group_3__1__Impl"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group_3_1__0"
    // InternalN4MFParser.g:3695:1: rule__ProvidedRuntimeLibraries__Group_3_1__0 : rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl rule__ProvidedRuntimeLibraries__Group_3_1__1 ;
    public final void rule__ProvidedRuntimeLibraries__Group_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3699:1: ( rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl rule__ProvidedRuntimeLibraries__Group_3_1__1 )
            // InternalN4MFParser.g:3700:2: rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl rule__ProvidedRuntimeLibraries__Group_3_1__1
            {
            pushFollow(FOLLOW_4);
            rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group_3_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group_3_1__0"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl"
    // InternalN4MFParser.g:3707:1: rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl : ( Comma ) ;
    public final void rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3711:1: ( ( Comma ) )
            // InternalN4MFParser.g:3712:1: ( Comma )
            {
            // InternalN4MFParser.g:3712:1: ( Comma )
            // InternalN4MFParser.g:3713:2: Comma
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getCommaKeyword_3_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getCommaKeyword_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group_3_1__0__Impl"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group_3_1__1"
    // InternalN4MFParser.g:3722:1: rule__ProvidedRuntimeLibraries__Group_3_1__1 : rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl ;
    public final void rule__ProvidedRuntimeLibraries__Group_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3726:1: ( rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl )
            // InternalN4MFParser.g:3727:2: rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group_3_1__1"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl"
    // InternalN4MFParser.g:3733:1: rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl : ( ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1 ) ) ;
    public final void rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3737:1: ( ( ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1 ) ) )
            // InternalN4MFParser.g:3738:1: ( ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1 ) )
            {
            // InternalN4MFParser.g:3738:1: ( ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1 ) )
            // InternalN4MFParser.g:3739:2: ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1 )
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_1_1()); 
            // InternalN4MFParser.g:3740:2: ( rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1 )
            // InternalN4MFParser.g:3740:3: rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1
            {
            pushFollow(FOLLOW_2);
            rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1();

            state._fsp--;


            }

             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__Group_3_1__1__Impl"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__0"
    // InternalN4MFParser.g:3749:1: rule__RequiredRuntimeLibraries__Group__0 : rule__RequiredRuntimeLibraries__Group__0__Impl rule__RequiredRuntimeLibraries__Group__1 ;
    public final void rule__RequiredRuntimeLibraries__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3753:1: ( rule__RequiredRuntimeLibraries__Group__0__Impl rule__RequiredRuntimeLibraries__Group__1 )
            // InternalN4MFParser.g:3754:2: rule__RequiredRuntimeLibraries__Group__0__Impl rule__RequiredRuntimeLibraries__Group__1
            {
            pushFollow(FOLLOW_26);
            rule__RequiredRuntimeLibraries__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__0"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__0__Impl"
    // InternalN4MFParser.g:3761:1: rule__RequiredRuntimeLibraries__Group__0__Impl : ( () ) ;
    public final void rule__RequiredRuntimeLibraries__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3765:1: ( ( () ) )
            // InternalN4MFParser.g:3766:1: ( () )
            {
            // InternalN4MFParser.g:3766:1: ( () )
            // InternalN4MFParser.g:3767:2: ()
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAction_0()); 
            // InternalN4MFParser.g:3768:2: ()
            // InternalN4MFParser.g:3768:3: 
            {
            }

             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__0__Impl"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__1"
    // InternalN4MFParser.g:3776:1: rule__RequiredRuntimeLibraries__Group__1 : rule__RequiredRuntimeLibraries__Group__1__Impl rule__RequiredRuntimeLibraries__Group__2 ;
    public final void rule__RequiredRuntimeLibraries__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3780:1: ( rule__RequiredRuntimeLibraries__Group__1__Impl rule__RequiredRuntimeLibraries__Group__2 )
            // InternalN4MFParser.g:3781:2: rule__RequiredRuntimeLibraries__Group__1__Impl rule__RequiredRuntimeLibraries__Group__2
            {
            pushFollow(FOLLOW_8);
            rule__RequiredRuntimeLibraries__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__1"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__1__Impl"
    // InternalN4MFParser.g:3788:1: rule__RequiredRuntimeLibraries__Group__1__Impl : ( RequiredRuntimeLibraries ) ;
    public final void rule__RequiredRuntimeLibraries__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3792:1: ( ( RequiredRuntimeLibraries ) )
            // InternalN4MFParser.g:3793:1: ( RequiredRuntimeLibraries )
            {
            // InternalN4MFParser.g:3793:1: ( RequiredRuntimeLibraries )
            // InternalN4MFParser.g:3794:2: RequiredRuntimeLibraries
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesKeyword_1()); 
            match(input,RequiredRuntimeLibraries,FOLLOW_2); 
             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__1__Impl"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__2"
    // InternalN4MFParser.g:3803:1: rule__RequiredRuntimeLibraries__Group__2 : rule__RequiredRuntimeLibraries__Group__2__Impl rule__RequiredRuntimeLibraries__Group__3 ;
    public final void rule__RequiredRuntimeLibraries__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3807:1: ( rule__RequiredRuntimeLibraries__Group__2__Impl rule__RequiredRuntimeLibraries__Group__3 )
            // InternalN4MFParser.g:3808:2: rule__RequiredRuntimeLibraries__Group__2__Impl rule__RequiredRuntimeLibraries__Group__3
            {
            pushFollow(FOLLOW_19);
            rule__RequiredRuntimeLibraries__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__2"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__2__Impl"
    // InternalN4MFParser.g:3815:1: rule__RequiredRuntimeLibraries__Group__2__Impl : ( LeftCurlyBracket ) ;
    public final void rule__RequiredRuntimeLibraries__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3819:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:3820:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:3820:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:3821:2: LeftCurlyBracket
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,LeftCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__2__Impl"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__3"
    // InternalN4MFParser.g:3830:1: rule__RequiredRuntimeLibraries__Group__3 : rule__RequiredRuntimeLibraries__Group__3__Impl rule__RequiredRuntimeLibraries__Group__4 ;
    public final void rule__RequiredRuntimeLibraries__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3834:1: ( rule__RequiredRuntimeLibraries__Group__3__Impl rule__RequiredRuntimeLibraries__Group__4 )
            // InternalN4MFParser.g:3835:2: rule__RequiredRuntimeLibraries__Group__3__Impl rule__RequiredRuntimeLibraries__Group__4
            {
            pushFollow(FOLLOW_19);
            rule__RequiredRuntimeLibraries__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__3"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__3__Impl"
    // InternalN4MFParser.g:3842:1: rule__RequiredRuntimeLibraries__Group__3__Impl : ( ( rule__RequiredRuntimeLibraries__Group_3__0 )? ) ;
    public final void rule__RequiredRuntimeLibraries__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3846:1: ( ( ( rule__RequiredRuntimeLibraries__Group_3__0 )? ) )
            // InternalN4MFParser.g:3847:1: ( ( rule__RequiredRuntimeLibraries__Group_3__0 )? )
            {
            // InternalN4MFParser.g:3847:1: ( ( rule__RequiredRuntimeLibraries__Group_3__0 )? )
            // InternalN4MFParser.g:3848:2: ( rule__RequiredRuntimeLibraries__Group_3__0 )?
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3()); 
            // InternalN4MFParser.g:3849:2: ( rule__RequiredRuntimeLibraries__Group_3__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==ProjectDependencies||LA26_0==ProjectVersion||LA26_0==ModuleFilters||(LA26_0>=ProjectType && LA26_0<=Application)||LA26_0==VendorName||(LA26_0>=Libraries && LA26_0<=VendorId)||LA26_0==Sources||LA26_0==Content||LA26_0==Output||(LA26_0>=Test && LA26_0<=API)||LA26_0==RULE_ID) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalN4MFParser.g:3849:3: rule__RequiredRuntimeLibraries__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RequiredRuntimeLibraries__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__3__Impl"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__4"
    // InternalN4MFParser.g:3857:1: rule__RequiredRuntimeLibraries__Group__4 : rule__RequiredRuntimeLibraries__Group__4__Impl ;
    public final void rule__RequiredRuntimeLibraries__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3861:1: ( rule__RequiredRuntimeLibraries__Group__4__Impl )
            // InternalN4MFParser.g:3862:2: rule__RequiredRuntimeLibraries__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__4"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group__4__Impl"
    // InternalN4MFParser.g:3868:1: rule__RequiredRuntimeLibraries__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__RequiredRuntimeLibraries__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3872:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:3873:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:3873:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:3874:2: RightCurlyBracket
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4()); 
            match(input,RightCurlyBracket,FOLLOW_2); 
             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group__4__Impl"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group_3__0"
    // InternalN4MFParser.g:3884:1: rule__RequiredRuntimeLibraries__Group_3__0 : rule__RequiredRuntimeLibraries__Group_3__0__Impl rule__RequiredRuntimeLibraries__Group_3__1 ;
    public final void rule__RequiredRuntimeLibraries__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3888:1: ( rule__RequiredRuntimeLibraries__Group_3__0__Impl rule__RequiredRuntimeLibraries__Group_3__1 )
            // InternalN4MFParser.g:3889:2: rule__RequiredRuntimeLibraries__Group_3__0__Impl rule__RequiredRuntimeLibraries__Group_3__1
            {
            pushFollow(FOLLOW_20);
            rule__RequiredRuntimeLibraries__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group_3__0"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group_3__0__Impl"
    // InternalN4MFParser.g:3896:1: rule__RequiredRuntimeLibraries__Group_3__0__Impl : ( ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0 ) ) ;
    public final void rule__RequiredRuntimeLibraries__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3900:1: ( ( ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0 ) ) )
            // InternalN4MFParser.g:3901:1: ( ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0 ) )
            {
            // InternalN4MFParser.g:3901:1: ( ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0 ) )
            // InternalN4MFParser.g:3902:2: ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0 )
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_0()); 
            // InternalN4MFParser.g:3903:2: ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0 )
            // InternalN4MFParser.g:3903:3: rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0();

            state._fsp--;


            }

             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group_3__0__Impl"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group_3__1"
    // InternalN4MFParser.g:3911:1: rule__RequiredRuntimeLibraries__Group_3__1 : rule__RequiredRuntimeLibraries__Group_3__1__Impl ;
    public final void rule__RequiredRuntimeLibraries__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3915:1: ( rule__RequiredRuntimeLibraries__Group_3__1__Impl )
            // InternalN4MFParser.g:3916:2: rule__RequiredRuntimeLibraries__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group_3__1"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group_3__1__Impl"
    // InternalN4MFParser.g:3922:1: rule__RequiredRuntimeLibraries__Group_3__1__Impl : ( ( rule__RequiredRuntimeLibraries__Group_3_1__0 )* ) ;
    public final void rule__RequiredRuntimeLibraries__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3926:1: ( ( ( rule__RequiredRuntimeLibraries__Group_3_1__0 )* ) )
            // InternalN4MFParser.g:3927:1: ( ( rule__RequiredRuntimeLibraries__Group_3_1__0 )* )
            {
            // InternalN4MFParser.g:3927:1: ( ( rule__RequiredRuntimeLibraries__Group_3_1__0 )* )
            // InternalN4MFParser.g:3928:2: ( rule__RequiredRuntimeLibraries__Group_3_1__0 )*
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3_1()); 
            // InternalN4MFParser.g:3929:2: ( rule__RequiredRuntimeLibraries__Group_3_1__0 )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==Comma) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalN4MFParser.g:3929:3: rule__RequiredRuntimeLibraries__Group_3_1__0
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__RequiredRuntimeLibraries__Group_3_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group_3__1__Impl"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group_3_1__0"
    // InternalN4MFParser.g:3938:1: rule__RequiredRuntimeLibraries__Group_3_1__0 : rule__RequiredRuntimeLibraries__Group_3_1__0__Impl rule__RequiredRuntimeLibraries__Group_3_1__1 ;
    public final void rule__RequiredRuntimeLibraries__Group_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3942:1: ( rule__RequiredRuntimeLibraries__Group_3_1__0__Impl rule__RequiredRuntimeLibraries__Group_3_1__1 )
            // InternalN4MFParser.g:3943:2: rule__RequiredRuntimeLibraries__Group_3_1__0__Impl rule__RequiredRuntimeLibraries__Group_3_1__1
            {
            pushFollow(FOLLOW_4);
            rule__RequiredRuntimeLibraries__Group_3_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group_3_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group_3_1__0"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group_3_1__0__Impl"
    // InternalN4MFParser.g:3950:1: rule__RequiredRuntimeLibraries__Group_3_1__0__Impl : ( Comma ) ;
    public final void rule__RequiredRuntimeLibraries__Group_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3954:1: ( ( Comma ) )
            // InternalN4MFParser.g:3955:1: ( Comma )
            {
            // InternalN4MFParser.g:3955:1: ( Comma )
            // InternalN4MFParser.g:3956:2: Comma
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getCommaKeyword_3_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getCommaKeyword_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group_3_1__0__Impl"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group_3_1__1"
    // InternalN4MFParser.g:3965:1: rule__RequiredRuntimeLibraries__Group_3_1__1 : rule__RequiredRuntimeLibraries__Group_3_1__1__Impl ;
    public final void rule__RequiredRuntimeLibraries__Group_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3969:1: ( rule__RequiredRuntimeLibraries__Group_3_1__1__Impl )
            // InternalN4MFParser.g:3970:2: rule__RequiredRuntimeLibraries__Group_3_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__Group_3_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group_3_1__1"


    // $ANTLR start "rule__RequiredRuntimeLibraries__Group_3_1__1__Impl"
    // InternalN4MFParser.g:3976:1: rule__RequiredRuntimeLibraries__Group_3_1__1__Impl : ( ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1 ) ) ;
    public final void rule__RequiredRuntimeLibraries__Group_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3980:1: ( ( ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1 ) ) )
            // InternalN4MFParser.g:3981:1: ( ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1 ) )
            {
            // InternalN4MFParser.g:3981:1: ( ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1 ) )
            // InternalN4MFParser.g:3982:2: ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1 )
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_1_1()); 
            // InternalN4MFParser.g:3983:2: ( rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1 )
            // InternalN4MFParser.g:3983:3: rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1
            {
            pushFollow(FOLLOW_2);
            rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1();

            state._fsp--;


            }

             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__Group_3_1__1__Impl"


    // $ANTLR start "rule__ExtendedRuntimeEnvironment__Group__0"
    // InternalN4MFParser.g:3992:1: rule__ExtendedRuntimeEnvironment__Group__0 : rule__ExtendedRuntimeEnvironment__Group__0__Impl rule__ExtendedRuntimeEnvironment__Group__1 ;
    public final void rule__ExtendedRuntimeEnvironment__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:3996:1: ( rule__ExtendedRuntimeEnvironment__Group__0__Impl rule__ExtendedRuntimeEnvironment__Group__1 )
            // InternalN4MFParser.g:3997:2: rule__ExtendedRuntimeEnvironment__Group__0__Impl rule__ExtendedRuntimeEnvironment__Group__1
            {
            pushFollow(FOLLOW_27);
            rule__ExtendedRuntimeEnvironment__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExtendedRuntimeEnvironment__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExtendedRuntimeEnvironment__Group__0"


    // $ANTLR start "rule__ExtendedRuntimeEnvironment__Group__0__Impl"
    // InternalN4MFParser.g:4004:1: rule__ExtendedRuntimeEnvironment__Group__0__Impl : ( () ) ;
    public final void rule__ExtendedRuntimeEnvironment__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4008:1: ( ( () ) )
            // InternalN4MFParser.g:4009:1: ( () )
            {
            // InternalN4MFParser.g:4009:1: ( () )
            // InternalN4MFParser.g:4010:2: ()
            {
             before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAction_0()); 
            // InternalN4MFParser.g:4011:2: ()
            // InternalN4MFParser.g:4011:3: 
            {
            }

             after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExtendedRuntimeEnvironment__Group__0__Impl"


    // $ANTLR start "rule__ExtendedRuntimeEnvironment__Group__1"
    // InternalN4MFParser.g:4019:1: rule__ExtendedRuntimeEnvironment__Group__1 : rule__ExtendedRuntimeEnvironment__Group__1__Impl rule__ExtendedRuntimeEnvironment__Group__2 ;
    public final void rule__ExtendedRuntimeEnvironment__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4023:1: ( rule__ExtendedRuntimeEnvironment__Group__1__Impl rule__ExtendedRuntimeEnvironment__Group__2 )
            // InternalN4MFParser.g:4024:2: rule__ExtendedRuntimeEnvironment__Group__1__Impl rule__ExtendedRuntimeEnvironment__Group__2
            {
            pushFollow(FOLLOW_3);
            rule__ExtendedRuntimeEnvironment__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExtendedRuntimeEnvironment__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExtendedRuntimeEnvironment__Group__1"


    // $ANTLR start "rule__ExtendedRuntimeEnvironment__Group__1__Impl"
    // InternalN4MFParser.g:4031:1: rule__ExtendedRuntimeEnvironment__Group__1__Impl : ( ExtendedRuntimeEnvironment ) ;
    public final void rule__ExtendedRuntimeEnvironment__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4035:1: ( ( ExtendedRuntimeEnvironment ) )
            // InternalN4MFParser.g:4036:1: ( ExtendedRuntimeEnvironment )
            {
            // InternalN4MFParser.g:4036:1: ( ExtendedRuntimeEnvironment )
            // InternalN4MFParser.g:4037:2: ExtendedRuntimeEnvironment
            {
             before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentKeyword_1()); 
            match(input,ExtendedRuntimeEnvironment,FOLLOW_2); 
             after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExtendedRuntimeEnvironment__Group__1__Impl"


    // $ANTLR start "rule__ExtendedRuntimeEnvironment__Group__2"
    // InternalN4MFParser.g:4046:1: rule__ExtendedRuntimeEnvironment__Group__2 : rule__ExtendedRuntimeEnvironment__Group__2__Impl rule__ExtendedRuntimeEnvironment__Group__3 ;
    public final void rule__ExtendedRuntimeEnvironment__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4050:1: ( rule__ExtendedRuntimeEnvironment__Group__2__Impl rule__ExtendedRuntimeEnvironment__Group__3 )
            // InternalN4MFParser.g:4051:2: rule__ExtendedRuntimeEnvironment__Group__2__Impl rule__ExtendedRuntimeEnvironment__Group__3
            {
            pushFollow(FOLLOW_4);
            rule__ExtendedRuntimeEnvironment__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExtendedRuntimeEnvironment__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExtendedRuntimeEnvironment__Group__2"


    // $ANTLR start "rule__ExtendedRuntimeEnvironment__Group__2__Impl"
    // InternalN4MFParser.g:4058:1: rule__ExtendedRuntimeEnvironment__Group__2__Impl : ( Colon ) ;
    public final void rule__ExtendedRuntimeEnvironment__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4062:1: ( ( Colon ) )
            // InternalN4MFParser.g:4063:1: ( Colon )
            {
            // InternalN4MFParser.g:4063:1: ( Colon )
            // InternalN4MFParser.g:4064:2: Colon
            {
             before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getColonKeyword_2()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getColonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExtendedRuntimeEnvironment__Group__2__Impl"


    // $ANTLR start "rule__ExtendedRuntimeEnvironment__Group__3"
    // InternalN4MFParser.g:4073:1: rule__ExtendedRuntimeEnvironment__Group__3 : rule__ExtendedRuntimeEnvironment__Group__3__Impl ;
    public final void rule__ExtendedRuntimeEnvironment__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4077:1: ( rule__ExtendedRuntimeEnvironment__Group__3__Impl )
            // InternalN4MFParser.g:4078:2: rule__ExtendedRuntimeEnvironment__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ExtendedRuntimeEnvironment__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExtendedRuntimeEnvironment__Group__3"


    // $ANTLR start "rule__ExtendedRuntimeEnvironment__Group__3__Impl"
    // InternalN4MFParser.g:4084:1: rule__ExtendedRuntimeEnvironment__Group__3__Impl : ( ( rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3 ) ) ;
    public final void rule__ExtendedRuntimeEnvironment__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4088:1: ( ( ( rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3 ) ) )
            // InternalN4MFParser.g:4089:1: ( ( rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3 ) )
            {
            // InternalN4MFParser.g:4089:1: ( ( rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3 ) )
            // InternalN4MFParser.g:4090:2: ( rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3 )
            {
             before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAssignment_3()); 
            // InternalN4MFParser.g:4091:2: ( rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3 )
            // InternalN4MFParser.g:4091:3: rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExtendedRuntimeEnvironment__Group__3__Impl"


    // $ANTLR start "rule__DeclaredVersion__Group__0"
    // InternalN4MFParser.g:4100:1: rule__DeclaredVersion__Group__0 : rule__DeclaredVersion__Group__0__Impl rule__DeclaredVersion__Group__1 ;
    public final void rule__DeclaredVersion__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4104:1: ( rule__DeclaredVersion__Group__0__Impl rule__DeclaredVersion__Group__1 )
            // InternalN4MFParser.g:4105:2: rule__DeclaredVersion__Group__0__Impl rule__DeclaredVersion__Group__1
            {
            pushFollow(FOLLOW_28);
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
    // InternalN4MFParser.g:4112:1: rule__DeclaredVersion__Group__0__Impl : ( ( rule__DeclaredVersion__MajorAssignment_0 ) ) ;
    public final void rule__DeclaredVersion__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4116:1: ( ( ( rule__DeclaredVersion__MajorAssignment_0 ) ) )
            // InternalN4MFParser.g:4117:1: ( ( rule__DeclaredVersion__MajorAssignment_0 ) )
            {
            // InternalN4MFParser.g:4117:1: ( ( rule__DeclaredVersion__MajorAssignment_0 ) )
            // InternalN4MFParser.g:4118:2: ( rule__DeclaredVersion__MajorAssignment_0 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getMajorAssignment_0()); 
            // InternalN4MFParser.g:4119:2: ( rule__DeclaredVersion__MajorAssignment_0 )
            // InternalN4MFParser.g:4119:3: rule__DeclaredVersion__MajorAssignment_0
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
    // InternalN4MFParser.g:4127:1: rule__DeclaredVersion__Group__1 : rule__DeclaredVersion__Group__1__Impl rule__DeclaredVersion__Group__2 ;
    public final void rule__DeclaredVersion__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4131:1: ( rule__DeclaredVersion__Group__1__Impl rule__DeclaredVersion__Group__2 )
            // InternalN4MFParser.g:4132:2: rule__DeclaredVersion__Group__1__Impl rule__DeclaredVersion__Group__2
            {
            pushFollow(FOLLOW_28);
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
    // InternalN4MFParser.g:4139:1: rule__DeclaredVersion__Group__1__Impl : ( ( rule__DeclaredVersion__Group_1__0 )? ) ;
    public final void rule__DeclaredVersion__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4143:1: ( ( ( rule__DeclaredVersion__Group_1__0 )? ) )
            // InternalN4MFParser.g:4144:1: ( ( rule__DeclaredVersion__Group_1__0 )? )
            {
            // InternalN4MFParser.g:4144:1: ( ( rule__DeclaredVersion__Group_1__0 )? )
            // InternalN4MFParser.g:4145:2: ( rule__DeclaredVersion__Group_1__0 )?
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup_1()); 
            // InternalN4MFParser.g:4146:2: ( rule__DeclaredVersion__Group_1__0 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==FullStop) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalN4MFParser.g:4146:3: rule__DeclaredVersion__Group_1__0
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
    // InternalN4MFParser.g:4154:1: rule__DeclaredVersion__Group__2 : rule__DeclaredVersion__Group__2__Impl ;
    public final void rule__DeclaredVersion__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4158:1: ( rule__DeclaredVersion__Group__2__Impl )
            // InternalN4MFParser.g:4159:2: rule__DeclaredVersion__Group__2__Impl
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
    // InternalN4MFParser.g:4165:1: rule__DeclaredVersion__Group__2__Impl : ( ( rule__DeclaredVersion__Group_2__0 )? ) ;
    public final void rule__DeclaredVersion__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4169:1: ( ( ( rule__DeclaredVersion__Group_2__0 )? ) )
            // InternalN4MFParser.g:4170:1: ( ( rule__DeclaredVersion__Group_2__0 )? )
            {
            // InternalN4MFParser.g:4170:1: ( ( rule__DeclaredVersion__Group_2__0 )? )
            // InternalN4MFParser.g:4171:2: ( rule__DeclaredVersion__Group_2__0 )?
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup_2()); 
            // InternalN4MFParser.g:4172:2: ( rule__DeclaredVersion__Group_2__0 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==HyphenMinus) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalN4MFParser.g:4172:3: rule__DeclaredVersion__Group_2__0
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
    // InternalN4MFParser.g:4181:1: rule__DeclaredVersion__Group_1__0 : rule__DeclaredVersion__Group_1__0__Impl rule__DeclaredVersion__Group_1__1 ;
    public final void rule__DeclaredVersion__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4185:1: ( rule__DeclaredVersion__Group_1__0__Impl rule__DeclaredVersion__Group_1__1 )
            // InternalN4MFParser.g:4186:2: rule__DeclaredVersion__Group_1__0__Impl rule__DeclaredVersion__Group_1__1
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
    // InternalN4MFParser.g:4193:1: rule__DeclaredVersion__Group_1__0__Impl : ( FullStop ) ;
    public final void rule__DeclaredVersion__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4197:1: ( ( FullStop ) )
            // InternalN4MFParser.g:4198:1: ( FullStop )
            {
            // InternalN4MFParser.g:4198:1: ( FullStop )
            // InternalN4MFParser.g:4199:2: FullStop
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
    // InternalN4MFParser.g:4208:1: rule__DeclaredVersion__Group_1__1 : rule__DeclaredVersion__Group_1__1__Impl rule__DeclaredVersion__Group_1__2 ;
    public final void rule__DeclaredVersion__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4212:1: ( rule__DeclaredVersion__Group_1__1__Impl rule__DeclaredVersion__Group_1__2 )
            // InternalN4MFParser.g:4213:2: rule__DeclaredVersion__Group_1__1__Impl rule__DeclaredVersion__Group_1__2
            {
            pushFollow(FOLLOW_29);
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
    // InternalN4MFParser.g:4220:1: rule__DeclaredVersion__Group_1__1__Impl : ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) ) ;
    public final void rule__DeclaredVersion__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4224:1: ( ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) ) )
            // InternalN4MFParser.g:4225:1: ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) )
            {
            // InternalN4MFParser.g:4225:1: ( ( rule__DeclaredVersion__MinorAssignment_1_1 ) )
            // InternalN4MFParser.g:4226:2: ( rule__DeclaredVersion__MinorAssignment_1_1 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getMinorAssignment_1_1()); 
            // InternalN4MFParser.g:4227:2: ( rule__DeclaredVersion__MinorAssignment_1_1 )
            // InternalN4MFParser.g:4227:3: rule__DeclaredVersion__MinorAssignment_1_1
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
    // InternalN4MFParser.g:4235:1: rule__DeclaredVersion__Group_1__2 : rule__DeclaredVersion__Group_1__2__Impl ;
    public final void rule__DeclaredVersion__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4239:1: ( rule__DeclaredVersion__Group_1__2__Impl )
            // InternalN4MFParser.g:4240:2: rule__DeclaredVersion__Group_1__2__Impl
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
    // InternalN4MFParser.g:4246:1: rule__DeclaredVersion__Group_1__2__Impl : ( ( rule__DeclaredVersion__Group_1_2__0 )? ) ;
    public final void rule__DeclaredVersion__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4250:1: ( ( ( rule__DeclaredVersion__Group_1_2__0 )? ) )
            // InternalN4MFParser.g:4251:1: ( ( rule__DeclaredVersion__Group_1_2__0 )? )
            {
            // InternalN4MFParser.g:4251:1: ( ( rule__DeclaredVersion__Group_1_2__0 )? )
            // InternalN4MFParser.g:4252:2: ( rule__DeclaredVersion__Group_1_2__0 )?
            {
             before(grammarAccess.getDeclaredVersionAccess().getGroup_1_2()); 
            // InternalN4MFParser.g:4253:2: ( rule__DeclaredVersion__Group_1_2__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==FullStop) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalN4MFParser.g:4253:3: rule__DeclaredVersion__Group_1_2__0
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
    // InternalN4MFParser.g:4262:1: rule__DeclaredVersion__Group_1_2__0 : rule__DeclaredVersion__Group_1_2__0__Impl rule__DeclaredVersion__Group_1_2__1 ;
    public final void rule__DeclaredVersion__Group_1_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4266:1: ( rule__DeclaredVersion__Group_1_2__0__Impl rule__DeclaredVersion__Group_1_2__1 )
            // InternalN4MFParser.g:4267:2: rule__DeclaredVersion__Group_1_2__0__Impl rule__DeclaredVersion__Group_1_2__1
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
    // InternalN4MFParser.g:4274:1: rule__DeclaredVersion__Group_1_2__0__Impl : ( FullStop ) ;
    public final void rule__DeclaredVersion__Group_1_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4278:1: ( ( FullStop ) )
            // InternalN4MFParser.g:4279:1: ( FullStop )
            {
            // InternalN4MFParser.g:4279:1: ( FullStop )
            // InternalN4MFParser.g:4280:2: FullStop
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
    // InternalN4MFParser.g:4289:1: rule__DeclaredVersion__Group_1_2__1 : rule__DeclaredVersion__Group_1_2__1__Impl ;
    public final void rule__DeclaredVersion__Group_1_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4293:1: ( rule__DeclaredVersion__Group_1_2__1__Impl )
            // InternalN4MFParser.g:4294:2: rule__DeclaredVersion__Group_1_2__1__Impl
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
    // InternalN4MFParser.g:4300:1: rule__DeclaredVersion__Group_1_2__1__Impl : ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) ) ;
    public final void rule__DeclaredVersion__Group_1_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4304:1: ( ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) ) )
            // InternalN4MFParser.g:4305:1: ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) )
            {
            // InternalN4MFParser.g:4305:1: ( ( rule__DeclaredVersion__MicroAssignment_1_2_1 ) )
            // InternalN4MFParser.g:4306:2: ( rule__DeclaredVersion__MicroAssignment_1_2_1 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getMicroAssignment_1_2_1()); 
            // InternalN4MFParser.g:4307:2: ( rule__DeclaredVersion__MicroAssignment_1_2_1 )
            // InternalN4MFParser.g:4307:3: rule__DeclaredVersion__MicroAssignment_1_2_1
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
    // InternalN4MFParser.g:4316:1: rule__DeclaredVersion__Group_2__0 : rule__DeclaredVersion__Group_2__0__Impl rule__DeclaredVersion__Group_2__1 ;
    public final void rule__DeclaredVersion__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4320:1: ( rule__DeclaredVersion__Group_2__0__Impl rule__DeclaredVersion__Group_2__1 )
            // InternalN4MFParser.g:4321:2: rule__DeclaredVersion__Group_2__0__Impl rule__DeclaredVersion__Group_2__1
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
    // InternalN4MFParser.g:4328:1: rule__DeclaredVersion__Group_2__0__Impl : ( HyphenMinus ) ;
    public final void rule__DeclaredVersion__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4332:1: ( ( HyphenMinus ) )
            // InternalN4MFParser.g:4333:1: ( HyphenMinus )
            {
            // InternalN4MFParser.g:4333:1: ( HyphenMinus )
            // InternalN4MFParser.g:4334:2: HyphenMinus
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
    // InternalN4MFParser.g:4343:1: rule__DeclaredVersion__Group_2__1 : rule__DeclaredVersion__Group_2__1__Impl ;
    public final void rule__DeclaredVersion__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4347:1: ( rule__DeclaredVersion__Group_2__1__Impl )
            // InternalN4MFParser.g:4348:2: rule__DeclaredVersion__Group_2__1__Impl
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
    // InternalN4MFParser.g:4354:1: rule__DeclaredVersion__Group_2__1__Impl : ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) ) ;
    public final void rule__DeclaredVersion__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4358:1: ( ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) ) )
            // InternalN4MFParser.g:4359:1: ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) )
            {
            // InternalN4MFParser.g:4359:1: ( ( rule__DeclaredVersion__QualifierAssignment_2_1 ) )
            // InternalN4MFParser.g:4360:2: ( rule__DeclaredVersion__QualifierAssignment_2_1 )
            {
             before(grammarAccess.getDeclaredVersionAccess().getQualifierAssignment_2_1()); 
            // InternalN4MFParser.g:4361:2: ( rule__DeclaredVersion__QualifierAssignment_2_1 )
            // InternalN4MFParser.g:4361:3: rule__DeclaredVersion__QualifierAssignment_2_1
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
    // InternalN4MFParser.g:4370:1: rule__SourceFragment__Group__0 : rule__SourceFragment__Group__0__Impl rule__SourceFragment__Group__1 ;
    public final void rule__SourceFragment__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4374:1: ( rule__SourceFragment__Group__0__Impl rule__SourceFragment__Group__1 )
            // InternalN4MFParser.g:4375:2: rule__SourceFragment__Group__0__Impl rule__SourceFragment__Group__1
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
    // InternalN4MFParser.g:4382:1: rule__SourceFragment__Group__0__Impl : ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) ) ;
    public final void rule__SourceFragment__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4386:1: ( ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) ) )
            // InternalN4MFParser.g:4387:1: ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) )
            {
            // InternalN4MFParser.g:4387:1: ( ( rule__SourceFragment__SourceFragmentTypeAssignment_0 ) )
            // InternalN4MFParser.g:4388:2: ( rule__SourceFragment__SourceFragmentTypeAssignment_0 )
            {
             before(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeAssignment_0()); 
            // InternalN4MFParser.g:4389:2: ( rule__SourceFragment__SourceFragmentTypeAssignment_0 )
            // InternalN4MFParser.g:4389:3: rule__SourceFragment__SourceFragmentTypeAssignment_0
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
    // InternalN4MFParser.g:4397:1: rule__SourceFragment__Group__1 : rule__SourceFragment__Group__1__Impl rule__SourceFragment__Group__2 ;
    public final void rule__SourceFragment__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4401:1: ( rule__SourceFragment__Group__1__Impl rule__SourceFragment__Group__2 )
            // InternalN4MFParser.g:4402:2: rule__SourceFragment__Group__1__Impl rule__SourceFragment__Group__2
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
    // InternalN4MFParser.g:4409:1: rule__SourceFragment__Group__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__SourceFragment__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4413:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:4414:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:4414:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:4415:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:4424:1: rule__SourceFragment__Group__2 : rule__SourceFragment__Group__2__Impl rule__SourceFragment__Group__3 ;
    public final void rule__SourceFragment__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4428:1: ( rule__SourceFragment__Group__2__Impl rule__SourceFragment__Group__3 )
            // InternalN4MFParser.g:4429:2: rule__SourceFragment__Group__2__Impl rule__SourceFragment__Group__3
            {
            pushFollow(FOLLOW_9);
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
    // InternalN4MFParser.g:4436:1: rule__SourceFragment__Group__2__Impl : ( ( rule__SourceFragment__PathsAssignment_2 ) ) ;
    public final void rule__SourceFragment__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4440:1: ( ( ( rule__SourceFragment__PathsAssignment_2 ) ) )
            // InternalN4MFParser.g:4441:1: ( ( rule__SourceFragment__PathsAssignment_2 ) )
            {
            // InternalN4MFParser.g:4441:1: ( ( rule__SourceFragment__PathsAssignment_2 ) )
            // InternalN4MFParser.g:4442:2: ( rule__SourceFragment__PathsAssignment_2 )
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsAssignment_2()); 
            // InternalN4MFParser.g:4443:2: ( rule__SourceFragment__PathsAssignment_2 )
            // InternalN4MFParser.g:4443:3: rule__SourceFragment__PathsAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__SourceFragment__PathsAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getSourceFragmentAccess().getPathsAssignment_2()); 

            }


            }

        }
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
    // InternalN4MFParser.g:4451:1: rule__SourceFragment__Group__3 : rule__SourceFragment__Group__3__Impl rule__SourceFragment__Group__4 ;
    public final void rule__SourceFragment__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4455:1: ( rule__SourceFragment__Group__3__Impl rule__SourceFragment__Group__4 )
            // InternalN4MFParser.g:4456:2: rule__SourceFragment__Group__3__Impl rule__SourceFragment__Group__4
            {
            pushFollow(FOLLOW_9);
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
    // InternalN4MFParser.g:4463:1: rule__SourceFragment__Group__3__Impl : ( ( rule__SourceFragment__Group_3__0 )* ) ;
    public final void rule__SourceFragment__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4467:1: ( ( ( rule__SourceFragment__Group_3__0 )* ) )
            // InternalN4MFParser.g:4468:1: ( ( rule__SourceFragment__Group_3__0 )* )
            {
            // InternalN4MFParser.g:4468:1: ( ( rule__SourceFragment__Group_3__0 )* )
            // InternalN4MFParser.g:4469:2: ( rule__SourceFragment__Group_3__0 )*
            {
             before(grammarAccess.getSourceFragmentAccess().getGroup_3()); 
            // InternalN4MFParser.g:4470:2: ( rule__SourceFragment__Group_3__0 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==Comma) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalN4MFParser.g:4470:3: rule__SourceFragment__Group_3__0
            	    {
            	    pushFollow(FOLLOW_10);
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
    // InternalN4MFParser.g:4478:1: rule__SourceFragment__Group__4 : rule__SourceFragment__Group__4__Impl ;
    public final void rule__SourceFragment__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4482:1: ( rule__SourceFragment__Group__4__Impl )
            // InternalN4MFParser.g:4483:2: rule__SourceFragment__Group__4__Impl
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
    // InternalN4MFParser.g:4489:1: rule__SourceFragment__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__SourceFragment__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4493:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:4494:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:4494:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:4495:2: RightCurlyBracket
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
    // InternalN4MFParser.g:4505:1: rule__SourceFragment__Group_3__0 : rule__SourceFragment__Group_3__0__Impl rule__SourceFragment__Group_3__1 ;
    public final void rule__SourceFragment__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4509:1: ( rule__SourceFragment__Group_3__0__Impl rule__SourceFragment__Group_3__1 )
            // InternalN4MFParser.g:4510:2: rule__SourceFragment__Group_3__0__Impl rule__SourceFragment__Group_3__1
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
    // InternalN4MFParser.g:4517:1: rule__SourceFragment__Group_3__0__Impl : ( Comma ) ;
    public final void rule__SourceFragment__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4521:1: ( ( Comma ) )
            // InternalN4MFParser.g:4522:1: ( Comma )
            {
            // InternalN4MFParser.g:4522:1: ( Comma )
            // InternalN4MFParser.g:4523:2: Comma
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
    // InternalN4MFParser.g:4532:1: rule__SourceFragment__Group_3__1 : rule__SourceFragment__Group_3__1__Impl ;
    public final void rule__SourceFragment__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4536:1: ( rule__SourceFragment__Group_3__1__Impl )
            // InternalN4MFParser.g:4537:2: rule__SourceFragment__Group_3__1__Impl
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
    // InternalN4MFParser.g:4543:1: rule__SourceFragment__Group_3__1__Impl : ( ( rule__SourceFragment__PathsAssignment_3_1 ) ) ;
    public final void rule__SourceFragment__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4547:1: ( ( ( rule__SourceFragment__PathsAssignment_3_1 ) ) )
            // InternalN4MFParser.g:4548:1: ( ( rule__SourceFragment__PathsAssignment_3_1 ) )
            {
            // InternalN4MFParser.g:4548:1: ( ( rule__SourceFragment__PathsAssignment_3_1 ) )
            // InternalN4MFParser.g:4549:2: ( rule__SourceFragment__PathsAssignment_3_1 )
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsAssignment_3_1()); 
            // InternalN4MFParser.g:4550:2: ( rule__SourceFragment__PathsAssignment_3_1 )
            // InternalN4MFParser.g:4550:3: rule__SourceFragment__PathsAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__SourceFragment__PathsAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getSourceFragmentAccess().getPathsAssignment_3_1()); 

            }


            }

        }
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
    // InternalN4MFParser.g:4559:1: rule__ModuleFilter__Group__0 : rule__ModuleFilter__Group__0__Impl rule__ModuleFilter__Group__1 ;
    public final void rule__ModuleFilter__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4563:1: ( rule__ModuleFilter__Group__0__Impl rule__ModuleFilter__Group__1 )
            // InternalN4MFParser.g:4564:2: rule__ModuleFilter__Group__0__Impl rule__ModuleFilter__Group__1
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
    // InternalN4MFParser.g:4571:1: rule__ModuleFilter__Group__0__Impl : ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) ) ;
    public final void rule__ModuleFilter__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4575:1: ( ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) ) )
            // InternalN4MFParser.g:4576:1: ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) )
            {
            // InternalN4MFParser.g:4576:1: ( ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 ) )
            // InternalN4MFParser.g:4577:2: ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 )
            {
             before(grammarAccess.getModuleFilterAccess().getModuleFilterTypeAssignment_0()); 
            // InternalN4MFParser.g:4578:2: ( rule__ModuleFilter__ModuleFilterTypeAssignment_0 )
            // InternalN4MFParser.g:4578:3: rule__ModuleFilter__ModuleFilterTypeAssignment_0
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
    // InternalN4MFParser.g:4586:1: rule__ModuleFilter__Group__1 : rule__ModuleFilter__Group__1__Impl rule__ModuleFilter__Group__2 ;
    public final void rule__ModuleFilter__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4590:1: ( rule__ModuleFilter__Group__1__Impl rule__ModuleFilter__Group__2 )
            // InternalN4MFParser.g:4591:2: rule__ModuleFilter__Group__1__Impl rule__ModuleFilter__Group__2
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
    // InternalN4MFParser.g:4598:1: rule__ModuleFilter__Group__1__Impl : ( LeftCurlyBracket ) ;
    public final void rule__ModuleFilter__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4602:1: ( ( LeftCurlyBracket ) )
            // InternalN4MFParser.g:4603:1: ( LeftCurlyBracket )
            {
            // InternalN4MFParser.g:4603:1: ( LeftCurlyBracket )
            // InternalN4MFParser.g:4604:2: LeftCurlyBracket
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
    // InternalN4MFParser.g:4613:1: rule__ModuleFilter__Group__2 : rule__ModuleFilter__Group__2__Impl rule__ModuleFilter__Group__3 ;
    public final void rule__ModuleFilter__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4617:1: ( rule__ModuleFilter__Group__2__Impl rule__ModuleFilter__Group__3 )
            // InternalN4MFParser.g:4618:2: rule__ModuleFilter__Group__2__Impl rule__ModuleFilter__Group__3
            {
            pushFollow(FOLLOW_9);
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
    // InternalN4MFParser.g:4625:1: rule__ModuleFilter__Group__2__Impl : ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) ) ;
    public final void rule__ModuleFilter__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4629:1: ( ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) ) )
            // InternalN4MFParser.g:4630:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) )
            {
            // InternalN4MFParser.g:4630:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 ) )
            // InternalN4MFParser.g:4631:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 )
            {
             before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_2()); 
            // InternalN4MFParser.g:4632:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_2 )
            // InternalN4MFParser.g:4632:3: rule__ModuleFilter__ModuleSpecifiersAssignment_2
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
    // InternalN4MFParser.g:4640:1: rule__ModuleFilter__Group__3 : rule__ModuleFilter__Group__3__Impl rule__ModuleFilter__Group__4 ;
    public final void rule__ModuleFilter__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4644:1: ( rule__ModuleFilter__Group__3__Impl rule__ModuleFilter__Group__4 )
            // InternalN4MFParser.g:4645:2: rule__ModuleFilter__Group__3__Impl rule__ModuleFilter__Group__4
            {
            pushFollow(FOLLOW_9);
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
    // InternalN4MFParser.g:4652:1: rule__ModuleFilter__Group__3__Impl : ( ( rule__ModuleFilter__Group_3__0 )* ) ;
    public final void rule__ModuleFilter__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4656:1: ( ( ( rule__ModuleFilter__Group_3__0 )* ) )
            // InternalN4MFParser.g:4657:1: ( ( rule__ModuleFilter__Group_3__0 )* )
            {
            // InternalN4MFParser.g:4657:1: ( ( rule__ModuleFilter__Group_3__0 )* )
            // InternalN4MFParser.g:4658:2: ( rule__ModuleFilter__Group_3__0 )*
            {
             before(grammarAccess.getModuleFilterAccess().getGroup_3()); 
            // InternalN4MFParser.g:4659:2: ( rule__ModuleFilter__Group_3__0 )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==Comma) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // InternalN4MFParser.g:4659:3: rule__ModuleFilter__Group_3__0
            	    {
            	    pushFollow(FOLLOW_10);
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
    // InternalN4MFParser.g:4667:1: rule__ModuleFilter__Group__4 : rule__ModuleFilter__Group__4__Impl ;
    public final void rule__ModuleFilter__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4671:1: ( rule__ModuleFilter__Group__4__Impl )
            // InternalN4MFParser.g:4672:2: rule__ModuleFilter__Group__4__Impl
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
    // InternalN4MFParser.g:4678:1: rule__ModuleFilter__Group__4__Impl : ( RightCurlyBracket ) ;
    public final void rule__ModuleFilter__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4682:1: ( ( RightCurlyBracket ) )
            // InternalN4MFParser.g:4683:1: ( RightCurlyBracket )
            {
            // InternalN4MFParser.g:4683:1: ( RightCurlyBracket )
            // InternalN4MFParser.g:4684:2: RightCurlyBracket
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
    // InternalN4MFParser.g:4694:1: rule__ModuleFilter__Group_3__0 : rule__ModuleFilter__Group_3__0__Impl rule__ModuleFilter__Group_3__1 ;
    public final void rule__ModuleFilter__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4698:1: ( rule__ModuleFilter__Group_3__0__Impl rule__ModuleFilter__Group_3__1 )
            // InternalN4MFParser.g:4699:2: rule__ModuleFilter__Group_3__0__Impl rule__ModuleFilter__Group_3__1
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
    // InternalN4MFParser.g:4706:1: rule__ModuleFilter__Group_3__0__Impl : ( Comma ) ;
    public final void rule__ModuleFilter__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4710:1: ( ( Comma ) )
            // InternalN4MFParser.g:4711:1: ( Comma )
            {
            // InternalN4MFParser.g:4711:1: ( Comma )
            // InternalN4MFParser.g:4712:2: Comma
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
    // InternalN4MFParser.g:4721:1: rule__ModuleFilter__Group_3__1 : rule__ModuleFilter__Group_3__1__Impl ;
    public final void rule__ModuleFilter__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4725:1: ( rule__ModuleFilter__Group_3__1__Impl )
            // InternalN4MFParser.g:4726:2: rule__ModuleFilter__Group_3__1__Impl
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
    // InternalN4MFParser.g:4732:1: rule__ModuleFilter__Group_3__1__Impl : ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) ) ;
    public final void rule__ModuleFilter__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4736:1: ( ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) ) )
            // InternalN4MFParser.g:4737:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) )
            {
            // InternalN4MFParser.g:4737:1: ( ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 ) )
            // InternalN4MFParser.g:4738:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 )
            {
             before(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_3_1()); 
            // InternalN4MFParser.g:4739:2: ( rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 )
            // InternalN4MFParser.g:4739:3: rule__ModuleFilter__ModuleSpecifiersAssignment_3_1
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
    // InternalN4MFParser.g:4748:1: rule__BootstrapModule__Group__0 : rule__BootstrapModule__Group__0__Impl rule__BootstrapModule__Group__1 ;
    public final void rule__BootstrapModule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4752:1: ( rule__BootstrapModule__Group__0__Impl rule__BootstrapModule__Group__1 )
            // InternalN4MFParser.g:4753:2: rule__BootstrapModule__Group__0__Impl rule__BootstrapModule__Group__1
            {
            pushFollow(FOLLOW_30);
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
    // InternalN4MFParser.g:4760:1: rule__BootstrapModule__Group__0__Impl : ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) ) ;
    public final void rule__BootstrapModule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4764:1: ( ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) ) )
            // InternalN4MFParser.g:4765:1: ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) )
            {
            // InternalN4MFParser.g:4765:1: ( ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 ) )
            // InternalN4MFParser.g:4766:2: ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 )
            {
             before(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardAssignment_0()); 
            // InternalN4MFParser.g:4767:2: ( rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 )
            // InternalN4MFParser.g:4767:3: rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0
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
    // InternalN4MFParser.g:4775:1: rule__BootstrapModule__Group__1 : rule__BootstrapModule__Group__1__Impl ;
    public final void rule__BootstrapModule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4779:1: ( rule__BootstrapModule__Group__1__Impl )
            // InternalN4MFParser.g:4780:2: rule__BootstrapModule__Group__1__Impl
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
    // InternalN4MFParser.g:4786:1: rule__BootstrapModule__Group__1__Impl : ( ( rule__BootstrapModule__Group_1__0 )? ) ;
    public final void rule__BootstrapModule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4790:1: ( ( ( rule__BootstrapModule__Group_1__0 )? ) )
            // InternalN4MFParser.g:4791:1: ( ( rule__BootstrapModule__Group_1__0 )? )
            {
            // InternalN4MFParser.g:4791:1: ( ( rule__BootstrapModule__Group_1__0 )? )
            // InternalN4MFParser.g:4792:2: ( rule__BootstrapModule__Group_1__0 )?
            {
             before(grammarAccess.getBootstrapModuleAccess().getGroup_1()); 
            // InternalN4MFParser.g:4793:2: ( rule__BootstrapModule__Group_1__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==In) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalN4MFParser.g:4793:3: rule__BootstrapModule__Group_1__0
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
    // InternalN4MFParser.g:4802:1: rule__BootstrapModule__Group_1__0 : rule__BootstrapModule__Group_1__0__Impl rule__BootstrapModule__Group_1__1 ;
    public final void rule__BootstrapModule__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4806:1: ( rule__BootstrapModule__Group_1__0__Impl rule__BootstrapModule__Group_1__1 )
            // InternalN4MFParser.g:4807:2: rule__BootstrapModule__Group_1__0__Impl rule__BootstrapModule__Group_1__1
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
    // InternalN4MFParser.g:4814:1: rule__BootstrapModule__Group_1__0__Impl : ( In ) ;
    public final void rule__BootstrapModule__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4818:1: ( ( In ) )
            // InternalN4MFParser.g:4819:1: ( In )
            {
            // InternalN4MFParser.g:4819:1: ( In )
            // InternalN4MFParser.g:4820:2: In
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
    // InternalN4MFParser.g:4829:1: rule__BootstrapModule__Group_1__1 : rule__BootstrapModule__Group_1__1__Impl ;
    public final void rule__BootstrapModule__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4833:1: ( rule__BootstrapModule__Group_1__1__Impl )
            // InternalN4MFParser.g:4834:2: rule__BootstrapModule__Group_1__1__Impl
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
    // InternalN4MFParser.g:4840:1: rule__BootstrapModule__Group_1__1__Impl : ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) ) ;
    public final void rule__BootstrapModule__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4844:1: ( ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) ) )
            // InternalN4MFParser.g:4845:1: ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) )
            {
            // InternalN4MFParser.g:4845:1: ( ( rule__BootstrapModule__SourcePathAssignment_1_1 ) )
            // InternalN4MFParser.g:4846:2: ( rule__BootstrapModule__SourcePathAssignment_1_1 )
            {
             before(grammarAccess.getBootstrapModuleAccess().getSourcePathAssignment_1_1()); 
            // InternalN4MFParser.g:4847:2: ( rule__BootstrapModule__SourcePathAssignment_1_1 )
            // InternalN4MFParser.g:4847:3: rule__BootstrapModule__SourcePathAssignment_1_1
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
    // InternalN4MFParser.g:4856:1: rule__ModuleFilterSpecifier__Group__0 : rule__ModuleFilterSpecifier__Group__0__Impl rule__ModuleFilterSpecifier__Group__1 ;
    public final void rule__ModuleFilterSpecifier__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4860:1: ( rule__ModuleFilterSpecifier__Group__0__Impl rule__ModuleFilterSpecifier__Group__1 )
            // InternalN4MFParser.g:4861:2: rule__ModuleFilterSpecifier__Group__0__Impl rule__ModuleFilterSpecifier__Group__1
            {
            pushFollow(FOLLOW_30);
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
    // InternalN4MFParser.g:4868:1: rule__ModuleFilterSpecifier__Group__0__Impl : ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) ) ;
    public final void rule__ModuleFilterSpecifier__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4872:1: ( ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) ) )
            // InternalN4MFParser.g:4873:1: ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) )
            {
            // InternalN4MFParser.g:4873:1: ( ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 ) )
            // InternalN4MFParser.g:4874:2: ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 )
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardAssignment_0()); 
            // InternalN4MFParser.g:4875:2: ( rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 )
            // InternalN4MFParser.g:4875:3: rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0
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
    // InternalN4MFParser.g:4883:1: rule__ModuleFilterSpecifier__Group__1 : rule__ModuleFilterSpecifier__Group__1__Impl ;
    public final void rule__ModuleFilterSpecifier__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4887:1: ( rule__ModuleFilterSpecifier__Group__1__Impl )
            // InternalN4MFParser.g:4888:2: rule__ModuleFilterSpecifier__Group__1__Impl
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
    // InternalN4MFParser.g:4894:1: rule__ModuleFilterSpecifier__Group__1__Impl : ( ( rule__ModuleFilterSpecifier__Group_1__0 )? ) ;
    public final void rule__ModuleFilterSpecifier__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4898:1: ( ( ( rule__ModuleFilterSpecifier__Group_1__0 )? ) )
            // InternalN4MFParser.g:4899:1: ( ( rule__ModuleFilterSpecifier__Group_1__0 )? )
            {
            // InternalN4MFParser.g:4899:1: ( ( rule__ModuleFilterSpecifier__Group_1__0 )? )
            // InternalN4MFParser.g:4900:2: ( rule__ModuleFilterSpecifier__Group_1__0 )?
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getGroup_1()); 
            // InternalN4MFParser.g:4901:2: ( rule__ModuleFilterSpecifier__Group_1__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==In) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalN4MFParser.g:4901:3: rule__ModuleFilterSpecifier__Group_1__0
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
    // InternalN4MFParser.g:4910:1: rule__ModuleFilterSpecifier__Group_1__0 : rule__ModuleFilterSpecifier__Group_1__0__Impl rule__ModuleFilterSpecifier__Group_1__1 ;
    public final void rule__ModuleFilterSpecifier__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4914:1: ( rule__ModuleFilterSpecifier__Group_1__0__Impl rule__ModuleFilterSpecifier__Group_1__1 )
            // InternalN4MFParser.g:4915:2: rule__ModuleFilterSpecifier__Group_1__0__Impl rule__ModuleFilterSpecifier__Group_1__1
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
    // InternalN4MFParser.g:4922:1: rule__ModuleFilterSpecifier__Group_1__0__Impl : ( In ) ;
    public final void rule__ModuleFilterSpecifier__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4926:1: ( ( In ) )
            // InternalN4MFParser.g:4927:1: ( In )
            {
            // InternalN4MFParser.g:4927:1: ( In )
            // InternalN4MFParser.g:4928:2: In
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
    // InternalN4MFParser.g:4937:1: rule__ModuleFilterSpecifier__Group_1__1 : rule__ModuleFilterSpecifier__Group_1__1__Impl ;
    public final void rule__ModuleFilterSpecifier__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4941:1: ( rule__ModuleFilterSpecifier__Group_1__1__Impl )
            // InternalN4MFParser.g:4942:2: rule__ModuleFilterSpecifier__Group_1__1__Impl
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
    // InternalN4MFParser.g:4948:1: rule__ModuleFilterSpecifier__Group_1__1__Impl : ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) ) ;
    public final void rule__ModuleFilterSpecifier__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4952:1: ( ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) ) )
            // InternalN4MFParser.g:4953:1: ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) )
            {
            // InternalN4MFParser.g:4953:1: ( ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 ) )
            // InternalN4MFParser.g:4954:2: ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 )
            {
             before(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathAssignment_1_1()); 
            // InternalN4MFParser.g:4955:2: ( rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 )
            // InternalN4MFParser.g:4955:3: rule__ModuleFilterSpecifier__SourcePathAssignment_1_1
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
    // InternalN4MFParser.g:4964:1: rule__ProjectDependency__Group__0 : rule__ProjectDependency__Group__0__Impl rule__ProjectDependency__Group__1 ;
    public final void rule__ProjectDependency__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4968:1: ( rule__ProjectDependency__Group__0__Impl rule__ProjectDependency__Group__1 )
            // InternalN4MFParser.g:4969:2: rule__ProjectDependency__Group__0__Impl rule__ProjectDependency__Group__1
            {
            pushFollow(FOLLOW_31);
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
    // InternalN4MFParser.g:4976:1: rule__ProjectDependency__Group__0__Impl : ( ( rule__ProjectDependency__ProjectAssignment_0 ) ) ;
    public final void rule__ProjectDependency__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4980:1: ( ( ( rule__ProjectDependency__ProjectAssignment_0 ) ) )
            // InternalN4MFParser.g:4981:1: ( ( rule__ProjectDependency__ProjectAssignment_0 ) )
            {
            // InternalN4MFParser.g:4981:1: ( ( rule__ProjectDependency__ProjectAssignment_0 ) )
            // InternalN4MFParser.g:4982:2: ( rule__ProjectDependency__ProjectAssignment_0 )
            {
             before(grammarAccess.getProjectDependencyAccess().getProjectAssignment_0()); 
            // InternalN4MFParser.g:4983:2: ( rule__ProjectDependency__ProjectAssignment_0 )
            // InternalN4MFParser.g:4983:3: rule__ProjectDependency__ProjectAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__ProjectDependency__ProjectAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getProjectDependencyAccess().getProjectAssignment_0()); 

            }


            }

        }
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
    // InternalN4MFParser.g:4991:1: rule__ProjectDependency__Group__1 : rule__ProjectDependency__Group__1__Impl rule__ProjectDependency__Group__2 ;
    public final void rule__ProjectDependency__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:4995:1: ( rule__ProjectDependency__Group__1__Impl rule__ProjectDependency__Group__2 )
            // InternalN4MFParser.g:4996:2: rule__ProjectDependency__Group__1__Impl rule__ProjectDependency__Group__2
            {
            pushFollow(FOLLOW_31);
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
    // InternalN4MFParser.g:5003:1: rule__ProjectDependency__Group__1__Impl : ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? ) ;
    public final void rule__ProjectDependency__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5007:1: ( ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? ) )
            // InternalN4MFParser.g:5008:1: ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? )
            {
            // InternalN4MFParser.g:5008:1: ( ( rule__ProjectDependency__VersionConstraintAssignment_1 )? )
            // InternalN4MFParser.g:5009:2: ( rule__ProjectDependency__VersionConstraintAssignment_1 )?
            {
             before(grammarAccess.getProjectDependencyAccess().getVersionConstraintAssignment_1()); 
            // InternalN4MFParser.g:5010:2: ( rule__ProjectDependency__VersionConstraintAssignment_1 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==LeftParenthesis||LA35_0==LeftSquareBracket||LA35_0==RULE_INT) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalN4MFParser.g:5010:3: rule__ProjectDependency__VersionConstraintAssignment_1
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
    // InternalN4MFParser.g:5018:1: rule__ProjectDependency__Group__2 : rule__ProjectDependency__Group__2__Impl ;
    public final void rule__ProjectDependency__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5022:1: ( rule__ProjectDependency__Group__2__Impl )
            // InternalN4MFParser.g:5023:2: rule__ProjectDependency__Group__2__Impl
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
    // InternalN4MFParser.g:5029:1: rule__ProjectDependency__Group__2__Impl : ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? ) ;
    public final void rule__ProjectDependency__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5033:1: ( ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? ) )
            // InternalN4MFParser.g:5034:1: ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? )
            {
            // InternalN4MFParser.g:5034:1: ( ( rule__ProjectDependency__DeclaredScopeAssignment_2 )? )
            // InternalN4MFParser.g:5035:2: ( rule__ProjectDependency__DeclaredScopeAssignment_2 )?
            {
             before(grammarAccess.getProjectDependencyAccess().getDeclaredScopeAssignment_2()); 
            // InternalN4MFParser.g:5036:2: ( rule__ProjectDependency__DeclaredScopeAssignment_2 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==Compile||LA36_0==Test) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalN4MFParser.g:5036:3: rule__ProjectDependency__DeclaredScopeAssignment_2
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


    // $ANTLR start "rule__SimpleProjectDescription__Group__0"
    // InternalN4MFParser.g:5045:1: rule__SimpleProjectDescription__Group__0 : rule__SimpleProjectDescription__Group__0__Impl rule__SimpleProjectDescription__Group__1 ;
    public final void rule__SimpleProjectDescription__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5049:1: ( rule__SimpleProjectDescription__Group__0__Impl rule__SimpleProjectDescription__Group__1 )
            // InternalN4MFParser.g:5050:2: rule__SimpleProjectDescription__Group__0__Impl rule__SimpleProjectDescription__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__SimpleProjectDescription__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SimpleProjectDescription__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__Group__0"


    // $ANTLR start "rule__SimpleProjectDescription__Group__0__Impl"
    // InternalN4MFParser.g:5057:1: rule__SimpleProjectDescription__Group__0__Impl : ( ( rule__SimpleProjectDescription__Group_0__0 )? ) ;
    public final void rule__SimpleProjectDescription__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5061:1: ( ( ( rule__SimpleProjectDescription__Group_0__0 )? ) )
            // InternalN4MFParser.g:5062:1: ( ( rule__SimpleProjectDescription__Group_0__0 )? )
            {
            // InternalN4MFParser.g:5062:1: ( ( rule__SimpleProjectDescription__Group_0__0 )? )
            // InternalN4MFParser.g:5063:2: ( rule__SimpleProjectDescription__Group_0__0 )?
            {
             before(grammarAccess.getSimpleProjectDescriptionAccess().getGroup_0()); 
            // InternalN4MFParser.g:5064:2: ( rule__SimpleProjectDescription__Group_0__0 )?
            int alt37=2;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // InternalN4MFParser.g:5064:3: rule__SimpleProjectDescription__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__SimpleProjectDescription__Group_0__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getSimpleProjectDescriptionAccess().getGroup_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__Group__0__Impl"


    // $ANTLR start "rule__SimpleProjectDescription__Group__1"
    // InternalN4MFParser.g:5072:1: rule__SimpleProjectDescription__Group__1 : rule__SimpleProjectDescription__Group__1__Impl ;
    public final void rule__SimpleProjectDescription__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5076:1: ( rule__SimpleProjectDescription__Group__1__Impl )
            // InternalN4MFParser.g:5077:2: rule__SimpleProjectDescription__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleProjectDescription__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__Group__1"


    // $ANTLR start "rule__SimpleProjectDescription__Group__1__Impl"
    // InternalN4MFParser.g:5083:1: rule__SimpleProjectDescription__Group__1__Impl : ( ( rule__SimpleProjectDescription__ProjectIdAssignment_1 ) ) ;
    public final void rule__SimpleProjectDescription__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5087:1: ( ( ( rule__SimpleProjectDescription__ProjectIdAssignment_1 ) ) )
            // InternalN4MFParser.g:5088:1: ( ( rule__SimpleProjectDescription__ProjectIdAssignment_1 ) )
            {
            // InternalN4MFParser.g:5088:1: ( ( rule__SimpleProjectDescription__ProjectIdAssignment_1 ) )
            // InternalN4MFParser.g:5089:2: ( rule__SimpleProjectDescription__ProjectIdAssignment_1 )
            {
             before(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdAssignment_1()); 
            // InternalN4MFParser.g:5090:2: ( rule__SimpleProjectDescription__ProjectIdAssignment_1 )
            // InternalN4MFParser.g:5090:3: rule__SimpleProjectDescription__ProjectIdAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__SimpleProjectDescription__ProjectIdAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__Group__1__Impl"


    // $ANTLR start "rule__SimpleProjectDescription__Group_0__0"
    // InternalN4MFParser.g:5099:1: rule__SimpleProjectDescription__Group_0__0 : rule__SimpleProjectDescription__Group_0__0__Impl rule__SimpleProjectDescription__Group_0__1 ;
    public final void rule__SimpleProjectDescription__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5103:1: ( rule__SimpleProjectDescription__Group_0__0__Impl rule__SimpleProjectDescription__Group_0__1 )
            // InternalN4MFParser.g:5104:2: rule__SimpleProjectDescription__Group_0__0__Impl rule__SimpleProjectDescription__Group_0__1
            {
            pushFollow(FOLLOW_3);
            rule__SimpleProjectDescription__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SimpleProjectDescription__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__Group_0__0"


    // $ANTLR start "rule__SimpleProjectDescription__Group_0__0__Impl"
    // InternalN4MFParser.g:5111:1: rule__SimpleProjectDescription__Group_0__0__Impl : ( ( rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0 ) ) ;
    public final void rule__SimpleProjectDescription__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5115:1: ( ( ( rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0 ) ) )
            // InternalN4MFParser.g:5116:1: ( ( rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0 ) )
            {
            // InternalN4MFParser.g:5116:1: ( ( rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0 ) )
            // InternalN4MFParser.g:5117:2: ( rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0 )
            {
             before(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdAssignment_0_0()); 
            // InternalN4MFParser.g:5118:2: ( rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0 )
            // InternalN4MFParser.g:5118:3: rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__Group_0__0__Impl"


    // $ANTLR start "rule__SimpleProjectDescription__Group_0__1"
    // InternalN4MFParser.g:5126:1: rule__SimpleProjectDescription__Group_0__1 : rule__SimpleProjectDescription__Group_0__1__Impl ;
    public final void rule__SimpleProjectDescription__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5130:1: ( rule__SimpleProjectDescription__Group_0__1__Impl )
            // InternalN4MFParser.g:5131:2: rule__SimpleProjectDescription__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SimpleProjectDescription__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__Group_0__1"


    // $ANTLR start "rule__SimpleProjectDescription__Group_0__1__Impl"
    // InternalN4MFParser.g:5137:1: rule__SimpleProjectDescription__Group_0__1__Impl : ( Colon ) ;
    public final void rule__SimpleProjectDescription__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5141:1: ( ( Colon ) )
            // InternalN4MFParser.g:5142:1: ( Colon )
            {
            // InternalN4MFParser.g:5142:1: ( Colon )
            // InternalN4MFParser.g:5143:2: Colon
            {
             before(grammarAccess.getSimpleProjectDescriptionAccess().getColonKeyword_0_1()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getSimpleProjectDescriptionAccess().getColonKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__Group_0__1__Impl"


    // $ANTLR start "rule__VersionConstraint__Group_0__0"
    // InternalN4MFParser.g:5153:1: rule__VersionConstraint__Group_0__0 : rule__VersionConstraint__Group_0__0__Impl rule__VersionConstraint__Group_0__1 ;
    public final void rule__VersionConstraint__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5157:1: ( rule__VersionConstraint__Group_0__0__Impl rule__VersionConstraint__Group_0__1 )
            // InternalN4MFParser.g:5158:2: rule__VersionConstraint__Group_0__0__Impl rule__VersionConstraint__Group_0__1
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
    // InternalN4MFParser.g:5165:1: rule__VersionConstraint__Group_0__0__Impl : ( ( rule__VersionConstraint__Alternatives_0_0 ) ) ;
    public final void rule__VersionConstraint__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5169:1: ( ( ( rule__VersionConstraint__Alternatives_0_0 ) ) )
            // InternalN4MFParser.g:5170:1: ( ( rule__VersionConstraint__Alternatives_0_0 ) )
            {
            // InternalN4MFParser.g:5170:1: ( ( rule__VersionConstraint__Alternatives_0_0 ) )
            // InternalN4MFParser.g:5171:2: ( rule__VersionConstraint__Alternatives_0_0 )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_0()); 
            // InternalN4MFParser.g:5172:2: ( rule__VersionConstraint__Alternatives_0_0 )
            // InternalN4MFParser.g:5172:3: rule__VersionConstraint__Alternatives_0_0
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
    // InternalN4MFParser.g:5180:1: rule__VersionConstraint__Group_0__1 : rule__VersionConstraint__Group_0__1__Impl rule__VersionConstraint__Group_0__2 ;
    public final void rule__VersionConstraint__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5184:1: ( rule__VersionConstraint__Group_0__1__Impl rule__VersionConstraint__Group_0__2 )
            // InternalN4MFParser.g:5185:2: rule__VersionConstraint__Group_0__1__Impl rule__VersionConstraint__Group_0__2
            {
            pushFollow(FOLLOW_32);
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
    // InternalN4MFParser.g:5192:1: rule__VersionConstraint__Group_0__1__Impl : ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) ) ;
    public final void rule__VersionConstraint__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5196:1: ( ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) ) )
            // InternalN4MFParser.g:5197:1: ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) )
            {
            // InternalN4MFParser.g:5197:1: ( ( rule__VersionConstraint__LowerVersionAssignment_0_1 ) )
            // InternalN4MFParser.g:5198:2: ( rule__VersionConstraint__LowerVersionAssignment_0_1 )
            {
             before(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_0_1()); 
            // InternalN4MFParser.g:5199:2: ( rule__VersionConstraint__LowerVersionAssignment_0_1 )
            // InternalN4MFParser.g:5199:3: rule__VersionConstraint__LowerVersionAssignment_0_1
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
    // InternalN4MFParser.g:5207:1: rule__VersionConstraint__Group_0__2 : rule__VersionConstraint__Group_0__2__Impl ;
    public final void rule__VersionConstraint__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5211:1: ( rule__VersionConstraint__Group_0__2__Impl )
            // InternalN4MFParser.g:5212:2: rule__VersionConstraint__Group_0__2__Impl
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
    // InternalN4MFParser.g:5218:1: rule__VersionConstraint__Group_0__2__Impl : ( ( rule__VersionConstraint__Alternatives_0_2 ) ) ;
    public final void rule__VersionConstraint__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5222:1: ( ( ( rule__VersionConstraint__Alternatives_0_2 ) ) )
            // InternalN4MFParser.g:5223:1: ( ( rule__VersionConstraint__Alternatives_0_2 ) )
            {
            // InternalN4MFParser.g:5223:1: ( ( rule__VersionConstraint__Alternatives_0_2 ) )
            // InternalN4MFParser.g:5224:2: ( rule__VersionConstraint__Alternatives_0_2 )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2()); 
            // InternalN4MFParser.g:5225:2: ( rule__VersionConstraint__Alternatives_0_2 )
            // InternalN4MFParser.g:5225:3: rule__VersionConstraint__Alternatives_0_2
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
    // InternalN4MFParser.g:5234:1: rule__VersionConstraint__Group_0_2_0__0 : rule__VersionConstraint__Group_0_2_0__0__Impl rule__VersionConstraint__Group_0_2_0__1 ;
    public final void rule__VersionConstraint__Group_0_2_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5238:1: ( rule__VersionConstraint__Group_0_2_0__0__Impl rule__VersionConstraint__Group_0_2_0__1 )
            // InternalN4MFParser.g:5239:2: rule__VersionConstraint__Group_0_2_0__0__Impl rule__VersionConstraint__Group_0_2_0__1
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
    // InternalN4MFParser.g:5246:1: rule__VersionConstraint__Group_0_2_0__0__Impl : ( Comma ) ;
    public final void rule__VersionConstraint__Group_0_2_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5250:1: ( ( Comma ) )
            // InternalN4MFParser.g:5251:1: ( Comma )
            {
            // InternalN4MFParser.g:5251:1: ( Comma )
            // InternalN4MFParser.g:5252:2: Comma
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
    // InternalN4MFParser.g:5261:1: rule__VersionConstraint__Group_0_2_0__1 : rule__VersionConstraint__Group_0_2_0__1__Impl rule__VersionConstraint__Group_0_2_0__2 ;
    public final void rule__VersionConstraint__Group_0_2_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5265:1: ( rule__VersionConstraint__Group_0_2_0__1__Impl rule__VersionConstraint__Group_0_2_0__2 )
            // InternalN4MFParser.g:5266:2: rule__VersionConstraint__Group_0_2_0__1__Impl rule__VersionConstraint__Group_0_2_0__2
            {
            pushFollow(FOLLOW_33);
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
    // InternalN4MFParser.g:5273:1: rule__VersionConstraint__Group_0_2_0__1__Impl : ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) ) ;
    public final void rule__VersionConstraint__Group_0_2_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5277:1: ( ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) ) )
            // InternalN4MFParser.g:5278:1: ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) )
            {
            // InternalN4MFParser.g:5278:1: ( ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 ) )
            // InternalN4MFParser.g:5279:2: ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 )
            {
             before(grammarAccess.getVersionConstraintAccess().getUpperVersionAssignment_0_2_0_1()); 
            // InternalN4MFParser.g:5280:2: ( rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 )
            // InternalN4MFParser.g:5280:3: rule__VersionConstraint__UpperVersionAssignment_0_2_0_1
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
    // InternalN4MFParser.g:5288:1: rule__VersionConstraint__Group_0_2_0__2 : rule__VersionConstraint__Group_0_2_0__2__Impl ;
    public final void rule__VersionConstraint__Group_0_2_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5292:1: ( rule__VersionConstraint__Group_0_2_0__2__Impl )
            // InternalN4MFParser.g:5293:2: rule__VersionConstraint__Group_0_2_0__2__Impl
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
    // InternalN4MFParser.g:5299:1: rule__VersionConstraint__Group_0_2_0__2__Impl : ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) ) ;
    public final void rule__VersionConstraint__Group_0_2_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5303:1: ( ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) ) )
            // InternalN4MFParser.g:5304:1: ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) )
            {
            // InternalN4MFParser.g:5304:1: ( ( rule__VersionConstraint__Alternatives_0_2_0_2 ) )
            // InternalN4MFParser.g:5305:2: ( rule__VersionConstraint__Alternatives_0_2_0_2 )
            {
             before(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2_0_2()); 
            // InternalN4MFParser.g:5306:2: ( rule__VersionConstraint__Alternatives_0_2_0_2 )
            // InternalN4MFParser.g:5306:3: rule__VersionConstraint__Alternatives_0_2_0_2
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
    // InternalN4MFParser.g:5315:1: rule__N4mfIdentifier__Group_11__0 : rule__N4mfIdentifier__Group_11__0__Impl rule__N4mfIdentifier__Group_11__1 ;
    public final void rule__N4mfIdentifier__Group_11__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5319:1: ( rule__N4mfIdentifier__Group_11__0__Impl rule__N4mfIdentifier__Group_11__1 )
            // InternalN4MFParser.g:5320:2: rule__N4mfIdentifier__Group_11__0__Impl rule__N4mfIdentifier__Group_11__1
            {
            pushFollow(FOLLOW_34);
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
    // InternalN4MFParser.g:5327:1: rule__N4mfIdentifier__Group_11__0__Impl : ( ProjectDependencies ) ;
    public final void rule__N4mfIdentifier__Group_11__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5331:1: ( ( ProjectDependencies ) )
            // InternalN4MFParser.g:5332:1: ( ProjectDependencies )
            {
            // InternalN4MFParser.g:5332:1: ( ProjectDependencies )
            // InternalN4MFParser.g:5333:2: ProjectDependencies
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
    // InternalN4MFParser.g:5342:1: rule__N4mfIdentifier__Group_11__1 : rule__N4mfIdentifier__Group_11__1__Impl ;
    public final void rule__N4mfIdentifier__Group_11__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5346:1: ( rule__N4mfIdentifier__Group_11__1__Impl )
            // InternalN4MFParser.g:5347:2: rule__N4mfIdentifier__Group_11__1__Impl
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
    // InternalN4MFParser.g:5353:1: rule__N4mfIdentifier__Group_11__1__Impl : ( KW_System ) ;
    public final void rule__N4mfIdentifier__Group_11__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5357:1: ( ( KW_System ) )
            // InternalN4MFParser.g:5358:1: ( KW_System )
            {
            // InternalN4MFParser.g:5358:1: ( KW_System )
            // InternalN4MFParser.g:5359:2: KW_System
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
    // InternalN4MFParser.g:5369:1: rule__N4mfIdentifier__Group_15__0 : rule__N4mfIdentifier__Group_15__0__Impl rule__N4mfIdentifier__Group_15__1 ;
    public final void rule__N4mfIdentifier__Group_15__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5373:1: ( rule__N4mfIdentifier__Group_15__0__Impl rule__N4mfIdentifier__Group_15__1 )
            // InternalN4MFParser.g:5374:2: rule__N4mfIdentifier__Group_15__0__Impl rule__N4mfIdentifier__Group_15__1
            {
            pushFollow(FOLLOW_35);
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
    // InternalN4MFParser.g:5381:1: rule__N4mfIdentifier__Group_15__0__Impl : ( Processor ) ;
    public final void rule__N4mfIdentifier__Group_15__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5385:1: ( ( Processor ) )
            // InternalN4MFParser.g:5386:1: ( Processor )
            {
            // InternalN4MFParser.g:5386:1: ( Processor )
            // InternalN4MFParser.g:5387:2: Processor
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
    // InternalN4MFParser.g:5396:1: rule__N4mfIdentifier__Group_15__1 : rule__N4mfIdentifier__Group_15__1__Impl ;
    public final void rule__N4mfIdentifier__Group_15__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5400:1: ( rule__N4mfIdentifier__Group_15__1__Impl )
            // InternalN4MFParser.g:5401:2: rule__N4mfIdentifier__Group_15__1__Impl
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
    // InternalN4MFParser.g:5407:1: rule__N4mfIdentifier__Group_15__1__Impl : ( Source ) ;
    public final void rule__N4mfIdentifier__Group_15__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5411:1: ( ( Source ) )
            // InternalN4MFParser.g:5412:1: ( Source )
            {
            // InternalN4MFParser.g:5412:1: ( Source )
            // InternalN4MFParser.g:5413:2: Source
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
    // InternalN4MFParser.g:5423:1: rule__ProjectDescription__UnorderedGroup : rule__ProjectDescription__UnorderedGroup__0 {...}?;
    public final void rule__ProjectDescription__UnorderedGroup() throws RecognitionException {

        		int stackSize = keepStackSize();
        		getUnorderedGroupHelper().enter(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
        	
        try {
            // InternalN4MFParser.g:5428:1: ( rule__ProjectDescription__UnorderedGroup__0 {...}?)
            // InternalN4MFParser.g:5429:2: rule__ProjectDescription__UnorderedGroup__0 {...}?
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
    // InternalN4MFParser.g:5437:1: rule__ProjectDescription__UnorderedGroup__Impl : ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__InitModulesAssignment_12 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) ) ;
    public final void rule__ProjectDescription__UnorderedGroup__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        		boolean selected = false;
        	
        try {
            // InternalN4MFParser.g:5442:1: ( ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__InitModulesAssignment_12 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) ) )
            // InternalN4MFParser.g:5443:3: ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__InitModulesAssignment_12 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) )
            {
            // InternalN4MFParser.g:5443:3: ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__InitModulesAssignment_12 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) )
            int alt38=21;
            alt38 = dfa38.predict(input);
            switch (alt38) {
                case 1 :
                    // InternalN4MFParser.g:5444:3: ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5444:3: ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) )
                    // InternalN4MFParser.g:5445:4: {...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0)");
                    }
                    // InternalN4MFParser.g:5445:112: ( ( ( rule__ProjectDescription__Group_0__0 ) ) )
                    // InternalN4MFParser.g:5446:5: ( ( rule__ProjectDescription__Group_0__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5452:5: ( ( rule__ProjectDescription__Group_0__0 ) )
                    // InternalN4MFParser.g:5453:6: ( rule__ProjectDescription__Group_0__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_0()); 
                    // InternalN4MFParser.g:5454:6: ( rule__ProjectDescription__Group_0__0 )
                    // InternalN4MFParser.g:5454:7: rule__ProjectDescription__Group_0__0
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
                    // InternalN4MFParser.g:5459:3: ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5459:3: ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) )
                    // InternalN4MFParser.g:5460:4: {...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1)");
                    }
                    // InternalN4MFParser.g:5460:112: ( ( ( rule__ProjectDescription__Group_1__0 ) ) )
                    // InternalN4MFParser.g:5461:5: ( ( rule__ProjectDescription__Group_1__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5467:5: ( ( rule__ProjectDescription__Group_1__0 ) )
                    // InternalN4MFParser.g:5468:6: ( rule__ProjectDescription__Group_1__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_1()); 
                    // InternalN4MFParser.g:5469:6: ( rule__ProjectDescription__Group_1__0 )
                    // InternalN4MFParser.g:5469:7: rule__ProjectDescription__Group_1__0
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
                    // InternalN4MFParser.g:5474:3: ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5474:3: ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) )
                    // InternalN4MFParser.g:5475:4: {...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2)");
                    }
                    // InternalN4MFParser.g:5475:112: ( ( ( rule__ProjectDescription__Group_2__0 ) ) )
                    // InternalN4MFParser.g:5476:5: ( ( rule__ProjectDescription__Group_2__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5482:5: ( ( rule__ProjectDescription__Group_2__0 ) )
                    // InternalN4MFParser.g:5483:6: ( rule__ProjectDescription__Group_2__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_2()); 
                    // InternalN4MFParser.g:5484:6: ( rule__ProjectDescription__Group_2__0 )
                    // InternalN4MFParser.g:5484:7: rule__ProjectDescription__Group_2__0
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
                    // InternalN4MFParser.g:5489:3: ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5489:3: ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) )
                    // InternalN4MFParser.g:5490:4: {...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3)");
                    }
                    // InternalN4MFParser.g:5490:112: ( ( ( rule__ProjectDescription__Group_3__0 ) ) )
                    // InternalN4MFParser.g:5491:5: ( ( rule__ProjectDescription__Group_3__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5497:5: ( ( rule__ProjectDescription__Group_3__0 ) )
                    // InternalN4MFParser.g:5498:6: ( rule__ProjectDescription__Group_3__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_3()); 
                    // InternalN4MFParser.g:5499:6: ( rule__ProjectDescription__Group_3__0 )
                    // InternalN4MFParser.g:5499:7: rule__ProjectDescription__Group_3__0
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
                    // InternalN4MFParser.g:5504:3: ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5504:3: ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) )
                    // InternalN4MFParser.g:5505:4: {...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4)");
                    }
                    // InternalN4MFParser.g:5505:112: ( ( ( rule__ProjectDescription__Group_4__0 ) ) )
                    // InternalN4MFParser.g:5506:5: ( ( rule__ProjectDescription__Group_4__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5512:5: ( ( rule__ProjectDescription__Group_4__0 ) )
                    // InternalN4MFParser.g:5513:6: ( rule__ProjectDescription__Group_4__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_4()); 
                    // InternalN4MFParser.g:5514:6: ( rule__ProjectDescription__Group_4__0 )
                    // InternalN4MFParser.g:5514:7: rule__ProjectDescription__Group_4__0
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
                    // InternalN4MFParser.g:5519:3: ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5519:3: ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) )
                    // InternalN4MFParser.g:5520:4: {...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5)");
                    }
                    // InternalN4MFParser.g:5520:112: ( ( ( rule__ProjectDescription__Group_5__0 ) ) )
                    // InternalN4MFParser.g:5521:5: ( ( rule__ProjectDescription__Group_5__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5527:5: ( ( rule__ProjectDescription__Group_5__0 ) )
                    // InternalN4MFParser.g:5528:6: ( rule__ProjectDescription__Group_5__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_5()); 
                    // InternalN4MFParser.g:5529:6: ( rule__ProjectDescription__Group_5__0 )
                    // InternalN4MFParser.g:5529:7: rule__ProjectDescription__Group_5__0
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
                    // InternalN4MFParser.g:5534:3: ({...}? => ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) ) ) )
                    {
                    // InternalN4MFParser.g:5534:3: ({...}? => ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) ) ) )
                    // InternalN4MFParser.g:5535:4: {...}? => ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6)");
                    }
                    // InternalN4MFParser.g:5535:112: ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) ) )
                    // InternalN4MFParser.g:5536:5: ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5542:5: ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) )
                    // InternalN4MFParser.g:5543:6: ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_6()); 
                    // InternalN4MFParser.g:5544:6: ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 )
                    // InternalN4MFParser.g:5544:7: rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_6()); 

                    }


                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalN4MFParser.g:5549:3: ({...}? => ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) ) ) )
                    {
                    // InternalN4MFParser.g:5549:3: ({...}? => ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) ) ) )
                    // InternalN4MFParser.g:5550:4: {...}? => ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7)");
                    }
                    // InternalN4MFParser.g:5550:112: ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) ) )
                    // InternalN4MFParser.g:5551:5: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5557:5: ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) )
                    // InternalN4MFParser.g:5558:6: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7()); 
                    // InternalN4MFParser.g:5559:6: ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 )
                    // InternalN4MFParser.g:5559:7: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7()); 

                    }


                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalN4MFParser.g:5564:3: ({...}? => ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) ) ) )
                    {
                    // InternalN4MFParser.g:5564:3: ({...}? => ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) ) ) )
                    // InternalN4MFParser.g:5565:4: {...}? => ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8)");
                    }
                    // InternalN4MFParser.g:5565:112: ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) ) )
                    // InternalN4MFParser.g:5566:5: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5572:5: ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) )
                    // InternalN4MFParser.g:5573:6: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8()); 
                    // InternalN4MFParser.g:5574:6: ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 )
                    // InternalN4MFParser.g:5574:7: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8()); 

                    }


                    }


                    }


                    }
                    break;
                case 10 :
                    // InternalN4MFParser.g:5579:3: ({...}? => ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) ) ) )
                    {
                    // InternalN4MFParser.g:5579:3: ({...}? => ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) ) ) )
                    // InternalN4MFParser.g:5580:4: {...}? => ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9)");
                    }
                    // InternalN4MFParser.g:5580:112: ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) ) )
                    // InternalN4MFParser.g:5581:5: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5587:5: ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) )
                    // InternalN4MFParser.g:5588:6: ( rule__ProjectDescription__ProjectDependenciesAssignment_9 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9()); 
                    // InternalN4MFParser.g:5589:6: ( rule__ProjectDescription__ProjectDependenciesAssignment_9 )
                    // InternalN4MFParser.g:5589:7: rule__ProjectDescription__ProjectDependenciesAssignment_9
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__ProjectDependenciesAssignment_9();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9()); 

                    }


                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalN4MFParser.g:5594:3: ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5594:3: ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) )
                    // InternalN4MFParser.g:5595:4: {...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10)");
                    }
                    // InternalN4MFParser.g:5595:113: ( ( ( rule__ProjectDescription__Group_10__0 ) ) )
                    // InternalN4MFParser.g:5596:5: ( ( rule__ProjectDescription__Group_10__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5602:5: ( ( rule__ProjectDescription__Group_10__0 ) )
                    // InternalN4MFParser.g:5603:6: ( rule__ProjectDescription__Group_10__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_10()); 
                    // InternalN4MFParser.g:5604:6: ( rule__ProjectDescription__Group_10__0 )
                    // InternalN4MFParser.g:5604:7: rule__ProjectDescription__Group_10__0
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
                    // InternalN4MFParser.g:5609:3: ({...}? => ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) ) ) )
                    {
                    // InternalN4MFParser.g:5609:3: ({...}? => ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) ) ) )
                    // InternalN4MFParser.g:5610:4: {...}? => ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11)");
                    }
                    // InternalN4MFParser.g:5610:113: ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) ) )
                    // InternalN4MFParser.g:5611:5: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5617:5: ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) )
                    // InternalN4MFParser.g:5618:6: ( rule__ProjectDescription__ImplementedProjectsAssignment_11 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11()); 
                    // InternalN4MFParser.g:5619:6: ( rule__ProjectDescription__ImplementedProjectsAssignment_11 )
                    // InternalN4MFParser.g:5619:7: rule__ProjectDescription__ImplementedProjectsAssignment_11
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__ImplementedProjectsAssignment_11();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11()); 

                    }


                    }


                    }


                    }
                    break;
                case 13 :
                    // InternalN4MFParser.g:5624:3: ({...}? => ( ( ( rule__ProjectDescription__InitModulesAssignment_12 ) ) ) )
                    {
                    // InternalN4MFParser.g:5624:3: ({...}? => ( ( ( rule__ProjectDescription__InitModulesAssignment_12 ) ) ) )
                    // InternalN4MFParser.g:5625:4: {...}? => ( ( ( rule__ProjectDescription__InitModulesAssignment_12 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12)");
                    }
                    // InternalN4MFParser.g:5625:113: ( ( ( rule__ProjectDescription__InitModulesAssignment_12 ) ) )
                    // InternalN4MFParser.g:5626:5: ( ( rule__ProjectDescription__InitModulesAssignment_12 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5632:5: ( ( rule__ProjectDescription__InitModulesAssignment_12 ) )
                    // InternalN4MFParser.g:5633:6: ( rule__ProjectDescription__InitModulesAssignment_12 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12()); 
                    // InternalN4MFParser.g:5634:6: ( rule__ProjectDescription__InitModulesAssignment_12 )
                    // InternalN4MFParser.g:5634:7: rule__ProjectDescription__InitModulesAssignment_12
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__InitModulesAssignment_12();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12()); 

                    }


                    }


                    }


                    }
                    break;
                case 14 :
                    // InternalN4MFParser.g:5639:3: ({...}? => ( ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) ) ) )
                    {
                    // InternalN4MFParser.g:5639:3: ({...}? => ( ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) ) ) )
                    // InternalN4MFParser.g:5640:4: {...}? => ( ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13)");
                    }
                    // InternalN4MFParser.g:5640:113: ( ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) ) )
                    // InternalN4MFParser.g:5641:5: ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5647:5: ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) )
                    // InternalN4MFParser.g:5648:6: ( rule__ProjectDescription__ExecModuleAssignment_13 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_13()); 
                    // InternalN4MFParser.g:5649:6: ( rule__ProjectDescription__ExecModuleAssignment_13 )
                    // InternalN4MFParser.g:5649:7: rule__ProjectDescription__ExecModuleAssignment_13
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__ExecModuleAssignment_13();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_13()); 

                    }


                    }


                    }


                    }
                    break;
                case 15 :
                    // InternalN4MFParser.g:5654:3: ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5654:3: ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) )
                    // InternalN4MFParser.g:5655:4: {...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14)");
                    }
                    // InternalN4MFParser.g:5655:113: ( ( ( rule__ProjectDescription__Group_14__0 ) ) )
                    // InternalN4MFParser.g:5656:5: ( ( rule__ProjectDescription__Group_14__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5662:5: ( ( rule__ProjectDescription__Group_14__0 ) )
                    // InternalN4MFParser.g:5663:6: ( rule__ProjectDescription__Group_14__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_14()); 
                    // InternalN4MFParser.g:5664:6: ( rule__ProjectDescription__Group_14__0 )
                    // InternalN4MFParser.g:5664:7: rule__ProjectDescription__Group_14__0
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
                    // InternalN4MFParser.g:5669:3: ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5669:3: ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) )
                    // InternalN4MFParser.g:5670:4: {...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15)");
                    }
                    // InternalN4MFParser.g:5670:113: ( ( ( rule__ProjectDescription__Group_15__0 ) ) )
                    // InternalN4MFParser.g:5671:5: ( ( rule__ProjectDescription__Group_15__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5677:5: ( ( rule__ProjectDescription__Group_15__0 ) )
                    // InternalN4MFParser.g:5678:6: ( rule__ProjectDescription__Group_15__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_15()); 
                    // InternalN4MFParser.g:5679:6: ( rule__ProjectDescription__Group_15__0 )
                    // InternalN4MFParser.g:5679:7: rule__ProjectDescription__Group_15__0
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
                    // InternalN4MFParser.g:5684:3: ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5684:3: ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) )
                    // InternalN4MFParser.g:5685:4: {...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16)");
                    }
                    // InternalN4MFParser.g:5685:113: ( ( ( rule__ProjectDescription__Group_16__0 ) ) )
                    // InternalN4MFParser.g:5686:5: ( ( rule__ProjectDescription__Group_16__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5692:5: ( ( rule__ProjectDescription__Group_16__0 ) )
                    // InternalN4MFParser.g:5693:6: ( rule__ProjectDescription__Group_16__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_16()); 
                    // InternalN4MFParser.g:5694:6: ( rule__ProjectDescription__Group_16__0 )
                    // InternalN4MFParser.g:5694:7: rule__ProjectDescription__Group_16__0
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
                    // InternalN4MFParser.g:5699:3: ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5699:3: ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) )
                    // InternalN4MFParser.g:5700:4: {...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17)");
                    }
                    // InternalN4MFParser.g:5700:113: ( ( ( rule__ProjectDescription__Group_17__0 ) ) )
                    // InternalN4MFParser.g:5701:5: ( ( rule__ProjectDescription__Group_17__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5707:5: ( ( rule__ProjectDescription__Group_17__0 ) )
                    // InternalN4MFParser.g:5708:6: ( rule__ProjectDescription__Group_17__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_17()); 
                    // InternalN4MFParser.g:5709:6: ( rule__ProjectDescription__Group_17__0 )
                    // InternalN4MFParser.g:5709:7: rule__ProjectDescription__Group_17__0
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
                    // InternalN4MFParser.g:5714:3: ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5714:3: ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) )
                    // InternalN4MFParser.g:5715:4: {...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18)");
                    }
                    // InternalN4MFParser.g:5715:113: ( ( ( rule__ProjectDescription__Group_18__0 ) ) )
                    // InternalN4MFParser.g:5716:5: ( ( rule__ProjectDescription__Group_18__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5722:5: ( ( rule__ProjectDescription__Group_18__0 ) )
                    // InternalN4MFParser.g:5723:6: ( rule__ProjectDescription__Group_18__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_18()); 
                    // InternalN4MFParser.g:5724:6: ( rule__ProjectDescription__Group_18__0 )
                    // InternalN4MFParser.g:5724:7: rule__ProjectDescription__Group_18__0
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
                    // InternalN4MFParser.g:5729:3: ({...}? => ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) ) ) )
                    {
                    // InternalN4MFParser.g:5729:3: ({...}? => ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) ) ) )
                    // InternalN4MFParser.g:5730:4: {...}? => ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19)");
                    }
                    // InternalN4MFParser.g:5730:113: ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) ) )
                    // InternalN4MFParser.g:5731:5: ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5737:5: ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) )
                    // InternalN4MFParser.g:5738:6: ( rule__ProjectDescription__TestedProjectsAssignment_19 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19()); 
                    // InternalN4MFParser.g:5739:6: ( rule__ProjectDescription__TestedProjectsAssignment_19 )
                    // InternalN4MFParser.g:5739:7: rule__ProjectDescription__TestedProjectsAssignment_19
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProjectDescription__TestedProjectsAssignment_19();

                    state._fsp--;


                    }

                     after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19()); 

                    }


                    }


                    }


                    }
                    break;
                case 21 :
                    // InternalN4MFParser.g:5744:3: ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) )
                    {
                    // InternalN4MFParser.g:5744:3: ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) )
                    // InternalN4MFParser.g:5745:4: {...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) )
                    {
                    if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20) ) {
                        throw new FailedPredicateException(input, "rule__ProjectDescription__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20)");
                    }
                    // InternalN4MFParser.g:5745:113: ( ( ( rule__ProjectDescription__Group_20__0 ) ) )
                    // InternalN4MFParser.g:5746:5: ( ( rule__ProjectDescription__Group_20__0 ) )
                    {

                    					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20);
                    				

                    					selected = true;
                    				
                    // InternalN4MFParser.g:5752:5: ( ( rule__ProjectDescription__Group_20__0 ) )
                    // InternalN4MFParser.g:5753:6: ( rule__ProjectDescription__Group_20__0 )
                    {
                     before(grammarAccess.getProjectDescriptionAccess().getGroup_20()); 
                    // InternalN4MFParser.g:5754:6: ( rule__ProjectDescription__Group_20__0 )
                    // InternalN4MFParser.g:5754:7: rule__ProjectDescription__Group_20__0
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
    // InternalN4MFParser.g:5767:1: rule__ProjectDescription__UnorderedGroup__0 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__1 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5771:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__1 )? )
            // InternalN4MFParser.g:5772:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__1 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5773:2: ( rule__ProjectDescription__UnorderedGroup__1 )?
            int alt39=2;
            alt39 = dfa39.predict(input);
            switch (alt39) {
                case 1 :
                    // InternalN4MFParser.g:5773:2: rule__ProjectDescription__UnorderedGroup__1
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
    // InternalN4MFParser.g:5779:1: rule__ProjectDescription__UnorderedGroup__1 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__2 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5783:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__2 )? )
            // InternalN4MFParser.g:5784:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__2 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5785:2: ( rule__ProjectDescription__UnorderedGroup__2 )?
            int alt40=2;
            alt40 = dfa40.predict(input);
            switch (alt40) {
                case 1 :
                    // InternalN4MFParser.g:5785:2: rule__ProjectDescription__UnorderedGroup__2
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
    // InternalN4MFParser.g:5791:1: rule__ProjectDescription__UnorderedGroup__2 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__3 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5795:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__3 )? )
            // InternalN4MFParser.g:5796:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__3 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5797:2: ( rule__ProjectDescription__UnorderedGroup__3 )?
            int alt41=2;
            alt41 = dfa41.predict(input);
            switch (alt41) {
                case 1 :
                    // InternalN4MFParser.g:5797:2: rule__ProjectDescription__UnorderedGroup__3
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
    // InternalN4MFParser.g:5803:1: rule__ProjectDescription__UnorderedGroup__3 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__4 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5807:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__4 )? )
            // InternalN4MFParser.g:5808:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__4 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5809:2: ( rule__ProjectDescription__UnorderedGroup__4 )?
            int alt42=2;
            alt42 = dfa42.predict(input);
            switch (alt42) {
                case 1 :
                    // InternalN4MFParser.g:5809:2: rule__ProjectDescription__UnorderedGroup__4
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
    // InternalN4MFParser.g:5815:1: rule__ProjectDescription__UnorderedGroup__4 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__5 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5819:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__5 )? )
            // InternalN4MFParser.g:5820:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__5 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5821:2: ( rule__ProjectDescription__UnorderedGroup__5 )?
            int alt43=2;
            alt43 = dfa43.predict(input);
            switch (alt43) {
                case 1 :
                    // InternalN4MFParser.g:5821:2: rule__ProjectDescription__UnorderedGroup__5
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
    // InternalN4MFParser.g:5827:1: rule__ProjectDescription__UnorderedGroup__5 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__6 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5831:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__6 )? )
            // InternalN4MFParser.g:5832:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__6 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5833:2: ( rule__ProjectDescription__UnorderedGroup__6 )?
            int alt44=2;
            alt44 = dfa44.predict(input);
            switch (alt44) {
                case 1 :
                    // InternalN4MFParser.g:5833:2: rule__ProjectDescription__UnorderedGroup__6
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
    // InternalN4MFParser.g:5839:1: rule__ProjectDescription__UnorderedGroup__6 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__7 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5843:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__7 )? )
            // InternalN4MFParser.g:5844:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__7 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5845:2: ( rule__ProjectDescription__UnorderedGroup__7 )?
            int alt45=2;
            alt45 = dfa45.predict(input);
            switch (alt45) {
                case 1 :
                    // InternalN4MFParser.g:5845:2: rule__ProjectDescription__UnorderedGroup__7
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
    // InternalN4MFParser.g:5851:1: rule__ProjectDescription__UnorderedGroup__7 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__8 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5855:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__8 )? )
            // InternalN4MFParser.g:5856:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__8 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5857:2: ( rule__ProjectDescription__UnorderedGroup__8 )?
            int alt46=2;
            alt46 = dfa46.predict(input);
            switch (alt46) {
                case 1 :
                    // InternalN4MFParser.g:5857:2: rule__ProjectDescription__UnorderedGroup__8
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
    // InternalN4MFParser.g:5863:1: rule__ProjectDescription__UnorderedGroup__8 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__9 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5867:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__9 )? )
            // InternalN4MFParser.g:5868:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__9 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5869:2: ( rule__ProjectDescription__UnorderedGroup__9 )?
            int alt47=2;
            alt47 = dfa47.predict(input);
            switch (alt47) {
                case 1 :
                    // InternalN4MFParser.g:5869:2: rule__ProjectDescription__UnorderedGroup__9
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
    // InternalN4MFParser.g:5875:1: rule__ProjectDescription__UnorderedGroup__9 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__10 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5879:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__10 )? )
            // InternalN4MFParser.g:5880:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__10 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5881:2: ( rule__ProjectDescription__UnorderedGroup__10 )?
            int alt48=2;
            alt48 = dfa48.predict(input);
            switch (alt48) {
                case 1 :
                    // InternalN4MFParser.g:5881:2: rule__ProjectDescription__UnorderedGroup__10
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
    // InternalN4MFParser.g:5887:1: rule__ProjectDescription__UnorderedGroup__10 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__11 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5891:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__11 )? )
            // InternalN4MFParser.g:5892:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__11 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5893:2: ( rule__ProjectDescription__UnorderedGroup__11 )?
            int alt49=2;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // InternalN4MFParser.g:5893:2: rule__ProjectDescription__UnorderedGroup__11
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
    // InternalN4MFParser.g:5899:1: rule__ProjectDescription__UnorderedGroup__11 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__12 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5903:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__12 )? )
            // InternalN4MFParser.g:5904:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__12 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5905:2: ( rule__ProjectDescription__UnorderedGroup__12 )?
            int alt50=2;
            alt50 = dfa50.predict(input);
            switch (alt50) {
                case 1 :
                    // InternalN4MFParser.g:5905:2: rule__ProjectDescription__UnorderedGroup__12
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
    // InternalN4MFParser.g:5911:1: rule__ProjectDescription__UnorderedGroup__12 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__13 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5915:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__13 )? )
            // InternalN4MFParser.g:5916:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__13 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5917:2: ( rule__ProjectDescription__UnorderedGroup__13 )?
            int alt51=2;
            alt51 = dfa51.predict(input);
            switch (alt51) {
                case 1 :
                    // InternalN4MFParser.g:5917:2: rule__ProjectDescription__UnorderedGroup__13
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
    // InternalN4MFParser.g:5923:1: rule__ProjectDescription__UnorderedGroup__13 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__14 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5927:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__14 )? )
            // InternalN4MFParser.g:5928:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__14 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5929:2: ( rule__ProjectDescription__UnorderedGroup__14 )?
            int alt52=2;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // InternalN4MFParser.g:5929:2: rule__ProjectDescription__UnorderedGroup__14
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
    // InternalN4MFParser.g:5935:1: rule__ProjectDescription__UnorderedGroup__14 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__15 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5939:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__15 )? )
            // InternalN4MFParser.g:5940:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__15 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5941:2: ( rule__ProjectDescription__UnorderedGroup__15 )?
            int alt53=2;
            alt53 = dfa53.predict(input);
            switch (alt53) {
                case 1 :
                    // InternalN4MFParser.g:5941:2: rule__ProjectDescription__UnorderedGroup__15
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
    // InternalN4MFParser.g:5947:1: rule__ProjectDescription__UnorderedGroup__15 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__16 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__15() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5951:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__16 )? )
            // InternalN4MFParser.g:5952:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__16 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5953:2: ( rule__ProjectDescription__UnorderedGroup__16 )?
            int alt54=2;
            alt54 = dfa54.predict(input);
            switch (alt54) {
                case 1 :
                    // InternalN4MFParser.g:5953:2: rule__ProjectDescription__UnorderedGroup__16
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
    // InternalN4MFParser.g:5959:1: rule__ProjectDescription__UnorderedGroup__16 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__17 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__16() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5963:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__17 )? )
            // InternalN4MFParser.g:5964:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__17 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5965:2: ( rule__ProjectDescription__UnorderedGroup__17 )?
            int alt55=2;
            alt55 = dfa55.predict(input);
            switch (alt55) {
                case 1 :
                    // InternalN4MFParser.g:5965:2: rule__ProjectDescription__UnorderedGroup__17
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
    // InternalN4MFParser.g:5971:1: rule__ProjectDescription__UnorderedGroup__17 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__18 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__17() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5975:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__18 )? )
            // InternalN4MFParser.g:5976:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__18 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5977:2: ( rule__ProjectDescription__UnorderedGroup__18 )?
            int alt56=2;
            alt56 = dfa56.predict(input);
            switch (alt56) {
                case 1 :
                    // InternalN4MFParser.g:5977:2: rule__ProjectDescription__UnorderedGroup__18
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
    // InternalN4MFParser.g:5983:1: rule__ProjectDescription__UnorderedGroup__18 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__19 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__18() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5987:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__19 )? )
            // InternalN4MFParser.g:5988:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__19 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:5989:2: ( rule__ProjectDescription__UnorderedGroup__19 )?
            int alt57=2;
            alt57 = dfa57.predict(input);
            switch (alt57) {
                case 1 :
                    // InternalN4MFParser.g:5989:2: rule__ProjectDescription__UnorderedGroup__19
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
    // InternalN4MFParser.g:5995:1: rule__ProjectDescription__UnorderedGroup__19 : rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__20 )? ;
    public final void rule__ProjectDescription__UnorderedGroup__19() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:5999:1: ( rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__20 )? )
            // InternalN4MFParser.g:6000:2: rule__ProjectDescription__UnorderedGroup__Impl ( rule__ProjectDescription__UnorderedGroup__20 )?
            {
            pushFollow(FOLLOW_36);
            rule__ProjectDescription__UnorderedGroup__Impl();

            state._fsp--;

            // InternalN4MFParser.g:6001:2: ( rule__ProjectDescription__UnorderedGroup__20 )?
            int alt58=2;
            alt58 = dfa58.predict(input);
            switch (alt58) {
                case 1 :
                    // InternalN4MFParser.g:6001:2: rule__ProjectDescription__UnorderedGroup__20
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
    // InternalN4MFParser.g:6007:1: rule__ProjectDescription__UnorderedGroup__20 : rule__ProjectDescription__UnorderedGroup__Impl ;
    public final void rule__ProjectDescription__UnorderedGroup__20() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6011:1: ( rule__ProjectDescription__UnorderedGroup__Impl )
            // InternalN4MFParser.g:6012:2: rule__ProjectDescription__UnorderedGroup__Impl
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
    // InternalN4MFParser.g:6019:1: rule__ProjectDescription__ProjectIdAssignment_0_2 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectDescription__ProjectIdAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6023:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6024:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6024:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6025:3: ruleN4mfIdentifier
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
    // InternalN4MFParser.g:6034:1: rule__ProjectDescription__ProjectTypeAssignment_1_2 : ( ruleProjectType ) ;
    public final void rule__ProjectDescription__ProjectTypeAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6038:1: ( ( ruleProjectType ) )
            // InternalN4MFParser.g:6039:2: ( ruleProjectType )
            {
            // InternalN4MFParser.g:6039:2: ( ruleProjectType )
            // InternalN4MFParser.g:6040:3: ruleProjectType
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
    // InternalN4MFParser.g:6049:1: rule__ProjectDescription__ProjectVersionAssignment_2_2 : ( ruleDeclaredVersion ) ;
    public final void rule__ProjectDescription__ProjectVersionAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6053:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6054:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6054:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6055:3: ruleDeclaredVersion
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
    // InternalN4MFParser.g:6064:1: rule__ProjectDescription__DeclaredVendorIdAssignment_3_2 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectDescription__DeclaredVendorIdAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6068:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6069:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6069:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6070:3: ruleN4mfIdentifier
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
    // InternalN4MFParser.g:6079:1: rule__ProjectDescription__VendorNameAssignment_4_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__VendorNameAssignment_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6083:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6084:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6084:2: ( RULE_STRING )
            // InternalN4MFParser.g:6085:3: RULE_STRING
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
    // InternalN4MFParser.g:6094:1: rule__ProjectDescription__MainModuleAssignment_5_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__MainModuleAssignment_5_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6098:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6099:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6099:2: ( RULE_STRING )
            // InternalN4MFParser.g:6100:3: RULE_STRING
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


    // $ANTLR start "rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6"
    // InternalN4MFParser.g:6109:1: rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 : ( ruleExtendedRuntimeEnvironment ) ;
    public final void rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6113:1: ( ( ruleExtendedRuntimeEnvironment ) )
            // InternalN4MFParser.g:6114:2: ( ruleExtendedRuntimeEnvironment )
            {
            // InternalN4MFParser.g:6114:2: ( ruleExtendedRuntimeEnvironment )
            // InternalN4MFParser.g:6115:3: ruleExtendedRuntimeEnvironment
            {
             before(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentExtendedRuntimeEnvironmentParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            ruleExtendedRuntimeEnvironment();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentExtendedRuntimeEnvironmentParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6"


    // $ANTLR start "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7"
    // InternalN4MFParser.g:6124:1: rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 : ( ruleProvidedRuntimeLibraries ) ;
    public final void rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6128:1: ( ( ruleProvidedRuntimeLibraries ) )
            // InternalN4MFParser.g:6129:2: ( ruleProvidedRuntimeLibraries )
            {
            // InternalN4MFParser.g:6129:2: ( ruleProvidedRuntimeLibraries )
            // InternalN4MFParser.g:6130:3: ruleProvidedRuntimeLibraries
            {
             before(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibrariesParserRuleCall_7_0()); 
            pushFollow(FOLLOW_2);
            ruleProvidedRuntimeLibraries();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibrariesParserRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7"


    // $ANTLR start "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8"
    // InternalN4MFParser.g:6139:1: rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 : ( ruleRequiredRuntimeLibraries ) ;
    public final void rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6143:1: ( ( ruleRequiredRuntimeLibraries ) )
            // InternalN4MFParser.g:6144:2: ( ruleRequiredRuntimeLibraries )
            {
            // InternalN4MFParser.g:6144:2: ( ruleRequiredRuntimeLibraries )
            // InternalN4MFParser.g:6145:3: ruleRequiredRuntimeLibraries
            {
             before(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibrariesParserRuleCall_8_0()); 
            pushFollow(FOLLOW_2);
            ruleRequiredRuntimeLibraries();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibrariesParserRuleCall_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8"


    // $ANTLR start "rule__ProjectDescription__ProjectDependenciesAssignment_9"
    // InternalN4MFParser.g:6154:1: rule__ProjectDescription__ProjectDependenciesAssignment_9 : ( ruleProjectDependencies ) ;
    public final void rule__ProjectDescription__ProjectDependenciesAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6158:1: ( ( ruleProjectDependencies ) )
            // InternalN4MFParser.g:6159:2: ( ruleProjectDependencies )
            {
            // InternalN4MFParser.g:6159:2: ( ruleProjectDependencies )
            // InternalN4MFParser.g:6160:3: ruleProjectDependencies
            {
             before(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependenciesParserRuleCall_9_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectDependencies();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependenciesParserRuleCall_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ProjectDependenciesAssignment_9"


    // $ANTLR start "rule__ProjectDescription__ImplementationIdAssignment_10_2"
    // InternalN4MFParser.g:6169:1: rule__ProjectDescription__ImplementationIdAssignment_10_2 : ( ruleN4mfIdentifier ) ;
    public final void rule__ProjectDescription__ImplementationIdAssignment_10_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6173:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6174:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6174:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6175:3: ruleN4mfIdentifier
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


    // $ANTLR start "rule__ProjectDescription__ImplementedProjectsAssignment_11"
    // InternalN4MFParser.g:6184:1: rule__ProjectDescription__ImplementedProjectsAssignment_11 : ( ruleImplementedProjects ) ;
    public final void rule__ProjectDescription__ImplementedProjectsAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6188:1: ( ( ruleImplementedProjects ) )
            // InternalN4MFParser.g:6189:2: ( ruleImplementedProjects )
            {
            // InternalN4MFParser.g:6189:2: ( ruleImplementedProjects )
            // InternalN4MFParser.g:6190:3: ruleImplementedProjects
            {
             before(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsImplementedProjectsParserRuleCall_11_0()); 
            pushFollow(FOLLOW_2);
            ruleImplementedProjects();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsImplementedProjectsParserRuleCall_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ImplementedProjectsAssignment_11"


    // $ANTLR start "rule__ProjectDescription__InitModulesAssignment_12"
    // InternalN4MFParser.g:6199:1: rule__ProjectDescription__InitModulesAssignment_12 : ( ruleInitModules ) ;
    public final void rule__ProjectDescription__InitModulesAssignment_12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6203:1: ( ( ruleInitModules ) )
            // InternalN4MFParser.g:6204:2: ( ruleInitModules )
            {
            // InternalN4MFParser.g:6204:2: ( ruleInitModules )
            // InternalN4MFParser.g:6205:3: ruleInitModules
            {
             before(grammarAccess.getProjectDescriptionAccess().getInitModulesInitModulesParserRuleCall_12_0()); 
            pushFollow(FOLLOW_2);
            ruleInitModules();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getInitModulesInitModulesParserRuleCall_12_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__InitModulesAssignment_12"


    // $ANTLR start "rule__ProjectDescription__ExecModuleAssignment_13"
    // InternalN4MFParser.g:6214:1: rule__ProjectDescription__ExecModuleAssignment_13 : ( ruleExecModule ) ;
    public final void rule__ProjectDescription__ExecModuleAssignment_13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6218:1: ( ( ruleExecModule ) )
            // InternalN4MFParser.g:6219:2: ( ruleExecModule )
            {
            // InternalN4MFParser.g:6219:2: ( ruleExecModule )
            // InternalN4MFParser.g:6220:3: ruleExecModule
            {
             before(grammarAccess.getProjectDescriptionAccess().getExecModuleExecModuleParserRuleCall_13_0()); 
            pushFollow(FOLLOW_2);
            ruleExecModule();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getExecModuleExecModuleParserRuleCall_13_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ExecModuleAssignment_13"


    // $ANTLR start "rule__ProjectDescription__OutputPathAssignment_14_2"
    // InternalN4MFParser.g:6229:1: rule__ProjectDescription__OutputPathAssignment_14_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__OutputPathAssignment_14_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6233:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6234:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6234:2: ( RULE_STRING )
            // InternalN4MFParser.g:6235:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getOutputPathSTRINGTerminalRuleCall_14_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getOutputPathSTRINGTerminalRuleCall_14_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__OutputPathAssignment_14_2"


    // $ANTLR start "rule__ProjectDescription__LibraryPathsAssignment_15_2"
    // InternalN4MFParser.g:6244:1: rule__ProjectDescription__LibraryPathsAssignment_15_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__LibraryPathsAssignment_15_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6248:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6249:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6249:2: ( RULE_STRING )
            // InternalN4MFParser.g:6250:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsSTRINGTerminalRuleCall_15_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsSTRINGTerminalRuleCall_15_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__LibraryPathsAssignment_15_2"


    // $ANTLR start "rule__ProjectDescription__LibraryPathsAssignment_15_3_1"
    // InternalN4MFParser.g:6259:1: rule__ProjectDescription__LibraryPathsAssignment_15_3_1 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__LibraryPathsAssignment_15_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6263:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6264:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6264:2: ( RULE_STRING )
            // InternalN4MFParser.g:6265:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getLibraryPathsSTRINGTerminalRuleCall_15_3_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getLibraryPathsSTRINGTerminalRuleCall_15_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__LibraryPathsAssignment_15_3_1"


    // $ANTLR start "rule__ProjectDescription__ResourcePathsAssignment_16_2"
    // InternalN4MFParser.g:6274:1: rule__ProjectDescription__ResourcePathsAssignment_16_2 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__ResourcePathsAssignment_16_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6278:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6279:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6279:2: ( RULE_STRING )
            // InternalN4MFParser.g:6280:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsSTRINGTerminalRuleCall_16_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getResourcePathsSTRINGTerminalRuleCall_16_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ResourcePathsAssignment_16_2"


    // $ANTLR start "rule__ProjectDescription__ResourcePathsAssignment_16_3_1"
    // InternalN4MFParser.g:6289:1: rule__ProjectDescription__ResourcePathsAssignment_16_3_1 : ( RULE_STRING ) ;
    public final void rule__ProjectDescription__ResourcePathsAssignment_16_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6293:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6294:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6294:2: ( RULE_STRING )
            // InternalN4MFParser.g:6295:3: RULE_STRING
            {
             before(grammarAccess.getProjectDescriptionAccess().getResourcePathsSTRINGTerminalRuleCall_16_3_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getProjectDescriptionAccess().getResourcePathsSTRINGTerminalRuleCall_16_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__ResourcePathsAssignment_16_3_1"


    // $ANTLR start "rule__ProjectDescription__SourceFragmentAssignment_17_2"
    // InternalN4MFParser.g:6304:1: rule__ProjectDescription__SourceFragmentAssignment_17_2 : ( ruleSourceFragment ) ;
    public final void rule__ProjectDescription__SourceFragmentAssignment_17_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6308:1: ( ( ruleSourceFragment ) )
            // InternalN4MFParser.g:6309:2: ( ruleSourceFragment )
            {
            // InternalN4MFParser.g:6309:2: ( ruleSourceFragment )
            // InternalN4MFParser.g:6310:3: ruleSourceFragment
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
    // InternalN4MFParser.g:6319:1: rule__ProjectDescription__ModuleFiltersAssignment_18_2 : ( ruleModuleFilter ) ;
    public final void rule__ProjectDescription__ModuleFiltersAssignment_18_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6323:1: ( ( ruleModuleFilter ) )
            // InternalN4MFParser.g:6324:2: ( ruleModuleFilter )
            {
            // InternalN4MFParser.g:6324:2: ( ruleModuleFilter )
            // InternalN4MFParser.g:6325:3: ruleModuleFilter
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


    // $ANTLR start "rule__ProjectDescription__TestedProjectsAssignment_19"
    // InternalN4MFParser.g:6334:1: rule__ProjectDescription__TestedProjectsAssignment_19 : ( ruleTestedProjects ) ;
    public final void rule__ProjectDescription__TestedProjectsAssignment_19() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6338:1: ( ( ruleTestedProjects ) )
            // InternalN4MFParser.g:6339:2: ( ruleTestedProjects )
            {
            // InternalN4MFParser.g:6339:2: ( ruleTestedProjects )
            // InternalN4MFParser.g:6340:3: ruleTestedProjects
            {
             before(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectsParserRuleCall_19_0()); 
            pushFollow(FOLLOW_2);
            ruleTestedProjects();

            state._fsp--;

             after(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectsParserRuleCall_19_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDescription__TestedProjectsAssignment_19"


    // $ANTLR start "rule__ProjectDescription__ModuleLoaderAssignment_20_2"
    // InternalN4MFParser.g:6349:1: rule__ProjectDescription__ModuleLoaderAssignment_20_2 : ( ruleModuleLoader ) ;
    public final void rule__ProjectDescription__ModuleLoaderAssignment_20_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6353:1: ( ( ruleModuleLoader ) )
            // InternalN4MFParser.g:6354:2: ( ruleModuleLoader )
            {
            // InternalN4MFParser.g:6354:2: ( ruleModuleLoader )
            // InternalN4MFParser.g:6355:3: ruleModuleLoader
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


    // $ANTLR start "rule__ExecModule__ExecModuleAssignment_3"
    // InternalN4MFParser.g:6364:1: rule__ExecModule__ExecModuleAssignment_3 : ( ruleBootstrapModule ) ;
    public final void rule__ExecModule__ExecModuleAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6368:1: ( ( ruleBootstrapModule ) )
            // InternalN4MFParser.g:6369:2: ( ruleBootstrapModule )
            {
            // InternalN4MFParser.g:6369:2: ( ruleBootstrapModule )
            // InternalN4MFParser.g:6370:3: ruleBootstrapModule
            {
             before(grammarAccess.getExecModuleAccess().getExecModuleBootstrapModuleParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleBootstrapModule();

            state._fsp--;

             after(grammarAccess.getExecModuleAccess().getExecModuleBootstrapModuleParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExecModule__ExecModuleAssignment_3"


    // $ANTLR start "rule__TestedProjects__TestedProjectsAssignment_3_0"
    // InternalN4MFParser.g:6379:1: rule__TestedProjects__TestedProjectsAssignment_3_0 : ( ruleTestedProject ) ;
    public final void rule__TestedProjects__TestedProjectsAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6383:1: ( ( ruleTestedProject ) )
            // InternalN4MFParser.g:6384:2: ( ruleTestedProject )
            {
            // InternalN4MFParser.g:6384:2: ( ruleTestedProject )
            // InternalN4MFParser.g:6385:3: ruleTestedProject
            {
             before(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_0_0()); 
            pushFollow(FOLLOW_2);
            ruleTestedProject();

            state._fsp--;

             after(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__TestedProjectsAssignment_3_0"


    // $ANTLR start "rule__TestedProjects__TestedProjectsAssignment_3_1_1"
    // InternalN4MFParser.g:6394:1: rule__TestedProjects__TestedProjectsAssignment_3_1_1 : ( ruleTestedProject ) ;
    public final void rule__TestedProjects__TestedProjectsAssignment_3_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6398:1: ( ( ruleTestedProject ) )
            // InternalN4MFParser.g:6399:2: ( ruleTestedProject )
            {
            // InternalN4MFParser.g:6399:2: ( ruleTestedProject )
            // InternalN4MFParser.g:6400:3: ruleTestedProject
            {
             before(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleTestedProject();

            state._fsp--;

             after(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProjects__TestedProjectsAssignment_3_1_1"


    // $ANTLR start "rule__InitModules__InitModulesAssignment_3_0"
    // InternalN4MFParser.g:6409:1: rule__InitModules__InitModulesAssignment_3_0 : ( ruleBootstrapModule ) ;
    public final void rule__InitModules__InitModulesAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6413:1: ( ( ruleBootstrapModule ) )
            // InternalN4MFParser.g:6414:2: ( ruleBootstrapModule )
            {
            // InternalN4MFParser.g:6414:2: ( ruleBootstrapModule )
            // InternalN4MFParser.g:6415:3: ruleBootstrapModule
            {
             before(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_0_0()); 
            pushFollow(FOLLOW_2);
            ruleBootstrapModule();

            state._fsp--;

             after(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__InitModulesAssignment_3_0"


    // $ANTLR start "rule__InitModules__InitModulesAssignment_3_1_1"
    // InternalN4MFParser.g:6424:1: rule__InitModules__InitModulesAssignment_3_1_1 : ( ruleBootstrapModule ) ;
    public final void rule__InitModules__InitModulesAssignment_3_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6428:1: ( ( ruleBootstrapModule ) )
            // InternalN4MFParser.g:6429:2: ( ruleBootstrapModule )
            {
            // InternalN4MFParser.g:6429:2: ( ruleBootstrapModule )
            // InternalN4MFParser.g:6430:3: ruleBootstrapModule
            {
             before(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBootstrapModule();

            state._fsp--;

             after(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__InitModules__InitModulesAssignment_3_1_1"


    // $ANTLR start "rule__ImplementedProjects__ImplementedProjectsAssignment_3_0"
    // InternalN4MFParser.g:6439:1: rule__ImplementedProjects__ImplementedProjectsAssignment_3_0 : ( ruleProjectReference ) ;
    public final void rule__ImplementedProjects__ImplementedProjectsAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6443:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:6444:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:6444:2: ( ruleProjectReference )
            // InternalN4MFParser.g:6445:3: ruleProjectReference
            {
             before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__ImplementedProjectsAssignment_3_0"


    // $ANTLR start "rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1"
    // InternalN4MFParser.g:6454:1: rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1 : ( ruleProjectReference ) ;
    public final void rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6458:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:6459:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:6459:2: ( ruleProjectReference )
            // InternalN4MFParser.g:6460:3: ruleProjectReference
            {
             before(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1"


    // $ANTLR start "rule__ProjectDependencies__ProjectDependenciesAssignment_3_0"
    // InternalN4MFParser.g:6469:1: rule__ProjectDependencies__ProjectDependenciesAssignment_3_0 : ( ruleProjectDependency ) ;
    public final void rule__ProjectDependencies__ProjectDependenciesAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6473:1: ( ( ruleProjectDependency ) )
            // InternalN4MFParser.g:6474:2: ( ruleProjectDependency )
            {
            // InternalN4MFParser.g:6474:2: ( ruleProjectDependency )
            // InternalN4MFParser.g:6475:3: ruleProjectDependency
            {
             before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectDependency();

            state._fsp--;

             after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__ProjectDependenciesAssignment_3_0"


    // $ANTLR start "rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1"
    // InternalN4MFParser.g:6484:1: rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1 : ( ruleProjectDependency ) ;
    public final void rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6488:1: ( ( ruleProjectDependency ) )
            // InternalN4MFParser.g:6489:2: ( ruleProjectDependency )
            {
            // InternalN4MFParser.g:6489:2: ( ruleProjectDependency )
            // InternalN4MFParser.g:6490:3: ruleProjectDependency
            {
             before(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectDependency();

            state._fsp--;

             after(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0"
    // InternalN4MFParser.g:6499:1: rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0 : ( ruleProvidedRuntimeLibraryDependency ) ;
    public final void rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6503:1: ( ( ruleProvidedRuntimeLibraryDependency ) )
            // InternalN4MFParser.g:6504:2: ( ruleProvidedRuntimeLibraryDependency )
            {
            // InternalN4MFParser.g:6504:2: ( ruleProvidedRuntimeLibraryDependency )
            // InternalN4MFParser.g:6505:3: ruleProvidedRuntimeLibraryDependency
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProvidedRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0"


    // $ANTLR start "rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1"
    // InternalN4MFParser.g:6514:1: rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1 : ( ruleProvidedRuntimeLibraryDependency ) ;
    public final void rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6518:1: ( ( ruleProvidedRuntimeLibraryDependency ) )
            // InternalN4MFParser.g:6519:2: ( ruleProvidedRuntimeLibraryDependency )
            {
            // InternalN4MFParser.g:6519:2: ( ruleProvidedRuntimeLibraryDependency )
            // InternalN4MFParser.g:6520:3: ruleProvidedRuntimeLibraryDependency
            {
             before(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProvidedRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1"


    // $ANTLR start "rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0"
    // InternalN4MFParser.g:6529:1: rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0 : ( ruleRequiredRuntimeLibraryDependency ) ;
    public final void rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6533:1: ( ( ruleRequiredRuntimeLibraryDependency ) )
            // InternalN4MFParser.g:6534:2: ( ruleRequiredRuntimeLibraryDependency )
            {
            // InternalN4MFParser.g:6534:2: ( ruleRequiredRuntimeLibraryDependency )
            // InternalN4MFParser.g:6535:3: ruleRequiredRuntimeLibraryDependency
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_0_0()); 
            pushFollow(FOLLOW_2);
            ruleRequiredRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0"


    // $ANTLR start "rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1"
    // InternalN4MFParser.g:6544:1: rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1 : ( ruleRequiredRuntimeLibraryDependency ) ;
    public final void rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6548:1: ( ( ruleRequiredRuntimeLibraryDependency ) )
            // InternalN4MFParser.g:6549:2: ( ruleRequiredRuntimeLibraryDependency )
            {
            // InternalN4MFParser.g:6549:2: ( ruleRequiredRuntimeLibraryDependency )
            // InternalN4MFParser.g:6550:3: ruleRequiredRuntimeLibraryDependency
            {
             before(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleRequiredRuntimeLibraryDependency();

            state._fsp--;

             after(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1"


    // $ANTLR start "rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3"
    // InternalN4MFParser.g:6559:1: rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3 : ( ruleProjectReference ) ;
    public final void rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6563:1: ( ( ruleProjectReference ) )
            // InternalN4MFParser.g:6564:2: ( ruleProjectReference )
            {
            // InternalN4MFParser.g:6564:2: ( ruleProjectReference )
            // InternalN4MFParser.g:6565:3: ruleProjectReference
            {
             before(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectReference();

            state._fsp--;

             after(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3"


    // $ANTLR start "rule__DeclaredVersion__MajorAssignment_0"
    // InternalN4MFParser.g:6574:1: rule__DeclaredVersion__MajorAssignment_0 : ( RULE_INT ) ;
    public final void rule__DeclaredVersion__MajorAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6578:1: ( ( RULE_INT ) )
            // InternalN4MFParser.g:6579:2: ( RULE_INT )
            {
            // InternalN4MFParser.g:6579:2: ( RULE_INT )
            // InternalN4MFParser.g:6580:3: RULE_INT
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
    // InternalN4MFParser.g:6589:1: rule__DeclaredVersion__MinorAssignment_1_1 : ( RULE_INT ) ;
    public final void rule__DeclaredVersion__MinorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6593:1: ( ( RULE_INT ) )
            // InternalN4MFParser.g:6594:2: ( RULE_INT )
            {
            // InternalN4MFParser.g:6594:2: ( RULE_INT )
            // InternalN4MFParser.g:6595:3: RULE_INT
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
    // InternalN4MFParser.g:6604:1: rule__DeclaredVersion__MicroAssignment_1_2_1 : ( RULE_INT ) ;
    public final void rule__DeclaredVersion__MicroAssignment_1_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6608:1: ( ( RULE_INT ) )
            // InternalN4MFParser.g:6609:2: ( RULE_INT )
            {
            // InternalN4MFParser.g:6609:2: ( RULE_INT )
            // InternalN4MFParser.g:6610:3: RULE_INT
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
    // InternalN4MFParser.g:6619:1: rule__DeclaredVersion__QualifierAssignment_2_1 : ( ruleN4mfIdentifier ) ;
    public final void rule__DeclaredVersion__QualifierAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6623:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6624:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6624:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6625:3: ruleN4mfIdentifier
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
    // InternalN4MFParser.g:6634:1: rule__SourceFragment__SourceFragmentTypeAssignment_0 : ( ruleSourceFragmentType ) ;
    public final void rule__SourceFragment__SourceFragmentTypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6638:1: ( ( ruleSourceFragmentType ) )
            // InternalN4MFParser.g:6639:2: ( ruleSourceFragmentType )
            {
            // InternalN4MFParser.g:6639:2: ( ruleSourceFragmentType )
            // InternalN4MFParser.g:6640:3: ruleSourceFragmentType
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


    // $ANTLR start "rule__SourceFragment__PathsAssignment_2"
    // InternalN4MFParser.g:6649:1: rule__SourceFragment__PathsAssignment_2 : ( RULE_STRING ) ;
    public final void rule__SourceFragment__PathsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6653:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6654:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6654:2: ( RULE_STRING )
            // InternalN4MFParser.g:6655:3: RULE_STRING
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsSTRINGTerminalRuleCall_2_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getSourceFragmentAccess().getPathsSTRINGTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__PathsAssignment_2"


    // $ANTLR start "rule__SourceFragment__PathsAssignment_3_1"
    // InternalN4MFParser.g:6664:1: rule__SourceFragment__PathsAssignment_3_1 : ( RULE_STRING ) ;
    public final void rule__SourceFragment__PathsAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6668:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6669:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6669:2: ( RULE_STRING )
            // InternalN4MFParser.g:6670:3: RULE_STRING
            {
             before(grammarAccess.getSourceFragmentAccess().getPathsSTRINGTerminalRuleCall_3_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getSourceFragmentAccess().getPathsSTRINGTerminalRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SourceFragment__PathsAssignment_3_1"


    // $ANTLR start "rule__ModuleFilter__ModuleFilterTypeAssignment_0"
    // InternalN4MFParser.g:6679:1: rule__ModuleFilter__ModuleFilterTypeAssignment_0 : ( ruleModuleFilterType ) ;
    public final void rule__ModuleFilter__ModuleFilterTypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6683:1: ( ( ruleModuleFilterType ) )
            // InternalN4MFParser.g:6684:2: ( ruleModuleFilterType )
            {
            // InternalN4MFParser.g:6684:2: ( ruleModuleFilterType )
            // InternalN4MFParser.g:6685:3: ruleModuleFilterType
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
    // InternalN4MFParser.g:6694:1: rule__ModuleFilter__ModuleSpecifiersAssignment_2 : ( ruleModuleFilterSpecifier ) ;
    public final void rule__ModuleFilter__ModuleSpecifiersAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6698:1: ( ( ruleModuleFilterSpecifier ) )
            // InternalN4MFParser.g:6699:2: ( ruleModuleFilterSpecifier )
            {
            // InternalN4MFParser.g:6699:2: ( ruleModuleFilterSpecifier )
            // InternalN4MFParser.g:6700:3: ruleModuleFilterSpecifier
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
    // InternalN4MFParser.g:6709:1: rule__ModuleFilter__ModuleSpecifiersAssignment_3_1 : ( ruleModuleFilterSpecifier ) ;
    public final void rule__ModuleFilter__ModuleSpecifiersAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6713:1: ( ( ruleModuleFilterSpecifier ) )
            // InternalN4MFParser.g:6714:2: ( ruleModuleFilterSpecifier )
            {
            // InternalN4MFParser.g:6714:2: ( ruleModuleFilterSpecifier )
            // InternalN4MFParser.g:6715:3: ruleModuleFilterSpecifier
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
    // InternalN4MFParser.g:6724:1: rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0 : ( RULE_STRING ) ;
    public final void rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6728:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6729:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6729:2: ( RULE_STRING )
            // InternalN4MFParser.g:6730:3: RULE_STRING
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
    // InternalN4MFParser.g:6739:1: rule__BootstrapModule__SourcePathAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__BootstrapModule__SourcePathAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6743:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6744:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6744:2: ( RULE_STRING )
            // InternalN4MFParser.g:6745:3: RULE_STRING
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
    // InternalN4MFParser.g:6754:1: rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0 : ( RULE_STRING ) ;
    public final void rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6758:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6759:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6759:2: ( RULE_STRING )
            // InternalN4MFParser.g:6760:3: RULE_STRING
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
    // InternalN4MFParser.g:6769:1: rule__ModuleFilterSpecifier__SourcePathAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__ModuleFilterSpecifier__SourcePathAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6773:1: ( ( RULE_STRING ) )
            // InternalN4MFParser.g:6774:2: ( RULE_STRING )
            {
            // InternalN4MFParser.g:6774:2: ( RULE_STRING )
            // InternalN4MFParser.g:6775:3: RULE_STRING
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


    // $ANTLR start "rule__ProvidedRuntimeLibraryDependency__ProjectAssignment"
    // InternalN4MFParser.g:6784:1: rule__ProvidedRuntimeLibraryDependency__ProjectAssignment : ( ruleSimpleProjectDescription ) ;
    public final void rule__ProvidedRuntimeLibraryDependency__ProjectAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6788:1: ( ( ruleSimpleProjectDescription ) )
            // InternalN4MFParser.g:6789:2: ( ruleSimpleProjectDescription )
            {
            // InternalN4MFParser.g:6789:2: ( ruleSimpleProjectDescription )
            // InternalN4MFParser.g:6790:3: ruleSimpleProjectDescription
            {
             before(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleSimpleProjectDescription();

            state._fsp--;

             after(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProvidedRuntimeLibraryDependency__ProjectAssignment"


    // $ANTLR start "rule__RequiredRuntimeLibraryDependency__ProjectAssignment"
    // InternalN4MFParser.g:6799:1: rule__RequiredRuntimeLibraryDependency__ProjectAssignment : ( ruleSimpleProjectDescription ) ;
    public final void rule__RequiredRuntimeLibraryDependency__ProjectAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6803:1: ( ( ruleSimpleProjectDescription ) )
            // InternalN4MFParser.g:6804:2: ( ruleSimpleProjectDescription )
            {
            // InternalN4MFParser.g:6804:2: ( ruleSimpleProjectDescription )
            // InternalN4MFParser.g:6805:3: ruleSimpleProjectDescription
            {
             before(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleSimpleProjectDescription();

            state._fsp--;

             after(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RequiredRuntimeLibraryDependency__ProjectAssignment"


    // $ANTLR start "rule__TestedProject__ProjectAssignment"
    // InternalN4MFParser.g:6814:1: rule__TestedProject__ProjectAssignment : ( ruleSimpleProjectDescription ) ;
    public final void rule__TestedProject__ProjectAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6818:1: ( ( ruleSimpleProjectDescription ) )
            // InternalN4MFParser.g:6819:2: ( ruleSimpleProjectDescription )
            {
            // InternalN4MFParser.g:6819:2: ( ruleSimpleProjectDescription )
            // InternalN4MFParser.g:6820:3: ruleSimpleProjectDescription
            {
             before(grammarAccess.getTestedProjectAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleSimpleProjectDescription();

            state._fsp--;

             after(grammarAccess.getTestedProjectAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TestedProject__ProjectAssignment"


    // $ANTLR start "rule__ProjectReference__ProjectAssignment"
    // InternalN4MFParser.g:6829:1: rule__ProjectReference__ProjectAssignment : ( ruleSimpleProjectDescription ) ;
    public final void rule__ProjectReference__ProjectAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6833:1: ( ( ruleSimpleProjectDescription ) )
            // InternalN4MFParser.g:6834:2: ( ruleSimpleProjectDescription )
            {
            // InternalN4MFParser.g:6834:2: ( ruleSimpleProjectDescription )
            // InternalN4MFParser.g:6835:3: ruleSimpleProjectDescription
            {
             before(grammarAccess.getProjectReferenceAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleSimpleProjectDescription();

            state._fsp--;

             after(grammarAccess.getProjectReferenceAccess().getProjectSimpleProjectDescriptionParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectReference__ProjectAssignment"


    // $ANTLR start "rule__ProjectDependency__ProjectAssignment_0"
    // InternalN4MFParser.g:6844:1: rule__ProjectDependency__ProjectAssignment_0 : ( ruleSimpleProjectDescription ) ;
    public final void rule__ProjectDependency__ProjectAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6848:1: ( ( ruleSimpleProjectDescription ) )
            // InternalN4MFParser.g:6849:2: ( ruleSimpleProjectDescription )
            {
            // InternalN4MFParser.g:6849:2: ( ruleSimpleProjectDescription )
            // InternalN4MFParser.g:6850:3: ruleSimpleProjectDescription
            {
             before(grammarAccess.getProjectDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleSimpleProjectDescription();

            state._fsp--;

             after(grammarAccess.getProjectDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectDependency__ProjectAssignment_0"


    // $ANTLR start "rule__ProjectDependency__VersionConstraintAssignment_1"
    // InternalN4MFParser.g:6859:1: rule__ProjectDependency__VersionConstraintAssignment_1 : ( ruleVersionConstraint ) ;
    public final void rule__ProjectDependency__VersionConstraintAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6863:1: ( ( ruleVersionConstraint ) )
            // InternalN4MFParser.g:6864:2: ( ruleVersionConstraint )
            {
            // InternalN4MFParser.g:6864:2: ( ruleVersionConstraint )
            // InternalN4MFParser.g:6865:3: ruleVersionConstraint
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
    // InternalN4MFParser.g:6874:1: rule__ProjectDependency__DeclaredScopeAssignment_2 : ( ruleProjectDependencyScope ) ;
    public final void rule__ProjectDependency__DeclaredScopeAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6878:1: ( ( ruleProjectDependencyScope ) )
            // InternalN4MFParser.g:6879:2: ( ruleProjectDependencyScope )
            {
            // InternalN4MFParser.g:6879:2: ( ruleProjectDependencyScope )
            // InternalN4MFParser.g:6880:3: ruleProjectDependencyScope
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


    // $ANTLR start "rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0"
    // InternalN4MFParser.g:6889:1: rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0 : ( ruleN4mfIdentifier ) ;
    public final void rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6893:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6894:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6894:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6895:3: ruleN4mfIdentifier
            {
             before(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0()); 
            pushFollow(FOLLOW_2);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0"


    // $ANTLR start "rule__SimpleProjectDescription__ProjectIdAssignment_1"
    // InternalN4MFParser.g:6904:1: rule__SimpleProjectDescription__ProjectIdAssignment_1 : ( ruleN4mfIdentifier ) ;
    public final void rule__SimpleProjectDescription__ProjectIdAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6908:1: ( ( ruleN4mfIdentifier ) )
            // InternalN4MFParser.g:6909:2: ( ruleN4mfIdentifier )
            {
            // InternalN4MFParser.g:6909:2: ( ruleN4mfIdentifier )
            // InternalN4MFParser.g:6910:3: ruleN4mfIdentifier
            {
             before(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleN4mfIdentifier();

            state._fsp--;

             after(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SimpleProjectDescription__ProjectIdAssignment_1"


    // $ANTLR start "rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0"
    // InternalN4MFParser.g:6919:1: rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0 : ( ( LeftParenthesis ) ) ;
    public final void rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6923:1: ( ( ( LeftParenthesis ) ) )
            // InternalN4MFParser.g:6924:2: ( ( LeftParenthesis ) )
            {
            // InternalN4MFParser.g:6924:2: ( ( LeftParenthesis ) )
            // InternalN4MFParser.g:6925:3: ( LeftParenthesis )
            {
             before(grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0()); 
            // InternalN4MFParser.g:6926:3: ( LeftParenthesis )
            // InternalN4MFParser.g:6927:4: LeftParenthesis
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
    // InternalN4MFParser.g:6938:1: rule__VersionConstraint__LowerVersionAssignment_0_1 : ( ruleDeclaredVersion ) ;
    public final void rule__VersionConstraint__LowerVersionAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6942:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6943:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6943:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6944:3: ruleDeclaredVersion
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
    // InternalN4MFParser.g:6953:1: rule__VersionConstraint__UpperVersionAssignment_0_2_0_1 : ( ruleDeclaredVersion ) ;
    public final void rule__VersionConstraint__UpperVersionAssignment_0_2_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6957:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6958:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6958:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6959:3: ruleDeclaredVersion
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
    // InternalN4MFParser.g:6968:1: rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0 : ( ( RightParenthesis ) ) ;
    public final void rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6972:1: ( ( ( RightParenthesis ) ) )
            // InternalN4MFParser.g:6973:2: ( ( RightParenthesis ) )
            {
            // InternalN4MFParser.g:6973:2: ( ( RightParenthesis ) )
            // InternalN4MFParser.g:6974:3: ( RightParenthesis )
            {
             before(grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0()); 
            // InternalN4MFParser.g:6975:3: ( RightParenthesis )
            // InternalN4MFParser.g:6976:4: RightParenthesis
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
    // InternalN4MFParser.g:6987:1: rule__VersionConstraint__LowerVersionAssignment_1 : ( ruleDeclaredVersion ) ;
    public final void rule__VersionConstraint__LowerVersionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalN4MFParser.g:6991:1: ( ( ruleDeclaredVersion ) )
            // InternalN4MFParser.g:6992:2: ( ruleDeclaredVersion )
            {
            // InternalN4MFParser.g:6992:2: ( ruleDeclaredVersion )
            // InternalN4MFParser.g:6993:3: ruleDeclaredVersion
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
    static final String dfa_3s = "\1\10\13\4\1\46\3\4\1\45\2\4\2\uffff\2\4";
    static final String dfa_4s = "\1\66\13\67\1\46\3\67\1\45\2\67\2\uffff\2\67";
    static final String dfa_5s = "\23\uffff\1\1\1\2\2\uffff";
    static final String dfa_6s = "\27\uffff}>";
    static final String[] dfa_7s = {
            "\1\14\2\uffff\1\4\2\uffff\1\13\4\uffff\1\3\1\17\2\uffff\1\6\1\uffff\1\10\1\2\1\11\1\20\1\5\2\uffff\1\12\1\uffff\1\21\1\uffff\1\7\3\uffff\1\22\1\16\1\15\13\uffff\1\1",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\1\25",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\1\26",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "",
            "",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24",
            "\5\24\1\uffff\3\24\1\uffff\2\24\2\uffff\2\24\1\uffff\3\24\1\uffff\3\24\1\uffff\1\24\2\uffff\2\24\2\uffff\1\24\3\uffff\1\24\3\uffff\1\24\1\uffff\1\24\2\uffff\1\23\1\24\2\uffff\1\24\1\uffff\1\24"
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
            return "5064:2: ( rule__SimpleProjectDescription__Group_0__0 )?";
        }
    }
    static final String dfa_8s = "\26\uffff";
    static final String dfa_9s = "\1\4\25\uffff";
    static final String dfa_10s = "\1\44\25\uffff";
    static final String dfa_11s = "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25";
    static final String dfa_12s = "\1\0\25\uffff}>";
    static final String[] dfa_13s = {
            "\1\7\1\10\1\11\1\14\1\12\1\uffff\1\13\1\3\1\24\1\uffff\1\23\1\25\2\uffff\1\15\1\2\1\uffff\1\16\1\6\1\5\1\uffff\1\20\1\1\1\21\1\uffff\1\4\2\uffff\1\22\3\uffff\1\17",
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
            return "5443:3: ( ({...}? => ( ( ( rule__ProjectDescription__Group_0__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_1__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_2__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_3__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_4__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_5__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ProjectDependenciesAssignment_9 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_10__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ImplementedProjectsAssignment_11 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__InitModulesAssignment_12 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__ExecModuleAssignment_13 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_14__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_15__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_16__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_17__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_18__0 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__TestedProjectsAssignment_19 ) ) ) ) | ({...}? => ( ( ( rule__ProjectDescription__Group_20__0 ) ) ) ) )";
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
    static final String dfa_16s = "\1\44\26\uffff";
    static final String dfa_17s = "\1\uffff\25\1\1\2";
    static final String dfa_18s = "\1\0\26\uffff}>";
    static final String[] dfa_19s = {
            "\1\7\1\10\1\11\1\14\1\12\1\uffff\1\13\1\3\1\24\1\uffff\1\23\1\25\2\uffff\1\15\1\2\1\uffff\1\16\1\6\1\5\1\uffff\1\20\1\1\1\21\1\uffff\1\4\2\uffff\1\22\3\uffff\1\17",
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
            return "5773:2: ( rule__ProjectDescription__UnorderedGroup__1 )?";
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
            return "5785:2: ( rule__ProjectDescription__UnorderedGroup__2 )?";
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
            return "5797:2: ( rule__ProjectDescription__UnorderedGroup__3 )?";
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
            return "5809:2: ( rule__ProjectDescription__UnorderedGroup__4 )?";
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
            return "5821:2: ( rule__ProjectDescription__UnorderedGroup__5 )?";
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
            return "5833:2: ( rule__ProjectDescription__UnorderedGroup__6 )?";
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
            return "5845:2: ( rule__ProjectDescription__UnorderedGroup__7 )?";
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
            return "5857:2: ( rule__ProjectDescription__UnorderedGroup__8 )?";
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
            return "5869:2: ( rule__ProjectDescription__UnorderedGroup__9 )?";
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
            return "5881:2: ( rule__ProjectDescription__UnorderedGroup__10 )?";
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
            return "5893:2: ( rule__ProjectDescription__UnorderedGroup__11 )?";
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
            return "5905:2: ( rule__ProjectDescription__UnorderedGroup__12 )?";
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
            return "5917:2: ( rule__ProjectDescription__UnorderedGroup__13 )?";
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
            return "5929:2: ( rule__ProjectDescription__UnorderedGroup__14 )?";
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
            return "5941:2: ( rule__ProjectDescription__UnorderedGroup__15 )?";
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
            return "5953:2: ( rule__ProjectDescription__UnorderedGroup__16 )?";
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
            return "5965:2: ( rule__ProjectDescription__UnorderedGroup__17 )?";
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
            return "5977:2: ( rule__ProjectDescription__UnorderedGroup__18 )?";
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
            return "5989:2: ( rule__ProjectDescription__UnorderedGroup__19 )?";
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
            return "6001:2: ( rule__ProjectDescription__UnorderedGroup__20 )?";
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
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x004007153E984900L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000050810102200L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0020400000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000012080000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000012080000002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000001010000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000001010002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000008040020000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x006007153E984900L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0120000000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0001800000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0084110200000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0008200000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x000000112EECDDF2L});

}
