package com.telstra.daas.ai.harold.translator

import akka.http.scaladsl.server.RouteConcatenation
import com.telstra.daas.ai.harold.translator.translate.{TranslateWva2FacebookService, TranslateWva2LiveEngageService}

/**
  * The REST API layer. It exposes the REST services, but does not provide any
  * web server interface.<br/>
  *
  * Notice that it requires to be mixed in with ``core.CoreActors``, which
  * provides access to the top-level actors that make up the system.<br/>
  *
  * Created by markmo on 12/03/2017.
  */
trait Api extends RouteConcatenation {
  this: CoreActors with Core =>

  private implicit val _ = system.dispatcher

  val routes =
    new TranslateWva2FacebookService(facebookTranslationActor, log).routes ~
      new TranslateWva2LiveEngageService(liveEngageTranslationActor, log).routes ~
      new SwaggerDocService(system, config).routes

}
