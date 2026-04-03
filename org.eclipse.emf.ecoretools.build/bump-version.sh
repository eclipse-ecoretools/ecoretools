
export VERSION=$1
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:5.0.2:set-version -DnewVersion=$VERSION-SNAPSHOT
sed -i -e "s/export VERSION=.*$/export VERSION="$VERSION"/g" publish-nightly-jiro.sh
