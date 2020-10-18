package dev.liwaak.com.obstacle;

public enum Obstacles {
	TREE(Tree.class);
	
	private Class c;
	private Obstacles(Class c) {
		this.c = c;
	}
	
	public Class getCls() {
		return c;
	}
	
	
}
