package olc2_p1_1s2020_201408486;


import java.io.*;   
import javax.swing.text.Segment;   
   
import org.fife.ui.rsyntaxtextarea.*;   
   
%%   
   
%public   
%class ColorDeTokens   
%extends AbstractJFlexCTokenMaker   
%unicode   
%ignorecase
%type org.fife.ui.rsyntaxtextarea.Token   
   
/**   
 * A simple    
 */   
%{   
   
   /**   
    * Constructor.  This must be here because JFlex does not generate a   
    * no-parameter constructor.   
    */   
   public ColorDeTokens() {   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param tokenType The token's type.   
    * @see #addToken(int, int, int)   
    */   
   private void addHyperlinkToken(int start, int end, int tokenType) {   
      int so = start + offsetShift;   
      addToken(zzBuffer, start,end, tokenType, so, true);   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param tokenType The token's type.   
    */   
   private void addToken(int tokenType) {   
      addToken(zzStartRead, zzMarkedPos-1, tokenType);   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param tokenType The token's type.   
    * @see #addHyperlinkToken(int, int, int)   
    */   
   private void addToken(int start, int end, int tokenType) {   
      int so = start + offsetShift;   
      addToken(zzBuffer, start,end, tokenType, so, false);   
   }   
   
   /**   
    * Adds the token specified to the current linked list of tokens.   
    *   
    * @param array The character array.   
    * @param start The starting offset in the array.   
    * @param end The ending offset in the array.   
    * @param tokenType The token's type.   
    * @param startOffset The offset in the document at which this token   
    *        occurs.   
    * @param hyperlink Whether this token is a hyperlink.   
    */   
   public void addToken(char[] array, int start, int end, int tokenType,   
                  int startOffset, boolean hyperlink) {   
      super.addToken(array, start,end, tokenType, startOffset, hyperlink);   
      zzStartRead = zzMarkedPos;   
   }   
   
   /**   
    * Returns the text to place at the beginning and end of a   
    * line to "comment" it in a this programming language.   
    *   
    * @return The start and end strings to add to a line to "comment"   
    *         it out.   
    */   
   public String[] getLineCommentStartAndEnd() {   
      return new String[] { "//", null };   
   }   
   
   /**   
    * Returns the first token in the linked list of tokens generated   
    * from <code>text</code>.  This method must be implemented by   
    * subclasses so they can correctly implement syntax highlighting.   
    *   
    * @param text The text from which to get tokens.   
    * @param initialTokenType The token type we should start with.   
    * @param startOffset The offset into the document at which   
    *        <code>text</code> starts.   
    * @return The first <code>Token</code> in a linked list representing   
    *         the syntax highlighted text.   
    */   
   public Token getTokenList(Segment text, int initialTokenType, int startOffset) {   
   
      resetTokenList();   
      this.offsetShift = -text.offset + startOffset;   
   
      // Start off in the proper state.   
      int state = Token.NULL;   
      switch (initialTokenType) {   
         case Token.COMMENT_MULTILINE:   
            state = comentarios;   
            start = text.offset;   
            break;   
   
         /* No documentation comments */   
         default:   
            state = Token.NULL;   
      }   
   
      s = text;   
      try {   
         yyreset(zzReader);   
         yybegin(state);   
         return yylex();   
      } catch (IOException ioe) {   
         ioe.printStackTrace();   
         return new TokenImpl();   
      }   
   
   }   
   
   /**   
    * Refills the input buffer.   
    *   
    * @return      <code>true</code> if EOF was reached, otherwise   
    *              <code>false</code>.   
    */   
   private boolean zzRefill() {   
      return zzCurrentPos>=s.offset+s.count;   
   }   
   
   /**   
    * Resets the scanner to read from a new input stream.   
    * Does not close the old reader.   
    *   
    * All internal variables are reset, the old input stream    
    * <b>cannot</b> be reused (internal buffer is discarded and lost).   
    * Lexical state is set to <tt>YY_INITIAL</tt>.   
    *   
    * @param reader   the new input stream    
    */   
   public final void yyreset(Reader reader) {   
      // 's' has been updated.   
      zzBuffer = s.array;   
      /*   
       * We replaced the line below with the two below it because zzRefill   
       * no longer "refills" the buffer (since the way we do it, it's always   
       * "full" the first time through, since it points to the segment's   
       * array).  So, we assign zzEndRead here.   
       */   
      //zzStartRead = zzEndRead = s.offset;   
      zzStartRead = s.offset;   
      zzEndRead = zzStartRead + s.count - 1;   
      zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;   
      zzLexicalState = YYINITIAL;   
      zzReader = reader;   
      zzAtBOL  = true;   
      zzAtEOF  = false;   
   }   
   
%}   

letras                                  =[a-zA-ZñÑ]
num                                     = [0-9]
finalCadenaUna                          = ([^\\'])   
finalCadenaDoble                        = ([^\\\"\n])   
ERIdInicio                              = ({letras}|"_"|".")
ERIdFIn                                 = ({ERIdInicio}|{num}) 
     
ERCadena              = ("\"" ({finalCadenaDoble})* "\"")   //= ("\"" ~"\"")|("'" ~"'")
   
CMultilinea          = "#*" 
CMultilineaFin       = "*#"
CommentUniLinea      = "#"
   
ERnumero             = ({num}+) //([0-9]+|[0-9]+"."[0-9]+)
ERDecimal            = ({num}+"."+{num}+) 
   
dividirtodo               = ([\(\)\{\}\[\]])   
  
   
ERId                = ({ERIdInicio} {ERIdFIn}*) // = [a-zA-ZñÑ"_""."]+([a-zA-ZñÑ"_""."]*|[0-9]*)
   
%state comentarios   
%%   
   
<YYINITIAL> {   
      
    "id"              { addToken(Token.RESERVED_WORD); }
    "if"              { addToken(Token.RESERVED_WORD); }
    "else"            { addToken(Token.RESERVED_WORD); }
    "switch"          { addToken(Token.RESERVED_WORD); }
    "case"            { addToken(Token.RESERVED_WORD); }
    "break"           { addToken(Token.RESERVED_WORD); }
    "default"         { addToken(Token.RESERVED_WORD); }
    "while"           { addToken(Token.RESERVED_WORD); }
    "print"           { addToken(Token.RESERVED_WORD); }
    "do"              { addToken(Token.RESERVED_WORD); }
    "for"             { addToken(Token.RESERVED_WORD); }
    "true"            { addToken(Token.RESERVED_WORD); }
    "false"           { addToken(Token.RESERVED_WORD); }
    "in"              { addToken(Token.RESERVED_WORD); }
    "continue"        { addToken(Token.RESERVED_WORD); }
    "return"          { addToken(Token.RESERVED_WORD); }
    "function"        { addToken(Token.RESERVED_WORD); }
    "c"               { addToken(Token.RESERVED_WORD); }
    "list"            { addToken(Token.RESERVED_WORD); }
    "typeof"          { addToken(Token.RESERVED_WORD); }
    "length"          { addToken(Token.RESERVED_WORD); }
    "ncol"            { addToken(Token.RESERVED_WORD); }
    "array"           { addToken(Token.RESERVED_WORD); }
    "nrow"            { addToken(Token.RESERVED_WORD); }
    "stringlength"    { addToken(Token.RESERVED_WORD); }
    "remove"          { addToken(Token.RESERVED_WORD); }
    "tolowercase"     { addToken(Token.RESERVED_WORD); }
    "touppercase"     { addToken(Token.RESERVED_WORD); }
    "trunk"           { addToken(Token.RESERVED_WORD); }
    "round"           { addToken(Token.RESERVED_WORD); }
    "mean"            { addToken(Token.RESERVED_WORD); }
    "median"          { addToken(Token.RESERVED_WORD); }
    "mode"            { addToken(Token.RESERVED_WORD); }
    "null"            { addToken(Token.RESERVED_WORD); }
    "matrix"          { addToken(Token.RESERVED_WORD); }
    "pie"             { addToken(Token.RESERVED_WORD); }
    "barplot"         { addToken(Token.RESERVED_WORD); }
    "plot"            { addToken(Token.RESERVED_WORD); }
    "hist"            { addToken(Token.RESERVED_WORD); }
    

          
   
   {ERId}            { addToken(Token.IDENTIFIER); }  
   //{ERTexto}         { addToken(Token.IDENTIFIER); }
   
   //cadenas  
   {ERCadena}               { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }   
   
   
   //comentarios de los 2 parsers  
   {CMultilinea}            { start = zzMarkedPos-2; yybegin(comentarios); }   
   {CommentUniLinea}.*      { addToken(Token.COMMENT_EOL); addNullToken(); return firstToken; }   
   
        
   
   //signos 
   ","      { addToken(Token.OPERATOR); }
   ";"      { addToken(Token.OPERATOR); }
   ":"      { addToken(Token.OPERATOR); }
   "?"      { addToken(Token.OPERATOR); }
   "{"      { addToken(Token.OPERATOR); }
   "}"      { addToken(Token.OPERATOR); }
   "["      { addToken(Token.OPERATOR); }
   "]"      { addToken(Token.OPERATOR); }
   "("      { addToken(Token.OPERATOR); }
   ")"      { addToken(Token.OPERATOR); }
   "+"      { addToken(Token.OPERATOR); }
   "-"      { addToken(Token.OPERATOR); }
   "*"      { addToken(Token.OPERATOR); }
   "/"      { addToken(Token.OPERATOR); }
   "^"      { addToken(Token.OPERATOR); }   
   "="      { addToken(Token.OPERATOR); }   
   ">"      { addToken(Token.OPERATOR); }
   "<"      { addToken(Token.OPERATOR); }
   ">="     { addToken(Token.OPERATOR); }
   "<="     { addToken(Token.OPERATOR); }
   "=="     { addToken(Token.OPERATOR); }
   "!="     { addToken(Token.OPERATOR); }
   "&"     { addToken(Token.OPERATOR); }
   "|"     { addToken(Token.OPERATOR); }
   "!"      { addToken(Token.OPERATOR); }


   //nums
   {ERnumero}                 { addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }   
   {ERDecimal}                { addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }
   
   /* Ended with a line not in a string or comment. */   
   \n |   
   <<EOF>>                  { addNullToken(); return firstToken; }   
   
   /* Catch any other (unhandled) characters. */   
   .                     { addToken(Token.IDENTIFIER); }   
   
}   
   
<comentarios> {   
   [^\n*]+            {}   
   {CMultilineaFin}   { yybegin(YYINITIAL); addToken(start,zzStartRead+2-1, Token.COMMENT_MULTILINE); }   
    "*"               {}   
   \n                 { addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken; }   
   <<EOF>>            { addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken; }   
}   

