// @GENERATOR:play-routes-compiler
// @SOURCE:/root/muscle-training_manager/conf/routes
// @DATE:Mon Sep 14 01:17:22 JST 2020


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
