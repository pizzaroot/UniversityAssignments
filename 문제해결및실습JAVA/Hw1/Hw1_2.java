import java.util.Scanner;

public class Hw1_2 {
	static double evalDouble(String d, int l, int r) {
		String res = "";
		for (int i = l; i < r; i++) res += d.charAt(i);
		return Double.parseDouble(res);
	}
	
	static double evalVal(String val, int l, int r) {
		for (int i = r - 1; i >= l; i--) {
			switch (val.charAt(i)) {
			case '*':
				return evalVal(val, l, i - 1) * evalDouble(val, i + 2, r);
			case '/':
				double div = evalDouble(val, i + 2, r);
				if (div == 0.0d) {
					System.out.println("0으로 나눌 수 없습니다.");
					System.exit(0);
				}
				return evalVal(val, l, i - 1) / div;
			}
		}
		return evalDouble(val, l, r);
	}
	
	static double evalExpr(String expr, int l, int r) {
		for (int i = r - 1; i >= l; i--) {
			switch (expr.charAt(i)) {
			case '+':
				return evalExpr(expr, l, i - 1) + evalVal(expr, i + 2, r);
			case '-':
				return evalExpr(expr, l, i - 1) - evalVal(expr, i + 2, r);
			}
		}
		return evalVal(expr, l, r);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("연산>>");
		String expr = sc.nextLine();
		for (int i = 0; i < expr.length(); i++) {
			if (expr.charAt(i) != ' ') System.out.print(expr.charAt(i));
		}
		System.out.println("의 계산 결과는 " + evalExpr(expr, 0, expr.length()));
		sc.close();
	}
}