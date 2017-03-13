package com.telstra.daas.ai.harold.translator.translate

import akka.actor.{Actor, ActorLogging}
import com.telstra.daas.ai.harold.translator.models._
import spray.json._

/**
  * Created by markmo on 13/03/2017.
  */
class LiveEngageChatTranslationActor extends Actor with ActorLogging with LpChatJsonSupport {

  override def receive: Receive = {

    case WvaPayload(from, WvaMessageResponse(_, _, WvaMessage(_, _, _, inputvalidation, _, layout, _, nodePosition, text))) =>
      val result = layout match {
        case Some(WvaLayout(name, _)) =>
          inputvalidation match {
            case Some(WvaInputValidation(Some(oneOf), _, _)) =>
              val buttons = oneOf.mkString("\n")
              val textReply =
                s"""
                  |${text.head}
                  |Type one of the following options:
                  |$buttons
                """.stripMargin
              LpMessage(LpMessageEvent(eventType = "line", text = textReply, textType = "plain"))

            case _ => LpMessage(LpMessageEvent(eventType = "line", text = text.head, textType = "plain"))
          }
        case _ => LpMessage(LpMessageEvent(eventType = "line", text = text.head, textType = "plain"))
      }
      sender ! result.toJson
  }

}
