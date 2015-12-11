package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.i18n.Messages
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

import business.{CVRQuery, HistoryQuery}

class Application extends Controller {

  val queryForm: Form[QueryData] = Form(
    mapping(
      "vid" -> text,
      "uid" -> text,
      "start_time" -> text,
      "end_time" -> text
    )(QueryData.apply)(QueryData.unapply)
  )

  def index = Action {
    Ok(views.html.index())
  }

  def cvr(src: String) = Action { implicit request =>
    val CVR = CVRQuery.getCVR(src)
    Ok(views.html.cvr(CVR))
  }

  def history = Action {
    Ok(views.html.history())
  }

  def historyQuery = Action { implicit request =>
    queryForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("Error!")
      },
      query => {
        val UserHistory = HistoryQuery.getHistory(query)
        Ok(views.html.history(UserHistory))
      }
    )
  }

}

case class QueryData(vid: String, uid: String, start_time: String, end_time: String)
