package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Forbidden(views.html.index("Your new application is not ready."))
  }
  
}