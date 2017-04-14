package br.edu.ifam.underattack;

public class TesteRegex {
	
	public static void main(String[] args) {
		
		/*String regex = "([\\d]{2}\\s){6}\\S+$";
		
		String reg = "[\\d]{2}";
		
		String r = "([\\d]{2}\\s){3}\\S+";
		
		String blank = "([\\d]{2}\\s){2}";
		
		System.out.println("12 33 ".matches(blank));
		
		System.out.println("22 22 33".matches(r));*/
		
		String s = "<teste>";
		String sn = s.replace("<teste>", "teste");
		
		System.out.println(s);
		System.out.println(sn);
		
		
	}

}
