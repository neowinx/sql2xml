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
    xmlBody += "<payload>"
    DB.withConnection { implicit c =>
      val consulta = SQL(" SELECT * FROM User ")()
      println(consulta)
      consulta foreach {
        row => {
          xmlBody += "<user>"
          val rowMap = row.asMap
          row.metaData.ms map {
            i => {
              val colAlias = i.column.alias.mkString.toLowerCase
              val valor = rowMap(i.column.qualified) match {
                case Some(x) => x
                case None => ""
                case y => y
              }
              xmlBody += "<" + colAlias + ">" + valor + "</" + colAlias + ">"
            }
          }
          xmlBody += "</user>"
        }
      }
      xmlBody += "</payload>"
    }
    Ok(scala.xml.XML.loadString(xmlBody))
    //Ok(xmlBody)
  }

}