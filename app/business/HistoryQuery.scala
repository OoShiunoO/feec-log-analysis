package business

import scala.collection.mutable.StringBuilder
//import org.apache.hive.jdbc.HiveDriver
import java.sql.{DriverManager, ResultSet, Connection, Statement}
import play.api.mvc.{Action, Controller}

import controllers.QueryData
import Model.UserHistoryModel

/**
 * Created by chiashunlu on 2015/12/2.
 */
object HistoryQuery {

  def getHistory(queryData: QueryData) = {

    Class.forName("org.apache.hive.jdbc.HiveDriver");
    val con = DriverManager.getConnection("jdbc:hive2://52.69.25.247:10000/default", "hadoop", "");
    val stmt = con.createStatement();
    val sql_vid = "select * from venraas_extracted_weblog where ven_guid='" + queryData.vid + "' and api_logtime >= '" +
      queryData.start_time + "' and api_logtime <= '" + queryData.end_time + "' order by api_logtime";
    val sql_uid = "select * from venraas_extracted_weblog  join (select ven_guid, uid from venraas_relation_vid_uid where uid='" +
      queryData.uid + "' group by ven_guid, uid) relation on  venraas_extracted_weblog.ven_guid=relation.ven_guid where api_logtime >= '" +
      queryData.start_time + "' and api_logtime <= '" + queryData.end_time + "' order by api_logtime";
    var sql = ""
    if (queryData.vid=="") {
      sql = sql_uid
    } else {
      sql = sql_vid
    }
    System.out.println("Running: " + sql);
    val res = stmt.executeQuery(sql);
    val result = scala.collection.mutable.ArrayBuffer.empty[UserHistoryModel]
    while (res.next()) {
      result += UserHistoryModel(res.getString(1), res.getString(2), res.getString(3), res.getString(4),res.getString(5))
    }
    con.close();

    result.toList
//    val result = scala.collection.mutable.ArrayBuffer.empty[UserHistoryModel]
//    result += UserHistoryModel("2015-11-11 14:00:00", "pageload", "/index.html", "?name=Shiun", "http://google.com.tw")
//    result += UserHistoryModel("2015-11-11 14:10:00", "pageload", "/product.html", "?name=Shiun", "http://google.com.tw")
//    result += UserHistoryModel("2015-11-11 14:20:00", "pageload", "/login.html", "?name=Shiun", "http://google.com.tw")
//    result.toList
  }

}
