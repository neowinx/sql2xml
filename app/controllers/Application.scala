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
    xmlBody += "<USERS>"
    DB.withConnection { implicit c =>
      val consulta = SQL(" SELECT id as toposte, email FROM User ")()
      println(consulta)
      consulta foreach {
        row => {
          xmlBody += "<USER>"
          val rowMap = row.asMap
          row.metaData.ms map {
            i => {
              val colAlias = i.column.alias.mkString
              xmlBody += "<" + colAlias + ">" + rowMap(i.column.qualified) + "</" + colAlias + ">"
            }
          }
          xmlBody += "</USER>"
        }
      }
      xmlBody += "</USERS>"
    }
    Ok(scala.xml.XML.loadString(xmlBody))
    //Ok(xmlBody)
  }

}