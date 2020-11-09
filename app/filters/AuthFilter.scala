package filters

import java.io.FileInputStream
import akka.stream.Materializer
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.{FirebaseApp, FirebaseOptions}
import com.google.inject.Inject
import play.api.Configuration
import play.api.mvc.Results.Forbidden
import play.api.mvc.{Filter, RequestHeader, Result}
import utils.Utils
import scala.concurrent.{ExecutionContext, Future}

class AuthFilter @Inject()(config: Configuration, utils: Utils)
    (
        implicit executionContext: ExecutionContext,
        implicit val mat: Materializer
    ) extends Filter {

  val serviceAccount = new FileInputStream(
    config.get[String]("secret.key.path")
  )
  val options: FirebaseOptions =
    new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build
  FirebaseApp.getApps.size match {
    case 0 => FirebaseApp.initializeApp(options)
    case _ => FirebaseApp.getApps.get(0)
  }

  def apply(
      nextFilter: RequestHeader => Future[Result]
  )(rh: RequestHeader): Future[Result] = {
    implicit def Pipeline[T](x: T): utils.Pipeline[T] = utils.Pipeline(x)

    rh.headers
        .get("Authorization")
        .getOrElse("") |> utils.getFirebaseUid match {
      case uid if uid.nonEmpty =>
        addUserIdToHeader(uid) flatMap { userIdHeader =>
          nextFilter(rh) map {
            _.withHeaders(userIdHeader)
          }
        }
      case _ => Future(Forbidden("invalid"))
    }
  }

  def addUserIdToHeader(uid: String): Future[(String, String)] = {
    implicit def Pipeline[T](x: T): utils.Pipeline[T] = utils.Pipeline(x)

    utils.getUserId(uid) flatMap { userId =>
      if (userId != 0) Future(("user_id", userId.toString))
      else
        (uid |> utils.createGuestUser) map { userId =>
          ("user_id", userId.toString)
        }
    }
  }
}
