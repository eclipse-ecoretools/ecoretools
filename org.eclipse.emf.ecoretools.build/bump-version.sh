
export VERSION=$1
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:0.22.0:set-version -DnewVersion=$VERSION-SNAPSHOT
sed -i -e "s/export VERSION=.*$/export VERSION="$VERSION"/g" publish-nightly.sh
