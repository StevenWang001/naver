package com.naver;

import java.util.Stack;
import org.junit.Assert;

public class MyQueue<E> {

    private Stack<E> stack1 = new Stack<>();
    private Stack<E> stack2 = new Stack<>();

    public static void main(String[] args) {

        MyQueue<String> myQueue = new MyQueue<>();
        myQueue.push("a");
        myQueue.push("b");

        Assert.assertEquals("a", myQueue.peek());
        Assert.assertEquals("a", myQueue.pop());
        myQueue.push("c");
        myQueue.push("d");
        Assert.assertEquals("b", myQueue.pop());
        Assert.assertEquals("c", myQueue.pop());
        Assert.assertEquals("d", myQueue.pop());
        Assert.assertTrue(myQueue.empty());

        myQueue.push("e");
        myQueue.push("f");
        Assert.assertEquals("e", myQueue.pop());
        Assert.assertEquals("f", myQueue.pop());
        Assert.assertTrue(myQueue.empty());
    }


    public synchronized void push(E str) {
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        stack1.push(str);
    }

    public synchronized E pop() {
        if (!stack2.isEmpty()) {
            return stack2.pop();
        }
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        if (stack2.isEmpty()) {
            throw new EmptyQueueException();
        }
        return stack2.pop();
    }

    public synchronized E peek() {
        if (!stack2.isEmpty()) {
            return stack2.peek();
        }
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        if (stack2.isEmpty()) {
            throw new EmptyQueueException();
        }
        return stack2.peek();
    }


    public synchronized boolean empty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }
}


class EmptyQueueException extends RuntimeException {

}