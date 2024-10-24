package main

import (
	"github.com/antlr4-go/antlr/v4"
	"github.com/vision-lang/listener"
	"github.com/vision-lang/parser"
	"os"
)

func main() {
	input, _ := antlr.NewFileStream(os.Args[1])
	lexer := parser.NewVisionLexer(input)
	stream := antlr.NewCommonTokenStream(lexer, 0)
	p := parser.NewVisionParser(stream)
	p.AddErrorListener(antlr.NewDiagnosticErrorListener(true))
	tree := p.Root()
	antlr.ParseTreeWalkerDefault.Walk(listener.NewTreeShapeListener(), tree)
}
