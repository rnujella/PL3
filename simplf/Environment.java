package simplf; 

class Environment {
    private final AssocList assocList;
    private final Environment enclosing;
    Environment() {
        this.assocList = null;
        this.enclosing = null;
    }

    Environment(Environment enclosing) {
        this.assocList = null;
        this.enclosing = enclosing;
    }

    Environment(AssocList assocList, Environment enclosing) {
        this.assocList = assocList;
        this.enclosing = enclosing;
    }

    // Return a new version of the environment that defines the variable "name"
    // and sets its initial value to "value". Take care to ensure the proper aliasing
    // relationship. There is an association list implementation in Assoclist.java.
    // If your "define" function adds a new entry to the association list without
    // modifying the previous list, this should yield the correct aliasing
    // relationsip.
    //
    // For example, if the original environment has the association list
    // [{name: "x", value: 1}, {name: "y", value: 2}]
    // the new environment after calling define(..., "z", 3) should have the following
    //  association list:
    // [{name: "z", value: 3}, {name: "x", value: 1}, {name: "y", value: 2}]
    // This should be constructed by building a new class of type AssocList whose "next"
    // reference is the previous AssocList.
    Environment define(Token varToken, String name, Object value) {
        return new Environment(new AssocList(name, value,this.assocList),this.enclosing);
    }

    void assign(Token name, Object value) {
        Environment env = this;
        while (env != null) {
            AssocList list = env.assocList;
            while (list != null) {
                if (list.name.equals(name.lexeme)) {
                    list.value = value;
                    return;
                }
                list = list.next;
            }
            env = env.enclosing;
        }
        throw new RuntimeException("Undeclared variable: " + name.lexeme);
    }

    Object get(Token name) {
        Environment env = this;
        while (env != null) {
            AssocList list = env.assocList;
            while (list != null) {
                if (list.name.equals(name.lexeme)) {
                    return list.value;
                }
                list = list.next;
            }
            env = env.enclosing;
        }
        throw new RuntimeException("Undeclared variable: " + name.lexeme);
    }
}

