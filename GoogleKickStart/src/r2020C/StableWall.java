package r2020C;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class StableWall {
	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for (int i = 1; i <= t; ++i) {
			int r = in.nextInt();
			int c = in.nextInt();
			char[][] a = getStringArr(in, r, c);
			String ans = solve(r, c, a);
			System.out.println("Case #" + i + ": " + ans);
		}
		in.close();
	}

	static Map<Character, Set<Character>> adj;
	static Map<Character, Integer> state;
	static List<String> res;

	private static String solve(int r, int c, char[][] a) {
		adj = new HashMap<>();
		state = new HashMap<>();
		res = new ArrayList<>();
		for (int j = 0; j < c; j++) {
			state.put(a[r - 1][j], 0);
		}
		for (int i = r - 2; i >= 0; i--) {
			for (int j = 0; j < c; j++) {
				state.put(a[i][j], 0);
				if (a[i][j] != a[i + 1][j]) {
					Set<Character> s = adj.getOrDefault(a[i][j], new HashSet<>());
					s.add(a[i + 1][j]);
					adj.put(a[i][j], s);
				}
			}
		}
		for (char x : state.keySet()) {
			if (state.get(x) == 0) {
				if (!dfs(x)) {
					return "-1";
				}
			}
		}
		return String.join("", res);
	}

	static boolean dfs(char c) {
		state.put(c, 1);
		for (char d : adj.getOrDefault(c, new HashSet<>())) {
			if (state.get(d) == 1) {
				return false;
			} else if (state.get(d) == 0) {
				if (!dfs(d)) {
					return false;
				}
			}
		}
		state.put(c, 2);
		res.add("" + c);
		return true;
	}

	private static long mod = 1000000007;

	private static long add(long a, long b) {
		long r = a + b;
		if (r < 0) {
			r += mod;
		}
		return r % mod;
	}

	private static long mul(long a, long b) {
		return (a * b) % mod;
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	private static String str(int[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		return sb.toString();
	}

	private static long[] getLongArr(Scanner in, int size) {
		long[] arr = new long[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextLong();
		}
		return arr;
	}

	private static int[] getIntArr(Scanner in, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.nextInt();
		}
		return arr;
	}

	private static int[][] getIntArr(Scanner in, int row, int col) {
		int[][] arr = new int[row][];
		for (int i = 0; i < row; i++) {
			arr[i] = getIntArr(in, col);
		}
		return arr;
	}

	private static char[] getCharArr(Scanner in, int size) {
		char[] arr = in.next().toCharArray();
		return arr;
	}

	private static String[] getStringArr(Scanner in, int size) {
		String[] arr = new String[size];
		for (int i = 0; i < size; i++) {
			arr[i] = in.next();
		}
		return arr;
	}

	private static char[][] getStringArr(Scanner in, int r, int c) {
		char[][] ret = new char[r][];
		for (int i = 0; i < r; i++) {
			ret[i] = in.next().toCharArray();
		}
		return ret;
	}

	private static Map<Integer, List<Integer>> getEdges(Scanner in, int size, boolean directed) {
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
}
