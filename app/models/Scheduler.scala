package models

import java.util.concurrent.{LinkedBlockingQueue, TimeUnit, ThreadPoolExecutor}

object Scheduler {
  val THREADS_NB = 100
  private val executor_ = new ThreadPoolExecutor(THREADS_NB, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new LinkedBlockingQueue)
  def putTask(runnable:Runnable) = executor_.execute(runnable)
  def getScheduledNb = executor_.getQueue.size
  def getRunningNb = executor_.getActiveCount
}
