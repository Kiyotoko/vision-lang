package listener

import (
	"fmt"
	"github.com/vision-lang/parser"
)

type TreeShapeListener struct {
	*parser.BaseVisionListener
}

func NewTreeShapeListener() *TreeShapeListener {
	return new(TreeShapeListener)
}

func (listener *TreeShapeListener) ExitPackageName(ctx *parser.PackageNameContext) {
	fmt.Println("File is in package:", ctx.Instance().GetText())
}

func (listener *TreeShapeListener) ExitClassDeclaration(ctx *parser.ClassDeclarationContext) {
	className := ctx.PASCAL_CASE().GetText()
	fmt.Println("Class Name:", className)
	body := ctx.ClassBody()

	args := body.AllArgDeclaration()
	for i := 0; i < len(args); i++ {
		arg := args[i]
		argName := arg.SNAKE_CASE().GetText()
		argType := arg.Type_().GetText()
		fmt.Println("Class", className, "has argument", argName, "with type", argType)
	}

	funs := body.AllFuncDeclaration()
	for i := 0; i < len(funs); i++ {
		fun := funs[i]
		header := fun.FuncHeader()
		funName := header.SNAKE_CASE().GetText()
		fmt.Println("Class", className, "has function", funName)
	}
}

func (listener *TreeShapeListener) ExitInterfaceDeclaration(ctx *parser.InterfaceDeclarationContext) {
	fmt.Println("Interface Name:", ctx.PASCAL_CASE().GetText())
}
