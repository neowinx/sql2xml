package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import play.api.db.DB

object Application extends Controller {

  def index = Action { implicit request =>
    var xmlBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
    DB.withConnection { implicit c =>
      SQL(" SELECT id as toposte, email FROM User ")().map {
        row => {
          row.metaData.ms map {
            i => {
              xmlBody += "<" + i.column.alias.mkString + ">" + row.data.mkString + "</" + i.column.alias.mkString + ">"
            }
          }
        }
      }
    }
    Ok(xmlBody)
  }

}