package com.akkademy

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.{FunSpecLike, Matchers}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class ScalaAskExamplesTest extends FunSpecLike with Matchers {
  val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)
  val pongActor = system.actorOf(Props(classOf[ScalaPongActor]))
  describe("Pong actor") {
    it("should respond with Pong") {
      val future = askPong("Ping")
      val result = Await.result(future, 1 second)
      assert(result == "Pong")
    }
    it("should fail on unknown message") {
      val future = askPong("unknown")
      intercept[Exception] {
        Await.result(future, 1 second)
      }
    }
  }

  describe("FutureExamples"){
    import scala.concurrent.ExecutionContext.Implicits.global
    it("should print to console"){
      (pongActor ? "Ping").onSuccess({
        case x: String => println("replied with: " + x)
      })
      Thread.sleep(100)
    }
  }

  def askPong(message: String): Future[String] = (pongActor ? message).
    mapTo[String]
}
