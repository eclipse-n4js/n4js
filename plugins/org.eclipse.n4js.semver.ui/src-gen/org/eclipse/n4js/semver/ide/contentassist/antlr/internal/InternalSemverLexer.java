package org.eclipse.n4js.semver.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSemverLexer extends Lexer {
    public static final int T__50=50;
    public static final int RULE_WHITESPACE_FRAGMENT=17;
    public static final int RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT=18;
    public static final int RULE_EOL=19;
    public static final int RULE_LETTER_OTHER=15;
    public static final int RULE_UNICODE_COMBINING_MARK_FRAGMENT=30;
    public static final int RULE_ZWNJ=24;
    public static final int RULE_ASTERIX=6;
    public static final int RULE_LETTER_E=14;
    public static final int RULE_ML_COMMENT_FRAGMENT=29;
    public static final int RULE_DIGITS=4;
    public static final int RULE_ZWJ=23;
    public static final int RULE_SL_COMMENT_FRAGMENT=28;
    public static final int RULE_UNICODE_DIGIT_FRAGMENT=31;
    public static final int T__37=37;
    public static final int RULE_LETTER_R=10;
    public static final int T__38=38;
    public static final int RULE_LETTER_S=8;
    public static final int T__39=39;
    public static final int RULE_LETTER_F=11;
    public static final int RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT=26;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int RULE_LETTER_I=12;
    public static final int EOF=-1;
    public static final int RULE_LETTER_L=13;
    public static final int RULE_LETTER_M=9;
    public static final int RULE_WS=16;
    public static final int RULE_BOM=25;
    public static final int RULE_LETTER_V=7;
    public static final int RULE_LETTER_X=5;
    public static final int RULE_ANY_OTHER=34;
    public static final int RULE_LINE_TERMINATOR_FRAGMENT=27;
    public static final int RULE_UNICODE_LETTER_FRAGMENT=33;
    public static final int RULE_DECIMAL_DIGIT_FRAGMENT=20;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_HEX_DIGIT=21;
    public static final int RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT=22;
    public static final int RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT=32;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

    // delegates
    // delegators

    public InternalSemverLexer() {;} 
    public InternalSemverLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalSemverLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalSemver.g"; }

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:11:7: ( '/' )
            // InternalSemver.g:11:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:12:7: ( '.' )
            // InternalSemver.g:12:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:13:7: ( '@' )
            // InternalSemver.g:13:9: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:14:7: ( '-' )
            // InternalSemver.g:14:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:15:7: ( '_' )
            // InternalSemver.g:15:9: '_'
            {
            match('_'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:16:7: ( '+' )
            // InternalSemver.g:16:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:17:7: ( ':' )
            // InternalSemver.g:17:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:18:7: ( '=' )
            // InternalSemver.g:18:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:19:7: ( '<' )
            // InternalSemver.g:19:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:20:7: ( '~' )
            // InternalSemver.g:20:9: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:21:7: ( '^' )
            // InternalSemver.g:21:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:22:7: ( '<=' )
            // InternalSemver.g:22:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:23:7: ( '>' )
            // InternalSemver.g:23:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:24:7: ( '>=' )
            // InternalSemver.g:24:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:25:7: ( '#' )
            // InternalSemver.g:25:9: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:26:7: ( '||' )
            // InternalSemver.g:26:9: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "RULE_LETTER_S"
    public final void mRULE_LETTER_S() throws RecognitionException {
        try {
            int _type = RULE_LETTER_S;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4758:15: ( ( 's' | 'S' ) )
            // InternalSemver.g:4758:17: ( 's' | 'S' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
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
    // $ANTLR end "RULE_LETTER_S"

    // $ANTLR start "RULE_LETTER_M"
    public final void mRULE_LETTER_M() throws RecognitionException {
        try {
            int _type = RULE_LETTER_M;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4760:15: ( ( 'm' | 'M' ) )
            // InternalSemver.g:4760:17: ( 'm' | 'M' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
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
    // $ANTLR end "RULE_LETTER_M"

    // $ANTLR start "RULE_LETTER_R"
    public final void mRULE_LETTER_R() throws RecognitionException {
        try {
            int _type = RULE_LETTER_R;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4762:15: ( ( 'r' | 'R' ) )
            // InternalSemver.g:4762:17: ( 'r' | 'R' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
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
    // $ANTLR end "RULE_LETTER_R"

    // $ANTLR start "RULE_LETTER_F"
    public final void mRULE_LETTER_F() throws RecognitionException {
        try {
            int _type = RULE_LETTER_F;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4764:15: ( ( 'f' | 'F' ) )
            // InternalSemver.g:4764:17: ( 'f' | 'F' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
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
    // $ANTLR end "RULE_LETTER_F"

    // $ANTLR start "RULE_LETTER_I"
    public final void mRULE_LETTER_I() throws RecognitionException {
        try {
            int _type = RULE_LETTER_I;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4766:15: ( ( 'i' | 'I' ) )
            // InternalSemver.g:4766:17: ( 'i' | 'I' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
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
    // $ANTLR end "RULE_LETTER_I"

    // $ANTLR start "RULE_LETTER_L"
    public final void mRULE_LETTER_L() throws RecognitionException {
        try {
            int _type = RULE_LETTER_L;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4768:15: ( ( 'l' | 'L' ) )
            // InternalSemver.g:4768:17: ( 'l' | 'L' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
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
    // $ANTLR end "RULE_LETTER_L"

    // $ANTLR start "RULE_LETTER_E"
    public final void mRULE_LETTER_E() throws RecognitionException {
        try {
            int _type = RULE_LETTER_E;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4770:15: ( ( 'e' | 'E' ) )
            // InternalSemver.g:4770:17: ( 'e' | 'E' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
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
    // $ANTLR end "RULE_LETTER_E"

    // $ANTLR start "RULE_LETTER_V"
    public final void mRULE_LETTER_V() throws RecognitionException {
        try {
            int _type = RULE_LETTER_V;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4772:15: ( ( 'v' | 'V' ) )
            // InternalSemver.g:4772:17: ( 'v' | 'V' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
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
    // $ANTLR end "RULE_LETTER_V"

    // $ANTLR start "RULE_LETTER_X"
    public final void mRULE_LETTER_X() throws RecognitionException {
        try {
            int _type = RULE_LETTER_X;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4774:15: ( ( 'x' | 'X' ) )
            // InternalSemver.g:4774:17: ( 'x' | 'X' )
            {
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
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
    // $ANTLR end "RULE_LETTER_X"

    // $ANTLR start "RULE_LETTER_OTHER"
    public final void mRULE_LETTER_OTHER() throws RecognitionException {
        try {
            int _type = RULE_LETTER_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4776:19: ( ( 'a' | 'A' | 'b' | 'B' | 'c' | 'C' | 'd' | 'D' | 'g' | 'G' | 'h' | 'H' | 'j' | 'J' | 'k' | 'K' | 'n' | 'N' | 'o' | 'O' | 'p' | 'P' | 'q' | 'Q' | 't' | 'T' | 'u' | 'U' | 'w' | 'W' | 'y' | 'Y' | 'z' | 'Z' ) )
            // InternalSemver.g:4776:21: ( 'a' | 'A' | 'b' | 'B' | 'c' | 'C' | 'd' | 'D' | 'g' | 'G' | 'h' | 'H' | 'j' | 'J' | 'k' | 'K' | 'n' | 'N' | 'o' | 'O' | 'p' | 'P' | 'q' | 'Q' | 't' | 'T' | 'u' | 'U' | 'w' | 'W' | 'y' | 'Y' | 'z' | 'Z' )
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='D')||(input.LA(1)>='G' && input.LA(1)<='H')||(input.LA(1)>='J' && input.LA(1)<='K')||(input.LA(1)>='N' && input.LA(1)<='Q')||(input.LA(1)>='T' && input.LA(1)<='U')||input.LA(1)=='W'||(input.LA(1)>='Y' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='d')||(input.LA(1)>='g' && input.LA(1)<='h')||(input.LA(1)>='j' && input.LA(1)<='k')||(input.LA(1)>='n' && input.LA(1)<='q')||(input.LA(1)>='t' && input.LA(1)<='u')||input.LA(1)=='w'||(input.LA(1)>='y' && input.LA(1)<='z') ) {
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
    // $ANTLR end "RULE_LETTER_OTHER"

    // $ANTLR start "RULE_ASTERIX"
    public final void mRULE_ASTERIX() throws RecognitionException {
        try {
            int _type = RULE_ASTERIX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4778:14: ( '*' )
            // InternalSemver.g:4778:16: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ASTERIX"

    // $ANTLR start "RULE_DIGITS"
    public final void mRULE_DIGITS() throws RecognitionException {
        try {
            int _type = RULE_DIGITS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4780:13: ( ( '0' .. '9' )+ )
            // InternalSemver.g:4780:15: ( '0' .. '9' )+
            {
            // InternalSemver.g:4780:15: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSemver.g:4780:16: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_DIGITS"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4782:9: ( ( RULE_WHITESPACE_FRAGMENT )+ )
            // InternalSemver.g:4782:11: ( RULE_WHITESPACE_FRAGMENT )+
            {
            // InternalSemver.g:4782:11: ( RULE_WHITESPACE_FRAGMENT )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\t'||(LA2_0>='\u000B' && LA2_0<='\f')||LA2_0==' '||LA2_0=='\u00A0'||LA2_0=='\u1680'||(LA2_0>='\u2000' && LA2_0<='\u200A')||LA2_0=='\u202F'||LA2_0=='\u205F'||LA2_0=='\u3000'||LA2_0=='\uFEFF') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSemver.g:4782:11: RULE_WHITESPACE_FRAGMENT
            	    {
            	    mRULE_WHITESPACE_FRAGMENT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_EOL"
    public final void mRULE_EOL() throws RecognitionException {
        try {
            int _type = RULE_EOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSemver.g:4784:10: ( RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT )
            // InternalSemver.g:4784:12: RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT
            {
            mRULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_EOL"

    // $ANTLR start "RULE_HEX_DIGIT"
    public final void mRULE_HEX_DIGIT() throws RecognitionException {
        try {
            // InternalSemver.g:4786:25: ( ( RULE_DECIMAL_DIGIT_FRAGMENT | 'a' .. 'f' | 'A' .. 'F' ) )
            // InternalSemver.g:4786:27: ( RULE_DECIMAL_DIGIT_FRAGMENT | 'a' .. 'f' | 'A' .. 'F' )
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
            // InternalSemver.g:4788:48: ( ( '0' | '1' .. '9' ( RULE_DECIMAL_DIGIT_FRAGMENT )* ) )
            // InternalSemver.g:4788:50: ( '0' | '1' .. '9' ( RULE_DECIMAL_DIGIT_FRAGMENT )* )
            {
            // InternalSemver.g:4788:50: ( '0' | '1' .. '9' ( RULE_DECIMAL_DIGIT_FRAGMENT )* )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='0') ) {
                alt4=1;
            }
            else if ( ((LA4_0>='1' && LA4_0<='9')) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // InternalSemver.g:4788:51: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // InternalSemver.g:4788:55: '1' .. '9' ( RULE_DECIMAL_DIGIT_FRAGMENT )*
                    {
                    matchRange('1','9'); 
                    // InternalSemver.g:4788:64: ( RULE_DECIMAL_DIGIT_FRAGMENT )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // InternalSemver.g:4788:64: RULE_DECIMAL_DIGIT_FRAGMENT
                    	    {
                    	    mRULE_DECIMAL_DIGIT_FRAGMENT(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
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
            // InternalSemver.g:4790:38: ( '0' .. '9' )
            // InternalSemver.g:4790:40: '0' .. '9'
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
            // InternalSemver.g:4792:19: ( '\\u200D' )
            // InternalSemver.g:4792:21: '\\u200D'
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
            // InternalSemver.g:4794:20: ( '\\u200C' )
            // InternalSemver.g:4794:22: '\\u200C'
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
            // InternalSemver.g:4796:19: ( '\\uFEFF' )
            // InternalSemver.g:4796:21: '\\uFEFF'
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
            // InternalSemver.g:4798:35: ( ( '\\t' | '\\u000B' | '\\f' | ' ' | '\\u00A0' | RULE_BOM | RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT ) )
            // InternalSemver.g:4798:37: ( '\\t' | '\\u000B' | '\\f' | ' ' | '\\u00A0' | RULE_BOM | RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT )
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
            // InternalSemver.g:4800:40: ( ( '\\n' | '\\r' | '\\u2028' | '\\u2029' ) )
            // InternalSemver.g:4800:42: ( '\\n' | '\\r' | '\\u2028' | '\\u2029' )
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
            // InternalSemver.g:4802:49: ( ( '\\n' | '\\r' ( '\\n' )? | '\\u2028' | '\\u2029' ) )
            // InternalSemver.g:4802:51: ( '\\n' | '\\r' ( '\\n' )? | '\\u2028' | '\\u2029' )
            {
            // InternalSemver.g:4802:51: ( '\\n' | '\\r' ( '\\n' )? | '\\u2028' | '\\u2029' )
            int alt6=4;
            switch ( input.LA(1) ) {
            case '\n':
                {
                alt6=1;
                }
                break;
            case '\r':
                {
                alt6=2;
                }
                break;
            case '\u2028':
                {
                alt6=3;
                }
                break;
            case '\u2029':
                {
                alt6=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalSemver.g:4802:52: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 2 :
                    // InternalSemver.g:4802:57: '\\r' ( '\\n' )?
                    {
                    match('\r'); 
                    // InternalSemver.g:4802:62: ( '\\n' )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='\n') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // InternalSemver.g:4802:62: '\\n'
                            {
                            match('\n'); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // InternalSemver.g:4802:68: '\\u2028'
                    {
                    match('\u2028'); 

                    }
                    break;
                case 4 :
                    // InternalSemver.g:4802:77: '\\u2029'
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
            // InternalSemver.g:4804:35: ( '//' (~ ( RULE_LINE_TERMINATOR_FRAGMENT ) )* )
            // InternalSemver.g:4804:37: '//' (~ ( RULE_LINE_TERMINATOR_FRAGMENT ) )*
            {
            match("//"); 

            // InternalSemver.g:4804:42: (~ ( RULE_LINE_TERMINATOR_FRAGMENT ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='\u0000' && LA7_0<='\t')||(LA7_0>='\u000B' && LA7_0<='\f')||(LA7_0>='\u000E' && LA7_0<='\u2027')||(LA7_0>='\u202A' && LA7_0<='\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalSemver.g:4804:42: ~ ( RULE_LINE_TERMINATOR_FRAGMENT )
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
            	    break loop7;
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
            // InternalSemver.g:4806:35: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalSemver.g:4806:37: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalSemver.g:4806:42: ( options {greedy=false; } : . )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='*') ) {
                    int LA8_1 = input.LA(2);

                    if ( (LA8_1=='/') ) {
                        alt8=2;
                    }
                    else if ( ((LA8_1>='\u0000' && LA8_1<='.')||(LA8_1>='0' && LA8_1<='\uFFFF')) ) {
                        alt8=1;
                    }


                }
                else if ( ((LA8_0>='\u0000' && LA8_0<=')')||(LA8_0>='+' && LA8_0<='\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalSemver.g:4806:70: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop8;
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
            // InternalSemver.g:4808:47: ( ( '\\u0300' .. '\\u036F' | '\\u0483' .. '\\u0487' | '\\u0591' .. '\\u05BD' | '\\u05BF' | '\\u05C1' .. '\\u05C2' | '\\u05C4' .. '\\u05C5' | '\\u05C7' | '\\u0610' .. '\\u061A' | '\\u064B' .. '\\u065F' | '\\u0670' | '\\u06D6' .. '\\u06DC' | '\\u06DF' .. '\\u06E4' | '\\u06E7' .. '\\u06E8' | '\\u06EA' .. '\\u06ED' | '\\u0711' | '\\u0730' .. '\\u074A' | '\\u07A6' .. '\\u07B0' | '\\u07EB' .. '\\u07F3' | '\\u0816' .. '\\u0819' | '\\u081B' .. '\\u0823' | '\\u0825' .. '\\u0827' | '\\u0829' .. '\\u082D' | '\\u0859' .. '\\u085B' | '\\u08E3' .. '\\u0903' | '\\u093A' .. '\\u093C' | '\\u093E' .. '\\u094F' | '\\u0951' .. '\\u0957' | '\\u0962' .. '\\u0963' | '\\u0981' .. '\\u0983' | '\\u09BC' | '\\u09BE' .. '\\u09C4' | '\\u09C7' .. '\\u09C8' | '\\u09CB' .. '\\u09CD' | '\\u09D7' | '\\u09E2' .. '\\u09E3' | '\\u0A01' .. '\\u0A03' | '\\u0A3C' | '\\u0A3E' .. '\\u0A42' | '\\u0A47' .. '\\u0A48' | '\\u0A4B' .. '\\u0A4D' | '\\u0A51' | '\\u0A70' .. '\\u0A71' | '\\u0A75' | '\\u0A81' .. '\\u0A83' | '\\u0ABC' | '\\u0ABE' .. '\\u0AC5' | '\\u0AC7' .. '\\u0AC9' | '\\u0ACB' .. '\\u0ACD' | '\\u0AE2' .. '\\u0AE3' | '\\u0B01' .. '\\u0B03' | '\\u0B3C' | '\\u0B3E' .. '\\u0B44' | '\\u0B47' .. '\\u0B48' | '\\u0B4B' .. '\\u0B4D' | '\\u0B56' .. '\\u0B57' | '\\u0B62' .. '\\u0B63' | '\\u0B82' | '\\u0BBE' .. '\\u0BC2' | '\\u0BC6' .. '\\u0BC8' | '\\u0BCA' .. '\\u0BCD' | '\\u0BD7' | '\\u0C00' .. '\\u0C03' | '\\u0C3E' .. '\\u0C44' | '\\u0C46' .. '\\u0C48' | '\\u0C4A' .. '\\u0C4D' | '\\u0C55' .. '\\u0C56' | '\\u0C62' .. '\\u0C63' | '\\u0C81' .. '\\u0C83' | '\\u0CBC' | '\\u0CBE' .. '\\u0CC4' | '\\u0CC6' .. '\\u0CC8' | '\\u0CCA' .. '\\u0CCD' | '\\u0CD5' .. '\\u0CD6' | '\\u0CE2' .. '\\u0CE3' | '\\u0D01' .. '\\u0D03' | '\\u0D3E' .. '\\u0D44' | '\\u0D46' .. '\\u0D48' | '\\u0D4A' .. '\\u0D4D' | '\\u0D57' | '\\u0D62' .. '\\u0D63' | '\\u0D82' .. '\\u0D83' | '\\u0DCA' | '\\u0DCF' .. '\\u0DD4' | '\\u0DD6' | '\\u0DD8' .. '\\u0DDF' | '\\u0DF2' .. '\\u0DF3' | '\\u0E31' | '\\u0E34' .. '\\u0E3A' | '\\u0E47' .. '\\u0E4E' | '\\u0EB1' | '\\u0EB4' .. '\\u0EB9' | '\\u0EBB' .. '\\u0EBC' | '\\u0EC8' .. '\\u0ECD' | '\\u0F18' .. '\\u0F19' | '\\u0F35' | '\\u0F37' | '\\u0F39' | '\\u0F3E' .. '\\u0F3F' | '\\u0F71' .. '\\u0F84' | '\\u0F86' .. '\\u0F87' | '\\u0F8D' .. '\\u0F97' | '\\u0F99' .. '\\u0FBC' | '\\u0FC6' | '\\u102B' .. '\\u103E' | '\\u1056' .. '\\u1059' | '\\u105E' .. '\\u1060' | '\\u1062' .. '\\u1064' | '\\u1067' .. '\\u106D' | '\\u1071' .. '\\u1074' | '\\u1082' .. '\\u108D' | '\\u108F' | '\\u109A' .. '\\u109D' | '\\u135D' .. '\\u135F' | '\\u1712' .. '\\u1714' | '\\u1732' .. '\\u1734' | '\\u1752' .. '\\u1753' | '\\u1772' .. '\\u1773' | '\\u17B4' .. '\\u17D3' | '\\u17DD' | '\\u180B' .. '\\u180D' | '\\u18A9' | '\\u1920' .. '\\u192B' | '\\u1930' .. '\\u193B' | '\\u1A17' .. '\\u1A1B' | '\\u1A55' .. '\\u1A5E' | '\\u1A60' .. '\\u1A7C' | '\\u1A7F' | '\\u1AB0' .. '\\u1ABD' | '\\u1B00' .. '\\u1B04' | '\\u1B34' .. '\\u1B44' | '\\u1B6B' .. '\\u1B73' | '\\u1B80' .. '\\u1B82' | '\\u1BA1' .. '\\u1BAD' | '\\u1BE6' .. '\\u1BF3' | '\\u1C24' .. '\\u1C37' | '\\u1CD0' .. '\\u1CD2' | '\\u1CD4' .. '\\u1CE8' | '\\u1CED' | '\\u1CF2' .. '\\u1CF4' | '\\u1CF8' .. '\\u1CF9' | '\\u1DC0' .. '\\u1DF5' | '\\u1DFC' .. '\\u1DFF' | '\\u20D0' .. '\\u20DC' | '\\u20E1' | '\\u20E5' .. '\\u20F0' | '\\u2CEF' .. '\\u2CF1' | '\\u2D7F' | '\\u2DE0' .. '\\u2DFF' | '\\u302A' .. '\\u302F' | '\\u3099' .. '\\u309A' | '\\uA66F' | '\\uA674' .. '\\uA67D' | '\\uA69E' .. '\\uA69F' | '\\uA6F0' .. '\\uA6F1' | '\\uA802' | '\\uA806' | '\\uA80B' | '\\uA823' .. '\\uA827' | '\\uA880' .. '\\uA881' | '\\uA8B4' .. '\\uA8C4' | '\\uA8E0' .. '\\uA8F1' | '\\uA926' .. '\\uA92D' | '\\uA947' .. '\\uA953' | '\\uA980' .. '\\uA983' | '\\uA9B3' .. '\\uA9C0' | '\\uA9E5' | '\\uAA29' .. '\\uAA36' | '\\uAA43' | '\\uAA4C' .. '\\uAA4D' | '\\uAA7B' .. '\\uAA7D' | '\\uAAB0' | '\\uAAB2' .. '\\uAAB4' | '\\uAAB7' .. '\\uAAB8' | '\\uAABE' .. '\\uAABF' | '\\uAAC1' | '\\uAAEB' .. '\\uAAEF' | '\\uAAF5' .. '\\uAAF6' | '\\uABE3' .. '\\uABEA' | '\\uABEC' .. '\\uABED' | '\\uFB1E' | '\\uFE00' .. '\\uFE0F' | '\\uFE20' .. '\\uFE2F' ) )
            // InternalSemver.g:4808:49: ( '\\u0300' .. '\\u036F' | '\\u0483' .. '\\u0487' | '\\u0591' .. '\\u05BD' | '\\u05BF' | '\\u05C1' .. '\\u05C2' | '\\u05C4' .. '\\u05C5' | '\\u05C7' | '\\u0610' .. '\\u061A' | '\\u064B' .. '\\u065F' | '\\u0670' | '\\u06D6' .. '\\u06DC' | '\\u06DF' .. '\\u06E4' | '\\u06E7' .. '\\u06E8' | '\\u06EA' .. '\\u06ED' | '\\u0711' | '\\u0730' .. '\\u074A' | '\\u07A6' .. '\\u07B0' | '\\u07EB' .. '\\u07F3' | '\\u0816' .. '\\u0819' | '\\u081B' .. '\\u0823' | '\\u0825' .. '\\u0827' | '\\u0829' .. '\\u082D' | '\\u0859' .. '\\u085B' | '\\u08E3' .. '\\u0903' | '\\u093A' .. '\\u093C' | '\\u093E' .. '\\u094F' | '\\u0951' .. '\\u0957' | '\\u0962' .. '\\u0963' | '\\u0981' .. '\\u0983' | '\\u09BC' | '\\u09BE' .. '\\u09C4' | '\\u09C7' .. '\\u09C8' | '\\u09CB' .. '\\u09CD' | '\\u09D7' | '\\u09E2' .. '\\u09E3' | '\\u0A01' .. '\\u0A03' | '\\u0A3C' | '\\u0A3E' .. '\\u0A42' | '\\u0A47' .. '\\u0A48' | '\\u0A4B' .. '\\u0A4D' | '\\u0A51' | '\\u0A70' .. '\\u0A71' | '\\u0A75' | '\\u0A81' .. '\\u0A83' | '\\u0ABC' | '\\u0ABE' .. '\\u0AC5' | '\\u0AC7' .. '\\u0AC9' | '\\u0ACB' .. '\\u0ACD' | '\\u0AE2' .. '\\u0AE3' | '\\u0B01' .. '\\u0B03' | '\\u0B3C' | '\\u0B3E' .. '\\u0B44' | '\\u0B47' .. '\\u0B48' | '\\u0B4B' .. '\\u0B4D' | '\\u0B56' .. '\\u0B57' | '\\u0B62' .. '\\u0B63' | '\\u0B82' | '\\u0BBE' .. '\\u0BC2' | '\\u0BC6' .. '\\u0BC8' | '\\u0BCA' .. '\\u0BCD' | '\\u0BD7' | '\\u0C00' .. '\\u0C03' | '\\u0C3E' .. '\\u0C44' | '\\u0C46' .. '\\u0C48' | '\\u0C4A' .. '\\u0C4D' | '\\u0C55' .. '\\u0C56' | '\\u0C62' .. '\\u0C63' | '\\u0C81' .. '\\u0C83' | '\\u0CBC' | '\\u0CBE' .. '\\u0CC4' | '\\u0CC6' .. '\\u0CC8' | '\\u0CCA' .. '\\u0CCD' | '\\u0CD5' .. '\\u0CD6' | '\\u0CE2' .. '\\u0CE3' | '\\u0D01' .. '\\u0D03' | '\\u0D3E' .. '\\u0D44' | '\\u0D46' .. '\\u0D48' | '\\u0D4A' .. '\\u0D4D' | '\\u0D57' | '\\u0D62' .. '\\u0D63' | '\\u0D82' .. '\\u0D83' | '\\u0DCA' | '\\u0DCF' .. '\\u0DD4' | '\\u0DD6' | '\\u0DD8' .. '\\u0DDF' | '\\u0DF2' .. '\\u0DF3' | '\\u0E31' | '\\u0E34' .. '\\u0E3A' | '\\u0E47' .. '\\u0E4E' | '\\u0EB1' | '\\u0EB4' .. '\\u0EB9' | '\\u0EBB' .. '\\u0EBC' | '\\u0EC8' .. '\\u0ECD' | '\\u0F18' .. '\\u0F19' | '\\u0F35' | '\\u0F37' | '\\u0F39' | '\\u0F3E' .. '\\u0F3F' | '\\u0F71' .. '\\u0F84' | '\\u0F86' .. '\\u0F87' | '\\u0F8D' .. '\\u0F97' | '\\u0F99' .. '\\u0FBC' | '\\u0FC6' | '\\u102B' .. '\\u103E' | '\\u1056' .. '\\u1059' | '\\u105E' .. '\\u1060' | '\\u1062' .. '\\u1064' | '\\u1067' .. '\\u106D' | '\\u1071' .. '\\u1074' | '\\u1082' .. '\\u108D' | '\\u108F' | '\\u109A' .. '\\u109D' | '\\u135D' .. '\\u135F' | '\\u1712' .. '\\u1714' | '\\u1732' .. '\\u1734' | '\\u1752' .. '\\u1753' | '\\u1772' .. '\\u1773' | '\\u17B4' .. '\\u17D3' | '\\u17DD' | '\\u180B' .. '\\u180D' | '\\u18A9' | '\\u1920' .. '\\u192B' | '\\u1930' .. '\\u193B' | '\\u1A17' .. '\\u1A1B' | '\\u1A55' .. '\\u1A5E' | '\\u1A60' .. '\\u1A7C' | '\\u1A7F' | '\\u1AB0' .. '\\u1ABD' | '\\u1B00' .. '\\u1B04' | '\\u1B34' .. '\\u1B44' | '\\u1B6B' .. '\\u1B73' | '\\u1B80' .. '\\u1B82' | '\\u1BA1' .. '\\u1BAD' | '\\u1BE6' .. '\\u1BF3' | '\\u1C24' .. '\\u1C37' | '\\u1CD0' .. '\\u1CD2' | '\\u1CD4' .. '\\u1CE8' | '\\u1CED' | '\\u1CF2' .. '\\u1CF4' | '\\u1CF8' .. '\\u1CF9' | '\\u1DC0' .. '\\u1DF5' | '\\u1DFC' .. '\\u1DFF' | '\\u20D0' .. '\\u20DC' | '\\u20E1' | '\\u20E5' .. '\\u20F0' | '\\u2CEF' .. '\\u2CF1' | '\\u2D7F' | '\\u2DE0' .. '\\u2DFF' | '\\u302A' .. '\\u302F' | '\\u3099' .. '\\u309A' | '\\uA66F' | '\\uA674' .. '\\uA67D' | '\\uA69E' .. '\\uA69F' | '\\uA6F0' .. '\\uA6F1' | '\\uA802' | '\\uA806' | '\\uA80B' | '\\uA823' .. '\\uA827' | '\\uA880' .. '\\uA881' | '\\uA8B4' .. '\\uA8C4' | '\\uA8E0' .. '\\uA8F1' | '\\uA926' .. '\\uA92D' | '\\uA947' .. '\\uA953' | '\\uA980' .. '\\uA983' | '\\uA9B3' .. '\\uA9C0' | '\\uA9E5' | '\\uAA29' .. '\\uAA36' | '\\uAA43' | '\\uAA4C' .. '\\uAA4D' | '\\uAA7B' .. '\\uAA7D' | '\\uAAB0' | '\\uAAB2' .. '\\uAAB4' | '\\uAAB7' .. '\\uAAB8' | '\\uAABE' .. '\\uAABF' | '\\uAAC1' | '\\uAAEB' .. '\\uAAEF' | '\\uAAF5' .. '\\uAAF6' | '\\uABE3' .. '\\uABEA' | '\\uABEC' .. '\\uABED' | '\\uFB1E' | '\\uFE00' .. '\\uFE0F' | '\\uFE20' .. '\\uFE2F' )
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
            // InternalSemver.g:4810:38: ( ( '0' .. '9' | '\\u0660' .. '\\u0669' | '\\u06F0' .. '\\u06F9' | '\\u07C0' .. '\\u07C9' | '\\u0966' .. '\\u096F' | '\\u09E6' .. '\\u09EF' | '\\u0A66' .. '\\u0A6F' | '\\u0AE6' .. '\\u0AEF' | '\\u0B66' .. '\\u0B6F' | '\\u0BE6' .. '\\u0BEF' | '\\u0C66' .. '\\u0C6F' | '\\u0CE6' .. '\\u0CEF' | '\\u0D66' .. '\\u0D6F' | '\\u0DE6' .. '\\u0DEF' | '\\u0E50' .. '\\u0E59' | '\\u0ED0' .. '\\u0ED9' | '\\u0F20' .. '\\u0F29' | '\\u1040' .. '\\u1049' | '\\u1090' .. '\\u1099' | '\\u17E0' .. '\\u17E9' | '\\u1810' .. '\\u1819' | '\\u1946' .. '\\u194F' | '\\u19D0' .. '\\u19D9' | '\\u1A80' .. '\\u1A89' | '\\u1A90' .. '\\u1A99' | '\\u1B50' .. '\\u1B59' | '\\u1BB0' .. '\\u1BB9' | '\\u1C40' .. '\\u1C49' | '\\u1C50' .. '\\u1C59' | '\\uA620' .. '\\uA629' | '\\uA8D0' .. '\\uA8D9' | '\\uA900' .. '\\uA909' | '\\uA9D0' .. '\\uA9D9' | '\\uA9F0' .. '\\uA9F9' | '\\uAA50' .. '\\uAA59' | '\\uABF0' .. '\\uABF9' | '\\uFF10' .. '\\uFF19' ) )
            // InternalSemver.g:4810:40: ( '0' .. '9' | '\\u0660' .. '\\u0669' | '\\u06F0' .. '\\u06F9' | '\\u07C0' .. '\\u07C9' | '\\u0966' .. '\\u096F' | '\\u09E6' .. '\\u09EF' | '\\u0A66' .. '\\u0A6F' | '\\u0AE6' .. '\\u0AEF' | '\\u0B66' .. '\\u0B6F' | '\\u0BE6' .. '\\u0BEF' | '\\u0C66' .. '\\u0C6F' | '\\u0CE6' .. '\\u0CEF' | '\\u0D66' .. '\\u0D6F' | '\\u0DE6' .. '\\u0DEF' | '\\u0E50' .. '\\u0E59' | '\\u0ED0' .. '\\u0ED9' | '\\u0F20' .. '\\u0F29' | '\\u1040' .. '\\u1049' | '\\u1090' .. '\\u1099' | '\\u17E0' .. '\\u17E9' | '\\u1810' .. '\\u1819' | '\\u1946' .. '\\u194F' | '\\u19D0' .. '\\u19D9' | '\\u1A80' .. '\\u1A89' | '\\u1A90' .. '\\u1A99' | '\\u1B50' .. '\\u1B59' | '\\u1BB0' .. '\\u1BB9' | '\\u1C40' .. '\\u1C49' | '\\u1C50' .. '\\u1C59' | '\\uA620' .. '\\uA629' | '\\uA8D0' .. '\\uA8D9' | '\\uA900' .. '\\uA909' | '\\uA9D0' .. '\\uA9D9' | '\\uA9F0' .. '\\uA9F9' | '\\uAA50' .. '\\uAA59' | '\\uABF0' .. '\\uABF9' | '\\uFF10' .. '\\uFF19' )
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
            // InternalSemver.g:4812:54: ( ( '_' | '\\u203F' .. '\\u2040' | '\\u2054' | '\\uFE33' .. '\\uFE34' | '\\uFE4D' .. '\\uFE4F' | '\\uFF3F' ) )
            // InternalSemver.g:4812:56: ( '_' | '\\u203F' .. '\\u2040' | '\\u2054' | '\\uFE33' .. '\\uFE34' | '\\uFE4D' .. '\\uFE4F' | '\\uFF3F' )
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
            // InternalSemver.g:4814:39: ( ( 'A' .. 'Z' | 'a' .. 'z' | '\\u00AA' | '\\u00B5' | '\\u00BA' | '\\u00C0' .. '\\u00D6' | '\\u00D8' .. '\\u00F6' | '\\u00F8' .. '\\u02C1' | '\\u02C6' .. '\\u02D1' | '\\u02E0' .. '\\u02E4' | '\\u02EC' | '\\u02EE' | '\\u0370' .. '\\u0374' | '\\u0376' .. '\\u0377' | '\\u037A' .. '\\u037D' | '\\u037F' | '\\u0386' | '\\u0388' .. '\\u038A' | '\\u038C' | '\\u038E' .. '\\u03A1' | '\\u03A3' .. '\\u03F5' | '\\u03F7' .. '\\u0481' | '\\u048A' .. '\\u052F' | '\\u0531' .. '\\u0556' | '\\u0559' | '\\u0561' .. '\\u0587' | '\\u05D0' .. '\\u05EA' | '\\u05F0' .. '\\u05F2' | '\\u0620' .. '\\u064A' | '\\u066E' .. '\\u066F' | '\\u0671' .. '\\u06D3' | '\\u06D5' | '\\u06E5' .. '\\u06E6' | '\\u06EE' .. '\\u06EF' | '\\u06FA' .. '\\u06FC' | '\\u06FF' | '\\u0710' | '\\u0712' .. '\\u072F' | '\\u074D' .. '\\u07A5' | '\\u07B1' | '\\u07CA' .. '\\u07EA' | '\\u07F4' .. '\\u07F5' | '\\u07FA' | '\\u0800' .. '\\u0815' | '\\u081A' | '\\u0824' | '\\u0828' | '\\u0840' .. '\\u0858' | '\\u08A0' .. '\\u08B4' | '\\u0904' .. '\\u0939' | '\\u093D' | '\\u0950' | '\\u0958' .. '\\u0961' | '\\u0971' .. '\\u0980' | '\\u0985' .. '\\u098C' | '\\u098F' .. '\\u0990' | '\\u0993' .. '\\u09A8' | '\\u09AA' .. '\\u09B0' | '\\u09B2' | '\\u09B6' .. '\\u09B9' | '\\u09BD' | '\\u09CE' | '\\u09DC' .. '\\u09DD' | '\\u09DF' .. '\\u09E1' | '\\u09F0' .. '\\u09F1' | '\\u0A05' .. '\\u0A0A' | '\\u0A0F' .. '\\u0A10' | '\\u0A13' .. '\\u0A28' | '\\u0A2A' .. '\\u0A30' | '\\u0A32' .. '\\u0A33' | '\\u0A35' .. '\\u0A36' | '\\u0A38' .. '\\u0A39' | '\\u0A59' .. '\\u0A5C' | '\\u0A5E' | '\\u0A72' .. '\\u0A74' | '\\u0A85' .. '\\u0A8D' | '\\u0A8F' .. '\\u0A91' | '\\u0A93' .. '\\u0AA8' | '\\u0AAA' .. '\\u0AB0' | '\\u0AB2' .. '\\u0AB3' | '\\u0AB5' .. '\\u0AB9' | '\\u0ABD' | '\\u0AD0' | '\\u0AE0' .. '\\u0AE1' | '\\u0AF9' | '\\u0B05' .. '\\u0B0C' | '\\u0B0F' .. '\\u0B10' | '\\u0B13' .. '\\u0B28' | '\\u0B2A' .. '\\u0B30' | '\\u0B32' .. '\\u0B33' | '\\u0B35' .. '\\u0B39' | '\\u0B3D' | '\\u0B5C' .. '\\u0B5D' | '\\u0B5F' .. '\\u0B61' | '\\u0B71' | '\\u0B83' | '\\u0B85' .. '\\u0B8A' | '\\u0B8E' .. '\\u0B90' | '\\u0B92' .. '\\u0B95' | '\\u0B99' .. '\\u0B9A' | '\\u0B9C' | '\\u0B9E' .. '\\u0B9F' | '\\u0BA3' .. '\\u0BA4' | '\\u0BA8' .. '\\u0BAA' | '\\u0BAE' .. '\\u0BB9' | '\\u0BD0' | '\\u0C05' .. '\\u0C0C' | '\\u0C0E' .. '\\u0C10' | '\\u0C12' .. '\\u0C28' | '\\u0C2A' .. '\\u0C39' | '\\u0C3D' | '\\u0C58' .. '\\u0C5A' | '\\u0C60' .. '\\u0C61' | '\\u0C85' .. '\\u0C8C' | '\\u0C8E' .. '\\u0C90' | '\\u0C92' .. '\\u0CA8' | '\\u0CAA' .. '\\u0CB3' | '\\u0CB5' .. '\\u0CB9' | '\\u0CBD' | '\\u0CDE' | '\\u0CE0' .. '\\u0CE1' | '\\u0CF1' .. '\\u0CF2' | '\\u0D05' .. '\\u0D0C' | '\\u0D0E' .. '\\u0D10' | '\\u0D12' .. '\\u0D3A' | '\\u0D3D' | '\\u0D4E' | '\\u0D5F' .. '\\u0D61' | '\\u0D7A' .. '\\u0D7F' | '\\u0D85' .. '\\u0D96' | '\\u0D9A' .. '\\u0DB1' | '\\u0DB3' .. '\\u0DBB' | '\\u0DBD' | '\\u0DC0' .. '\\u0DC6' | '\\u0E01' .. '\\u0E30' | '\\u0E32' .. '\\u0E33' | '\\u0E40' .. '\\u0E46' | '\\u0E81' .. '\\u0E82' | '\\u0E84' | '\\u0E87' .. '\\u0E88' | '\\u0E8A' | '\\u0E8D' | '\\u0E94' .. '\\u0E97' | '\\u0E99' .. '\\u0E9F' | '\\u0EA1' .. '\\u0EA3' | '\\u0EA5' | '\\u0EA7' | '\\u0EAA' .. '\\u0EAB' | '\\u0EAD' .. '\\u0EB0' | '\\u0EB2' .. '\\u0EB3' | '\\u0EBD' | '\\u0EC0' .. '\\u0EC4' | '\\u0EC6' | '\\u0EDC' .. '\\u0EDF' | '\\u0F00' | '\\u0F40' .. '\\u0F47' | '\\u0F49' .. '\\u0F6C' | '\\u0F88' .. '\\u0F8C' | '\\u1000' .. '\\u102A' | '\\u103F' | '\\u1050' .. '\\u1055' | '\\u105A' .. '\\u105D' | '\\u1061' | '\\u1065' .. '\\u1066' | '\\u106E' .. '\\u1070' | '\\u1075' .. '\\u1081' | '\\u108E' | '\\u10A0' .. '\\u10C5' | '\\u10C7' | '\\u10CD' | '\\u10D0' .. '\\u10FA' | '\\u10FC' .. '\\u1248' | '\\u124A' .. '\\u124D' | '\\u1250' .. '\\u1256' | '\\u1258' | '\\u125A' .. '\\u125D' | '\\u1260' .. '\\u1288' | '\\u128A' .. '\\u128D' | '\\u1290' .. '\\u12B0' | '\\u12B2' .. '\\u12B5' | '\\u12B8' .. '\\u12BE' | '\\u12C0' | '\\u12C2' .. '\\u12C5' | '\\u12C8' .. '\\u12D6' | '\\u12D8' .. '\\u1310' | '\\u1312' .. '\\u1315' | '\\u1318' .. '\\u135A' | '\\u1380' .. '\\u138F' | '\\u13A0' .. '\\u13F5' | '\\u13F8' .. '\\u13FD' | '\\u1401' .. '\\u166C' | '\\u166F' .. '\\u167F' | '\\u1681' .. '\\u169A' | '\\u16A0' .. '\\u16EA' | '\\u16EE' .. '\\u16F8' | '\\u1700' .. '\\u170C' | '\\u170E' .. '\\u1711' | '\\u1720' .. '\\u1731' | '\\u1740' .. '\\u1751' | '\\u1760' .. '\\u176C' | '\\u176E' .. '\\u1770' | '\\u1780' .. '\\u17B3' | '\\u17D7' | '\\u17DC' | '\\u1820' .. '\\u1877' | '\\u1880' .. '\\u18A8' | '\\u18AA' | '\\u18B0' .. '\\u18F5' | '\\u1900' .. '\\u191E' | '\\u1950' .. '\\u196D' | '\\u1970' .. '\\u1974' | '\\u1980' .. '\\u19AB' | '\\u19B0' .. '\\u19C9' | '\\u1A00' .. '\\u1A16' | '\\u1A20' .. '\\u1A54' | '\\u1AA7' | '\\u1B05' .. '\\u1B33' | '\\u1B45' .. '\\u1B4B' | '\\u1B83' .. '\\u1BA0' | '\\u1BAE' .. '\\u1BAF' | '\\u1BBA' .. '\\u1BE5' | '\\u1C00' .. '\\u1C23' | '\\u1C4D' .. '\\u1C4F' | '\\u1C5A' .. '\\u1C7D' | '\\u1CE9' .. '\\u1CEC' | '\\u1CEE' .. '\\u1CF1' | '\\u1CF5' .. '\\u1CF6' | '\\u1D00' .. '\\u1DBF' | '\\u1E00' .. '\\u1F15' | '\\u1F18' .. '\\u1F1D' | '\\u1F20' .. '\\u1F45' | '\\u1F48' .. '\\u1F4D' | '\\u1F50' .. '\\u1F57' | '\\u1F59' | '\\u1F5B' | '\\u1F5D' | '\\u1F5F' .. '\\u1F7D' | '\\u1F80' .. '\\u1FB4' | '\\u1FB6' .. '\\u1FBC' | '\\u1FBE' | '\\u1FC2' .. '\\u1FC4' | '\\u1FC6' .. '\\u1FCC' | '\\u1FD0' .. '\\u1FD3' | '\\u1FD6' .. '\\u1FDB' | '\\u1FE0' .. '\\u1FEC' | '\\u1FF2' .. '\\u1FF4' | '\\u1FF6' .. '\\u1FFC' | '\\u2071' | '\\u207F' | '\\u2090' .. '\\u209C' | '\\u2102' | '\\u2107' | '\\u210A' .. '\\u2113' | '\\u2115' | '\\u2119' .. '\\u211D' | '\\u2124' | '\\u2126' | '\\u2128' | '\\u212A' .. '\\u212D' | '\\u212F' .. '\\u2139' | '\\u213C' .. '\\u213F' | '\\u2145' .. '\\u2149' | '\\u214E' | '\\u2160' .. '\\u2188' | '\\u2C00' .. '\\u2C2E' | '\\u2C30' .. '\\u2C5E' | '\\u2C60' .. '\\u2CE4' | '\\u2CEB' .. '\\u2CEE' | '\\u2CF2' .. '\\u2CF3' | '\\u2D00' .. '\\u2D25' | '\\u2D27' | '\\u2D2D' | '\\u2D30' .. '\\u2D67' | '\\u2D6F' | '\\u2D80' .. '\\u2D96' | '\\u2DA0' .. '\\u2DA6' | '\\u2DA8' .. '\\u2DAE' | '\\u2DB0' .. '\\u2DB6' | '\\u2DB8' .. '\\u2DBE' | '\\u2DC0' .. '\\u2DC6' | '\\u2DC8' .. '\\u2DCE' | '\\u2DD0' .. '\\u2DD6' | '\\u2DD8' .. '\\u2DDE' | '\\u2E2F' | '\\u3005' .. '\\u3007' | '\\u3021' .. '\\u3029' | '\\u3031' .. '\\u3035' | '\\u3038' .. '\\u303C' | '\\u3041' .. '\\u3096' | '\\u309D' .. '\\u309F' | '\\u30A1' .. '\\u30FA' | '\\u30FC' .. '\\u30FF' | '\\u3105' .. '\\u312D' | '\\u3131' .. '\\u318E' | '\\u31A0' .. '\\u31BA' | '\\u31F0' .. '\\u31FF' | '\\u3400' .. '\\u4DB5' | '\\u4E00' .. '\\u9FD5' | '\\uA000' .. '\\uA48C' | '\\uA4D0' .. '\\uA4FD' | '\\uA500' .. '\\uA60C' | '\\uA610' .. '\\uA61F' | '\\uA62A' .. '\\uA62B' | '\\uA640' .. '\\uA66E' | '\\uA67F' .. '\\uA69D' | '\\uA6A0' .. '\\uA6EF' | '\\uA717' .. '\\uA71F' | '\\uA722' .. '\\uA788' | '\\uA78B' .. '\\uA7AD' | '\\uA7B0' .. '\\uA7B7' | '\\uA7F7' .. '\\uA801' | '\\uA803' .. '\\uA805' | '\\uA807' .. '\\uA80A' | '\\uA80C' .. '\\uA822' | '\\uA840' .. '\\uA873' | '\\uA882' .. '\\uA8B3' | '\\uA8F2' .. '\\uA8F7' | '\\uA8FB' | '\\uA8FD' | '\\uA90A' .. '\\uA925' | '\\uA930' .. '\\uA946' | '\\uA960' .. '\\uA97C' | '\\uA984' .. '\\uA9B2' | '\\uA9CF' | '\\uA9E0' .. '\\uA9E4' | '\\uA9E6' .. '\\uA9EF' | '\\uA9FA' .. '\\uA9FE' | '\\uAA00' .. '\\uAA28' | '\\uAA40' .. '\\uAA42' | '\\uAA44' .. '\\uAA4B' | '\\uAA60' .. '\\uAA76' | '\\uAA7A' | '\\uAA7E' .. '\\uAAAF' | '\\uAAB1' | '\\uAAB5' .. '\\uAAB6' | '\\uAAB9' .. '\\uAABD' | '\\uAAC0' | '\\uAAC2' | '\\uAADB' .. '\\uAADD' | '\\uAAE0' .. '\\uAAEA' | '\\uAAF2' .. '\\uAAF4' | '\\uAB01' .. '\\uAB06' | '\\uAB09' .. '\\uAB0E' | '\\uAB11' .. '\\uAB16' | '\\uAB20' .. '\\uAB26' | '\\uAB28' .. '\\uAB2E' | '\\uAB30' .. '\\uAB5A' | '\\uAB5C' .. '\\uAB65' | '\\uAB70' .. '\\uABE2' | '\\uAC00' .. '\\uD7A3' | '\\uD7B0' .. '\\uD7C6' | '\\uD7CB' .. '\\uD7FB' | '\\uF900' .. '\\uFA6D' | '\\uFA70' .. '\\uFAD9' | '\\uFB00' .. '\\uFB06' | '\\uFB13' .. '\\uFB17' | '\\uFB1D' | '\\uFB1F' .. '\\uFB28' | '\\uFB2A' .. '\\uFB36' | '\\uFB38' .. '\\uFB3C' | '\\uFB3E' | '\\uFB40' .. '\\uFB41' | '\\uFB43' .. '\\uFB44' | '\\uFB46' .. '\\uFBB1' | '\\uFBD3' .. '\\uFD3D' | '\\uFD50' .. '\\uFD8F' | '\\uFD92' .. '\\uFDC7' | '\\uFDF0' .. '\\uFDFB' | '\\uFE70' .. '\\uFE74' | '\\uFE76' .. '\\uFEFC' | '\\uFF21' .. '\\uFF3A' | '\\uFF41' .. '\\uFF5A' | '\\uFF66' .. '\\uFFBE' | '\\uFFC2' .. '\\uFFC7' | '\\uFFCA' .. '\\uFFCF' | '\\uFFD2' .. '\\uFFD7' | '\\uFFDA' .. '\\uFFDC' ) )
            // InternalSemver.g:4814:41: ( 'A' .. 'Z' | 'a' .. 'z' | '\\u00AA' | '\\u00B5' | '\\u00BA' | '\\u00C0' .. '\\u00D6' | '\\u00D8' .. '\\u00F6' | '\\u00F8' .. '\\u02C1' | '\\u02C6' .. '\\u02D1' | '\\u02E0' .. '\\u02E4' | '\\u02EC' | '\\u02EE' | '\\u0370' .. '\\u0374' | '\\u0376' .. '\\u0377' | '\\u037A' .. '\\u037D' | '\\u037F' | '\\u0386' | '\\u0388' .. '\\u038A' | '\\u038C' | '\\u038E' .. '\\u03A1' | '\\u03A3' .. '\\u03F5' | '\\u03F7' .. '\\u0481' | '\\u048A' .. '\\u052F' | '\\u0531' .. '\\u0556' | '\\u0559' | '\\u0561' .. '\\u0587' | '\\u05D0' .. '\\u05EA' | '\\u05F0' .. '\\u05F2' | '\\u0620' .. '\\u064A' | '\\u066E' .. '\\u066F' | '\\u0671' .. '\\u06D3' | '\\u06D5' | '\\u06E5' .. '\\u06E6' | '\\u06EE' .. '\\u06EF' | '\\u06FA' .. '\\u06FC' | '\\u06FF' | '\\u0710' | '\\u0712' .. '\\u072F' | '\\u074D' .. '\\u07A5' | '\\u07B1' | '\\u07CA' .. '\\u07EA' | '\\u07F4' .. '\\u07F5' | '\\u07FA' | '\\u0800' .. '\\u0815' | '\\u081A' | '\\u0824' | '\\u0828' | '\\u0840' .. '\\u0858' | '\\u08A0' .. '\\u08B4' | '\\u0904' .. '\\u0939' | '\\u093D' | '\\u0950' | '\\u0958' .. '\\u0961' | '\\u0971' .. '\\u0980' | '\\u0985' .. '\\u098C' | '\\u098F' .. '\\u0990' | '\\u0993' .. '\\u09A8' | '\\u09AA' .. '\\u09B0' | '\\u09B2' | '\\u09B6' .. '\\u09B9' | '\\u09BD' | '\\u09CE' | '\\u09DC' .. '\\u09DD' | '\\u09DF' .. '\\u09E1' | '\\u09F0' .. '\\u09F1' | '\\u0A05' .. '\\u0A0A' | '\\u0A0F' .. '\\u0A10' | '\\u0A13' .. '\\u0A28' | '\\u0A2A' .. '\\u0A30' | '\\u0A32' .. '\\u0A33' | '\\u0A35' .. '\\u0A36' | '\\u0A38' .. '\\u0A39' | '\\u0A59' .. '\\u0A5C' | '\\u0A5E' | '\\u0A72' .. '\\u0A74' | '\\u0A85' .. '\\u0A8D' | '\\u0A8F' .. '\\u0A91' | '\\u0A93' .. '\\u0AA8' | '\\u0AAA' .. '\\u0AB0' | '\\u0AB2' .. '\\u0AB3' | '\\u0AB5' .. '\\u0AB9' | '\\u0ABD' | '\\u0AD0' | '\\u0AE0' .. '\\u0AE1' | '\\u0AF9' | '\\u0B05' .. '\\u0B0C' | '\\u0B0F' .. '\\u0B10' | '\\u0B13' .. '\\u0B28' | '\\u0B2A' .. '\\u0B30' | '\\u0B32' .. '\\u0B33' | '\\u0B35' .. '\\u0B39' | '\\u0B3D' | '\\u0B5C' .. '\\u0B5D' | '\\u0B5F' .. '\\u0B61' | '\\u0B71' | '\\u0B83' | '\\u0B85' .. '\\u0B8A' | '\\u0B8E' .. '\\u0B90' | '\\u0B92' .. '\\u0B95' | '\\u0B99' .. '\\u0B9A' | '\\u0B9C' | '\\u0B9E' .. '\\u0B9F' | '\\u0BA3' .. '\\u0BA4' | '\\u0BA8' .. '\\u0BAA' | '\\u0BAE' .. '\\u0BB9' | '\\u0BD0' | '\\u0C05' .. '\\u0C0C' | '\\u0C0E' .. '\\u0C10' | '\\u0C12' .. '\\u0C28' | '\\u0C2A' .. '\\u0C39' | '\\u0C3D' | '\\u0C58' .. '\\u0C5A' | '\\u0C60' .. '\\u0C61' | '\\u0C85' .. '\\u0C8C' | '\\u0C8E' .. '\\u0C90' | '\\u0C92' .. '\\u0CA8' | '\\u0CAA' .. '\\u0CB3' | '\\u0CB5' .. '\\u0CB9' | '\\u0CBD' | '\\u0CDE' | '\\u0CE0' .. '\\u0CE1' | '\\u0CF1' .. '\\u0CF2' | '\\u0D05' .. '\\u0D0C' | '\\u0D0E' .. '\\u0D10' | '\\u0D12' .. '\\u0D3A' | '\\u0D3D' | '\\u0D4E' | '\\u0D5F' .. '\\u0D61' | '\\u0D7A' .. '\\u0D7F' | '\\u0D85' .. '\\u0D96' | '\\u0D9A' .. '\\u0DB1' | '\\u0DB3' .. '\\u0DBB' | '\\u0DBD' | '\\u0DC0' .. '\\u0DC6' | '\\u0E01' .. '\\u0E30' | '\\u0E32' .. '\\u0E33' | '\\u0E40' .. '\\u0E46' | '\\u0E81' .. '\\u0E82' | '\\u0E84' | '\\u0E87' .. '\\u0E88' | '\\u0E8A' | '\\u0E8D' | '\\u0E94' .. '\\u0E97' | '\\u0E99' .. '\\u0E9F' | '\\u0EA1' .. '\\u0EA3' | '\\u0EA5' | '\\u0EA7' | '\\u0EAA' .. '\\u0EAB' | '\\u0EAD' .. '\\u0EB0' | '\\u0EB2' .. '\\u0EB3' | '\\u0EBD' | '\\u0EC0' .. '\\u0EC4' | '\\u0EC6' | '\\u0EDC' .. '\\u0EDF' | '\\u0F00' | '\\u0F40' .. '\\u0F47' | '\\u0F49' .. '\\u0F6C' | '\\u0F88' .. '\\u0F8C' | '\\u1000' .. '\\u102A' | '\\u103F' | '\\u1050' .. '\\u1055' | '\\u105A' .. '\\u105D' | '\\u1061' | '\\u1065' .. '\\u1066' | '\\u106E' .. '\\u1070' | '\\u1075' .. '\\u1081' | '\\u108E' | '\\u10A0' .. '\\u10C5' | '\\u10C7' | '\\u10CD' | '\\u10D0' .. '\\u10FA' | '\\u10FC' .. '\\u1248' | '\\u124A' .. '\\u124D' | '\\u1250' .. '\\u1256' | '\\u1258' | '\\u125A' .. '\\u125D' | '\\u1260' .. '\\u1288' | '\\u128A' .. '\\u128D' | '\\u1290' .. '\\u12B0' | '\\u12B2' .. '\\u12B5' | '\\u12B8' .. '\\u12BE' | '\\u12C0' | '\\u12C2' .. '\\u12C5' | '\\u12C8' .. '\\u12D6' | '\\u12D8' .. '\\u1310' | '\\u1312' .. '\\u1315' | '\\u1318' .. '\\u135A' | '\\u1380' .. '\\u138F' | '\\u13A0' .. '\\u13F5' | '\\u13F8' .. '\\u13FD' | '\\u1401' .. '\\u166C' | '\\u166F' .. '\\u167F' | '\\u1681' .. '\\u169A' | '\\u16A0' .. '\\u16EA' | '\\u16EE' .. '\\u16F8' | '\\u1700' .. '\\u170C' | '\\u170E' .. '\\u1711' | '\\u1720' .. '\\u1731' | '\\u1740' .. '\\u1751' | '\\u1760' .. '\\u176C' | '\\u176E' .. '\\u1770' | '\\u1780' .. '\\u17B3' | '\\u17D7' | '\\u17DC' | '\\u1820' .. '\\u1877' | '\\u1880' .. '\\u18A8' | '\\u18AA' | '\\u18B0' .. '\\u18F5' | '\\u1900' .. '\\u191E' | '\\u1950' .. '\\u196D' | '\\u1970' .. '\\u1974' | '\\u1980' .. '\\u19AB' | '\\u19B0' .. '\\u19C9' | '\\u1A00' .. '\\u1A16' | '\\u1A20' .. '\\u1A54' | '\\u1AA7' | '\\u1B05' .. '\\u1B33' | '\\u1B45' .. '\\u1B4B' | '\\u1B83' .. '\\u1BA0' | '\\u1BAE' .. '\\u1BAF' | '\\u1BBA' .. '\\u1BE5' | '\\u1C00' .. '\\u1C23' | '\\u1C4D' .. '\\u1C4F' | '\\u1C5A' .. '\\u1C7D' | '\\u1CE9' .. '\\u1CEC' | '\\u1CEE' .. '\\u1CF1' | '\\u1CF5' .. '\\u1CF6' | '\\u1D00' .. '\\u1DBF' | '\\u1E00' .. '\\u1F15' | '\\u1F18' .. '\\u1F1D' | '\\u1F20' .. '\\u1F45' | '\\u1F48' .. '\\u1F4D' | '\\u1F50' .. '\\u1F57' | '\\u1F59' | '\\u1F5B' | '\\u1F5D' | '\\u1F5F' .. '\\u1F7D' | '\\u1F80' .. '\\u1FB4' | '\\u1FB6' .. '\\u1FBC' | '\\u1FBE' | '\\u1FC2' .. '\\u1FC4' | '\\u1FC6' .. '\\u1FCC' | '\\u1FD0' .. '\\u1FD3' | '\\u1FD6' .. '\\u1FDB' | '\\u1FE0' .. '\\u1FEC' | '\\u1FF2' .. '\\u1FF4' | '\\u1FF6' .. '\\u1FFC' | '\\u2071' | '\\u207F' | '\\u2090' .. '\\u209C' | '\\u2102' | '\\u2107' | '\\u210A' .. '\\u2113' | '\\u2115' | '\\u2119' .. '\\u211D' | '\\u2124' | '\\u2126' | '\\u2128' | '\\u212A' .. '\\u212D' | '\\u212F' .. '\\u2139' | '\\u213C' .. '\\u213F' | '\\u2145' .. '\\u2149' | '\\u214E' | '\\u2160' .. '\\u2188' | '\\u2C00' .. '\\u2C2E' | '\\u2C30' .. '\\u2C5E' | '\\u2C60' .. '\\u2CE4' | '\\u2CEB' .. '\\u2CEE' | '\\u2CF2' .. '\\u2CF3' | '\\u2D00' .. '\\u2D25' | '\\u2D27' | '\\u2D2D' | '\\u2D30' .. '\\u2D67' | '\\u2D6F' | '\\u2D80' .. '\\u2D96' | '\\u2DA0' .. '\\u2DA6' | '\\u2DA8' .. '\\u2DAE' | '\\u2DB0' .. '\\u2DB6' | '\\u2DB8' .. '\\u2DBE' | '\\u2DC0' .. '\\u2DC6' | '\\u2DC8' .. '\\u2DCE' | '\\u2DD0' .. '\\u2DD6' | '\\u2DD8' .. '\\u2DDE' | '\\u2E2F' | '\\u3005' .. '\\u3007' | '\\u3021' .. '\\u3029' | '\\u3031' .. '\\u3035' | '\\u3038' .. '\\u303C' | '\\u3041' .. '\\u3096' | '\\u309D' .. '\\u309F' | '\\u30A1' .. '\\u30FA' | '\\u30FC' .. '\\u30FF' | '\\u3105' .. '\\u312D' | '\\u3131' .. '\\u318E' | '\\u31A0' .. '\\u31BA' | '\\u31F0' .. '\\u31FF' | '\\u3400' .. '\\u4DB5' | '\\u4E00' .. '\\u9FD5' | '\\uA000' .. '\\uA48C' | '\\uA4D0' .. '\\uA4FD' | '\\uA500' .. '\\uA60C' | '\\uA610' .. '\\uA61F' | '\\uA62A' .. '\\uA62B' | '\\uA640' .. '\\uA66E' | '\\uA67F' .. '\\uA69D' | '\\uA6A0' .. '\\uA6EF' | '\\uA717' .. '\\uA71F' | '\\uA722' .. '\\uA788' | '\\uA78B' .. '\\uA7AD' | '\\uA7B0' .. '\\uA7B7' | '\\uA7F7' .. '\\uA801' | '\\uA803' .. '\\uA805' | '\\uA807' .. '\\uA80A' | '\\uA80C' .. '\\uA822' | '\\uA840' .. '\\uA873' | '\\uA882' .. '\\uA8B3' | '\\uA8F2' .. '\\uA8F7' | '\\uA8FB' | '\\uA8FD' | '\\uA90A' .. '\\uA925' | '\\uA930' .. '\\uA946' | '\\uA960' .. '\\uA97C' | '\\uA984' .. '\\uA9B2' | '\\uA9CF' | '\\uA9E0' .. '\\uA9E4' | '\\uA9E6' .. '\\uA9EF' | '\\uA9FA' .. '\\uA9FE' | '\\uAA00' .. '\\uAA28' | '\\uAA40' .. '\\uAA42' | '\\uAA44' .. '\\uAA4B' | '\\uAA60' .. '\\uAA76' | '\\uAA7A' | '\\uAA7E' .. '\\uAAAF' | '\\uAAB1' | '\\uAAB5' .. '\\uAAB6' | '\\uAAB9' .. '\\uAABD' | '\\uAAC0' | '\\uAAC2' | '\\uAADB' .. '\\uAADD' | '\\uAAE0' .. '\\uAAEA' | '\\uAAF2' .. '\\uAAF4' | '\\uAB01' .. '\\uAB06' | '\\uAB09' .. '\\uAB0E' | '\\uAB11' .. '\\uAB16' | '\\uAB20' .. '\\uAB26' | '\\uAB28' .. '\\uAB2E' | '\\uAB30' .. '\\uAB5A' | '\\uAB5C' .. '\\uAB65' | '\\uAB70' .. '\\uABE2' | '\\uAC00' .. '\\uD7A3' | '\\uD7B0' .. '\\uD7C6' | '\\uD7CB' .. '\\uD7FB' | '\\uF900' .. '\\uFA6D' | '\\uFA70' .. '\\uFAD9' | '\\uFB00' .. '\\uFB06' | '\\uFB13' .. '\\uFB17' | '\\uFB1D' | '\\uFB1F' .. '\\uFB28' | '\\uFB2A' .. '\\uFB36' | '\\uFB38' .. '\\uFB3C' | '\\uFB3E' | '\\uFB40' .. '\\uFB41' | '\\uFB43' .. '\\uFB44' | '\\uFB46' .. '\\uFBB1' | '\\uFBD3' .. '\\uFD3D' | '\\uFD50' .. '\\uFD8F' | '\\uFD92' .. '\\uFDC7' | '\\uFDF0' .. '\\uFDFB' | '\\uFE70' .. '\\uFE74' | '\\uFE76' .. '\\uFEFC' | '\\uFF21' .. '\\uFF3A' | '\\uFF41' .. '\\uFF5A' | '\\uFF66' .. '\\uFFBE' | '\\uFFC2' .. '\\uFFC7' | '\\uFFCA' .. '\\uFFCF' | '\\uFFD2' .. '\\uFFD7' | '\\uFFDA' .. '\\uFFDC' )
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
            // InternalSemver.g:4816:48: ( ( ' ' | '\\u00A0' | '\\u1680' | '\\u2000' .. '\\u200A' | '\\u202F' | '\\u205F' | '\\u3000' ) )
            // InternalSemver.g:4816:50: ( ' ' | '\\u00A0' | '\\u1680' | '\\u2000' .. '\\u200A' | '\\u202F' | '\\u205F' | '\\u3000' )
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
            // InternalSemver.g:4818:25: ( . )
            // InternalSemver.g:4818:27: .
            {
            matchAny(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalSemver.g:1:8: ( T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | RULE_LETTER_S | RULE_LETTER_M | RULE_LETTER_R | RULE_LETTER_F | RULE_LETTER_I | RULE_LETTER_L | RULE_LETTER_E | RULE_LETTER_V | RULE_LETTER_X | RULE_LETTER_OTHER | RULE_ASTERIX | RULE_DIGITS | RULE_WS | RULE_EOL )
        int alt9=30;
        alt9 = dfa9.predict(input);
        switch (alt9) {
            case 1 :
                // InternalSemver.g:1:10: T__35
                {
                mT__35(); 

                }
                break;
            case 2 :
                // InternalSemver.g:1:16: T__36
                {
                mT__36(); 

                }
                break;
            case 3 :
                // InternalSemver.g:1:22: T__37
                {
                mT__37(); 

                }
                break;
            case 4 :
                // InternalSemver.g:1:28: T__38
                {
                mT__38(); 

                }
                break;
            case 5 :
                // InternalSemver.g:1:34: T__39
                {
                mT__39(); 

                }
                break;
            case 6 :
                // InternalSemver.g:1:40: T__40
                {
                mT__40(); 

                }
                break;
            case 7 :
                // InternalSemver.g:1:46: T__41
                {
                mT__41(); 

                }
                break;
            case 8 :
                // InternalSemver.g:1:52: T__42
                {
                mT__42(); 

                }
                break;
            case 9 :
                // InternalSemver.g:1:58: T__43
                {
                mT__43(); 

                }
                break;
            case 10 :
                // InternalSemver.g:1:64: T__44
                {
                mT__44(); 

                }
                break;
            case 11 :
                // InternalSemver.g:1:70: T__45
                {
                mT__45(); 

                }
                break;
            case 12 :
                // InternalSemver.g:1:76: T__46
                {
                mT__46(); 

                }
                break;
            case 13 :
                // InternalSemver.g:1:82: T__47
                {
                mT__47(); 

                }
                break;
            case 14 :
                // InternalSemver.g:1:88: T__48
                {
                mT__48(); 

                }
                break;
            case 15 :
                // InternalSemver.g:1:94: T__49
                {
                mT__49(); 

                }
                break;
            case 16 :
                // InternalSemver.g:1:100: T__50
                {
                mT__50(); 

                }
                break;
            case 17 :
                // InternalSemver.g:1:106: RULE_LETTER_S
                {
                mRULE_LETTER_S(); 

                }
                break;
            case 18 :
                // InternalSemver.g:1:120: RULE_LETTER_M
                {
                mRULE_LETTER_M(); 

                }
                break;
            case 19 :
                // InternalSemver.g:1:134: RULE_LETTER_R
                {
                mRULE_LETTER_R(); 

                }
                break;
            case 20 :
                // InternalSemver.g:1:148: RULE_LETTER_F
                {
                mRULE_LETTER_F(); 

                }
                break;
            case 21 :
                // InternalSemver.g:1:162: RULE_LETTER_I
                {
                mRULE_LETTER_I(); 

                }
                break;
            case 22 :
                // InternalSemver.g:1:176: RULE_LETTER_L
                {
                mRULE_LETTER_L(); 

                }
                break;
            case 23 :
                // InternalSemver.g:1:190: RULE_LETTER_E
                {
                mRULE_LETTER_E(); 

                }
                break;
            case 24 :
                // InternalSemver.g:1:204: RULE_LETTER_V
                {
                mRULE_LETTER_V(); 

                }
                break;
            case 25 :
                // InternalSemver.g:1:218: RULE_LETTER_X
                {
                mRULE_LETTER_X(); 

                }
                break;
            case 26 :
                // InternalSemver.g:1:232: RULE_LETTER_OTHER
                {
                mRULE_LETTER_OTHER(); 

                }
                break;
            case 27 :
                // InternalSemver.g:1:250: RULE_ASTERIX
                {
                mRULE_ASTERIX(); 

                }
                break;
            case 28 :
                // InternalSemver.g:1:263: RULE_DIGITS
                {
                mRULE_DIGITS(); 

                }
                break;
            case 29 :
                // InternalSemver.g:1:275: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 30 :
                // InternalSemver.g:1:283: RULE_EOL
                {
                mRULE_EOL(); 

                }
                break;

        }

    }


    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA9_eotS =
        "\11\uffff\1\36\2\uffff\1\40\24\uffff";
    static final String DFA9_eofS =
        "\41\uffff";
    static final String DFA9_minS =
        "\1\11\10\uffff\1\75\2\uffff\1\75\24\uffff";
    static final String DFA9_maxS =
        "\1\ufeff\10\uffff\1\75\2\uffff\1\75\24\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\12\1\13\1\uffff\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\14\1\11\1\16\1\15";
    static final String DFA9_specialS =
        "\41\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\33\1\34\2\33\1\34\22\uffff\1\33\2\uffff\1\15\6\uffff\1\31\1\6\1\uffff\1\4\1\2\1\1\12\32\1\7\1\uffff\1\11\1\10\1\14\1\uffff\1\3\4\30\1\25\1\22\2\30\1\23\2\30\1\24\1\20\4\30\1\21\1\17\2\30\1\26\1\30\1\27\2\30\3\uffff\1\13\1\5\1\uffff\4\30\1\25\1\22\2\30\1\23\2\30\1\24\1\20\4\30\1\21\1\17\2\30\1\26\1\30\1\27\2\30\1\uffff\1\16\1\uffff\1\12\41\uffff\1\33\u15df\uffff\1\33\u097f\uffff\13\33\35\uffff\2\34\5\uffff\1\33\57\uffff\1\33\u0fa0\uffff\1\33\ucefe\uffff\1\33",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\35",
            "",
            "",
            "\1\37",
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

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | RULE_LETTER_S | RULE_LETTER_M | RULE_LETTER_R | RULE_LETTER_F | RULE_LETTER_I | RULE_LETTER_L | RULE_LETTER_E | RULE_LETTER_V | RULE_LETTER_X | RULE_LETTER_OTHER | RULE_ASTERIX | RULE_DIGITS | RULE_WS | RULE_EOL );";
        }
    }
 

}