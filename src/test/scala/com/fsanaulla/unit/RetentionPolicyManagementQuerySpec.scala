package com.fsanaulla.unit

import com.fsanaulla.query.RetentionPolicyManagementQuery
import com.fsanaulla.utils.Helper._
import com.fsanaulla.utils.InfluxDuration._
import org.scalatest.{FlatSpecLike, Matchers}

/**
  * Created by
  * Author: fayaz.sanaulla@gmail.com
  * Date: 27.07.17
  */
class RetentionPolicyManagementQuerySpec
  extends RetentionPolicyManagementQuery
    with FlatSpecLike
    with Matchers {

  val testRPName = "testRP"
  val testDBName = "testDB"

  "create retention policy" should "return correct query" in {
    createRetentionPolicyQuery(testRPName, testDBName, 4 hours, 3, Some(4 hours), default = true) shouldEqual queryTester(s"CREATE+RETENTION+POLICY+$testRPName+ON+$testDBName+DURATION+4h+REPLICATION+3+SHARD+DURATION+4h+DEFAULT")

    createRetentionPolicyQuery(testRPName, testDBName, 4 hours, 3, None) shouldEqual queryTester(s"CREATE+RETENTION+POLICY+$testRPName+ON+$testDBName+DURATION+4h+REPLICATION+3")

    createRetentionPolicyQuery(testRPName, testDBName, 4 hours, 3, None, default = true) shouldEqual queryTester(s"CREATE+RETENTION+POLICY+$testRPName+ON+$testDBName+DURATION+4h+REPLICATION+3+DEFAULT")

    createRetentionPolicyQuery(testRPName, testDBName, 4 hours, 3, Some(4 hours)) shouldEqual queryTester(s"CREATE+RETENTION+POLICY+$testRPName+ON+$testDBName+DURATION+4h+REPLICATION+3+SHARD+DURATION+4h")

  }

  "drop retention policy" should "return correct query" in {
    dropRetentionPolicyQuery(testRPName, testDBName) shouldEqual queryTester(s"DROP+RETENTION+POLICY+$testRPName+ON+$testDBName")
  }

  "update retention policy" should "return correct query" in {
    updateRetentionPolicyQuery(testRPName, testDBName, Some(4 hours), Some(3), Some(4 hours), default = true) shouldEqual queryTester(s"ALTER+RETENTION+POLICY+$testRPName+ON+$testDBName+DURATION+4h+REPLICATION+3+SHARD+DURATION+4h+DEFAULT")

    updateRetentionPolicyQuery(testRPName, testDBName, Some(4 hours), None, None) shouldEqual queryTester(s"ALTER+RETENTION+POLICY+$testRPName+ON+$testDBName+DURATION+4h")

    updateRetentionPolicyQuery(testRPName, testDBName, Some(4 hours), Some(3), None) shouldEqual queryTester(s"ALTER+RETENTION+POLICY+$testRPName+ON+$testDBName+DURATION+4h+REPLICATION+3")

    updateRetentionPolicyQuery(testRPName, testDBName, None, Some(3), Some(4 hours)) shouldEqual queryTester(s"ALTER+RETENTION+POLICY+$testRPName+ON+$testDBName+REPLICATION+3+SHARD+DURATION+4h")
  }
}