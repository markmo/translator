package com.telstra.daas.ai.harold.translator.translate

import akka.actor.{Actor, ActorLogging}
import com.telstra.daas.ai.harold.translator.models._
import spray.json._

/**
  * Created by markmo on 13/03/2017.
  */
class FacebookTranslationActor extends Actor with ActorLogging with FacebookJsonSupport {

  import Builder._

  override def receive: Receive = {

    case WvaPayload(from, WvaMessageResponse(_, _, WvaMessage(_, _, _, inputvalidation, _, layout, _, nodePosition, text))) =>
      val result = layout match {
        case Some(WvaLayout(name, _)) =>
          inputvalidation match {
            case Some(WvaInputValidation(Some(oneOf), _, _)) =>
              if (oneOf.length > 3) {
                log.debug("Building Card")
                val buttons = oneOf map { el =>
                  FacebookPostbackButton(el, JsString(el))
                }
                val elements = List(
                  FacebookElement(
                    "",
                    text.head,
                    "",
                    "",
                    buttons
                  )
                )
                (
                  genericTemplate
                    forSender from
                    withElements elements
                    build()
                  ).toJson
              } else {
                log.debug("Building Quick Reply")
                val quickReplies = oneOf map { el =>
                  FacebookQuickReply(
                    contentType = "text",
                    title = el,
                    payload = el
                  )
                }
                FacebookQuickReplyTemplate(
                  FacebookRecipient(from),
                  FacebookQuickReplyMessage(
                    text.head,
                    quickReplies
                  )
                ).toJson
              }
            case _ => (messageElement forSender from withText text.head build()).toJson
          }
        case _ => (messageElement forSender from withText text.head build()).toJson
      }
      sender ! result

  }

}
