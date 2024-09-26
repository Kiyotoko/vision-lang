grammar Vision;

root: moduleDeclaration (useDeclaration)* (classDeclaration | interfaceDeclaration | funcDeclaration)* EOF;

// Module, import and packages
moduleDeclaration: KEY_MOD packageName ;
useDeclaration: KEY_USE packageName ;
packageName: instance;

// Strutures
classDeclaration: KEY_CLASS PASCAL_CASE '{' classBody '}' ;
classBody: ( argDeclaration )* ( funcDeclaration )* ;

interfaceDeclaration: KEY_INTERFACE PASCAL_CASE '{' interfaceBody '}' ;
interfaceBody: funcHeader* ;

// Functions
funcDeclaration: funcHeader '{' body '}';
funcHeader: KEY_FUNC SNAKE_CASE '(' funcParameters ')' funcReturntype?;
funcParameters: (argDeclaration (',' argDeclaration)*)? ;
funcReturntype: RETURN type;

// Body and statements
body: (statement)* (returnStatement|breakStatement)? ;
statement: varDeclaration | ifDeclaration | whileDeclaration | forDeclaration | callValue;
returnStatement: KEY_RETURN value?;
breakStatement: KEY_BREAK;

// If, elif and if/else
ifDeclaration: KEY_IF boolValue '{' body '}' (KEY_ELIF boolValue '{' body '}')* (KEY_ELSE '{' body '}')?;

// Loops
whileDeclaration: KEY_WHILE boolValue '{' body '}' ;
forDeclaration: KEY_FOR iterDeclaration '{' body '}' ;
iterDeclaration: instance IN iterValue ;

// Variables and arguments
instance: SNAKE_CASE ('.' SNAKE_CASE)*;
varDeclaration: KEY_VAR SNAKE_CASE EQUALS value ;
argDeclaration: SNAKE_CASE ':' type ;

// Values
value: varValue
    | callValue
    | boolValue
    | numValue
    | stringValue
    | iterValue;
varValue: instance;
callValue: instance '(' callArgs? ')'
        | PASCAL_CASE '(' callArgs? ')';
callArgs: value (',' value)*;
boolValue: BOOL | varValue | '(' boolEvaluation ')' | numToBoolEvaluation;
boolEvaluation: OP_NOT? boolValue boolOperator boolValue;
numToBoolEvaluation: numValue numToBoolOpertor numValue;
numValue: INTEGER | FLOAT | varValue | '(' numEvaluation ')';
numEvaluation: numValue numOperator numValue;
iterValue: varValue | rangeEvaluation;
rangeEvaluation: numValue TO numValue;
stringValue: STRING | varValue;

// Types
type: PASCAL_CASE | nativeType;
nativeType: numberType | TYPE_BOOL | TYPE_CHAR | TYPE_STRING;
numberType: TYPE_INT | TYPE_FLOAT;
TYPE_BOOL: 'bool';
TYPE_CHAR: 'char';
TYPE_STRING: 'str';
TYPE_INT: 'int';
TYPE_FLOAT: 'float';

// Bool Operators
boolOperator: OP_AND | OP_EQU | OP_NEQ | OP_OR | OP_XOR;
OP_AND: '&&';
OP_EQU: '==';
OP_NEQ: '!=';
OP_OR: '||';
OP_XOR: '|';
OP_NOT: '!';

numToBoolOpertor: OP_LARGER | OP_SMALLER | OP_EQU | OP_LEQ | OP_SEQ;
OP_LARGER: '>';
OP_SMALLER: '<';
OP_LEQ: '>=';
OP_SEQ: '<=';

// Number Operators
numOperator: OP_ADD | OP_SUB | OP_MUL | OP_DIV | OP_MOD;
OP_ADD: '+';
OP_SUB: '-';
OP_MUL: '*';
OP_DIV: '/';
OP_MOD: '%';

// Keywords
KEY_VAR: 'var';
KEY_FUNC: 'fn';
KEY_CLASS: 'class';
KEY_INTERFACE: 'interface';
KEY_IF: 'if';
KEY_ELIF: 'elif';
KEY_ELSE: 'else';
KEY_FOR: 'for';
KEY_WHILE: 'while';
KEY_BREAK: 'break';
KEY_RETURN: 'return';
KEY_MOD: 'mod';
KEY_USE: 'use';

// Key symbols
EQUALS: '=';
RETURN: '->';
IN: ':';
TO: '..';

// Names
SNAKE_CASE: [_]*[a-z][a-z_]*; // Used for functions, variables, modules and packages
PASCAL_CASE: [A-Z][a-zA-Z]*; // Used for class names
UPPER_CASE: [A-Z][A-Z_]*; // Used for consance

// Numbers
SIGN: '+'|'-';
INTEGER: SIGN?[0-9]+;
FLOAT: SIGN?[0-9]+'.'[0-9]+;

// Strings
STRING: '"'().*?'"';

// Booleans
BOOL: 'true' | 'false';

WS: [ \t\n\r]+ -> skip;
