package minijava;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GCI {
	private static GCI gen;
	public static String path;

	public static GCI gen() {
		if (gen == null)
			gen = new GCI();

		return gen;
	}

	private int e;
	private FileWriter f;
	private PrintWriter pw;
	private String tab = "\t\t";

	private String spaces, nop;

	private GCI() {
		setSpaces(25);
		e = 0;
		try {
			f = new FileWriter(path);
			pw = new PrintWriter(f);
		} catch (Exception e) {
			System.out.println("Archivo de salida invalido.");
		}
	}

	public void setSpaces(int max) {
		String s = "";
		for (int i = 0; i < max; i++)
			s += " ";
		
		String ss="";
		for(int i=0;i<max/1.5;i++){
			ss += "-"; 
		}
		nop = ss;
		spaces = s;
	}

	public String label() {
		String l = "L" + e;
		e++;
		return l;
	}

	public void close() throws IOException {
		this.f.close();
	}

	public void ln() {
		this.pw.println("");
	}

	public void openCommentD(String c) {
		ln();
		gen(";"+nop,c);
	}
	public void closeCommentD(String c) {
		gen(";"+nop,c);
	}

	public void comment(String c) {
		this.pw.println("; " + c);
	}

	public void code() {
		this.pw.println(".CODE");
	}

	public void data() {
		this.pw.println(".DATA");
	}

	public void gen(String label, String code, String comment) {
		String s = "";
		if (!label.equals(""))
			s += label + ": ";

		if (!code.equals(""))
			s += calc_spaces(label) + code;

		if (!comment.equals(""))
			s += calc_spaces(code) + "; " + comment;
		this.pw.println(s);
	}

	public void gen(String code, String comment) {
		String s = "";
		if (!code.equals(""))
			s += spaces + code;

		if (!comment.equals(""))
			s += calc_spaces(code) + "; " + comment;
		this.pw.println(s);
	}

	private String calc_spaces(String d) {
		if (d.length() == 0)
			return spaces;
		int s = spaces.length() - (d.length() + 2);
		String ss = "";
		if (s > 0)
			ss = spaces.substring(0, s);

		return ss;
	}
}
