package business

import java.sql.{DriverManager, ResultSet, Connection, Statement}
import play.api.mvc.{Action, Controller}

import Model.CVRModel

/**
 * Created by chiashunlu on 2015/12/4.
 */
object CVRQuery {

  def getCVR(src: String) = {

    Class.forName("org.apache.hive.jdbc.HiveDriver");
    val con = DriverManager.getConnection("jdbc:hive2://52.69.25.247:10000/default", "hadoop", "");
    val stmt = con.createStatement();
    val sql = "select * from " + src;
    System.out.println("Running: " + sql);
    val res = stmt.executeQuery(sql);
    val result = scala.collection.mutable.ArrayBuffer.empty[CVRModel]
    while (res.next()) {
      result += CVRModel(res.getInt(1), res.getInt(2), res.getInt(3), res.getDouble(4), res.getDouble(5), res.getString(6))
    }
    con.close();

    result.toList

  }

}
