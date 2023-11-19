import hudson.markup.RawHtmlMarkupFormatter
import hudson.security.HudsonPrivateSecurityRealm
import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.model.Jenkins

def instance = Jenkins.instanceOrNull
instance.setMarkupFormatter(new RawHtmlMarkupFormatter(false))
instance.setCrumbIssuer(new DefaultCrumbIssuer(true))
def jenkinsRealm = new HudsonPrivateSecurityRealm(false)
jenkinsRealm.createAccount('admin', 'admin')
instance.setSecurityRealm(jenkinsRealm)
instance.save()