package com.epdc.study.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionProducer {

	public class TransactionListenerImpl implements TransactionListener {
		private AtomicInteger transactionIndex = new AtomicInteger(0);
		private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();
		@Override
		public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
			int value = transactionIndex.getAndIncrement();
			int status = value % 3;
			localTrans.put(msg.getTransactionId(), status);
			return LocalTransactionState.UNKNOW;
		}
		@Override
		public LocalTransactionState checkLocalTransaction(MessageExt msg) {
			Integer status = localTrans.get(msg.getTransactionId());
			if (null != status) {
				switch (status) {
					case 0:
						return LocalTransactionState.UNKNOW;
					case 1:
						return LocalTransactionState.COMMIT_MESSAGE;
					case 2:
						return LocalTransactionState.ROLLBACK_MESSAGE;
				}
			}
			return LocalTransactionState.COMMIT_MESSAGE;
		}
	}

	@Test
	public void test() throws Exception {
		TransactionListener transactionListener = new TransactionListenerImpl();
		TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
		ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("client-transaction-msg-check-thread");
				return thread;
			}
		});
		producer.setExecutorService(executorService);
		producer.setTransactionListener(transactionListener);
		producer.start();
		String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
		for (int i = 0; i < 10; i++) {
			try {
				Message msg =
						new Message("TopicTest1234", tags[i % tags.length], "KEY" + i,
								("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
				SendResult sendResult = producer.sendMessageInTransaction(msg, null);
				System.out.printf("%s%n", sendResult);
				Thread.sleep(10);
			} catch (MQClientException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < 100000; i++) {
			Thread.sleep(1000);
		}
		producer.shutdown();
	}

}
