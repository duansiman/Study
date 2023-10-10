// 注意: 题目有四道, 请认真仔细读题,
//      如果有不理解的地方, 请联系 HR 或面试官,
//      如果有不会的, 请留空, 不要求做完, 不要盲目答题.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Q1: 加权随机函数生成器
 * <p>
 * 给定一个正整数数组 input, 构造一个加权随机类实例,
 * 该实例的 {@link #next()} 方法被调用时, 随机返回一个该数组的下标, 下标 i 被返回的概率
 * 为该下标对应的元素的值 / 所有元素之和.
 * <p>
 * 要求: {@link #next()} 方法的时间复杂度不超过 O(log(N))
 */
class WeightedRandom {

	private List<Integer> seed;
	private int sum;
	private Random random;

	public WeightedRandom(int[] input) {
		// show me your code
		this.seed = IntStream.of(input).boxed().collect(Collectors.toList());
		sum = IntStream.of(input).sum();
		random = new Random();
	}

	public int next() {
		// show me your code
		int nextInt = random.nextInt(sum);
		int total = 0;
		for (int i = 0; i < seed.size(); i++) {
			total += seed.get(i);
			if (nextInt + 1 > total) {
				continue;
			}
			return i;
		}
		return 0;
	}
}

/**
 * Q2: 字符跳动
 * <p>
 * 给定一个由不重复的小写字母组成的字符串，通过一系列跳动指令变换成一个新的字符串。
 * 跳动指令有：
 * * Move(移动)，简写：M，例如：M:7 表示将字符串向右移动7位。
 * * eXchange(交换)，简写：X，例如：X:a/c 表示将字符串中的 a c 位置交换。
 * * IndexChange(按位置交换)，简写：I，例如：I:2/4 表示将位置2和位置4的字符交换，位置的索引从0开始计数。
 * 示例：
 * 给定初始字符串为：wosjgncxyakdbefh
 * 给定如下指令：
 * M:3   则变换后的新字符串为：efhwosjgncxyakdb
 * I:7/2  则变换后的新字符串为：efgwosjhncxyakdb
 * X:o/h  则变换后的新字符串为：efgwhsjoncxyakdb
 * 因此给定初始字符串：wosjgncxyakdbefh，在经过指令 M:3,I:7/2,X:o/h 的变换后得到新的字符串：efgwhsjoncxyakdb。
 */
class CharDance {

	/**
	 * 题目1：
	 * 给定一个随机的初始字符串: src，给定一组随机的指令: ops，(src 和 ops 一定是合法的)，求经过转换后得到的新字符串。
	 */
	public String transfer(String src, String ops) {
		// show me your code
		StringBuilder sb = new StringBuilder(src);
		String[] opsArray = ops.split(",");
		for (int i = 0; i < opsArray.length; i++) {
			String[] instruction = opsArray[i].split(":");
			switch (instruction[0]) {
				case "M":
					int moveStep = Integer.parseInt(instruction[1]);
					CharSequence movStr = sb.subSequence(sb.length() - moveStep, sb.length());
					sb.delete(sb.length() - moveStep, sb.length()).insert(0, movStr);
					break;
				case "I":
					String[] changeIndexes = instruction[1].split("/");
					int oneIndex = Integer.parseInt(changeIndexes[0]);
					int twoIndex = Integer.parseInt(changeIndexes[1]);
					char oneIndexChar = sb.charAt(oneIndex);
					char twoIndexChar = sb.charAt(twoIndex);
					sb.setCharAt(oneIndex, twoIndexChar);
					sb.setCharAt(twoIndex, oneIndexChar);
					break;
				case "X":
					String[] changeChars = instruction[1].split("/");
					char oneChar = changeChars[0].charAt(0);
					char twoChar = changeChars[1].charAt(0);
					int oneCharIndex = sb.lastIndexOf(changeChars[0]);
					int twoCharIndex = sb.lastIndexOf(changeChars[1]);
					sb.setCharAt(oneCharIndex, twoChar);
					sb.setCharAt(twoCharIndex, oneChar);
					break;
				default:
					break;
			}
		}
		return sb.toString();
	}

	/**
	 * 题目2：
	 * 将上一次转换后得到的新字符串作为初始字符串，使用相同的跳动指令集再进行转换，如此重复执行 count 次，求得到的最终字符串是什么？
	 * 注意: count 足够大, 比如可能超过 2^32.
	 */
	public String transferMultipleTimes(String src, String ops, long count) {
		// show me your code
		char[] chars = src.toCharArray();
		int startIndex = 0;
		String[] opsArray = ops.split(",");
		List<String> opsList = new ArrayList<>();

		int moveStep = 0;
		int oneIndex=0;
		int twoIndex=0;
		char oneChar=' ';
		char twoChar=' ';

		for (int i = 0; i < opsArray.length; i++) {
			String[] instruction = opsArray[i].split(":");
			switch (instruction[0]) {
				case "M":
					moveStep = Integer.parseInt(instruction[1]);
					opsList.add("M");
					break;
				case "I":
					String[] changeIndexes = instruction[1].split("/");
					oneIndex =Integer.parseInt(changeIndexes[0]);
					twoIndex = Integer.parseInt(changeIndexes[1]);
					opsList.add("I");
					break;
				case "X":
					String[] changeChars = instruction[1].split("/");
					oneChar = changeChars[0].charAt(0);
					twoChar = changeChars[1].charAt(0);
					opsList.add("X");
					break;
			}
		}

		for (int j = 0; j < count; j++) {
			for (int i = 0; i < opsList.size(); i++) {
				switch (opsList.get(i)) {
					case "M":
						startIndex = (startIndex + chars.length - moveStep) % chars.length;
						break;
					case "I":
						int oneArrayIndex = (oneIndex + startIndex) % chars.length;
						int twoArrayIndex = (twoIndex + startIndex) % chars.length;
						char change = chars[oneArrayIndex];
						chars[oneArrayIndex] = chars[twoArrayIndex];
						chars[twoArrayIndex] = change;
						break;
					case "X":
						int changeCount = 0;
						for (int k = 0; k < chars.length; k++) {
							if (chars[k] == oneChar) {
								chars[k] = twoChar;
								changeCount++;
							} else if (chars[k] == twoChar) {
								chars[k] = oneChar;
								changeCount++;
							}
							if (changeCount == 2) {
								break;
							}
						}
						break;
					default:
						break;
				}
			}
		}

		StringBuilder sb = new StringBuilder("");
		for (int i = startIndex; ; i++) {
			if (i - startIndex >= chars.length) {
				break;
			}
			sb.append(chars[i % chars.length]);
		}
		return sb.toString();
	}
}

/**
 * Q3: 并发任务控制器
 * <p>
 * 注意: 不可使用 java 提供的线程池相关接口
 */
class AsyncWorker {

	private List<Thread> threads;
	private int capacity;
	private BlockingQueue<Worker> queue;

	/**
	 * 构造函数
	 *
	 * @param capacity 最大并发数量
	 */
	public AsyncWorker(int capacity) {
		// show me your code
		this.threads = new ArrayList<>(capacity);
		this.capacity = capacity;
		this.queue = new LinkedBlockingQueue<>();
	}

	/**
	 * 任务提交函数: 当前正在执行的任务数小于 capacity 时, 立即异步执行, 否则
	 * 等到任意一个任务执行完成后立即执行.
	 *
	 * @param task 任务函数
	 * @param <T>  返回值类型
	 * @return 返回由 Future 包装的任务函数的返回值, 其状态应该和 task 的执行结果一致
	 */
	public <T> Future<T> submit(Callable<T> task) {
		// show me your code
		try {
			CompletableFuture<T> future = new CompletableFuture<>();
			Worker worker = new Worker(task, future);
			this.queue.put(worker);
			threads.removeIf(thread -> !thread.isAlive());
			if (threads.size() < capacity) {
				WorkerThread workerThread = new WorkerThread();
				this.threads.add(workerThread);
				workerThread.start();
			}
			return future;
		} catch (InterruptedException e) {
			System.out.println(e);
			CompletableFuture<T> future = new CompletableFuture<>();
			future.completeExceptionally(e);
			return future;
		}
	}

	class Worker<T> {
		public Callable<T> callable;
		public CompletableFuture<T> future;

		public Worker(Callable<T> callable, CompletableFuture<T> future) {
			this.callable = callable;
			this.future = future;
		}
	}

	class WorkerThread extends Thread {
		@Override
		public void run() {
			while (true) {
				Worker worker = null;
				try {
					worker = AsyncWorker.this.queue.take();
					Object result = worker.callable.call();
					worker.future.complete(result);
				} catch (Exception e) {
					if (worker != null) {
						worker.future.completeExceptionally(e);
					} else {
						break;
					}
				}
			}
		}
	}
}

/* ----------------- 以下是测试用例 -----------------*/

class TestWeightedRandom {

	public static void testWeightedRandom(Function<int[], WeightedRandom> factory) {
		int[] input = { 4, 2, 1, 3, 3 };
		WeightedRandom random = factory.apply(input);
		int[] count = new int[input.length];
		for (int i = 0; i < 10000; i++) {
			int v = random.next();
			if (v < 0 || v >= input.length) {
				throw new RuntimeException("invalid random value: " + v);
			}
			count[v]++;
		}
		int sum = Arrays.stream(input).sum();
		for (int i = 0; i < input.length; i++) {
			double expectedWeight = (double) input[i] / (double) sum;
			double realWeight = (double) count[i] / 10000D;
			if (Math.abs(expectedWeight - realWeight) > 0.01) {
				throw new RuntimeException(
						"invalid weight " + realWeight + " for index " + i + ", expected is " + expectedWeight
				);
			}
		}
	}
}

class TestCharDance {

	public static void testCharDance(Supplier<CharDance> factory) {
		CharDance charDance = factory.get();
		String src = "wosjgncxyakdbefh";
		String ops = "M:3,I:7/2,X:o/h";
		String dst = "efgwhsjoncxyakdb";
		String realDst = charDance.transfer(src, ops);
		if (!dst.equals(realDst)) {
			throw new RuntimeException("invalid transfer result " + realDst + ", expected is " + dst);
		}
		String dst100 = src;
		for (int i = 0; i < 100; i++) {
			dst100 = charDance.transfer(dst100, ops);
		}

		String realDst100 = charDance.transferMultipleTimes(src, ops, 100);
		if (!dst100.equals(realDst100)) {
			throw new RuntimeException("invalid transfer result " + realDst100 + " after 100 times, expected is " + dst100);
		}
	}
}

class TestAsyncWorker {

	private static AsyncWorker worker;
	private static List<Task> tasks;

	public static void testAsyncWorker(Function<Integer, AsyncWorker> factory) {
		worker = factory.apply(2);
		tasks = new ArrayList<>();

		runTask(1, 100, 100, false);
		runTask(2, 200, 200, true);
		runTask(3, 300, 400, false);
		runTask(4, 400, 600, true);
		runTask(5, 100, 500, false);
		runTask(6, 200, 700, true);
		runTask(7, 100, 700, false);
		runTask(8, 200, 900, false);

		for (Task task : tasks) {
			check(task);
		}
	}

	private static void runTask(int id, int delay, int expectedDelay, boolean fail) {
		Task task = new Task();
		task.id = id;
		task.expectedDelay = expectedDelay;
		task.fail = fail;
		long now = System.currentTimeMillis();
		task.value =
				worker.submit(() -> {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException ignored) {}
					long realDelay = System.currentTimeMillis() - now;
					if (fail) {
						throw new RuntimeException(String.valueOf(realDelay));
					} else {
						return (int) realDelay;
					}
				});
		tasks.add(task);
	}

	private static class Task {

		private int id;
		private int expectedDelay;
		private boolean fail;
		private Future<Integer> value;
	}

	private static void check(Task task) {
		int realDelay;
		boolean realFail = false;
		try {
			realDelay = task.value.get();
		} catch (Exception e) {
			realDelay = Integer.parseInt(e.getCause().getMessage());
			realFail = true;
		}
		if (realFail != task.fail) {
			throw new RuntimeException(
					"status of task " +
							task.id +
							" should be " +
							(task.fail ? "failed" : "succeeded") +
							", rather than " +
							(realFail ? "failed" : "succeeded")
			);
		}
		// 忽略延时误差
		if (realDelay / 100 != task.expectedDelay / 100) {
			throw new RuntimeException(
					"delay of task " + task.id + " should be " + task.expectedDelay + ", rather than " + realDelay
			);
		}
	}
}

public class ShowMeBug {

	public static void main(String[] args) {
		TestWeightedRandom.testWeightedRandom(WeightedRandom::new);
		TestCharDance.testCharDance(CharDance::new);
		TestAsyncWorker.testAsyncWorker(AsyncWorker::new);
		System.out.println("done");
	}
}

