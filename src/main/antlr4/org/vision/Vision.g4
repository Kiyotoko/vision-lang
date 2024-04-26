grammar Vision;

aroot: (declaration | call)* EOF;
call: NAME '(' ')';
declaration: VAR NAME EQUALS value;
value: NAME
     | STRING
     | NUMBER;

VAR : 'var';
NAME: [a-zA-Z]+;
EQUALS: '=';
NUMBER: [0-9]+;
STRING: '"'().*?'"';
NEWLINE : [\r\n]+;
WS: [ \t\n\r]+ -> skip;
