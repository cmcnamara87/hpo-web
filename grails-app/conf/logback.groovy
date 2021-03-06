import grails.util.BuildSettings
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import java.nio.charset.Charset

conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    charset = Charset.forName('UTF-8')

    pattern =
      '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} ' + // Date
        '%clr(%5p) ' + // Log level
        '%clr(---){faint} %clr([%15.15t]){faint} ' + // Thread
        '%clr(%-40.40logger{39}){cyan} %clr(:){faint} ' + // Logger
        '%m%n%wex' // Message
  }
}
//logger("org.hibernate.SQL",TRACE,['STDOUT'],false)
//logger("org.hibernate.type.descriptor.sql.BasicBinder",TRACE,['STDOUT'],false)
def targetDir = BuildSettings.TARGET_DIR
def env = System.getProperty("grails.env")
if (env != "production"  && targetDir != null) {
  appender("FULL_STACKTRACE", FileAppender) {
    file = "${targetDir}/stacktrace.log"
    append = true
    encoder(PatternLayoutEncoder) {
      pattern = "%level %logger - %msg%n"
    }
  }
  appender("HIBERNATE_SQL", FileAppender) {
    file = "${targetDir}/hibernate-sql.log"
    append = true
    encoder(PatternLayoutEncoder) {
      pattern = "%level %logger - %msg%n"
    }
  }
  appender("HPO", FileAppender) {
    file = "${targetDir}/hpo.log"
    append = true
    encoder(PatternLayoutEncoder) {
      pattern = "%level %logger - %msg%n"
    }
  }
  // Logging for SQL
  logger("org.hibernate.SQL", DEBUG, ['HIBERNATE_SQL'], false)
  logger("org.hibernate.type.descriptor.sql.BasicBinder", DEBUG, ['HIBERNATE_SQL'], false)

  logger("hpo.api", WARN, ['HPO'], false)
  //
  logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}
root(ERROR, ['STDOUT'])
scan("10 seconds")
