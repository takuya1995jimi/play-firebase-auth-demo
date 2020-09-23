package usecase.LiftType

import com.google.inject.{Inject, Singleton}
import domain.{LiftType, LiftTypeRepositoryInterface}

import scala.concurrent.Future

@Singleton
case class ListUserLiftTypeService @Inject()(repository: LiftTypeRepositoryInterface) {
  def apply(userId: Int): Future[Seq[LiftType]] = repository.findByUserId(userId)
}