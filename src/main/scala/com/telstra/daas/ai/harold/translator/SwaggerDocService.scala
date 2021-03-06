package com.telstra.daas.ai.harold.translator

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.swagger.akka.model.Info
import com.github.swagger.akka.{HasActorSystem, SwaggerHttpService}
import com.telstra.daas.ai.harold.translator.translate.{TranslateWva2FacebookService, TranslateWva2LiveEngageService}
import com.typesafe.config.Config

import scala.reflect.runtime.{universe => ru}

/**
  * Default path `/api-docs/swagger.json`
  *
  * Created by markmo on 12/03/2017.
  */
class SwaggerDocService(system: ActorSystem, config: Config) extends SwaggerHttpService with HasActorSystem {

  override implicit val actorSystem: ActorSystem = system

  override implicit val materializer: ActorMaterializer = ActorMaterializer()

  override val apiTypes = Seq(ru.typeOf[TranslateWva2FacebookService], ru.typeOf[TranslateWva2LiveEngageService])

  override val host = config.getString("api.host")

  override val info = Info(version = "1.0")

  override val apiDocsPath = "translator-api-docs"

  //override val externalDocs = Some(new ExternalDocs("Docs", "http://aiplatform.host"))

  //override val securitySchemeDefinitions = Map("basicAuth" -> new BasicAuthDefinition())

}