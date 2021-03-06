package x

import org.scalatest.{FlatSpec, Matchers}

class ParserTest extends FlatSpec with Matchers {

  "A single monitor" should "parse" in {
    val monitors = Parser.parseMonitors("org.h2.engine.Session@99fe2a4 at org.h2.jdbc.JdbcPreparedStatement.execute(JdbcPreparedStatement.java:190)Locked Synchronizers:")
    monitors shouldBe Seq(LockedMonitor("org.h2.engine.Session@99fe2a4", "org.h2.jdbc.JdbcPreparedStatement.execute", "JdbcPreparedStatement.java:190"))
  }

  "Multiple monitors" should "parse" in {
    val input = "org.h2.engine.Session@93a14d3 at org.h2.jdbc.JdbcPreparedStatement.execute(JdbcPreparedStatement.java:190)" +
      "java.lang.String@1fbcbfb4 at com.thoughtworks.go.server.dao.JobInstanceSqlMapDao.buildByIdWithTransitions(JobInstanceSqlMapDao.java:89)" +
      "java.lang.String@21385501 at com.thoughtworks.go.server.service.ScheduleService.jobCompleting(ScheduleService.java:651)" +
      "java.lang.String@55decdee at com.thoughtworks.go.server.service.ScheduleService.jobCompleting(ScheduleService.java:651)" +
      "Locked Synchronizers:"
    val monitors = Parser.parseMonitors(input)
    monitors should have(length(4))
  }

}
