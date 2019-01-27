
package mynode;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;        
import java.awt.*;
import java.awt.event.*;
import java.util.*;



public class MyNode extends DefaultMutableTreeNode {

	private String namn;
	private String text;
	private String nodelevel;

	public MyNode(String name) {
		namn = name;
	}

	public static void main(String[] args) {

	}

	public String toString() {
		return namn;
	}

	public void setText(String input) {
		text = input;
	}

	public void setLevel(String input) {
		nodelevel = input;
	}

	public String getnodeLevel() {
		return nodelevel;
	}


	public String getText() {
		return text;
	}

}