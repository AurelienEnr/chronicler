package com.github.fsanaulla.io

import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model.Uri
import com.github.fsanaulla.handlers.{AkkaQueryHandler, AkkaRequestHandler, AkkaResponseHandler}
import com.github.fsanaulla.model.{InfluxCredentials, QueryResult}
import com.github.fsanaulla.query.DatabaseOperationQuery
import com.github.fsanaulla.utils.constants.Epochs
import com.github.fsanaulla.utils.constants.Epochs.Epoch
import spray.json.JsArray

import scala.concurrent.{ExecutionContext, Future}

protected[fsanaulla] trait AkkaReader
  extends AkkaRequestHandler
    with AkkaResponseHandler
    with AkkaQueryHandler
    with DatabaseOperationQuery[Uri] { self: ReadOperations =>

  protected implicit val ex: ExecutionContext
  protected implicit val credentials: InfluxCredentials

  override def readJs0(dbName: String,
                       query: String,
                       epoch: Epoch = Epochs.NANOSECONDS,
                       pretty: Boolean = false,
                       chunked: Boolean = false): Future[QueryResult[JsArray]] = {

    readRequest(readFromInfluxSingleQuery(dbName, query, epoch, pretty, chunked), GET)
      .flatMap(toQueryJsResult)
  }

  override def bulkReadJs0(dbName: String,
                           querys: Seq[String],
                           epoch: Epoch = Epochs.NANOSECONDS,
                           pretty: Boolean = false,
                           chunked: Boolean = false): Future[QueryResult[Seq[JsArray]]] = {
    readRequest(readFromInfluxBulkQuery(dbName, querys, epoch, pretty, chunked), GET)
      .flatMap(toBulkQueryJsResult)
  }
}
