public class ArgSum {
	public static void main(String[] args) {
		int retSum = 0;

		for (int i = 0; i < args.length; i += 1) {
			retSum += Integer.parseInt(args[i]);
		}
		System.out.println(retSum);
	}
}