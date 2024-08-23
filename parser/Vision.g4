grammar Vision;

root: moduleDeclaration (useDeclaration)* (structDeclaration | funcDeclaration)* EOF;

// Module, import and packages
moduleDeclaration: KEY_MOD packageName ;
useDeclaration: KEY_USE packageName ;
packageName: NAME('.'NAME)*;

// Strutures
structDeclaration: KEY_STRUCT NAME '{' structBody '}' ;
structBody: ( argDeclaration )* ;

// Functions
funcDeclaration: KEY_FUNC NAME '(' funcParameters ')' '{' body '}'
               | KEY_FUNC NAME '(' funcParameters ')' funcReturntype '{' body '}';
funcParameters: (argDeclaration (',' argDeclaration)*)? ;
funcReturntype: RETURN type;
funcCall: NAME '(' funcArgs ')' ;
funcArgs: (value (',' value)*)?;

// Body and statements
body: (statement)* (returnStatement|breakStatement)? ;
statement: varDeclaration | funcCall | ifDeclaration | whileDeclaration | forDeclaration;
returnStatement: KEY_RETURN value?;
breakStatement: KEY_BREAK;

// If, elif and if/else
ifDeclaration: KEY_IF boolValue '{' body '}' (KEY_ELIF boolValue '{' body '}')* (KEY_ELSE '{' body '}')?;

// Loops
whileDeclaration: KEY_WHILE boolValue '{' body '}' ;
forDeclaration: KEY_FOR iterDeclaration '{' body '}' ;
iterDeclaration: NAME IN iterValue ;

// Variables and arguments
varDeclaration: argDeclaration EQUALS value ;
argDeclaration: NAME ':' type ;

// Values
value: boolValue | numValue | stringValue | iterValue | newValue | accessValue;
varValue: NAME | funcCall | accessValue;
boolValue: BOOL | varValue | '(' boolEvaluation ')' | numToBoolEvaluation;
boolEvaluation: OP_NOT? boolValue boolOperator boolValue;
numToBoolEvaluation: numValue numToBoolOpertor numValue;
numValue: INTEGER | FLOAT | varValue | '(' numEvaluation ')';
numEvaluation: numValue numOperator numValue;
iterValue: varValue | rangeEvaluation;
rangeEvaluation: numValue TO numValue;
stringValue: STRING | varValue;
newValue: NAME '{' newParameters '}';
newParameters: (newArgument (',' newArgument)*)?;
newArgument: NAME EQUALS value ;
accessValue: NAME('.'NAME)+;

// Types
type: NAME | nativeType;
nativeType: numberType | TYPE_BOOL | TYPE_CHAR | TYPE_STRING;
numberType: integerType | floatType;
integerType: TYPE_INT8 | TYPE_INT16 | TYPE_INT32 | TYPE_INT64;
floatType: TYPE_FLOAT | TYPE_DOUBLE;
TYPE_BOOL: 'bool';
TYPE_CHAR: 'char';
TYPE_STRING: 'str';
TYPE_INT8: 'i8';
TYPE_INT16: 'i16';
TYPE_INT32: 'i32';
TYPE_INT64: 'i64';
TYPE_FLOAT: 'float';
TYPE_DOUBLE: 'double';

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
KEY_STRUCT: 'struct';
KEY_IF: 'if';
KEY_ELIF: 'elif';
KEY_ELSE: 'else';
KEY_FOR: 'for';
KEY_WHILE: 'while';
KEY_BREAK: 'break';
KEY_RETURN: 'return';
KEY_MOD: 'mod';
KEY_USE: 'use';

NAME: [_]*[a-zA-Z][a-zA-Z0-9_]*;
EQUALS: '=';
RETURN: '->';
IN: ':';
TO: '..';
SIGN: '+'|'-';
INTEGER: SIGN?[0-9]+;
FLOAT: SIGN?[0-9]+'.'[0-9]+;
STRING: '"'().*?'"';
BOOL: 'true' | 'false';
WS: [ \t\n\r]+ -> skip;
