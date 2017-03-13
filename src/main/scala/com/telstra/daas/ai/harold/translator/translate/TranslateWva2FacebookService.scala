package com.telstra.daas.ai.harold.translator.translate

import javax.ws.rs.Path

import akka.actor.ActorRef
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout
import com.telstra.daas.ai.harold.translator.models.{WvaJsonSupport, WvaMessageResponse, WvaPayload}
import io.swagger.annotations._
import spray.json._

import scala.concurrent.ExecutionContext

/**
  * Created by markmo on 12/03/2017.
  */
@Api(value = "/translate-wva-facebook/{sender}", produces = "application/json")
@Path("/translate-wva-facebook/{sender}")
class TranslateWva2FacebookService(facebookTranslationActor: ActorRef,
                                   log: LoggingAdapter
                                  )(implicit executionContext: ExecutionContext)
  extends WvaJsonSupport {

  import akka.pattern.ask

  import scala.concurrent.duration._

  implicit val timeout = Timeout(20.seconds)

  val routes = translateWva2Facebook

  @ApiOperation(httpMethod = "POST", response = classOf[JsValue], value = "Returns a translated message")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(
      name = "sender",
      value = "Sender ID",
      required = true,
      dataType = "string",
      paramType = "path"
    ),
    new ApiImplicitParam(
      name = "body",
      value = "WVA JSON Message",
      required = true,
      dataType = "com.telstra.daas.ai.harold.translator.models.WvaMessageResponse",
      paramType = "body"
    )
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return result", response = classOf[JsValue]),
    new ApiResponse(code = 404, message = "Empty text in request")
  ))
  def translateWva2Facebook =
    post {
      pathPrefix("translate-wva-facebook") {
        path(Segment) { sender =>
          entity(as[WvaMessageResponse]) { msg =>
            log.debug("received body:\n{}", msg.toJson.prettyPrint)
            val f = facebookTranslationActor ? WvaPayload(sender, msg)
            complete {
              f.mapTo[JsValue] map { result =>
                HttpEntity(ContentTypes.`application/json`, result.toJson.compactPrint)
              }
            }
          }
        }
      }
    }

}
