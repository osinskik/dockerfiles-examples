import jenkins.*
import jenkins.model.*
import hudson.model.*
import jenkins.model.Jenkins
import org.jenkinsci.plugins.authorizeproject.*
import org.jenkinsci.plugins.authorizeproject.strategy.*
import jenkins.security.QueueItemAuthenticatorConfiguration

def instance = Jenkins.getInstance()

// Define which strategies you want to allow to be set per project

def authenticators = QueueItemAuthenticatorConfiguration.get().getAuthenticators()

// sets global strategy
authenticators.removeAll { it instanceof GlobalQueueItemAuthenticator }
authenticators.add(new GlobalQueueItemAuthenticator(new TriggeringUsersAuthorizationStrategy()))




// It can be also configured with specific settings for builds.
// Then in DSL

// job("job1") {
//   properties {
//     authorizeProjectProperty {
//       strategy {
//         triggeringUsersAuthorizationStrategy()
//       }   
//     }   
//   ...
// } 


// def configureProjectAuthenticator = true
// for(authenticator in authenticators) {
//   if(authenticator instanceof ProjectQueueItemAuthenticator) {
//     configureProjectAuthenticator = false
//   }
// }
// if(configureProjectAuthenticator) {
//   def strategyMap = [
//     (instance.getDescriptor(AnonymousAuthorizationStrategy.class).getId()): true, 
//     (instance.getDescriptor(TriggeringUsersAuthorizationStrategy.class).getId()): true,
//     (instance.getDescriptor(SpecificUsersAuthorizationStrategy.class).getId()): true,
//     (instance.getDescriptor(SystemAuthorizationStrategy.class).getId()): false
//   ]

//   authenticators.add(new ProjectQueueItemAuthenticator(strategyMap))
// }

instance.save()