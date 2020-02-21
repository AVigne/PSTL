package ast;

import java.util.List;

import interfaces.IAST;

public abstract class AST implements IAST {
	protected IAST owner;
	public IAST getOwner() {
		return owner;
	}
}
