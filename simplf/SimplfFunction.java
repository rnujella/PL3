package simplf;
 
import java.util.List;

class SimplfFunction implements SimplfCallable {
    private  Environment closure;
    private final Stmt.Function declaration;
    

    SimplfFunction(Stmt.Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    public void setClosure(Environment environment) {
        this.closure = environment;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        Environment env = new Environment(closure);

        for (int i = 0; i < declaration.params.size(); i++) {
            String param = declaration.params.get(i).lexeme();
            env.define(null, param, args.get(i));
        }

        try {
            interpreter.executeBlock(declaration.body, env);
        } catch (Return returnValue) {
            return returnValue.value;
        }
        return null;
    }

    @Override
    public String toString() {
        return "<fn >";
    }

    public int arity() {
        return declaration.params.size(); // Returns the number of parameters
    }

}


class Return extends RuntimeException {
    final Object value;

    Return(Object value) {
        super(null, null, false, false);
        this.value = value;
    }
}