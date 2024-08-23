package main

import (
	"github.com/antlr4-go/antlr/v4"
	"github.com/vision-lang/parsing"
	"os"
	"fmt"
)

type TreeShapeListener struct {
	*parsing.BaseVisionListener
}

func NewTreeShapeListener() *TreeShapeListener {
	return new(TreeShapeListener)
}

func (this *TreeShapeListener) EnterEveryRule(ctx antlr.ParserRuleContext) {
	fmt.Println(ctx.GetText())
}

func main() {
	input, _ := antlr.NewFileStream(os.Args[1])
	lexer := parsing.NewVisionLexer(input)
	stream := antlr.NewCommonTokenStream(lexer,0)
	p := parsing.NewVisionParser(stream)
	p.AddErrorListener(antlr.NewDiagnosticErrorListener(true))
	tree := p.Root()
	antlr.ParseTreeWalkerDefault.Walk(NewTreeShapeListener(), tree)
}
