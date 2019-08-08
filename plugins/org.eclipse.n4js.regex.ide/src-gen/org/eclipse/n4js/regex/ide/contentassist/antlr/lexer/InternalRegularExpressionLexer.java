package org.eclipse.n4js.regex.ide.contentassist.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


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
public class InternalRegularExpressionLexer extends Lexer {
    public static final int RULE_WHITESPACE_FRAGMENT=44;
    public static final int LeftParenthesis=6;
    public static final int RULE_HEX_ESCAPE=29;
    public static final int RightSquareBracket=18;
    public static final int ExclamationMark=4;
    public static final int RULE_CONTROL_LETTER_ESCAPE=27;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=46;
    public static final int RightParenthesis=7;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=49;
    public static final int RULE_ZWNJ=41;
    public static final int VerticalLine=21;
    public static final int PlusSign=9;
    public static final int LeftSquareBracket=17;
    public static final int RULE_DECIMAL_ESCAPE=32;
    public static final int RULE_ML_COMMENT_FRAGMENT=48;
    public static final int RULE_PATTERN_CHARACTER_NO_DASH=38;
    public static final int RULE_WORD_BOUNDARY=23;
    public static final int RULE_UNICODE_ESCAPE=30;
    public static final int Comma=10;
    public static final int EqualsSign=15;
    public static final int RULE_ZWJ=40;
    public static final int RULE_SL_COMMENT_FRAGMENT=47;
    public static final int HyphenMinus=11;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=34;
    public static final int RULE_UNICODE_LETTER=37;
    public static final int RULE_CONTROL_ESCAPE=26;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=43;
    public static final int Solidus=13;
    public static final int Colon=14;
    public static final int RightCurlyBracket=22;
    public static final int RULE_CHARACTER_CLASS_ESCAPE=25;
    public static final int EOF=-1;
    public static final int Asterisk=8;
    public static final int FullStop=12;
    public static final int RULE_UNICODE_DIGIT=35;
    public static final int RULE_BOM=42;
    public static final int LeftCurlyBracket=20;
    public static final int RULE_ANY_OTHER=51;
    public static final int CircumflexAccent=19;
    public static final int RULE_NOT_WORD_BOUNDARY=24;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=45;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=36;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=39;
    public static final int QuestionMark=16;
    public static final int DollarSign=5;
    public static final int RULE_HEX_DIGIT=28;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=31;
    public static final int RULE_IDENTITY_ESCAPE=33;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=50;

    // delegates
    // delegators

    public InternalRegularExpressionLexer() {;} 
    public InternalRegularExpressionLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalRegularExpressionLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalRegularExpressionLexer.g"; }

    // $ANTLR start "ExclamationMark"
    public final void mExclamationMark() throws RecognitionException {
        try {
            int _type = ExclamationMark;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:21:17: ( '!' )
            // InternalRegularExpressionLexer.g:21:19: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ExclamationMark"

    // $ANTLR start "DollarSign"
    public final void mDollarSign() throws RecognitionException {
        try {
            int _type = DollarSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:23:12: ( '$' )
            // InternalRegularExpressionLexer.g:23:14: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DollarSign"

    // $ANTLR start "LeftParenthesis"
    public final void mLeftParenthesis() throws RecognitionException {
        try {
            int _type = LeftParenthesis;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:25:17: ( '(' )
            // InternalRegularExpressionLexer.g:25:19: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LeftParenthesis"

    // $ANTLR start "RightParenthesis"
    public final void mRightParenthesis() throws RecognitionException {
        try {
            int _type = RightParenthesis;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:27:18: ( ')' )
            // InternalRegularExpressionLexer.g:27:20: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RightParenthesis"

    // $ANTLR start "Asterisk"
    public final void mAsterisk() throws RecognitionException {
        try {
            int _type = Asterisk;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:29:10: ( '*' )
            // InternalRegularExpressionLexer.g:29:12: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Asterisk"

    // $ANTLR start "PlusSign"
    public final void mPlusSign() throws RecognitionException {
        try {
            int _type = PlusSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:31:10: ( '+' )
            // InternalRegularExpressionLexer.g:31:12: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PlusSign"

    // $ANTLR start "Comma"
    public final void mComma() throws RecognitionException {
        try {
            int _type = Comma;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:33:7: ( ',' )
            // InternalRegularExpressionLexer.g:33:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Comma"

    // $ANTLR start "HyphenMinus"
    public final void mHyphenMinus() throws RecognitionException {
        try {
            int _type = HyphenMinus;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:35:13: ( '-' )
            // InternalRegularExpressionLexer.g:35:15: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HyphenMinus"

    // $ANTLR start "FullStop"
    public final void mFullStop() throws RecognitionException {
        try {
            int _type = FullStop;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:37:10: ( '.' )
            // InternalRegularExpressionLexer.g:37:12: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FullStop"

    // $ANTLR start "Solidus"
    public final void mSolidus() throws RecognitionException {
        try {
            int _type = Solidus;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:39:9: ( '/' )
            // InternalRegularExpressionLexer.g:39:11: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Solidus"

    // $ANTLR start "Colon"
    public final void mColon() throws RecognitionException {
        try {
            int _type = Colon;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:41:7: ( ':' )
            // InternalRegularExpressionLexer.g:41:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Colon"

    // $ANTLR start "EqualsSign"
    public final void mEqualsSign() throws RecognitionException {
        try {
            int _type = EqualsSign;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:43:12: ( '=' )
            // InternalRegularExpressionLexer.g:43:14: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EqualsSign"

    // $ANTLR start "QuestionMark"
    public final void mQuestionMark() throws RecognitionException {
        try {
            int _type = QuestionMark;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:45:14: ( '?' )
            // InternalRegularExpressionLexer.g:45:16: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QuestionMark"

    // $ANTLR start "LeftSquareBracket"
    public final void mLeftSquareBracket() throws RecognitionException {
        try {
            int _type = LeftSquareBracket;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:47:19: ( '[' )
            // InternalRegularExpressionLexer.g:47:21: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LeftSquareBracket"

    // $ANTLR start "RightSquareBracket"
    public final void mRightSquareBracket() throws RecognitionException {
        try {
            int _type = RightSquareBracket;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:49:20: ( ']' )
            // InternalRegularExpressionLexer.g:49:22: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RightSquareBracket"

    // $ANTLR start "CircumflexAccent"
    public final void mCircumflexAccent() throws RecognitionException {
        try {
            int _type = CircumflexAccent;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:51:18: ( '^' )
            // InternalRegularExpressionLexer.g:51:20: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CircumflexAccent"

    // $ANTLR start "LeftCurlyBracket"
    public final void mLeftCurlyBracket() throws RecognitionException {
        try {
            int _type = LeftCurlyBracket;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:53:18: ( '{' )
            // InternalRegularExpressionLexer.g:53:20: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LeftCurlyBracket"

    // $ANTLR start "VerticalLine"
    public final void mVerticalLine() throws RecognitionException {
        try {
            int _type = VerticalLine;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:55:14: ( '|' )
            // InternalRegularExpressionLexer.g:55:16: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VerticalLine"

    // $ANTLR start "RightCurlyBracket"
    public final void mRightCurlyBracket() throws RecognitionException {
        try {
            int _type = RightCurlyBracket;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:57:19: ( '}' )
            // InternalRegularExpressionLexer.g:57:21: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RightCurlyBracket"

    // $ANTLR start "RULE_WORD_BOUNDARY"
    public final void mRULE_WORD_BOUNDARY() throws RecognitionException {
        try {
            int _type = RULE_WORD_BOUNDARY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:59:20: ( '\\\\' 'b' )
            // InternalRegularExpressionLexer.g:59:22: '\\\\' 'b'
            {
            match('\\'); 
            match('b'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WORD_BOUNDARY"

    // $ANTLR start "RULE_NOT_WORD_BOUNDARY"
    public final void mRULE_NOT_WORD_BOUNDARY() throws RecognitionException {
        try {
            int _type = RULE_NOT_WORD_BOUNDARY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:61:24: ( '\\\\' 'B' )
            // InternalRegularExpressionLexer.g:61:26: '\\\\' 'B'
            {
            match('\\'); 
            match('B'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_NOT_WORD_BOUNDARY"

    // $ANTLR start "RULE_CHARACTER_CLASS_ESCAPE"
    public final void mRULE_CHARACTER_CLASS_ESCAPE() throws RecognitionException {
        try {
            int _type = RULE_CHARACTER_CLASS_ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:63:29: ( '\\\\' ( 'd' | 'D' | 's' | 'S' | 'w' | 'W' ) )
            // InternalRegularExpressionLexer.g:63:31: '\\\\' ( 'd' | 'D' | 's' | 'S' | 'w' | 'W' )
            {
            match('\\'); 
            if ( input.LA(1)=='D'||input.LA(1)=='S'||input.LA(1)=='W'||input.LA(1)=='d'||input.LA(1)=='s'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_CHARACTER_CLASS_ESCAPE"

    // $ANTLR start "RULE_CONTROL_ESCAPE"
    public final void mRULE_CONTROL_ESCAPE() throws RecognitionException {
        try {
            int _type = RULE_CONTROL_ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:65:21: ( '\\\\' ( 'f' | 'n' | 'r' | 't' | 'v' ) )
            // InternalRegularExpressionLexer.g:65:23: '\\\\' ( 'f' | 'n' | 'r' | 't' | 'v' )
            {
            match('\\'); 
            if ( input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_CONTROL_ESCAPE"

    // $ANTLR start "RULE_CONTROL_LETTER_ESCAPE"
    public final void mRULE_CONTROL_LETTER_ESCAPE() throws RecognitionException {
        try {
            int _type = RULE_CONTROL_LETTER_ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:67:28: ( '\\\\' 'c' ( 'a' .. 'z' | 'A' .. 'Z' )? )
            // InternalRegularExpressionLexer.g:67:30: '\\\\' 'c' ( 'a' .. 'z' | 'A' .. 'Z' )?
            {
            match('\\'); 
            match('c'); 
            // InternalRegularExpressionLexer.g:67:39: ( 'a' .. 'z' | 'A' .. 'Z' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>='A' && LA1_0<='Z')||(LA1_0>='a' && LA1_0<='z')) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalRegularExpressionLexer.g:
                    {
                    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_CONTROL_LETTER_ESCAPE"

    // $ANTLR start "RULE_HEX_ESCAPE"
    public final void mRULE_HEX_ESCAPE() throws RecognitionException {
        try {
            int _type = RULE_HEX_ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:69:17: ( '\\\\' 'x' ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )? )
            // InternalRegularExpressionLexer.g:69:19: '\\\\' 'x' ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )?
            {
            match('\\'); 
            match('x'); 
            // InternalRegularExpressionLexer.g:69:28: ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='F')||(LA3_0>='a' && LA3_0<='f')) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalRegularExpressionLexer.g:69:29: RULE_HEX_DIGIT ( RULE_HEX_DIGIT )?
                    {
                    mRULE_HEX_DIGIT(); 
                    // InternalRegularExpressionLexer.g:69:44: ( RULE_HEX_DIGIT )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( ((LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='F')||(LA2_0>='a' && LA2_0<='f')) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // InternalRegularExpressionLexer.g:69:44: RULE_HEX_DIGIT
                            {
                            mRULE_HEX_DIGIT(); 

                            }
                            break;

                    }


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_HEX_ESCAPE"

    // $ANTLR start "RULE_UNICODE_ESCAPE"
    public final void mRULE_UNICODE_ESCAPE() throws RecognitionException {
        try {
            int _type = RULE_UNICODE_ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:71:21: ( '\\\\' ( 'u' ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )? )? | '{' ( RULE_HEX_DIGIT )* ( '}' )? )? )? )
            // InternalRegularExpressionLexer.g:71:23: '\\\\' ( 'u' ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )? )? | '{' ( RULE_HEX_DIGIT )* ( '}' )? )? )?
            {
            match('\\'); 
            // InternalRegularExpressionLexer.g:71:28: ( 'u' ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )? )? | '{' ( RULE_HEX_DIGIT )* ( '}' )? )? )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='u') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalRegularExpressionLexer.g:71:29: 'u' ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )? )? | '{' ( RULE_HEX_DIGIT )* ( '}' )? )?
                    {
                    match('u'); 
                    // InternalRegularExpressionLexer.g:71:33: ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )? )? | '{' ( RULE_HEX_DIGIT )* ( '}' )? )?
                    int alt9=3;
                    int LA9_0 = input.LA(1);

                    if ( ((LA9_0>='0' && LA9_0<='9')||(LA9_0>='A' && LA9_0<='F')||(LA9_0>='a' && LA9_0<='f')) ) {
                        alt9=1;
                    }
                    else if ( (LA9_0=='{') ) {
                        alt9=2;
                    }
                    switch (alt9) {
                        case 1 :
                            // InternalRegularExpressionLexer.g:71:34: RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )? )?
                            {
                            mRULE_HEX_DIGIT(); 
                            // InternalRegularExpressionLexer.g:71:49: ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )? )?
                            int alt6=2;
                            int LA6_0 = input.LA(1);

                            if ( ((LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='F')||(LA6_0>='a' && LA6_0<='f')) ) {
                                alt6=1;
                            }
                            switch (alt6) {
                                case 1 :
                                    // InternalRegularExpressionLexer.g:71:50: RULE_HEX_DIGIT ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )?
                                    {
                                    mRULE_HEX_DIGIT(); 
                                    // InternalRegularExpressionLexer.g:71:65: ( RULE_HEX_DIGIT ( RULE_HEX_DIGIT )? )?
                                    int alt5=2;
                                    int LA5_0 = input.LA(1);

                                    if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='F')||(LA5_0>='a' && LA5_0<='f')) ) {
                                        alt5=1;
                                    }
                                    switch (alt5) {
                                        case 1 :
                                            // InternalRegularExpressionLexer.g:71:66: RULE_HEX_DIGIT ( RULE_HEX_DIGIT )?
                                            {
                                            mRULE_HEX_DIGIT(); 
                                            // InternalRegularExpressionLexer.g:71:81: ( RULE_HEX_DIGIT )?
                                            int alt4=2;
                                            int LA4_0 = input.LA(1);

                                            if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='F')||(LA4_0>='a' && LA4_0<='f')) ) {
                                                alt4=1;
                                            }
                                            switch (alt4) {
                                                case 1 :
                                                    // InternalRegularExpressionLexer.g:71:81: RULE_HEX_DIGIT
                                                    {
                                                    mRULE_HEX_DIGIT(); 

                                                    }
                                                    break;

                                            }


                                            }
                                            break;

                                    }


                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // InternalRegularExpressionLexer.g:71:101: '{' ( RULE_HEX_DIGIT )* ( '}' )?
                            {
                            match('{'); 
                            // InternalRegularExpressionLexer.g:71:105: ( RULE_HEX_DIGIT )*
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( ((LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='F')||(LA7_0>='a' && LA7_0<='f')) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // InternalRegularExpressionLexer.g:71:105: RULE_HEX_DIGIT
                            	    {
                            	    mRULE_HEX_DIGIT(); 

                            	    }
                            	    break;

                            	default :
                            	    break loop7;
                                }
                            } while (true);

                            // InternalRegularExpressionLexer.g:71:121: ( '}' )?
                            int alt8=2;
                            int LA8_0 = input.LA(1);

                            if ( (LA8_0=='}') ) {
                                alt8=1;
                            }
                            switch (alt8) {
                                case 1 :
                                    // InternalRegularExpressionLexer.g:71:121: '}'
                                    {
                                    match('}'); 

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_UNICODE_ESCAPE"

    // $ANTLR start "RULE_DECIMAL_ESCAPE"
    public final void mRULE_DECIMAL_ESCAPE() throws RecognitionException {
        try {
            int _type = RULE_DECIMAL_ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:73:21: ( '\\\\' RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT )
            // InternalRegularExpressionLexer.g:73:23: '\\\\' RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT
            {
            match('\\'); 
            mRULE_DECIMAL_INTEGER_LITERAL_FRAGMENT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_DECIMAL_ESCAPE"

    // $ANTLR start "RULE_IDENTITY_ESCAPE"
    public final void mRULE_IDENTITY_ESCAPE() throws RecognitionException {
        try {
            int _type = RULE_IDENTITY_ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:75:22: ( '\\\\' . )
            // InternalRegularExpressionLexer.g:75:24: '\\\\' .
            {
            match('\\'); 
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_IDENTITY_ESCAPE"

    // $ANTLR start "RULE_UNICODE_DIGIT"
    public final void mRULE_UNICODE_DIGIT() throws RecognitionException {
        try {
            int _type = RULE_UNICODE_DIGIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:77:20: ( RULE_UNICODE_DIGIT_FRAGMENT )
            // InternalRegularExpressionLexer.g:77:22: RULE_UNICODE_DIGIT_FRAGMENT
            {
            mRULE_UNICODE_DIGIT_FRAGMENT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_UNICODE_DIGIT"

    // $ANTLR start "RULE_UNICODE_LETTER"
    public final void mRULE_UNICODE_LETTER() throws RecognitionException {
        try {
            int _type = RULE_UNICODE_LETTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:79:21: ( RULE_UNICODE_LETTER_FRAGMENT )
            // InternalRegularExpressionLexer.g:79:23: RULE_UNICODE_LETTER_FRAGMENT
            {
            mRULE_UNICODE_LETTER_FRAGMENT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_UNICODE_LETTER"

    // $ANTLR start "RULE_PATTERN_CHARACTER_NO_DASH"
    public final void mRULE_PATTERN_CHARACTER_NO_DASH() throws RecognitionException {
        try {
            int _type = RULE_PATTERN_CHARACTER_NO_DASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalRegularExpressionLexer.g:81:32: (~ ( ( '^' | '$' | '\\\\' | '.' | '*' | '+' | '?' | '(' | ')' | '[' | ']' | '{' | '}' | '|' | '-' ) ) )
            // InternalRegularExpressionLexer.g:81:34: ~ ( ( '^' | '$' | '\\\\' | '.' | '*' | '+' | '?' | '(' | ')' | '[' | ']' | '{' | '}' | '|' | '-' ) )
            {
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='#')||(input.LA(1)>='%' && input.LA(1)<='\'')||input.LA(1)==','||(input.LA(1)>='/' && input.LA(1)<='>')||(input.LA(1)>='@' && input.LA(1)<='Z')||(input.LA(1)>='_' && input.LA(1)<='z')||(input.LA(1)>='~' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_PATTERN_CHARACTER_NO_DASH"

    // $ANTLR start "RULE_HEX_DIGIT"
    public final void mRULE_HEX_DIGIT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:83:25: ( ( RULE_DECIMAL_DIGIT_FRAGMENT | 'a' .. 'f' | 'A' .. 'F' ) )
            // InternalRegularExpressionLexer.g:83:27: ( RULE_DECIMAL_DIGIT_FRAGMENT | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_HEX_DIGIT"

    // $ANTLR start "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT"
    public final void mRULE_DECIMAL_INTEGER_LITERAL_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:85:48: ( ( '0' | '1' .. '9' ( RULE_DECIMAL_DIGIT_FRAGMENT )* ) )
            // InternalRegularExpressionLexer.g:85:50: ( '0' | '1' .. '9' ( RULE_DECIMAL_DIGIT_FRAGMENT )* )
            {
            // InternalRegularExpressionLexer.g:85:50: ( '0' | '1' .. '9' ( RULE_DECIMAL_DIGIT_FRAGMENT )* )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='0') ) {
                alt12=1;
            }
            else if ( ((LA12_0>='1' && LA12_0<='9')) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // InternalRegularExpressionLexer.g:85:51: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionLexer.g:85:55: '1' .. '9' ( RULE_DECIMAL_DIGIT_FRAGMENT )*
                    {
                    matchRange('1','9'); 
                    // InternalRegularExpressionLexer.g:85:64: ( RULE_DECIMAL_DIGIT_FRAGMENT )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // InternalRegularExpressionLexer.g:85:64: RULE_DECIMAL_DIGIT_FRAGMENT
                    	    {
                    	    mRULE_DECIMAL_DIGIT_FRAGMENT(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);


                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT"

    // $ANTLR start "RULE_DECIMAL_DIGIT_FRAGMENT"
    public final void mRULE_DECIMAL_DIGIT_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:87:38: ( '0' .. '9' )
            // InternalRegularExpressionLexer.g:87:40: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_DECIMAL_DIGIT_FRAGMENT"

    // $ANTLR start "RULE_ZWJ"
    public final void mRULE_ZWJ() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:89:19: ( '\\u200D' )
            // InternalRegularExpressionLexer.g:89:21: '\\u200D'
            {
            match('\u200D'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_ZWJ"

    // $ANTLR start "RULE_ZWNJ"
    public final void mRULE_ZWNJ() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:91:20: ( '\\u200C' )
            // InternalRegularExpressionLexer.g:91:22: '\\u200C'
            {
            match('\u200C'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_ZWNJ"

    // $ANTLR start "RULE_BOM"
    public final void mRULE_BOM() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:93:19: ( '\\uFEFF' )
            // InternalRegularExpressionLexer.g:93:21: '\\uFEFF'
            {
            match('\uFEFF'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_BOM"

    // $ANTLR start "RULE_WHITESPACE_FRAGMENT"
    public final void mRULE_WHITESPACE_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:95:35: ( ( '\\t' | '\\u000B' | '\\f' | ' ' | '\\u00A0' | RULE_BOM | RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT ) )
            // InternalRegularExpressionLexer.g:95:37: ( '\\t' | '\\u000B' | '\\f' | ' ' | '\\u00A0' | RULE_BOM | RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT )
            {
            if ( input.LA(1)=='\t'||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||input.LA(1)==' '||input.LA(1)=='\u00A0'||input.LA(1)=='\u1680'||(input.LA(1)>='\u2000' && input.LA(1)<='\u200A')||input.LA(1)=='\u202F'||input.LA(1)=='\u205F'||input.LA(1)=='\u3000'||input.LA(1)=='\uFEFF' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_WHITESPACE_FRAGMENT"

    // $ANTLR start "RULE_LINE_TERMINATOR_FRAGMENT"
    public final void mRULE_LINE_TERMINATOR_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:97:40: ( ( '\\n' | '\\r' | '\\u2028' | '\\u2029' ) )
            // InternalRegularExpressionLexer.g:97:42: ( '\\n' | '\\r' | '\\u2028' | '\\u2029' )
            {
            if ( input.LA(1)=='\n'||input.LA(1)=='\r'||(input.LA(1)>='\u2028' && input.LA(1)<='\u2029') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_LINE_TERMINATOR_FRAGMENT"

    // $ANTLR start "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT"
    public final void mRULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:99:49: ( ( '\\n' | '\\r' ( '\\n' )? | '\\u2028' | '\\u2029' ) )
            // InternalRegularExpressionLexer.g:99:51: ( '\\n' | '\\r' ( '\\n' )? | '\\u2028' | '\\u2029' )
            {
            // InternalRegularExpressionLexer.g:99:51: ( '\\n' | '\\r' ( '\\n' )? | '\\u2028' | '\\u2029' )
            int alt14=4;
            switch ( input.LA(1) ) {
            case '\n':
                {
                alt14=1;
                }
                break;
            case '\r':
                {
                alt14=2;
                }
                break;
            case '\u2028':
                {
                alt14=3;
                }
                break;
            case '\u2029':
                {
                alt14=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // InternalRegularExpressionLexer.g:99:52: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 2 :
                    // InternalRegularExpressionLexer.g:99:57: '\\r' ( '\\n' )?
                    {
                    match('\r'); 
                    // InternalRegularExpressionLexer.g:99:62: ( '\\n' )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='\n') ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // InternalRegularExpressionLexer.g:99:62: '\\n'
                            {
                            match('\n'); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // InternalRegularExpressionLexer.g:99:68: '\\u2028'
                    {
                    match('\u2028'); 

                    }
                    break;
                case 4 :
                    // InternalRegularExpressionLexer.g:99:77: '\\u2029'
                    {
                    match('\u2029'); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT"

    // $ANTLR start "RULE_SL_COMMENT_FRAGMENT"
    public final void mRULE_SL_COMMENT_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:101:35: ( '//' (~ ( RULE_LINE_TERMINATOR_FRAGMENT ) )* )
            // InternalRegularExpressionLexer.g:101:37: '//' (~ ( RULE_LINE_TERMINATOR_FRAGMENT ) )*
            {
            match("//"); 

            // InternalRegularExpressionLexer.g:101:42: (~ ( RULE_LINE_TERMINATOR_FRAGMENT ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>='\u0000' && LA15_0<='\t')||(LA15_0>='\u000B' && LA15_0<='\f')||(LA15_0>='\u000E' && LA15_0<='\u2027')||(LA15_0>='\u202A' && LA15_0<='\uFFFF')) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalRegularExpressionLexer.g:101:42: ~ ( RULE_LINE_TERMINATOR_FRAGMENT )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\u2027')||(input.LA(1)>='\u202A' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT_FRAGMENT"

    // $ANTLR start "RULE_ML_COMMENT_FRAGMENT"
    public final void mRULE_ML_COMMENT_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:103:35: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalRegularExpressionLexer.g:103:37: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalRegularExpressionLexer.g:103:42: ( options {greedy=false; } : . )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0=='*') ) {
                    int LA16_1 = input.LA(2);

                    if ( (LA16_1=='/') ) {
                        alt16=2;
                    }
                    else if ( ((LA16_1>='\u0000' && LA16_1<='.')||(LA16_1>='0' && LA16_1<='\uFFFF')) ) {
                        alt16=1;
                    }


                }
                else if ( ((LA16_0>='\u0000' && LA16_0<=')')||(LA16_0>='+' && LA16_0<='\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalRegularExpressionLexer.g:103:70: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            match("*/"); 


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT_FRAGMENT"

    // $ANTLR start "RULE_UNICODE_COMBINING_MARK_FRAGMENT"
    public final void mRULE_UNICODE_COMBINING_MARK_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:105:47: ( ( '\\u0300' .. '\\u036F' | '\\u0483' .. '\\u0487' | '\\u0591' .. '\\u05BD' | '\\u05BF' | '\\u05C1' .. '\\u05C2' | '\\u05C4' .. '\\u05C5' | '\\u05C7' | '\\u0610' .. '\\u061A' | '\\u064B' .. '\\u065F' | '\\u0670' | '\\u06D6' .. '\\u06DC' | '\\u06DF' .. '\\u06E4' | '\\u06E7' .. '\\u06E8' | '\\u06EA' .. '\\u06ED' | '\\u0711' | '\\u0730' .. '\\u074A' | '\\u07A6' .. '\\u07B0' | '\\u07EB' .. '\\u07F3' | '\\u0816' .. '\\u0819' | '\\u081B' .. '\\u0823' | '\\u0825' .. '\\u0827' | '\\u0829' .. '\\u082D' | '\\u0859' .. '\\u085B' | '\\u08E3' .. '\\u0903' | '\\u093A' .. '\\u093C' | '\\u093E' .. '\\u094F' | '\\u0951' .. '\\u0957' | '\\u0962' .. '\\u0963' | '\\u0981' .. '\\u0983' | '\\u09BC' | '\\u09BE' .. '\\u09C4' | '\\u09C7' .. '\\u09C8' | '\\u09CB' .. '\\u09CD' | '\\u09D7' | '\\u09E2' .. '\\u09E3' | '\\u0A01' .. '\\u0A03' | '\\u0A3C' | '\\u0A3E' .. '\\u0A42' | '\\u0A47' .. '\\u0A48' | '\\u0A4B' .. '\\u0A4D' | '\\u0A51' | '\\u0A70' .. '\\u0A71' | '\\u0A75' | '\\u0A81' .. '\\u0A83' | '\\u0ABC' | '\\u0ABE' .. '\\u0AC5' | '\\u0AC7' .. '\\u0AC9' | '\\u0ACB' .. '\\u0ACD' | '\\u0AE2' .. '\\u0AE3' | '\\u0B01' .. '\\u0B03' | '\\u0B3C' | '\\u0B3E' .. '\\u0B44' | '\\u0B47' .. '\\u0B48' | '\\u0B4B' .. '\\u0B4D' | '\\u0B56' .. '\\u0B57' | '\\u0B62' .. '\\u0B63' | '\\u0B82' | '\\u0BBE' .. '\\u0BC2' | '\\u0BC6' .. '\\u0BC8' | '\\u0BCA' .. '\\u0BCD' | '\\u0BD7' | '\\u0C00' .. '\\u0C03' | '\\u0C3E' .. '\\u0C44' | '\\u0C46' .. '\\u0C48' | '\\u0C4A' .. '\\u0C4D' | '\\u0C55' .. '\\u0C56' | '\\u0C62' .. '\\u0C63' | '\\u0C81' .. '\\u0C83' | '\\u0CBC' | '\\u0CBE' .. '\\u0CC4' | '\\u0CC6' .. '\\u0CC8' | '\\u0CCA' .. '\\u0CCD' | '\\u0CD5' .. '\\u0CD6' | '\\u0CE2' .. '\\u0CE3' | '\\u0D01' .. '\\u0D03' | '\\u0D3E' .. '\\u0D44' | '\\u0D46' .. '\\u0D48' | '\\u0D4A' .. '\\u0D4D' | '\\u0D57' | '\\u0D62' .. '\\u0D63' | '\\u0D82' .. '\\u0D83' | '\\u0DCA' | '\\u0DCF' .. '\\u0DD4' | '\\u0DD6' | '\\u0DD8' .. '\\u0DDF' | '\\u0DF2' .. '\\u0DF3' | '\\u0E31' | '\\u0E34' .. '\\u0E3A' | '\\u0E47' .. '\\u0E4E' | '\\u0EB1' | '\\u0EB4' .. '\\u0EB9' | '\\u0EBB' .. '\\u0EBC' | '\\u0EC8' .. '\\u0ECD' | '\\u0F18' .. '\\u0F19' | '\\u0F35' | '\\u0F37' | '\\u0F39' | '\\u0F3E' .. '\\u0F3F' | '\\u0F71' .. '\\u0F84' | '\\u0F86' .. '\\u0F87' | '\\u0F8D' .. '\\u0F97' | '\\u0F99' .. '\\u0FBC' | '\\u0FC6' | '\\u102B' .. '\\u103E' | '\\u1056' .. '\\u1059' | '\\u105E' .. '\\u1060' | '\\u1062' .. '\\u1064' | '\\u1067' .. '\\u106D' | '\\u1071' .. '\\u1074' | '\\u1082' .. '\\u108D' | '\\u108F' | '\\u109A' .. '\\u109D' | '\\u135D' .. '\\u135F' | '\\u1712' .. '\\u1714' | '\\u1732' .. '\\u1734' | '\\u1752' .. '\\u1753' | '\\u1772' .. '\\u1773' | '\\u17B4' .. '\\u17D3' | '\\u17DD' | '\\u180B' .. '\\u180D' | '\\u18A9' | '\\u1920' .. '\\u192B' | '\\u1930' .. '\\u193B' | '\\u1A17' .. '\\u1A1B' | '\\u1A55' .. '\\u1A5E' | '\\u1A60' .. '\\u1A7C' | '\\u1A7F' | '\\u1AB0' .. '\\u1ABD' | '\\u1B00' .. '\\u1B04' | '\\u1B34' .. '\\u1B44' | '\\u1B6B' .. '\\u1B73' | '\\u1B80' .. '\\u1B82' | '\\u1BA1' .. '\\u1BAD' | '\\u1BE6' .. '\\u1BF3' | '\\u1C24' .. '\\u1C37' | '\\u1CD0' .. '\\u1CD2' | '\\u1CD4' .. '\\u1CE8' | '\\u1CED' | '\\u1CF2' .. '\\u1CF4' | '\\u1CF8' .. '\\u1CF9' | '\\u1DC0' .. '\\u1DF5' | '\\u1DFC' .. '\\u1DFF' | '\\u20D0' .. '\\u20DC' | '\\u20E1' | '\\u20E5' .. '\\u20F0' | '\\u2CEF' .. '\\u2CF1' | '\\u2D7F' | '\\u2DE0' .. '\\u2DFF' | '\\u302A' .. '\\u302F' | '\\u3099' .. '\\u309A' | '\\uA66F' | '\\uA674' .. '\\uA67D' | '\\uA69E' .. '\\uA69F' | '\\uA6F0' .. '\\uA6F1' | '\\uA802' | '\\uA806' | '\\uA80B' | '\\uA823' .. '\\uA827' | '\\uA880' .. '\\uA881' | '\\uA8B4' .. '\\uA8C4' | '\\uA8E0' .. '\\uA8F1' | '\\uA926' .. '\\uA92D' | '\\uA947' .. '\\uA953' | '\\uA980' .. '\\uA983' | '\\uA9B3' .. '\\uA9C0' | '\\uA9E5' | '\\uAA29' .. '\\uAA36' | '\\uAA43' | '\\uAA4C' .. '\\uAA4D' | '\\uAA7B' .. '\\uAA7D' | '\\uAAB0' | '\\uAAB2' .. '\\uAAB4' | '\\uAAB7' .. '\\uAAB8' | '\\uAABE' .. '\\uAABF' | '\\uAAC1' | '\\uAAEB' .. '\\uAAEF' | '\\uAAF5' .. '\\uAAF6' | '\\uABE3' .. '\\uABEA' | '\\uABEC' .. '\\uABED' | '\\uFB1E' | '\\uFE00' .. '\\uFE0F' | '\\uFE20' .. '\\uFE2F' ) )
            // InternalRegularExpressionLexer.g:105:49: ( '\\u0300' .. '\\u036F' | '\\u0483' .. '\\u0487' | '\\u0591' .. '\\u05BD' | '\\u05BF' | '\\u05C1' .. '\\u05C2' | '\\u05C4' .. '\\u05C5' | '\\u05C7' | '\\u0610' .. '\\u061A' | '\\u064B' .. '\\u065F' | '\\u0670' | '\\u06D6' .. '\\u06DC' | '\\u06DF' .. '\\u06E4' | '\\u06E7' .. '\\u06E8' | '\\u06EA' .. '\\u06ED' | '\\u0711' | '\\u0730' .. '\\u074A' | '\\u07A6' .. '\\u07B0' | '\\u07EB' .. '\\u07F3' | '\\u0816' .. '\\u0819' | '\\u081B' .. '\\u0823' | '\\u0825' .. '\\u0827' | '\\u0829' .. '\\u082D' | '\\u0859' .. '\\u085B' | '\\u08E3' .. '\\u0903' | '\\u093A' .. '\\u093C' | '\\u093E' .. '\\u094F' | '\\u0951' .. '\\u0957' | '\\u0962' .. '\\u0963' | '\\u0981' .. '\\u0983' | '\\u09BC' | '\\u09BE' .. '\\u09C4' | '\\u09C7' .. '\\u09C8' | '\\u09CB' .. '\\u09CD' | '\\u09D7' | '\\u09E2' .. '\\u09E3' | '\\u0A01' .. '\\u0A03' | '\\u0A3C' | '\\u0A3E' .. '\\u0A42' | '\\u0A47' .. '\\u0A48' | '\\u0A4B' .. '\\u0A4D' | '\\u0A51' | '\\u0A70' .. '\\u0A71' | '\\u0A75' | '\\u0A81' .. '\\u0A83' | '\\u0ABC' | '\\u0ABE' .. '\\u0AC5' | '\\u0AC7' .. '\\u0AC9' | '\\u0ACB' .. '\\u0ACD' | '\\u0AE2' .. '\\u0AE3' | '\\u0B01' .. '\\u0B03' | '\\u0B3C' | '\\u0B3E' .. '\\u0B44' | '\\u0B47' .. '\\u0B48' | '\\u0B4B' .. '\\u0B4D' | '\\u0B56' .. '\\u0B57' | '\\u0B62' .. '\\u0B63' | '\\u0B82' | '\\u0BBE' .. '\\u0BC2' | '\\u0BC6' .. '\\u0BC8' | '\\u0BCA' .. '\\u0BCD' | '\\u0BD7' | '\\u0C00' .. '\\u0C03' | '\\u0C3E' .. '\\u0C44' | '\\u0C46' .. '\\u0C48' | '\\u0C4A' .. '\\u0C4D' | '\\u0C55' .. '\\u0C56' | '\\u0C62' .. '\\u0C63' | '\\u0C81' .. '\\u0C83' | '\\u0CBC' | '\\u0CBE' .. '\\u0CC4' | '\\u0CC6' .. '\\u0CC8' | '\\u0CCA' .. '\\u0CCD' | '\\u0CD5' .. '\\u0CD6' | '\\u0CE2' .. '\\u0CE3' | '\\u0D01' .. '\\u0D03' | '\\u0D3E' .. '\\u0D44' | '\\u0D46' .. '\\u0D48' | '\\u0D4A' .. '\\u0D4D' | '\\u0D57' | '\\u0D62' .. '\\u0D63' | '\\u0D82' .. '\\u0D83' | '\\u0DCA' | '\\u0DCF' .. '\\u0DD4' | '\\u0DD6' | '\\u0DD8' .. '\\u0DDF' | '\\u0DF2' .. '\\u0DF3' | '\\u0E31' | '\\u0E34' .. '\\u0E3A' | '\\u0E47' .. '\\u0E4E' | '\\u0EB1' | '\\u0EB4' .. '\\u0EB9' | '\\u0EBB' .. '\\u0EBC' | '\\u0EC8' .. '\\u0ECD' | '\\u0F18' .. '\\u0F19' | '\\u0F35' | '\\u0F37' | '\\u0F39' | '\\u0F3E' .. '\\u0F3F' | '\\u0F71' .. '\\u0F84' | '\\u0F86' .. '\\u0F87' | '\\u0F8D' .. '\\u0F97' | '\\u0F99' .. '\\u0FBC' | '\\u0FC6' | '\\u102B' .. '\\u103E' | '\\u1056' .. '\\u1059' | '\\u105E' .. '\\u1060' | '\\u1062' .. '\\u1064' | '\\u1067' .. '\\u106D' | '\\u1071' .. '\\u1074' | '\\u1082' .. '\\u108D' | '\\u108F' | '\\u109A' .. '\\u109D' | '\\u135D' .. '\\u135F' | '\\u1712' .. '\\u1714' | '\\u1732' .. '\\u1734' | '\\u1752' .. '\\u1753' | '\\u1772' .. '\\u1773' | '\\u17B4' .. '\\u17D3' | '\\u17DD' | '\\u180B' .. '\\u180D' | '\\u18A9' | '\\u1920' .. '\\u192B' | '\\u1930' .. '\\u193B' | '\\u1A17' .. '\\u1A1B' | '\\u1A55' .. '\\u1A5E' | '\\u1A60' .. '\\u1A7C' | '\\u1A7F' | '\\u1AB0' .. '\\u1ABD' | '\\u1B00' .. '\\u1B04' | '\\u1B34' .. '\\u1B44' | '\\u1B6B' .. '\\u1B73' | '\\u1B80' .. '\\u1B82' | '\\u1BA1' .. '\\u1BAD' | '\\u1BE6' .. '\\u1BF3' | '\\u1C24' .. '\\u1C37' | '\\u1CD0' .. '\\u1CD2' | '\\u1CD4' .. '\\u1CE8' | '\\u1CED' | '\\u1CF2' .. '\\u1CF4' | '\\u1CF8' .. '\\u1CF9' | '\\u1DC0' .. '\\u1DF5' | '\\u1DFC' .. '\\u1DFF' | '\\u20D0' .. '\\u20DC' | '\\u20E1' | '\\u20E5' .. '\\u20F0' | '\\u2CEF' .. '\\u2CF1' | '\\u2D7F' | '\\u2DE0' .. '\\u2DFF' | '\\u302A' .. '\\u302F' | '\\u3099' .. '\\u309A' | '\\uA66F' | '\\uA674' .. '\\uA67D' | '\\uA69E' .. '\\uA69F' | '\\uA6F0' .. '\\uA6F1' | '\\uA802' | '\\uA806' | '\\uA80B' | '\\uA823' .. '\\uA827' | '\\uA880' .. '\\uA881' | '\\uA8B4' .. '\\uA8C4' | '\\uA8E0' .. '\\uA8F1' | '\\uA926' .. '\\uA92D' | '\\uA947' .. '\\uA953' | '\\uA980' .. '\\uA983' | '\\uA9B3' .. '\\uA9C0' | '\\uA9E5' | '\\uAA29' .. '\\uAA36' | '\\uAA43' | '\\uAA4C' .. '\\uAA4D' | '\\uAA7B' .. '\\uAA7D' | '\\uAAB0' | '\\uAAB2' .. '\\uAAB4' | '\\uAAB7' .. '\\uAAB8' | '\\uAABE' .. '\\uAABF' | '\\uAAC1' | '\\uAAEB' .. '\\uAAEF' | '\\uAAF5' .. '\\uAAF6' | '\\uABE3' .. '\\uABEA' | '\\uABEC' .. '\\uABED' | '\\uFB1E' | '\\uFE00' .. '\\uFE0F' | '\\uFE20' .. '\\uFE2F' )
            {
            if ( (input.LA(1)>='\u0300' && input.LA(1)<='\u036F')||(input.LA(1)>='\u0483' && input.LA(1)<='\u0487')||(input.LA(1)>='\u0591' && input.LA(1)<='\u05BD')||input.LA(1)=='\u05BF'||(input.LA(1)>='\u05C1' && input.LA(1)<='\u05C2')||(input.LA(1)>='\u05C4' && input.LA(1)<='\u05C5')||input.LA(1)=='\u05C7'||(input.LA(1)>='\u0610' && input.LA(1)<='\u061A')||(input.LA(1)>='\u064B' && input.LA(1)<='\u065F')||input.LA(1)=='\u0670'||(input.LA(1)>='\u06D6' && input.LA(1)<='\u06DC')||(input.LA(1)>='\u06DF' && input.LA(1)<='\u06E4')||(input.LA(1)>='\u06E7' && input.LA(1)<='\u06E8')||(input.LA(1)>='\u06EA' && input.LA(1)<='\u06ED')||input.LA(1)=='\u0711'||(input.LA(1)>='\u0730' && input.LA(1)<='\u074A')||(input.LA(1)>='\u07A6' && input.LA(1)<='\u07B0')||(input.LA(1)>='\u07EB' && input.LA(1)<='\u07F3')||(input.LA(1)>='\u0816' && input.LA(1)<='\u0819')||(input.LA(1)>='\u081B' && input.LA(1)<='\u0823')||(input.LA(1)>='\u0825' && input.LA(1)<='\u0827')||(input.LA(1)>='\u0829' && input.LA(1)<='\u082D')||(input.LA(1)>='\u0859' && input.LA(1)<='\u085B')||(input.LA(1)>='\u08E3' && input.LA(1)<='\u0903')||(input.LA(1)>='\u093A' && input.LA(1)<='\u093C')||(input.LA(1)>='\u093E' && input.LA(1)<='\u094F')||(input.LA(1)>='\u0951' && input.LA(1)<='\u0957')||(input.LA(1)>='\u0962' && input.LA(1)<='\u0963')||(input.LA(1)>='\u0981' && input.LA(1)<='\u0983')||input.LA(1)=='\u09BC'||(input.LA(1)>='\u09BE' && input.LA(1)<='\u09C4')||(input.LA(1)>='\u09C7' && input.LA(1)<='\u09C8')||(input.LA(1)>='\u09CB' && input.LA(1)<='\u09CD')||input.LA(1)=='\u09D7'||(input.LA(1)>='\u09E2' && input.LA(1)<='\u09E3')||(input.LA(1)>='\u0A01' && input.LA(1)<='\u0A03')||input.LA(1)=='\u0A3C'||(input.LA(1)>='\u0A3E' && input.LA(1)<='\u0A42')||(input.LA(1)>='\u0A47' && input.LA(1)<='\u0A48')||(input.LA(1)>='\u0A4B' && input.LA(1)<='\u0A4D')||input.LA(1)=='\u0A51'||(input.LA(1)>='\u0A70' && input.LA(1)<='\u0A71')||input.LA(1)=='\u0A75'||(input.LA(1)>='\u0A81' && input.LA(1)<='\u0A83')||input.LA(1)=='\u0ABC'||(input.LA(1)>='\u0ABE' && input.LA(1)<='\u0AC5')||(input.LA(1)>='\u0AC7' && input.LA(1)<='\u0AC9')||(input.LA(1)>='\u0ACB' && input.LA(1)<='\u0ACD')||(input.LA(1)>='\u0AE2' && input.LA(1)<='\u0AE3')||(input.LA(1)>='\u0B01' && input.LA(1)<='\u0B03')||input.LA(1)=='\u0B3C'||(input.LA(1)>='\u0B3E' && input.LA(1)<='\u0B44')||(input.LA(1)>='\u0B47' && input.LA(1)<='\u0B48')||(input.LA(1)>='\u0B4B' && input.LA(1)<='\u0B4D')||(input.LA(1)>='\u0B56' && input.LA(1)<='\u0B57')||(input.LA(1)>='\u0B62' && input.LA(1)<='\u0B63')||input.LA(1)=='\u0B82'||(input.LA(1)>='\u0BBE' && input.LA(1)<='\u0BC2')||(input.LA(1)>='\u0BC6' && input.LA(1)<='\u0BC8')||(input.LA(1)>='\u0BCA' && input.LA(1)<='\u0BCD')||input.LA(1)=='\u0BD7'||(input.LA(1)>='\u0C00' && input.LA(1)<='\u0C03')||(input.LA(1)>='\u0C3E' && input.LA(1)<='\u0C44')||(input.LA(1)>='\u0C46' && input.LA(1)<='\u0C48')||(input.LA(1)>='\u0C4A' && input.LA(1)<='\u0C4D')||(input.LA(1)>='\u0C55' && input.LA(1)<='\u0C56')||(input.LA(1)>='\u0C62' && input.LA(1)<='\u0C63')||(input.LA(1)>='\u0C81' && input.LA(1)<='\u0C83')||input.LA(1)=='\u0CBC'||(input.LA(1)>='\u0CBE' && input.LA(1)<='\u0CC4')||(input.LA(1)>='\u0CC6' && input.LA(1)<='\u0CC8')||(input.LA(1)>='\u0CCA' && input.LA(1)<='\u0CCD')||(input.LA(1)>='\u0CD5' && input.LA(1)<='\u0CD6')||(input.LA(1)>='\u0CE2' && input.LA(1)<='\u0CE3')||(input.LA(1)>='\u0D01' && input.LA(1)<='\u0D03')||(input.LA(1)>='\u0D3E' && input.LA(1)<='\u0D44')||(input.LA(1)>='\u0D46' && input.LA(1)<='\u0D48')||(input.LA(1)>='\u0D4A' && input.LA(1)<='\u0D4D')||input.LA(1)=='\u0D57'||(input.LA(1)>='\u0D62' && input.LA(1)<='\u0D63')||(input.LA(1)>='\u0D82' && input.LA(1)<='\u0D83')||input.LA(1)=='\u0DCA'||(input.LA(1)>='\u0DCF' && input.LA(1)<='\u0DD4')||input.LA(1)=='\u0DD6'||(input.LA(1)>='\u0DD8' && input.LA(1)<='\u0DDF')||(input.LA(1)>='\u0DF2' && input.LA(1)<='\u0DF3')||input.LA(1)=='\u0E31'||(input.LA(1)>='\u0E34' && input.LA(1)<='\u0E3A')||(input.LA(1)>='\u0E47' && input.LA(1)<='\u0E4E')||input.LA(1)=='\u0EB1'||(input.LA(1)>='\u0EB4' && input.LA(1)<='\u0EB9')||(input.LA(1)>='\u0EBB' && input.LA(1)<='\u0EBC')||(input.LA(1)>='\u0EC8' && input.LA(1)<='\u0ECD')||(input.LA(1)>='\u0F18' && input.LA(1)<='\u0F19')||input.LA(1)=='\u0F35'||input.LA(1)=='\u0F37'||input.LA(1)=='\u0F39'||(input.LA(1)>='\u0F3E' && input.LA(1)<='\u0F3F')||(input.LA(1)>='\u0F71' && input.LA(1)<='\u0F84')||(input.LA(1)>='\u0F86' && input.LA(1)<='\u0F87')||(input.LA(1)>='\u0F8D' && input.LA(1)<='\u0F97')||(input.LA(1)>='\u0F99' && input.LA(1)<='\u0FBC')||input.LA(1)=='\u0FC6'||(input.LA(1)>='\u102B' && input.LA(1)<='\u103E')||(input.LA(1)>='\u1056' && input.LA(1)<='\u1059')||(input.LA(1)>='\u105E' && input.LA(1)<='\u1060')||(input.LA(1)>='\u1062' && input.LA(1)<='\u1064')||(input.LA(1)>='\u1067' && input.LA(1)<='\u106D')||(input.LA(1)>='\u1071' && input.LA(1)<='\u1074')||(input.LA(1)>='\u1082' && input.LA(1)<='\u108D')||input.LA(1)=='\u108F'||(input.LA(1)>='\u109A' && input.LA(1)<='\u109D')||(input.LA(1)>='\u135D' && input.LA(1)<='\u135F')||(input.LA(1)>='\u1712' && input.LA(1)<='\u1714')||(input.LA(1)>='\u1732' && input.LA(1)<='\u1734')||(input.LA(1)>='\u1752' && input.LA(1)<='\u1753')||(input.LA(1)>='\u1772' && input.LA(1)<='\u1773')||(input.LA(1)>='\u17B4' && input.LA(1)<='\u17D3')||input.LA(1)=='\u17DD'||(input.LA(1)>='\u180B' && input.LA(1)<='\u180D')||input.LA(1)=='\u18A9'||(input.LA(1)>='\u1920' && input.LA(1)<='\u192B')||(input.LA(1)>='\u1930' && input.LA(1)<='\u193B')||(input.LA(1)>='\u1A17' && input.LA(1)<='\u1A1B')||(input.LA(1)>='\u1A55' && input.LA(1)<='\u1A5E')||(input.LA(1)>='\u1A60' && input.LA(1)<='\u1A7C')||input.LA(1)=='\u1A7F'||(input.LA(1)>='\u1AB0' && input.LA(1)<='\u1ABD')||(input.LA(1)>='\u1B00' && input.LA(1)<='\u1B04')||(input.LA(1)>='\u1B34' && input.LA(1)<='\u1B44')||(input.LA(1)>='\u1B6B' && input.LA(1)<='\u1B73')||(input.LA(1)>='\u1B80' && input.LA(1)<='\u1B82')||(input.LA(1)>='\u1BA1' && input.LA(1)<='\u1BAD')||(input.LA(1)>='\u1BE6' && input.LA(1)<='\u1BF3')||(input.LA(1)>='\u1C24' && input.LA(1)<='\u1C37')||(input.LA(1)>='\u1CD0' && input.LA(1)<='\u1CD2')||(input.LA(1)>='\u1CD4' && input.LA(1)<='\u1CE8')||input.LA(1)=='\u1CED'||(input.LA(1)>='\u1CF2' && input.LA(1)<='\u1CF4')||(input.LA(1)>='\u1CF8' && input.LA(1)<='\u1CF9')||(input.LA(1)>='\u1DC0' && input.LA(1)<='\u1DF5')||(input.LA(1)>='\u1DFC' && input.LA(1)<='\u1DFF')||(input.LA(1)>='\u20D0' && input.LA(1)<='\u20DC')||input.LA(1)=='\u20E1'||(input.LA(1)>='\u20E5' && input.LA(1)<='\u20F0')||(input.LA(1)>='\u2CEF' && input.LA(1)<='\u2CF1')||input.LA(1)=='\u2D7F'||(input.LA(1)>='\u2DE0' && input.LA(1)<='\u2DFF')||(input.LA(1)>='\u302A' && input.LA(1)<='\u302F')||(input.LA(1)>='\u3099' && input.LA(1)<='\u309A')||input.LA(1)=='\uA66F'||(input.LA(1)>='\uA674' && input.LA(1)<='\uA67D')||(input.LA(1)>='\uA69E' && input.LA(1)<='\uA69F')||(input.LA(1)>='\uA6F0' && input.LA(1)<='\uA6F1')||input.LA(1)=='\uA802'||input.LA(1)=='\uA806'||input.LA(1)=='\uA80B'||(input.LA(1)>='\uA823' && input.LA(1)<='\uA827')||(input.LA(1)>='\uA880' && input.LA(1)<='\uA881')||(input.LA(1)>='\uA8B4' && input.LA(1)<='\uA8C4')||(input.LA(1)>='\uA8E0' && input.LA(1)<='\uA8F1')||(input.LA(1)>='\uA926' && input.LA(1)<='\uA92D')||(input.LA(1)>='\uA947' && input.LA(1)<='\uA953')||(input.LA(1)>='\uA980' && input.LA(1)<='\uA983')||(input.LA(1)>='\uA9B3' && input.LA(1)<='\uA9C0')||input.LA(1)=='\uA9E5'||(input.LA(1)>='\uAA29' && input.LA(1)<='\uAA36')||input.LA(1)=='\uAA43'||(input.LA(1)>='\uAA4C' && input.LA(1)<='\uAA4D')||(input.LA(1)>='\uAA7B' && input.LA(1)<='\uAA7D')||input.LA(1)=='\uAAB0'||(input.LA(1)>='\uAAB2' && input.LA(1)<='\uAAB4')||(input.LA(1)>='\uAAB7' && input.LA(1)<='\uAAB8')||(input.LA(1)>='\uAABE' && input.LA(1)<='\uAABF')||input.LA(1)=='\uAAC1'||(input.LA(1)>='\uAAEB' && input.LA(1)<='\uAAEF')||(input.LA(1)>='\uAAF5' && input.LA(1)<='\uAAF6')||(input.LA(1)>='\uABE3' && input.LA(1)<='\uABEA')||(input.LA(1)>='\uABEC' && input.LA(1)<='\uABED')||input.LA(1)=='\uFB1E'||(input.LA(1)>='\uFE00' && input.LA(1)<='\uFE0F')||(input.LA(1)>='\uFE20' && input.LA(1)<='\uFE2F') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_UNICODE_COMBINING_MARK_FRAGMENT"

    // $ANTLR start "RULE_UNICODE_DIGIT_FRAGMENT"
    public final void mRULE_UNICODE_DIGIT_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:107:38: ( ( '0' .. '9' | '\\u0660' .. '\\u0669' | '\\u06F0' .. '\\u06F9' | '\\u07C0' .. '\\u07C9' | '\\u0966' .. '\\u096F' | '\\u09E6' .. '\\u09EF' | '\\u0A66' .. '\\u0A6F' | '\\u0AE6' .. '\\u0AEF' | '\\u0B66' .. '\\u0B6F' | '\\u0BE6' .. '\\u0BEF' | '\\u0C66' .. '\\u0C6F' | '\\u0CE6' .. '\\u0CEF' | '\\u0D66' .. '\\u0D6F' | '\\u0DE6' .. '\\u0DEF' | '\\u0E50' .. '\\u0E59' | '\\u0ED0' .. '\\u0ED9' | '\\u0F20' .. '\\u0F29' | '\\u1040' .. '\\u1049' | '\\u1090' .. '\\u1099' | '\\u17E0' .. '\\u17E9' | '\\u1810' .. '\\u1819' | '\\u1946' .. '\\u194F' | '\\u19D0' .. '\\u19D9' | '\\u1A80' .. '\\u1A89' | '\\u1A90' .. '\\u1A99' | '\\u1B50' .. '\\u1B59' | '\\u1BB0' .. '\\u1BB9' | '\\u1C40' .. '\\u1C49' | '\\u1C50' .. '\\u1C59' | '\\uA620' .. '\\uA629' | '\\uA8D0' .. '\\uA8D9' | '\\uA900' .. '\\uA909' | '\\uA9D0' .. '\\uA9D9' | '\\uA9F0' .. '\\uA9F9' | '\\uAA50' .. '\\uAA59' | '\\uABF0' .. '\\uABF9' | '\\uFF10' .. '\\uFF19' ) )
            // InternalRegularExpressionLexer.g:107:40: ( '0' .. '9' | '\\u0660' .. '\\u0669' | '\\u06F0' .. '\\u06F9' | '\\u07C0' .. '\\u07C9' | '\\u0966' .. '\\u096F' | '\\u09E6' .. '\\u09EF' | '\\u0A66' .. '\\u0A6F' | '\\u0AE6' .. '\\u0AEF' | '\\u0B66' .. '\\u0B6F' | '\\u0BE6' .. '\\u0BEF' | '\\u0C66' .. '\\u0C6F' | '\\u0CE6' .. '\\u0CEF' | '\\u0D66' .. '\\u0D6F' | '\\u0DE6' .. '\\u0DEF' | '\\u0E50' .. '\\u0E59' | '\\u0ED0' .. '\\u0ED9' | '\\u0F20' .. '\\u0F29' | '\\u1040' .. '\\u1049' | '\\u1090' .. '\\u1099' | '\\u17E0' .. '\\u17E9' | '\\u1810' .. '\\u1819' | '\\u1946' .. '\\u194F' | '\\u19D0' .. '\\u19D9' | '\\u1A80' .. '\\u1A89' | '\\u1A90' .. '\\u1A99' | '\\u1B50' .. '\\u1B59' | '\\u1BB0' .. '\\u1BB9' | '\\u1C40' .. '\\u1C49' | '\\u1C50' .. '\\u1C59' | '\\uA620' .. '\\uA629' | '\\uA8D0' .. '\\uA8D9' | '\\uA900' .. '\\uA909' | '\\uA9D0' .. '\\uA9D9' | '\\uA9F0' .. '\\uA9F9' | '\\uAA50' .. '\\uAA59' | '\\uABF0' .. '\\uABF9' | '\\uFF10' .. '\\uFF19' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='\u0660' && input.LA(1)<='\u0669')||(input.LA(1)>='\u06F0' && input.LA(1)<='\u06F9')||(input.LA(1)>='\u07C0' && input.LA(1)<='\u07C9')||(input.LA(1)>='\u0966' && input.LA(1)<='\u096F')||(input.LA(1)>='\u09E6' && input.LA(1)<='\u09EF')||(input.LA(1)>='\u0A66' && input.LA(1)<='\u0A6F')||(input.LA(1)>='\u0AE6' && input.LA(1)<='\u0AEF')||(input.LA(1)>='\u0B66' && input.LA(1)<='\u0B6F')||(input.LA(1)>='\u0BE6' && input.LA(1)<='\u0BEF')||(input.LA(1)>='\u0C66' && input.LA(1)<='\u0C6F')||(input.LA(1)>='\u0CE6' && input.LA(1)<='\u0CEF')||(input.LA(1)>='\u0D66' && input.LA(1)<='\u0D6F')||(input.LA(1)>='\u0DE6' && input.LA(1)<='\u0DEF')||(input.LA(1)>='\u0E50' && input.LA(1)<='\u0E59')||(input.LA(1)>='\u0ED0' && input.LA(1)<='\u0ED9')||(input.LA(1)>='\u0F20' && input.LA(1)<='\u0F29')||(input.LA(1)>='\u1040' && input.LA(1)<='\u1049')||(input.LA(1)>='\u1090' && input.LA(1)<='\u1099')||(input.LA(1)>='\u17E0' && input.LA(1)<='\u17E9')||(input.LA(1)>='\u1810' && input.LA(1)<='\u1819')||(input.LA(1)>='\u1946' && input.LA(1)<='\u194F')||(input.LA(1)>='\u19D0' && input.LA(1)<='\u19D9')||(input.LA(1)>='\u1A80' && input.LA(1)<='\u1A89')||(input.LA(1)>='\u1A90' && input.LA(1)<='\u1A99')||(input.LA(1)>='\u1B50' && input.LA(1)<='\u1B59')||(input.LA(1)>='\u1BB0' && input.LA(1)<='\u1BB9')||(input.LA(1)>='\u1C40' && input.LA(1)<='\u1C49')||(input.LA(1)>='\u1C50' && input.LA(1)<='\u1C59')||(input.LA(1)>='\uA620' && input.LA(1)<='\uA629')||(input.LA(1)>='\uA8D0' && input.LA(1)<='\uA8D9')||(input.LA(1)>='\uA900' && input.LA(1)<='\uA909')||(input.LA(1)>='\uA9D0' && input.LA(1)<='\uA9D9')||(input.LA(1)>='\uA9F0' && input.LA(1)<='\uA9F9')||(input.LA(1)>='\uAA50' && input.LA(1)<='\uAA59')||(input.LA(1)>='\uABF0' && input.LA(1)<='\uABF9')||(input.LA(1)>='\uFF10' && input.LA(1)<='\uFF19') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_UNICODE_DIGIT_FRAGMENT"

    // $ANTLR start "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT"
    public final void mRULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:109:54: ( ( '_' | '\\u203F' .. '\\u2040' | '\\u2054' | '\\uFE33' .. '\\uFE34' | '\\uFE4D' .. '\\uFE4F' | '\\uFF3F' ) )
            // InternalRegularExpressionLexer.g:109:56: ( '_' | '\\u203F' .. '\\u2040' | '\\u2054' | '\\uFE33' .. '\\uFE34' | '\\uFE4D' .. '\\uFE4F' | '\\uFF3F' )
            {
            if ( input.LA(1)=='_'||(input.LA(1)>='\u203F' && input.LA(1)<='\u2040')||input.LA(1)=='\u2054'||(input.LA(1)>='\uFE33' && input.LA(1)<='\uFE34')||(input.LA(1)>='\uFE4D' && input.LA(1)<='\uFE4F')||input.LA(1)=='\uFF3F' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT"

    // $ANTLR start "RULE_UNICODE_LETTER_FRAGMENT"
    public final void mRULE_UNICODE_LETTER_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:111:39: ( ( 'A' .. 'Z' | 'a' .. 'z' | '\\u00AA' | '\\u00B5' | '\\u00BA' | '\\u00C0' .. '\\u00D6' | '\\u00D8' .. '\\u00F6' | '\\u00F8' .. '\\u02C1' | '\\u02C6' .. '\\u02D1' | '\\u02E0' .. '\\u02E4' | '\\u02EC' | '\\u02EE' | '\\u0370' .. '\\u0374' | '\\u0376' .. '\\u0377' | '\\u037A' .. '\\u037D' | '\\u037F' | '\\u0386' | '\\u0388' .. '\\u038A' | '\\u038C' | '\\u038E' .. '\\u03A1' | '\\u03A3' .. '\\u03F5' | '\\u03F7' .. '\\u0481' | '\\u048A' .. '\\u052F' | '\\u0531' .. '\\u0556' | '\\u0559' | '\\u0561' .. '\\u0587' | '\\u05D0' .. '\\u05EA' | '\\u05F0' .. '\\u05F2' | '\\u0620' .. '\\u064A' | '\\u066E' .. '\\u066F' | '\\u0671' .. '\\u06D3' | '\\u06D5' | '\\u06E5' .. '\\u06E6' | '\\u06EE' .. '\\u06EF' | '\\u06FA' .. '\\u06FC' | '\\u06FF' | '\\u0710' | '\\u0712' .. '\\u072F' | '\\u074D' .. '\\u07A5' | '\\u07B1' | '\\u07CA' .. '\\u07EA' | '\\u07F4' .. '\\u07F5' | '\\u07FA' | '\\u0800' .. '\\u0815' | '\\u081A' | '\\u0824' | '\\u0828' | '\\u0840' .. '\\u0858' | '\\u08A0' .. '\\u08B4' | '\\u0904' .. '\\u0939' | '\\u093D' | '\\u0950' | '\\u0958' .. '\\u0961' | '\\u0971' .. '\\u0980' | '\\u0985' .. '\\u098C' | '\\u098F' .. '\\u0990' | '\\u0993' .. '\\u09A8' | '\\u09AA' .. '\\u09B0' | '\\u09B2' | '\\u09B6' .. '\\u09B9' | '\\u09BD' | '\\u09CE' | '\\u09DC' .. '\\u09DD' | '\\u09DF' .. '\\u09E1' | '\\u09F0' .. '\\u09F1' | '\\u0A05' .. '\\u0A0A' | '\\u0A0F' .. '\\u0A10' | '\\u0A13' .. '\\u0A28' | '\\u0A2A' .. '\\u0A30' | '\\u0A32' .. '\\u0A33' | '\\u0A35' .. '\\u0A36' | '\\u0A38' .. '\\u0A39' | '\\u0A59' .. '\\u0A5C' | '\\u0A5E' | '\\u0A72' .. '\\u0A74' | '\\u0A85' .. '\\u0A8D' | '\\u0A8F' .. '\\u0A91' | '\\u0A93' .. '\\u0AA8' | '\\u0AAA' .. '\\u0AB0' | '\\u0AB2' .. '\\u0AB3' | '\\u0AB5' .. '\\u0AB9' | '\\u0ABD' | '\\u0AD0' | '\\u0AE0' .. '\\u0AE1' | '\\u0AF9' | '\\u0B05' .. '\\u0B0C' | '\\u0B0F' .. '\\u0B10' | '\\u0B13' .. '\\u0B28' | '\\u0B2A' .. '\\u0B30' | '\\u0B32' .. '\\u0B33' | '\\u0B35' .. '\\u0B39' | '\\u0B3D' | '\\u0B5C' .. '\\u0B5D' | '\\u0B5F' .. '\\u0B61' | '\\u0B71' | '\\u0B83' | '\\u0B85' .. '\\u0B8A' | '\\u0B8E' .. '\\u0B90' | '\\u0B92' .. '\\u0B95' | '\\u0B99' .. '\\u0B9A' | '\\u0B9C' | '\\u0B9E' .. '\\u0B9F' | '\\u0BA3' .. '\\u0BA4' | '\\u0BA8' .. '\\u0BAA' | '\\u0BAE' .. '\\u0BB9' | '\\u0BD0' | '\\u0C05' .. '\\u0C0C' | '\\u0C0E' .. '\\u0C10' | '\\u0C12' .. '\\u0C28' | '\\u0C2A' .. '\\u0C39' | '\\u0C3D' | '\\u0C58' .. '\\u0C5A' | '\\u0C60' .. '\\u0C61' | '\\u0C85' .. '\\u0C8C' | '\\u0C8E' .. '\\u0C90' | '\\u0C92' .. '\\u0CA8' | '\\u0CAA' .. '\\u0CB3' | '\\u0CB5' .. '\\u0CB9' | '\\u0CBD' | '\\u0CDE' | '\\u0CE0' .. '\\u0CE1' | '\\u0CF1' .. '\\u0CF2' | '\\u0D05' .. '\\u0D0C' | '\\u0D0E' .. '\\u0D10' | '\\u0D12' .. '\\u0D3A' | '\\u0D3D' | '\\u0D4E' | '\\u0D5F' .. '\\u0D61' | '\\u0D7A' .. '\\u0D7F' | '\\u0D85' .. '\\u0D96' | '\\u0D9A' .. '\\u0DB1' | '\\u0DB3' .. '\\u0DBB' | '\\u0DBD' | '\\u0DC0' .. '\\u0DC6' | '\\u0E01' .. '\\u0E30' | '\\u0E32' .. '\\u0E33' | '\\u0E40' .. '\\u0E46' | '\\u0E81' .. '\\u0E82' | '\\u0E84' | '\\u0E87' .. '\\u0E88' | '\\u0E8A' | '\\u0E8D' | '\\u0E94' .. '\\u0E97' | '\\u0E99' .. '\\u0E9F' | '\\u0EA1' .. '\\u0EA3' | '\\u0EA5' | '\\u0EA7' | '\\u0EAA' .. '\\u0EAB' | '\\u0EAD' .. '\\u0EB0' | '\\u0EB2' .. '\\u0EB3' | '\\u0EBD' | '\\u0EC0' .. '\\u0EC4' | '\\u0EC6' | '\\u0EDC' .. '\\u0EDF' | '\\u0F00' | '\\u0F40' .. '\\u0F47' | '\\u0F49' .. '\\u0F6C' | '\\u0F88' .. '\\u0F8C' | '\\u1000' .. '\\u102A' | '\\u103F' | '\\u1050' .. '\\u1055' | '\\u105A' .. '\\u105D' | '\\u1061' | '\\u1065' .. '\\u1066' | '\\u106E' .. '\\u1070' | '\\u1075' .. '\\u1081' | '\\u108E' | '\\u10A0' .. '\\u10C5' | '\\u10C7' | '\\u10CD' | '\\u10D0' .. '\\u10FA' | '\\u10FC' .. '\\u1248' | '\\u124A' .. '\\u124D' | '\\u1250' .. '\\u1256' | '\\u1258' | '\\u125A' .. '\\u125D' | '\\u1260' .. '\\u1288' | '\\u128A' .. '\\u128D' | '\\u1290' .. '\\u12B0' | '\\u12B2' .. '\\u12B5' | '\\u12B8' .. '\\u12BE' | '\\u12C0' | '\\u12C2' .. '\\u12C5' | '\\u12C8' .. '\\u12D6' | '\\u12D8' .. '\\u1310' | '\\u1312' .. '\\u1315' | '\\u1318' .. '\\u135A' | '\\u1380' .. '\\u138F' | '\\u13A0' .. '\\u13F5' | '\\u13F8' .. '\\u13FD' | '\\u1401' .. '\\u166C' | '\\u166F' .. '\\u167F' | '\\u1681' .. '\\u169A' | '\\u16A0' .. '\\u16EA' | '\\u16EE' .. '\\u16F8' | '\\u1700' .. '\\u170C' | '\\u170E' .. '\\u1711' | '\\u1720' .. '\\u1731' | '\\u1740' .. '\\u1751' | '\\u1760' .. '\\u176C' | '\\u176E' .. '\\u1770' | '\\u1780' .. '\\u17B3' | '\\u17D7' | '\\u17DC' | '\\u1820' .. '\\u1877' | '\\u1880' .. '\\u18A8' | '\\u18AA' | '\\u18B0' .. '\\u18F5' | '\\u1900' .. '\\u191E' | '\\u1950' .. '\\u196D' | '\\u1970' .. '\\u1974' | '\\u1980' .. '\\u19AB' | '\\u19B0' .. '\\u19C9' | '\\u1A00' .. '\\u1A16' | '\\u1A20' .. '\\u1A54' | '\\u1AA7' | '\\u1B05' .. '\\u1B33' | '\\u1B45' .. '\\u1B4B' | '\\u1B83' .. '\\u1BA0' | '\\u1BAE' .. '\\u1BAF' | '\\u1BBA' .. '\\u1BE5' | '\\u1C00' .. '\\u1C23' | '\\u1C4D' .. '\\u1C4F' | '\\u1C5A' .. '\\u1C7D' | '\\u1CE9' .. '\\u1CEC' | '\\u1CEE' .. '\\u1CF1' | '\\u1CF5' .. '\\u1CF6' | '\\u1D00' .. '\\u1DBF' | '\\u1E00' .. '\\u1F15' | '\\u1F18' .. '\\u1F1D' | '\\u1F20' .. '\\u1F45' | '\\u1F48' .. '\\u1F4D' | '\\u1F50' .. '\\u1F57' | '\\u1F59' | '\\u1F5B' | '\\u1F5D' | '\\u1F5F' .. '\\u1F7D' | '\\u1F80' .. '\\u1FB4' | '\\u1FB6' .. '\\u1FBC' | '\\u1FBE' | '\\u1FC2' .. '\\u1FC4' | '\\u1FC6' .. '\\u1FCC' | '\\u1FD0' .. '\\u1FD3' | '\\u1FD6' .. '\\u1FDB' | '\\u1FE0' .. '\\u1FEC' | '\\u1FF2' .. '\\u1FF4' | '\\u1FF6' .. '\\u1FFC' | '\\u2071' | '\\u207F' | '\\u2090' .. '\\u209C' | '\\u2102' | '\\u2107' | '\\u210A' .. '\\u2113' | '\\u2115' | '\\u2119' .. '\\u211D' | '\\u2124' | '\\u2126' | '\\u2128' | '\\u212A' .. '\\u212D' | '\\u212F' .. '\\u2139' | '\\u213C' .. '\\u213F' | '\\u2145' .. '\\u2149' | '\\u214E' | '\\u2160' .. '\\u2188' | '\\u2C00' .. '\\u2C2E' | '\\u2C30' .. '\\u2C5E' | '\\u2C60' .. '\\u2CE4' | '\\u2CEB' .. '\\u2CEE' | '\\u2CF2' .. '\\u2CF3' | '\\u2D00' .. '\\u2D25' | '\\u2D27' | '\\u2D2D' | '\\u2D30' .. '\\u2D67' | '\\u2D6F' | '\\u2D80' .. '\\u2D96' | '\\u2DA0' .. '\\u2DA6' | '\\u2DA8' .. '\\u2DAE' | '\\u2DB0' .. '\\u2DB6' | '\\u2DB8' .. '\\u2DBE' | '\\u2DC0' .. '\\u2DC6' | '\\u2DC8' .. '\\u2DCE' | '\\u2DD0' .. '\\u2DD6' | '\\u2DD8' .. '\\u2DDE' | '\\u2E2F' | '\\u3005' .. '\\u3007' | '\\u3021' .. '\\u3029' | '\\u3031' .. '\\u3035' | '\\u3038' .. '\\u303C' | '\\u3041' .. '\\u3096' | '\\u309D' .. '\\u309F' | '\\u30A1' .. '\\u30FA' | '\\u30FC' .. '\\u30FF' | '\\u3105' .. '\\u312D' | '\\u3131' .. '\\u318E' | '\\u31A0' .. '\\u31BA' | '\\u31F0' .. '\\u31FF' | '\\u3400' .. '\\u4DB5' | '\\u4E00' .. '\\u9FD5' | '\\uA000' .. '\\uA48C' | '\\uA4D0' .. '\\uA4FD' | '\\uA500' .. '\\uA60C' | '\\uA610' .. '\\uA61F' | '\\uA62A' .. '\\uA62B' | '\\uA640' .. '\\uA66E' | '\\uA67F' .. '\\uA69D' | '\\uA6A0' .. '\\uA6EF' | '\\uA717' .. '\\uA71F' | '\\uA722' .. '\\uA788' | '\\uA78B' .. '\\uA7AD' | '\\uA7B0' .. '\\uA7B7' | '\\uA7F7' .. '\\uA801' | '\\uA803' .. '\\uA805' | '\\uA807' .. '\\uA80A' | '\\uA80C' .. '\\uA822' | '\\uA840' .. '\\uA873' | '\\uA882' .. '\\uA8B3' | '\\uA8F2' .. '\\uA8F7' | '\\uA8FB' | '\\uA8FD' | '\\uA90A' .. '\\uA925' | '\\uA930' .. '\\uA946' | '\\uA960' .. '\\uA97C' | '\\uA984' .. '\\uA9B2' | '\\uA9CF' | '\\uA9E0' .. '\\uA9E4' | '\\uA9E6' .. '\\uA9EF' | '\\uA9FA' .. '\\uA9FE' | '\\uAA00' .. '\\uAA28' | '\\uAA40' .. '\\uAA42' | '\\uAA44' .. '\\uAA4B' | '\\uAA60' .. '\\uAA76' | '\\uAA7A' | '\\uAA7E' .. '\\uAAAF' | '\\uAAB1' | '\\uAAB5' .. '\\uAAB6' | '\\uAAB9' .. '\\uAABD' | '\\uAAC0' | '\\uAAC2' | '\\uAADB' .. '\\uAADD' | '\\uAAE0' .. '\\uAAEA' | '\\uAAF2' .. '\\uAAF4' | '\\uAB01' .. '\\uAB06' | '\\uAB09' .. '\\uAB0E' | '\\uAB11' .. '\\uAB16' | '\\uAB20' .. '\\uAB26' | '\\uAB28' .. '\\uAB2E' | '\\uAB30' .. '\\uAB5A' | '\\uAB5C' .. '\\uAB65' | '\\uAB70' .. '\\uABE2' | '\\uAC00' .. '\\uD7A3' | '\\uD7B0' .. '\\uD7C6' | '\\uD7CB' .. '\\uD7FB' | '\\uF900' .. '\\uFA6D' | '\\uFA70' .. '\\uFAD9' | '\\uFB00' .. '\\uFB06' | '\\uFB13' .. '\\uFB17' | '\\uFB1D' | '\\uFB1F' .. '\\uFB28' | '\\uFB2A' .. '\\uFB36' | '\\uFB38' .. '\\uFB3C' | '\\uFB3E' | '\\uFB40' .. '\\uFB41' | '\\uFB43' .. '\\uFB44' | '\\uFB46' .. '\\uFBB1' | '\\uFBD3' .. '\\uFD3D' | '\\uFD50' .. '\\uFD8F' | '\\uFD92' .. '\\uFDC7' | '\\uFDF0' .. '\\uFDFB' | '\\uFE70' .. '\\uFE74' | '\\uFE76' .. '\\uFEFC' | '\\uFF21' .. '\\uFF3A' | '\\uFF41' .. '\\uFF5A' | '\\uFF66' .. '\\uFFBE' | '\\uFFC2' .. '\\uFFC7' | '\\uFFCA' .. '\\uFFCF' | '\\uFFD2' .. '\\uFFD7' | '\\uFFDA' .. '\\uFFDC' ) )
            // InternalRegularExpressionLexer.g:111:41: ( 'A' .. 'Z' | 'a' .. 'z' | '\\u00AA' | '\\u00B5' | '\\u00BA' | '\\u00C0' .. '\\u00D6' | '\\u00D8' .. '\\u00F6' | '\\u00F8' .. '\\u02C1' | '\\u02C6' .. '\\u02D1' | '\\u02E0' .. '\\u02E4' | '\\u02EC' | '\\u02EE' | '\\u0370' .. '\\u0374' | '\\u0376' .. '\\u0377' | '\\u037A' .. '\\u037D' | '\\u037F' | '\\u0386' | '\\u0388' .. '\\u038A' | '\\u038C' | '\\u038E' .. '\\u03A1' | '\\u03A3' .. '\\u03F5' | '\\u03F7' .. '\\u0481' | '\\u048A' .. '\\u052F' | '\\u0531' .. '\\u0556' | '\\u0559' | '\\u0561' .. '\\u0587' | '\\u05D0' .. '\\u05EA' | '\\u05F0' .. '\\u05F2' | '\\u0620' .. '\\u064A' | '\\u066E' .. '\\u066F' | '\\u0671' .. '\\u06D3' | '\\u06D5' | '\\u06E5' .. '\\u06E6' | '\\u06EE' .. '\\u06EF' | '\\u06FA' .. '\\u06FC' | '\\u06FF' | '\\u0710' | '\\u0712' .. '\\u072F' | '\\u074D' .. '\\u07A5' | '\\u07B1' | '\\u07CA' .. '\\u07EA' | '\\u07F4' .. '\\u07F5' | '\\u07FA' | '\\u0800' .. '\\u0815' | '\\u081A' | '\\u0824' | '\\u0828' | '\\u0840' .. '\\u0858' | '\\u08A0' .. '\\u08B4' | '\\u0904' .. '\\u0939' | '\\u093D' | '\\u0950' | '\\u0958' .. '\\u0961' | '\\u0971' .. '\\u0980' | '\\u0985' .. '\\u098C' | '\\u098F' .. '\\u0990' | '\\u0993' .. '\\u09A8' | '\\u09AA' .. '\\u09B0' | '\\u09B2' | '\\u09B6' .. '\\u09B9' | '\\u09BD' | '\\u09CE' | '\\u09DC' .. '\\u09DD' | '\\u09DF' .. '\\u09E1' | '\\u09F0' .. '\\u09F1' | '\\u0A05' .. '\\u0A0A' | '\\u0A0F' .. '\\u0A10' | '\\u0A13' .. '\\u0A28' | '\\u0A2A' .. '\\u0A30' | '\\u0A32' .. '\\u0A33' | '\\u0A35' .. '\\u0A36' | '\\u0A38' .. '\\u0A39' | '\\u0A59' .. '\\u0A5C' | '\\u0A5E' | '\\u0A72' .. '\\u0A74' | '\\u0A85' .. '\\u0A8D' | '\\u0A8F' .. '\\u0A91' | '\\u0A93' .. '\\u0AA8' | '\\u0AAA' .. '\\u0AB0' | '\\u0AB2' .. '\\u0AB3' | '\\u0AB5' .. '\\u0AB9' | '\\u0ABD' | '\\u0AD0' | '\\u0AE0' .. '\\u0AE1' | '\\u0AF9' | '\\u0B05' .. '\\u0B0C' | '\\u0B0F' .. '\\u0B10' | '\\u0B13' .. '\\u0B28' | '\\u0B2A' .. '\\u0B30' | '\\u0B32' .. '\\u0B33' | '\\u0B35' .. '\\u0B39' | '\\u0B3D' | '\\u0B5C' .. '\\u0B5D' | '\\u0B5F' .. '\\u0B61' | '\\u0B71' | '\\u0B83' | '\\u0B85' .. '\\u0B8A' | '\\u0B8E' .. '\\u0B90' | '\\u0B92' .. '\\u0B95' | '\\u0B99' .. '\\u0B9A' | '\\u0B9C' | '\\u0B9E' .. '\\u0B9F' | '\\u0BA3' .. '\\u0BA4' | '\\u0BA8' .. '\\u0BAA' | '\\u0BAE' .. '\\u0BB9' | '\\u0BD0' | '\\u0C05' .. '\\u0C0C' | '\\u0C0E' .. '\\u0C10' | '\\u0C12' .. '\\u0C28' | '\\u0C2A' .. '\\u0C39' | '\\u0C3D' | '\\u0C58' .. '\\u0C5A' | '\\u0C60' .. '\\u0C61' | '\\u0C85' .. '\\u0C8C' | '\\u0C8E' .. '\\u0C90' | '\\u0C92' .. '\\u0CA8' | '\\u0CAA' .. '\\u0CB3' | '\\u0CB5' .. '\\u0CB9' | '\\u0CBD' | '\\u0CDE' | '\\u0CE0' .. '\\u0CE1' | '\\u0CF1' .. '\\u0CF2' | '\\u0D05' .. '\\u0D0C' | '\\u0D0E' .. '\\u0D10' | '\\u0D12' .. '\\u0D3A' | '\\u0D3D' | '\\u0D4E' | '\\u0D5F' .. '\\u0D61' | '\\u0D7A' .. '\\u0D7F' | '\\u0D85' .. '\\u0D96' | '\\u0D9A' .. '\\u0DB1' | '\\u0DB3' .. '\\u0DBB' | '\\u0DBD' | '\\u0DC0' .. '\\u0DC6' | '\\u0E01' .. '\\u0E30' | '\\u0E32' .. '\\u0E33' | '\\u0E40' .. '\\u0E46' | '\\u0E81' .. '\\u0E82' | '\\u0E84' | '\\u0E87' .. '\\u0E88' | '\\u0E8A' | '\\u0E8D' | '\\u0E94' .. '\\u0E97' | '\\u0E99' .. '\\u0E9F' | '\\u0EA1' .. '\\u0EA3' | '\\u0EA5' | '\\u0EA7' | '\\u0EAA' .. '\\u0EAB' | '\\u0EAD' .. '\\u0EB0' | '\\u0EB2' .. '\\u0EB3' | '\\u0EBD' | '\\u0EC0' .. '\\u0EC4' | '\\u0EC6' | '\\u0EDC' .. '\\u0EDF' | '\\u0F00' | '\\u0F40' .. '\\u0F47' | '\\u0F49' .. '\\u0F6C' | '\\u0F88' .. '\\u0F8C' | '\\u1000' .. '\\u102A' | '\\u103F' | '\\u1050' .. '\\u1055' | '\\u105A' .. '\\u105D' | '\\u1061' | '\\u1065' .. '\\u1066' | '\\u106E' .. '\\u1070' | '\\u1075' .. '\\u1081' | '\\u108E' | '\\u10A0' .. '\\u10C5' | '\\u10C7' | '\\u10CD' | '\\u10D0' .. '\\u10FA' | '\\u10FC' .. '\\u1248' | '\\u124A' .. '\\u124D' | '\\u1250' .. '\\u1256' | '\\u1258' | '\\u125A' .. '\\u125D' | '\\u1260' .. '\\u1288' | '\\u128A' .. '\\u128D' | '\\u1290' .. '\\u12B0' | '\\u12B2' .. '\\u12B5' | '\\u12B8' .. '\\u12BE' | '\\u12C0' | '\\u12C2' .. '\\u12C5' | '\\u12C8' .. '\\u12D6' | '\\u12D8' .. '\\u1310' | '\\u1312' .. '\\u1315' | '\\u1318' .. '\\u135A' | '\\u1380' .. '\\u138F' | '\\u13A0' .. '\\u13F5' | '\\u13F8' .. '\\u13FD' | '\\u1401' .. '\\u166C' | '\\u166F' .. '\\u167F' | '\\u1681' .. '\\u169A' | '\\u16A0' .. '\\u16EA' | '\\u16EE' .. '\\u16F8' | '\\u1700' .. '\\u170C' | '\\u170E' .. '\\u1711' | '\\u1720' .. '\\u1731' | '\\u1740' .. '\\u1751' | '\\u1760' .. '\\u176C' | '\\u176E' .. '\\u1770' | '\\u1780' .. '\\u17B3' | '\\u17D7' | '\\u17DC' | '\\u1820' .. '\\u1877' | '\\u1880' .. '\\u18A8' | '\\u18AA' | '\\u18B0' .. '\\u18F5' | '\\u1900' .. '\\u191E' | '\\u1950' .. '\\u196D' | '\\u1970' .. '\\u1974' | '\\u1980' .. '\\u19AB' | '\\u19B0' .. '\\u19C9' | '\\u1A00' .. '\\u1A16' | '\\u1A20' .. '\\u1A54' | '\\u1AA7' | '\\u1B05' .. '\\u1B33' | '\\u1B45' .. '\\u1B4B' | '\\u1B83' .. '\\u1BA0' | '\\u1BAE' .. '\\u1BAF' | '\\u1BBA' .. '\\u1BE5' | '\\u1C00' .. '\\u1C23' | '\\u1C4D' .. '\\u1C4F' | '\\u1C5A' .. '\\u1C7D' | '\\u1CE9' .. '\\u1CEC' | '\\u1CEE' .. '\\u1CF1' | '\\u1CF5' .. '\\u1CF6' | '\\u1D00' .. '\\u1DBF' | '\\u1E00' .. '\\u1F15' | '\\u1F18' .. '\\u1F1D' | '\\u1F20' .. '\\u1F45' | '\\u1F48' .. '\\u1F4D' | '\\u1F50' .. '\\u1F57' | '\\u1F59' | '\\u1F5B' | '\\u1F5D' | '\\u1F5F' .. '\\u1F7D' | '\\u1F80' .. '\\u1FB4' | '\\u1FB6' .. '\\u1FBC' | '\\u1FBE' | '\\u1FC2' .. '\\u1FC4' | '\\u1FC6' .. '\\u1FCC' | '\\u1FD0' .. '\\u1FD3' | '\\u1FD6' .. '\\u1FDB' | '\\u1FE0' .. '\\u1FEC' | '\\u1FF2' .. '\\u1FF4' | '\\u1FF6' .. '\\u1FFC' | '\\u2071' | '\\u207F' | '\\u2090' .. '\\u209C' | '\\u2102' | '\\u2107' | '\\u210A' .. '\\u2113' | '\\u2115' | '\\u2119' .. '\\u211D' | '\\u2124' | '\\u2126' | '\\u2128' | '\\u212A' .. '\\u212D' | '\\u212F' .. '\\u2139' | '\\u213C' .. '\\u213F' | '\\u2145' .. '\\u2149' | '\\u214E' | '\\u2160' .. '\\u2188' | '\\u2C00' .. '\\u2C2E' | '\\u2C30' .. '\\u2C5E' | '\\u2C60' .. '\\u2CE4' | '\\u2CEB' .. '\\u2CEE' | '\\u2CF2' .. '\\u2CF3' | '\\u2D00' .. '\\u2D25' | '\\u2D27' | '\\u2D2D' | '\\u2D30' .. '\\u2D67' | '\\u2D6F' | '\\u2D80' .. '\\u2D96' | '\\u2DA0' .. '\\u2DA6' | '\\u2DA8' .. '\\u2DAE' | '\\u2DB0' .. '\\u2DB6' | '\\u2DB8' .. '\\u2DBE' | '\\u2DC0' .. '\\u2DC6' | '\\u2DC8' .. '\\u2DCE' | '\\u2DD0' .. '\\u2DD6' | '\\u2DD8' .. '\\u2DDE' | '\\u2E2F' | '\\u3005' .. '\\u3007' | '\\u3021' .. '\\u3029' | '\\u3031' .. '\\u3035' | '\\u3038' .. '\\u303C' | '\\u3041' .. '\\u3096' | '\\u309D' .. '\\u309F' | '\\u30A1' .. '\\u30FA' | '\\u30FC' .. '\\u30FF' | '\\u3105' .. '\\u312D' | '\\u3131' .. '\\u318E' | '\\u31A0' .. '\\u31BA' | '\\u31F0' .. '\\u31FF' | '\\u3400' .. '\\u4DB5' | '\\u4E00' .. '\\u9FD5' | '\\uA000' .. '\\uA48C' | '\\uA4D0' .. '\\uA4FD' | '\\uA500' .. '\\uA60C' | '\\uA610' .. '\\uA61F' | '\\uA62A' .. '\\uA62B' | '\\uA640' .. '\\uA66E' | '\\uA67F' .. '\\uA69D' | '\\uA6A0' .. '\\uA6EF' | '\\uA717' .. '\\uA71F' | '\\uA722' .. '\\uA788' | '\\uA78B' .. '\\uA7AD' | '\\uA7B0' .. '\\uA7B7' | '\\uA7F7' .. '\\uA801' | '\\uA803' .. '\\uA805' | '\\uA807' .. '\\uA80A' | '\\uA80C' .. '\\uA822' | '\\uA840' .. '\\uA873' | '\\uA882' .. '\\uA8B3' | '\\uA8F2' .. '\\uA8F7' | '\\uA8FB' | '\\uA8FD' | '\\uA90A' .. '\\uA925' | '\\uA930' .. '\\uA946' | '\\uA960' .. '\\uA97C' | '\\uA984' .. '\\uA9B2' | '\\uA9CF' | '\\uA9E0' .. '\\uA9E4' | '\\uA9E6' .. '\\uA9EF' | '\\uA9FA' .. '\\uA9FE' | '\\uAA00' .. '\\uAA28' | '\\uAA40' .. '\\uAA42' | '\\uAA44' .. '\\uAA4B' | '\\uAA60' .. '\\uAA76' | '\\uAA7A' | '\\uAA7E' .. '\\uAAAF' | '\\uAAB1' | '\\uAAB5' .. '\\uAAB6' | '\\uAAB9' .. '\\uAABD' | '\\uAAC0' | '\\uAAC2' | '\\uAADB' .. '\\uAADD' | '\\uAAE0' .. '\\uAAEA' | '\\uAAF2' .. '\\uAAF4' | '\\uAB01' .. '\\uAB06' | '\\uAB09' .. '\\uAB0E' | '\\uAB11' .. '\\uAB16' | '\\uAB20' .. '\\uAB26' | '\\uAB28' .. '\\uAB2E' | '\\uAB30' .. '\\uAB5A' | '\\uAB5C' .. '\\uAB65' | '\\uAB70' .. '\\uABE2' | '\\uAC00' .. '\\uD7A3' | '\\uD7B0' .. '\\uD7C6' | '\\uD7CB' .. '\\uD7FB' | '\\uF900' .. '\\uFA6D' | '\\uFA70' .. '\\uFAD9' | '\\uFB00' .. '\\uFB06' | '\\uFB13' .. '\\uFB17' | '\\uFB1D' | '\\uFB1F' .. '\\uFB28' | '\\uFB2A' .. '\\uFB36' | '\\uFB38' .. '\\uFB3C' | '\\uFB3E' | '\\uFB40' .. '\\uFB41' | '\\uFB43' .. '\\uFB44' | '\\uFB46' .. '\\uFBB1' | '\\uFBD3' .. '\\uFD3D' | '\\uFD50' .. '\\uFD8F' | '\\uFD92' .. '\\uFDC7' | '\\uFDF0' .. '\\uFDFB' | '\\uFE70' .. '\\uFE74' | '\\uFE76' .. '\\uFEFC' | '\\uFF21' .. '\\uFF3A' | '\\uFF41' .. '\\uFF5A' | '\\uFF66' .. '\\uFFBE' | '\\uFFC2' .. '\\uFFC7' | '\\uFFCA' .. '\\uFFCF' | '\\uFFD2' .. '\\uFFD7' | '\\uFFDA' .. '\\uFFDC' )
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='\u00AA'||input.LA(1)=='\u00B5'||input.LA(1)=='\u00BA'||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u02C1')||(input.LA(1)>='\u02C6' && input.LA(1)<='\u02D1')||(input.LA(1)>='\u02E0' && input.LA(1)<='\u02E4')||input.LA(1)=='\u02EC'||input.LA(1)=='\u02EE'||(input.LA(1)>='\u0370' && input.LA(1)<='\u0374')||(input.LA(1)>='\u0376' && input.LA(1)<='\u0377')||(input.LA(1)>='\u037A' && input.LA(1)<='\u037D')||input.LA(1)=='\u037F'||input.LA(1)=='\u0386'||(input.LA(1)>='\u0388' && input.LA(1)<='\u038A')||input.LA(1)=='\u038C'||(input.LA(1)>='\u038E' && input.LA(1)<='\u03A1')||(input.LA(1)>='\u03A3' && input.LA(1)<='\u03F5')||(input.LA(1)>='\u03F7' && input.LA(1)<='\u0481')||(input.LA(1)>='\u048A' && input.LA(1)<='\u052F')||(input.LA(1)>='\u0531' && input.LA(1)<='\u0556')||input.LA(1)=='\u0559'||(input.LA(1)>='\u0561' && input.LA(1)<='\u0587')||(input.LA(1)>='\u05D0' && input.LA(1)<='\u05EA')||(input.LA(1)>='\u05F0' && input.LA(1)<='\u05F2')||(input.LA(1)>='\u0620' && input.LA(1)<='\u064A')||(input.LA(1)>='\u066E' && input.LA(1)<='\u066F')||(input.LA(1)>='\u0671' && input.LA(1)<='\u06D3')||input.LA(1)=='\u06D5'||(input.LA(1)>='\u06E5' && input.LA(1)<='\u06E6')||(input.LA(1)>='\u06EE' && input.LA(1)<='\u06EF')||(input.LA(1)>='\u06FA' && input.LA(1)<='\u06FC')||input.LA(1)=='\u06FF'||input.LA(1)=='\u0710'||(input.LA(1)>='\u0712' && input.LA(1)<='\u072F')||(input.LA(1)>='\u074D' && input.LA(1)<='\u07A5')||input.LA(1)=='\u07B1'||(input.LA(1)>='\u07CA' && input.LA(1)<='\u07EA')||(input.LA(1)>='\u07F4' && input.LA(1)<='\u07F5')||input.LA(1)=='\u07FA'||(input.LA(1)>='\u0800' && input.LA(1)<='\u0815')||input.LA(1)=='\u081A'||input.LA(1)=='\u0824'||input.LA(1)=='\u0828'||(input.LA(1)>='\u0840' && input.LA(1)<='\u0858')||(input.LA(1)>='\u08A0' && input.LA(1)<='\u08B4')||(input.LA(1)>='\u0904' && input.LA(1)<='\u0939')||input.LA(1)=='\u093D'||input.LA(1)=='\u0950'||(input.LA(1)>='\u0958' && input.LA(1)<='\u0961')||(input.LA(1)>='\u0971' && input.LA(1)<='\u0980')||(input.LA(1)>='\u0985' && input.LA(1)<='\u098C')||(input.LA(1)>='\u098F' && input.LA(1)<='\u0990')||(input.LA(1)>='\u0993' && input.LA(1)<='\u09A8')||(input.LA(1)>='\u09AA' && input.LA(1)<='\u09B0')||input.LA(1)=='\u09B2'||(input.LA(1)>='\u09B6' && input.LA(1)<='\u09B9')||input.LA(1)=='\u09BD'||input.LA(1)=='\u09CE'||(input.LA(1)>='\u09DC' && input.LA(1)<='\u09DD')||(input.LA(1)>='\u09DF' && input.LA(1)<='\u09E1')||(input.LA(1)>='\u09F0' && input.LA(1)<='\u09F1')||(input.LA(1)>='\u0A05' && input.LA(1)<='\u0A0A')||(input.LA(1)>='\u0A0F' && input.LA(1)<='\u0A10')||(input.LA(1)>='\u0A13' && input.LA(1)<='\u0A28')||(input.LA(1)>='\u0A2A' && input.LA(1)<='\u0A30')||(input.LA(1)>='\u0A32' && input.LA(1)<='\u0A33')||(input.LA(1)>='\u0A35' && input.LA(1)<='\u0A36')||(input.LA(1)>='\u0A38' && input.LA(1)<='\u0A39')||(input.LA(1)>='\u0A59' && input.LA(1)<='\u0A5C')||input.LA(1)=='\u0A5E'||(input.LA(1)>='\u0A72' && input.LA(1)<='\u0A74')||(input.LA(1)>='\u0A85' && input.LA(1)<='\u0A8D')||(input.LA(1)>='\u0A8F' && input.LA(1)<='\u0A91')||(input.LA(1)>='\u0A93' && input.LA(1)<='\u0AA8')||(input.LA(1)>='\u0AAA' && input.LA(1)<='\u0AB0')||(input.LA(1)>='\u0AB2' && input.LA(1)<='\u0AB3')||(input.LA(1)>='\u0AB5' && input.LA(1)<='\u0AB9')||input.LA(1)=='\u0ABD'||input.LA(1)=='\u0AD0'||(input.LA(1)>='\u0AE0' && input.LA(1)<='\u0AE1')||input.LA(1)=='\u0AF9'||(input.LA(1)>='\u0B05' && input.LA(1)<='\u0B0C')||(input.LA(1)>='\u0B0F' && input.LA(1)<='\u0B10')||(input.LA(1)>='\u0B13' && input.LA(1)<='\u0B28')||(input.LA(1)>='\u0B2A' && input.LA(1)<='\u0B30')||(input.LA(1)>='\u0B32' && input.LA(1)<='\u0B33')||(input.LA(1)>='\u0B35' && input.LA(1)<='\u0B39')||input.LA(1)=='\u0B3D'||(input.LA(1)>='\u0B5C' && input.LA(1)<='\u0B5D')||(input.LA(1)>='\u0B5F' && input.LA(1)<='\u0B61')||input.LA(1)=='\u0B71'||input.LA(1)=='\u0B83'||(input.LA(1)>='\u0B85' && input.LA(1)<='\u0B8A')||(input.LA(1)>='\u0B8E' && input.LA(1)<='\u0B90')||(input.LA(1)>='\u0B92' && input.LA(1)<='\u0B95')||(input.LA(1)>='\u0B99' && input.LA(1)<='\u0B9A')||input.LA(1)=='\u0B9C'||(input.LA(1)>='\u0B9E' && input.LA(1)<='\u0B9F')||(input.LA(1)>='\u0BA3' && input.LA(1)<='\u0BA4')||(input.LA(1)>='\u0BA8' && input.LA(1)<='\u0BAA')||(input.LA(1)>='\u0BAE' && input.LA(1)<='\u0BB9')||input.LA(1)=='\u0BD0'||(input.LA(1)>='\u0C05' && input.LA(1)<='\u0C0C')||(input.LA(1)>='\u0C0E' && input.LA(1)<='\u0C10')||(input.LA(1)>='\u0C12' && input.LA(1)<='\u0C28')||(input.LA(1)>='\u0C2A' && input.LA(1)<='\u0C39')||input.LA(1)=='\u0C3D'||(input.LA(1)>='\u0C58' && input.LA(1)<='\u0C5A')||(input.LA(1)>='\u0C60' && input.LA(1)<='\u0C61')||(input.LA(1)>='\u0C85' && input.LA(1)<='\u0C8C')||(input.LA(1)>='\u0C8E' && input.LA(1)<='\u0C90')||(input.LA(1)>='\u0C92' && input.LA(1)<='\u0CA8')||(input.LA(1)>='\u0CAA' && input.LA(1)<='\u0CB3')||(input.LA(1)>='\u0CB5' && input.LA(1)<='\u0CB9')||input.LA(1)=='\u0CBD'||input.LA(1)=='\u0CDE'||(input.LA(1)>='\u0CE0' && input.LA(1)<='\u0CE1')||(input.LA(1)>='\u0CF1' && input.LA(1)<='\u0CF2')||(input.LA(1)>='\u0D05' && input.LA(1)<='\u0D0C')||(input.LA(1)>='\u0D0E' && input.LA(1)<='\u0D10')||(input.LA(1)>='\u0D12' && input.LA(1)<='\u0D3A')||input.LA(1)=='\u0D3D'||input.LA(1)=='\u0D4E'||(input.LA(1)>='\u0D5F' && input.LA(1)<='\u0D61')||(input.LA(1)>='\u0D7A' && input.LA(1)<='\u0D7F')||(input.LA(1)>='\u0D85' && input.LA(1)<='\u0D96')||(input.LA(1)>='\u0D9A' && input.LA(1)<='\u0DB1')||(input.LA(1)>='\u0DB3' && input.LA(1)<='\u0DBB')||input.LA(1)=='\u0DBD'||(input.LA(1)>='\u0DC0' && input.LA(1)<='\u0DC6')||(input.LA(1)>='\u0E01' && input.LA(1)<='\u0E30')||(input.LA(1)>='\u0E32' && input.LA(1)<='\u0E33')||(input.LA(1)>='\u0E40' && input.LA(1)<='\u0E46')||(input.LA(1)>='\u0E81' && input.LA(1)<='\u0E82')||input.LA(1)=='\u0E84'||(input.LA(1)>='\u0E87' && input.LA(1)<='\u0E88')||input.LA(1)=='\u0E8A'||input.LA(1)=='\u0E8D'||(input.LA(1)>='\u0E94' && input.LA(1)<='\u0E97')||(input.LA(1)>='\u0E99' && input.LA(1)<='\u0E9F')||(input.LA(1)>='\u0EA1' && input.LA(1)<='\u0EA3')||input.LA(1)=='\u0EA5'||input.LA(1)=='\u0EA7'||(input.LA(1)>='\u0EAA' && input.LA(1)<='\u0EAB')||(input.LA(1)>='\u0EAD' && input.LA(1)<='\u0EB0')||(input.LA(1)>='\u0EB2' && input.LA(1)<='\u0EB3')||input.LA(1)=='\u0EBD'||(input.LA(1)>='\u0EC0' && input.LA(1)<='\u0EC4')||input.LA(1)=='\u0EC6'||(input.LA(1)>='\u0EDC' && input.LA(1)<='\u0EDF')||input.LA(1)=='\u0F00'||(input.LA(1)>='\u0F40' && input.LA(1)<='\u0F47')||(input.LA(1)>='\u0F49' && input.LA(1)<='\u0F6C')||(input.LA(1)>='\u0F88' && input.LA(1)<='\u0F8C')||(input.LA(1)>='\u1000' && input.LA(1)<='\u102A')||input.LA(1)=='\u103F'||(input.LA(1)>='\u1050' && input.LA(1)<='\u1055')||(input.LA(1)>='\u105A' && input.LA(1)<='\u105D')||input.LA(1)=='\u1061'||(input.LA(1)>='\u1065' && input.LA(1)<='\u1066')||(input.LA(1)>='\u106E' && input.LA(1)<='\u1070')||(input.LA(1)>='\u1075' && input.LA(1)<='\u1081')||input.LA(1)=='\u108E'||(input.LA(1)>='\u10A0' && input.LA(1)<='\u10C5')||input.LA(1)=='\u10C7'||input.LA(1)=='\u10CD'||(input.LA(1)>='\u10D0' && input.LA(1)<='\u10FA')||(input.LA(1)>='\u10FC' && input.LA(1)<='\u1248')||(input.LA(1)>='\u124A' && input.LA(1)<='\u124D')||(input.LA(1)>='\u1250' && input.LA(1)<='\u1256')||input.LA(1)=='\u1258'||(input.LA(1)>='\u125A' && input.LA(1)<='\u125D')||(input.LA(1)>='\u1260' && input.LA(1)<='\u1288')||(input.LA(1)>='\u128A' && input.LA(1)<='\u128D')||(input.LA(1)>='\u1290' && input.LA(1)<='\u12B0')||(input.LA(1)>='\u12B2' && input.LA(1)<='\u12B5')||(input.LA(1)>='\u12B8' && input.LA(1)<='\u12BE')||input.LA(1)=='\u12C0'||(input.LA(1)>='\u12C2' && input.LA(1)<='\u12C5')||(input.LA(1)>='\u12C8' && input.LA(1)<='\u12D6')||(input.LA(1)>='\u12D8' && input.LA(1)<='\u1310')||(input.LA(1)>='\u1312' && input.LA(1)<='\u1315')||(input.LA(1)>='\u1318' && input.LA(1)<='\u135A')||(input.LA(1)>='\u1380' && input.LA(1)<='\u138F')||(input.LA(1)>='\u13A0' && input.LA(1)<='\u13F5')||(input.LA(1)>='\u13F8' && input.LA(1)<='\u13FD')||(input.LA(1)>='\u1401' && input.LA(1)<='\u166C')||(input.LA(1)>='\u166F' && input.LA(1)<='\u167F')||(input.LA(1)>='\u1681' && input.LA(1)<='\u169A')||(input.LA(1)>='\u16A0' && input.LA(1)<='\u16EA')||(input.LA(1)>='\u16EE' && input.LA(1)<='\u16F8')||(input.LA(1)>='\u1700' && input.LA(1)<='\u170C')||(input.LA(1)>='\u170E' && input.LA(1)<='\u1711')||(input.LA(1)>='\u1720' && input.LA(1)<='\u1731')||(input.LA(1)>='\u1740' && input.LA(1)<='\u1751')||(input.LA(1)>='\u1760' && input.LA(1)<='\u176C')||(input.LA(1)>='\u176E' && input.LA(1)<='\u1770')||(input.LA(1)>='\u1780' && input.LA(1)<='\u17B3')||input.LA(1)=='\u17D7'||input.LA(1)=='\u17DC'||(input.LA(1)>='\u1820' && input.LA(1)<='\u1877')||(input.LA(1)>='\u1880' && input.LA(1)<='\u18A8')||input.LA(1)=='\u18AA'||(input.LA(1)>='\u18B0' && input.LA(1)<='\u18F5')||(input.LA(1)>='\u1900' && input.LA(1)<='\u191E')||(input.LA(1)>='\u1950' && input.LA(1)<='\u196D')||(input.LA(1)>='\u1970' && input.LA(1)<='\u1974')||(input.LA(1)>='\u1980' && input.LA(1)<='\u19AB')||(input.LA(1)>='\u19B0' && input.LA(1)<='\u19C9')||(input.LA(1)>='\u1A00' && input.LA(1)<='\u1A16')||(input.LA(1)>='\u1A20' && input.LA(1)<='\u1A54')||input.LA(1)=='\u1AA7'||(input.LA(1)>='\u1B05' && input.LA(1)<='\u1B33')||(input.LA(1)>='\u1B45' && input.LA(1)<='\u1B4B')||(input.LA(1)>='\u1B83' && input.LA(1)<='\u1BA0')||(input.LA(1)>='\u1BAE' && input.LA(1)<='\u1BAF')||(input.LA(1)>='\u1BBA' && input.LA(1)<='\u1BE5')||(input.LA(1)>='\u1C00' && input.LA(1)<='\u1C23')||(input.LA(1)>='\u1C4D' && input.LA(1)<='\u1C4F')||(input.LA(1)>='\u1C5A' && input.LA(1)<='\u1C7D')||(input.LA(1)>='\u1CE9' && input.LA(1)<='\u1CEC')||(input.LA(1)>='\u1CEE' && input.LA(1)<='\u1CF1')||(input.LA(1)>='\u1CF5' && input.LA(1)<='\u1CF6')||(input.LA(1)>='\u1D00' && input.LA(1)<='\u1DBF')||(input.LA(1)>='\u1E00' && input.LA(1)<='\u1F15')||(input.LA(1)>='\u1F18' && input.LA(1)<='\u1F1D')||(input.LA(1)>='\u1F20' && input.LA(1)<='\u1F45')||(input.LA(1)>='\u1F48' && input.LA(1)<='\u1F4D')||(input.LA(1)>='\u1F50' && input.LA(1)<='\u1F57')||input.LA(1)=='\u1F59'||input.LA(1)=='\u1F5B'||input.LA(1)=='\u1F5D'||(input.LA(1)>='\u1F5F' && input.LA(1)<='\u1F7D')||(input.LA(1)>='\u1F80' && input.LA(1)<='\u1FB4')||(input.LA(1)>='\u1FB6' && input.LA(1)<='\u1FBC')||input.LA(1)=='\u1FBE'||(input.LA(1)>='\u1FC2' && input.LA(1)<='\u1FC4')||(input.LA(1)>='\u1FC6' && input.LA(1)<='\u1FCC')||(input.LA(1)>='\u1FD0' && input.LA(1)<='\u1FD3')||(input.LA(1)>='\u1FD6' && input.LA(1)<='\u1FDB')||(input.LA(1)>='\u1FE0' && input.LA(1)<='\u1FEC')||(input.LA(1)>='\u1FF2' && input.LA(1)<='\u1FF4')||(input.LA(1)>='\u1FF6' && input.LA(1)<='\u1FFC')||input.LA(1)=='\u2071'||input.LA(1)=='\u207F'||(input.LA(1)>='\u2090' && input.LA(1)<='\u209C')||input.LA(1)=='\u2102'||input.LA(1)=='\u2107'||(input.LA(1)>='\u210A' && input.LA(1)<='\u2113')||input.LA(1)=='\u2115'||(input.LA(1)>='\u2119' && input.LA(1)<='\u211D')||input.LA(1)=='\u2124'||input.LA(1)=='\u2126'||input.LA(1)=='\u2128'||(input.LA(1)>='\u212A' && input.LA(1)<='\u212D')||(input.LA(1)>='\u212F' && input.LA(1)<='\u2139')||(input.LA(1)>='\u213C' && input.LA(1)<='\u213F')||(input.LA(1)>='\u2145' && input.LA(1)<='\u2149')||input.LA(1)=='\u214E'||(input.LA(1)>='\u2160' && input.LA(1)<='\u2188')||(input.LA(1)>='\u2C00' && input.LA(1)<='\u2C2E')||(input.LA(1)>='\u2C30' && input.LA(1)<='\u2C5E')||(input.LA(1)>='\u2C60' && input.LA(1)<='\u2CE4')||(input.LA(1)>='\u2CEB' && input.LA(1)<='\u2CEE')||(input.LA(1)>='\u2CF2' && input.LA(1)<='\u2CF3')||(input.LA(1)>='\u2D00' && input.LA(1)<='\u2D25')||input.LA(1)=='\u2D27'||input.LA(1)=='\u2D2D'||(input.LA(1)>='\u2D30' && input.LA(1)<='\u2D67')||input.LA(1)=='\u2D6F'||(input.LA(1)>='\u2D80' && input.LA(1)<='\u2D96')||(input.LA(1)>='\u2DA0' && input.LA(1)<='\u2DA6')||(input.LA(1)>='\u2DA8' && input.LA(1)<='\u2DAE')||(input.LA(1)>='\u2DB0' && input.LA(1)<='\u2DB6')||(input.LA(1)>='\u2DB8' && input.LA(1)<='\u2DBE')||(input.LA(1)>='\u2DC0' && input.LA(1)<='\u2DC6')||(input.LA(1)>='\u2DC8' && input.LA(1)<='\u2DCE')||(input.LA(1)>='\u2DD0' && input.LA(1)<='\u2DD6')||(input.LA(1)>='\u2DD8' && input.LA(1)<='\u2DDE')||input.LA(1)=='\u2E2F'||(input.LA(1)>='\u3005' && input.LA(1)<='\u3007')||(input.LA(1)>='\u3021' && input.LA(1)<='\u3029')||(input.LA(1)>='\u3031' && input.LA(1)<='\u3035')||(input.LA(1)>='\u3038' && input.LA(1)<='\u303C')||(input.LA(1)>='\u3041' && input.LA(1)<='\u3096')||(input.LA(1)>='\u309D' && input.LA(1)<='\u309F')||(input.LA(1)>='\u30A1' && input.LA(1)<='\u30FA')||(input.LA(1)>='\u30FC' && input.LA(1)<='\u30FF')||(input.LA(1)>='\u3105' && input.LA(1)<='\u312D')||(input.LA(1)>='\u3131' && input.LA(1)<='\u318E')||(input.LA(1)>='\u31A0' && input.LA(1)<='\u31BA')||(input.LA(1)>='\u31F0' && input.LA(1)<='\u31FF')||(input.LA(1)>='\u3400' && input.LA(1)<='\u4DB5')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FD5')||(input.LA(1)>='\uA000' && input.LA(1)<='\uA48C')||(input.LA(1)>='\uA4D0' && input.LA(1)<='\uA4FD')||(input.LA(1)>='\uA500' && input.LA(1)<='\uA60C')||(input.LA(1)>='\uA610' && input.LA(1)<='\uA61F')||(input.LA(1)>='\uA62A' && input.LA(1)<='\uA62B')||(input.LA(1)>='\uA640' && input.LA(1)<='\uA66E')||(input.LA(1)>='\uA67F' && input.LA(1)<='\uA69D')||(input.LA(1)>='\uA6A0' && input.LA(1)<='\uA6EF')||(input.LA(1)>='\uA717' && input.LA(1)<='\uA71F')||(input.LA(1)>='\uA722' && input.LA(1)<='\uA788')||(input.LA(1)>='\uA78B' && input.LA(1)<='\uA7AD')||(input.LA(1)>='\uA7B0' && input.LA(1)<='\uA7B7')||(input.LA(1)>='\uA7F7' && input.LA(1)<='\uA801')||(input.LA(1)>='\uA803' && input.LA(1)<='\uA805')||(input.LA(1)>='\uA807' && input.LA(1)<='\uA80A')||(input.LA(1)>='\uA80C' && input.LA(1)<='\uA822')||(input.LA(1)>='\uA840' && input.LA(1)<='\uA873')||(input.LA(1)>='\uA882' && input.LA(1)<='\uA8B3')||(input.LA(1)>='\uA8F2' && input.LA(1)<='\uA8F7')||input.LA(1)=='\uA8FB'||input.LA(1)=='\uA8FD'||(input.LA(1)>='\uA90A' && input.LA(1)<='\uA925')||(input.LA(1)>='\uA930' && input.LA(1)<='\uA946')||(input.LA(1)>='\uA960' && input.LA(1)<='\uA97C')||(input.LA(1)>='\uA984' && input.LA(1)<='\uA9B2')||input.LA(1)=='\uA9CF'||(input.LA(1)>='\uA9E0' && input.LA(1)<='\uA9E4')||(input.LA(1)>='\uA9E6' && input.LA(1)<='\uA9EF')||(input.LA(1)>='\uA9FA' && input.LA(1)<='\uA9FE')||(input.LA(1)>='\uAA00' && input.LA(1)<='\uAA28')||(input.LA(1)>='\uAA40' && input.LA(1)<='\uAA42')||(input.LA(1)>='\uAA44' && input.LA(1)<='\uAA4B')||(input.LA(1)>='\uAA60' && input.LA(1)<='\uAA76')||input.LA(1)=='\uAA7A'||(input.LA(1)>='\uAA7E' && input.LA(1)<='\uAAAF')||input.LA(1)=='\uAAB1'||(input.LA(1)>='\uAAB5' && input.LA(1)<='\uAAB6')||(input.LA(1)>='\uAAB9' && input.LA(1)<='\uAABD')||input.LA(1)=='\uAAC0'||input.LA(1)=='\uAAC2'||(input.LA(1)>='\uAADB' && input.LA(1)<='\uAADD')||(input.LA(1)>='\uAAE0' && input.LA(1)<='\uAAEA')||(input.LA(1)>='\uAAF2' && input.LA(1)<='\uAAF4')||(input.LA(1)>='\uAB01' && input.LA(1)<='\uAB06')||(input.LA(1)>='\uAB09' && input.LA(1)<='\uAB0E')||(input.LA(1)>='\uAB11' && input.LA(1)<='\uAB16')||(input.LA(1)>='\uAB20' && input.LA(1)<='\uAB26')||(input.LA(1)>='\uAB28' && input.LA(1)<='\uAB2E')||(input.LA(1)>='\uAB30' && input.LA(1)<='\uAB5A')||(input.LA(1)>='\uAB5C' && input.LA(1)<='\uAB65')||(input.LA(1)>='\uAB70' && input.LA(1)<='\uABE2')||(input.LA(1)>='\uAC00' && input.LA(1)<='\uD7A3')||(input.LA(1)>='\uD7B0' && input.LA(1)<='\uD7C6')||(input.LA(1)>='\uD7CB' && input.LA(1)<='\uD7FB')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFA6D')||(input.LA(1)>='\uFA70' && input.LA(1)<='\uFAD9')||(input.LA(1)>='\uFB00' && input.LA(1)<='\uFB06')||(input.LA(1)>='\uFB13' && input.LA(1)<='\uFB17')||input.LA(1)=='\uFB1D'||(input.LA(1)>='\uFB1F' && input.LA(1)<='\uFB28')||(input.LA(1)>='\uFB2A' && input.LA(1)<='\uFB36')||(input.LA(1)>='\uFB38' && input.LA(1)<='\uFB3C')||input.LA(1)=='\uFB3E'||(input.LA(1)>='\uFB40' && input.LA(1)<='\uFB41')||(input.LA(1)>='\uFB43' && input.LA(1)<='\uFB44')||(input.LA(1)>='\uFB46' && input.LA(1)<='\uFBB1')||(input.LA(1)>='\uFBD3' && input.LA(1)<='\uFD3D')||(input.LA(1)>='\uFD50' && input.LA(1)<='\uFD8F')||(input.LA(1)>='\uFD92' && input.LA(1)<='\uFDC7')||(input.LA(1)>='\uFDF0' && input.LA(1)<='\uFDFB')||(input.LA(1)>='\uFE70' && input.LA(1)<='\uFE74')||(input.LA(1)>='\uFE76' && input.LA(1)<='\uFEFC')||(input.LA(1)>='\uFF21' && input.LA(1)<='\uFF3A')||(input.LA(1)>='\uFF41' && input.LA(1)<='\uFF5A')||(input.LA(1)>='\uFF66' && input.LA(1)<='\uFFBE')||(input.LA(1)>='\uFFC2' && input.LA(1)<='\uFFC7')||(input.LA(1)>='\uFFCA' && input.LA(1)<='\uFFCF')||(input.LA(1)>='\uFFD2' && input.LA(1)<='\uFFD7')||(input.LA(1)>='\uFFDA' && input.LA(1)<='\uFFDC') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_UNICODE_LETTER_FRAGMENT"

    // $ANTLR start "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT"
    public final void mRULE_UNICODE_SPACE_SEPARATOR_FRAGMENT() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:113:48: ( ( ' ' | '\\u00A0' | '\\u1680' | '\\u2000' .. '\\u200A' | '\\u202F' | '\\u205F' | '\\u3000' ) )
            // InternalRegularExpressionLexer.g:113:50: ( ' ' | '\\u00A0' | '\\u1680' | '\\u2000' .. '\\u200A' | '\\u202F' | '\\u205F' | '\\u3000' )
            {
            if ( input.LA(1)==' '||input.LA(1)=='\u00A0'||input.LA(1)=='\u1680'||(input.LA(1)>='\u2000' && input.LA(1)<='\u200A')||input.LA(1)=='\u202F'||input.LA(1)=='\u205F'||input.LA(1)=='\u3000' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            // InternalRegularExpressionLexer.g:115:25: ( . )
            // InternalRegularExpressionLexer.g:115:27: .
            {
            matchAny(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalRegularExpressionLexer.g:1:8: ( ExclamationMark | DollarSign | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | EqualsSign | QuestionMark | LeftSquareBracket | RightSquareBracket | CircumflexAccent | LeftCurlyBracket | VerticalLine | RightCurlyBracket | RULE_WORD_BOUNDARY | RULE_NOT_WORD_BOUNDARY | RULE_CHARACTER_CLASS_ESCAPE | RULE_CONTROL_ESCAPE | RULE_CONTROL_LETTER_ESCAPE | RULE_HEX_ESCAPE | RULE_UNICODE_ESCAPE | RULE_DECIMAL_ESCAPE | RULE_IDENTITY_ESCAPE | RULE_UNICODE_DIGIT | RULE_UNICODE_LETTER | RULE_PATTERN_CHARACTER_NO_DASH )
        int alt17=31;
        alt17 = dfa17.predict(input);
        switch (alt17) {
            case 1 :
                // InternalRegularExpressionLexer.g:1:10: ExclamationMark
                {
                mExclamationMark(); 

                }
                break;
            case 2 :
                // InternalRegularExpressionLexer.g:1:26: DollarSign
                {
                mDollarSign(); 

                }
                break;
            case 3 :
                // InternalRegularExpressionLexer.g:1:37: LeftParenthesis
                {
                mLeftParenthesis(); 

                }
                break;
            case 4 :
                // InternalRegularExpressionLexer.g:1:53: RightParenthesis
                {
                mRightParenthesis(); 

                }
                break;
            case 5 :
                // InternalRegularExpressionLexer.g:1:70: Asterisk
                {
                mAsterisk(); 

                }
                break;
            case 6 :
                // InternalRegularExpressionLexer.g:1:79: PlusSign
                {
                mPlusSign(); 

                }
                break;
            case 7 :
                // InternalRegularExpressionLexer.g:1:88: Comma
                {
                mComma(); 

                }
                break;
            case 8 :
                // InternalRegularExpressionLexer.g:1:94: HyphenMinus
                {
                mHyphenMinus(); 

                }
                break;
            case 9 :
                // InternalRegularExpressionLexer.g:1:106: FullStop
                {
                mFullStop(); 

                }
                break;
            case 10 :
                // InternalRegularExpressionLexer.g:1:115: Solidus
                {
                mSolidus(); 

                }
                break;
            case 11 :
                // InternalRegularExpressionLexer.g:1:123: Colon
                {
                mColon(); 

                }
                break;
            case 12 :
                // InternalRegularExpressionLexer.g:1:129: EqualsSign
                {
                mEqualsSign(); 

                }
                break;
            case 13 :
                // InternalRegularExpressionLexer.g:1:140: QuestionMark
                {
                mQuestionMark(); 

                }
                break;
            case 14 :
                // InternalRegularExpressionLexer.g:1:153: LeftSquareBracket
                {
                mLeftSquareBracket(); 

                }
                break;
            case 15 :
                // InternalRegularExpressionLexer.g:1:171: RightSquareBracket
                {
                mRightSquareBracket(); 

                }
                break;
            case 16 :
                // InternalRegularExpressionLexer.g:1:190: CircumflexAccent
                {
                mCircumflexAccent(); 

                }
                break;
            case 17 :
                // InternalRegularExpressionLexer.g:1:207: LeftCurlyBracket
                {
                mLeftCurlyBracket(); 

                }
                break;
            case 18 :
                // InternalRegularExpressionLexer.g:1:224: VerticalLine
                {
                mVerticalLine(); 

                }
                break;
            case 19 :
                // InternalRegularExpressionLexer.g:1:237: RightCurlyBracket
                {
                mRightCurlyBracket(); 

                }
                break;
            case 20 :
                // InternalRegularExpressionLexer.g:1:255: RULE_WORD_BOUNDARY
                {
                mRULE_WORD_BOUNDARY(); 

                }
                break;
            case 21 :
                // InternalRegularExpressionLexer.g:1:274: RULE_NOT_WORD_BOUNDARY
                {
                mRULE_NOT_WORD_BOUNDARY(); 

                }
                break;
            case 22 :
                // InternalRegularExpressionLexer.g:1:297: RULE_CHARACTER_CLASS_ESCAPE
                {
                mRULE_CHARACTER_CLASS_ESCAPE(); 

                }
                break;
            case 23 :
                // InternalRegularExpressionLexer.g:1:325: RULE_CONTROL_ESCAPE
                {
                mRULE_CONTROL_ESCAPE(); 

                }
                break;
            case 24 :
                // InternalRegularExpressionLexer.g:1:345: RULE_CONTROL_LETTER_ESCAPE
                {
                mRULE_CONTROL_LETTER_ESCAPE(); 

                }
                break;
            case 25 :
                // InternalRegularExpressionLexer.g:1:372: RULE_HEX_ESCAPE
                {
                mRULE_HEX_ESCAPE(); 

                }
                break;
            case 26 :
                // InternalRegularExpressionLexer.g:1:388: RULE_UNICODE_ESCAPE
                {
                mRULE_UNICODE_ESCAPE(); 

                }
                break;
            case 27 :
                // InternalRegularExpressionLexer.g:1:408: RULE_DECIMAL_ESCAPE
                {
                mRULE_DECIMAL_ESCAPE(); 

                }
                break;
            case 28 :
                // InternalRegularExpressionLexer.g:1:428: RULE_IDENTITY_ESCAPE
                {
                mRULE_IDENTITY_ESCAPE(); 

                }
                break;
            case 29 :
                // InternalRegularExpressionLexer.g:1:449: RULE_UNICODE_DIGIT
                {
                mRULE_UNICODE_DIGIT(); 

                }
                break;
            case 30 :
                // InternalRegularExpressionLexer.g:1:468: RULE_UNICODE_LETTER
                {
                mRULE_UNICODE_LETTER(); 

                }
                break;
            case 31 :
                // InternalRegularExpressionLexer.g:1:488: RULE_PATTERN_CHARACTER_NO_DASH
                {
                mRULE_PATTERN_CHARACTER_NO_DASH(); 

                }
                break;

        }

    }


    protected DFA17 dfa17 = new DFA17(this);
    static final String DFA17_eotS =
        "\24\uffff\1\47\34\uffff";
    static final String DFA17_eofS =
        "\61\uffff";
    static final String DFA17_minS =
        "\1\0\23\uffff\1\0\34\uffff";
    static final String DFA17_maxS =
        "\1\uffff\23\uffff\1\uffff\34\uffff";
    static final String DFA17_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\uffff\1\35\1\36\1\37\1\1\1\7\1\12\1\13\1\14\1\24\1\25\1\26\1\27\1\30\1\31\2\33\1\32\1\34\1\32\1\35\1\36\1\24\1\25\1\26\1\27\1\30\1\31\1\33";
    static final String DFA17_specialS =
        "\1\1\23\uffff\1\0\34\uffff}>";
    static final String[] DFA17_transitionS = {
            "\41\27\1\1\2\27\1\2\3\27\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\12\25\1\13\2\27\1\14\1\27\1\15\1\27\32\26\1\16\1\24\1\17\1\20\2\27\32\26\1\21\1\22\1\23\54\27\1\26\12\27\1\26\4\27\1\26\5\27\27\26\1\27\37\26\1\27\u01ca\26\4\27\14\26\16\27\5\26\7\27\1\26\1\27\1\26\u0081\27\5\26\1\27\2\26\2\27\4\26\1\27\1\26\6\27\1\26\1\27\3\26\1\27\1\26\1\27\24\26\1\27\123\26\1\27\u008b\26\10\27\u00a6\26\1\27\46\26\2\27\1\26\7\27\47\26\110\27\33\26\5\27\3\26\55\27\53\26\25\27\12\25\4\27\2\26\1\27\143\26\1\27\1\26\17\27\2\26\7\27\2\26\12\25\3\26\2\27\1\26\20\27\1\26\1\27\36\26\35\27\131\26\13\27\1\26\16\27\12\25\41\26\11\27\2\26\4\27\1\26\5\27\26\26\4\27\1\26\11\27\1\26\3\27\1\26\27\27\31\26\107\27\25\26\117\27\66\26\3\27\1\26\22\27\1\26\7\27\12\26\4\27\12\25\1\27\20\26\4\27\10\26\2\27\2\26\2\27\26\26\1\27\7\26\1\27\1\26\3\27\4\26\3\27\1\26\20\27\1\26\15\27\2\26\1\27\3\26\4\27\12\25\2\26\23\27\6\26\4\27\2\26\2\27\26\26\1\27\7\26\1\27\2\26\1\27\2\26\1\27\2\26\37\27\4\26\1\27\1\26\7\27\12\25\2\27\3\26\20\27\11\26\1\27\3\26\1\27\26\26\1\27\7\26\1\27\2\26\1\27\5\26\3\27\1\26\22\27\1\26\17\27\2\26\4\27\12\25\11\27\1\26\13\27\10\26\2\27\2\26\2\27\26\26\1\27\7\26\1\27\2\26\1\27\5\26\3\27\1\26\36\27\2\26\1\27\3\26\4\27\12\25\1\27\1\26\21\27\1\26\1\27\6\26\3\27\3\26\1\27\4\26\3\27\2\26\1\27\1\26\1\27\2\26\3\27\2\26\3\27\3\26\3\27\14\26\26\27\1\26\25\27\12\25\25\27\10\26\1\27\3\26\1\27\27\26\1\27\20\26\3\27\1\26\32\27\3\26\5\27\2\26\4\27\12\25\25\27\10\26\1\27\3\26\1\27\27\26\1\27\12\26\1\27\5\26\3\27\1\26\40\27\1\26\1\27\2\26\4\27\12\25\1\27\2\26\22\27\10\26\1\27\3\26\1\27\51\26\2\27\1\26\20\27\1\26\20\27\3\26\4\27\12\25\12\27\6\26\5\27\22\26\3\27\30\26\1\27\11\26\1\27\1\26\2\27\7\26\37\27\12\25\21\27\60\26\1\27\2\26\14\27\7\26\11\27\12\25\47\27\2\26\1\27\1\26\2\27\2\26\1\27\1\26\2\27\1\26\6\27\4\26\1\27\7\26\1\27\3\26\1\27\1\26\1\27\1\26\2\27\2\26\1\27\4\26\1\27\2\26\11\27\1\26\2\27\5\26\1\27\1\26\11\27\12\25\2\27\4\26\40\27\1\26\37\27\12\25\26\27\10\26\1\27\44\26\33\27\5\26\163\27\53\26\24\27\1\26\12\25\6\27\6\26\4\27\4\26\3\27\1\26\3\27\2\26\7\27\3\26\4\27\15\26\14\27\1\26\1\27\12\25\6\27\46\26\1\27\1\26\5\27\1\26\2\27\53\26\1\27\u014d\26\1\27\4\26\2\27\7\26\1\27\1\26\1\27\4\26\2\27\51\26\1\27\4\26\2\27\41\26\1\27\4\26\2\27\7\26\1\27\1\26\1\27\4\26\2\27\17\26\1\27\71\26\1\27\4\26\2\27\103\26\45\27\20\26\20\27\126\26\2\27\6\26\3\27\u026c\26\2\27\21\26\1\27\32\26\5\27\113\26\3\27\13\26\7\27\15\26\1\27\4\26\16\27\22\26\16\27\22\26\16\27\15\26\1\27\3\26\17\27\64\26\43\27\1\26\4\27\1\26\3\27\12\25\46\27\12\25\6\27\130\26\10\27\51\26\1\27\1\26\5\27\106\26\12\27\37\26\47\27\12\25\36\26\2\27\5\26\13\27\54\26\4\27\32\26\6\27\12\25\46\27\27\26\11\27\65\26\53\27\12\25\6\27\12\25\15\27\1\26\135\27\57\26\21\27\7\26\4\27\12\25\51\27\36\26\15\27\2\26\12\25\54\26\32\27\44\26\34\27\12\25\3\27\3\26\12\25\44\26\153\27\4\26\1\27\4\26\3\27\2\26\11\27\u00c0\26\100\27\u0116\26\2\27\6\26\2\27\46\26\2\27\6\26\2\27\10\26\1\27\1\26\1\27\1\26\1\27\1\26\1\27\37\26\2\27\65\26\1\27\7\26\1\27\1\26\3\27\3\26\1\27\7\26\3\27\4\26\2\27\6\26\4\27\15\26\5\27\3\26\1\27\7\26\164\27\1\26\15\27\1\26\20\27\15\26\145\27\1\26\4\27\1\26\2\27\12\26\1\27\1\26\3\27\5\26\6\27\1\26\1\27\1\26\1\27\1\26\1\27\4\26\1\27\13\26\2\27\4\26\5\27\5\26\4\27\1\26\21\27\51\26\u0a77\27\57\26\1\27\57\26\1\27\u0085\26\6\27\4\26\3\27\2\26\14\27\46\26\1\27\1\26\5\27\1\26\2\27\70\26\7\27\1\26\20\27\27\26\11\27\7\26\1\27\7\26\1\27\7\26\1\27\7\26\1\27\7\26\1\27\7\26\1\27\7\26\1\27\7\26\120\27\1\26\u01d5\27\3\26\31\27\11\26\7\27\5\26\2\27\5\26\4\27\126\26\6\27\3\26\1\27\132\26\1\27\4\26\5\27\51\26\3\27\136\26\21\27\33\26\65\27\20\26\u0200\27\u19b6\26\112\27\u51d6\26\52\27\u048d\26\103\27\56\26\2\27\u010d\26\3\27\20\26\12\25\2\26\24\27\57\26\20\27\37\26\2\27\120\26\47\27\11\26\2\27\147\26\2\27\43\26\2\27\10\26\77\27\13\26\1\27\3\26\1\27\4\26\1\27\27\26\35\27\64\26\16\27\62\26\34\27\12\25\30\27\6\26\3\27\1\26\1\27\1\26\2\27\12\25\34\26\12\27\27\26\31\27\35\26\7\27\57\26\34\27\1\26\12\25\6\27\5\26\1\27\12\26\12\25\5\26\1\27\51\26\27\27\3\26\1\27\10\26\4\27\12\25\6\27\27\26\3\27\1\26\3\27\62\26\1\27\1\26\3\27\2\26\2\27\5\26\2\27\1\26\1\27\1\26\30\27\3\26\2\27\13\26\7\27\3\26\14\27\6\26\2\27\6\26\2\27\6\26\11\27\7\26\1\27\7\26\1\27\53\26\1\27\12\26\12\27\163\26\15\27\12\25\6\27\u2ba4\26\14\27\27\26\4\27\61\26\u2104\27\u016e\26\2\27\152\26\46\27\7\26\14\27\5\26\5\27\1\26\1\27\12\26\1\27\15\26\1\27\5\26\1\27\1\26\1\27\2\26\1\27\2\26\1\27\154\26\41\27\u016b\26\22\27\100\26\2\27\66\26\50\27\14\26\164\27\5\26\1\27\u0087\26\23\27\12\25\7\27\32\26\6\27\32\26\13\27\131\26\3\27\6\26\2\27\6\26\2\27\6\26\2\27\3\26\43\27",
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
            "\60\46\1\43\11\44\10\46\1\36\1\46\1\37\16\46\1\37\3\46\1\37\12\46\1\35\1\41\1\37\1\46\1\40\7\46\1\40\3\46\1\40\1\37\1\40\1\45\1\40\1\37\1\42\uff87\46",
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
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( ExclamationMark | DollarSign | LeftParenthesis | RightParenthesis | Asterisk | PlusSign | Comma | HyphenMinus | FullStop | Solidus | Colon | EqualsSign | QuestionMark | LeftSquareBracket | RightSquareBracket | CircumflexAccent | LeftCurlyBracket | VerticalLine | RightCurlyBracket | RULE_WORD_BOUNDARY | RULE_NOT_WORD_BOUNDARY | RULE_CHARACTER_CLASS_ESCAPE | RULE_CONTROL_ESCAPE | RULE_CONTROL_LETTER_ESCAPE | RULE_HEX_ESCAPE | RULE_UNICODE_ESCAPE | RULE_DECIMAL_ESCAPE | RULE_IDENTITY_ESCAPE | RULE_UNICODE_DIGIT | RULE_UNICODE_LETTER | RULE_PATTERN_CHARACTER_NO_DASH );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA17_20 = input.LA(1);

                        s = -1;
                        if ( (LA17_20=='b') ) {s = 29;}

                        else if ( (LA17_20=='B') ) {s = 30;}

                        else if ( (LA17_20=='D'||LA17_20=='S'||LA17_20=='W'||LA17_20=='d'||LA17_20=='s'||LA17_20=='w') ) {s = 31;}

                        else if ( (LA17_20=='f'||LA17_20=='n'||LA17_20=='r'||LA17_20=='t'||LA17_20=='v') ) {s = 32;}

                        else if ( (LA17_20=='c') ) {s = 33;}

                        else if ( (LA17_20=='x') ) {s = 34;}

                        else if ( (LA17_20=='0') ) {s = 35;}

                        else if ( ((LA17_20>='1' && LA17_20<='9')) ) {s = 36;}

                        else if ( (LA17_20=='u') ) {s = 37;}

                        else if ( ((LA17_20>='\u0000' && LA17_20<='/')||(LA17_20>=':' && LA17_20<='A')||LA17_20=='C'||(LA17_20>='E' && LA17_20<='R')||(LA17_20>='T' && LA17_20<='V')||(LA17_20>='X' && LA17_20<='a')||LA17_20=='e'||(LA17_20>='g' && LA17_20<='m')||(LA17_20>='o' && LA17_20<='q')||(LA17_20>='y' && LA17_20<='\uFFFF')) ) {s = 38;}

                        else s = 39;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA17_0 = input.LA(1);

                        s = -1;
                        if ( (LA17_0=='!') ) {s = 1;}

                        else if ( (LA17_0=='$') ) {s = 2;}

                        else if ( (LA17_0=='(') ) {s = 3;}

                        else if ( (LA17_0==')') ) {s = 4;}

                        else if ( (LA17_0=='*') ) {s = 5;}

                        else if ( (LA17_0=='+') ) {s = 6;}

                        else if ( (LA17_0==',') ) {s = 7;}

                        else if ( (LA17_0=='-') ) {s = 8;}

                        else if ( (LA17_0=='.') ) {s = 9;}

                        else if ( (LA17_0=='/') ) {s = 10;}

                        else if ( (LA17_0==':') ) {s = 11;}

                        else if ( (LA17_0=='=') ) {s = 12;}

                        else if ( (LA17_0=='?') ) {s = 13;}

                        else if ( (LA17_0=='[') ) {s = 14;}

                        else if ( (LA17_0==']') ) {s = 15;}

                        else if ( (LA17_0=='^') ) {s = 16;}

                        else if ( (LA17_0=='{') ) {s = 17;}

                        else if ( (LA17_0=='|') ) {s = 18;}

                        else if ( (LA17_0=='}') ) {s = 19;}

                        else if ( (LA17_0=='\\') ) {s = 20;}

                        else if ( ((LA17_0>='0' && LA17_0<='9')||(LA17_0>='\u0660' && LA17_0<='\u0669')||(LA17_0>='\u06F0' && LA17_0<='\u06F9')||(LA17_0>='\u07C0' && LA17_0<='\u07C9')||(LA17_0>='\u0966' && LA17_0<='\u096F')||(LA17_0>='\u09E6' && LA17_0<='\u09EF')||(LA17_0>='\u0A66' && LA17_0<='\u0A6F')||(LA17_0>='\u0AE6' && LA17_0<='\u0AEF')||(LA17_0>='\u0B66' && LA17_0<='\u0B6F')||(LA17_0>='\u0BE6' && LA17_0<='\u0BEF')||(LA17_0>='\u0C66' && LA17_0<='\u0C6F')||(LA17_0>='\u0CE6' && LA17_0<='\u0CEF')||(LA17_0>='\u0D66' && LA17_0<='\u0D6F')||(LA17_0>='\u0DE6' && LA17_0<='\u0DEF')||(LA17_0>='\u0E50' && LA17_0<='\u0E59')||(LA17_0>='\u0ED0' && LA17_0<='\u0ED9')||(LA17_0>='\u0F20' && LA17_0<='\u0F29')||(LA17_0>='\u1040' && LA17_0<='\u1049')||(LA17_0>='\u1090' && LA17_0<='\u1099')||(LA17_0>='\u17E0' && LA17_0<='\u17E9')||(LA17_0>='\u1810' && LA17_0<='\u1819')||(LA17_0>='\u1946' && LA17_0<='\u194F')||(LA17_0>='\u19D0' && LA17_0<='\u19D9')||(LA17_0>='\u1A80' && LA17_0<='\u1A89')||(LA17_0>='\u1A90' && LA17_0<='\u1A99')||(LA17_0>='\u1B50' && LA17_0<='\u1B59')||(LA17_0>='\u1BB0' && LA17_0<='\u1BB9')||(LA17_0>='\u1C40' && LA17_0<='\u1C49')||(LA17_0>='\u1C50' && LA17_0<='\u1C59')||(LA17_0>='\uA620' && LA17_0<='\uA629')||(LA17_0>='\uA8D0' && LA17_0<='\uA8D9')||(LA17_0>='\uA900' && LA17_0<='\uA909')||(LA17_0>='\uA9D0' && LA17_0<='\uA9D9')||(LA17_0>='\uA9F0' && LA17_0<='\uA9F9')||(LA17_0>='\uAA50' && LA17_0<='\uAA59')||(LA17_0>='\uABF0' && LA17_0<='\uABF9')||(LA17_0>='\uFF10' && LA17_0<='\uFF19')) ) {s = 21;}

                        else if ( ((LA17_0>='A' && LA17_0<='Z')||(LA17_0>='a' && LA17_0<='z')||LA17_0=='\u00AA'||LA17_0=='\u00B5'||LA17_0=='\u00BA'||(LA17_0>='\u00C0' && LA17_0<='\u00D6')||(LA17_0>='\u00D8' && LA17_0<='\u00F6')||(LA17_0>='\u00F8' && LA17_0<='\u02C1')||(LA17_0>='\u02C6' && LA17_0<='\u02D1')||(LA17_0>='\u02E0' && LA17_0<='\u02E4')||LA17_0=='\u02EC'||LA17_0=='\u02EE'||(LA17_0>='\u0370' && LA17_0<='\u0374')||(LA17_0>='\u0376' && LA17_0<='\u0377')||(LA17_0>='\u037A' && LA17_0<='\u037D')||LA17_0=='\u037F'||LA17_0=='\u0386'||(LA17_0>='\u0388' && LA17_0<='\u038A')||LA17_0=='\u038C'||(LA17_0>='\u038E' && LA17_0<='\u03A1')||(LA17_0>='\u03A3' && LA17_0<='\u03F5')||(LA17_0>='\u03F7' && LA17_0<='\u0481')||(LA17_0>='\u048A' && LA17_0<='\u052F')||(LA17_0>='\u0531' && LA17_0<='\u0556')||LA17_0=='\u0559'||(LA17_0>='\u0561' && LA17_0<='\u0587')||(LA17_0>='\u05D0' && LA17_0<='\u05EA')||(LA17_0>='\u05F0' && LA17_0<='\u05F2')||(LA17_0>='\u0620' && LA17_0<='\u064A')||(LA17_0>='\u066E' && LA17_0<='\u066F')||(LA17_0>='\u0671' && LA17_0<='\u06D3')||LA17_0=='\u06D5'||(LA17_0>='\u06E5' && LA17_0<='\u06E6')||(LA17_0>='\u06EE' && LA17_0<='\u06EF')||(LA17_0>='\u06FA' && LA17_0<='\u06FC')||LA17_0=='\u06FF'||LA17_0=='\u0710'||(LA17_0>='\u0712' && LA17_0<='\u072F')||(LA17_0>='\u074D' && LA17_0<='\u07A5')||LA17_0=='\u07B1'||(LA17_0>='\u07CA' && LA17_0<='\u07EA')||(LA17_0>='\u07F4' && LA17_0<='\u07F5')||LA17_0=='\u07FA'||(LA17_0>='\u0800' && LA17_0<='\u0815')||LA17_0=='\u081A'||LA17_0=='\u0824'||LA17_0=='\u0828'||(LA17_0>='\u0840' && LA17_0<='\u0858')||(LA17_0>='\u08A0' && LA17_0<='\u08B4')||(LA17_0>='\u0904' && LA17_0<='\u0939')||LA17_0=='\u093D'||LA17_0=='\u0950'||(LA17_0>='\u0958' && LA17_0<='\u0961')||(LA17_0>='\u0971' && LA17_0<='\u0980')||(LA17_0>='\u0985' && LA17_0<='\u098C')||(LA17_0>='\u098F' && LA17_0<='\u0990')||(LA17_0>='\u0993' && LA17_0<='\u09A8')||(LA17_0>='\u09AA' && LA17_0<='\u09B0')||LA17_0=='\u09B2'||(LA17_0>='\u09B6' && LA17_0<='\u09B9')||LA17_0=='\u09BD'||LA17_0=='\u09CE'||(LA17_0>='\u09DC' && LA17_0<='\u09DD')||(LA17_0>='\u09DF' && LA17_0<='\u09E1')||(LA17_0>='\u09F0' && LA17_0<='\u09F1')||(LA17_0>='\u0A05' && LA17_0<='\u0A0A')||(LA17_0>='\u0A0F' && LA17_0<='\u0A10')||(LA17_0>='\u0A13' && LA17_0<='\u0A28')||(LA17_0>='\u0A2A' && LA17_0<='\u0A30')||(LA17_0>='\u0A32' && LA17_0<='\u0A33')||(LA17_0>='\u0A35' && LA17_0<='\u0A36')||(LA17_0>='\u0A38' && LA17_0<='\u0A39')||(LA17_0>='\u0A59' && LA17_0<='\u0A5C')||LA17_0=='\u0A5E'||(LA17_0>='\u0A72' && LA17_0<='\u0A74')||(LA17_0>='\u0A85' && LA17_0<='\u0A8D')||(LA17_0>='\u0A8F' && LA17_0<='\u0A91')||(LA17_0>='\u0A93' && LA17_0<='\u0AA8')||(LA17_0>='\u0AAA' && LA17_0<='\u0AB0')||(LA17_0>='\u0AB2' && LA17_0<='\u0AB3')||(LA17_0>='\u0AB5' && LA17_0<='\u0AB9')||LA17_0=='\u0ABD'||LA17_0=='\u0AD0'||(LA17_0>='\u0AE0' && LA17_0<='\u0AE1')||LA17_0=='\u0AF9'||(LA17_0>='\u0B05' && LA17_0<='\u0B0C')||(LA17_0>='\u0B0F' && LA17_0<='\u0B10')||(LA17_0>='\u0B13' && LA17_0<='\u0B28')||(LA17_0>='\u0B2A' && LA17_0<='\u0B30')||(LA17_0>='\u0B32' && LA17_0<='\u0B33')||(LA17_0>='\u0B35' && LA17_0<='\u0B39')||LA17_0=='\u0B3D'||(LA17_0>='\u0B5C' && LA17_0<='\u0B5D')||(LA17_0>='\u0B5F' && LA17_0<='\u0B61')||LA17_0=='\u0B71'||LA17_0=='\u0B83'||(LA17_0>='\u0B85' && LA17_0<='\u0B8A')||(LA17_0>='\u0B8E' && LA17_0<='\u0B90')||(LA17_0>='\u0B92' && LA17_0<='\u0B95')||(LA17_0>='\u0B99' && LA17_0<='\u0B9A')||LA17_0=='\u0B9C'||(LA17_0>='\u0B9E' && LA17_0<='\u0B9F')||(LA17_0>='\u0BA3' && LA17_0<='\u0BA4')||(LA17_0>='\u0BA8' && LA17_0<='\u0BAA')||(LA17_0>='\u0BAE' && LA17_0<='\u0BB9')||LA17_0=='\u0BD0'||(LA17_0>='\u0C05' && LA17_0<='\u0C0C')||(LA17_0>='\u0C0E' && LA17_0<='\u0C10')||(LA17_0>='\u0C12' && LA17_0<='\u0C28')||(LA17_0>='\u0C2A' && LA17_0<='\u0C39')||LA17_0=='\u0C3D'||(LA17_0>='\u0C58' && LA17_0<='\u0C5A')||(LA17_0>='\u0C60' && LA17_0<='\u0C61')||(LA17_0>='\u0C85' && LA17_0<='\u0C8C')||(LA17_0>='\u0C8E' && LA17_0<='\u0C90')||(LA17_0>='\u0C92' && LA17_0<='\u0CA8')||(LA17_0>='\u0CAA' && LA17_0<='\u0CB3')||(LA17_0>='\u0CB5' && LA17_0<='\u0CB9')||LA17_0=='\u0CBD'||LA17_0=='\u0CDE'||(LA17_0>='\u0CE0' && LA17_0<='\u0CE1')||(LA17_0>='\u0CF1' && LA17_0<='\u0CF2')||(LA17_0>='\u0D05' && LA17_0<='\u0D0C')||(LA17_0>='\u0D0E' && LA17_0<='\u0D10')||(LA17_0>='\u0D12' && LA17_0<='\u0D3A')||LA17_0=='\u0D3D'||LA17_0=='\u0D4E'||(LA17_0>='\u0D5F' && LA17_0<='\u0D61')||(LA17_0>='\u0D7A' && LA17_0<='\u0D7F')||(LA17_0>='\u0D85' && LA17_0<='\u0D96')||(LA17_0>='\u0D9A' && LA17_0<='\u0DB1')||(LA17_0>='\u0DB3' && LA17_0<='\u0DBB')||LA17_0=='\u0DBD'||(LA17_0>='\u0DC0' && LA17_0<='\u0DC6')||(LA17_0>='\u0E01' && LA17_0<='\u0E30')||(LA17_0>='\u0E32' && LA17_0<='\u0E33')||(LA17_0>='\u0E40' && LA17_0<='\u0E46')||(LA17_0>='\u0E81' && LA17_0<='\u0E82')||LA17_0=='\u0E84'||(LA17_0>='\u0E87' && LA17_0<='\u0E88')||LA17_0=='\u0E8A'||LA17_0=='\u0E8D'||(LA17_0>='\u0E94' && LA17_0<='\u0E97')||(LA17_0>='\u0E99' && LA17_0<='\u0E9F')||(LA17_0>='\u0EA1' && LA17_0<='\u0EA3')||LA17_0=='\u0EA5'||LA17_0=='\u0EA7'||(LA17_0>='\u0EAA' && LA17_0<='\u0EAB')||(LA17_0>='\u0EAD' && LA17_0<='\u0EB0')||(LA17_0>='\u0EB2' && LA17_0<='\u0EB3')||LA17_0=='\u0EBD'||(LA17_0>='\u0EC0' && LA17_0<='\u0EC4')||LA17_0=='\u0EC6'||(LA17_0>='\u0EDC' && LA17_0<='\u0EDF')||LA17_0=='\u0F00'||(LA17_0>='\u0F40' && LA17_0<='\u0F47')||(LA17_0>='\u0F49' && LA17_0<='\u0F6C')||(LA17_0>='\u0F88' && LA17_0<='\u0F8C')||(LA17_0>='\u1000' && LA17_0<='\u102A')||LA17_0=='\u103F'||(LA17_0>='\u1050' && LA17_0<='\u1055')||(LA17_0>='\u105A' && LA17_0<='\u105D')||LA17_0=='\u1061'||(LA17_0>='\u1065' && LA17_0<='\u1066')||(LA17_0>='\u106E' && LA17_0<='\u1070')||(LA17_0>='\u1075' && LA17_0<='\u1081')||LA17_0=='\u108E'||(LA17_0>='\u10A0' && LA17_0<='\u10C5')||LA17_0=='\u10C7'||LA17_0=='\u10CD'||(LA17_0>='\u10D0' && LA17_0<='\u10FA')||(LA17_0>='\u10FC' && LA17_0<='\u1248')||(LA17_0>='\u124A' && LA17_0<='\u124D')||(LA17_0>='\u1250' && LA17_0<='\u1256')||LA17_0=='\u1258'||(LA17_0>='\u125A' && LA17_0<='\u125D')||(LA17_0>='\u1260' && LA17_0<='\u1288')||(LA17_0>='\u128A' && LA17_0<='\u128D')||(LA17_0>='\u1290' && LA17_0<='\u12B0')||(LA17_0>='\u12B2' && LA17_0<='\u12B5')||(LA17_0>='\u12B8' && LA17_0<='\u12BE')||LA17_0=='\u12C0'||(LA17_0>='\u12C2' && LA17_0<='\u12C5')||(LA17_0>='\u12C8' && LA17_0<='\u12D6')||(LA17_0>='\u12D8' && LA17_0<='\u1310')||(LA17_0>='\u1312' && LA17_0<='\u1315')||(LA17_0>='\u1318' && LA17_0<='\u135A')||(LA17_0>='\u1380' && LA17_0<='\u138F')||(LA17_0>='\u13A0' && LA17_0<='\u13F5')||(LA17_0>='\u13F8' && LA17_0<='\u13FD')||(LA17_0>='\u1401' && LA17_0<='\u166C')||(LA17_0>='\u166F' && LA17_0<='\u167F')||(LA17_0>='\u1681' && LA17_0<='\u169A')||(LA17_0>='\u16A0' && LA17_0<='\u16EA')||(LA17_0>='\u16EE' && LA17_0<='\u16F8')||(LA17_0>='\u1700' && LA17_0<='\u170C')||(LA17_0>='\u170E' && LA17_0<='\u1711')||(LA17_0>='\u1720' && LA17_0<='\u1731')||(LA17_0>='\u1740' && LA17_0<='\u1751')||(LA17_0>='\u1760' && LA17_0<='\u176C')||(LA17_0>='\u176E' && LA17_0<='\u1770')||(LA17_0>='\u1780' && LA17_0<='\u17B3')||LA17_0=='\u17D7'||LA17_0=='\u17DC'||(LA17_0>='\u1820' && LA17_0<='\u1877')||(LA17_0>='\u1880' && LA17_0<='\u18A8')||LA17_0=='\u18AA'||(LA17_0>='\u18B0' && LA17_0<='\u18F5')||(LA17_0>='\u1900' && LA17_0<='\u191E')||(LA17_0>='\u1950' && LA17_0<='\u196D')||(LA17_0>='\u1970' && LA17_0<='\u1974')||(LA17_0>='\u1980' && LA17_0<='\u19AB')||(LA17_0>='\u19B0' && LA17_0<='\u19C9')||(LA17_0>='\u1A00' && LA17_0<='\u1A16')||(LA17_0>='\u1A20' && LA17_0<='\u1A54')||LA17_0=='\u1AA7'||(LA17_0>='\u1B05' && LA17_0<='\u1B33')||(LA17_0>='\u1B45' && LA17_0<='\u1B4B')||(LA17_0>='\u1B83' && LA17_0<='\u1BA0')||(LA17_0>='\u1BAE' && LA17_0<='\u1BAF')||(LA17_0>='\u1BBA' && LA17_0<='\u1BE5')||(LA17_0>='\u1C00' && LA17_0<='\u1C23')||(LA17_0>='\u1C4D' && LA17_0<='\u1C4F')||(LA17_0>='\u1C5A' && LA17_0<='\u1C7D')||(LA17_0>='\u1CE9' && LA17_0<='\u1CEC')||(LA17_0>='\u1CEE' && LA17_0<='\u1CF1')||(LA17_0>='\u1CF5' && LA17_0<='\u1CF6')||(LA17_0>='\u1D00' && LA17_0<='\u1DBF')||(LA17_0>='\u1E00' && LA17_0<='\u1F15')||(LA17_0>='\u1F18' && LA17_0<='\u1F1D')||(LA17_0>='\u1F20' && LA17_0<='\u1F45')||(LA17_0>='\u1F48' && LA17_0<='\u1F4D')||(LA17_0>='\u1F50' && LA17_0<='\u1F57')||LA17_0=='\u1F59'||LA17_0=='\u1F5B'||LA17_0=='\u1F5D'||(LA17_0>='\u1F5F' && LA17_0<='\u1F7D')||(LA17_0>='\u1F80' && LA17_0<='\u1FB4')||(LA17_0>='\u1FB6' && LA17_0<='\u1FBC')||LA17_0=='\u1FBE'||(LA17_0>='\u1FC2' && LA17_0<='\u1FC4')||(LA17_0>='\u1FC6' && LA17_0<='\u1FCC')||(LA17_0>='\u1FD0' && LA17_0<='\u1FD3')||(LA17_0>='\u1FD6' && LA17_0<='\u1FDB')||(LA17_0>='\u1FE0' && LA17_0<='\u1FEC')||(LA17_0>='\u1FF2' && LA17_0<='\u1FF4')||(LA17_0>='\u1FF6' && LA17_0<='\u1FFC')||LA17_0=='\u2071'||LA17_0=='\u207F'||(LA17_0>='\u2090' && LA17_0<='\u209C')||LA17_0=='\u2102'||LA17_0=='\u2107'||(LA17_0>='\u210A' && LA17_0<='\u2113')||LA17_0=='\u2115'||(LA17_0>='\u2119' && LA17_0<='\u211D')||LA17_0=='\u2124'||LA17_0=='\u2126'||LA17_0=='\u2128'||(LA17_0>='\u212A' && LA17_0<='\u212D')||(LA17_0>='\u212F' && LA17_0<='\u2139')||(LA17_0>='\u213C' && LA17_0<='\u213F')||(LA17_0>='\u2145' && LA17_0<='\u2149')||LA17_0=='\u214E'||(LA17_0>='\u2160' && LA17_0<='\u2188')||(LA17_0>='\u2C00' && LA17_0<='\u2C2E')||(LA17_0>='\u2C30' && LA17_0<='\u2C5E')||(LA17_0>='\u2C60' && LA17_0<='\u2CE4')||(LA17_0>='\u2CEB' && LA17_0<='\u2CEE')||(LA17_0>='\u2CF2' && LA17_0<='\u2CF3')||(LA17_0>='\u2D00' && LA17_0<='\u2D25')||LA17_0=='\u2D27'||LA17_0=='\u2D2D'||(LA17_0>='\u2D30' && LA17_0<='\u2D67')||LA17_0=='\u2D6F'||(LA17_0>='\u2D80' && LA17_0<='\u2D96')||(LA17_0>='\u2DA0' && LA17_0<='\u2DA6')||(LA17_0>='\u2DA8' && LA17_0<='\u2DAE')||(LA17_0>='\u2DB0' && LA17_0<='\u2DB6')||(LA17_0>='\u2DB8' && LA17_0<='\u2DBE')||(LA17_0>='\u2DC0' && LA17_0<='\u2DC6')||(LA17_0>='\u2DC8' && LA17_0<='\u2DCE')||(LA17_0>='\u2DD0' && LA17_0<='\u2DD6')||(LA17_0>='\u2DD8' && LA17_0<='\u2DDE')||LA17_0=='\u2E2F'||(LA17_0>='\u3005' && LA17_0<='\u3007')||(LA17_0>='\u3021' && LA17_0<='\u3029')||(LA17_0>='\u3031' && LA17_0<='\u3035')||(LA17_0>='\u3038' && LA17_0<='\u303C')||(LA17_0>='\u3041' && LA17_0<='\u3096')||(LA17_0>='\u309D' && LA17_0<='\u309F')||(LA17_0>='\u30A1' && LA17_0<='\u30FA')||(LA17_0>='\u30FC' && LA17_0<='\u30FF')||(LA17_0>='\u3105' && LA17_0<='\u312D')||(LA17_0>='\u3131' && LA17_0<='\u318E')||(LA17_0>='\u31A0' && LA17_0<='\u31BA')||(LA17_0>='\u31F0' && LA17_0<='\u31FF')||(LA17_0>='\u3400' && LA17_0<='\u4DB5')||(LA17_0>='\u4E00' && LA17_0<='\u9FD5')||(LA17_0>='\uA000' && LA17_0<='\uA48C')||(LA17_0>='\uA4D0' && LA17_0<='\uA4FD')||(LA17_0>='\uA500' && LA17_0<='\uA60C')||(LA17_0>='\uA610' && LA17_0<='\uA61F')||(LA17_0>='\uA62A' && LA17_0<='\uA62B')||(LA17_0>='\uA640' && LA17_0<='\uA66E')||(LA17_0>='\uA67F' && LA17_0<='\uA69D')||(LA17_0>='\uA6A0' && LA17_0<='\uA6EF')||(LA17_0>='\uA717' && LA17_0<='\uA71F')||(LA17_0>='\uA722' && LA17_0<='\uA788')||(LA17_0>='\uA78B' && LA17_0<='\uA7AD')||(LA17_0>='\uA7B0' && LA17_0<='\uA7B7')||(LA17_0>='\uA7F7' && LA17_0<='\uA801')||(LA17_0>='\uA803' && LA17_0<='\uA805')||(LA17_0>='\uA807' && LA17_0<='\uA80A')||(LA17_0>='\uA80C' && LA17_0<='\uA822')||(LA17_0>='\uA840' && LA17_0<='\uA873')||(LA17_0>='\uA882' && LA17_0<='\uA8B3')||(LA17_0>='\uA8F2' && LA17_0<='\uA8F7')||LA17_0=='\uA8FB'||LA17_0=='\uA8FD'||(LA17_0>='\uA90A' && LA17_0<='\uA925')||(LA17_0>='\uA930' && LA17_0<='\uA946')||(LA17_0>='\uA960' && LA17_0<='\uA97C')||(LA17_0>='\uA984' && LA17_0<='\uA9B2')||LA17_0=='\uA9CF'||(LA17_0>='\uA9E0' && LA17_0<='\uA9E4')||(LA17_0>='\uA9E6' && LA17_0<='\uA9EF')||(LA17_0>='\uA9FA' && LA17_0<='\uA9FE')||(LA17_0>='\uAA00' && LA17_0<='\uAA28')||(LA17_0>='\uAA40' && LA17_0<='\uAA42')||(LA17_0>='\uAA44' && LA17_0<='\uAA4B')||(LA17_0>='\uAA60' && LA17_0<='\uAA76')||LA17_0=='\uAA7A'||(LA17_0>='\uAA7E' && LA17_0<='\uAAAF')||LA17_0=='\uAAB1'||(LA17_0>='\uAAB5' && LA17_0<='\uAAB6')||(LA17_0>='\uAAB9' && LA17_0<='\uAABD')||LA17_0=='\uAAC0'||LA17_0=='\uAAC2'||(LA17_0>='\uAADB' && LA17_0<='\uAADD')||(LA17_0>='\uAAE0' && LA17_0<='\uAAEA')||(LA17_0>='\uAAF2' && LA17_0<='\uAAF4')||(LA17_0>='\uAB01' && LA17_0<='\uAB06')||(LA17_0>='\uAB09' && LA17_0<='\uAB0E')||(LA17_0>='\uAB11' && LA17_0<='\uAB16')||(LA17_0>='\uAB20' && LA17_0<='\uAB26')||(LA17_0>='\uAB28' && LA17_0<='\uAB2E')||(LA17_0>='\uAB30' && LA17_0<='\uAB5A')||(LA17_0>='\uAB5C' && LA17_0<='\uAB65')||(LA17_0>='\uAB70' && LA17_0<='\uABE2')||(LA17_0>='\uAC00' && LA17_0<='\uD7A3')||(LA17_0>='\uD7B0' && LA17_0<='\uD7C6')||(LA17_0>='\uD7CB' && LA17_0<='\uD7FB')||(LA17_0>='\uF900' && LA17_0<='\uFA6D')||(LA17_0>='\uFA70' && LA17_0<='\uFAD9')||(LA17_0>='\uFB00' && LA17_0<='\uFB06')||(LA17_0>='\uFB13' && LA17_0<='\uFB17')||LA17_0=='\uFB1D'||(LA17_0>='\uFB1F' && LA17_0<='\uFB28')||(LA17_0>='\uFB2A' && LA17_0<='\uFB36')||(LA17_0>='\uFB38' && LA17_0<='\uFB3C')||LA17_0=='\uFB3E'||(LA17_0>='\uFB40' && LA17_0<='\uFB41')||(LA17_0>='\uFB43' && LA17_0<='\uFB44')||(LA17_0>='\uFB46' && LA17_0<='\uFBB1')||(LA17_0>='\uFBD3' && LA17_0<='\uFD3D')||(LA17_0>='\uFD50' && LA17_0<='\uFD8F')||(LA17_0>='\uFD92' && LA17_0<='\uFDC7')||(LA17_0>='\uFDF0' && LA17_0<='\uFDFB')||(LA17_0>='\uFE70' && LA17_0<='\uFE74')||(LA17_0>='\uFE76' && LA17_0<='\uFEFC')||(LA17_0>='\uFF21' && LA17_0<='\uFF3A')||(LA17_0>='\uFF41' && LA17_0<='\uFF5A')||(LA17_0>='\uFF66' && LA17_0<='\uFFBE')||(LA17_0>='\uFFC2' && LA17_0<='\uFFC7')||(LA17_0>='\uFFCA' && LA17_0<='\uFFCF')||(LA17_0>='\uFFD2' && LA17_0<='\uFFD7')||(LA17_0>='\uFFDA' && LA17_0<='\uFFDC')) ) {s = 22;}

                        else if ( ((LA17_0>='\u0000' && LA17_0<=' ')||(LA17_0>='\"' && LA17_0<='#')||(LA17_0>='%' && LA17_0<='\'')||(LA17_0>=';' && LA17_0<='<')||LA17_0=='>'||LA17_0=='@'||(LA17_0>='_' && LA17_0<='`')||(LA17_0>='~' && LA17_0<='\u00A9')||(LA17_0>='\u00AB' && LA17_0<='\u00B4')||(LA17_0>='\u00B6' && LA17_0<='\u00B9')||(LA17_0>='\u00BB' && LA17_0<='\u00BF')||LA17_0=='\u00D7'||LA17_0=='\u00F7'||(LA17_0>='\u02C2' && LA17_0<='\u02C5')||(LA17_0>='\u02D2' && LA17_0<='\u02DF')||(LA17_0>='\u02E5' && LA17_0<='\u02EB')||LA17_0=='\u02ED'||(LA17_0>='\u02EF' && LA17_0<='\u036F')||LA17_0=='\u0375'||(LA17_0>='\u0378' && LA17_0<='\u0379')||LA17_0=='\u037E'||(LA17_0>='\u0380' && LA17_0<='\u0385')||LA17_0=='\u0387'||LA17_0=='\u038B'||LA17_0=='\u038D'||LA17_0=='\u03A2'||LA17_0=='\u03F6'||(LA17_0>='\u0482' && LA17_0<='\u0489')||LA17_0=='\u0530'||(LA17_0>='\u0557' && LA17_0<='\u0558')||(LA17_0>='\u055A' && LA17_0<='\u0560')||(LA17_0>='\u0588' && LA17_0<='\u05CF')||(LA17_0>='\u05EB' && LA17_0<='\u05EF')||(LA17_0>='\u05F3' && LA17_0<='\u061F')||(LA17_0>='\u064B' && LA17_0<='\u065F')||(LA17_0>='\u066A' && LA17_0<='\u066D')||LA17_0=='\u0670'||LA17_0=='\u06D4'||(LA17_0>='\u06D6' && LA17_0<='\u06E4')||(LA17_0>='\u06E7' && LA17_0<='\u06ED')||(LA17_0>='\u06FD' && LA17_0<='\u06FE')||(LA17_0>='\u0700' && LA17_0<='\u070F')||LA17_0=='\u0711'||(LA17_0>='\u0730' && LA17_0<='\u074C')||(LA17_0>='\u07A6' && LA17_0<='\u07B0')||(LA17_0>='\u07B2' && LA17_0<='\u07BF')||(LA17_0>='\u07EB' && LA17_0<='\u07F3')||(LA17_0>='\u07F6' && LA17_0<='\u07F9')||(LA17_0>='\u07FB' && LA17_0<='\u07FF')||(LA17_0>='\u0816' && LA17_0<='\u0819')||(LA17_0>='\u081B' && LA17_0<='\u0823')||(LA17_0>='\u0825' && LA17_0<='\u0827')||(LA17_0>='\u0829' && LA17_0<='\u083F')||(LA17_0>='\u0859' && LA17_0<='\u089F')||(LA17_0>='\u08B5' && LA17_0<='\u0903')||(LA17_0>='\u093A' && LA17_0<='\u093C')||(LA17_0>='\u093E' && LA17_0<='\u094F')||(LA17_0>='\u0951' && LA17_0<='\u0957')||(LA17_0>='\u0962' && LA17_0<='\u0965')||LA17_0=='\u0970'||(LA17_0>='\u0981' && LA17_0<='\u0984')||(LA17_0>='\u098D' && LA17_0<='\u098E')||(LA17_0>='\u0991' && LA17_0<='\u0992')||LA17_0=='\u09A9'||LA17_0=='\u09B1'||(LA17_0>='\u09B3' && LA17_0<='\u09B5')||(LA17_0>='\u09BA' && LA17_0<='\u09BC')||(LA17_0>='\u09BE' && LA17_0<='\u09CD')||(LA17_0>='\u09CF' && LA17_0<='\u09DB')||LA17_0=='\u09DE'||(LA17_0>='\u09E2' && LA17_0<='\u09E5')||(LA17_0>='\u09F2' && LA17_0<='\u0A04')||(LA17_0>='\u0A0B' && LA17_0<='\u0A0E')||(LA17_0>='\u0A11' && LA17_0<='\u0A12')||LA17_0=='\u0A29'||LA17_0=='\u0A31'||LA17_0=='\u0A34'||LA17_0=='\u0A37'||(LA17_0>='\u0A3A' && LA17_0<='\u0A58')||LA17_0=='\u0A5D'||(LA17_0>='\u0A5F' && LA17_0<='\u0A65')||(LA17_0>='\u0A70' && LA17_0<='\u0A71')||(LA17_0>='\u0A75' && LA17_0<='\u0A84')||LA17_0=='\u0A8E'||LA17_0=='\u0A92'||LA17_0=='\u0AA9'||LA17_0=='\u0AB1'||LA17_0=='\u0AB4'||(LA17_0>='\u0ABA' && LA17_0<='\u0ABC')||(LA17_0>='\u0ABE' && LA17_0<='\u0ACF')||(LA17_0>='\u0AD1' && LA17_0<='\u0ADF')||(LA17_0>='\u0AE2' && LA17_0<='\u0AE5')||(LA17_0>='\u0AF0' && LA17_0<='\u0AF8')||(LA17_0>='\u0AFA' && LA17_0<='\u0B04')||(LA17_0>='\u0B0D' && LA17_0<='\u0B0E')||(LA17_0>='\u0B11' && LA17_0<='\u0B12')||LA17_0=='\u0B29'||LA17_0=='\u0B31'||LA17_0=='\u0B34'||(LA17_0>='\u0B3A' && LA17_0<='\u0B3C')||(LA17_0>='\u0B3E' && LA17_0<='\u0B5B')||LA17_0=='\u0B5E'||(LA17_0>='\u0B62' && LA17_0<='\u0B65')||LA17_0=='\u0B70'||(LA17_0>='\u0B72' && LA17_0<='\u0B82')||LA17_0=='\u0B84'||(LA17_0>='\u0B8B' && LA17_0<='\u0B8D')||LA17_0=='\u0B91'||(LA17_0>='\u0B96' && LA17_0<='\u0B98')||LA17_0=='\u0B9B'||LA17_0=='\u0B9D'||(LA17_0>='\u0BA0' && LA17_0<='\u0BA2')||(LA17_0>='\u0BA5' && LA17_0<='\u0BA7')||(LA17_0>='\u0BAB' && LA17_0<='\u0BAD')||(LA17_0>='\u0BBA' && LA17_0<='\u0BCF')||(LA17_0>='\u0BD1' && LA17_0<='\u0BE5')||(LA17_0>='\u0BF0' && LA17_0<='\u0C04')||LA17_0=='\u0C0D'||LA17_0=='\u0C11'||LA17_0=='\u0C29'||(LA17_0>='\u0C3A' && LA17_0<='\u0C3C')||(LA17_0>='\u0C3E' && LA17_0<='\u0C57')||(LA17_0>='\u0C5B' && LA17_0<='\u0C5F')||(LA17_0>='\u0C62' && LA17_0<='\u0C65')||(LA17_0>='\u0C70' && LA17_0<='\u0C84')||LA17_0=='\u0C8D'||LA17_0=='\u0C91'||LA17_0=='\u0CA9'||LA17_0=='\u0CB4'||(LA17_0>='\u0CBA' && LA17_0<='\u0CBC')||(LA17_0>='\u0CBE' && LA17_0<='\u0CDD')||LA17_0=='\u0CDF'||(LA17_0>='\u0CE2' && LA17_0<='\u0CE5')||LA17_0=='\u0CF0'||(LA17_0>='\u0CF3' && LA17_0<='\u0D04')||LA17_0=='\u0D0D'||LA17_0=='\u0D11'||(LA17_0>='\u0D3B' && LA17_0<='\u0D3C')||(LA17_0>='\u0D3E' && LA17_0<='\u0D4D')||(LA17_0>='\u0D4F' && LA17_0<='\u0D5E')||(LA17_0>='\u0D62' && LA17_0<='\u0D65')||(LA17_0>='\u0D70' && LA17_0<='\u0D79')||(LA17_0>='\u0D80' && LA17_0<='\u0D84')||(LA17_0>='\u0D97' && LA17_0<='\u0D99')||LA17_0=='\u0DB2'||LA17_0=='\u0DBC'||(LA17_0>='\u0DBE' && LA17_0<='\u0DBF')||(LA17_0>='\u0DC7' && LA17_0<='\u0DE5')||(LA17_0>='\u0DF0' && LA17_0<='\u0E00')||LA17_0=='\u0E31'||(LA17_0>='\u0E34' && LA17_0<='\u0E3F')||(LA17_0>='\u0E47' && LA17_0<='\u0E4F')||(LA17_0>='\u0E5A' && LA17_0<='\u0E80')||LA17_0=='\u0E83'||(LA17_0>='\u0E85' && LA17_0<='\u0E86')||LA17_0=='\u0E89'||(LA17_0>='\u0E8B' && LA17_0<='\u0E8C')||(LA17_0>='\u0E8E' && LA17_0<='\u0E93')||LA17_0=='\u0E98'||LA17_0=='\u0EA0'||LA17_0=='\u0EA4'||LA17_0=='\u0EA6'||(LA17_0>='\u0EA8' && LA17_0<='\u0EA9')||LA17_0=='\u0EAC'||LA17_0=='\u0EB1'||(LA17_0>='\u0EB4' && LA17_0<='\u0EBC')||(LA17_0>='\u0EBE' && LA17_0<='\u0EBF')||LA17_0=='\u0EC5'||(LA17_0>='\u0EC7' && LA17_0<='\u0ECF')||(LA17_0>='\u0EDA' && LA17_0<='\u0EDB')||(LA17_0>='\u0EE0' && LA17_0<='\u0EFF')||(LA17_0>='\u0F01' && LA17_0<='\u0F1F')||(LA17_0>='\u0F2A' && LA17_0<='\u0F3F')||LA17_0=='\u0F48'||(LA17_0>='\u0F6D' && LA17_0<='\u0F87')||(LA17_0>='\u0F8D' && LA17_0<='\u0FFF')||(LA17_0>='\u102B' && LA17_0<='\u103E')||(LA17_0>='\u104A' && LA17_0<='\u104F')||(LA17_0>='\u1056' && LA17_0<='\u1059')||(LA17_0>='\u105E' && LA17_0<='\u1060')||(LA17_0>='\u1062' && LA17_0<='\u1064')||(LA17_0>='\u1067' && LA17_0<='\u106D')||(LA17_0>='\u1071' && LA17_0<='\u1074')||(LA17_0>='\u1082' && LA17_0<='\u108D')||LA17_0=='\u108F'||(LA17_0>='\u109A' && LA17_0<='\u109F')||LA17_0=='\u10C6'||(LA17_0>='\u10C8' && LA17_0<='\u10CC')||(LA17_0>='\u10CE' && LA17_0<='\u10CF')||LA17_0=='\u10FB'||LA17_0=='\u1249'||(LA17_0>='\u124E' && LA17_0<='\u124F')||LA17_0=='\u1257'||LA17_0=='\u1259'||(LA17_0>='\u125E' && LA17_0<='\u125F')||LA17_0=='\u1289'||(LA17_0>='\u128E' && LA17_0<='\u128F')||LA17_0=='\u12B1'||(LA17_0>='\u12B6' && LA17_0<='\u12B7')||LA17_0=='\u12BF'||LA17_0=='\u12C1'||(LA17_0>='\u12C6' && LA17_0<='\u12C7')||LA17_0=='\u12D7'||LA17_0=='\u1311'||(LA17_0>='\u1316' && LA17_0<='\u1317')||(LA17_0>='\u135B' && LA17_0<='\u137F')||(LA17_0>='\u1390' && LA17_0<='\u139F')||(LA17_0>='\u13F6' && LA17_0<='\u13F7')||(LA17_0>='\u13FE' && LA17_0<='\u1400')||(LA17_0>='\u166D' && LA17_0<='\u166E')||LA17_0=='\u1680'||(LA17_0>='\u169B' && LA17_0<='\u169F')||(LA17_0>='\u16EB' && LA17_0<='\u16ED')||(LA17_0>='\u16F9' && LA17_0<='\u16FF')||LA17_0=='\u170D'||(LA17_0>='\u1712' && LA17_0<='\u171F')||(LA17_0>='\u1732' && LA17_0<='\u173F')||(LA17_0>='\u1752' && LA17_0<='\u175F')||LA17_0=='\u176D'||(LA17_0>='\u1771' && LA17_0<='\u177F')||(LA17_0>='\u17B4' && LA17_0<='\u17D6')||(LA17_0>='\u17D8' && LA17_0<='\u17DB')||(LA17_0>='\u17DD' && LA17_0<='\u17DF')||(LA17_0>='\u17EA' && LA17_0<='\u180F')||(LA17_0>='\u181A' && LA17_0<='\u181F')||(LA17_0>='\u1878' && LA17_0<='\u187F')||LA17_0=='\u18A9'||(LA17_0>='\u18AB' && LA17_0<='\u18AF')||(LA17_0>='\u18F6' && LA17_0<='\u18FF')||(LA17_0>='\u191F' && LA17_0<='\u1945')||(LA17_0>='\u196E' && LA17_0<='\u196F')||(LA17_0>='\u1975' && LA17_0<='\u197F')||(LA17_0>='\u19AC' && LA17_0<='\u19AF')||(LA17_0>='\u19CA' && LA17_0<='\u19CF')||(LA17_0>='\u19DA' && LA17_0<='\u19FF')||(LA17_0>='\u1A17' && LA17_0<='\u1A1F')||(LA17_0>='\u1A55' && LA17_0<='\u1A7F')||(LA17_0>='\u1A8A' && LA17_0<='\u1A8F')||(LA17_0>='\u1A9A' && LA17_0<='\u1AA6')||(LA17_0>='\u1AA8' && LA17_0<='\u1B04')||(LA17_0>='\u1B34' && LA17_0<='\u1B44')||(LA17_0>='\u1B4C' && LA17_0<='\u1B4F')||(LA17_0>='\u1B5A' && LA17_0<='\u1B82')||(LA17_0>='\u1BA1' && LA17_0<='\u1BAD')||(LA17_0>='\u1BE6' && LA17_0<='\u1BFF')||(LA17_0>='\u1C24' && LA17_0<='\u1C3F')||(LA17_0>='\u1C4A' && LA17_0<='\u1C4C')||(LA17_0>='\u1C7E' && LA17_0<='\u1CE8')||LA17_0=='\u1CED'||(LA17_0>='\u1CF2' && LA17_0<='\u1CF4')||(LA17_0>='\u1CF7' && LA17_0<='\u1CFF')||(LA17_0>='\u1DC0' && LA17_0<='\u1DFF')||(LA17_0>='\u1F16' && LA17_0<='\u1F17')||(LA17_0>='\u1F1E' && LA17_0<='\u1F1F')||(LA17_0>='\u1F46' && LA17_0<='\u1F47')||(LA17_0>='\u1F4E' && LA17_0<='\u1F4F')||LA17_0=='\u1F58'||LA17_0=='\u1F5A'||LA17_0=='\u1F5C'||LA17_0=='\u1F5E'||(LA17_0>='\u1F7E' && LA17_0<='\u1F7F')||LA17_0=='\u1FB5'||LA17_0=='\u1FBD'||(LA17_0>='\u1FBF' && LA17_0<='\u1FC1')||LA17_0=='\u1FC5'||(LA17_0>='\u1FCD' && LA17_0<='\u1FCF')||(LA17_0>='\u1FD4' && LA17_0<='\u1FD5')||(LA17_0>='\u1FDC' && LA17_0<='\u1FDF')||(LA17_0>='\u1FED' && LA17_0<='\u1FF1')||LA17_0=='\u1FF5'||(LA17_0>='\u1FFD' && LA17_0<='\u2070')||(LA17_0>='\u2072' && LA17_0<='\u207E')||(LA17_0>='\u2080' && LA17_0<='\u208F')||(LA17_0>='\u209D' && LA17_0<='\u2101')||(LA17_0>='\u2103' && LA17_0<='\u2106')||(LA17_0>='\u2108' && LA17_0<='\u2109')||LA17_0=='\u2114'||(LA17_0>='\u2116' && LA17_0<='\u2118')||(LA17_0>='\u211E' && LA17_0<='\u2123')||LA17_0=='\u2125'||LA17_0=='\u2127'||LA17_0=='\u2129'||LA17_0=='\u212E'||(LA17_0>='\u213A' && LA17_0<='\u213B')||(LA17_0>='\u2140' && LA17_0<='\u2144')||(LA17_0>='\u214A' && LA17_0<='\u214D')||(LA17_0>='\u214F' && LA17_0<='\u215F')||(LA17_0>='\u2189' && LA17_0<='\u2BFF')||LA17_0=='\u2C2F'||LA17_0=='\u2C5F'||(LA17_0>='\u2CE5' && LA17_0<='\u2CEA')||(LA17_0>='\u2CEF' && LA17_0<='\u2CF1')||(LA17_0>='\u2CF4' && LA17_0<='\u2CFF')||LA17_0=='\u2D26'||(LA17_0>='\u2D28' && LA17_0<='\u2D2C')||(LA17_0>='\u2D2E' && LA17_0<='\u2D2F')||(LA17_0>='\u2D68' && LA17_0<='\u2D6E')||(LA17_0>='\u2D70' && LA17_0<='\u2D7F')||(LA17_0>='\u2D97' && LA17_0<='\u2D9F')||LA17_0=='\u2DA7'||LA17_0=='\u2DAF'||LA17_0=='\u2DB7'||LA17_0=='\u2DBF'||LA17_0=='\u2DC7'||LA17_0=='\u2DCF'||LA17_0=='\u2DD7'||(LA17_0>='\u2DDF' && LA17_0<='\u2E2E')||(LA17_0>='\u2E30' && LA17_0<='\u3004')||(LA17_0>='\u3008' && LA17_0<='\u3020')||(LA17_0>='\u302A' && LA17_0<='\u3030')||(LA17_0>='\u3036' && LA17_0<='\u3037')||(LA17_0>='\u303D' && LA17_0<='\u3040')||(LA17_0>='\u3097' && LA17_0<='\u309C')||LA17_0=='\u30A0'||LA17_0=='\u30FB'||(LA17_0>='\u3100' && LA17_0<='\u3104')||(LA17_0>='\u312E' && LA17_0<='\u3130')||(LA17_0>='\u318F' && LA17_0<='\u319F')||(LA17_0>='\u31BB' && LA17_0<='\u31EF')||(LA17_0>='\u3200' && LA17_0<='\u33FF')||(LA17_0>='\u4DB6' && LA17_0<='\u4DFF')||(LA17_0>='\u9FD6' && LA17_0<='\u9FFF')||(LA17_0>='\uA48D' && LA17_0<='\uA4CF')||(LA17_0>='\uA4FE' && LA17_0<='\uA4FF')||(LA17_0>='\uA60D' && LA17_0<='\uA60F')||(LA17_0>='\uA62C' && LA17_0<='\uA63F')||(LA17_0>='\uA66F' && LA17_0<='\uA67E')||(LA17_0>='\uA69E' && LA17_0<='\uA69F')||(LA17_0>='\uA6F0' && LA17_0<='\uA716')||(LA17_0>='\uA720' && LA17_0<='\uA721')||(LA17_0>='\uA789' && LA17_0<='\uA78A')||(LA17_0>='\uA7AE' && LA17_0<='\uA7AF')||(LA17_0>='\uA7B8' && LA17_0<='\uA7F6')||LA17_0=='\uA802'||LA17_0=='\uA806'||LA17_0=='\uA80B'||(LA17_0>='\uA823' && LA17_0<='\uA83F')||(LA17_0>='\uA874' && LA17_0<='\uA881')||(LA17_0>='\uA8B4' && LA17_0<='\uA8CF')||(LA17_0>='\uA8DA' && LA17_0<='\uA8F1')||(LA17_0>='\uA8F8' && LA17_0<='\uA8FA')||LA17_0=='\uA8FC'||(LA17_0>='\uA8FE' && LA17_0<='\uA8FF')||(LA17_0>='\uA926' && LA17_0<='\uA92F')||(LA17_0>='\uA947' && LA17_0<='\uA95F')||(LA17_0>='\uA97D' && LA17_0<='\uA983')||(LA17_0>='\uA9B3' && LA17_0<='\uA9CE')||(LA17_0>='\uA9DA' && LA17_0<='\uA9DF')||LA17_0=='\uA9E5'||LA17_0=='\uA9FF'||(LA17_0>='\uAA29' && LA17_0<='\uAA3F')||LA17_0=='\uAA43'||(LA17_0>='\uAA4C' && LA17_0<='\uAA4F')||(LA17_0>='\uAA5A' && LA17_0<='\uAA5F')||(LA17_0>='\uAA77' && LA17_0<='\uAA79')||(LA17_0>='\uAA7B' && LA17_0<='\uAA7D')||LA17_0=='\uAAB0'||(LA17_0>='\uAAB2' && LA17_0<='\uAAB4')||(LA17_0>='\uAAB7' && LA17_0<='\uAAB8')||(LA17_0>='\uAABE' && LA17_0<='\uAABF')||LA17_0=='\uAAC1'||(LA17_0>='\uAAC3' && LA17_0<='\uAADA')||(LA17_0>='\uAADE' && LA17_0<='\uAADF')||(LA17_0>='\uAAEB' && LA17_0<='\uAAF1')||(LA17_0>='\uAAF5' && LA17_0<='\uAB00')||(LA17_0>='\uAB07' && LA17_0<='\uAB08')||(LA17_0>='\uAB0F' && LA17_0<='\uAB10')||(LA17_0>='\uAB17' && LA17_0<='\uAB1F')||LA17_0=='\uAB27'||LA17_0=='\uAB2F'||LA17_0=='\uAB5B'||(LA17_0>='\uAB66' && LA17_0<='\uAB6F')||(LA17_0>='\uABE3' && LA17_0<='\uABEF')||(LA17_0>='\uABFA' && LA17_0<='\uABFF')||(LA17_0>='\uD7A4' && LA17_0<='\uD7AF')||(LA17_0>='\uD7C7' && LA17_0<='\uD7CA')||(LA17_0>='\uD7FC' && LA17_0<='\uF8FF')||(LA17_0>='\uFA6E' && LA17_0<='\uFA6F')||(LA17_0>='\uFADA' && LA17_0<='\uFAFF')||(LA17_0>='\uFB07' && LA17_0<='\uFB12')||(LA17_0>='\uFB18' && LA17_0<='\uFB1C')||LA17_0=='\uFB1E'||LA17_0=='\uFB29'||LA17_0=='\uFB37'||LA17_0=='\uFB3D'||LA17_0=='\uFB3F'||LA17_0=='\uFB42'||LA17_0=='\uFB45'||(LA17_0>='\uFBB2' && LA17_0<='\uFBD2')||(LA17_0>='\uFD3E' && LA17_0<='\uFD4F')||(LA17_0>='\uFD90' && LA17_0<='\uFD91')||(LA17_0>='\uFDC8' && LA17_0<='\uFDEF')||(LA17_0>='\uFDFC' && LA17_0<='\uFE6F')||LA17_0=='\uFE75'||(LA17_0>='\uFEFD' && LA17_0<='\uFF0F')||(LA17_0>='\uFF1A' && LA17_0<='\uFF20')||(LA17_0>='\uFF3B' && LA17_0<='\uFF40')||(LA17_0>='\uFF5B' && LA17_0<='\uFF65')||(LA17_0>='\uFFBF' && LA17_0<='\uFFC1')||(LA17_0>='\uFFC8' && LA17_0<='\uFFC9')||(LA17_0>='\uFFD0' && LA17_0<='\uFFD1')||(LA17_0>='\uFFD8' && LA17_0<='\uFFD9')||(LA17_0>='\uFFDD' && LA17_0<='\uFFFF')) ) {s = 23;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 17, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}