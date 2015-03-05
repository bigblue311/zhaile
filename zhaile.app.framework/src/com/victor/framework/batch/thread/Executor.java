package com.victor.framework.batch.thread;

import java.util.Queue;

import com.google.common.collect.Lists;

public abstract class Executor<T extends ExecutorTask> {
	
	private ExecutorStatusEnum status = ExecutorStatusEnum.停止;
	
	private Queue<T> taskList = Lists.newLinkedList();
	
	//启动
	protected abstract void doStart();
	
	public void start(){
		this.status = ExecutorStatusEnum.停止;
		if(this.taskList.size() == 0){
			return;
		}
		try{
			doStart();
			this.status =  ExecutorStatusEnum.运行;
		} catch(Exception ex){
			this.status =  ExecutorStatusEnum.异常;
			return;
		}
	}
	
	//重启
	public void restart(){
		stop();
		start();
	}
	
	protected abstract void doStop();
	
	//停止
	public void stop(){
		try{
			doStop();
			this.status =  ExecutorStatusEnum.停止;
		} catch(Exception ex){
			this.status =  ExecutorStatusEnum.异常;
			return;
		}
	}
	
	//获取运行状态
	public ExecutorStatusEnum status(){
		return this.status;
	}
	
	//添加一个任务
	public void Enqueue(T task){
		taskList.add(task);
	}
	
	public T Dequeue(){
		return taskList.poll();
	}
	
	public boolean isEmpty(){
		return taskList.isEmpty();
	}
}
