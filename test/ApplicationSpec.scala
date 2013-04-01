package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.test.FakeApplication


class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        val result = route(FakeRequest(GET, "/"))
        result must beNone
      }
    }

    "send 200 on a normal, correct task schedule request" in {
      running(FakeApplication()) {
        val result = route(FakeRequest(GET, "/tasks/schedule?class=example.Itemize")).get
        status(result) must equalTo(OK)
        contentAsString(result) must beEmpty
      }
    }
    "send 400 on a wrong task name schedule request" in {
      running(FakeApplication()) {
        val result = route(FakeRequest(GET, "/tasks/schedule?class=NotExist")).get
        status(result) must equalTo(BAD_REQUEST)
        contentAsString(result) must beEmpty
      }
    }
    "send 400 on a non-runnable task schedule request" in {
      running(FakeApplication()) {
        val result = route(FakeRequest(GET, "/tasks/schedule?class=test.ApplicationSpec")).get
        status(result) must equalTo(BAD_REQUEST)
        contentAsString(result) must beEmpty
      }
    }

    "send 200 and number on a count scheduled request" in {
      running(FakeApplication()) {
        val result = route(FakeRequest(GET, "/tasks/count-scheduled")).get
        status(result) must equalTo(OK)
        contentAsString(result).toInt must be_>=(0)
      }
    }

    "send 200 and number on a count running request" in {
      running(FakeApplication()) {
        val result = route(FakeRequest(GET, "/tasks/count-scheduled")).get
        status(result) must equalTo(OK)
        contentAsString(result).toInt must be_>=(0)
      }
    }
  }
}