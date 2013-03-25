package controllers

import play.api._
import play.api.mvc._
import java.util.concurrent._
import org.springframework.core.env.SystemEnvironmentPropertySource

object Application extends Controller {

  val executor = new ThreadPoolExecutor(20, 40, 60, TimeUnit.SECONDS, new LinkedBlockingQueue)

  def newTask(classpath:String) = Action {
    try {
      executor.execute(Class.forName(classpath).newInstance.asInstanceOf[Runnable])
      Ok
    } catch {
      case e @ (_ : ClassCastException | _ :  InstantiationException | _ : ClassNotFoundException | _ : IllegalAccessException) => println(e+" (returned 400)"); BadRequest
      case e: Exception => println(e + " (returned 500)"); InternalServerError
    }
  }

  def scheduled = Action { Ok(executor.getQueue.size.toString) }

  def running = Action { Ok(executor.getActiveCount.toString) }

  def setthreads(classpath:String) = Action {

    val task = Class.forName(classpath).newInstance.asInstanceOf[Runnable];
    def nextTest(nbthreads:Int, lasttimepertask:Double) :Int ={
      executor.setCorePoolSize(nbthreads)
      executor.setMaximumPoolSize(2*nbthreads)
      //System.out.print("Test for" + nbthreads)
      val s= System.nanoTime
      for(j <- 1 to nbthreads-1)  executor.execute(task)
      executor.submit(task).get
      val timepertask : Double= ((System.nanoTime-s))/nbthreads
      //System.out.println(": " +timepertask)
      if ( timepertask > lasttimepertask) nbthreads  else nextTest(nbthreads*2,timepertask)

    }
    val nb=nextTest(10,Double.MaxValue)
    executor.setCorePoolSize(3*nb/4);
    executor.setMaximumPoolSize(nb)
    Ok((3*nextTest(10,Double.MaxValue)/4).toString)
  }

}