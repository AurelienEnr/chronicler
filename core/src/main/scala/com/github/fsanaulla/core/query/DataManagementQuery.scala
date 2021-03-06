package com.github.fsanaulla.core.query

import com.github.fsanaulla.core.handlers.query.QueryHandler
import com.github.fsanaulla.core.model.HasCredentials

import scala.collection.mutable

/**
  * Created by fayaz on 27.06.17.
  */
private[fsanaulla] trait DataManagementQuery[U] {
  self: QueryHandler[U] with HasCredentials =>

  def createDatabaseQuery(dbName: String,
                          duration: Option[String],
                          replication: Option[Int],
                          shardDuration: Option[String],
                          rpName: Option[String]): U = {

    val sb = StringBuilder.newBuilder

    sb.append(s"CREATE DATABASE $dbName")

    if (duration.isDefined || replication.isDefined || shardDuration.isDefined || rpName.isDefined) {
      sb.append(" WITH")
    }

    for (d <- duration) {
      sb.append(s" DURATION $d")
    }

    for (r <- replication) {
      sb.append(s" REPLICATION $r")
    }

    for (sd <- shardDuration) {
      sb.append(s" SHARD DURATION $sd")
    }

    for (rp <- rpName) {
      sb.append(s" NAME $rp")
    }

    buildQuery("/query", buildQueryParams(sb.toString()))
  }

  def dropDatabaseQuery(dbName: String): U = {
    buildQuery("/query", buildQueryParams(s"DROP DATABASE $dbName"))
  }

  def dropSeriesQuery(dbName: String, seriesName: String): U = {
    buildQuery("/query", buildQueryParams(mutable.Map("db" -> dbName, "q" -> s"DROP SERIES FROM $seriesName")))
  }

  def dropMeasurementQuery(dbName: String, measurementName: String): U = {
    buildQuery("/query", buildQueryParams(mutable.Map("db" -> dbName, "q" -> s"DROP MEASUREMENT $measurementName")))
  }

  def deleteAllSeriesQuery(dbName: String, seriesName: String): U = {
    buildQuery("/query", buildQueryParams(mutable.Map("db" -> dbName, "q" -> s"DELETE FROM $seriesName")))
  }

  def showMeasurementQuery(dbName: String): U = {
    buildQuery("/query", buildQueryParams(mutable.Map("db" -> dbName, "q" -> s"SHOW MEASUREMENTS")))
  }

  def showDatabasesQuery(): U = {
    buildQuery("/query", buildQueryParams(s"SHOW DATABASES"))
  }

  def showFieldKeysQuery(dbName: String, measurementName: String): U = {
    buildQuery("/query", buildQueryParams(s"SHOW FIELD KEYS ON $dbName FROM $measurementName"))
  }

  def showTagKeysQuery(dbName: String,
                       measurementName: String,
                       whereClause: Option[String],
                       limit: Option[Int],
                       offset: Option[Int]): U = {
    val sb = StringBuilder.newBuilder

    sb.append("SHOW TAG KEYS ON ")
      .append(dbName)
      .append(" FROM ")
      .append(measurementName)

    for (where <- whereClause) {
      sb.append(" WHERE ").append(where)
    }

    for (l <- limit) {
      sb.append(" LIMIT ").append(l)
    }

    for (o <- offset) {
      sb.append(" OFFSET ").append(o)
    }

    buildQuery("/query", buildQueryParams(sb.toString()))
  }

  def showTagValuesQuery(dbName: String,
                         measurementName: String,
                         withKey: Seq[String],
                         whereClause: Option[String],
                         limit: Option[Int],
                         offset: Option[Int]): U = {
    require(withKey.nonEmpty, "Keys can't be empty")

    val sb = StringBuilder.newBuilder

    sb.append("SHOW TAG VALUES ON ")
      .append(dbName)
      .append(" FROM ")
      .append(measurementName)

    if (withKey.lengthCompare(1) == 0) {
      sb.append(" WITH KEY = ").append(withKey.head)
    } else {
      sb.append(" WITH KEY IN ").append(s"(${withKey.mkString(",")})")
    }

    for (where <- whereClause) {
      sb.append(" WHERE ").append(where)
    }

    for (l <- limit) {
      sb.append(" LIMIT ").append(l)
    }

    for (o <- offset) {
      sb.append(" OFFSET ").append(o)
    }

    buildQuery("/query", buildQueryParams(sb.toString()))
  }
}
