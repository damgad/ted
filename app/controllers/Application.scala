package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.concurrent._
import org.springframework.core.env.SystemEnvironmentPropertySource

object Application extends Controller {

  def newTask(classpath:String) = Action {
    try {
      Scheduler.putTask(Class.forName(classpath).newInstance.asInstanceOf[Runnable])
      Ok
    } catch {
      case e @ (_ : ClassCastException | _ :  InstantiationException | _ : ClassNotFoundException | _ : IllegalAccessException) => println(e+" (returned 400)"); BadRequest
      case e: Exception => println(e + " (returned 500)"); InternalServerError
    }
  }

  def scheduled = Action { Ok(Scheduler.getScheduledNb.toString) }

  def running = Action { Ok(Scheduler.getRunningNb.toString) }

}