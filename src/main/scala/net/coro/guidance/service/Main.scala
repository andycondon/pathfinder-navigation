package net.coro.guidance.service

import akka.actor.{Actor, ActorSystem, Props}
import akka.io.IO
import net.coro.guidance.service.route.GuidanceApi
import spray.can.Http

object Main extends App {
  implicit val system = ActorSystem("guidance-service")

  val service   = system.actorOf(Props(classOf[GuidanceServiceActor]), "GuidanceService")
  val interface = "192.168.1.7"
  val port      = 8080

  IO(Http) ! Http.Bind(service, interface, port)
}

class GuidanceServiceActor extends Actor with GuidanceApi {
  def actorRefFactory = context

  def receive = runRoute(guidanceRoute)
}
