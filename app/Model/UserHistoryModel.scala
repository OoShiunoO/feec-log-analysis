package Model

import controllers.QueryData

/**
 * Created by chiashunlu on 2015/12/2.
 */
case class UserHistoryModel(api_logtime: String, action: String, uri:String, para: String, referrer: String)
