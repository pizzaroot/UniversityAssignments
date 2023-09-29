import java.util.Scanner;

public class Hw1_3 {
	static int genNextInt() {
		return (int)(Math.floor(Math.random() * 100)) + 1;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("정수 몇개?");
		int gen = sc.nextInt();
		int[] arr = new int[gen];
		
		for (int i = 0; i < gen; i++) {
			boolean valid;
			do {
				int x = genNextInt();
				arr[i] = x;
				valid = true;
				for (int j = 0; j < i && valid; j++) {
					if (arr[j] == x) valid = false;
				}
			} while (!valid);
		}
		
		for (int i = 0; i < gen; i++) {
			System.out.print(arr[i]);
			System.out.print(" \n".charAt(i % 10 == 9 ? 1 : 0));
		}
		
		sc.close();
	}
}