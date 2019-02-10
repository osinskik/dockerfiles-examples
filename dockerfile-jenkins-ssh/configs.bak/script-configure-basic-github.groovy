@Grapes([
    @Grab(group='org.yaml', module='snakeyaml', version='1.17')
])
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.SafeConstructor
import java.util.logging.Logger
import jenkins.model.Jenkins

Logger logger = Logger.getLogger("")
Jenkins jenkins = Jenkins.getInstance()
Yaml yaml = new Yaml(new SafeConstructor())

String configPath = System.getenv("JENKINS_GROOVY_INIT_FOLDER")
String creds = "${configPath}/git_config.yml"
try {
    configText = new File(creds).text
} catch (FileNotFoundException e) {
    logger.severe("no config file ${creds}")
    jenkins.doSafeExit(null)
    System.exit(1)
}
try {
    gitConfig = yaml.load(configText)
} catch (FileNotFoundException e) {
    logger.severe("cannot parse ${creds} as YML")
    jenkins.doSafeExit(null)
    System.exit(1)
}

def gitScm = jenkins.getDescriptorByType(hudson.plugins.git.GitSCM.DescriptorImpl.class)

gitScm.setGlobalConfigName(gitConfig['user.name'])
gitScm.setGlobalConfigEmail(gitConfig['user.email'])
jenkins.save()