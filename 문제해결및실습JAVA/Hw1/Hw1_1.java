import java.util.Scanner;

class Circle {
	int x, y, r;
	Circle(Scanner sc) {
		x = sc.nextInt();
		y = sc.nextInt();
		r = sc.nextInt();
	}
}

public class Hw1_1 {
	static boolean intersect(Circle c1, Circle c2) {
		long d = (long)(c1.x - c2.x) * (c1.x - c2.x) + (long)(c1.y - c2.y) * (c1.y - c2.y);
		return ((long)c1.r - c2.r) * ((long)c1.r - c2.r) <= d && d <= ((long)c1.r + c2.r) * ((long)c1.r + c2.r);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("첫번째 원의 중심과 반지름 입력>>");
		Circle c1 = new Circle(sc);

		System.out.print("두번째 원의 중심과 반지름 입력>>");
		Circle c2 = new Circle(sc);
		
		if (intersect(c1, c2)) {
			System.out.println("두 원은 서로 겹친다.");
		} else {
			System.out.println("두 원은 서로 겹치지 않는다.");
		}
		
		sc.close();
	}
}