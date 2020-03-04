package structures;

import java.util.ArrayList;

import interfaces.IAST;

public class ReturnEnrichissement {
	public ArrayList<IAST> preList=new ArrayList<>();
	public IAST a=null;
	public ArrayList<IAST> postList=new ArrayList<>();
	public ReturnEnrichissement(IAST ast) {
		a=ast;
	}
	public ReturnEnrichissement( ArrayList<IAST> pre,IAST ast) {
		a=ast;
		preList=pre;
	}
	public ReturnEnrichissement(IAST ast,ArrayList<IAST> post) {
		a=ast;
		postList=post;
	}
	public ReturnEnrichissement(ArrayList<IAST> pre,IAST ast, ArrayList<IAST> post) {
		a=ast;
		preList=pre;
		postList=post;
	}
	public ArrayList<IAST> getPreList(){
		return preList;
	}
	public IAST getIAST() {
		return a;
	}
	public ArrayList<IAST> getPostList(){
		return postList;
	}
	public String toString() {
		return (""+preList.size()+a.getClass()+postList.size());
	}
}
