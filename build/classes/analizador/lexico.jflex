package analizador;

import java_cup.runtime.*;
import java.util.ArrayList;

%%
%{
    //cod
    //metodo para error
    //public static ArrayList<ErrorE> errorLexico = new ArrayList<ErrorE>();
%}

%public
%class Lexico
%cup
%char
%column
%full
%ignorecase
%line
%unicode


//Palabras---------------------------------------------------------------------------------------------------------------------
iff = "if"
elsee = "else"
switchh = "switch"
casee = "case"
breakk = "break"
defaultt = "default"
whilee = "while"
print = "print"
doo = "do"
forr = "for"
truee = "true"
falsee = "false"
inn = "in"
continuee = "continue"
returnn = "return"
functionn = "function"
c = "c"
listt = "list"
typeoff = "typeof"
lengthh = "length"
nCol = "ncol"
arrayy = "array"
nrow = "nrow"
stringlength = "stringlength"
removee = "remove"
tolowercase = "tolowercase"
touppercase = "touppercase"
trunkk = "trunk"
roundd = "round"
meann = "mean"
mediann = "median"
modee = "mode"
nulo = "null"
matrix = "matrix"
pie = "pie"
barplot = "barplot"
plot = "plot"
hist = "hist"



//Signos-----------------------------------------------------------------------------------------------------------------
allave = "{"
cllave = "}"
acorchete = "["
ccorchete = "]"
aparentesis = "("
cparentesis = ")"
igual = "="
suma = "+"
resta = "-"
por = "*"
division = "/"
potencia = "^"
modulo = "%%"
mayorq = ">"
menorq = "<"
mayorigualq = ">="
menorigualq = "<="
igualigual = "=="
diferente = "!="
interrogacion = "?"
and = "&"
or = "|"
not = "!"
dospuntos = ":"
puntoycoma = ";"
coma = ","
flecha = "=>"



//ER----------------------------------------------------------------------------------------------------------------------
ERnumero        = [0-9]+
ERdecimal       = [0-9]+"."[0-9]+
id             = ([a-zA-ZñÑ]+([a-zA-ZñÑ"_""."]*|[0-9]*)*)|("."[a-zA-ZñÑ]+([a-zA-ZñÑ"_""."]*|[0-9]*)*)
//id2             = "."[a-zA-ZñÑ]+([a-zA-ZñÑ"_""."]*|[0-9]*)*
//ERCadena        = ("\"" ~"\"")|("'" ~"'")

espacio   = [\ \r\t\f\t]
enter   = [\ \n]


%state comentariosimple
%state comentariomultiple
%%

<YYINITIAL> "#*"                       {yybegin(comentariomultiple);}
<comentariomultiple>  .                {/* omitilo weeeeeee */}
<comentariomultiple> "*#"              {yybegin(YYINITIAL);
                                        System.out.println("Comentario multiple: <<"+yytext()+">> Linea: "+yyline+" ,Columna: "+yycolumn);}
<comentariomultiple> [ \t\r\n\f]       {}

<YYINITIAL> "#"                        {yybegin(comentariosimple);}
<comentariosimple>  .                   {/* omitilo weeeeeee */}
<comentariosimple> [^"\n"]              {}
<comentariosimple> "\n"                 {yybegin(YYINITIAL);
                                        System.out.println("Comentario simple: <<"+yytext()+">> Linea: "+yyline+" ,Columna: "+yycolumn);}


//palabras---------------------------------------------------------------------------------------------------------------------------------------
<YYINITIAL> {iff}              {  System.out.println("Reconocido: <<"+yytext()+">>, iff");
                                return new Symbol(sym.iff, yyline, yycolumn, yytext()); }
<YYINITIAL> {elsee}              {  System.out.println("Reconocido: <<"+yytext()+">>, elsee");
                                return new Symbol(sym.elsee, yyline, yycolumn, yytext()); } 
<YYINITIAL> {switchh}              {  System.out.println("Reconocido: <<"+yytext()+">>, switchh");
                                return new Symbol(sym.switchh, yyline, yycolumn, yytext()); } 
<YYINITIAL> {casee}              {  System.out.println("Reconocido: <<"+yytext()+">>, casee");
                                return new Symbol(sym.casee, yyline, yycolumn, yytext()); } 
<YYINITIAL> {breakk}              {  System.out.println("Reconocido: <<"+yytext()+">>, breakk");
                                return new Symbol(sym.breakk, yyline, yycolumn, yytext()); } 
<YYINITIAL> {defaultt}              {  System.out.println("Reconocido: <<"+yytext()+">>, defaultt");
                                return new Symbol(sym.defaultt, yyline, yycolumn, yytext()); } 
<YYINITIAL> {whilee}              {  System.out.println("Reconocido: <<"+yytext()+">>, whilee");
                                return new Symbol(sym.whilee, yyline, yycolumn, yytext()); } 
<YYINITIAL> {print}              {  System.out.println("Reconocido: <<"+yytext()+">>, print");
                                return new Symbol(sym.print, yyline, yycolumn, yytext()); } 
<YYINITIAL> {doo}              {  System.out.println("Reconocido: <<"+yytext()+">>, doo");
                                return new Symbol(sym.doo, yyline, yycolumn, yytext()); }
<YYINITIAL> {forr}              {  System.out.println("Reconocido: <<"+yytext()+">>, forr");
                                return new Symbol(sym.forr, yyline, yycolumn, yytext()); }
<YYINITIAL> {truee}              {  System.out.println("Reconocido: <<"+yytext()+">>, truee");
                                return new Symbol(sym.truee, yyline, yycolumn, yytext()); }
<YYINITIAL> {falsee}              {  System.out.println("Reconocido: <<"+yytext()+">>, falsee");
                                return new Symbol(sym.falsee, yyline, yycolumn, yytext()); }
<YYINITIAL> {inn}              {  System.out.println("Reconocido: <<"+yytext()+">>, inn");
                                return new Symbol(sym.inn, yyline, yycolumn, yytext()); }
<YYINITIAL> {continuee}              {  System.out.println("Reconocido: <<"+yytext()+">>, continuee");
                                return new Symbol(sym.continuee, yyline, yycolumn, yytext()); }
<YYINITIAL> {returnn}              {  System.out.println("Reconocido: <<"+yytext()+">>, returnn");
                                return new Symbol(sym.returnn, yyline, yycolumn, yytext()); }
<YYINITIAL> {functionn}              {  System.out.println("Reconocido: <<"+yytext()+">>, functionn");
                                return new Symbol(sym.functionn, yyline, yycolumn, yytext()); }
<YYINITIAL> {c}              {  System.out.println("Reconocido: <<"+yytext()+">>, c");
                                return new Symbol(sym.c, yyline, yycolumn, yytext()); }
<YYINITIAL> {listt}              {  System.out.println("Reconocido: <<"+yytext()+">>, listt");
                                return new Symbol(sym.listt, yyline, yycolumn, yytext()); }
<YYINITIAL> {typeoff}              {  System.out.println("Reconocido: <<"+yytext()+">>, typeoff");
                                return new Symbol(sym.typeoff, yyline, yycolumn, yytext()); }
<YYINITIAL> {lengthh}              {  System.out.println("Reconocido: <<"+yytext()+">>, lengthh");
                                return new Symbol(sym.lengthh, yyline, yycolumn, yytext()); }
<YYINITIAL> {nCol}              {  System.out.println("Reconocido: <<"+yytext()+">>, nCol");
                                return new Symbol(sym.nCol, yyline, yycolumn, yytext()); }
<YYINITIAL> {arrayy}              {  System.out.println("Reconocido: <<"+yytext()+">>, arrayy");
                                return new Symbol(sym.arrayy, yyline, yycolumn, yytext()); }
<YYINITIAL> {nrow}              {  System.out.println("Reconocido: <<"+yytext()+">>, nrow");
                                return new Symbol(sym.nrow, yyline, yycolumn, yytext()); }
<YYINITIAL> {removee}      {  System.out.println("Reconocido: <<"+yytext()+">>, removee");
                                return new Symbol(sym.removee, yyline, yycolumn, yytext()); }
<YYINITIAL> {tolowercase}      {  System.out.println("Reconocido: <<"+yytext()+">>, tolowercase");
                                return new Symbol(sym.tolowercase, yyline, yycolumn, yytext()); }
<YYINITIAL> {stringlength}      {  System.out.println("Reconocido: <<"+yytext()+">>, stringlength");
                                return new Symbol(sym.stringlength, yyline, yycolumn, yytext()); }
<YYINITIAL> {touppercase}      {  System.out.println("Reconocido: <<"+yytext()+">>, touppercase");
                                return new Symbol(sym.touppercase, yyline, yycolumn, yytext()); }
<YYINITIAL> {trunkk}             {  System.out.println("Reconocido: <<"+yytext()+">>, trunkk");
                                return new Symbol(sym.trunkk, yyline, yycolumn, yytext()); }
<YYINITIAL> {roundd}             {  System.out.println("Reconocido: <<"+yytext()+">>, roundd");
                                return new Symbol(sym.roundd, yyline, yycolumn, yytext()); }
<YYINITIAL> {meann}              {  System.out.println("Reconocido: <<"+yytext()+">>, meann");
                                return new Symbol(sym.meann, yyline, yycolumn, yytext()); }
<YYINITIAL> {mediann}            {  System.out.println("Reconocido: <<"+yytext()+">>, mediann");
                                return new Symbol(sym.mediann, yyline, yycolumn, yytext()); }
<YYINITIAL> {modee}              {  System.out.println("Reconocido: <<"+yytext()+">>, modee");
                                return new Symbol(sym.modee, yyline, yycolumn, yytext()); }
<YYINITIAL> {nulo}              {  System.out.println("Reconocido: <<"+yytext()+">>, nulo");
                                return new Symbol(sym.nulo, yyline, yycolumn, yytext()); }
<YYINITIAL> {matrix}            {  System.out.println("Reconocido: <<"+yytext()+">>, matrix");
                                return new Symbol(sym.matrix, yyline, yycolumn, yytext()); }
<YYINITIAL> {pie}              {  System.out.println("Reconocido: <<"+yytext()+">>, pie");
                                return new Symbol(sym.pie, yyline, yycolumn, yytext()); }
<YYINITIAL> {barplot}              {  System.out.println("Reconocido: <<"+yytext()+">>, barplot");
                                return new Symbol(sym.barplot, yyline, yycolumn, yytext()); }
<YYINITIAL> {plot}              {  System.out.println("Reconocido: <<"+yytext()+">>, plot");
                                return new Symbol(sym.plot, yyline, yycolumn, yytext()); }
<YYINITIAL> {hist}              {  System.out.println("Reconocido: <<"+yytext()+">>, hist");
                                return new Symbol(sym.hist, yyline, yycolumn, yytext()); }


//signos--------------------------------------------------------------------------------------------------------------
<YYINITIAL> {flecha}              {  System.out.println("Reconocido: <<"+yytext()+">>, flecha");
                                return new Symbol(sym.flecha, yyline, yycolumn, yytext()); }
<YYINITIAL> {coma}              {  System.out.println("Reconocido: <<"+yytext()+">>, coma");
                                return new Symbol(sym.coma, yyline, yycolumn, yytext()); }
<YYINITIAL> {puntoycoma}        {  System.out.println("Reconocido: <<"+yytext()+">>, puntoycoma");
                                return new Symbol(sym.puntoycoma, yyline, yycolumn, yytext()); }
<YYINITIAL> {dospuntos}         {  System.out.println("Reconocido: <<"+yytext()+">>, dospuntos");
                                return new Symbol(sym.dospuntos, yyline, yycolumn, yytext()); }
<YYINITIAL> {interrogacion}     {  System.out.println("Reconocido: <<"+yytext()+">>, interrogacion");
                                return new Symbol(sym.interrogacion, yyline, yycolumn, yytext()); }
<YYINITIAL> {allave}            {  System.out.println("Reconocido: <<"+yytext()+">>, allave");
                                return new Symbol(sym.allave, yyline, yycolumn, yytext()); }
<YYINITIAL> {cllave}            {  System.out.println("Reconocido: <<"+yytext()+">>, cllave");
                                return new Symbol(sym.cllave, yyline, yycolumn, yytext()); }
<YYINITIAL> {acorchete}         {  System.out.println("Reconocido: <<"+yytext()+">>, acorchete");
                                return new Symbol(sym.acorchete, yyline, yycolumn, yytext()); }
<YYINITIAL> {ccorchete}         {  System.out.println("Reconocido: <<"+yytext()+">>, ccorchete");
                                return new Symbol(sym.ccorchete, yyline, yycolumn, yytext()); }
<YYINITIAL> {aparentesis}              {  System.out.println("Reconocido: <<"+yytext()+">>, aparentesis");
                                return new Symbol(sym.aparentesis, yyline, yycolumn, yytext()); }
<YYINITIAL> {cparentesis}              {  System.out.println("Reconocido: <<"+yytext()+">>, cparentesis");
                                return new Symbol(sym.cparentesis, yyline, yycolumn, yytext()); }
<YYINITIAL> {suma}              {  System.out.println("Reconocido: <<"+yytext()+">>, suma");
                                return new Symbol(sym.suma, yyline, yycolumn, yytext()); }
<YYINITIAL> {resta}             {  System.out.println("Reconocido: <<"+yytext()+">>, resta");
                                return new Symbol(sym.resta, yyline, yycolumn, yytext()); }
<YYINITIAL> {division}          {  System.out.println("Reconocido: <<"+yytext()+">>, division");
                                return new Symbol(sym.division, yyline, yycolumn, yytext()); }
<YYINITIAL> {por}               {  System.out.println("Reconocido: <<"+yytext()+">>, por");
                                return new Symbol(sym.por, yyline, yycolumn, yytext()); }
<YYINITIAL> {potencia}          {  System.out.println("Reconocido: <<"+yytext()+">>, potencia");
                                return new Symbol(sym.potencia, yyline, yycolumn, yytext()); }
<YYINITIAL> {modulo}          {  System.out.println("Reconocido: <<"+yytext()+">>, modulo");
                                return new Symbol(sym.modulo, yyline, yycolumn, yytext()); }
<YYINITIAL> {igual}             {  System.out.println("Reconocido: <<"+yytext()+">>, igual");
                                return new Symbol(sym.igual, yyline, yycolumn, yytext()); }
<YYINITIAL> {mayorq}            {  System.out.println("Reconocido: <<"+yytext()+">>, mayorq");
                                return new Symbol(sym.mayorq, yyline, yycolumn, yytext()); }
<YYINITIAL> {menorq}            {  System.out.println("Reconocido: <<"+yytext()+">>, menorq");
                                return new Symbol(sym.menorq, yyline, yycolumn, yytext()); }
<YYINITIAL> {mayorigualq}       {  System.out.println("Reconocido: <<"+yytext()+">>, mayorigualq");
                                return new Symbol(sym.mayorigualq, yyline, yycolumn, yytext()); }
<YYINITIAL> {menorigualq}       {  System.out.println("Reconocido: <<"+yytext()+">>, menorigualq");
                                return new Symbol(sym.menorigualq, yyline, yycolumn, yytext()); }
<YYINITIAL> {igualigual}        {  System.out.println("Reconocido: <<"+yytext()+">>, igualigual");
                                return new Symbol(sym.igualigual, yyline, yycolumn, yytext()); }
<YYINITIAL> {diferente}         {  System.out.println("Reconocido: <<"+yytext()+">>, diferente");
                                return new Symbol(sym.diferente, yyline, yycolumn, yytext()); }
<YYINITIAL> {and}               {  System.out.println("Reconocido: <<"+yytext()+">>, and");
                                return new Symbol(sym.and, yyline, yycolumn, yytext()); }
<YYINITIAL> {or}                {  System.out.println("Reconocido: <<"+yytext()+">>, or");
                                return new Symbol(sym.or, yyline, yycolumn, yytext()); }
<YYINITIAL> {not}               {  System.out.println("Reconocido: <<"+yytext()+">>, not");
                                return new Symbol(sym.not, yyline, yycolumn, yytext()); }


//ER------------------------------------------------------------------------------------------------------------------------------
<YYINITIAL> {ERnumero}          {  System.out.println("Reconocido: <<"+yytext()+">>, ERnumero");
                                return new Symbol(sym.ERnumero, yyline, yycolumn, yytext()); }
<YYINITIAL> {ERdecimal}          {  System.out.println("Reconocido: <<"+yytext()+">>, ERdecimal");
                                return new Symbol(sym.ERdecimal, yyline, yycolumn, yytext()); }
<YYINITIAL> {id}              {  System.out.println("Reconocido: <<"+yytext()+">>, id");
                                return new Symbol(sym.id, yyline, yycolumn, yytext()); }
/*<YYINITIAL> {id2}              {  System.out.println("Reconocido: <<"+yytext()+">>, id2");
                                return new Symbol(sym.id2, yyline, yycolumn, yytext()); }*/
//<YYINITIAL> {ERCadena}          {  System.out.println("Reconocido: <<"+yytext()+">>, ERCadena");
//                                return new Symbol(sym.ERCadena, yyline, yycolumn, yytext()); }

  


[ \t\r\n\f]                 {/* ignore white space. */ }
.                           {System.out.println("ERROR LEXICO: <<"+yytext()+">> Linea: "+yyline+" ,Columna: "+yycolumn);
                            /*errorLexico.add(new ErrorE("ERROR LEXICO: <<"+yytext()+">> Linea: "+yyline+" ,Columna: "+yycolumn));*/
                            ;}