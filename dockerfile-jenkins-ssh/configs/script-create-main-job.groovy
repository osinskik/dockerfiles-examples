import jenkins.model.*
import java.util.logging.Logger
import javax.xml.transform.stream.StreamSource

Logger logger = Logger.getLogger("")

def jobName = "generateJobs.dsl"

def git_url = 'https://github.com/osinskik/job-dsl-playground'

def scm = """\
    <scm class="hudson.plugins.git.GitSCM">
      <configVersion>2</configVersion>
      <userRemoteConfigs>
        <hudson.plugins.git.UserRemoteConfig>
          <url><![CDATA[${git_url}]]></url>
        </hudson.plugins.git.UserRemoteConfig>
      </userRemoteConfigs>
      <branches>
        <hudson.plugins.git.BranchSpec>
          <name>**</name>
        </hudson.plugins.git.BranchSpec>
      </branches>
      <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
      <submoduleCfg class="list"/>
      <extensions/>
    </scm>
  """

  def configXml = """\
  <?xml version='1.0' encoding='UTF-8'?>
  <project>
    <actions/>
    <description>Create Jenkins jobs from DSL groovy files</description>
    <keepDependencies>false</keepDependencies>
    <properties>
    </properties>
    ${scm}
    <canRoam>true</canRoam>
    <disabled>false</disabled>
    <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
    <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
    <triggers/>
    <concurrentBuild>false</concurrentBuild>
    <builders>
      <javaposse.jobdsl.plugin.ExecuteDslScripts plugin="job-dsl@1.37">
        <targets>${jobName}</targets>
        <usingScriptText>false</usingScriptText>
        <ignoreExisting>false</ignoreExisting>
        <removedJobAction>IGNORE</removedJobAction>
        <removedViewAction>IGNORE</removedViewAction>
        <lookupStrategy>JENKINS_ROOT</lookupStrategy>
        <additionalClasspath></additionalClasspath>
      </javaposse.jobdsl.plugin.ExecuteDslScripts>
    </builders>
    <publishers/>
    <buildWrappers/>
  </project>
""".stripIndent()

def xmlStream = new ByteArrayInputStream(configXml.getBytes())
try {
  def seedJob = Jenkins.instance.getItem(jobName)
  if (!seedJob) {
    seedJob = Jenkins.instance.createProjectFromXML(jobName, xmlStream)
    logger.info("DSL job ${jobName} created")
  } else {
    seedJob.updateByXml(new StreamSource(xmlStream))
    logger.info("DSL job ${jobName} updated")
  }
  seedJob.scheduleBuild(0, null)
  logger.info("DSL job ${jobName} should run now")
} catch (ex) {
 logger.error("cannot run initial build ${ex}")
 println configXml.stripIndent()
}