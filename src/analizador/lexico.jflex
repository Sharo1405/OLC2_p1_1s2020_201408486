package analizador;

import java_cup.runtime.*;
import java.util.ArrayList;

%%
%{
    //cod
    //metodo para error
    public static ArrayList<ErrorE> errorLexico = new ArrayList<ErrorE>();
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
if = "if"
else = "else"
switch = "switch"
case = "case"
break = "break"
default = "default"
while = "while"
print = "print"
do = "do"
true = "true"
false = "false"
in = "in"
continue = "continue"
return = "return"
function = "function"
c = "c"
list = "list"
typeof = "typeof"
length = "length"
nCol = "nCol"
array = "array"
nrow = "nrow"
stringlength = "stringlength"
remove = "remove"
tolowercase = "tolowercase"
toupperCase = "toupperCase"
trunk = "trunk"
round = "round"
mean = "mean"
median = "median"
mode = "mode"
nulo = "null"
matrix = "matrix"
result = "result"
pie = "pie"
barplot = "barplot"
plot = "plot"
hist = "hist"



//Signos-----------------------------------------------------------------------------------------------------------------
allave = "{"
cllave = "}"
aCorchete = "["
cCorchete = "]"
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
punto = "."
flecha = "=>"



//ER----------------------------------------------------------------------------------------------------------------------
ERnumero        = [0-9]+
ERdecimal       = [0-9]+"."[0-9]+
id1             = [a-zA-ZñÑ]+([a-zA-ZñÑ"_""."]*|[0-9]*)*
id2             = "."[a-zA-ZñÑ]+([a-zA-ZñÑ"_""."]*|[0-9]*)*
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
<YYINITIAL> {if}              {  System.out.println("Reconocido: <<"+yytext()+">>, if");
                                return new Symbol(sym.if, yyline, yycolumn, yytext()); }
<YYINITIAL> {else}              {  System.out.println("Reconocido: <<"+yytext()+">>, else");
                                return new Symbol(sym.else, yyline, yycolumn, yytext()); } 
<YYINITIAL> {switch}              {  System.out.println("Reconocido: <<"+yytext()+">>, switch");
                                return new Symbol(sym.switch, yyline, yycolumn, yytext()); } 
<YYINITIAL> {case}              {  System.out.println("Reconocido: <<"+yytext()+">>, case");
                                return new Symbol(sym.case, yyline, yycolumn, yytext()); } 
<YYINITIAL> {break}              {  System.out.println("Reconocido: <<"+yytext()+">>, break");
                                return new Symbol(sym.break, yyline, yycolumn, yytext()); } 
<YYINITIAL> {default}              {  System.out.println("Reconocido: <<"+yytext()+">>, default");
                                return new Symbol(sym.default, yyline, yycolumn, yytext()); } 
<YYINITIAL> {while}              {  System.out.println("Reconocido: <<"+yytext()+">>, while");
                                return new Symbol(sym.while, yyline, yycolumn, yytext()); } 
<YYINITIAL> {print}              {  System.out.println("Reconocido: <<"+yytext()+">>, print");
                                return new Symbol(sym.print, yyline, yycolumn, yytext()); } 
<YYINITIAL> {do}              {  System.out.println("Reconocido: <<"+yytext()+">>, do");
                                return new Symbol(sym.do, yyline, yycolumn, yytext()); }
<YYINITIAL> {true}              {  System.out.println("Reconocido: <<"+yytext()+">>, true");
                                return new Symbol(sym.true, yyline, yycolumn, yytext()); }
<YYINITIAL> {false}              {  System.out.println("Reconocido: <<"+yytext()+">>, false");
                                return new Symbol(sym.false, yyline, yycolumn, yytext()); }
<YYINITIAL> {in}              {  System.out.println("Reconocido: <<"+yytext()+">>, in");
                                return new Symbol(sym.in, yyline, yycolumn, yytext()); }
<YYINITIAL> {continue}              {  System.out.println("Reconocido: <<"+yytext()+">>, continue");
                                return new Symbol(sym.continue, yyline, yycolumn, yytext()); }
<YYINITIAL> {return}              {  System.out.println("Reconocido: <<"+yytext()+">>, return");
                                return new Symbol(sym.return, yyline, yycolumn, yytext()); }
<YYINITIAL> {function}              {  System.out.println("Reconocido: <<"+yytext()+">>, function");
                                return new Symbol(sym.function, yyline, yycolumn, yytext()); }
<YYINITIAL> {c}              {  System.out.println("Reconocido: <<"+yytext()+">>, c");
                                return new Symbol(sym.c, yyline, yycolumn, yytext()); }
<YYINITIAL> {list}              {  System.out.println("Reconocido: <<"+yytext()+">>, list");
                                return new Symbol(sym.list, yyline, yycolumn, yytext()); }
<YYINITIAL> {typeof}              {  System.out.println("Reconocido: <<"+yytext()+">>, typeof");
                                return new Symbol(sym.typeof, yyline, yycolumn, yytext()); }
<YYINITIAL> {length}              {  System.out.println("Reconocido: <<"+yytext()+">>, length");
                                return new Symbol(sym.length, yyline, yycolumn, yytext()); }
<YYINITIAL> {nCol}              {  System.out.println("Reconocido: <<"+yytext()+">>, nCol");
                                return new Symbol(sym.nCol, yyline, yycolumn, yytext()); }
<YYINITIAL> {array}              {  System.out.println("Reconocido: <<"+yytext()+">>, array");
                                return new Symbol(sym.array, yyline, yycolumn, yytext()); }
<YYINITIAL> {nrow}              {  System.out.println("Reconocido: <<"+yytext()+">>, nrow");
                                return new Symbol(sym.nrow, yyline, yycolumn, yytext()); }
<YYINITIAL> {remove}      {  System.out.println("Reconocido: <<"+yytext()+">>, remove");
                                return new Symbol(sym.remove, yyline, yycolumn, yytext()); }
<YYINITIAL> {tolowercase}      {  System.out.println("Reconocido: <<"+yytext()+">>, tolowercase");
                                return new Symbol(sym.tolowercase, yyline, yycolumn, yytext()); }
<YYINITIAL> {stringlength}      {  System.out.println("Reconocido: <<"+yytext()+">>, stringlength");
                                return new Symbol(sym.stringlength, yyline, yycolumn, yytext()); }
<YYINITIAL> {toupperCase}      {  System.out.println("Reconocido: <<"+yytext()+">>, toupperCase");
                                return new Symbol(sym.toupperCase, yyline, yycolumn, yytext()); }
<YYINITIAL> {trunk}             {  System.out.println("Reconocido: <<"+yytext()+">>, trunk");
                                return new Symbol(sym.trunk, yyline, yycolumn, yytext()); }
<YYINITIAL> {round}             {  System.out.println("Reconocido: <<"+yytext()+">>, round");
                                return new Symbol(sym.round, yyline, yycolumn, yytext()); }
<YYINITIAL> {mean}              {  System.out.println("Reconocido: <<"+yytext()+">>, mean");
                                return new Symbol(sym.mean, yyline, yycolumn, yytext()); }
<YYINITIAL> {median}            {  System.out.println("Reconocido: <<"+yytext()+">>, median");
                                return new Symbol(sym.median, yyline, yycolumn, yytext()); }
<YYINITIAL> {mode}              {  System.out.println("Reconocido: <<"+yytext()+">>, mode");
                                return new Symbol(sym.mode, yyline, yycolumn, yytext()); }
<YYINITIAL> {nulo}              {  System.out.println("Reconocido: <<"+yytext()+">>, nulo");
                                return new Symbol(sym.nulo, yyline, yycolumn, yytext()); }
<YYINITIAL> {matrix}            {  System.out.println("Reconocido: <<"+yytext()+">>, matrix");
                                return new Symbol(sym.matrix, yyline, yycolumn, yytext()); }
<YYINITIAL> {result}              {  System.out.println("Reconocido: <<"+yytext()+">>, result");
                                return new Symbol(sym.result, yyline, yycolumn, yytext()); }
<YYINITIAL> {pie}              {  System.out.println("Reconocido: <<"+yytext()+">>, pie");
                                return new Symbol(sym.pie, yyline, yycolumn, yytext()); }
<YYINITIAL> {barplot}              {  System.out.println("Reconocido: <<"+yytext()+">>, barplot");
                                return new Symbol(sym.barplot, yyline, yycolumn, yytext()); }
<YYINITIAL> {plot}              {  System.out.println("Reconocido: <<"+yytext()+">>, plot");
                                return new Symbol(sym.plot, yyline, yycolumn, yytext()); }
<YYINITIAL> {hist}              {  System.out.println("Reconocido: <<"+yytext()+">>, hist");
                                return new Symbol(sym.hist, yyline, yycolumn, yytext()); }


//signos--------------------------------------------------------------------------------------------------------------
<YYINITIAL> {coma}              {  System.out.println("Reconocido: <<"+yytext()+">>, coma");
                                return new Symbol(sym.coma, yyline, yycolumn, yytext()); }
<YYINITIAL> {punto}             {  System.out.println("Reconocido: <<"+yytext()+">>, punto");
                                return new Symbol(sym.punto, yyline, yycolumn, yytext()); }
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
<YYINITIAL> {aCorchete}         {  System.out.println("Reconocido: <<"+yytext()+">>, aCorchete");
                                return new Symbol(sym.aCorchete, yyline, yycolumn, yytext()); }
<YYINITIAL> {cCorchete}         {  System.out.println("Reconocido: <<"+yytext()+">>, cCorchete");
                                return new Symbol(sym.cCorchete, yyline, yycolumn, yytext()); }
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
<YYINITIAL> {id1}              {  System.out.println("Reconocido: <<"+yytext()+">>, id1");
                                return new Symbol(sym.id1, yyline, yycolumn, yytext()); }
<YYINITIAL> {id2}              {  System.out.println("Reconocido: <<"+yytext()+">>, id2");
                                return new Symbol(sym.id2, yyline, yycolumn, yytext()); }
//<YYINITIAL> {ERCadena}          {  System.out.println("Reconocido: <<"+yytext()+">>, ERCadena");
//                                return new Symbol(sym.ERCadena, yyline, yycolumn, yytext()); }

  


[ \t\r\n\f]                 {/* ignore white space. */ }
.                           {System.out.println("ERROR LEXICO: <<"+yytext()+">> Linea: "+yyline+" ,Columna: "+yycolumn);
                            errorLexico.add(new ErrorE("ERROR LEXICO: <<"+yytext()+">> Linea: "+yyline+" ,Columna: "+yycolumn));
                            ;}