## Introduction
bmapper is an API for the berkeleymapper application and can be extended for use in other applications. 

Instructions for using bmapper are found in the <a href='https://github.com/bnhm/bmapper/wiki'>wiki</a>

## Developers
All external libraries are controlled by gradle, so to get started, you need to just:

```
git clone {this_repo}
# install gradle if you have not done so, then...
gradle build

# source ~/.profile (or ~/.bashrc)
deployBmapper
```         

The gradle build process will create a WAR file called ```dist/berkeleymapper.war```

There is a file called config.props which you can create by copying the file ```config.props.template```

Certain connections require importing certificate to allow 3rd party access:
First, obtain an exported copy of certificate. (On chrome, developer tools->security)
Second, import into keystore using the keytool program
```
keytool -import -alias example -keystore /etc/ssl/certs/java/cacerts -file {FILEAME}
```
Note that cert files are stored in ~jdeck/certs

