package il.ac.technion.cs.ssdl.lola.parser.builders;
import il.ac.technion.cs.ssdl.lola.parser.*;
import il.ac.technion.cs.ssdl.lola.parser.builders.AST.*;
import il.ac.technion.cs.ssdl.lola.parser.lexer.*;
import il.ac.technion.cs.ssdl.lola.utils.*;
public class $Assert extends ExecutableElaborator implements GeneratingKeyword {
	public $Assert(final Token token) {
		super(token);
		state = Automaton.Snippet;
	}

	/**
	 * $If follows the pattern: #If(<expression>) B1 #elseIf(<expression>) B2
	 * #elseIf(<expression>) B3 ... #else Bn
	 */
	@Override
	public boolean accepts(final AST.Node b) {
		return iz.snippetToken(b) && state == Automaton.Snippet || iz.triviaToken(b);
	}

	@Override
	public void adopt(final AST.Node b) {
		if (iz.triviaToken(b))
			return;
		snippet = (SnippetToken) b;
		state = Automaton.Done;
	}

	@Override
	public void execute(final Chain<Bunny, Lexi>.Interval __, final PythonAdapter a, final Parser p) {
		if (!a.evaluateBooleanExpression(snippet.getExpression()))
			throw new AssertionError("Assertion failed: " + snippet);
	}

	@Override
	public String generate(final PythonAdapter ¢) {
		if (snippet != null && !¢.evaluateBooleanExpression(snippet.getExpression()))
			throw new AssertionError("Assertion failed: " + snippet);
		return "";
	}
};
