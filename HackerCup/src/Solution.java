
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) throws FileNotFoundException {
		try (Scanner in = new Scanner(new File("input.txt")); PrintWriter out = new PrintWriter("output.txt")) {
			int t = in.nextInt();
			for (int i = 1; i <= t; i++) {
				int n = in.nextInt();
				int[] a = getIntArr(in, n);
				int[] b = getIntArr(in, n);
				int r = solve(n, a, b);
				out.println("Case #" + i + ": " + r);
			}

		}

	}

	private static int solve(int n, int[] a, int[] b) {
		return (n % 2 == 0) ? solveEven(n, a, b) : solveOdd(n, a, b);
	}

	static int solveEven(int n, int[] a, int[] b) {
		int ans = 0;
		int xid = findTheOnlyEquivalent(ans, a, b);
		if (xid != -1) {
			return -1;
		}
		int lDiff = 0;
		int rDiff = 0;
		int turnCnt = 0;
		for (int i = 0; i < n; i++) {
			if (i > 0 && ((a[i] < b[i] && a[i - 1] > b[i - 1]) || (a[i] > b[i] && a[i - 1] < b[i - 1]))) {
				turnCnt++;
				if (turnCnt > 2) {
					return -1;
				}
			}
			if (i < n / 2) {
				lDiff += getDiff(a, b, i);
			} else {
				rDiff += getDiff(a, b, i);
			}
		}
		int step = -1;
		for (int i = 0; i < n; i++) {
			if (lDiff == -n / 2 && rDiff == n / 2) {
				step = i;
				break;
			}
			int x = getDiff(a, b, i);
			int y = getDiff(a, b, (i + n / 2) % n);
			if (i >= n / 2) {
				y = -y;
			}
			lDiff += y - x;
			rDiff += -x - y;
		}

		if (step == -1 && lDiff == -n / 2 && rDiff == n / 2) {
			step = n;
		}
		if (step == -1) {
			return -1;
		}
		shiftLeft(a, b, step);
		ans += step;
		if (check(n, a, b)) {
			return ans;
		}
		return -1;
	}

	private static int getDiff(int[] a, int[] b, int i) {
		return (a[i] < b[i]) ? -1 : 1;
	}

	static int solveOdd(int n, int[] a, int[] b) {
		int ans = 0;
		int id = findTheOnlyEquivalent(n, a, b);
		if (id == -1) {
			return -1;
		}
		int step = 0;
		if (id >= n / 2) {
			step = id - n / 2;
		} else {
			step = id + n / 2 + 1;
		}
		shiftLeft(a, b, step);
		ans += step;
		if (a[n / 2 - 1] > b[n / 2 - 1]) {
			shiftLeft(a, b, n);
			ans += n;
		}
		if (check(n, a, b)) {
			return ans;
		}
		return -1;
	}

	private static boolean check(int n, int[] a, int[] b) {
		if (!checkOrder(n, a, b)) {
			return false;
		}

		return checkValue(n, a, b);
	}

	private static boolean checkValue(int n, int[] a, int[] b) {
		int i = 0;
		int j = n - 1;
		while (i < j) {
			if (a[i] - b[i] != b[j] - a[j] || a[i] != b[j]) {
				return false;
			}
			i++;
			j--;
		}
		return true;
	}

	private static boolean checkOrder(int n, int[] a, int[] b) {
		for (int i = 0; i < n; i++) {
			if (n % 2 == 1 && i == n / 2) {
				if (a[i] != b[i]) {
					return false;
				}
			}
			if (i < n / 2) {
				if (a[i] > b[i]) {
					return false;
				}
			} else {
				if (a[i] < b[i]) {
					return false;
				}
			}
		}
		return true;
	}

	static void shiftLeft(int[] a, int[] b, int x) {
		reverseSingle(a, x);
		reverseSingle(b, x);
		for (int i = 0; i < x; i++) {
			swap(a, b, a.length - i - 1);
		}
	}

	private static void reverseSingle(int[] a, int x) {
		reverse(a, 0, x - 1);
		reverse(a, x, a.length - 1);
		reverse(a, 0, a.length - 1);
	}

	static void reverse(int[] a, int start, int end) {
		int i = start;
		int j = end;
		while (i < j) {
			swap(a, i++, j--);
		}
	}

	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	static void swap(int[] a, int[] b, int i) {
		int t = a[i];
		a[i] = b[i];
		b[i] = t;
	}

	private static int findTheOnlyEquivalent(int n, int[] a, int[] b) {
		int ans = -1;
		for (int i = 0; i < n; i++) {
			if (a[i] == b[i]) {
				if (ans == -1) {
					ans = i;
				} else {
					return -1;
				}
			}
		}
		return ans;
	}

	static int[][] dirs = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };
	static long MOD = 1000000007;

	static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += MOD;
		}
		return r % MOD;
	}

	static long mul(long a, long b) {
		long r = a * b;
		while (r < 0) {
			r += MOD;
		}
		return r % MOD;
	}

	static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	static String str(List<Integer> a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i) + " ");
		}
		return sb.toString();
	}

	static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
	}

	static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	static long[][] getLongArr(Scanner in, int row, int col) {
		long[][] arr = new long[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getLongArr(in, col);
		}
		return arr;
	}

	static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	static char[][] getCharArr(Scanner in, int row, int col) {
		char[][] arr = new char[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = in.next().toCharArray();
		}

		return arr;
	}

	static String[] getLineArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLine();
		}
		return arr;
	}

	static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
		Map<Integer, List<Integer>> edges = new HashMap<>();
		for (int i = 0; i < size; i++) {
			int from = in.nextInt();
			int to = in.nextInt();
			if (!edges.containsKey(from)) {
				edges.put(from, new ArrayList<Integer>());
			}
			edges.get(from).add(to);
			if (!directed) {
				if (!edges.containsKey(to)) {
					edges.put(to, new ArrayList<Integer>());
				}
				edges.get(to).add(from);
			}

		}
		return edges;
	}

	static void set(int[][] a, int v) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = v;
			}
		}
	}
}
