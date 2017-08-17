package com.fsanaulla.api

import com.fsanaulla.InfluxClient
import com.fsanaulla.model.InfluxImplicits._
import com.fsanaulla.model._
import com.fsanaulla.query.DataManagementQuery
import com.fsanaulla.utils.ResponseWrapper.{toQueryResult, toResult}

import scala.concurrent.Future

private[fsanaulla] trait DatabaseManagement extends DataManagementQuery { self: InfluxClient =>

  def createDatabase(dbName: String,
                     duration: Option[String] = None,
                     replication: Option[Int] = None,
                     shardDuration: Option[String] = None,
                     rpName: Option[String] = None): Future[Result] = {
    buildRequest(createDatabaseQuery(dbName, duration, replication, shardDuration, rpName)).flatMap(toResult)
  }

  def dropDatabase(dbName: String): Future[Result] = {
    buildRequest(dropDatabaseQuery(dbName)).flatMap(toResult)
  }

  def dropMeasurement(dbName: String, measurementName: String): Future[Result] = {
    buildRequest(dropMeasurementQuery(dbName, measurementName)).flatMap(toResult)
  }

  def dropShard(shardId: Int): Future[Result] = {
    buildRequest(dropShardQuery(shardId)).flatMap(toResult)
  }

  def showMeasurement(dbName: String)(implicit reader: InfluxReader[String]): Future[QueryResult[String]] = {
    buildRequest(showMeasurementQuery(dbName)).flatMap(toQueryResult[String])
  }

  def showDatabases()(implicit reader: InfluxReader[String]): Future[QueryResult[String]] = {
    buildRequest(showDatabasesQuery())
      .flatMap(toQueryResult[String])
      .map(res => res.copy(queryResult = res.queryResult))
  }
}
