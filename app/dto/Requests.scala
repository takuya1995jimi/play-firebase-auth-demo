package dto

case class RegisterLiftTypesToTrainingMenuRequest(
                                                   trainingMenuId: Int,
                                                   liftTypes: List[TargetedLiftTypeRequest]
                                                 )

case class TargetedLiftTypeRequest(
                                    id: Int,
                                    lightRep: Int = 0,
                                    lightWeight: Int = 0,
                                    lightSetCount: Int = 0,
                                    heavyRep: Int = 0,
                                    heavyWeight: Int = 0,
                                    heavySetCount: Int = 0,
                                    targetBodyPartIds: List[Int],
                                    isMain: Int
                                  )
case class LiftTypeRequest(
                            name: Option[String],
                            referenceUrl: Option[Option[String]],
                            description: Option[Option[String]],
                            defaultRep: Option[Int],
                            defaultWeight: Option[Int],
                            defaultSetCount: Option[Int],
                            shareFlag: Option[Boolean]
                          )