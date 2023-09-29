public class Hw1_4 {
	public static void main(String[] args) {
		int[][] arr = new int[4][4];
		int cnt = 0;
		
		while (cnt < 10) {
			int i = (int)Math.floor(Math.random() * 4);
			int j = (int)Math.floor(Math.random() * 4);
			if (arr[i][j] == 0) {
				arr[i][j] = (int)Math.floor(Math.random() * 10) + 1;
				cnt++;
			}
		}
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(arr[i][j]);
				System.out.print("\t\n".charAt(j == 3 ? 1 : 0));
			}
		}
	}
}